package Cjson;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray; 

/*
*/
public class Formjson
{

	public Formjson ()
	{} 
	
	 	public static String getsidjsoin(String selectcolumn, String sql)
	{
		String[] idstr= selectcolumn.split(",");
		JSONObject obj = new JSONObject();       

		try
		{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sql);
			if (rs.next())
			{

				for (int i=0,j=1;i < idstr.length ;i++,j++)
				{
					 obj.put(idstr[i], rs.getString(j))  ;
				 	//array.add(obj);
				}
			}
			dbc.close()	;
		}
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString() );
			
		System.out.print( " getsidjsion( selectcolumn :"+ selectcolumn + ", sql,:"+sql );
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			
		System.out.print( sql );
		}
		finally
		{
			//关闭连接
		}
		return obj.toString();
	}
	public static String getsidrecord(int  mm, String sql)
	{
		String result="";
		try
		{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sql);
			if (rs.next())
			{

				for (int i=1;i <= mm ;i++)
				{
					result +="\""+ rs.getString(i) + "\",";

				}
			}
			dbc.close()	;
		}
		 
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString() );
			
		System.out.print( " getsidrecord(int  mm, :"+ mm+ ", sql,:"+sql );
		}
		catch (Exception e)
		{ 
			System.out.println(e.toString());
			
		}
		finally
		{
			//关闭连接
		}
		return result.substring(0, result.length() - 1) ;
	}

}