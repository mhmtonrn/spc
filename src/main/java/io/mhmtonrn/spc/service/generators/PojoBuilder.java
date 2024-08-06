package io.mhmtonrn.spc.service.generators;

import io.mhmtonrn.spc.model.database.res.TableColumnDTO;
import io.mhmtonrn.spc.model.springcli.CreateAppDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class PojoBuilder {
    private final CreateAppDTO createAppDTO;
    private final Path path;
    private final List<TableColumnDTO> columns;


    public PojoBuilder entity() throws IOException {
        HibernateEntityGenerator.generateClass(createAppDTO, path, columns);
        return this;
    }

    public PojoBuilder dto() throws IOException {
        JavaDTOGenerator.generateClass(createAppDTO, path, columns);
        return this;
    }

    public PojoBuilder mapper() throws IOException {
        MapperGenerator.generateClass(createAppDTO, path, columns);
        return this;
    }

    public PojoBuilder properties(Path getInnerResourcePath) throws IOException {
        PropertyFileGenerator.generateClass(createAppDTO, getInnerResourcePath, columns);
        return this;
    }

    public void build() {
        log.info("Build");
    }


}
