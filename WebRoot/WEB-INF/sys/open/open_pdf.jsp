<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<style type="text/css">

 
#pdf {
width: 100%;
height: 100%;
margin: 2em auto;
border: 1px solid #65452;
}

 

</style>
</head>

<html>
  <head>
     <title>PDF文件</title>
  </head>
<body class="gray-bg">
	 <!-- <div class="col-sm-12">
	 	<div class= "ibox-content" id="s"></div>
	 </div> -->
	<div id="pdf"></div>
   <%@ include file="../../include/js.jsp"%>
   <script src="${basePath}ext/js/pdfobject.js"></script>
   <script type="text/javascript">
		 $(function(){
			/* var uri = '${basePath}${tempPath}';
			var success = new PDFObject({ url:uri,pdfOpenParams: { scrollbars: '0', toolbar: '0', statusbar: '0'}}).embed('pdf'); */
			location.href="${basePath}ext/pdf/index.html?file=${basePath}${tempPath}";
		}); 
   </script>
</body>
</html>

