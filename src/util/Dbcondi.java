package util;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


public class Dbcondi
{

	public Dbcondi()
	{} 
	public static String getConditionStrSql(String fromStr, String rolecode) throws Exception
	{
		String condition = "";
		String TableField = "";
		String fieldtype1 = "";
		String Operator1 = "";
		String OperatorName1 = "";
		String FieldValue1 = "";
		String Relation1 = "";
		String ConditionHint= "";
		String Relationname1= "";
		String TableFieldName= "";
		try
		{
			String sqltext = " SELECT * FROM sys_condition " + " where  S_Role ='"
				+ (rolecode) + "' and Scd_Frmname ='" + (fromStr) + "'";

  Connection conn = DruidConnect.openConnection(); 
  Statement dbc = conn.createStatement(); 
			ResultSet rs = dbc.executeQuery(sqltext);

			while (rs.next())
			{
				fieldtype1 = rs.getString("Scd_fieldlx");
				TableField = rs.getString("Scd_field");
				Operator1 = rs.getString("Scd_opcode");
				OperatorName1 = rs.getString("Scd_opName");
				TableFieldName = rs.getString("Scd_fieldname");
				if (fieldtype1.equals("数字") || fieldtype1.equals("浮点"))
				{
					FieldValue1 = rs.getString("Scd_FValue");
				}
				else if (fieldtype1.equals("日期"))
				{
				/*	if (rs.getString("Scd_FValue").equals("当前月")) 
						FieldValue1 = "'" + GetDate.getStringMonthDate() + "'";
					else FieldValue1 = "'" + rs.getString("Scd_FValue") + "'";r*/
				}
				else if (fieldtype1.equals("字符"))
				{

					if (OperatorName1.equals("前包含"))
						FieldValue1 = "'%" + rs.getString("Scd_FValue") + "'";
					else if (OperatorName1.equals("后包含"))
						FieldValue1 = "'" + rs.getString("Scd_FValue") + "%'";
					else
						FieldValue1 = "'%" + rs.getString("Scd_FValue") + "%'";
				}
				Relationname1 = rs.getString("Scd_RlName");
				Relation1 = rs.getString("Scd_RlCode");
				condition = condition + Relation1 + " " + TableField + " "
					+ Operator1 + " " + FieldValue1 + " ";
				ConditionHint = ConditionHint + " " + Relationname1 + " " + fieldtype1 + "  " +
					"  " + TableFieldName + " " + " " + OperatorName1 + " " + FieldValue1;
			}
			rs.close();
			dbc.close();
			 conn.close();

			return condition + "⊙" + ConditionHint;

		}
		catch (Exception ex)
		{
			return "错误";
		}

	}		


}