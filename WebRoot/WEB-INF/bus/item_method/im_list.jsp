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
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<th style="width: 150px;">项目</th>
								<th>方法</th>
								<th style="width: 100px;">费用(元)</th>
							</tr>
						</thead>
						<c:forEach items="${list}" var="e" varStatus="v">
						<tr>
							<td>${e.itemName}</td>
							<td>${e.methodName}</td>
							<td>${e.price}</td>
						</tr>
						</c:forEach>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
	$(document).ready(function() {
		$(".i-checks").iCheck({
			radioClass : "iradio_square-green"
		});
	});
	var index = parent.layer.getFrameIndex(window.name); 
	function submitSave(){
		var ids=[];
		var names=[];
		$('input[name="userId"]:checked').each(function(){
			ids.push($(this).val());
			names.push($(this).attr('key'));
		});
		if(ids.length==0){
			parent.layer.msg("检测人不能为空！", {icon: 0,time: 3000})
			return false;
		}
		if($('#remark').val()==''){
			parent.layer.msg("转移原由不能为空！", {icon: 0,time: 3000})
			return false;
		}
		$('#testManId').val(ids.join(','));
		$('#testMan').val(names.join(','));
		 
		$.ajax({ 
			url:"update4Zy.do",
			dataType:"json",
			data:$('#thisForm').serialize(),
			type:"post",
			success: function(data){
				parent.layer.msg(data.message, {icon: 0,time: 3000})
				if("success"==data.status){
					parent.jqgridReload();
					parent.layer.close(index);
				}
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }  
		});
	}
	</script>
</body>
</html>
