<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>后台主题UI框架 - 主页</title>
    <link rel="shortcut icon" href="favicon.ico">
	<link href="${basePath}h/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${basePath}h/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${basePath}h/css/animate.css" rel="stylesheet">
    <link href="${basePath}h/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="${basePath}h/css/login.css" rel="stylesheet">
<style type="text/css">
.wrapper-content{
	width:100%;
	height:100%;
	display: flex;
	justify-content: center;  /*水平居中*/
    align-items: center;      /*垂直居中*/
}
#divWin{
	height: 420px;
	background-color: #fff;
	/*box-shadow: 0px 0px 3px 1px #B5DBF4;*/
	border-top: 1px solid #74afe6;
	box-shadow: 0 2px 1px 0 #337ab7, 
				0 4px 1px -1px #2b6aa0, 
				0 6px 1px -2px #245988, 
				0 8px 1px -3px #204e77, 
				0 10px 1px -4px #1c466b, 
				0 12px 1px -5px #183d5d, 
				0 14px 1px -6px #163652, 
				0 16px 1px -7px #15344e, 
				0 18px 1px -8px #112b40, 
				0 20px 1px -9px #0e2333, 
				0 6px 1px rgba(0, 0, 0, 0.1), 
				0 10px 20px rgba(0, 0, 0, 0.75), 
				0 20px 4px -6px rgba(0, 0, 0, 0.9), 
				0 45px 5px -5px rgba(0, 0, 0, 0.05), 
				0 35px 10px -5px rgba(0, 0, 0, 0.1), 
				0 25px 55px 5px rgba(0, 0, 0, 0.15);
}
.head_div{
	height: 70px;
	text-align: center;
	background-color: #4e97d9;
	color: #fff;
	font-size: 24px;
	padding-top: 20px;
	/*width: 100% !important;
	box-shadow: 0px 0px 3px 3px #999999;
	box-shadow: 0px 3px 3px #999999;*/
}
.body_tb{
	margin: 20px 30px 20px 30px;
}
.img_td{
	text-align: center;
	padding: 30px;
}
.image{
	width:200px !important;
	height:250px !important;
}

.image.hover{
	/*border:1px dotted #d60000;
	padding:2px;*/
	margin-top:-5px;
	box-shadow: 0px 5px 5px #999999;
}
</style>
</head>
<body class="signin">
<input type="hidden" id="atsize" value="${fn:length(accountList)}">
    <div class="wrapper-content">
    	<div id="divWin" class="row animated bounceIn" style="text-align:center;">
    		<div class="head_div">
	    		选择进入平台
    		</div>
    		<table class="body_tb">
   				<tr>
    				<c:forEach items="${accountList}" var="e">
    					<td  class="img_td">
    						<a href="javascript:void(0);" onclick="changeAcount('${e.loginName}')">
			                      <img class="image" alt="image" src="${basePath}static/img/login_btn/${e.orgVo.id}.jpg">
			                      <p style="display: none;">${e.orgVo.name}</p>
			                  </a>
    					</td>
    				</c:forEach>
    			</tr>
    		</table>
		</div>
    </div>
    <!-- 全局js -->
    <script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
    <script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript">
function changeAcount(loginName){
	window.location.href='updateAccount.do?loginname='+loginName;
}
$(function(){
	var width='840px';
	var atsize=$('#atsize').val();
	if(atsize=='2'){
		width='580px';
	}
	$('#divWin').css('width',width);
	
	$('.image').each(function(){
		$(this).hover(function() {
			if(!$(this).hasClass('hover'))
			{
			    $(this).addClass("hover");
			 }
		},
		function() {
		   if($(this).hasClass('hover'))
		   {
		    $(this).removeClass("hover");
		   }  
		});
	});
})
</script>
</body>
</html>



