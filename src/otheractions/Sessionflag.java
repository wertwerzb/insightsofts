//对一个业务进行处理可以定制

package otheractions;

import android.os.Environment; 
import java.io.File;

import org.dom4j.*;
import org.dom4j.io.*;

import util.innpath;
import java.util.Iterator;
import java.util.HashMap;

import java.sql.SQLException;

import Com.util.StrFun;
import java.sql.*;
import javax.servlet.http.*;
import java.util.*;
import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
public class Sessionflag
{

	private 	String  columntitle;	 	 
	private String comparam;
	
	private String exterstr;

	private String fieldtitle;
	private String sqltext;
	private String rolestr;
	private String usercode;
	private String retvalue; 

	private String setvalue; 
	private String unretvalue; 
	private int checkin;
	public Sessionflag( String role, String czy, String hreft)
	{
	rolestr=role;
	usercode=czy;
		setvar(hreft);
	} 
		public String excheck
	(HttpServletRequest request) 	{
		HttpSession session = request.getSession(true);

		Random random = new Random();
		int a=random.nextInt(10);
		int b=random.nextInt(10);
		session.setAttribute("rand", a + b + ""); 	
	 	return a + "+" + b + "=";
	}
	public int yezhen
	(HttpServletRequest request)
	throws Exception
	{

		HttpSession session = request.getSession(true);
	
	 	String[] feildstr = fieldtitle.split(",");
		String str;

		for (int i = 0; i < feildstr.length;i++)
		{

		
	if (request.getParameter("rand").trim().equals(session.getAttribute("rand"))) 
		return 0;
		else return 1;
		}

return 1;
	}
	public void CheckFlag
	(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession(true);
		String[] feildstr = fieldtitle.split(",");
		String str;

		for (int i = 0; i < feildstr.length;i++)
		{

			sqltext = sqltext.replace("{" + (i + 1) + "}", StrFun.getString(request, feildstr[i].trim()));

		}
		str = sqltext;
		String[] sessionstr= setvalue.split(",");
		 		  Connection conn = DruidConnect.openConnection(); 
  Statement dcm = conn.createStatement(); 
		ResultSet rs = dcm.executeQuery(str); 		 
		try
		{
			if (rs.next())
			{
				if (rs.getInt(1) > 0)
				{
					response.sendRedirect(retvalue);
					for (int i=0;i < sessionstr.length;i++)
					{
						session.setAttribute(sessionstr[i], request.getParameter(sessionstr[i].trim()));

					}
				}
				else
				{
					response.sendRedirect(unretvalue);
				}
			}			

			rs.close();



			dcm.close();
conn.close();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}

	}

	public String xmltosql(HttpServletRequest request)
	throws SQLException
	{
		String result= retvalue ;
		HttpSession session = request.getSession(true);
		String[] feildstr = fieldtitle.split(",");
		String str;

		for (int i = 0; i < feildstr.length;i++)
		{

			sqltext = sqltext.replace("{" + (i + 1) + "}", StrFun.getString(request, feildstr[i].trim()));

		}
		str = sqltext;
		String[] sessionstr= setvalue.split(",");
		 		  Connection conn = DruidConnect.openConnection(); 
  Statement dcm= conn.createStatement(); 
		ResultSet rs = dcm.executeQuery(str); 		 
		try
		{
			if (rs.next())
			{
				if (rs.getInt(1) > 0)
				{

					for (int i=0;i < sessionstr.length;i++)
					{
						session.setAttribute(sessionstr[i], request.getParameter(sessionstr[i].trim()));

					}
				}
				else
				{
					result = unretvalue;
				}
			}			
			rs.close();
			dcm.close();
conn.close();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		} 	

		return result;
	}


	private void setvar(String hreft)
	{
	
	 if ( ("R").equals( hreft.substring(2,3) )) 
	 checkin=1;
	 else checkin=0;
		 String 	  realPath= innpath.getxmlpath( rolestr,usercode,"/Osession/"+ hreft+".xml" );


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
				Element extendxml = (Element)document.selectSingleNode("//" + hreft + "//extendxml");
				exterstr = "";
				for (Iterator ipe = extendxml.elementIterator(); ipe.hasNext();)
				{
					Element e_pe = (Element) ipe.next();
					exterstr += "\"" + e_pe.getName() + "\":\"" + e_pe.getText() + "\","; 

				}

				retvalue = database.element("retvalue").getText().trim() ; 
				unretvalue = database.element("unretvalue").getText().trim() ; 

				setvalue = database.element("setvalue").getText().trim() ; 
				sqltext = database.element("sqltext").getText().trim() ; 

				fieldtitle = database.element("fieldtitle").getText().trim() ; 
				columntitle = database.element("columntitle").getText().replaceAll("\\s*", "") ;

				comparam = database.element("comparam").getText().trim() ;
			}
			 else
		  {
		  System.out.println("check xml not exist");
		  }
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}