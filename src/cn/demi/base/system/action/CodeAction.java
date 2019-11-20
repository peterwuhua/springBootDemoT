package cn.demi.base.system.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.service.ICodeService;
import cn.demi.base.system.vo.CodeVo;
/**
 * <strong>Create on : Dave Yu 2016年11月2日 下午2:09:00 </strong> <br>
 * <strong>Description : 岗位action</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Controller("sys.codeAction")
@RequestMapping("sys/code")
public class CodeAction extends BaseAction<CodeVo>{
	final String VIEW_PATH = "/sys/code/code";
	@Autowired private ICodeService codeService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<CodeVo> baseService(){
		return codeService;
	}
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:10:06 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param codeVo codeVo
	 * @return String
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = {"ajaxCodeContent.json","ajaxCodeContent.do"})
	public String ajaxCodeContent(CodeVo codeVo) throws GlobalException{
		return codeService.findByCode(codeVo.getCode()).getContent();
	}
	
	/**
	 * <strong>Create on : Dave Yu 2016年11月2日 下午2:10:06 </strong> <br>
	 * <strong>Description : </strong> <br>
	 * @param codeVo codeVo
	 * @return List
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value =  {"ajaxCodeList.json","ajaxCodeList.do"})
	public List<String> ajaxCodeList(CodeVo codeVo) throws GlobalException{
		return codeService.listByCode(codeVo.getCode());
	}
	@ResponseBody
	@RequestMapping(value =  {"ajaxCodeVoList.json","ajaxCodeVoList.do"})
	public List<String> ajaxCodeVoList(CodeVo codeVo) throws GlobalException{
		return codeService.listByCode(codeVo.getCode());
	}
}
