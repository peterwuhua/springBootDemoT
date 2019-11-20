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
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
.funTable{
	margin-bottom: 0px;
}
.btn-group>button{
	margin-right: 10px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">报告编号:</label></td>
							<td class="width-35">
								${vo.reportNo}
							</td>
							<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
							<td class="width-35">
								<a href="javascript:void(0);" onclick="fnTask('${vo.taskVo.id}','${vo.taskVo.no}');">${vo.taskVo.no}</a>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">客户名称:</label></td>
							<td>
								${vo.custVo.custName}
							</td>
							 <td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">样品类别:</label></td>
							<td>
								${vo.sampTypeName}
							</td>
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>
								${vo.sampName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">报告日期:</label></td>
							<td>
								${vo.reportDate}
							</td>
							<td class="active"><label class="pull-right">要求完成日期:</label></td>
							<td>
								${vo.finishDate}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测结论:</label></td>
							<td colspan="3">
								${vo.result}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">建&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;议:</label></td>
							<td colspan="3">
								${vo.jssm}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea class="form-control" rows="2" id="remark" maxlength="2000" name="remark" placeholder="特殊情况说明">${vo.remark}</textarea>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">编&nbsp;制&nbsp;&nbsp;人:</label></td>
							<td>
								${vo.makeUser}
							</td>
							<td class="width-15 active"><label class="pull-right">编制日期:</label></td>
							<td>
								${vo.makeDate}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">报告信息:</label></td>
							<td colspan="3">
		                  	 	<div class="btn-group" id="editReprt">
		                  	 		<button class="btn btn-info" type="button" onclick="fnShowReport('');">在线查看报告</button>
		                       		<c:if test="${vo.taskType=='定期检测'}">
		                       			<button class="btn btn-info" type="button" onclick="fnShowReport('dq');">在线查看报告(总)</button>
		                       		</c:if>
			                    </div>
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
							<td class="width-15 active"><label class="pull-right">审核人员:</label></td>
							<td>
								${vo.auditUser}
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${logVo.msg}
							</td>
						</tr>
						<tr>
							<td colspan="4">
								检测项目
								<a class="pull-right btn btn-xs btn-outline btn-danger" onclick="itemToggle(this);"><i class="fa fa-chevron-up" aria-hidden="true"></i>闭合</a>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								 <c:forEach items="${vo.timList}" var="e"  varStatus="v">
									<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
										<tr>
											<td>
												<span class="pull-left"><label> 检测项目：</label>${e.itemName}</span>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<label> 计量单位：</label>${e.unit} 
											</td>
										</tr>
										<tr>
											<td>
												<label> 检测方法：</label>${e.methodName} 
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<label> 检出限：</label>${e.limitLine} 
											</td>
										</tr>
										<tr>
											<td>
												<label> 评价依据：</label>${e.standName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<c:choose>
													<c:when test="${e.limited=='a'}">
														MAC：${e.mac} ${e.unit}
														<input type="hidden" id="mac" class="form-control" value="${e.mac}">
													</c:when>
													<c:when test="${e.limited=='b'}">
														 <label>  
														 	PC-TWA：${e.twa} ${e.unit}
														    <input type="hidden" id="twa" class="form-control" value="${e.twa}">
														 </label>
														 <label>
														 	&nbsp;&nbsp;&nbsp;&nbsp;
														 	PC-STEL：${e.stel} ${e.unit}
														 	<input type="hidden" id="stel" class="form-control" value="${e.stel}">
														 </label>
													</c:when>
													<c:when test="${e.limited=='c'}">
														<label>  
															PC-TWA：${e.twa} ${e.unit}
														  <input type="hidden" id="twa" class="form-control" value="${e.twa}">
														</label>
														<label> 
														    &nbsp;&nbsp;&nbsp;&nbsp;
														         超限倍数：${e.lmt}
															<input type="hidden" id="lmt" class="form-control" value="${e.lmt}"> 
														</label>
													</c:when>
													<c:when test="${e.limited!=''&&e.limited!=null}">
															限值：${e.limited} ${e.unit}
													</c:when> 
													<c:otherwise>
														<font color="red">限值未找到</font>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</table>
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
														<th rowspan="2" width="10%" style="text-align: center;">
															车间
														</th>
														<th rowspan="2" width="10%" style="text-align: center;">
															检测点位
														</th>
														<th rowspan="2" width="10%" style="text-align: center;">
															接触时间（h/d）
														</th>
														<th colspan="5" style="text-align: center;">测量结果(mg)</th>
														
														<th rowspan="2" width="10%">CTWA (mg/m3)</th>
														<th rowspan="2" width="10%">超限倍数</th>
														
														<th rowspan="2" width="10%">单项判定</th>
													</tr>
													<tr>
														<th style="text-align: center;width: 150px;">序号</th>
														<th style="text-align: center;width: 80px;">采样前滤膜质量</th>
														<th style="text-align: center;width: 80px;">采样后滤膜质量</th>
														<th style="text-align: center;width: 80px;">滤膜增重</th>
														<th style="text-align: center;width: 80px;">浓度</th>
													</tr>
												</thead>
												<tbody class="val_tb">
													<c:forEach items="${e.itemList}" var="it" varStatus="iv">
													 	<c:forEach items="${it.trList}" var="e2" varStatus="v2">
															<tr>
																<c:if test="${v2.index==0}">
																	<td rowspan="${fn:length(it.trList)}">
																		${it.pointVo.room}
																		<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].id" value="${it.id}">
																	</td>
																	<td rowspan="${fn:length(it.trList)}">
																		${it.pointVo.pointName}
																	</td>
																	<td rowspan="${fn:length(it.trList)}">
																		${it.pointVo.workHours}
																	</td>
																</c:if>
																<td>
																	${e2.sampVo.sampCode}
																	<input type="hidden" name="timList[${v.index}].itemList[${iv.index}].trList[${v2.index}].id" value="${e2.id}">
																</td>
																<td><!-- 测量结果 -->
																	${e2.sampVo.recordVo.v1}
																</td>
																 <td>
																	${e2.sampVo.recordVo.v2}
																</td>
																<td>
																	${e2.sampVo.recordVo.v3}
																</td>
																<td>
																	${e2.sampVo.recordVo.v4}
																</td>
																<c:if test="${v2.index==0}">
																	<td rowspan="${fn:length(it.trList)}"><!-- CTWA (mg/m3) -->
																		${it.twa}
																	</td>
																	<td rowspan="${fn:length(it.trList)}"><!-- 超限倍数 -->
																		${it.lmt}
																	</td>
																	<td rowspan="${fn:length(it.trList)}">
																		${it.result}
																	</td>
																</c:if>
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
																${it.trVo.sampVo.recordVo.v4}
															</td>
															 <td>
																${it.trVo.sampVo.recordVo.v5}
															</td>
															<td>
																${it.trVo.sampVo.recordVo.v6}
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
														<td style="text-align: center;">检测岗位</td>
														<td style="text-align: center;">检测点</td>
														<td style="text-align: center;width: 100px;">接触时间</td>
														<c:forEach begin="1" end="${e.count}" var="e1">
															<td style="text-align: center;width: 80px;">${e1}</td>
														</c:forEach>
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
												<tbody>
													<c:forEach items="${e.itemList}" var="e1" varStatus="s1">
														<tr>
															<td align="center">
																${s1.index+1}
															</td>
															<td align="center">
																${e1.pointVo.room}
															</td>
															<td align="center">
																${e1.pointVo.pointName}
															</td>
															<td align="center">
																${e1.workHours}
															</td>
															<c:forEach items="${e1.trArry}" var="e2">
																<td align="center">
								                                   ${e2}
																</td>
															</c:forEach>
															<c:choose>
																<c:when test="${e.limited=='a'}">
																	<td align="center">
									                                   ${e1.mac}
																	</td>
																</c:when>
																<c:when test="${e.limited=='b'}">
																	<td align="center">
									                                    ${e1.twa}
																	</td>
																	<td align="center">
									                                   ${e1.stel}
																	</td>
																</c:when>
																<c:when test="${e.limited=='c'}">
																	<td align="center">
									                                  ${e1.twa}
																	</td>
																	<td align="center">
									                                   ${e1.lmt}
																	</td>
																</c:when>
																<c:otherwise>
																	<td align="center">
									                                    ${e1.value}
																	</td>
																</c:otherwise>
															</c:choose>
															<td align="center">
																${e1.result}
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
<!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
<script>
function fnShowReport(type){
	POBrowser.openWindow('${basePath}bus/report/showReport.do?id=${vo.id}&uuid='+type,'width=1200px;height=800px;');
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
</body>
</html>
