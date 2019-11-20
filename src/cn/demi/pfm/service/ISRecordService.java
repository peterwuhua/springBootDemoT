package cn.demi.pfm.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.pfm.vo.SRecordVo;
import cn.demi.pfm.vo.ScoreVo;

@Transactional
public interface ISRecordService extends IBaseService<SRecordVo> {
    public void add(SRecordVo sRecordVo) throws GlobalException;

    /**
     * 每人所扣除的分数
     *
     * @return
     * @throws GlobalException
     */
    public List<ScoreVo> countScore() throws GlobalException;
}
