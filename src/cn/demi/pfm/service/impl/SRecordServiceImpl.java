package cn.demi.pfm.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.dao.IUserDao;
import cn.demi.pfm.dao.ISRecordDao;
import cn.demi.pfm.dao.ISetDao;
import cn.demi.pfm.po.SRecord;
import cn.demi.pfm.po.Set;
import cn.demi.pfm.service.ISRecordService;
import cn.demi.pfm.vo.SRecordVo;

@Service("pfm.recordService")
public class SRecordServiceImpl extends BaseServiceImpl<SRecordVo, SRecord> implements ISRecordService {
    @Autowired
    ISRecordDao recordDao;

    @Autowired
    IUserDao userDao;

    @Autowired
    ISetDao setDao;

    /**
     * 新增扣分记录
     *
     * @param sRecordVo
     * @throws GlobalException
     */
    @Override
    public void add(SRecordVo sRecordVo) throws GlobalException {
        SRecord sRecord = new SRecord();
        sRecord.setOrgId(sRecordVo.getOrgId());
        sRecord.setOrgName(sRecordVo.getOrgName());
        sRecord.setUserId(sRecordVo.getUserId());
        sRecord.setUserName(sRecordVo.getUserName());
        sRecord.setScoreExplain(sRecordVo.getScoreExplain());
        sRecord.setCode(sRecordVo.getCode());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        sRecord.setRecordTime(df.format(new Date()));
        recordDao.add(sRecord);
    }

    @SuppressWarnings("unchecked")
	@Override
    public GridVo gridData(GridVo gridVo, SRecordVo sRecordVo) throws GlobalException {
        PageResult pageResult = getPageResult(gridVo, sRecordVo);
        pageResult = recordDao.getPageResult(pageResult);
        List<SRecord> list = (List<SRecord>) pageResult.getResultList();
        for (SRecord sRecord : list) {
            Set set = setDao.findByCode(sRecord.getCode());
            sRecord.setProject(set.getWorkName());
            sRecord.setValue(set.getValue());
        }

        gridVo.setDatas(poList2mapList(list));
        gridVo.setRecords(pageResult.getPageBean().getTotalRows());
        gridVo.setPage(pageResult.getPageBean().getCurrentPage());
        gridVo.setRows(pageResult.getPageBean().getPageSize());
        gridVo.setTotal(pageResult.getPageBean().getTotalPages());
        return gridVo;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List countScore() throws GlobalException {
        List list = recordDao.queryBySql("SELECT a.`name`,( 100- IFNULL( SUM( c.`value` ), 0 ) ) AS score ,a.org_name as org FROM v_sys_user a " +
                "LEFT JOIN v_pfm_record b ON a.`id` = b.user_id LEFT JOIN v_pfm_rules c ON b.`code` = c.`code` GROUP BY a.`name`");
        return list;
    }
}
