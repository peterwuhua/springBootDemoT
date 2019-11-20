package cn.demi.base.system.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.QuestionsVo;
/**
 * Create on : 2017年2月21日 下午1:20:07  <br>
 * Description : 常见问题Iservice <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Transactional
public interface IQuestionsService extends IBaseService<QuestionsVo> {
	/**
	 * Create on : Paddy Zhang 2017年2月22日 下午3:08:58 <br>
	 * Description : 查询问题 <br>
	 * @param v
	 * @param pageResult
	 * @return
	 * @throws GlobalException
	 */
	public PageResult getQuestionsList(QuestionsVo v, PageResult pageResult) throws GlobalException;

	/**Create on : Paddy Zhang 2017年3月10日 上午10:29:46 <br>
	 * Description :  <br>
	 * @return List<String[]> modelName集合
	 * @throws GlobalException
	 */
	public List<String[]> listModule() throws GlobalException;
	
}
