package cn.core.framework.utils.output;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.core.framework.utils.output.OfficeXmlTemplate.TemplateUtil.Type;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 注意：此工具只能解析 word 2003 XML 文档 模板中包含中文的引号，逻辑模板没有结尾模板， 模板没有对应的数据等情况都会导致模板解析失败
 * 并且该文件文档必须是 UTF-8 编码格式的
 *
 * Originated by 刘彤彬 on 20013/12/04
 */
@Deprecated
public class OfficeXmlTemplate {

    /* 这个工具类提供了对于DOM对象的公共操作方法 */
    private XmlUtil xmlUtil = new XmlUtil(true);
    /* 这个工具类提供了计算模板的方法， 并且提供了模板的正则表达式用于匹配模板 */
    private TemplateUtil templateUtil;
    /* URL 正则表达式 */
    private final String URL_REGEX = "[a-zA-Z0-9]+:\\/\\/.+?";

    /**
     * 构造器构造对象，接收要操作的模板类型对应的 TemplateUtil
     *
     * @param isFtl 是要实例化的对象是用于解析 Freemarker 模板
     */
    public OfficeXmlTemplate(boolean isFtl) {
        this.templateUtil = TemplateUtil.getInstance(isFtl ? Type.FTL : Type.VM);
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
    public String getRealWord(InputStream xmlInputStream, Map<String, Object> data, Map<String, String> templateProperties) throws Exception {
        Document document = this.xmlUtil.getDocument(xmlInputStream);
        // 假如模板是最原始的粗糙模板，就处理为有规则的模板
        if (StringUtils.isBlank(this.xmlUtil.getAttribute(document.getDocumentElement(), "isRealTemplate"))) {
            // 得到所有包含模板的 w:p 节点
            List<Node> wpTemplateNodeList = this.getWpTemplateNodeList(document);
            // 整理模板碎片, 将模板放到单独的 w:t 节点下面
            this.isolateTemplateIntoWtNode(wpTemplateNodeList);
            // 冒泡逻辑模板到合适的位置，然后注释掉逻辑模板
            this.bubblingAndCommentOutLogicTemplate(wpTemplateNodeList);
            // 为合并单元格设置模板，在单元格前后添加判断
            this.setTemplatesForMergingCell(document);
        }
        // 得到所有包含模板的 w:p 节点
        List<Node> wpTemplateNodeList = this.getWpTemplateNodeList(document);
        // 模板解析
        String realView = this.analyzeTemplate(document, wpTemplateNodeList, data, templateProperties);
        // 我正打算在这一步操作之前， 将所有的 w:binData 和 v:shapetype 放到一个 w:r
        // 下面，但是没有成功，以后可以进一步尝试一下
        // 这一步是用于当文档中有多个命名相同的 w:binData, v:shapetype也是， 减少文件大小 消除视图中重复的元素
        document = this.xmlUtil.getDocument(new ByteArrayInputStream(realView.getBytes("UTF-8")));
        this.eliminateConflictForWordView(document, "w:binData", "w:name");
        this.eliminateConflictForWordView(document, "v:shapetype", "id");
        // 返回最终文档视图
        return this.xmlUtil.getXml(document);
    }

    /**
     * 暴露给客户端的方法，根据 word 的 xml 模板流和提供的数据返回两者相结合的视图。
     *
     * @param xmlInputStream Office Word 文档的xml描述流， 这个一个原始模板。
     * @return 返回经过处理后的模板，这个模板可以被某一种模板引擎计算。
     * @throws Exception 模板格式不正确会引发异常， 这个异常需要被捕获，以为用户提供正确的错误信息。
     */
    public String getRealTemplate(InputStream xmlInputStream) throws Exception {
        Document document = this.xmlUtil.getDocument(xmlInputStream);
        // 得到所有包含模板的 w:p 节点
        List<Node> wpTemplateNodeList = this.getWpTemplateNodeList(document);
        // 整理模板碎片, 将模板放到单独的 w:t 节点下面
        this.isolateTemplateIntoWtNode(wpTemplateNodeList);
        // 冒泡逻辑模板到合适的位置，然后注释掉逻辑模板
        this.bubblingAndCommentOutLogicTemplate(wpTemplateNodeList);
        // 为合并单元格设置模板，在单元格前后添加判断
        this.setTemplatesForMergingCell(document);
        // 为模板设置“已经被处理”标记
        this.xmlUtil.setAttributes(document.getDocumentElement(), "isRealTemplate", "true");
        // 返回最终文档视图
        return this.xmlUtil.getXml(document);
    }

    /**
     * 为合并单元格设置模板
     *
     * @param document
     * @throws Exception
     */
    private void setTemplatesForMergingCell(Document document) throws Exception {
        NodeList wtcNodeList = (NodeList) this.xmlUtil.parse("descendant::w:tc", document, this.xmlUtil.T_NS);
        //循环检查所有的单元格， 即 w:tc 节点
        for (Node wtcNode : this.xmlUtil.getIterable(wtcNodeList)) {
            //假如该当前 w:tc 节点的前一个节点时 foreach 注释节点， 那么就为当前的 w:tc 添加模板
            if (wtcNode.getPreviousSibling() != null && wtcNode.getPreviousSibling().getNodeType() == Node.COMMENT_NODE) {
                String logicTemplate = wtcNode.getPreviousSibling().getNodeValue();
                if (this.templateUtil.extractLogicName(logicTemplate).equals("foreach")) {
                    //获取 FOREACH 元信息
                    Map<String, String> foreachMeta = this.templateUtil.analyzeForeach(logicTemplate);
                    //添加跨列模板
                    String colspanTemplate = this.templateUtil.PSEUDO_VAR.replace("NAME", foreachMeta.get("item") + ".colspan");
                    Node wtcW = (Node) this.xmlUtil.parse("descendant::w:tcW", wtcNode, this.xmlUtil.T_N);
                    Node wgridSpan = this.xmlUtil.addNodeBefore(wtcW, "w:gridSpan");
                    this.xmlUtil.setAttributes(wgridSpan, "w:val", colspanTemplate);
                    //设置宽度模板
                    String widthOriginalWidth = this.xmlUtil.getAttribute(wtcW, "w:w");
                    String widthNewWidthTemplate = this.templateUtil.PSEUDO_VAR.replace("NAME", foreachMeta.get("item") + ".width");
                    String widthIfTemplate = this.templateUtil.PSEUDO_IF.replace("CONDITION", widthNewWidthTemplate + " == ''");
                    String widthElseTemplate = this.templateUtil.PSEUDO_ELSE;
                    String widthEndTemplate = this.templateUtil.PSEUDO_END.replace("ENDTYPE", "if");
                    this.xmlUtil.setAttributes(wtcW, "w:w", widthIfTemplate + widthOriginalWidth + widthElseTemplate + widthNewWidthTemplate + widthEndTemplate);
                    //添加跨列模板
                    String spanIfTemplate = this.templateUtil.PSEUDO_IF.replace("CONDITION", colspanTemplate + ">0");
                    String spanEndTemplate = this.templateUtil.PSEUDO_END.replace("ENDTYPE", "if");
                    wtcNode.getParentNode().insertBefore(document.createComment(spanIfTemplate), wtcNode);
                    wtcNode.getParentNode().insertBefore(document.createComment(spanEndTemplate), wtcNode.getNextSibling());
                    //添加跨行模板
                    wtcW.getParentNode().appendChild(document.createTextNode("$" + foreachMeta.get("item") + ".wVmergeTagTemplate"));
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
    private String analyzeTemplate(Document document, List<Node> wpTemplateNodeList, Map<String, Object> data, Map<String, String> templateProperties) throws Exception {
        // 假如数据存储中没有模板对应的数据， 那么在数据存储中放入模板的名称作为数据
        for (Node wpTemplateNode : wpTemplateNodeList) {
            this.putMockDataForPreventingNullPointException(wpTemplateNode, data);
        }
        // 处理图片模板
        String template = this.processImageTemplate(document, data);
        // 使用模板引擎和数据得到视图
        return this.templateUtil.evaluate(template, data, templateProperties);
    }

    /**
     * 冒泡逻辑模板到合适的位置，然后注释掉逻辑模板
     *
     * @param wpTemplateNodeList 所有包含有模板的 w:p 节点， 在这些节点下面进行操作
     * @throws Exception 未知
     */
    private void bubblingAndCommentOutLogicTemplate(List<Node> wpTemplateNodeList) throws Exception {
        // 用于存放逻辑模板的 w:t
        List<Node> wtLogicTemplateList = new ArrayList<Node>();
        // 获取所有包含逻辑模板的 w:t
        for (Node wpTemplateNode : wpTemplateNodeList) {
            this.extractWtLogicTemplateList(wpTemplateNode, wtLogicTemplateList);
        }
        // 循环处理包含控制模板的 w:t
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
        // 循环处理包含模板的 w:p 节点, 将 w:p 节点下的模板碎片结合成一个完整的模板， 放到 w:t 下
        for (Node wpTemplateNode : wpTemplateNodeList) {
            this.integrateTempateFragmentsInWp(wpTemplateNode);
        }
        // 将 w:t 下面的所有存储模板的文本节点替换为 CDATA, 使用 CDATA 来存储模板， 以防模板指向的数据对 XML word
        // 的格式产生损坏
        for (Node wpTemplateNode : wpTemplateNodeList) {
            this.packupVarTemplateUsingCDataNode(wpTemplateNode);
        }
    }

    /**
     * 这是处理图片模板的方法入口， 假如图片模板在 data 中没有对应的数据， 那么就在数据层 data 放入模板的名称作为数据
     *
     * @param document 要操作的模板对象
     * @param data 要使用模板来展示的数据
     * @throws Exception 暂时没有发现
     */
    private String processImageTemplate(Document document, Map<String, Object> data) throws Exception {
        // 获取包含模板的 v:shape 节点列表
        List<Node> vshapeTemplateNodeList = this.getVshapeTemplateNodeList(document, data);
        // 循环处理包含模板的 v:shape 节点列表
        for (Node vshapeTemplateNode : vshapeTemplateNodeList) {
            /**
             * 这个方法会获取当前 v:shape 对应的 w:binData, 这个 w:binData 仅包含一个 模板文本节点， 假如这种
             * w:binData 之前 不存在， 则会创建一个
             */
            this.processVshapeTempalteNode(vshapeTemplateNode);
        }
        // 删除没有被引用的 w:binData 节点
        this.removeUnusefulWbinData(document);
        // 读取数据模板的名称为URL的变量
        this.readImageForURLImageTemplate(document);
        // 返回处理完图片之后的模板
        return this.xmlUtil.getXml(document);
    }

    /**
     * 将变量模板使用 CDATA 节点存放， 以防变量模板指向的真实数据对 XML word 文件的格式产生损坏 他会寻找所有 w:t
     * 下面的文本节点，假如文本节点的值是一个模板， 那么使用 CDATA 节点替换该文本节点 来存放模板
     *
     * @param wpTemplateNode 在该 w:p 模板节点下面进行查找替换操作
     * @throws Exception 未知
     */
    private void packupVarTemplateUsingCDataNode(Node wpTemplateNode) throws Exception {
        // 查找模板 w:p 下面的所有 w:r 节点

        NodeList varTemplateTextNodeList = (NodeList) this.xmlUtil.parse("descendant::w:t/text()", wpTemplateNode, this.xmlUtil.T_NS);
        // 循环判断文本节点是否存放着一个模板， 假如存放着的话， 使用 CDATA 节点替换
        for (Node varTemplateTextNode : this.xmlUtil.getIterable(varTemplateTextNodeList)) {
            if (varTemplateTextNode.getNodeValue().matches(this.templateUtil.REGEX_VAR)) {
                Node cdataNode = wpTemplateNode.getOwnerDocument().createCDATASection(varTemplateTextNode.getNodeValue());
                varTemplateTextNode.getParentNode().replaceChild(cdataNode, varTemplateTextNode);
            }
        }
    }

    /**
     * 假如变量模板在数据块中没有对应的数据，那么就将变量模板的名字作为数据
     *
     * @param wpTemplateNode 包含变量模板的 w:p 节点
     * @param data 数据存储仓库
     * @throws Exception 未知
     */
    private void putMockDataForPreventingNullPointException(Node wpTemplateNode, Map<String, Object> data) throws Exception {
        // 查找整个文档下面的所有 w:t 节点
        NodeList varTemplateTextNodeList = (NodeList) this.xmlUtil.parse("descendant::w:t/text()", wpTemplateNode, this.xmlUtil.T_NS);
        // 循环判断文本节点是否存放着一个模板， 假如存放着的话， 使用 CDATA 节点替换
        for (Node varTemplateTextNode : this.xmlUtil.getIterable(varTemplateTextNodeList)) {
            String varTemplateText = varTemplateTextNode.getNodeValue();
            // 假如这个变量模板是一个简单的模板，并且没有对应的数据存在，那么就将该变量的名称作为数据
            if (varTemplateText.matches(this.templateUtil.REGEX_VAR)) {
                String varTemplateName = this.templateUtil.extractVarName(varTemplateText).trim();
                if (varTemplateName.matches("\\w+") && data.get(varTemplateName) == null) {
                    data.put(varTemplateName, varTemplateName);
                }
            }
        }
    }

    /**
     * 图片中的模板可能存储的是一个URL， 所有在解析模板之前要先读取这些数据
     *
     * @param document 要在该文档下面进行查找
     * @param data 将读取到的图片数据放到这个 Map 中， 供后面的视图展示使用
     * @throws Exception 可能会发生网络异常
     */
    private void readImageForURLImageTemplate(Document document) throws Exception {
        // 读取所有的 w:binData 列表
        NodeList wbinDataNodeList = (NodeList) this.xmlUtil.parse("descendant::w:binData", document, this.xmlUtil.T_NS);

        // 循环判断 w:binData 中的模板的名称是否是一个 URL， 假如是的话，就将此 URL 指向的图片读取出来
        for (int i = 0; i < wbinDataNodeList.getLength(); i++) {
            String template = this.xmlUtil.getText(wbinDataNodeList.item(i));
            String templateName = this.templateUtil.extractVarName(template);
            if (templateName.matches(this.URL_REGEX)) {
                // 将图片数据放到 w:binData 节点中
                String imageData = this.readImage(templateName);
                wbinDataNodeList.item(i).getFirstChild().setNodeValue(imageData);
            }
        }
    }

    /**
     * 根据一个 URL 读取图片
     *
     * @param imageURL 图片地址
     * @return 被base64编码后的图片数据
     */
    private String readImage(String imageURL) {
        try {
            byte[] imageData = IOUtils.toByteArray(new FileInputStream(imageURL));
            return Base64.encodeBase64String(imageData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 这一步是用于当文档中有多个命名相同的 w:binData 或 v:shapetype，那么只保留一份
     *
     * @param document 在该文档下面进行查找
     * @param nodeName 要检查的节点名称
     * @param checkedAttrName 根据该属性名称来检查两个节点是否相同
     * @throws Exception 没有预料到会抛出什么异常
     */
    private void eliminateConflictForWordView(Document document, String nodeName, String checkedAttrName) throws Exception {
        // 存储已有的节点的被用作检查的属性的名称
        List<String> existingNodeNameList = new ArrayList<String>();
        // 获取整个文档中要检查的节点
        NodeList nodelist = (NodeList) this.xmlUtil.parse("descendant::" + nodeName, document, this.xmlUtil.T_NS);

        for (int i = 0; i < nodelist.getLength(); i++) {
            // 预先获取节点
            Node currentNode = nodelist.item(i);
            // 获取被用作检查的属性的值
            String wname = this.xmlUtil.parse("@" + checkedAttrName, currentNode, this.xmlUtil.T_S).toString();
            // 判断是否该值已经在 existingNodeNameList 中存在， 如果不存在，就加到列表中
            if (!existingNodeNameList.contains(wname)) {
                existingNodeNameList.add(wname);
            } // 如果存在，就删除这个节点
            else {
                currentNode.getParentNode().removeChild(currentNode);
            }
        }
    }

    /**
     * 获取包含模板的 v:shpae 对应的 w:binData, 如果该 w:binData 不存在， 就创建一个， 这个 w:binData
     * 包含一个模板文本节点 该模板和当前的 v:shape 所包含的模板相同
     *
     * @param vshapeTemplateNode 当前正在处理的包含模板的 v:shape 节点
     * @return 返回对应的 w:binData 节点， 该节点存储了表示图片数据的模板
     * @throws Exception 没有预料到会抛出什么异常
     */
    private Node getWbinDataTemplateNode(Node vshapeTemplateNode) throws Exception {
        // 存储要返回的 w:binData 节点的引用
        Node wbinDataTemplateNode = null;

        // 获取当前 v:shape 中的 alt 属性值， 该属性值就是模板
        String template = vshapeTemplateNode.getAttributes().getNamedItem("alt").getNodeValue();
        // 获取所有的 w:binData 节点列表
        NodeList wbinDataNodeList = (NodeList) this.xmlUtil.parse("descendant::w:binData", vshapeTemplateNode.getOwnerDocument(), this.xmlUtil.T_NS);

        // 循环检查 w:binData 是否包含和当前 v:shape 相同的模板， 如果有的话，就跳出循环， 将这个 w:binData
        // 作为结果返回
        for (int i = 0; i < wbinDataNodeList.getLength(); i++) {
            if (this.xmlUtil.getText(wbinDataNodeList.item(i)).equals(template)) {
                wbinDataTemplateNode = wbinDataNodeList.item(i);
                break;
            }
        }

        // 如果整个文档中没有符合条件的 w:binData, 那么就创建一个
        if (wbinDataTemplateNode == null) {
            // 获取新创建的 w:binData
            wbinDataTemplateNode = wbinDataNodeList.item(0).getParentNode().insertBefore(wbinDataNodeList.item(0).cloneNode(false), wbinDataNodeList.item(0));
            // 为该新建的 w;binData 添加文本
            wbinDataTemplateNode.appendChild(vshapeTemplateNode.getOwnerDocument().createTextNode(template));
            // 创建 w:name 属性， 并为该属性赋予一个值
            Node wNameNode = vshapeTemplateNode.getOwnerDocument().createAttributeNS("http://schemas.microsoft.com/office/word/2003/wordml", "w:name");
            wNameNode.setNodeValue("wordml://" + java.util.UUID.randomUUID().toString());
            wbinDataTemplateNode.getAttributes().setNamedItem(wNameNode);
        }

        // 返回包含的模板和 v:shape 的模板相同的 w:binData
        return wbinDataTemplateNode;
    }

    /**
     * 获取包含模板的 v:shape 对应的 w:binData, 这个 w:binData 必须包含和 v:shape 相同的模板， 如果没有这种
     * w:binData, 那么就在整个 DOM 文档中的第一个 w:binData 之前创建一个， 为该新的 w:binData 赋予一个
     * w:name 值， 当前 v:shape 下面的 v:imagedata 的 src 属性值和 w:binData 的属性值相同 最后消除
     * v:shape 的 alt 属性的模板值， 和 v:imagedata 的 o:title 的属性值
     *
     * @param vshapeTemplateNode 要处理的包含模板的 v:shape 节点
     * @throws Exception 没有预料到会抛出什么异常
     */
    private void processVshapeTempalteNode(Node vshapeTemplateNode) throws Exception {
        // 通过 this.getWbinDataTemplateNode() 获取对应的 w:binData
        Node wbinDataTempateNode = this.getWbinDataTemplateNode(vshapeTemplateNode);
        // 为 v:shape 下面的 v:imagedata 的 src 属性赋予 w:binData 的 名称
        String wName = wbinDataTempateNode.getAttributes().getNamedItem("w:name").getNodeValue();
        ((Node) this.xmlUtil.parse("child::v:imagedata/@src", vshapeTemplateNode, this.xmlUtil.T_N)).setNodeValue(wName);

        // 清空不必要的模板
        ((Node) this.xmlUtil.parse("@alt", vshapeTemplateNode, this.xmlUtil.T_N)).setNodeValue("");
        ((Node) this.xmlUtil.parse("child::v:imagedata/@o:title", vshapeTemplateNode, this.xmlUtil.T_N)).setNodeValue("");
    }

    /**
     * 它删除没有被引用的 bin:data 节点， 以减少最终视图文件的大小
     *
     * @param document 在该文档下面进行查找
     * @throws Exception 没有预料到会抛出什么异常
     */
    private void removeUnusefulWbinData(Document document) throws Exception {
        // 获取所有的 w:binData 节点
        NodeList wbinDataNodeList = (NodeList) this.xmlUtil.parse("descendant::w:binData", document, this.xmlUtil.T_NS);

        // 循环检查 w:binData 是否被引用， 如果没有，就删除掉
        for (int i = 0; i < wbinDataNodeList.getLength(); i++) {
            // 获取该 w:binData 的 w:name 属性的 值， 其它节点是通过该值来引用 w:binData 的数据
            String src = this.xmlUtil.parse("@w:name", wbinDataNodeList.item(i), this.xmlUtil.T_S).toString();
            // 检查是否有 v:imagedata 节点引用 当前的 w:binData, 如果没有，就删除掉这个 w:binData.
            Node vimagedataNode = (Node) this.xmlUtil.parse("descendant::v:imagedata[@src='" + src + "']", document, this.xmlUtil.T_N);
            if (vimagedataNode == null) {
                wbinDataNodeList.item(i).getParentNode().removeChild(wbinDataNodeList.item(i));
            }
        }
    }

    /**
     * 返回在数据层 data 中有对应数据的图片模板 假如图片模板在数据层没有对应的数据，那么在数据层放入一个数据， 数据的键值都说模板名称，
     * 以防发生空指针异常
     *
     * @param document 要处理的文档对象
     * @param data 在此数据模块中寻找数据
     * @return 返回符合条件的图片模板
     * @throws Exception 未知
     */
    private List<Node> getVshapeTemplateNodeList(Document document, Map<String, Object> data) throws Exception {
        // 用于存储包含模板的 v:shape 节点
        List<Node> vshapeTemplateNodeList = new ArrayList<Node>();

        // 查找所有的 w:pict 下面的 v:shape
        NodeList vShapeNodeList = (NodeList) this.xmlUtil.parse("descendant::w:pict/descendant::v:shape", document, this.xmlUtil.T_NS);

        // 查找含有模板的 v:shape 节点， 放到 vshapeTemplateNodeList 中
        for (int i = 0; i < vShapeNodeList.getLength(); i++) {
            String altAttrValue = this.xmlUtil.parse("@alt", vShapeNodeList.item(i), this.xmlUtil.T_S).toString();
            if (altAttrValue.matches(this.templateUtil.REGEX_VAR)) {
                String varTemplateName = this.templateUtil.extractVarName(altAttrValue).trim();
                // 假如模板的名称是一个 URL, 并且这个 URL 是一个有效的图片 URL，那么就将这个模板放在
                // vshapeTemplateNodeList 中， 稍后进行处理
                if (varTemplateName.matches(this.URL_REGEX)) {
                    try {
                        // 检查 URL 有效性
                        this.readImage(varTemplateName);
                        vshapeTemplateNodeList.add(vShapeNodeList.item(i));
                    } catch (Exception e) {
                        // 这个URL是无效的， 将模板清空
                        ((Node) this.xmlUtil.parse("@alt", vShapeNodeList.item(i), this.xmlUtil.T_N)).setNodeValue("");
                        ((Node) this.xmlUtil.parse("child::v:imagedata/@o:title", vShapeNodeList.item(i), this.xmlUtil.T_N)).setNodeValue("");
                    }
                } // 假如在数据块中有这份模板代表的数据， 那么就将这个模板放在在 vshapeTemplateNodeList 中，
                // 稍后进行处理
                else if (data.get(varTemplateName) != null) {
                    vshapeTemplateNodeList.add(vShapeNodeList.item(i));
                } /*
                 * 假如数据块中没有这个模板对应的数据， 并且这个模板的名称不是一个有效的 URL,
                 * 那么就在数据块给这个模板放置一个对应的数据， 这个数据就是模板的名称， 以防在后面模板引擎解析的时候抛出空指针异常
                 */ else {
                    data.put(varTemplateName, varTemplateName);
                }
            }
        }

        // 返回所有包含模板的 v:shape 节点
        return vshapeTemplateNodeList;
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
        // 假如当前节点的父节点不为 null， 那么就继续冒泡
        while (currentNode.getParentNode() != null) {
            // 得到当前节点名称
            String nodeName = currentNode.getNodeName();
            // 查看当前层级是否有相同命名的节点
            Node sameNode = (Node) this.xmlUtil.parse("descendant::" + nodeName + "[2]", currentNode.getParentNode(), this.xmlUtil.T_N);
            // 假如有的话， 冒泡停止， 使用注释节点替换当前节点
            if (sameNode != null) {
                Comment comment = currentNode.getOwnerDocument().createComment(this.xmlUtil.getText(wtLogicTemplate));
                currentNode.getParentNode().replaceChild(currentNode.getParentNode().appendChild(comment), currentNode);
            } else {
                currentNode = currentNode.getParentNode();
            }
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
        // 获取 w:p 模板节点下所有的 w:r
        NodeList wtNodeList = (NodeList) this.xmlUtil.parse("descendant::w:t", wpTemplateNode, this.xmlUtil.T_NS);
        // 循环判断获取符合条件的 w:r
        for (Node wtNode : this.xmlUtil.getIterable(wtNodeList)) {
            String text = this.xmlUtil.getText(wtNode);
            if (text.matches(this.templateUtil.REGEX_LOGIC)) {
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
        // 得到 wpTemplateNode 下面所有的文本
        String wpText = this.xmlUtil.getText(wpTemplateNode);
        // 得到 wpText 中所有的模板字符串列表， 并将这些模板字符串放到队列 templateList 中
        List<String> templateList = Util.match(wpText, this.templateUtil.REGEX_ALL);
        // 得到 wpTemplateNode 下面的第一个 w:r 节点， 后面的迭代做准备
        Node nextWrTemplateNode = (Node) this.xmlUtil.parse("child::w:r[1]", wpTemplateNode, this.xmlUtil.T_N);

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
     * 正则匹配字符串中符合条件的字串。
     *
     * @param string 在该字符串上面进行匹配。
     * @param regexp 匹配工作使用的正则表达式。
     * @return 返回匹配到的字串列表。
     */
    @SuppressWarnings("unused")
	private LinkedList<String> match(String string, String regexp) {
        LinkedList<String> results = new LinkedList<String>();
        Matcher m = Pattern.compile(regexp).matcher(string);
        while (m.find()) {
            results.add(m.group());
        }
        return results;
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
        // 用于存放所遍历过的 w:r 节点中的包含的文本
        StringBuilder roughTemplate = new StringBuilder();
        while (nextWrTemplateNode != null) {
            // 保存当前遍历的 w:r 节点中包含的文本
            roughTemplate.append(this.xmlUtil.getText(nextWrTemplateNode));
            // 假如 roughTemplate 刚好包含所要匹配的模板， 那么开始在所遍历过的 w:r 节点之上进行模板提取操作
            if (roughTemplate.toString().contains(template)) {
                // 从当前所在的节点位置开始， 进行模板碎片整合操作
                nextWrTemplateNode = this.processIteratedWrForOneTemplate(nextWrTemplateNode, template);
                roughTemplate.delete(0, roughTemplate.length());
                break;
            }
            // Iterating the next w:r.
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
        // 当前 w:p 下面下一个模板匹配过程所开始的节点
        Node resultingNextNode = endedWrNode;
        // 一个模板的结尾节点 w:r 下面所包含的文本
        String endedWrText = this.xmlUtil.getText(endedWrNode);
        // 假如 endedWrText 包含template， 另外还包含其他字符
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
            // endedWrText 和模板交集的长度
            int intersectionLength = this.getIntersectionLength(endedWrText, template);
            // 获取将交集部分从模板剔除后的部分
            String remainingInTemplate = template.substring(0, template.length() - intersectionLength);
            // 获取前一个节点
            Node prevWrNode = endedWrNode.getPreviousSibling();

            // 假如模板完全包含结尾节点， 那么将结尾节点删除
            if (template.contains(endedWrText)) {
                resultingNextNode = endedWrNode.getNextSibling();
                endedWrNode.getParentNode().removeChild(endedWrNode);
            } // 否则将模板部分从结尾节点中剔除
            else {
                String reservedText = endedWrText.substring(intersectionLength);
                Node textNode = (Node) this.xmlUtil.parse("descendant::text()[1]", endedWrNode, this.xmlUtil.T_N);
                textNode.setNodeValue(reservedText);
            }

            // 调用此方法， 进行向前搜索， 直到找到模板的开始 w:r 节点
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
        String currentWrText = this.xmlUtil.getText(prevWrNode);

        // 假如 remainingInTemplate 包含当前 w:r 所包含的文本
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
        ((Node) this.xmlUtil.parse("descendant::text()[1]", wrNode, this.xmlUtil.T_N)).setNodeValue(text);
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
        // 子孙文本节点包含模板的 w:p 节点列表
        List<Node> wpTemplateList = new ArrayList<Node>();
        // 整个DOM文档中所有的 w:p 节点
        NodeList wpNodeList = (NodeList) this.xmlUtil.parse("descendant::w:p", document, this.xmlUtil.T_NS);

        // 循环判断 w:p 下面的子孙文本节点是否包含模板，假如包含，就放到 wpTemplateList 中
        for (Node wpNode : this.xmlUtil.getIterable(wpNodeList)) {
            String text = this.xmlUtil.getText(wpNode);
            if (text.matches(this.templateUtil.REGEX_ALL_LIKE)) {
                wpTemplateList.add(wpNode);
            }
        }

        // 返回子孙文本节点包含模板的 w:p 节点列表
        return wpTemplateList;
    }

    /**
     * 将数据放到Cell对象中， 以便保存单元格数据的元信息
     *
     * @param rawTable 原始的数据
     * @return 被封装的数据
     */
    public List<List<Cell>> getFormalTable(List<List<String>> rawTable) {
        List<List<Cell>> table = new ArrayList<List<Cell>>();
        for (List<String> rawRow : rawTable) {
            List<Cell> row = new ArrayList<Cell>();
            for (String content : rawRow) {
                row.add(new Cell(content));
            }
            table.add(row);
        }
        return table;
    }

    /**
     * 合并单元格，假如单元格要被合并掉，那么这个Cell对象不会被在这个方法中被删除，但是它的状态会被置为已删除。
     *
     * @param table 合并这个表中的单元格
     * @param startRowIndex 横向合并的起始位置
     * @param endRowIndex 横向合并的结束位置
     * @param startColumnIndex 纵向合并的起始位置
     * @param endColumnIndex 纵向合并的结束位置
     */
    public void mergeCells(List<List<Cell>> table, int startRowIndex, int endRowIndex, int startColumnIndex, int endColumnIndex) {
        // Merge in x-axis
        for (int rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
            for (int columnIndex = startColumnIndex + 1; columnIndex <= endColumnIndex; columnIndex++) {
                Cell current = table.get(rowIndex).get(columnIndex);
                Cell previous = table.get(rowIndex).get(columnIndex - 1);
                // 假如当前单元格和前一个单元格的内容相同， 那么它会持有合并开始的那个单元格，并且它会被置为已删除状态，并且他的
                if (current.equals(previous)) {
                    current.setXAxisHead(previous);
                }
            }
        }
        // Merge in y-axis
        for (int columnIndex = startColumnIndex; columnIndex <= endColumnIndex; columnIndex++) {
            for (int rowIndex = startRowIndex + 1; rowIndex <= endRowIndex; rowIndex++) {
                Cell current = table.get(rowIndex).get(columnIndex);
                Cell previous = table.get(rowIndex - 1).get(columnIndex);
                // 假如当前单元格和前一个单元格的内容相同， 那么它会持有合并开始的那个单元格，并且它会被置为已删除状态，并且他的
                if (current.equals(previous)) {
                    current.setYAxisHead(previous);
                }
            }
        }
    }

    /**
     * 为一个表中的某个区域的单元格设置宽度
     *
     * @param table 从这个表中寻找要设置的区域
     * @param width 单元格宽度
     * @param table 合并这个表中的单元格
     * @param startRowIndex 横向合并的起始位置
     * @param endRowIndex 横向合并的结束位置
     * @param startColumnIndex 纵向合并的起始位置
     * @param endColumnIndex 纵向合并的结束位置
     */
    public void setCellWidth(List<List<Cell>> table, int width, int startRowIndex, int endRowIndex, int startColumnIndex, int endColumnIndex) {
        for (int rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
            for (int columnIndex = startColumnIndex; columnIndex <= endColumnIndex; columnIndex++) {
                table.get(rowIndex).get(columnIndex).setWidth(width);
            }
        }
    }

    public static class Cell {

        private Object value;
        private int colspan = 1, rowspan = 1;
        private Cell xAxisHead, yAxisHead;
        private Integer width = null;

        public Cell(Object value) {
            this.value = value;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Object getWidth() {
            return this.width == null ? "" : this.width;
        }

        public void setXAxisHead(Cell previous) {
            while (previous.getXAxisHead() != null) {
                previous = previous.getXAxisHead();
            }
            this.xAxisHead = previous;
            if (this.colspan > 0) {
                this.xAxisHead.increaseColspanOnXAxis();
                this.colspan = 0;
            }
        }

        public Cell getXAxisHead() {
            return this.xAxisHead;
        }

        public void setYAxisHead(Cell previous) {
            while (previous.getYAxisHead() != null) {
                previous = previous.getYAxisHead();
            }
            this.yAxisHead = previous;
            if (this.rowspan > 0) {
                this.yAxisHead.increaseRowspanOnYAxis();
                this.rowspan = 0;
            }
        }

        public Cell getYAxisHead() {
            return this.yAxisHead;
        }

        @Override
		public String toString() {
            return this.value.toString();
        }

        public void increaseColspanOnXAxis() {
            this.colspan++;
        }

        public void increaseRowspanOnYAxis() {
            this.rowspan++;
        }

        public int getColspan() {
            return this.colspan;
        }

        public String getWVmergeTagTemplate() {
            if (this.rowspan == 1) {
                return "";
            } else if (this.rowspan == 0) {
                return "<w:vmerge/>";
            } else {
                return "<w:vmerge w:val=\"restart\"/>";
            }
        }

        @Override
		public boolean equals(Object o) {
            return this.toString().equals(o.toString());
        }
    }

    /**
     * The XML utility.
     */
    public static class XmlUtil {

        //This final variables are used to specify what kind of data you want.
        public final QName T_N = XPathConstants.NODE;
        public final QName T_NS = XPathConstants.NODESET;
        public final QName T_D = XPathConstants.NUMBER;
        public final QName T_S = XPathConstants.STRING;
        public final QName T_B = XPathConstants.BOOLEAN;
        //It specifies whether the current XmlUtil object uses name space to query using XPath.
        public final boolean NAMESPACE_AWARE;

        /**
         * When constructing a XmlUtil object, to initialize the global final
         * variable NAMESPACE_AWARE.
         *
         * @param namespaceAware It is assigned to the global final variable
         * NAMESPACE_AWARE.
         */
        public XmlUtil(boolean namespaceAware) {
            this.NAMESPACE_AWARE = namespaceAware;
        }

        /**
         * It creates a org.w3c.dom.Document object using a XML stream.
         *
         * @param stream The XML stream used to create a
         * org.w3c.dom.Documentobject.
         * @return The org.w3c.dom.Document object.
         * @throws Exception If the argument stream is not from a standard XML,
         * then a exception will be thrown.
         */
        public Document getDocument(InputStream in) throws Exception {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                docFactory.setNamespaceAware(this.NAMESPACE_AWARE);
                DocumentBuilder db = docFactory.newDocumentBuilder();
                return db.parse(in);
            } finally {
                IOUtils.closeQuietly(in);
            }
        }

        /**
         * It creates a blank a org.w3c.dom.Document object.
         *
         * @return A blank org.w3c.dom.Document object.
         * @throws Exception I have no idea to what situation where a exception
         * will be throwed.
         */
        public Document createDocument() throws Exception {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setNamespaceAware(this.NAMESPACE_AWARE);
            DocumentBuilder db = docFactory.newDocumentBuilder();
            return db.newDocument();
        }

        /**
         * It parse a XPath using a context.
         *
         * @param expr The XPath.
         * @param context You can start your querying from this context.
         * @param returnType It specifies what kind of data you want.
         * @return It returns a result data which type is specified by the
         * argument returnType.
         * @throws Exception If the name space can not be found, a exception
         * will be thrown.
         */
        public Object parse(String expr, Node context, QName returnType) throws Exception {
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            if (this.NAMESPACE_AWARE) {
                Node temp = context;
                do {
                    if (temp.getNodeType() == Node.DOCUMENT_NODE) {
                        xpath.setNamespaceContext(this.getNamespaceContext((Document) temp));
                        break;
                    }
                    temp = temp.getParentNode();
                } while (true);
            }
            XPathExpression xe = xpath.compile(expr);
            return xe.evaluate(context, returnType);
        }

        /**
         * It returns a XML string from a org.w3c.dom.Document object.
         *
         * @param node The resulting data is from it.
         * @param properties It specifies the resulting xml's properties.
         * @return The resulting XML string.
         * @throws Exception If the passed properties are not suitable, a
         * exception will be thrown.
         */
        public String getXml(Node node, KV<?, ?>... properties) throws Exception {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            for (KV<?, ?> property : properties) {
                transformer.setOutputProperty(property.k.toString(), property.v.toString());
            }
            StringWriter writer = null;
            try {
                writer = new StringWriter();
                transformer.transform(new DOMSource(node), new StreamResult(writer));
            } finally {
                writer.close();
            }
            return writer.toString();
        }

        /**
         * It searches all the text nodes among the argument and its
         * descendants, and returns their values.
         *
         * @param node The searching starts with this node.
         * @return The searched text nodes' values.
         * @throws Exception I have no idea to what situation where a exception
         * will be thrown.
         */
        public String getText(Node node) throws Exception {
            if (node.getNodeType() == Node.TEXT_NODE) {
                return node.getNodeValue();
            } else {
                StringBuilder text = new StringBuilder();
                NodeList textNodeList = (NodeList) this.parse("descendant::text()", node, this.T_NS);
                for (int i = 0; i < textNodeList.getLength(); i++) {
                    text.append(textNodeList.item(i).getNodeValue());
                }
                return text.toString();
            }
        }

        /**
         * Encapsulate NodeList as Iterable for supporting the Java feature
         * 'foreach'
         *
         * @param nodeList Encapsulated object
         * @return Iterable object on behalf of 'nodeList'
         */
        public Iterable<Node> getIterable(final NodeList nodeList) {
            return Util.getIterable(new Iterator<Node>() {
                private int index = 0;

                @Override
				public boolean hasNext() {
                    return nodeList.getLength() > this.index;
                }

                @Override
				public Node next() {
                    return nodeList.item(this.index++);
                }

                @Override
				public void remove() {
                    throw new UnsupportedOperationException();
                }
            });
        }

        /**
         * It returns a javax.xml.namespace.NamespaceContext object.
         *
         * @param document The resulting object serves this one.
         * @return It returns serves XPath querying by name space.
         */
        private NamespaceContext getNamespaceContext(final Document document) {
            return new NamespaceContext() {
                @Override
				public String getNamespaceURI(String prefix) {
                    return XmlUtil.this.getNamespaceForPrefix(prefix, document);
                }

                @Override
				public String getPrefix(String namespaceURI) {
                    return null;
                }

                @Override
				public Iterator<?> getPrefixes(String namespaceURI) {
                    return null;
                }
            };
        }

        /**
         * It searches a correction name space by a prefix in a
         * org.w3c.dom.Document.
         *
         * @param prefix It is corresponding with a name space in a
         * org.w3c.dom.Document.
         * @param namespaceContext The searching is under this
         * org.w3c.dom.Document.
         * @return It return a name space.
         */
        private String getNamespaceForPrefix(String prefix, Document namespaceContext) {
            Node parent = namespaceContext.getDocumentElement();
            String namespace = null;
            if (prefix.equals("xml")) {
                namespace = "\"http://www.w3.org/XML/1998/namespace\"";
            } else {
                int type;
                while ((null != parent) && (null == namespace) && (((type = parent.getNodeType()) == Node.ELEMENT_NODE) || (type == Node.ENTITY_REFERENCE_NODE))) {
                    if (type == Node.ELEMENT_NODE) {
                        if (parent.getNodeName().indexOf(prefix + ":") == 0) {
                            return parent.getNamespaceURI();
                        }
                        NamedNodeMap nnm = parent.getAttributes();
                        for (int i = 0; i < nnm.getLength(); i++) {
                            Node attr = nnm.item(i);
                            String aname = attr.getNodeName();
                            boolean isPrefix = aname.startsWith("xmlns:");
                            if (isPrefix || aname.equals("xmlns")) {
                                int index = aname.indexOf(':');
                                String p = isPrefix ? aname.substring(index + 1) : "";
                                if (p.equals(prefix)) {
                                    namespace = attr.getNodeValue();
                                    break;
                                }
                            }
                        }
                    }
                    parent = parent.getParentNode();
                }
            }
            return namespace;
        }

        /**
         * This is just a example. Since situations are many more. You can take
         * this method as a reference to add new node. Note: If the two nodes
         * inverse in insertBefore(), the newNode must be add by
         * node.getParentNode().appendChild() in advance.
         *
         * @param before Insert the new node before this one.
         * @param newNodeName The new node's name.
         * @param attributes Put this attributes into the new node.
         * @return The new node.
         */
        public Node addNodeBefore(Node node, String newNodeName) {
            Document document = node.getOwnerDocument();
            Node newNode = document.createElement(newNodeName);
            node.getParentNode().insertBefore(newNode, node);
            return newNode;
        }

        /**
         * Set attributes for a node.
         *
         * @param node Set attributes for this one.
         * @param attributes Set these attributes.
         * @return The one past in.
         */
        public Node setAttributes(Node node, String attributeName, String attributeValue) {
            Node attr = node.getAttributes().getNamedItem(attributeName);
            if (attr == null) {
                attr = node.getOwnerDocument().createAttribute(attributeName);
                node.getAttributes().setNamedItem(attr);
            }
            attr.setNodeValue(attributeValue);
            return node;
        }

        /**
         * Get a specific attribute's value by the attribute's name from a node.
         *
         * @param node Searching the attribute from this node.
         * @param name Searched attribute's name.
         * @return The attribute's value. Return null if searching fail.
         */
        public String getAttribute(Node node, String name) {
            Node attr = node.getAttributes().getNamedItem(name);
            return attr == null ? null : attr.getNodeValue();
        }
    }

    /**
     * 这是一个模板引擎工具类， 提供了模板引擎的正则表达式， 并且可以根据数据计算试图
     */
    public static abstract class TemplateUtil {

        public static enum Type {

            FTL, VM
        }

        /**
         * 静态方法获取模板工具类
         *
         * @param type 模板工具栏类型
         * @return 具体的模板工具栏
         */
        public static TemplateUtil getInstance(Type type) {
            switch (type) {
                case FTL:
                    return new FtlTemplateUtil();
                default:
                    return new VmTemplateUtil();
            }
        }

        /* 模板正则表达式 */
        public final String REGEX_LOGIC, REGEX_VAR, REGEX_ALL, REGEX_ALL_LIKE;
        /* 假模板 */
        public final String PSEUDO_VAR, PSEUDO_FOREACH, PSEUDO_IF, PSEUDO_ELSEIF, PSEUDO_ELSE, PSEUDO_END;
        /* 映射模板标准名称, foreach else */
        protected Map<String, String> nameMap = new HashMap<String, String>();

        /**
         * 构造函数初始化正则表达式
         *
         * @param regexLogic 逻辑控制模板正则表达式
         * @param regexVar 变量模板正则表达式
         * @param pseudoVar 参数： NAME
         * @param pseudoForeach 参数：LIST, ITEM
         * @param pseudoIf 参数：CONDITION
         * @param pseudoElseif 参数：CONDITION
         * @param pseudoElse 参数：
         * @param pseudoEnd 参数：ENDTYPE
         */
        public TemplateUtil(String regexLogic, String regexVar,
                String pseudoVar, String pseudoForeach,
                String pseudoIf, String pseudoElseif, String pseudoElse, String pseudoEnd) {
            final String L = "([a-zA-Z]+)", V = "([a-zA-Z](#w|#.|#(|#))+)".replace("#", "\\");
            this.REGEX_LOGIC = regexLogic.replace("L", L);
            this.REGEX_VAR = regexVar.replace("V", V);
            this.REGEX_ALL = this.REGEX_LOGIC + "|" + this.REGEX_VAR;
            this.REGEX_ALL_LIKE = ".*(" + this.REGEX_ALL + ").*";

            this.PSEUDO_VAR = pseudoVar;
            this.PSEUDO_FOREACH = pseudoForeach;
            this.PSEUDO_IF = pseudoIf;
            this.PSEUDO_ELSEIF = pseudoElseif;
            this.PSEUDO_ELSE = pseudoElse;
            this.PSEUDO_END = pseudoEnd;
        }

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

        /**
         * 提取数据模板的变量名称
         *
         * @param varTempalte 要操作的数据模板
         * @return 返回数据模板的名称
         */
        public String extractVarName(String varTempalte) {
            return varTempalte.replaceFirst(this.REGEX_VAR, "$2");
        }

        /**
         * 提取逻辑模板的名称
         *
         * @param logicTemplate 要操作的逻辑模板
         * @return 返回逻辑模板的名称
         * @throws Exception
         */
        public String extractLogicName(String logicTemplate) {
            for (int i = 1;; i++) {
                String name = logicTemplate.replaceFirst(this.REGEX_LOGIC, "$" + i).toLowerCase();
                if (name.matches("([a-z]+)")) {
                    return Util.fixValue(this.nameMap.get(name), name);
                }
            }
        }

        /**
         * 解析 foreach 模板
         *
         * @param foreachTemplate 要解析模板
         * @return 被解析出来的信息, 如 list: 列表名称，item 项目名称
         */
        public abstract Map<String, String> analyzeForeach(String foreachTemplate);
    }

    /**
     * FTL 模板工具类实现
     */
    private static class FtlTemplateUtil extends TemplateUtil {

        public FtlTemplateUtil() {
            super("(<#L .+?>)|(</#L>)|(<#L>)", "(\\$\\{V?\\s?\\})",
                    "${NAME}", "<#list LIST as ITEM>",
                    "<#if CONDITION>", "<#elseif CONDITION>", "<#else>", "</#ENDTYPE>");
        }

        @Override
		public String evaluate(String tl, Map<String, Object> data, Map<String, String> properties) throws Exception {
            //设置属性
            Configuration configuration = new Configuration();
            for (Map.Entry<String, String> property : Util.fixValue(properties, new HashMap<String, String>()).entrySet()) {
                configuration.setSetting(property.getKey(), property.getValue());
            }

            //获取字符串模板加载器
            StringTemplateLoader templateLoader = new StringTemplateLoader();
            templateLoader.putTemplate("ftl", tl);

            //获取模板
            configuration.setTemplateLoader(templateLoader);
            Template template = configuration.getTemplate("ftl");

            //解析模板
            StringWriter writer = new StringWriter();
            template.process(data, writer);
            writer.close();
            return writer.toString();
        }

        @Override
		public Map<String, String> analyzeForeach(String foreachTemplate) {
            Map<String, String> meta = new HashMap<String, String>();
            meta.put("list", foreachTemplate.replaceFirst("<#list (.+?) as (.+?)>", "$1"));
            meta.put("item", foreachTemplate.replaceFirst("<#list (.+?) as (.+?)>", "$2"));
            return meta;
        }
    }

    /**
     * VM 模板工具类实现
     */
    private static class VmTemplateUtil extends TemplateUtil {

        public VmTemplateUtil() {
            super("(#L\\(.+?\\))|(#(end|else))", "(\\$V)",
                    "$NAME", "#foreach($ITEM in $LIST)",
                    "#if(CONDITION)", "#elseif(CONDITION)", "#else", "#end");
            this.nameMap.put("list", "foreach");

        }

        @Override
		public String evaluate(String tl, Map<String, Object> data, Map<String, String> properties) throws Exception {
            //属性设置
            Properties props = new Properties();
            props.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
            props.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
            props.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
            props.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");

            for (Map.Entry<String, String> property : Util.fixValue(properties, new HashMap<String, String>()).entrySet()) {
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

        @Override
		public Map<String, String> analyzeForeach(String foreachTemplate) {
            Map<String, String> meta = new HashMap<String, String>();
            meta.put("item", foreachTemplate.replaceFirst("#foreach\\(\\$(.+?) in \\$(.+?)\\)", "$1"));
            meta.put("list", foreachTemplate.replaceFirst("#foreach\\(\\$(.+?) in \\$(.+?)\\)", "$2"));
            return meta;
        }
    }

    private static class KV<K, V> {

        public K k;
        public V v;

        public KV(K k, V v) {
            this.k = k;
            this.v = v;
        }

        @SuppressWarnings("unused")
		public static <K, V> KV<K, V> get(K k, V v) {
            return new KV<K, V>(k, v);
        }
    }

    private static class Util {

        public static <T> T fixValue(T value, T defaultValue) {
            return value == null || value.toString().equals("") ? defaultValue : value;
        }

        public static List<String> match(String string, String regexp) {
            List<String> results = new ArrayList<String>();
            Matcher m = Pattern.compile(regexp).matcher(string);
            while (m.find()) {
                results.add(m.group());
            }
            return results;
        }

        public static <T> Iterable<T> getIterable(final Iterator<T> iterator) {
            return new Iterable<T>() {
                @Override
				public Iterator<T> iterator() {
                    return iterator;
                }
            };
        }
    }
}
