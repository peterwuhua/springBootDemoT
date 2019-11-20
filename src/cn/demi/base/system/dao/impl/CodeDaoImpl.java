package cn.demi.base.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.core.framework.common.po.Po;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.dao.ICodeDao;
import cn.demi.base.system.po.Code;
/**
 * <strong>Create on : 2016年11月2日 下午5:06:06 </strong> <br>
 * <strong>Description : CodeDaoImpl</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Dave Yu</strong><br>
 */
@Repository("sys.codeDao")
public class CodeDaoImpl extends BaseDaoImpl<Code> implements ICodeDao {
	@Override
	public Code findByCode(String code) throws GlobalException{
		StringBuffer jpql = new StringBuffer("FROM ");
		jpql.append(getEntityName(getEntityClazz()));
		jpql.append(" WHERE isDel= "+Po.N+" ");
		jpql.append(" AND code = '"+code+"'");
		List<Code> list=list(jpql.toString());
		if(list==null||list.size()==0)
			return new Code();
		else
		return list.get(0);
	}
}
