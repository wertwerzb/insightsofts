//增加langCondstr,pagesizestr
package otheractions;
import android.os.Environment; 
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.*;
import org.dom4j.io.*;
import util.innpath;
import java.lang.reflect.*;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.io.PrintWriter;

import java.util.List;
import org.apache.commons.fileupload.FileItem;
import javax.servlet.ServletException;
import com.oreilly.servlet.multipart.FilePart; 
   import com.oreilly.servlet.multipart.MultipartParser; 
   import com.oreilly.servlet.multipart.ParamPart; 
   
import com.oreilly.servlet.multipart.Part;
public class mulForm 

{

	private String  tempFilePath;
	private 	String  methodname ;
	private  String paramstr ;

	private  String usercode;
	 	private  String langstr ;
	 	private  String filepath ;
	 	
	private String[] param_array = null;
	public mulForm(String lang, String userstr, String hreft,String flpath)
	{
		langstr = lang;
		usercode=userstr;
		 filepath= flpath;
		setvar(hreft);
	} 

public String actionrequststr(HttpServletRequest request) throws Exception 
	{ 
	 
try{
MultipartParser mp = new MultipartParser(request, 2*1024*1024, false, false, "UTF-8");
 Part part;
  while ((part = mp.readNextPart()) != null) { 
  String name = part.getName(); 
  if (part.isParam()) { ParamPart paramPart = (ParamPart) part;
   String value = paramPart.getStringValue(); 
   System.out.println("param: name=" + name + ";value=" + value);
     } 
     else if (part.isFile()) { 
     // it's a file part 
     FilePart filePart = (FilePart) part;
      String fileName = filePart.getFileName(); 
      if (fileName != null) { long size = filePart.writeTo(new File("c:/tmp/")); 
      System.out.println("file: name=" + name + "; fileName=" + fileName + ", filePath=" + filePart.getFilePath() + ", contentType=" + filePart.getContentType() + ", size=" + size);
       } 
       else { 
       System.out.println("file: name=" + name + "; EMPTY");
        } 
        System.out.flush(); 
        } 
        } 
    }catch(Exception e){
       throw new ServletException(e);
    }
    return "操作成功";
  }

  private void processFormField(FileItem item){
    String name = item.getFieldName();
    
    
    String value = item.getString();
  }
  
  
  private void processUploadedFile(FileItem item)throws Exception{
    String filename=item.getName();
    int index=filename.lastIndexOf("//");
    filename=filename.substring(index+1,filename.length());
    long fileSize=item.getSize();
    
    if(filename.equals("") && fileSize==0)return;

    File uploadedFile = new File(filepath+"/"+filename);
    item.write(uploadedFile);
  
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

				 Element extendxml = database.element("paramstr ");
				 for(Iterator ipe = extendxml.elementIterator(); ipe.hasNext();){
  Element e_pe = (Element) ipe.next();
    paramstr +=e_pe.getText()+":"; 
           
}
paramstr= paramstr.substring(0, paramstr.length()-1);

				Element setupxml = (Element)document.selectSingleNode("//" + hreft);
				tempFilePath = database.element("tempFilePath").getText().trim() ;
				filepath= database.element("filepath").getText().trim() ;


			}
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}