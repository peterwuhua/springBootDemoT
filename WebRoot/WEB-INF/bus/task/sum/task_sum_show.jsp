<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend {
	border-bottom: 0px;
	width: 80px;
	margin-bottom: 0px;
	font-size: 14px !important;
}

.funTable{
	margin-bottom: 0px;
}
.table-bordered > thead > tr > td{
	background-color: white;
}
.data_tb> tr > td,.val_tb> tr > td{
	text-align: center;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  style="margin-bottom: -1px;">
						<tr>
							<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
							<td class="width-35">
								<div class="input-group">
									${vo.no}
								</div>
							</td>
							<td class="width-15 active"><label class="pull-right">委托单位:</label></td>
							<td class="width-35">
								${vo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">样品类别:</label></td>
							<td class="width-35">
								${vo.sampTypeName}
							</td>
							<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
							<td class="width-35">
								${vo.sampName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测性质:</label></td>
							<td class="width-35">
								${vo.taskType}
							</td>
							<td class="active"><label class="pull-right ">要求完成日期:</label></td>
							<td>
								${vo.finishDate}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">${vo.remark}</td>
						</tr>
						<tr>
							<td colspan="4"  class="active">
								<b>检测项目结果汇总</b>
							</td>
						</tr>
					</table>
					 <c:forEach items="${vo.timList}" var="e"  varStatus="v">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
							<tr class="jcTb ${e.color}">
								<td>
									<span class="pull-left" style="padding-top: 7px;">检测项目:${e.itemName}</span>
								</td>
							</tr>
							<c:if test="${e.status=='IT_03'}">
								<tr>
									<td>
										检测方法: ${e.methodName} 
										检测仪器: ${e.appName} 
										<c:if test="${e.limitLine !=null && e.limitLine !=''}">
											检出限:${e.limitLine}
										</c:if>
										<c:if test="${e.wd !=null && e.wd !=''}">
											温度:${e.wd} ℃
										</c:if>
										<c:if test="${e.sd !=null && e.sd !=''}">
											湿度: ${e.sd} %
										</c:if>
									</td>
								</tr>
								<c:if test="${fn:length(e.fileList)>0}">
									<tr>
										<td>
											附件信息:
											<c:forEach items="${e.fileList}" var="e1" varStatus="v1">
												 	<a href="download.do?filePath=${e1.filePath}&trueName=${e1.fileName}" >${e1.fileName}</a>
											 </c:forEach>
										</td>
									</tr>
								</c:if>
							</c:if>
						</table>
						<c:choose>
							<c:when test="${vo.sampType=='环境'}">
								<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 1px;">
									<thead>
										<tr>
											<th style="text-align: center;width: 50px;">序号</th>
											<th style="text-align: center;width: 80px;">样品名称</th>
											<th style="text-align: center;width: 80px;">采样日期</th>
											<th style="text-align: center;width: 80px;">样品编号</th>
											<c:choose>
												<c:when test="${e.st=='气'}">
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
									<tbody class="data_tb">
										<c:forEach items="${e.itemList}" var="e1" varStatus="s1">
											<tr>
												<td align="center">
													${s1.index+1}
												</td>
												<td align="center">
													${e1.trVo.sampVo.sampName}
												</td>
												<td align="center">
													${e1.cyDate}
												</td>
												<td>
													${e1.trVo.sampVo.sampCode}
												</td>
												<c:choose>
													<c:when test="${e.st=='气'}">
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
								<c:choose>
									<c:when test="${e.itemName.indexOf('超高频辐射')>=0}">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th rowspan="2" width="10%">
														车间
													</th>
													<th rowspan="2" width="10%">
														检测点位
													</th>
													<th rowspan="2" width="10%">
														波形分类
													</th>
													<th rowspan="2" width="10%">
														接触时间（h/d）
													</th>
													<th colspan="4" style="text-align: center;">测量结果（${e.itemType}）</th>
													<th rowspan="2" width="10%">单项判定</th>
												</tr>
												<tr>
													<th style="text-align: center;width: 80px;">1</th>
													<th style="text-align: center;width: 80px;">2</th>
													<th style="text-align: center;width: 80px;">3</th>
													<th style="text-align: center;width: 80px;">均值</th>
												</tr>
											</thead>
											<tbody class="val_tb">
												<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													<tr>
														<td>
															${it.pointVo.room}
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
														</td>
														<td>
															${it.pointVo.pointName}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.pl}
														</td>
														<td>
															${it.trVo.sampVo.workHours}
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v4}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v5}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v6}
														</td>
														<td>
															${it.value}
														</td>
														<td>
															${it.result}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:when test="${e.itemName.indexOf('高频电磁场')>=0}">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th rowspan="2" width="10%">
														车间
													</th>
													<th rowspan="2" width="10%">
														检测点位
													</th>
													<th rowspan="2" width="10%">
														设备工作频率<br>(f，MHz)
													</th>
													<th rowspan="2" width="10%">
														接触时间（h/d）
													</th>
													<th colspan="5" style="text-align: center;">测量结果</th>
													<th rowspan="2" width="10%">单项判定</th>
												</tr>
												<tr>
													<th style="text-align: center;width: 100px;">电磁场强度</th>
													<th style="text-align: center;width: 80px;">1</th>
													<th style="text-align: center;width: 80px;">2</th>
													<th style="text-align: center;width: 80px;">3</th>
													<th style="text-align: center;width: 80px;">均值</th>
												</tr>
											</thead>
											<tbody class="val_tb">
												<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													<tr>
														<td rowspan="2">
															${it.pointVo.room}
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
														</td>
														<td rowspan="2">
															${it.pointVo.pointName}
														</td>
														<td rowspan="2">
															${it.trVo.sampVo.recordVo.pl}
														</td>
														<td rowspan="2">
															${it.trVo.sampVo.workHours}
														</td>
														<td>
															电场强度(V/m)
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v4}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v5}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v6}
														</td>
														<td>
															${it.value}
														</td>
														<td rowspan="2">
															${it.result}
														</td>
													</tr>
													<tr>
														<td>
															磁场强度(A/m)
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v10}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v11}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v12}
														</td>
														<td>
															${it.value2}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:when test="${e.itemName.indexOf('工频电场')>=0}">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th rowspan="2" width="10%">
														车间
													</th>
													<th rowspan="2" width="10%">
														检测点位
													</th>
													<th rowspan="2" width="10%">
														接触时间（h/d）
													</th>
													<th colspan="4" style="text-align: center;">电场强度(kV/m)</th>
													<th rowspan="2" width="10%">单项判定</th>
												</tr>
												<tr>
													<th style="text-align: center;width: 80px;">1</th>
													<th style="text-align: center;width: 80px;">2</th>
													<th style="text-align: center;width: 80px;">3</th>
													<th style="text-align: center;width: 80px;">均值</th>
												</tr>
											</thead>
											<tbody class="val_tb">
												<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													<tr>
														<td>
															${it.pointVo.room}
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
														</td>
														<td>
															${it.pointVo.pointName}
														</td>
														<td>
															${it.trVo.sampVo.workHours}
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v4}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v5}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v6}
														</td>
														<td>
															${it.value}
														</td>
														<td>
															${it.result}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:when test="${e.itemName.indexOf('激光辐射')>=0}">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th rowspan="2" width="10%">
														车间
													</th>
													<th rowspan="2" width="10%">
														检测点位
													</th>
													<th rowspan="2" width="10%">
														光谱范围
													</th>
													<th rowspan="2" width="10%">
														波长(nm)
													</th>
													<th rowspan="2" width="10%">
														接触时间（h/d）
													</th>
													<th colspan="5" style="text-align: center;">测量结果（${e.itemType}）</th>
													<th rowspan="2" width="10%">单项判定</th>
												</tr>
												<tr>
													<th style="text-align: center;width: 100px;">测量部位</th>
													<th style="text-align: center;width: 80px;">1</th>
													<th style="text-align: center;width: 80px;">2</th>
													<th style="text-align: center;width: 80px;">3</th>
													<th style="text-align: center;width: 80px;">均值</th>
												</tr>
											</thead>
											<tbody class="val_tb">
												<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													<tr>
														<td>
															${it.pointVo.room}
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
														</td>
														<td>
															${it.pointVo.pointName}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.demo1}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.pl}
														</td>
														<td>
															${it.trVo.sampVo.workHours}
														</td>
														<td>
															${it.trVo.sampVo.fcType}
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v4}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v5}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v6}
														</td>
														<td>
															${it.value}
														</td>
														<td>
															${it.result}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:when test="${e.itemName.indexOf('微波辐射')>=0}">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th rowspan="2" width="10%">
														车间
													</th>
													<th rowspan="2" width="10%">
														检测点位
													</th>
													<th rowspan="2" width="10%">
														波型分类
													</th>
													<th rowspan="2" width="10%">
														接触时间（h/d）
													</th>
													<th rowspan="2" width="10%">
														测量部位
													</th>
													<th colspan="4" style="text-align: center;">测量结果(μW/cm2)</th>
													<th rowspan="2" width="10%">单项判定</th>
												</tr>
												<tr>
													<th style="text-align: center;width: 80px;">1</th>
													<th style="text-align: center;width: 80px;">2</th>
													<th style="text-align: center;width: 80px;">3</th>
													<th style="text-align: center;width: 80px;">均值</th>
												</tr>
											</thead>
											<tbody class="val_tb">
												<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													<tr>
														<td>
															${it.pointVo.room}
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
														</td>
														<td>
															${it.pointVo.pointName}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.pl}
														</td>
														<td>
															${it.trVo.sampVo.workHours}
														</td>
														<td>
															${it.trVo.sampVo.fcType}
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v4}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v5}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v6}
														</td>
														<td>
															${it.value}
														</td>
														<td>
															${it.result}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:when test="${e.itemName.indexOf('紫外辐射')>=0}">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th rowspan="2" width="10%">
														车间
													</th>
													<th rowspan="2" width="10%">
														检测点位
													</th>
													<th rowspan="2" width="10%">
														紫外光谱分类
													</th>
													<th colspan="5" style="text-align: center;">辐照度(μW/cm2)</th>
													<th rowspan="2" width="10%">单项判定</th>
												</tr>
												<tr>
													<th style="text-align: center;width: 100px;">测量部位</th>
													<th style="text-align: center;width: 80px;">EA(UVA)</th>
													<th style="text-align: center;width: 80px;">EB (UVB)</th>
													<th style="text-align: center;width: 80px;">EC(UVC)</th>
													<th style="text-align: center;width: 80px;">Eeff</th>
												</tr>
											</thead>
											<tbody class="val_tb">
												<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													<tr>
														<td>
															${it.pointVo.room}
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
														</td>
														<td>
															${it.pointVo.pointName}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.pl}
														</td>
														<td>
															${it.trVo.sampVo.fcType}
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v2}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v4}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v6}
														</td>
														<td>
															${it.value}
														</td>
														<td>
															${it.result}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:when test="${e.itemName.indexOf('高温')>=0}">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th rowspan="2" width="10%">
														车间
													</th>
													<th rowspan="2" width="10%">
														检测点位
													</th>
													<th rowspan="2" width="10%">
														测量时段
													</th>
													<th colspan="4" style="text-align: center;">结果(℃)</th>
													<th rowspan="2" width="10%">接触时间率(%)</th>
													<th rowspan="2" width="10%">体力劳动强度</th>
													<th rowspan="2" width="10%">单项判定</th>
												</tr>
												<tr>
													<th style="text-align: center;width: 80px;">湿球温度（头）</th>
													<th style="text-align: center;width: 80px;">黑球温度（腹）</th>
													<th style="text-align: center;width: 80px;">干球温度（踝）</th>
													<th style="text-align: center;width: 80px;">WBGT指数</th>
												</tr>
											</thead>
											<tbody class="val_tb">
												<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													<tr>
														<td rowspan="3">
															${it.pointVo.room}
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
														</td>
														<td rowspan="3">
															${it.pointVo.pointName}
														</td>
														<td>
															工作开始后0.5h
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v1}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v2}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v3}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.avg1}
														</td>
														<td rowspan="3">
															${it.trVo.sampVo.workHours}
														</td>
														<td rowspan="3">
															${it.trVo.sampVo.fcType}
														</td>
														<td rowspan="3">
															${it.result}
														</td>
													</tr>
													<tr>
														<td>
															工作中
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v4}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v5}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v6}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.avg2}
														</td>
													</tr>
													<tr>
														<td>
															工作结束前0.5h
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v7}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v8}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v9}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.avg3}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:when test="${e.itemName.indexOf('噪声')>=0}">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th rowspan="2" width="10%">
														车间
													</th>
													<th rowspan="2" width="10%">
														检测点位
													</th>
													<th rowspan="2" width="10%">
														接触时间（h/d）
													</th>
													<th colspan="3" style="text-align: center;">测量结果[dB(A)]</th>
													<th rowspan="2" width="10%">LAeq,Ti</th>
													<th rowspan="2" width="10%">LEX,8h</th>
													<th rowspan="2" width="10%">单项判定</th>
												</tr>
												<tr>
													<th style="text-align: center;width: 80px;">1</th>
													<th style="text-align: center;width: 80px;">2</th>
													<th style="text-align: center;width: 80px;">3</th>
												</tr>
											</thead>
											<tbody class="val_tb">
												<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													<tr>
														<td>
															${it.pointVo.room}
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
														</td>
														<td>
															${it.pointVo.pointName}
														</td>
														<td>
															${it.trVo.sampVo.workHours}
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v1}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v2}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v3}
														</td>
														<td>
															${it.value}
														</td>
														<td>
															${it.value2}
														</td>
														<td>
															${it.result}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:when test="${e.itemName.indexOf('手传振动')>=0}">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th rowspan="2" width="10%">
														车间
													</th>
													<th rowspan="2" width="10%">
														检测点位
													</th>
													<th rowspan="2" width="10%">
														接触时间（h/d）
													</th>
													<th colspan="5" style="text-align: center;">测量结果(m/s²)</th>
													<th rowspan="2" width="10%">单项判定</th>
												</tr>
												<tr>
													<th style="text-align: center;width: 80px;">1</th>
													<th style="text-align: center;width: 80px;">2</th>
													<th style="text-align: center;width: 80px;">3</th>
													<th style="text-align: center;width: 80px;">ahw(eq)</th>
													<th style="text-align: center;width: 80px;">ahw(4)</th>
												</tr>
											</thead>
											<tbody class="val_tb">
												<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													<tr>
														<td>
															${it.pointVo.room}
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
														</td>
														<td>
															${it.pointVo.pointName}
														</td>
														<td>
															${it.trVo.sampVo.workHours}
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v2}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v4}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v6}
														</td>
														<td>
															${it.value}
														</td>
														<td>
															${it.value2}
														</td>
														<td>
														${it.result}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:when test="${e.itemName.indexOf('照度')>=0}">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th rowspan="2" width="10%">
														车间
													</th>
													<th rowspan="2" width="10%">
														检测点位
													</th>
													<th rowspan="2" width="10%">
														测点高度
													</th>
													<th colspan="7" style="text-align: center;">测量结果(m/s²)（${e.itemType}）</th>
													<th rowspan="2" width="10%">单项判定</th>
												</tr>
												<tr>
													<th style="text-align: center;width: 80px;">1</th>
													<th style="text-align: center;width: 80px;">2</th>
													<th style="text-align: center;width: 80px;">3</th>
													<th style="text-align: center;width: 80px;">4</th>
													<th style="text-align: center;width: 80px;">5</th>
													<th style="text-align: center;width: 80px;">6</th>
													<th style="text-align: center;width: 80px;">均值</th>
												</tr>
											</thead>
											<tbody class="val_tb">
												<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													<tr>
														<td>
															${it.pointVo.room}
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
														</td>
														<td>
															${it.pointVo.pointName}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.pl}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v1}
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v2}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v3}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v4}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v5}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v6}
														</td>
														<td>
															${it.value}
														</td>
														<td>
															${it.result}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:when test="${e.itemName.indexOf('X射线')>=0}">
											<table class="table table-bordered">
												<thead>
													<tr>
														<th  width="10%">
															车间
														</th>
														<th width="10%">
															检测点位
														</th>
														<th  width="10%">
															接触时间
														</th>
														<th  width="10%">
															探伤机类别
														</th>
														<th style="text-align: center;">空气比释动能率（μGy·h-1）</th>
														<th  width="10%">单项判定</th>
													</tr>
												</thead>
												<tbody class="val_tb">
													<c:forEach items="${e.itemList}" var="it" varStatus="iv">
														<tr>
															<td>
																${it.pointVo.room}
																<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
																<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
															</td>
															<td>
																${it.pointVo.pointName}
															</td>
															<td>
																${it.trVo.sampVo.workHours}
															</td>
															<td>
																${it.trVo.sampVo.recordVo.demo1}
															</td>
															<td>
																${it.trVo.sampVo.recordVo.demo6}
															</td>
															<td>
																${it.result}
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:when>
									<c:when test="${e.itemName.indexOf('空气比释动能率')>=0}">
											<table class="table table-bordered">
												<thead>
													<tr>
														<th rowspan="2" width="10%">
															车间
														</th>
														<th rowspan="2" width="10%">
															检测点位
														</th>
														<th rowspan="2" width="10%">
															部位
														</th>
														<th colspan="6" style="text-align: center;">周围剂量当量率(µSv/h)</th>
														<th rowspan="2" width="10%">单项判定</th>
													</tr>
													<tr>
														<th style="text-align: center;width: 80px;">1</th>
														<th style="text-align: center;width: 80px;">2</th>
														<th style="text-align: center;width: 80px;">3</th>
														<th style="text-align: center;width: 80px;">4</th>
														<th style="text-align: center;width: 80px;">5</th>
														<th style="text-align: center;width: 80px;">均值</th>
													</tr>
												</thead>
												<tbody class="val_tb">
													<c:forEach items="${e.itemList}" var="it" varStatus="iv">
														<tr>
															<td>
																${it.pointVo.room}
																<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
																<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
															</td>
															<td>
																${it.pointVo.pointName}
															</td>
															<td>
																${it.trVo.sampVo.fcType}
															</td>
															<td>
																${it.trVo.sampVo.recordVo.v1}
															</td>
															 <td>
																${it.trVo.sampVo.recordVo.v2}
															</td>
															<td>
																${it.trVo.sampVo.recordVo.v3}
															</td>
															<td>
																${it.trVo.sampVo.recordVo.v4}
															</td>
															<td>
																${it.trVo.sampVo.recordVo.v5}
															</td>
															<td>
																${it.value}
															</td>
															<td>
																${it.result}
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:when>
										<c:when test="${e.itemName.indexOf('粉尘')>=0}">
											<table class="table table-bordered">
												<thead>
													<tr>
														<th >
															车间
														</th>
														<th >
															检测点位
														</th>
														<th>
															样品编号
														</th>
														<th>
															接触时间
														</th>
														<th>
															采样体积
														</th>
														<th style="text-align: center;">采样前滤膜质量(mg)</th>
													</tr>
												</thead>
												<tbody class="val_tb">
													<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													<c:forEach  items="${it.trList}" var="r1" varStatus="rs">
														<tr>
															<c:if test="${rs.index==0}">
																<td  rowspan="${fn:length(it.trList)}">
																	${it.pointVo.room}
																</td>
																<td rowspan="${fn:length(it.trList)}">
																	${it.pointVo.pointName}
																</td>
															</c:if>
															<td>
																${r1.sampVo.sampCode}
															</td>
															<td>
																${r1.sampVo.workHours}
															</td>
															<td>
																${r1.sampVo.recordVo.demo2}
															</td>
															<td>
																${r1.sampVo.recordVo.v1}
															</td>
														</tr>
													</c:forEach>
													</c:forEach>
												</tbody>
											</table>
										</c:when>
									<c:when test="${e.type=='1'}">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th width="10%">
														车间
													</th>
													<th width="10%">
														检测点位
													</th>
													<th width="10%">
														检测日期
													</th>
													<th style="text-align: center;width: 80px;">1</th>
													<th style="text-align: center;width: 80px;">2</th>
													<th style="text-align: center;width: 80px;">3</th>
													<c:choose>
														<c:when test="${e.limited=='a'}">
															<th style="text-align: center;width: 80px;">Cmac</th>
														</c:when>
														<c:when test="${e.limited=='b'}">
															<th style="text-align: center;width: 80px;">Cstel</th>
															<th style="text-align: center;width: 80px;">Ctwa</th>
														</c:when>
														<c:when test="${e.limited=='c'}">
															<th style="text-align: center;width: 80px;">Ctwa</th>
															<th style="text-align: center;width: 80px;">超限倍数</th>
														</c:when>
														<c:otherwise>
															<th style="text-align: center;width: 80px;">均值</th>
														</c:otherwise>
													</c:choose>
													<th width="10%">
														单项判定
													</th>
												</tr>
											</thead>
											<tbody class="val_tb">
												<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													<tr>
														<td>
															${it.pointVo.room}
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
															<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
														</td>
														<td>
															${it.pointVo.pointName}
														</td>
														<td>
															${it.cyDate}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v1}
														</td>
														 <td>
															${it.trVo.sampVo.recordVo.v2}
														</td>
														<td>
															${it.trVo.sampVo.recordVo.v3}
														</td>
														<c:choose>
															<c:when test="${e.limited=='a'}">
																<td>
																	${it.mac}
																</td>
															</c:when>
															<c:when test="${e.limited=='b'}">
																<td>
																	${it.stel}
																</td>
																<td>
																	${it.twa}
																</td>
															</c:when>
															<c:when test="${e.limited=='c'}">
																<td>
																	${it.twa}
																</td>
																<td>
																	${it.lmt}
																</td>
															</c:when>
															<c:otherwise>
																<td>
																	${it.value}
																</td>
															</c:otherwise>
														</c:choose>
														<td>
															${it.result}
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:when>
									<c:otherwise>
										<table class="table table-bordered" style="margin-bottom: 1px;">
											<thead>
												<tr>
													<td style="text-align: center;width: 50px;">序号</td>
													<td style="text-align: center;width: 10%;">检测岗位</td>
													<td style="text-align: center;width: 10%;">检测点</td>
													<td style="text-align: center;width: 10%;">采样日期</td>
													<td style="text-align: center;width: 80px;">接触时间</td>
													<td style="text-align: center;width: 130px;">样品编号</td>
													<td style="text-align: center;">检测值</td>
													<c:choose>
														<c:when test="${e.limited=='a'}">
															<td style="text-align: center;width: 80px;">Cmac</td>
														</c:when>
														<c:when test="${e.limited=='b'}">
															<td style="text-align: center;width: 80px;">Ctwa</td>
															<td style="text-align: center;width: 80px;">Cstel</td>
														</c:when>
														<c:when test="${e.limited=='c'}">
															<td style="text-align: center;width: 80px;">Ctwa</td>
															<td style="text-align: center;width: 80px;">超限倍数</td>
														</c:when>
														<c:otherwise>
															<td style="text-align: center;width: 80px;">均值</td>
														</c:otherwise>
													</c:choose>
													<td style="text-align: center;width: 80px;">单项判定</td>
												</tr>
											</thead>
											<tbody class="data_tb">
												<c:forEach items="${e.itemList}" var="e1" varStatus="s1">
													<c:forEach  items="${e1.trList}" var="r2" varStatus="rs2">
														<tr>
															<c:if test="${rs2.index==0}">
																<td align="center" rowspan="${fn:length(e1.trList)}">
																	${s1.index+1}
																</td>
																<td align="center" rowspan="${fn:length(e1.trList)}">
																	${e1.pointVo.room}
																</td>
																<td align="center" rowspan="${fn:length(e1.trList)}">
																	${e1.pointVo.pointName}
																</td>
																<td align="center" rowspan="${fn:length(e1.trList)}">
																	${e1.cyDate}
																</td>
																<td align="center" rowspan="${fn:length(e1.trList)}">
																	${e1.pointVo.workHours}
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
																	<c:when test="${e.limited=='a'}">
																		<td rowspan="${fn:length(e1.trList)}">
										                                   ${e1.mac}
																		</td>
																	</c:when>
																	<c:when test="${e.limited=='b'}">
																		<td rowspan="${fn:length(e1.trList)}">
										                                    ${e1.twa}
																		</td>
																		<td rowspan="${fn:length(e1.trList)}">
										                                   ${e1.stel}
																		</td>
																	</c:when>
																	<c:when test="${e.limited=='c'}">
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
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-top: 20px;">
						<tr>
							<td class="active"><label class="pull-right">原始记录:</label></td>
							<td colspan="3">
		                  	 	<c:forEach items="${vo.tempList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
								 		<button class="btn btn-info" type="button" onclick="fnOpenTemp('${e.busId}');">${e.fileName}</button>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:</label></td>
							<td colspan="3">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">审&nbsp;核&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								${vo.sumUser}
							</td>
							<td class="width-15 active"><label class="pull-right">审核时间:</label></td>
							<td class="width-35">
								${vo.sumDate}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">审核结果:</label></td>
							<td>
								 ${logVo.audit}
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
function fnTask(id,no){
	parent.layer.open({
		title:'任务【'+no+'】',	
		type: 2,
		area: ['1000px','85%'],
		fix: false, //不固定
		maxmin: true,
		content: '/bus/task/show.do?id='+id
	});
}
function fnShowWord(){
	POBrowser.openWindow('${basePath}bus/taskData/showBugFile.do?id=${vo.id}','width=1200px;height=800px;');
}
function itemToggle(obj){
	var tt=$(obj).html();
	if(tt.indexOf('展开')>=0){
		$(obj).html('<i class="fa fa-chevron-down" aria-hidden="true"></i>闭合');
		$('#item_table').show()
	}else{
		$(obj).html('<i class="fa fa-chevron-up" aria-hidden="true"></i>展开');
		$('#item_table').hide()
	}
}
function envToggle(obj){
	var tt=$(obj).html();
	if(tt.indexOf('展开')>=0){
		$(obj).html('<i class="fa fa-chevron-down" aria-hidden="true"></i>闭合');
		$('#env_table').show()
	}else{
		$(obj).html('<i class="fa fa-chevron-up" aria-hidden="true"></i>展开');
		$('#env_table').hide()
	}
}
function funToggle(obj){
	var tt=$(obj).html();
	if(tt.indexOf('展开')>=0){
		$(obj).html('<i class="fa fa-chevron-down" aria-hidden="true"></i>闭合');
		$('#fun_table').show()
	}else{
		$(obj).html('<i class="fa fa-chevron-up" aria-hidden="true"></i>展开');
		$('#fun_table').hide()
	}
}
</script>
</body>
</html>
