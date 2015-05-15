package util;

import javax.servlet.http.HttpServletRequest;

/**
 * Title:       
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      insightsoft
 * @author:		 weizhengbin
 * @version 1.0
 */

public class StrFun {
  public StrFun(){}	
  
  public static String chinese(String str) throws Exception{
     if(str!=null){
        try {
        
           str = new String(str.getBytes("IS0-8859-1"),"UTF-8") ;//IS0-8859-1，GBK，GB2312，UTF-16，US-ASCII,UTF-8
     
        }
         catch (Exception ex) {
         }
     }
     return str ;
  }
  
	public static String getString(HttpServletRequest request, String s){
        String temp = null;
		try{
			temp = chinese(request.getParameter(s).trim());
		}catch(Exception e){}
	return temp;
    }
       
       
	public static String getString(HttpServletRequest request, String s, String defaultString) throws Exception {
		String s1 = getString(request,s);
		if(s1==null) return defaultString;
		return s1;
	}

	public static int getInt(HttpServletRequest request,String s, int defaultInt){
		try{
			String temp = getString(request,s);
			if(temp==null)
				return defaultInt;
			else
				return Integer.parseInt(temp);
		}catch(NumberFormatException e){
			return 0;
		}
	}

	public static int getInt(HttpServletRequest request,String s){
          return getInt(request,s,0);
	}
	
	public static float getFloat(HttpServletRequest request,String s, float defaultfloat){
		try{
			String temp = getString(request,s);
			if(temp==null)
				return defaultfloat;
			else
				return Float.parseFloat(temp);
		}catch(NumberFormatException e){
			return 0;
		}
	}

	public static float getFloat(HttpServletRequest request,String s){
        return getFloat(request,s,0);
	}
			
	public static long getLong(HttpServletRequest request, String s, long defaultLong){
		try{
			String temp = getString(request,s);
			if(temp==null)
				return defaultLong;
			else
				return Long.parseLong(temp);
		}catch(NumberFormatException e){
			return 0;
		}
	}  

	public static long getLong(HttpServletRequest request,String s){
        return getLong(request,s,0);
	}	
	
}

