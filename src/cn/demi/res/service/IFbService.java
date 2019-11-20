package cn.demi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.res.vo.FbObj;
import cn.demi.res.vo.FbVo;

@Transactional
public interface IFbService extends IBaseService<FbVo> {
	
	/**
	 * 定时任务 
	 * 自动自动 检查 分包单位资质到期
	 *  向负责人 推送消息 
	 * @param v
	 * @return
	 */
	public void excutSchedule()throws GlobalException;
	/**
	 * 自动补全
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	public List<FbObj> list4Full(FbVo v) throws GlobalException;
}
