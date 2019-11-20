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
.form-control {
	padding: 2px;
}

.table>tbody>tr>td {
	padding: 2px;
}

.table>tbody>tr>td>input, .table>tbody>tr>td>select {
	height: 100%;
	padding: 2px;
}
</style>
</head>
<body style="overflow-x: auto;">
	<div class="col-sm-12" style="min-width: 2700px">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="updateTaskPoint.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table style="margin-bottom: 5px; width: 100%">
						<tr>
							<td style="width:150px; text-align: right;">排放设备：</td>
							<td style="padding: 2px;">${vo.deme1}</td>
							<td style="width:150px; text-align: right;">设备型号：</td>
							<td style="padding: 2px;">${vo.deme2}</td>
							<td style="width:150px; text-align: right;">处理设施：</td>
							<td style="padding: 2px;">${vo.deme3}</td>
							<td style="width:150px; text-align: right;">设施型号：</td>
							<td style="padding: 2px;">${vo.deme4}</td>
						</tr>
						<tr>
							<td style="text-align: right;">排气筒内径：</td>
							<td style="padding: 2px;">${vo.deme5}</td>
							<td style="text-align: right;">排气筒高：</td>
							<td style="padding: 2px;">${vo.deme6}</td>
							<td style="text-align: right;">截&nbsp;面&nbsp;&nbsp;积：</td>
							<td style="padding: 2px;">${vo.deme7}</td>
							<td style="text-align: right;">启用时间：</td>
							<td style="padding: 2px;">${vo.deme8}</td>
						</tr>
						<tr>
							<td style="text-align: right;">运行负荷：</td>
							<td style="padding: 2px;">${vo.deme9}</td>
							<td style="text-align: right;">出力系数K：</td>
							<td style="padding: 2px;">${vo.deme10}</td>
							<td style="text-align: right;">采样嘴直径：</td>
							<td style="padding: 2px;">${vo.deme11}</td>
							<td style="text-align: right;">使用的燃料：</td>
							<td style="padding: 2px;">${vo.deme12}</td>
						</tr>
						<c:if test="${fn:contains(vo.itemNames,'黑度')}">
							<tr>
								<td style="text-align: right;">烟囱所在方向：</td>
								<td style="padding: 2px;">${vo.deme13}</td>
								<td style="text-align: right;">出口形状：</td>
								<td style="padding: 2px;">${vo.deme14}</td>
								<td style="text-align: right;">烟羽背景：</td>
								<td style="padding: 2px;">
									${vo.deme15}
								</td>
								<td style="text-align: right;">烟囱距离：</td>
								<td style="padding: 2px;">${vo.deme16}</td>
							</tr>
						</c:if>
						<c:if test="${fn:contains(vo.itemNames,'油烟')}">
							<tr>
								<td style="text-align: right;">风机设计风量：</td>
								<td style="padding: 2px;">${vo.deme17}</td>
								<td style="text-align: right;">实测排风量：</td>
								<td style="padding: 2px;">${vo.deme18}</td>
								<td style="text-align: right;">灶头总数：</td>
								<td style="padding: 2px;">${vo.deme19}</td>
								<td style="text-align: right;">实测灶头数：</td>
								<td style="padding: 2px;">${vo.deme20}</td>
							</tr>
							<tr>
								<td style="text-align: right;">折算灶头数：</td>
								<td style="padding: 2px;">${vo.deme21}</td>
								<td style="text-align: right;">规模：</td>
								<td style="padding: 2px;">${vo.deme22}</td>
								<td style="text-align: right;">净化方式：</td>
								<td style="padding: 2px;">${vo.deme23}</td>
								<td style="text-align: right;">投影面积：</td>
								<td style="padding: 2px;">${vo.deme24}</td>
							</tr>
						</c:if>
						<tr>
							<td style="text-align: right;">采&nbsp;样&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
								${vo.cyName}
							</td>
							<td style="text-align: right;">校&nbsp;核&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
								${vo.jhName}
							</td>
							<td style="text-align: right;">现场描述：</td>
							<td style="padding: 2px;">${vo.xcDesc}</td>
							<td style="text-align: right;">受检单位陪同人：</td>
							<td style="padding: 2px;">
								${vo.ptUser}
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
						</tr>
					</table>
					<div style="overflow-x: auto;">
						<table class="table table-bordered" style="margin-bottom: 0px;">
							<thead>
								<tr>
									<th width="60">序号</th>
									<th width="130">样品编号</th>
									<th width="100">开始时间</th>
									<th width="100">结束时间</th>
									<th width="80">采样流量</th>
									<th width="80">浓度</th>
									<th width="80">排放速率</th>
									<th width="80">动压值</th>
									<th width="80">烟气流速</th>
									<th width="80">标态气量</th>
									<th width="80">相对湿度</th>
									<th width="80">烟气温度</th>
									<th width="80">含湿量</th>
									<th width="80">含氧量</th>
									<th width="80">静压值</th>
									<th width="80">烟气密度</th>
									<th width="80" style="text-align: center;">皮托管<br>系数
									</th>
									<th width="80" style="text-align: center;">过量空<br>气系数
									</th>
									<th width="80">测态气量</th>
									<th width="100">天气</th>
									<th width="100">风向</th>
									<th width="80">风速</th>
									<th width="80">大气压</th>
									<th width="80">气温</th>
									<th width="80">采样体积</th>
									<th width="80">标态体积</th>
									<th width="80">级数</th>
									<th>监测项目</th>
								</tr>
							</thead>
							<tbody id="samp_tb">
								<c:forEach items="${vo.sampList}" var="e" varStatus="v">
									<tr>
										<td align="center">${v.index+1} 
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
												${e.recordVo.demo4}
										</td>
										<td>
												${e.recordVo.demo5}
										</td>
										<td>
												${e.recordVo.demo19}
										</td>
										<td>
												${e.recordVo.demo20}
										</td>
										<td>
												${e.recordVo.demo22}
										</td>
										<td>
												${e.recordVo.demo11}
										</td>
										<td>
												${e.recordVo.demo12}
										</td>
										<td>
												${e.recordVo.demo13}
										</td>
										<td>
												${e.recordVo.demo14}
										</td>
										<td>
												${e.recordVo.demo15}
										</td>
										<td>
												${e.recordVo.demo16}
										</td>
										<td>
												${e.recordVo.demo17}
										</td>
										<td>
												${e.recordVo.demo18}
										</td>
										<td>
												${e.recordVo.demo21}
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
												${e.recordVo.demo2}
										</td>
										<td>
												${e.recordVo.demo3}
										</td>
										<td>
												${e.num}
										</td>
										<td>${e.itemNames}</td>
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
	 <!-- Chosen -->
<script src="${basePath}h/js/plugins/chosen/chosen.jquery.js"></script>

</body>
</html>
