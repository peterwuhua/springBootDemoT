/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.core.framework.utils.output.demo;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import cn.core.framework.utils.output.word2003XmlTemplate.TemplateUtil;
import cn.core.framework.utils.output.word2003XmlTemplate.Word2003XmlTemplate;

/**
 *
 * @author lyc
 */
public class DocxOutput{

    public static void main(String[] args) throws Exception {
        DocxOutput dos = new DocxOutput();
        dos.createWord();

    }
    
    public void createWord() throws Exception{
    	 System.out.println("开始生成文档...");
    	 String s="<![CDATA[${!vo.name}]]>";
    	 s=s.replaceAll("\\$\\{!", "\\$!\\{");
    	 System.out.println(s);
    	 Word2003XmlTemplate wt = new Word2003XmlTemplate(TemplateUtil.Type.VM);
         Map<String, Object> data = new HashMap<String, Object>();
         DemoVo demoVo=new DemoVo();
         demoVo.setId("No1");
         demoVo.setName(null);
         demoVo.setAge("28");
         data.put("vo", demoVo);
         List<DemoVo> table=new ArrayList<DemoVo>();
         table.add(demoVo);
         data.put("table", table);
//         data.put("ico1", Base64Utils.encodeToString(IOUtils.toByteArray(new FileInputStream(this.getClass().getResource("").getPath()+"//1.png"))));
//         data.put("singn", Base64Utils.encodeToString(IOUtils.toByteArray(new FileInputStream(this.getClass().getResource("").getPath()+"//2.png"))));
//         data.put("ico3", Base64Utils.encodeToString(IOUtils.toByteArray(new FileInputStream(this.getClass().getResource("").getPath()+"//3.png"))));
//         String realWord = wt.getRealWord(getTemplateStream("out.xml"), data, null,null);
    	 Map<String,String> replace=new HashMap<String, String>();
//    	 replace.put("singn", "\\${singn}");
//    	 replace.put("ico3", "\\${ico3}");
     String realWord = wt.getRealWord(new FileInputStream("d:\\1.xml"), data, null,replace);
         IOUtils.write(realWord, new FileOutputStream("d:\\out.doc"));
         System.out.println("文档生成成功.");
    }
    /**
     * 获取模板流
     *
     * @param fileName
     * @return
     * @throws java.lang.Exception
     */
    public InputStream getTemplateStream(String fileName) throws Exception {
        InputStream in = null;
        in = this.getClass().getClassLoader().getResource("wordTem/" + fileName).openStream();
        return in;
    }
}
