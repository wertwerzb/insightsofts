package util;

import java.io.File;
import java.util.Properties;
import java.io.*;

public class innpath
{
String rootstr;
	public innpath()
	{} 
	
public  String getxmlpath( String role, String user,String filepath)
	{ 
	getProperty();
            
String gg="";
	String ff= filepath.substring( filepath.indexOf(".")+1, filepath.length()).trim();

	if( (role.length()>3  )
	 	&& ("0101").equals( role.substring(0,4) )  ) 
	 	 {
	 	
	 	 	gg=	rootstr+"//"+user+filepath;
	 	 
	 File file = new File(gg);
	 if(!file.exists())
	  { 
	  gg=	rootstr+"/"+ff+filepath;
	  
	 }
	 
	 	 }
	 	 else
	 	  gg=	rootstr+ "/"+ff+ filepath;
	  
return gg;
	}

public  String getPath(String path )
	{ 
	getProperty();
return rootstr+"//"+path+"//";
	}
public String getclassPath(String path )
	{ 
	getProperty();
 return rootstr+"/WEB-INF/class/"+path+"//";
	}
public static void main(String[] array)  {
	innpath inn=new innpath();
	System.out.print(inn.getclassPath("hhh"));
}
public void getProperty() {
    Properties properties = new Properties();
    try {
        InputStream inputstream = getClass().getClassLoader().getResourceAsStream("/path.properties");

        properties.load(inputstream);
        
        if (inputstream != null) {
            inputstream.close();
        }
        
    } catch (IOException ex) {
        System.err.println("Open Propety File Error");
    }
    
    rootstr= properties.getProperty("root");
}    

}