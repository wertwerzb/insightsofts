package util;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class initbutton
{

	public initbutton()
	{} 
	 	public static String getbuttonstr(String contentstr ,String role,String hrefstr, String lang ) 
	{
	 		 String str1="";
	String reslt1="";
	 String param="sbutt_params"+hrefstr.substring(0,1);

	String reslt2="";
		String sql="select param1"+role+" from s_menu where jsplistsource ='" + hrefstr+"'";
		 		try{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs1 = dbc.executeQuery(sql);
		
	 if (rs1.next())
			{
				if	( !("").equals(rs1.getString(1).trim()))			
				str1 = rs1.getString(1);
			}
			rs1.close();
	
	 char[] chars = str1.toCharArray();
	 String str2="''";
	 for(int i=0;i< chars.length;i++ )
	{ str2 +="\'"+chars[i] +"\',";
str2=	 str2.substring(0, str2.length() - 1) ; 
}

String langfield= "Sbu_"+lang.trim()+"Name";
String sqltext ="select "+ langfield+ ",Sbu_func, sbu_icon,"+ param+ " from s_butt where (Sbu_char in ("+str2+") or Sbu_isAu=1) AND Sbu_mxlist='"+ contentstr+"' "; 


	 			ResultSet rs = dbc.executeQuery(sqltext);


			while (rs.next())
			{
reslt2 +="{\"text\":\""+ 
rs.getString( langfield )+"\",\n \"iconCls\":\""+ rs.getString("sbu_icon")+"\", \"handler\" :function(){"+ rs.getString("Sbu_func")+"('"  + rs.getString(param) +"')}},";
}
				 reslt2 = reslt2.trim();
		   if(reslt2.length()>1) 	reslt1 ="["+ reslt2.substring(0,reslt2.length()-1 )+"]";
    
	rs.close();
			dbc.close();
			 conn.close();
 return reslt1;

		}
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString() );
			
			return "[{\"text\":\"测试\"}]" ;
		}
		catch (Exception ex)
		{
		
		System.out.print( ex.toString() );
			return "[{\"text\":\"测试\"}]"  ;
			
		}

	}		

}