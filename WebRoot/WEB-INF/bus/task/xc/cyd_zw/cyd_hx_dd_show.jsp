<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../../include/css.jsp"%>
<%@ include file="../../../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
.form-control{
	padding: 4px;
}
.table>thead>tr>th{
	text-align: center;
}
.table>tbody>tr>td{
	text-align: center;
}
</style>
</head>
<body style="overflow: auto;">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="updateTaskPoint.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table style="margin-bottom: 5px;width: 100%">
						<tr>
							<td style="text-align: right;">温&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td>
							<td style="padding: 2px;">
								 ${vo.qw}
							</td>
							<td style="text-align: right;">相对湿度：</td>
							<td style="padding: 2px;">
								${vo.sd}
							</td>
							<td style="text-align: right;">气压：</td>
							<td style="padding: 2px;">
								${vo.qy}
							</td>
							<td style="text-align: right;">风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：</td>
							<td style="padding: 2px;">
								${vo.fs}
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">采&nbsp;样&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
								${vo.cyName}
							</td>
							<td style="text-align: right;">校&nbsp;核&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
								${vo.jhName}
							</td>
							<td style="text-align: right;">测量仪器：</td>
							<td style="padding: 2px;">
								${vo.cyAppName}
							</td>
							<td style="text-align: right;">现场描述：</td>
							<td style="padding: 2px;">
								${vo.xcDesc}
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">采样方法：</td>
							<td style="padding: 2px;">
								${vo.cyStandName}
							</td>
							<td style="text-align: right;">受检单位陪同人：</td>
							<td style="padding: 2px;">
								${vo.ptUser}
							</td>
						</tr>
					</table>
					<table class="table table-bordered" style="margin-bottom: 0px;">
						<thead>
							<tr>
								<th rowspan="2" width="140">样品编号</th>
								<th rowspan="2" >仪器编号</th>
								<th rowspan="2" >车间/岗位</th>
								<th rowspan="2" >检测点</th>
								<th rowspan="2" >工人巡检停留时间/巡检次数</th>
								<th rowspan="2" >接触时间</th>
								<th colspan="2">采样流量</th>
								<th colspan="2">采样时间</th>
								<th rowspan="2" >采样体积</th>
								<th rowspan="2" >标准体积</th>
								<!-- <th rowspan="2" >采样前滤膜质量</th>
								<th rowspan="2" >采样后滤膜质量</th> -->
								<th rowspan="2" >备注</th>
								<th rowspan="2">检测项目</th>
							</tr>
							<tr>
								<th width="70">采样前</th>
								<th width="70">采样后</th>
								<th width="70">开始时间</th>
								<th width="70">结束时间</th>
							</tr>
						</thead>
						<tbody id="samp_tb">
							<c:forEach items="${vo.sampList}" var="e" varStatus="v">
								<tr>
							 		<td>
							 			${e.sampCode}
							 		</td>
							 		<td>
							 			${e.recordVo.demo1}
							 		</td>
							 		<td>
							 			${e.pointVo.room}
							 		</td>
							 		<td>
							 			${e.pointName}
							 		</td>
							 		<td>
								 		${e.workPc}
							 		</td>
							 		<td>
								 		${e.workHours}
							 		</td>
							 		<td>
								 		${e.recordVo.demo2}
							 		</td>
							 		<td>
								 		${e.recordVo.demo3}
							 		</td>
							 		<td>
								 		${e.cyTime}
							 		</td>
							 		<td>
								 		${e.cyEndTime}
							 		</td>
							 		<td>
								 		${e.num}
							 		</td>
							 		<td>
								 		${e.tj}
							 		</td>
							 		<!-- <td>
								 		${e.v1}
							 		</td>
							 		<td>
								 		${e.v2}
							 		</td> -->
							 		<td>
								 		${e.remark}
							 		</td>
							 		<td>
							 			${e.itemNames}
							 		</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../../include/js.jsp"%>

</body>
</html>
