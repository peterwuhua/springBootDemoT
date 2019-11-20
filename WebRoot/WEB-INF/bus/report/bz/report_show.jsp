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
							<td class="active"><label class="pull-right">评价标准:</label></td>
							<td colspan="3">
								${vo.standNames}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">检测内容:</label></td>
							<td colspan="3">
								${vo.taskVo.jcct}
							</td>
						</tr>
						<!-- <tr>
							<td class="active"><label class="pull-right">检测目的:</label></td>
							<td colspan="3">
								<textarea id="testResult" name="testResult" class="form-control">${vo.testResult}</textarea>
							</td>
						</tr> -->
						<tr>
							<td class="active"><label class="pull-right">检测结论:</label></td>
							<td colspan="3">
								${vo.result}
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
			                       <button class="btn btn-info" type="button" onclick="fnShowReport();">在线查看报告</button>
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
						<tr id="item_table" >
							<td colspan="4">
								 <table class="table table-bordered">
									<thead>
										<tr>
											<th width="60">序号</th>
											<th style="text-align: center;width: 10%;">检测点位</th>
											<th style="text-align: center;width: 10%;">样品编号</th>
											<th style="width: 10%" colspan="2">检测项目</th>
											<th style="text-align: center;width: 10%;">检测结果</th>
											<th style="text-align: center;width: 70px;">标准值</th>
											<th style="text-align: center;width: 70px;">检出限</th>
											<c:if test="${vo.taskVo.pj=='是'}">
											 <th style="text-align: center;width: 70px;">单项判定</th>
											</c:if>
											<th style="text-align: center;">检测方法</th>
											<th style="text-align: center;width: 10%;">检测仪器</th>
										</tr>
									</thead>
									<tbody id="itemTb">
										<c:set var="num" value="0"/>
										<c:forEach items="${vo.detailList}" var="t">
											<tr>
												<td colspan="10">
													${t.sampName}
												</td>
											</tr>
											<c:forEach items="${t.subList}" var="e" varStatus="v">
												<c:choose>
													<c:when test="${fn:length(e.subList)>0}">
														<c:forEach items="${e.subList}" var="e1" varStatus="v1">
															<tr>
																<c:if test="${v1.index==0}">
																	<td rowspan="${fn:length(e.subList)}">
																		${e.sort}
																	</td>
																	<td rowspan="${fn:length(e.subList)}">
																		${e.pointName}
																	</td>
																	<td rowspan="${fn:length(e.subList)}">
																		${e.sampCode}
																	</td>
																	<td rowspan="${fn:length(e.subList)}">
																		${e.itemName}
																	</td>
																</c:if>
																<td>
																	${e1.itemName}
																</td>
																<td>
																	<c:choose>
																		<c:when test="${e.type=='气'}">
																			浓度：${e1.value2}<br>
																			速率：${e1.sl}
																		</c:when>
																		<c:otherwise>
																			${e1.value}
																		</c:otherwise>
																	</c:choose>
																</td>
																<td>${e1.limited}</td>
																<td>${e1.limitLine}</td>
																<c:if test="${vo.taskVo.pj=='是'}">
																	<td>
																		${e1.result}
																	</td>
																</c:if>
																<td>
																	${e1.methodName}
																</td>
																<td>
																	${e1.appName}
																</td>
															</tr>
															<c:set var="num" value="${num+1}"/>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr index="${v.index}" >
															<td>
																${e.sort}
															</td>
															<td>
																${e.pointName}
															</td>
															<td>
																${e.sampCode}
															</td>
															<td colspan="2">
																${e.itemName}
															</td>
														 	<td>
																<c:choose>
																	<c:when test="${e.type=='气'}">
																		浓度：${e.value}<br>
																		速率：${e.value2}
																	</c:when>
																	<c:otherwise>
																		${e.value}
																	</c:otherwise>
																</c:choose>
															</td>
															<td>${e.limited}</td>
															<td>${e.limitLine}</td>
															<c:if test="${vo.taskVo.pj=='是'}">
																<td>
																	${e.result}
																</td>
															</c:if>
															<td>
																${e.methodName}
															</td>
															<td>
																${e.appName}
															</td>
														</tr>
														<c:set var="num" value="${num+1}"/>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:forEach>
									</tbody>
								</table>
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
function fnShowReport(){
	POBrowser.openWindow('${basePath}bus/report/showReport.do?id=${vo.id}','width=1200px;height=800px;');
}
function fnShowWord(){
	POBrowser.openWindow('${basePath}bus/taskData/showBugFile.do?id=${vo.taskVo.id}','width=1200px;height=800px;');
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
