//对一个业务进行处理可以定制
package filter;


import util.innpath; 
import java.io.File;
import Com.db.DruidConnect;
import org.dom4j.*;
import org.dom4j.io.*;
	 import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.*;
import javax.servlet.http.*;
public class Ufilter 
{
private String sigleform;
private String useflag;
private String idfield;
private String faultmessage; 
private String langstr; 
private String usercode; 

  public Ufilter( String lang, String czy, String hreft,String fileph )
	{
	langstr=lang;
	usercode=czy;
	setvar(hreft);
  } 

public String getusesql (String id ) 
	{

		String Str="select   " +useflag+" from " +sigleform + " where " + idfield + "='" + id + "'" ;
		return Str;
		 
	}	
	public String Use (HttpServletRequest request) 
	{
	 		 String id = request.getParameter("id");
	String Str=	 getusesql( id); 
	 try { 		 		
	   Connection conn = DruidConnect.openConnection(); 
  Statement dcm = conn.createStatement(); 
			
			ResultSet rs = dcm.executeQuery( Str ); 
			 if (rs.next()) 
		 if (rs.getInt(1)==0) 
		 return "已生效不能操作";
		 
			
				
}
catch (SQLException e)
		{
		System.out.print(getvar());
	return Str;
		}
		 catch (Exception e)
		{
			
			 	e.printStackTrace();
	 return Str;
		}
		

		return "操作成功";

	}		
	 	public String getvar()
	 	{
	 	return " useflag "+useflag
+" sigleform "+sigleform 
+" idfield "+idfield 
+" faultmessage "+faultmessage ;
	 	
	 	}
	private void setvar(String hreft)
	{
	 innpath inn=new innpath();
		 String 	  realPath= inn.getxmlpath( langstr,usercode,"/Ofilter/dbfilter"+ hreft+".xml" );
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

useflag=database.element("useflag").getText().trim() ; 

sigleform =database.element("sigleform").getText().trim() ; 
idfield =database.element("idfield").getText().trim() ; 

faultmessage =database.element("faultmessage").getText().trim() ; 

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