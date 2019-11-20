package cn.demi.base.system.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.base.system.dao.INoticeDao;
import cn.demi.base.system.po.Notice;
/**
 * <strong>Create on : 2016年11月2日 下午5:07:01 </strong> <br>
 * <strong>Description : NoticeDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.noticeDao")
public class NoticeDaoImpl extends BaseDaoImpl<Notice> implements INoticeDao {}
