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
public class FormEdit
{
	private String  selectcolumn;
	private 	String  fieldtitle; 
	private 	String  sigleform;
	private 	String  idfield ;
	private 	String  typestr ;
 private 	String modextfield;

	private 	String  langstr ;
	private 	String usercode;
private 	String filepath;
	public FormEdit(String lang, String czy, String hreft,String fileph )
	{
		langstr = lang;

		usercode = czy;
		 filepath= fileph;
		setvar(hreft);

	} 
	
	public String getmodsql(HttpServletRequest request) 
	{
		String sql = "Update " + sigleform + " set " ; 
		HashMap<String,String> hm=new HashMap<String,String>(); 
	 	String[] feildstr = new String[40];
		String[] valuestr = new String[40];
		feildstr = selectcolumn.split(",");
		valuestr = fieldtitle.split(",");
		String  idstr=" where " + feildstr[0] + "='" + StrFun.getString(request, valuestr[0]) + "'";
	String[] dattype = new String[40];
		dattype = typestr.split(",");

		for (int i= 0; i < valuestr.length;i++)
		{
		 if(!("h".equals(dattype[i]))) 	hm.put(feildstr[i], "'"+java.net.URLDecoder.decode(request.getParameter( valuestr[i] ) )+"'");
		}
		 if (modextfield.length() > 0)
		{
			String[] modextstr = new String[40];
    	modextstr = modextfield.split(",");
			for (int i= 0; i < modextstr.length;i++)
			{
				try
				{
					if (("user").equals(modextstr[i].split(":")[1]))
					 hm.put( modextstr[i].split(":")[0], "'"+ usercode+ "'" );

	 		 		else if (("getid").equals(modextstr[i].split(":")[1])) 
	 		 	 hm.put( modextstr[i].split(":")[0], "'"+ Idcode.getidcode(idfield, modextstr[i].split(":")[2], sigleform, modextstr[i].split(":")[3]) + "'" );
	 	 		else if (("ip").equals(modextstr[i].split(":")[1])) 
	 		 	 hm.put( modextstr[i].split(":")[0], "'"+ request.getRemoteAddr()+ "'" );
	 		 	 		
	 			else 
	 			 hm.put( modextstr[i].split(":")[0], modextstr[i].split(":")[1] );
	 			}
				catch (Exception e)
				{}
			}
		}

 java.util.Iterator it = hm.entrySet().iterator();
   while(it.hasNext()){
   java.util.Map.Entry entry = (java .util.Map.Entry) it.next();

sql = sql + entry.getKey() + "="  + entry.getValue () + "," ;

}

sql = sql.substring(0, sql.length() - 1); 

return replcerol( sql+ idstr );
	
	}
	
	
	 private  String getvar () 
	{
	
	return 
" selectcolumn "+selectcolumn+
	 " fieldtitle "+fieldtitle+
	 " sigleform "+sigleform+
	 " idfield " +idfield +
	" typestr "+typestr +
	" modextfield "	 +modextfield ;
	}
	
	public String Edit(HttpServletRequest request) 
{

String str= getmodsql( request );
	try
		{	
		  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
		dbc.executeUpdate(str);
		dbc.close();
		 conn.close();
		 } 
		 catch (SQLException e)
		{
			 System.out.print(getvar());
			 	return str;
		}
		 catch (Exception e)
		{
			
		}
		

		return "操作成功";

}
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "本地";
		}
		if (ip.split(",").length > 1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}
	 private String replcerol(String sql )
	{
	String prole;
	String resultstr= sql ;

 if( sql.indexOf("{U}")>0)
resultstr= resultstr.replace("{U}", usercode);

  return resultstr;
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
				selectcolumn = database.element("selectcolumn").getText().replaceAll("\\s*", "") ;
				fieldtitle = database.element("fieldtitle").getText().replaceAll("\\s*", "") ;
				sigleform = database.element("sigleform").getText().trim() ;
				idfield = database.element("idfield").getText().trim() ;
				typestr = database.element("typestr").getText().trim() ;
      modextfield = database.element("modextfield").getText().trim() ;


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