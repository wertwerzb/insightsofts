package Cjson;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.File; 
import java.sql.SQLException;

/* import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;


import Com.db.DruidConnect;

import java.sql.Connection;
import java.sql.Statement;

*/
public class Listjson
{

	public Listjson ()
	{} 
	
	public static String getscombox(String sqlstr)
	{

		JSONArray array = new JSONArray();      //定义JSON数组
		try
		{

  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sqlstr);
			while (rs.next())
			{
				JSONObject obj = new JSONObject();       
				obj.put("id", rs.getString(1));
				obj.put("text", rs.getString(2));

				array.add(obj);
			}
			rs.close();
			dbc.close();
			 conn.close();

		}
	 catch (SQLException sqle)
		{
			System.out.println( sqle.toString());
			
		System.out.print( "Listjson_getscombox:"+ sqlstr );
		}
		catch (Exception sqle)
		{
			System.out.print(sqle);
		}
		return array.toString();
	}

	public static String  getscombox(String sqlstr, String selectitem)
	{

		JSONArray array = new JSONArray();      //定义JSON数组
		try
		{

  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sqlstr);
			while (rs.next())
			{
				JSONObject obj = new JSONObject();       
				obj.put("id", rs.getString(1));
				obj.put("text", rs.getString(2));
				if	(rs.getString(1).equals(selectitem))
					obj.put("selected", true);
				array.add(obj);
			}
			rs.close();
			dbc.close();
			 conn.close();

		}
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString());
			
		System.out.print( "Listjson_getscombox2:"+ sqlstr );
		}
	
		catch (Exception sqle)
		{
			System.out.print(sqle);
		}
		return array.toString();
	}

	public static String getmcombox(String sqlstr,String initstr,String ablestr,int roleleng)
	{

		JSONArray array = new JSONArray();      //定义JSON数组
		try
		{


  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sqlstr);

			while (rs.next())
			{
			 if(( initstr.indexOf( rs.getString(1) )>-1 ) || ( roleleng< 3) )
			 { 	JSONObject obj = new JSONObject();       
				obj.put("id", rs.getString(1));
				obj.put("text", rs.getString(2));

if( ablestr.indexOf( rs.getString(1) )>-1 )
	obj.put("selected","true");
		else		
				obj.put("selected","false"); 				
				array.add(obj);
				 }
			}
			rs.close();
			dbc.close();

		}
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString());
			
		System.out.print( "Listjson_getmcombox:"+ sqlstr );
		}
	
		catch (Exception sqle)
		{
			System.out.print(sqle);
		}
		return array.toString();
	}


public static String SS(String gg,String filter){
		JSONArray array = new JSONArray();   
	 File file = new File(gg);
File sec[]=file.listFiles(); for(int i=0;i<sec.length;i++){ 
if("Dir".equals(filter)) 
 { if(sec[i].isDirectory() )
   {

   	JSONObject obj = new JSONObject();  
				obj.put("id", sec[i].getName() );
			 	obj.put("text", sec[i].getName() );
			 	 				
				array.add(obj);
				 }

 
 }
 else
 { 
 if(sec[i].isFile() )
   {
 	String ff= sec[i].getName().substring(sec[i].getName().indexOf(".")+1,sec[i].getName().length());
 	 	String dd= sec[i].getName().substring(0,sec[i].getName().indexOf("."));
 	 	
if (filter.equals(ff)  )
{
   	JSONObject obj = new JSONObject();  
				obj.put("id", sec[i].getName() );
			 	obj.put("text",dd);
			 	 				
				array.add(obj);
				 }
				 }
 } 
 }
		return array.toString();
 }


	 
}