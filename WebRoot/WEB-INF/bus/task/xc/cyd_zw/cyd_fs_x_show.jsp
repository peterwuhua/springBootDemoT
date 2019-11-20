<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
 <link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
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
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="updateTaskPoint.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table style="margin-bottom: 5px;width: 100%">
						<tr>
							<td style="text-align: right;">测&nbsp;试&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;width:300px;">
									${vo.fxName}
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
							<td style="text-align: right;">检测依据：</td>
							<td style="padding: 2px;">
								${vo.cyStandName}
							</td>
							<td style="text-align: right;">受检(委托)单位陪同人：</td>
							<td style="padding: 2px;">
								${vo.ptUser}
							</td>
						</tr>
					</table>
					<table class="table table-bordered" style="margin-bottom: 0px;">
						<thead>
							<tr>
								<th width="60" rowspan="2">序号</th>
								<th width="10%" rowspan="2">检测地点</th>
								<th width="10%" rowspan="2">工作场所探伤机设备型号、名称和参数</th>
								<th width="10%" rowspan="2">生产情况及劳动者个体防护措施</th>
								<th width="80" rowspan="2">接触时间(h/d)</th>
								<th width="80" rowspan="2">测量时间</th>
								<th width="80" rowspan="2">气温(℃)</th>
								<th width="80" rowspan="2">相对湿度(%)</th>
								<th width="80" rowspan="2">测量方位</th>
								<th width="200" rowspan="2">空气比释动能率(μGy·h-1)</th>
								<th width="150" rowspan="2">备注</th>
								<th width="150" rowspan="2">检测项目</th>
							</tr>
						</thead>
						<tbody id="samp_tb">
							<c:forEach items="${vo.sampList}" var="e" varStatus="v">
								<tr>
							 		<td align="center">
							 			${v.index+1}
							 			<input type="hidden" name="sampList[${v.index}].id" value="${e.id}">
							 			<input type="hidden" name="sampList[${v.index}].recordVo.id" value="${e.recordVo.id}">
							 		</td>
							 		<td>
							 			${e.pointName}
							 		</td>
							 		<td>
								 			${e.recordVo.demo1}
							 		</td>
							 		<td>
								 			${e.recordVo.demo2}
							 		</td>
							 		<td><!-- 接触时间 -->
								 			${e.workHours}
							 		</td>
							 		<td><!-- 测量时间 -->
								 			${e.cyTime}
							 		</td>
							 		<td><!-- 气温 -->
								 			${e.recordVo.demo3}
							 		</td>
							 		<td><!-- 相对湿度 -->
								 			${e.recordVo.demo4}
							 		</td>
							 		<td><!-- 测量方位 -->
								 			${e.recordVo.demo5}
							 		</td>
							 		<td><!-- 空气比释动能率 -->
								 			${e.recordVo.demo6}
							 		</td>
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
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../../include/js.jsp"%>
 <!-- Chosen -->
<script src="${basePath}h/js/plugins/chosen/chosen.jquery.js"></script>
  <!-- Input Mask-->
<script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
</body>
</html>
