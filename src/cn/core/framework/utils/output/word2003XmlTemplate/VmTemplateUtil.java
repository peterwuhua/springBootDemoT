package cn.core.framework.utils.output.word2003XmlTemplate;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * VM 模板工具类实现
 */
class VmTemplateUtil extends TemplateUtil {
	
	private final String R_VAR = StringUtils.join(
			"\\$",
			"\\{",
				"(?:.+?)",
			"\\}"
		);
	private final String R_VAR_C = this.R_VAR.replace("(?:", "(");
	private final String R_FOREACH = StringUtils.join(
			"#foreach\\(",
				R_VAR,
				" in ",
				R_VAR,
			"\\)"
		);
	private final String R_FOREACH_C = this.R_FOREACH.replace("(?:", "(");
	private final String R_LOGIC = StringUtils.join(
			"#end", //是#end模板
			"|",    //或者。。。
			"#else",//是#else模板
			"|",    //或者。。。
			"(?:",  //是带有条件的模板
				"#[a-zA-Z]+", //条件模板前面的声明部分
				"\\(",        //开始括号中的表达式
					"(?:", 
						"[^(]", //或者模板不包含括号
						"|",
						"\\(",   //或者模板包含括号
							"(?:",
								"[^\"']*", //或者括号中是数字
								"|", 
								"[\"][^\"]*[\"]",      //或者括号中是字符串
							")",
						"\\)", 
					")+?",
				"\\)", 
			")"
		);
	private final String R_TEMPLATE = StringUtils.join(
			"(?:",
				this.R_LOGIC,
			")",
			"|",
			"(?:",
				this.R_VAR,
			")"
		);
	private final String R_TEMPLATE_IN = StringUtils.join(
			"^[\\s\\S]*?(?:",
				this.R_TEMPLATE,
			")[\\s\\S]*"
		);
	
    @Override public String mkVar(String name) { return "${" + name + "}"; };
    @Override public String mkForeach(String list, String item) { return "#foreach($" + item + " in $" + list + ")"; };
    @Override public String mkIf(String condition) { return "#if(" + condition + ")"; };
    @Override public String mkElseIf(String condition) { return "#elseif(" + condition + ")"; };
    @Override public String mkElse() { return "#else"; };
    @Override public String mkEndIf() { return "#end"; };
    @Override public String mkEndForeach() { return "#end"; };
    @Override public String mkSet(String key, String value) { return "#set($" + key + "=\"" + value + "\")"; };
    
	@Override public boolean isForeach(String template) { return template.matches(this.R_FOREACH); }
	@Override public boolean isEnd(String template) { return template.equals("#end"); }
	@Override public boolean isLogic(String template) { return template.matches(this.R_LOGIC); }
	@Override public boolean isVar(String template) { return template.matches(this.R_VAR); }
	@Override public boolean isTemplate(String template) { return template.matches(this.R_TEMPLATE); }
	@Override public boolean isTemplateIn(String template) { return template.matches(this.R_TEMPLATE_IN); }
    
    @Override public String evaluate(String tl, Map<String, Object> data, Map<String, String> properties) throws Exception {
    	//属性设置
    	Properties props = new Properties();
        props.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        props.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
        props.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
        props.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        for (Entry<String, String> property : ObjectUtils.defaultIfNull(properties, new HashMap<String, String>()).entrySet()) {
        	 props.put(property.getKey(), property.getValue());
        }
        VelocityEngine engine = new VelocityEngine(props);
        
        //设置上下文数据
    	VelocityContext context = new VelocityContext();
        for (String dataName : data.keySet()) {
            context.put(dataName, data.get(dataName));
        }

        //解析模板
        StringWriter writer = new StringWriter();
        engine.evaluate(context, writer, "", tl);
        writer.close();
        return writer.toString();
    }

	@Override public List<String> extractTemplates(String roughTemplate) {
		return this.match(roughTemplate, this.R_TEMPLATE);
	}

	@Override public Foreach analyzeForeach(String foreachTemplate) {
		String[] metadate = foreachTemplate.replaceFirst(this.R_FOREACH_C, "$1 $2").split(" ");
		return new Foreach(metadate[1], metadate[0]);
	}
	
	@Override public String extractVarName(String varTemplate) {
		return varTemplate.replaceFirst(this.R_VAR_C, "$1");
	}
	
	@Override public String getForeachIndex(String foreachTemplate) {
		return "$velocityCount";
	}
}













