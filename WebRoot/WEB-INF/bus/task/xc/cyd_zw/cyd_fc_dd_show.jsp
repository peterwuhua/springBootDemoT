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
	padding: 2px;
}
</style>
</head>
<body style="overflow: auto;">
	<div class="col-sm-12" style="min-width: 2600px;">
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
							<td style="text-align: right;">校&nbsp;对&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
									${vo.jhName}
							</td>
							<td style="text-align: right;">测量仪器：</td>
							<td style="padding: 2px;">${vo.cyAppName}
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">采样方法：</td>
							<td style="padding: 2px;">
								<div class="input-group" style="width: 100%">
									<input id="cyStandId" name="cyStandId" value="${vo.cyStandId}" type="hidden" /> 
									${vo.cyStandName}
								</div>
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
								<th rowspan="2" width="100">仪器编号</th>
								
								<th rowspan="2" width="150">车间/岗位</th>
								<th rowspan="2" width="150">检测点/检测对象</th>
								
								<th rowspan="2" width="80">工人巡检停留时间/巡检次数</th>
								<th rowspan="2" width="80">接触时间(h/d)</th>
								<th colspan="2" >采样流量(L/min)</th>
								<th rowspan="2"  width="100">开始时间</th>
								<th rowspan="2"  width="100">结束时间</th>
								<th rowspan="2" width="100">采样体积(L)</th>
								<th rowspan="2" width="100">标准体积(L)</th>
								<th rowspan="2">采样前滤膜质量</th>
								<th rowspan="2"  width="100">粉尘种类</th>
								<th rowspan="2"  width="120">备注</th>
							</tr>
							<tr>
								<th width="100">采样前</th>
								<th width="100">采样后</th>
							</tr>
						</thead>
						<tbody id="samp_tb">
							<c:forEach items="${vo.sampList}" var="e" varStatus="v">
								<tr>
							 		<td>
							 			<input type="hidden" name="sampList[${v.index}].id" value="${e.id}">
							 			<input type="hidden" name="sampList[${v.index}].recordVo.id" value="${e.recordVo.id}">
							 			<input type="hidden" id="cyHours_${v.index}" name="sampList[${v.index}].pointVo.cyHours" value="${e.pointVo.cyHours}">
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
							 		<td><!-- 采样体积 -->
								 			${e.recordVo.demo2}
							 		</td>
							 		<td>
								 			${e.recordVo.demo3}
							 		</td>
							 		<td><!-- 开始时间 -->
								 			${e.cyTime}
							 		</td>
							 		<td><!-- 结束时间 -->
								 			${e.cyEndTime}
							 		</td>
							 		<td>
							 			${e.num}
							 		</td>
							 		<td>
							 			${e.tj}
							 		</td>
							 		<td><!-- 检测结果 -->
								 		${e.recordVo.v1}
							 		</td>
							 		<td>
							 			${e.fcType}
							 		</td>
							 		<td>
							 			${e.remark}
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
