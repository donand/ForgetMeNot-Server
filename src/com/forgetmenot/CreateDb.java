package com.forgetmenot;

import java.sql.*;

public class CreateDb {
	
	public static void main(String[]args) throws ClassNotFoundException, SQLException{
		
		/*Connection con = ConnectionManager.getConnection();
		Statement stmt=con.createStatement();
		
		/*acqua, concimazione, potatura indicano OGNI QUANTO la pianta ha bisogno 
		  di acqua, concimazione, potatura. Foto è un'immagine in stringa, fioritura è il mese
		  di fioritura, terreno è il tipo di terreno. Luce indica il livello di luminosità
		  di cui ha bisogno (intero da 1 a 10)*/
		/*String createTable="CREATE TABLE PIANTE"+
				"(NOME VARCHAR(32), LUCE INTEGER, ACQUA INTEGER, CONCIMAZIONE INTEGER, "+
				"FOTO VARCHAR(32), DESCRIZIONE VARCHAR(32), "+
				"FIORITURA VARCHAR(32), POTATURA INTEGER, TERRENO VARCHAR(32))";
		stmt.executeUpdate(createTable);
		stmt.executeUpdate("INSERT INTO PIANTE VALUES "+
				"('Girasole', 7, 1, 7, 'girasole', 'pianta bella', 'giugno', 30, 'erba')");
		stmt.executeUpdate("INSERT INTO PIANTE VALUES "+
				"('Rosa', 6, 3, 6, 'rosa', 'pianta bella', 'marzo', 30, 'erba')");
		stmt.executeUpdate("INSERT INTO PIANTE VALUES "+
				"('Cactus', 10, 10, 10, 'cactus', 'pianta bella', 'ottobre', 40, 'sabbia')");
		stmt.executeUpdate("INSERT INTO PIANTE VALUES "+
				"('Tulipano', 4, 1, 9, 'tulipano', 'pianta bella', 'maggio', 20, 'erba')");
		stmt.executeUpdate("INSERT INTO PIANTE VALUES "+
				"('Margherita', 8, 3, 7, 'margherita', 'pianta bella', 'maggio', 30, 'erba')");
		stmt.executeUpdate("INSERT INTO PIANTE VALUES "+
				"('Viola', 5, 3, 9, 'viola', 'pianta bella', 'settembre', 30, 'erba')");
		stmt.executeUpdate("INSERT INTO PIANTE VALUES "+
				"('Basilico', 6, 2, 20, 'basilico', 'pianta bella', 'settembre', 50, 'terra')");
		stmt.executeUpdate("INSERT INTO PIANTE VALUES "+
				"('Rosmarino', 4, 3, 20, 'rosmarino', 'pianta bella', 'gennaio', 50, 'terra')");
		stmt.executeUpdate("INSERT INTO PIANTE VALUES "+
				"('Menta', 8, 2, 20, 'menta', 'pianta bella', 'settembre', 30, 'erba')");*/
		
	}
}
