package util;

import java.io.File;
import java.io.*;

public class FileOperate
{

	public FileOperate()
	{} 
	
public static String copyfile(String olefile,String newfile)
	{ 
	File destFile= new File( newfile );
	 if (!destFile.getParentFile().exists()) 
   {
    destFile.getParentFile().mkdir();
     }
	 try{ 
	 FileInputStream input=new FileInputStream( olefile );//可替换为任何路径何和文件名 
	 
	 FileOutputStream output=new FileOutputStream( newfile );//可替 换为任何路径何和文件名

int in=input.read();
 while(in!=-1){ 
 output.write(in); in=input.read(); 
 } 
 return "success";
 }catch (IOException e){ System.out.println(e.toString());
 
 return "fail";
  } 
  }
	
	
	


}