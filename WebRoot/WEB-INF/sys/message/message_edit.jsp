<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${basePath}h/css/font-awesome.min.css?v=4.7.0" rel="stylesheet">
<link href="${basePath}h/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${basePath}h/css/plugins/summernote/summernote.css" rel="stylesheet">
<link href="${basePath}h/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
<link href="${basePath}h/css/animate.css" rel="stylesheet">
<link href="${basePath}h/css/style.css?v=4.1.0" rel="stylesheet">
<style type="text/css">
.note-editor {
	min-height: 300px;
}

.col-sm-10 {
	padding-right: 5px;
	padding-left: 2px;
}
.btn-info{
  background-color: #18a689;
  border-color: #18a689;
}
</style>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-2">
				<jsp:include page="message_left.jsp" />
			</div>
			<div class="col-sm-10 animated fadeInRight">
				<div class="mail-box" style="margin-bottom: 0px;">
					<form method="post" action="update4Data.do?isCommit=0" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
						<input type="hidden" name="content" id="content" value=""> <input type="hidden" name="position" id="position" value=""> <input type="hidden" name="id" id="position" value="${vo.id }">
						<div class="mail-body">
							<div class="form-group">
								<label class="col-sm-1 control-label" style="width: 100px;">收件人：</label>
								<div class="col-sm-10">
									<div class="input-group">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectuser(0)">选择</button>
										</div>
										<c:if test="${'draft' eq param.flag || param.flag eq 'sentbox'}">
											<input type="text" readonly="readonly" id="selectAccountNames" name="reciver" class="form-control required" validate="required"  placeholder="请选择收件人" value="${vo.reciver}">
											<input type="hidden" id="selectAccountIds" name="reciverIds" value="${vo.reciverIds}">
										</c:if>
										<c:if test="${param.flag ne 'draft' &&  param.flag ne 'sentbox'}">
											<input type="text" readonly="readonly" id="selectAccountNames" name="reciver" class="form-control required" validate="required"  placeholder="请选择收件人" <c:if test="${fn:length(vo.id)>0 }">value="${vo.senderVo.userVo.name}【${vo.senderVo.loginName}】"</c:if>>
											<input type="hidden" id="selectAccountIds" name="reciverIds" value="${vo.senderVo.id}">
										</c:if>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 control-label" style="width: 100px;">主&nbsp;&nbsp;&nbsp;&nbsp;题：</label>
								<div class="col-sm-10">
									<input type="text" name="subject" class="form-control required" placeholder="请输入主题" value="${vo.subject}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 control-label" style="width: 100px;">添加附件：</label>
								<div class="col-sm-10">
								<input type="file" name="files" multiple="multiple" class="form-control "/>
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-info">${e.fileName}</a>
									 	<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
								 	</div>
								 </c:forEach>
								</div>								 
							</div>
						</div>
					</form>
					<div class="mail-text h-200">
						<div class="summernote" style="min-height: 300px;">${vo.content}</div>
					</div>
					<div class="mail-body text-right tooltip-demo">
						<a href="javascript:;" onclick="fnSubmit(1)" type="submit" class="btn btn-sm btn-primary" data-toggle="tooltip" data-placement="top" title="发送"><i class="fa fa-reply"></i> 发送</a>
						<a href="javascript:;" onclick="fnSubmit(-2)" type="submit" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="存为草稿"><i class="fa fa-pencil"></i> 存为草稿</a> 
						<a href="javascript:;" onclick="fnBack()" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="返回"><i class="fa fa-undo"></i>返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${basePath}h/js/jquery.min.js?v=2.1.4"></script>
	<script src="${basePath}h/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${basePath}h/js/content.js?v=1.0.0"></script>
	<script src="${basePath}h/js/plugins/iCheck/icheck.min.js"></script>
	<script src="${basePath}h/js/plugins/layer/layer.min.js"></script>
	<script src="${basePath}h/js/plugins/pace/pace.min.js"></script>
	<script src="${basePath}h/js/plugins/treeview/bootstrap-treeview.js"></script>
	<script src="${basePath}h/js/demo/treeview-demo.js" type="text/javascript"></script>
	<script src="${basePath}h/js/plugins/prettyfile/bootstrap-prettyfile.js"></script>
	<script src="${basePath}h/js/plugins/validate/jquery.validate.min.js"></script>
	<script src="${basePath}h/js/plugins/validate/messages_zh.min.js"></script>
	<script src="${basePath}h/js/plugins/blueimp/jquery.blueimp-gallery.min.js"></script>
	<script src="${basePath}h/js/plugins/validate/ajaxValidate.js"></script>


	<script src="${basePath}ext/js/ext.js" type="text/javascript"></script>
	<script src="${basePath}ext/search_suggest/js/search-suggest.js" type="text/javascript"></script>
	<script src="${basePath}h/js/plugins/summernote/summernote.min.js"></script>
	<script src="${basePath}h/js/plugins/summernote/summernote-zh-CN.js"></script>
	<script>
		$(document)
				.ready(
						function() {
							$(".summernote")
									.summernote(
											{
												lang : 'zh-CN',
												toolbar : [
														[ 'style', [ 'style' ] ],
														[
																'font',
																[
																		'bold',
																		'underline',
																		'clear' ] ],
														[ 'fontname',
																[ 'fontname' ] ],
														[ 'color', [ 'color' ] ],
														[
																'para',
																[ 'ul', 'ol',
																		'paragraph' ] ],
														[ 'table', [ 'table' ] ],
														[
																'view',
																[ 'fullscreen' ] ] ]
											});
							var edit = function() {
								$(".click2edit").summernote({
									focus : true
								})
							};
							var save = function() {
								var aHTML = $(".click2edit").code();
								$(".click2edit").destroy()
							};
						});

		function fnSubmit(position) {
			$("#content").val($(".summernote").code());
			$("#position").val(position);
			$(".form-horizontal").submit();
		}

		function fnBack() {
			var flag = '${param.flag}';
			if ("sentbox" == flag) {
				location.href = 'sentbox.do';
			} else if ("draft" == flag) {
				location.href = 'draft.do';
			} else if ("inbox" == flag) {
				location.href = 'inbox.do';
			} else {
				location.href = 'inbox.do';
			}
		}

		function fnSelectuser(type) {
			layer.open({
				title : '收件人选择',
				type : 2,
				area : [ '70%', '85%' ],
				fix : false, //不固定
				maxmin : true,
				content : '/sys/account/selects.do?ids='+ $('#selectAccountIds').val(),
				btn : [ '确定', '取消' ], //按钮
				btn1 : function(index, layero) {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var data = iframeWin.fnSelect();
					$('#selectAccountIds').val(data.id);
					$('#selectAccountNames').val(data.name);
				}
			});
		}

		$('input[type="file"]').prettyFile();
		function removeFiles(id,obj){
			layer.confirm('确认要删除?', {icon:3, title:'系统提示'}, function(index){
				$.ajax({
					url:'${basePath}sys/files/deleteOne.do?id='+id,
					dataType:"json",
					type:"post",
					async:false,
					success: function(data){
						if(data.status=='success'){
							layer.msg(data.message, {icon: 0,time: 1000});
							$(obj).parent().remove();
						}
					},
					error:function(ajaxobj){
				    }  
				});
			});
		}

	</script>
</body>
</html>
