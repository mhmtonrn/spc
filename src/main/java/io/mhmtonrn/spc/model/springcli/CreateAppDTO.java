package io.mhmtonrn.spc.model.springcli;

import io.mhmtonrn.spc.model.database.req.DataBaseRequestDTO;
import io.mhmtonrn.spc.model.database.res.DataBaseTableDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateAppDTO {

  private String appName;
  private String projectGroupId = "com.example";
  private String projectArtifactId = appName;
  private String projectDescription = String.format("%s project description", appName);
  private String projectPackageName = projectGroupId;
  private String projectVersion = "1.0.0";
  private List<String> dependencies;
  private List<DataBaseTableDTO> tables;
  private DataBaseRequestDTO database;

}
