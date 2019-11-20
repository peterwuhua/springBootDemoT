package cn.demi.base.system.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.base.system.dao.IEmailDao;
import cn.demi.base.system.po.Email;
/**
 * <strong>Create on : 2016年11月2日 下午5:06:22 </strong> <br>
 * <strong>Description : EmailDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.emailDao")
public class EmailDaoImpl extends BaseDaoImpl<Email> implements IEmailDao {}
