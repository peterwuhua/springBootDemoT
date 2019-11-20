package cn.demi.app.res.service.impl;

import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.demi.app.res.service.ResService;
import cn.demi.app.res.vo.*;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.base.system.dao.ICodeDao;
import cn.demi.base.system.po.Code;
import cn.demi.res.dao.*;
import cn.demi.res.po.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("app.resService")
public class ResServiceImpl implements ResService {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();

	@Autowired
	private IReagentDao reagentDao;

	@Autowired
	private IStandardDao standarDao;

	@Autowired
	private IConsumableDao consumableDao;

	@Autowired
	private IRegOutInDao regOutInDao;

	@Autowired
	private IStanOutInDao stanOutInDao;

	@Autowired
	private IConsOutInDao consOutInDao;

	@Autowired
	private ISupplierDao supplierDao;

	@Autowired
	private ICodeDao codeDao;

	@Override
	public List<AppAgentia> agentialist(ObjVo v, String searchValue) throws GlobalException {
		StringBuffer hql = new StringBuffer(
				"FROM " + reagentDao.getEntityName(Reagent.class) + " WHERE isDel= " + Po.N);
		if (searchValue != null && !("").equals(searchValue) && !("null").equals(searchValue)) {
			hql.append("and ( name like '%" + searchValue + "%' or no like '%" + searchValue + "%')");
		}

		hql.append(" ORDER BY  lastUpdTime desc");
		Query query = reagentDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		@SuppressWarnings("unchecked")
		List<Reagent> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppAgentia> outList = new ArrayList<>();
		if (null != list) {
			for (Reagent reagent : list) {
				AppAgentia agentiaVo = new AppAgentia();
				agentiaVo.setId(reagent.getId());
				agentiaVo.setNo(reagent.getNo());
				agentiaVo.setName(reagent.getName());
				agentiaVo.setType(reagent.getType());
				agentiaVo.setPurity(reagent.getPurity());
				agentiaVo.setGrade(reagent.getGrade());
				agentiaVo.setAmount(reagent.getAmount());
				agentiaVo.setSafeAmount(reagent.getSafeAmount());
				agentiaVo.setExp(reagent.getExp());
				agentiaVo.setKeeper(reagent.getKeeper());
				agentiaVo.setKeepId(reagent.getKeepId());
				outList.add(agentiaVo);
			}
		}
		return outList;

	}

	@Override
	public List<AppStandard> Standardlist(ObjVo v, String searchValue) throws GlobalException {
		StringBuffer hql = new StringBuffer(
				"FROM " + standarDao.getEntityName(Standard.class) + " WHERE isDel= " + Po.N);
		if (searchValue != null && !("").equals(searchValue) && !("null").equals(searchValue)) {
			hql.append("and ( name like '%" + searchValue + "%' or no like '%" + searchValue + "%')");
		}
		hql.append(" ORDER BY lastUpdTime desc");
		Query query = standarDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		@SuppressWarnings("unchecked")
		List<Standard> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppStandard> outList = new ArrayList<>();
		if (null != list) {
			for (Standard standard : list) {
				AppStandard appStandard = new AppStandard();
				appStandard.setId(standard.getId());
				appStandard.setNo(standard.getNo());
				appStandard.setName(standard.getName());
				appStandard.setRule(standard.getRule());
				appStandard.setContent(standard.getContent());
				appStandard.setAmount(standard.getAmount());
				appStandard.setSafeAmount(standard.getSafeAmount());
				appStandard.setExp(standard.getExp());
				appStandard.setKeeper(standard.getKeeper());
				appStandard.setKeepId(standard.getKeepId());
				outList.add(appStandard);
			}
		}
		return outList;

	}

	@Override
	public List<AppConsumable> consumablelist(ObjVo v, String searchValue) throws GlobalException {
		StringBuffer hql = new StringBuffer(
				"FROM " + consumableDao.getEntityName(Consumable.class) + " WHERE isDel= " + Po.N);

		if (searchValue != null && !("").equals(searchValue) && !("null").equals(searchValue)) {
			hql.append("and ( name like '%" + searchValue + "%' or no like '%" + searchValue + "%')");
		}
		hql.append(" ORDER BY  lastUpdTime desc");
		Query query = consumableDao.query(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		@SuppressWarnings("unchecked")
		List<Consumable> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows())
				.getResultList();
		List<AppConsumable> outList = new ArrayList<>();
		if (null != list) {
			for (Consumable consumable : list) {
				AppConsumable appConsumable = new AppConsumable();
				appConsumable.setId(consumable.getId());
				appConsumable.setNo(consumable.getNo());
				appConsumable.setName(consumable.getName());
				appConsumable.setType(consumable.getType());
				appConsumable.setUnit(consumable.getUnit());
				appConsumable.setModel(consumable.getModel());
				appConsumable.setAmount(consumable.getAmount());
				appConsumable.setSafeAmount(consumable.getSafeAmount());
				appConsumable.setExp(consumable.getExp());
				appConsumable.setKeeper(consumable.getKeeper());
				appConsumable.setKeepId(consumable.getKeepId());
				outList.add(appConsumable);
			}
		}
		return outList;

	}

	@Override
	public AppAgentia findAgentia(String id) throws GlobalException {
		Reagent reagent = reagentDao.findById(id);
		AppAgentia agentiaVo = new AppAgentia();
		agentiaVo.setId(reagent.getId());
		agentiaVo.setNo(reagent.getNo());
		agentiaVo.setName(reagent.getName());
		agentiaVo.setType(reagent.getType());
		agentiaVo.setPurity(reagent.getPurity());
		agentiaVo.setGrade(reagent.getGrade());
		agentiaVo.setAmount(reagent.getAmount());
		agentiaVo.setSafeAmount(reagent.getSafeAmount());
		agentiaVo.setExp(reagent.getExp());
		agentiaVo.setKeeper(reagent.getKeeper());
		agentiaVo.setKeepId(reagent.getKeepId());
		agentiaVo.setEname(reagent.getEname());
		agentiaVo.setSname(reagent.getSname());
		agentiaVo.setUnit(reagent.getUnit());
		agentiaVo.setSupplier(reagent.getSupplier());
		agentiaVo.setSupplierId(reagent.getSupplierId());
		agentiaVo.setBnum(reagent.getBnum());
		agentiaVo.setSaveCode(reagent.getSaveCode());
		agentiaVo.setPurpose(reagent.getPurpose());
		agentiaVo.setMfg(reagent.getMfg());
		agentiaVo.setPrice(reagent.getPrice());
		return agentiaVo;
	}

	@Override
	public AppStandard findStandard(String id) throws GlobalException {
		Standard standard = standarDao.findById(id);
		AppStandard appStandard = new AppStandard();
		appStandard.setId(standard.getId());
		appStandard.setNo(standard.getNo());
		appStandard.setName(standard.getName());
		appStandard.setRule(standard.getRule());
		appStandard.setContent(standard.getContent());
		appStandard.setAmount(standard.getAmount());
		appStandard.setSafeAmount(standard.getSafeAmount());
		appStandard.setExp(standard.getExp());
		appStandard.setKeeper(standard.getKeeper());
		appStandard.setKeepId(standard.getKeepId());
		appStandard.setCas(standard.getCas());
		appStandard.setSupplier(standard.getSupplier());
		appStandard.setSupplierId(standard.getSupplierId());
		appStandard.setCerNo(standard.getCerNo());
		appStandard.setProducer(standard.getProducer());
		appStandard.setSaveCondition(standard.getSaveCondition());
		appStandard.setPrice(standard.getPrice());
		appStandard.setRemark(standard.getRemark());
		appStandard.setMfg(standard.getMfg());
		appStandard.setEname(standard.getEname());
		return appStandard;
	}

	@Override
	public AppConsumable findConsumable(String id) throws GlobalException {
		Consumable consumable = consumableDao.findById(id);
		AppConsumable appConsumable = new AppConsumable();
		appConsumable.setId(consumable.getId());
		appConsumable.setNo(consumable.getNo());
		appConsumable.setName(consumable.getName());
		appConsumable.setType(consumable.getType());
		appConsumable.setUnit(consumable.getUnit());
		appConsumable.setModel(consumable.getModel());
		appConsumable.setAmount(consumable.getAmount());
		appConsumable.setSafeAmount(consumable.getSafeAmount());
		appConsumable.setExp(consumable.getExp());
		appConsumable.setKeeper(consumable.getKeeper());
		appConsumable.setKeepId(consumable.getKeepId());
		appConsumable.setSupplier(consumable.getSupplier());
		appConsumable.setSupplierId(consumable.getSupplierId());
		appConsumable.setMfg(consumable.getMfg());
		appConsumable.setPrice(consumable.getPrice());
		return appConsumable;
	}

	@Override
	public JSONArray AgentiaList() throws GlobalException {
		JSONArray arr = new JSONArray();
		List<Reagent> userList = reagentDao.list();
		for (Reagent reagent : userList) {
			JSONObject obj = new JSONObject();
			obj.put("id", reagent.getId());
			obj.put("no", reagent.getNo());
			obj.put("name", reagent.getName());
			obj.put("amount", reagent.getAmount());
			arr.add(obj);
		}
		return arr;
	}

	@Override
	public JSONArray StandardList() throws GlobalException {
		JSONArray arr = new JSONArray();
		List<Standard> userList = standarDao.list();
		for (Standard Standard : userList) {
			JSONObject obj = new JSONObject();
			obj.put("id", Standard.getId());
			obj.put("no", Standard.getNo());
			obj.put("name", Standard.getName());
			obj.put("amount", Standard.getAmount());
			arr.add(obj);
		}
		return arr;
	}

	@Override
	public JSONArray ConsumableList() throws GlobalException {
		JSONArray arr = new JSONArray();
		List<Consumable> userList = consumableDao.list();
		for (Consumable Consumable : userList) {
			JSONObject obj = new JSONObject();
			obj.put("id", Consumable.getId());
			obj.put("no", Consumable.getNo());
			obj.put("name", Consumable.getName());
			obj.put("amount", Consumable.getAmount());
			arr.add(obj);
		}
		return arr;
	}

	@Override
	public String updateaddAgentia(String id, Double inNum, String keeper, String date, String remark)
			throws GlobalException {
		Reagent reagent = reagentDao.findById(id);
		BigDecimal bd1 = new BigDecimal(Double.toString(reagent.getAmount()));
		BigDecimal bd2 = new BigDecimal(Double.toString(inNum));
		double amount = bd1.add(bd2).doubleValue();
		reagent.setAmount(amount);
		reagentDao.update(reagent);
		// log

		RegOutIn p = new RegOutIn();

		p.setReagent(reagent);
		p.setStatus(RegOutIn.in);
		p.setAmount(amount);
		p.setInNum(inNum);
		p.setInPerson(keeper);
		p.setRemark(remark);
		p.setInDate(date);
		try {
			regOutInDao.add(p);
		} catch (Exception e) {
			return e.toString();
		}

		return null;
	}

	@Override
	public String updatesubtractAgentia(String id, Double outNum, String leadingPerson, String date, String remark)
			throws GlobalException {
		Reagent reagent = reagentDao.findById(id);
		int i = reagent.getAmount().compareTo(outNum);
		if (i < 0) {
			return "库存不足";
		} else {

			BigDecimal bd1 = new BigDecimal(Double.toString(reagent.getAmount()));
			BigDecimal bd2 = new BigDecimal(Double.toString(outNum));
			double amount = bd1.subtract(bd2).doubleValue();
			reagent.setAmount(amount);
			reagentDao.update(reagent);
			// log
			RegOutIn p = new RegOutIn();
			p.setReagent(reagent);
			p.setStatus(RegOutIn.out);
			p.setAmount(amount);
			p.setLeadingPerson(leadingPerson);
			p.setLeadingDate(date);
			p.setLeadingNum(outNum);
			p.setRemark(remark);
			regOutInDao.add(p);
			return null;

		}
	}

	@Override
	public String updateAddStandard(String id, Double inNum, String keeper, String date, String remark)
			throws GlobalException {
		Standard standard = standarDao.findById(id);
		BigDecimal bd1 = new BigDecimal(Double.toString(standard.getAmount()));
		BigDecimal bd2 = new BigDecimal(Double.toString(inNum));
		double amount = bd1.add(bd2).doubleValue();
		standard.setAmount(amount);
		standarDao.update(standard);
		// log
		StanOutIn p = new StanOutIn();
		p.setStandard(standard);
		p.setStatus(RegOutIn.in);
		p.setAmount(amount);
		p.setInNum(inNum);
		p.setInPerson(keeper);
		p.setRemark(remark);
		p.setInDate(date);
		stanOutInDao.add(p);
		return null;
	}

	@Override
	public String updatesubtractStandard(String id, Double outNum, String leadingPerson, String date, String remark)
			throws GlobalException {
		Standard standard = standarDao.findById(id);
		int i = standard.getAmount().compareTo(outNum);
		if (i < 0) {
			return "库存不足";
		} else {
			BigDecimal bd1 = new BigDecimal(Double.toString(standard.getAmount()));
			BigDecimal bd2 = new BigDecimal(Double.toString(outNum));
			double amount = bd1.subtract(bd2).doubleValue();
			standard.setAmount(amount);
			standarDao.update(standard);
			// log
			StanOutIn p = new StanOutIn();
			p.setStandard(standard);
			p.setStatus(RegOutIn.out);
			p.setAmount(amount);
			p.setLeadingPerson(leadingPerson);
			p.setLeadingDate(date);
			p.setLeadingNum(outNum);
			p.setRemark(remark);
			stanOutInDao.add(p);
			return null;

		}

	}

	@Override
	public String updateAddConsumable(String id, Double inNum, String keeper, String date, String remark)
			throws GlobalException {
		Consumable consumable = consumableDao.findById(id);
		BigDecimal bd1 = new BigDecimal(Double.toString(consumable.getAmount()));
		BigDecimal bd2 = new BigDecimal(Double.toString(inNum));
		double amount = bd1.add(bd2).doubleValue();
		consumable.setAmount(amount);
		consumableDao.update(consumable);
		// log
		ConsOutIn p = new ConsOutIn();
		p.setConsumable(consumable);
		p.setStatus(RegOutIn.in);
		p.setAmount(amount);
		p.setInNum(inNum);
		p.setInPerson(keeper);
		p.setRemark(remark);
		p.setInDate(date);
		consOutInDao.add(p);
		return null;
	}

	@Override
	public String updatesubtractConsumable(String id, Double outNum, String leadingPerson, String date, String remark)
			throws GlobalException {
		Consumable consumable = consumableDao.findById(id);
		int i = consumable.getAmount().compareTo(outNum);
		if (i < 0) {
			return "库存不足";
		} else {
			BigDecimal bd1 = new BigDecimal(Double.toString(consumable.getAmount()));
			BigDecimal bd2 = new BigDecimal(Double.toString(outNum));
			double amount = bd1.subtract(bd2).doubleValue();
			consumable.setAmount(amount);
			consumableDao.update(consumable);
			// log
			ConsOutIn p = new ConsOutIn();
			p.setConsumable(consumable);
			p.setStatus(RegOutIn.out);
			p.setAmount(amount);
			p.setLeadingPerson(leadingPerson);
			p.setLeadingDate(date);
			p.setLeadingNum(outNum);
			p.setRemark(remark);
			consOutInDao.add(p);
			return null;
		}
	}

	@Override
	public AppAgentia add4dateAgentia(AppAgentia agentia) throws GlobalException {
		Reagent reagent = new Reagent();

		if (null != agentia.getId()) {
			reagent.setId(agentia.getId());
			reagent.setNo(agentia.getNo());
			reagent.setName(agentia.getName());
			reagent.setType(agentia.getType());
			reagent.setPurity(agentia.getPurity());
			reagent.setGrade(agentia.getGrade());
			reagent.setAmount(Double.parseDouble(agentia.getAmountstr()));
			reagent.setSafeAmount(Double.parseDouble(agentia.getSafeAmountstr()));
			reagent.setExp(agentia.getExp());
			reagent.setKeeper(agentia.getKeeper());
			reagent.setKeepId(agentia.getKeepId());
			reagent.setEname(agentia.getEname());
			reagent.setSname(agentia.getSname());
			reagent.setUnit(agentia.getSname());
			reagent.setSupplier(agentia.getSupplier());
			reagent.setSupplierId(agentia.getSupplierId());
			reagent.setBnum(agentia.getBnum());
			reagent.setSaveCode(agentia.getSaveCode());
			reagent.setPurpose(agentia.getPurpose());
			reagent.setMfg(agentia.getMfg());
			reagent.setPrice(agentia.getPrice());
			reagentDao.update(reagent);

		} else {
			reagent.setNo(agentia.getNo());
			reagent.setName(agentia.getName());
			reagent.setType(agentia.getType());
			reagent.setPurity(agentia.getPurity());
			reagent.setGrade(agentia.getGrade());
			reagent.setAmount(Double.parseDouble(agentia.getAmountstr()));
			reagent.setSafeAmount(Double.parseDouble(agentia.getSafeAmountstr()));
			reagent.setExp(agentia.getExp());
			reagent.setKeeper(agentia.getKeeper());
			reagent.setKeepId(agentia.getKeepId());
			reagent.setEname(agentia.getEname());
			reagent.setSname(agentia.getSname());
			reagent.setUnit(agentia.getSname());
			reagent.setSupplier(agentia.getSupplier());
			reagent.setSupplierId(agentia.getSupplierId());
			reagent.setBnum(agentia.getBnum());
			reagent.setSaveCode(agentia.getSaveCode());
			reagent.setPurpose(agentia.getPurpose());
			reagent.setMfg(agentia.getMfg());
			reagent.setPrice(agentia.getPrice());
			reagentDao.add(reagent);
			reagent.setId(agentia.getId());
		}
		return agentia;
	}

	@Override
	public AppStandard add4dateStandard(AppStandard standard) throws GlobalException {
		Standard standardpo = new Standard();

		if (null != standard.getId()) {
			standardpo.setId(standard.getId());
			standardpo.setNo(standard.getNo());
			standardpo.setName(standard.getName());
			standardpo.setRule(standard.getRule());
			standardpo.setContent(standard.getContent());
			standardpo.setAmount(Double.parseDouble(standard.getAmountstr()));
			standardpo.setSafeAmount(Double.parseDouble(standard.getSafeAmountstr()));
			standardpo.setExp(standard.getExp());
			standardpo.setKeeper(standard.getKeeper());
			standardpo.setKeepId(standard.getKeepId());
			standardpo.setCas(standard.getCas());
			standardpo.setSupplier(standard.getSupplier());
			standardpo.setSupplierId(standard.getSupplierId());
			standardpo.setCerNo(standard.getCerNo());
			standardpo.setProducer(standard.getProducer());
			standardpo.setSaveCondition(standard.getSaveCondition());
			standardpo.setPrice(standard.getPrice());
			standardpo.setRemark(standard.getRemark());
			standarDao.update(standardpo);

		} else {
			standardpo.setNo(standard.getNo());
			standardpo.setName(standard.getName());
			standardpo.setRule(standard.getRule());
			standardpo.setContent(standard.getContent());
			standardpo.setAmount(Double.parseDouble(standard.getAmountstr()));
			standardpo.setSafeAmount(Double.parseDouble(standard.getSafeAmountstr()));
			standardpo.setExp(standard.getExp());
			standardpo.setKeeper(standard.getKeeper());
			standardpo.setKeepId(standard.getKeepId());
			standardpo.setCas(standard.getCas());
			standardpo.setSupplier(standard.getSupplier());
			standardpo.setSupplierId(standard.getSupplierId());
			standardpo.setCerNo(standard.getCerNo());
			standardpo.setProducer(standard.getProducer());
			standardpo.setSaveCondition(standard.getSaveCondition());
			standardpo.setPrice(standard.getPrice());
			standardpo.setRemark(standard.getRemark());
			standarDao.add(standardpo);
			standardpo.setId(standard.getId());

		}
		return standard;
	}

	@Override
	public AppConsumable add4dateConsumable(AppConsumable consumable) throws GlobalException {
		Consumable consumablepo = new Consumable();

		if (null != consumable.getId()) {
			consumablepo.setId(consumable.getId());
			consumablepo.setNo(consumable.getNo());
			consumablepo.setName(consumable.getName());
			consumablepo.setType(consumable.getType());
			consumablepo.setUnit(consumable.getUnit());
			consumablepo.setModel(consumable.getModel());
			consumablepo.setAmount(Double.parseDouble(consumable.getAmountstr()));
			consumablepo.setSafeAmount(Double.parseDouble(consumable.getSafeAmountstr()));
			consumablepo.setExp(consumable.getExp());
			consumablepo.setKeeper(consumable.getKeeper());
			consumablepo.setKeepId(consumable.getKeepId());
			consumablepo.setSupplier(consumable.getSupplier());
			consumablepo.setSupplierId(consumable.getSupplierId());
			consumablepo.setMfg(consumable.getMfg());
			consumablepo.setPrice(consumable.getPrice());
			consumableDao.update(consumablepo);

		} else {
			consumablepo.setNo(consumable.getNo());
			consumablepo.setName(consumable.getName());
			consumablepo.setType(consumable.getType());
			consumablepo.setUnit(consumable.getUnit());
			consumablepo.setModel(consumable.getModel());
			consumablepo.setAmount(Double.parseDouble(consumable.getAmountstr()));
			consumablepo.setSafeAmount(Double.parseDouble(consumable.getSafeAmountstr()));
			consumablepo.setExp(consumable.getExp());
			consumablepo.setKeeper(consumable.getKeeper());
			consumablepo.setKeepId(consumable.getKeepId());
			consumablepo.setSupplier(consumable.getSupplier());
			consumablepo.setSupplierId(consumable.getSupplierId());
			consumablepo.setMfg(consumable.getMfg());
			consumablepo.setPrice(consumable.getPrice());
			consumableDao.add(consumablepo);
			consumablepo.setId(consumable.getId());

		}
		return consumable;
	}

	@Override
	public String saveOrUpdateAgentia(String id, String no, String name, String type, String purity, String grade,
			Double amount, Double safeAmount, String exp, String keeper, String keepId, String ename, String sname,
			String unit, String supplier, String supplierId, String bnum, String saveCode, String purpose, String mfg,
			String price) {
		if (id == null || id.toString().equals("") || id.toString().equals("null")) {
			Reagent reagent = new Reagent();
			reagent.setNo(no);
			reagent.setName(name);
			reagent.setType(type);
			reagent.setPurity(purity);
			reagent.setGrade(grade);
			reagent.setAmount(amount);
			reagent.setSafeAmount(safeAmount);
			reagent.setExp(exp);
			reagent.setKeeper(keeper);
			reagent.setKeepId(keepId);
			reagent.setEname(ename);
			reagent.setSname(sname);
			reagent.setUnit(unit);
			reagent.setSupplier(supplier);
			reagent.setSupplierId(supplierId);
			reagent.setBnum(bnum);
			reagent.setSaveCode(saveCode);
			reagent.setMfg(mfg);
			reagent.setPrice(price);
			try {
				reagentDao.add(reagent);

			} catch (Exception e) {
				return e.toString();
			}

		} else {
			Reagent reagent = reagentDao.findById(id);
			reagent.setId(id);
			reagent.setNo(no);
			reagent.setName(name);
			reagent.setType(type);
			reagent.setPurity(purity);
			reagent.setGrade(grade);
			reagent.setAmount(amount);
			reagent.setSafeAmount(safeAmount);
			reagent.setExp(exp);
			reagent.setKeeper(keeper);
			reagent.setKeepId(keepId);
			reagent.setEname(ename);
			reagent.setSname(sname);
			reagent.setUnit(unit);
			reagent.setSupplier(supplier);
			reagent.setSupplierId(supplierId);
			reagent.setBnum(bnum);
			reagent.setSaveCode(saveCode);
			reagent.setMfg(mfg);
			reagent.setPrice(price);
			try {
				reagentDao.update(reagent);

			} catch (Exception e) {
				return ename.toString();
			}

		}
		return null;
	}

	@Override
	public String saveOrUpdateStandard(Standard standard) {
		String id = standard.getId();
		if (id == null || id.toString().equals("") || id.toString().equals("null")) {
			try {
				standarDao.add(standard);
			} catch (Exception e) {

			}

		} else {
			try {
				Standard temp = standarDao.findById(id);
				temp.setId(id);
				temp.setNo(standard.getNo());
				temp.setName(standard.getName());
				temp.setRule(standard.getRule());
				temp.setContent(standard.getContent());
				temp.setAmount(standard.getAmount());
				temp.setSafeAmount(standard.getSafeAmount());
				temp.setExp(standard.getExp());
				temp.setKeeper(standard.getKeeper());
				temp.setKeepId(standard.getKeepId());
				temp.setCas(standard.getCas());
				temp.setSupplier(standard.getSupplier());
				temp.setSupplierId(standard.getSupplierId());
				temp.setCerNo(standard.getCerNo());
				temp.setProducer(standard.getProducer());
				temp.setSaveCondition(standard.getSaveCondition());
				temp.setPrice(standard.getPrice());
				temp.setRemark(standard.getRemark());
				temp.setMfg(standard.getMfg());
				temp.setEname(standard.getEname());
				standarDao.update(temp);
			} catch (Exception e) {
				return e.toString();
			}

		}
		return null;
	}

	@Override
	public String saveOrUpdateConsumable(Consumable consumable) {
		String id = consumable.getId();
		if (id == null || id.equals("") || id.equals("null")) {
			try {
				consumableDao.add(consumable);
			} catch (Exception e) {
				return e.toString();
			}
		} else {
			try {
				Consumable temp = consumableDao.findById(id);

				temp.setId(id);
				temp.setNo(consumable.getNo());
				temp.setName(consumable.getName());
				temp.setType(consumable.getType());
				temp.setUnit(consumable.getUnit());
				temp.setAmount(consumable.getAmount());
				temp.setSafeAmount(consumable.getSafeAmount());
				temp.setExp(consumable.getExp());
				temp.setKeeper(consumable.getKeeper());
				temp.setKeepId(consumable.getKeepId());
				temp.setSupplier(consumable.getSupplier());
				temp.setSupplierId(consumable.getSupplierId());
				temp.setPrice(consumable.getPrice());
				temp.setMfg(consumable.getMfg());
				consumableDao.update(temp);
			} catch (Exception e) {
				return e.toString();
			}
		}
		return null;
	}

	@Override
	public List<AppAgentiaOut> reagentOutList(ObjVo v, int type, String searchValue) throws GlobalException {
		StringBuffer hql = new StringBuffer(
				"SELECT regoutin.id,  reagrnt.`name` as name, reagrnt. NO as no, regoutin.remark,reagrnt.type, regoutin.in_num, regoutin.in_person, regoutin.in_date,regoutin.leading_num, regoutin.leading_person, regoutin.leading_date FROM v_res_regoutin regoutin JOIN v_res_reagent reagrnt ON regoutin.reagent_id = reagrnt.id AND regoutin.is_del = '0'");
		if (type == 1) {
			hql.append("and status = '出库'");
		} else if (type == 2) {
			hql.append("and status = '入库'");
		}
		if (searchValue != null && !("").equals(searchValue) && !("null").equals(searchValue)) {
			hql.append("and ( name like '%" + searchValue + "%' or no like '%" + searchValue + "%')");

		}

		hql.append(" ORDER BY regoutin.last_upd_time desc ");
		Query query = regOutInDao.queryBysql(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}

		List list2 = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppAgentiaOut> outList = new ArrayList<>();
		if (null != query) {
			for (Object o : list2) {
				Object[] objects = (Object[]) o;
				String tempRemark = "";
				if (objects[3] != null) {
					tempRemark = objects[3].toString();
				}
				String tempType = "";
				if (objects[4] != null) {
					tempType = objects[4].toString();
				}
				Double tempInNum = 0.0;
				if (objects[5] != null) {
					tempInNum = Double.parseDouble(objects[5].toString());
				}
				String tempInPerson = "";
				if (objects[6] != null) {
					tempInPerson = objects[6].toString();
				}
				String tempInDate = "";
				if (objects[7] != null) {
					tempInDate = objects[7].toString();
				}
				Double tempOutNum = 0.0;
				if (objects[8] != null) {
					tempOutNum = Double.parseDouble(objects[8].toString());
				}
				String tempOutPerson = "";
				if (objects[9] != null) {
					tempOutPerson = objects[9].toString();
				}
				String tempOutDate = "";
				if (objects[10] != null) {
					tempOutDate = objects[10].toString();
				}
				AppAgentiaOut appAgentiaOut = new AppAgentiaOut();
				appAgentiaOut.setId(objects[0].toString());
				appAgentiaOut.setName(objects[1].toString());
				appAgentiaOut.setNo(objects[2].toString());
				appAgentiaOut.setRemark(tempRemark);
				appAgentiaOut.setType(tempType);
				appAgentiaOut.setInNum(tempInNum);
				appAgentiaOut.setInPerson(tempInPerson);
				appAgentiaOut.setInDate(tempInDate);
				appAgentiaOut.setLeadingNum(tempOutNum);
				appAgentiaOut.setLeadingPerson(tempOutPerson);
				appAgentiaOut.setLeadingDate(tempOutDate);
				outList.add(appAgentiaOut);
			}
		}
		return outList;

	}

	@Override
	public List<AppStandardOut> standardOutList(ObjVo v, int type, String searchValue) throws GlobalException {
		StringBuffer hql = new StringBuffer(
				"SELECT stanoutin.id, standard.NO as no , standard.`name` as name, standard.rule, standard.exp, stanoutin.leading_person, stanoutin.leading_date, stanoutin.leading_num, "
						+ "stanoutin.in_person, stanoutin.in_num, stanoutin.in_date, stanoutin.remark FROM v_res_stanoutin stanoutin JOIN v_res_standard standard ON stanoutin.standard_id = standard.id AND stanoutin.is_del = '0'");
		if (type == 1) {
			hql.append("and status = '出库'");
		} else if (type == 2) {
			hql.append("and status = '入库'");
		}
		if (searchValue != null && !("").equals(searchValue) && !("null").equals(searchValue)) {
			hql.append("and ( name like '%" + searchValue + "%' or no like '%" + searchValue + "%')");

		}
		hql.append("ORDER BY stanoutin.last_upd_time DESC");
		Query query = reagentDao.queryBysql(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}

		List list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppStandardOut> outList = new ArrayList<>();
		if (null != list) {
			for (Object o : list) {
				AppStandardOut appStandardOut = new AppStandardOut();
				Object[] objects = (Object[]) o;
				appStandardOut.setId(objects[0].toString());
				appStandardOut.setNo(objects[1].toString());
				appStandardOut.setName(objects[1].toString());
				String tempRule = "";
				if (objects[3] != null) {
					tempRule = objects[3].toString();
				}
				String tempExp = "";
				if (objects[4] != null) {
					tempExp = objects[4].toString();
				}
				String tempOutPerson = "";
				if (objects[5] != null) {
					tempOutPerson = objects[5].toString();
				}
				String tempOutDate = "";
				if (objects[6] != null) {
					tempOutDate = objects[6].toString();
				}
				Double tempOutNum = 0.0;
				if (objects[7] != null) {
					tempOutNum = Double.parseDouble(objects[7].toString());
				}

				String tempInPerson = "";
				if (objects[8] != null) {
					tempInPerson = objects[8].toString();
				}
				Double tempInNum = 0.0;
				if (objects[9] != null) {
					tempInNum = Double.parseDouble(objects[9].toString());
				}
				String tempInDate = "";
				if (objects[10] != null) {
					tempInDate = objects[10].toString();
				}
				String tempRemark = "";
				if (objects[11] != null) {
					tempInDate = objects[11].toString();
				}
				appStandardOut.setRule(tempRule);
				appStandardOut.setExp(tempExp);
				appStandardOut.setLeadingPerson(tempOutPerson);
				appStandardOut.setLeadingDate(tempOutDate);
				appStandardOut.setLeadingNum(tempOutNum);
				appStandardOut.setInPerson(tempInPerson);
				appStandardOut.setInNum(tempInNum);
				appStandardOut.setInDate(tempInDate);
				appStandardOut.setRemark(tempRemark);
				outList.add(appStandardOut);
			}
		}
		return outList;

	}

	@Override
	public List<AppConsumableOut> consumableOutList(ObjVo v, int type, String searchValue) throws GlobalException {
		StringBuffer hql = new StringBuffer(
				"SELECT consOutIn.id,consumable.no as no ,consumable.name as name,consumable.type,consumable.unit,consumable.model,consOutIn.in_person,consOutIn.in_date,consOutIn.in_num,consOutIn.remark,consOutIn.leading_person,consOutIn.leading_date,consOutIn.leading_num FROM v_res_consOutIn consOutIn JOIN v_res_consumable consumable ON consOutIn.consumable_id=consumable.id AND consOutIn.is_del = '0'");
		if (type == 1) {
			hql.append("and status = '出库'");
		} else if (type == 2) {
			hql.append("and status = '入库'");
		}
		if (searchValue != null && !("").equals(searchValue) && !("null").equals(searchValue)) {
			hql.append("and ( name like '%" + searchValue + "%' or no like '%" + searchValue + "%')");

		}
		hql.append(" ORDER BY consOutIn.last_upd_time DESC");
		Query query = reagentDao.queryBysql(hql.toString());
		int page = v.getPage();
		if (page < 1) {
			page = 1;
		}
		@SuppressWarnings("unchecked")
		List list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
		List<AppConsumableOut> outList = new ArrayList<>();
		if (null != list) {
			for (Object o : list) {
				Object[] objects = (Object[]) o;
				AppConsumableOut appConsumableOut = new AppConsumableOut();
				appConsumableOut.setId(objects[0].toString());
				appConsumableOut.setNo(objects[1].toString());
				appConsumableOut.setName(objects[2].toString());
				String tempType = "";
				if (objects[3] != null) {
					tempType = objects[3].toString();
				}
				String unit = "";
				if (objects[4] != null) {
					unit = objects[4].toString();
				}
				String model = "";
				if (objects[5] != null) {
					model = objects[5].toString();
				}
				String inPerson = "";
				if (objects[6] != null) {
					inPerson = objects[6].toString();
				}
				String inDate = "";
				if (objects[7] != null) {
					inDate = objects[7].toString();
				}
				Double inNum = 0.0;
				if (objects[8] != null) {
					inNum = Double.parseDouble(objects[8].toString());
				}
				String remark = "";
				if (objects[9] != null) {
					remark = objects[9].toString();
				}
				String outPerson = "";
				if (objects[10] != null) {
					remark = objects[10].toString();
				}
				String outDate = "";
				if (objects[11] != null) {
					remark = objects[11].toString();
				}
				Double outnum = 0.0;
				if (objects[12] != null) {
					inNum = Double.parseDouble(objects[12].toString());
				}
				appConsumableOut.setType(tempType);
				appConsumableOut.setUnit(unit);
				appConsumableOut.setModel(model);
				appConsumableOut.setInPerson(inPerson);
				appConsumableOut.setInDate(inDate);
				appConsumableOut.setInNum(inNum);
				appConsumableOut.setRemark(remark);
				appConsumableOut.setLeadingPerson(outPerson);
				appConsumableOut.setLeadingDate(outDate);
				appConsumableOut.setLeadingNum(outnum);

				outList.add(appConsumableOut);
			}
		}
		return outList;

	}

	@Override
	public List<AppSupplier> SupplierList(ObjVo v) throws GlobalException {
		List<AppSupplier> listAppSupplier = new ArrayList<>();
		List<Supplier> list = supplierDao.list();
		for (Supplier supplier : list) {
			AppSupplier appSupplier = new AppSupplier();
			appSupplier.setId(supplier.getId());
			appSupplier.setNo(supplier.getNo());
			appSupplier.setName(supplier.getName());
			appSupplier.setProductType(supplier.getProductType());
			appSupplier.setLinkman(supplier.getLinkman());
			appSupplier.setLinkmanTel(supplier.getLinkmanTel());
			appSupplier.setEmail(supplier.getEmail());
			listAppSupplier.add(appSupplier);
		}
		return listAppSupplier;

	}

	@Override
	public JSONArray codeContent(String code) throws GlobalException {
		JSONArray arr = new JSONArray();
		Code findByCode = codeDao.findByCode(code);
		JSONObject obj = new JSONObject();
		obj.put("id", findByCode.getId());
		obj.put("content", findByCode.getContent());
		arr.add(obj);
		return arr;

	}

}
