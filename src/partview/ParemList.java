package partview;

import java.io.*;
import java.util.*;
import util.innpath;
import org.dom4j.*;
import org.dom4j.io.*;

import util.S_Param;
import javax.servlet.http.HttpServletRequest;

public class ParemList
{
private String titlefield ;
private String 	sqlstring  ;
	private String 	rolestr  ;
	private String 	usercode ;

	private String 	hrefstr ;
	 private String 	langstr ;
	
	 	public ParemList
( String role, String czy, String hreft , String langName )	{

		rolestr = role;
		langstr= langName;
		 usercode= czy;
		 hrefstr= hreft;
		
} 



	public String getdbresult (HttpServletRequest request) throws Exception 
	{
	String Id= request.getParameter("idstr");
	 	 String 	realPath= innpath.getxmlpath( rolestr,usercode,"//Pdbparam//"+ hrefstr+".xml" );
  	
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
				
		 		titlefield = database.element("titlefield").getText().trim() ;
				sqlstring = database.element("sqlstring").getText().trim();
				 	 	 if( ( sqlstring.indexOf("S_")>0)&&(  realPath.indexOf( "/"+usercode )>0))
		
		 	{			
		 	
		 	 sqlstring = "DDD";
		 	System.out.print( "访问系统xml:"+realPath+"#"+ sqlstring );
		 	
		 }
				
				 
				}
			 else
		  {
		  System.out.println( realPath + " ,param xml not exist");
		}
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}




String resultstr="";
resultstr =S_Param.Getparams ( sqlstring+"'"+Id+"'", titlefield );
if( resultstr.length()>0 )
return resultstr.substring(0, resultstr.length() - 1) ;
else return "无内容";

}
	public String getxmlresult(String idstr)
{
   String 	 realPath= innpath.getxmlpath( rolestr,usercode,"/"+ hrefstr +"/"+ idstr+ ".xml" );
	  try
		{
		 	SAXReader reader = new SAXReader();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			File file = new File(realPath);
			 
			 if (file.isFile())
			{
			 	  	
				Document document = reader.read(file);
				Element database = (Element)document.selectSingleNode("//" +idstr);
		 		titlefield = database.element("helpdesc").getText().trim() ;
				}
			 else
		  {
		  System.out.println( realPath + " ,param xml not exist");
		}
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
return titlefield ;


}



}