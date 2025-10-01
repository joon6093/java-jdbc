package com.techcourse.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.jdbc.core.JdbcTemplate;
import com.techcourse.config.DataSourceConfig;
import com.techcourse.domain.User;
import com.techcourse.support.jdbc.init.DatabasePopulatorUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDaoTest {

    private UserDao userDao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        final var dataSource = DataSourceConfig.getInstance();
        DatabasePopulatorUtils.execute(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
        userDao = new UserDao(jdbcTemplate);
    }

    @AfterEach
    void teardown() {
        final var sql = "TRUNCATE TABLE users";
        jdbcTemplate.update(sql);
    }

    @Test
    void findAll() {
        final var user = new User("gugu", "password", "hkkang@woowahan.com");
        final var id = userDao.insert(user);

        final var users = userDao.findAll();

        assertThat(users).hasSize(1);
        assertThat(users.getFirst().getId()).isEqualTo(id);
    }

    @Test
    void findById() {
        final var user = new User("gugu", "password", "hkkang@woowahan.com");
        final var id = userDao.insert(user);

        final var found = userDao.findById(id);

        assertThat(found.getAccount()).isEqualTo("gugu");
    }

    @Test
    void findByAccount() {
        final var account = "gugu";
        final var user = new User(account, "password", "hkkang@woowahan.com");
        userDao.insert(user);

        final var found = userDao.findByAccount(account);

        assertThat(found.getAccount()).isEqualTo(account);
    }

    @Test
    void insert() {
        final var account = "insert-gugu";
        final var user = new User(account, "password", "hkkang@woowahan.com");

        final var id = userDao.insert(user);

        final var actual = userDao.findById(id);
        assertThat(actual.getAccount()).isEqualTo(account);
    }

    @Test
    void update() {
        final var user = new User("gugu", "password", "hkkang@woowahan.com");
        final var id = userDao.insert(user);

        final var found = userDao.findById(id);
        final var newPassword = "password99";
        found.changePassword(newPassword);

        userDao.update(found);

        final var actual = userDao.findById(id);
        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }
}
