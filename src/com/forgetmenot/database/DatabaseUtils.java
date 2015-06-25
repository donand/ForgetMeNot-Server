package com.forgetmenot.database;

import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
	
	//elimino momentaneamente le foreign key nella tabella "possesso"
	public static void dropFKConstraintsFromPossesso(Statement stmt) throws SQLException {
		stmt.executeUpdate("ALTER TABLE possesso DROP CONSTRAINT fk_possesso_possessore");
		stmt.executeUpdate("ALTER TABLE possesso DROP CONSTRAINT fk_possesso_posseduta");
	}
	
	//rimette i vicnoli di foreign key nella tabella "possesso"
	public static void addFKConstraintsToPossesso(Statement stmt) throws SQLException {
		stmt.executeUpdate("ALTER TABLE possesso ADD CONSTRAINT fk_possesso_posseduta FOREIGN KEY (id) "
				+ "REFERENCES posseduta(idPossesso) ON UPDATE CASCADE ON DELETE RESTRICT");
		stmt.executeUpdate("ALTER TABLE possesso ADD CONSTRAINT fk_possesso_possessore FOREIGN KEY (id) "
				+ "REFERENCES possessore(idPossesso) ON UPDATE CASCADE ON DELETE RESTRICT");
	}
}
