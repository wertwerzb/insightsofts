package Cjson;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;


public class extendjson
{

	public extendjson()
	{} 


public static String getextendjson(String indexstr,String para)
	{
	 		  	String result = null;
	 String buttonstr="";
	 if ( indexstr.trim().length()<1) {
		return "";
		 	 } 	
		 	 
		String fielddata[]= (indexstr).split(",");
		 String titledata[]=  ( para ).split("⊙");
		 		String[] param ;
		 		 	int groupindex;
		    for(int i=0;i< fielddata.length ;i++)
       {
       		 	 
       param= fielddata[i].split(":");
       groupindex=Integer.parseInt( param[3] );
          buttonstr+= "{\"id\":\""+ param[0]+"\" \n, \"name\":\""+ param[1] +"\",\n\"href\":\""+ param[2] +"\",\n "+                     "\"option\":{"+ titledata[ groupindex ]+"} } "+",";
		
	     	} 		
		 	 	result = "⊙[" + buttonstr.substring(0, buttonstr.length() - 1) + "]"; 	
		return result; 		
		 		
	}
	

}