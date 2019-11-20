<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/css.jsp"%>
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
legend {
	border-bottom: 0px;
	width: 80px;
	margin-bottom: 0px;
	font-size: 14px !important;
}
.compTd{
	background-color: #ACE1AF;
}
.backTd{
	background-color: #FF6600!important;
}
.waitTd{
	background-color: #eee;
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
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><a>数据审核</a></li>
						<li><strong>编辑</strong></li>
					</ol>
				</h5>
			</div>
			<div class="ibox-content">
				<form method="post" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<input id="ids" name="ids" value="" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
						<tr>
							<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
							<td class="width-35">
								<a onclick="fnTask('${vo.id}','${vo.no}');">${vo.no}</a>
							</td>
							<td class="width-15 active"><label class="pull-right">受检单位:</label></td>
							<td class="width-35">
								${vo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测性质:</label></td>
							<td class="width-35">
								${vo.taskType}
							</td>
							<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
							<td class="width-35">
								${vo.sampName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">受理日期:</label></td>
							<td>
								${vo.date}
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
									<span class="pull-right">
                                       	当前状态：
										<c:if test="${e.status=='IT_03'}">已完成</c:if>
										<c:if test="${e.status!='IT_03'}">${e.status}</c:if>
										&nbsp;&nbsp;
                                        <input type="checkbox" name="${e.color}" class="${e.color}" value="${e.id}"/>
									</span>
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
						<c:if test="${e.status=='IT_03'}">
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
						</c:if>
					</c:forEach>
					<!-- <p>注：[<font style="background-color: #ACE1AF">绿色</font>]表示已完成；[<font style="background-color:#DDDDDD">灰色</font>]表示未完成；所有可操作数据选中为[<font style="background-color:#FF6600 ">红色</font>]；</p> -->
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-top: 20px;">
						<%-- <tr>
							<td class="width-15 active"><label class="pull-right">报告编制人员:</label></td>
							<td>
								<select class="form-control" id="makeUserId" name="makeUserId" onchange="fnSelect(this);">
									<option value="">-请选择-</option>
									<c:forEach items="${userList}" var="e" varStatus="v">
									 	 <c:choose>
									 	 	<c:when test="${vo.makeUserId==e.id}">
									 	 		<option value="${e.id}" selected="selected">${e.userVo.name}</option>
									 	 	</c:when>
									 	 	<c:otherwise>
									 	 		<option value="${e.id}">${e.userVo.name}</option>
									 	 	</c:otherwise>
									 	 </c:choose>
									 </c:forEach>
								</select>
								<input type="hidden" name="makeUser" id="makeUser" value="${vo.makeUser}">
							</td>
							<td></td>
							<td></td>
						</tr> --%>
						<tr>
							<td class="width-15 active"><label class="pull-right">审&nbsp;核&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								<input id="sumUserId" name="sumUserId" type="hidden" value="${vo.sumUserId}" /> 
								<input id="sumUser" name="sumUser" class="form-control" maxlength=32 placeholder="审核人" type="text" value="${vo.sumUser}" readonly="readonly" />
							</td>
							<td class="width-15 active"><label class="pull-right">审核时间:</label></td>
							<td class="width-35"><input id="sumDate" name="sumDate" class="form-control datetimeISO" maxlength=20 placeholder="审核时间" type="text" value="${vo.sumDate}" /></td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">审核意见:</label></td>
							<td colspan="3">
								<textarea rows="2" maxlength="128" class="form-control" id="sumMsg" name="sumMsg"></textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<!-- <button class="btn btn-w-m btn-danger" type="button" onclick="fnSubmitDel('update2Stop.do')"><i class="fa fa-close" aria-hidden="true"></i> 终止</button> -->
							<button class="btn btn-w-m btn-danger" type="button" onclick="fnSubmitBack('updateBack.do?isCommit=-1')"><i class="fa fa-check" aria-hidden="true"></i> 退回</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="fnSubmitGo('updateData.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 提交</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<!-- Sweet alert -->
	<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
    <script src="${basePath}h/js/jquery-ui-1.10.4.min.js"></script>
  <script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
<script type="text/javascript">
function fnOpenTemp(id){
	POBrowser.openWindow('${basePath}bus/itemTest/showTemp.do?id='+id,'width=1200px;height=800px;');
}
</script>
<script>
$(document).ready(function() {
	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green",
	});
});
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
function fnSubmitGo(url){
	var num=$('.waitTd').length;
	if(num>0){
		swal("部分项目未完成，不能提交！", "", "warning");
		return ;
	}
	swal({
        title: "您确定要提交该任务吗",
        text: "提交后将无法修改，请谨慎操作！",
        type: "success",
        showCancelButton: true,
        confirmButtonColor: "#1ab394",
        confirmButtonText: "确定",
        cancelButtonText: "取消"
    }, function () {
		$('#thisForm').attr('action',url);
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
		        backMainPage();
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
        swal("提交成功！", "", "success");
    });
}
function fnSubmitBack(url){
	var ids="";
	$('input[type="checkbox"]:checked').each(function(){
		ids+=$(this).val()+",";
	});
	if(ids==''){
		swal("请选择退回项目！", "", "warning");
		return ;
	}
	var idStr=$('.waitTd.backTd').length;
	if(idStr>0){
		swal("未完成项目不能进行退回操作！", "", "warning");
		return ;
	}
	$('#ids').val(ids);
	var sumMsg=$('#sumMsg').val();
	if(sumMsg==''){
		swal("请输入审核意见！", "", "warning");
		return ;
	}
	swal({
        title: "您确定要退回这些项目吗",
        text: "将会退回重新录入，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "退回",
        cancelButtonText: "取消"
    }, function () {
		$('#thisForm').attr('action',url);
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
	    	    location.reload();
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
        swal("退回成功！", "", "success");
    });
}
 
function fnSubmitDel(url){
	var ids="";
	$('input[type="checkbox"]:checked').each(function(){
		ids+=$(this).val()+",";
	});
	if(ids==''){
		swal("请选择需要终止的项目！", "", "warning");
		return ;
	}
	$('#ids').val(ids);
	var sumMsg=$('#sumMsg').val();
	if(sumMsg==''){
		swal("请输入意见！", "", "warning");
		return ;
	}
	swal({
        title: "您确定要终止这些项目吗",
        text: "终止操作是不可逆的，请谨慎操作！",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "终止",
        cancelButtonText: "取消"
    }, function () {
    	$('#thisForm').attr('action',url);
    	$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
	    	    location.reload();
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
        swal("终止成功！", "", "success");
    });
}
 
$(function(){
	 $('tr.jcTb').click(function(){
		var checkbox=$(this).find('input[type="checkbox"]')
		if(checkbox.prop("checked")){
			checkbox.prop("checked",false)
			 $(this).removeClass("backTd");
		}else{
			checkbox.prop("checked",true)
			 $(this).addClass("backTd");
		}
	 });
	/* $('#zkTb tr').click(function(){
		var checkbox=$(this).find('input[type="checkbox"]')
		if(checkbox.prop("checked")){
			checkbox.prop("checked",false)
			 $(this).removeClass("backTd");
		}else{
			checkbox.prop("checked",true)
			 $(this).addClass("backTd");
		}
	 });*/
})
function fnSelect(obj){
	var v=$(obj).find('option:selected').text();
	$(obj).closest('td').find('input').val(v)
}
</script>
</body>
</html>
