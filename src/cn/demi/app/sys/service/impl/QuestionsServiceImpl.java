package cn.demi.app.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.demi.app.sys.service.IQuestionsService;
import cn.demi.app.sys.vo.QuestionsVo;
import cn.demi.base.system.dao.IAccountDao;
import cn.demi.base.system.dao.IQuestionsDao;
import cn.demi.base.system.po.Account;
import cn.demi.base.system.po.Questions;

/**
 * 意见反馈
 */
@Service("app.questionsService")
public class QuestionsServiceImpl implements IQuestionsService {

    @Autowired
    private IQuestionsDao questionsDao;
    @Autowired
    private IAccountDao accountDao;

    @Override
    public List<QuestionsVo> list(QuestionsVo v) throws GlobalException {
        return null;
    }

    @Override
    public QuestionsVo add(QuestionsVo v) throws GlobalException {
        Questions po = new Questions();
        Account user = accountDao.findById(v.getUserId());
        po.setUserId(user.getId());
        po.setUserName(user.getUser().getName());
        po.setDate(DateUtils.getCurrDateTimeStr());
        po.setTitle(v.getTitle());
        po.setContact(v.getContact());
        po.setContent(v.getContent());
        questionsDao.add(po);
        v.setId(po.getId());
        return v;
    }


}
