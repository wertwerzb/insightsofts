//增加roleCondstr,pagesizestr 
package mainview;
import Cjson.Gridprecord;
import Cjson.Formjson;

import android.os.Environment; 
import java.io.File;
import Cjson.extendjson;
import org.dom4j.*;
import org.dom4j.io.*;
import util.innpath;
import util.initbutton;
import util.S_Param;
import util.Dbcondi;
import java.util.Iterator;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import Cjson.* ;

import org.apache.log4j.Logger;
/**
 * Title:       
 * Description:
 * Copyright:    Copyright (c) 2013
 * Company:  insightsoft
 * @author:		 weizhengbin
 * @version 1.0
 */
public class OuterList 
{
private String  title;
	private 	String  joinform ;
	private 	String  indextitle;
	private  String  indexfield;
 private  String  indexwidth;
	
	private  String rowcount;
	private String rolestr; 
	private String incondition;
	
	private String conditionable;
 private String fieldstyle;
	//从表中
	private String selectcolumn;
	private 	String  fieldtitle;
	private 	String  columntitle;	
	private 	String 	 idfield; 	 
	private 	String  sigleform;


	private String comparam;
	private String innerparam;
 	private String firstcom;
	 private String group ;
	 private String exterstr;
	 private String usercode;
	 	 private String langstr;
	 	 private String hrefstr;
private static final Logger logger = Logger.getLogger(OuterList.class);

	public OuterList(String role,String userstr,String hreft,String langname )
	{
	rolestr=role;
	usercode=userstr;
		hrefstr = hreft;
		 langstr = langname;
		 logger.debug("启动");
		 logger.debug( " debug " ); logger.error( " error " );
		setvar(hreft);
		 if (("1").equals(conditionable.trim()))  incondition = getconditionsql(role);
		else incondition = "";
	} 	

  public String getfirsttitle()
  
	{
	return Cjson.IndexGridjson.getgridtitle(fieldstyle,indexfield, title,indextitle,indexwidth );	}
	public String getfirstbutton(String role)
	{ 

		String result = null;

			 
		result = initbutton.getbuttonstr("outerfirst", rolestr,hrefstr,langstr );
		
		 return result;
		 
		 
		 
		}

	 public String get2init()
{   


String result="{\"button\":" +getfirstbutton(rolestr)+","+getfirsttitle()+",\"idfield\":\""+ idfield +"\", \"rowcount\":"+ rowcount+",\"formatstr\":"+  getsecondindex()+", \"exterstr\":{"+ exterstr.substring(0, exterstr.length()- 1) + "}, \"innerparam\":"+ innerparam+",\"firstcom\":"+ firstcom+ "}" ;


return result ;


}
	public String gettotal(String constr)
	{
	 replcerol( );
		String sqlstr="select count(*) from " + joinform +
			incondition + " and " + constr ;
			
		String resultstr = S_Param.Getparam(sqlstr, "0");
		return resultstr;
	}

	public String getpagesql(HttpServletRequest request) throws Exception 
	{
	 replcerol( );
	 		String constr = request.getParameter("condstr");
		        String pagecondstr = request.getParameter("page");
       String pagesizestr = request.getParameter("rows");
	 Integer it = new Integer( pagecondstr );
  int  i = it.intValue();
		Integer PageS= new Integer( pagesizestr ); 
	 int  m = PageS.intValue();
	 	
		String sqlstr="select top " + pagesizestr + " * FROM (select ROW_NUMBER()Over(order by " + idfield + " desc) as rowId, " + indexfield + " from " + joinform +
			incondition + " and " + constr + ") as #mytable " +
			" where rowId > " +( m *(i-1) ) ;
		
		return sqlstr ;
	}
	public String getpagerecord (HttpServletRequest request) throws Exception 
	{
	 replcerol( );
	 		String constr = request.getParameter("condstr");
			String pagetotal= gettotal( constr);
			String sqlstr= getpagesql ( request);
		String resultstr = Gridprecord.getspagerecord(indexfield , sqlstr, pagetotal );
		return resultstr;
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
	String getmx=""+getmxtotal();
 String result=Cjson.FormInitjson.getFormtitle(fieldtitle,columntitle, comparam,group,getmx);
return result;

	}
	
		public String getmxbutton( String role)
	{ 

		String result = initbutton.getbuttonstr("outersecond", rolestr,hrefstr, langstr );
		return result;
	}
	public String getvar()
	{
		String result="";
		
		if( innerparam==null ) 
		 result= result+"innerparam is null ";
		 		if( firstcom==null ) 
		 result= result+"firstcom is null ";
		 if( sigleform ==null ) 
		 result= result+" sigleform is null "; 
		 		 if( joinform ==null ) 
		 result= result+" joinform is null "; 
		 		 if( title ==null ) 
		 result= result +" title is null "; 
		 
		 		 if( indextitle ==null ) 
		 result= result +" indextitle is null "; 
		 		 		 if( indexfield ==null ) 
		 result= result +" indexfield is null "; 
		 		 		 if( indexwidth ==null ) 
		 result= result +" indexwidth is null "; 
		 		 		 		 if( rowcount ==null ) 
		 result= result +" rowcount is null "; 
	 		 if( conditionable ==null ) 
		 result= result +" conditionable is null "; 
		 
	 		 if( fieldstyle ==null ) 
		 result= result +" fieldstyle is null "; 
		 	 		 if( selectcolumn ==null ) 
		 result= result +" selectcolumn is null "; 
		 	 		 if( fieldtitle ==null ) 
		 result= result +" fieldtitle is null "; 		 	 		 if( columntitle ==null ) 
		 result= result +" columntitle is null "; 
		 if( idfield ==null ) 
		 result= result +" idfield is null "; 	
if( comparam ==null ) 
		 result= result +" comparam is null "; 	

if( group ==null ) 
		 result= result +" group is null "; 	
		 if( exterstr ==null ) 
		 result= result +" exterstr is null "; 	

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

String 	realPath=innpath.getxmlpath( rolestr,usercode,"/Mouter//"+ hreft+".xml" );


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
				 //document.selectSingleNode("//" + hreft+"//extendxml");
				 exterstr="";
				 for(Iterator ipe = extendxml.elementIterator(); ipe.hasNext();){
  Element e_pe = (Element) ipe.next();
    exterstr+= "\""+e_pe.getName()+"\":\""+ e_pe.getText()+"\","; 
           
}
			 title = database.element("title").getText().trim() ;
			joinform = database.element("joinform").getText().toUpperCase(); 
			 	 if( ( joinform.indexOf("S_")>0)&&(  realPath.indexOf( "/"+usercode )>0))
		
		 	{			
		 	
		 	 joinform = "DDD";
		 	System.out.print( "访问系统xml:"+realPath+"#"+ joinform );
		 	
		 }
		
				indextitle = database.element("indextitle").getText().replaceAll("\\s*","") ;
				indexfield = database.element("indexfield").getText().replaceAll("\\s*","") ;
			 indexwidth = database.element("indexwidth").getText().trim() ;
				conditionable = database.element("conditionable").getText().trim() ;
				rowcount = database.element("rowcount").getText().trim() ;

		  fieldstyle= database.element("fieldstyle").getText().trim() ;
				selectcolumn = database.element("selectcolumn").getText().replaceAll("\\s*", "") ;
				fieldtitle = database.element("fieldtitle").getText().replaceAll("\\s*", "") ;
				columntitle = database.element("columntitle").getText().replaceAll("\\s*", "") ;
     
	    group = database.element("group").getText().trim() ;
				sigleform = database.element("sigleform").getText().trim() ; 
			 
				
				idfield = database.element("idfield").getText().trim() ;
		comparam = database.element("comparam").getText().trim() ;
		 firstcom = database.element("firstcom").getText().trim() ;
		 
		 innerparam = database.element("innerparam").getText().trim() ;
			
			}
			 else
		  {
		  System.out.println( " realPath :"+realPath + "out xml not exist");
		  }
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}