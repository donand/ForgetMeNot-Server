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
 * Servlet implementation class SetNotificheAcqua
 */
@WebServlet("/SetNotificheAcqua")
public class SetNotificheAcqua extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetNotificheAcqua() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement setNotifiche = null;
		
		try {
			JSONObject input = Utils.parseJSONObject(request);
			conn = ConnectionManager.getConnection();
			setNotifiche = conn.prepareStatement("UPDATE possesso "
					+ "SET notificheAcqua = ? "
					+ "WHERE id = ?");
			
			setNotifiche.setBoolean(1, input.getBoolean("notificheAcqua"));
			setNotifiche.setInt(2, input.getInt("idPossesso"));
			setNotifiche.executeUpdate();
			
			response.getWriter().write(new JSONObject().toString());
			
			setNotifiche.close();
			conn.close();
		} 
		catch (JSONException e1) {
			e1.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		catch (SQLException e2) {
			e2.printStackTrace();
			
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		catch (ClassNotFoundException e3) {
			e3.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
