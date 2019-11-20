package cn.demi.app.sys.service;

import java.util.List;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.vo.QuestionsVo;

/**
 * 意见反馈
 */
public interface IQuestionsService {
    /**
     * 获取问题列表页面
     *
     * @param v
     * @return
     * @throws GlobalException
     */
    public List<QuestionsVo> list(QuestionsVo v) throws GlobalException;

    /**
     * 保证问题
     *
     * @param v
     * @return
     * @throws GlobalException
     */
    public QuestionsVo add(QuestionsVo v) throws GlobalException;
}
