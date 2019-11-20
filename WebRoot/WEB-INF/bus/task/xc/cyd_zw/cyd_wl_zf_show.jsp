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
							<td style="text-align: right;">测&nbsp;量&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
									${vo.fxName}
							</td>
							<td style="text-align: right;">校&nbsp;对&nbsp;&nbsp;人：</td>
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
							<td style="text-align: right;">修正参数：</td>
							<td style="padding: 2px;">
									${vo.deme1}
							</td>
						</tr>
					</table>
					<table class="table table-bordered" style="margin-bottom: 0px;">
						<thead>
							<tr>
								<th rowspan="3" width="50">序号</th>
								<th rowspan="3" width="8%">车间/岗位</th>
								<th rowspan="3" width="8%">检测点/检测对象</th>
								<th rowspan="3" width="80">测量时间</th>
								<th rowspan="3" width="100">紫外光谱分类</th>
								<th rowspan="3" width="100">测量部位</th>
								<th rowspan="3" width="80">工人巡检停留时间/巡检次数</th>
								<th rowspan="3" width="80">接触时间(h/d)</th>
								<th  colspan="7">测量结果(μW/cm2)</th>
								<th rowspan="3" width="80">备注</th>
							</tr>
							<tr>
								<th colspan="2" >EA(UVA)</th>
								<th colspan="2" >EB(UVB)</th>
								<th colspan="2" >EC(UVC)</th>
								<th rowspan="2" width="70">Eeff</th>
								<th rowspan="2">检测项目</th>
							</tr>
							<tr>
								<th width="70">测量值</th>
								<th width="70">修正<br>结果</th>
								<th width="70">测量值</th>
								<th width="70">修正<br>结果</th>
								<th width="70">测量值</th>
								<th width="70">修正<br>结果</th>
							</tr>
						</thead>
						<tbody id="samp_tb">
							<c:forEach items="${vo.sampList}" var="e" varStatus="v">
								<tr>
							 		<td align="center">
							 			${v.index+1}
							 		</td>
							 		<td>
							 			${e.pointVo.room}
							 		</td>
						    		<td>
							 			${e.pointName}
							 		</td>
							 		 <td>
								 			${e.cyTime}
							 		</td>
							 		<td>
								 		   ${e.recordVo.pl}
							 		</td>
							 		<td>
								 		   ${e.fcType}
							 		</td>
							 		<td>
								 			${e.workPc}
							 		   
							 		</td>
							 		<td>
								 			${e.workHours}
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
								 			${e.recordVo.avg1}
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
					<div class="hr-line-dashed"></div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../../include/js.jsp"%>
</body>
</html>
