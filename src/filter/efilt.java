package filter;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.*;
public class efilt
{

	public efilt()
	{} 
	
	 	public static String getresults (HttpServletRequest request) throws Exception

	{
	 	HttpSession session = request.getSession();
			
	String ff="0";
	try{
	 String usercode= (String) session.getAttribute("usernamec");
			 	String ROLE = (String) session.getAttribute("rolecode");
String password = (String) session.getAttribute("password");
	 	 	
	 	String 	 str="select count(*) from s_czyaccess where webname='"+ usercode+"' and roleid='"+ ROLE+"' and password='"+ password+"'" ;
	 	 Connection conn = DruidConnect.openConnection(); 
  Statement dcm = conn.createStatement(); 
		ResultSet rs = dcm.executeQuery(str); 		 
		
			if (rs.next())
			{
				if (rs.getInt(1) == 0)
				{
				ff="密码角色用户名不对或不对应";
			}
	 	 	}
	 	 	
	 	 	
	 	 	
	 	 	
return ff;
		}
		 
		catch (Exception ex)
		{
		
			System.out.println( ex.toString() );
			return "0" ;
			
		}

	}		

}