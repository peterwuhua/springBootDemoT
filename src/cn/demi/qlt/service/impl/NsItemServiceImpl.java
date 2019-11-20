package cn.demi.qlt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.qlt.dao.INsItemDao;
import cn.demi.qlt.po.NsItem;
import cn.demi.qlt.service.INsItemService;
import cn.demi.qlt.vo.NsItemVo;

@Service("qlt.nsItemService")
public class NsItemServiceImpl extends BaseServiceImpl<NsItemVo,NsItem> implements
		INsItemService {
	@Autowired
	private INsItemDao nsItemDao;
	
	@Override
	public NsItemVo findById(String id) throws GlobalException {
		NsItem po = nsItemDao.findById(id);
		NsItemVo vo=new NsItemVo();
		vo=po2Vo(po);
		vo.setSubList(treeList(vo.getId()));
		return vo;
	}
	
	@Override
	public List<NsItemVo> treeList(String pid) throws GlobalException {
		List<NsItemVo> itemList=null;
		List<NsItem> plist= nsItemDao.listByItemId(pid);
		if(null!=plist &&plist.size()>0) {
			itemList=new ArrayList<NsItemVo>();
			for (NsItem item : plist) {
				NsItemVo itemVo=new NsItemVo();
				itemVo=itemVo.toVo(item);
				itemVo.setSubList(treeList(item.getId()));
				int count=0;
				if(null!=itemVo.getSubList()) {
					for(NsItemVo subVo :itemVo.getSubList()) {
						count+=subVo.getCount();
					}
				}else {
					count=1;
				}
				itemVo.setCount(count);
				itemList.add(itemVo);
			}
		}
		return itemList;
	}
	@Override
	public void add(NsItemVo v) throws GlobalException {
		NsItem p = vo2Po(v);
//		if(null!=v.getItemVo()&&!StrUtils.isBlankOrNull(v.getItemVo().getId())) {
//			NsItem item=nsItemDao.findById(v.getItemVo().getId());
//			p.setItem(item);
//		}
		p.setLevel(1);
		nsItemDao.add(p);
		v.setId(p.getId());
		saveSub(p, v.getSubList());
	}
	@Override
	public void update(NsItemVo v) throws GlobalException {
		NsItem p = nsItemDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
//		if(null!=v.getItemVo()&&!StrUtils.isBlankOrNull(v.getItemVo().getId())) {
//			NsItem item=nsItemDao.findById(v.getItemVo().getId());
//			p.setItem(item);
//		}
		p.setLevel(1);
		nsItemDao.update(p);
		saveSub(p, v.getSubList());
	}
	public void saveSub(NsItem p,List<NsItemVo> subList) {
		nsItemDao.delete4Ct(p.getId());
		if(subList!=null) {
			int i=0;
			for (NsItemVo subVo : subList) {//添加内容
				NsItem sub=new NsItem();
				sub.setCode(subVo.getCode());
				sub.setName(subVo.getName());
				sub.setItem(p);
				sub.setLevel(p.getLevel()+1);
				sub.setSort(i);
				nsItemDao.add(sub);
				i++;
				List<NsItemVo> tLitst=subVo.getSubList();//添加重点
				if(tLitst!=null) {
					int j=0;
					for (NsItemVo tVo : tLitst) {
						NsItem thd=new NsItem();
						thd.setCode(tVo.getCode());
						thd.setName(tVo.getName());
						thd.setItem(sub);
						thd.setLevel(sub.getLevel()+1);
						thd.setSort(j);
						nsItemDao.add(thd);
						j++;
					}
				}
			}
		}
	}

	@Override
	public List<NsItemVo> list4Ys(String ids) throws GlobalException {
		String jpql="FROM "+nsItemDao.getEntityName(NsItem.class)+" WHERE isDel='"+Po.N+"' AND item.id is null and id not in('"+ids.replace(",", "','")+"') ORDER BY code,sort asc";
		List<NsItem> itList=nsItemDao.list(jpql);
		return toVoList(itList);
	}
}
