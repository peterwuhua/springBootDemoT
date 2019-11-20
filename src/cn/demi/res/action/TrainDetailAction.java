package cn.demi.res.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.res.service.ITrainDetailService;
import cn.demi.res.vo.TrainDetailVo;

@Controller("res.trainDetailAction")
@RequestMapping("/res/trainDetail")
public class TrainDetailAction extends BaseAction<TrainDetailVo> {
	final String VIEW_PATH = "/res/train/detail/trainDetail";
	@Autowired private ITrainDetailService trainDetailService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<TrainDetailVo> baseService() {
		return trainDetailService;
	}
}