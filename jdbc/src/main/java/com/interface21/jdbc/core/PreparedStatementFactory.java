package com.interface21.jdbc.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementFactory {

    PreparedStatement create(final Connection connection, final String sql) throws SQLException;
}
