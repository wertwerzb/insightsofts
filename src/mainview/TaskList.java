//增加roleCondstr,pagesizestr
package mainview;

import util.innpath;
import android.os.Environment; 
import java.io.File;

import org.dom4j.*;
import org.dom4j.io.*;
import java.util.Iterator;
import java.util.HashMap;
/**
 * Title:       
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      insightsoft
 * @author:		 weizhengbin
 * @version 1.0
 */
public class TaskList 
{
private String selectcolumn;
	private 	String  fieldtitle;
	private 	String  columntitle;	 	 
	private 	String  sigleform;
private String comparam;
	private String path ; 	
	 private String exterstr;
	 private String langstr;
	 private String rolestr; 
	 private String usercode; 
	 
	 
	public TaskList(String role, String userstr, String hreft, String langname )
	{
	rolestr=role;
	usercode=usercode;
	 langstr = langname;
		setvar(hreft);
	} 
		public String getfirstindex()
	{
		  	String result = null;
		String fielddata[]= (fieldtitle).split(",");
		String titledata[]=  (columntitle).split(",");
//		String comdata[]=  (comname).split(",");
		String paramdata[]= (comparam).split("⊙");
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


			buttonstr += "{\"field\":\"" + fielddata[i].trim() +"\",\n"+ placeholder+
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
			//	if (paramdata[j].indexOf("@") < 1) {
					paramdatastr = paramdata[j];
					buttonstr += "\"options\":{" + paramdatastr + "}}}\n,";
			
			}
		}
		result = "[" + buttonstr.substring(0, buttonstr.length() - 1) + "]";
		
		return result;
	}

	public String getinithtml()
	{
	String result="{\"formatstr\":"+getfirstindex()+"," + "\"exterstr\":{"+ exterstr.substring(0, exterstr.length() - 1) + "}}";
return result;

	}

	private void setvar(String hreft)
	{
//tableconfig.xml" ;

String 	  realPath= innpath.getxmlpath( rolestr,usercode,"/Otable/"+ hreft+".xml" );
 try
		{
			SAXReader reader = new SAXReader();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			File file = new File(realPath);
			if (file.isFile())
			{
				Document document = reader.read(file);
				 Element database = (Element)document.selectSingleNode("//" + hreft+ "[@lang='"+ langstr +"']" ); // 获取<line>元素的属性值  
				 Element extendxml = database.element("extendxml");
				 exterstr="";
				 for(Iterator ipe = extendxml.elementIterator(); ipe.hasNext();){
  Element e_pe = (Element) ipe.next();
    exterstr+= "\""+e_pe.getName()+"\":\""+ e_pe.getText()+"\","; 
           
}
				 
				selectcolumn = database.element("selectcolumn").getText().replaceAll("\\s*", "") ;
				fieldtitle = database.element("fieldtitle").getText().replaceAll("\\s*", "") ;
				columntitle = database.element("columntitle").getText().replaceAll("\\s*", "") ;
     sigleform = database.element("sigleform").getText().trim() ;
			
		comparam = database.element("comparam").getText().trim() ;
			}
			 else
		  {
		  System.out.println("table xml not exist");
		  }
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}