package core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * callback interface
 */
public interface RowMapper {

	Object mapRow(final ResultSet rs) throws SQLException;
}
