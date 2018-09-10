package com.project.pethost.mapper;

import com.project.pethost.dbo.UserDbo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<UserDbo> {

    public static final String BASE_SQL = "Select u.id, u.email, u.password From user u ";

    @Override
    public UserDbo mapRow(ResultSet rs, int rowNum) throws SQLException {

        final Long userId = rs.getLong("id");
        final String userName = rs.getString("name");
        final String encrytedPassword = rs.getString("password");

        return new UserDbo(userId, userName, encrytedPassword);
    }

}
