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
<!-- Data Tables -->
<link href="${basePath}h/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
<style type="text/css">
#tongzhigonggao > .list-group-item{
	display: none;
}
#tongzhigonggao > .list-group-item.show{
	display: inline;
}
.carousel-control-up > .up {
    position: absolute;
    top: 0;
    right:0;
    width:20%;
    height:100px;
    padding-right:20px;
    font-size: 30px;
    text-align: right;
    text-shadow: 0 1px 2px rgba(0,0,0,.6);
    background-color: rgba(0,0,0,0);
    filter: alpha(opacity=50);
    opacity: .5;
     z-index:9999;
}
.carousel-control-up > .down {
    position: absolute;
    bottom: 0;
    right:0;
    width:20%;
    height:100px;
    padding-top:60px;
    padding-right:20px;
    font-size: 30px;
    text-align: right;
    text-shadow: 0 1px 2px rgba(0,0,0,.6);
    background-color: rgba(0,0,0,0);
    filter: alpha(opacity=50);
    opacity: .5;
     z-index:9999;
}
.down:focus,.down:hover {
    opacity: 0.9;
    text-decoration: none;
    outline: 0px;
    color:  #676a6c;
}
.up:focus, .up:hover {
    opacity: 0.9;
    text-decoration: none;
    outline: 0px;
    color:  #676a6c;
}
.col-sm-3{
	padding: 0px 1px;
}
.col-sm-6{
	padding: 0px 1px;
}
.modal-dialog{
	width: 300px;
}
.modal-body{
	text-align: center;
	font-size: 18px;
}
.modal-footer{
	text-align: center;
}
.hr-line-dashed {
    border-top: 1px dashed #676a6c;
    color: #ffffff;
    background-color: #ffffff;
    height: 1px;
    margin: 20px 0;
}
.text-navy{
	color: red;
}
.timeline-item .date{
	width: 80px;
}
.chat-users{
    margin-left:0px;
}
.chat-user {
    padding: 8px 0px;
    width: 48%;
    float: left;
    margin: 2px;
}
.image{
	height: 80px;
	width: 100%;
	box-shadow: 0px 3px 3px #B5DBF4;
}
.image.hover{
	padding: 1px;
}
.navy-bg{
	text-shadow: 0px 1px 0px #797676;
	box-shadow: 0px 5px 5px #19a588,
				0px 5px 5px #14806a,
				0px 5px 5px #0e5d4d,
				0px 5px 5px #0a4237,
				0 6px 1px rgba(0, 0, 0, 0.1), 
				0 5px 10px rgba(0, 0, 0, 0.75), 
				0 5px 5px -5px rgba(0, 0, 0, 0.05);
}
.lazur-bg{
	text-shadow: 0px 1px 0px #797676;
	box-shadow: 0px 5px 5px #1fafb1,
				0px 5px 5px #1a9496,
				0px 5px 5px #157c7d,
				0px 5px 5px #0e5252,
				0 6px 1px rgba(0, 0, 0, 0.1), 
				0 5px 10px rgba(0, 0, 0, 0.75), 
				0 5px 5px -5px rgba(0, 0, 0, 0.05);
}
.yellow-bg{
	text-shadow: 0px 1px 0px #797676;
	box-shadow: 0px 5px 5px #d8954c,
				0px 5px 5px #bf8443,
				0px 5px 5px #a57139,
				0px 5px 5px #82582c,
				0 6px 1px rgba(0, 0, 0, 0.1), 
				0 5px 10px rgba(0, 0, 0, 0.75), 
				0 5px 5px -5px rgba(0, 0, 0, 0.05);
}
.dbDiv.hover{
	margin-top: -1px !important;
}
</style>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content" style="margin-bottom: 25px;">
		<div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><i class="fa fa-bell-o" aria-hidden="true"></i> 提醒信息</h5>
                        <div class="ibox-tools">
                            <span class="label label-warning-light">${fn:length(txList)}条</span>
                        </div>
                    </div>
                    <div class="ibox-content timeline" style="height: 410px;overflow-y: auto;">
                    	<c:forEach items="${txList}" var="e" varStatus="v">
                    		<div class="timeline-item">
	                            <div class="row">
	                                <div class="col-xs-2 date ui-sortable">
	                                    <i class="${e.icon}"></i>
	                                    <small class="text-navy">${e.status}</small>
	                                </div>
		                    		<c:choose>
		                    			<c:when test="${v.index==0}">
			                                <div class="col-xs-8 content no-top-border ui-sortable" style="min-height:60px;">
			                                    <p class="m-b-xs"><strong>${e.name}</strong></p>
			                                    <p>${e.content}</p>
			                                </div>
                    					</c:when>
                    					<c:otherwise>
			                                <div class="col-xs-8 content ui-sortable" style="min-height:60px;">
			                                    <p class="m-b-xs"><strong>${e.name}</strong></p>
			                                    <p>${e.content}</p>
			                                </div>
		                    			</c:otherwise>
		                    		</c:choose>
                    			</div>
			            	</div>
                        </c:forEach>
                    </div>
                </div>
            </div>
           
		</div>
	</div>
	
	<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
 	<!-- 全局js -->
    <script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${basePath}h/js/plugins/layer/layer.min.js"></script>
    <!-- Data Tables -->
    <script src="${basePath}h/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="${basePath}h/js/plugins/dataTables/dataTables.bootstrap.js"></script>
    
    <!-- portal 页代办提示点击链接专用js,代办提示页面必须 -->
    <script src="${basePath}h/js/contabs_portal.js"></script>
 <script type="text/javascript">

 </script>
</body>
</html>



