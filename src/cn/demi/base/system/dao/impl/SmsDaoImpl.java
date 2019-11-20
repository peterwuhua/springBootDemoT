package cn.demi.base.system.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.base.system.dao.ISmsDao;
import cn.demi.base.system.po.Sms;
/**
 * <strong>Create on : 2016年11月2日 下午5:07:43 </strong> <br>
 * <strong>Description : SmsDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.smsDao")
public class SmsDaoImpl extends BaseDaoImpl<Sms> implements ISmsDao {}
