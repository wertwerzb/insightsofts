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
public class FormAdd
{
	private String  selectcolumn;
	private 	String  fieldtitle; 
	private 	String  sigleform;
	private 	String  idfield ;
	private 	String  rolestr ;
	private 	String  typestr ;

	private 	String path;
	private 	String extendfield;
private 	String modextfield;

	private 	String usercode;
private 	String filepath;
	public FormAdd(String role, String czy, String hreft,String fileph )
	{
		rolestr = role;

		usercode = czy;
		 filepath= fileph;
		setvar(hreft);

	} 
	public  String AddString()
	{
		String selstr;
		String str="";
		String firstfield= selectcolumn.split(",")[0];

		String firstvalue;

		int j= Integer.parseInt(extendfield); 
		if ((firstfield.equals(idfield)) || (j == 0))//id在其中
		{
			selstr = selectcolumn;

			firstvalue = "request" + fieldtitle.split(",")[0]  ;
		}
		else
		{
			selstr = idfield + "," + selectcolumn;
			firstvalue = " getaddid()";
			fieldtitle = "," + fieldtitle;
			typestr = "s," + typestr;

		}

		str = " insert into " + sigleform + " (" + selstr + ") values('" + firstvalue + "'," ;
 		String[] valuestr = new String[40];
		valuestr = fieldtitle.split(",");
		String[] dattype = new String[40];
		dattype = typestr.split(",");

		for (int i =1; i < valuestr.length;i++)
		{
			if (("f").equals(dattype[i])) str = str + " " + "request:" + valuestr[i] + ",";
			else if (("i").equals(dattype[i])) str = str + " " + "request:'" + valuestr[i] + "'," ;
			else str = str + " '" + 
					"request:'" + valuestr[i] + "',";

		}
		str = str.substring(0, str.length() - 1); 

		return str;

	}
	
	 public  String getvar (HttpServletRequest request) throws Exception 
	{
	
	return "selectcolumn"+ selectcolumn+ ";fieldtitle "+ fieldtitle+ ";sigleform "+ sigleform +";idfield "+ idfield+" ; typestr "+ typestr+";path "+filepath +";extendfield "+ extendfield +";modextfield"+ modextfield ;
	}
	
	
	public  String getaddsql(HttpServletRequest request) throws Exception 
	{
	 	String selstr ="";
 		String valuestring ="";
	 	HashMap<String,String> hm=new HashMap<String,String>(); 
	 	String[] feildstr = new String[40];
		String[] valuestr = new String[40];
		feildstr = selectcolumn.split(",");
		valuestr = fieldtitle.split(",");
		 	String[] dattype = new String[40];
		dattype = typestr.split(",");

		for (int i= 0; i < valuestr.length;i++)
		{
		 if(!("h".equals(dattype[i])))
			hm.put(feildstr[i], "'"+ java.net.URLDecoder.decode(request.getParameter( valuestr[i] ) )+ "'" );
		}
		 if ( extendfield.length() > 0)
		{
			String[] extstr = new String[40];
    	extstr = extendfield.split(",");
			for (int i= 0; i < extstr.length;i++)
			{
				try
				{
					if (("user").equals(extstr[i].split(":")[1]))
					 hm.put(extstr[i].split(":")[0], "'"+ usercode+ "'" );
	 			else if (("role").equals(extstr[i].split(":")[1]))
				 hm.put( extstr[i].split(":")[0], "'"+ rolestr+ "'" );
	 		 		else if (("getid").equals(extstr[i].split(":")[1])) 
	 		 	 hm.put( extstr[i].split(":")[0], "'"+ Idcode.getidcode(idfield, extstr[i].split(":")[2], sigleform, extstr[i].split(":")[3])+ "'" );
	 		 	else if (("ip").equals(extstr[i].split(":")[1])) 
	 		 	 hm.put( extstr[i].split(":")[0], "'"+ getIpAddr(request) + "'" );
	 		
	 			else 
	 			 hm.put( extstr[i].split(":")[0], extstr[i].split(":")[1] );
	 			}
				catch (Exception e)
				{}
			}
		}

 java.util.Iterator it = hm.entrySet().iterator();
 while(it.hasNext()){
   java.util.Map.Entry entry = (java .util.Map.Entry) it.next();
    

//entry.getValue() 返回与此项对 应的值
selstr= selstr+ entry.getKey()+",";
valuestring = valuestring  + entry.getValue () + ",";
}
selstr= selstr.substring(0, selstr.length() - 1) ;

valuestring= valuestring.substring(0, valuestring.length() - 1) ;
String str =" insert into " + sigleform + " (" + selstr +  ") values(" + valuestring + ")" ;
		return str;
	}
	public  void Add(HttpServletRequest request) throws Exception
	{
		String str= getaddsql(request) ;
		  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
		dbc.executeUpdate(str);
		dbc.close();
conn.close();

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

	private void setvar(String hreft)
	{


		String 	  realPath= innpath.getxmlpath(rolestr, usercode, "/"+filepath+"/" + hreft + ".xml");
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
				selectcolumn = database.element("selectcolumn").getText().replaceAll("\\s*", "") ;
				fieldtitle = database.element("fieldtitle").getText().replaceAll("\\s*", "") ;
				sigleform = database.element("sigleform").getText().trim() ;
				idfield = database.element("idfield").getText().trim() ;
				typestr = database.element("typestr").getText().trim() ;
				extendfield = database.element("extendfield").getText().trim() ;
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