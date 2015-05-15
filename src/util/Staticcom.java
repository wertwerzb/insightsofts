//增加langCondstr,pagesizestr
package util;

import java.lang.reflect.*;

public class Staticcom 

{


public static String getfun(String
methodname , String
classname, String
para) throws Exception 
	{ 
	
	String result="";
	String[]  param_array= para.split(":");
int paramnum=param_array.length;

	try
		{
	Class<?> clazz = Class.forName(classname);
	Method method;
			 
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
			 			System.out.print( " para "+ para+" classname "+ classname+ " methodname "+ methodname ) ;
		}
		catch (InvocationTargetException ive)
		{
			ive.printStackTrace();
System.out.print( " para "+ para+" classname "+ classname+ " methodname "+ methodname ) ;
		}
return result;
	} 

}