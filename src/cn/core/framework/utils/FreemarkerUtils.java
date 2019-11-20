package cn.core.framework.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import freemarker.cache.TemplateLoader;
import freemarker.cache.URLTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Create on : 2016年11月3日 上午9:36:29  <br>
 * Description :  Freemarker工具类 <br>
 * @version  v 1.0.0  <br>
 * @author Dave Yu<br>
 */
public class FreemarkerUtils {
	private Logger log = Logger.getLogger(FreemarkerUtils.class);
	/**
	 * Create on : 2016年11月3日 下午2:19:47  <br>
	 * Description : LoadModel 枚举内嵌类 <br>
	 * @version  v 1.0.0  <br>
	 * @author Dave Yu<br>
	 */
	public static enum LoadModel {
		CLASSPATH
	}
	/**
	 * Freemarker配置环境
	 */
	private Configuration cfg = new Configuration();

	/**
	 * Create on :Dave Yu 2016年11月3日 上午9:36:57  <br>
	 * Description : 创建一个Freemarker工具类 <br>
	 * @param templateFileDir 模版文件存放路径，该路径为classPath路径，写作方式以包名的方式展示
	 * @param lm lm
	 */
	public FreemarkerUtils(String templateFileDir, LoadModel lm) {
		TemplateLoader templateLoader = null;
		if (lm.equals(LoadModel.CLASSPATH)) {
			templateLoader = getClassPathTeamplateLoader(templateFileDir);
		}
		cfg.setTemplateLoader(templateLoader);
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:38:02 <br>
	 * Description : getClassPathTeamplateLoader <br>
	 * @param templateFileDir templateFileDir
	 * @return TemplateLoader
	 */
	private TemplateLoader getClassPathTeamplateLoader(
			final String templateFileDir) {
		return new URLTemplateLoader() {
			@Override
			protected URL getURL(String name) {

				String url = new StringBuffer(templateFileDir.replace(".", "/"))
						.append("/").append(name).toString();
				URL _url = FreemarkerUtils.class.getClassLoader().getResource(
						url);
				if (_url == null) {
					name = StringUtils.replace(name, "_"
							+ cfg.getLocale().getLanguage() + "_"
							+ cfg.getLocale().getCountry(), "");
					url = new StringBuffer(templateFileDir.replace(".", "/"))
							.append("/").append(name).toString();
					_url = FreemarkerUtils.class.getClassLoader().getResource(
							url);
				}
				return _url;
			}
		};
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:38:28 <br>
	 * Description : 使用模版生成数据到指定流 <br>
	 * @param templateFileName 模版文件名称
	 * @param writer 输出流
	 * @param model 对象模型
	 */
	public void writer(String templateFileName, Writer writer, Object model) {
		try {
			Template template = cfg.getTemplate(templateFileName, "UTF-8");
			template.process(model, writer);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		} catch (TemplateException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:38:51 <br>
	 * Description : 使用模版生成数据到指定流 <br>
	 * @param templatePath 模版文件路径
	 * @param targetPath 目标文件路径
	 * @param data 数据模型
	 */
	public void writer(String templatePath, String targetPath, Object data) {
		try {
			Template template = cfg.getTemplate(templatePath, "UTF-8");
			System.out.println(templatePath);
			template.process(data, new OutputStreamWriter(new FileOutputStream(
					targetPath)));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (TemplateException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Create on : Dave Yu 2016年11月3日 上午9:39:16 <br>
	 * Description : 根据模版获取生成后的数据，返回生成后的数据为字符串 <br>
	 * @param templateFileName 模版文件名称
	 * @param model 数据模型
	 * @return String 生成成功的数据
	 */
	public String getStringByTemplate(String templateFileName, Object model) {
		StringWriter sw = new StringWriter();
		this.writer(templateFileName, sw, model);
		return sw.toString();
	}
}
