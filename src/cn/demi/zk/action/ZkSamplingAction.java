package cn.demi.zk.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.zk.service.IZkSamplingService;
import cn.demi.zk.vo.ZkSamplingVo;

@Controller("zk.samplingAction")
@RequestMapping("/zk/sampling")
public class ZkSamplingAction extends BaseAction<ZkSamplingVo> {
	final String VIEW_PATH = "/zk/sampling/sampling";
	@Autowired private IZkSamplingService zkSamplingService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<ZkSamplingVo> baseService() {
		return zkSamplingService;
	}
}