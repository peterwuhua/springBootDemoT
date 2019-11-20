/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.core.framework.utils.output;

import java.io.OutputStream;

import javax.jws.WebParam;

/**
 * ClassName: HtmlToDoc Description:
 *
 * Version information: 2.0 Copyright notice: Richfit Originated by: liyuanyuan
 * on 2014-1-10 9:18:02 Modified by: Modified content: *
 */
public interface HtmlToDoc {

    public void writeWordFile(@WebParam(name = "htmlString") String htmlString, @WebParam(name = "title") String title, OutputStream out) throws Exception;

    public String readFile(@WebParam(name = "filename") String filename) throws Exception;
}
