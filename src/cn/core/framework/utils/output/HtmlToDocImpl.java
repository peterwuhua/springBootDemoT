/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.core.framework.utils.output;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 将html文档转为doc liyuanyuan --- 2014-01-08
 */
public class HtmlToDocImpl implements HtmlToDoc {

    /**
     * 读取html文件到word
     *
     * @param filepath html文件的路径
     * @return 是否转成功的标示
     * @throws Exception
     */
    @SuppressWarnings({ "unused"})
	@Override
    public void writeWordFile(String htmlString, String title, OutputStream out) throws Exception {

//        if (out instanceof ResponseOutputStream) {
//            ((ResponseOutputStream) out).setDownloadFileName(title + ".doc");
//             out.setContentType("application/octet-stream");
//        }
        ByteArrayInputStream bais = null;
        FileOutputStream fos = null;
        //可以选择性的加上<span style='font-family:宋体'></span>,目的是为了编码正确性---解析汉字
        htmlString = "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'><html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'></head><body><span style='font-family:宋体'></span>" + htmlString + "</body></html>";
        // 字符串转成字节数组
        byte b[] = htmlString.getBytes("UTF-8");
        // 字节数组转成字节流
        bais = new ByteArrayInputStream(b);
        // 定义POIFSFileSystem对象
        POIFSFileSystem poifs = new POIFSFileSystem();
        DirectoryEntry directory = poifs.getRoot();
        DocumentEntry documentEntry = directory.createDocument(
                "WordDocument", bais);
        poifs.writeFilesystem(out);
        // 关闭文件流
        bais.close();
    }

    /**
     * 读取html文件到字符串
     *
     * @param filename
     * @return
     * @throws Exception
     */
    @Override
    public String readFile(String filename) throws Exception {
        StringBuffer buffer = new StringBuffer("");
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(filename));
        buffer = new StringBuffer();
        while (br.ready()) {
            buffer.append((char) br.read());
        }
        if (br != null) {
            br.close();
        }
        return buffer.toString();
    }

    public static void main(String[] args) throws Exception {
        HtmlToDocImpl htmlToDoc = new HtmlToDocImpl();
        String htmlString = htmlToDoc.readFile("D:/index.html");
        new HtmlToDocImpl().writeWordFile(htmlString, "xxxxxx", new FileOutputStream("D:/temp.doc"));
    }
}
