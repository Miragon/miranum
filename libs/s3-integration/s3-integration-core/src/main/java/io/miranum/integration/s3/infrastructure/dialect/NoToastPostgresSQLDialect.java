package io.miranum.integration.s3.infrastructure.dialect;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import java.sql.Types;

/**
 * Custom dialect mapping SQL BLOB type to BYTEA postgresql type.
 */
public class NoToastPostgresSQLDialect extends PostgreSQL10Dialect {

    public NoToastPostgresSQLDialect() {
        registerColumnType(Types.BLOB, "BYTEA");
    }

    public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
        if (sqlTypeDescriptor.getSqlType() == Types.BLOB) {
            return BinaryTypeDescriptor.INSTANCE;
        } else {
            return super.remapSqlTypeDescriptor(sqlTypeDescriptor);
        }
    }
}
