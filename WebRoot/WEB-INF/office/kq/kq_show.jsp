<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form method="post" class="form-horizontal" id="listForm">
					<input name="id" id="id" value="${vo.id}" type="hidden" />
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label></td>
								<td class="width-35">
									${vo.type}
								</td>
								<td class="width-15 active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></td>
								<td class="width-35">
									${vo.deptName}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">申&nbsp;请&nbsp;&nbsp;人:</label></td>
								<td class="width-35">
									${vo.userName}
								</td>
								<td class="width-15 active"><label class="pull-right">申请时间:</label></td>
								<td class="width-35">
									${vo.date}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">开始时间:</label></td>
								<td>
									${vo.startTime}
								</td>
								<td class="active"><label class="pull-right">截止时间:</label></td>
								<td>
									${vo.endTime}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">共计(h):</label></td>
								<td class="width-35">
								  ${vo.hours}
								</td>
								<td class="active"><label class="pull-right">工作代理人:</label></td>
								<td>
									${vo.jober}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">请假事由:</label></td>
								<td colspan="3">
									${vo.content}
								</td>
							</tr>
							<tr>
								<td colspan="4">
									审批记录
								</td>
							</tr>
						</tbody>
					</table>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th width="50">序号</th>
								<th width="150">节点</th>
								<th width="150">审批人</th>
								<th width="150">审批时间</th>
								<th width="150">审批结果</th>
								<th>审批意见</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${logList}" var="e" varStatus="v">
								<tr>
									<td>${v.index+1}</td>
									<td>${e.busType}</td>
									<td>${e.userName}</td>
									<td>${e.logTime}</td>
									<td>
										<c:choose>
											<c:when test="${e.audit=='提交'}">
												通过
											</c:when>
											<c:otherwise>
												${e.audit}
											</c:otherwise>
										</c:choose>
									</td>
									<td>${e.msg}</td>
								</tr>
							</c:forEach>
						</tbody>
						<c:if test="${fn:length(logList)==0}">
							<tfoot>
								<tr>
									<td colspan="6">
										暂无记录
									</td>
								</tr>
							</tfoot>
						</c:if>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
        <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
	<script>
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
	});
	 
	</script>
</body>
</html>