package com.jy.common.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Excel导入工具类 2017/02/08
 * 
 * @author lisongbai
 *
 */
public class ExcelImportUtil {
	// excel2003扩展名
	public static final String EXTE_EXCEL2003 = ".xls";
	// excel2007扩展名
	public static final String EXTE_EXCEL2007 = ".xlsx";

	/**
	 * 
	 * @param reader
	 *            IExcelRowReader
	 * @param fileName
	 *            excel文件路径
	 * @throws Exception
	 */
	public static void readExcel(IExcelRowReader reader, String fileName) throws Exception {

		if (fileName.endsWith(EXTE_EXCEL2003)) {
			// 处理excel2003文件
			Excel2003Reader exceXls = new Excel2003Reader();
			exceXls.setRowReader(reader);
			exceXls.process(fileName);
		} else if (fileName.endsWith(EXTE_EXCEL2007)) {
			// 处理excel2007文件
			Excel2007Reader exce2007 = new Excel2007Reader();
			exce2007.setRowReader(reader);
			exce2007.process(fileName);
		} else {
			throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
		}
	}

	/**
	 * Excel2007专用
	 * 
	 * @param reader
	 *            IExcelRowReader
	 * @param fileName
	 *            excel文件路径
	 * @param sheetId
	 *            工作簿id 如只读第一个 1
	 * @throws Exception
	 */
	public static void readExcel2007(IExcelRowReader reader, String fileName, Integer sheetId) throws Exception {
		if (!fileName.endsWith(EXTE_EXCEL2007))
			throw new Exception("文件格式错误，只支持Excel2007，fileName的扩展名只能是xlsx。");
		Excel2007Reader exce2007 = new Excel2007Reader();
		exce2007.setRowReader(reader);
		if (sheetId == null)
			exce2007.process(fileName);
		else
			exce2007.processOneSheet(fileName, sheetId);
	}

	/**
     * 通过文件流构建DOM进行解析
     * @param ins
     * @param headRowCount   跳过读取的表头的行数
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     */
    public static List<List<String>> processDOMReadSheet(InputStream ins,int headRowCount) throws InvalidFormatException, IOException {
        Workbook workbook = WorkbookFactory.create(ins);
        return processDOMRead(workbook, headRowCount);
    }
    
    /**
     * 采用DOM的形式进行解析
     * @param filename
     * @param headRowCount   跳过读取的表头的行数
     * @return
     * @throws IOException 
     * @throws InvalidFormatException 
     * @throws Exception
     */
    public static List<List<String>> processDOMReadSheet(String filename,int headRowCount) throws InvalidFormatException, IOException {
    	Workbook workbook = WorkbookFactory.create(new File(filename));
        return processDOMRead(workbook, headRowCount);
    }
    
	private static List<List<String>> processDOMRead(Workbook workbook,int headRowCount) throws InvalidFormatException, IOException {
		List<List<String>> list = new ArrayList<List<String>>();
        Sheet sheet = workbook.getSheetAt(0);
        //行数
        int endRowIndex = sheet.getLastRowNum();
        Row row = null;
        List<String> rowList = null;
        //表头列数
        int colsNum = sheet.getRow(0).getLastCellNum();
        for(int i=headRowCount; i<=endRowIndex; i++){
            rowList = new ArrayList<String>();
            row = sheet.getRow(i);
            for(int j=0; j<colsNum;j++){
                if(null==row.getCell(j)){
                    rowList.add("");
                    continue;
                }
                int dataType = row.getCell(j).getCellType();
                if(dataType == Cell.CELL_TYPE_NUMERIC){
                	if (HSSFDateUtil.isCellDateFormatted(row.getCell(j))) {    //判断是日期类型  
                        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");  
                        Date dt = HSSFDateUtil.getJavaDate(row.getCell(j).getNumericCellValue());//获取成DATE类型     
                        rowList.add(dateformat.format(dt));  
                    }else{  
                    	rowList.add(String.valueOf(row.getCell(j).getNumericCellValue()));  
                    }  
                }else if(dataType == Cell.CELL_TYPE_BLANK){
                    rowList.add("");
                }else if(dataType == Cell.CELL_TYPE_ERROR){
                    rowList.add("");
                }else{
                    //这里的去空格根据自己的情况判断
                	String valString = "";
                	try{
                		valString = row.getCell(j).getStringCellValue();
                	}catch(IllegalStateException e){
                		valString = String.valueOf(row.getCell(j).getNumericCellValue());
                	}
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(valString);
                    valString = m.replaceAll("");
                    //去掉狗日的不知道是啥东西的空格
                    if(valString.indexOf(" ")!=-1){
                        valString = valString.substring(0, valString.indexOf(" "));
                    }
                    
                    rowList.add(valString.trim());
                }
            }
            list.add(rowList);
        }
        return list;
    }

	
}
