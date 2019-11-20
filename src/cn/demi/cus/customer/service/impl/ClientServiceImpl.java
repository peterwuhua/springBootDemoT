package cn.demi.cus.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.cus.customer.dao.IClientDao;
import cn.demi.cus.customer.dao.IClientPointDao;
import cn.demi.cus.customer.po.Client;
import cn.demi.cus.customer.po.ClientPoint;
import cn.demi.cus.customer.service.IClientService;
import cn.demi.cus.customer.vo.ClientPointVo;
import cn.demi.cus.customer.vo.ClientVo;
import cn.demi.cus.customer.vo.CustVo;
/**
 * Create on : 2016年11月15日 下午2:55:56  <br>
 * Description : ClientServiceImpl <br>
 * @version  v 1.0.0  <br>
 * @author Danlee Li<br>
 */
@Service("cus.clientService")
public class ClientServiceImpl extends BaseServiceImpl<ClientVo,Client> implements
		IClientService {
	@Autowired 
	private IClientDao clientDao;
	@Autowired 
	private IClientPointDao clientPointDao;
//	@Autowired private ICustomerDao customerDao;
//	@Autowired private ICusClientDao cusClientDao;
	
	@Override
	public ClientVo findById(String id) throws GlobalException {
		Client po;
		try {
			po = clientDao.findById(id);
		} catch (Exception e) {
			throw new GlobalException("根据id获取对象失败  id:"+id,e);
		} 
		ClientVo vo=null;
		if(null!=po){
			vo=po2Vo(po);
			List<ClientPoint> cpList=clientPointDao.listBycusId(po.getId());
			List<ClientPointVo> cpVoList=new ArrayList<ClientPointVo>();
			if(null!=cpList) {
				for (ClientPoint cp : cpList) {
					ClientPointVo cpVo=new ClientPointVo();
					cpVo=cpVo.toVo(cp);
					cpVoList.add(cpVo);
				}
			}
			vo.setCpList(cpVoList);
		    
		}
		return vo;
	}
	
	@Override
	public void add(ClientVo v) throws GlobalException {
		Client p = vo2Po(v);
		clientDao.add(p);
		v.setId(p.getId());
	}
	
	@Override
	public void update(ClientVo v) throws GlobalException {
		Client p = clientDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		clientDao.update(p);
	}

	@Override
	public ClientVo find(String name) throws GlobalException {
		Client cus = clientDao.findByName(name);
		if (null == cus) return null; 
		ClientVo vo = po2Vo(cus);
		return vo;
	}
	@Override
	public List<CustVo> list4Sim(ClientVo v) throws GlobalException {
		String hql="FROM "+clientDao.getEntityName(Client.class)+" WHERE isDel='"+Po.N+"' ";
		if(StrUtils.isNotBlankOrNull(v.getName())) {
			hql+="AND name like '%"+v.getName().trim()+"%' ";
		}
		hql+="order by sort asc";
		List<Client> custList=clientDao.list(hql);
		List<CustVo> custVoList=new ArrayList<CustVo>();
		if(null!=custList) {
			for (Client po : custList) {
				CustVo vo=new CustVo();
				vo.setId(po.getId());
				vo.setName(po.getName());
				vo.setAddress(po.getAddress());
				vo.setEmail(po.getEmail());
				vo.setFax(po.getFax());
				vo.setPerson(po.getPerson());
				vo.setPhone(po.getPhone());
				vo.setZip(po.getZip());
				vo.setCusType(po.getIndustry());
				vo.setAttribute(po.getAttribute());
				vo.setProduct(po.getProduct());
				custVoList.add(vo);
			}
		}
		return custVoList;
	}
}
