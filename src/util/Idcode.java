package util;

import Com.db.DruidConnect;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Idcode {

	public Idcode() {
	}

	public static String getidcode(String fieldname, String fontstr,
			String tablename, String dateformat) throws Exception {
		int i, n;
		String tr = "";
		if (dateformat.length() > 0) {
			Date currentTime = new Date();

			SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
			String dateString = formatter.format(currentTime);

			fontstr = fontstr + dateString;

		}
		String sqltext = "select " + fieldname + " as  ss from " + tablename
				+ " where  " + fieldname + " like '" + fontstr + "%'";
		try {
			Connection conn = DruidConnect.openConnection();
			Statement dbc1 = conn.createStatement();
			ResultSet rs1 = dbc1.executeQuery(sqltext);
			if (!rs1.next()) {
				rs1.close();
				dbc1.close();
				conn.close();
				return "'" + fontstr + "-1" + "'";
			} else {
				sqltext = "select  MAX({fn LENGTH(" + fieldname
						+ ")}) as  ss  from " + tablename + " where  "
						+ fieldname + " like '" + fontstr + "%'";
				Statement dbc = conn.createStatement();
				ResultSet rs = dbc.executeQuery(sqltext);
				if (rs.next()) {
					i = rs.getInt("ss");
				} else
					i = 11;

				rs.close();
				dbc.close();

				sqltext = "select MAX(" + fieldname + ") as  ss from "
						+ tablename + " where  " + fieldname + " like '"
						+ fontstr + "%' AND {fn LENGTH(" + fieldname + ")}="
						+ String.valueOf(i);

				Statement dbc2 = conn.createStatement();
				ResultSet rs2 = dbc2.executeQuery(sqltext);
				if (rs2.next())
					tr = rs2.getString("ss").split("-")[1];
				n = Integer.parseInt(tr) + 1;

				rs2.close();
				dbc2.close();
				conn.close();
				return fontstr + "-" + String.valueOf(n);
			}

		} catch (SQLException sqle) {
			System.out.println(sqle.toString());

			return "getlcode" + fieldname + "," + fontstr + "," + tablename
					+ "," + dateformat;

		} catch (Exception sqle) {
			return "'00'";
		} finally {
			// 关闭连接
		}
	}

	public static String getidcodes(String fieldname, String fontstr,
			String tablename, String dateformat) {
		return "'" + getidcodes(fieldname, fontstr, tablename, dateformat)
				+ "'";

	}

	public static String getplcode(String tablename, String fieldname,
			String perentcode) throws Exception {
		String result = "";
		try {

			String sql = "exec sp_getidcode @parentcode='" + perentcode
					+ "%',@fieldname='" + fieldname + "',@tablename='"
					+ tablename + "'";
			Connection conn = DruidConnect.openConnection();
			Statement dbc1 = conn.createStatement();
			ResultSet rs1 = dbc1.executeQuery(sql);

			if (rs1.next())
				result = rs1.getString(2);
			rs1.close();
			dbc1.close();
			return result;
		} catch (SQLException sqle) {
			System.out.println(sqle.toString());

			return "getplcode" + fieldname + "," + tablename + "," + perentcode;

		} catch (Exception sqle) {
			return sqle.toString();
		}

	}

	public static String getplcodes(String tablename, String fieldname,
			String perentcode) throws Exception {
		return "'" + getplcode(tablename, fieldname, perentcode) + "'";
	}

	public static String getlcodes(String tablename, String fieldname,
			String perentcode) throws Exception {
		return "'" + getlcode(tablename, fieldname, perentcode) + "'";
	}

	public static String getlcode(String tablename, String fieldname,
			String perentcode) throws Exception {

		String sqltext, st, tr, tr1, tt, result;
		result = "";
		try {
			Connection conn = DruidConnect.openConnection();

			if (!("").equals(perentcode)) {

				sqltext = "SELECT MAX( " + fieldname + " ) AS ss, "
						+ " CASE WHEN  MAX( " + fieldname + " )= '"
						+ perentcode + "' then 1  else 2 end as mm " + " FROM "
						+ tablename + " where " + fieldname + " like '"
						+ perentcode + "%' and {fn LENGTH(" + fieldname
						+ " )}<" + (perentcode.length() + 3);

				Statement dbc1 = conn.createStatement();
				ResultSet rs1 = dbc1.executeQuery(sqltext);
				if (rs1.next()) {
					if (rs1.getInt("mm") == 1) {
						result = rs1.getString("ss") + "01";
					} else {

						st = rs1.getString("ss").substring(0,
								rs1.getString("ss").length() - 2);
						tr = rs1.getString("ss").substring(
								rs1.getString("ss").length() - 2,
								rs1.getString("ss").length());
						tr1 = (Integer.parseInt(tr) + 1) + "";
						if (2 > tr1.length())
							tt = "0";
						else
							tt = "";
						result = st + tt + tr1;

					}
				}

				rs1.close();
				dbc1.close();
			} else {
				sqltext = "SELECT MAX(" + fieldname + ") AS ss " + "FROM "
						+ tablename + " where {fn LENGTH(" + fieldname + ")}<3";

				Statement dbc2 = conn.createStatement();
				ResultSet rs2 = dbc2.executeQuery(sqltext);

				if (rs2.next()) {
					if (rs2.getString("ss") == null)
						result = "01";
					else {
						st = rs2.getString("ss").substring(0,
								rs2.getString("ss").length() - 2);
						tr = rs2.getString("ss").substring(
								rs2.getString("ss").length() - 2,
								rs2.getString("ss").length());
						tr1 = (Integer.parseInt(tr) + 1) + "";
						if (2 > tr1.length())
							tt = "0";
						else
							tt = "";
						result = st + tt + tr1;

					}
				}

				rs2.close();
				dbc2.close();

			}
			conn.close();
			return result;
		} catch (Exception sqle) {
			return "getlcode" + fieldname + "," + tablename + "," + perentcode
					+ sqle.toString();
		}

	}

}