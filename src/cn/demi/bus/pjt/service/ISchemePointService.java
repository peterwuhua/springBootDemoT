package cn.demi.bus.pjt.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.vo.SchemePointVo;

@Transactional
public interface ISchemePointService extends IBaseService<SchemePointVo> {
	
	List<SchemePointVo> listBySchemeId(String schemeId) throws GlobalException;
	/**
	 * 添加点位
	 * @param schemeId 方案Id
	 *@param projectId 项目Id
	 * @param pointIds 监测点 ids
	 * @throws GlobalException
	 */
	public void addSp(String schemeId,String projectId,String pointIds) throws GlobalException;
}
