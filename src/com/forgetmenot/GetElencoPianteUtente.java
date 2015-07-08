package com.forgetmenot;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.forgetmenot.database.ConnectionManager;

/**
 * Servlet implementation class GetElencoPianteUtente
 */
@WebServlet("/ElencoPianteUtente")
public class GetElencoPianteUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetElencoPianteUtente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// l'ID dell'utente viene passato come parametro alla GET
		int userID = -1;
		try {
			userID = Integer.parseInt(request.getParameter("utente"));
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		String queryElencoPiante = "SELECT p2.id as id, p2.nomeassegnato as nomeAssegnato, "
				+ "p4.nome as nomePianta, p2.dataultimaacqua as ultimaAcqua, "
				+ "p2.dataultimofertilizzante as ultimoFertilizzante, "
				+ "p4.acqua as intervalloAcqua, p4.intervalloconcimazione as intervalloConcimazione, "
				+ "p4.immagine as immagine "
				+ "FROM possessore as p1, possesso as p2, posseduta as p3, pianta as p4 "
				+ "WHERE p1.idutente = ? and p1.idpossesso = p2.id and "
				+ "p2.id = p3.idpossesso and p3.idpianta = p4.id";
		
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(queryElencoPiante);
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			JSONArray result = new JSONArray();
			JSONObject obj;
			
			while (rs.next()) {
				obj = new JSONObject();
				obj.put("ID", rs.getInt("id"));
				obj.put("nomeAssegnato", rs.getString("nomeAssegnato"));
				obj.put("nomePianta", rs.getString("nomePianta"));
				obj.put("immagine", rs.getString("immagine"));
				obj.put("livelloAcqua", Utils.calcolaLivello(rs.getLong("ultimaAcqua"), rs.getInt("intervalloAcqua")));
				obj.put("livelloConcimazione", Utils.calcolaLivello(rs.getLong("ultimoFertilizzante"), rs.getInt("intervalloConcimazione")));
				result.add(obj);
			}
			
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().write(result.toString());
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		catch (ClassNotFoundException e2) {
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
		}
	}
	
	

}
