<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
.form-control{
	padding: 4px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><a href="page.do">采样准备</a></li>
						<li><strong>查看</strong></li>
					</ol>
				</h5>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active"  style="width: 150px;"><label class="pull-right">任务编号:</label></td>
							<td>
								<a onclick="fnTask('${vo.id}','${vo.no}');">${vo.no}</a>
							</td>
							 <td class="active"  style="width: 150px;"><label class="pull-right">受检单位:</label></td>
							<td>
								${vo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
							<td>
								${vo.custVo.custUser}
							</td>
							<td class="active"><label class="pull-right">联系电话:</label></td>
							<td>
								${vo.custVo.custTel}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">单位地址:</label></td>
							<td colspan="3">
								${vo.custVo.custAddress}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>
								${vo.sampName}
							</td>
						</tr>
						<tr>
							 
							<td class="active"><label class="pull-right">受理日期:</label></td>
							<td>
								${vo.date}
							</td>
							<td class="active"><label class="pull-right">要求完成日期:</label></td>
							<td>
								${vo.finishDate}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">采样开始日期:</label></td>
							<td class="width-35">
								${vo.cyDate}
							</td>
							<td class="width-15 active"><label class="pull-right">采样结束日期:</label></td>
							<td class="width-35">
								${vo.cyEndDate}
							</td>
						</tr>
						<tr>
							<td class="active">
								<label class="pull-right ">采样设备:</label>
							</td>
							<td>
								${vo.cyAppNames}
								<%-- <div class="chosen-container chosen-container-multi chosen-with-drop" style="height: 100%;width: 100%" title="采样设备">
									<ul class="chosen-choices" id="appUL">
										<c:forEach items="${vo.appList}" var="e" varStatus="v">
											<li class="search-choice" index="${v.index}">
												<span>${e.appName}</span>
		          								<input name="appList[${v.index}].id" value="${e.id}" type="hidden" />
		          								<input name="appList[${v.index}].appId" value="${e.appId}" type="hidden" />
		          								<input name="appList[${v.index}].appName" value="${e.appName}" type="hidden" />
	          								</li>
										</c:forEach>
									</ul>
								</div> --%>
							</td>
							<td class="active"><label class="pull-right ">车辆预约:</label></td>
							<td>
								<div class="chosen-container chosen-container-multi chosen-with-drop" style="height: 100%;width: 100%" title="采样设备">
									<ul class="chosen-choices" id="carUL">
										<c:forEach items="${vo.carList}" var="e" varStatus="v">
											<li class="search-choice" index="${v.index}">
												<span>${e.name}(${e.code})</span>
		          								<input name="carList[${v.index}].id" value="${e.id}" type="hidden" />
		          								<input name="carList[${v.index}].carId" value="${e.carId}" type="hidden" />
		          								<input name="carList[${v.index}].name" value="${e.name}" type="hidden" />
	          								</li>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right"> 采样小组人员:</label></td>
							<td class="width-35">
								${vo.cyName}
							</td>
							<td class="active"><label class="pull-right">负&nbsp;&nbsp;责&nbsp;人:</label></td>
							<td>
								${vo.fzName}
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>样品信息</b>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 0px;">
								<table class="table table-bordered" style="margin-bottom: 0px;border: 0px;">
									<thead>
										<tr>
											<th width="60px">序号</th>
											<th width="15%">检测点位</th>
											<th width="15%">样品名称</th>
											<th width="20%" style="text-align: center;">样品编号</th>
											<th>检测项目</th>
											<th>使用容器及仪器</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="num" value="0"/>
										<c:forEach items="${vo.tpList}" var="m">
										<c:forEach items="${m.sampList}" var="e" varStatus="v">
											<tr>
												<td>
													${e.sort}
													<input name="sampList[${num}].id" value="${e.id}" type="hidden" />
												</td>
												<td>
													${e.pointName}
												</td>
												<td>
													${e.sampName}
												</td>
												<td>
													${e.sampCode}
												</td>
												<td>
													${e.itemNames}
												</td>
												<td>
													<c:forEach items="${e.containerList}" var="e1" varStatus="v1">
						                             	<c:if test="${v1.index!=0}"><br></c:if>${e1.container}  / ${e1.num}
					                                </c:forEach>
												</td>
											</tr>
											<c:set var="num" value="${num+1}"/>
										</c:forEach>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>采样规范</b>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 0px;">
								<table class="table table-bordered" style="margin-bottom: 0px;border: 0px;">
									 <thead>
									 	<tr>
									 		<th style="width: 50px;">序号</th>
										 	<th style="min-width: 100px;">项目名称</th>
									 		<th style="min-width: 100px;">采样标准</th>
									 		<th style="min-width: 100px;">采样仪器</th>
									 		<th style="min-width: 100px;">采样容器</th>
									 		<th style="min-width: 70px;">采样流量</th>
									 		<th style="min-width: 70px;">采样时长</th>
									 		<th style="min-width: 70px;">采样体积</th>
									 		<th style="min-width: 70px;">存储时间</th>
									 		<th style="min-width: 100px;">运输、存储条件</th>
									 		<th style="min-width: 50px;">备注</th>
									 	</tr>
									 </thead>
									 <tbody>
									 	<c:forEach items="${sopList}" var="e" varStatus="v">
									 		<tr>
									 			<td>${v.index+1}</td>
									 			<td>${e.itemVo.name}</td>
									 			<td>${e.methodVo.code}</td>
									 			<td>${e.cyAppName}</td>
									 			<td>${e.ctName}</td>
									 			<td align="center">${e.cyll}</td>
									 			<td align="center">${e.cysc}</td>
									 			<td align="center">${e.cytj}</td>
									 			<td align="center">${e.bcsj}</td>
									 			<td>${e.cctj}</td>
									 			<td>${e.remark}</td>
									 		</tr>
									 	</c:forEach>
									 </tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>准备人信息</b>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">准&nbsp;备&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								${vo.zbName}
							</td>
							<td class="width-15 active"><label class="pull-right"> 准备日期:</label></td>
							<td class="width-35">
								${vo.zbDate}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.zbMsg}
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-info" type="button" onclick="fnPrint4Sop()"><i class="fa fa-print" aria-hidden="true"></i> 打印采样规范</a>
							<a class="btn btn-w-m btn-info" type="button" onclick="fnPrint4Ct()"><i class="fa fa-print" aria-hidden="true"></i> 打印耗材清单</a>
							<a class="btn btn-w-m btn-info" type="button" onclick="fnPrint4Tm()"><i class="fa fa-print" aria-hidden="true"></i> 打印编号条码</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
<%@ include file="../../../include/js.jsp"%>
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
//打印
function fnPrint4Tm(){
	parent.layer.open({
		title:'条码打印',	
		type: 2,
		 area: ['900px', '500px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}bus/taskJj/print.do?id=${vo.id}'
	});
}
 
function fnPrint4Sop(){
	parent.layer.open({
		title:'采样规范打印',	
		type: 2,
		 area: ['80%', '90%'],
		fix: false, //不固定
		maxmin: true,
		content:'${basePath}bus/taskZb/printSop.do?id=${vo.id}'
	});
}
function fnPrint4Ct(){
	var	url='${basePath}bus/taskZb/printCt.do?id=${vo.id}';
	parent.layer.open({
		title:'耗材清单',	
		type: 2,
		 area: ['800px', '90%'],
		fix: false, //不固定
		maxmin: true,
		content: url
	});
}
</script>
</html>
