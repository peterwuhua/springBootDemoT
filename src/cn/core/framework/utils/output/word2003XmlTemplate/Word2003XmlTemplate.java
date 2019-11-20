package cn.core.framework.utils.output.word2003XmlTemplate;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.OutputKeys;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import cn.core.framework.utils.output.word2003XmlTemplate.TemplateUtil.Foreach;

/**
 * 注意：此工具只能解析 word 2003 XML 文档 模板中包含中文的引号，逻辑模板没有结尾模板， 模板没有对应的数据等情况都会导致模板解析失败
 * 并且该文件文档必须是 UTF-8 编码格式的
 *
 * Originated by 刘彤彬 on 20013/12/04
 */
public class Word2003XmlTemplate {

    /* 这个工具类提供了对于DOM对象的公共操作方法 */
    private XmlUtil xu = new XmlUtil(true);
    /* 这个工具类提供了计算模板的方法， 并且提供了模板的正则表达式用于匹配模板 */
    private TemplateUtil tu;
    private final String R_URL = "[a-zA-Z0-9]+:\\/\\/.+?";
    public final String BOOKMARK = "bookmark";

    //标记是否需要大纲样式
    private final String FLAG_NEED_OUTLINE_STYLE = "needOutlineStyle";
    //标记模板是否被处理过
    private final String FLAG_IS_REAL_TEMPLATE = "isRealTemplate";
    
    /**
     * 构造器构造对象，接收要操作的模板类型对应的 TemplateUtil
     *
     * @param vm 模板引擎工具类型
     */
    public Word2003XmlTemplate(TemplateUtil vm) {
        this.tu = vm;
    }

    /**
     * 暴露给客户端的方法， 根据 word 的 xml 模板流和提供的数据返回两者相结合的视图
     *
     * @param xmlInputStream Office Word 文档的xml描述流， 这个一个原始模板
     * @param data 为模板填充此数据
     * @param templateProperties 模板所需要设置的属性
     * @return 返回真实的 word 视图， 这是模板和数据结合的产物
     * @throws Exception 模板格式不正确或属性设置多不正确都会引发异常， 这个异常需要被捕获，以为用户提供正确的错误信息
     */
    public String getRealWord(InputStream xmlInputStream, Map<String, Object> data, Map<String, String> templateProperties,Map<String, String > replace) throws Exception {
    	
    	//设置工具类
    	data.put("WordUtil", Word2003XmlTemplate.class);
    	
    	InputStream templateinInputStream = xmlInputStream;
    	String template = IOUtils.toString(templateinInputStream, "UTF-8");
    	if(replace!=null){
    		for(String key:replace.keySet()){
    			template=template.replaceAll(key, replace.get(key));
    		}    		
    	}
    	//假如模板是最原始的粗糙模板，就处理为有规则的模板
    	Document document = this.xu.getDocument(template);
        if (StringUtils.isBlank(this.xu.getAttribute(document.getDocumentElement(), this.FLAG_IS_REAL_TEMPLATE))) {
        	template = this.getRealTemplate(document);
        	template=template.replaceAll("\\$\\{!", "\\$!\\{");//处理字段为null的情况
        }
        
        //模板解析, 返回最终文档视图
        return this.analyzeTemplate(template, data, templateProperties);
    }

    /**
     * 暴露给客户端的方法，根据 word 的 xml 模板流和提供的数据返回两者相结合的视图。
     *
     * @param xmlInputStream Office Word 文档的xml描述流， 这个一个原始模板。
     * @return 返回经过处理后的模板，这个模板可以被某一种模板引擎计算。
     * @throws Exception 模板格式不正确会引发异常， 这个异常需要被捕获，以为用户提供正确的错误信息。
     */
    public String getRealTemplate(InputStream xmlInputStream) throws Exception {
        return this.getRealTemplate(this.xu.getDocument(xmlInputStream));
    }
    private String getRealTemplate(Document document) throws Exception {
    	//为 w:binData 设置 md5 名称
        this.md5ImageDataForAllWbinDatas(document);
        //将换页标签单独分离出来
        this.isolateWbrPage(document);
        //得到所有包含模板的 w:p 节点
        List<Node> wpTemplateNodeList = this.getWpTemplateNodeList(document);
        //整理模板碎片, 将模板放到单独的 w:t 节点下面
        this.isolateTemplateIntoWtNode(wpTemplateNodeList);
        //冒泡逻辑模板到合适的位置，然后注释掉逻辑模板
        this.bubblingAndCommentOutLogicTemplate(wpTemplateNodeList);
        //为合并单元格设置模板，在单元格前后添加判断
        this.setTemplatesForMergingCell(document);
        //处理 sub-section 中，逻辑模板的不对称问题， 开头和结尾不在同一个标签下
        this.fixLogicalTemplatesForSubsectionTags(document);
        //处理大纲
        this.processOutline(document);
        //处理图片模板
        this.processImageTemplate(document);
        //为模板设置“已经被处理”标记
        this.xu.setAttribute(document.getDocumentElement(), this.FLAG_IS_REAL_TEMPLATE, "true");
        //返回最终文档视图
        Map<String, String> xmlProperties = new HashMap<String, String>();
        xmlProperties.put(OutputKeys.CDATA_SECTION_ELEMENTS, this.xu.getQName("http://schemas.microsoft.com/office/word/2003/wordml", "t"));
        return this.xu.getXml(document, xmlProperties);
    }

    /**
     * 处理大纲
     *
     * @param document 处理整个文档
     * @throws Exception 未知
     */
    private void processOutline(Document document) throws Exception {
        if (this.hasOutline(document)) {
            //备份开始大纲的样式
        	this.markNeedOutlineStyle(document);
            //循环所有注释节点 ， 假如注释节点所处的 w:p 下面包含 w:hlink 节点， 那么这个注释处理的就是大纲， 就将注释节点冒泡到 w:p
            this.fixLogicalTemplatesForOutlines(document);
            //循环所有注释节点 ，为 wx:sub-section 设置书签
            this.setBookmarksForWxsubsectionNodeList(document);
            //循环所有注释节点 ，为 w:hlink 设置书签
            this.setBookmarksForWhlinkNodeList(document);
            //为大纲设置页码
            this.setPageNOForOutlines(document);
        }
    }

    /**
     * 为文档标记需要大纲样式
     */
    private void markNeedOutlineStyle(Document document) throws Exception {
        //获取目录 w:p 节点
        Node dirWpNode = this.xu.getNode("descendant::w:pStyle[@w:val='TOC'][1]", document).getParentNode().getParentNode();
        //开始的第一大纲条目， 它保存了大纲的重要样式，在处理模板之前，要备份这个样式，以便以后还原
        Node startWpNode = dirWpNode.getNextSibling();
        if (this.xu.getNode("child::w:hlink", startWpNode).getFirstChild().getNodeType() == Node.COMMENT_NODE) {
            this.xu.setAttribute(document.getDocumentElement(), this.FLAG_NEED_OUTLINE_STYLE, "true");
        }
    }

    /**
     * 假如有一个注释节点是
     */
    private boolean hasOutline(Document document) throws Exception {
        for (Node commentNode : this.xu.getNodeList("descendant::comment()", document)) {
            if (this.tu.isForeach(commentNode.getNodeValue()) && commentNode.getParentNode().getNodeName().equals("w:hlink")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 循环所有注释节点 ， 假如注释节点所处的 w:p 下面包含 w:hlink 节点， 那么这个注释处理的就是大纲， 就将注释节点冒泡到 w:p
     *
     * @param document 处理整个文档
     * @throws Exception 未知
     */
    private void fixLogicalTemplatesForOutlines(Document document) throws Exception {
        for (Node commentNode : this.xu.getNodeList("descendant::comment()", document)) {
            Node parentWpNode = this.xu.getNode("ancestor::w:p[1]", commentNode);
            if (parentWpNode != null && this.xu.getNode("descendant::w:hlink", parentWpNode) != null) {
                parentWpNode.getParentNode().replaceChild(commentNode, parentWpNode);
            }
        }
    }

    /**
     * 修正大纲样式， 将有 this.OUTLINE_TEMPLATE_FLAG 标记的临时的 w:p 样式放到第一个大纲条目中
     *
     * @param document 全文档搜索
     * @throws Exception 未知
     */
    private String resumeOutlineStyle(String document) throws Exception {
    	String style = "<w:r><w:fldChar w:fldCharType=\"begin\"/></w:r><w:r><w:instrText> TOC \\o \"1-3\" \\h \\z \\u </w:instrText></w:r><w:r><w:fldChar w:fldCharType=\"separate\"/></w:r>";
        return document.replaceFirst(Pattern.quote("<w:hlink"), style + "<w:hlink");
    }

    /**
     * 为包含有 aml:annotation 节点的 wx:sub-section 节点设置书签
     *
     * @param document 处理整个文档
     * @throws Exception 未知
     */
    private void setBookmarksForWxsubsectionNodeList(Document document) throws Exception {
        for (Node commentNode : this.xu.getNodeList("descendant::comment()", document)) {
            if (this.tu.isForeach(commentNode.getNodeValue()) && commentNode.getNextSibling().getNodeName().equals("wx:sub-section")) {
                Node amlannotationNode = this.xu.getNode("descendant::aml:annotation[1]", commentNode.getNextSibling());
                //设置书签模板
                if (amlannotationNode != null) {
                    Foreach each = this.tu.analyzeForeach(commentNode.getNodeValue());
                    this.xu.setAttribute(amlannotationNode, "w:name", this.tu.mkVar(each.ITEM + "." + this.BOOKMARK));
                }
            }
        }
    }

    /**
     * 为大纲中的 w:hlink 节点设置书签
     *
     * @param document 处理整个文档
     * @throws Exception 未知
     */
    private void setBookmarksForWhlinkNodeList(Document document) throws Exception {
        for (Node commentNode : this.xu.getNodeList("descendant::comment()", document)) {
            if (this.tu.isForeach(commentNode.getNodeValue())) {
                Node whlinkNode = this.xu.getNode("descendant::w:hlink[1]", commentNode.getNextSibling());
                if (whlinkNode != null) {
                    Foreach each = this.tu.analyzeForeach(commentNode.getNodeValue());
                    this.xu.setAttribute(whlinkNode, "w:bookmark", this.tu.mkVar(each.ITEM + "." + this.BOOKMARK));
                }
            }
        }
    }

    /**
     * 为大纲设置页码
     *
     * @param document 处理整个文档
     * @throws Exception 未知
     */
    private void setPageNOForOutlines(Document document) throws Exception {
        for (Node commentNode : this.xu.getNodeList("descendant::comment()", document)) {
            if (this.tu.isForeach(commentNode.getNodeValue()) && this.xu.getNode("descendant::w:hlink[1]", commentNode.getNextSibling()) != null) {
                Node nextWpNode = commentNode;
                while ((nextWpNode = nextWpNode.getNextSibling()) != null) {
                    if (nextWpNode.getNodeValue() != null && this.tu.isTemplate(nextWpNode.getNodeValue())) {
                        continue;
                    }
                    Node whlinkNode = this.xu.getNode("descendant::w:hlink[1]", nextWpNode);
                    if (whlinkNode == null) {
                        break;
                    }
                    whlinkNode.getLastChild().getPreviousSibling().getLastChild().getFirstChild().setNodeValue("");
                }

                Node prevWpNode = commentNode;
                while ((prevWpNode = prevWpNode.getPreviousSibling()) != null) {
                    if (prevWpNode.getNodeValue() != null && this.tu.isTemplate(prevWpNode.getNodeValue())) {
                        continue;
                    }
                    Node whlinkNode = this.xu.getNode("descendant::w:hlink[1]", prevWpNode);
                    if (whlinkNode == null) {
                        break;
                    }
                    whlinkNode.getLastChild().getPreviousSibling().getLastChild().getFirstChild().setNodeValue("");
                }

                break;
            }
        }
    }

    /**
     * 处理 sub-section 中，逻辑模板的不对称问题， 开头和结尾不在同一个标签下 注意： 目前这个方法做的不全面，只是处理了 foreach
     * 的情况， 并且只是循环大纲的情况
     *
     * @param document 处理整个文档
     * @throws Exception 未知
     */
    private void fixLogicalTemplatesForSubsectionTags(Document document) throws Exception {
        //循环所有注释节点
        for (Node commentNode : this.xu.getNodeList("descendant::comment()", document)) {
            //假如注释节点是“结束模板”
            if (this.tu.isEnd(commentNode.getNodeValue())) {
                //查找是否有对应的开始节点
                if (this.xu.getNode("child::comment()[2]", commentNode.getParentNode()) == null) {
                    commentNode.getParentNode().getParentNode().insertBefore(commentNode, commentNode.getParentNode());
                }
            }
        }
    }

    /**
     * 为合并单元格设置模板
     *
     * @param document
     * @throws Exception
     */
    private void setTemplatesForMergingCell(Document document) throws Exception {
        //循环检查所有的单元格， 即 w:tc 节点
        for (Node wtcNode : this.xu.getNodeList("descendant::w:tc", document)) {
            //假如该当前 w:tc 节点的前一个节点时 foreach 注释节点， 那么就为当前的 w:tc 添加模板
            if (wtcNode.getPreviousSibling() != null && wtcNode.getPreviousSibling().getNodeType() == Node.COMMENT_NODE) {
                String logicTemplate = wtcNode.getPreviousSibling().getNodeValue();
                if (this.tu.isForeach(logicTemplate)) {
                    //获取 FOREACH 元信息
                    Foreach each = this.tu.analyzeForeach(logicTemplate);
                    //添加跨列模板
                    String colspanTemplate = this.tu.mkVar(each.ITEM + ".colspan");
                    Node wtcW = this.xu.getNode("descendant::w:tcW", wtcNode);
                    Node wgridSpan = this.xu.addNodeBefore(wtcW, "w:gridSpan");
                    this.xu.setAttribute(wgridSpan, "w:val", colspanTemplate);
                    //设置宽度模板
                    this.xu.setAttribute(wtcW, "w:w",
                            this.tu.mkIf(this.tu.mkVar(each.ITEM + ".width") + " == ''") + //开始 IF 判断， 假如用户没有设置模板宽度
                            this.xu.getAttribute(wtcW, "w:w") + //就使用原始模板宽度
                            this.tu.mkElse() + //否则
                            this.tu.mkVar(each.ITEM + ".width") + //就是用用户设置的模板宽度
                            this.tu.mkEndIf());                                                 //结束 IF 判断
                    //添加跨列模板
                    wtcNode.getParentNode().insertBefore(document.createComment(this.tu.mkIf(colspanTemplate + ">0")), wtcNode);
                    wtcNode.getParentNode().insertBefore(document.createComment(this.tu.mkEndIf()), wtcNode.getNextSibling());
                    //添加跨行模板
                    wtcW.getParentNode().appendChild(document.createTextNode("$" + each.ITEM + ".wVmergeTagTemplate"));
                    //设置背景色
                    Node wshd = this.xu.getNode("descendant::w:shd", wtcNode);
                    if (wshd != null) {
                        this.xu.setAttribute(wshd, "w:fill",
                                this.tu.mkIf(this.tu.mkVar(each.ITEM + ".bgColor") + " == ''") + //开始 IF 判断， 假如用户没有设置模板宽度
                                this.xu.getAttribute(wshd, "w:fill") + //就使用原始模板宽度
                                this.tu.mkElse() + //否则
                                this.tu.mkVar(each.ITEM + ".bgColor") + //就是用用户设置的模板宽度
                                this.tu.mkEndIf());       
                    }
                }
            }
        }
    }

    /**
     * 解析模板
     *
     * @param template 要解析的模板
     * @param data 使用的数据
     * @return 解析后的视图
     * @throws Exception 未知
     */
    private String analyzeTemplate(String template, Map<String, Object> data, Map<String, String> templateProperties) throws Exception {
    	//使用模板引擎和数据得到视图
        String view = this.tu.evaluate(template, data, templateProperties);
        
        //整理大纲风格，是否需要添加大纲样式
        if (StringUtils.isNotBlank(this.xu.getAttribute(this.xu.getDocument(template).getDocumentElement(), this.FLAG_NEED_OUTLINE_STYLE))) {
        	view = this.resumeOutlineStyle(view); 
        }
        
        //为新的图片数据设置标识符
    	view = this.replace(view, "<w:binData w:name=\"\" .+?</v:shape>", new Processor() {
			@Override
			public String process(String arg) throws Exception {
				String wnameValue = "wordml://" + DigestUtils.md5Hex(StringUtils.substringBetween(arg, ">", "<")) + ".emz";
				return arg.replace("w:name=\"\"", "w:name=\"" + wnameValue + "\"").replace("src=\"\"", "src=\"" + wnameValue + "\"");
			}
		});
    	
        //消重图片数据
        final Set<String> imageIds = new HashSet<String>();
        view = this.replace(view, "<w:binData .+?</w:binData>", new Processor() {
			@Override
			public String process(String arg) throws Exception {
				return imageIds.add(StringUtils.substringBetween(arg, "w:name=\"", "\"")) ? arg : "";
			}
		});
        
        //消重图片样式数据
        final Set<String> imageStyleIds = new HashSet<String>();
        view = this.replace(view, "<v:shapetype .+?</v:shapetype>", new Processor() {
			@Override
			public String process(String arg) throws Exception {
				return imageStyleIds.add(StringUtils.substringBetween(arg, "id=\"", "\"")) ? arg : "";
			}
		});
        
        return view;
    }

    /**
     * 冒泡逻辑模板到合适的位置，然后注释掉逻辑模板
     *
     * @param wpTemplateNodeList 所有包含有模板的 w:p 节点， 在这些节点下面进行操作
     * @throws Exception 未知
     */
    private void bubblingAndCommentOutLogicTemplate(List<Node> wpTemplateNodeList) throws Exception {
        //用于存放逻辑模板的 w:t
        List<Node> wtLogicTemplateList = new ArrayList<Node>();
        //获取所有包含逻辑模板的 w:t
        for (Node wpTemplateNode : wpTemplateNodeList) {
            this.extractWtLogicTemplateList(wpTemplateNode, wtLogicTemplateList);
        }
        //循环处理包含控制模板的 w:t
        for (Node wtLogicTemplate : wtLogicTemplateList) {
            /*
             * 这个方法会以当前的 w:t 为起始点， 不断得到其父节点， 如果父节点所在的层级有和父节点相同命名的兄弟节点，
             * 那么就使用注释节点将父节点注释掉， 注释内容就是模板内容
             */
            this.commentOutLogicTemplate(wtLogicTemplate);
        }
    }

    /**
     * 将模板放到单独的 w:t 节点下面
     *
     * @param wpTemplateNodeList 所有包含有模板的 w:p 节点， 在这些节点下面进行操作
     * @throws Exception 未知
     */
    private void isolateTemplateIntoWtNode(List<Node> wpTemplateNodeList) throws Exception {
        //循环处理包含模板的 w:p 节点, 将 w:p 节点下的模板碎片结合成一个完整的模板， 放到 w:t 下
        for (Node wpTemplateNode : wpTemplateNodeList) {
            this.integrateTempateFragmentsInWp(wpTemplateNode);
        }
    }
    
    /**
     * 处理 v:shape 模板，
     * 
     * @param document 要操作的模板对象
     * @param data 要使用模板来展示的数据
     * @throws Exception 暂时没有发现
     */
    private void processImageTemplate(Document document) throws Exception {
    	//查找所有的 w:pict 下面的含有模板的 v:shape 节点， 放到 vshapeTemplateNodeList 中
        for (Node vShapeNode : this.xu.getNodeList("descendant::w:pict/descendant::v:shape", document)) {
        	if (this.tu.isVar(this.xu.getString("@alt", vShapeNode))) {
            	this.processVshapeTempalteNode(vShapeNode);
            }
        }
    }
    
    private void processVshapeTempalteNode(Node vshapeTemplateNode) throws Exception {
      	//获取模板名称
        String template = this.xu.getString("@alt", vshapeTemplateNode);
    	String varTemplateName = this.tu.extractVarName(template);
    	if (varTemplateName.matches(this.R_URL)) {
    		template = this.tu.mkVar("WordUtil.encodeImage('" + varTemplateName + "')");
    	}
    	
        //获取 w:binData 节点
    	Node wbinDataNode = this.xu.getNode("descendant::w:binData[1]", vshapeTemplateNode.getOwnerDocument()).cloneNode(false);
		vshapeTemplateNode.getParentNode().insertBefore(wbinDataNode, vshapeTemplateNode);
		wbinDataNode.appendChild(wbinDataNode.getOwnerDocument().createTextNode(template));
		this.xu.setAttribute(wbinDataNode, "w:name", "");
		
    	//将 v:shape 节点的  alt 属性值清 空
        this.xu.setAttribute(vshapeTemplateNode, "alt", "");
        //将 v:shape 节点下的  v:imagedata 节点 的 o:title 属性值清 空
        this.xu.setAttribute(vshapeTemplateNode.getFirstChild(), "o:title", "");
        //将 v:shape 节点下的 src 节点 的 o:title 属性值清 空
        this.xu.setAttribute(vshapeTemplateNode.getFirstChild(), "src", "");
    }
    
    private void md5ImageDataForAllWbinDatas(Document document) throws Exception {
        //循环所有的 w:binData
        for (Node wbinData : this.xu.getNodeList("descendant::w:binData", document)) {
            //获取图片数据
            String imageData = this.xu.getText(wbinData);
            //获取图片的 md5 值, 重置图片的标识符
            String oldWnameValue = this.xu.getAttribute(wbinData, "w:name");
            String newWnameValue = "wordml://" + DigestUtils.md5Hex(imageData) + ".emz";
            for (Node vimagedataNode : this.xu.getNodeList("descendant::v:imagedata[@src='" + oldWnameValue + "']", document)) {
                this.xu.setAttribute(vimagedataNode, "src", newWnameValue);
            }
            this.xu.setAttribute(wbinData, "w:name", newWnameValue);
        }
    }

    /**
     * 这个方法会以当前的 w:t 为起始点， 不断得到其父节点， 如果父节点所在的层级有和父节点相同命名的兄弟节点，
     * 那么就使用注释节点将父节点注释掉，注释内容就是模板内容
     *
     * @param wtLogicTemplate 要处理的 w;t 节点， 此节点包含控制类型的模板冒泡从此节点开始
     * @throws Exception 可能会在冒泡过程中出现未知异常
     */
    private void commentOutLogicTemplate(Node wtLogicTemplate) throws Exception {
        Node currentNode = wtLogicTemplate;
        //假如当前节点的父节点不为 null， 那么就继续冒泡
        while (currentNode.getParentNode() != null) {
            //得到当前节点名称
            String nodeName = currentNode.getNodeName();
            //查看当前层级是否有相同命名的节点
            Node sameNode = this.xu.getNode("descendant::" + nodeName + "[2]", currentNode.getParentNode());
            //假如有的话， 冒泡停止， 使用注释节点替换当前节点
            if (sameNode != null
            		//假如当前节点名称的 w:r, 并且此节点的兄弟节点中都没有文本，那么再往上跳
//            		&& (!"w:r".equals(currentNode.getNodeName()) || 
//            				!StringUtils.equals(this.xu.getText(currentNode), this.xu.getText(currentNode.getParentNode())))
            	) {
            	Comment comment = currentNode.getOwnerDocument().createComment(this.xu.getText(wtLogicTemplate));

                currentNode.getParentNode().replaceChild(currentNode.getParentNode().appendChild(comment), currentNode);	
            } else {
                currentNode = currentNode.getParentNode();
            }
        }
    }
    
    /**
     * 将换页标签单独分离出来
     * @param document 全文的搜索
     * @throws Exception 未知
     */
    private void isolateWbrPage(Document document) throws Exception {
    	for (Node wbrNode : this.xu.getNodeList("descendant::w:br[@w:type='page']", document)) {
    		//拷贝一份 w:p, 用来包裹换页标签
    		Node wp = wbrNode.getParentNode().getParentNode();
                Node newWp = wp.getParentNode().insertBefore(wp.getParentNode().appendChild(wp.cloneNode(false)), wp);
                newWp.appendChild(wbrNode.getParentNode());
                wp.getParentNode().insertBefore(wp, newWp);
    	}
    }

    /**
     * 提取 w:t 列表， 这个列表中的 w:t 包含像 for 循环， if判断等等这种类型的模板， 这一类模板不会产生数据
     *
     * @param wpTemplateNode 从该 w:p 节点下提取包含逻辑模板的 w:t
     * @param wtLogicTemplateList 存放包含逻辑模板的 w:t 的容器
     * @throws Exception 未知
     */
    private void extractWtLogicTemplateList(Node wpTemplateNode, List<Node> wtLogicTemplateList) throws Exception {
        //获取 w:p 模板节点下所有的 w:r, 循环判断获取符合条件的 w:r
        for (Node wtNode : this.xu.getNodeList("descendant::w:t", wpTemplateNode)) {
            String text = this.xu.getText(wtNode);
            if (this.tu.isLogic(text)) {
                wtLogicTemplateList.add(wtNode);
            }
        }
    }

    /**
     * 这个方法将 w:p 下面的模板碎片整合成一个完整的模板，放到 w:r 下面 一个 w:p 下面可能有多个模板， 一个 w:r 可能包含多个模板，
     * 一个模板可能跨越多个 w:r等等，这些情况都有在整合模板碎片的过程中考虑到
     *
     * @param wpTemplateNode 包含模板碎片的 w:p 节点， 所有的DOM操作都在这个节点之下
     * @throws Exception 暂时没有预料到的情况
     */
    private void integrateTempateFragmentsInWp(Node wpTemplateNode) throws Exception {
        //得到 wpTemplateNode 下面所有的文本
        String wpText = this.xu.getText(wpTemplateNode);
        //得到 wpText 中所有的模板字符串列表， 并将这些模板字符串放到队列 templateList 中
        List<String> templateList = this.tu.extractTemplates(wpText);
        //得到 wpTemplateNode 下面的第一个 w:r 节点， 后面的迭代做准备
        //首先假设是处理目录中存在的模板
        Node nextWrTemplateNode = this.xu.getNode("w:hlink[1]/w:r[1]", wpTemplateNode);
        //假如不是目录中的模板的话
        if (nextWrTemplateNode == null) {
        	nextWrTemplateNode = this.xu.getNode("w:r[1]", wpTemplateNode);
        }

        /*
         * 这个循环会依次迭代 wpTemplateNode 下面所有的 w:r 节点，
         * this.integrateWrsTempateFragmentsForOneTemplate(）方法会 从
         * nextWrTemplateNode 开始往后遍历它的兄弟节点， 并记录每个 wr 下面的文本， 直到所遍历过的 w:r
         * 节点下面的文本可以组成 一个模板， 然后将这个模板提取出来， 放到一个 w:r 中， 最后返回 wpTemplateNode
         * 中下一个模板匹配的开始节点
         */
        for (int i = 0; i < templateList.size() && nextWrTemplateNode != null; i++) {
            nextWrTemplateNode = this.integrateWrsTempateFragmentsForOneTemplate(nextWrTemplateNode, templateList.get(i));
        }
    }

    /**
     * 它从服务于 this.integrateTempateFragmentsInWp（）, 从 nextWrTemplateNode
     * 开始往后遍历它的兄弟节点， 并提去所遍历过的节点的文本，记录到一个 StringBuilder 中， 当 StringBuilder
     * 中记录的文本包含当前所有匹配的 模板 template 时， 停止跌待， 将模板所占据的一个或多个 w:r 节点变为一个 w:r
     * 节点，用于存放模板
     *
     * @param nextWrNode 迭代开始出
     * @param template 所有匹配的模板
     * @return 返回下一个模板匹配开始出
     * @throws Exception 暂时没有预料到的情况
     */
    private Node integrateWrsTempateFragmentsForOneTemplate(Node nextWrTemplateNode, String template) throws Exception {
        //用于存放所遍历过的 w:r 节点中的包含的文本
        StringBuilder roughTemplate = new StringBuilder();
        while (nextWrTemplateNode != null) {
            //保存当前遍历的 w:r 节点中包含的文本
            roughTemplate.append(this.xu.getText(nextWrTemplateNode));
            //假如 roughTemplate 刚好包含所要匹配的模板， 那么开始在所遍历过的 w:r 节点之上进行模板提取操作
            if (roughTemplate.toString().contains(template)) {
                //从当前所在的节点位置开始， 进行模板碎片整合操作
                nextWrTemplateNode = this.processIteratedWrForOneTemplate(nextWrTemplateNode, template);
                roughTemplate.delete(0, roughTemplate.length());
                break;
            }
            //Iterating the next w:r.
            nextWrTemplateNode = nextWrTemplateNode.getNextSibling();
        }
        return nextWrTemplateNode;
    }

    /**
     * 它服务于 this.integrateWrsTempateFragmentsForOneTemplate(), 为一个模板做碎片整合操作
     *
     * @param currentWr 它是当前程序正在处理的节点， 也是一个 w:p 下面的一个模板的结尾节点
     * @param template 这是所有将模板碎片整理成的最终模板的样子
     * @return 返回当前 w:p 下面下一个模板匹配过程所开始的节点
     * @throws Exception 暂时没有预料到的情况
     */
    private Node processIteratedWrForOneTemplate(Node endedWrNode, String template) throws Exception {
        //当前 w:p 下面下一个模板匹配过程所开始的节点
        Node resultingNextNode = endedWrNode;
        //一个模板的结尾节点 w:r 下面所包含的文本
        String endedWrText = this.xu.getText(endedWrNode);
        //假如 endedWrText 包含template， 另外还包含其他字符
        if (!endedWrText.equals(template) && endedWrText.contains(template)) {
            /*
             * 假如 endedWrText 既不以 template 开始， 也不以 template 结尾， 那么将当前的节点
             * endedWrNode 截为三个 w:r 节点， 中间的 w:r 节点单独包含模板文本
             */
            if (!endedWrText.startsWith(template) && !endedWrText.endsWith(template)) {
                this.addNewWrNodeBeforeReferencedNode(endedWrNode, endedWrText.substring(0, endedWrText.indexOf(template)));
                this.addNewWrNodeBeforeReferencedNode(endedWrNode, template);
                this.setTextForWr(endedWrNode, endedWrText.substring(endedWrText.indexOf(template) + template.length()));
            } /*
             * 假如 endedWrText 是以 template 开始， 那么当前的节点 endedWrNode 截为两个 w:r 节点，
             * 第一个 w:r 单独包含模板节点
             */ else if (endedWrText.startsWith(template)) {
                this.addNewWrNodeBeforeReferencedNode(endedWrNode, template);
                this.setTextForWr(endedWrNode, endedWrText.substring(template.length()));
            } /*
             * 假如 endedWrText 是以 template 结尾， 那么当前的节点 endedWrNode 截为两个 w:r 节点，
             * 第二个 w:r 单独包含模板节点 并且将并将下一个模板匹配过程所开始的节点往后移动一个节点
             */ else if (endedWrText.endsWith(template)) {
                this.addNewWrNodeBeforeReferencedNode(endedWrNode, endedWrText.substring(0, endedWrText.indexOf(template)));
                this.setTextForWr(endedWrNode, template);

                resultingNextNode = endedWrNode.getNextSibling();
            }
        } /*
         * 假如 endedWrText 和当前正在匹配的模板有一个交集， 则表明当前模板跨越当前的 w:r 和 它前面的 w:r，那么 将当前
         * w:r 中的模板部分剔除掉， 然后向前移动一个节点， 判断前一个节点是否是模板的开始处
         */ else if (!endedWrText.equals(template)) {
            //endedWrText 和模板交集的长度
            int intersectionLength = this.getIntersectionLength(endedWrText, template);
            //获取将交集部分从模板剔除后的部分
            String remainingInTemplate = template.substring(0, template.length() - intersectionLength);
            //获取前一个节点
            Node prevWrNode = endedWrNode.getPreviousSibling();

            //假如模板完全包含结尾节点， 那么将结尾节点删除
            if (template.contains(endedWrText)) {
                resultingNextNode = endedWrNode.getNextSibling();
                endedWrNode.getParentNode().removeChild(endedWrNode);
            } //否则将模板部分从结尾节点中剔除
            else {
                String reservedText = endedWrText.substring(intersectionLength);
                Node textNode = this.xu.getNode("descendant::text()[1]", endedWrNode);
                textNode.setNodeValue(reservedText);
            }

            //调用此方法， 进行向前搜索， 直到找到模板的开始 w:r 节点
            this.forwardSearchOnWrsForOneTemplate(prevWrNode, remainingInTemplate, template);
        }

        return resultingNextNode;
    }

    /**
     * 它服务于this.processIteratedWrForOneTemplate(), 从给定节点开始寻找模板的开始 w:r 节点
     * 这是一个递归方法， 会不断获取前一个 w:r 节点， 直到要匹配的模板的开始处
     *
     * @param prevWrNode 给定的开始节点
     * @param remainingInTemplate 每次判断完一个 w:r 节点， 它的长度都会减去 被检查的 w:r 包含的文本的长度，
     * 当遇到一个 w:r 下面的文本包含 remainingInTemplate 时， 就说明这个 w:r 是所有匹配的模板的开始处
     * @param template 所有匹配的模板
     * @throws Exception 暂时没有预料到的情况
     */
    private void forwardSearchOnWrsForOneTemplate(Node prevWrNode, String remainingInTemplate, String template) throws Exception {
        String currentWrText = this.xu.getText(prevWrNode);

        //假如 remainingInTemplate 包含当前 w:r 所包含的文本
        if (remainingInTemplate.contains(currentWrText)) {
            /*
             * 假如 remainingInTemplate 以 w:r 所包含的文本开始， 说这个 w:r 是所有匹配的模板的开始处,
             * 那么将这个 w:r 的文本节点设置为 template
             */
            if (remainingInTemplate.startsWith(currentWrText) && !StringUtils.isBlank(currentWrText)) {
                this.setTextForWr(prevWrNode, template);
            } /*
             * 否则所有匹配的模板还跨越前一个 w:r， 那么将当前的 w:r 删除， 将 remainingInTemplate 截短
             * currentWrText.length()长度，继续向前搜索
             */ else {
                prevWrNode = prevWrNode.getPreviousSibling();
                prevWrNode.getParentNode().removeChild(prevWrNode.getNextSibling());
                remainingInTemplate = remainingInTemplate.substring(0, remainingInTemplate.length() - currentWrText.length());
                this.forwardSearchOnWrsForOneTemplate(prevWrNode, remainingInTemplate, template);
            }
        } /*
         * 假如 remainingInTemplate 不包含当前 w:r 所包含的文本， 说明当前 w:r 是所要匹配的模板的开始处， 将该
         * w:r 截断为两个 w:r ， 第二个存放模板
         */ else {
            this.setTextForWr(prevWrNode, template);
            this.addNewWrNodeBeforeReferencedNode(prevWrNode, currentWrText.substring(0, currentWrText.length() - remainingInTemplate.length()));
        }
    }

    /**
     * 为 w:r 下面的一个文本节点设置文本
     *
     * @param wrNode 从这个节点下面找文本节点
     * @param text 所有设置的文本
     * @throws Exception 暂时没有预料到的情况
     */
    private void setTextForWr(Node wrNode, String text) throws Exception {
        this.xu.getNode("descendant::text()[1]", wrNode).setNodeValue(text);
    }

    /**
     * 它创建一个新的 w:r 节点， 为这个新的节点下的文本节点设置文本， 并把它放到 referencedNode 之前
     *
     * @param referencedNode 用它来定位新节点的位置
     * @param text 要为新节点设入的文本
     * @throws Exception 暂时没有预料到的情况
     */
    private void addNewWrNodeBeforeReferencedNode(Node referencedNode, String text) throws Exception {
        Node parent = referencedNode.getParentNode();
        Node newNode = parent.insertBefore(parent.appendChild(referencedNode.cloneNode(true)), referencedNode);
        this.setTextForWr(newNode, text);
    }

    /**
     * 获取两个字符串的交集长度， 比如： 'abcd' 和 'cdef', 'cd' 是两个字符串的交集
     *
     * @param headIntersectionString 字符串开头处于交集区域的串
     * @param trailIntersectionString 字符串结尾处于交集区域的串
     * @return 返回交集区域的长度
     * @throws Exception
     */
    private int getIntersectionLength(String headIntersectionString, String trailIntersectionString) throws Exception {
        for (int breakpoint = 1; breakpoint <= headIntersectionString.length(); breakpoint++) {
            if (trailIntersectionString.endsWith(headIntersectionString.substring(0, breakpoint))) {
                return breakpoint;
            }
        }
        throw new Exception("There is no intersection point.");
    }

    /**
     * 标记子孙文本节点包含模板的所有 w:p 节点，为其添加一个属性， 使用 this.ATTR_WP_TL_FLAG_NAME 作为属性名称
     *
     * @param document 这是所要查找的对象
     * @return 返回子孙文本节点包含模板的 w:p 节点列表
     * @throws Exception 暂时没有预料到的情况
     */
    private List<Node> getWpTemplateNodeList(Document document) throws Exception {
        //子孙文本节点包含模板的 w:p 节点列表
        List<Node> wpTemplateList = new ArrayList<Node>();

        //整个DOM文档中所有的 w:p 节点, 循环判断 w:p 下面的子孙文本节点是否包含模板，假如包含，就放到 wpTemplateList 中
        for (Node wpNode : this.xu.getNodeList("descendant::w:p", document)) {
            String text = this.xu.getText(wpNode);
            if (!StringUtils.isBlank(text) && this.tu.isTemplateIn(text)) {         
                wpTemplateList.add(wpNode);
            }
        }

        //返回子孙文本节点包含模板的 w:p 节点列表
        return wpTemplateList;
    }
    
	private interface Processor { String process(String arg) throws Exception; }
	private String replace(String string, String regex, Processor processor) throws Exception {
    	StringBuffer sb = new StringBuffer();
		Matcher m = Pattern.compile(regex).matcher(string);
		while (m.find()) {
			m.appendReplacement(sb, Matcher.quoteReplacement(processor.process(m.group())));
		}
		m.appendTail(sb);
		return sb.toString();
    }
    
    /************************以下是静态工具方法*********************************/
	public static String encodeImage(String url) throws Exception {
        return Base64.encodeBase64String(IOUtils.toByteArray(new FileInputStream(url)));
	}
}



