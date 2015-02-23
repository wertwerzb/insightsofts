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
public class CheckFlg
{
	
	private String fieldtitle;
	private String sqltext;
	private String retvalue; 
private String setvalue; 
	private String unretvalue; 
	
	private String langstr;
	private String usercode;
	 private String filepath;
	public CheckFlg( String lang, String czy, String hreft,String fileph )
	{
	langstr=lang;
	usercode=czy;
	filepath= fileph ;
	setvar(hreft);
	} 

			
	public String getjsp(HttpServletRequest request)throws SQLException
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
		return getvar();
		} 	

		return result;
	}
	
	 private  String getvar () 
	{
	
	return 
" sqltext "+ sqltext +
	 " fieldtitle "+fieldtitle+
	 " retvalue "+ retvalue +
	 " setvalue " + setvalue +
	" unretvalue "+ unretvalue ;
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

				Element database = (Element)document.selectSingleNode("//" + hreft + "[@lang='" + langstr+ "']");

				
				retvalue = database.element("retvalue").getText().trim() ; 
				unretvalue = database.element("unretvalue").getText().trim() ; 

				setvalue = database.element("setvalue").getText().trim() ; 
				sqltext = database.element("sqltext").getText().trim() ; 

				fieldtitle = database.element("fieldtitle").getText().trim() ; 
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