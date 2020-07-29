package core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * callback interface
 */
public interface PreparedStatementSetter {

	void setValues(final PreparedStatement pstmt) throws SQLException;
}
