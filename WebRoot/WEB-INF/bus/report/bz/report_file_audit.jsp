<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="utf-8"%>
<%

WordDocument doc = new WordDocument();
DataRegion BZ = doc.openDataRegion("PO_HYR");
DataRegion CZ = doc.openDataRegion("PO_JDY");
DataRegion DZ = doc.openDataRegion("PO_PZR");	
BZ.setEditing(true);
CZ.setEditing(true);
DZ.setEditing(true);
	
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//设置服务器页面
poCtrl.setWriter(doc);
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");

//添加自定义按钮
poCtrl.addCustomToolButton("保存","Save",1);
String id = (String)request.getAttribute("id");
String uuid = (String)request.getAttribute("uuid");
//设置保存页面
poCtrl.setSaveFilePage("ajaxSaveFile.do?id="+id+"&uuid="+uuid);

poCtrl.addCustomToolButton("全屏切换", "setFullScreen()", 4);  
poCtrl.addCustomToolButton("-", "", 0);
poCtrl.addCustomToolButton("加盖印章", "InsertSeal()", 2);
poCtrl.addCustomToolButton("验证印章", "VerifySeal()", 5);
poCtrl.addCustomToolButton("修改密码", "ChangePsw()", 0);
poCtrl.addCustomToolButton("-", "", 0);
poCtrl.addCustomToolButton("保存并关闭", "SaveAndClose", 21);
poCtrl.addCustomToolButton("关闭", "Close", 21);

poCtrl.setTitlebar(false); //隐藏标题栏
poCtrl.setMenubar(false); //隐藏菜单栏
poCtrl.setOfficeToolbars(false);//隐藏Office工具条
//打开文档之后执行全屏
poCtrl.setJsFunction_AfterDocumentOpened("setFullScreen");
String tempPath = (String)request.getAttribute("tempPath");
poCtrl.webOpen(request.getContextPath()+"/"+tempPath, OpenModeType.docSubmitForm, "");

poCtrl.setTagId("PageOfficeCtrl1");//此行必需
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>在线编辑报告</title>
<script type="text/javascript">
function InsertSeal() {
	try {
		document.getElementById("PageOfficeCtrl1").ZoomSeal.AddSeal("","");
	} catch (e) {
	}
}
function VerifySeal() {
	document.getElementById("PageOfficeCtrl1").ZoomSeal.VerifySeal();
}
function ChangePsw() {
	document.getElementById("PageOfficeCtrl1").ZoomSeal
			.ShowSettingsBox();
}
function Save() {
    document.getElementById("PageOfficeCtrl1").WebSave();
    SaveAsPDF();
}
function setFullScreen() {  
    document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;  
}
function SaveAndClose() {
	document.getElementById("PageOfficeCtrl1").WebSave();
	SaveAsPDF();
	window.external.close();
}
//另存为PDF文件
function SaveAsPDF() {
    document.getElementById("PageOfficeCtrl1").WebSaveAsPDF();
}
function Close() {
	window.external.close();
}
//文档关闭前先提示用户是否保存
function BeforeBrowserClosed() {
	if (document.getElementById("PageOfficeCtrl1").IsDirty) {
		if (confirm("提示：文档已被修改，是否继续关闭放弃保存 ？")) {
			return true;

		} else {

			return false;
		}

	}

}
</script>
  </head>
<body>
    <form id="form1">
	    <div style=" width:100%; height:700px;">
	        <po:PageOfficeCtrl id="PageOfficeCtrl1"></po:PageOfficeCtrl>
	    </div>
    </form>
</body>
</html>

