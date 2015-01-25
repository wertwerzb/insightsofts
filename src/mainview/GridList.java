//增加roleCondstr,pagesizestr
 package mainview;
import contrl.*;
import android.os.Environment; 
import java.io.*;
import java.util.*;
import util.innpath;
import util.initbutton;
import util.S_Param;
import Cjson.Gridprecord;

import Cjson.extendjson;
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
public class GridList
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
 private 	String rowcount;
 private String exterstr;
	private String rolestr;
	 private String usercode;
	 private String firstcom;
 	private String useflag;
 	 private String langstr;
	public GridList(String role, String usercode, String hreft,String langname )
	{
	rolestr=role;
	usercode=usercode;
		hrefstr = hreft;
		 langstr = langname;
		setvar(hreft);

	} 
private String getbutton()
	{ 


		String result = initbutton.getbuttonstr("grid" ,rolestr,hrefstr,langstr);
		return result;
}


	private String  getSelectSql( String contentstr )
	{ 
		String resultstr = "select " + selectcolumn + " from " + sigleform + " where "+ contentstr +" order by " + idfield ;
		return resultstr;
	}

	private String gettotal(String constr )
	{
		String sqlstr="select count(*) from " + sigleform +" where " +constr ;
		String resultstr = S_Param.Getparam(sqlstr, "0");
		return resultstr;
	}

	public String getrecord (HttpServletRequest request ) 
	{
	 String contentstr = request.getParameter("condstr");
	
		String pagetotal= gettotal( contentstr );
   String 	sql = getSelectSql( contentstr );
	 String resultstr = Gridprecord.getsrecord(fieldtitle, sql, pagetotal);
		return resultstr;
	}
		public String gettitlehtml()
	{	return Cjson.EditGridInit. getgridtitle (fieldtitle, columntitle,fieldwidth,comname,comparam ) ;
	 	}
public String getinithtml()
	{
		String result= "{\"columns\":"+gettitlehtml() +",\"idfield\":\"" + idfield + "\", \"namefield\":\""+ namefield + "\",\"rowcount\":"+ rowcount + ",\"button\":"+ getbutton() +",\"exterstr\":" +
"{"+ exterstr.substring(0, exterstr.length() - 1) + "},\"firstcom\":"+ firstcom+"}"; 

return result ;


	}
	 public String getpagesql(HttpServletRequest request) throws Exception 

	{
	 String constr = request.getParameter("condstr");
		        String page= request.getParameter("page");
       String pagesizestr = request.getParameter("rows");
	
		 constr = 	 replcerol( constr );
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
public String getpagerecord (HttpServletRequest request) throws Exception 

	{
	 String constr = request.getParameter("condstr");
		 
		 constr = 	 replcerol( constr );
		String sqlstr=getpagesql(request) ;
		 	String pagetotal= gettotal( constr);
		String resultstr = Gridprecord.getspagerecord( fieldtitle ,sqlstr, pagetotal );
		return resultstr;
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

String 	  realPath= innpath.getxmlpath( rolestr,usercode,"/Meditgrid/"+ hreft+".xml" );
	
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
	
				selectcolumn = database.element("selectcolumn").getText().replaceAll("\\s*", "") ; 
				fieldtitle = database.element("fieldtitle").getText().replaceAll("\\s*", "") ;
				columntitle = database.element("columntitle").getText().replaceAll("\\s*", "") ; ;
				fieldwidth = database.element("fieldwidth").getText().trim() ;
      sigleform = database.element("sigleform").getText().toUpperCase(); ;
			 	 if( ( sigleform.indexOf("S_")>0)&&(  realPath.indexOf( "/"+usercode )>0)) 	{			
		 	 sigleform = "DDD";
		 	System.out.print( "访问系统xml:"+realPath+"#"+ sigleform );
		 	
		 }
		
				idfield = database.element("idfield").getText().trim() ;
				comname = database.element("comname").getText().trim() ; 
			
				namefield = database.element("namefield").getText().trim() ;
				comparam = database.element("comparam").getText().trim() ;
		  	rowcount = database.element("rowcount").getText().trim() ;
		 
				 firstcom = database.element("firstcom").getText().trim() ;
		 useflag = database.element("useflag").getText().trim() ; 	
		
		
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