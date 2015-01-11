//增加roleCondstr,pagesizestr
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

public class Staticcom 

{

	private String  classname;
	private 	String  methodname ;
	private  String paramstr ;

	private  String usercode;
	 	private  String rolestr ;
	 	private  String filepath ;
	 	
	private String[] param_array = null;
	public Staticcom(String role, String userstr, String hreft,String flpath)
	{
		rolestr = role;
		usercode=userstr;
		 filepath= flpath;
		setvar(hreft);
	} 

public String actionrequststr(HttpServletRequest request) throws Exception 
	{ 
	
		  	HttpSession session = request.getSession();
	String result="";
	 	 param_array= paramstr.split(":");
int paramnum=param_array.length;
/*	 for(int i=0;i< param_array.length;i++ )
	 {
	 if( param_array[i].substring(0,1).equals("@") )
	 {

	 param_array[i]= java.net.URLDecoder.decode(request.getParameter(  param_array[i].substring(1, param_array[i].length() )));
	 
	 
	 }
	 if( param_array[i].substring(0,1).equals("#") )
	 {

	 param_array[i]= (String) session.getAttribute (  param_array[i].substring(1, param_array[i].length() ));
	 
	 
	 }

	 }
	 */
	try
		{
	Class clazz = Class.forName(classname); Method method;
			 
			 if( paramnum==1 )
			 { method = clazz.getMethod( methodname, String.class); 
				result= (String) method.invoke(null, (Object) param_array[0] );
				 }
			 if( paramnum==2 )
			 { method = clazz.getMethod( methodname, new Class[] { String.class, String.class }); 

			 	 Object[] args1 = { param_array[0] , param_array[1] };
				result= (String) 	method.invoke(null, args1 );
				 }
			 
		else	 if( paramnum==3 )
			 { method = clazz.getMethod( methodname, new Class[] { String.class, String.class, String.class }); 

			 	 Object[] args1 = { param_array[0] ,param_array[1],param_array[2] };
				result=(String)	method.invoke(null, args1 );
				 }
			else	 if( paramnum==4 )
			 { method = clazz.getMethod( methodname, new Class[] { String.class, String.class,String.class,String.class }); 

			 	 Object[] args1 = { param_array[0], param_array[1], param_array[2], param_array[3] };
				result=(String)	method.invoke(null, args1 );
				 }
			 	else		 if( paramnum==5 )
			 { method = clazz.getMethod( methodname, new Class[] { String.class, String.class,String.class,String.class ,String.class }); 

			 	 Object[] args1 = { param_array[0], param_array[1], param_array[2], param_array[3] , param_array[4] };
				result=(String)	method.invoke(null, args1 );
				 }
			 
		}
		catch (ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}
		catch (NoSuchMethodException nsme)
		{
			nsme.printStackTrace();
		}
		catch (IllegalAccessException ile)
		{
			ile.printStackTrace();
		}
		catch (InvocationTargetException ive)
		{
			ive.printStackTrace();
		}
return result;
	} 
public void actionrequsts(HttpServletRequest request) throws Exception 
	{ 

	try
		{
	Class clazz = Class.forName(classname);
			Method method = clazz.getMethod( methodname, HttpServletRequest.class); 
			method.invoke(null, (Object)request);
		}
		catch (ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}
		catch (NoSuchMethodException nsme)
		{
			nsme.printStackTrace();
		}
		catch (IllegalAccessException ile)
		{
			ile.printStackTrace();
		}
		catch (InvocationTargetException ive)
		{
			ive.printStackTrace();
		}
	} 
	private void setvar(String hreft)
	{

		String 	  realPath= innpath.getxmlpath(rolestr, usercode, "/"+ filepath+ "/" + hreft + ".xml");
		try
		{
			SAXReader reader = new SAXReader();
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			File file = new File(realPath);
			if (file.isFile())
			{
				Document document = reader.read(file);
				Element database = (Element)document.selectSingleNode("//" + hreft);

				 Element extendxml = database.element("paramstr ");
				 for(Iterator ipe = extendxml.elementIterator(); ipe.hasNext();){
  Element e_pe = (Element) ipe.next();
    paramstr +=e_pe.getText()+":"; 
           
}
paramstr= paramstr.substring(0, paramstr.length()-1);

				Element setupxml = (Element)document.selectSingleNode("//" + hreft);
				classname = database.element("classname").getText().trim() ;
				methodname = database.element("methodname").getText().trim() ;


			}
		}
		catch (Exception ce)
		{
			ce.printStackTrace();
		}
	}
}