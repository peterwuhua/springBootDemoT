package cn.demi.bi.res.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.bi.res.vo.ConsumableVo;

@Transactional
public interface IConsumableService extends IBaseService<ConsumableVo> {
	/**
	 * Create on : Dave Yu 2016年12月14日 上午9:26:18 <br>
	 * Description : 通过属性获取group by下的list <br>
	 * @param pageType
	 * @return List
	 * @throws GlobalException
	 */
	public List<String> selectByPageType(String pageType) throws GlobalException;
	/**
	 * Create on : Dave Yu 2017年1月23日 上午10:19:14 <br>
	 * Description : 返回十个最大数量耗材数据 <br>
	 * @param vo
	 * @return List
	 * @throws GlobalException
	 */
	public List<Object> MaxPayTenConsumable(ConsumableVo vo) throws GlobalException;
}
