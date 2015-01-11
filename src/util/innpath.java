package util;

import java.io.File;

public class innpath
{

	public innpath()
	{} 
	
public static String getxmlpath( String role, String user,String filepath)
	{ 
	String cc= "e/ROOT" ;
	String gg="";
	String ff= filepath.substring( filepath.indexOf(".")+1, filepath.length()).trim();

	if( (role.length()>3  )
	 	&& ("0101").equals( role.substring(0,4) )  ) 
	 	 {
	 	
	 	 	gg=	cc+"//"+user+filepath;
	 	 
	 File file = new File(gg);
	 if(!file.exists())
	  { 
	  gg=	cc+"//"+ff+filepath;
	  
	 }
	 
	 	 }
	 	 else
	 	  gg=	cc+ "//"+ff+ filepath;
	  
return gg;
	}
public static String getclasspath( String role, String user,String filepath)
	{ 
	String cc= "e:/xxx//ROOT" ;
	String gg="";
	String ff= filepath.substring( filepath.indexOf(".")+1, filepath.length()).trim();

	if( ("0101").equals( role.substring(0,4) )  ) 
	 	 {
	 	
	 	 	gg=	cc+"//"+user+filepath;
	 	 
	 File file = new File(gg);
	 if(!file.exists())
	  { 
	  gg=	cc+"//"+ff+filepath;
	  
	 }
	 
	 	 }
	 	 else
	 	  gg=	cc+ "//"+ff+ filepath;
	  
return gg;
	}


}