package otheractions;

import 
android.os.Environment; 
import java.io.*;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;
import java.sql.ResultSet;
import java.net.URLDecoder;
import util.innpath;
import util.Idcode;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import Com.db.DruidConnect;
import Com.util.StrFun;


import java.sql.Connection;
import java.sql.Statement;
public class FormDel
{ 
	private 	String  sigleform;
	private 	String  idfield ;
private 	String delextfield;

	private 	String  langstr ;
	private 	String usercode;
private 	String filepath;
	public FormDel(String lang, String czy, String hreft,String fileph )
	{
		langstr = lang;

		usercode = czy;
		 filepath= fileph;
		setvar(hreft);

	} 
public String getdelsql (HttpServletRequest request) 
	{
		 String id = request.getParameter("id");
		String Str="Delete From " + sigleform + " where " + idfield + "='" + id + "'" ;
		return Str;
		 
	}	
	public String Del (HttpServletRequest request) 
	{
	
	String Str=	 getdelsql( request );
try{
		  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
		dbc.executeUpdate(Str);
		dbc.close();
conn.close();
}
catch (SQLException e)
		{
	return Str;
		}
		 catch (Exception e)
		{
			
			 	e.printStackTrace();
	return getvar();
		}
		

		return "操作成功";

	}	
	 private String replcerol(String sql )
	{
	String plang;
	String resultstr= sql ;
	if( langstr.length()>1 )
	 	plang= langstr.substring( 0,langstr.length()-2 ) ; 
	else
	 plang="";

if( sql.indexOf("{R}")>0)
resultstr= sql.replace("{R}", langstr );

 if( sql.indexOf("{U}")>0)
resultstr= resultstr.replace("{U}", usercode);

  return resultstr;
	}
 private  String getvar () 
	{
	
	return 
" sigleform "+ sigleform +
	 " idfield "+ idfield +
	 " delextfield "+ delextfield;
	}
	private void setvar(String hreft)
	{

String 	  realPath= innpath.getPath("xml")+ filepath+ "/" + hreft + ".xml";
		try
		{
			SAXReader reader = new SAXReader();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			File file = new File(realPath);
			if (file.isFile())
			{
				Document document = reader.read(file);
				Element database = (Element)document.selectSingleNode("//" + hreft + "[@lang='" + langstr + "']");

				sigleform = database.element("sigleform").getText().trim() ;
				idfield = database.element("idfield").getText().trim() ;
			
      delextfield = database.element("delextfield").getText().trim() ;


			}
			else
			{
				System.out.println(realPath + " ,outer xml not exist");
			}

		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}