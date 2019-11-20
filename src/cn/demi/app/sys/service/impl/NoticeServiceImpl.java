package cn.demi.app.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.app.sys.service.INoticeService;
import cn.demi.app.sys.vo.NoticeOutVo;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.base.system.dao.INoticeDao;
import cn.demi.base.system.po.Notice;

@Service("app.noticeService")
public class NoticeServiceImpl implements INoticeService {

    @Autowired
    private INoticeDao noticeDao;

    @Override
    public List<NoticeOutVo> list(ObjVo v) throws GlobalException {
        StringBuffer hql = new StringBuffer("FROM " + noticeDao.getEntityName(Notice.class) + " WHERE isDel='" + Po.N + "' ");
        hql.append(" AND (orgId is null or orgId ='' or orgId like '%" + v.getOrgId() + "%' or orgId like '%" + v.getDepId() + "%')");
        if (!StrUtils.isBlankOrNull(v.getName())) {
            hql.append(" AND subject like '%" + v.getName() + "%'");
        }
        hql.append(" ORDER BY sendTime DESC");
        Query query = noticeDao.query(hql.toString());
        int page = v.getPage();
        if (page < 1) {
            page = 1;
        }
        List<Notice> list = query.setFirstResult((page - 1) * v.getRows()).setMaxResults(v.getRows()).getResultList();
        List<NoticeOutVo> outList = new ArrayList<>();
        if (null != list) {
            for (Notice notice : list) {
                NoticeOutVo outVo = new NoticeOutVo();
                outVo.setId(notice.getId());
                outVo.setTitle(notice.getSubject());
                outVo.setContent(notice.getContent());
                outVo.setTime(notice.getSendTime());
                outVo.setEndTime(notice.getEndTime());
                outVo.setIssuer(notice.getUserName());
                long n = DateUtils.getIntevalDays(DateUtils.getCurrDateStr(), notice.getEndTime());
                if (n > 0) {
                    outVo.setState(String.valueOf(Po.N));
                } else {
                    outVo.setState(String.valueOf(Po.Y));
                }
                if (StrUtils.isBlankOrNull(notice.getOrgName())) {
                    outVo.setNotifyObject(notice.getOrgName());
                } else {
                    outVo.setNotifyObject("所有人");
                }
                outList.add(outVo);
            }
        }
        return outList;
    }

    @Override
    public NoticeOutVo find(String id) throws GlobalException {
        Notice notice = noticeDao.findById(id);
        NoticeOutVo outVo = new NoticeOutVo();
        outVo.setId(notice.getId());
        outVo.setTitle(notice.getSubject());
        outVo.setContent(notice.getContent());
        outVo.setTime(notice.getSendTime());
        outVo.setEndTime(notice.getEndTime());
        outVo.setIssuer(notice.getUserName());
        long n = DateUtils.getIntevalDays(DateUtils.getCurrDateStr(), notice.getEndTime());
        if (n > 0) {
            outVo.setState(String.valueOf(Po.N));
        } else {
            outVo.setState(String.valueOf(Po.Y));
        }
        if (StrUtils.isBlankOrNull(notice.getOrgName())) {
            outVo.setNotifyObject(notice.getOrgName());
        } else {
            outVo.setNotifyObject("所有人");
        }
        return outVo;
    }

    @Override
    public List<NoticeOutVo> tvList() throws GlobalException {
        StringBuffer hql = new StringBuffer("FROM " + noticeDao.getEntityName(Notice.class) + " WHERE isDel='" + Po.N + "' ");
        hql.append(" ORDER BY sendTime DESC");
        Query query = noticeDao.query(hql.toString());
        List<Notice> list = query.getResultList();
        List<NoticeOutVo> outList = new ArrayList<>();
        if (null != list) {
            for (Notice notice : list) {
                long n = DateUtils.getIntevalDays(DateUtils.getCurrDateStr(), notice.getEndTime());
                if (n > 0) {
                    NoticeOutVo outVo = new NoticeOutVo();
                    outVo.setId(notice.getId());
                    outVo.setTitle(notice.getSubject());
                    outVo.setContent(notice.getContent());
                    outVo.setTime(notice.getSendTime());
                    outVo.setEndTime(notice.getEndTime());
                    outVo.setIssuer(notice.getUserName());
                    outList.add(outVo);
                } else {

                }
            }
        }
        return outList;
    }

}
