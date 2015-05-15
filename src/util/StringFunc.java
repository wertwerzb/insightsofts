package util;


public class StringFunc
{

	public StringFunc()
	{} 
	 public static String  trimstr(String str,int gg)
	{
	String[] strdate= str.split(",");
	String result="";
	for(int i=0;i< gg;i++ )		
		{
		 result = result + strdate[i] + "," ;
		}
		return result.substring(0, result.length()-1);
		
	}
	 public static String  trimsplitstr(String str,int gg)
	{
	String[] strdate= str.split(",");
	String result="";
	for(int i=0;i< gg;i++ )		
		{
		 result = result + strdate[i].split(":")[0] + "," ;
		}
		return result.substring(0, result.length()-1);
		
	}
	
	
}