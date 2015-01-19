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
/** * 生成一个Excel文件 jxl * @param fileName 要生成的Excel文件名 * @jxl.jar 版本：2.6 */
public static String writeExcel(String fileName){ 
String result="";
WritableWorkbook wwb = null;
 try { //首先要使用Workbook类的工厂方法创 建一个可写入的工作薄(Workbook)对象
  wwb = Workbook.createWorkbook(new File(fileName));

} 
catch (IOException e) { 
e.printStackTrace(); } 
if(wwb!=null)
{ //创建一个可写入的工作表 //Workbook的createSheet方法有两个参 数，第一个是工作表的名称，第二个是工作表在 工作薄中的位置 
WritableSheet ws = wwb.createSheet("工作表名称", 0);

//下面开始添加单元格 
for(int i=0;i<10;i++){ for(int j=0;j<5;j++){ 
//这里需要注意的是，在Excel中，第 一个参数表示列，第二个表示行 
Label labelC = new Label(j, i, "这是 第"+(i+1)+"行，第"+(j+1)+"列"); 
try { //将生成的单元格添加到工作表中 
ws.addCell(labelC); 
} catch (RowsExceededException e) 
{ e.printStackTrace();
 return "未打开工作薄";
 } 
catch (WriteException e) { e.printStackTrace(); 
return "写工作薄失败1";
}

} }

try { //从内存中写入文件中 
wwb.write(); //关闭资源，释放内存 
wwb.close(); 
return "写工作薄成功";
} 
catch (IOException e) { e.printStackTrace();
return "写工作薄失败2"; }
 catch (WriteException e) { e.printStackTrace(); 
 return "写工作薄失败3"; } }
 return "没有工作薄失败";
 } 
 }

