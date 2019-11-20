package cn.demi.app.res.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.common.action.Action;
import cn.core.framework.constant.ConstantApp;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.JsonUtil;
import cn.demi.app.res.service.ResService;
import cn.demi.app.res.vo.AppAgentia;
import cn.demi.app.res.vo.AppAgentiaOut;
import cn.demi.app.res.vo.AppConsumable;
import cn.demi.app.res.vo.AppConsumableOut;
import cn.demi.app.res.vo.AppStandard;
import cn.demi.app.res.vo.AppStandardOut;
import cn.demi.app.res.vo.AppSupplier;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;
import cn.demi.res.po.Consumable;
import cn.demi.res.po.Standard;
import net.sf.json.JSONArray;

@Controller("app.resAction")
@RequestMapping("/app_res_web/")
public class ResAction extends Action {

	@Autowired
	private ResService resService;

	/**
	 * app 获取试剂列表
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_agentia_list.do")
	public String noticeList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		objVo.setName(request.getParameter("title"));
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String searchValue = request.getParameter("searchValue");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		List<AppAgentia> ntListVo = resService.agentialist(objVo,searchValue);
		outVo.setData(ntListVo);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取试剂详情
	 */
	@ResponseBody
	@RequestMapping(value = "app_agentia_show.do")
	public String showAgentia(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String id = (String) request.getParameter("id");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		AppAgentia agentiaVo = resService.findAgentia(id);
		outVo.setData(agentiaVo);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取试剂所有
	 */
	@ResponseBody
	@RequestMapping(value = "app_agentia_all.do")
	public String agentiaList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		JSONArray rows = resService.AgentiaList();
		outVo.setData(rows);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取试剂出入库 type = 1入库 type = 2出库
	 */
	@ResponseBody
	@RequestMapping(value = "app_agentia_update.do")
	public String agentiaupdate(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		int type = Integer.parseInt(request.getParameter("type"));
		if (type == 1) {
			String flag = resService.updateaddAgentia(request.getParameter("id"),
					Double.parseDouble(request.getParameter("inNum")), request.getParameter("keeper"),
					request.getParameter("date"), request.getParameter("remark"));
			if (flag == null) {
				outVo.setCode(ConstantApp.ST_1);
			} else {
				outVo.setCode(ConstantApp.ST_0);
				outVo.setMessage(flag);
			}
		} else if (type == 2) {
			String flag = resService.updatesubtractAgentia(request.getParameter("id"),
					Double.parseDouble(request.getParameter("outNum")), request.getParameter("leadingPerson"),
					request.getParameter("date"), request.getParameter("remark"));
			if (flag == null) {
				outVo.setCode(ConstantApp.ST_1);
			} else {
				outVo.setCode(ConstantApp.ST_0);
				outVo.setMessage(flag);
			}
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 试剂增加修改
	 */
	@ResponseBody
	@RequestMapping(value = "app_update_agentia.do")
	public String updateagentia(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		AppAgentia appAgentia = new AppAgentia(request.getParameterMap());
		appAgentia.setKeepId(objVo.getUserId());
		outVo.setToken(objVo.getToken());
		appAgentia = resService.add4dateAgentia(appAgentia);
		outVo.setCode(ConstantApp.ST_1);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取标准品列表
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_standard_list.do")
	public String StandardList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		objVo.setName(request.getParameter("title"));
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String searchValue = request.getParameter("searchValue");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		List<AppStandard> ntListVo = resService.Standardlist(objVo,searchValue);
		outVo.setData(ntListVo);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取标准品详情
	 */
	@ResponseBody
	@RequestMapping(value = "app_standard_show.do")
	public String showStandard(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String id = (String) request.getParameter("id");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		AppStandard standard = resService.findStandard(id);
		outVo.setData(standard);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取标准品所有
	 */
	@ResponseBody
	@RequestMapping(value = "app_standard_all.do")
	public String standardList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		JSONArray rows = resService.StandardList();
		outVo.setData(rows);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取标准品出入库 type = 1入库 type = 2出库
	 */
	@ResponseBody
	@RequestMapping(value = "app_standard_update.do")
	public String standardupdate(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		int type = Integer.parseInt(request.getParameter("type"));
		if (type == 1) {
			String flag = resService.updateAddStandard(request.getParameter("id"),
					Double.parseDouble(request.getParameter("inNum")), request.getParameter("keeper"),
					request.getParameter("date"), request.getParameter("remark"));
			if (flag == null) {
				outVo.setCode(ConstantApp.ST_1);
			} else {
				outVo.setCode(ConstantApp.ST_0);
				outVo.setMessage(flag);
			}
		} else if (type == 2) {
			String flag = resService.updatesubtractStandard(request.getParameter("id"),
					Double.parseDouble(request.getParameter("outNum")), request.getParameter("leadingPerson"),
					request.getParameter("date"), request.getParameter("remark"));
			if (flag == null) {
				outVo.setCode(ConstantApp.ST_1);
			} else {
				outVo.setCode(ConstantApp.ST_0);
				outVo.setMessage(flag);
			}
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 标准品增加修改
	 */
	@ResponseBody
	@RequestMapping(value = "app_update_standard.do")
	public String updatestandard(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		AppStandard appStandard = new AppStandard(request.getParameterMap());
		appStandard.setKeepId(objVo.getUserId());
		outVo.setToken(objVo.getToken());
		appStandard = resService.add4dateStandard(appStandard);
		outVo.setCode(ConstantApp.ST_1);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取耗材列表
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_consumable_list.do")
	public String ConsumableList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		objVo.setName(request.getParameter("title"));
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
      String searchValue= request.getParameter("searchValue");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		List<AppConsumable> ntListVo = resService.consumablelist(objVo,searchValue);
		outVo.setData(ntListVo);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取耗材详情
	 */
	@ResponseBody
	@RequestMapping(value = "app_consumable_show.do")
	public String showConsumable(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		String id = (String) request.getParameter("id");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		AppConsumable consumable = resService.findConsumable(id);
		outVo.setData(consumable);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取耗材所有
	 */
	@ResponseBody
	@RequestMapping(value = "app_consumable_all.do")
	public String consumableList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		JSONArray rows = resService.ConsumableList();
		outVo.setData(rows);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取耗材出入库 type = 1入库 type = 2出库
	 */
	@ResponseBody
	@RequestMapping(value = "app_consumable_update.do")
	public String consumableupdate(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		int type = Integer.parseInt(request.getParameter("type"));
		if (type == 1) {
			String flag = resService.updateAddConsumable(request.getParameter("id"),
					Double.parseDouble(request.getParameter("inNum")), request.getParameter("keeper"),
					request.getParameter("date"), request.getParameter("remark"));
			if (flag == null) {
				outVo.setCode(ConstantApp.ST_1);
			} else {
				outVo.setCode(ConstantApp.ST_0);
				outVo.setMessage(flag);
			}
		} else if (type == 2) {
			String flag = resService.updatesubtractConsumable(request.getParameter("id"),
					Double.parseDouble(request.getParameter("outNum")), request.getParameter("leadingPerson"),
					request.getParameter("date"), request.getParameter("remark"));
			if (flag == null) {
				outVo.setCode(ConstantApp.ST_1);
			} else {
				outVo.setCode(ConstantApp.ST_0);
				outVo.setMessage(flag);
			}
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 标准品增加修改
	 */
	@ResponseBody
	@RequestMapping(value = "app_update_consumable.do")
	public String updateconsumable(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		AppConsumable appConsumable = new AppConsumable(request.getParameterMap());
		appConsumable.setKeepId(objVo.getUserId());
		outVo.setToken(objVo.getToken());
		appConsumable = resService.add4dateConsumable(appConsumable);
		outVo.setCode(ConstantApp.ST_1);
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * 试剂新增修改
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_save_update_agentia.do")
	public String saveOrUpdateAgentia(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");

		String id = request.getParameter("id");
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String purity = request.getParameter("purity");
		String grade = request.getParameter("grage");
		Double amount = Double.parseDouble(request.getParameter("amount"));
		Double safeAmount = Double.parseDouble(request.getParameter("safeAmount"));
		String exp = request.getParameter("exp");
		String keeper = request.getParameter("keeper");
		String keepId = request.getParameter("keepId");
		String ename = request.getParameter("ename");
		String sname = request.getParameter("sname");
		String unit = request.getParameter("unit");
		String supplier = request.getParameter("supplier");
		String supplierId = request.getParameter("supplierId");
		String bnum = request.getParameter("bnum");
		String saveCode = request.getParameter("saveCode");
		String purpose = request.getParameter("purpose");
		String mfg = request.getParameter("mfg");
		String price = request.getParameter("price");

		String back = resService.saveOrUpdateAgentia(id, no, name, type, purity, grade, amount, safeAmount, exp, keeper,
				keepId, ename, sname, unit, supplier, supplierId, bnum, saveCode, purpose, mfg, price);

		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		if (back == null || back.equals("null") || back.equals("")) {
			outVo.setCode(ConstantApp.ST_1);

		} else {
			outVo.setCode(ConstantApp.ST_0);
			outVo.setMessage(back);
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 标准品新增修改
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_save_update_standard.do")
	public String saveOrUpdateStandard(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		Standard standard = new Standard();
		String id = request.getParameter("id");
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String rule = request.getParameter("rule");
		String content = request.getParameter("content");
		Double amount = Double.parseDouble(request.getParameter("amount"));
		Double safeAmount = Double.parseDouble(request.getParameter("safeAmount"));
		String exp = request.getParameter("exp");
		String keeper = request.getParameter("keeper");
		String keepId = request.getParameter("keepId");
		String cas = request.getParameter("cas");
		String supplier = request.getParameter("supplier");
		String supplierId = request.getParameter("supplierId");
		String cerNo = request.getParameter("cerNo");
		String producer = request.getParameter("producer");
		String saveCondition = request.getParameter("saveCondition");
		String price = request.getParameter("price");
		String remark = request.getParameter("remark");
		String mfg = request.getParameter("mfg");
		String ename = request.getParameter("ename");
		standard.setId(id);
		standard.setNo(no);
		standard.setName(name);
		standard.setRule(rule);
		standard.setContent(content);
		standard.setAmount(amount);
		standard.setSafeAmount(safeAmount);
		standard.setExp(exp);
		standard.setKeeper(keeper);
		standard.setKeepId(keepId);
		standard.setCas(cas);
		standard.setSupplier(supplier);
		standard.setSupplierId(supplierId);
		standard.setCerNo(cerNo);
		standard.setProducer(producer);
		standard.setSaveCondition(saveCondition);
		standard.setPrice(price);
		standard.setRemark(remark);
		standard.setMfg(mfg);
		standard.setEname(ename);
		String back = resService.saveOrUpdateStandard(standard);
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());

		if (back == null || back.equals("null") || back.equals("")) {
			outVo.setCode(ConstantApp.ST_1);

		} else {
			outVo.setCode(ConstantApp.ST_0);
			outVo.setMessage(back);
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 耗材新增修改
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_save_update_consumable.do")
	public String saveOrUpdateConsumable(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");

		String id = request.getParameter("id");
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String unit = request.getParameter("unit");
		Double amount = Double.parseDouble(request.getParameter("amount"));
		Double safeAmount = Double.parseDouble(request.getParameter("safeAmount"));
		String exp = request.getParameter("exp");
		String keeper = request.getParameter("keeper");
		String keepId = request.getParameter("keepId");
		String supplier = request.getParameter("supplier");
		String supplierId = request.getParameter("supplierId");
		String price = request.getParameter("price");
		String mfg = request.getParameter("mfg");
		Consumable consumable = new Consumable();
		consumable.setId(id);
		consumable.setNo(no);
		consumable.setName(name);
		consumable.setType(type);
		consumable.setUnit(unit);
		consumable.setAmount(amount);
		consumable.setSafeAmount(safeAmount);
		consumable.setExp(exp);
		consumable.setKeeper(keeper);
		consumable.setKeepId(keepId);
		consumable.setSupplier(supplier);
		consumable.setSupplierId(supplierId);
		consumable.setPrice(price);
		consumable.setMfg(mfg);

		String back = resService.saveOrUpdateConsumable(consumable);
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());

		if (back == null || back.equals("null") || back.equals("")) {
			outVo.setCode(ConstantApp.ST_1);

		} else {
			outVo.setCode(ConstantApp.ST_0);
			outVo.setMessage(back);
		}
		return JsonUtil.beanToJson(outVo);
	}

	/**
	 * app 获取试剂出入库列表
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_agentia_outInlist.do")
	public String agentiaOutOrInList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		objVo.setName(request.getParameter("title"));
		int type = Integer.parseInt(request.getParameter("type"));
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String searchValue = request.getParameter("searchValue");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		List<AppAgentiaOut> reagentOutList = resService.reagentOutList(objVo,type,searchValue);
		outVo.setData(reagentOutList);
		return JsonUtil.beanToJson(outVo);
	}
	
	/**
	 * app 获取标准品出入库列表
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_standard_outOrInlist.do")
	public String standardOutOrInList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		objVo.setName(request.getParameter("title"));
		int type = Integer.parseInt(request.getParameter("type"));
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String searchValue = request.getParameter("searchValue");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		 List<AppStandardOut> standardOutList = resService.standardOutList(objVo,type,searchValue);
		outVo.setData(standardOutList);
		return JsonUtil.beanToJson(outVo);
	}
	/**
	 * app 获取耗材出入库列表
	 * 
	 * @param request
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "app_consumable_outOrInlist.do")
	public String ConsumableOutOrInList(HttpServletRequest request) throws GlobalException {
		ObjVo objVo = (ObjVo) request.getAttribute("objVo");
		objVo.setName(request.getParameter("title"));
		int type = Integer.parseInt(request.getParameter("type"));
		OutVo outVo = new OutVo();
		outVo.setToken(objVo.getToken());
		outVo.setCode(ConstantApp.ST_1);
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String searchValue = request.getParameter("searchValue");
		objVo.setPage(Integer.valueOf(page));
		objVo.setRows(Integer.valueOf(rows));
		List<AppConsumableOut> consumableOutList = resService.consumableOutList(objVo, type,searchValue);		
		outVo.setData(consumableOutList);
		return JsonUtil.beanToJson(outVo);
	}
	   /**
     * app 供应商列表
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_supplier_list.do")
    public String supplierList(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        List<AppSupplier> list = resService.SupplierList(objVo);
        outVo.setData(list);
        return JsonUtil.beanToJson(outVo);
    }
    
    /**
     * app 字典项内容
     *
     * @param request
     * @return
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = "app_code_content.do")
    public String codeContent(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        String code = request.getParameter("code");
        outVo.setCode(ConstantApp.ST_1);
        JSONArray codeContent = resService.codeContent(code);
        outVo.setData(codeContent);
        return JsonUtil.beanToJson(outVo);
    }
    
    
}
