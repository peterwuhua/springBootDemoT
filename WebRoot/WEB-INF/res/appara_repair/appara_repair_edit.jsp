<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

<style type="text/css">
.panel-heading {
	padding: 0px;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form id="thisForm" method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" class="form-horizontal" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<input type="hidden" name="apparaVo.id" id="apparaId" class="formText" value="${vo.apparaVo.id}" /> 
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<th class="width-15 active"><label class="pull-right">设备编号:</label></th>
								<td class="width-35">${vo.apparaVo.no}</td>
								<th class="width-15 active"><label class="pull-right">仪器名称:</label></th>
								<td class="width-35">${vo.apparaVo.name}</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">维&nbsp;&nbsp;修&nbsp;人:</label></th>
								<td class="width-35"><input id="person" class="form-control required" validate="required" name="person" type="text" value="${vo.person}" /></td>
								<th class="width-15 active"><label class="pull-right">维修日期:</label></th>
								<td class="width-35">
									<div class="input-group date">
										<input id="date" class="form-control required dateISO" validate="required" name="date" type="text" value="${vo.date }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<th class="width-15 active"><label class="pull-right">维修单位:</label></th>
								<td class="width-35"><input id="unit" class="form-control" name="unit" type="text" value="${vo.unit}" /></td>
								<td class="active"><label class="pull-right">维修结果:</label></td>
								<td>
									<select name="result" id="result" class="input-sm form-control input-s-sm inline">
										<c:choose>
											<c:when test="${vo.result=='正常'}">
												<option value="正常" selected="selected">正常</option>
												<option value="异常">异常</option>
											</c:when>
											<c:otherwise>
												<option value="正常">正常</option>
												<option value="异常" selected="selected">异常</option>
											</c:otherwise>
										</c:choose>
									</select>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">维修内容:</label></td>
								<td colspan="3">
									<textarea rows="2"  name="content" id="content" class="form-control">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3"><input maxlength="128" name="remark" id="remark" value="${vo.remark}" type="text" class="form-control" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script type="text/javascript">
		$('input[type="file"]').prettyFile();
		
		var index = parent.layer.getFrameIndex(window.name); 
		function fnSelect(){
			var t = $("#thisForm").FormValidate();
			if(t){
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    		parent.layer.msg(res.message, {icon: 0,time: 3000})
						parent.jqgridReload();
						parent.layer.close(index);
			        }else{
			        	layer.msg(res.message, {icon: 0,time: 3000});
			        }
				});
			}
		}
	</script>
</body>
</html>
