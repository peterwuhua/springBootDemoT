package cn.demi.bus.pjt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.bus.pjt.dao.IImDao;
import cn.demi.bus.pjt.po.Im;
import cn.demi.bus.pjt.service.IImService;
import cn.demi.bus.pjt.vo.ImVo;
/**
 * 现场踏勘业务逻辑层
 * @author QuJunLong
 */
@Service("bus.imService")
public class ImServiceImpl extends BaseServiceImpl<ImVo,Im> implements
		IImService {
 
	@Autowired
	private IImDao imDao;
	@Override
	public ImVo findById(String id) throws GlobalException {
		Im po=imDao.findById(id);
		ImVo vo=po2Vo(po);
		return vo;
	}
	@Override
	public List<ImVo> list4Im(String busId) throws GlobalException {
		String jpql="FROM "+imDao.getEntityName(Im.class)+" WHERE busId='"+busId+"' order by sort asc";
		List<Im> imList=imDao.list(jpql);
		return toVoList(imList);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ImVo> list4Method(String busId) throws GlobalException {
		String sql="select im.method_id,im.method_name,m.code FROM v_bus_im im join v_init_method m on m.id=im.method_id WHERE bus_id='"+busId+"' group by im.method_id order by im.sort asc";
		List<Object[]> imList=imDao.queryBySql(sql);
		List<ImVo> imVoList=new ArrayList<ImVo>();
		if(null!=imList) {
			for (Object[] obj : imList) {
				ImVo imVo=new ImVo();
				imVo.setMethodId(String.valueOf(obj[0]));
				String name="《"+String.valueOf(obj[1])+"》";
				if(obj[2]!=null) {
					name+="（"+String.valueOf(obj[2])+"）";
				}
				imVo.setMethodName(name);
				imVoList.add(imVo);
			}
		}
		return imVoList;
	}
	 
	
}
