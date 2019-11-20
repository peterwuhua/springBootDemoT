package cn.demi.base.system.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.service.IExportxService;
import cn.demi.base.system.vo.ExportxVo;
/**
 * <strong>Create on : 2017年4月20日 下午7:46:57 </strong> <br>
 * <strong>Description : </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
@Controller("sys.ExportxAction")
@RequestMapping("sys/exportx")
public class ExportxAction extends BaseAction<ExportxVo>{
	final String VIEW_PATH = "/sys/exportx/exportx";
	@Autowired private IExportxService exportxService;
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	@Override
	public IBaseService<ExportxVo> baseService(){
		return exportxService;
	}
	
	@RequestMapping(value="ajaxAdd.do")
	public ExportxVo ajaxAdd(ExportxVo v) throws GlobalException {
		exportxService.add(v);
		return v;
	}
	
	@RequestMapping(value="ajaxTempList.do")
	public List<ExportxVo> ajaxTempList(String busType) throws GlobalException {
		return exportxService.list(busType);
	}
	
}
