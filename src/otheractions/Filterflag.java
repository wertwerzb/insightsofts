//对一个业务进行处理可以定制
package otheractions;

import util.innpath;
import android.os.Environment; 
import java.io.File;
import Com.db.DruidConnect;
import org.dom4j.*;
import org.dom4j.io.*;
 import java.net.URLDecoder;
	 import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import Com.util.StrFun;
import java.sql.*;
import javax.servlet.http.*;
public class Filterflag {
private String replacefield;
private String sqltext;
private String faultmessage; 
private String rolestr; 
private String usercode; 

private int count; 
  public Filterflag( String role,String userstr, String hreft)
	{
	rolestr=role;
	usercode=userstr;
	
		setvar(hreft);
  } 

public String filterFlag
(HttpServletRequest request) throws Exception
	{
	String ff="1";
	  String[] feildstr = replacefield.split(",");
  	  String[] str;
    String[] faultstr;
		 for (int i = 0; i < feildstr.length;i++) {
		
		 sqltext= sqltext.replace("{"+(i+1)+"}",  StrFun.getString(request, feildstr[i].trim()));
		
  }
  str = sqltext.split("⊙");
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
		} catch (Exception exception) {
				exception.printStackTrace();
			}
			return ff;
	
}
	 
public String xmltosql( )
	{
	 	  String[] feildstr = replacefield.split(",");
	  String str;

		 for (int i = 0; i < feildstr.length;i++) {
		
		  sqltext= sqltext.replace("{"+(i+1)+"}",feildstr[i].trim());
		
  }
  str = sqltext;
		 
		 //System.out.print(str); 
		 
		 return( str );
	}

	
	private void setvar(String hreft)
	{
//fliterflag.xml" Environment.getExternalStorageDirectory().getPath() ;   

	 	 
		 String 	  realPath= innpath.getxmlpath( rolestr,usercode,"/Ofilter/"+ hreft+".xml" );
		try
		{
			SAXReader reader = new SAXReader();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			File file = new File(realPath);
			if (file.isFile())
			{
				Document document = reader.read(file);
				Element database = (Element)document.selectSingleNode("//" + hreft);

faultmessage =database.element("faultmessage").getText().trim() ; 


sqltext=database.element("sqltext").getText().trim() ; 

replacefield =database.element("replacefield").getText().trim() ; 

} else
		  {
		  System.out.println( realPath + "filter xml not exist");
		}
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}