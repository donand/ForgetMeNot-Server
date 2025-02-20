package com.forgetmenot;
 
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 

import java.util.Date;

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
        @SuppressWarnings("unchecked")
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        	// TODO Auto-generated method stub
               
            //prende come parametro l'id del Possesso(entit� che collega un user alla sua pianta)
            int id=Integer.parseInt(request.getParameter("id"));
            try{
                       
            Connection con = ConnectionManager.getConnection();
            String query="SELECT possesso.indirizzo as indirizzo, possesso.GPSLat as gpslat, possesso.GPSLong as gpslong, "
                            + "pianta.immagine as immagine, "
                            + "pianta.descrizioneConcimazione as descrizioneConcimazione, pianta.descrizioneAcqua as descrizioneAcqua, "
                            + "pianta.luce as luce, possesso.dataUltimaAcqua as dataUltimaAcqua, possesso.dataUltimoFertilizzante as dataUltimoFertilizzante, "
                            + "pianta.acqua, pianta.intervalloConcimazione, "
                            + "possesso.notificheAcqua as notificheAcqua, possesso.notificheFertilizzante as notificheFertilizzante "
                            + "FROM possesso, posseduta, pianta "
                            + "WHERE posseduta.idpossesso=? AND posseduta.idpossesso=possesso.id AND posseduta.idpianta=pianta.id";
               
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs= stmt.executeQuery();
            //StringWriter out=new StringWriter();
            rs.next();
               
            JSONObject obj=new JSONObject();
            obj.put("dataUltimaAcqua", new Date(rs.getLong("dataUltimaAcqua")).toString());
            obj.put("dataUltimoFertilizzante", new Date(rs.getLong("dataUltimoFertilizzante")).toString());
            obj.put("acqua", rs.getInt("acqua"));
            obj.put("intervalloConcimazione", rs.getInt("intervalloConcimazione"));
            obj.put("luce",rs.getInt("luce"));
            obj.put("descrizioneAcqua",rs.getString("descrizioneAcqua"));
            obj.put("descrizioneConcimazione",rs.getString("descrizioneConcimazione"));
            obj.put("gpslat", rs.getFloat("GPSLat"));
            obj.put("gpslong", rs.getFloat("GPSLong"));
            obj.put("indirizzo", rs.getString("indirizzo"));
            obj.put("livelloAcqua", Utils.calcolaLivello(rs.getLong("dataUltimaAcqua"), rs.getInt("acqua")));
            obj.put("livelloConcimazione", Utils.calcolaLivello(rs.getLong("dataUltimoFertilizzante"), rs.getInt("intervalloConcimazione")));
            obj.put("notificheAcqua", rs.getBoolean("notificheAcqua"));
            obj.put("notificheFertilizzante",  rs.getBoolean("notificheFertilizzante"));
            
            
            //String jsonText = out.toString();
            String jsonText = obj.toString();
            response.setContentType("application/json; charset=UTF-8");
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
        	e.printStackTrace();
        	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        }
 
        /**
         * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
         */
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                // TODO Auto-generated method stub
        }
 
}