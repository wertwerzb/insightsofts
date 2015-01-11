//增加roleCondstr,pagesizestr
package mainview;


import android.os.Environment; 
import java.io.File;

import org.dom4j.*;
import org.dom4j.io.*;
import util.innpath;
import util.initbutton;
import util.S_Param;
import util.Dbcondi;
import Cjson.Gridprecord;
import Cjson.extendjson;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;


/**
 * Title:       
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      insightsoft
 * @author:		 weizhengbin
 * @version 1.0
 */
public class TianbaList 
{
 private  String  Xselectcolumn;
	private String  Xcolumntitle;
	private 	String  Xfieldtitle;
	private 	String  jionform;
	private 	String Xfieldwidth;
	private 	String Xgroupfield;
	private 	String Xrowcount;
 private 	String Xorderfield;
 
	private String conditionable;
 private String  selectcolumn;
	private 	String  fieldtitle;
	private 	String  columntitle;	
	 
	private 	String 	 idfield; 	
	 private String fieldstyle; 
	private 	String  sigleform;
	
	 private  String comparam ;
	private String exterstr;
	 private 	String rolestr;
	 
	 private 	String usercode;
	 private 	String incondition;
	 
	 private String firstcom;
 	private String exterparam;
 	 private String hrefstr;
 	 private String langstr;
 	 private String title;
 	 
	public TianbaList(String role, String usercode, String hreft,String langname )
	{
	rolestr=role;
	usercode=usercode;
		hrefstr = hreft;
		 langstr= langname;
		setvar(hreft);
		 if (("1").equals(conditionable.trim()))  incondition = getconditionsql(role);
		else incondition = "";
		 
	} 
	
	
	public int getMtotal()
	{
		int i= fieldtitle.split(",").length;
		int j= columntitle.split(",").length;
		int result;
		if (i == j) result = i;
		else result = -1;
		return result ;
	}
	public String getMfirstindex()
	{
	
	 	String getmx=""+getMtotal();
	 return Cjson.FormInitjson.getFormtitle(fieldtitle,columntitle,comparam,getmx);
	
	/*	String result = null;
		String fielddata[]= (fieldtitle).split(",");
		String titledata[]=  (columntitle).split(",");
//		String comdata[]=  (comname).split(",");
		String paramdata[]= (comparam).split("⊙");
		String resultstr="";
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

		 	int groupindex=Integer.parseInt(title[1].substring(0, 1));
			resultstr += "{\"field\":\"" + fielddata[i].trim() + "\",\n" +
				placeholder + 
				//formaterstr +
				"\"grouprow\":\"" + title[1].trim() + "\",\n" +
				"\"name\":\"" + title[0].trim() + "\",\n" ;
			if (title[2].indexOf("-") < 1)
			{ 
				comnames = title[2] ;
				resultstr += "\"editor\":\"" + comnames + "\"},";
			}
			else
			{
				String comnamestr[] = title[2].split("-");
				comnames = comnamestr[0];
				resultstr += "\"editor\":{\"type\":\"" + comnames + "\"," ;
				int j=Integer.parseInt(comnamestr[1]);
				//if (paramdata[j].indexOf("@") < 1) {
					paramdatastr = paramdata[j];
					resultstr += "\"options\":{" + paramdatastr + "}}}\n,";
			
			}
		}
		result = "{\"total\":" + getMtotal() + ",\"rows\":[" + resultstr.substring(0, resultstr.length() - 1) + "]}";

		return result;
		*/
	}
	public String getbutton()
	{ 

		String result = null;

		result = initbutton.getbuttonstr("tianba" ,rolestr,hrefstr,langstr);
		return result;
	}

	public String  getXpageSql (HttpServletRequest request) throws Exception 	{

	
String page = request.getParameter("page");
String pagesizestr= request.getParameter("rows");
	 	
	 String constr = request.getParameter("condstr");
	 	
		Integer it = new Integer(page);
		int  i = it.intValue();
		Integer PageS= new Integer(pagesizestr); 
		int  m = PageS.intValue();

		String sqlstr="select top "
			+ pagesizestr + " * FROM (select ROW_NUMBER()Over(order by " + Xorderfield + " desc) as rowId, " + Xselectcolumn + " from " + jionform +
			" and " + Xgroupfield + "='"+ constr+ "' ) as #mytable " +
			" where rowId > " + (m * (i - 1)) ;
			 return sqlstr ;
	}

	public String getXtotal(String constr)
	{
		String sqlstr="select count(*) from " + jionform +incondition  + " where "+ Xgroupfield + "='"+ constr+ "'" ;
	String resultstr = S_Param.Getparam(sqlstr, "0");
	return resultstr;
	 //	 return sqlstr;
	}
 public String getconditionsql(String role)
	{
		String condistr="";
		try
		{
			String str= Dbcondi.getConditionStrSql(hrefstr, role) ;
			String  tmpstr= str.split("⊙")[0];
			if (!tmpstr.equals("")) condistr = tmpstr;
		}
		catch (Exception e)
		{}
		return condistr ;
	}


	public String getXtitlehtml()
	{
	 return Cjson.IndexGridjson.getgridtitle(fieldstyle, Xfieldtitle , title, Xcolumntitle , Xfieldwidth );	
	
		/*String result = null;
	
			String fielddata[]= (Xfieldtitle).split(",");
			String titledata[]=  (Xcolumntitle).split(",");
			String widthdata[]=  (Xfieldwidth).split(",");
		
			String buttonstr="";
	 int froint,finint;
	 String frostring="";
	String[] frocols= fieldstyle.split(":");
   froint=Integer.parseInt( frocols[0] );
	 finint =Integer.parseInt ( frocols[1] );
			for (int i= froint ;i < finint ;i++)
			{

				String paramdatastr="";
				String comnames= "";

				String formatstr="";
				String titstr;
				if (titledata[i].indexOf(":") > 0)
				{ 
					formatstr = "\"formatter\":" + titledata[i].split(":")[1] + ",";
					titstr = titledata[i].split(":")[0];
				}
				else
				{
					formatstr = "";
					titstr = titledata[i];
				}
				buttonstr += "{\"field\":\"" + fielddata[i] + "\"\n," +
					"\"title\":\"" + titstr + "\",\n" +
					"\"width\":\"" + widthdata[i]+"\"},";
					
				}
			

			buttonstr = buttonstr.trim();
			result = "[[" + buttonstr.substring(0, buttonstr.length() - 1) + "]]";
		
		return result;
		*/
	}
	 	public String getXpagerecord (HttpServletRequest request) throws Exception 	{
	String  sqlstr= getXpageSql (request) 	;
	 String constr = request.getParameter("condstr");
	
	 	String pagetotal= getXtotal(constr);
		String resultstr = Gridprecord.getspagerecord(Xfieldtitle , sqlstr, pagetotal);
		return resultstr;
	}
	
	 	public String getinithtml()
	{
		String result="{\"button\":"+getbutton() + ","+ getXtitlehtml() + ",\"formatstr\":" + getMfirstindex() + ",\"otherinfo\":"+ getother() +",\"exterstr\":" +
"{"+ exterstr.substring(0, exterstr.length() - 1) + "}}" + extendjson.getextendjson( firstcom, exterparam ) ; 

		return result;

	}
	
	 	public String getother()
	{
		String result= "{\"Xrowcount\":"+Xrowcount +"}";

		return result;

	}

	private void setvar(String hreft)
	{
	   String path = this.getClass().getClassLoader().getResource("").getPath();
 System.out.print( path );

		 String 	  realPath= innpath.getxmlpath( rolestr,usercode,"/Mtianba/"+ hreft+".xml" );
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
				
         Attribute lang = database.attribute("lang");
         langstr= lang.getValue();
         
	 if(langstr==null)
	 langstr="zh_CN";
				 			 Element extendxml = (Element)document.selectSingleNode("//" + hreft+"//extendxml");
	exterstr="";
				 for(Iterator ipe = extendxml.elementIterator(); ipe.hasNext();){
  Element e_pe = (Element) ipe.next();
    exterstr+= "\""+e_pe.getName()+"\":\""+ e_pe.getText()+"\","; 
           
}
		Xselectcolumn = database.element("Xselectcolumn").getText().replaceAll("\\s*","") ;
				Xfieldtitle = database.element("Xfieldtitle").getText().replaceAll("\\s*", "") ;
				Xcolumntitle = database.element("Xcolumntitle").getText().replaceAll("\\s*", "") ;
				Xfieldwidth = database.element("Xfieldwidth").getText().trim() ;
				jionform = database.element("jionform").getText().toUpperCase();
				 			 	 if( ( jionform.indexOf("S_")>0)&&(  realPath.indexOf( "/"+usercode )>0)) 	 	{			
		 	 jionform = "DDD";
		 	System.out.print( "访问系统xml:"+realPath+"#"+ jionform );
		 	
		 }
	
				Xorderfield = database.element("Xorderfield").getText().trim() ;
		 	 Xgroupfield = database.element("Xgroupfield").getText().trim() ;
		
				Xrowcount = database.element("Xrowcount").getText().trim() ;
	
				selectcolumn = database.element("selectcolumn").getText().replaceAll("\\s*", "") ;
				fieldtitle = database.element("fieldtitle").getText().replaceAll("\\s*", "") ;
				columntitle = database.element("columntitle").getText().trim() ;
				title= database.element("title").getText().trim() ;
				
				conditionable = database.element("conditionable").getText().trim() ;

				sigleform = database.element("sigleform").getText() .toUpperCase(); 
			 	 if( ( sigleform.indexOf("S_")>0)&&(  realPath.indexOf( "/"+usercode )>0)) 	{			
		 	 sigleform = "DDD";
		 	System.out.print( "访问系统xml:"+realPath+"#"+ sigleform );
		 	
		 }
	
				idfield = database.element("idfield").getText().trim() ;
				comparam = database.element("comparam").getText().trim() ;
				 fieldstyle= database.element("fieldstyle").getText().trim() ;

				 firstcom = database.element("firstcom").getText().trim() ;
		 exterparam = database.element("exterparam").getText().trim() ;
		}			
		 else
		  {
		  System.out.println( hreft+ "tianbaxml not exist");
		  }
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}