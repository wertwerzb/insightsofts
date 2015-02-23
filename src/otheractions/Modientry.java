package otheractions;


import java.io.*;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;
import java.sql.ResultSet;
 import java.net.URLDecoder;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

//import Com.util.sysglfun;
import util.innpath;

import Com.db.DruidConnect;
import Com.util.StrFun;

import java.sql.Connection;
import java.sql.Statement;
public class Modientry 
{
	 private String Xselectcolumn;
	 
	 private 	String  Xfieldtitle; 
	private 	String  Xsigleform;
	private 	String  Xidfield ;
	private 	String  langstr ;
	 private 	String  Xtypestr ;
	 private 	String Xextendfield;
	 private 	String Xmodextfield;
	 private 	String usercode;
	 
	 private 	String filepath;
	 private 	String Xjionfield;
	public Modientry(String role, String czy, String hreft,String path)
	{
		langstr = role;
		 
		 usercode= czy;
		 filepath=path;
		setvar(hreft);

	} 
	
	
		 	public String mxEdit (HttpServletRequest request) 
	{
	try{
		 String str= getXaddsql( request ) ;
	 	  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
		 dbc.executeUpdate( str);
		 dbc.close();
		 conn.close();
		 }
		 catch (SQLException e)
		{
			
		}
		 catch (Exception e)
		{
			
		}
		

		return "操作成功";

	}

	public  String getXaddsql( HttpServletRequest request ) 
	 	{
	 	String selstr ="";
 		String valuestring ="";
	 	HashMap<String,String> hm=new HashMap<String,String>(); 
	 	String[] feildstr = new String[40];
		String[] valuestr = new String[40];
		feildstr = Xselectcolumn.split(",");
		valuestr =Xfieldtitle.split(",");
	
		for (int i= 0; i < valuestr.length;i++)
		{
			hm.put(feildstr[i], "'"+ java.net.URLDecoder.decode(request.getParameter( valuestr[i] ) )+ "'" );
		}
		 hm.put( Xjionfield, "'"+ java.net.URLDecoder.decode(request.getParameter("jionstr") )+ "'" );
		
		 if ( Xextendfield.length() > 0)
		{
			String[] extstr = new String[40];
    	extstr = Xextendfield .split(",");
			for (int i= 0; i < extstr.length;i++)
			{
				try
				{
					if (("user").equals(extstr[i].split(":")[1]))
					 hm.put(extstr[i].split(":")[0], "'"+ usercode+ "'" );
	 			else if (("role").equals(extstr[i].split(":")[1]))
				 hm.put( extstr[i].split(":")[0], "'"+ langstr+ "'" );
	 		 		else if (("getid").equals(extstr[i].split(":")[1])) 
	 		 	 hm.put( extstr[i].split(":")[0], "'"+ util.Idcode.getidcode(Xidfield, extstr[i].split(":")[2], Xsigleform, extstr[i].split(":")[3])+ "'" );
	 		 	else if (("ip").equals(extstr[i].split(":")[1])) 
	 		 	 hm.put( extstr[i].split(":")[0], "'"+ request.getRemoteAddr()+ "'" );
	 		
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
    
selstr= selstr+ entry.getKey()+",";
valuestring = valuestring  + entry.getValue () + ",";
}
selstr= selstr.substring(0, selstr.length() - 1) ;

valuestring= valuestring.substring(0, valuestring.length() - 1) ;
String str =" insert into " + Xsigleform + " (" + selstr +  ") values(" + valuestring + ")" ;
		return str;
	 
	 
	 
	 	}

	public String getXmodsql(HttpServletRequest request) throws Exception
	{
		String sql = "Update " +Xsigleform + " set " ; 
		HashMap<String,String> hm=new HashMap<String,String>(); 
	 	String[] feildstr = new String[40];
		String[] valuestr = new String[40];
		feildstr = Xselectcolumn.split(",");
		valuestr = Xfieldtitle.split(",");
	 	String  idstr=" where " + feildstr[0] + "='" + StrFun.getString(request, valuestr[0])+"' and  "+Xjionfield+"='"+ StrFun.getString(request,"jionstr") + "'" ;

		for (int i= 0; i < valuestr.length;i++)
		{
			hm.put(feildstr[i], "'"+java.net.URLDecoder.decode(request.getParameter( valuestr[i] ) )+"'");
		}
		 if (Xmodextfield.length() > 0)
		{
			String[] modextstr = new String[40];
    	modextstr = Xmodextfield.split(",");
			for (int i= 0; i < modextstr.length;i++)
			{
				try
				{
					if (("user").equals(modextstr[i].split(":")[1]))
					 hm.put( modextstr[i].split(":")[0], "'"+ usercode+ "'" );
	 			else if (("role").equals(modextstr[i].split(":")[1]))
				 hm.put( modextstr[i].split(":")[0], "'"+ langstr+ "'" );
	 		 		else if (("getid").equals(modextstr[i].split(":")[1])) 
	 		 	 hm.put( modextstr[i].split(":")[0], "'"+ util.Idcode.getidcode(Xidfield, modextstr[i].split(":")[2], Xsigleform, modextstr[i].split(":")[3]) + "'" );
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

return sql+ idstr ;
	
	}
	
	 public  String mxAdd(HttpServletRequest request) throws Exception//无主键,主键随机数,主键自增
	{
	try{
		 String str= getXaddsql( request ) ;
	  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
		dbc.executeUpdate(str );
		dbc.close();
		 conn.close();
		 
		 }
		 catch (SQLException e)
		{
			
		}
		 catch (Exception e)
		{
			
		}
		

		return "操作成功";

	}

	 public String mxDel( String id,String jionstr) throws Exception {
		try{
		String Str="Delete From "+ Xsigleform+" where "+ Xidfield +"='"+id+"' and "+Xjionfield +"='"+ jionstr +"'";
		//and bcp_Uflag=0";
		// 	System.out.print( Str );
		  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
		dbc.executeUpdate(Str);
		dbc.close();
conn.close();
}
catch (SQLException e)
		{
			
		}
		 catch (Exception e)
		{
			
		}
		

		return "操作成功";

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
				Element database = (Element)document.selectSingleNode("//" + hreft);
			
   
	Xselectcolumn = database.element("Xselectcolumn").getText().replaceAll("\\s*", ""); 
	 Xtypestr = database.element("Xtypestr").getText().replaceAll("\\s*", ""); ;
	 
	 Xfieldtitle = database.element("Xfieldtitle").getText().replaceAll("\\s*", ""); ; 
	Xsigleform = database.element("Xsigleform").getText() ;
	Xidfield = database.element("Xidfield").getText() ; ;
	 Xjionfield = database.element("Xjionfield").getText().trim() ;
			 Xextendfield = database.element("Xextendfield").getText().trim() ;
    Xmodextfield = database.element("Xmodextfield").getText().trim() ;
 
			}
			 else
		  {
		  System.out.println( realPath + " ,entry xml not exist");
		}
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}