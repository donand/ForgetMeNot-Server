package com.forgetmenot;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.forgetmenot.database.ConnectionManager;

/**
 * Servlet implementation class GetDettagliPiantaUser
 */
@WebServlet("/GetDettagliPiantaUser")
public class GetDettagliPiantaUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDettagliPiantaUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//prende come parametro l'id del Possesso(entità che collega un user alla sua pianta)
		int id=Integer.parseInt(request.getParameter("id"));
		try{
			
        	Connection con = ConnectionManager.getConnection();
        	String query="SELECT possesso.indirizzo as indirizzo, possesso.GPSLat as gpslat, possesso.GPSLong as gpslong, "
        			+ "pianta.immagine as immagine, "
        			+ "pianta.descrizioneConcimazione as descrizioneConcimazione, pianta.descrizioneAcqua as descrizioneAcqua, "
        			+ "pianta.luce as luce, possesso.dataUltimaAcqua, pianta.acqua, pianta.intervalloConcimazione "
        			+ "FROM possesso, posseduta, pianta "
        			+ "WHERE posseduta.idpossesso=? AND posseduta.idpossesso=possesso.id AND posseduta.idpianta=pianta.id";
        	
    		PreparedStatement stmt=con.prepareStatement(query);
    		stmt.setInt(1, id);
    		ResultSet rs= stmt.executeQuery();
    		//StringWriter out=new StringWriter();
    		rs.next();
    		
    		JSONObject obj=new JSONObject();
    		
			obj.put("dataUltimaAcqua",rs.getString("dataUltimaAcqua"));
			obj.put("acqua", rs.getInt("acqua"));
			obj.put("intervalloConcimazione", rs.getInt("intervalloConcimazione"));
			obj.put("luce",rs.getInt("luce"));
			obj.put("descrizioneAcqua",rs.getString("descrizioneAcqua"));
			obj.put("descrizioneConcimazione",rs.getString("descrizioneConcimazione"));
			obj.put("gpslat", rs.getFloat("GPSLat"));
			obj.put("gpslong", rs.getFloat("GPSLong"));
			obj.put("indirizzo", rs.getString("indirizzo"));
			
    		//String jsonText = out.toString();
    		String jsonText = obj.toString();
            OutputStreamWriter outR = new OutputStreamWriter(response.getOutputStream());
            outR.write(jsonText);
            outR.flush();
            outR.close();
            //out.close();
            rs.close();
            con.close();
        }
        catch(ClassNotFoundException e){
        }
        catch(SQLException e){
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}