package io.mhmtonrn.spc.service.generators;

import io.mhmtonrn.spc.model.database.res.TableColumnDTO;
import io.mhmtonrn.spc.model.springcli.CreateAppDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@NoArgsConstructor
@Slf4j
public class PropertyFileGenerator extends PojoGenerator {

    public static void generateClass(CreateAppDTO createAppDTO, Path path, List<TableColumnDTO> columns) throws IOException {
        log.info("{}", columns);
        StringBuilder propertyFile = new StringBuilder();

        propertyFile.append(String.format("spring.application.name=%s", createAppDTO.getAppName()));
        propertyFile.append("\n\nspring.datasource.hikari.auto-commit: false\n");
        propertyFile.append(String.format("spring.datasource.jdbc-url: %s%n", createAppDTO.getDatabase().getDatabaseUrl()));
        propertyFile.append(String.format("spring.datasource.password: %s%n", createAppDTO.getDatabase().getDatabasePassword()));
        propertyFile.append(String.format("spring.datasource.url: %s%n", createAppDTO.getDatabase().getDatabaseUrl()));
        propertyFile.append(String.format("spring.datasource.username: %s%n", createAppDTO.getDatabase().getDatabaseUser()));

        try (FileWriter writer = new FileWriter(path.toFile())) {
            writer.write(propertyFile.toString());
        }

    }
}
