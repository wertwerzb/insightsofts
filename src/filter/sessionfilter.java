package filter;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sessionfilter
{

	public sessionfilter()
	{} 
	
	 	public static String getisequil(String sql ,String  faultmessage ) throws Exception
	{
	 		
 
	String ff="1";
  	  String[] str;
    String[] faultstr;
     str = sql.split("⊙");
  faultstr=faultmessage.split("⊙");
 try {
		int	 j=0;
			while(j<str.length) {
			String st1= str[j].split(":")[0];
			String st2= str[j].split(":")[1];
			if(st1.equals(st2))
		 j=j+1;
		 
		 else
		 { 
		 ff= faultstr[j];
		 break;
		 }
		}			
		 
return ff;
		}
		 
		catch (Exception ex)
		{
		
			System.out.println( ex.toString() );
			return "1" ;
			
		}

	}		

}