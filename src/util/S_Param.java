package util;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;


public class S_Param
{

	public S_Param()
	{} 

	public static String Getparam(String sqlstr, String defaultstr)
	{
		String resultstr= defaultstr ;

	 	try
		{
			  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sqlstr);
			if (rs.next())
			{
				if	( !("").equals(rs.getString(1).trim()))			
				resultstr = rs.getString(1);
			}
			rs.close();
			dbc.close();
conn.close();
		}
		catch (Exception sqle)
		{
			return sqle.toString();
   }
   finally
		{
		 	//关闭连接
		}
		return resultstr ;
	 	// TODO Auto-generated method stub
	}
	 public static String Getparams(String sqlstr, int deint)
	{
		String resultstr= "" ;
		 	try
		{  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sqlstr);
			if (rs.next())
			{
			for(int i=1;i< deint+1 ;i++)
	{	
			resultstr =  resultstr +rs.getString(i )+"/n,";
		 } 	}
			rs.close();
			 dbc.close();
			 conn.close();

		}
		catch (Exception sqle)
		{ 
			return sqle.toString();
   }
		return resultstr ;
	 	// TODO Auto-generated method stub
	}

	public static String Getparams(String sqlstr, String destr)
	{
		String resultstr= "" ;
String[] ddd=destr.split(":");
	 
	 	try
		{
		  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
  	ResultSet rs = dbc.executeQuery(sqlstr);
		
			if (rs.next())
			{
			int j=0;
			for(int i=0;i<ddd.length;i++)
	{		j=i+1;
			resultstr = resultstr+ "\""+ddd[i]+"\":\""+rs.getString( j )+"\",";
		 } 	}
			rs.close();
			 dbc.close();
conn.close();

		}
		catch (Exception sqle)
		{
			return sqle.toString();
   }
   finally
		{
			//关闭连接
		}
		return resultstr ;
	 	// TODO Auto-generated method stub
	}

}