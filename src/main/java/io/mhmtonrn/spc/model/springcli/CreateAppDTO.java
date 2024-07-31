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
  private List<String> dependencies;
  private List<DataBaseTableDTO> dataBaseTable;
  private DataBaseRequestDTO dataBaseRequestDTO;
}
