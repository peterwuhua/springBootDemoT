package cn.demi.app.wenjian.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.wenjian.service.CateService;
import cn.demi.app.wenjian.vo.AppCate;
import cn.demi.doc.dao.ICategoryDao;
import cn.demi.doc.po.Category;

@Service("app.cateService")
public class CateServiceImpl implements CateService {
	@Autowired
	private ICategoryDao categoryDao;

	/**
	 * app 文件路径
	 */
	
	@Override
	public List<AppCate> list(String id) throws GlobalException {
		List<AppCate> outList = new ArrayList<>();
		if (id == null || id.equals("null") || id.equals("")) {
			outList.clear();
			Category category = categoryDao.findRoot();
			AppCate appCate = new AppCate();
			appCate.setId(category.getId());
			appCate.setName(category.getName());
			appCate.setPath(category.getPath());
			outList.add(appCate);
		} else {
			outList.clear();
			List<Category> list = categoryDao.listByPid(id);
			for (int i = 0; i < list.size(); i++) {
				AppCate appCate = new AppCate();
				appCate.setName(list.get(i).getName());
				appCate.setId(list.get(i).getId());
				appCate.setPath(list.get(i).getPath());
				appCate.setPid(id);
				outList.add(appCate);
			}
		}

		return outList;
	}

}
