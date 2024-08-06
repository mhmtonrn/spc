package io.mhmtonrn.spc.service.generators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class PomGenerator {

    public static void addDependency(String projectPath, List<String> postgresDependency) {
        Path entityPath = Paths.get(projectPath + "/pom.xml");


        try {
            List<String> lines = Files.readAllLines(entityPath);

            List<String> updatedLines = new ArrayList<>();
            boolean inserted = false;

            for (String line : lines) {
                updatedLines.add(line);
                if (line.contains("</dependency>") && !inserted) {
                    updatedLines.addAll(postgresDependency);
                    inserted = true;
                }
            }

            Files.write(entityPath, updatedLines);

            log.error("Yeni satırlar başarıyla eklendi.");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }
}
