<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.zhuozhengsoft.pageoffice.*, com.zhuozhengsoft.pageoffice.wordwriter.*,java.awt.*"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
PDFCtrl poCtrl = new PDFCtrl(request);
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
 
poCtrl.addCustomToolButton("打印", "Print()", 6);
poCtrl.addCustomToolButton("隐藏/显示书签", "SetBookmarks()", 0);
poCtrl.addCustomToolButton("-", "", 0);
poCtrl.addCustomToolButton("实际大小", "SetPageReal()", 16);
poCtrl.addCustomToolButton("适合页面", "SetPageFit()", 17);
poCtrl.addCustomToolButton("适合宽度", "SetPageWidth()", 18);
poCtrl.addCustomToolButton("-", "", 0);
poCtrl.addCustomToolButton("首页", "FirstPage()", 8);
poCtrl.addCustomToolButton("上一页", "PreviousPage()", 9);
poCtrl.addCustomToolButton("下一页", "NextPage()", 10);
poCtrl.addCustomToolButton("尾页", "LastPage()", 11);
poCtrl.addCustomToolButton("-", "", 0);
poCtrl.addCustomToolButton("全屏切换", "setFullScreen()", 4); 
poCtrl.addCustomToolButton("关闭", "Close",22);

poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");

String tempPath = (String)request.getAttribute("tempPath"); 
poCtrl.webOpen(request.getContextPath()+"/"+tempPath);
poCtrl.setTagId("PDFCtrl1");//此行必需
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>编辑模板文件</title>
  </head>
<body>
<script type="text/javascript">
function Close() {
	window.external.close();
}
function AfterDocumentOpened() {
    document.getElementById("PDFCtrl1").SetPageFit(1);
    setFullScreen();
}
function SetBookmarks() {
    document.getElementById("PDFCtrl1").BookmarksVisible = !document.getElementById("PDFCtrl1").BookmarksVisible;
}

function PrintFile() {
    document.getElementById("PDFCtrl1").ShowDialog(4);
}
function setFullScreen() {
    document.getElementById("PDFCtrl1").FullScreen = !document.getElementById("PDFCtrl1").FullScreen;
}
function SetPageReal() {
    document.getElementById("PDFCtrl1").SetPageFit(1);
}
function SetPageFit() {
    document.getElementById("PDFCtrl1").SetPageFit(2);
}
function SetPageWidth() {
    document.getElementById("PDFCtrl1").SetPageFit(3);
}
function ZoomIn() {
    document.getElementById("PDFCtrl1").ZoomIn();
}
function ZoomOut() {
    document.getElementById("PDFCtrl1").ZoomOut();
}
function FirstPage() {
    document.getElementById("PDFCtrl1").GoToFirstPage();
}
function PreviousPage() {
    document.getElementById("PDFCtrl1").GoToPreviousPage();
}
function NextPage() {
    document.getElementById("PDFCtrl1").GoToNextPage();
}
function LastPage() {
    document.getElementById("PDFCtrl1").GoToLastPage();
}
function RotateRight() {
    document.getElementById("PDFCtrl1").RotateRight();
}
function RotateLeft() {
    document.getElementById("PDFCtrl1").RotateLeft();
}

</script>
<form id="form1">
    <div style=" width:100%; height:700px;">
        <po:PDFCtrl id="PDFCtrl1"></po:PDFCtrl>
    </div>
</form>
</body>
</html>

