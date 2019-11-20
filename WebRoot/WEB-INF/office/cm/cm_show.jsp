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
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					 <fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						  <legend style="font-size:15px">基本信息</legend>
						</fieldset>
					  <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label></td>
								<td>
									${vo.type }
								</td>
								<td class="active"></td>
								<td>
								</td>
							</tr>
								<tr>
								<td class="active"><label class="pull-right">开始时间:</label></td>
								<td>
										${vo.beginTime } 
								</td>
								<td class="active"><label class="pull-right">结束时间:</label></td>
								<td>
										 ${vo.endTime }
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时:</label></td>
								<td>
									${vo.hours }
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:</label></td>
								<td colspan="3">
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">参与人员:</label></td>
								<td>
									${vo.userNames}
								</td>
								<td class="active"><label class="pull-right">申请日期:</label></td>
								<td>
									${vo.sdate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审&nbsp;&nbsp;批&nbsp;人:</label></td>
								<td>
									${vo.auditName}
								</td>
								<td class="active"><label class="pull-right">抄&nbsp;&nbsp;送&nbsp;人:</label></td>
								<td>
									${vo.viewNames}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
							<tr>
								<th class="active"><label class="pull-right">上传附件:</label></th>
								<td colspan="3" id="removeFile">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
									 	<div style="float: left;margin-right: 10px;">
										 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-info">${e.fileName}</a>
									 	</div>
									 </c:forEach>
								</td>
							</tr>
						</tbody>
					</table>
					<c:if test="${fn:length(auditList)>0}">
						<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						  <legend style="font-size:15px">审核历史记录</legend>
						</fieldset>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<thead>
								<tr>
									<th>
					                   	 审核人
									</th>
									<th>
					             	      审核日期
									</th>
									<th>
					                 	 审核状态
									</th>
									<th>
					                 	 审核意见
									</th>
								</tr>
							</thead>
							<c:forEach items="${auditList}" var="e" varStatus="v">
								<tr>
									<td>
					                    ${e.auditName}
									</td>
									<td>
					                    ${e.auditTime}
									</td>
									<td>
					                    ${e.status}
									</td>
									<td>
					                    ${e.auditCt}
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
</body>
</html>
