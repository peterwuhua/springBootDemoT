<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'updateTab.do':'addTab.do'}" class="form-horizontal">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<input name="customerVo.id" value="${vo.customerVo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;&nbsp;人:</label></td>
								<td class="width-35"><input id="name" class="form-control required" validate="required" name="name" type="text" value="${vo.name}" /></td>
								<td class="width-15 active"><label class="pull-right">联系方式:</label></td>
								<td class="width-35"><input id="phone" placeholder="13800000000" class="form-control required" validate="required" name="phone" type="text" value="${vo.phone}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></td>
								<td class="width-35"><input id="depart" class="form-control" name="depart" type="text" value="${vo.depart}" /></td>
								<td class="width-15 active"><label class="pull-right">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务:</label></td>
								<td class="width-35"><input id="duty" class="form-control" name="duty" type="text" value="${vo.duty}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">出生年月:</label></td>
								<td class="width-35">
								   <div class="input-group date">
										<input id="birthDate" name="birthDate"  class="form-control dateISO"  type="text" value="${vo.birthDate }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
								<td class="width-35"><input id="email" placeholder="x@xx.com" class="form-control email" name="email" type="text" value="${vo.email}" /></td>
							</tr>

							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td class="width-35" colspan="3"><input id="remark" class="form-control" name="remark" type="text" value="${vo.remark}" maxlength="128"/></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label></td>
								<td class="width-35"><input id="sort" placeholder="0" class="form-control" name="sort" type="text" value="${vo.sort}" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
	var index = parent.layer.getFrameIndex(window.name); 
	function saveTable(_target){
		var b = $("#thisForm").FormValidate();
		if(b){
			 $('#thisForm').ajaxSubmit(function(res) {
				parent.layer.msg(res.message, {icon: 0,time: 3000})
		    	if(res.status=='success'){
		    	    parent.jqgridReload();
		    	    parent.layer.close(index);
		        }
			});
		}
	}
</script>
</body>
</html>
