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
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">补卡审核</a></li>
					<li><strong>编辑</strong>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					 <input name="id" value="${vo.id}" type="hidden" />
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">类型:</label></td>
								<td class="width-35">
									${vo.type}
								</td>
								<td class="width-15 active"><label class="pull-right">补卡人:</label></td>
								<td class="width-35">
									${vo.userName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">时间:</label></td>
								<td>
									${vo.date}
								</td>
								<td class="active"><label class="pull-right">描述:</label></td>
								<td>
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
						</tbody>
					</table>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active"><label class="pull-right">审核意见:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="auditMsg" placeholder="" name="auditMsg">${vo.auditMsg}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审&nbsp;&nbsp;核&nbsp;人:</label></td>
								<td>
									<input readonly="readonly" id="auditName" name="auditName" class="form-control " type="text" value="${vo.auditName}" />
									<input  id="auditId" name="auditId" type="hidden" value="${vo.auditId}" />
								</td>
								<td class="active"><label class="pull-right">审核日期:</label></td>
								<td>
									<div class="input-group date">
		                           		<input id="auditDate" name="auditDate" class="form-control required datetimeISO" validate="required" type="text" value="${vo.auditDate}" />
		                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
						 
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('updateData.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 通过</a>
							<a class="btn btn-w-m btn-danger" href="javascript:;" onclick="formSubmit4Back('updateData.do?isCommit=-1');"><i class="fa fa-check" aria-hidden="true"></i> 退回</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
		   <!-- Sweet alert -->
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>
	function formSubmit(url){
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
			swal({
		        title: "您确定要提交吗",
		        text: "提交后将无法修改，请谨慎操作！",
		        type: "success",
		        showCancelButton: true,
		        confirmButtonColor: "#1ab394",
		        confirmButtonText: "确定",
		        cancelButtonText: "取消"
		    }, function () {
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
	   		});
		}
	}
	function formSubmit4Back(url){
		var auditCt=$('#auditMsg').val();
		if(auditCt==''){
			parent.toastr.error('请输入原因！', '');
			return false;
		}
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
			swal({
		        title: "您确定要退回该任务吗",
		        text: "提交后将无法修改，请谨慎操作！",
		        type: "warning",
		        showCancelButton: true,
		        confirmButtonColor: "#DD6B55",
		        confirmButtonText: "确定",
		        cancelButtonText: "取消"
		    }, function () {
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
	   		 });
		}
	}
	</script>
</body>
</html>
