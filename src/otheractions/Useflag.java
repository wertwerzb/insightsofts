//对一个业务进行处理可以定制
package otheractions;

import include.StaticFun; 
import java.io.File;
import util.innpath;
import org.dom4j.*;
import org.dom4j.io.*;
import java.net.URLDecoder;

import java.sql.SQLException;

import javax.servlet.http.*;
import Com.db.DruidConnect;
import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.Statement;
public class Useflag
{
	private String updatefield;
	private String updatesql;
	private String rolestr;
	 private String usercode;
	private String path;

	private String replacefield;
	private String sqltext;
	private String faultmessage; 
	 private String langstr; 

	public Useflag(String czy, String userstr, String hreft , String langName )
	{
		rolestr = czy ;
		usercode=userstr;
		langstr= langName;
		setvar(hreft);
	} 
	public String getvar()
	{
		String sqlstr= "- updatefield " + updatefield;

		return sqlstr ;
	}

	public String UseFlag
	(HttpServletRequest request) throws Exception
	{
	
		String str = xmltosql(  request ) ;
try{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
		dbc.executeUpdate(str);
		
		dbc.close();
		 conn.close();
		 }
		 catch (SQLException e)
		{
			System.out.print(str);
		}
		 catch (Exception e)
		{
			 	e.printStackTrace();
		}
		

		return "操作成功";

	}



	public String xmltosql( HttpServletRequest request )
	{
	
		 	String[] feildstr = updatefield.split(",");
	String str;
	   	HttpSession session = request.getSession();
		for (int i = 0; i < feildstr.length;i++)
		{
		 String param="";
		if( feildstr[i].substring(0, 1).equals("@") )
		{
		String sessionname= feildstr[i].substring(1, feildstr[i].length() );
		param= (String) session.getAttribute( sessionname);
		}
		else
		{
		 param= java.net.URLDecoder.decode(feildstr[i]);
		}

			updatesql = updatesql.replace("{" + (i + 1) + "}", param);

		}
		str = updatesql;
		//System.out.print(str); 

		return(str);
	}


	private void setvar(String hreft)
	{	
 String 	  realPath= innpath.getxmlpath( rolestr,usercode,"/Ouse/"+ hreft+".xml" );
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

				updatesql = database.element("updatesql").getText().trim() ; 

				updatefield = database.element("updatefield").getText().trim() ; 
				faultmessage = database.element("faultmessage").getText().trim() ; 


				sqltext = database.element("sqltext").getText().trim() ; 

				replacefield = database.element("replacefield").getText().trim() ; 

			}
		
			 else
		  {
		  System.out.println( realPath + "use xml not exist");
		}
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}