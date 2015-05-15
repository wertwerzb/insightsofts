package util;

import java.io.File; 
import java.io.IOException; 
import jxl.Workbook; 
import jxl.write.Label; 
import jxl.write.WritableSheet; 
import jxl.write.WritableWorkbook; 
import jxl.write.WriteException; 

import jxl.write.WritableImage;
import jxl.write.biff.RowsExceededException;
import jxl.read.biff.*;
import jxl.Sheet;

public class ExcelOpt
{ 


	public static String getputinsql( String fields, String fieldtitle, String tablename, String fileName) throws WriteException
	{ 
	 String[] fieldt=fieldtitle.split(",");
String sql="";

	 File file= new File(fileName);
	 		if (!file.exists())
	 		return "1";
	 try{ 
	 
	 
Workbook rwb=Workbook.getWorkbook( file ); 
System.out.print("ghh");
Sheet rs=rwb.getSheet("sheet1");

System.out.print("ghh1"); //或者rwb.get Sheet(0)

int mclos=0;
int rows=rs.getRows();//得到所 有的行

for(int m = 0; m < fieldt.length;m++) 
{
if( fieldt[m].equals(rs.getCell(m,1).getContents() )) mclos++ ; 

}
if ( mclos==0 ) return "2";

if ( mclos<fieldt.length ) 
{
String selectc="";
String [] feildstr= fields.split(",");
	for(int i=0;i< mclos ;i++ )
		{
		 selectc= selectc+ feildstr[i]+",";
		
		}
		
		 fields= selectc.substring(0, selectc.length()-1 ) ;
		}
	


String sqlpre="insert "+tablename+"("+ fields+ ")";

for(int i = 2; i < rows; i++) { 
String sqlvalues="";
for(int j = 0; j < mclos; j++) { 
String id=rs.getCell(j, i).getContents();

sqlvalues= sqlvalues+"'"+ id+"',";

} 
sqlvalues="values("+ sqlvalues.substring(0, sqlvalues.length()-1 ) +")";


 sql=sql+ sqlpre+ sqlvalues+";" ;

} 
return sql;

} 
  catch(Exception e) { //TODO Auto -generated catch block
System.out.print(e.toString());
e.printStackTrace(); 
} 
return sql ; 
	
	}

	public static String gridwriteExcel(String dastr , String title, String fieldtitle, String fileName) throws WriteException
	{ 
		File  excelPath = new File(fileName.substring(0, fileName.lastIndexOf("//")));

		if (!excelPath.exists())
		{ excelPath.mkdirs(); } 
		String[] ggg= fieldtitle.split(",");

		int num= ggg.length ;
		WritableWorkbook wwb = null;
		try
		{ 
			wwb = Workbook.createWorkbook(new File(fileName));

		} 
		catch (IOException e)
		{ 
			e.printStackTrace(); } 
		if (wwb != null)
		{ 
			WritableSheet ws=wwb.createSheet(title, 0);


			try
			{ 
				ws.mergeCells(0, 0, num, 0);
			}
			catch (Exception e)
			{ e.printStackTrace();
				return "未能合并单元格"; 
			}
			try
			{
				Label titbe = new Label(0, 0, title); 
				ws.addCell(titbe); 

				for (int i=0;i < num;i++)
				{

					Label labelc = new Label(i, 1, ggg[i]); 
					ws.addCell(labelc); 

				}
				String[] rows= dastr.split("⊙");

				for (int j=0;j < rows.length ;j++)
				{
					String[] columns= rows[j].split(",");
					int totalco= columns.length;

					for (int i=0;i < totalco;i++)
					{

						Label labelc = new Label(i, j + 2, columns[i]); 
						ws.addCell(labelc); 

					}
				}

			}
			catch (RowsExceededException e) 
			{ e.printStackTrace();
				return "未打开工作薄";
			} 
			catch (WriteException e)
			{ e.printStackTrace(); 
				return "写工作薄失败1";
			}
			try
			{ //从内存中写入文件中 
				wwb.write(); //关闭资源，释放内存 
				wwb.close(); 
				return "写工作薄成功";
			} 
			catch (IOException e)
			{ e.printStackTrace();
				return "写工作薄失败2"; }
			catch (WriteException e)
			{ e.printStackTrace(); 
				return "写工作薄失败3"; } 
		}
		return "没有工作薄失败";
	} 
	public static String formwriteExcel(String dastr , String title, String fileName) throws BiffException
	{ 

		int fclose=0;
		Workbook wb=null;
		WritableWorkbook wwb = null;

		try
		{ 
			File file = new File(fileName);
			if (!file.exists())
			{


	  			wwb = Workbook.createWorkbook(file);
			}
			else
			{
				wb = Workbook.getWorkbook(file); 
				wwb = Workbook.createWorkbook(file , wb);

				fclose = 1;
			}
		} 
		catch (IOException e)
		{ 
			e.printStackTrace(); 
		} 
		if (wwb != null)
		{ 
			WritableSheet ws = wwb.getSheet(title);

			if (ws == null)ws = wwb.createSheet(title, 0);
			try
			{ 
				ws.mergeCells(0, 0, 4, 0);
			}
			catch (Exception e)
			{ e.printStackTrace();
				return "未能合并单元格"; 
			}
			try
			{
				String[] rows= dastr.split(",");

				for (int j=0;j < rows.length;j++)
				{
					String[] columns= rows[j].split(":");
					int col= Integer.parseInt(columns[1]);

					int row= Integer.parseInt(columns[2]);


					Label labelc = new Label(col , row, columns[0]); 
					ws.addCell(labelc);

				}

			}
			catch (RowsExceededException e) 
			{ e.printStackTrace();
				return "未打开工作薄";
			} 
			catch (WriteException e)
			{ e.printStackTrace(); 
				return "写工作薄失败1";
			}
			try
			{ //从内存中写入文件中 
				wwb.write(); //关闭资源，释放内存 
				wwb.close(); 
				if (fclose == 1)		wb.close(); 
				return "写工作薄成功";
			} 
			catch (IOException e)
			{ e.printStackTrace();
				return "写工作薄失败2"; }
			catch (WriteException e)
			{ e.printStackTrace(); 
				return "写工作薄失败3"; } 
		}
		return "没有工作薄失败";
	} 
	public static String formatreport(String dastr , String title, String fileName, String oldName, String imagefile) throws BiffException
	{ 

		int fclose=0;
		Workbook wb=null;
		WritableWorkbook wwb = null;

		try
		{ 

			FileOperate.copyfile(oldName ,  fileName);
			File file = new File(fileName);

			wb = Workbook.getWorkbook(file); 
			wwb = Workbook.createWorkbook(file , wb);

			fclose = 1;

		} 
		catch (IOException e)
		{ 
			e.printStackTrace(); 
		} 
		if (wwb != null)
		{ 
			WritableSheet ws = wwb.getSheet(0);

			if (ws == null)ws = wwb.createSheet(title, 0);

			try
			{
				String[] rows= dastr.split("⊙");

				for (int j=0;j < rows.length;j++)
				{
					String[] constent= rows[j].split("©");
					String[] colinfo=  constent[1].split(":");
					Label labelc = new Label(Integer.parseInt(colinfo[0]) , Integer.parseInt(colinfo[1]) , constent[0]); 

					if (!(constent[0].equals("pngimg")))
					{
	if (colinfo.length == 4)
						{
							ws.mergeCells(Integer.parseInt(colinfo[0]) , Integer.parseInt(colinfo[1]) , Integer.parseInt(colinfo[2]) , Integer.parseInt(colinfo[3]));

						}
						ws.addCell(labelc);
					}
					else
					{
						File imfile=new File(imagefile); 

						if (imfile .exists())
						{	
							WritableImage image=new WritableImage(Integer.parseInt(colinfo[0]) , Integer.parseInt(colinfo[1]) , Integer.parseInt(colinfo[2]) , Integer.parseInt(colinfo[3]) , imfile);

							ws.addImage(image);
						}
					}




				}

			}
			catch (RowsExceededException e) 
			{ e.printStackTrace();
				return "未打开工作薄";
			} 
			catch (WriteException e)
			{ e.printStackTrace(); 
				return "写工作薄失败1";
			}
			try
			{ //从内存中写入文件中 
				wwb.write(); //关闭资源，释放内存 
				wwb.close(); 
				if (fclose == 1)		wb.close(); 
				return "写工作薄成功";
			} 
			catch (IOException e)
			{ e.printStackTrace();
				return "写工作薄失败2"; }
			catch (WriteException e)
			{ e.printStackTrace(); 
				return "写工作薄失败3"; } 
		}
		return "没有工作薄失败";
	} 

 
}