package cn.demi.app.sys.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.vo.AppSysMsgVo;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.base.system.vo.SysMsgVo;
import cn.demi.office.vo.WorkPlanVo;

@Transactional
public interface IAppSysMsgService extends IBaseService<SysMsgVo>  {
	
	/**
	 * 获取未读消息数量
	 * @return
	 * @throws GlobalException
	 */
	public int getCount4Wd(ObjVo objVo)throws GlobalException;
	/**
	 * 显示未读消息集合
	 * @return
	 */
	public List<AppSysMsgVo> list4Wd(ObjVo objVo)throws GlobalException;
	/**
	 * 修改 msg
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	String updateMsg(SysMsgVo v) throws GlobalException;
}
