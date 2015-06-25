package com.forgetmenot;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.forgetmenot.database.ConnectionManager;
import com.forgetmenot.database.DatabaseUtils;

/**
 * Servlet implementation class AggiuntaNuovaPianta
 */
@WebServlet("/AggiuntaNuovaPianta")
public class AggiuntaNuovaPianta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiuntaNuovaPianta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement insertPossessore = null;
		PreparedStatement insertPosseduta = null;
		PreparedStatement insertPossesso = null;
		Statement stmt = null;
		
		try {
			long idPossesso;
			JSONObject input = Utils.parseJSONObject(request);
			conn = ConnectionManager.getConnection();
			stmt = conn.createStatement();
			insertPossessore = conn.prepareStatement("INSERT INTO possessore VALUES (?, ?)");
			insertPosseduta = conn.prepareStatement("INSERT INTO posseduta VALUES (?, ?)");
			insertPossesso = conn.prepareStatement("INSERT INTO possesso(nomeassegnato, "
					+ "gpslat, gpslong, dataultimaacqua, dataultimofertilizzante, indirizzo) "
					+ "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			//Inizia la transazione
			conn.setAutoCommit(false);
			
			//Elimino le foreign key dalla tabella possesso
			DatabaseUtils.dropFKConstraintsFromPossesso(stmt);
			
			insertPossesso.setString(1, input.getString("nome"));
			insertPossesso.setDouble(2, input.getDouble("lat"));
			insertPossesso.setDouble(3, input.getDouble("lon"));
			insertPossesso.setDate(4, new Date(new java.util.Date().getTime()));
			insertPossesso.setDate(5, new Date(new java.util.Date().getTime()));
			if (input.isNull("indirizzo"))
				insertPossesso.setNull(6, java.sql.Types.VARCHAR);
			insertPossesso.executeUpdate();
			ResultSet generatedKeys = insertPossesso.getGeneratedKeys();
			if (generatedKeys.next()) {
                idPossesso = generatedKeys.getLong(1);
                System.out.println("id: " + idPossesso);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
			
			insertPossessore.setLong(1, idPossesso);
			insertPossessore.setLong(2, input.getInt("utenteID"));
			insertPossessore.executeUpdate();
			
			insertPosseduta.setLong(1, idPossesso);
			insertPosseduta.setLong(2, input.getInt("piantaID"));
			insertPosseduta.executeUpdate();
			
			//Inserisco nuovamente le foreign key nella tabella "possesso"
			DatabaseUtils.addFKConstraintsToPossesso(stmt);
			
			conn.commit();
			conn.setAutoCommit(true);
			
			stmt.close();
			insertPossesso.close();
			insertPossessore.close();
			insertPosseduta.close();
			conn.close();
		} catch (JSONException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		catch (SQLException e1) {
			e1.printStackTrace();
			if (conn != null) {
				try {
					System.err.println("Transaction is being "+"rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.err.println("Error: "+excep.getMessage());
				}
			}//end if
		}
		catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		finally {
			if (conn != null)
				try {
					stmt.close();
					insertPossesso.close();
					insertPossessore.close();
					insertPosseduta.close();
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException ex2) {
					System.err.println(ex2.getMessage());
				}
		}
	}
}
