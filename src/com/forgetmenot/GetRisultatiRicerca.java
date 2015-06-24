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

/**
 * Servlet implementation class GetRisultatiRicerca
 */
@WebServlet("/GetRisultatiRicerca")
public class GetRisultatiRicerca extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRisultatiRicerca() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nomePianta=request.getParameter("nome");
		try{
        	Connection con = ConnectionManager.getConnection();
    		PreparedStatement stmt=con.prepareStatement("SELECT * FROM pianta "+"WHERE nome=?");
    		stmt.setString(1, nomePianta);
    		ResultSet rs= stmt.executeQuery();
    		//StringWriter out=new StringWriter();
    		rs.next();
    		
    		JSONObject obj=new JSONObject();
    		obj.put("id", rs.getInt("id"));
    		obj.put("nome",rs.getString("nome"));
			obj.put("immagine",rs.getString("immagine"));
			
    		//String jsonText = out.toString();
    		String jsonText = obj.toString();
            System.out.print(jsonText);
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
