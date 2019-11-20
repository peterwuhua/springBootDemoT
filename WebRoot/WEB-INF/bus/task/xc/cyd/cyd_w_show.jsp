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
.table > thead > tr > th,.table > tbody > tr > td{
	text-align: center;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="updateTaskPoint.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table style="margin-bottom: 5px;width: 100%">
						<tr>
							<td style="width: 8%;text-align: right;">天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;气：</td>
							<td style="width:17%;padding: 2px;">
								${vo.tx}
							</td>
							<td style="width: 8%;text-align: right;">气&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;温：</td>
							<td style="width: 17%;padding: 2px;">
								${vo.qw} ℃
							</td>
							<td style="width: 8%;text-align: right;">气&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;压：</td>
							<td style="width: 17%;padding: 2px;">
								${vo.qy} kPa
							</td>
							<td style="width: 8%;text-align: right;">采&nbsp;样&nbsp;&nbsp;人：</td>
							<td style="width: 17%;padding: 2px;">
								${vo.cyName}
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">采样方法：</td>
							<td style="padding: 2px;">
								${vo.cyStandName}
							</td>
							<td style="text-align: right;">采样设备：</td>
							<td style="padding: 2px;">
								${vo.cyAppName}
							</td>
							<td style="text-align: right;">校&nbsp;核&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
								${vo.jhName}
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">pH值标准溶液值：</td>
							<td style="padding: 2px;">
								${vo.deme1}
							</td>
							<td style="text-align: right;">pH值实际测定值：</td>
							<td style="padding: 2px;">
								${vo.deme2}
							</td>
							<td style="text-align: right;">标准溶液值：</td>
							<td style="padding: 2px;">
								${vo.deme3}
							</td>
							<td style="text-align: right;">实际测定值：</td>
							<td style="padding: 2px;">
								${vo.deme4}
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">溶解氧溶液：</td>
							<td style="padding: 2px;">
								${vo.deme5}
							</td>
							<td style="text-align: right;">满度校准：</td>
							<td style="padding: 2px;">
								${vo.deme6}
							</td>
							<td style="text-align: right;">其他描述：</td>
							<td style="padding: 2px;">
								${vo.xcDesc}
							</td>
							<td style="text-align: right;">受检单位陪同人：</td>
							<td style="padding: 2px;">
								${vo.ptUser}
							</td>
						</tr>
					</table>
					<div style="overflow-x: auto;">
						<table class="table table-bordered" style="margin-bottom: 0px;">
							<thead>
								<tr>
									<th width="60">序号</th>
									<th width="100">采样地点</th>
									<th width="100">采样时间</th>
									<th width="130">样品编号</th>
									<th width="25%">样品感观性状</th>
									<th width="100">样品数量</th>
									<th width="100">流速</th>
									<th width="100">水位</th>
									<th width="100">取样量</th>
									<th>分析项目</th>
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
								 			${e.cyTime}
								 		</td>
								 		<td>
								 		 ${e.sampCode}
								 		</td>
								 		<td>
								 			${e.xz}
								 		</td>
								 		<td>
								 			${e.num}
								 		</td>
								 		<td>
								 			${e.recordVo.v3}
								 		</td>
								 		<td>
								 			${e.recordVo.demo1}
								 		</td>
								 		<td>
								 			${e.recordVo.demo7}
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
