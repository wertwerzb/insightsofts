//增加roleCondstr,pagesizestr
package mainview;
import Cjson.Formjson;
import Cjson.Gridprecord;
import android.os.Environment; 
import java.io.File;

import org.dom4j.*;
import org.dom4j.io.*;
import util.innpath;
import util.initbutton;
import util.S_Param;
import util.Dbcondi;
import Cjson.extendjson;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

//import org.json.*;
/**
 * Title:       
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      insightsoft
 * @author:		 weizhengbin
 * @version 1.0
 */
public class EntList 
{
private String  title;
	private 	String  joinform ;
	private 	String  indextitle;
	private  String  indexfield;
 private  String  indexwidth;
	
	private  String rowcount;
	private String incondition;
	
	private String conditionable;
 private String fieldstyle;

	//private String  title;
	//private String  idstr;
	private String  selectcolumn;
	private 	String  fieldtitle;
	private 	String  columntitle;	
	private 	String 	 idfield; 	 
	private 	String  sigleform;
	
	//private String comname;  //引用两列的记录  
	private String comparam;
	private String Mpath ; 
	
	private  String  Xselectcolumn;
	private String  Xcolumntitle;
	private 	String  Xfieldtitle;
	private 	String  Xsigleform;
	private 	String  Xidfield ;
	private  String  Xaddidmodal;
	private  String  Xcomname ;
	private 	String  Xcomparam;
	private 	String Xfieldwidth;
	private 	String rolestr;
	private 	String 	 Xjionfield;
	private 	String Xrowcount;
 private String exterstr;
 private 	String fontstr;
 private String langstr;
 private String usercode;

 	private String firstcom;
	private String secondcom;
 	private String exterparam;
 	
 
 	private String hrefstr;
	public EntList(String role,
	String userstr, String hreft, String langname )
	{
	rolestr=role;
	usercode=usercode;
	hrefstr = hreft;
 langstr = langname; 
		 setvar(hreft);
		 	 if (("1").equals(conditionable.trim()))  incondition = getconditionsql(role);
		else incondition = "";
	
	} 
	
	 private String getfirsttitle()
  
	{
	 return Cjson.IndexGridjson.getgridtitle(fieldstyle,indexfield, title,indextitle,indexwidth );
	
	}
	private String getfirstbutton(String role)
	{ 

			 	
String result=initbutton.getbuttonstr("head",rolestr,hrefstr,langstr);
		return result;
}
	 public String getfirstinit()
{   
String result= "{\"firstbutton\":"+getfirstbutton(rolestr)+",\"columns\":"+ getfirsttitle() +",\"idfield\":\""+ idfield +"\",\"rowcount\":"+ rowcount+",\"exterstr\":" +
"{"+ exterstr.substring(0, exterstr.length() - 1) + "}}" + extendjson.getextendjson( firstcom, exterparam ) ;
return result ;


}
	private String gettotal(String constr)
	{
		String sqlstr="select count(*) from " + joinform +
			incondition + " and " + constr ;
		String resultstr = S_Param.Getparam(sqlstr, "0");
		return resultstr;
	}
	 	public String  getvar()
	{ 
		return  "selectcolumn: " + selectcolumn +
			" fieldtitle:" + fieldtitle +
		  	" columntitle:" + columntitle +
			" sigleform:" + sigleform +
			", Xjionfield : " + Xjionfield ;
	}

public String getpagesql (HttpServletRequest request) throws Exception 
{
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
			return sqlstr;
}
	public String getpagerecord (HttpServletRequest request) throws Exception 
{
String constr = request.getParameter("condstr");
		 String sqlstr= getpagesql (request) ;	
			String pagetotal= gettotal( constr);
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

	public int getMtotal()
	{
		int i= fieldtitle.split(",").length;
		int j= columntitle.split(",").length;
		int result;
		if (i == j) result = i;
		else result = -1;
		return result ;
	}
	public String getMidsql(String Id)
	{
		String resultstr = "select " + selectcolumn + " from " + sigleform + " where " + idfield + "='" + Id + "'" ;
		return resultstr;
	}
	public String getMidjsoin (HttpServletRequest request) throws Exception 
	{
	String Id= request.getParameter("idstr");
		String sql= getMidsql(Id) ;
		String resultstr = Formjson.getsidjsoin(fieldtitle, sql);
		return resultstr;

	}
	public String getMidrecord(String ids)
	{
		String sqlstr= getMidsql(ids);
		int total= getMtotal();
		String resultstr = Formjson.getsidrecord(total, sqlstr);
		return resultstr;
	}

	public String getMfirstindex()
	{
	 	String getmx=""+getMtotal();
 String result=Cjson.FormInitjson.getFormtitle(fieldtitle,columntitle, comparam,getmx);
return result;
	
		}
	public String getMmlook(String contentstr)
	{ 
		String result = null;
		String valuesdata[]= getMidrecord(contentstr).trim().split(",");
		String titledata[]=  (columntitle).split(",");
		String paramdata[]= (comparam).split("⊙");

		String resultstr="";
		for (int i=0;i < titledata.length;i++)
		{
			// 	String paramdatastr="";
	
			String title[]= titledata[i].split(":");
		 	int groupindex=Integer.parseInt(title[1].substring(0, 1));
			String comstring="";
			if (("com").equals(title[2].substring(0, 3)) || ("tre").equals(title[2].substring(0, 3)))
			{
				String comnamestr[] = title[2].split("-");
				int j=Integer.parseInt(comnamestr[1]);
			/*	*/
			}

			resultstr += "{\"title\":\"" + title[0].trim() + "\",\n" +
				comstring +
				"\"value\":" + valuesdata[i].trim() + ",\n" +
				"\"grouprow\":\"" + title[1].trim() + "\"},";
		}
		result = "{\"total\":" + getMtotal() + ",\"rows\":[" + resultstr.substring(0, resultstr.length() - 1) + "]}";

		 return result;

	}
	public String  getMlook( String contentstr )
{
return getMmlook( contentstr )
+ "⊙" + getXtitlehtml() +"⊙" + Xrowcount ;
 }
	
	public String getbutton(String contentstr, String role)
	{ 


		String result =initbutton.getbuttonstr(contentstr ,rolestr,hrefstr,langstr);
		return result;
	}

	public String  getXSelectSql(String contentstr)
	{ 
		String resultstr = "select " + Xselectcolumn + " from " + Xsigleform + " where " + Xjionfield +"='"+ contentstr+ "' order by " + Xidfield ;
		return resultstr;
	}

	public String getXtotal(String constr)
	{
		String sqlstr="select count(*) from " + Xsigleform + " where "+ Xjionfield + "='"+ constr+ "'" ;
	String resultstr = S_Param.Getparam(sqlstr, "0");
	return resultstr;
	 //	 return sqlstr;
	}

	public String getXrecord(String contentstr)
	{

		String pagetotal= getXtotal(contentstr);
		String 	sql = getXSelectSql(contentstr);
		String resultstr= Gridprecord.getsrecord(Xfieldtitle, sql, pagetotal);
		return resultstr;
	}
	public String getXtitlehtml()
	{
	 	return Cjson.EditGridInit. getgridtitle (Xfieldtitle, Xcolumntitle,Xfieldwidth,Xcomname,Xcomparam ) ;

	}
	 	public String getXpagerecord(String page, String constr, String pagesizestr)
	{
		Integer it = new Integer(page);
		int  i = it.intValue();
		Integer PageS= new Integer(pagesizestr); 
		int  m = PageS.intValue();

		String sqlstr="select top "
			+ pagesizestr + " * FROM (select ROW_NUMBER()Over(order by " + Xidfield + " desc) as rowId, " + Xselectcolumn + " from " + Xsigleform +
			" where " + Xjionfield + "='"+ constr+ "' ) as #mytable " +
			" where rowId > " + (m * (i - 1)) ;
		String pagetotal= getXtotal(constr);
		String resultstr = Gridprecord.getspagerecord(Xfieldtitle , sqlstr, pagetotal);
		return resultstr;
	}
	
	 
	 	public String getsecondinit()
	{
		String result="{\"mxlistbutton\":"+getbutton("mxlist", rolestr) + ",\"xcolumns\":" + getXtitlehtml() + ",\"mformatstr\":" + getMfirstindex() + ",\"mxheadbutton\":"+ getbutton("mxhead", rolestr) +",\"otherinfo\":"+ getother() +",\"exterstr\":" +
"{"+ exterstr.substring(0, exterstr.length() - 1) + "}}" + extendjson.getextendjson( secondcom, exterparam ) ; 

		return result;

	}
	 	public String getother()
	{
		String result= "{\"Xrowcount\":"+Xrowcount +",\"Xaddidmodal\":"+ Xaddidmodal+"}";

		return result;

	}

	private void setvar(String hreft)
	{
	 //entconfig.xml" ;
String Mpath=Environment.getExternalStorageDirectory().getPath() ;

		String realPath = Mpath +  "//"+hreft+".xml" ;
//String 	 realPath= innpath.getxmlpath( rolestr,usercode,"/Meditentry/"+ hreft+".xml" );
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
			 title = database.element("title").getText().trim() ;
			joinform = database.element("joinform").getText().trim() ;
				indextitle = database.element("indextitle").getText().replaceAll("\\s*","") ;
				indexfield = database.element("indexfield").getText().replaceAll("\\s*","") ;
			 indexwidth = database.element("indexwidth").getText().trim() ;
	   	
				conditionable = database.element("conditionable").getText().trim() ;
				rowcount = database.element("rowcount").getText().trim() ;

		  fieldstyle= database.element("fieldstyle").getText().trim() ;
		 
		selectcolumn = database.element("selectcolumn").getText().replaceAll("\\s*", "") ;
				fieldtitle = database.element("fieldtitle").getText().replaceAll("\\s*", "") ;
				columntitle = database.element("columntitle").getText().trim() ;

				sigleform = database.element("sigleform").getText().trim() ;
				idfield = database.element("idfield").getText().trim() ;
				comparam = database.element("comparam").getText().trim() ;
				Xselectcolumn = database.element("Xselectcolumn").getText().replaceAll("\\s*","") ;
				Xfieldtitle = database.element("Xfieldtitle").getText().replaceAll("\\s*", "") ;
				Xcolumntitle = database.element("Xcolumntitle").getText().replaceAll("\\s*", "") ;
				Xfieldwidth = database.element("Xfieldwidth").getText().trim() ;
				Xsigleform = database.element("Xsigleform").getText().trim() ;
				Xidfield = database.element("Xidfield").getText().trim() ;
			 	Xcomparam = database.element("Xcomparam").getText().trim() ;
			
				Xcomname = database.element("Xcomname").getText().trim() ; 
				Xaddidmodal = database.element("Xaddidmodal").getText().trim() ; 	
			
			
					fontstr = database.element("Mfontstr").getText().trim() ;
	 
			 Xjionfield = database.element("Xjionfield").getText().trim() ;
		
				Xrowcount = database.element("Xrowcount").getText().trim() ;
				 firstcom = database.element("firstcom").getText().trim() ;
		 secondcom = database.element("secondcom").getText().trim() ; 
		 exterparam = database.element("exterparam").getText().trim() ;
	}
	 else
		  {
		  System.out.println("ent xml not exist");
		  }
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}