package io.mhmtonrn.spc.service;

import io.mhmtonrn.spc.config.DataBaseConfiguration;
import io.mhmtonrn.spc.dao.ICustomQueryBuilder;
import io.mhmtonrn.spc.dao.queries.DataBaseTableDao;
import io.mhmtonrn.spc.model.database.req.DataBaseRequestDTO;
import io.mhmtonrn.spc.model.database.req.TableDetailRequestDTO;
import io.mhmtonrn.spc.model.database.res.DataBaseTableDTO;
import io.mhmtonrn.spc.model.database.res.TableColumnDTO;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseService {

  private static final Logger log = LoggerFactory.getLogger(DatabaseService.class);
  private final DataBaseConfiguration dataBaseConfiguration;
  private final DataBaseTableDao dataBaseTableDao;

  public List<DataBaseTableDTO> getTables(DataBaseRequestDTO dataBaseRequestDto) throws SQLException {
    NamedParameterJdbcTemplate dataSource = dataBaseConfiguration.getJdbcTemplate(dataBaseRequestDto);
    ICustomQueryBuilder<DataBaseTableDTO> tables = dataBaseTableDao.getTables();
    return dataSource.query(tables.getSql(), tables.getParameters(), tables.getRowMapper());
  }

  public List<TableColumnDTO> getTablesDetail(TableDetailRequestDTO tableDetailRequestDto) {
    NamedParameterJdbcTemplate dataSource = dataBaseConfiguration.getJdbcTemplate(tableDetailRequestDto.getDatabase());
    ICustomQueryBuilder<TableColumnDTO> tableDetails = dataBaseTableDao.getTableDetails(tableDetailRequestDto.getTableName(),
        tableDetailRequestDto.getSchemaName());
    return dataSource.query(tableDetails.getSql(), tableDetails.getParameters(), tableDetails.getRowMapper());
  }
}
