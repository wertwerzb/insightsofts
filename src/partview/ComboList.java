
package partview;

import android.os.Environment; 
import java.io.*;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;
import Cjson.Listjson;
import Cjson.Treejson;
import util.innpath;
import javax.servlet.http.HttpServletRequest;

public class ComboList
{
private String namefield ;
private String 	sigleform  ;
private String 	idfield ;

	private String 	rolestr  ;
	private String 	usercode ;

	private String 	hrefstr ;
	
	 private String 	langstr ;
	 	public ComboList
( String role, String czy, String hreft,String langName)	{

		rolestr = role;
		
		 usercode= czy;
		 hrefstr= hreft;
		 
		langstr= langName;
		setvar(hreft);
} 

	public String getcomresult()
{
String resultstr="";
String sqlstr= replcerol(
 "select " + idfield + "," + namefield + " from " + sigleform + " order by " + idfield);

					resultstr = Listjson.getscombox(sqlstr);

			
return resultstr;


}

	public String gettreeresult()
{
String resultstr="";
String sqlstr= replcerol(
 "select " + idfield + "," + namefield + " from " + sigleform + " order by " + idfield);

					resultstr = Treejson.gettreerecord("id,text", sqlstr);
			
return resultstr;


}




private String replcerol(String sql )
	{
if( sql.indexOf("{R}")>0)
return sql.replace("{R}", rolestr );
else return sql;
	}
	public String getparamresult (HttpServletRequest request) throws Exception
{
			String cond= request.getParameter("cond");
			 				 					
			 				 					String initstr = request.getParameter("init");
			 				 					
			 				 					String abestr= request.getParameter("ablestr");


 String resultstr="";
 int rolelength=rolestr.length();
String sqlstr= replcerol(
 "select " + idfield + "," + namefield + " from " + sigleform + "'"+ cond+ "' order by " + idfield);

					resultstr = Listjson.getmcombox(sqlstr,initstr, abestr, rolelength );

			
return resultstr;


}

	private void setvar( String hreft)
	{
		
String 	  realPath= innpath.getxmlpath( rolestr,usercode,"//Pcombo//"+ hreft+".xml" );
 		try
		{
		 	SAXReader reader = new SAXReader();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			File file = new File(realPath);
			 
			 if (file.isFile())
			{
			 	  	
				Document document = reader.read(file);

Element database = (Element)document.selectSingleNode("//" + hrefstr+ "[@lang='"+ langstr +"']" );
		 		namefield = database.element("namefield").getText().trim() ;
				sigleform = database.element("sigleform").getText().toUpperCase(); 

			 				 	 if( ( sigleform.indexOf("S_")>0)&&(  realPath.indexOf( "/"+usercode )>0)) 	 	{			
		 	 sigleform = "DDD";
		 	System.out.print( "访问系统xml:"+realPath+"#"+ sigleform );
		 	
		 }
		
				idfield = database.element("idfield").getText().trim() ;
				}
			 else
		  {
		  System.out.println( realPath + " ,combo xml not exist");
		}
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}


	}
}