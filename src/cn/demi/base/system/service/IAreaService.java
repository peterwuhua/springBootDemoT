package cn.demi.base.system.service;

import java.util.List;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.AreaVo;
/**
 * <strong>Create on : 2016年11月2日 下午3:19:42 </strong> <br>
 * <strong>Description : 城市编码库service </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
public interface IAreaService extends IBaseService<AreaVo> {

	List<AreaVo> listName(String pname) throws GlobalException;
	
}
