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
import util.ExcelOpt;
import util.Dbcondi;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import Com.db.DruidConnect;
import Com.util.StrFun;

import util.S_Param;

import java.sql.Connection;
import java.sql.Statement;
public class excelexport
{
private String  title;
	private 	String  joinform ;
	private 	String  indextitle;
	private  String  indexfield;
	private String  selectcolumn;
	private 	String  columntitle; 
	private 	String  sigleform;
	private 	String  idfield ;
	private 	String  langstr ;
	private 	String  typestr ;
	private 	String path;
	private String conditionable;
	
	private String incondition;
	private 	String usercode;
	
private 	String hrefstr;
private 	String filepath;
	public excelexport(String role, String czy, String hreft,String fileph )
	{
		langstr = role;
hrefstr= hreft;
		usercode = czy;
		 filepath= fileph;
		setvar(hreft);
		 if (("1").equals(conditionable.trim()))  incondition = getconditionsql(role);
		else incondition = "";

	} 
	
	
	public  String getAllSql (String constr) 
	{
	
		replcerol();
	 	String str = "select " + indexfield + " from " + joinform +
			incondition + " and " + constr;

		return str;
	}
	 	public String getpagesql(HttpServletRequest request) throws Exception 
	{
		replcerol();
		String constr = request.getParameter("condstr");
		String pagecondstr = request.getParameter("page");
		String pagesizestr = request.getParameter("rows");
		Integer it = new Integer(pagecondstr);
		int  i = it.intValue();
		Integer PageS= new Integer(pagesizestr); 
		int  m = PageS.intValue();

		String sqlstr="select top " + pagesizestr + " * FROM (select ROW_NUMBER()Over(order by " + idfield + " desc) as rowId, " + indexfield + " from " + joinform +
			incondition + " and " + constr + ") as #mytable " +
			" where rowId > " + (m * (i - 1)) ;

		return sqlstr ;
	}
	 public String gettotal(String constr)
	{
		replcerol();
		String sqlstr="select count(*) from " + joinform +
			incondition + " and " + constr ;

		String resultstr = S_Param.Getparam(sqlstr, "0");
		return resultstr;
	}

	public  String  exportAll(HttpServletRequest request) throws Exception
	{
	 String str;
	 String[] DD=indexfield.split(",");
	 int mm;
	 String result ="";
	 int exporttype =Integer.parseInt( request.getParameter("exporttype"));
		if( exporttype==0 ){
	 mm= DD.length+1;
	 String constr = request.getParameter("condstr"); 	
		str= getAllSql( constr ) ;
		 }
		else
		{str = getpagesql( request ) ;
		mm= DD.length+2;
		 indextitle="序号,"+ indextitle;
		}
		 try
		{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(str);
			while (rs.next())
			{  String inresult="" ; 
				for (int i=1;i < mm;i++)
				{
				inresult = inresult +
				 rs.getString(i)+",";


				}
result += inresult+"⊙";
			}
			dbc.close()	;
		}
		catch (Exception e)
		{
		System.out.print( str );
			System.out.println(e.toString());
		}
		finally
		{
			//关闭连接
		}
		 ExcelOpt.gridwriteExcel ( result , title, indextitle , innpath.getPath("xls") +hrefstr +"index.xls" );
		return "写记录成功";


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
	 public String getvar(String gg)
	{
		String result="";

		if (sigleform == null) 
			result = result + " sigleform is null "; 
		if (joinform == null) 
			result = result + " joinform is null "; 
		if (title == null) 
			result = result + " title is null "; 

		if (indextitle == null) 
			result = result + " indextitle is null "; 
		if (indexfield == null) 
			result = result + " indexfield is null "; 
		if (conditionable == null) 
			result = result + " conditionable is null "; 

		if (selectcolumn == null) 
			result = result + " selectcolumn is null "; 
		if (columntitle == null) 
			result = result + " columntitle is null "; 		 	 		 		if (idfield == null) 
			result = result + " idfield is null "; 	

		
		return result;
	}
	private void replcerol()
	{
		if (joinform.indexOf("{U}") > 0)
			joinform = joinform.replace("{U}", usercode);
		if (joinform.indexOf("{R}") > 0)
			joinform = joinform.replace("{U}", langstr);

	}

public String getidsql(String Id)
	{
		String resultstr = "select " + selectcolumn + " from " + sigleform + " where " + idfield + "='" + Id + "'" ;
		return resultstr;
	}
	public String getidstr(HttpServletRequest request) throws Exception 
	{
	 	String resultstr = "";
		String Id= request.getParameter("idstr");
		String sql= getidsql(Id) ;
		
		 try
		{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sql);
			String[] field=columntitle.split(",");
			if (rs.next())
			{
   int j=1;
				int m=1;
				for (int i=1;i < field.length;i++)
				{
				String title=field[i].split(":")[0];
				
				String format=field[i].split(":")[1];
				if(format.substring(2,3).equals("1"))
				{
					j=j+1;
					m=1;
					
				}
				 					resultstr +="\""+ title+"\":"+m+":"+j+",\""+ rs.getString(i) + "\":"+(m+1)+":"+j+",";
m=m+2;
				}
			}
			dbc.close()	;
		}
		 
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString() );
			
		}
		catch (Exception e)
		{ 
			System.out.println(e.toString());
			
		}
		finally
		{
			//关闭连接
		}
		resultstr.substring(0, resultstr.length() - 1) ;
		 ExcelOpt.formwriteExcel( resultstr, title, innpath.getPath("xls") +hrefstr +"form.xls" );
		return "写记录成功";

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
				Element database = (Element)document.selectSingleNode("//" + hreft + "[@lang='" + langstr + "']");
				 	joinform = database.element("joinform").getText().toUpperCase(); 
				if ((joinform.indexOf("S_") > 0) && (realPath.indexOf("/" + usercode) > 0))

				{			

					joinform = "DDD";
					System.out.print("访问系统xml:" + realPath + "#" + joinform);

				}
				title = database.element("title").getText().replaceAll("\\s*", "") ;

				indextitle = database.element("indextitle").getText().replaceAll("\\s*", "") ;
				indexfield = database.element("indexfield").getText().replaceAll("\\s*", "") ;

				selectcolumn = database.element("selectcolumn").getText().replaceAll("\\s*", "") ;
				columntitle = database.element("columntitle").getText().replaceAll("\\s*", "") ;
				sigleform = database.element("sigleform").getText().trim() ;
				idfield = database.element("idfield").getText().trim() ;
				typestr = database.element("typestr").getText().trim() ;
conditionable = database.element("conditionable").getText().trim() ;

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