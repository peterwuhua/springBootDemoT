package cn.demi.base.system.dao.impl;

import org.springframework.stereotype.Repository;

import cn.core.framework.common.dao.BaseDaoImpl;
import cn.demi.base.system.dao.IQuestionsDao;
import cn.demi.base.system.po.Questions;
/**
 * Create on : 2017年2月21日 下午1:17:37  <br>
 * Description : 常见问题DaoImpl  <br>
 * @version  v 1.0.0  <br>
 * @author Paddy Zhang<br>
 */
@Repository("sys.questionsDao")
public class QuestionsDaoImpl extends BaseDaoImpl<Questions> implements IQuestionsDao {

}
