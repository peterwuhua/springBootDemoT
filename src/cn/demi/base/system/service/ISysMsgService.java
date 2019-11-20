package cn.demi.base.system.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.demi.base.system.vo.SysMsgVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;

@Transactional
public interface ISysMsgService extends IBaseService<SysMsgVo> {
	/**
	 * 获取未读消息数量
	 * @return
	 * @throws GlobalException
	 */
	public int getCount4Wd()throws GlobalException;
	/**
	 * 显示未读消息集合
	 * @return
	 */
	public List<SysMsgVo> list4Wd()throws GlobalException;
	/**
	 * 首页显示未读消息和最近一个月消息集合
	 * @return
	 */
	public List<SysMsgVo> list4Show()throws GlobalException;
}
