package cn.demi.bus.pjt.service;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.vo.SurveyVo;

@Transactional
public interface ISurveyService extends IBaseService<SurveyVo> {
 
	/**
	 * 已办记录
	 * @param gridVo
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	GridVo gridDatad(GridVo gridVo, SurveyVo v) throws GlobalException;
	/**
	 * 获取踏勘信息
	 * @param projectId 项目Id
	 */
	SurveyVo findByProjectId(String projectId) throws GlobalException;
}
