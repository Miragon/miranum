package io.miranum.platform.engine.adapter.out;

import lombok.val;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.jdbc.BinaryJdbcType;
import org.hibernate.type.descriptor.sql.internal.DdlTypeImpl;

import java.sql.Types;

/**
 * Custom dialect mapping SQL BLOB type to BYTEA postgresql type.
 */
public class NoToastPostgresSQLDialect extends PostgreSQLDialect {

    public NoToastPostgresSQLDialect(DialectResolutionInfo info) {
        super(info);
    }

    @Override
    protected void registerColumnTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.registerColumnTypes(typeContributions, serviceRegistry);
        val ddlTypeRegistry = typeContributions.getTypeConfiguration().getDdlTypeRegistry();
        ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.BLOB, "bytea", this));
    }

    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.contributeTypes(typeContributions, serviceRegistry);
        val jdbcTypeRegistry = typeContributions.getTypeConfiguration().getJdbcTypeRegistry();
        jdbcTypeRegistry.addDescriptor(Types.BLOB, BinaryJdbcType.INSTANCE);
    }

    @Override
    protected String columnType(int sqlTypeCode) {
        return switch (sqlTypeCode) {
            case SqlTypes.BLOB -> "bytea";
            default -> super.columnType(sqlTypeCode);
        };
    }

    @Override
    protected String castType(int sqlTypeCode) {
        return switch (sqlTypeCode) {
            case SqlTypes.BLOB -> "bytea";
            default -> super.castType(sqlTypeCode);
        };
    }
}
