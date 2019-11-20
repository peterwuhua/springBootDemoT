package cn.demi.app.sys.service;

import java.util.List;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.sys.vo.NoticeOutVo;
import cn.demi.app.sys.vo.ObjVo;


public interface INoticeService {
    /**
     * 获取通知公告列表页面
     *
     * @param v
     * @return
     * @throws GlobalException
     */
    public List<NoticeOutVo> list(ObjVo v) throws GlobalException;

    /**
     * 获取通知公告详情页面
     *
     * @param id
     * @return
     * @throws GlobalException
     */
    public NoticeOutVo find(String id) throws GlobalException;

    /**
     * 获取通知公告列表（电视）
     *
     * @return
     * @throws GlobalException
     */
    public List<NoticeOutVo> tvList() throws GlobalException;
}
