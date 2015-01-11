//增加roleCondstr,pagesizestr
package partview;
import Com.util.*;
import contrl.*;
import Cjson.Treejson;
import android.os.Environment; 
import java.io.*;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;
import javax.servlet.http.HttpServletRequest;
import util.innpath;
public class SyncList 
{
	private 	String  sigleform;
	private 	String  idfield ;
	private  String  namefield;
	private  String  statefield;
	private  String  attrfield;
	private  String  iconCls;
	private 	String hrefstr;

	private 	String condistr;
	 private 	String rolestr;
	 private 	String usercode;
	 
	 	 private String langstr;
	public SyncList( String role , String userstr, String hreft,String langName)
	{
	rolestr= role ;
	 usercode=userstr;
		hrefstr = hreft;
		langstr=langName;
		setvar(hreft);
  } 


	public String  getSelectSql()
	{ 
		String resultstr =  idfield +"," + namefield +"," + statefield +"," + attrfield ;
		return resultstr;
	}

	public String getsteprecord (HttpServletRequest request) throws Exception 
	{
      String codeId = request.getParameter("id");
		  	if( codeId ==null ) codeId ="0";
		String feilstr= idfield +"," + namefield +"," + statefield ;
		int len= codeId.length();
		String slen= String.valueOf(len + 3); 
		String sql="";// DATALENGTH
		if (len == 1)
			sql = "select " + feilstr + " from " + sigleform + " where "+condistr+" and len(" + idfield + ")<3 order by " + idfield + " asc ";
		else
			sql = "select " + feilstr + " from " + sigleform + " where "+condistr+" and len ( " + idfield + ")<" +
		slen + " and " + idfield + "!= '" + codeId + "' and " + idfield + " like '" + codeId + "%' order by " + idfield + " asc";  //定义SQL语句
   String resultstr = Treejson.getsimptreerecord(sql);
	 return resultstr ;
	
	}
	public String getstepattr (HttpServletRequest request) throws Exception 
	{
      String codeId = request.getParameter("id");
		  	if( codeId ==null ) codeId ="0";
		  	

		String feilstr= idfield +"," + namefield +"," + statefield +"," + attrfield ;
		int len= codeId.length();
		String slen= String.valueOf(len + 3); 
		String sql="";
		if (len == 1)
			sql = "select " + feilstr + " from " + sigleform + " where "+condistr+" and len (" + idfield + ")< 3 order by " + idfield + " asc ";
		else
		 	sql = "select " + feilstr + " from " + sigleform + " where "+condistr+" and len ( " + idfield + ")<" +
			slen + " and " + idfield + "!= '" + codeId + "' and " + idfield + " like '" + codeId + "%' order by " + idfield + " asc";  //定义SQL语句
    String resultstr = Treejson.getsimpattr(attrfield,sql);
	 return resultstr ;
	 // return sql ;
	}
	 	public String getcondicon (HttpServletRequest request) throws Exception 
	{
      String codeId = request.getParameter("id");
		  	if( codeId ==null ) codeId ="0";
		  	 String outcondistr = request.getParameter("condistr");
		  if( outcondistr ==null ) outcondistr ="1=1";
		  	

		String feilstr= idfield +"," + namefield +"," + statefield +","+ iconCls +"," + attrfield  ;
		int len= codeId.length();
		String slen= String.valueOf(len + 3); 
		String sql="";
	 if (len == 1)
			sql = "select " + feilstr + " from " + sigleform + " where "+ condistr +" and len (" + idfield + ")<3 and "+ outcondistr +" order by " + idfield + " asc ";
		else
		 	sql = "select " + feilstr + " from " + sigleform + " where "+ condistr +" and len ( " + idfield + ")<" +
			slen + " and " + idfield + "!= '" + codeId + "' and "+ outcondistr +" and " + idfield + " like '" + codeId + "%' order by " + idfield + " asc";  //定义SQL语句
     String resultstr = Treejson.gettreeicon(attrfield,sql);
	 return resultstr ;
	 // return sql ;
	}

public String getcondattr (HttpServletRequest request) throws Exception 
	{
      String codeId = request.getParameter("id");
		  	if( codeId ==null ) codeId ="0";
		  	 String outcondistr = request.getParameter("condistr");
		  if( outcondistr ==null ) outcondistr ="1=1";
		  	
		String feilstr= idfield +"," + namefield +"," + statefield +"," + attrfield ;
		int len= codeId.length();
		String slen= String.valueOf(len + 3); 
		String sql="";
		if (len == 1)
			sql = "select " + feilstr + " from " + sigleform + " where "+ condistr +" and len (" + idfield + ")<3 and "+ outcondistr +" order by " + idfield + " asc ";
		else
		 	sql = "select " + feilstr + " from " + sigleform + " where "+ condistr +" and len ( " + idfield + ")<" +
			slen + " and " + idfield + "!= '" + codeId + "' and "+ outcondistr +" and " + idfield + " like '" + codeId + "%' order by " + idfield + " asc";  //定义SQL语句
   
    String resultstr = Treejson.getsimpattr(attrfield,sql);
	 return resultstr ;
	 // return sql ;
	}

	private void setvar(String hreft)
	{
	 String 	 realPath= innpath.getxmlpath( rolestr,usercode,"/Psync/"+ hreft+".xml" );
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
	    sigleform = database.element("sigleform").getText().trim() ;
	    if( ( sigleform.indexOf("S_")>0)&&(  realPath.indexOf( "/"+usercode )>0))
		 	{			
		 	
		 	 sigleform = "DDD";
		 	System.out.print( "访问系统xml:"+realPath+"#"+sigleform );
		 	
		 }
		
				idfield = database.element("idfield").getText().trim() ;
				namefield = database.element("namefield").getText().trim() ;
				statefield = database.element("statefield").getText().trim() ;
			  attrfield = database.element("attrfield").getText().trim() ;
		   iconCls = database.element("iconCls").getText().trim() ;
		 condistr = database.element("condistr").getText().trim() ;
		 
		
			}
			else
			{
			System.out.println("realPath:"+realPath+" not exsist");
			}
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}