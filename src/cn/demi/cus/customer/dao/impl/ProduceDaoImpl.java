package cn.demi.cus.customer.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.cus.customer.dao.IProduceDao;
import cn.demi.cus.customer.po.Produce;
/**
 * Create on : 2016年11月15日 下午2:46:03  <br>
 * Description :LevelDaoImpl  <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Repository("cus.produceDao")
public class ProduceDaoImpl extends BaseDaoImpl<Produce> implements IProduceDao {
}
