package cn.core.framework.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.OutputKeys;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.util.Base64Utils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.core.framework.utils.output.word2003XmlTemplate.XmlUtil;
/**
 * Create on : 2017年5月11日 下午6:15:11  <br>
 * Description :  替换word里边指定的alt图片<br>
 * @version  v 1.0.0  <br>
 * @author 刘永超<br>
 */
public class WordImgUtils {
   /**
    * Create on : 刘永超 2017年5月11日 下午6:17:10 <br>
    * Description :  <br>
    * @param docPath 文档路径
    * @param _imgAltName 图片的AltName 
    * @param imgBase64 图片的Base64字符串
    * @throws Exception
    */
	public static void replaceImg(String docPath,String _imgName,String imgBase64) throws Exception {
		String docFile=IOUtils.toString(new FileInputStream(docPath), "UTF-8");
		docFile=docFile.replaceAll("\r\n", "");
		XmlUtil xu = new XmlUtil(true);
		Document document=xu.getDocument(docFile);
	        //循环所有的 w:binData
	        for (Node wbinData :xu.getNodeList("descendant::w:binData", document)) {
	            //获取图片数据
	        	Node shapeNode=wbinData.getNextSibling();
	        	NamedNodeMap nm=shapeNode.getAttributes();
	        	if(nm.getNamedItem("alt")==null){
	        		shapeNode=wbinData.getParentNode();
		        	nm=shapeNode.getAttributes();
	        	}
	        	String imgAlt=nm.getNamedItem("alt").getNodeValue();
	        	System.out.println(imgAlt);
	        	if(imgAlt!=null&&imgAlt.equals(_imgName)){
//	        		String imageData = xu.getText(wbinData);
//	        		System.out.println(imageData);
	        		NodeList textNodeList = (NodeList) xu.parse("descendant::text()", wbinData, xu.T_NS);
	        		for (int i = 0; i < textNodeList.getLength(); i++) {
	        			textNodeList.item(i).setNodeValue(imgBase64);
	        		}
//	        		String imageData1 = xu.getText(wbinData);
//	        		System.out.println(imageData1);	        		
	        	}
	        }
	      //返回最终文档视图
	        Map<String, String> xmlProperties = new HashMap<String, String>();
	        xmlProperties.put(OutputKeys.CDATA_SECTION_ELEMENTS, xu.getQName("http://schemas.microsoft.com/office/word/2003/wordml", "t"));
	        docFile=xu.getXml(document, xmlProperties);
//	        System.out.println(docFile);
			FileOutputStream fileOutputStream=new FileOutputStream(docPath);
			IOUtils.write(docFile, fileOutputStream,"UTF-8");
			fileOutputStream.close();
	}
	
	/************************以下是静态工具方法*********************************/
	public static String encodeImage(String url) throws Exception {
        return Base64.encodeBase64String(IOUtils.toByteArray(new FileInputStream(url)));
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
		replaceImg("d:221.doc","img",Base64Utils.encodeToString(IOUtils.toByteArray(new FileInputStream("d:mainTester.png"))));
	}
}
