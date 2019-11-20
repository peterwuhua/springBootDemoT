package cn.demi.app.init.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.constant.ConstantApp;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.JsonUtil;
import cn.demi.app.init.service.AppInitService;
import cn.demi.app.init.vo.AppInitItem;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.app.sys.vo.OutVo;

@Controller("app.initAction")
@RequestMapping("/app_init/")
public class AppInitAction {

	@Autowired
	private AppInitService initService;
	 /**
     * app 监测项目 列表
     */
    @ResponseBody
    @RequestMapping(value = "app_init_item_list.do")
    public String appAllTask(HttpServletRequest request) throws GlobalException {
        ObjVo objVo = (ObjVo) request.getAttribute("objVo");
        OutVo outVo = new OutVo();
        outVo.setToken(objVo.getToken());
        outVo.setCode(ConstantApp.ST_1);
        int page  = Integer.parseInt(request.getParameter("page"));
        int rows  = Integer.parseInt(request.getParameter("rows"));
        String name = request.getParameter("name");
        objVo.setPage(page);
        objVo.setRows(rows);
        List<AppInitItem> list = initService.itemList(objVo,name);
        outVo.setData(list);
        return JsonUtil.beanToJson(outVo);
    }
}
