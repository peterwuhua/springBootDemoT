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
								<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
								<td class="width-35"><input id="sampName" placeholder="请输入样品名称" class="form-control required" maxlength=64 name="sampName" type="text" value="${vo.sampName}" /></td>
								<td class="width-15 active"><label class="pull-right">样品类型 :</label></td>
								<td class="width-35"><input id="sampType" placeholder="请输入样品类型" class="form-control required" maxlength=64 name="sampType" type="text" value="${vo.sampType}" /></td>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">测试参数:</label></td>
								<td class="width-35"><input id="item" placeholder="请输入测试参数" class="form-control required" maxlength=64 name="item" type="text" value="${vo.item}" /></td>
								<td class="width-15 active"><label class="pull-right">检测标准:</label></td>
								<td class="width-35"><input id="standard" placeholder="请输入检测标准" class="form-control required" maxlength=16 name="standard" type="text" value="${vo.standard}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">样品批次:</label></td>
								<td class="width-35"><input id="batch" placeholder="请输入样品批次" class="form-control required" maxlength=16 name="batch" type="text" value="${vo.batch}" /></td>
								<td class="width-15 active"><label class="pull-right">样品单价:</label></td>
								<td class="width-35"><input id="price" placeholder="请输入样品单价" class="form-control" maxlength=16 name="price" type="text" value="${vo.price}" /></td>
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
