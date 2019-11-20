package cn.demi.base.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.dao.ITemplateDao;
import cn.demi.base.system.po.Template;
import cn.demi.base.system.service.ITemplateService;
import cn.demi.base.system.vo.TemplateVo;
/**
 * <strong>Create on : 2016年11月2日 下午5:13:06 </strong> <br>
 * <strong>Description : TemplateServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.templateService")
public class TemplateServiceImpl extends BaseServiceImpl<TemplateVo, Template> implements
		ITemplateService {
	@Autowired private ITemplateDao templateDao;
	@Override
	public void update4Grid(TemplateVo v) throws GlobalException {
		Template po = templateDao.findById(v.getId());
		po.setCode(v.getCode());
		po.setName(v.getName());
		po.setSort(v.getSort());
		templateDao.update(po);
	}
	
	@Override
	public void updateFile(String id) throws GlobalException {
		Template po = templateDao.findById(id);
		templateDao.update(po);
	}
}
