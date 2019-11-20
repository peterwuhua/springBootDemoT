package cn.demi.base.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.service.IAreaService;
import cn.demi.base.system.vo.AreaVo;
import sun.misc.BASE64Decoder;

/**
 * <strong>Create on : 2016年11月2日 下午1:59:56 </strong> <br>
 * <strong>Description : 组织action</strong> <br>
 *
 * @author <strong>Dave Yu</strong><br>
 * @version <strong> v 1.0.0 </strong> <br>
 */
@Controller("sys.areaAction")
@RequestMapping("sys/area")
public class AreaAction extends BaseAction<AreaVo> {

    final String VIEW_PATH = "/sys/area/area";

    @Autowired
    private IAreaService areaService;

    @Override
    public String getViewPath() {
        return VIEW_PATH;
    }

    @Override
    public IBaseService<AreaVo> baseService() {
        return areaService;
    }

    @Override
    public ModelAndView edit(AreaVo vo) throws GlobalException {
        AreaVo parentVo = null;
        ModelAndView mav = null;
        //新增
        if (StrUtils.isBlankOrNull(vo.getId())) {
            //是否出入父级
            if (StrUtils.isBlankOrNull(vo.getPid())) {
                parentVo = areaService.findRoot();
            } else {
                parentVo = areaService.findById(vo.getPid());
            }
            mav = super.edit(vo);
            vo.setParentVo(parentVo);
        } else {
            mav = super.edit(vo);
        }
        //mav.addObject("areaList", areaService.treeList());
        return mav;
    }

    /**
     * <strong>Create on : Dave Yu 2016年11月2日 下午2:00:24 </strong> <br>
     * <strong>Description : </strong> <br>
     *
     * @return List
     * @throws GlobalException
     */
    @ResponseBody
    @RequestMapping(value = {"treeData.do", "treeData.json"})
    public List<Map<String, Object>> treeData() throws GlobalException {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        List<AreaVo> list = areaService.list();
        for (AreaVo vo : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", vo.getId());
            map.put("pId", null != vo.getParentVo() ? vo.getParentVo().getId()
                    : "");
            map.put("name", vo.getName());
            map.put("path", vo.getPath());
            map.put("open", null != vo.getParentVo() ? vo.getParentVo().getId() == "001" ? "false" : "ture" : "true");
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public ModelAndView update(AreaVo v, RedirectAttributes attr) throws GlobalException {
        ModelAndView mav = super.update(v, attr);
        mav.addObject("pid", v.getPid());
        return mav;
    }

    @Override
    public ModelAndView add(AreaVo v, RedirectAttributes attr) throws GlobalException {
        ModelAndView mav = super.add(v, attr);
        mav.addObject("pid", v.getPid());
        return mav;
    }

    @Override
    public ModelAndView save(AreaVo v, RedirectAttributes attr) throws GlobalException {
        ModelAndView mav = super.save(v, attr);
        mav.addObject("pid", v.getPid());
        return mav;
    }

    @Override
    public ModelAndView update2del(AreaVo v, RedirectAttributes attr) throws GlobalException {
        ModelAndView mav = super.update2del(v, attr);
        mav.addObject("pid", v.getPid());
        return mav;
    }

    @Override
    public ModelAndView gridPage(AreaVo v) throws GlobalException {
        ModelAndView mav = new ModelAndView();
        if (StrUtils.isBlankOrNull(v.getPid())) {
            v.setPid(areaService.findRoot().getId());
        }
        mav.addObject(VO, v);
        mav.setViewName(getViewPath() + "_page");
        return mav;
    }

    @Override
    public GridVo gridData(GridVo gridVo, AreaVo v) throws GlobalException {
        return baseService().gridData(gridVo, v);
    }

    @RequestMapping(value = "ajax4list.do")
    @ResponseBody
    public List<AreaVo> ajax4list(String name) throws GlobalException {
        if (StrUtils.isBlankOrNull(name)) {
            name = areaService.findRoot().getName();
        } else {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                name = name.replaceAll(" ", "+");
                byte[] b = decoder.decodeBuffer(name);
                name = new String(b, "utf-8");
            } catch (Exception e) {
                return null;
            }
        }
        return areaService.listName(name);

    }
}
