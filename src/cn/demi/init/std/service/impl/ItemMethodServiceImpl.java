package cn.demi.init.std.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.bus.task.vo.TaskPointVo;
import cn.demi.init.std.dao.IItemDao;
import cn.demi.init.std.dao.IItemMethodDao;
import cn.demi.init.std.dao.IMethodDao;
import cn.demi.init.std.po.Item;
import cn.demi.init.std.po.ItemMethod;
import cn.demi.init.std.po.Method;
import cn.demi.init.std.service.IItemMethodService;
import cn.demi.init.std.vo.ItemMethodVo;
import cn.demi.init.std.vo.MethodVo;

@Service("init.item_methodService")
public class ItemMethodServiceImpl extends BaseServiceImpl<ItemMethodVo, ItemMethod> implements IItemMethodService {
	@Autowired
	private IItemMethodDao itemMethodDao;
	@Autowired
	private IItemDao itemDao;
	@Autowired
	private IMethodDao methodDao;

	@Override
	public boolean addMethod(ItemMethodVo v) throws GlobalException {
		String[] ids = v.getMethodVo().getIds().split(",");
		for (String mid : ids) {
			if (mid.trim().equals("")) {
				continue;
			}
			ItemMethod p = itemMethodDao.findByMethodIdAndItemId(mid, v.getItemVo().getId());
			if (p == null) {
				p = new ItemMethod();
				Item item = itemDao.findById(v.getItemVo().getId());
				p.setItem(item);
				Method method = methodDao.findById(mid);
				p.setMethod(method);
				itemMethodDao.add(p);
				// 更新方法里的项目信息
				List<String> midList = new ArrayList<String>();
				List<String> mNameList = new ArrayList<String>();
				List<ItemMethod> imList = itemMethodDao.listByMethodId(method.getId());
				for (ItemMethod itemMethod : imList) {
					midList.add(itemMethod.getItem().getId());
					mNameList.add(itemMethod.getItem().getName());
				}
				method.setItemIds(String.join(",", midList));
				method.setItemNames(String.join(",", mNameList));
				methodDao.update(method);
			}
		}
		return true;
	}

	@Override
	public void update(ItemMethodVo v) throws GlobalException {
		ItemMethod p = itemMethodDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.setCyAppId(v.getCyAppId());
		p.setCyAppName(v.getCyAppName());
		p.setCtId(v.getCtId());
		p.setCtName(v.getCtName());
		p.setBcsj(v.getBcsj());
		p.setCctj(v.getCctj());
		p.setCyll(v.getCyll());
		p.setCysc(v.getCysc());
		p.setCytj(v.getCytj());
		p.setRemark(v.getRemark());
		itemMethodDao.update(p);
	}

	@Override
	public List<ItemMethodVo> list(ItemMethodVo v) throws GlobalException {
		String jpql = "FROM " + itemMethodDao.getEntityName(ItemMethod.class) + " WHERE isDel='" + Po.N + "'";
		if (v.getItemVo() != null && v.getItemVo().getId() != null) {
			jpql += " AND item.id='" + v.getItemVo().getId() + "'";
		}
		if (v.getMethodVo() != null && v.getMethodVo().getId() != null) {
			jpql += " AND method.id='" + v.getMethodVo().getId() + "'";
		}
		List<ItemMethod> imList = itemMethodDao.list(jpql);
		return toVoList(imList);
	}

	@Override
	public List<ItemMethodVo> listMethod4AllUser(String itemId) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("item.id", QueryCondition.EQ, itemId));
		List<ItemMethod> imList = itemMethodDao.list(queryConditions);
		List<ItemMethodVo> methodList = new ArrayList<>();
		if (null != imList) {
			for (ItemMethod im : imList) {
				ItemMethodVo vo = new ItemMethodVo();
				vo = vo.toVo(im);
				methodList.add(vo);
			}
		}
		return methodList;
	}

	@Override
	public List<MethodVo> findMethodList(String itemId) throws GlobalException {
		Item item = itemDao.findById(itemId);
		if (item.getParent() != null) {
			itemId = item.getParent().getId();
		}
		String jpql = "FROM " + itemMethodDao.getEntityName(ItemMethod.class) + " WHERE isDel='" + Po.N + "' AND item.id='" + itemId + "'";
		jpql += " group by method.id";
		List<ItemMethod> imList = itemMethodDao.list(jpql);
		List<MethodVo> methodList = new ArrayList<>();
		if (null != imList) {
			for (ItemMethod im : imList) {
				MethodVo vo = new MethodVo();
				vo = vo.toVo(im.getMethod());
				methodList.add(vo);
			}
		}
		return methodList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, ItemMethodVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "sort".equals(gridVo.getSidx())) {
			pageResult.setOrder(OrderCondition.ORDER_ASC);
			pageResult.setOrderColumn("item.sort");
		} else {
			pageResult.setOrder(gridVo.getSord());
			pageResult.setOrderColumn(gridVo.getSidx());
		}
		pageResult = itemMethodDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<ItemMethod>) pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}

	@Override
	public void delete(String... ids) throws GlobalException {
		List<ItemMethod> imList = itemMethodDao.listByIds(ids);
		for (ItemMethod itemMethod : imList) {
			Method m = itemMethod.getMethod();
			itemMethodDao.delete(itemMethod);
			// 更新方法里的项目信息
			List<String> midList = new ArrayList<String>();
			List<String> mNameList = new ArrayList<String>();
			List<ItemMethod> list = itemMethodDao.listByMethodId(m.getId());
			for (ItemMethod im : list) {
				midList.add(im.getItem().getId());
				mNameList.add(im.getItem().getName());
			}
			m.setItemIds(String.join(",", midList));
			m.setItemNames(String.join(",", mNameList));
			methodDao.update(m);
		}
	}

	@Override
	public List<ItemMethodVo> listByItemIds(String itemIds) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		queryConditions.add(new QueryCondition("item.id in('" + itemIds.replace(",", "','") + "')"));
		queryConditions.add(new QueryCondition("method.isDel =" + Po.N + ""));
		List<OrderCondition> orderConditions = new ArrayList<OrderCondition>();
		orderConditions.add(new OrderCondition("sort", OrderCondition.ORDER_ASC));
		List<ItemMethod> imList = itemMethodDao.list(queryConditions, orderConditions);
		List<ItemMethodVo> methodList = new ArrayList<>();
		if (null != imList) {
			for (ItemMethod im : imList) {
				ItemMethodVo vo = new ItemMethodVo();
				vo = vo.toVo(im);
				if (StrUtils.isBlankOrNull(im.getCtId()) && StrUtils.isBlankOrNull(im.getCyAppId()) && StrUtils.isBlankOrNull(im.getCyll())
						&& StrUtils.isBlankOrNull(im.getCysc()) && StrUtils.isBlankOrNull(im.getCytj()) && StrUtils.isBlankOrNull(im.getCctj())) {
					continue;
				}
				methodList.add(vo);
			}
		}
		return methodList;
	}

	/**
	 * 
	 * <p>
	 * Title: getEnvAndDesc
	 * </p>
	 * <p>
	 * Description: 获取环境运输条件以及备注
	 * </p>
	 * 
	 * @param id
	 * @return
	 * @throws GlobalException
	 * @see cn.demi.init.std.service.IItemMethodService#getEnvAndDesc(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TaskPointVo> getEnvAndDesc(List<TaskPointVo> taskPs) throws GlobalException {
		List<TaskPointVo> taskPvos = new ArrayList<>();
		for (TaskPointVo pvo : taskPs) // 每个采样点位对应多个检测项目
		{
			String itemids = pvo.getItemIds();// 多个用逗号进行拼接
			String itemIDs = "'" + itemids.replace(",", "','") + "'";
			String sql = "select cctj,remark from v_init_item_method  where item_id in (" + itemIDs + ")  ";
			List<Object[]> itemList = itemMethodDao.queryBySql(sql);
			if (null != itemList) {
				StringBuffer cctjs = new StringBuffer();
				StringBuffer remarks = new StringBuffer();
				for (Object[] obj : itemList) { // 循环检测项目方法，合并环境运输条件以及备注，用。进行拼接
					cctjs.append(String.valueOf((obj[0] == null || obj[0] == "null") ? "" : obj[0]));
					cctjs.append("。");
					remarks.append(String.valueOf((obj[1] == null || obj[1] == "null") ? "" : obj[1]));
					remarks.append("。");
				}
				pvo.setItemcctjs(cctjs.toString());
				pvo.setItemRemarks(remarks.toString());
			}
			taskPvos.add(pvo);
		}
		return taskPvos;
	}
}
