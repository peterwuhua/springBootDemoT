package cn.core.framework.utils.code;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

import cn.core.framework.utils.ApplicationUtils;
import cn.core.framework.utils.FreemarkerUtils;
import cn.core.framework.utils.code.bean.EntityInfoBean;

public class CreateCodeUtils {
	private static Logger log = Logger.getLogger(CreateCodeUtils.class);
	public static String BASE_PROJECT_PATH = ApplicationUtils.getValue(
			"config.project.baseDir").toString();
	public static String BASE_PROJECT_NAME = ApplicationUtils.getValue(
			"config.project.basePackage").toString();
	public static String BASE_PROJECT_PACKAGE = BASE_PROJECT_NAME + ".";
	public static String TEMPLATE_FILE_PACKAGE = "resources.template";

	public static String DAO_FTL_FILE_NAME = "Dao.ftl";
	public static String DAO_IMPL_FTL_FILE_NAME = "DaoImpl.ftl";
	public static String ISERVICE_FTL_FILE_NAME = "IService.ftl";
	public static String SERVICE_IMPL_FTL_FILE_NAME = "ServiceImpl.ftl";
	public static String CONTROLLER_FTL_FILE_NAME = "Action.ftl";
	public static String JSP_CONTROLLER_FTL_FILE_NAME = "JSPAction.ftl";
	public static String VO_FTL_FILE_NAME = "Vo.ftl";

	public static void autoCreateCode(Class<? extends Object> entityCls,
			Boolean isReplace, ActionType at) {
		EntityInfoBean eib = LoadEntityInfo.load(entityCls);
		FreemarkerUtils fts = new FreemarkerUtils(TEMPLATE_FILE_PACKAGE,
				FreemarkerUtils.LoadModel.CLASSPATH);

		createDAO(isReplace, eib, fts);
		createDAOImpl(isReplace, eib, fts);
		createIService(isReplace, eib, fts);
		createServiceImpl(isReplace, eib, fts);
		if (ActionType.JSP.equals(at)) {
			createController4Jsp(isReplace, eib, fts);
		} else {
			createController(isReplace, eib, fts);
		}
		createVo(isReplace, eib, fts);
		// createJsp(isReplace,eib,fts);
		// createJs(isReplace,eib,fts);
	}

	private static void createVo(Boolean isReplace, EntityInfoBean eib,
			FreemarkerUtils fts) {
		File baseFile = new File(BASE_PROJECT_PATH);
		File voFileDir = new File(baseFile, eib.getVoPackageName().replace(".",
				File.separator));
		if (!voFileDir.exists()) {
			voFileDir.mkdirs();
		}
		File daoFile = new File(voFileDir, eib.getSimpleEntityName()
				+ "Vo.java");
		if (daoFile.exists() && isReplace) {
			daoFile.delete();
		}
		writerFile(eib, fts, daoFile, VO_FTL_FILE_NAME);
	}

	private static void createDAOImpl(Boolean isReplace, EntityInfoBean eib,
			FreemarkerUtils fts) {
		File baseFile = new File(BASE_PROJECT_PATH);
		File daoFileDir = new File(baseFile, eib.getDaoImplPackageName()
				.replace(".", File.separator));
		if (!daoFileDir.exists()) {
			daoFileDir.mkdirs();
		}
		File daoFile = new File(daoFileDir, eib.getSimpleEntityName()
				+ "DaoImpl.java");
		if (daoFile.exists() && isReplace) {
			daoFile.delete();
		}
		writerFile(eib, fts, daoFile, DAO_IMPL_FTL_FILE_NAME);

	}

	private static void createController(Boolean isReplace, EntityInfoBean eib,
			FreemarkerUtils fts) {
		File baseFile = new File(BASE_PROJECT_PATH);
		File daoFileDir = new File(baseFile, eib.getControllerPackageName()
				.replace(".", File.separator));
		if (!daoFileDir.exists()) {
			daoFileDir.mkdirs();
		}
		File xmlFile = new File(daoFileDir, eib.getSimpleEntityName()
				+ "Action.java");
		if (xmlFile.exists() && isReplace) {
			xmlFile.delete();
		}
		writerFile(eib, fts, xmlFile, CONTROLLER_FTL_FILE_NAME);
	}

	private static void createController4Jsp(Boolean isReplace,
			EntityInfoBean eib, FreemarkerUtils fts) {
		File baseFile = new File(BASE_PROJECT_PATH);
		File daoFileDir = new File(baseFile, eib.getControllerPackageName()
				.replace(".", File.separator));
		if (!daoFileDir.exists()) {
			daoFileDir.mkdirs();
		}
		File xmlFile = new File(daoFileDir, eib.getSimpleEntityName()
				+ "Action.java");
		if (xmlFile.exists() && isReplace) {
			xmlFile.delete();
		}
		writerFile(eib, fts, xmlFile, JSP_CONTROLLER_FTL_FILE_NAME);
	}

	private static void createServiceImpl(Boolean isReplace,
			EntityInfoBean eib, FreemarkerUtils fts) {
		File baseFile = new File(BASE_PROJECT_PATH);
		File daoFileDir = new File(baseFile, eib.getServiceImplPackageName()
				.replace(".", File.separator));
		if (!daoFileDir.exists()) {
			daoFileDir.mkdirs();
		}
		File xmlFile = new File(daoFileDir, eib.getSimpleEntityName()
				+ "ServiceImpl.java");
		if (xmlFile.exists() && isReplace) {
			xmlFile.delete();
		}
		writerFile(eib, fts, xmlFile, SERVICE_IMPL_FTL_FILE_NAME);
	}

	private static void createIService(Boolean isReplace, EntityInfoBean eib,
			FreemarkerUtils fts) {
		File baseFile = new File(BASE_PROJECT_PATH);
		File daoFileDir = new File(baseFile, eib
				.getServiceInterfacePackageName().replace(".", File.separator));
		if (!daoFileDir.exists()) {
			daoFileDir.mkdirs();
		}
		File xmlFile = new File(daoFileDir, "I" + eib.getSimpleEntityName()
				+ "Service.java");
		if (xmlFile.exists() && isReplace) {
			xmlFile.delete();
		}
		writerFile(eib, fts, xmlFile, ISERVICE_FTL_FILE_NAME);
	}

	private static void createDAO(Boolean isReplace, EntityInfoBean eib,
			FreemarkerUtils fts) {

		File baseFile = new File(BASE_PROJECT_PATH);
		File daoFileDir = new File(baseFile, eib.getDaoInterfacePackageName()
				.replace(".", File.separator));
		if (!daoFileDir.exists()) {
			daoFileDir.mkdirs();
		}
		File daoFile = new File(daoFileDir, "I" + eib.getSimpleEntityName()
				+ "Dao.java");
		if (daoFile.exists() && isReplace) {
			daoFile.delete();
		}
		writerFile(eib, fts, daoFile, DAO_FTL_FILE_NAME);
	}

	private static void writerFile(EntityInfoBean eib, FreemarkerUtils fts,
			File file, String tamplateFileName) {
		Writer writer = null;
		try {
			file.createNewFile();
			writer = new OutputStreamWriter(new FileOutputStream(file));
			fts.writer(tamplateFileName, writer, eib);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

}
