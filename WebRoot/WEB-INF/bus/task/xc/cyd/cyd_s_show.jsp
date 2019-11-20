<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../../include/css.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
.form-control{
	padding: 4px;
}
.table > thead > tr > th,.table > tbody > tr > td{
	text-align: center;
}
</style>
</head>
<body >
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="updateTaskPoint.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					 <table style="margin-bottom: 5px;width: 100%">
						<tr>
							<td style="width: 12%;text-align: right;">校准器名称：</td>
							<td style="width:20%;padding: 2px;">
								${vo.deme1}
							</td>
							<td style="width: 12%;text-align: right;">功能区类别：</td>
							<td style="width: 20%;padding: 2px;">
								${vo.gnq}
							</td>
							<td style="width: 12%;text-align: right;">测量时工况：</td>
							<td style="padding: 2px;">
								${vo.deme2}
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;气：</td>
							<td style="padding: 2px;">
								${vo.tx}
							</td>
							<td style="text-align: right;">风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向：</td>
							<td style="padding: 2px;">
								${vo.fx}
							</td>
							<td style="text-align: right;">风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：</td>
							<td style="padding: 2px;">
								${vo.fs}
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">测量仪器：</td>
							<td style="padding: 2px;">
								${vo.cyAppName}
							</td>
							<td style="text-align: right;">监测方法：</td>
							<td style="padding: 2px;">
								${vo.cyStandName}
							</td>
							<td style="text-align: right;">其他描述：</td>
							<td style="padding: 2px;">
								${vo.xcDesc}
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
						</tr>
					</table>
					<div style="overflow-x: auto;">
						<table class="table table-bordered" style="margin-bottom: 0px; ">
							<thead>
								<tr>
									<th width="50">
										序号
									</th>
									<th >测点名称</th>
									<th >测点编号</th>
									<th >开始时间</th>
									<th >结束时间</th>
									<th >昼夜类型</th>
									<th>声源类型</th>
									<th>校准前示值</th>
									<th>校准后示值</th>
									<th>Leq</th>
									<th>L10</th>
									<th>L50</th>
									<th>L90</th>
									<th>Lmax</th>
									<th>Lmin</th>
									<th>标准偏差SD</th>
									<th>监测项目</th>
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
									 		${e.sampCode}
								 		</td>
								 		<td>
								 			${e.cyTime}
								 		</td>
								 		<td>
								 			${e.cyEndTime}
								 		</td>
								 		<td>
								 			${e.fcType}
								 		</td>
								 		<td>
								 			${e.recordVo.demo1}
								 		</td>
								 		<td>
								 			${e.recordVo.demo2}
								 		</td>
								 		<td>
								 			${e.recordVo.demo3}
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
								 			${e.recordVo.avg1}
								 		</td>
								 		<td>
								 			${e.recordVo.avg2}
								 		</td>
								 		<td>
								 			${e.recordVo.demo10}
								 		</td>
								 		<td>
								 			${e.itemNames}
								 		</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../../include/js.jsp"%>
</body>
</html>
