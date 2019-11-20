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
.table > thead > tr > th{
	text-align: center;
}
</style>
</head>
<body  style="overflow: auto;">
	<div class="col-sm-12" style=" min-width:1800px">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="updateTaskPoint.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table style="margin-bottom: 5px;width: 100%">
						<tr>
							<td style="width: 10%;text-align: right;">天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;气：</td>
							<td style="width:15%;padding: 2px;">
								${vo.tx}
							</td>
							<td style="width: 10%;text-align: right;">风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向：</td>
							<td style="width:15%;padding: 2px;">
								${vo.fx}
							</td>
							<td style="width: 10%;text-align: right;">风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：</td>
							<td style="width:15%;padding: 2px;">
								${vo.fs}
							</td>
							<td style="width: 10%;text-align: right;">气&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;温：</td>
							<td style="padding: 2px;">
								${vo.qw}
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">水域名称：</td>
							<td style="padding: 2px;">
								${vo.deme1}
							</td>
							<td style="text-align: right;">水域功能类别：</td>
							<td style="padding: 2px;">
								${vo.deme2}
							</td>
							<td style="text-align: right;">调&nbsp;查&nbsp;&nbsp;船：</td>
							<td style="padding: 2px;">
								${vo.deme3}
							</td>
							<td style="text-align: right;">固废类别：</td>
							<td style="padding: 2px;">
								${vo.deme4}
							</td>
						</tr>
						<tr>
							
							<td style="text-align: right;">采样仪器：</td>
							<td style="padding: 2px;">
								${vo.cyAppName}
							</td>
							<td style="text-align: right;">采样方法：</td>
							<td style="padding: 2px;">
								${vo.cyStandName}
							</td>
							<td style="text-align: right;">盛装容器：</td>
							<td style="padding: 2px;">
								${vo.deme5}
							</td>
							<td style="text-align: right;">污染状况：</td>
							<td style="padding: 2px;">
								${vo.deme6}
							</td>
						</tr>
						<tr>
							<td style="width: 6%;text-align: right;">采&nbsp;样&nbsp;&nbsp;人：</td>
							<td style="width: 19%;padding: 2px;">
								${vo.cyName}
							</td>
							<td style="text-align: right;">校&nbsp;核&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
								${vo.jhName}
							</td>
							<td style="text-align: right;">现场描述：</td>
							<td style="padding: 2px;">
								${vo.xcDesc}
							</td>
							<td style="text-align: right;">受检单位陪同人：</td>
							<td style="padding: 2px;">
								${vo.ptUser}
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">pH浸提剂：</td>
							<td style="padding: 2px;">
								${vo.deme10}
							</td>
						</tr>
					</table>
					<div style="overflow-x: auto;">
						<table class="table table-bordered" style="margin-bottom: 0px;">
							<thead>
								<tr>
									<th width="100px" rowspan="2">站位名称</th>
									<th width="120px" rowspan="2">站位编号</th>
									<th width="120px" rowspan="2">采样位置</th>
									<th width="100px" rowspan="2">采样时间</th>
									<th width="100px" rowspan="2">水深(m)</th>
									<th width="130px" rowspan="2">样品编号</th>
									<th colspan="4">样品描述</th>
									<th width="100px" rowspan="2">采样量</th>
									<th width="100px" rowspan="2">生物状况</th>
									<th width="100px" rowspan="2">周围环<br>境情况</th>
									<th rowspan="2">监测项目</th>
								</tr>
								<tr>
									<th width="100px">嗅味</th>
									<th width="100px">颜色</th>
									<th width="100px">表观</th>
									<th width="100px">其它</th>
								</tr>
							</thead>
							<tbody id="samp_tb">
								<c:forEach items="${vo.sampList}" var="e" varStatus="v">
									<tr>
									 	<td>
								 			${e.pointName}
								 		</td>
								 		<td>
									 			${e.recordVo.demo1}
								 		</td>
								 		<td>
									 			${e.recordVo.demo2}
								 		</td>
								 		<td>
									 			${e.cyTime}
								 		</td>
								 		<td>
									 			${e.recordVo.demo3}
								 		</td>
								 		<td>${e.sampCode}</td>
								 		<td>
									 			${e.recordVo.demo4}
								 		</td>
								 		<td>
										 		${e.recordVo.demo5}
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
								 			${e.itemNames}
								 		</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="hr-line-dashed"></div>					
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../../include/js.jsp"%>
</body>
</html>
