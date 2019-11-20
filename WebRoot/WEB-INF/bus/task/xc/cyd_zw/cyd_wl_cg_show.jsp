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
							<td style="text-align: right;">测&nbsp;试&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
									${vo.fxName}
							</td>
							<td style="text-align: right;">校&nbsp;核&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
									${vo.jhName}
							</td>
							<td style="text-align: right;">测量仪器：</td>
							<td style="padding: 2px;">${vo.cyAppName}
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
							<td style="text-align: right;">修正参数：</td>
							<td style="padding: 2px;">
									${vo.deme1}
							</td>
							<td style="text-align: right;">测量类型：</td>
							<td style="padding: 2px;">
								${vo.itemType}
							</td>
						</tr>
					</table>
					<table class="table table-bordered" style="margin-bottom: 0px;">
						<thead>
							<tr>
								<th rowspan="2" width="50">序号</th>
								<th rowspan="2" width="10%">检测点</th>
								<th rowspan="2" width="80">测点高度/<br>采光等级</th>
								<th rowspan="2" width="80">测量时间</th>
								<th colspan="2" width="100">1</th>
								<th colspan="2" width="100">2</th>
								<th colspan="2" width="100">3</th>
								<th colspan="2" width="100">4</th>
								<th colspan="2" width="100">5</th>
								<th colspan="2" width="100">6</th>
								<th rowspan="2" >照度平均值Eav</th>
								<th rowspan="2" >照度均匀度U</th>
							</tr>
							<tr>
								<th width="6%">测量值</th>
								<th width="6%">修正结果</th>
								<th width="6%">测量值</th>
								<th width="6%">修正结果</th>
								<th width="6%">测量值</th>
								<th width="6%">修正结果</th>
								<th width="6%">测量值</th>
								<th width="6%">修正结果</th>
								<th width="6%">测量值</th>
								<th width="6%">修正结果</th>
								<th width="6%">测量值</th>
								<th width="6%">修正结果</th>
							</tr>
						</thead>
						<tbody id="samp_tb">
							<c:forEach items="${vo.sampList}" var="e" varStatus="v">
								<tr>
							 		<td align="center">
							 			${v.index+1}
							 		</td>
							 		<td>
							 			${e.pointName}
							 		</td>
							 		<td>
							 			${e.recordVo.pl}
							 		</td>
							 	    <td>
								 		${e.cyTime}
							 		</td>
							 		<td>
								 		${e.recordVo.v1}
							 		</td>
							 		<td>
								 		${e.recordVo.v2}
							 		</td>
							 		<td>
								 		${e.recordVo.v3}
							 		</td>
							 		<td>
								 		${e.recordVo.v4}
							 		</td>
							 		<td>
								 		${e.recordVo.v5}
							 		</td>
							 		<td>
								 		${e.recordVo.v6}
							 		</td>
							 		<td>
								 		${e.recordVo.v7}
							 		</td>
							 		<td>
								 		${e.recordVo.v8}
							 		</td>
							 		<td>
								 		${e.recordVo.v9}
							 		</td>
							 		<td>
								 		${e.recordVo.v10}
							 		</td>
							 		<td>
								 		${e.recordVo.v11}
							 		</td>
							 		<td>
								 		${e.recordVo.v12}
							 		</td>
							 		<td>
								 		${e.recordVo.avg1}
							 		</td>
							 		<td>
								 		${e.recordVo.avg2}
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
