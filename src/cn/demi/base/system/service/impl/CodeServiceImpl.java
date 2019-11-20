package cn.demi.base.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.GridVo;
import cn.core.framework.common.page.OrderCondition;
import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.ICodeDao;
import cn.demi.base.system.po.Code;
import cn.demi.base.system.service.ICodeService;
import cn.demi.base.system.vo.CodeVo;
/**
 * <strong>Create on : 2016年11月2日 下午5:11:15 </strong> <br>
 * <strong>Description : CodeServiceImpl </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Service("sys.codeService")
public class CodeServiceImpl extends BaseServiceImpl<CodeVo, Code> implements
		ICodeService {
	@Autowired private ICodeDao codeDao;
	@Override
	public CodeVo findByCode(String code) throws GlobalException {
		return po2Vo(codeDao.findByCode(code));
	}
	@Override
	public void add(CodeVo v) throws GlobalException {
		if(StrUtils.isNotBlankOrNull(v.getContent())) {
			v.setContent(v.getContent().replace("，", ","));
		}
		super.add(v);
	}
	@Override
	public void update(CodeVo v) throws GlobalException {
		if(StrUtils.isNotBlankOrNull(v.getContent())) {
			v.setContent(v.getContent().replace("，", ","));
		}
		super.update(v);
	}
	
	@Override
	public List<String> listByCode(String code) throws GlobalException {
		Code p = codeDao.findByCode(code);
		return toList(p.getContent());
	}
	
	private List<String> toList(String content) {
		if(content==null) {
			content="";
		}
		return StrUtils.splitList(content,',');
	}
	
	@Override
	public List<CodeVo> listCodeVoByCode(String code) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<>();
		queryConditions.add(new QueryCondition("code",QueryCondition.RK,code));
		return toVoList(codeDao.list(queryConditions));
	}
	@SuppressWarnings("unchecked")
	@Override
	public GridVo gridData(GridVo gridVo, CodeVo v) throws GlobalException {
		PageResult pageResult = getPageResult(gridVo, v);
		pageResult.addOrder("sort", OrderCondition.ORDER_ASC);
		pageResult = codeDao.getPageResult(pageResult);
		gridVo.setDatas(poList2mapList((List<Code>)pageResult.getResultList()));
		gridVo.setRecords(pageResult.getPageBean().getTotalRows());
		gridVo.setPage(pageResult.getPageBean().getCurrentPage());
		gridVo.setRows(pageResult.getPageBean().getPageSize());
		gridVo.setTotal(pageResult.getPageBean().getTotalPages());
		return gridVo;
	}
}
