package cn.core.framework.utils.export;

import java.io.File;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.core.framework.exception.GlobalException;
import cn.core.framework.log.Logger;
import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.importx.ImportUtils;
import net.sf.jxls.transformer.XLSTransformer;
/**
 * Create on : 2016年11月3日 上午10:18:55  <br>
 * Description : 导出工具类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public final class ExportUtils {

	public static final String SERVER_BASE = ApplicationUtils.getValue("config.server.base").toString().replace("\\", File.separator);
	public static final String TEMPLATE_PATH = SERVER_BASE + ApplicationUtils.getValue("config.upload.export.template").toString().replace("\\", File.separator);
	public static final String TEMP_PATH = SERVER_BASE + ApplicationUtils.getValue("config.upload.export.temp").toString().replace("\\", File.separator);

	/**
	 * Create on : Dave Yu 2016年11月3日 上午10:19:12 <br>
	 * Description : 通用导出方法 <br>
	 * @param source 模板文件路径
	 * @param target 目标文件路径
	 * @param beansMaps 数据对象
	 * @return String
	 * @throws GlobalException
	 */
	public static String export(String source, String target,
			Map<String, Object> beansMaps) throws GlobalException {
		String[] sourceArrays = StrUtils.split(source, '.');
		source = TEMPLATE_PATH + File.separator + source;
		target = TEMP_PATH + File.separator + sourceArrays[0] + "-"
				+ System.currentTimeMillis() + "." + sourceArrays[1];
		File file = new File(TEMP_PATH); 
		if (!file.exists()) 
			   file.mkdirs();
		XLSTransformer transformer = new XLSTransformer();
		try {
			transformer.transformXLS(source, beansMaps, target);
		} catch (Exception e) {
			Logger.getLogger(ExportUtils.class).error("导出excel文件出错 ", e);
		}
		return target;
	}

	/**
	 * <strong>创建信息:2015年7月10日 上午9:35:19 </strong> <br>
	 * <strong>概要 : 存储导入模板文件</strong> <br>
	 * <strong>修改记录 : (修改人： ;时间： ;目的: ;)</strong> <br>
	 * 
	 * @param file
	 *            模板文件对象
	 * @param templateName
	 *            模板文件名
	 * @return 文件存储路径
	 */
	public static String uploadTemplate(MultipartFile file, String templateName) {
		File targetFile = template(templateName);
		saveFile(file, targetFile);
		return targetFile.getAbsolutePath();
	}

	/**
	 * <strong>创建信息:2015年7月10日 下午2:24:51 </strong> <br>
	 * <strong>概要 : 模板文件对象</strong> <br>
	 * <strong>修改记录 : (修改人： ;时间： ;目的: ;)</strong> <br>
	 * 
	 * @param templateName
	 *            模板名称
	 * @return File 对象
	 */
	public static File template(String templateName) {
		return new File(TEMPLATE_PATH, templateName);
	}

	/**
	 * <strong>创建信息:2015年7月10日 下午2:23:01 </strong> <br>
	 * <strong>概要 : 根据模板名称获取模板路径</strong> <br>
	 * <strong>修改记录 : (修改人： ;时间： ;目的: ;)</strong> <br>
	 * 
	 * @param templateName
	 * @return String
	 */
	public static String getTemplatePath(String templateName) {
		return template(templateName).getAbsolutePath();
	}

	/**
	 * <strong>创建信息: 2015年7月10日 上午9:36:11 </strong> <br>
	 * <strong>概要 : 文件存储</strong> <br>
	 * <strong>修改记录 : (修改人： ;时间： ;目的: ;)</strong> <br>
	 * 
	 * @param file
	 *            原始文件对象
	 * @param targetFile
	 *            文件存储对象
	 */
	private static void saveFile(MultipartFile file, File targetFile) {
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			Logger.getLogger(ImportUtils.class).error("文件存储出错", e);
		}
	}

	/**
	 * <strong>创建信息:2015年7月10日 上午9:36:46 </strong> <br>
	 * <strong>概要 : 文件存储</strong> <br>
	 * <strong>修改记录 : (修改人： ;时间： ;目的: ;)</strong> <br>
	 * 
	 * @param file
	 *            临时文件对象
	 * @return 
	 * 			String
	 */
	public static String uploadTemp(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		File targetFile = new File(TEMP_PATH, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			Logger.getLogger(ImportUtils.class).error(
					"文件导出，临时文件存储出错" + fileName, e);
		}

		return targetFile.getAbsolutePath();
	}
}
