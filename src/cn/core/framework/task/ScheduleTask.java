package cn.core.framework.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.core.framework.exception.GlobalException;
import cn.core.framework.log.Logger;
import cn.demi.bus.pjt.service.ISchemeService;
import cn.demi.cus.contract.service.IContractService;
import cn.demi.office.service.IDgTjService;

/**
 * 定时任务
 * @author QuJunLong
 *
 */
@Component("ScheduleTask")
public class ScheduleTask {
	public Logger log = Logger.getLogger(this.getClass());
	@Autowired 
	private ISchemeService schemeService;
	@Autowired 
	private IDgTjService dgTjService;
	@Autowired
	private IContractService contractService;
	
	
	
	
	@Scheduled(cron = "0 0 9 * * ?")//每天早上九点执行
    public void job() throws GlobalException {
		schemeService.executeSchedule();
		dgTjService.excutSchedule();
    }
	
	@Scheduled(cron = "0 0 9 * * ?") //每天早上九点执行
    public void contractJob() throws GlobalException {
		contractService.executeSchedule();
    }
	
	
}
