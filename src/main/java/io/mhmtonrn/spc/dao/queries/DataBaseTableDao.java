package io.mhmtonrn.spc.dao.queries;

import io.mhmtonrn.spc.dao.ICustomQueryBuilder;
import io.mhmtonrn.spc.model.database.res.DataBaseTableDTO;
import io.mhmtonrn.spc.model.database.res.TableColumnDTO;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class DataBaseTableDao {


  public ICustomQueryBuilder<DataBaseTableDTO> getTables() {
    return new ICustomQueryBuilder<>() {
      @Override
      public String getSql() {
        return """
            select table_schema, table_name
            from
             information_schema.tables tbl
            where
             table_schema not like 'pg_%'
             and table_schema not like 'dba'
             and table_schema != 'information_schema'
            order by
             table_schema
            """;
      }

      @Override
      public MapSqlParameterSource getParameters() {
        return null;
      }

      @Override
      public RowMapper<DataBaseTableDTO> getRowMapper() {
        return (rs, rowNum) -> {
          DataBaseTableDTO table = new DataBaseTableDTO();
          String schema = rs.getString("table_schema");
          String tableName = rs.getString("table_name");
          table.setSchemaName(schema);
          table.setTableName(tableName);
          return table;
        };
      }
    };
  }
  
  public ICustomQueryBuilder<TableColumnDTO> getTableDetails(String tableName, String tableSchema) {
    return new ICustomQueryBuilder<>() {
      @Override
      public String getSql() {
        return """
            select
             *
            from
             information_schema.columns
            where
             table_schema = :schemaName
             and table_name = :tableName
            """;
      }

      @Override
      public MapSqlParameterSource getParameters() {
        return new MapSqlParameterSource().addValue("schemaName", tableSchema).addValue("tableName", tableName);
      }

      @Override
      public RowMapper<TableColumnDTO> getRowMapper() {
        return (rs, rowNum) -> {
          TableColumnDTO dto = new TableColumnDTO();
          dto.setTableCatalog(rs.getString("table_catalog"));
          dto.setTableSchema(rs.getString("table_schema"));
          dto.setTableName(rs.getString("table_name"));
          dto.setColumnName(rs.getString("column_name"));
          dto.setOrdinalPosition(rs.getInt("ordinal_position"));
          dto.setColumnDefault(rs.getString("column_default"));
          dto.setNullable("YES".equals(rs.getString("is_nullable")));
          dto.setDataType(rs.getString("data_type"));
          dto.setCharacterMaximumLength((Integer) rs.getObject("character_maximum_length"));
          dto.setCharacterOctetLength((Integer) rs.getObject("character_octet_length"));
          dto.setNumericPrecision((Integer) rs.getObject("numeric_precision"));
          dto.setNumericPrecisionRadix((Integer) rs.getObject("numeric_precision_radix"));
          dto.setNumericScale((Integer) rs.getObject("numeric_scale"));
          dto.setDatetimePrecision((Integer) rs.getObject("datetime_precision"));
          dto.setIntervalType(rs.getString("interval_type"));
          dto.setIntervalPrecision((Integer) rs.getObject("interval_precision"));
          dto.setCharacterSetCatalog(rs.getString("character_set_catalog"));
          dto.setCharacterSetSchema(rs.getString("character_set_schema"));
          dto.setCharacterSetName(rs.getString("character_set_name"));
          dto.setCollationCatalog(rs.getString("collation_catalog"));
          dto.setCollationSchema(rs.getString("collation_schema"));
          dto.setCollationName(rs.getString("collation_name"));
          dto.setDomainCatalog(rs.getString("domain_catalog"));
          dto.setDomainSchema(rs.getString("domain_schema"));
          dto.setDomainName(rs.getString("domain_name"));
          dto.setUdtCatalog(rs.getString("udt_catalog"));
          dto.setUdtSchema(rs.getString("udt_schema"));
          dto.setUdtName(rs.getString("udt_name"));
          dto.setScopeCatalog(rs.getString("scope_catalog"));
          dto.setScopeSchema(rs.getString("scope_schema"));
          dto.setScopeName(rs.getString("scope_name"));
          dto.setMaximumCardinality((Integer) rs.getObject("maximum_cardinality"));
          dto.setDtdIdentifier(rs.getString("dtd_identifier"));
          dto.setSelfReferencing("YES".equals(rs.getString("is_self_referencing")));
          dto.setIdentity("YES".equals(rs.getString("is_identity")));
          dto.setIdentityGeneration(rs.getString("identity_generation"));
          return dto;
        };
      }
    };
  }
}
