package com.forgetmenot;

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
import org.json.simple.JSONObject;

/**
 * Servlet implementation class GetDatiGeneraliPianta
 */
@WebServlet("/GetDatiGeneraliPianta")
public class GetDatiGeneraliPianta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDatiGeneraliPianta() {
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
		int length = request.getContentLength();
        byte[] input = new byte[length];
        
        ServletInputStream sin = request.getInputStream();
        int c, count = 0 ;
        while ((c = sin.read(input, count, input.length-count)) != -1) {
            count +=c;
        }
        sin.close();
        
        String nomePianta = new String(input);
		
        try{
        	Connection con = ConnectionManager.getConnection();
    		PreparedStatement stmt=con.prepareStatement("SELECT * FROM piante "+"WHERE nome=?");
    		stmt.setString(1, nomePianta);
    		ResultSet rs= stmt.executeQuery();
    		//StringWriter out=new StringWriter();
    		
    		JSONArray a=new JSONArray();
    		while(rs.next()){
    			JSONObject obj=new JSONObject();
    			obj.put("nome",rs.getString("nome"));
    			obj.put("luce",rs.getInt("luce"));
    			obj.put("acqua",rs.getInt("acqua"));
    			obj.put("concimazione",rs.getInt("concimazione"));
    			obj.put("foto",rs.getString("foto"));
    			obj.put("descrizione",rs.getString("descrizione"));
    			obj.put("fioritura",rs.getString("fioritura"));
    			obj.put("potatura",rs.getInt("potatura"));
    			obj.put("terreno",rs.getString("terreno"));
    	        //obj.writeJSONString(out);
    	        a.add(obj);
            }
    		//String jsonText = out.toString();
    		String jsonText = a.toString();
            //System.out.print(jsonText);
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

}
