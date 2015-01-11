//增加roleCondstr,pagesizestr
package mainview;
import Cjson.Gridprecord;
import util.S_Param;
import util.initbutton;
import util.Dbcondi;
import android.os.Environment; 
import java.io.File;

import org.dom4j.*;
import org.dom4j.io.*;
public class GridDetail 
/**
 * Title:       
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      insightsoft
 * @author:		 weizhengbin
 * @version 1.0
 */
{
	
	private String  title;
	private 	String  joinform ;
	private 	String  indextitle;
	private  String  indexfield;
 private  String  indexwidth;
	private 	String  idfield ;
	private  String conditionform;
	private  String rowcount;
	private String rolestr; 
	private String incondition;
	
	private String treecondition;
	private String conditionable;
	
	private String usercode;

	private String hrefstr;
	private int pageSize;
	 	private String langstr ;
 
	public GridDetail(String role, String userstr, String hreft,String langname )
	{
	rolestr=role;
	usercode=usercode;
		hrefstr = hreft;
		 langstr = langname; 
		 setvar(hreft);
		if (("1").equals(conditionable.trim()))  incondition = getconditionsql(role);
		else incondition = " where 1=1 ";

	} 
	public   String  getindexsql(String indexcolumn , String sigleform, String conditionstr)
	{ 
		return "SELECT " + indexcolumn + " from " + sigleform + " where " + conditionstr ; 
	}

  public String gettitlehtml()
	{
		String result = null;
		 String fielddata[]= ( indexfield ).split(",");
       String titledata[]=  (indextitle).split(",");
       String widthdata[]=  (indexwidth).split(",");
       String buttonstr="";
       for(int i=0;i<titledata.length-1;i++)
       {
          buttonstr+= "{field:\'"+ fielddata[i]+"\',"+
                      "title:\'"+ titledata[i]+"\',"+
                      "width:"+ widthdata[i]+"}"+",";
		
	     	}
	     	 buttonstr= buttonstr.trim();
		   	result ="[["+ buttonstr.substring(0, buttonstr.length()-1 )+"]]";
				
		return result;
	}
	public String getbutton(String contentstr, String role)
	{ 

		String result = null;
		result = initbutton.getbuttonstr(contentstr ,rolestr,hrefstr,langstr);
		return result;
}
	public String getfirstindex()
{
String result= getbutton("list",rolestr)+"⊙"+ gettitlehtml() +"⊙"+ idfield +"⊙"+ pageSize +"⊙"+ treecondition ;

return result;

}
	public String gettotal(String constr)
	{
		String sqlstr="select count(*) from " + joinform +
			incondition + " and " + constr ;
		String resultstr = S_Param.Getparam(sqlstr, "0");
		return resultstr;
	}


	public String getpagerecord(String pagecondstr, String constr, String pagesizestr )
	{
	 Integer it = new Integer( pagecondstr );
  int  i = it.intValue();
		Integer PageS= new Integer( pagesizestr ); 
	 int  m = PageS.intValue();
	 	
		String sqlstr="select top " + pagesizestr + " * FROM (select ROW_NUMBER()Over(order by " + idfield + " desc) as rowId, " + indexfield + " from " + joinform +
			incondition + " and " + constr + ") as #mytable " +
			" where rowId > " +( m *(i-1) ) ;
		
			String pagetotal= gettotal( constr);
		String resultstr = Gridprecord.getspagerecord(indexfield , sqlstr, pagetotal );
		return resultstr;
	}
	public String getrecord(String condstr)
	{
	 	String sqlstr="select  " + indexfield + " from " + joinform +
			incondition + " and " + condstr;
	
		 return sqlstr;
	}

	public String getconditionsql(String role)
	{
		String condistr=" where 1=1 ";
		try
		{
			String str= Dbcondi.getConditionStrSql(conditionform , role) ;
			String  tmpstr= str.split("⊙")[0];
			if (!tmpstr.equals("")) condistr = tmpstr;
		}
		catch (Exception e)
		{}
		return condistr ;
	}
	private void setvar(String hreft)
	{
		  String		 path = Environment.getExternalStorageDirectory().getPath() ;   
	 		 // String path = this.getClass().getClassLoader().getResource("").getPath();
		String realPath = path + "/griddetail.xml" ;
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
         
				Element setupxml = (Element)document.selectSingleNode("//" + hreft + "/setupxml");
			 title = database.element("title").getText().trim() ;
			joinform = database.element("joinform").getText().trim() ;
				indextitle = database.element("indextitle").getText().replaceAll("\\s*", "") ;
				indexfield = database.element("indexfield").getText().replaceAll("\\s*", "") ; ;
			 indexwidth = database.element("indexwidth").getText().trim() ;
	   	idfield = database.element("idfield").getText().trim() ;
				conditionform = database.element("conditionform").getText().trim() ;
				conditionable = database.element("conditionable").getText().trim() ;
				rowcount = database.element("rowcount").getText().trim() ;
			 treecondition=  database.element("treecondition").getText().trim() ;
				pageSize = Integer.parseInt(rowcount);
			
		  }
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}