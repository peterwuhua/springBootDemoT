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
					<table style="margin-bottom: 5px; width: 100%">
						<tr>
							<td style="width: 9%; text-align: right;">功能区类：</td>
							<td style="width: 16%; padding: 2px;">
								${vo.gnq}
							</td>
							<td style="width: 8%; text-align: right;">流量校准值：</td>
							<td style="width: 17%; padding: 2px;">
								${vo.deme1}
							</td>
							<td style="width: 8%; text-align: right;">校准日期：</td>
							<td style="width: 17%; padding: 2px;">
								${vo.deme2}
							</td>
							<td style="width: 7%; text-align: right;">其他描述：</td>
							<td style="width: 18%; padding: 2px;">
								${vo.xcDesc}
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
							<td style="text-align: right;">采样设备：</td>
							<td style="padding: 2px;">
								${vo.cyAppName}
							</td>
							<td style="text-align: right;">采样方法：</td>
							<td style="padding: 2px;">
								${vo.cyStandName}
							</td>
						</tr>
					</table>
					<div style="overflow-x: auto;">
						<table class="table table-bordered" style="margin-bottom: 0px;">
							<thead>
								<tr>
									<th width="60">序号</th>
									<th>样品编号</th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th>采样流量</th>
									<th>采样体积</th>
									<th>标况/参<br>比体积</th>
									<th>天气</th>
									<th>风向</th>
									<th>风速</th>
									<th>大气压</th>
									<th>气温</th>
									<th>相对湿度</th>
									<th>监测项目</th>
								</tr>
							</thead>
							<tbody id="samp_tb">
								<c:forEach items="${vo.sampList}" var="e" varStatus="v">
									<tr>
										<td align="center">${v.index+1} <input type="hidden" name="sampList[${v.index}].id" value="${e.id}"> <input type="hidden" name="sampList[${v.index}].recordVo.id" value="${e.recordVo.id}">
										</td>
										<td>${e.sampCode}</td>
										<td>
											${e.cyTime}
										</td>
										<td>
											${e.cyEndTime}
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
											${e.recordVo.demo6}
										</td>
										<td>
											${e.recordVo.demo7}
										</td>
										<td>
											${e.recordVo.demo8}
										</td>
										<td>
											${e.recordVo.demo9}
										</td>
										<td>
											${e.recordVo.demo10}
										</td>
										<td>
											${e.recordVo.demo11}
										</td>
										<td>${e.itemNames}</td>
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
