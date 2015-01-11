//打开一个可多选的表,并将选择内容插入到新表中.
package partview;
import Cjson.Gridprecord;
import android.os.Environment; 
import java.io.File;

import org.dom4j.*;
import org.dom4j.io.*;
 import java.net.URLDecoder;
	
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import util.innpath;
import util.S_Param;
public class ChoicePutin 
{
	private String  title;
private String searchbox;
private String indexfield;
private String indextitle; 
private String outfield; 	  
private String injoinform;	  	 
 private String outsigleform;
private String indexwidth;
private String idfield;
private String rowcount;
private int ableputin; //如果0全部如果为1部分记录

private String outidfield ;
private String rolestr;
private String usercode;

	 	 private String langstr;
public ChoicePutin(String role, String userstr, String hreft,String langName)
	{
	usercode=userstr;
		rolestr = role;
		langstr= langName;
		setvar(hreft);
} 

	public   String  getindexsql (String condstr)
	{
	 	String sqlstr="select  " + indexfield + " from " + injoinform +" and "+ condstr ;
	
		return sqlstr ; 
	}

  public String gettitlehtml()
	{
		String result = null;
	
		    String fielddata[]= ( indexfield ).split(",");
       String titledata[]=  (indextitle).split(",");
       String widthdata[]=  (indexwidth).split(",");
       String buttonstr="";
       for(int i=0;i<titledata.length;i++)
       {
          buttonstr+= "{\"field\":\""+ fielddata[i]+"\" ,"+
                      "\"title\":\""+ titledata[i]+"\","+
                      "\"width\":"+ widthdata[i]+"}"+",";
		
	     	}
	     	 buttonstr= buttonstr.trim();
		   	result ="[["+ buttonstr.substring(0, buttonstr.length()-1 )+"]]";
				
		return result;
	}

	public String getinit()
{
String result= gettitlehtml() +"⊙"+ idfield +"⊙"+ rowcount  +"⊙"+ searchbox +"⊙" +outfield ;

return result;

}
	public String gettotal(String constr)
	{
		String sqlstr="select count(*) from " + injoinform + " and " + constr ;
		String resultstr = S_Param.Getparam(sqlstr, "0");
		return resultstr;
	}

 public void  proputin( HttpServletRequest request )throws Exception {
  String[] feildstr = indexfield.split(",");
String str;
		 for (int i = 0; i < feildstr.length;i++) {
		
		 injoinform = injoinform.replace("{"+(i+1)+"}",  java.net.URLDecoder.decode( request.getParameter( feildstr[i].trim()) ));
		
  }
  str = injoinform ;
		  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
		 dbc.executeUpdate( str);
		 dbc.close();
		 conn.close();
 };

	public String getpagerecord
	 ( HttpServletRequest request )throws Exception {
	 	String constr = request.getParameter("condstr");
		        String pagecondstr = request.getParameter("page");
       String pagesizestr = request.getParameter("rows");

			
	
	
	
	 Integer it = new Integer( pagecondstr );
  int  i = it.intValue();
		Integer PageS= new Integer( pagesizestr ); 
	 int  m = PageS.intValue();
	 	
		String sqlstr="select top " + pagesizestr + " * FROM (select ROW_NUMBER()Over(order by " + idfield + " desc) as rowId, " + indexfield + " from " + injoinform +
" and " + constr + ") as #mytable " +
			" where rowId > " +( m *(i-1) ) ;
			String pagetotal= gettotal( constr);
		String resultstr = Gridprecord.getspagerecord(indexfield , sqlstr, pagetotal );
		return resultstr;
	}
	
	// condstr 选择id号， idstr新id号
public void insertrecord 	 ( HttpServletRequest request )throws Exception {
	 	String condstr = request.getParameter("condstr");
		        String idstr = request.getParameter("id");
	 String ddd=" delete FROM "+ outsigleform+" where " + outidfield+"='"+ idstr+"' insert into "+ outsigleform +"("+ outidfield+"," ;
	if( outfield.indexOf(",")>0 ) {
	String gg[]=outfield.split(","); 
	
	for(int i=0;i<gg.length;i++)
	{
	 ddd=ddd+ gg[i].split(":")[0]+",";
	}	
	 ddd= ddd.substring(0, ddd.length()-1 )+") select '"+ idstr+"'," ;
	 for(int i=0;i<gg.length;i++)
	{
	 ddd=ddd+ gg[i].split(":")[1]+",";
	}	 }
	else
	{
	 ddd=ddd+ outfield.split(":")[0]+") select '"+ idstr+"'," + outfield.split(":")[1]+"," ;
	
	}
	 ddd= ddd.substring(0, ddd.length()-1 )+" from "+
	 injoinform;
	 if(!("ALL⊙").equals( condstr ) )
	 {
	 if( condstr.indexOf(",") ==0){
	 ddd=ddd+" and "+ idfield+"='"+ condstr+"'";
	 }
	 else
	 { 
	 String mm[]= condstr.split(","); 
	 ddd= ddd+ " and (";
	 for(int j=0;j<mm.length;j++)
	{
	 ddd=ddd + idfield+"='"+ 
	 mm[j]+"' or " ;
	}	
	 
	 ddd= ddd.substring(0, ddd.length()-3 )+")";
	 } 
	 }  
	 Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
		 dbc.executeUpdate(ddd);
		 dbc.close();
		 conn.close();
	}

	
	private void setvar(String hreft)
	{
		 // String		 path = Environment.getExternalStorageDirectory().getPath() ;   
	//choicegrid.xml" ;

String 	 realPath= innpath.getxmlpath( rolestr,usercode,"/Pputingrid/"+ hreft+".xml" );
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
	    
ableputin =Integer.parseInt( database.element("ableputin").getText().trim());
if(ableputin==1)
{	title = database.element("title").getText().trim() ;
searchbox = database.element("searchbox").getText().trim() ;
indextitle = database.element("indextitle").getText().trim() ; 
rowcount = database.element("rowcount").getText().trim() ;
indexwidth = database.element("indexwidth").getText().trim() ; 

outfield = database.element("outfield").getText().trim() ; 
 outsigleform = database.element("outsigleform").getText().trim() ;

idfield = database.element("idfield").getText().trim() ; 
 outidfield = database.element("outidfield").getText().trim() ;
}
indexfield = database.element("indexfield").getText().trim() ;
 injoinform = database.element("injoinform").getText().trim() ; 


}
	
		 else
		  {
		  System.out.println( realPath + "put xml not exist");
		  }
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}