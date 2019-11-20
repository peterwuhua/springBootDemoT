package cn.core.framework.utils.output.word2003XmlTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这是一个模板引擎工具类， 提供了模板引擎的正则表达式， 并且可以根据数据计算试图
 */
public abstract class TemplateUtil {

	/**
	 * 模板工具实例
	 */
	public static interface Type { 
		TemplateUtil VM = new VmTemplateUtil();
	}
	
	/**
	 * Foreach 元数据
	 */
	public static class Foreach {
		public final String LIST, ITEM;
		public Foreach(String list, String item) { 
			this.LIST = list; this.ITEM = item; 
		} 
	}

    /**
     * 解析 foreach 模板
     * 
     * @param foreachTemplate 要解析模板
     * @return 被解析出来的信息
     */
    public abstract Foreach analyzeForeach(String foreachTemplate);
    
	/**
     * 根据模板和数据以及模板定制的一些属性来产生试图
     *
     * @param tl 一种模板引擎的模板
     * @param data 要展示的数据
     * @param properties 模板定制的属性
     * @return 返回模板和数据结合的试图
     * @throws Exception 当模板文件的格式不正确时，一个异常会被抛出
     */
    public abstract String evaluate(String tl, Map<String, Object> data, Map<String, String> properties) throws Exception;
    
    public String evaluate(String tl, Map<String, Object> data) throws Exception {
    	return this.evaluate(tl, data, null);
    }

    /**
     * 模板生成方法
     */
    public abstract String mkVar(String name);
    public abstract String mkForeach(String list, String item);
    public abstract String mkIf(String condition);
    public abstract String mkElseIf(String condition);
    public abstract String mkElse();
    public abstract String mkEndIf();
    public abstract String mkEndForeach();
    public abstract String mkSet(String key, String value);
    
    /**
     * 判断模板类型
     */
    public abstract boolean isForeach(String template);
    public abstract boolean isEnd(String template);
	public abstract boolean isLogic(String template);
	public abstract boolean isVar(String template);
	public abstract boolean isTemplate(String template);
	public abstract boolean isTemplateIn(String template);
    
    /**
     * 提取模板
     */
	public abstract List<String> extractTemplates(String roughTemplate);
	
	/**
	 * 提取变量模板的名称
	 */
	public abstract String extractVarName(String varTemplate);
	
	/**
	 * 从一段文本中匹配模板，作为公共方法供子类使用
	 */
    protected List<String> match(String string, String regex) {
        List<String> results = new ArrayList<String>();
        Matcher m = Pattern.compile(regex).matcher(string);
        while (m.find()) {
            results.add(m.group());
        }
        return results;
    }
    
    public abstract String getForeachIndex(String foreachTemplate);
}


