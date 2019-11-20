<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<!-- Sweet Alert -->
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>

<style type="text/css">
 
#jcTb .input-group-btn .btn{
	padding: 6px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="update4Data.do?isCommit=0" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<input name="ids" value="${vo.ids}" type="hidden" />
					<input name="taskVo.id" value="${vo.taskVo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
						<tr>
							<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
							<td class="width-35">
								<a href="javascript:void(0);" onclick="fnTask('${vo.taskVo.id}','${vo.taskVo.no}');">${vo.taskVo.no}</a>
							</td>
							<td class="width-15 active"><label class="pull-right">受检单位:</label></td>
							<td class="width-35">
								${vo.taskVo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测性质:</label></td>
							<td class="width-35">
								${vo.taskVo.taskType}
							</td>
							<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
							<td class="width-35">
								${vo.taskVo.sampName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测项目:</label></td>
							<td class="width-35">
								${vo.itemName}
							</td>
							<td class="width-15 active"><label class="pull-right">计量单位:</label></td>
							<td class="width-35">
	                         	${vo.unit}
							</td>
						</tr>
						<c:if test="${vo.taskVo.pj=='是'}">
							<tr>
								<td class="width-15 active"><label class="pull-right">评价标准:</label></td>
								<td class="width-35">
									${vo.standName}
								</td>
								<td class="width-15 active"><label class="pull-right">标&nbsp;准&nbsp;&nbsp;值:</label></td>
								<td class="width-35">
									<c:choose>
										<c:when test="${vo.limited=='a'}">
											MAC：${vo.mac} ${vo.unit}
											<input type="hidden" id="mac" class="form-control" value="${vo.mac}">
										</c:when>
										<c:when test="${vo.limited=='b'}">
											 <label>  
											 	PC-TWA：${vo.twa} ${vo.unit}
											    <input type="hidden" id="twa" class="form-control" value="${vo.twa}">
											 </label>
											 <label>
											 	PC-STEL：${vo.stel} ${vo.unit}
											 	<input type="hidden" id="stel" class="form-control" value="${vo.stel}">
											 </label>
										</c:when>
										<c:when test="${vo.limited=='c'}">
											<label>  
												PC-TWA：${vo.twa} ${vo.unit}
											  <input type="hidden" id="twa" class="form-control" value="${vo.twa}">
											</label>
											<label> 
												超限倍数：${vo.lmt}
												<input type="hidden" id="lmt" class="form-control" value="${vo.lmt}"> 
											</label>
										</c:when>
										<c:when test="${vo.limited!=''&&vo.limited!=null}">
												${vo.limited} ${vo.unit}
										</c:when> 
										<c:otherwise>
											<font color="red">限值未找到</font>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
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
							<td class="width-15 active"><label class="pull-right">使用仪器:</label></td>
							<td class="width-35">
								${vo.appName}
							</td>
							<td class="width-15 active"><label class="pull-right">标准物质:</label></td>
							<td class="width-35">
	                         	${vo.stName}
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
						<tfoot>
							<tr>
								<td colspan="7"><b>检测结果</b></td>
							</tr>
						</tfoot>
					</table>
					<c:choose>
						<c:when test="${vo.itemName.indexOf('超高频辐射')>=0}">
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
										<th colspan="4" style="text-align: center;">测量结果（${vo.itemType}）</th>
										<th rowspan="2" width="10%">单项判定</th>
									</tr>
									<tr>
										<th style="text-align: center;width: 80px;">1</th>
										<th style="text-align: center;width: 80px;">2</th>
										<th style="text-align: center;width: 80px;">3</th>
										<th style="text-align: center;width: 80px;">均值</th>
									</tr>
								</thead>
								<tbody class="val_tb"  id="jcTb">
									<c:forEach items="${vo.itemList}" var="it" varStatus="iv">
										<tr>
											<td>
												${it.pointVo.room}
												<input type="hidden" name="itemList[${iv.index}].id" value="${it.id}">
												<input type="hidden" name="itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
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
						<c:when test="${vo.itemName.indexOf('高频电磁场')>=0}">
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
								<tbody class="val_tb"  id="jcTb">
									<c:forEach items="${vo.itemList}" var="it" varStatus="iv">
										<tr>
											<td rowspan="2">
												${it.pointVo.room}
												<input type="hidden" name="itemList[${iv.index}].id" value="${it.id}">
												<input type="hidden" name="itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
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
						<c:when test="${vo.itemName.indexOf('工频电场')>=0}">
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
								<tbody class="val_tb"  id="jcTb">
									<c:forEach items="${vo.itemList}" var="it" varStatus="iv">
										<tr>
											<td>
												${it.pointVo.room}
												<input type="hidden" name="itemList[${iv.index}].id" value="${it.id}">
												<input type="hidden" name="itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
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
						<c:when test="${vo.itemName.indexOf('激光辐射')>=0}">
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
										<th colspan="5" style="text-align: center;">测量结果（${vo.itemType}）</th>
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
								<tbody class="val_tb"  id="jcTb">
									<c:forEach items="${vo.itemList}" var="it" varStatus="iv">
										<tr>
											<td>
												${it.pointVo.room}
												<input type="hidden" name="itemList[${iv.index}].id" value="${it.id}">
												<input type="hidden" name="itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
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
						<c:when test="${vo.itemName.indexOf('微波辐射')>=0}">
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
								<tbody class="val_tb" id="jcTb">
									<c:forEach items="${vo.itemList}" var="it" varStatus="iv">
										<tr>
											<td>
												${it.pointVo.room}
												<input type="hidden" name="itemList[${iv.index}].id" value="${it.id}">
												<input type="hidden" name="itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
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
						<c:when test="${vo.itemName.indexOf('紫外辐射')>=0}">
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
								<tbody class="val_tb"  id="jcTb">
									<c:forEach items="${vo.itemList}" var="it" varStatus="iv">
										<tr>
											<td>
												${it.pointVo.room}
												<input type="hidden" name="itemList[${iv.index}].id" value="${it.id}">
												<input type="hidden" name="itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
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
						<c:when test="${vo.itemName.indexOf('高温')>=0}">
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
								<tbody class="val_tb"  id="jcTb">
									<c:forEach items="${vo.itemList}" var="it" varStatus="iv">
										<tr>
											<td rowspan="3">
												${it.pointVo.room}
												<input type="hidden" name="itemList[${iv.index}].id" value="${it.id}">
												<input type="hidden" name="itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
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
						<c:when test="${vo.itemName.indexOf('噪声')>=0}">
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
								<tbody class="val_tb"  id="jcTb">
									<c:forEach items="${vo.itemList}" var="it" varStatus="iv">
										<tr>
											<td>
												${it.pointVo.room}
												<input type="hidden" name="itemList[${iv.index}].id" value="${it.id}">
												<input type="hidden" name="itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
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
						<c:when test="${vo.itemName.indexOf('手传振动')>=0}">
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
								<tbody class="val_tb"  id="jcTb">
									<c:forEach items="${vo.itemList}" var="it" varStatus="iv">
										<tr>
											<td>
												${it.pointVo.room}
												<input type="hidden" name="itemList[${iv.index}].id" value="${it.id}">
												<input type="hidden" name="itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
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
						<c:when test="${vo.itemName.indexOf('照度')>=0}">
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
								<tbody class="val_tb"  id="jcTb">
									<c:forEach items="${vo.itemList}" var="it" varStatus="iv">
										<tr>
											<td>
												${it.pointVo.room}
												<input type="hidden" name="itemList[${iv.index}].id" value="${it.id}">
												<input type="hidden" name="itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
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
						<c:when test="${vo.itemName.indexOf('空气比释动能率')>=0}">
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
								<tbody class="val_tb"  id="jcTb">
									<c:forEach items="${vo.itemList}" var="it" varStatus="iv">
										<tr>
											<td>
												${it.pointVo.room}
												<input type="hidden" name="itemList[${iv.index}].id" value="${it.id}">
												<input type="hidden" name="itemList[${iv.index}].trVo.id" value="${it.trVo.id}">
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
						<c:when test="${vo.itemName.indexOf('粉尘')>=0}">
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
								<tbody class="val_tb" id="jcTb">
									<c:forEach items="${vo.itemList}" var="it" varStatus="iv">
										<c:forEach  items="${it.trList}" var="r1" varStatus="rs">
											<tr>
												<c:if test="${rs.index==0}">
													<td  rowspan="${fn:length(it.trList)}">
														${it.pointVo.room}
														<input type="hidden" name="itemList[${iv.index}].id" value="${it.id}">
													</td>
													<td rowspan="${fn:length(it.trList)}">
														${it.pointVo.pointName}
													</td>
												</c:if>
												<td>
													${r1.sampVo.sampCode}
													 <input type="hidden"  name="itemList[${iv.index}].trList[${rs.index}].id" value="${r1.id}">
													 <input type="hidden" class="form-control" name="itemList[${iv.index}].trList[${rs.index}].sampVo.sampCode" value="${r1.sampVo.sampCode}">
												</td>
												<td>
													${r1.sampVo.workHours}
													<input type="hidden" class="form-control" name="itemList[${iv.index}].trList[${rs.index}].sampVo.workHours" value="${r1.sampVo.workHours}">
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
								<tbody id="jcTb">
									<c:forEach items="${vo.itemList}" var="e" varStatus="s">
										<c:forEach  items="${e.trList}" var="r" varStatus="rs">
											<tr id="${e.id}" key="${s.index}">
												<c:if test="${rs.index==0}">
													<td align="center" rowspan="${fn:length(e.trList)}">
														${s.index+1}
														<input type="hidden" value="${e.id}" name="itemList[${s.index}].id" />
													</td>
													<td align="center" rowspan="${fn:length(e.trList)}">
														${e.pointVo.sampName}
													</td>
													<td align="center" rowspan="${fn:length(e.trList)}">
														${e.cyDate}
													</td>
												</c:if>
												<td>
													${r.sampVo.sampCode}
													 <input type="hidden"  name="itemList[${s.index}].trList[${rs.index}].id" value="${r.id}">
				                                     <input type="hidden" id="sampCode_${s.index}_${rs.index}" class="form-control" value="${r.sampVo.sampCode}">
												</td>
												<td>
				                                    <input type="text" id="value_${s.index}_${rs.index}" name="itemList[${s.index}].trList[${rs.index}].value" class="form-control required"  onkeyup="keygo(event,this)" validate="required" value="${r.value}"  onchange="validtValue(this)">
												</td>
												<c:if test="${rs.index==0}">
													<c:choose>
														<c:when test="${vo.limited=='a'}">
															<td rowspan="${fn:length(e.trList)}">
							                                  ${e.mac}
															</td>
														</c:when>
														<c:when test="${vo.limited=='b'}">
															<td rowspan="${fn:length(e.trList)}">
							                                   ${e.twa}
															</td>
															<td rowspan="${fn:length(e.trList)}">
							                                   ${e.stel}
															</td>
														</c:when>
														<c:when test="${vo.limited=='c'}">
															<td rowspan="${fn:length(e.trList)}">
							                                    ${e.twa}
															</td>
															<td rowspan="${fn:length(e.trList)}">
							                                    ${e.lmt}
															</td>
														</c:when>
														<c:otherwise>
															<td rowspan="${fn:length(e.trList)}">
							                                    ${e.value}
															</td>
														</c:otherwise>
													</c:choose>
													<td rowspan="${fn:length(e.trList)}">
														${it.result}
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
							<td class="active"><label class="pull-right">原始记录:</label></td>
							<td colspan="3">
								 <div class="input-group-btn">
										<c:if test="${vo.filePath!='' && vo.filePath!=null}">
											<button tabindex="-1" class="btn btn-default" type="button" onclick="fnOpenTemp(this)">在线查看</button>
										</c:if>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">上传附件:</label></td>
							<td colspan="3" >
								<div id="removeFile">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
										<div style="float: left; margin-right: 10px;">
											<c:choose>
												<c:when test="${fn:contains(e.fileName,'.doc')||fn:contains(e.fileName,'.xls')||fn:contains(e.fileName,'.ppt')||fn:contains(e.fileName,'.pdf')}">
													<a href="javascript:void(0);" onclick="fnShowFile('${e.id}');" class="btn btn-w-m btn-info">${e.fileName}</a>
												</c:when>
												<c:when test="${fn:contains(e.fileName,'.jpg')||fn:contains(e.fileName,'.png')||fn:contains(e.fileName,'.jpeg')||fn:contains(e.fileName,'.gif')}">
													<a href="javascript:openImg('${e.filePath}','${e.fileName}');" class="btn btn-w-m btn-info">${e.fileName}</a>
												</c:when>
												<c:otherwise>
													<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
												</c:otherwise>
											</c:choose>
										</div>
									</c:forEach>
								</div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">检&nbsp;测&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								${vo.testMan}
							</td>
							<td class="active"><label class="pull-right">检测时间:	</label></td>
							<td>
								${vo.testTime} ~
								${vo.testEndTime}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.testMsg}
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../include/js.jsp"%>
<!-- Sweet alert -->
<%@ include file="../../../sys/open/open_img.jsp"%>
</body>
<script>
$('input[type="file"]').prettyFile();
function fnShowFile(id){
	POBrowser.openWindow('${basePath}sys/files/showFile.do?id='+id,'width=1200px;height=800px;');
}
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
</script>
<script type="text/javascript">
function fnOpenTemp(obj){
	POBrowser.openWindow('${basePath}bus/itemTest/showTemp.do?id=${vo.id}','width=1200px;height=800px;');
	$(obj).removeClass('btn-default')
	$(obj).addClass('btn-info')
}
 
</script>
 
</html>
