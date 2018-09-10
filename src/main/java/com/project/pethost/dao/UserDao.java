package com.project.pethost.dao;

import com.project.pethost.dbo.UserDbo;
import com.project.pethost.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional
public class UserDao extends JdbcDaoSupport {

    @Autowired
    public UserDao(final DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public UserDbo findUserAccount(final String email) {
        // Select .. from user u where u.name = ?
        final String sql = UserMapper.BASE_SQL + " where u.email = ? ";

        final Object[] params = new Object[] { email };
        final UserMapper mapper = new UserMapper();
        try {
            final UserDbo userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        } catch (final EmptyResultDataAccessException e) {
            return null;
        }
    }

}
