package Cjson;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray; 
import java.util.ArrayList;

/* import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;




import Com.db.DruidConnect;

import java.sql.Connection;
import java.sql.Statement;

*/
public class Treejson
{

	public Treejson()
	{} 
	
	 public static String getsimptreerecord(String sql)
	{
   JSONArray array = new JSONArray();      //定义JSON数组
		try
		{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sql);
			while (rs.next())
			{
				JSONObject obj = new JSONObject();       
			
					obj.put("id", rs.getString(1));
				 obj.put("text", rs.getString(2));
				 if( rs.getInt(3)==0 )
				 obj.put("state","open");
				 else if ( rs.getInt(3)==1 )
				 obj.put("state","closed");
				
				array.add(obj);
			}
			dbc.close()	;
			 conn.close();
		}
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString());
			
		System.out.print("Treejson_getsimptreerecord " +sql );
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			
		}
		finally
		{
			//关闭连接
		}
		return array.toString(); 
	}
	  public static String  getsimpattr( String attr, String sql )
	{
   String gg[]= attr.split(",");
   int  ii= gg.length;
   JSONArray array = new JSONArray();      //定义JSON数组
		try
		{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sql);
			while (rs.next())
			{
				JSONObject obj = new JSONObject();       
			obj.put("id", rs.getString(1));
				 obj.put("text", rs.getString(2));
				 if( rs.getInt(3)==0 )
				 obj.put("state","open");
				 else if ( rs.getInt(3)==1 )
				 obj.put("state","closed");
				 for(int i=0;i<ii;i++)
				 {
				 if(( rs.getString(4+i)!=null) )
				 
				 
				 
				 { 
				 JSONObject oobj = new JSONObject();  
				 oobj.put("atr"+(i+1), rs.getString(i+4));
			  	obj.put("attributes", oobj );
			  	 }
			  	 else break;
				 
				 }
				 

				array.add(obj);
				
			}
			dbc.close()	;
		}
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString() );
			
		System.out.print(" getsimpattr  "+ sql +": attr "+ attr );
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			
		System.out.print( sql );
		}
		finally
		{
			//关闭连接
		}
		return array.toString(); 
	}
  public static String gettreeicon(String attr, String sql )
	{
   String gg[]= attr.split(",");
   int  ii= gg.length;
   JSONArray array = new JSONArray();      //定义JSON数组
		try
		{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sql);
			while (rs.next())
			{
				JSONObject obj = new JSONObject();       
			obj.put("id", rs.getString(1));
				 obj.put("text", rs.getString(2));
				 if( rs.getInt(3)==0 )
				 obj.put("state","open");
				 else if ( rs.getInt(3)==1 )
				 obj.put("state","closed");
				 if( !( rs.getString(4)==null) )
				 obj.put("iconCls", rs.getString(4) );
				 
				 JSONObject oobj = new JSONObject();  
				 	 for(int i=0;i<ii;i++)
				 {
				 
				 if(( rs.getString(5+i)!=null) )
				 { 
				 oobj.put("atr"+(i+1), rs.getString(i+5));
			  	
			  	 }
			  	 else break;
				 
				 }
			 obj.put("attributes", oobj );
				array.add(obj);
				
			}
			dbc.close()	;
		}
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString() );
			
		System.out.print(" getsimpicon  " +sql +": attr "+ attr );
		}
		catch (Exception e)
		{
		
			System.out.println(e.toString());
			
		}
		finally
		{
			//关闭连接
		}
		return array.toString(); 
	}

	public static String gettreerecord(String sindexfield, String sql)
	{

	 	ArrayList idd= new ArrayList<String>();
		String[] sstar = new String[2];
		sstar[0] = "[";
		sstar[1] = ",\"children\":[";
		String[] toal = new String[6];
		toal[0] = " ,";
		toal[1] = "}]},";
		toal[2] = "}]}]},";
		toal[3] = "}]}]}]},";
		toal[4] = "}]}]}]}]},";
		toal[5] = "}]}]}]}]}]},";
		//定义JSON数组
		String[] idstr= sindexfield.split(",");
		StringBuffer jsonStr = null;
		jsonStr = new StringBuffer();
		int len=0;
		int ii=0;
		int xx=0;
		int mm=0;
		idd.add("");
		try
		{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sql);
			int m,n=0;
			int t=0;
			while (rs.next())
			{
			if(t==0) xx = rs.getString(1).length();
			t=2;
				n = rs.getString(1).length();
				 
				m = n - idd.get(ii).toString().length();

			  	idd.add(rs.getString(1));
				ii = ii + 1;
				if (m > 0)
				{
					if (ii == 1) jsonStr.append(sstar[0]);
					else  jsonStr.append(sstar[1]);
				}
				else if (m < 0)
				{
					m = -(m / 2);
					jsonStr.append(toal[m]);
		  	}
				else
				{
					jsonStr.append("},");

				}
				jsonStr.append("{");

				for (int i=0,j=1;i < idstr.length;i++,j++)
				{

					if (("1").equals(rs.getString(j).trim()))
						jsonStr.append("\"" + idstr[i].trim() + "\":true");
					else if (("0").equals(rs.getString(j).trim()))
						jsonStr.append("\"" + idstr[i].trim() + "\":false");
					else if (("-1").equals(rs.getString(j).trim()))
					{
						jsonStr.delete(jsonStr.length() - 1, jsonStr.length());

					}

					else
					{
						jsonStr.append("\"" + idstr[i].trim() + "\":\"" + rs.getString(j).trim() + "\"");
						if (i < idstr.length - 1) jsonStr.append(",");
					}

				}

			}
			mm= (n+2-xx) / 2;
			jsonStr.append(toal[mm]);
			jsonStr.delete(jsonStr.length() - 2 , jsonStr.length());

			//指定父记录如果长为2得
			dbc.close()	;
		}
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString() );
			
		System.out.print( "gettreerecord,sindexfield "+ sindexfield+ " sql "+sql );
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			
		}
		finally
		{
			//关闭连接
		}


		return jsonStr.toString();

	}
	//利用分割符生成树记录
	public static String gettree2record(String sindexfield, String sql , String perstr)
	{

	 	ArrayList idd= new ArrayList<Integer>();
		String[] sstar = new String[2];
		
		sstar[0] = "[";
		sstar[1] = ",\"children\":[";
		String[] toal = new String[6];
		toal[0] = " ,";
		toal[1] = "}]},";
		toal[2] = "}]}]},";
		toal[3] = "}]}]}]},";
		toal[4] = "}]}]}]}]},";
		toal[5] = "}]}]}]}]}]},";
		//定义JSON数组
		String[] idstr= sindexfield.split(",");
		StringBuffer jsonStr = null;
		jsonStr = new StringBuffer();
		int len=0;
		int ii=0;
		idd.add(0);
		try
		{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sql);
			int m,n=0;
			while (rs.next())
			{
				String hhh[]= rs.getString(1).split(perstr);
				n = hhh.length;
				m = n - (Integer)idd.get(ii);

			  	idd.add(n);
				ii = ii + 1;
				if (m > 0)
				{
					if (ii == 1) jsonStr.append(sstar[0]);
					else  jsonStr.append(sstar[1]);
				}
				else if (m < 0)
				{
					m = -m ;

					jsonStr.append(toal[m]);
				}
				else
				{
					jsonStr.append("},");

				}
				jsonStr.append("{");

				for (int i=0,j=1;i < idstr.length;i++,j++)
				{

					if (("1").equals(rs.getString(j).trim()))
						jsonStr.append("\"" + idstr[i].trim() + "\":true");
					else if (("0").equals(rs.getString(j).trim()))
						jsonStr.append("\"" + idstr[i].trim() + "\":false");
					else if (("-1").equals(rs.getString(j).trim()))
					{
						jsonStr.delete(jsonStr.length() - 1, jsonStr.length());

					}

					else
					{
						jsonStr.append("\"" + idstr[i].trim() + "\":\"" + rs.getString(j).trim() + "\"");
						if (i < idstr.length - 1) jsonStr.append(",");
					}

				}

			}

			jsonStr.append(toal[n]);
			jsonStr.delete(jsonStr.length() - 2 , jsonStr.length());

			//指定父记录如果长为2得
			dbc.close()	;
		}
		 catch (SQLException sqle)
		{
			System.out.println( sqle.toString() );
			
		System.out.print( " gettree2record sindexfield " + sindexfield+"sql"+ sql+" perstr " + perstr );
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			
		}
		finally
		{
			//关闭连接
		}


		return jsonStr.toString();

	}
public static String getstreerecord(String indexfield , String sql, String pagetotal,String curnode)
	{

		JSONObject whdata= new JSONObject();   
		whdata.put("total", pagetotal);

		JSONArray array = new JSONArray();      //定义JSON数组
		String[] idstr=indexfield.split(",");
		try
		{
  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sql);

			while (rs.next())
			{
				JSONObject obj = new JSONObject();       
				for (int i=0,j=1;i < idstr.length;i++,j++)
				{
					obj.put(idstr[i] , rs.getString(j));
					//	obj.put("id",idstr[i]);
					//	obj.put("name",rs.getString(j));

				}
				String ss= rs.getString(1).trim();
				if	 ( (ss.length()-curnode.length() ) > 1) obj.put("_parentId" , ss.substring(0, ss.length() -2 ));

				array.add(obj);

			}
			whdata.put("rows", array);
			dbc.close()	;
		}
		catch (SQLException sqle)
		{
			System.out.println( sqle.toString() );
			
		System.out.print( " getstreerecord( indexfield  :"+ indexfield+ ", sql,:"+sql+"  pagetotal"+ pagetotal+ ",curnode "+ curnode );
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			
		}
		finally
		{
			//关闭连接
		}
		return whdata.toString();

	}


	
}