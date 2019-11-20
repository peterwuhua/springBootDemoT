package cn.demi.pfm.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.core.framework.common.action.BaseAction;
import cn.core.framework.common.service.IBaseService;
import cn.demi.pfm.service.ISRecordService;
import cn.demi.pfm.vo.SRecordVo;

@Controller("pfm.recordAction")
@RequestMapping("/pfm/record")
public class RecordAction extends BaseAction<SRecordVo> {
    final String VIEW_PATH = "/pfm/record/record";

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
}
