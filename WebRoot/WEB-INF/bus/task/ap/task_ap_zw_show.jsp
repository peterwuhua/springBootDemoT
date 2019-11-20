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
						<li><a href="page.do">采样安排</a></li>
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
								${vo.no}
							</td>
							<td class="active"  style="width: 150px;"><label class="pull-right">单位名称:</label></td>
							<td>
								${vo.custVo.custName}
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
							<tr>
								<td class="width-15 active"><label class="pull-right"> 采样标准:</label></td>
								<td class="width-35" colspan="3">
									${vo.cyStandNames}
								</td>
							</tr>
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
							<td colspan="4">
								<b>检测点位信息配置</b>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th width="5%">序号</th>
											<th width="10%">车间/岗位</th>
											<th width="10%">检测点位</th>
											<th width="10%">样品名称</th>
											<th width="7%">采样数量</th>
											<th width="7%">采样频次</th>
											<th>检测项目</th>
										</tr>
									</thead>
									<tbody id="point_tb">
										<c:forEach items="${vo.tpList}" var="e" varStatus="v">
											<tr key="${v.index}" >
												<td>
													${e.sort}<input name="tpList[${v.index}].id" value="${e.id}" type="hidden" />
												</td>
												<td>
													${e.room}
												</td>
												<td>
													${e.pointName}
												</td>
												<td>
													${vo.sampName}
												</td>
												<td align="center">
													${e.sampNum}
												</td>
												<td align="center">
													${e.pc}
												</td>
												<td>
													${e.itemNames}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
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
											<th width="10%">车间/岗位</th>
											<th width="15%">检测点位</th>
											<th width="15%">样品名称</th>
											<th width="50px">批次</th>
											<th width="20%" style="text-align: center;">样品编号</th>
											<th>检测项目</th>
											<th>使用容器</th>
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
													${e.pointVo.room}
												</td>
												<td>
													${e.pointName}
												</td>
												<td>
													${e.sampName}
												</td>
												<td>
													${e.p}
													
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
								<b>安排人信息</b>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">安&nbsp;排&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								${vo.apName}
							</td>
							<td class="width-15 active"><label class="pull-right"> 安排日期:</label></td>
							<td class="width-35">
								${vo.apDate}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.apMsg}
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
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
</script>
</html>
