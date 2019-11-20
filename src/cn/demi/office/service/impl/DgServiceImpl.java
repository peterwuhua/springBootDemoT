package cn.demi.office.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.constant.EunmTask;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.ICfgDao;
import cn.demi.base.system.po.Cfg;
import cn.demi.bus.pg.dao.IProgressDao;
import cn.demi.bus.pg.dao.IProgressLogDao;
import cn.demi.bus.pg.po.Progress;
import cn.demi.office.dao.IDgDao;
import cn.demi.office.dao.IDgTjDao;
import cn.demi.office.po.Dg;
import cn.demi.office.service.IDgService;
import cn.demi.office.vo.DgCfgVo;
import cn.demi.office.vo.DgVo;

@Service("office.dgService")
public class DgServiceImpl extends BaseServiceImpl<DgVo, Dg> implements
        IDgService {

    @Autowired
    private IDgDao dgDao;
    @Autowired
    private IProgressDao progressDao;
    @Autowired
    private IProgressLogDao progressLogDao;
    @Autowired
    private IDgTjDao dgTjDao;
    @Autowired
    private ICfgDao cfgDao;

    @Override
    public DgVo findById(String id) throws GlobalException {
        return super.findById(id);
    }

    @Override
    public void add2Dk() throws GlobalException {
        Dg po = new Dg();
        po.setType(Dg.TP_DK);
        po.setDeptId(getCurrent().getDepId());
        po.setDeptName(getCurrent().getDepName());
        po.setUserId(getCurrent().getUserId());
        po.setUserName(getCurrent().getUserName());
        po.setDate(DateUtils.getCurrDateTimeStr());
        po.setCurTime(DateUtils.getCurrDateTimeStr());
        po.setContent("考勤打卡");
        po.setStatus(Dg.ST_2);
        dgDao.add(po);
        dgTjDao.updDgTj(po.getUserId(), DateUtils.getCurrDateTimeStr());
    }

    @Override
    public void add(DgVo v) throws GlobalException {
        Dg po = new Dg();
        po.setDate(v.getDate());
        po.setContent(v.getContent());
        po.setRemark(v.getRemark());
        po.setCurTime(DateUtils.getCurrDateTimeStr());
        po.setDeptId(getCurrent().getDepId());
        po.setDeptName(getCurrent().getDepName());
        po.setUserId(getCurrent().getUserId());
        po.setUserName(getCurrent().getUserName());
        po.setType(Dg.TP_BK);
        po.setAuditId(v.getAuditId());
        po.setAuditName(v.getAuditName());
        dgDao.add(po);
        v.setId(po.getId());
        if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
            po.setStatus(Dg.ST_1);
            Progress progress = progressDao.add(po.getId(), EunmTask.BK_SH.getStatus(), null, null, po.getAuditId(), po.getAuditName());
            po.setProgress(progress);
            progressLogDao.add(po.getId(), po.getId(), EunmTask.BK_SQ.getStatus(), v.getIsCommit(), po.getRemark(), po.getDeptId(), po.getDeptName(), po.getUserId(), po.getUserName());
        } else {
            po.setStatus(Dg.ST_0);
        }
        dgDao.update(po);
    }

    @Override
    public void update(DgVo v) throws GlobalException {
        Dg po = dgDao.findById(v.getId());
        po.setDate(v.getDate());
        po.setContent(v.getContent());
        po.setCurTime(DateUtils.getCurrDateTimeStr());
        po.setAuditId(v.getAuditId());
        po.setAuditName(v.getAuditName());
        if (null != v.getIsCommit() && v.getIsCommit().equals(Constants.PASS_Y)) {
            po.setStatus(Dg.ST_1);
            Progress progress = progressDao.add(po.getId(), EunmTask.BK_SH.getStatus(), null, null, po.getAuditId(), po.getAuditName());
            po.setProgress(progress);
            progressLogDao.add(po.getId(), po.getId(), EunmTask.BK_SQ.getStatus(), v.getIsCommit(), po.getRemark(), po.getDeptId(), po.getDeptName(), po.getUserId(), po.getUserName());
        }
        dgDao.update(po);
    }

    @SuppressWarnings("unchecked")
    @Override
    public GridVo gridData(GridVo gridVo, DgVo v) throws GlobalException {
        PageResult pageResult = getPageResult(gridVo, v);
        if (StrUtils.isBlankOrNull(gridVo.getSidx()) || "lastUpdTime".equals(gridVo.getSidx())) {
            pageResult.addOrder("createTime", OrderCondition.ORDER_DESC);
        } else {
            pageResult.setOrder(gridVo.getSord());
            pageResult.setOrderColumn(gridVo.getSidx());
        }
        pageResult.addCondition(new QueryCondition("userId", QueryCondition.EQ, getCurrent().getUserId()));
        pageResult = dgDao.getPageResult(pageResult);
        gridVo.setDatas(poList2mapList((List<Dg>) pageResult.getResultList()));
        gridVo.setRecords(pageResult.getPageBean().getTotalRows());
        gridVo.setPage(pageResult.getPageBean().getCurrentPage());
        gridVo.setRows(pageResult.getPageBean().getPageSize());
        gridVo.setTotal(pageResult.getPageBean().getTotalPages());
        return gridVo;
    }

    @Override
    public List<QueryCondition> toQueryList(DgVo v) throws GlobalException {
        return super.toQueryList(v);
    }

    @Override
    public DgCfgVo find() throws GlobalException {
        Cfg cfg = cfgDao.find(Cfg.TYPE_KT);
        DgCfgVo cfgVo = new DgCfgVo();
        if (cfg != null) {
            cfgVo.setAmst(cfg.getD1());
            cfgVo.setAmet(cfg.getD2());
            cfgVo.setPmst(cfg.getD3());
            cfgVo.setPmet(cfg.getD4());
        } else {
            cfgVo = new DgCfgVo();
        }
        return cfgVo;
    }

    @Override
    public void updateCfg(DgCfgVo v) throws GlobalException {
        Cfg cfg = cfgDao.find(Cfg.TYPE_KT);
        if (null != cfg) {
            cfg.setType(Cfg.TYPE_KT);
            cfg.setD1(v.getAmst());
            cfg.setD2(v.getAmet());
            cfg.setD3(v.getPmst());
            cfg.setD4(v.getPmet());
            cfgDao.update(cfg);
        } else {
            cfg = new Cfg();
            cfg.setType(Cfg.TYPE_KT);
            cfg.setD1(v.getAmst());
            cfg.setD2(v.getAmet());
            cfg.setD3(v.getPmst());
            cfg.setD4(v.getPmet());
            cfgDao.add(cfg);
        }
    }

    @Override
    public String checkTime() throws GlobalException {
        String msg = null;
        Cfg cfg = cfgDao.find(Cfg.TYPE_KT);
        if (cfg != null) {
            String amst = cfg.getD1();//上午上班时间
			String amet=cfg.getD2();//上午上班时间
            String pmst = cfg.getD3();//上午上班时间
            String pmet = cfg.getD4();//上午上班时间
            if (StrUtils.isNotBlankOrNull(amst) && StrUtils.isNotBlankOrNull(pmst)) {
                Date date = new Date();
                SimpleDateFormat df = new SimpleDateFormat("HH");
                String str = df.format(date);
                int a = Integer.parseInt(str);
                if (a < 12) {
                    List<Dg> dgList = dgDao.find(getCurrent().getUserId(), DateUtils.getCurrDateStr());
                    long am1 = DateUtils.getIntevalMinutes(DateUtils.getCurrDateStr() + " " + amst + ":00", DateUtils.getCurrDateTimeStr());
                    long am2 = DateUtils.getIntevalMinutes(DateUtils.getCurrDateTimeStr(), DateUtils.getCurrDateStr() + " " + amet + ":00");
                    //long pm1=DateUtils.getIntevalMinutes(DateUtils.getCurrDateStr()+" "+pmst+":00", DateUtils.getCurrDateTimeStr());
                    //long pm2=DateUtils.getIntevalMinutes(DateUtils.getCurrDateTimeStr(),DateUtils.getCurrDateStr()+" "+pmet+":00");
                    if (dgList == null || dgList.size() <= 0) {
                        //第一次打卡验证
                        if (am1 > 0) {//超过早上规定时间为迟到，不可打卡
                            msg = "当前已过规定打卡时间，请进行补卡流程！（上班打卡时间：" + amst + "之前）";
                        }
                    } else {
                        //可能是下班时间验证
                        if (am2 > 0) {//未到下班时间不可打卡
                            msg = "未在规定打卡时间，不可打卡！（下班打卡时间：" + amet + "之后）";
                        }
                    }
                } else {
                    List<Dg> dgList = dgDao.findPm(getCurrent().getUserId(), DateUtils.getCurrDateStr() + " 12:00:00");
                    if (dgList == null || dgList.size() <= 0) {
                        long pm1 = DateUtils.getIntevalMinutes(DateUtils.getCurrDateStr() + " " + pmst + ":00", DateUtils.getCurrDateTimeStr());
                        if (pm1 > 0) {//超过下午规定时间为迟到，不可打卡
                            msg = "当前已过规定打卡时间，请进行补卡流程！（上班打卡时间：" + pmst + "之前）";
                        }
                    } else {
                        long pm2 = DateUtils.getIntevalMinutes(DateUtils.getCurrDateTimeStr(), DateUtils.getCurrDateStr() + " " + pmet + ":00");
                        if (pm2 > 0) {//未到下班时间不可打卡
                            msg = "未在规定打卡时间，不可打卡！（下班打卡时间：" + pmet + "之后）";
                        }
                    }
                }
            }
        }
        return msg;
    }
}
