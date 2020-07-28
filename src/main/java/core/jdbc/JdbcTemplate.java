package core.jdbc;

import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {

	public void update(final User user) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = createQuery();
			pstmt = con.prepareStatement(sql);
			setValues(user, pstmt);
			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

	public abstract String createQuery();
	public abstract void setValues(final User user, final PreparedStatement pstmt) throws SQLException;
}
