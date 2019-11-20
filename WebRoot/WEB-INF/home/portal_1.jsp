<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>实验室运营服务云平台</title>
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${basePath}h/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="${basePath}h/css/animate.css" rel="stylesheet">
<link href="${basePath}h/css/style.css?v=4.1.0" rel="stylesheet">

<link rel="icon" type="image/x-icon" href="${basePath}static/js/OrgChart/dist/img/logo.ico">
<link rel="stylesheet" href="${basePath}static/js/OrgChart/dist/css/font-awesome.min.css">
<link rel="stylesheet" href="${basePath}static/js/OrgChart/dist/css/jquery.orgchart.css">
<link rel="stylesheet" href="${basePath}static/js/OrgChart/dist/css/style.css">

<style type="text/css">
.orgchart {
	background: #fff;
}
#chart-container {
  position: relative;
  display: inline-block;
  top: 0px;
  left: 10px;
  width: calc(100% - 24px);
  border-radius: 5px;
  overflow: visible;
  text-align: left;
}
</style>
</head>
<body>
	<div style="width: 100%" id="chart-container"></div>
	<!-- 全局js -->
	<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
	<script src="${basePath}h/js/plugins/layer/layer.min.js"></script>
	<script type="text/javascript" src="${basePath}static/js/OrgChart/dist/js/html2canvas.min.js"></script>
	<script type="text/javascript" src="${basePath}static/js/OrgChart/dist/js/jquery.orgchart.js"></script>
	<script type="text/javascript">
		$(function() {
			$('#chart-container').orgchart({
				'data' : fnJsonData(),
				'nodeContent' : 'title',
				'direction': 'l2r',
			});

		});
		function fnJsonData(){
			var jsonData={};
			$.ajax({ 
				url:"${basePath}sys/org/showJsonTree.do",
				type:"post",
				async:false,
				success: function(data){
					jsonData=data;
				},
				error:function(ajaxobj){  
					layer.msg(ajaxobj, {icon: 0,time: 3000});
			    }  
			});
			return jsonData;
		}
	</script>
</body>
</html>



