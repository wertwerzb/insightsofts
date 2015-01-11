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
public class IndexGridjson 
{

	public IndexGridjson ()
	{} 
	
	 public static String getgridtitle( String fieldstyle, String indexfield, String title, String indextitle , String indexwidth )
  
	{
	int froint,finint;
	 String frostring="";
	String[] frocols= fieldstyle.split(":");
   froint=Integer.parseInt( frocols[0] );
	 finint =Integer.parseInt ( frocols[1] );
	 	String firstrow="";
	int cols= indexfield.split(",").length; 
		String result = null;
		
		if ( title.indexOf("⊙")>0) 
		{	
		 String[] firstr=title.split("⊙");
		 firstrow= "[";
		 for (int i=0;i < firstr.length;i++)
				{
				 	 
		 firstrow += "{\"title\": \""+ firstr[i].split(":")[0] +"\",\n \"colspan\":"+ firstr[i].split(":")[1] + "},";
		 }
		 
		 firstrow= firstrow.substring(0,firstrow.length()-1 )+"],";
		}
		else
		{ 
		 firstrow= "[{\"title\":\" "+title+"\", \n \"colspan\":"+ cols+ "}],";
			}
			
 String fielddata[]= ( indexfield ).split(",");
       String titledata[]=  (indextitle).split(",");
       String widthdata[]=  (indexwidth).split(",");
       String buttonstr="";
       if( froint>0 )
       {
       for(int i= 0;i<froint;i++)
       {
          frostring += "{\"field\":\""+ fielddata[i]+"\",\n \"title\":\""+ titledata[i]+"\"\n,"+
                     "\"width\":"+ widthdata[i]+"}"+",";
		
	     	}
	 
       
       }
       for(int i= froint;i<finint;i++)
       {
          buttonstr+= "{\"field\":\""+ fielddata[i]+"\" \n, \"title\":\""+ titledata[i]+"\",\n "+
                      "\"width\":"+ widthdata[i]+"}"+",";
		
	     	}
	     	 buttonstr= buttonstr.trim();
		  if( frostring.length()>0 )
		  result ="\"frocolumns\":["+ firstrow+ "["+ buttonstr.substring(0, buttonstr.length()-1 )+"]],\"columns\":[["+ frostring.substring(0,frostring.length()-1 )+ "]]";
				
		  else 	result ="\"columns\":["+ firstrow+ "["+ buttonstr.substring(0, buttonstr.length()-1 )+"]]";
				
		return result;
	}


}