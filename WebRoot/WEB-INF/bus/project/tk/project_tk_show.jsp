<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
							<td class="width-35">
								${vo.projectVo.no}
							</td>
							<td class="width-15 active"><label class="pull-right">受检单位:</label></td>
							<td class="width-35">
								${vo.projectVo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.projectVo.taskType}
							</td>
							<td class="width-15 active"><label class="pull-right">样品类别:</label></td>
							<td class="width-35">
								${vo.projectVo.sampTypeName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">立项日期:</label></td>
							<td class="width-35">${vo.projectVo.rdate}</td>
							<td class="width-15 active"><label class="pull-right">拟完成日期:</label></td>
							<td class="width-35">${vo.projectVo.finishDate}</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.projectVo.remark}
							</td>
						</tr>
					</table>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
					  <legend>指派信息</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">建设单位名称:</label></td>
							<td class="width-35">
								${vo.buildUnit}
							</td>
							<td class="width-15 active"><label class="pull-right">项目名称:</label></td>
							<td class="width-35">
								${vo.name}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">项目环评时间:</label></td>
							<td class="width-35">
								${vo.itemHpTime}
							</td>
							<td class="width-15 active"><label class="pull-right">项目环评单位:</label></td>
							<td class="width-35">
								${vo.itemHpUnit}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">项目开工建设<br>及投产时间:</label></td>
							<td class="width-35">
								${vo.productionDate}
							</td>
							<td class="width-15 active"><label class="pull-right">环保设施设计<br>及施工单位:</label></td>
							<td class="width-35">
								${vo.constructUnit}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">项目设计生产能力:</label></td>
							<td class="width-35">
								${vo.designCapacity}
							</td>
							<td class="width-15 active"><label class="pull-right">项目目前生产能力:</label></td>
							<td class="width-35">
								${vo.nowCapacity}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">主管部门:</label></td>
							<td class="width-35">
								${vo.mainName}
							</td>
							<td class="width-15 active"><label class="pull-right">整体或分期验监测:</label></td>
							<td class="width-35">
								${vo.staging}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">废水排口共有:</label></td>
							<td class="width-35">
								${vo.wasteWater}
							</td>
							<td class="width-15 active"><label class="pull-right">废水治理措施:</label></td>
							<td class="width-35">
								${vo.wasteMeasures}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">雨水排口共有:</label></td>
							<td class="width-35">
								${vo.rain}
							</td>
							<td class="width-15 active"><label class="pull-right">雨水治理措施:</label></td>
							<td class="width-35">
								${vo.rainMeasures}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">废气排口共有:</label></td>
							<td class="width-35">
								${vo.exhaustGas}
							</td>
							<td class="width-15 active"><label class="pull-right">废气治理措施:</label></td>
							<td class="width-35">
								${vo.gasMeasures}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">固体废物治理措施:</label></td>
							<td class="width-35">
								${vo.solidWaste}
							</td>
							<td class="width-15 active"><label class="pull-right">噪声治理措施:</label></td>
							<td class="width-35">
								${vo.noise}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">废水处理能力:</label></td>
							<td class="width-35">
								${vo.wasteCapacity}
							</td>
							<td class="width-15 active"><label class="pull-right">废气处理能力:</label></td>
							<td class="width-35">
								${vo.gasCapacity}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">废水排口在<br>线装置情况:</label></td>
							<td class="width-35">
								${vo.wasteDevice}
							</td>
							<td class="width-15 active"><label class="pull-right">废气排口在<br>线装置情况:</label></td>
							<td class="width-35">
								${vo.gasDevice}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">应急预案及<br>事故应急池:</label></td>
							<td class="width-35">
								${vo.emergencyPlan}
							</td>
							<td class="width-15 active"><label class="pull-right">排污口设置<br>及规范化情况:</label></td>
							<td class="width-35">
								${vo.standardiz}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">固体废物处理<br>协议签订情况:</label></td>
							<td class="width-35">
								${vo.solidAgreement}
							</td>
							<td class="width-15 active"><label class="pull-right">废水处理协<br>议签订情况:</label></td>
							<td class="width-35">
								${vo.wasteAgreement}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">其他相关情况:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">踏勘人员:</label></td>
							<td class="width-35">
								${vo.userName}
							</td>
							<td class="width-15 active"><label class="pull-right">踏勘日期:</label></td>
							<td class="width-35">
								${vo.tdate}
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
</body>
</html>
