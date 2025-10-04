package com.interface21.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecutor<R> {

    R apply(final PreparedStatement pstmt) throws SQLException;
}
