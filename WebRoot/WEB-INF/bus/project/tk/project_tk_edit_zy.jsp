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
					<input name="projectVo.id" value="${vo.projectVo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">踏勘人员:</label></td>
							<td class="width-35">
								<c:forEach items="${userList}" var="e" varStatus="v">
									<label class="checkbox-inline i-checks ">
										<div class="icheckbox_square-green">
											<input  type="checkbox" name="tkUserId" key="${e.name}" value="${e.id}" <c:if test="${fn:indexOf(vo.projectVo.tkUserId, e.id)>-1}">checked="checked"</c:if>>
										</div>${e.name}
									</label>
								</c:forEach>
								<input type="hidden" id="tkUserId" name="projectVo.tkUserId" value="${vo.projectVo.tkUserId}">
								<input type="hidden" id="tkUserName" name="projectVo.tkUserName" value="${vo.projectVo.tkUserName}" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">原&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由:</label></td>
							<td class="width-35">
								<textarea rows="2" class="form-control required" validate="required" id="remark" name="remark" maxlength="128"></textarea>
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
			checkboxClass : "icheckbox_square-green"
		});
	});
	var index = parent.layer.getFrameIndex(window.name); 
	function submitSave(){
		var ids=[];
		var names=[];
		$('input[name="tkUserId"]:checked').each(function(){
			ids.push($(this).val());
			names.push($(this).attr('key'));
		});
		if(ids.length==0){
			parent.layer.msg("踏勘人不能为空！", {icon: 0,time: 3000})
			return false;
		}
		if($('#remark').val()==''){
			parent.layer.msg("转移原由不能为空！", {icon: 0,time: 3000})
			return false;
		}
		$('#tkUserId').val(ids.join(','));
		$('#tkUserName').val(names.join(','));
		 
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
