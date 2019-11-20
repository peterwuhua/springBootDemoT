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
				<form method="post" class="form-horizontal" id="listForm">
					<input name="id" id="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">工作名称:</label></td>
								<td class="width-35">
								    ${vo.name}
								</td>
								<td class="width-15 active"><label class="pull-right">汇报部门:</label></td>
								<td class="width-35">
									${vo.depart}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">工作内容:</label></td>
								<td colspan="3">
								   	${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">查&nbsp;&nbsp;阅&nbsp;人:</label></td>
								<td>
							${vo.viewer}
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">汇报人员:</label></td>
								<td>
									${vo.person}
								</td>
								<td class="active"><label class="pull-right">汇报日期:</label></td>
								<td>
									${vo.reportDate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">工作文件:</label></td>
								 <td colspan="3">
								 <c:if test="${fn:length(vo.filepath)>0}">
													<a href="download.do?filePath=${vo.filepath}&trueName=${vo.filename}" class="btn btn-w-m btn-info">下载文件</a>
									</c:if>
								</td>
							</tr>
							<tr>
							    <td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							    <td colspan="3">
							    	 <textarea rows="3" class="form-control" readonly="readonly">${vo.remark}</textarea>
							    </td>
							</tr>
														<tr>
								<td class="active"><label class="pull-right">查阅人员:</label></td>
								<td>
									${vo.viewer}
								</td>
								<td class="active"><label class="pull-right">查阅日期:</label></td>
								<td>
									${vo.viewDate}
								</td>
							</tr>
														<tr >
							    <td class="active"><label class="pull-right">反馈内容:</label></td>
							    <td colspan="3">
							        <textarea rows="3" class="form-control" readonly="readonly">${vo.fkContent}</textarea>   
							    </td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
        <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>

</body>
</html>