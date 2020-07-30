package core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * callback interface
 */
@FunctionalInterface
public interface RowMapper<T> {

	T mapRow(final ResultSet rs) throws SQLException;
}
