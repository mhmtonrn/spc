package io.mhmtonrn.spc.controller;

import io.mhmtonrn.spc.model.database.req.DataBaseRequestDTO;
import io.mhmtonrn.spc.model.database.req.TableDetailRequestDTO;
import io.mhmtonrn.spc.model.database.res.DataBaseTableDTO;
import io.mhmtonrn.spc.model.database.res.TableColumnDTO;
import io.mhmtonrn.spc.service.DatabaseService;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/database")
@RequiredArgsConstructor
public class DataBaseApi {

  private final DatabaseService databaseService;

  @PostMapping("/tables")
  public List<DataBaseTableDTO> getTables(@RequestBody DataBaseRequestDTO dataBaseRequestDto) throws SQLException {
    return databaseService.getTables(dataBaseRequestDto);
  }


  @PostMapping("/table-detail")
  public List<TableColumnDTO> getTablesDetail(@RequestBody TableDetailRequestDTO tableDetailRequestDto) {
    return databaseService.getTablesDetail(tableDetailRequestDto);
  }


}
