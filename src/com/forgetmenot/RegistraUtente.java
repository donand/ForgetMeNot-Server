package com.forgetmenot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.forgetmenot.database.ConnectionManager;

/**
 * Servlet implementation class RegistraUtente
 */
@WebServlet("/RegistraUtente")
public class RegistraUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistraUtente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement stmt = null, stmt1 = null;long id = -1;
        try{
        	JSONObject input = Utils.parseJSONObject(request);
        	
    		con = ConnectionManager.getConnection();
    		stmt1 = con.prepareStatement("SELECT id "
    				+ "FROM utente "
    				+ "WHERE email = ?");
    		stmt1.setString(1, input.getString("email"));
    		ResultSet idList = stmt1.executeQuery();
    		if (idList.next())
    			id = idList.getLong("id");
    		else {
    			stmt = con.prepareStatement("INSERT into utente(email) VALUES (?)", 
    					Statement.RETURN_GENERATED_KEYS);
    			stmt.setString(1, input.getString("email"));
    			stmt.executeUpdate();
    		
    			ResultSet generatedKeys = stmt.getGeneratedKeys();
    			if (generatedKeys.next()) {
    				id = generatedKeys.getLong(1);
    				System.out.println("id: " + id);
    			}
    			else {
    				throw new SQLException("Creating user failed, no ID obtained.");
    			}
    		}
    		
			JSONObject res = new JSONObject();
			res.put("id", id);
			response.getWriter().write(res.toString());
        }
        catch(ClassNotFoundException e){
        }
        catch(JSONException e){
        	response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        catch(SQLException e){
        	System.err.println("Error: "+e.getMessage());
			if (con != null) {
				try {
					System.err.println("Transaction is being "+"rolled back");
					con.rollback();
				} catch (SQLException excep) {
					System.err.println("Error: "+excep.getMessage());
				}
			}
        }
        finally {
			if (con != null)
				try {
					if (stmt != null)
						stmt.close();
					stmt1.close();
					con.close();
				} catch (SQLException ex2) {
					System.err.println(ex2.getMessage());
				}
		}
	}

}
