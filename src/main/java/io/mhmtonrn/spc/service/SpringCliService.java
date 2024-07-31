package io.mhmtonrn.spc.service;

import io.mhmtonrn.spc.model.springcli.CreateAppDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpringCliService {

    @Value("${spc.app.basic}")
    private String basicApp;

    public ResponseEntity<InputStreamResource> createApp(CreateAppDTO createAppDTO) throws IOException, InterruptedException {
        UUID uuid = UUID.randomUUID();

        StringBuilder cmd = new StringBuilder();
        cmd.append("cd temp ");
        cmd.append(" && mkdir ").append(uuid);
        cmd.append(" && cd ").append(uuid);
        cmd.append(" && java -jar /home/idea/projects/spc/doc/spring-cli-0.9.0.jar boot new ")
                .append(" --from ").append(basicApp)
                .append(" --group-id ").append(createAppDTO.getProjectGroupId())
                .append(" --artifact-id ").append(createAppDTO.getProjectArtifactId())
                .append(" --version ").append(createAppDTO.getProjectVersion())
                .append(" --description ").append(createAppDTO.getProjectDescription())
                .append(" --package-name ").append(createAppDTO.getProjectPackageName())
                .append(" --package-name ").append(createAppDTO.getProjectPackageName())
                .append(" --name ").append(createAppDTO.getAppName());

        String[] cmdBash = {"/bin/bash", "-c", cmd.toString()};
        Runtime.getRuntime().exec(cmdBash).waitFor();

        StringBuilder cmdZip = new StringBuilder();
        cmdZip.append(" cd temp/").append(uuid);
        cmdZip.append(" && zip -r ").append(createAppDTO.getAppName()).append(" ").append(createAppDTO.getAppName());

        cmdBash[2] = cmdZip.toString();
        Runtime.getRuntime().exec(cmdBash).waitFor();

        File file = new File("temp/"+uuid+"/"+createAppDTO.getAppName()+".zip");

        new Timer().schedule(new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
            cmdBash[2] = "rm -rf temp/"+uuid;
            Runtime.getRuntime().exec(cmdBash).waitFor();
            }
        }, 20000);




        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+createAppDTO.getAppName()+".zip");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        header.add("File-Name", createAppDTO.getAppName()+".zip");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(resource);
    }
}
