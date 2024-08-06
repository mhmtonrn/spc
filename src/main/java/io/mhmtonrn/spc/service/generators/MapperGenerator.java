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

public class MapperGenerator extends PojoGenerator {

    public static void generateClass(CreateAppDTO createAppDTO, Path path, List<TableColumnDTO> columns) throws IOException {
        Path dtoPath = Paths.get(path + "/data/mapper");
        FileUtils.forceMkdir(dtoPath.toFile());

        Map<String, StringBuilder> dtos = new HashMap<>();
        Set<String> tables = columns.stream().collect(Collectors.groupingBy(TableColumnDTO::getTableName)).keySet();

        for (String tableName : tables) {
            if (!dtos.containsKey(tableName)) {
                dtos.put(tableName, new StringBuilder());
            }
            StringBuilder dto = dtos.get(tableName);
            dto.append("package ").append(createAppDTO.getProjectPackageName()).append(".").append(createAppDTO.getAppName()).append(".data.mapper;\n\n");
            dto.append("import ").append(createAppDTO.getProjectPackageName()).append(".").append(createAppDTO.getAppName()).append(".data.dto.").append(toCamelCase(tableName)).append("DTO;\n");
            dto.append("import ").append(createAppDTO.getProjectPackageName()).append(".").append(createAppDTO.getAppName()).append(".data.entity.").append(toCamelCase(tableName)).append(";\n\n");
            dto.append("import ").append("org.mapstruct.Mapper;").append(";\n\n");
            dto.append("@Mapper(componentModel = \"spring\")\n");
            dto.append("public interface ").append(toCamelCase(tableName)).append("Mapper").append(" {\n\n");
            dto.append("\t").append(toCamelCase(tableName)).append("DTO").append(" entityToDto(").append(toCamelCase(tableName)).append(" ").append(tableName).append(");\n");
            dto.append("\t").append(toCamelCase(tableName)).append(" dtoToEntity(").append(toCamelCase(tableName)).append("DTO").append(" ").append(tableName).append("DTO);\n\n");
            dto.append("}");

        }


        for (Map.Entry<String, StringBuilder> tableName : dtos.entrySet()) {
            try (FileWriter writer = new FileWriter(dtoPath + "/" + toCamelCase(tableName.getKey()) + "Mapper.java")) {
                writer.write(tableName.getValue().toString());
            }
        }
    }
}
