<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>
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
					<input id="id" name="timVo.id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">质控项目:</label></td>
							<td class="width-35">
								${vo.itemName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">质控类型:</label></td>
							<td class="width-35">
								<label class="checkbox-inline i-checks ">
									<div class="iradio_square-green">
										<input  type="radio" name="zkType" value="2" checked="checked">
									</div>室内平行样
								</label>
								<label class="checkbox-inline i-checks ">
									<div class="iradio_square-green">
										<input  type="radio" name="zkType" value="4">
									</div>加标回收样
								</label>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<script>
	$(document).ready(function() {
		$(".i-checks").iCheck({
			radioClass : "iradio_square-green"
		});
	});
	var index = parent.layer.getFrameIndex(window.name); 
	function fnSelect(){
		var id='';
		$.ajax({ 
			url:"update4Zk.do",
			dataType:"json",
			async:false,
			data:$('#thisForm').serialize(),
			type:"post",
			success: function(data){
				if("success"==data.status){
					id=data.object;
				}
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }  
		});
		return id;
	}
	</script>
</body>
</html>
