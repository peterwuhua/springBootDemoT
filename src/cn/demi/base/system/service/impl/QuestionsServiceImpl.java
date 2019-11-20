package cn.demi.base.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.PageResult;
import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.core.framework.utils.StrUtils;
import cn.demi.base.system.dao.IQuestionsDao;
import cn.demi.base.system.po.Questions;
import cn.demi.base.system.service.IQuestionsService;
import cn.demi.base.system.vo.QuestionsVo;

/**
 * Create on : 2017年2月21日 下午1:20:29 <br>
 * Description : 常见问题serviceImpl <br>
 * 
 * @version v 1.0.0 <br>
 * @author Paddy Zhang<br>
 */
@Service("sys.questionsService")
public class QuestionsServiceImpl extends BaseServiceImpl<QuestionsVo, Questions> implements IQuestionsService {
	@Autowired
	private IQuestionsDao questionsDao;
	@Override
	public PageResult getQuestionsList(QuestionsVo v, PageResult pageResult) throws GlobalException {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		if (StrUtils.isNotBlankOrNull(v.getSelectName())) {
			queryConditions.add(new QueryCondition("question",QueryCondition.AK,v.getSelectName()));
		}
		if (StrUtils.isNotBlankOrNull(v.getModuleName())) {
			queryConditions.add(new QueryCondition("moduleName",QueryCondition.EQ,v.getModuleName()));
		}
		pageResult.addConditionList(queryConditions);
		return super.pageResult(v, pageResult);

	}
	
	@Override
	public List<QuestionsVo> toVoList(List<Questions> pList) throws GlobalException {
		List<QuestionsVo> vList = new ArrayList<QuestionsVo>();
		if (null == pList || pList.size() == 0)
			return vList;
		for (Questions p : pList) {
			String answer = p.getContent();
			if (StrUtils.isNotBlankOrNull(answer)) {
				Pattern pattern = Pattern.compile("<img[^>]*>");
				Matcher m = pattern.matcher(answer);
				int count = 0;
				while (m.find()) {
					count+=1;
					String img = m.group();
					 Matcher ms  = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
					 String src = ""; 
	                if (ms.find()) {
	                    String str_src = ms.group();
	                    src = str_src.substring(str_src.indexOf("\"")+1, str_src.lastIndexOf("\""));
	                }
					answer = answer.replaceAll(img,"<button type=\"button\" class=\"btn btn-default btn-xs\" onclick=\"openImg('"+src+"','【图片" + count + "】');\">【图片" + count + ":点击查看】</button>");
				}
			}
			QuestionsVo questionsVo = new QuestionsVo();
			questionsVo = questionsVo.toVo(p, questionsVo);
			questionsVo.setContent(answer);
			vList.add(questionsVo);
		}
		return vList;
	}
	@Override
	public void add(QuestionsVo v) throws GlobalException {
		v.setDate(DateUtils.getCurrDateTimeStr());
		Questions p = vo2Po(v);
		p.setUserId(getCurrent().getAccountId());
		p.setUserName(getCurrent().getUserName());
		questionsDao.add(p);
		v.setId(p.getId());
	}	
	@Override
	public void update(QuestionsVo v) throws GlobalException {
		v.setDate(DateUtils.getCurrDateTimeStr());
		Questions p = questionsDao.findById(v.getId());
		valVersion(v.getVersion(), p.getVersion());
		p.toPo(v, p);
		questionsDao.update(p);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> listModule() throws GlobalException{
		StringBuffer jpql = new StringBuffer();
		jpql.append("SELECT COUNT(id),moduleName FROM  sys_questions WHERE isDel=0 GROUP BY moduleName");
		List<String[]> listModelName =  questionsDao.query(jpql.toString()).getResultList();
		return listModelName;
		
	}

}
