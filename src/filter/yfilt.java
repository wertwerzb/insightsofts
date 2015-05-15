package filter;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.*;
public class yfilt
{

	public yfilt()
	{} 
	
	 	public static String getresults (HttpServletRequest request) throws Exception

	{
	 	HttpSession session = request.getSession();
			
			 	String ROLE = (String) session.getAttribute("rolecode");
	String ff="0";
	try{
	 	if(!( ( ROLE.length()>3  )
	 	&& ("0101").equals( ROLE.substring(0,4) )  ) )
	 	
	 	ff="不是基层开发人员不能操作";
	
return ff;
		}
		 
		catch (Exception ex)
		{
		
			System.out.println( ex.toString() );
			return ex.toString() ;
			
		}

	}		

}