<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
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
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
						<tr>
							<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
							<td class="width-35">
								<a href="javascript:void();" onclick="fnShow('${vo.id}','${vo.no}');">${vo.no}</a>
							</td>
							<td class="width-15 active"><label class="pull-right">受检单位:</label></td>
							<td class="width-35">
								${vo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">检测类型:</label></td>
							<td class="width-35">
								${vo.taskType}
							</td>
							<td class="width-15 active"><label class="pull-right">样品类别:</label></td>
							<td class="width-35">
								${vo.sampTypeName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">立项日期:</label></td>
							<td class="width-35">${vo.rdate}</td>
							<td class="width-15 active"><label class="pull-right">拟完成日期:</label></td>
							<td class="width-35">${vo.finishDate}</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">项目频次:</label></td>
							<td class="width-35">
								${vo.pc} ${vo.pcUnit}
							</td>
							<td class="width-15 active"><label class="pull-right">单次周期:</label></td>
							<td class="width-35">
								${vo.cycle}
								${vo.cycleUnit}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
					</table>
					 <c:if test="${surveyVo!=null && surveyVo.id!=''}">
					 	 <fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						  <legend>踏勘信息</legend>
						</fieldset>
                    	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
							<tr>
								<td class="width-15 active"><label class="pull-right">建设单位名称:</label></td>
								<td class="width-35">
									${surveyVo.buildUnit}
								</td>
								<td class="width-15 active"><label class="pull-right">项目名称:</label></td>
								<td class="width-35">
									${surveyVo.name}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">项目环评时间:</label></td>
								<td class="width-35">
									${surveyVo.itemHpTime}
								</td>
								<td class="width-15 active"><label class="pull-right">项目环评单位:</label></td>
								<td class="width-35">
									${surveyVo.itemHpUnit}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">项目开工建设<br>及投产时间:</label></td>
								<td class="width-35">
									${surveyVo.productionDate}
								</td>
								<td class="width-15 active"><label class="pull-right">环保设施设计<br>及施工单位:</label></td>
								<td class="width-35">
									${surveyVo.constructUnit}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">项目设计生产能力:</label></td>
								<td class="width-35">
									${surveyVo.designCapacity}
								</td>
								<td class="width-15 active"><label class="pull-right">项目目前生产能力:</label></td>
								<td class="width-35">
									${surveyVo.nowCapacity}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">主管部门:</label></td>
								<td class="width-35">
									${surveyVo.mainName}
								</td>
								<td class="width-15 active"><label class="pull-right">整体或分期验监测:</label></td>
								<td class="width-35">
									${surveyVo.staging}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">废水排口共有:</label></td>
								<td class="width-35">
									${surveyVo.wasteWater}
								</td>
								<td class="width-15 active"><label class="pull-right">废水治理措施:</label></td>
								<td class="width-35">
									${surveyVo.wasteMeasures}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">雨水排口共有:</label></td>
								<td class="width-35">
									${surveyVo.rain}
								</td>
								<td class="width-15 active"><label class="pull-right">雨水治理措施:</label></td>
								<td class="width-35">
									${surveyVo.rainMeasures}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">废气排口共有:</label></td>
								<td class="width-35">
									${surveyVo.exhaustGas}
								</td>
								<td class="width-15 active"><label class="pull-right">废气治理措施:</label></td>
								<td class="width-35">
									${surveyVo.gasMeasures}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">固体废物治理措施:</label></td>
								<td class="width-35">
									${surveyVo.solidWaste}
								</td>
								<td class="width-15 active"><label class="pull-right">噪声治理措施:</label></td>
								<td class="width-35">
									${surveyVo.noise}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">废水处理能力:</label></td>
								<td class="width-35">
									${surveyVo.wasteCapacity}
								</td>
								<td class="width-15 active"><label class="pull-right">废气处理能力:</label></td>
								<td class="width-35">
									${surveyVo.gasCapacity}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">废水排口在<br>线装置情况:</label></td>
								<td class="width-35">
									${surveyVo.wasteDevice}
								</td>
								<td class="width-15 active"><label class="pull-right">废气排口在<br>线装置情况:</label></td>
								<td class="width-35">
									${surveyVo.gasDevice}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">应急预案及<br>事故应急池:</label></td>
								<td class="width-35">
									${surveyVo.emergencyPlan}
								</td>
								<td class="width-15 active"><label class="pull-right">排污口设置<br>及规范化情况:</label></td>
								<td class="width-35">
									${surveyVo.standardiz}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">固体废物处理<br>协议签订情况:</label></td>
								<td class="width-35">
									${surveyVo.solidAgreement}
								</td>
								<td class="width-15 active"><label class="pull-right">废水处理协<br>议签订情况:</label></td>
								<td class="width-35">
									${surveyVo.wasteAgreement}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">其他相关情况:</label></td>
								<td colspan="3">
									${surveyVo.remark}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">踏勘人员:</label></td>
								<td class="width-35">
									${surveyVo.userName}
								</td>
								<td class="width-15 active"><label class="pull-right">踏勘日期:</label></td>
								<td class="width-35">
									${surveyVo.tdate}
								</td>
							</tr>
						</table>
					</c:if>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
					  <legend>方案信息</legend>
					</fieldset>
					<c:forEach items="${vo.schemeList}" var="e" varStatus="s">
                   		<div class="schemeDiv">
                   			<input type="hidden" name="schemeList[${s.index}].num" value="${e.num}" >
                   			<input type="hidden" name="schemeList[${s.index}].id" value="${e.id}" >
                   			<table class="table table-bordered" style="margin-bottom: -1px;">
								<c:if test="${fn:length(vo.schemeList)>1}">
									<tr style="background-color:#f5f5f5">
								 		<td colspan="8">
								 			&nbsp;&nbsp;<strong>第&nbsp;&nbsp;<font style="font-style:italic">${e.num}</font>&nbsp;&nbsp;期</strong>
								 		</td>
								 	</tr>
								</c:if>
								<tr>
									<td class="active"  style="width: 100px;"><label class="pull-right">开始日期:</label></td>
									<td>
											${e.startDate}
									</td>
									<td class="active" style="width: 100px;"><label class="pull-right">截止日期:</label></td>
									<td>
										${e.endDate}
									</td>
									<td class="active" style="width: 100px;"><label class="pull-right">检测费用:</label></td>
									<td>
										${e.fxPrice}
									</td>
									<td class="active" style="width: 100px;"><label class="pull-right">采样费用:</label></td>
									<td>
										${e.cyPrice}
									</td>
								</tr>
								<tr>
									<td class="active" style="width: 100px;"><label class="pull-right">采样天数:</label></td>
									 <td>
										${e.cyDay}
									</td> 
								</tr>
							</table>
                  			<table class="table table-bordered">
                  				<thead>
								 	<tr>
								 		<th style="width: 50px;">序号</th>
								 		<th style="width: 150px;">样品名称</th>
								 		<th style="width: 12%;">检测点位</th>
								 		<th style="width: 100px;">点位编码</th>
								 		<th style="width: 80px;">采样批次</th>
								 		<th >检测项目</th>
								 	</tr>
								 </thead>
								 <tbody id="detail_tb_${s.index}">
									<c:forEach items="${e.pointList}" var="e1" varStatus="s1">
										<tr index="${s.index}">
									 		<td>
									 			${e1.sort}
									 			<input type="hidden" name="schemeList[${s.index}].pointList[${s1.index}].id" value="${e1.id}">
									 		</td>
									 		<td>
									 			${e1.sampTypeName}
									 		</td>
									 		<td>
									 			${e1.pointName}
									 		</td>
									 		<td>
									 			${e1.pointCode}
									 		</td>
									 		<td>
									 			${e1.pc}
									 		</td>
									 		<td>
									 			<a href="javascript:void(0);" onclick="showIM('${e1.id}');">${e1.itemName}</a>
									 		</td>
									 	</tr>
									</c:forEach>
								</tbody>
                   			</table>
                   		</div>
                   	</c:forEach>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" >
						<tr>
							<td class="width-15 active"><label class="pull-right">费用合计:</label></td>
							<td class="width-35">
								${vo.invoiceVo.price}
							</td>
							<td class="width-15 active"><label class="pull-right">折&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扣:</label></td>
							<td class="width-35">
								${vo.invoiceVo.discount}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">交通费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.jtPrice}
							</td>
							<td class="width-15 active"><label class="pull-right">报告费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.bgPrice}
							</td>
						</tr>
						<tr>
							
							<td class="width-15 active"><label class="pull-right">税费等其他费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.otherPrice}
							</td>
							<td class="width-15 active"><label class="pull-right">优惠费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.yhPrice}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">费用总计:</label></td>
							<td class="width-35">
								${vo.invoiceVo.totalPrice}
							</td>
							<td class="width-15 active"><label class="pull-right">合同费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.htPrice}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">是否分包:</label></td>
							<td class="width-35">
								${vo.fb}
							</td>
						</tr>
						<tr>
							<th class="active"><label class="pull-right">上传文件:</label></th>
							<td colspan="3" id="removeFile">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.faMsg}
							</td>
						</tr>
					</table>
					<c:if test="${vo.fb=='是'}">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							 <thead>
							 	<tr>
							 		<th style="width: 50px;">序号</th>
							 		<th style="width: 250px;">分包单位</th>
							 		<th style="width: 100px;">联系人</th>
							 		<th style="width: 120px;">联系电话</th>
							 		<th >分包项目</th>
							 		<th style="width: 100px;">分包数量</th>
							 		<th style="width: 100px;">分包费用</th>
							 	</tr>
							 </thead>
							<tbody id="fb_tb">
								<c:forEach items="${vo.fbList}" var="e" varStatus="s">
									<tr index="${s.index}">
								 		<td>
								 			${s.index+1}
								 		</td>
								 		<td>
								 			${e.fbVo.name}
								 		</td>
								 		<td>
								 			${e.fbUser}
								 		</td>
								 		<td>
								 			${e.fbMobile}
								 		</td>
								 		<td>
								 			${e.itemNames}
								 		</td>
										<td>
								 			${e.num}
								 		</td>					 		 
								 		<td>
								 			${e.price}
								 		</td>
								 	</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="7" style="text-align: right;"><div class="input-group" style="width: 100%"><label style="padding:6px 8px;">费用合计:</label><input style="width: 100px;float: right;" type="text" id="fbPrice" name="fbPrice" class="form-control" value="${vo.fbPrice}" readonly="readonly"></div></td>
								</tr>
							</tfoot>
						</table> 
					</c:if>
					
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
									<!-- 最多5个部门可选 -->
									<tr>
											<td class="active"  style="width: 100px;"><label class="pull-right">评审部门1:</label></td>
											<td>
													${vo.orgName1}
											</td>
											<td class="active"  style="width: 100px;"><label class="pull-right">评审部门2:</label></td>
											<td>
													${vo.orgName2}
											</td>
											<td class="active"  style="width: 100px;"><label class="pull-right">评审部门3:</label></td>
											<td>
												${vo.orgName3}
											</td>
											<td class="active"  style="width: 100px;"><label class="pull-right">评审部门4:</label></td>
											<td>
													${vo.orgName4}
											</td>
										 </tr>
										<tr>
											<td class="active"  style="width: 100px;"><label class="pull-right">评审部门5:</label></td>
											<td>
													${vo.orgName5}
											</td>
										 </tr>
						   </table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
<script type="text/javascript">
function showIM(id){
	parent.layer.open({
		title:'已选项目方法列表',	
		type: 2,
		area: ['700px', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}/bus/im/list4Im.do?busId='+id,
	});
}
</script>
</body>
</html>
