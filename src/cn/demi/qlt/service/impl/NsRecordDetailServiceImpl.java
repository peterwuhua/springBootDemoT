package cn.demi.qlt.service.impl;

import org.springframework.stereotype.Service;

import cn.core.framework.common.service.BaseServiceImpl;
import cn.demi.qlt.po.NsRecordDetail;
import cn.demi.qlt.service.INsRecordDetailService;
import cn.demi.qlt.vo.NsRecordDetailVo;

@Service("qlt.nsRecordDetailService")
public class NsRecordDetailServiceImpl extends BaseServiceImpl<NsRecordDetailVo,NsRecordDetail> implements
		INsRecordDetailService {
}
