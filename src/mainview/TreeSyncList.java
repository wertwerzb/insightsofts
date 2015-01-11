//增加roleCondstr,pagesizestr
package mainview;
import Cjson.Treejson;
import Cjson.Formjson;
import android.os.Environment; 
import java.io.*;
import java.util.*;
import util.initbutton;
import util.Idcode;
import util.S_Param;
import util.innpath;

import Cjson.extendjson;
import Cjson.Treejson;
import org.dom4j.*;
import org.dom4j.io.*;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;


/**
 * Title:       
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      insightsoft
 * @author:		 weizhengbin
 * @version 1.0
 */
public class TreeSyncList 
{
	private  String  selectcolumn;
	private String  columntitle;
	private 	String  fieldtitle;
	private 	String  sigleform;
	private 	String  idfield ;
	private  String  namefield;
	private  String  comname ;
	private 	String  comparam;
	private 	String fieldwidth;
	private 	String hrefstr;
	 private 	String usercode;
	 private 	String curnode;
	 private 	String group;
	 private 	String joinform;
	 private 	String secondcom;
	
	 
	 private 	String rolestr;
	 private 	String addidmodal;
	 private String exterstr;
 
 private String langstr;
private String firstcom;
 	private String exterparam;
	
 
 
	public TreeSyncList( String role,String userstr, String hreft,String langname )
	{
	rolestr=role;
	usercode=usercode;
		hrefstr = hreft;
		 langstr = langname;
		setvar(hreft);

	} 

private String getbutton()
	{ 

		String result = null;

		result = initbutton.getbuttonstr("tree", rolestr,hrefstr,langstr);
		return result;
}



	private String  getSelectSql()
	{ 
		String resultstr = "select " + selectcolumn + " from " + sigleform + " where "+ idfield+ " like '"+curnode+"%' order by " + idfield ;
		 	return replcerol( resultstr ); 
	
	}

	private String gettotal()
	{
		String sqlstr="select count(*) from " + sigleform +" where "+ idfield +" like '"+curnode+"%'" ;
		 sqlstr= replcerol( sqlstr ); 
	
		String resultstr = S_Param.Getparam(sqlstr, "0");
		return resultstr;
	}


	public String getsteprecord (HttpServletRequest request) throws Exception 	{
	
String codeId= request.getParameter("id");
if( codeId==null )
   codeId="0";
		String feilstr= selectcolumn ;
		int len= codeId.length();
		String slen= String.valueOf(len + 3); 
		String sql="";
		if (len == 1)
			sql = "select " + feilstr + " from " + sigleform + " where DATALENGTH (" + idfield + ")<3 order by " + idfield + " asc ";
		else
			sql = "select " + feilstr + " from " + sigleform + " where DATALENGTH ( " + idfield + ")<" +
				slen + " and " + idfield + "!= '" + codeId + "' and " + idfield + " like '" + codeId + "%' order by " + idfield + " asc";  //定义SQL语句

		String resultstr = Treejson.getsimptreerecord(sql);
		return resultstr ;
	}

	private String gettitlehtml()
	{

		String result = null;
	
		    String fielddata[]= (fieldtitle).split(",");
			String titledata[]=  (columntitle).split(",");
			String widthdata[]=  (fieldwidth).split(",");
			String comdata[]=  (comname).split(",");
			String paramdata[]= (comparam).split("⊙");

			String buttonstr="";
			for (int i=0;i < titledata.length;i++)
			{
			
				String paramdatastr="";
				String comnames= "";
				
			  String formatstr="";
			  String titstr;
			if( titledata[i].indexOf(":") >0 )
			{ 
			  formatstr= "\"formatter\":\""+titledata[i].split(":")[1]+"\",";
			 titstr= titledata[i].split(":")[0];
			}
			else
			{
			 formatstr="";
			 titstr= titledata[i];
			 }
				buttonstr += "{\"field\":\"" + fielddata[i] + "\"," +
					"\"title\":\"" + titstr + "\"," +
					"\"width\":" + widthdata[i]  + "," + formatstr ;
				if (comdata[i].indexOf("-") < 1)
				{ 
					comnames = comdata[i];
					buttonstr += "\"editor\":\"" + comnames + "\"},";
				}
				else
				{
					String comnamestr[] = comdata[i].split("-");
					comnames = comnamestr[0];
					buttonstr += "\"editor\":{\"type\":\"" + comnames + "\"," ;
					int j=Integer.parseInt(comnamestr[1]);
				//	if (paramdata[j].indexOf("@") < 1) 	{
						paramdatastr = paramdata[j];
						buttonstr += "\"options\":{" + paramdatastr + "}}},";
				
      }
			}
			
			buttonstr = buttonstr.trim();
		 result = "[[" + buttonstr.substring(0, buttonstr.length() - 1) + "]]";
	
		return result;
	}
	 private String replcerol(String sql )
	{
if( sql.indexOf("{R}")>0)
return sql.replace("{R}", rolestr );
else return sql;
	}
	
public String getinithtml()
	{
		String result= "{\"columns\":"+gettitlehtml() + ",\"idfield\":\""  + idfield+ "\",\"namefield\":\""+ namefield + "\",\"button\":"+ getbutton() +"," +
"\"exterstr\":{"+ exterstr.substring(0, exterstr.length() - 1) + "}}" + extendjson.getextendjson( firstcom, exterparam ) ; 

return result ;


	}
	
public  String getaddid (HttpServletRequest request) throws Exception 	{
	
String fontstr = request.getParameter("pid");

		int j= Integer.parseInt( addidmodal );
		String st="";
	try
				{
				 	if (j >0) st = Idcode.getlcode( sigleform, idfield , fontstr  ) ;
				

				}
				catch (Exception e)
				{}  
		


		return st;
	}
 
	public int getmxtotal()
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
	public String getidjsoin (HttpServletRequest request) throws Exception 
	{
	String Id= request.getParameter("idstr");
		String sql= getidsql(Id) ;
		String resultstr = Formjson.getsidjsoin(fieldtitle,sql);
		return resultstr;

	}
	
	
	public String getidrecord(String ids)
	{
	
		String sqlstr= getidsql(ids);
		int total= getmxtotal();
		String resultstr = Formjson.getsidrecord(total, sqlstr);
		return resultstr;
	}

	public String getsecondindex()
	{

		  	String result = null;
		String fielddata[]= (fieldtitle).split(",");
		String titledata[]=  (columntitle).split(",");
//		String comdata[]=  (comname).split(",");
		String paramdata[]= (comparam).split("⊙");
		 	String groupdata[]= group.split("⊙");
		String buttonstr="";
		String placeholder="";
		for (int i=0;i < titledata.length;i++)
		{
		
			String paramdatastr="";
		 	String formaterstr="";
		 	
			String comnames= "";
			String title[]= titledata[i].split(":");
			if( title.length>3 ){
			placeholder="\"placeholder\":\""+ title[3]+"\",\n";
			} 
			else placeholder="" ;

		 	int groupindex=Integer.parseInt( title[1].substring(0,1) );
		 String groupstr= groupdata[groupindex];
			buttonstr += "{\"field\":\"" + fielddata[i].trim() +"\",\n"+
				 "\"group\":\"" + groupstr.trim() + "\",\n" +
				 placeholder+ 
				 //formaterstr +
				 "\"grouprow\":\"" + title[1].trim() +"\",\n" +
			 "\"name\":\"" + title[0].trim() + "\",\n" ;
			if ( title[2].indexOf("-") < 1)
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
		result = "{\"total\":" + getmxtotal() + ",\"rows\":[" + buttonstr.substring(0, buttonstr.length() - 1) + "]}";
		
		return result;
	}
	
		public String getmxbutton(String contentstr, String role)
	{ 

		String result = initbutton.getbuttonstr("outersecond", rolestr,hrefstr, langstr );
		return result;
	}
	public String getsecondinit()
	{
		String result= "{\"mxbutton\":"+getmxbutton("mxlist", rolestr ) +",\"formatstr\":"+  getsecondindex()+ ",\"exterstr\":" + 
"{"+ exterstr.substring(0, exterstr.length() - 1) + "}}" + extendjson.getextendjson( secondcom, exterparam ); 
return result;

	}
private void replcerol( )
	{
if( joinform.indexOf("{U}")>0)
 joinform = joinform.replace("{U}", usercode );
if( joinform.indexOf("{R}")>0)
 joinform = joinform.replace("{U}",rolestr );
		
	}

 	private void setvar(String hreft)
	{
		 //String		 path = Environment.getExternalStorageDirectory().getPath() ;   

//treeconfig.xml" ;


String 	 realPath= innpath.getxmlpath( rolestr,usercode,"/treesync/"+ hreft+".xml" );
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
				
				 Element extendxml = database.element("extendxml");
	exterstr="";
				 for(Iterator ipe = extendxml.elementIterator(); ipe.hasNext();){
  Element e_pe = (Element) ipe.next();
    exterstr+= "\""+e_pe.getName()+"\":\""+ e_pe.getText()+"\","; 
           
}
	
				selectcolumn = database.element("selectcolumn").getText().replaceAll("\\s*", "") ; ;
				fieldtitle = database.element("fieldtitle").getText().replaceAll("\\s*", "") ;
				columntitle = database.element("columntitle").getText().replaceAll("\\s*", "") ;
				fieldwidth = database.element("fieldwidth").getText().trim() ;

				sigleform = database.element("sigleform").getText().toUpperCase(); 
			 	 if( ( sigleform.indexOf("S_")>0)&&(  realPath.indexOf( "/"+usercode )>0)) 	{			
		 	 sigleform = "DDD";
		 	System.out.print( "访问系统xml:"+realPath+"#"+ sigleform );
		 	
		 }
	 	 
				idfield = database.element("idfield").getText().trim() ;
				comname = database.element("comname").getText().trim() ; ;
				namefield = database.element("namefield").getText().trim() ;
				comparam = database.element("comparam").getText().trim() ;

addidmodal = database.element("addidmodal").getText().trim();
curnode= database.element("curnode").getText().trim();
				 firstcom = database.element("firstcom").getText().trim() ;
		 exterparam = database.element("exterparam").getText().trim() ; 	 
		 	 if( ( joinform.indexOf("S_")>0)&&(  realPath.indexOf( "/"+usercode )>0))
		
		 	{			
		 	
		 	 joinform = "DDD";
		 	System.out.print( "访问系统xml:"+realPath+"#"+ joinform );
		 	
		 }
		
secondcom = database.element("secondcom").getText().trim() ; 
	    group = database.element("group").getText().trim() ;
			}
			
			 else
		  {
		  System.out.println( realPath+ " not exist");
		  }
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}