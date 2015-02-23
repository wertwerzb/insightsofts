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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import javax.servlet.ServletException;
import org.apache.commons.fileupload.DiskFileUpload;
import java.util.Map;
public class UpFile 

{


	private 	String  filetype ;
	private  String paramstr ;

	private  String usercode;
	 	private  String langstr ;
	 	private  String fileid ;
	 	
	private String hrefstr ;
	
	public UpFile(String lang, String userstr, String hreft,String flpath)
	{
		langstr = lang;
		usercode=userstr;
		 fileid= flpath;
		 hrefstr=hreft;
	} 

public String fileloadup(HttpServletRequest request) throws Exception 
	{ 
	 if(!DiskFileUpload.isMultipartContent(request))
	 {
	 return "表单数据格式不是multipart/form-data";
	 }
try{
      DiskFileItemFactory factory = new DiskFileItemFactory();
factory.setSizeThreshold(4*1024); 
      factory.setRepository(new File(util.innpath.getPath("temp")));

      ServletFileUpload upload = new ServletFileUpload(factory);
      
      upload.setSizeMax(1*1024*1024);
   
     List items = upload.parseRequest(request);    

      Iterator iter = items.iterator();
      while (iter.hasNext()) {
        FileItem item = (FileItem) iter.next();
        if((item.isFormField()) ) 
      {
      if( ("type").equals(item.getname ) )
      String filetypestr= item
      
      
      
      }  
        
      else   processUploadedFile(item); 
        
      }
     // outNet.close();
    }catch(Exception e){
       throw new ServletException(e);
    }
    return "操作成功";
  }

  
  private void processUploadedFile(FileItem item)throws Exception{
    String filename=item.getName();
       long fileSize=item.getSize();
    if(filename.equals("")||fileSize==0)return;
  filename=filename.substring( filename.lastIndexOf("//") +1,filename.length());
    
    if(filename.indexOf(".")==0)return;
    filetype= filename.substring( filename.lastIndexOf(".")+1, filename.length());
File uploadPath = new File( innpath.getPath(usercode+"//"+filetype)+hrefstr);
 if (!uploadPath.exists()) { uploadPath.mkdirs(); } 
 File uploadedFile = new File( uploadPath, fileid+"." +filetype);
    item.write(uploadedFile);
  
  }
	

}