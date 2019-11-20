package cn.demi.bi.res.dao;

import java.util.List;

import cn.core.framework.common.dao.IBaseDao;
import cn.core.framework.exception.GlobalException;
import cn.demi.bi.res.po.Consumable;
import cn.demi.bi.res.vo.ConsumableVo;

public interface IConsumableDao extends IBaseDao<Consumable> {
	/**
	 * Create on : Dave Yu 2016年12月14日 上午9:27:53 <br>
	 * Description : 通过属性获取group by下的list <br>
	 * @param pageType
	 * @return List
	 * @throws GlobalException
	 */
	public List<String> selectByPageType(String pageType) throws GlobalException;
	/**
	 * Create on : Dave Yu 2017年1月23日 上午10:20:30 <br>
	 * Description : 返回十个最大数量试剂数据 <br>
	 * @param vo
	 * @return List
	 * @throws GlobalException
	 */
	public List<Object> MaxPayTenConsumable(ConsumableVo vo) throws GlobalException;
}

