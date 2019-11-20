<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
a{
	color: blue;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 0px;">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input id="id" name="id" value="${vo.id}" type="hidden" />
					<div class="ibox-content">
                        <ul class="nav nav-tabs" id="avatar-tab">
                            <li class="active" id="upload"><a href="javascript:;">本地上传</a></li>
                            <li id="webcam"><a href="javascript:;">视频拍照</a></li>
                        </ul>
                        <div class="m-t m-b" style="margin-top:0px; ">
                            <div id="flash1">
                                <p id="swf1">本组件需要安装Flash Player后才可使用，请从<a href="http://www.adobe.com/go/getflashplayer">这里</a>下载安装。</p>
                            </div>
                            <div id="editorPanelButtons" style="display:none">
                                <p>
                                    <a href="javascript:;" class="btn btn-w-m btn-primary button_upload"><i class="fa fa-upload"></i> 上传</a>
                                    <a href="javascript:;" class="btn btn-w-m btn-white button_cancel">取消</a>
                                </p>
                            </div>
                            <p id="webcamPanelButton" style="display:none">
                                <a href="javascript:;" class="btn btn-w-m btn-info button_shutter"><i class="fa fa-camera"></i> 拍照</a>
                            </p>
                        </div>
                    </div>
				</form>
			</div>
		</div>
	</div>
	
	<%@ include file="../../include/js.jsp"%>
    <!-- fullavatareditor -->
    <script type="text/javascript" src="${basePath}h/plugins/fullavatareditor/scripts/swfobject.js"></script>
    <script type="text/javascript" src="${basePath}h/plugins/fullavatareditor/scripts/fullAvatarEditor.js"></script>
    <script type="text/javascript" src="${basePath}h/plugins/fullavatareditor/scripts/jQuery.Cookie.js"></script>
    <script>var userId ='${vo.id}'</script>
    <script type="text/javascript" src="${basePath}h/plugins/fullavatareditor/scripts/test_dt.js"></script>
	<script>
	var index = parent.layer.getFrameIndex(window.name); 
	function fnUpdateAvatar(path){
		parent.$('#filePath').val(path);
		parent.$('#fileName').val('online');
		setTimeout(parent.formSubmit4Online(),2000)
	}
</script>
</body>
</html>
