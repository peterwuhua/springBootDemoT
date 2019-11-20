package cn.demi.init.std.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.init.std.vo.MethodVo;

@Transactional
public interface IMethodService extends IBaseService<MethodVo> {
	/**
	 * 根据编号获取方法
	 * @param code
	 * @return
	 * @throws GlobalException
	 */
	public MethodVo findByCode(String code) throws GlobalException;
	/**
	 * 验证检测方法使用的仪器状态是否有效
	 * @param code
	 * @return
	 * @throws GlobalException
	 */
	public String checkMethodApp(String ids) throws GlobalException;
	
}
