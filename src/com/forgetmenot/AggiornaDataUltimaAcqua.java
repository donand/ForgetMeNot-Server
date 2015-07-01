package com.forgetmenot;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.forgetmenot.database.ConnectionManager;

/**
 * Servlet implementation class AggiornaDataUltimaAcqua
 */
@WebServlet("/AggiornaDataUltimaAcqua")
public class AggiornaDataUltimaAcqua extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiornaDataUltimaAcqua() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement updatePossesso = null;
		
		try {
			JSONObject input = Utils.parseJSONObject(request);
			conn = ConnectionManager.getConnection();
			updatePossesso = conn.prepareStatement("UPDATE possesso "
					+ "SET dataultimaacqua = ? "
					+ "WHERE id = ?");
			updatePossesso.setLong(1, new java.util.Date().getTime());
			updatePossesso.setInt(2, input.getInt("idPossesso"));
			updatePossesso.executeUpdate();
			
			response.getWriter().write(new JSONObject().toString());
			
			updatePossesso.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} 
		catch (JSONException e1) {
			e1.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} 
		catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
	}

}
