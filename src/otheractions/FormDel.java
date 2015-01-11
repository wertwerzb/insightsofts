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
	private 	String  rolestr ;
	private 	String  typestr ;

	private 	String path;
private 	String delextfield;

	private 	String usercode;
private 	String filepath;
	public FormDel(String role, String czy, String hreft,String fileph )
	{
		rolestr = role;

		usercode = czy;
		 filepath= fileph;
		setvar(hreft);

	} 

	public void Del (HttpServletRequest request) throws Exception 
	{
		 String id = request.getParameter("id");
		String Str="Delete From " + sigleform + " where " + idfield + "='" + id + "'" ;
		//and bcp_Uflag=0";
		  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
		dbc.executeUpdate(Str);
		dbc.close();
conn.close();
	}	
	 private String replcerol(String sql )
	{
	String prole;
	String resultstr= sql ;
	if( rolestr.length()>1 )
	 	prole= rolestr.substring( 0,rolestr.length()-2 ) ; 
	else
	 prole="";

if( sql.indexOf("{R}")>0)
resultstr= sql.replace("{R}", rolestr );

 if( sql.indexOf("{U}")>0)
resultstr= resultstr.replace("{U}", usercode);

  return resultstr;
	}

	private void setvar(String hreft)
	{


		String 	  realPath= innpath.getxmlpath(rolestr, usercode, "/"+ filepath+ "/" + hreft + ".xml");
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