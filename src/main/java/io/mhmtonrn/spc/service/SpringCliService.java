package io.mhmtonrn.spc.service;

import io.mhmtonrn.spc.model.database.req.TableDetailRequestDTO;
import io.mhmtonrn.spc.model.database.res.DataBaseTableDTO;
import io.mhmtonrn.spc.model.database.res.TableColumnDTO;
import io.mhmtonrn.spc.model.springcli.CreateAppDTO;
import io.mhmtonrn.spc.service.generators.PojoBuilder;
import io.mhmtonrn.spc.service.impl.ProjectZipService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpringCliService {

    private final ProjectCreatorService projectCreatorService;
    private final ProjectZipService projectZipService;
    private final DatabaseService databaseService;


    public ResponseEntity<InputStreamResource> createApp(CreateAppDTO createAppDTO) throws IOException, InterruptedException {

        String projectPath = projectCreatorService.createProjectFromBasicTemplate(createAppDTO);

        Path entityPath = Paths.get(projectCreatorService.getInnerPath(createAppDTO, projectPath));

        List<DataBaseTableDTO> dataBaseTable = createAppDTO.getTables();
        TableDetailRequestDTO tablesDetail = new TableDetailRequestDTO();
        tablesDetail.setDatabase(createAppDTO.getDatabase());

        for (DataBaseTableDTO dataBaseTableDTO : dataBaseTable) {
            tablesDetail.setTableName(dataBaseTableDTO.getTableName());
            tablesDetail.setSchemaName(dataBaseTableDTO.getSchemaName());
            List<TableColumnDTO> tableColumnDTOs = databaseService.getTablesDetail(tablesDetail);
            new PojoBuilder(createAppDTO, entityPath, tableColumnDTOs).entity().dto().mapper();

        }

        String projectZipFile = projectZipService.createZip(createAppDTO, projectPath);

        removeTemp(projectPath);
        HttpHeaders header = getHttpHeaders(createAppDTO);

        File file = new File(projectZipFile);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(resource);
    }

    private static void removeTemp(String projectPath) {
        new Timer().schedule(new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", "rm -rf " + projectPath});
            }
        }, 200000);
    }

    private static HttpHeaders getHttpHeaders(CreateAppDTO createAppDTO) {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + createAppDTO.getAppName() + ".zip");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        header.add("File-Name", createAppDTO.getAppName() + ".zip");
        return header;
    }


}
