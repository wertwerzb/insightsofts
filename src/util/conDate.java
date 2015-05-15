package util;

import java.util.Date;

import java.text.SimpleDateFormat;


public class conDate
{

public static String getcon( String format)
	{ 
	 SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式

return df.format(new Date());
	
		}

}