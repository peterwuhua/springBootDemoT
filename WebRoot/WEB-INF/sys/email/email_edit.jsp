<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<link href="${basePath}h/css/plugins/summernote/summernote.css" rel="stylesheet">
<link href="${basePath}h/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
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
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="gridPage.do">邮件</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" action="add4Data.do" class="form-horizontal" enctype="multipart/form-data">
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">收件人:</label></td>
								<td class="width-85"><input id="receiver" placeholder="收件人" class="form-control email required"  validate="required" maxlength=64 name="receiver" type="email" value="${vo.receiver}" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">主题:</label></td>
								<td><input id="subject" name="subject" class="form-control " placeholder="主题" type="text" value="${vo.subject }" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内容:</label></td>
								<td><div class="summernote">${vo.content }</div> <input id="content" name="content" type="hidden" value="${vo.content }" /></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-primary" type="button" onclick="fnFormSubmit();"><i class="fa fa-check" aria-hidden="true"></i> 发送</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	 <%@ include file="../../include/js.jsp"%>

	<script src="${basePath}h/js/plugins/summernote/summernote.min.js"></script>
	<script src="${basePath}h/js/plugins/summernote/summernote-zh-CN.js"></script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('.summernote')
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
														[ 'table', [ 'table' ] ] ],
												height : "100px",
												focus : true
											});

						});
		function fnFormSubmit() {
			$("#content").val($(".summernote").code());
			var b = $("form").FormValidate();
			if(b){
				 $('form').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
			}
		}
		function test() {
			var smsData = '{"receiver":"receiver","content":"content","busType":"busType","busInfo":"busInfo","remark":"remark"}';
			var mailData = '{"receiver":"receiver","subject":"subject","content":"content","busType":"busType","busInfo":"busInfo","remark":"remark"}';
			fnSendMessage(smsData, mailData);
		}
	</script>
</body>
</html>
