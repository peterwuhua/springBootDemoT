package cn.demi.app.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.service.IOrgService;
import cn.demi.base.system.dao.IOrgDao;
import cn.demi.base.system.po.Org;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("app.orgService")
public class OrgServiceImpl implements IOrgService {
    @Autowired
    private IOrgDao orgDao;

    @Override
    public JSONArray orgList() throws GlobalException {
        JSONArray arr = new JSONArray();
        List<Org> userList = orgDao.listByPid(orgDao.findRoot().getId());
        for (Org org : userList) {
            JSONObject obj = new JSONObject();
            obj.put("id", org.getId());
            obj.put("pId", org.getParent().getId());
            obj.put("name", org.getName());
            arr.add(obj);
        }
        return arr;
    }
}
