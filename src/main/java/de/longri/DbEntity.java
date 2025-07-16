package de.longri;

import de.longri.privilege.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DbEntity {

        void initFromResultSet(ResultSet rs, JdbcTemplate jdbcTemplate, User executeUser) throws SQLException;

}
