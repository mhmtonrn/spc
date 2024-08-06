package io.mhmtonrn.spc.service.generators;

import io.mhmtonrn.spc.model.database.res.TableColumnDTO;
import io.mhmtonrn.spc.model.springcli.CreateAppDTO;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@AllArgsConstructor
public class PojoBuilder {
    private final CreateAppDTO createAppDTO;
    private final Path path;
    private final List<TableColumnDTO> columns;


    public PojoBuilder entity() throws IOException {
        HibernateEntityGenerator.generateClass(createAppDTO, path, columns);
        return this;
    }

    ;

    public PojoBuilder dto() throws IOException {
        JavaDTOGenerator.generateClass(createAppDTO, path, columns);
        return this;
    }

    ;

    public PojoBuilder mapper() throws IOException {
        MapperGenerator.generateClass(createAppDTO, path, columns);
        return this;
    }

    ;
}
