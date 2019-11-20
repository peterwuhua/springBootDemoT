package cn.demi.res.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.action.Status;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.interceptor.Log;
import cn.demi.res.service.IFbService;
import cn.demi.res.vo.FbObj;
import cn.demi.res.vo.FbVo;
import net.sf.json.JSONObject;

@Controller("res.fbAction")
@RequestMapping("/res/fb")
public class FbAction extends BaseAction<FbVo> {
	final String VIEW_PATH = "/res/fb/fb";
	@Autowired private IFbService fbService;	
		
	@Override
	public String getViewPath() {
		return VIEW_PATH;
	}
	
	@Override
	public IBaseService<FbVo> baseService() {
		return fbService;
	}
	
	@Override
	@Log(operation=Log.Operation.ADD,content="新增方法",module="分包单位管理")
	public Status addData(FbVo v, RedirectAttributes attr) throws GlobalException {
		return super.addData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.UPDATE,content="修改方法",module="分包单位管理")
	public Status updateData(FbVo v, RedirectAttributes attr) throws GlobalException {
		return super.updateData(v, attr);
	}
	@Override
	@Log(operation=Log.Operation.SAVE,content="保存方法",module="分包单位管理")
	public ModelAndView save(FbVo v, RedirectAttributes attr) throws GlobalException {
		return super.save(v, attr);
	}
	
	@Override
	@Log(operation=Log.Operation.UPDATE,content="删除方法",module="分包单位管理")
	public ModelAndView update2del(FbVo v, RedirectAttributes attr) throws GlobalException {
		return super.update2del(v, attr);
	}
	/**
	 * 业务受理
	 * 自动补全功能
	 * join联系人的并集  集合
	 * @param v
	 * @return
	 * @throws GlobalException
	 */
	@ResponseBody
	@RequestMapping(value = "ajax4FullJson.do")
	public JSONObject ajax4FullJson(FbVo v) throws GlobalException {
		JSONObject obj=new JSONObject();
		try {
			List<FbObj> list = fbService.list4Full(v);
			obj.put("code", 200);
			obj.put("value",list.toArray());
			obj.put("message", "");
			obj.put("redirect","");
		} catch (GlobalException e) {
			log.info("删除失败",e);
			obj.put("code", 404);
			obj.put("message", e.getMessage());
		}
		return obj;
	}
}