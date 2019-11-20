package cn.demi.base.system.service;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.NoticeVo;
/**
 * <strong>Create on : 2016年11月2日 下午3:23:48 </strong> <br>
 * <strong>Description : 公告service </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface INoticeService extends IBaseService<NoticeVo> {
	
	public int count(String orgId)throws GlobalException;
}
