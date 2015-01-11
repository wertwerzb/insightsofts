package filter;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dbfilter
{

	public dbfilter()
	{} 
	 	public static String getre(String sql ,String  faultmessage ) throws Exception
	{
	 		
 
	String ff="1";
  	  String[] str;
    String[] faultstr;
     str = sql.split("⊙");
  faultstr=faultmessage.split("⊙");
		 		  Connection conn = DruidConnect.openConnection(); 
  Statement dcm = conn.createStatement(); 
			 try {
		int	 j=0;
			while(j<str.length) {
			ResultSet rs = dcm.executeQuery( str[j] ); 		 
		 
				if (rs.next()) {
		 if (rs.getInt(1)==0) {
		 
		  
		 j=j+1;
		 
		 }
		 else
		 { 
		 int h= rs.getInt(1);
		 ff= faultstr[h];
		 break;
		 }
		 rs.close();
		}			
		 }
	 dcm.close();
		 conn.close();
return ff;
		}
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString() );
			
			return "1" ;
		}
		catch (Exception ex)
		{
		
			return "1" ;
			
		}

	}		

}