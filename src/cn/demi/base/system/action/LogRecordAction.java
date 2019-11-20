package cn.demi.base.system.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.service.ILogRecordService;
import cn.demi.base.system.vo.LogRecordVo;

@Controller("sys.logRecordAction")
@RequestMapping("/sys/logRecord")
public class LogRecordAction extends BaseAction<LogRecordVo> {
	final String VIEW_PATH = "/sys/log_record/log_record";
	@Autowired private ILogRecordService logRecordService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<LogRecordVo> baseService() {
		return logRecordService;
	}
	@Override
	public GridVo gridData(GridVo gridVo, LogRecordVo v) throws GlobalException {
		return logRecordService.gridData(gridVo,v);
	}
	@ResponseBody
	@RequestMapping(value ="listData")
	public List<LogRecordVo> listData(GridVo gridVo,LogRecordVo v) throws GlobalException{
		return logRecordService.subList();
	}
}