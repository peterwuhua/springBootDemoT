<%@ page language="java" import="java.util.*,com.zhuozhengsoft.pageoffice.*" pageEncoding="gb2312"%>
<%@page import="com.zhuozhengsoft.pageoffice.excelwriter.*"%>
<%@page import="java.awt.Color"%>
<%@page import="java.text.*"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//���÷�����ҳ��
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
//����Զ��尴ť
poCtrl.addCustomToolButton("ȷ��","Save",1);
cn.demi.base.system.vo.TemplateVo t = (cn.demi.base.system.vo.TemplateVo)request.getAttribute("vo");

//���ñ���ҳ��
poCtrl.setSaveFilePage("ajaxSaveFile.do?id="+t.getId()+"&type="+t.getType()+"&code="+t.getCode()+"&name="+t.getName());
//��Word�ĵ�
String tempPath = (String)request.getAttribute("tempPath");
String suffix = tempPath.substring(tempPath.lastIndexOf("."), tempPath.length());
System.out.print(suffix);
if(suffix.contains("xls")){
	poCtrl.webOpen(request.getContextPath()+"/"+tempPath, OpenModeType.xlsNormalEdit, "");
}else if(suffix.contains("doc")){
	poCtrl.webOpen(request.getContextPath()+"/"+tempPath, OpenModeType.docNormalEdit, "");
}else if(suffix.contains("ppt")){
	poCtrl.webOpen(request.getContextPath()+"/"+tempPath, OpenModeType.pptNormalEdit, "");
}
poCtrl.setTagId("PageOfficeCtrl1");//���б���
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>�༭ģ���ļ�</title>
  </head>
<body>
    <script type="text/javascript">
        function Save() {
            document.getElementById("PageOfficeCtrl1").WebSave();
        }
    </script>
    <form id="form1">
    <div style=" width:100%; height:700px;">
        <po:PageOfficeCtrl id="PageOfficeCtrl1">
        </po:PageOfficeCtrl>
    </div>
    </form>
</body>
</html>

