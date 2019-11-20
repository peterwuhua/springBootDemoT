<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
.form-control{
	padding: 4px;
}
.table > tbody > tr > td{
	padding: 2px;
	text-align: center;
}
.table > thead > tr > th{
	text-align: center;
}
.val_tb>tr>td>input{
	height: 100%;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="update4Item.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<c:forEach items="${timList}" var="e" varStatus="v">
						<table class="table table-bordered">
							<tr>
								<td style="width: 10%;text-align: right;" class="active">检测项目：</td>
								<td style="width: 15%;">
									${e.itemName}
									<input name="timList[${v.index}].id" value="${e.id}" type="hidden" />
									<input id="itemId_${v.index}" name="timList[${v.index}].itemId" value="${e.itemId}" type="hidden" />
								</td>
								<td style="width: 10%;text-align: right;" class="active">分&nbsp;析&nbsp;&nbsp;人：</td>
								<td style="width: 15%;">
									${e.testMan}
								</td>
								<td style="width: 10%;text-align: right;" class="active">校&nbsp;验&nbsp;&nbsp;人：</td>
								<td style="width: 15%;">
									${e.checkMan}
								</td>
								<td style="width: 10%;text-align: right;" class="active">检测时间：</td>
								<td style="width: 15%;">
									${e.testTime}
								</td>
							</tr>
							<tr>
								<td style="text-align: right;" class="active">检测方法：</td>
								<td>
									${e.methodName}
								</td>
								<td style="text-align: right;" class="active">检测仪器：</td>
								<td>
									${e.appName}
								</td>
								<td style="text-align: right;" class="active">检&nbsp;出&nbsp;&nbsp;限：</td>
								<td>
									${e.limitLine}
								</td>
								<td style="text-align: right;" class="active">温&nbsp;湿&nbsp;&nbsp;度：</td>
								<td>
									${e.wd} ℃ 
									${e.sd} % 
								</td>
							</tr>
							<tr>
								<td style="text-align: right;" class="active">评价标准：</td>
								<td>
									${e.standName}
								</td>
								<td style="text-align: right;" class="active">限&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值：</td>
								<td >
									<c:choose>
										<c:when test="${e.limited=='a'}">
											MAC：${e.mac} ${e.unit}
											<input type="hidden" id="mac_${v.index}" class="form-control" value="${e.mac}">
										</c:when>
										<c:when test="${e.limited=='b'}">
											 <label>  
											 	PC-TWA：${e.twa} ${e.unit}
											    <input type="hidden" id="twa_${v.index}" class="form-control" value="${e.twa}">
											 </label>
											 <label>
											 	PC-STEL：${e.stel} ${e.unit}
											 	<input type="hidden" id="stel_${v.index}" class="form-control" value="${e.stel}">
											 </label>
										</c:when>
										<c:when test="${e.limited=='c'}">
											<label>  
												PC-TWA：${e.twa} ${e.unit}
											  <input type="hidden" id="twa_${v.index}" class="form-control" value="${e.twa}">
											</label>
											<label> 
												超限倍数：${e.lmt}
												<input type="hidden" id="lmt_${v.index}" class="form-control" value="${e.lmt}"> 
											</label>
										</c:when>
										<c:when test="${e.limited!=''&&e.limited!=null}">
												${e.limited} ${e.unit}
										</c:when> 
										<c:otherwise>
											<font color="red">限值未找到</font>
										</c:otherwise>
									</c:choose>
								</td>
								<td style="text-align: right;" class="active">计量单位：</td>
								<td>
									${e.unit}
								</td>
								<td style="text-align: right;" class="active">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
								<td>
									${e.remark}
								</td>
							</tr>
							<tr>
								<td colspan="8">
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
										<c:otherwise>
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
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</table>
					</c:forEach>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../include/js.jsp"%>
<script src="${basePath}h/js/plugins/suggest/bootstrap-suggest.min.js"></script>
<script type="text/javascript">
  
</script>
</body>
</html>
