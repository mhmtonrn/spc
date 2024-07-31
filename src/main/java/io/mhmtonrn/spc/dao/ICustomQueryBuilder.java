package io.mhmtonrn.spc.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public interface ICustomQueryBuilder<T> {

  String getSql();

  MapSqlParameterSource getParameters();

  RowMapper<T> getRowMapper();
}
