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
			<div class="ibox-content">
				<form method="post" action="${fn:length(vo.id)>0? 'update.do':'add.do'}" class="form-horizontal" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<input name="contractVo.id" value="${vo.contractVo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
								<td class="width-35"><input id="taskNo" placeholder="请输入任务编号" class="form-control required" maxlength=64 name="taskNo" type="text" value="${vo.taskNo}" /></td>
								<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
								<td class="width-35"><input id="sampName" placeholder="请输入样品名称" class="form-control required" maxlength=64 name="sampName" type="text" value="${vo.sampName}" /></td>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">样品数量:</label></td>
								<td class="width-35"><input id="sampCount" placeholder="请输入样品数量" class="form-control required" maxlength=16 name="sampCount" type="text" value="${vo.sampCount}" /></td>
								<td class="width-15 active"><label class="pull-right">送样日期:</label></td>
								<td class="width-35"><input id="sendSampDate" placeholder="请选择送样日期" class="form-control required dateISO" maxlength=16 name="sendSampDate" type="text" value="${vo.sendSampDate}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">送样人:</label></td>
								<td class="width-35"><input id="sendSampPerson" placeholder="请输入送样人" class="form-control required" maxlength=16 name="sendSampPerson" type="text" value="${vo.sendSampPerson}" /></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script type="text/javascript">
	    
	    
	    var index = parent.layer.getFrameIndex(window.name); 
		function saveTable(_target){
			$("form").attr('target',_target);
			$("form").submit();
			parent.$("#contractId").val('${vo.contractVo.id}');
			//parent.layer.close(index);
			 
		}
    </script>
</body>
</html>
