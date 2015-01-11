//增加roleCondstr,pagesizestr
package mainview;
import util.S_Param;
import Cjson.Gridprecord;
import android.os.Environment; 
import java.io.*;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;
import util.innpath;
import Cjson.extendjson;
import util.initbutton;

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
public class DetailList 
{
	private  String  selectcolumn;
	private String  columntitle;
	private 	String  sigleform;
	 private 	String  idfield ;
	private  String  namefield;
	private  String  comname ;
	private 	String  comparam;
	private 	String fieldwidth;
	 private 	String hrefstr;
 private 	String rowcount;
 
 
	private String title;
	private 	String  fieldtitle;
	 private String exterstr;
	private String rolestr;
	 private String usercode;
	 private String firstcom;
 	private String exterparam;
	private String fieldstyle;
	private String langstr;
	public DetailList(String role, String usercode, String hreft, String langname )
	{
	rolestr=role;
	usercode=usercode;
		hrefstr = hreft;
		 langstr = langname;
		setvar(hreft);

	} 
public String getbutton()
	{ 

	String	result = initbutton.getbuttonstr("detail" ,rolestr,hrefstr,langstr);
		return result;
}


	public String  getSelectSql( String contentstr )
	{ 
		String resultstr = "select " + selectcolumn + " from " + sigleform + " where "+ contentstr +" order by " + idfield ;
		return resultstr;
	}

	public String gettotal(String constr )
	{
		String sqlstr="select count(*) from " + sigleform +" where " +constr ;
		String resultstr = S_Param.Getparam(sqlstr, "0");
		return resultstr;
	}

	
		public String gettitlehtml()
	{
return Cjson.IndexGridjson.getgridtitle(fieldstyle,fieldtitle, title, columntitle , fieldwidth );	
	/*	
	int froint,finint;
	 String frostring="";
	String[] frocols= fieldstyle.split(":");
   froint=Integer.parseInt( frocols[0] );
	 finint =Integer.parseInt ( frocols[1] );
	 	String firstrow="";
	int cols= fieldtitle.split(",").length; 
		String result = null;
		
		if ( title.indexOf("⊙")>0) 
		{	
		 String[] firstr=title.split("⊙");
		 firstrow= "[";
		 for (int i=0;i < firstr.length;i++)
				{
				 	 
		 firstrow += "{\"title\": \""+ firstr[i].split(":")[0] +"\",\n \"colspan\":"+ firstr[i].split(":")[1] + "},";
		 }
		 
		 firstrow= firstrow.substring(0,firstrow.length()-1 )+"],";
		}
		else
		{ 
		 firstrow= "[{\"title\":\" "+title+"\", \n \"colspan\":"+ cols+ "}],";
			}
			
 String fielddata[]= ( fieldtitle ).split(",");
       String titledata[]=  ( columntitle ).split(",");
       String widthdata[]=  ( fieldwidth ).split(",");
       String buttonstr="";
       if( froint>0 )
       {
       for(int i= 0;i<froint;i++)
       {
          frostring += "{\"field\":\""+ fielddata[i]+"\",\n \"title\":\""+ titledata[i]+"\"\n,"+
                     "\"width\":"+ widthdata[i]+"}"+",";
		
	     	}
	 
       
       }
       for(int i= froint;i<finint;i++)
       {
          buttonstr+= "{\"field\":\""+ fielddata[i]+"\" \n, \"title\":\""+ titledata[i]+"\",\n "+
                      "\"width\":"+ widthdata[i]+"}"+",";
		
	     	}
	     	 buttonstr= buttonstr.trim();
		  if( frostring.length()>0 )
		  result ="\"frocolumns\":["+ firstrow+ "["+ buttonstr.substring(0, buttonstr.length()-1 )+"]],\"columns\":[["+ frostring.substring(0,frostring.length()-1 )+ "]]";
				
		  else 	result ="\"columns\":["+ firstrow+ "["+ buttonstr.substring(0, buttonstr.length()-1 )+"]]";
				
		return result;
		*/
	}
public String getinit()
	{
		String result= "{"+gettitlehtml() +",\"idfield\":\"" + idfield + "\", \"namefield\":\""+ namefield + "\",\"rowcount\":"+ rowcount + ",\"button\":"+ getbutton() +",\"exterstr\":" +
"{"+ exterstr.substring(0, exterstr.length() - 1) + "}} " + extendjson.getextendjson( firstcom, exterparam ) ; 

return result ;


	}
public String getpagerecord (HttpServletRequest request) throws Exception 
	{

String  constr = request.getParameter("condstr");
	
		String sqlstr= getpagesql (request) ;
		String pagetotal= gettotal( constr);
		String resultstr = Gridprecord.getspagerecord( fieldtitle,sqlstr, pagetotal );
		return resultstr;
	}
	 public String getpagesql (HttpServletRequest request) throws Exception 
	{
String page = request.getParameter("page");
String pagesizestr= request.getParameter("rows");
String  constr = request.getParameter("condstr");
	 Integer it = new Integer( page );
  int  i = it.intValue();
		Integer PageS= new Integer( pagesizestr ); 
	 int  m = PageS.intValue();
	 	
		String sqlstr="select top "
		+ pagesizestr + " * FROM (select ROW_NUMBER()Over(order by " + idfield + " desc) as rowId, " + selectcolumn + " from " + sigleform +
			 " where " + constr + ") as #mytable " +
			" where rowId > " +( m *(i-1) ) ;
		return sqlstr;
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

 if( sql.indexOf("{P}")>0)
resultstr= resultstr.replace("{P}", prole);

  return resultstr;
	}

	private void setvar(String hreft)
	{

String 	  realPath= innpath.getxmlpath( rolestr,usercode,"/Mdetailview/"+ hreft+".xml" );
	
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
    exterstr+= "\""+e_pe.getName()+"\":"+ e_pe.getText()+","; 
           
}
	
				selectcolumn = replcerol( database.element("selectcolumn").getText()) ; 
				fieldtitle = database.element("fieldtitle").getText().replaceAll("\\s*", "") ;
				columntitle = database.element("columntitle").getText().replaceAll("\\s*", "") ; ;
				fieldwidth = database.element("fieldwidth").getText().trim() ;
      sigleform = database.element("sigleform").getText().trim() ; 			 	
      
       if( ( sigleform.indexOf("S_")>0)&&(  realPath.indexOf( "/"+usercode )>0)) 	 	{			
		 	 sigleform = "DDD";
		 	System.out.print( "访问系统xml:"+realPath+"#"+ sigleform );
		 	
		 }
		
				idfield = database.element("idfield").getText().trim() ;

				comname = database.element("comname").getText().trim() ; 
		
			 title = database.element("title").getText().trim() ;
		  fieldstyle= database.element("fieldstyle").getText().trim() ; 	
				namefield = database.element("namefield").getText().trim() ;
				comparam = database.element("comparam").getText().trim() ;
		  	rowcount = database.element("rowcount").getText().trim() ;
		 
				 firstcom = database.element("firstcom").getText().trim() ;
		 exterparam = database.element("exterparam").getText().trim() ; 	
		
		
			}
			 else
		  {
		  System.out.println( hreft+ "grid xml not exist");
			}
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}