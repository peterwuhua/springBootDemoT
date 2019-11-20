<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend {
	border-bottom: 0px;
	width: 80px;
	margin-bottom: 0px;
	font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>培训管理</a></li>
					<li><strong>实施</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form id="thisForm" method="post" action="" class="form-horizontal">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></td>
								<td class="width-35">${vo.orgName}</td>
								<td class="width-15 active"><label class="pull-right">负&nbsp;责&nbsp;人:</label></td>
								<td class="width-35">${vo.userName }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题 :</label></td>
								<td class="width-35">${vo.title}</td>
								<td class="width-15 active"><label class="pull-right">岗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
								<td class="width-35">${vo.post}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">开始时间:</label></td>
								<td class="width-35">${vo.startDate }</td>
								<td class="width-15 active"><label class="pull-right">结束时间:</label></td>
								<td class="width-35">${vo.endDate }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型 :</label></td>
								<td class="width-35">${vo.type}</td>
								<td class="width-15 active"><label class="pull-right">培训地点:</label></td>
								<td class="width-35">${vo.address}</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">培训内容:</label></td>
								<td class="width-35" colspan="3">${vo.content }</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td class="width-35" colspan="3">${vo.remark }</td>
							</tr>
						</tbody>
					</table>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						<legend>实施信息</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<th>序号</th>
							<th>被培训/考核人员</th>
							<th>培训/考核时间</th>
							<th>结果</th>
							<th>备注</th>
						</tr>
						<c:forEach items="${vo.detailList}" var="e" varStatus="v">
							<tr>
								<td>${v.index+1} <input type="hidden" name="detailList[${v.index}].id" value="${e.id}">
								</td>
								<td>${e.userName}</td>
								<td><input type="text" placeholder="请选择" name="detailList[${v.index}].trainDate" value="${e.trainDate}"  class="form-control datetimeISO required" validate="required"></td>
								<td><input type="text" placeholder="请输入" name="detailList[${v.index}].result" value="${e.result}" class="form-control required" validate="required"></td>
								<td><input maxlength="128" type="text" placeholder="请输入" name="detailList[${v.index}].remark" value="${e.remark}" class="form-control"></td>
							</tr>
						</c:forEach>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmitSave('updateDetail.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('update4Comp.do');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<!-- Sweet alert -->
	<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script type="text/javascript">
    function formSubmitSave(url){
    	$('#thisForm').attr('action',url);
		$('#thisForm').submit()
	}
    function formSubmit(url){
    	swal({
	        title: "您确定要提交吗",
	        text: "提交后将无法修改，请谨慎操作！",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消"
	    }, function () {
	    	$('#thisForm').attr('action',url);
	    	var b = $("#thisForm").FormValidate();
			if(b){
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
			}
	    });
	}
    </script>
</body>
</html>
