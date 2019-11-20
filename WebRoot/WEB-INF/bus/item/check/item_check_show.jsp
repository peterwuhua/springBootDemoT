<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
legend {
	border-bottom: 0px;
	width: 80px;
	margin-bottom: 0px;
	font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" class="form-horizontal" id="thisForm">
					<input name="ids" value="${vo.ids}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
						<tr>
							<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
							<td class="width-35">
								${vo.taskVo.no}
							</td>
							<td class="width-15 active"><label class="pull-right">检测项目:</label></td>
							<td class="width-35">
								${vo.itemName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测方法:</label></td>
							<td class="width-35">
								${vo.methodName}
							</td>
							<td class="width-15 active"><label class="pull-right">检&nbsp;出&nbsp;&nbsp;限:</label></td>
							<td class="width-35">
								${vo.limitLine}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">计量单位:</label></td>
							<td class="width-35">
								${vo.unit}
							</td>
							<td class="width-15 active"><label class="pull-right">检测仪器:</label></td>
							<td class="width-35">
								${vo.appName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">温&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度:</label></td>
							<td class="width-35">
								${vo.wd}
							</td>
							<td class="width-15 active"><label class="pull-right">湿&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度:</label></td>
							<td class="width-35">
								${vo.sd}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">附件信息:</label></td>
							<td colspan="3">
								<c:forEach items="${vo.fileList}" var="e1" varStatus="v1">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e1.filePath}&trueName=${e1.fileName}" class="btn btn-w-m btn-info">${e1.fileName}</a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>结果信息</b>
							</td>
						</tr>
					</table>
					<c:choose>
						<c:when test="${vo.taskVo.sampType=='环境'}">
							<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 1px;">
								<thead>
									<tr>
										<th style="text-align: center;width: 50px;">序号</th>
										<th style="text-align: center;width: 80px;">样品名称</th>
										<th style="text-align: center;width: 80px;">采样日期</th>
										<th style="text-align: center;width: 80px;">样品编号</th>
										<c:choose>
											<c:when test="${vo.st=='气'}">
												<th style="text-align: center;width: 150px;">浓度值</th>
												<th style="text-align: center;width: 150px;">速率值</th>
											</c:when>
											<c:otherwise>
												<th style="text-align: center;width: 150px;">检测值</th>
											</c:otherwise>
										</c:choose>
										<th style="text-align: center;width: 80px;">单项判定</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${vo.itemList}" var="e1" varStatus="s1">
										<tr>
											<td align="center">
												${s1.index+1}
											</td>
											<td align="center">
												${e1.trVo.sampVo.sampTypeName}
											</td>
											<td align="center">
												${e1.cyDate}
											</td>
											<td>
												${e1.trVo.sampVo.sampCode}
											</td>
											<c:choose>
												<c:when test="${vo.st=='气'}">
													<td>
					                                    ${e1.trVo.value}
													</td>
													<td>
					                                   ${e1.trVo.sl}
													</td>
												</c:when>
												<c:otherwise>
													<td>
					                                   ${e1.trVo.value}
													</td>
												</c:otherwise>
											</c:choose>
											<td >
												${e1.result}
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:when>
						<c:otherwise>
							<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 1px;">
								<thead>
									<tr>
										<th style="text-align: center;width: 50px;">序号</th>
										<th style="text-align: center;width: 80px;">样品名称</th>
										<th style="text-align: center;width: 80px;">采样日期</th>
										<th style="text-align: center;width: 80px;">样品编号</th>
										<th style="text-align: center;width: 150px;">检测值</th>
										<c:choose>
											<c:when test="${vo.limited=='a'}">
												<th style="text-align: center;width: 80px;">Cmac</th>
											</c:when>
											<c:when test="${vo.limited=='b'}">
												<th style="text-align: center;width: 80px;">Ctwa</th>
												<th style="text-align: center;width: 80px;">Cstel</th>
											</c:when>
											<c:when test="${vo.limited=='c'}">
												<th style="text-align: center;width: 80px;">Ctwa</th>
												<th style="text-align: center;width: 80px;">超限倍数</th>
											</c:when>
											<c:otherwise>
												<th style="text-align: center;width: 80px;">均值</th>
											</c:otherwise>
										</c:choose>
										<th style="text-align: center;width: 80px;">单项判定</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${vo.itemList}" var="e1" varStatus="s1">
										<c:forEach  items="${e1.trList}" var="r2" varStatus="rs2">
											<tr>
												<c:if test="${rs2.index==0}">
													<td align="center" rowspan="${fn:length(e1.trList)}">
														${s1.index+1}
													</td>
													<td align="center" rowspan="${fn:length(e1.trList)}">
														${e1.pointVo.sampName}
													</td>
													<td align="center" rowspan="${fn:length(e1.trList)}">
														${e1.cyDate}
													</td>
												</c:if>
												<td>
													${r2.sampVo.sampCode}
												</td>
												<td>
				                                   ${r2.value}
												</td>
												<c:if test="${rs2.index==0}">
													<c:choose>
														<c:when test="${vo.limited=='a'}">
															<td rowspan="${fn:length(e1.trList)}">
							                                   ${e1.mac}
															</td>
														</c:when>
														<c:when test="${vo.limited=='b'}">
															<td rowspan="${fn:length(e1.trList)}">
							                                    ${e1.twa}
															</td>
															<td rowspan="${fn:length(e1.trList)}">
							                                   ${e1.stel}
															</td>
														</c:when>
														<c:when test="${vo.limited=='c'}">
															<td rowspan="${fn:length(e1.trList)}">
							                                  ${e1.twa}
															</td>
															<td rowspan="${fn:length(e1.trList)}">
							                                   ${e1.lmt}
															</td>
														</c:when>
														<c:otherwise>
															<td rowspan="${fn:length(e1.trList)}">
							                                    ${e1.value}
															</td>
														</c:otherwise>
													</c:choose>
													<td rowspan="${fn:length(e1.trList)}">
														${e1.result}
													</td>
												</c:if>
											</tr>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">审&nbsp;&nbsp;核&nbsp;人:</label></td>
							<td class="width-35">
								${vo.checkMan}
							</td>
							<td class="width-15 active"><label class="pull-right">审核时间:</label></td>
							<td class="width-35">
								${vo.checkTime}
							</td>
						</tr>
						 <tr>
							<td class="width-15 active"><label class="pull-right">审核结果:</label></td>
							<td>
								<c:choose>
									<c:when test="${logVo.audit=='1'}">
										通过
									</c:when>
									<c:when test="${logVo.audit=='-1'}">
										不通过
									</c:when>
									<c:otherwise>
										${logVo.audit}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="width-15 active"><label class="pull-right">审核意见:</label></td>
							<td>${logVo.msg}</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
<!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
<script type="text/javascript">
function fnOpenTemp(id){
	POBrowser.openWindow('${basePath}bus/itemTest/showTemp.do?id='+id,'width=1200px;height=800px;');
}
</script>
</body>
</html>
