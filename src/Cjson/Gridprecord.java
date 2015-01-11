package Cjson;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray; 

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
public class Gridprecord
{

	public Gridprecord()
	{} 
	
	 public static String getspagerecord(String indexfield , String sql, String pagetotal)
	{

		JSONObject whdata= new JSONObject();   
		whdata.put("total", pagetotal);

		JSONArray array = new JSONArray();      //定义JSON数组
		String[] idstr=("id," + indexfield).split(",");
		try
		{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sql);

			while (rs.next())
			{
				JSONObject obj = new JSONObject();       
				for (int i=0,j=1;i < idstr.length;i++,j++)
				{
					obj.put(idstr[i] , rs.getString(j));
					//	obj.put("id",idstr[i]);
					//	obj.put("name",rs.getString(j));

				}
				array.add(obj);

			}
			whdata.put("rows", array);
			dbc.close()	;
		}
		catch (Exception e)
		{
		System.out.print( sql );
			System.out.println(e.toString());
		}
		finally
		{
			//关闭连接
		}
		return whdata.toString();

	}
public static String getsrecord (String indexfield , String sql, String pagetotal)
	 {

		JSONObject whdata= new JSONObject();   
		whdata.put("total", pagetotal);
		 JSONArray array = new JSONArray();      //定义JSON数组
		String[] idstr= indexfield.split(",");
		try
		{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sql);
			while (rs.next())
			{
				JSONObject obj = new JSONObject();       
				for (int i=0,j=1;i < idstr.length;i++,j++)
				{
					obj.put(idstr[i] , rs.getString(j));
				}
				array.add(obj);
			}
			 whdata.put("rows", array);
			dbc.close()	;
			 conn.close();
		}
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString() );
			
		System.out.print( sql );
		}
		catch (Exception e)
		{
		
			System.out.println(e.toString());
		}
		finally
		{
			//关闭连接
		}
		
		return whdata.toString(); 
	}

	
}