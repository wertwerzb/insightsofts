//对一个业务进行处理可以定制

package otheractions;
import util.innpath;
import android.os.Environment; 
import java.io.File;

import org.dom4j.*;
import org.dom4j.io.*;

import java.util.Iterator;
import java.util.HashMap;

import java.sql.SQLException;
import Com.db.DruidConnect;
import Com.util.StrFun;
import java.sql.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.Connection;
import java.sql.Statement;
public class Checkflag
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
	private String langstr;
	private int checkin;
	public Checkflag( String role, String czy, String hreft)
	{
	rolestr=role;
	usercode=czy;
	setvar(hreft);
	} 
	public String getvar()
	{
		String sqlstr= "- fieldtitle " + fieldtitle;

		return sqlstr ;
	}
	
	public int gettotal()
	{
		int i= fieldtitle.split(",").length;
		int j= columntitle.split(",").length;
		int result;
		if (i == j) result = i;
		else result = -1;
		return result ;
	}
	public String getfirstindex( HttpServletRequest request )
	{
		String result = null;
		String fielddata[]= (fieldtitle).split(",");
		String titledata[]=  (columntitle).split(",");
//		String comdata[]=  l(comname).split(",");
		String paramdata[]= (comparam).split("⊙");
		String buttonstr="";
		String placeholder="";
		for (int i=0;i < titledata.length;i++)
		{

			String paramdatastr="";
		 	String formaterstr="";

			String comnames= "";
			String title[]= titledata[i].split(":");
			if (title.length > 3)
			{
				placeholder = "\"placeholder\":\"" + title[3] + "\",\n";
			} 
			else placeholder = "" ;


			buttonstr += "{\"field\":\"" + fielddata[i].trim() + "\",\n" + placeholder +
				"\"grouprow\":\"" + title[1].trim() + "\",\n" +
				"\"name\":\"" + title[0].trim() + "\",\n" ;
			if (title[2].indexOf("-") < 1)
			{ 
				comnames = title[2] ;
				buttonstr += "\"editor\":\"" + comnames + "\"},";
			}
			else
			{
				String comnamestr[] = title[2].split("-");
				comnames = comnamestr[0];
				buttonstr += "\"editor\":{\"type\":\"" + comnames + "\"," ;
				int j=Integer.parseInt(comnamestr[1]);

					paramdatastr = paramdata[j];
					buttonstr += "\"options\":{" + paramdatastr + "}}}\n,";
		
			}
		}
		if( checkin>0 )
		 buttonstr += "{\"field\":\"rand\",\"grouprow\":\"1\",\"name\":\""+excheck(request)+"\", \"editor\":{\"type\":\"textbox\", \"options\":{\"width\":\"300px\"}} },";
		result = "{\"total\":" +( gettotal()+1 ) + ",\"rows\":[" + buttonstr.substring(0, buttonstr.length() - 1) + "]}";
	
		return result;
	}

	public String getinithtml( HttpServletRequest request )
	{
		String result= getfirstindex( request ) + "⊙" + "{" + exterstr.substring(0, exterstr.length() - 1) + "}";
		return result;

	}


	public String excheck
	(HttpServletRequest request) 	{
		HttpSession session = request.getSession(true);

		Random random = new Random();
		int a=random.nextInt(20);
		int b=random.nextInt(20);
		session.setAttribute("rand", a + b + ""); 	
	 	return a + "+" + b + "=";
	}
	public int yezhen
	(HttpServletRequest request)
	throws Exception
	{

		HttpSession session = request.getSession(true);
		if (request.getParameter("rand").trim().equals(session.getAttribute("rand"))) 
		return 0;
		else return 1;


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

	public String xmltosql(HttpServletRequest request)throws SQLException
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
  Statement dcm = conn.createStatement(); 
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
	
	 if ( ("F").equals( hreft.substring(1,2) )) 
	 checkin=1;
	 else checkin=0;
 String 	  realPath= innpath.getxmlpath( rolestr,usercode,"/Ocheck/"+ hreft+".xml" );


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