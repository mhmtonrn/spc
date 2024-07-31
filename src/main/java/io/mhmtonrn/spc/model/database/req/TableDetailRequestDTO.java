package io.mhmtonrn.spc.model.database.req;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TableDetailRequestDTO {

  private DataBaseRequestDTO database;
  private String tableName;
  private String schemaName;


}
