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
<body >
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
									<select name="timList[${v.index}].testManId"  class="form-control  required" validate="required" onchange="checkThis(this);">
										<option value="">-请选择-</option>
										<c:forEach items="${userList}" var="e1">
											<c:choose>
												<c:when test="${e.testManId==e1.id}">
													<option value="${e1.id}" selected="selected">${e1.userName}</option>
												</c:when>
												<c:otherwise>
													<option value="${e1.id}">${e1.userName}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<input type="hidden" name="timList[${v.index}].testMan" value="${e.testMan}" >
								</td>
								<td style="width: 10%;text-align: right;" class="active">校&nbsp;验&nbsp;&nbsp;人：</td>
								<td style="width: 15%;">
									<select name="timList[${v.index}].checkManId"  class="form-control" onchange="checkThis(this);">
										<option value="">-请选择-</option>
										<c:forEach items="${userList}" var="e1">
											<c:choose>
												<c:when test="${e.checkManId==e1.id}">
													<option value="${e1.id}" selected="selected">${e1.userName}</option>
												</c:when>
												<c:otherwise>
													<option value="${e1.id}">${e1.userName}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<input type="hidden" name="timList[${v.index}].checkMan" value="${e.checkMan}" >
								</td>
								<td style="width: 10%;text-align: right;" class="active">检测时间：</td>
								<td style="width: 15%;">
									<input type="text" name="timList[${v.index}].testTime" class="form-control dtISO" value="${e.testTime}">
								</td>
							</tr>
							<tr>
								<td style="text-align: right;" class="active">检测方法：</td>
								<td>
									<input type="text" id="methodName_${v.index}" name="timList[${v.index}].methodName" class="form-control" value="${e.methodName}"  title="${e.methodName}" onclick="chooseMethod(${v.index})">
									<input type="hidden" id="methodId_${v.index}" name="timList[${v.index}].methodId" class="form-control" value="${e.methodId}">
								</td>
								<td style="text-align: right;" class="active">检测仪器：</td>
								<td>
									<input type="text" id="appName_${v.index}" name="timList[${v.index}].appName" class="form-control" value="${e.appName}" title="${e.appName}"  onclick="chooseApp(${v.index})">
									<input type="hidden" id="appId_${v.index}" name="timList[${v.index}].appId" class="form-control" value="${e.appId}">
								</td>
								<td style="text-align: right;" class="active">检&nbsp;出&nbsp;&nbsp;限：</td>
								<td>
									<input type="text" id="limitLine_${v.index}" name="timList[${v.index}].limitLine" class="form-control" value="${e.limitLine}">
								</td>
								<td style="text-align: right;" class="active">温&nbsp;湿&nbsp;&nbsp;度：</td>
								<td>
									<div class="input-group date">
									<input style="width: 49%" type="text" name="timList[${v.index}].wd" class="form-control" value="${e.wd}" placeholder="温度：℃">
									<input style="width: 49%"  type="text" name="timList[${v.index}].sd" class="form-control" value="${e.sd}" placeholder="湿度：%">
									</div>
								</td>
							</tr>
							<tr>
								<td style="text-align: right;" class="active">评价标准：</td>
								<td>
									<input type="text" id="standName_${v.index}" name="timList[${v.index}].standName" class="form-control" value="${e.standName}" title="${e.standName}" onclick="choosePstand(${v.index},'${e.sampTypeId}')">
									<input type="hidden" id="standId_${v.index}"  name="timList[${v.index}].standId" class="form-control" value="${e.standId}">
								</td>
								<td style="text-align: right;" class="active">限&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值：</td>
								<td style="text-align: left;">
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
									<a  class="btn btn-info btn-xs"  onclick="chooseLimit(${v.index},'${e.id}','${e.itemId}','${e.sampTypeName}')">选择</a>
								</td>
								<td style="text-align: right;" class="active">计量单位：</td>
								<td>
									<input type="text" name="timList[${v.index}].unit" class="form-control" value="${e.unit}">
								</td>
								<td style="text-align: right;" class="active">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
								<td>
									 <input type="text" name="timList[${v.index}].remark" class="form-control" value="${e.remark}" maxlength="128">
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
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value" value="${it.value}">
															</td>
															<td>
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
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
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value" value="${it.value}">
															</td>
															<td rowspan="2">
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
															</td>
														</tr>
														<tr>
															<td>
																磁场强度(A/m)
															</td>
															 <td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v10" value="${it.trVo.sampVo.recordVo.v10}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v11" value="${it.trVo.sampVo.recordVo.v11}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v12" value="${it.trVo.sampVo.recordVo.v12}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value2" value="${it.value2}">
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
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value" value="${it.value}">
															</td>
															<td>
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
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
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value" value="${it.value}">
															</td>
															<td>
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
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
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value" value="${it.value}">
															</td>
															<td>
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
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
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v2" value="${it.trVo.sampVo.recordVo.v2}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value" value="${it.value}">
															</td>
															<td>
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
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
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v1" value="${it.trVo.sampVo.recordVo.v1}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v2" value="${it.trVo.sampVo.recordVo.v2}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v3" value="${it.trVo.sampVo.recordVo.v3}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.avg1" value="${it.trVo.sampVo.recordVo.avg1}">
															</td>
															<td rowspan="3">
																${it.trVo.sampVo.workHours}
															</td>
															<td rowspan="3">
																${it.trVo.sampVo.fcType}
															</td>
															<td rowspan="3">
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
															</td>
														</tr>
														<tr>
															<td>
																工作中
															</td>
															 <td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.avg2" value="${it.trVo.sampVo.recordVo.avg2}">
															</td>
														</tr>
														<tr>
															<td>
																工作结束前0.5h
															</td>
															 <td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v7" value="${it.trVo.sampVo.recordVo.v7}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v8" value="${it.trVo.sampVo.recordVo.v8}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v9" value="${it.trVo.sampVo.recordVo.v9}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.avg3" value="${it.trVo.sampVo.recordVo.avg3}">
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
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v1" value="${it.trVo.sampVo.recordVo.v1}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v2" value="${it.trVo.sampVo.recordVo.v2}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v3" value="${it.trVo.sampVo.recordVo.v3}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value" value="${it.value}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value2" value="${it.value2}">
															</td>
															<td>
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
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
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v2" value="${it.trVo.sampVo.recordVo.v2}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value" value="${it.value}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value2" value="${it.value2}">
															</td>
															<td>
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
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
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v1" value="${it.trVo.sampVo.recordVo.v1}">
															</td>
															 <td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v2" value="${it.trVo.sampVo.recordVo.v2}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v3" value="${it.trVo.sampVo.recordVo.v3}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value" value="${it.value}">
															</td>
															<td>
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
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
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.demo1" value="${it.trVo.sampVo.recordVo.demo1}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.demo6" value="${it.trVo.sampVo.recordVo.demo6}">
															</td>
															<td>
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
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
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v1" value="${it.trVo.sampVo.recordVo.v1}">
															</td>
															 <td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v2" value="${it.trVo.sampVo.recordVo.v2}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v3" value="${it.trVo.sampVo.recordVo.v3}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value" value="${it.value}">
															</td>
															<td>
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
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
																	<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
																</td>
																<td rowspan="${fn:length(it.trList)}">
																	${it.pointVo.pointName}
																</td>
															</c:if>
															<td>
																${r1.sampVo.sampCode}
																 <input type="hidden"  name="timList[${v.index}].itemList[${iv.index}].trList[${rs.index}].id" value="${r1.id}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trList[${rs.index}].sampVo.workHours" value="${r1.sampVo.workHours}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trList[${rs.index}].sampVo.recordVo.demo2" value="${r1.sampVo.recordVo.demo2}">
															</td>
															<td>
																<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].trList[${rs.index}].sampVo.recordVo.v1" value="${r1.sampVo.recordVo.v1}">
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
															<c:choose>
																<c:when test="${e.limited=='a'}">
																	<td>
																		<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].mac" value="${it.mac}">
																	</td>
																</c:when>
																<c:when test="${e.limited=='b'}">
																	<td>
																		<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].stel" value="${it.stel}">
																	</td>
																	<td>
																		<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].twa" value="${it.twa}">
																	</td>
																</c:when>
																<c:when test="${e.limited=='c'}">
																	<td>
																		<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].twa" value="${it.twa}">
																	</td>
																	<td>
																		<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].lmt" value="${it.lmt}">
																	</td>
																</c:when>
																<c:otherwise>
																	<td>
																		<input type="text" class="form-control" name="timList[${v.index}].itemList[${iv.index}].value" value="${it.value}">
																	</td>
																</c:otherwise>
															</c:choose>
															<td>
																<select id="result_${v.index}_${iv.index}" name="timList[${v.index}].itemList[${iv.index}].result" class="form-control" >
																 	<c:choose>
																 		<c:when test="${it.result=='合格'}">
																 			<option value="">/</option>
																		 	<option value="合格" selected="selected">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:when>
																 		<c:when test="${it.result=='不合格'}">
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格" selected="selected">不合格</option>
																 		</c:when>
																 		<c:otherwise>
																 			<option value="">/</option>
																		 	<option value="合格">合格</option>
																		 	<option value="不合格">不合格</option>
																 		</c:otherwise>
																 	</c:choose>
																 </select>
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
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12" style="text-align: right;">
							<a class="btn btn-w-m btn-success" type="button" onclick="fnSubmit('update4Item.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('update4Item.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 完成</a>
							<a class="btn btn-w-m btn-white" href="javascript:close();" ><i class="fa fa-times" aria-hidden="true"></i> 关闭</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../include/js.jsp"%>
<script src="${basePath}h/js/plugins/suggest/bootstrap-suggest.min.js"></script>
<script type="text/javascript">

$('.dtISO').each(function(){
  laydate.render({
    elem: this,
    theme: 'molv',
    type:'datetime',
    format: 'yyyy-MM-dd HH:mm',
    calendar: true,
    trigger: 'click'
  });
});
var index = parent.layer.getFrameIndex(window.name); 
function fnSubmit(url){
	var t = $("#thisForm").FormValidate();
	if(t){
		$.ajax({ 
			url:url,
			dataType:"json",
			data:$('#thisForm').serialize(),
			type:"post",
			success: function(data){
				parent.layer.msg(data.message, {icon: 0,time: 3000})
				if("success"==data.status){
					parent.layer.close(index);
				}
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }  
		});
	}
}
function close(){
	parent.layer.close(index);
}
function copyVals(obj){
	obj=$(obj);
	var value0=obj.closest('td').find('input').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#samp_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(value0);
	});
}
function getData(arrStr){
	var data = {
	    	value: []
	    };
	var u=arrStr;
	var ut=u.split(';');
	for(var i=0;i<ut.length;i++){
		data.value.push({word: ut[i]});
	}
	return data;
}
$(function(){
	$('input[id^="limited_"]').each(function(n){
		var arrStr=$('#limitedStr_'+n).val();
		$('#limited_'+n).bsSuggest({
            indexId: 0, //data.value 的第几个数据，作为input输入框的内容
            indexKey: 0, //data.value 的第几个数据，作为input输入框的内容
            data: getData(arrStr),
            showBtn: true
        });
		if(arrStr.indexOf(';')==-1&&arrStr!=''&&$('#limited_'+n).val()==''){
			$('#limited_'+n).val(arrStr);
		}
	})
});
//标准物质 、 方法、 仪器
function chooseMethod(n){
	var methodId=$('#methodId_'+n).val();
	var itemId=$('#itemId_'+n).val();
	layer.open({
		title:'检测方法',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/itemMethod/showMethod.do?methodVo.id='+methodId+'&itemVo.id='+itemId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#methodId_'+n).val(data.id);
			$('#methodName_'+n).val(data.name);
			$('#limitLine_'+n).val(data.limitLine);
			if(data.id!='methodId'){
				$('#appId_'+n).val(null);
				$('#appName_'+n).val(null);
			}
			//保存方法
			$('#thisForm').attr('action','update4Item.do?isCommit=0');
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success('更新成功！', '');
		    	    location.reload();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	});
}
function chooseApp(n){
	var methodId=$('#methodId_'+n).val();
	if(methodId==''){
		layer.msg('请选择检测方法！', {icon: 0,time: 1000});
		return false;
	}
	var appId=$('#appId_'+n).val();
	layer.open({
		title:'检测仪器',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}bus/itemTest/showApp.do?methodId='+methodId+'&appId='+appId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#appId_'+n).val(data.id);
			$('#appName_'+n).val(data.name);
		}
	});
}
function validtValue(obj,index,itemId){
	var vl=parseFloat($(obj).val());
	$.ajax({ 
		url:"checkValue.do?pointId=${vo.id}&itemId="+itemId+'&value='+vl,
		dataType:"json",
		type:"post",
		async:false,
		success: function(data){
			 if(data.type=='error'){
				 $('#result_'+index).val('不合格');
			 }else{
				 $('#result_'+index).val('合格');
			 }
		},
		error:function(ajaxobj){  
			msg=ajaxobj;
	    }  
	});
}
function checkThis(obj){
	var t=$(obj).find('option:selected').text();
	$(obj).closest('td').find('input').val(t);
}
//测试标准
function choosePstand(n,sampTypeId){
	var standId=$('#standId_'+n).val();
	layer.open({
		title:'评价标准',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/pstandard/select.do?id='+standId+'&sampTypeId='+sampTypeId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#standId_'+n).val(data.id);
			$('#standName_'+n).val(data.name);
			//保存方法
			$('#thisForm').attr('action','update4Item.do?isCommit=0');
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success('更新成功！', '');
		    	    location.reload();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	});
}
//限值
function chooseLimit(n,timd,itemId,sampTypeName){
	var standId=$('#standId_'+n).val();
	if(standId==null||standId==''){
		layer.msg('请选择评价标准！', {icon: 0,time: 1000});
		return false;
	}
	layer.open({
		title:'评价标准',	
		type: 2,
		area: ['1000px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/pstandItem/select.do?sampType='+encodeURI(sampTypeName)+'&standId='+standId+'&itemVo.id='+itemId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$.ajax({ 
				url:'update4Limited.do?id='+timd+'&uuid='+data.id,
				dataType:"json",
				type:"post",
				async:false,
				success: function(data){
					if(data.status=='success'){
			    	    parent.toastr.success('更新成功！', '');
			    	    location.reload();
			        }else{
			        	parent.toastr.error(data.message, '');
			        }
				},
				error:function(ajaxobj){  
					msg=ajaxobj;
			    }  
			});
		}
	});
}
</script>
</body>
</html>
