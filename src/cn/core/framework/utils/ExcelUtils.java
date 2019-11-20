package cn.core.framework.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.core.framework.exception.GlobalException;
import cn.core.framework.log.Logger;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Create on : 2017年5月10日 下午8:05:01  <br>
 * Description : 往excel中放入图片  <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
public class ExcelUtils {
	public static final String SERVER_BASE = ApplicationUtils.getValue("config.server.base").toString().replace("\\", File.separator);
	public static final Logger log = Logger.getLogger(ExcelUtils.class);
	
	
	public static void test(String filePath,String value ,String cellStr) {
	    	InputStream fileInput = null ;
	    	FileOutputStream os = null;
	    	String sourcePath = "";
	    	try {
	    		sourcePath = SERVER_BASE+File.separator + filePath;
				fileInput =new BufferedInputStream(new FileInputStream(sourcePath));
				Workbook jxwb = Workbook.getWorkbook(fileInput);
				Sheet jxsheet = jxwb.getSheet(0);
				Cell jxcell = jxsheet.findCell(cellStr);
				ExcelUtils.test(sourcePath,jxcell.getRow(),jxcell.getColumn(),value);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if(null != fileInput)
						fileInput.close();
					if(null != os )
						os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
	 }
	public static void test(String filePath,int x,int y,String value) {
	    	FileOutputStream os = null;
	    	try {
				// 创建Excel的工作书册 Workbook,对应到一个excel文档
				HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
				HSSFSheet sheet=wb.getSheetAt(0);
				HSSFRow row=sheet.getRow(x);
				HSSFCell cell=row.getCell(y);
				cell.setCellValue(value);
				
				os = new FileOutputStream(filePath);
				wb.write(os);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}

		}
	public static String excelInsertImg(String filePath,String targetPath,Map<String, String> imgMap){
		List<ExcelImage> excelImageList = new ArrayList<ExcelImage>();
		String targetFilePath = "";
		try {
			String fileName = filePath.substring(filePath.lastIndexOf("/")+1,filePath.length());
			String[] sourceArray = StrUtils.split(fileName, "."); 
			String target =targetPath+File.separator+sourceArray[0];
			File file = new File( SERVER_BASE + File.separator+target);
			if(!file.exists()){
				file.mkdirs();
			}
			targetFilePath = target + File.separator+DateUtils.getCurrDateTime()+"."+sourceArray[1];
			String temPath = SERVER_BASE+File.separator+targetFilePath;
			File targetFile = new File(temPath);
			File sourceFile = new File(SERVER_BASE+File.separator+filePath);
			targetFile.createNewFile();
			org.apache.commons.io.FileUtils.copyFile(sourceFile,targetFile);
			for (Map.Entry<String, String> entry : imgMap.entrySet()) {
				ExcelUtils.addIMageList(filePath, excelImageList,entry.getKey(),entry.getValue());
			}
		ExcelPoiUtils.insertImg(temPath,0,excelImageList);
		} catch (IOException | GlobalException e) {
			log.info("eclel插入图片失败，请检查文件路径");
		}
		return targetFilePath;
	}
	/**
	 * Create on : Paddy Zhang 2017年5月11日 上午8:19:53 <br>
	 * Description :  <br>
	 * @param filePath 文件路径
	 * @param imgMap 包含图片的map   key 要查找的单元格占位符       value  要替换的图片
	 */
	public static void excelInsertImg(String filePath,Map<String, String> imgMap){
		List<ExcelImage> excelImageList = new ArrayList<ExcelImage>();
		String sourcePath = SERVER_BASE+File.separator +filePath;
		try {
			for (Map.Entry<String, String> entry : imgMap.entrySet()) {
				ExcelUtils.addIMageList(filePath, excelImageList,entry.getKey(),entry.getValue());
			}
		ExcelPoiUtils.insertImg(sourcePath,0,excelImageList);
		} catch (IOException | GlobalException e) {
			log.info("eclel插入图片失败，请检查文件路径");
		}
	}
	/**
	 * Create on : Paddy Zhang 2017年5月11日 上午8:21:31 <br>
	 * Description :  <br>
	 * @param filePath 文件路径
	 * @param cellStr 单元格占位符
	 * @param imagePath 要替换的文件路径
	 */
	public static void excelInsertImg(String filePath,String cellStr,String imagePath,String status){
		List<ExcelImage> excelImageList = new ArrayList<ExcelImage>();
		String sourcePath = SERVER_BASE+File.separator +filePath;
		try {
			String[] sourceArray = StringUtils.split(filePath, ".");
			String targetPath =  SERVER_BASE+File.separator+sourceArray[0]+"-"+status+"."+sourceArray[1];
			File sourceFile = new File(sourcePath);
			File targetFile = new File(targetPath);
			org.apache.commons.io.FileUtils.copyFile(sourceFile, targetFile);
			ExcelUtils.addIMageList(filePath, excelImageList,cellStr,imagePath);
		ExcelPoiUtils.insertImg(sourcePath,0,excelImageList);
		} catch (IOException | GlobalException e) {
			e.printStackTrace();
			log.info("eclel插入图片失败，请检查文件路径");
		}
	}
	/**
	 * Create on : Paddy Zhang 2017年5月11日 上午8:22:24 <br>
	 * Description :  <br>
	 * @param source 源文件路径
	 * @param excelImageList 图片list
	 * @param cellStr 单元格占位符
	 * @param imagePath 图片路径
	 */
	public static void addIMageList(String source,List<ExcelImage> excelImageList,String cellStr, String imagePath) {
		InputStream fileInput = null;
		String sourcePath ="";
		try {
			sourcePath = SERVER_BASE+File.separator + source;
			fileInput =new BufferedInputStream(new FileInputStream(sourcePath));
			Workbook wb = Workbook.getWorkbook(fileInput);
			Sheet sheet = wb.getSheet(0);
			if(StrUtils.isBlankOrNull(imagePath)){
				imagePath="static/upload/sign/wqm.gif";
			}
			Cell cell = sheet.findCell(cellStr);
			if(null != cell)
				excelImageList.addAll(excelImageList(cell.getColumn(),cell.getRow(), imagePath));
		} catch (BiffException | IOException e) {
			log.info("获取excelImageList 出错，请检查参数是否有误");
			//e.printStackTrace();
		}finally {
			try {
				if(null != fileInput)
				fileInput.close();
			} catch (IOException e) {
				log.info("流关闭异常");
				e.printStackTrace();
			}
		}
	
	}	
	/**
	 * Create on : Paddy Zhang 2017年5月10日 下午8:14:16 <br>
	 * Description :  <br>
	 * @param col
	 * @param row
	 * @param imagePath
	 * @return
	 */
	public static List<ExcelImage> excelImageList(int col,int row,String imagePath){
		List<ExcelImage> excelImageList = new ArrayList<ExcelImage>();
		String newStr = SERVER_BASE+imagePath;
		ExcelImage excelImage = new ExcelImage(col, row, 4, 1,newStr, "");
		excelImageList.add(excelImage);
		return excelImageList;
	}
	public static void copyFile(String reportPath, String status) {
		String[] sourceArray = StringUtils.split(reportPath, ".");
		String sourcePath =  SERVER_BASE+File.separator+sourceArray[0]+"-"+status+"."+sourceArray[1];
		File sourceFile = new File(sourcePath);
		String targetPath = SERVER_BASE+File.separator +reportPath;
		File targetFile = new File(targetPath);
		try {
			org.apache.commons.io.FileUtils.copyFile(sourceFile, targetFile);
		} catch (IOException e) {
			log.info("复制文件出错");
			e.printStackTrace();
		}
	}
		

	public static void main(String[] args) {
		ExcelUtils.excelInsertImg("F:\\project\\eclipse\\WorkSpace\\LABG1604\\src\\main\\webapp\\static\\upload\\report\\samp-report-cj.xls","[审核人]","F:\\project\\eclipse\\WorkSpace\\LABG1604\\src\\main\\webapp\\static\\upload\\sign\\dushaoming.gif","0");
	}
	

}
