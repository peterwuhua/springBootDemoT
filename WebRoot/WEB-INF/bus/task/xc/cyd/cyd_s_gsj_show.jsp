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
							<td style="width: 10%;text-align: right;">校准器名称：</td>
							<td style="width:23%;padding: 2px;">
								${vo.deme4}
							</td>
							<td style="width: 10%;text-align: right;">校准器声级值：</td>
							<td style="width: 23%;padding: 2px;">
								${vo.deme5}
							</td>
							<td style="width: 10%;text-align: right;">测定范围：</td>
							<td style="padding: 2px;">
								${vo.deme6}
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;气：</td>
							<td style="padding: 2px;">
								${vo.deme1}
							</td>
							<td style="text-align: right;">风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向：</td>
							<td style="padding: 2px;">
								${vo.deme2}
							</td>
							<td style="text-align: right;">风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：</td>
							<td style="padding: 2px;">
								${vo.deme3}
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
							<td style="width: 10%;text-align: right;">功能区类别：</td>
							<td style="padding: 2px;">
								${vo.gnq}
							</td>
						</tr>
					</table>
					<div style="overflow-x: auto;">
						<table class="table table-bordered" style="margin-bottom: 0px;">
							<thead>
								<tr>
									<th width="50">
										序号
									</th>
									<th width="100px">测点名称</th>
									<th width="100px">编号</th>
									<th  width="110px">主要声源</th>
									 <th width="170px">测量开始时间</th>
									<th width="170px">测量结束时间</th>
									<th >昼夜类型</th>
									<th width="80px">测量值</th>
									<th width="80px">监测前校准值</th>
									<th width="80px">监测后校准值</th>
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
								 			${e.recordVo.demo1}
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
								 			${e.recordVo.v1}
								 		</td>
								 		<td>
								 			${e.recordVo.demo2}
								 		</td>
								 		<td>
								 			${e.recordVo.demo3}
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
