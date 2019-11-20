package cn.core.framework.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.core.framework.log.Logger;

public class FileUtils extends org.apache.commons.io.FileUtils {

	private static final Logger log = Logger.getLogger(FileUtils.class);

	/**
	 * 获取指定目录下的所有文件 递归
	 * 
	 * @param directory
	 *            需要查找的目录
	 * @return 目录下的所有文件
	 */
	public static List<File> getFiles4Directory(String directory) {
		List<File> fileList = new ArrayList<File>();
		File file = FileUtils.getDirectory(directory);
		File[] fileArray = null;
		if (ObjectUtils.isNotNull(file)) {
			fileArray = file.listFiles();
		}
		if (org.apache.commons.lang3.ArrayUtils.isEmpty(fileArray)) {
			fileArray = null;
		} else {
			for (File subFile : fileArray) {
				if (subFile.isFile()) {
					fileList.add(subFile);
				} else {
					List<File> subFileList = FileUtils
							.getFiles4Directory(subFile.getPath());
					fileList.addAll(subFileList);
				}
			}
		}

		return fileList;
	}

	/**
	 * 根据路径获取目录文件
	 * 
	 * @param path
	 *            目录的路径字符串
	 * @return 目录的File对象
	 */
	public static File getDirectory(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				return file;
			} else {
				log.error(String.format("[%s]文件不是目录类型", path));
				return null;
			}
		} else {
			log.error(String.format("[%s]文件不存在", path));
			return null;
		}
	}

	/**
	 * 获取文件路径 根据包名称
	 * 
	 * @param packageName
	 *            依据的包的名称
	 * @return 文件的路径
	 */
	public static String getPath4PackageName(String packageName) {
		return FileUtils.class.getClassLoader()
				.getResource(packageName.replace(".", "/")).getPath();
	}

	/**
	 * 加载指定路径的文件为InputStream
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 文件关联的Stream
	 */
	public static InputStream loadClassPathFile2Stream(String filePath) {
		return FileUtils.class.getClassLoader().getResourceAsStream(filePath);
	}

	/**
	 * 创建指定文件，如果文件不存在
	 * 
	 * @param file
	 */
	public static void createFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 创建指定文件夹，如果文件夹不存在
	 * 
	 * @param file
	 */
	public static void createDir(File file) {
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	/**
	 * Create on : Paddy Zhang 2016年11月26日 下午4:00:00 <br>
	 * Description : 计算文件大小 <br>
	 * @param size
	 * @return
	 */
	public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
 
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

}
