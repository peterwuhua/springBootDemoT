<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
.form-control{
	padding: 4px 4px;
}
.table select{
	padding-top: 2px;
}
#detail_tb .btn{
	padding: 6px;
}
a{
	color: blue;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><strong>合同评审</strong></li>
					<li><strong>${vo.sampType}</strong></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>受<br>检<br>单<br>位</label></td>
								<td class="active" style="width: 15%;"><label class="pull-right">单位名称:</label></td>
								<td style="width: 35%">
									${vo.custVo.custName}
								</td>
								 <td class="active" style="width: 15%;"><label class="pull-right">单位地址:</label></td>
								<td >
									${vo.custVo.custAddress} 
								</td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td>
									${vo.custVo.custUser}
								</td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td>
									${vo.custVo.custTel}
								</td>
							</tr>
							<tr>
								 <td class="active"><label class="pull-right">行业分类和代码:</label></td>
								<td>
									${vo.custVo.industry} 
								</td>
								<td class="active"><label class="pull-right">单位性质:</label></td>
								<td>
									${vo.custVo.attribute}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">主要产品:</label></td>
								<td colspan="3">
									${vo.custVo.product}
								</td>
							</tr>
							<tr>
								<td class="active" rowspan="3" style="text-align: center;width: 50px;"><label>委<br>托<br>单<br>位</label></td>
								<td class=" active"><label class="pull-right">单位名称:</label></td>
								<td >
									${vo.custVo.wtName}
								</td>
								<td class=" active"><label class="pull-right">单位地址:</label></td>
								<td >
									${vo.custVo.wtAddress}
								</td>
								
							</tr>
							<tr>
								<td class="active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td>
									${vo.custVo.wtUser}
								</td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td>
									${vo.custVo.wtTel}
								</td>
							</tr>
							<tr>
								 <td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
								<td>
									${vo.custVo.wtEmail}
								</td>
								<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
								<td>
									${vo.custVo.wtZip}
								</td>
							</tr>
							<tr>
								<c:choose>
									<c:when test="${vo.pj=='是'}">
										<td id="rowsTd" class="active" rowspan="10" style="text-align: center;"><label>检<br>测<br>要<br>求</label></td>
									</c:when>
									<c:otherwise>
										<td id="rowsTd" class="active" rowspan="9" style="text-align: center;"><label>检<br>测<br>要<br>求</label></td>
									</c:otherwise>
								</c:choose>
								<td class="active"><label class="pull-right">样品类别:</label></td>
								<td colspan="3">
									${vo.sampName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">检测类型:</label></td>
								<td>
									${vo.taskType}
								</td>
								<td class="active"><label class="pull-right">项目频次:</label></td>
								<td>
									${vo.pc} ${vo.pcUnit}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">立项日期:</label></td>
								<td>
									${vo.rdate}
								</td>
								<td class="active"><label class="pull-right">单次周期:</label></td>
								<td>
									${vo.cycle}
									${vo.cycleUnit}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">报告份数:</label></td>
								<td>
									${vo.reportNum}
								</td>
								<td class="active"><label class="pull-right">拟完成日期:</label></td>
								<td>
									${vo.finishDate}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">取报告方式:</label></td>
								<td>
									${vo.reportWay}
								</td>
								<td class="active"><label class="pull-right ">加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;急:</label></td>
								<td>
									${vo.jj}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">样品来源:</label></td>
								<td>
									${vo.zsy}
								</td>
								<td class="active"><label class="pull-right ">同意使用非标准方法:</label></td>
								<td>
									${vo.fbzff}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">现场踏勘:</label></td>
								<td class="zcytd">
									${vo.xctk}
								</td>
								<td class="active"><label class="pull-right ">是否评价:</label></td>
								<td>
									${vo.pj}
								</td>
							</tr>
							<c:if test="${vo.pj=='是'}">
								<tr id="pjTr">
									<td class="active"><label class="pull-right">评价依据:</label></td>
									<td colspan="3">
										${vo.standNames}
									</td>
								</tr>
							</c:if>
							<tr>
								<td class="active"><label class="pull-right">委托方提供资料:</label></td>
								<td colspan="3">
									${vo.info}
								</td>
							</tr>
							<tr>
								<th class="active"><label class="pull-right">附件信息:</label></th>
								<td colspan="3">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
									 	<div style="float: left;margin-right: 10px;">
										 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
									 	</div>
									 </c:forEach>
								</td>
							</tr>
						</tbody>
					</table>
					 <c:if test="${vo.xctk=='是' &&( vo.sampType=='职业卫生'|| vo.sampType=='公共卫生')}">
                      	<div style="overflow-x: auto;">
                        	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;min-width: 1500px;">
								 <thead>
								 	<tr>
								 		<td colspan="14">现场调查记录表（表一）</td>
								 	</tr>
									<tr>
										<th width="50" rowspan="2">序号</th>
										<th style="min-width:100px;" rowspan="2">车间名称</th>
										<th style="min-width:100px;" rowspan="2">检测点名称</th>
										<th colspan="2">作业人数</th>
										<th rowspan="2" style="min-width:150px;" >生产设备</th>
										<th colspan="2">设备数量</th>
										<th rowspan="2" >危害因素</th>
										<th rowspan="2" width="50">接触<br>时间</th>
										<th rowspan="2" style="min-width:150px;" >防护设备</th>
										<th colspan="2">设备数量</th>
										<th rowspan="2" style="min-width:150px;" >防护用品<br>及佩戴情况</th>
									</tr>
									<tr>
										<th width="50">总数</th>
										<th width="50">数/班</th>
										<th width="50">总</th>
										<th width="50">开启</th>
										<th width="50">总</th>
										<th width="50">开启</th>
									</tr>
								</thead>
								<tbody id="point">
									<c:forEach items="${potList}" var="e" varStatus="v">
										<tr>
											<td>
												${e.sort}
												<input type="hidden" name="potList[${v.index}].id"  value="${e.id}">
											</td>
											<td>
												${e.roomVo.name}
											</td>
											<td>
												${e.name}
											</td>
											<td>
												${e.workTal}
											</td>
											<td>
												${e.workNum}
											</td>
											<td>
												${e.productName}
											</td>
											<td>
												${e.productTal}
											</td>
											<td>
												${e.productNum}
											</td>
											<td>
												${e.itemNames}
											</td>
											<td>
												${e.workHours}
											</td>
											<td>
												${e.fhName}
											</td>
											<td>
												${e.fhTal}
											</td>
											<td>
												${e.fhNum}
											</td>
											<td>
												${e.others}
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
                          </div>
	                     
                              <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
							  <thead>
							  	<tr>
							  		<td colspan="8">
							  			现场调查记录表（表二）
							  		</td>
							  	</tr>
								<tr>
									<th width="50">序号</th>
									<th width="100">类型</th>
									<th width="15%">物料名称</th>
									<th width="15%">主要成分及含量</th>
									<th width="15%">性状</th>
									<th width="15%">用量及使用<br>时间或产生量</th>
									<th width="100">所在车间</th>
									<th>接触方式</th>
								</tr>
							</thead>
							<tbody id="material">
								<c:forEach items="${mtList}" var="e" varStatus="v">
									<tr>
										<td>
											${e.sort}
											<input type="hidden" name="materialList[${v.index}].id"  value="${e.id}">
										</td>
										<td>
											${e.type}
										</td>
										<td>
											${e.name}
										</td>
										<td>
											${e.cts}
										</td>
										<td>
											${e.xz}
										</td>
										<td>
											${e.yl}
										</td>
										<td>
											${e.roomVo.name}
										</td>
										<td>
											${e.useType}
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
	                     
                              <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
							 <thead>
							 		<tr>
							 			<td colspan="11">
							 				劳动者工作日写实调查表
							 			</td>
							 		</tr>
									<tr>
										<th width="50">序号</th>
										<th>所在车间</th>
										<th>检测点</th>
										<th width="60">姓名</th>
										<th width="50">工龄</th>
										<th width="150">工作开始时间</th>
										<th width="150">工作结束时间</th>
										<th >工作内容</th>
										<th width="50px">耗费工时</th>
										<th >危害因素</th>
										<th >备注</th>
									</tr>
								</thead>
								<tbody id="work">
									<c:forEach items="${workList}" var="e" varStatus="v">
										<tr>
											<td>
												${e.sort}
												<input type="hidden" name="workList[${v.index}].id"  value="${e.id}">
											</td>
											<td>
												${e.roomVo.name}
											</td>
											<td>
												${e.pointVo.name}
											</td>
											<td>
												${e.user}
											</td>
											<td>
												${e.age}
											</td>
											<td>
												${e.startTime}
											</td>
											<td>
												${e.endTime}
											</td>
											<td>
												${e.works}
											</td>
											<td>
												${e.workNum}
											</td>
											<td>
												${e.itemNames}
											</td>
											<td>
												${e.remark}
											</td>
										</tr>
									</c:forEach>
								</tbody>
						</table>
					</c:if>
					<c:if test="${vo.xctk=='是' && surveyVo!=null && surveyVo.id!=''}">
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
					<c:if test="${vo.zsy=='送样'}">
						 <fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						 	<legend>样品信息</legend>
						 </fieldset>
						 <c:forEach items="${vo.schemeList}" var="e" varStatus="s">
							<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
								 <thead>
								 	<tr>
								 		<th>序号</th>
								 		<th>样品名称</th>
								 		<th>样品性状</th>
								 		<th>包装情况</th>
								 		<th>检测项目</th>
								 	</tr>
								 </thead>
								<tbody id="detail_tb">
									<c:forEach items="${e.pointList}" var="e1" varStatus="s1">
										<tr index="${s.index}">
									 		<td>
									 			${s1.index+1}
									 		</td>
									 		<td>
									 			${e1.sampTypeName}
									 		</td>
									 		<td>
									 			${e1.xz}
									 		</td>
									 		<td>
									 			${e1.packing}
									 		</td>
									 		<td>
									 			<a href="javascript:void(0);" onclick="showIM('${e1.id}');">${e1.itemName}</a>
									 		</td>
									 	</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="table table-bordered" style="margin-bottom: -1px;">
								<tr>
									<td class="width-15 active"><label class="pull-right">开始日期:</label></td>
									<td colspan="width-35">
										${e.startDate}
									</td>
									<td class="width-15 active"><label class="pull-right">截止日期:</label></td>
									<td colspan="width-35">
										${e.endDate}
									</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">检测费用:</label></td>
									<td>
										${e.fxPrice}
									</td>
									<td class="active"><label class="pull-right">采样费用:</label></td>
									<td>
										${e.cyPrice}
									</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right">采样天数:</label></td>
									 <td>
										${e.cyDay}
									</td> 
								</tr>
							</table>
						</c:forEach>
					</c:if>
					<c:if test="${vo.zsy!='送样'}">
						<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						  <legend>方案信息</legend>
						</fieldset>
						<c:if test="${vo.sampType=='职业卫生' || vo.sampType=='公共卫生'}">
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
										<tr>
											<td class="active"><label class="pull-right">采样天数:</label></td>
											 <td>
												${e.cyDay}
											</td> 
											</tr>
										</tr>
									</table>
		                  			<table class="table table-bordered">
		                  				<thead>
										 	<tr>
										 		<th style="width: 50px;">序号</th>
										 		<th style="width: 15%;">检测岗位/车间</th>
										 		<th style="width: 15%;">检测点</th>
										 		<th style="width: 80px;">采样频次</th>
										 		<th >检测项目</th>
										 		<th style="width: 120px;">采样方式</th>
										 		<th style="width: 80px;">采样时长</th>
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
											 			${e1.room}
											 		</td>
											 		<td>
											 			${e1.pointName}
											 		</td>
											 		<td>
											 			${e1.pc} ${e1.pcUnit}
											 		</td>
											 		<td>
											 			<a href="javascript:void(0);" onclick="showIM('${e1.id}');">${e1.itemName}</a>
											 		</td>
											 		<td>
											 			${e1.cyType}
											 		</td>
											 		<td>
											 			${e1.cyHours}（min）
											 		</td>
											 	</tr>
											</c:forEach>
										</tbody>
		                   			</table>
		                   		</div>
		                   	</c:forEach>
						</c:if>
						<c:if test="${vo.sampType=='环境'}">
							 <c:forEach items="${vo.schemeList}" var="e" varStatus="s">
		                   		<div class="schemeDiv">
		                   			<input type="hidden" name="schemeList[${s.index}].num" value="${e.num}" >
		                   			<input type="hidden" name="schemeList[${s.index}].id" value="${e.id}" >
		                   			<table class="table table-bordered" style="border-bottom: 0px;margin-bottom: -1px;">
										<c:if test="${fn:length(vo.schemeList)>1}">
											<tr style="background-color:#f5f5f5">
										 		<td colspan="8">
										 			&nbsp;&nbsp;<strong>第&nbsp;&nbsp;<font style="font-style:italic">${e.num}</font>&nbsp;&nbsp;期</strong>
										 		</td>
										 	</tr>
										</c:if>
										<tr>
											<td class="active"  style="border-bottom: 0px;width: 100px;"><label class="pull-right">开始日期:</label></td>
											<td>
												${e.startDate}
											</td>
											<td class="active" style="border-bottom: 0px;width: 100px;"><label class="pull-right">截止日期:</label></td>
											<td>
												${e.endDate}
											</td>
											<td class="active" style="border-bottom: 0px;width: 100px;"><label class="pull-right">检测费用:</label></td>
											<td>
												${e.fxPrice}
											</td>
											<td class="active" style="border-bottom: 0px;width: 100px;"><label class="pull-right">采样费用:</label></td>
											<td>
												${e.cyPrice}
											</td>
										</tr>	
										<tr>
											<td class="active" style="border-bottom: 0px;width: 100px;"><label class="pull-right">采样天数:</label></td>
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
		            	</c:if>
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
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
							<td class="width-15 active"><label class="pull-right">合同费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.htPrice}
							</td>
							<td class="width-15 active"><label class="pull-right">是否分包:</label></td>
							<td class="width-35">
								${vo.fb}
							</td>
						</tr>
					</table>
					<c:if test="${vo.fb=='是'}">
						<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						  <legend>分包信息</legend>
						</fieldset>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" >
							 <thead>
							 	<tr>
							 		<th style="width: 50px;">序号</th>
							 		<th style="width: 250px;">分包单位</th>
							 		<th style="width: 100px;">联系人</th>
							 		<th style="width: 120px;">联系电话</th>
							 		<th >分包项目</th>
							 		<th style="width: 100px;">分包数量</th>
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
								 	</tr>
								</c:forEach>
							</tbody>
						</table> 
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
						<tr>
							<td class="active"><label class="pull-right ">评审内容:</label></td>
							<td colspan="5">
								<textarea rows="2" class="form-control" id="psCt" name="psCt">${vo.psCt}</textarea>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">评审结论:</label></td>
							<td colspan="5">
								<textarea rows="6" class="form-control" id="psResult" name="psResult">${vo.psResult}</textarea>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">合同评审记录:</label></td>
							<td colspan="2">
								<c:if test="${vo.psFilePath==null || vo.psFilePath==''}">
								 	<div class="btn-group" >
				                       <a class="btn btn-info" type="button" onclick="fnOpenWord();">在线编辑</a>
				                    </div>
								</c:if>
								<c:if test="${vo.psFilePath!=null && vo.psFilePath!=''}">
									<c:choose> 
									     <c:when test="${fn:contains(vo.psId,vo.dqPsId)}">    <!--如果 --> 
											<div class="btn-group" >
						                       <a class="btn btn-info" type="button" onclick="fnShowWord();">在线查看</a>
						                    </div>
									 	</c:when>      
									     <c:otherwise>  <!--否则 -->    
											<div class="btn-group" >
						                       <a class="btn btn-info" type="button" onclick="fnOpenWord();">在线编辑</a>
						                    </div>
									  	</c:otherwise> 
									</c:choose>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">评审人员:</label></td>
							<td>
								<div class="input-group">
									<input type="hidden" id="psId" name="psId" value="${vo.psId}">
									<input type="hidden" id="psName" name="psName" value="${vo.psName}" >
									<input type="hidden" id="dqPsId" name="dqPsId" value="${vo.dqPsId}">
									<input type="text" id="dqPsName" name="dqPsName" value="${vo.dqPsName}" class="form-control required" validate="required" placeholder="请选择" onclick="fnSelectUserPz()">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUserPz()">单选</button>
									</div>
								</div>
							</td>
							<td class="active"><label class="pull-right">评审日期:</label></td>
							<td>
								<div class="input-group date">
									<input type="text" id="psDate" name="psDate" class="form-control datetimeISO required" validate="required" value="${vo.psDate}">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="5">
								<textarea rows="2" class="form-control" id="psMsg" name="psMsg" maxlength="128">${vo.psMsg}</textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('updateData.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 提交</a>
							<a class="btn btn-w-m btn-danger" href="javascript:;" onclick="formSubmit4Back('updateData.do?isCommit=-1');"><i class="fa fa-check" aria-hidden="true"></i> 退回</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
		   <!-- Sweet alert -->
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
     <!--PageOffice.js -->
     <script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
	<script>
	function fnOpenWord(){
		 var url = 'updateData.do';
		 $('#thisForm').attr('action',url);
		 $('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
		    	    POBrowser.openWindow('${basePath}bus/projectPs/editWord.do?id=${vo.id}','width=1200px;height=800px;');	        
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
	}
	
	function fnShowWord(){
		POBrowser.openWindow('${basePath}bus/projectPs/showWord.do?id=${vo.id}','width=1200px;height=800px;');
	}
	
	
	function fnShow(id,no){
		parent.layer.open({
			title:'项目详情【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/project/show.do?id='+id
		});
	}

	$('input[name="isCommit"]').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
		changed();
	});
	function changed(){
		var isCommit=$('input[name="isCommit"]:checked').val();
		if(isCommit=='-1'){
			 $('#auditJsResult').attr('validate','required');
			 $('#auditJsResult').addClass('required');
		}else{
			 $('#auditJsResult').removeAttr('validate','required');
			 $('#auditJsResult').removeClass('required');
		}
	};
	function fnSelectUserPz(){
		var userId=$('#dqPsId').val();
		layer.open({
			title:'评审人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/select.do?ids='+userId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#psId').val(data.id);
			  $('#psName').val(data.name);
			  $('#dqPsId').val(data.id);
			  $('#dqPsName').val(data.name);
			}
		});
	}
	function formSubmit4Back(url){
		var auditJsResult=$('#psMsg').val();
		if(auditJsResult==''){
			parent.toastr.error('请输入退回原因！', '');
			return false;
		}
		$('#pzId').removeAttr('validate');
		$('#pzName').removeAttr('validate');
		$('#pzId').removeClass('required');
		$('#pzName').removeClass('required');
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
			swal({
		        title: "您确定要退回该任务吗",
		        text: "提交后将无法修改，请谨慎操作！",
		        type: "warning",
		        showCancelButton: true,
		        confirmButtonColor: "#ed5565",
		        confirmButtonText: "确定",
		        cancelButtonText: "取消"
		    }, function () {
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
	   		});
		}
	}
	function formSubmit(url){
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
			swal({
		        title: "您确定要提交该任务吗",
		        text: "提交后将无法修改，请谨慎操作！",
		        type: "success",
		        showCancelButton: true,
		        confirmButtonColor: "#1ab394",
		        confirmButtonText: "确定",
		        cancelButtonText: "取消"
		    }, function () {
			
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
			
	    	});
		}
	}
	$(function(){
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
		
	});
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
