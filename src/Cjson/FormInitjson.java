package Cjson;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray; 

/*
*/
public class FormInitjson
{

	public FormInitjson ()
	{} 
	
	 public static String getFormtitle(String fieldtitle,String columntitle,String comparam, String group,String getmxtotal)
  
	{
  	String result = null;
		String fielddata[]= (fieldtitle).split(",");
		String titledata[]=  (columntitle).split(",");
//		String comdata[]=  (comname).split(",");
		String paramdata[]= (comparam).split("⊙");
		 	String groupdata[]= group.split("⊙");
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

		 	int groupindex=Integer.parseInt( title[1].substring(0,1) );
		 String groupstr= groupdata[groupindex];
			buttonstr += "{\"field\":\"" + fielddata[i].trim() +"\",\n"+
				 "\"group\":\"" + groupstr.trim() + "\",\n" +
				 placeholder+ 
				 //formaterstr +
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

					paramdatastr = paramdata[j];
					buttonstr += "\"options\":{" + paramdatastr + "}}}\n,";
			
			}
		}
		result = "{\"total\":" + getmxtotal+ ",\"rows\":[" + buttonstr.substring(0, buttonstr.length() - 1) + "]}";
		
		return result;
	}
public static String getFormtitle(String Mfieldtitle ,String Mcolumntitle ,String Mcomparam , String getmxtotal)
  
	{
String result = null;
		String fielddata[]= (Mfieldtitle).split(",");
		String titledata[]=  (Mcolumntitle).split(",");
//		String comdata[]=  (comname).split(",");
		String paramdata[]= (Mcomparam).split("⊙");
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

					paramdatastr = paramdata[j];
					resultstr += "\"options\":{" + paramdatastr + "}}}\n,";
			
			}
		}
		result = "{\"total\":" + getmxtotal + ",\"rows\":[" + resultstr.substring(0, resultstr.length() - 1) + "]}";

		return result;
	}
}