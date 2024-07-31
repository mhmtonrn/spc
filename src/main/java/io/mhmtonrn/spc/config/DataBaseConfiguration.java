package io.mhmtonrn.spc.config;

import io.mhmtonrn.spc.model.database.req.DataBaseRequestDTO;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariDataSource;

@Service
public class DataBaseConfiguration {

  public NamedParameterJdbcTemplate getJdbcTemplate(DataBaseRequestDTO dataBaseRequestDto) {
    return new NamedParameterJdbcTemplate(getDataSource(dataBaseRequestDto));
  }

  public DataSource getDataSource(DataBaseRequestDTO dataBaseRequestDto) {
    DataSourceBuilder<?> ds = DataSourceBuilder.create();
    ds.driverClassName(dataBaseRequestDto.getDriverClassName());
    ds.url(dataBaseRequestDto.getDatabaseUrl());
    ds.username(dataBaseRequestDto.getDatabaseUser());
    ds.password(dataBaseRequestDto.getDatabasePassword());
    ds.type(HikariDataSource.class);
    return ds.build();
  }


}
