<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="tabs-container" id="ibox-content">
				<div class="panel-heading" style="padding-bottom: 0px;">
					<div class="panel-options">
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">任务进度</a></li>
							<li><a data-toggle="tab" href="#tab-3" aria-expanded="false">日志详情</a></li>
						</ul>
					</div>
				</div>
				<div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<div id="vertical-timeline" class="vertical-container light-timeline">
							<c:forEach items="${vo.progressList}" var="e" varStatus="v">
								<div class="vertical-timeline-block">
									<div class="vertical-timeline-icon ${e.classes}">
										<i class="fa fa-briefcase"></i>
									</div>
									<div class="vertical-timeline-content">
										<h2>${e.busType}</h2>
										<c:choose>
											<c:when test="${fn:length(e.itList)>0}">
												<c:forEach items="${e.itList}" var="e1" varStatus="v1">
															${e1.busType}
													<div class="progress progress-striped active" style="width: 90%; margin-left: 5%">
														<c:forEach items="${e1.progressList}" var="e2" varStatus="v2">
															<div class="progress-bar ${e2.classes}" style="width: 25%;">${v2.index+1}、${e2.busType}</div>
														</c:forEach>
													</div>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<span class="vertical-date"> ${e.orgName} ${e.userName} <small>${e.date}</small>
												</span>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
					<div id="tab-3" class="tab-pane" style="padding:0px 10px;">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="background-color: white;">
							<tr>
								<td width="50px">序号</td>
								<td width="70px">操作环节</td>
								<td width="70px">操作人</td>
								<td width="150px">操作时间</td>
								<td width="100px">审核状态</td>
								<td>审核意见</td>
							</tr>
							<tbody>
								<c:forEach items="${vo.logList}" var="e" varStatus="s">
									<tr>
										<td align="center">${s.index+1}</td>
										<td>${e.busType}</td>
										<td>${e.userName}</td>
										<td>${e.logTime}</td>
										<td align="center">
											<c:choose>
												<c:when test="${e.audit=='不通过'}">
												<font color="red">${e.audit}</font>
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
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script type="text/javascript">
	function showAdivice(id,busId){
		layer.open({
			title:'进度详情',	
			type: 2,
			area: ['60%','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/samp/adivice/show4Item.do?id='+id+'&busId='+busId,
			btn: ['关闭'],
			btn1: function(index, layero) {
			}
		});
	}
</script>
</body>
</html>