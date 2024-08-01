package io.mhmtonrn.spc.service;

import io.mhmtonrn.spc.model.database.res.TableColumnDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HibernateEntityGenerator {

    public static void generateEntities(Path path, List<TableColumnDTO> columns) throws IOException {


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
                entity.append("import jakarta.persistence.*;\n\n");
                entity.append("@Entity\n");
                entity.append("@Table(name = \"").append(tableName).append("\")\n");
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

    private static String mapDataType(String dataType) {
        return switch (dataType.toLowerCase()) {
            case "varchar", "char", "text" -> "String";
            case "int", "integer" -> "Integer";
            case "bigint" -> "Long";
            case "decimal", "numeric" -> "java.math.BigDecimal";
            case "date" -> "java.time.LocalDate";
            case "timestamp without time zone" -> "java.time.LocalDateTime";
            // Add other mappings as needed
            default -> "String";
        };
    }

    private static String toCamelCase(String s) {
        return toCamelCase(s, true);
    }

    private static String toCamelCase(String s, boolean capitalizeFirstLetter) {
        StringBuilder result = new StringBuilder();
        boolean nextUpperCase = capitalizeFirstLetter;
        for (char c : s.toCharArray()) {
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    result.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }
}
