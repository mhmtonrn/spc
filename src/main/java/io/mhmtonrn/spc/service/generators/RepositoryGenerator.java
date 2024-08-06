package io.mhmtonrn.spc.service.generators;

import io.mhmtonrn.spc.model.database.res.TableColumnDTO;
import io.mhmtonrn.spc.model.springcli.CreateAppDTO;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class RepositoryGenerator extends PojoGenerator {

    public static void generateClass(CreateAppDTO createAppDTO, Path path, List<TableColumnDTO> columns) throws IOException {

        Path entityPath = Paths.get(path + "/repository");

        FileUtils.forceMkdir(entityPath.toFile());


        Map<String, StringBuilder> dtos = new HashMap<>();
        Set<String> tables = columns.stream().collect(Collectors.groupingBy(TableColumnDTO::getTableName)).keySet();

        for (String tableName : tables) {
            if (!dtos.containsKey(tableName)) {
                dtos.put(tableName, new StringBuilder());
            }

            TableColumnDTO id = columns.stream().filter(p -> p.getTableName().equals(tableName)).filter(p -> p.getColumnName().equals("id")).toList().stream().findFirst().orElse(null);
            String fieldType = mapDataType(id.getDataType());
            StringBuilder dto = dtos.get(tableName);
            dto.append("package ").append(createAppDTO.getProjectPackageName()).append(".").append(createAppDTO.getAppName()).append(".repository;\n\n");
            dto.append("import org.springframework.data.jpa.repository.JpaRepository;\n");
            dto.append("import ").append(createAppDTO.getProjectPackageName()).append(".").append(createAppDTO.getAppName()).append(".data.entity.").append(toCamelCase(tableName)).append(";\n\n");

            dto.append("public interface ").append(toCamelCase(tableName)).append("Repository extends JpaRepository<").append(toCamelCase(tableName)).append(",").append(fieldType).append(">{\n\n");
            dto.append("}");

        }


        for (Map.Entry<String, StringBuilder> tableName : dtos.entrySet()) {
            try (FileWriter writer = new FileWriter(entityPath + "/" + toCamelCase(tableName.getKey()) + "Repository.java")) {
                writer.write(tableName.getValue().toString());
            }
        }


    }
}
