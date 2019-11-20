<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
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
.panel-body {
    padding: 10px;
}
.panel-heading {
    padding: 5px 15px;
    border-bottom: 1px solid transparent;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px;
}
.panel-title {
    margin-top: 0;
    margin-bottom: 5px;
    font-size: 16px;
    color: inherit;
}
table>thead>tr>th{
	text-align: center;
}
.schemeDiv>.table > tbody > tr > td{
	padding: 3px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><strong>方案编制</strong></li>
					<li><strong>${vo.sampType}</strong></li>
					<li>
						<strong>编辑</strong>
						<c:if test="${vo.isBack=='Y'}">
							（<span style="color: red"> 退回原因：${logVo.msg}</span> ）
						</c:if>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<div class="panel-group" id="accordion">
	                     <div class="panel panel-default">
	                     	 <div class="panel-heading">
	                             <h4 class="panel-title">
	                                 <a>基本信息</a>
	                                 <a style="float: right;padding-left: 500px;"  onclick="changeIcon(this)" data-toggle="collapse" href="tabs_panels.html#collapseOne">
		                                <i class="fa fa-chevron-down"></i>
		                             </a>
	                             </h4>
	                         </div>
	                         <div id="collapseOne" class="panel-collapse collapse in">
	                             <div class="panel-body">
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
												<input style="width: 70px; display: inline-block;" type="number" id="pc" name="pc"  class="form-control required digits" min="1" validate="required" value="${vo.pc}"  onchange="reloadPage()">
												<input type="hidden" id="pcUnit" name="pcUnit" value="${vo.pcUnit}">
												<span>${vo.pcUnit}</span>
											</td>
											<td class="width-15 active"><label class="pull-right">单次周期:</label></td>
											<td class="width-35">
												<input style="width: 70px; display: inline-block;" type="number" id="cycle" name="cycle" min="1" class="form-control required digits" validate="required" value="${vo.cycle}" onchange="reloadPage()">
												<select style="width: 50px; display: inline-block;" id="cycleUnit" name="cycleUnit"  class="form-control"  onchange="countFinishDay()">
													<c:choose>
														<c:when test="${vo.cycleUnit=='年'}">
															<option selected="selected">年</option>
															<option>季</option>
															<option>月</option>
															<option>日</option>
														</c:when>
														<c:when test="${vo.cycleUnit=='季'}">
															<option>年</option>
															<option selected="selected">季</option>
															<option>月</option>
															<option>日</option>
														</c:when>
														<c:when test="${vo.cycleUnit=='月'}">
															<option>年</option>
															<option>季</option>
															<option selected="selected">月</option>
															<option>日</option>
														</c:when>
														<c:otherwise>
															<option>年</option>
															<option>季</option>
															<option>月</option>
															<option selected="selected">日</option>
														</c:otherwise>
													</c:choose>
												</select>
											</td>
										</tr>
										<tr>
											<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
											<td colspan="3">
												${vo.remark}
											</td>
										</tr>
									</table>
	                             </div>
	                         </div>
	                     </div>
	                     <c:if test="${surveyVo!=null && surveyVo.id!=''}">
		                     <div class="panel panel-default">
		                         <div class="panel-heading">
		                             <h4 class="panel-title">
		                                 <a>踏勘信息</a>
		                                 <a style="float: right;padding-left: 500px;"  onclick="changeIcon(this)" data-toggle="collapse" href="tabs_panels.html#collapseTwo">
			                                <i class="fa fa-chevron-up"></i>
			                             </a>
		                             </h4>
		                         </div>
		                         <div id="collapseTwo" class="panel-collapse collapse">
		                             <div class="panel-body">
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
		                             </div>
		                         </div>
		                     </div>
						</c:if>
	                     <div class="panel panel-default">
	                     	<c:set var="sch_num" value="${fn:length(vo.schemeList)}"/>
	                         <div class="panel-heading">
	                             <h4 class="panel-title">
	                                 <a>方案信息</a>
	                                 <c:if test="${sch_num>1}">
	                                 	<!--  <a onclick="chooseAllOpt()" type="button" class="btn btn-info btn-xs" style="margin-bottom: 0px;"><i class="fa fa-plus"></i>批量点位</a>-->
	                                 	<button onclick="chooseAllOpt()" type="button" class="btn btn-info btn-xs" style="margin-bottom: 0px;"><i class="fa fa-plus"></i>批量点位</button>
	                                 </c:if>
	                             	 <a style="float: right;padding-left:500px;"  onclick="changeIcon(this)" data-toggle="collapse" href="tabs_panels.html#collapseThree">
		                                <i class="fa fa-chevron-down"></i>
		                             </a>
	                             </h4>
	                         </div>
	                         <div id="collapseThree" class="panel-collapse collapse in">
	                             <div class="panel-body">
	                             	<c:forEach items="${vo.schemeList}" var="e" varStatus="s">
	                             		<div class="schemeDiv">
	                             			<input type="hidden" name="schemeList[${s.index}].num" value="${e.num}" >
	                             			<input type="hidden" name="schemeList[${s.index}].id" value="${e.id}" >
	                             			<table class="table table-bordered" style="margin-bottom: -1px;">
												<c:if test="${sch_num>1}">
													<tr style="background-color:#f5f5f5">
												 		<td colspan="8">
												 			&nbsp;&nbsp;<strong>第&nbsp;&nbsp;<font style="font-style:italic">${e.num}</font>&nbsp;&nbsp;期</strong>
												 		</td>
												 	</tr>
												</c:if>

												
												<tr>
													<td class="active"  style="width: 100px;"><label class="pull-right">开始日期:</label></td>
													<td>
														<div class="input-group date">
															<input type="text" id="startDate_${s.index}" name="schemeList[${s.index}].startDate" class="form-control dateISO" value="${e.startDate}" autocomplete="off">
															 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
														</div>
													</td>
													<td class="active" style="width: 100px;"><label class="pull-right">截止日期:</label></td>
													<td>
														<div class="input-group date">
															<input type="text" id="endDate_${s.index}" name="schemeList[${s.index}].endDate" class="form-control dateISO" value="${e.endDate}" autocomplete="off">
															 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
														</div>
													</td>
													<td class="active" style="width: 100px;"><label class="pull-right">检测费用:</label></td>
													<td>
														<input type="text" id="fxPrice_${s.index}" name="schemeList[${s.index}].fxPrice" class="form-control number" value="${e.fxPrice}" autocomplete="off" readonly="readonly">
													</td>
													<td class="active" style="width: 100px;"><label class="pull-right">采样费用:</label></td>
													<td>
														<input type="text" id="cyPrice_${s.index}" name="schemeList[${s.index}].cyPrice" class="form-control number" value="${e.cyPrice}" autocomplete="off" onchange="countPrice();">
													</td>
												</tr>
												<tr>
													<td class="active"><label class="pull-right">采样天数:</label></td>
													 <td>
														<input  type="number"  name="schemeList[${s.index}].cyDay"  class="form-control digits" value="${e.cyDay}" >
													</td> 
												</tr>	 
											</table>
	                             			<table class="table table-bordered">
	                             				<thead>
												 	<tr>
												 		<th style="width: 50px;">序号</th>
												 		<th style="width: 100px;">样品名称</th>
												 		<th style="width: 12%;">检测点位</th>
												 		<th style="width: 80px;">点位编码</th>
												 		<th style="width: 80px;">采样频次（次/天）</th>
												 		<th >检测项目</th>
												 		<th style="width: 50px;">
												 			
												 		</th>
												 	</tr>
												 </thead>
												 <tbody id="detail_tb_${s.index}">
													<c:forEach items="${e.pointList}" var="e1" varStatus="s1">
														<tr index="${s.index}">
													 		<td>
													 			<input type="text" name="schemeList[${s.index}].pointList[${s1.index}].sort" class="form-control required digits" validate="required" value="${e1.sort}" autocomplete="off">
													 			<input type="hidden" name="schemeList[${s.index}].pointList[${s1.index}].id" value="${e1.id}">
													 		</td>
													 		<td>
													 			<select id="sampTypeId_${s.index}_${s1.index}" name="schemeList[${s.index}].pointList[${s1.index}].sampTypeId" class="form-control required" validate="required">
													 				<c:forEach items="${sampTypeList}" var="e2" varStatus="s2">
													 					<c:choose>
													 						<c:when test="${e2.id==e1.sampTypeId}">
													 							<option value="${e2.id}" selected="selected">${e2.name}</option>
													 						</c:when>
													 						<c:otherwise>
													 							<option value="${e2.id}">${e2.name}</option>
													 						</c:otherwise>
													 					</c:choose>
													 				</c:forEach>
													 			</select>
													 			<input type="hidden" id="sampTypeName_${s.index}_${s1.index}" name="schemeList[${s.index}].pointList[${s1.index}].sampTypeName" value="${e1.sampTypeName}">
													 		</td>
													 		<td>
													 			<div class="input-group">
														 			<input type="text" id="pointName_${s.index}_${s1.index}" name="schemeList[${s.index}].pointList[${s1.index}].pointName" class="form-control required" validate="required" value="${e1.pointName}"  onchange="checkPoint(this)">
													 			</div>
													 		</td>
													 		<td>
													 			<div class="input-group">
														 			<input type="text" id="pointCode_${s.index}_${s1.index}" name="schemeList[${s.index}].pointList[${s1.index}].pointCode" class="form-control" value="${e1.pointCode}" >
													 			</div>
													 		</td>
													 		<td>
													 			<div class="input-group" style="width: 100%">
													 				<input type="text" id="pc_${s.index}_${s1.index}" name="schemeList[${s.index}].pointList[${s1.index}].pc" class="form-control required digits" validate="required" value="${e1.pc}" autocomplete="off">
													 				<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${s.index});return false;"></span>
													 			</div>
													 		</td>
													 		<td title="${e1.itemName}">
													 			<div class="input-group" style="width: 100%">
																	<input type="text" id="itemName_${s.index}_${s1.index}" name="schemeList[${s.index}].pointList[${s1.index}].itemName" class="form-control required" validate="required" placeholder="请选择" value="${e1.itemName}"  onclick="chooseItem(${s.index},${s1.index});">
																	<input type="hidden" id="itemId_${s.index}_${s1.index}" name="schemeList[${s.index}].pointList[${s1.index}].itemId" value="${e1.itemId}">
																	<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this,${s.index});return false;"></span>
													 			</div>
													 			<input type="hidden" id="imId_${s.index}_${s1.index}" name="schemeList[${s.index}].pointList[${s1.index}].imId" value="${e1.imId}">
													 			<input type="hidden" id="fxPrice_${s.index}_${s1.index}" name="schemeList[${s.index}].pointList[${s1.index}].fxPrice" value="${e1.fxPrice}" />
													 		</td>
													 		<td align="center">
													 			<a  href="javascript:;" onclick="deleteOpt('${e1.id}',this);"><font color="red">删除</font></a>
													 		</td>
													 	</tr>
													</c:forEach>
												</tbody>
												<tfoot>
													<tr>
												 		<td colspan="8">
												 			&nbsp;&nbsp;检测点位<strong> 共&nbsp;&nbsp;<font style="font-style:italic">${fn:length(e.pointList)}</font>&nbsp;&nbsp;个</strong>
												 			<div  style="float: right;">
													 			<button onclick="chooseOpt('${e.id}')" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>选择点位</button>
																&nbsp;&nbsp;
																<button onclick="addOpt('${e.id}')" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>手动新增</button>
												 			</div>
												 		</td>
												 	</tr>
												</tfoot>
	                             			</table>
	                             		</div>
	                             	</c:forEach>
	                             	<!-- <div style="color: red;">
	                             		注：声检测，同点位昼间夜间分两行，用样品名称标注昼间、夜间；
	                             	</div> -->
									<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" >
										<tr>
											<td class="width-15 active"><label class="pull-right">费用合计:</label></td>
											<td class="width-35">
												<input type="text" id="price" name="invoiceVo.price" class="form-control number" value="${vo.invoiceVo.price}" readonly="readonly">
											</td>
											<td class="width-15 active"><label class="pull-right">折&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扣:</label></td>
											<td class="width-35">
												<input type="text" id="discount" name="invoiceVo.discount" class="form-control number" value="${vo.invoiceVo.discount}" onchange="countPrice()">
											</td>
										</tr>
										<tr>
											<td class="width-15 active"><label class="pull-right">交通费用:</label></td>
											<td class="width-35">
												<input type="text" id="jtPrice" name="invoiceVo.jtPrice" class="form-control number" value="${vo.invoiceVo.jtPrice}" onchange="countHtPrice();">
											</td>
											<td class="width-15 active"><label class="pull-right">报告费用:</label></td>
											<td class="width-35">
												<input type="text" id="bgPrice" name="invoiceVo.bgPrice" class="form-control number" value="${vo.invoiceVo.bgPrice}" onchange="countHtPrice();">
											</td>
										</tr>
										<tr>
											
											<td class="width-15 active"><label class="pull-right">税费等其他费用:</label></td>
											<td class="width-35">
												<input type="text" id="otherPrice" name="invoiceVo.otherPrice" class="form-control number" value="${vo.invoiceVo.otherPrice}" onchange="countHtPrice();">
											</td>
											<td class="width-15 active"><label class="pull-right">优惠费用:</label></td>
											<td class="width-35">
												<input type="text" id="yhPrice" name="invoiceVo.yhPrice" class="form-control number" value="${vo.invoiceVo.yhPrice}" onchange="countHtPrice();">
											</td>
										</tr>
										<tr>
											<td class="width-15 active"><label class="pull-right">合同费用:</label></td>
											<td class="width-35">
												<input type="text" id="htPrice" name="invoiceVo.htPrice" class="form-control number" value="${vo.invoiceVo.htPrice}" readonly="readonly">
											</td>
											<td class="width-15 active"><label class="pull-right">是否分包:</label></td>
											<td class="width-35">
												<div class="radio i-checks">
				                                    <label>
				                                        <div class="iradio_square-green">
				                                        <input type="radio" value="是" name="fb" <c:if test="${vo.fb=='是'}">checked</c:if>>
				                                        </div>是
				                                    </label>
				                                    <label>
				                                        <div class="iradio_square-green">
				                                        <input type="radio" value="否" name="fb" <c:if test="${vo.fb!='是'}">checked</c:if>>
				                                        </div>否
				                                    </label>
				                                </div>
											</td>
										</tr>
										<tr>
											<th class="active"><label class="pull-right">上传文件:</label></th>
											<td>
												<input type="file" name="file" multiple="multiple" class="form-control"/>
											</td>
											<td colspan="2" id="removeFile">
												<c:forEach items="${vo.fileList}" var="e" varStatus="v">
												 	<div style="float: left;margin-right: 10px;">
													 	<a href="javascript:fnDownload('${e.filePath}','${e.fileName}')" class="btn btn-w-m btn-info">${e.fileName}</a>
													 	<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
												 	</div>
												 </c:forEach>
											</td>
										</tr>
										<tr>
											<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
											<td colspan="3">
												<textarea rows="2" class="form-control" id="faMsg" name="faMsg" maxlength="128">${vo.faMsg}</textarea>
											</td>
										</tr>
									</table>
									<div class="fbTr">
										<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
											 <thead>
											 	<tr>
											 		<th style="width: 50px;">序号</th>
											 		<th style="width: 250px;">分包单位</th>
											 		<th style="width: 100px;">联系人</th>
											 		<th style="width: 120px;">联系电话</th>
											 		<th >分包项目</th>
											 		<th style="width: 100px;">分包数量</th>
											 		<th style="width: 100px;">分包费用</th>
											 		<th style="width: 50px;">
											 			<button onclick="chooseFbUnit()" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>添加</button>
											 		</th>
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
												 			<input type="hidden" id="fbId${s.index}" name="fbList[${s.index}].fbVo.id" value="${e.fbVo.id}">
												 		</td>
												 		<td>
												 			<input type="text" id="fbUser${s.index}" name="fbList[${s.index}].fbUser" class="form-control required" validate="required" value="${e.fbUser}">
												 		</td>
												 		<td>
												 			<input type="text" id="fbMobile${s.index}" name="fbList[${s.index}].fbMobile" class="form-control required" validate="required" value="${e.fbMobile}">
												 		</td>
												 		<td>
												 			<div class="input-group" style="width: 100%">
																<input type="text" id="itemNames${s.index}" name="fbList[${s.index}].itemNames" validate="required" class="form-control required" placeholder="请选择" value="${e.itemNames}"  onclick="chooseItem4Fb(${s.index});">
																<input type="hidden" id="itemIds${s.index}" name="fbList[${s.index}].itemIds" value="${e.itemIds}">
																<div class="input-group-btn">
																	<button tabindex="-1" class="btn btn-primary" type="button"  onclick="chooseItem4Fb(${s.index});">选择</button>
																</div>
												 			</div>
												 		</td>
														<td>
												 			<input type="text" id="num${s.index}" name="fbList[${s.index}].num" class="form-control digits required" validate="required" value="${e.num}">
												 		</td>					 		 
												 		<td>
												 			<input type="text" id="price${s.index}" name="fbList[${s.index}].price" class="form-control number required" validate="required" value="${e.price}" onchange="countFbPrice()">
												 		</td>
												 		<td align="center">
												 			<a  href="javascript:;" onclick="removeTr(this);"><font color="red">删除</font></a>
												 		</td>
												 	</tr>
												</c:forEach>
											</tbody>
											<tfoot>
												<tr>
													<td colspan="8" style="text-align: right;"><div class="input-group" style="width: 100%"><label style="padding:6px 8px;">费用合计:</label><input style="width: 100px;float: right;" type="text" id="fbPrice" name="fbPrice" class="form-control" value="${vo.fbPrice}" readonly="readonly"></div></td>
												</tr>
											</tfoot>
										</table>	 
									</div>
									
									<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
									<!-- 最多5个部门可选 -->
									<tr>
											<td class="active"  style="width: 100px;"><label class="pull-right">评审部门1:</label></td>
											<td>
											<input type="hidden" id="orgIds" name="orgIds" value="${vo.orgIds}">
											<input type="hidden" id="orgNames" name="orgNames" value="${vo.orgNames}">
												<div class="input-group">
													<input type="hidden" id="orgId1" name="orgId1" value="${vo.orgId1}">
													<input type="text" id="orgName1" name="orgName1" value="${vo.orgName1}" class="form-control " placeholder="请选择" onclick="fnSelectDept(1)">
													<div class="input-group-btn">
														<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectDept(1)">单选</button>
													</div>
												</div>
											</td>
											<td class="active"  style="width: 100px;"><label class="pull-right">评审部门2:</label></td>
											<td>
												<div class="input-group">
													<input type="hidden" id="orgId2" name="orgId2" value="${vo.orgId2}">
													<input type="text" id="orgName2" name="orgName2" value="${vo.orgName2}" class="form-control "  placeholder="请选择" onclick="fnSelectDept(2)">
													<div class="input-group-btn">
														<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectDept(2)">单选</button>
													</div>
												</div>
											</td>
											<td class="active"  style="width: 100px;"><label class="pull-right">评审部门3:</label></td>
											<td>
												<div class="input-group">
													<input type="hidden" id="orgId3" name="orgId3" value="${vo.orgId3}">
													<input type="text" id="orgName3" name="orgName3" value="${vo.orgName3}" class="form-control "  placeholder="请选择" onclick="fnSelectDept(3)">
													<div class="input-group-btn">
														<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectDept(3)">单选</button>
													</div>
												</div>
											</td>
											<td class="active"  style="width: 100px;"><label class="pull-right">评审部门4:</label></td>
											<td>
												<div class="input-group">
													<input type="hidden" id="orgId4" name="orgId4" value="${vo.orgId4}">
													<input type="text" id="orgName4" name="orgName4" value="${vo.orgName4}" class="form-control "  placeholder="请选择" onclick="fnSelectDept(4)">
													<div class="input-group-btn">
														<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectDept(4)">单选</button>
													</div>
												</div>
											</td>
										 </tr>
										<tr>
											<td class="active"  style="width: 100px;"><label class="pull-right">评审部门5:</label></td>
											<td>
												<div class="input-group">
													<input type="hidden" id="orgId5" name="orgId5" value="${vo.orgId5}">
													<input type="text" id="orgName5" name="orgName5" value="${vo.orgName5}" class="form-control "  placeholder="请选择" onclick="fnSelectDept(5)">
													<div class="input-group-btn">
														<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectDept(5)">单选</button>
													</div>
												</div>
											</td>
										 </tr>
									   </table>
	                             </div>
	                         </div>
	                     </div>
	                 </div>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('save4Data.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('update4Data.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
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
	<script>
	$('input[type="file"]').prettyFile();
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
	function reloadPage(){
		$('#thisForm').attr('action','save4Data.do');
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
	    	    location.reload();
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
	
	function unique(){//检验重复
		var arr = [];
		if ($("#orgId1").val()!=""){
			arr.push($("#orgId1").val());	
		}
		if ($("#orgId2").val()!=""){
			arr.push($("#orgId2").val());	
		}
		if ($("#orgId3").val()!=""){
			arr.push($("#orgId3").val());	
		}
		if ($("#orgId4").val()!=""){
			arr.push($("#orgId4").val());	
		}
		if ($("#orgId5").val()!=""){
			arr.push($("#orgId5").val());	
		}
	    for(var i = 1; i < arr.length; i ++){
	        if(arr[i] === arr[i - 1]){
	            return false;
	        }
	    }
	    return true;
	}
	
	
	
	function formSubmitSave(url){
		
		var flag = unique();
		if(!flag){
			parent.toastr.error('所选的部门有重复的！', '');
			return false;
		}
		$('#thisForm').attr('action',url);
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
		        location.reload();
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
	function formSubmit(url){
		/*var f=true;
		$('tbody[id^="detail_tb_"]').each(function(){
			var l= $(this).find('tr').length;
			if(l==0){
				 f=false;
			}
		});
		if(!f){
			 parent.toastr.error('请添加检测点位信息！', '');
			return false;
		}*/
		var flag = unique();
		if(!flag){
			parent.toastr.error('所选的部门有重复的！', '');
			return false;
		}
		swal({
	        title: "您确定要提交该任务吗",
	        text: "提交后将无法修改，请谨慎操作！",
	        type: "success",
	        showCancelButton: true,
	        confirmButtonColor: "#1ab394",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消"
	    }, function () {
			$('#thisForm').attr('action',url);
			var b = $("#thisForm").FormValidate();
			if(b){
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
			}
	    });
	}
	function chooseItem(n,m){
		var sampTypeId=$('#sampTypeId_'+n+'_'+m).val();
		var sampType='${vo.sampType}';
		var imId=$('#imId_'+n+'_'+m).val();
		var	url='${basePath}init/item/im_selects.do?&ids='+imId+'&sampTypeIds='+sampTypeId+'&sampType='+encodeURI(sampType);
		layer.open({
			title:'检测项目',	
			type: 2,
			 area: ['1000px', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: url,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin=layero.find('iframe')[0];
				var data=iframeWin.contentWindow.fnSelect();
				$('#itemId_'+n+'_'+m).val(data.id);
				$('#itemName_'+n+'_'+m).val(data.name);
				$('#imId_'+n+'_'+m).val(data.im);
				$('#fxPrice_'+n+'_'+m).val(data.price);
				parent.layer.close(index);
				countPrice();
			}
		});
	}
	//选择检测点位 批量 
	function chooseAllOpt(){
		$('#thisForm').attr('action','save4Data.do');
		$('#thisForm').ajaxSubmit(function(res) {
			if(res.status!='success'){
		    	 parent.toastr.error(res.message, '');
		    	 return false;
			}
		});
		var projectId='${vo.id}';
		var sampTypeId='${vo.sampTypeId}';
		var clientId='${vo.custVo.clientVo.id}';
		layer.open({
			title:'批量选择-检测点位',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'opt_select.do?custVo.clientVo.id='+clientId+'&sampTypeId='+sampTypeId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				var pointIds=data.id;
				ajaxSavePoint(projectId,'',pointIds);
			}
		});
	}
	//选择检测点位 某个方案
	function chooseOpt(schemeId){
		$('#thisForm').attr('action','save4Data.do');
		$('#thisForm').ajaxSubmit(function(res) {
			if(res.status!='success'){
		    	 parent.toastr.error(res.message, '');
		    	 return false;
			}
		});
		var sampTypeId='${vo.sampTypeId}';
		var clientId='${vo.custVo.clientVo.id}';
		layer.open({
			title:'选择-检测点位',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'opt_select.do?custVo.clientVo.id='+clientId+'&sampTypeId='+sampTypeId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				var pointIds=data.id;
				ajaxSavePoint('',schemeId,pointIds);
			}
		});
	}
	//手动添加点位
	function addOpt(schemeId){
		$('#thisForm').attr('action','save4Data.do');
		$('#thisForm').ajaxSubmit(function(res) {
			if(res.status!='success'){
		    	 parent.toastr.error(res.message, '');
		    	 return false;
			}else{
				ajaxSavePoint('',schemeId,'');
			}
		});
	}
	//ajax 动态保存点位
	function ajaxSavePoint(projectId,schemeId,pointIds){
		$.ajax({
			url:'${basePath}bus/projectFa/addPoint.do?projectId='+projectId+'&schemeVo.id='+schemeId+'&ids='+pointIds,
			dataType:"json",
			type:"post",
			async:false,
			success: function(data){
				if(data.status=='success'){
					parent.toastr.success(data.message, '');
					location.reload();
				}
			},
			error:function(ajaxobj){
				parent.toastr.error('添加点位异常！', '');
		    }  
		});
	}
	function deleteOpt(id,obj){
		$.ajax({
			url:'${basePath}bus/projectFa/deletePoint.do?id='+id,
			dataType:"json",
			type:"post",
			async:false,
			success: function(data){
				if(data.status=='success'){
					parent.toastr.success(data.message, '');
					$(obj).parent().parent().remove();
					countPrice();
				}else{
					parent.toastr.error(data.message, '');
				}
			},
			error:function(ajaxobj){
				parent.toastr.error('删除点位异常！', '');
		    }  
		});
	}
	function removeTr(obj){
		$(obj).parent().parent().remove();
		countPrice();
	}
	function copyVals(obj,index){
		obj=$(obj);
		var value=obj.closest('td').find('input').eq(0).val();
		var indexTr=obj.closest('td').closest('tr').index();
		var indexTd=obj.closest('td').index();
		var sampTypeId=obj.closest('tr').find('select[id^="sampTypeId_'+index+'_"]').eq(0).val();
		$('#detail_tb_'+index+' tr:gt('+indexTr+')').each(function(){
			var thisSampTypeId=$(this).closest('tr').find('select[id^="sampTypeId_'+index+'_"]').eq(0).val();
			if(thisSampTypeId==sampTypeId){
				$(this).find('td').eq(indexTd).find('input').eq(0).val(value);
			}
		});
	}
	function copyItem(obj,index){
		obj=$(obj);
		var idValue=obj.closest('td').find('input').eq(0).val();
		var nameValue=obj.closest('td').find('input').eq(1).val();
		var imValue=obj.closest('td').find('input').eq(2).val();
		var priceValue=obj.closest('td').find('input').eq(3).val();
		var indexTr=obj.closest('td').closest('tr').index();
		var indexTd=obj.closest('td').index();
		var sampTypeId=obj.closest('tr').find('select[id^="sampTypeId_'+index+'_"]').eq(0).val();
		$('#detail_tb_'+index+' tr:gt('+indexTr+')').each(function(){
			var thisSampTypeId=$(this).closest('tr').find('select[id^="sampTypeId_'+index+'_"]').eq(0).val();
			if(thisSampTypeId==sampTypeId){
				$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
				$(this).find('td').eq(indexTd).find('input').eq(1).val(nameValue);
				$(this).find('td').eq(indexTd).find('input').eq(2).val(imValue);
				$(this).find('td').eq(indexTd).find('input').eq(3).val(priceValue);
			}
		});
		countPrice();
	}
	function fnSelectSampType(n){
		var sampTypeId=$('#sampTypeId'+n).val();
		layer.open({
			title:'样品类型',	
			type: 2,
			area: ['300px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/sampType/select.do?id='+sampTypeId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#sampTypeId'+n).val(data.id);
			  $('#sampTypeName'+n).val(data.name);
			}
		});
	}
	function countPrice(){
		var t=0;
		$('.schemeDiv').each(function(n){
			var fxPrice=$(this).find('input[id="fxPrice_'+n+'"]');
			var price=0;
			$('#detail_tb_'+n).find('input[id^="fxPrice_'+n+'_"]').each(function(m){
				price+=parseFloat($(this).val());
			});
			fxPrice.val(price);
			var cyPrice=$(this).find('input[id="cyPrice_'+n+'"]').val();
			if(cyPrice==null||cyPrice=="")
				{
					cyPrice = 0;
				}
			t+=price+parseFloat(cyPrice);
		});
		$('#price').val(t);
		countHtPrice();
	}
	function ftmNum(d){
		var m=d+"";
		if(m.indexOf('.')>0){
			var m=m.substring(m.indexOf('.'));
			if(m.length>2){
				d=d.toFixed(2);
			}
		}
		return d;
	};
	function countHtPrice(){
		var discount=parseFloat($('#discount').val());
		var price=parseFloat($('#price').val());
		var yhPrice=parseFloat($('#yhPrice').val());
		var jtPrice=parseFloat($('#jtPrice').val());
		var bgPrice=parseFloat($('#bgPrice').val());
		var otherPrice=parseFloat($('#otherPrice').val());
		if (isNaN(discount))
			{
				discount=0;
			}
		if (isNaN(price))
		{
			price=0;
		}
		if (isNaN(yhPrice))
		{
			yhPrice=0;
		}
		if (isNaN(jtPrice))
		{
			jtPrice=0;
		}
		if (isNaN(bgPrice))
		{
			bgPrice=0;
		}
		if (isNaN(otherPrice))
		{
			otherPrice=0;
		}
		$('#htPrice').val(price*discount+jtPrice+bgPrice+otherPrice-yhPrice);
	}
	function removeFiles(id,obj){
		layer.confirm('确认要删除?', {icon:3, title:'系统提示'}, function(index){
			$.ajax({
				url:'${basePath}sys/files/deleteOne.do?id='+id,
				dataType:"json",
				type:"post",
				async:false,
				success: function(data){
					if(data.status=='success'){
						layer.msg(data.message, {icon: 0,time: 1000});
						$(obj).parent().remove();
					}
				},
				error:function(ajaxobj){
			    }  
			});
		});
	}
	$(function(){
        $(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
		changed1();
		countPrice();
	});
	function changeIcon(obj){
		if($(obj).find('i').hasClass('fa-chevron-up')){
			$(obj).find('i').removeClass('fa-chevron-up')
			$(obj).find('i').addClass('fa-chevron-down')
		}else{
			$(obj).find('i').removeClass('fa-chevron-down')
			$(obj).find('i').addClass('fa-chevron-up')
		}
	}
</script>
<script type="text/javascript">
<!--分包相关信息-->
$('input[name="fb"]').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
	changed1();
});
function changed1(){
	var radioVal=$('input[name="fb"]:checked').val();
	if(radioVal=='是'){
		$('.fbTr').show()
	}else{
		$('.fbTr').hide()
		$('#fb_tb').html('');
	}
};
function chooseFbUnit(){
	var ids=[];
	$('input[id^="fbId"]').each(function(){
		ids.push($(this).val());
	});
	var	url='${basePath}res/supplier/selects.do?ids='+ids.join(',');
	layer.open({
		title:'分包单位',	
		type: 2,
		 area: ['70%', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: url,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			addFbRow(data);
		}
	});
}
function chooseItem4Fb(n){
	//获取本次需要的检测项目集合
	var ids='';
	$('input[id^="itemId_"]').each(function(){
		var itemId=$(this).val();
		var itemIds=itemId.split(',');
		for(var i=0;i<itemIds.length;i++){
			if(itemIds[i]!='' && ids.indexOf(itemIds[i])<0){
				ids+=itemIds[i]+",";
			}
		}
	});
	if(ids==''){
		layer.msg("请先选择检测项目", {icon: 0,time: 3000});
		return false;
	}
	//获取已选分包项目
	var	url='fb_item_select.do?ids='+ids+'&id='+$('#itemIds'+n).val();
	layer.open({
		title:'分包项目选择',	
		type: 2,
		 area: ['70%', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: url,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#itemIds'+n).val(data.id.join(','));
			$('#itemNames'+n).val(data.name.join(','));
			$('#price'+n).val(data.price);
			$('#num'+n).val(data.id.length);
			countFbPrice();
		}
	});
}
function addFbRow(data){
	var table=$('#fb_tb');
	var num=table.find('tr').length;
	var index=0;
	if(num>0){
		index=parseInt(table.find('tr').eq(num-1).attr('index'));
		index++;
	}
	data.forEach(function(val) {
		table.append($('<tr index='+index+'>').append('<td>'+(index+1)+'</td>')
				.append('<td>'+val.name+'<input type="hidden" id="fbId'+index+'" name="fbList['+index+'].fbVo.id" value="'+val.id+'"></td>')
				.append('<td><input type="text" id="fbUser'+index+'" name="fbList['+index+'].fbUser" class="form-control required" validate="required" value="'+val.linkman+'"></td>')
				.append('<td><input type="text" id="fbMobile'+index+'" name="fbList['+index+'].fbMobile" class="form-control required" validate="required" value="'+val.linkmanTel+'"></td>')
				.append('<td><div class="input-group" style="width: 100%">'+
					'<input type="text" id="itemNames'+index+'" name="fbList['+index+'].itemNames" class="form-control required" validate="required" placeholder="请选择" onclick="chooseItem4Fb('+index+');">'+
					'<input type="hidden" id="itemIds'+index+'" name="fbList['+index+'].itemIds" >'+
					'<div class="input-group-btn"><button tabindex="-1" class="btn btn-primary" type="button"  onclick="chooseItem4Fb('+index+');">选择</button></div>'+
		 			'</div></td>')
		 		.append('<td><input type="text" id="num'+index+'" name="fbList['+index+'].num" class="form-control digits required" validate="required" ></td>')
				.append('<td><input type="text" id="price'+index+'" name="fbList['+index+'].price" class="form-control number required" validate="required" onchange="countFbPrice()"></td>')
				.append('<td><a  href="javascript:;" onclick="removeTr(this);"><font color="red">删除</font></a></td>'));
		index++;
	});
	countFbPrice();
}
function countFbPrice(){
	var price=0;
	$('#fb_tb input[name$=".price"]').each(function(){
		if(isNaN(parseFloat($(this).val()))){
			price+=0;
		}else{
			price+=parseFloat($(this).val());
		}
	})
	$('#fbPrice').val(price);
}
function checkPoint(obj){
	var val=$(obj).val();
	var indexTr=$(obj).closest('td').closest('tr').index();
	$(obj).closest('tbody').find('input[id^="pointName_"]').each(function(n){
		var thisVal=$(this).val();
		if(indexTr!=n &&val==thisVal){
			$(obj).val('');
			layer.alert('测点名称重复!', {icon: 2,title:'系统提示',shadeClose: true});
		}
	});
}

function fnSelectDept(no){
	var pId = "";
	if (no==1){
		pId= $('#orgId1').val();
	}else if(no==2){
		pId= $('#orgId2').val();
	}else if(no==3){
		pId= $('#orgId3').val();
	}else if(no==4){
		pId= $('#orgId4').val();
	}else if(no==5){
		pId= $('#orgId5').val();
	}
	layer.open({
		title:'部门选择',	
		type: 2,
		area: ['30%', '75%'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}sys/org/select.do?id='+pId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
		  var iframeWin = window[layero.find('iframe')[0]['name']];
		  var data=iframeWin.fnSelect();
		  
		  if (no==1){
			  $('#orgId1').val(data.id);
			  $('#orgName1').val(data.name);
		  }else if(no==2){
			  $('#orgId2').val(data.id);
			  $('#orgName2').val(data.name);
		  }else if(no==3){
			  $('#orgId3').val(data.id);
			  $('#orgName3').val(data.name);
		  }else if(no==4){
			  $('#orgId4').val(data.id);
			  $('#orgName4').val(data.name);
		  }else if(no==5){
			  $('#orgId5').val(data.id);
			  $('#orgName5').val(data.name);
		  }
		  var orgIds = "";
		  var orgNames = "";
		  if($('#orgId1').val()!=""){
			  orgIds +=$('#orgId1').val();
			  orgIds +=",";
		  }
		  if($('#orgId2').val()!=""){
			  orgIds +=$('#orgId2').val();
			  orgIds +=",";
		  }
		  if($('#orgId3').val()!=""){
			  orgIds +=$('#orgId3').val();
			  orgIds +=",";
		  }
		  if($('#orgId4').val()!=""){
			  orgIds +=$('#orgId4').val();
			  orgIds +=",";
		  }
		  if($('#orgId5').val()!=""){
			  orgIds +=$('#orgId5').val();
			  orgIds +=",";
		  }
		  if($('#orgName1').val()!=""){
			  orgNames +=$('#orgName1').val();
			  orgNames +=",";
		  }
		  if($('#orgName2').val()!=""){
			  orgNames +=$('#orgName2').val();
			  orgNames +=",";
		  }
		  if($('#orgName3').val()!=""){
			  orgNames +=$('#orgName3').val();
			  orgNames +=",";
		  }
		  if($('#orgName4').val()!=""){
			  orgNames +=$('#orgName4').val();
			  orgNames +=",";
		  }
		  if($('#orgName5').val()!=""){
			  orgNames +=$('#orgName5').val();
			  orgNames +=",";
		  }
		  
		  $("#orgIds").val(orgIds);
		  $("#orgNames").val(orgNames);
		}
	});
}


</script>
</body>
</html>
