package com.forgetmenot;

import java.sql.*;

public class ConnectionManager {
	private ConnectionManager() {
    }
    ;
    
    private static Connection conn = null;
    private static final String MY_DRIVER = "org.postgresql.Driver";
    private static final String MY_URL = "jdbc:postgresql://localhost/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWD = "postgres";
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (conn == null) {
            Class.forName(MY_DRIVER);
            conn = DriverManager.getConnection(MY_URL, LOGIN, PASSWD);
        } else if (conn.isClosed()) {
            conn = DriverManager.getConnection(MY_URL, LOGIN, PASSWD);
        } else // In questo caso si e’ verificato un comportamento
        // non atteso. Inserisco comandi che corrispondono
        // ad una politica da seguire in caso di errore
        {
            conn.rollback(); //si e’ scelto di disfare tutto il lavoro eseguito dall’ultimo commit.
        }
        return conn;
    }
}
