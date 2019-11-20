package cn.demi.base.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.core.framework.utils.current.Current;
import cn.core.framework.utils.current.CurrentUtils;
import cn.demi.base.system.dao.INoticeDao;
import cn.demi.base.system.po.Notice;
import cn.demi.base.system.service.INoticeService;
import cn.demi.base.system.vo.NoticeVo;

/**
 * <strong>Create on : 2016年11月2日 下午5:12:20 </strong> <br>
 * <strong>Description : NoticeServiceImpl </strong> <br>
 *
 * @author <strong>Dave Yu</strong><br>
 * @version <strong> v 1.0.0 </strong> <br>
 */
@Service("sys.noticeService")
public class NoticeServiceImpl extends BaseServiceImpl<NoticeVo, Notice> implements INoticeService {
    @Autowired
    private INoticeDao noticeDao;

    @Override
    public List<NoticeVo> list(NoticeVo v) throws GlobalException {
        Current current = CurrentUtils.getCurrent();
        List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
        queryConditions.add(new QueryCondition("orgId is null or orgId ='' or orgId like '%" + current.getOrgId() + "%' or orgId like '%" + current.getDepId() + "%'"));
        queryConditions.add(new QueryCondition("endTime>='" + DateUtils.getCurrDateStr() + "'"));
        List<NoticeVo> list = super.list(queryConditions);
        return list;
    }

    @Override
    public List<QueryCondition> toQueryList(NoticeVo v) throws GlobalException {
        Current current = CurrentUtils.getCurrent();
        List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
        queryConditions.add(new QueryCondition("orgId is null or orgId ='' or orgId like '%" + current.getOrgId() + "%' or orgId like '%" + current.getDepId() + "%'"));
        return queryConditions;
    }

    @Override
    public void add(NoticeVo v) throws GlobalException {
        Notice p = vo2Po(v);
        if (!getCurrent().getRoleNames().contains(Constants.SUADMIN) && StrUtils.isBlankOrNull(v.getOrgId())) {
            p.setOrgId(getCurrent().getOrgId());
            p.setOrgName(getCurrent().getOrgName());
        }
        noticeDao.add(p);
        v.setId(p.getId());

        //TODO 权限划分
//        //推送通知公告至手机端
//        PushPayload pushPayload = PushPayload.newBuilder()
//                .setPlatform(Platform.android_ios())
//                .setAudience(Audience.all())
//                .setNotification(Notification.android(v.getSubject(), "通知公告", null))
//                .build();
//        try {
//            jPushClient.sendPush(pushPayload);
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//        } catch (APIRequestException e) {
//            e.printStackTrace();
//        }
//        jPushClient.close();

    }

    @Override
    public void update(NoticeVo v) throws GlobalException {
        Notice p = noticeDao.findById(v.getId());
        valVersion(v.getVersion(), p.getVersion());
        p.toPo(v, p);
        if (!getCurrent().getRoleNames().contains(Constants.SUADMIN) && StrUtils.isBlankOrNull(v.getOrgId())) {
            p.setOrgId(getCurrent().getOrgId());
            p.setOrgName(getCurrent().getOrgName());
        }
        noticeDao.update(p);
    }

    @Override
    public int count(String orgId) throws GlobalException {
        String jpql = "SELECT count(id) FROM " + noticeDao.getEntityName(Notice.class) + " WHERE (orgId is null  or orgId='' or orgId like '%" + orgId + "%') AND endTime>='" + DateUtils.getCurrDateStr() + "'";
        Object ct = noticeDao.query(jpql).getSingleResult();
        int count = 0;
        try {
            count = Integer.valueOf(ct.toString());
        } catch (NumberFormatException e) {
            count = 0;
        }
        return count;
    }
}
