package edu.umdearborn.astronomyapp.config.jpa;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL94Dialect;
import org.hibernate.type.descriptor.sql.LongVarcharTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

/**
 * Fix for error:<br />
 * <code>org.postgresql.util.PSQLException: Bad value for type long : x</code>
 * 
 * @author <a href=https://stackoverflow.com/users/2932614/torsten-krah>Torsten Krah</a>
 * @see <a href=https://stackoverflow.com/a/34286801>Correct JPA Annotation for PostgreSQL's text
 *      type without Hibernate Annotations</a>
 *
 */
public class PostgreSql94RemapClobDialect extends PostgreSQL94Dialect {

  @Override
  public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
    if (Types.CLOB == sqlTypeDescriptor.getSqlType()) {
      return LongVarcharTypeDescriptor.INSTANCE;
    }
    return super.remapSqlTypeDescriptor(sqlTypeDescriptor);
  }
}
