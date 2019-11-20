package cn.demi.base.system.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.base.system.dao.ITemplateDao;
import cn.demi.base.system.po.Template;
/**
 * <strong>Create on : 2016年11月2日 下午5:07:50 </strong> <br>
 * <strong>Description : TemplateDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.templateDao")
public class TemplateDaoImpl extends BaseDaoImpl<Template> implements ITemplateDao {}
