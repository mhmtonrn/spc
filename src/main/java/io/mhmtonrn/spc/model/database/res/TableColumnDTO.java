package io.mhmtonrn.spc.model.database.res;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TableColumnDTO {

    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String columnName;
    private int ordinalPosition;
    private String columnDefault;
    private boolean nullable;
    private String dataType;
    private Integer characterMaximumLength;
    private Integer characterOctetLength;
    private Integer numericPrecision;
    private Integer numericPrecisionRadix;
    private Integer numericScale;
    private Integer datetimePrecision;
    private String intervalType;
    private Integer intervalPrecision;
    private String characterSetCatalog;
    private String characterSetSchema;
    private String characterSetName;
    private String collationCatalog;
    private String collationSchema;
    private String collationName;
    private String domainCatalog;
    private String domainSchema;
    private String domainName;
    private String udtCatalog;
    private String udtSchema;
    private String udtName;
    private String scopeCatalog;
    private String scopeSchema;
    private String scopeName;
    private Integer maximumCardinality;
    private String dtdIdentifier;
    private boolean selfReferencing;
    private boolean identity;
    private String identityGeneration;
}