package com.forgetmenot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        Connection con=null;
        PreparedStatement stmt=null;
        try{
        	JSONObject input=parseJSON(request);
        	
    		con = ConnectionManager.getConnection();
    		stmt=con.prepareStatement("INSERT into utente(email) VALUES (?)");
    		stmt.setString(1, input.getString("email"));
    		stmt.executeUpdate();
    		
    		stmt.close();
    		con.close();
        }
        catch(ClassNotFoundException e){
        }
        catch(JSONException e){
        	
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
			}//end if
        }
        finally {
			if (con != null)
				try {
					stmt.close();
					con.close();
				} catch (SQLException ex2) {
					System.err.println(ex2.getMessage());
				}
		}
	}
	
	//restituisce il JSON presente nel corpo della POST
		private JSONObject parseJSON(HttpServletRequest request) throws IOException, JSONException {
			StringBuilder sb = new StringBuilder();
		    BufferedReader reader = request.getReader();
		    try {
		        String line;
		        while ((line = reader.readLine()) != null) {
		            sb.append(line).append('\n');
		        }
		    } finally {
		        reader.close();
		    }
		    return new JSONObject(sb.toString());
		}

}
