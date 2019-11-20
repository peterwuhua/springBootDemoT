package cn.core.framework.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class CsvUtil {

	

	@SuppressWarnings("rawtypes")
	public static File createCSVFile(List exportData, LinkedHashMap map,
			String outPutPath, String fileName) {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			// 定义文件名格式并创建
			csvFile = new File(outPutPath + File.separator + fileName + ".csv");
			if (csvFile.exists()) {
				csvFileOutputStream = new BufferedWriter(new FileWriter(csvFile, true)); // 附加
				csvFileOutputStream.newLine();
				for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
					Object row = iterator.next();
					for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
						java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
						if(null != BeanUtils.getProperty(row,(String) propertyEntry.getKey())){
							String rowValue = BeanUtils.getProperty(row,(String) propertyEntry.getKey()).replace(",", " ");
							csvFileOutputStream.write(rowValue);
						}
						if (propertyIterator.hasNext()) {
							csvFileOutputStream.write(",");
						}
					}
					if (iterator.hasNext()) {
						csvFileOutputStream.newLine();
					}
				}
			} else {
				// UTF-8使正确读取分隔符","
				csvFileOutputStream = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(csvFile),"UTF-8"), 1024);
				System.out.println("csvFileOutputStream：" + csvFileOutputStream);
				// 写入文件头部
				for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
					java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
					csvFileOutputStream.write("" + (String) propertyEntry.getValue() != null ? (String) propertyEntry.getValue() : "" + "");
					csvFileOutputStream.write(",");
				}
				csvFileOutputStream.newLine();
				// 写入文件内容
				for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
					Object row = iterator.next();
					for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
						java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
						if(null != BeanUtils.getProperty(row,(String) propertyEntry.getKey())){
							String rowValue = BeanUtils.getProperty(row,(String) propertyEntry.getKey()).replace(",", " ");
							csvFileOutputStream.write(rowValue);
						}
						if (propertyIterator.hasNext()) {
							csvFileOutputStream.write(",");
						}
					}
					if (iterator.hasNext()) {
						csvFileOutputStream.newLine();
					}
				}
				csvFileOutputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

}
