package com.forgetmenot;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.forgetmenot.database.ConnectionManager;

/**
 * Servlet implementation class GetElencoTipiPiante
 */
@WebServlet("/GetElencoTipiPiante")
public class GetElencoTipiPiante extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetElencoTipiPiante() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = ConnectionManager.getConnection();
			stmt = conn.createStatement();
			String query = "SELECT nome, immagine "
					+ "FROM pianta "
					+ "ORDER BY nome";
			ResultSet rs = stmt.executeQuery(query);
			JSONArray result = new JSONArray();
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("nome", rs.getString("nome"));
				obj.put("immagine", rs.getString("immagine"));
				result.put(obj);
			}
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(result.toString());
		}
		catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		catch (JSONException e2) {
			e2.printStackTrace();
		}
		finally {
			if (conn != null) {
				try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
