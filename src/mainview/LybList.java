//增加roleCondstr,pagesizest 
package mainview;
import Cjson.Formjson;
import android.os.Environment; 
import java.io.File;
import util.innpath;
import org.dom4j.*;
import org.dom4j.io.*;
import java.util.Iterator;
import java.util.HashMap;
import Cjson.extendjson;
/**
 * Title:       
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      insightsoft
 * @author:		 weizhengbin
 * @version 1.0
 */
public class LybList 
{

	private String  idstr;
	private String selectcolumn;
	private 	String  fieldtitle;
	private 	String  columntitle;	
	private 	String 	 idfield; 	 
	private 	String  sigleform;
	private String comparam;
	private String rolestr ;
	private String group ;
	private String exterstr;
	private String langstr;
	private String usercode;
	private String firstcom;
 	private String exterparam;
	private String langName;
 


	public LybList(String role, String usercode, String hreft, String langname )
	{
	rolestr=role;
	usercode=usercode;
	
		 langstr = langname;
		setvar(hreft);
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
	public String getidsql(String Id)
	{
		String resultstr = "select " + selectcolumn + " from " + sigleform + " where " + idfield + "='" + Id + "'" ;
		return resultstr;
	}
	public String getidjsoin(String Id)
	{
		String sql= getidsql(Id) ;
		String resultstr = Formjson.getsidjsoin(fieldtitle, sql);
		return resultstr;

	}


	public String getidrecord(String ids)
	{
		String sqlstr= getidsql(ids);
		int total= gettotal();
		String resultstr = Formjson.getsidrecord(total, sqlstr);
		return resultstr;
	}

	public String getfirstindex()
	{
	 	String getmx=""+gettotal();
 String result=Cjson.FormInitjson.getFormtitle(fieldtitle,columntitle, comparam,group,getmx);
return result;

	}


	public String getinithtml()
	{
		String result="{\"formatstr\":" +  getfirstindex() + ",\"exterstr\":{" + exterstr.substring(0, exterstr.length() - 1) + "}}" + extendjson.getextendjson(firstcom, exterparam) ;
		return result;

	}
	private void setvar(String hreft)
	{

		String 	 realPath= innpath.getxmlpath(rolestr, usercode, "/Mlyb/" + hreft + ".xml");
		try
		{
			SAXReader reader = new SAXReader();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			File file = new File(realPath);
			if (file.isFile())
			{
				Document document = reader.read(file);
Element database = (Element)document.selectSingleNode("//" + hreft+ "[@lang='"+ langstr +"']" );

		// 获取<line>元素的属性值  
				Element extendxml = database.element("extendxml");
				exterstr = "";
				for (Iterator ipe = extendxml.elementIterator(); ipe.hasNext();)
				{
					Element e_pe = (Element) ipe.next();
					exterstr += "\"" + e_pe.getName() + "\":\"" + e_pe.getText() + "\","; 

				}

				selectcolumn = database.element("selectcolumn").getText().replaceAll("\\s*", "") ;
				fieldtitle = database.element("fieldtitle").getText().replaceAll("\\s*", "") ;
				columntitle = database.element("columntitle").getText().replaceAll("\\s*", "") ;
				group = database.element("group").getText().trim() ;
				sigleform = database.element("sigleform").getText().toUpperCase(); 
			 	 if( ( sigleform.indexOf("S_")>0)&&(  realPath.indexOf( "/"+usercode )>0)) 	{			
		 	 sigleform = "DDD";
		 	System.out.print( "访问系统xml:"+realPath+"#"+ sigleform );
		 	
		 }
		 
		
				idfield = database.element("idfield").getText().trim() ;
				comparam = database.element("comparam").getText().trim() ;
				firstcom = database.element("firstcom").getText().trim() ;
				exterparam = database.element("exterparam").getText().trim() ;
			}
			else
			{
				System.out.println(realPath + "mx xml not exist");
			}
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}