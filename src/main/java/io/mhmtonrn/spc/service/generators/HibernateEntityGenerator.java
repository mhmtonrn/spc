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


public class HibernateEntityGenerator extends PojoGenerator {

    public static void generateClass(CreateAppDTO createAppDTO, Path path, List<TableColumnDTO> columns) throws IOException {

        Path entityPath = Paths.get(path + "/data/entity");

        FileUtils.forceMkdir(entityPath.toFile());

        Map<String, StringBuilder> entities = new HashMap<>();
        for (TableColumnDTO column : columns) {
            String tableName = column.getTableName();
            if (!entities.containsKey(tableName)) {
                entities.put(tableName, new StringBuilder());
            }

            StringBuilder entity = entities.get(tableName);

            if (entity.isEmpty()) {
                entity.append("package ").append(createAppDTO.getProjectPackageName()).append(".").append(createAppDTO.getAppName()).append(".data.entity;\n\n");
                entity.append("import jakarta.persistence.*;\n\n");
                entity.append("@Entity\n");
                entity.append("@Table(name = \"").append(tableName).append("\"").append(", schema = \"").append(column.getTableSchema()).append("\")\n");
                entity.append("public class ").append(toCamelCase(tableName)).append(" {\n\n");
            }

            if (column.getColumnName().equals("id")) {
                entity.append("    @Id\n");
            }
            entity.append("    @Column(name = \"").append(column.getColumnName()).append("\")\n");
            entity.append("    private ").append(mapDataType(column.getDataType())).append(" ").append(toCamelCase(column.getColumnName(), false)).append(";\n\n");
        }

        for (TableColumnDTO column : columns) {
            String tableName = column.getTableName();
            if (!entities.containsKey(tableName)) {
                entities.put(tableName, new StringBuilder());
            }
            String fieldType = mapDataType(column.getDataType());
            String fieldName = toCamelCase(column.getColumnName(), false);

            StringBuilder entity = entities.get(tableName);
            // Add getter
            entity.append("    public ").append(fieldType).append(" get").append(toCamelCase(column.getColumnName())).append("() {\n");
            entity.append("        return ").append(fieldName).append(";\n");
            entity.append("    }\n\n");

            // Add setter
            entity.append("    public void set").append(toCamelCase(column.getColumnName())).append("(").append(fieldType).append(" ").append(fieldName).append(") {\n");
            entity.append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
            entity.append("    }\n\n");
        }

        for (Map.Entry<String, StringBuilder> tableName : entities.entrySet()) {
            tableName.getValue().append("}\n");

            try (FileWriter writer = new FileWriter(entityPath + "/" + toCamelCase(tableName.getKey()) + ".java")) {
                writer.write(tableName.getValue().toString());
            }
        }
    }
}
