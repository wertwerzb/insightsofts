package filter;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.*;
public class sfilt
{

	public sfilt()
	{} 
	
	 	public static String getresults (HttpServletRequest request) throws Exception

	{
	 	HttpSession session = request.getSession();
			
			 	String ROLE = (String) session.getAttribute("rolecode");
	String ff="0";
	try{
	 if( "0".equals(ROLE )||( ROLE==null ) ) ff="未登录请登录请先登录";
	
return ff;
		}
		 
		catch (Exception ex)
		{
		
			System.out.println( ex.toString() );
			return ex.toString() ;
			
		}

	}		

}