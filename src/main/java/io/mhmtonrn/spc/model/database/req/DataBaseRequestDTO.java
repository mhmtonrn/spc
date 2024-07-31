package io.mhmtonrn.spc.model.database.req;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataBaseRequestDTO {

  private String databaseUrl;
  private String databaseUser;
  private String databasePassword;
  private String driverClassName;

}
