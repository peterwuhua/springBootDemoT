package cn.demi.pfm.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.pfm.service.ISRecordService;
import cn.demi.pfm.vo.SRecordVo;

@Controller("pfm.countAction")
@RequestMapping("/pfm/count")
public class CountAction extends BaseAction<SRecordVo> {
    final String VIEW_PATH = "/pfm/count/count";

    @Autowired
    private ISRecordService recordService;

    @Override
    public String getViewPath() {
        return VIEW_PATH;
    }

    @Override
    public IBaseService<SRecordVo> baseService() {
        return recordService;
    }

    @SuppressWarnings("rawtypes")
	@ResponseBody
    @RequestMapping(value = "count")
    public GridVo gridData(GridVo gridVo, SRecordVo sRecordVo) throws GlobalException {
        PageResult pageResult = new PageResult();
        List list = recordService.countScore();
        List<Map<String, Object>> tempList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            Object[] o = (Object[]) list.get(i);
            map.put("name", o[0]);
            map.put("score", o[1]);
            map.put("org", o[2]);
            tempList.add(map);
        }
        gridVo.setDatas(tempList);
        gridVo.setRecords(list.size());
        gridVo.setPage(pageResult.getPageBean().getCurrentPage());
        gridVo.setRows(pageResult.getPageBean().getPageSize());
        gridVo.setTotal(pageResult.getPageBean().getTotalPages());
        return gridVo;
    }
}
