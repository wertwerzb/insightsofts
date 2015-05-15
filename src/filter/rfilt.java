package filter;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.*;
public class rfilt
{

	public rfilt()
	{} 
	
	 	public static String getresults (HttpServletRequest request) throws Exception

	{
	 	HttpSession session = request.getSession();
			
	String ff="0";
	try{
	 			String rand1= (String) session.getAttribute("rand");
 	 	String rand2 = request.getParameter("rand");
	 if(!(rand2.equals(rand1)))
	{
	 ff="验证码错误!";
	 
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