<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${basePath}h/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="${basePath}h/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${basePath}h/css/plugins/summernote/summernote.css" rel="stylesheet">
<link href="${basePath}h/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
<link href="${basePath}h/css/animate.css" rel="stylesheet">
<link href="${basePath}h/css/style.css?v=4.1.0" rel="stylesheet">
<style>
    .col-sm-3{
            padding-right: 2px;
            padding-left: 5px;
    }
    .col-sm-9{
            padding-right: 5px;
            padding-left: 2px;
    }
 
    .col-sm-12{
            padding-right: 5px;
            padding-left: 5px;
    }
</style>
</head>

<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="gridPage.do">用户体验</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form class="form-horizontal" action="${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}" method="post" enctype="multipart/form-data">
					<div class="mail-box">
						<c:if test="${fn:length(vo.id)>0}">
							<input name="id" value="${vo.id}" type="hidden" />
						</c:if>
						<input id="content" name="content" type="hidden" />
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tbody>
								<tr>
									<td class="width-15 active"><label class="pull-right">问题描述:</label></td>
									<td><input type="text" id="title" name="title" class="form-control" placeholder="请输入问题描述" value="${vo.title }"></td>
									<td class="width-15 active"><label class="pull-right">模块:</label></td>
									<td><input type="text" id="moduleName" name="moduleName" class="form-control" placeholder="输入模块名称" value="${vo.moduleName }"></td>
								</tr>
								<tr>
									<td class="width-15 active"><label class="pull-right">排序:</label></td>
									<td><input type="text" id="sort" name="sort" class="form-control" placeholder="排序" value="${vo.sort }"></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">上传文件:</label></td>
									<td>
										<input type="file" name="file" multiple="multiple" class="form-control"/>
									</td>
									<td colspan="2" id="removeFile">
										<c:forEach items="${vo.fileList}" var="e" varStatus="v">
										 	<div style="float: left;margin-right: 10px;">
											 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
											 	<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
										 	</div>
										 </c:forEach>
									</td>
								</tr>
							</tbody>
						</table>
	
						<div class="mail-text h-200">
							<div class="summernote" id="summernote">${vo.content }</div>
							<div class="clearfix"></div>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="row">
						<div class="form-group">
							<div class="col-sm-12 col-sm-offset-1">
								<button class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('');"><i class="fa fa-check" aria-hidden="true"></i> 保存</button>
								<a href="javascript:backMainPage();" class="btn btn-w-m btn-white" data-placement="top" data-toggle="tooltip" data-original-title="返回"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>	
	 <%@ include file="../../include/js.jsp"%>

	<script src="${basePath}h/js/plugins/summernote/summernote.min.js"></script>
	<script src="${basePath}h/js/plugins/summernote/summernote-zh-CN.js"></script>
	<script>
	$('input[type="file"]').prettyFile();
		$(document).ready(function() {
			$(".summernote").summernote({
				lang: 'zh-CN',
				toolbar: [
				          ['style', ['style']],
				          ['font', ['bold', 'underline', 'clear']],
				          ['fontname', ['fontname']],
				          ['color', ['color']],
				          ['para', ['ul', 'ol', 'paragraph']],
				          ['table', ['table']],
				          ['picture', ['picture']],
				          ['view', ['fullscreen']]
				        ],
				height: "300px",  
            	 onImageUpload: function(files,editor,welEditable) {
                    var formData = new FormData();
                    formData.append('file',files[0]);
                    $.ajax({
                        url : 'uploadFile.do',//后台文件上传接口
                        type : 'POST',
                        data : formData,
                        processData : false,
                        contentType : false,
                        success : function(data) {
                            editor.insertImage(welEditable,"/"+data);
                        }
                    });
		       }  
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
		
		function fnSubmit(flag){
			$("#content").val($(".summernote").code());
			 $('form').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
			        backMainPage();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
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
