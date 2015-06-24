package com.forgetmenot;

import java.io.*;


import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class GetPianteFromLuce
 */
@WebServlet("/GetPianteFromLuce")
public class GetPianteFromLuce extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPianteFromLuce() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int luce=Integer.parseInt(request.getParameter("luce"));
		try{
    		Connection con = ConnectionManager.getConnection();
    		PreparedStatement stmt=con.prepareStatement("SELECT * FROM pianta "+"WHERE luce=?");
    		stmt.setInt(1, luce);
    		ResultSet rs= stmt.executeQuery();
    		//StringWriter out=new StringWriter();
    		
    		JSONArray a=new JSONArray();
    		while(rs.next()){
    			JSONObject obj=new JSONObject();
    			obj.put("nome",rs.getString("nome"));
    			obj.put("immagine", rs.getString("immagine"));
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*int length = request.getContentLength();
        byte[] input = new byte[length];
        
        ServletInputStream sin = request.getInputStream();
        int c, count = 0 ;
        while ((c = sin.read(input, count, input.length-count)) != -1) {
            count +=c;
        }
        sin.close();
        String receivedString = new String(input);
        Integer luce=Integer.parseInt(receivedString);
        try{
    		Connection con = ConnectionManager.getConnection();
    		PreparedStatement stmt=con.prepareStatement("SELECT * FROM piante "+"WHERE luce=?");
    		stmt.setInt(1, luce);
    		ResultSet rs= stmt.executeQuery();
    		//StringWriter out=new StringWriter();
    		
    		JSONArray a=new JSONArray();
    		while(rs.next()){
    			JSONObject obj=new JSONObject();
    			obj.put("nome",rs.getString("nome"));
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
        }*/
	}
}
