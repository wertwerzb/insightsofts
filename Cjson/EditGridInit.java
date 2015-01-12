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
public class EditGridInit 
{

	public EditGridInit ()
	{} 
	
	 public static String getgridtitle( String fieldtitle, String columntitle, String fieldwidth, String comname , String comparam )
  
	{
	 	String result = null;
		
		  String fielddata[]= (fieldtitle).split(",");
			String titledata[]=  (columntitle).split(",");
			String widthdata[]=  (fieldwidth).split(",");
			String comdata[]=  (comname).split(",");
			String paramdata[]= (comparam).split("⊙");

			String buttonstr="";
			for (int i=0;i < titledata.length;i++)
			{
			
				String paramdatastr="";
				String comnames= "";
				
			  String formatstr="";
			  String titstr;
			if( titledata[i].indexOf(":") >0 )
			{ 
			  formatstr= "\"formatter\":"+titledata[i].split(":")[1]+",";
			 titstr= titledata[i].split(":")[0];
			}
			else
			{
			 formatstr="";
			 titstr= titledata[i];
			 }
				buttonstr += "{\"field\":\"" + fielddata[i] + "\"\n," +
					"\"title\":\"" + titstr + "\",\n" +
					"\"width\":" + widthdata[i]  + ",\n" + formatstr ;
				if (comdata[i].indexOf("-") < 1)
				{ 
					comnames = comdata[i];
					buttonstr += "\"editor\":\"" + comnames + "\"},";
				}
				else
				{
					String comnamestr[] = comdata[i].split("-");
					comnames = comnamestr[0];
					buttonstr += "\"editor\":{\"type\":\"" + comnames + "\"," ;
					int j=Integer.parseInt(comnamestr[1]);
					//if (paramdata[j].indexOf("@") < 1) {
						paramdatastr = paramdata[j];
						buttonstr += "\"options\":{" + paramdatastr + "}}},";
				
      }
			}
			
			buttonstr = buttonstr.trim();
		 result = "[[" + buttonstr.substring(0, buttonstr.length() - 1) + "]]";
	
		return result;
	}


}