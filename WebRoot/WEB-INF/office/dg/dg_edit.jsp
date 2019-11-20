<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 5px 10px;">
				<form method="post" action="${fn:length(vo.id)>0? 'update2Bk.do?isCommit=0':'add2Bk.do?isCommit=0'}" class="form-horizontal" id="listForm">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" id="id" value="${vo.id}" type="hidden" />
					</c:if>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;型:</label></td>
								<td class="width-35">
									<input readonly="readonly" id="type" name="type" class="form-control " type="text" value="补卡" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">补卡人:</label></td>
								<td class="width-35">
									<input readonly="readonly" id="userName" name="userName" class="form-control " type="text" value="${vo.userName}" />
									<input  id="userId" name="userId" type="hidden" value="${vo.userId}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">补卡时间:</label></td>
								<td>
									<div class="input-group date">
		                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                           		<input id="date" name="date"  class="form-control required datetimeISO" validate="required" placeholder="开始日期" type="text" value="${vo.date}" />
		                            </div>
								</td>
							</tr>
							 <tr>
								<td class="active"><label class="pull-right">描&nbsp;&nbsp;&nbsp;述:</label></td>
								<td>
									<textarea rows="2" class="form-control" id="content" placeholder="补卡原因" name="content">${vo.content}</textarea>
								</td>
							</tr>
							 <tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;注:</label></td>
								<td>
									<textarea rows="2" class="form-control" id="remark" placeholder="" name="remark">${vo.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审核人:</label></td>
								<td>
									<select class="form-control required" validate="required" id="auditId" name="auditId">
										<option value="">-请选择-</option>
										<c:forEach items="${userList}" var="e" varStatus="v">
										 	 <c:choose>
										 	 	<c:when test="${vo.auditId==e.id}">
										 	 		<option value="${e.id}" selected="selected">${e.userVo.name}</option>
										 	 	</c:when>
										 	 	<c:otherwise>
										 	 		<option value="${e.id}">${e.userVo.name}</option>
										 	 	</c:otherwise>
										 	 </c:choose>
										 </c:forEach>
									</select>
									<input  id="auditName" name="auditName" type="hidden" value="${vo.auditName}" />
								</td>
							</tr>
						</tbody>
					</table>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1" align="center">
							<a class="btn  btn-success" type="button" onclick="fnSubmitSave('save2Bk.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn  btn-primary" type="button" onclick="submitForm('${fn:length(vo.id)>0? 'update2Bk.do?isCommit=1':'add2Bk.do?isCommit=1'}')"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
	var index = parent.layer.getFrameIndex(window.name); 
	function fnSubmitSave(url){
		var t = $("#listForm").FormValidate();
		if(t){
			$('#auditName').val($('#auditId').find('option[selected="selected"]').text());
			$.ajax({ 
				url:url,
				dataType:"json",
				data:$('#listForm').serialize(),
				type:"post",
				success: function(data){
					parent.layer.msg(data.message, {icon: 0,time: 3000})
					if("success"==data.status){
						parent.layer.close(index);
					}
				},
				error:function(ajaxobj){  
					layer.msg(ajaxobj, {icon: 0,time: 3000});
			    }  
			});
		}
	}
	function submitForm(url){
		var t = $("#listForm").FormValidate();
		if(t){
			layer.confirm('确认要提交吗?', {icon:3, title:'系统提示'}, function(index){
				$('#auditName').val($('#auditId').find('option[selected="selected"]').text());
				$.ajax({ 
					url:url,
					dataType:"json",
					data:$('#listForm').serialize(),
					type:"post",
					success: function(data){
						parent.layer.msg(data.message, {icon: 0,time: 3000})
						if("success"==data.status){
							parent.layer.close(index);
						}
					},
					error:function(ajaxobj){  
						layer.msg(ajaxobj, {icon: 0,time: 3000});
				    }  
				});
			});
		}
	}
	</script>
</body>
</html>