<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<!-- Sweet Alert -->
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
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
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>数据录入</a></li>
					<c:if test="${vo.type=='2'}">
						<li><strong>重测</strong></li>
					</c:if>
					<li><strong>编辑</strong>
						<c:choose>
							<c:when test="${vo.isBack=='Y'}">
								（<span style="color: red"> 退回原因：${logVo.msg}</span> ）
							</c:when>
							<c:when test="${vo.checkMsg!='' && vo.checkMsg!=null}">
								（<span style="color: red"> ${vo.checkMsg}</span> ）
							</c:when>
						</c:choose>
					</li>
				</ol>
			</div>
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
								<input id="itemName" name="itemName" class="form-control" type="text" value="${vo.itemName}"  type="hidden" readonly="readonly" /> 
								<input type="hidden" id="itemId" name="itemId" value="${vo.itemId}"/>
							</td>
							<td class="width-15 active"><label class="pull-right">计量单位:</label></td>
							<td class="width-35">
	                         	<input id="unit" name="unit" class="form-control" maxlength=20 type="text" value="${vo.unit}" />
							</td>
						</tr>
						<c:if test="${vo.taskVo.pj=='是'}">
							<tr>
								<td class="width-15 active"><label class="pull-right">评价标准:</label></td>
								<td class="width-35">
									<textarea id="standName" name="standName" class="form-control required" validate="required" onclick="choosePstand()">${vo.standName}</textarea>
									<input id="standId" name="standId" value="${vo.standId}" type="hidden" />
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
									<a  class="btn btn-info btn-xs" onclick="chooseLimit()">选择</a>
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测方法:</label></td>
							<td class="width-35">
								<textarea id="methodName" name="methodName" class="form-control required" validate="required" onclick="chooseMethod()">${vo.methodName}</textarea>
								<input id="methodId" name="methodId" value="${vo.methodId}" type="hidden" />
							</td>
							<td class="width-15 active"><label class="pull-right">检&nbsp;出&nbsp;&nbsp;限:</label></td>
							<td class="width-35">
								<textarea id="limitLine" name="limitLine" class="form-control" maxlength=64>${vo.limitLine}</textarea>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">使用仪器:</label></td>
							<td class="width-35">
								<textarea id="appName" name="appName" class="form-control "  onclick="chooseApp()">${vo.appName}</textarea>
								<input id="appId" name="appId" value="${vo.appId}" type="hidden" />
							</td>
							<td class="width-15 active"><label class="pull-right">标准物质:</label></td>
							<td class="width-35">
	                         	<textarea id="stName" name="stName" class="form-control" onclick="chooseSt()">${vo.stName}</textarea>
								<input id="stId" name="stId" value="${vo.stId}" type="hidden" />
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">温&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度:</label></td>
							<td class="width-35">
								<input id="wd" name="wd" class="form-control" placeholder="°C" maxlength=20 type="text" value="${vo.wd}" /> 
							</td>
							<td class="width-15 active"><label class="pull-right">湿&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度:</label></td>
							<td class="width-35">
	                         	<input id="sd" name="sd" class="form-control" placeholder="%" maxlength=20 type="text" value="${vo.sd}" />
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
												<input type="text" id="val_${iv.index}_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}"  onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="value_${iv.index}" class="form-control" name="itemList[${iv.index}].value" value="${it.value}">
											</td>
											<td>
												<select id="result_${iv.index}" name="itemList[${iv.index}].result" class="form-control" >
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
												<input type="text" id="val_${iv.index}_1_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}"   onchange="validtValue(this,${iv.index},'高频电磁场')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_2_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}"   onchange="validtValue(this,${iv.index},'高频电磁场')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}"   onchange="validtValue(this,${iv.index},'高频电磁场')">
											</td>
											<td>
												<input type="text" id="value_${iv.index}" class="form-control" name="itemList[${iv.index}].value" value="${it.value}">
											</td>
											<td rowspan="2">
												<select id="result_${iv.index}" name="itemList[${iv.index}].result" class="form-control" >
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
												<input type="text" id="val_${iv.index}_1_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v10" value="${it.trVo.sampVo.recordVo.v10}"   onchange="validtValue(this,${iv.index},'高频电磁场')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_2_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v11" value="${it.trVo.sampVo.recordVo.v11}"   onchange="validtValue(this,${iv.index},'高频电磁场')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v12" value="${it.trVo.sampVo.recordVo.v12}"   onchange="validtValue(this,${iv.index},'高频电磁场')">
											</td>
											<td>
												<input type="text" id="value2_${iv.index}" class="form-control" name="itemList[${iv.index}].value2" value="${it.value2}">
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
												<input type="text" id="val_${iv.index}_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}"  onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="value_${iv.index}" class="form-control" name="itemList[${iv.index}].value" value="${it.value}">
											</td>
											<td>
												<select id="result_${iv.index}" name="itemList[${iv.index}].result" class="form-control" >
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
												<input type="text" id="val_${iv.index}_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}"    onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}"    onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}"    onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="value_${iv.index}" class="form-control" name="itemList[${iv.index}].value" value="${it.value}">
											</td>
											<td>
												<select id="result_${iv.index}" name="itemList[${iv.index}].result" class="form-control" >
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
												<input type="text" id="val_${iv.index}_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}"    onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}"    onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}"  onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="value_${iv.index}" class="form-control" name="itemList[${iv.index}].value" value="${it.value}">
											</td>
											<td>
												<select id="result_${iv.index}" name="itemList[${iv.index}].result" class="form-control" >
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
												<input type="text" id="val_${iv.index}_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v2" value="${it.trVo.sampVo.recordVo.v2}"  onchange="validtValue(this)" onchange="validtValue(this,${iv.index},'紫外辐射')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}"  onchange="validtValue(this)" onchange="validtValue(this,${iv.index},'紫外辐射')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}"  onchange="validtValue(this)" onchange="validtValue(this,${iv.index},'紫外辐射')">
											</td>
											<td>
												<input type="text" id="value_${iv.index}" class="form-control" name="itemList[${iv.index}].value" value="${it.value}">
											</td>
											<td>
												<select id="result_${iv.index}" name="itemList[${iv.index}].result" class="form-control" >
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
												<input type="text" id="val_${iv.index}_1_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v1" value="${it.trVo.sampVo.recordVo.v1}"   onchange="validtValue(this,${iv.index},'高温')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_2_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v2" value="${it.trVo.sampVo.recordVo.v2}"   onchange="validtValue(this,${iv.index},'高温')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v3" value="${it.trVo.sampVo.recordVo.v3}"   onchange="validtValue(this,${iv.index},'高温')">
											</td>
											<td>
												<input type="text" id="avg1_${iv.index}" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.avg1" value="${it.trVo.sampVo.recordVo.avg1}">
											</td>
											<td rowspan="3">
												${it.trVo.sampVo.workHours}
											</td>
											<td rowspan="3">
												${it.trVo.sampVo.fcType}
											</td>
											<td rowspan="3">
												<select id="result_${iv.index}" name="itemList[${iv.index}].result" class="form-control" >
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
												<input type="text" id="val_${iv.index}_1_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}"   onchange="validtValue(this,${iv.index},'高温')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_2_2"  class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}"   onchange="validtValue(this,${iv.index},'高温')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}"   onchange="validtValue(this,${iv.index},'高温')">
											</td>
											<td>
												<input type="text" id="avg2_${iv.index}" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.avg2" value="${it.trVo.sampVo.recordVo.avg2}">
											</td>
										</tr>
										<tr>
											<td>
												工作结束前0.5h
											</td>
											 <td>
												<input type="text" id="val_${iv.index}_1_3" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v7" value="${it.trVo.sampVo.recordVo.v7}"   onchange="validtValue(this,${iv.index},'高温')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_2_3" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v8" value="${it.trVo.sampVo.recordVo.v8}"   onchange="validtValue(this,${iv.index},'高温')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3_3" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v9" value="${it.trVo.sampVo.recordVo.v9}"   onchange="validtValue(this,${iv.index},'高温')">
											</td>
											<td>
												<input type="text" id="avg3_${iv.index}" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.avg3" value="${it.trVo.sampVo.recordVo.avg3}">
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
												<input type="text" id="val_${iv.index}_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v1" value="${it.trVo.sampVo.recordVo.v1}"   onchange="validtValue(this,${iv.index},'噪声')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v2" value="${it.trVo.sampVo.recordVo.v2}"  onchange="validtValue(this,${iv.index},'噪声')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v3" value="${it.trVo.sampVo.recordVo.v3}"  onchange="validtValue(this,${iv.index},'噪声')">
											</td>
											<td>
												<input type="text" id="value_${iv.index}" class="form-control" name="itemList[${iv.index}].value" value="${it.value}">
											</td>
											<td>
												<input type="text" id="value2_${iv.index}" class="form-control" name="itemList[${iv.index}].value2" value="${it.value2}">
											</td>
											<td>
												<select id="result_${iv.index}" name="itemList[${iv.index}].result" class="form-control" >
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
												<input type="text" id="val_${iv.index}_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v2" value="${it.trVo.sampVo.recordVo.v2}"   onchange="validtValue(this,${iv.index},'手传振动')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}"   onchange="validtValue(this,${iv.index},'手传振动')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}"   onchange="validtValue(this,${iv.index},'手传振动')">
											</td>
											<td>
												<input type="text"id="value_${iv.index}" class="form-control" name="itemList[${iv.index}].value" value="${it.value}">
											</td>
											<td>
												<input type="text" id="value2_${iv.index}" class="form-control" name="itemList[${iv.index}].value2" value="${it.value2}">
											</td>
											<td>
												<select id="result_${iv.index}" name="itemList[${iv.index}].result" class="form-control" >
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
												<input type="text" id="val_${iv.index}_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v1" value="${it.trVo.sampVo.recordVo.v1}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											 <td>
												<input type="text" id="val_${iv.index}_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v2" value="${it.trVo.sampVo.recordVo.v2}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v3" value="${it.trVo.sampVo.recordVo.v3}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_4" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_5" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_6" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v6" value="${it.trVo.sampVo.recordVo.v6}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="value_${iv.index}" class="form-control" name="itemList[${iv.index}].value" value="${it.value}">
											</td>
											<td>
												<select id="result_${iv.index}" name="itemList[${iv.index}].result" class="form-control" >
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
												<input type="text"  id="val_${iv.index}_1" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v1" value="${it.trVo.sampVo.recordVo.v1}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											 <td>
												<input type="text"  id="val_${iv.index}_2" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v2" value="${it.trVo.sampVo.recordVo.v2}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_3" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v3" value="${it.trVo.sampVo.recordVo.v3}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_4" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v4" value="${it.trVo.sampVo.recordVo.v4}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="val_${iv.index}_5" class="form-control" name="itemList[${iv.index}].trVo.sampVo.recordVo.v5" value="${it.trVo.sampVo.recordVo.v5}"   onchange="validtValue(this,${iv.index},'均值')">
											</td>
											<td>
												<input type="text" id="value_${iv.index}" class="form-control" name="itemList[${iv.index}].value" value="${it.value}">
											</td>
											<td>
												<select id="result_${iv.index}" name="itemList[${iv.index}].result" class="form-control" >
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
													<input type="text" class="form-control" name="itemList[${iv.index}].trList[${rs.index}].sampVo.recordVo.demo2" value="${r1.sampVo.recordVo.demo2}">
												</td>
												<td>
													<input type="text" class="form-control" name="itemList[${iv.index}].trList[${rs.index}].sampVo.recordVo.v1" value="${r1.sampVo.recordVo.v1}">
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
														<input type="hidden" id="workHours_${s.index}" value="${e.pointVo.workHours}" />
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
				                                    <input type="text" id="val_${s.index}_${rs.index}" name="itemList[${s.index}].trList[${rs.index}].value" class="form-control required"  onkeyup="keygo(event,this)" validate="required" value="${r.value}"  onchange="validtValue(this,${s.index},'${vo.limited}')">
												</td>
												<c:if test="${rs.index==0}">
													<c:choose>
														<c:when test="${vo.limited=='a'}">
															<td rowspan="${fn:length(e.trList)}">
							                                    <input type="text" id="mac_${s.index}" name="itemList[${s.index}].mac" class="form-control  required" validate="required" value="${e.mac}" onkeyup="keygo(event,this)">
															</td>
														</c:when>
														<c:when test="${vo.limited=='b'}">
															<td rowspan="${fn:length(e.trList)}">
							                                    <input type="text" id="twa_${s.index}" name="itemList[${s.index}].twa" class="form-control  required" validate="required" value="${e.twa}" onkeyup="keygo(event,this)">
															</td>
															<td rowspan="${fn:length(e.trList)}">
							                                    <input type="text" id="stel_${s.index}" name="itemList[${s.index}].stel" class="form-control  required" validate="required" value="${e.stel}" onkeyup="keygo(event,this)">
															</td>
														</c:when>
														<c:when test="${vo.limited=='c'}">
															<td rowspan="${fn:length(e.trList)}">
							                                    <input type="text" id="twa_${s.index}" name="itemList[${s.index}].twa" class="form-control  required" validate="required" value="${e.twa}" onkeyup="keygo(event,this)">
															</td>
															<td rowspan="${fn:length(e.trList)}">
							                                    <input type="text" id="lmt_${s.index}" name="itemList[${s.index}].lmt" class="form-control  required" validate="required" value="${e.lmt}" onkeyup="keygo(event,this)">
															</td>
														</c:when>
														<c:otherwise>
															<td rowspan="${fn:length(e.trList)}">
							                                    <input type="text" id="value_${s.index}" name="itemList[${s.index}].value" class="form-control  required" validate="required" value="${e.value}" onkeyup="keygo(event,this)">
															</td>
														</c:otherwise>
													</c:choose>
													<td rowspan="${fn:length(e.trList)}">
														<select id="result_${s.index}" name="itemList[${s.index}].result" class="form-control" >
														 	<c:choose>
														 		<c:when test="${e.result=='合格'}">
														 			<option value="">/</option>
																 	<option value="合格" selected="selected">合格</option>
																 	<option value="不合格">不合格</option>
														 		</c:when>
														 		<c:when test="${e.result=='不合格'}">
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
												</c:if>
											</tr>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<!-- <tr>
							<td class="active"><label class="pull-right">原始记录:</label></td>
							<td colspan="3">
								<div class="input-group" style="100%">
									<select id="temp" name="temp" class="form-control" style="width: 200px;">
									 	<option value="">-请选择-</option>
									 	<c:forEach items="${vo.tempList}" var="e" varStatus="v">
									 		<c:choose>
										 		<c:when test="${vo.temp==e.relativePath}">
												 	<option value="${e.relativePath}" selected="selected">${e.name}</option>
										 		</c:when>
										 		<c:otherwise>
												 	<option value="${e.relativePath}">${e.name}</option>
										 		</c:otherwise>
										 	</c:choose>
									 	</c:forEach>
									 </select>
									 <div class="input-group-btn">
									 	<button tabindex="-1" class="btn btn-warning" type="button" onclick="createTemp(this)">重新生成</button>
										<c:choose>
											<c:when test="${vo.filePath==''||vo.filePath==null}">
												<button tabindex="-1" class="btn btn-default" type="button" onclick="fnOpenTemp(this)">在线编辑</button>
											</c:when>
											<c:otherwise>
												<button tabindex="-1" class="btn btn-info" type="button" onclick="fnOpenTemp(this)">在线编辑</button>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</td>
						</tr>-->
						<tr>
							<td class="active"><label class="pull-right">上传附件:</label></td>
							<td colspan="3" >
								<a class="btn btn-info" href="javascript:void(0);" onclick="fileUpload();" style="float: left; margin-right: 5px;">在线上传</a> 
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
											<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
										</div>
									</c:forEach>
								</div>
							</td>
						</tr> 
						<tr>
							<td class="width-15 active"><label class="pull-right">检&nbsp;测&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								<input id="testManId" name="testManId" type="hidden" value="${vo.testManId}" /> 
								<input id="testMan" name="testMan" class="form-control" maxlength=32  type="text" value="${vo.testMan}" readonly="readonly" />
							</td>
							<td class="active"><label class="pull-right">检测时间:	</label></td>
							<td>
								<div class="input-group" style="width: 100%">
									<input type="text" name="testTime" class="form-control  datetimeISO required" validate="required"  validate="required" value="${vo.testTime}" autocomplete="off">
									 <span class="input-group-addon">至</span>
									<input type="text" name="testEndTime" class="form-control  datetimeISO required" validate="required"  validate="required" value="${vo.testEndTime}" autocomplete="off">
							 	</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea  rows="2" id="testMsg" name="testMsg" class="form-control" maxlength="128">${vo.testMsg}</textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" type="button" onclick="formSubmitSave('update4Data.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('update4Data.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
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
<%@ include file="../../../sys/open/open_img.jsp"%>
</body>
<script>
$('input[type="file"]').prettyFile();
function fnShowFile(id){
	POBrowser.openWindow('${basePath}sys/files/showFile.do?id='+id,'width=1200px;height=800px;');
}
function fileUpload(){
	var id='${vo.id}';
	$('#thisForm').attr('action','update4Data.do?isCommit=0');
	$('#thisForm').ajaxSubmit(function(res) {
    	if(res.status=='error'){
        	parent.toastr.error(res.message, '');
        	return false;
        }
	});
	layer.open({
		title:'在线上传',	
		type: 2,
		area: ['700px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: 'upload.do?id='+id,
		end: function () {
            location.reload();
        }
	});
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
function copyVal(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('input').eq(0).val();
	var idValue1=obj.closest('td').find('input').eq(1).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#stand_method tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
		$(this).find('td').eq(indexTd).find('input').eq(1).val(idValue1);
	});
}
 
function copySel(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('select').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#jcTb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('select').eq(0).val(idValue);
	});
}
function formSubmitSave(url){
	$('#thisForm').attr('action',url);
	$('#thisForm').ajaxSubmit(function(res) {
    	if(res.status=='success'){
    	    parent.toastr.success(res.message, '');
    	    location.reload();
        }else{
        	parent.toastr.error(res.message, '');
        }
	});
}	
function fnSubmit(url){
	$('#thisForm').attr('action',url);
	var b = $("#thisForm").FormValidate();
	if(b){
		swal({
	        title: "您确定要提交该任务吗",
	        text: "提交后将无法修改，请谨慎操作！",
	        type: "success",
	        showCancelButton: true,
	        confirmButtonColor: "#1ab394",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消"
	    }, function () {
	    	$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
			        backMainPage();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
	    });
	}
}
function keygo(evt,obj){
	var cols=$('#jcTb').find('tr').length-1;
	var key = window .event?evt.keyCode:evt.which; 
	var tdIndex=$(obj).closest('td').index();
	var trIndex=$(obj).closest('tr').index();
	if (key==37){//左 
		if(tdIndex>5){
			var tdStr=$('#jcTb').find('tr:eq('+trIndex+')').find('td:eq('+(tdIndex-1)+')');
			if(tdStr.find('input').length>0){
				tdStr.find('input').focus();
			}else{
				tdStr.find('select').focus();
			}
		}
	}else if(key==38){//上
		if(trIndex>0){
			var tdStr=$('#jcTb').find('tr:eq('+(trIndex-1)+')').find('td:eq('+tdIndex+')');
			if(tdStr.find('input').length>0){
				tdStr.find('input').focus();
			}else{
				tdStr.find('select').focus();
			}
		}
	}else if(key==39){//右 
		if(tdIndex<8){
			var tdStr=$('#jcTb').find('tr:eq('+trIndex+')').find('td:eq('+(tdIndex+1)+')');
			if(tdStr.find('input').length>0){
				tdStr.find('input').focus();
			}else{
				tdStr.find('select').focus();
			}
		}
	}else if(key==40){//下
		if(trIndex<cols){
			var tdStr=$('#jcTb').find('tr:eq('+(trIndex+1)+')').find('td:eq('+tdIndex+')');
			if(tdStr.find('input').length>0){
				tdStr.find('input').focus();
			}else{
				tdStr.find('select').focus();
			}
		}
	}
}
function checkMethod(obj,n){
	var methodId=$('#methodId'+n).val();
	if(methodId==''){
		layer.msg('请选择检测方法！', {icon: 0,time: 1000});
		$(obj).blur();
	}
}
function removeFiles(id,obj){
	layer.confirm('确认要删除?', {icon:3, title:'系统提示'}, function(index){
		$.ajax({
			url:'${basePath}sys/files/deleteOne.do?id='+id,
			dataType:"json",
			type:"post",
			async:false,
			success: function(data){
				if(data.status=='success'){
					layer.msg(data.message, {icon: 0,time: 1000});
					$(obj).parent().remove();
				}
			},
			error:function(ajaxobj){
		    }  
		});
	});
}
 
</script>
<script type="text/javascript">
//标准物质 、 方法、 仪器
//测试标准
function choosePstand(){
	var standId=$('#standId').val();
	var sampTypeId='${vo.sampTypeId}';
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
			$('#standId').val(data.id);
			$('#standName').val(data.name);
			//保存方法
			$('#thisForm').attr('action','update4Data.do?isCommit=0');
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
function chooseLimit(){
	var standId=$('#standId').val();
	if(standId==null||standId==''){
		layer.msg('请选择评价标准！', {icon: 0,time: 1000});
		return false;
	}
	var sampTypeId='${vo.sampTypeId}';
	var sampType='${vo.sampTypeName}';
	layer.open({
		title:'评价标准',	
		type: 2,
		area: ['1000px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/pstandItem/select.do?sampType='+encodeURI(sampType)+'&standId='+standId+'&itemVo.id=${vo.itemId}',
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#thisForm').attr('action','update4Limited.do?id=${vo.id}&uuid='+data.id);
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
//测试方法
function chooseMethod(){
	var methodId=$('#methodId').val();
	var itemId=$('#itemId').val();
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
			$('#methodId').val(data.id);
			$('#methodName').val(data.name);
			$('#limitLine').val(data.limitLine);
			if(data.id!='methodId'){
				$('#appId').val(null);
				$('#appName').val(null);
			}
			//保存方法
			$('#thisForm').attr('action','update4Data.do?isCommit=0');
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
//测试仪器
function chooseApp(){
	var methodId=$('#methodId').val();
	if(methodId==''){
		layer.msg('请选择检测方法！', {icon: 0,time: 1000});
		return false;
	}
	var appId=$('#appId').val();
	layer.open({
		title:'检测仪器',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: 'showApp.do?methodId='+methodId+'&appId='+appId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#appId').val(data.id);
			$('#appName').val(data.name);
			//保存方法
			$('#thisForm').attr('action','update4Data.do?isCommit=0');
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success('更新成功！', '');
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	});
}
//标准物质
function chooseSt(){
	var stId=$('#stId').val();
	layer.open({
		title:'标准物质',	
		type: 2,
		area: ['800px', '400px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}res/standard/selects.do?ids='+stId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#stId').val(data.id);
			$('#stName').val(data.name);
			//保存方法
			$('#thisForm').attr('action','update4Data.do?isCommit=0');
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success('更新成功！', '');
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	});
}
</script>
<script type="text/javascript">
function createTemp(obj){
	var tempName = $("#temp").val();
	if (tempName=="")
		{
		layer.msg('请选择相应的原始记录模板', {icon: 0,time: 3000});
		 return;
		}
	var msg=checkTempFile();
	$('#thisForm').attr('action','update4Data.do?isCommit=0');
	$('#thisForm').ajaxSubmit(function(res) {
    	if(res.status=='success'){
    		if(msg==''){
    			if(confirm("文件已经存在，要重新生成吗？")){
    				POBrowser.openWindow('${basePath}bus/itemTest/createTemp.do?id=${vo.id}','width=1200px;height=800px;');
    			}
    		}else{
    			POBrowser.openWindow('${basePath}bus/itemTest/createTemp.do?id=${vo.id}','width=1200px;height=800px;');
    			$(obj).next().removeClass('btn-default')
    			$(obj).next().addClass('btn-info')
    		}
        }else{
        	parent.toastr.error(res.message, '');
        }
	});
}
function fnOpenTemp(obj){
	var tempName = $("#temp").val();
	if (tempName=="")
		{
		layer.msg('请选择相应的原始记录模板', {icon: 0,time: 3000});
		 return;
		}
	POBrowser.openWindow('${basePath}bus/itemTest/editTemp.do?id=${vo.id}','width=1200px;height=800px;');
	$(obj).removeClass('btn-default')
	$(obj).addClass('btn-info')
}

function checkTempFile(){
	var msg='';
	$.ajax({ 
		url:"checkTempFile.do?id=${vo.id}",
		dataType:"json",
		type:"post",
		async:false,
		success: function(data){
			if("error"==data.type){
				msg=data.message;
			}
		},
		error:function(ajaxobj){  
			msg=ajaxobj;
	    }  
	});
	return msg;
}
</script>
<script type="text/javascript">
//自动计算
function validtValue(obj,index,type){
	if(type=='均值'){
		fnPjz(index);
	}else if(type=='高频电磁场'){
		fnToGPDC(index);
	}else if(type=='紫外辐射'){
		//fnToZWFS(index);
	}else if(type=='高温'){
		fnToGW(index);
	}else if(type=='噪声'){
		
	}else if(type=='手传振动'){
	
	}else if(type.indexOf('粉尘')>=0){
	
	}else if(type=='a'){
		fnToA(index);
	}else if(type=='b'){
		fnToB(index);
	}else if(type=='c'){
		fnToC(index);
	}else{
		
	}
}
//后台判定 结果是否合格
function fnResult(index,type){
	var vl=parseFloat($(obj).val());
	var id='${vo.id}';
	$.ajax({ 
		url:"checkValue.do?id="+id+'&value='+vl,
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

//高频电磁场计算公式
function fnToGPDC(index){
	
}

//高温计算公式
function fnToGW(index){
	var val_1_1 = $('input[id^="val_'+index+'_1_1"]').val();
	var val_2_1 = $('input[id^="val_'+index+'_2_1"]').val();
	var val_3_1 = $('input[id^="val_'+index+'_3_1"]').val();
	var avg1=0.0;
	avg1 = (val_1_1*0.7+val_2_1*0.2+val_3_1*0.1).toFixed(2);
	$('input[id^="avg1_'+index+'"]').val(avg1);
	
	var val_1_2 = $('input[id^="val_'+index+'_1_2"]').val();
	var val_2_2 = $('input[id^="val_'+index+'_2_2"]').val();
	var val_3_2 = $('input[id^="val_'+index+'_3_2"]').val();
	var avg2=0.0;
	avg2 = (val_1_2*0.7+val_2_2*0.2+val_3_2*0.1).toFixed(2);
	$('input[id^="avg2_'+index+'"]').val(avg2);
	
	var val_1_3 = $('input[id^="val_'+index+'_1_3"]').val();
	var val_2_3 = $('input[id^="val_'+index+'_2_3"]').val();
	var val_3_3 = $('input[id^="val_'+index+'_3_3"]').val();
	var avg3=0.0;
	avg3 = (val_1_3*0.7+val_2_3*0.2+val_3_3*0.1).toFixed(2);
	$('input[id^="avg3_'+index+'"]').val(avg3);
	
}



//平均值 计算
function fnPjz(index){
	var val=0.0;
	var n=0;
	$('input[id^="val_'+index+'_"]').each(function (){
		var v=$(this).val();
		if(v!=''){
			var thisVal=parseFloat(v);
			if(!isNaN(thisVal)){
				val+=thisVal;
				n++;
			}
		}
	});
	if(n>0){
		val=val/n;
		if(val.toString().indexOf('.')>0){
			var vn=val.toString().split(".")[1].length
			if(vn>=5){
				val=val.toFixed(2);
			}
		}
		$('#value_'+index).val(val);
	}
}
//计算 a 类限值 Cmac 最大值
function fnToA(index){
	var val=0.0;
	var valStr='0';
	$('input[id^="val_'+index+'_"]').each(function (){
		var v=$(this).val();
		if(v!=''){
			var thisVal=parseFloat(v);
			if(!isNaN(thisVal) && val<thisVal){
				val=thisVal;
			}else{
				valStr=v;
			}
		}
	});
	if(val==0){
		$('#mac_'+index).val(valStr);
	}else{
		$('#mac_'+index).val(val);
	}
	var mac=parseFloat($('#mac').val());
	if(val>mac){
		$('#result_'+index).val('不合格');
	}else{
		$('#result_'+index).val('合格');
	}
}
//计算 b 类限值 twa stel
function fnToB(index){
	var val=0.0;//平均值
	var val2=0.0;//最大值
	var n=0;
	var valStr='0';
	$('input[id^="val_'+index+'_"]').each(function (){
		var v=$(this).val();
		if(v!=''){
			var thisVal=parseFloat(v);
			if(!isNaN(thisVal) && val2<thisVal){
				val2=thisVal;
			}
			if(!isNaN(thisVal)){
				val+=thisVal;
				n++;
			}else{
				valStr=v;
			}
		}
	});
	if(val2==0){
		$('#stel_'+index).val(valStr);
	}else{
		$('#stel_'+index).val(val2);
	}
	var wh=parseFloat($('#workHours_'+index).val());
	if(n>0 && !isNaN(wh)){
		val=((val/n)*wh)/8;
		if(val.toString().indexOf('.')>0){
			var vn=val.toString().split(".")[1].length
			if(vn>=5){
				val=val.toFixed(2);
			}
		}
		$('#twa_'+index).val(val);
	}else{//获取其他异常 ，twa=val
		$('#twa_'+index).val(valStr);
	}
	var twa=parseFloat($('#twa').val());
	var stel=parseFloat($('#stel').val());
	if(val>twa || val2>stel){
		$('#result_'+index).val('不合格');
	}else{
		$('#result_'+index).val('合格');
	}
}
//计算 C 类限值twa lmt(超限倍数)
function fnToC(index){
	var val=0.0;//平均值
	var val2=0.0;//最大值
	var n=0;
	var valStr='0';
	$('input[id^="val_'+index+'_"]').each(function (){
		var v=$(this).val();
		if(v!=''){
			var thisVal=parseFloat(v);
			if(!isNaN(thisVal) && val2<thisVal){
				val2=thisVal;
			}
			if(!isNaN(thisVal)){
				val+=thisVal;
				n++;
			}else{
				valStr=v;
			}
		}
	});
	var wh=parseFloat($('#workHours_'+index).val());
	if(n>0 && !isNaN(wh)){
		val=((val/n)*wh)/8;
		if(val2.toString().indexOf('.')>0){
			var vn=val.toString().split(".")[1].length
			if(vn>=5){
				val=val.toFixed(2);
			}
		}
		$('#twa_'+index).val(val);
	}else{//获取其他异常 ，twa=val
		$('#twa_'+index).val(valStr);
	}
	var lmt=parseFloat($('#lmt').val());
	if(val2>0 && !isNaN(lmt)){
		val2=val2/lmt
		if(val2.toString().indexOf('.')>0){
			var vn=(val2.toString().split(".")[1]).length
			if(vn>=5){
				val2=val2.toFixed(2);
			}
		}
	}
	$('#lmt_'+index).val(val2);
	if(lmt<val2){
		$('#result_'+index).val('不合格');
	}else{
		$('#result_'+index).val('合格');
	}
}
</script>
</html>
