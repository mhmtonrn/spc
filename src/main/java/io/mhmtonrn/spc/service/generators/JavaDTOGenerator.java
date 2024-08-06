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

public class JavaDTOGenerator extends PojoGenerator {

    public static void generateClass(CreateAppDTO createAppDTO, Path path, List<TableColumnDTO> columns) throws IOException {


        Path dtoPath = Paths.get(path + "/data/dto");

        FileUtils.forceMkdir(dtoPath.toFile());

        Map<String, StringBuilder> dtos = new HashMap<>();
        for (TableColumnDTO column : columns) {
            String tableName = column.getTableName();
            if (!dtos.containsKey(tableName)) {
                dtos.put(tableName, new StringBuilder());
            }

            StringBuilder dto = dtos.get(tableName);

            if (dto.isEmpty()) {
                dto.append("package ").append(createAppDTO.getProjectPackageName()).append(".").append(createAppDTO.getAppName()).append(".data.dto;\n\n");
                dto.append("public class ").append(toCamelCase(tableName)).append("DTO").append(" {\n\n");
            }

            dto.append("    private ").append(mapDataType(column.getDataType())).append(" ").append(toCamelCase(column.getColumnName(), false)).append(";\n\n");
        }

        for (TableColumnDTO column : columns) {
            String tableName = column.getTableName();
            if (!dtos.containsKey(tableName)) {
                dtos.put(tableName, new StringBuilder());
            }
            String fieldType = mapDataType(column.getDataType());
            String fieldName = toCamelCase(column.getColumnName(), false);

            StringBuilder dto = dtos.get(tableName);
            // Add getter
            dto.append("    public ").append(fieldType).append(" get").append(toCamelCase(column.getColumnName())).append("() {\n");
            dto.append("        return ").append(fieldName).append(";\n");
            dto.append("    }\n\n");

            // Add setter
            dto.append("    public void set").append(toCamelCase(column.getColumnName())).append("(").append(fieldType).append(" ").append(fieldName).append(") {\n");
            dto.append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
            dto.append("    }\n\n");
        }

        for (Map.Entry<String, StringBuilder> tableName : dtos.entrySet()) {
            tableName.getValue().append("}\n");

            try (FileWriter writer = new FileWriter(dtoPath + "/" + toCamelCase(tableName.getKey()) + "DTO.java")) {
                writer.write(tableName.getValue().toString());
            }
        }
    }

}
