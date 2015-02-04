package util;

import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.util.ArrayList; 
import java.util.List;
import jxl.Workbook; 
import jxl.format.UnderlineStyle; 
import jxl.write.Label; 
import jxl.write.WritableFont; 
import jxl.write.WritableSheet; 
import jxl.write.WritableWorkbook; 
import jxl.write.WriteException; 
import jxl.write.biff.RowsExceededException;
import org.apache.poi.hssf.usermodel.HSSFCell; 
import org.apache.poi.hssf.usermodel.HSSFCellStyle; 
import org.apache.poi.hssf.usermodel.HSSFRow; 
import org.apache.poi.hssf.usermodel.HSSFSheet; 
import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelOpt { 
public static String gridwriteExcel(String dastr , String title, String fieldtitle, String fileName) throws WriteException{ 

String result="";
String[] ggg= fieldtitle.split(",");

int num= ggg.length ;
WritableWorkbook wwb = null;
 try { 
  wwb = Workbook.createWorkbook(new File(fileName));

} 
catch (IOException e) { 
e.printStackTrace(); } 
if(wwb!=null)
{ 
WritableSheet ws = wwb.createSheet( title , 0);
try { 
ws.mergeCells(0,0,num,0);
  }catch(Exception e)
  { e.printStackTrace();
 return "未能合并单元格"; 
 }
 try {
Label titbe = new Label(0, 0,title ); 
ws.addCell(titbe); 

for(int i=0;i<num;i++)
{

Label labelc = new Label(i,1,ggg[i]); 
ws.addCell( labelc ); 

}
 String[] rows= dastr.split("⊙");

for(int j=0;j<rows.length-1;j++ ){
String[] columns= rows[j].split(",");
int totalco= columns.length;

for(int i=0;i< totalco;i++)
{

Label labelc = new Label(i,j+2, columns[i]); 
ws.addCell( labelc ); 

}
}

} catch (RowsExceededException e) 
{ e.printStackTrace();
 return "未打开工作薄";
 } 
catch (WriteException e) { e.printStackTrace(); 
return "写工作薄失败1";
}
try { //从内存中写入文件中 
wwb.write(); //关闭资源，释放内存 
wwb.close(); 
return "写工作薄成功";
} 
catch (IOException e) { e.printStackTrace();
return "写工作薄失败2"; }
 catch (WriteException e) { e.printStackTrace(); 
 return "写工作薄失败3"; } 
 }
 return "没有工作薄失败";
 } 
public static String formwriteExcel(String dastr , String title, String fileName) throws WriteException{ 

String result="";

WritableWorkbook wwb = null;
 try { 
  wwb = Workbook.createWorkbook(new File(fileName));

} 
catch (IOException e) { 
e.printStackTrace(); 
} 
if(wwb!=null)
{ 
WritableSheet ws = wwb.createSheet( title , 0);
try { 
ws.mergeCells(0,0,4,0);
  }catch(Exception e)
  { e.printStackTrace();
 return "未能合并单元格"; 
 }
try{
 String[] rows= dastr.split(",");

for(int j=0;j<rows.length-1;j++ ){
String[] columns= rows[j].split(":");
int col= Integer.parseInt(columns[1] );

int row= Integer.parseInt(columns[2] );


Label labelc = new Label( col ,row, columns[0]); 
ws.addCell( labelc ); 


}

} catch (RowsExceededException e) 
{ e.printStackTrace();
 return "未打开工作薄";
 } 
catch (WriteException e) { e.printStackTrace(); 
return "写工作薄失败1";
}
try { //从内存中写入文件中 
wwb.write(); //关闭资源，释放内存 
wwb.close(); 
return "写工作薄成功";
} 
catch (IOException e) { e.printStackTrace();
return "写工作薄失败2"; }
 catch (WriteException e) { e.printStackTrace(); 
 return "写工作薄失败3"; } 
 }
 return "没有工作薄失败";
 } 
 
 }
