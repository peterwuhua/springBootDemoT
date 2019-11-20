<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<style type="text/css">
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
.form-control{
	padding: 4px;
}
.table > thead > tr > th{
	text-align: center;
}
.table > tbody > tr > td,.table > thead > tr > td{
	padding: 2px;
}
.table > tbody > tr > td >input,.table > tbody > tr > td >select{
	height: 100%;
}
.table > tbody > tr > td > div >textarea{
	height: 100%;
	overflow-y:visible;
}
tbody.ct_td>tr>td{
	padding: 0px;
}
.closeTd{
	color: blue;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><a>采样安排</a></li>
						<li><strong>编辑</strong>
							<c:if test="${vo.isBack=='Y'}">
								（<span style="color: red"> 退回原因：${logVo.msg}</span> ）
							</c:if>
						</li>
					</ol>
				</h5>
			</div>
			<div class="ibox-content">
				<form method="post" action="updateData.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active"  style="width: 150px;"><label class="pull-right">任务编号:</label></td>
							<td>
								<a onclick="fnTask('${vo.id}','${vo.no}');">${vo.no}</a>
							</td>
							<td class="active"  style="width: 150px;"><label class="pull-right">报告编号:</label></td>
							<td>
								<input type="text" id="reportNo" name="reportNo" class="form-control" value="${vo.reportNo}" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td class="active"  style="width: 150px;"><label class="pull-right">受检单位:</label></td>
							<td>
								${vo.custVo.custName}
							</td>
							<td class="active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
							<td>
								${vo.custVo.custUser}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>
								${vo.sampName}
							</td>
						</tr>
						<tr>
							 
							<td class="active"><label class="pull-right">受理日期:</label></td>
							<td>
								${vo.date}
							</td>
							<td class="active"><label class="pull-right">要求完成日期:</label></td>
							<td>
								${vo.finishDate}
							</td>
						</tr>
						
						<tr>
							<td class="width-15 active"><label class="pull-right">采样开始日期:</label></td>
							<td class="width-35">
								<div class="input-group date">
									<input type="text" id="cyDate" name="cyDate" class="form-control required" validate="required" value="${vo.cyDate}">
									 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
							<td class="width-15 active"><label class="pull-right">采样结束日期:</label></td>
							<td class="width-35">
								<div class="input-group date">
									<input type="text" id="cyEndDate" name="cyEndDate" class="form-control required" validate="required" value="${vo.cyEndDate}">
									 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right"> 采样小组人员:</label></td>
							<td class="width-35">
								<div class="input-group" style="width: 100%;">
									<input type="hidden" id="cyId" name="cyId" value="${vo.cyId}">
									<input type="text" id="cyName" name="cyName" value="${vo.cyName}" class="form-control required" validate="required" placeholder="请选择" onclick="fnSelectUsers()">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUsers()">选择</button>
									</div>
								</div>
							</td>
							<td class="active"><label class="pull-right">负&nbsp;&nbsp;责&nbsp;人:</label></td>
							<td>
								<select id="fzId" name="fzId" class="form-control required" validate="required" >
									<c:forEach items="${fzList}" var="e">
										<c:choose>
											<c:when test="${e.id==vo.fzId}">
												<option value="${e.id}" selected="selected">${e.userName}</option>
											</c:when>
											<c:otherwise>
												<option value="${e.id}">${e.userName}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<input type="hidden" id="fzName" name="fzName"  value="${vo.fzName}">
							</td>
						</tr>
						<tr>
							<td class="active">
								<label class="pull-right ">采样设备:</label>
							</td>
							<td >
								<%-- <div class="input-group" style="width: 100%">
									<div class="chosen-container chosen-container-multi chosen-with-drop" style="width: 85%">
										<ul class="chosen-choices" id="appUL">
											<c:forEach items="${vo.appList}" var="e" varStatus="v">
												<li class="search-choice" index="${v.index}">
													<span>${e.appName}</span>
			          								<input name="appList[${v.index}].id" value="${e.id}" type="hidden" />
			          								<input name="appList[${v.index}].appId" value="${e.appId}" type="hidden" />
			          								<input name="appList[${v.index}].appName" value="${e.appName}" type="hidden" />
			          								<a class="search-choice-close" onclick="removeLi(this)" title="删除"><i class="fa fa-close text-danger"></i></a>
		          								</li>
											</c:forEach>
										</ul>
									</div>
									<button class="btn btn-primary" type="button" onclick="fnSelectApp()">选择</button>
								</div> --%>
								<div class="input-group" style="width: 100%">
									<textarea id="cyAppNames" name="cyAppNames" class="form-control" onclick="fnSelectApp()">${vo.cyAppNames}</textarea>
									<input id="cyAppIds" name="cyAppIds" value="${vo.cyAppIds}" type="hidden" />
								</div>
							</td>
							<td class="active"><label class="pull-right ">车辆预约:</label></td>
							<td>
								<div class="input-group" style="width: 100%">
									<div class="chosen-container chosen-container-multi chosen-with-drop" style="width: 85%">
										<ul class="chosen-choices" id="carUL">
											<c:forEach items="${vo.carList}" var="e" varStatus="v">
												<li class="search-choice" index="${v.index}">
													<span>${e.name}(${e.code})</span>
			          								<input name="carList[${v.index}].id" value="${e.id}" type="hidden" />
			          								<input name="carList[${v.index}].carId" value="${e.carId}" type="hidden" />
			          								<input name="carList[${v.index}].name" value="${e.name}" type="hidden" />
			          								<a class="search-choice-close" onclick="removeLi(this)" title="删除"><i class="fa fa-close text-danger"></i></a>
		          								</li>
											</c:forEach>
										</ul>
									</div>
									<button class="btn btn-primary" type="button" onclick="fnSelectCar()">选择</button>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">采样方法:</label></td>
							<td colspan="3">
								<div class="input-group" style="width: 100%">
									<textarea id="cyStandNames" name="cyStandNames" class="form-control" onclick="chooseCyMethod()">${vo.cyStandNames}</textarea>
									<input id="cyStandIds" name="cyStandIds" value="${vo.cyStandIds}" type="hidden" />
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>检测点位信息配置</b>
								<div style="float: right;">【<a class="closeTd" onclick="closeTd(this)">闭合</a>】</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 0px;">
								<table class="table table-bordered" style="margin-bottom: 0px;border: 0px;">
									<thead>
										<tr>
											<th width="5%">序号</th>
											<th width="10%">检测点位</th>
											<th width="10%">样品名称</th>
											<th width="130">采样频次</th>
											<th width="7%">采样数量</th>
											<th width="7%">空白样</th>
											<th width="7%">平行样</th>
											<th>检测项目</th>
											<th style="width:100px;">普通样</th>
											<th style="width:60px;">空白样</th>
											<th style="width:60px;">平行样</th>
										</tr>
									</thead>
									<tbody id="point_tb">
										<c:forEach items="${vo.tpList}" var="e" varStatus="v">
											<tr key="${v.index}" >
												<td align="center">
													${e.sort}
													<input name="tpList[${v.index}].id" value="${e.id}" type="hidden" />
													<input id="sampId_${v.index}" name="tpList[${v.index}].sampTypeId" value="${e.sampTypeId}" type="hidden" />
												</td>
												
												<td>
													${e.pointName}
												</td>
												<td>
													${e.sampName}
												</td>
												<td align="center">
													<div class="input-group" style="width: 100%">
														<input style="width:50px;" type="text" class="form-control" name="tpList[${v.index}].pc" value="${e.pc}" readonly="readonly">
														<select style="width: 70px;" class="form-control required" validate="required" name="tpList[${v.index}].pcUnit" onchange="fnInitSamp();">
															<c:forEach items="${pcUnitList}" var="er">
																<c:choose>
																	<c:when test="${er.name==e.pcUnit}">
																		<option value="${er.name}" selected="selected">${er.name}</option>
																	</c:when>
																	<c:otherwise>
																		<option value="${er.name}">${er.name}</option>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</select>
													</div>
												</td>
												<td align="center">
													${e.sampNum}
												</td>
												<td align="center">
													<c:choose>
														<c:when test="${e.type=='声'}">
														/
														</c:when>
														<c:otherwise>
															${e.zkNum}
														</c:otherwise>
													</c:choose>
												</td>
												<td align="center">
													<c:choose>
														<c:when test="${e.type=='声'}">
															/
														</c:when>
														<c:otherwise>
															${e.pxNum}
														</c:otherwise>
													</c:choose>
												</td>
												<td align="center">
													${e.itemNames}
												</td>
												<td align="center">
													<a class="btn btn-primary btn-xs btn-outline" onclick="addSamp('${e.id}','')">&nbsp;&nbsp;<i class="fa fa-plus" aria-hidden="true"></i>&nbsp;&nbsp;</a>
													 &nbsp;
													 <a class="btn btn-danger btn-xs btn-outline" onclick="delSamp('${e.id}','','${e.pc}')">&nbsp;&nbsp;<i class="fa fa-close" aria-hidden="true"></i>&nbsp;&nbsp;</a>
												</td>
												<td align="center">
													 <c:if test="${e.type!='声'}">
													 	<a class="btn btn-primary btn-xs btn-outline" onclick="addSampZk('${e.id}','3','${e.itemIds}')">&nbsp;&nbsp;<i class="fa fa-plus" aria-hidden="true"></i>&nbsp;&nbsp;</a>
													 </c:if>
												</td>
												<td align="center">
													 <c:if test="${e.type!='声'}">
														<a class="btn btn-primary btn-xs btn-outline" onclick="addSampZk('${e.id}','1','${e.itemIds}')">&nbsp;&nbsp;<i class="fa fa-plus" aria-hidden="true"></i>&nbsp;&nbsp;</a>
													 </c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>样品信息</b>
								&nbsp;
								<a class="btn btn-danger btn-xs" onclick="fnInitAllSamp()">重置样品信息</a>
								<div style="float: right;">【<a class="closeTd" onclick="closeTd(this)">闭合</a>】</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 0px;">
								<table class="table table-bordered" style="margin-bottom: 0px;border: 0px;">
									<thead>
										<tr>
											<th width="50px">序号</th>
											<th width="100px">检测点位</th>
											<th width="100px">样品类别</th>
											<th width="100px">采样日期</th>
											<th width="50px">批次</th>
											<th width="150px">样品编号</th>
											<th>检测项目</th>
											<th width="20%">使用容器</th>
										</tr>
									</thead>
									<tbody id="sampTb">
										<c:set var="num" value="0"/>
										<c:forEach items="${vo.tpList}" var="m">
											<c:forEach items="${m.sampList}" var="e" varStatus="v">
												<tr>
													<td>
														<input type="text" class="form-control required" validate="required" name="sampList[${num}].sort" value="${num+1}" >
														<input name="sampList[${num}].id" value="${e.id}" type="hidden" />
													</td>
													<td>
														${e.pointName}
													</td>
													<td>
														${e.sampTypeName}
													</td>
													<td>
														${e.cyDate}
													</td>
													<td>
														<input type="text" class="form-control" name="sampList[${num}].p" value="${e.p}" readonly="readonly">
													</td>
													<td>
														 <input type="text" id="sampTb_sampCode_${num}" class="form-control"  name="sampList[${num}].sampCode" value="${e.sampCode}" placeholder="现场检测">
													</td>
													<td>
														<div class="input-group" style="width: 100%">
															<textarea id="sampTb_itemNames_${num}" name="sampList[${num}].itemNames" class="form-control  required" validate="required" onclick="chooseItem('sampTb','${m.itemIds}','${num}','${e.id}')">${e.itemNames}</textarea>
															<input id="sampTb_itemIds_${num}" name="sampList[${num}].itemIds" value="${e.itemIds}" type="hidden" />
														</div>
													</td>
													<td>
														<c:choose>
															<c:when test="${e.type=='-1'}">
																/
															</c:when>
															<c:otherwise>
																<table class="table" style="margin-bottom: 0px;">
										                            <thead>
										                            	<tr>
										                            		<td colspan="3">
										                            			 <a class="btn btn-primary btn-xs" onclick="fnSelectCt(${num})">选择</a>
										                            			  <a class=" btn btn-primary btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyCt(${num})"></a>
										                            		</td>
										                            	</tr>
										                            </thead>
										                            <tbody class="ct_td" id="sampTb_ct_${num}" index="${num}">
										                            	<c:forEach items="${e.containerList}" var="e1" varStatus="v1">
											                                <tr>
											                                    <td>
											                                    	${e1.container}
											                                    	<input name="sampList[${num}].containerList[${v1.index}].containerId"  value="${e1.containerId}" type="hidden" />
																					<input name="sampList[${num}].containerList[${v1.index}].container"  value="${e1.container}" type="hidden" />
											                                    </td>
											                                    <td width="40">
											                                    	 <input name="sampList[${num}].containerList[${v1.index}].num" value="${e1.num}" type="text"  class="form-control required digits" validate="required"/> 
											                                    </td>
											                                    <td width="34" style="text-align:center;"><a onclick="removeTr(this)"><i class="fa fa-close text-danger"></i></a></td>
											                                </tr>
										                                </c:forEach>
										                            </tbody>
										                        </table>
															</c:otherwise>
														</c:choose>
													</td>
												</tr>
												<c:set var="num" value="${num+1}"/>
											</c:forEach>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>质控信息</b>
								<div style="float: right;">【<a class="closeTd" onclick="closeTd(this)">闭合</a>】</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 0px;">
								<table class="table table-bordered" style="margin-bottom: 0px;border: 0px;">
									<thead>
										<tr>
											<th width="50px">序号</th>
											<th width="100px">检测点位</th>
											<th width="100px">样品类型</th>
											<th width="100px">采样日期</th>
											<th width="50px">批次</th>
											<th width="150px">样品编号</th>
											<th>检测项目</th>
											<th width="20%">使用容器</th>
											<th width="40px"></th>
										</tr>
									</thead>
									<tbody id="zkTb">
										<c:set var="num" value="0"/>
										<c:forEach items="${vo.tpList}" var="m">
											<c:forEach items="${m.zkList}" var="e" varStatus="v">
												<tr>
													<td>
														<input type="text" class="form-control required" validate="required" name="zsampList[${num}].sort" value="${num+1}" >
														<input name="zsampList[${num}].id" value="${e.id}" type="hidden" />
													</td>
													<td>
														${e.pointName}
													</td>
													<td>
														${e.sampTypeName}
													</td>
													<td>
														${e.cyDate}
													</td>
													<td>
														<input type="text" class="form-control" name="zsampList[${num}].p" value="${e.p}" readonly="readonly">
													</td>
													<td>
														<input type="text" id="zkTb_sampCode_${num}" class="form-control" name="zsampList[${num}].sampCode" value="${e.sampCode}" >
													</td>
													<td>
														<div class="input-group" style="width: 100%">
															<textarea id="zkTb_itemNames_${num}" name="zsampList[${num}].itemNames" class="form-control required" validate="required" onclick="chooseItem('zkTb','${m.itemIds}','${num}','${e.id}')">${e.itemNames}</textarea>
															<input id="zkTb_itemIds_${num}" name="zsampList[${num}].itemIds" value="${e.itemIds}" type="hidden" />
														</div>
													</td>
													<td>
														<c:choose>
															<c:when test="${e.type=='-1'}">
																/
															</c:when>
															<c:otherwise>
																 <table class="table" style="margin-bottom: 0px;">
										                            <thead>
										                            	<tr>
										                            		<td colspan="3">
										                            			 <a class="btn btn-primary btn-xs" onclick="fnSelectCtZk(${num})">选择</a>
										                            			  <a class=" btn btn-primary btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyCtZk(${num})"></a>
										                            		</td>
										                            	</tr>
										                            </thead>
										                            <tbody class="ct_td" id="zkTb_ct_${num}" index="${num}">
										                            	<c:forEach items="${e.containerList}" var="e1" varStatus="v1">
											                                <tr>
											                                    <td>
											                                    	${e1.container}
											                                    	<input name="zsampList[${num}].containerList[${v1.index}].containerId"  value="${e1.containerId}" type="hidden" />
																					<input name="zsampList[${num}].containerList[${v1.index}].container"  value="${e1.container}" type="hidden" />
											                                    </td>
											                                    <td width="40">
											                                    	 <input name="zsampList[${num}].containerList[${v1.index}].num" value="${e1.num}" type="text"  class="form-control required digits" validate="required"/> 
											                                    </td>
											                                    <td width="34" style="text-align:center;"><a onclick="removeTr(this)"><i class="fa fa-close text-danger"></i></a></td>
											                                </tr>
										                                </c:forEach>
										                            </tbody>
										                        </table>
										                	</c:otherwise>
										            	</c:choose>
													</td>
													<td align="center">
														<a onclick="deleteSamp('${e.id}')"><i class="fa fa-close text-danger"></i></a>
													</td>
												</tr>
												<c:set var="num" value="${num+1}"/>
											</c:forEach>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>未来一周安排表</b>
								<div style="float: right;">【<a class="closeTd" onclick="closeTd(this)">闭合</a>】</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 0px;">
								<table id="table" class="table table-bordered" style="margin: -1px;width: 100%">
									<thead>
										<tr>
											<th style="text-align:center;width: 110px;">任务编号</th>
											<th style="text-align:center;">客户名称</th>
											<c:forEach items="${panMap.date}" var="e">
												<th style="min-width:10%;text-align:center;">${e}</th>
											</c:forEach>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${panMap.data}" var="e">
											<c:set var="index" value="0"/>
											 <tr>
											 	<td>${e.no}</td>
												<td>${e.custName}</td>
												<c:forEach items="${e.tabList}" var="e1" varStatus="v1">
													<c:choose>
														<c:when test="${e1.has && index==0}">
															<td align="center" style="background-color:${e.color}" colspan="${e.colSpan}">
																${e.user}
																<c:set var="index" value="${index+1}"/>
															</td>
														</c:when>
														<c:when test="${e1.has}">
															 
														</c:when>
														<c:otherwise>
															<td align="center"></td>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>安排人信息</b>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">安&nbsp;排&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								<input type="hidden" id="apId" name="apId" value="${vo.apId}">
								<input type="text" id="apName" name="apName" value="${vo.apName}" class="form-control" readonly="readonly">
							</td>
							<td class="width-15 active"><label class="pull-right"> 安排日期:</label></td>
							<td class="width-35">
								<div class="input-group date">
									<input type="text" id="apDate" name="apDate" class="form-control datetimeISO" value="${vo.apDate}">
									 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="apMsg" name="apMsg" maxLength="128">${vo.apMsg}</textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-danger" type="button" onclick="fnSubmitBack('updateBack.do?isCommit=-1')"><i class="fa fa-check" aria-hidden="true"></i> 退回</button>
							<a class="btn btn-w-m btn-success" type="button" onclick="formSubmit4Save('updateData.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('updateData.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
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
	laydate.render({
		  elem: '#cyDate',
		  theme: 'molv',
		  done: function(value, date, endDate){
			  $('#cyDate').val(value);
			  fnInitSamp();
		  }
	});
	laydate.render({
		  elem: '#cyEndDate',
		  theme: 'molv',
		  done: function(value, date, endDate){
			  $('#cyEndDate').val(value);
			  fnInitSamp();
		  }
	});
	function fnInitSamp(){
		$('#thisForm').attr('action','update4Samp.do');
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
	    	    window.location.reload(true);
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
	function fnInitAllSamp(){
		swal({
	        title: "您确定要重置样品信息吗",
	        text: "该操作无法回退，请谨慎操作！",
	        type: "success",
	        showCancelButton: true,
	        confirmButtonColor: "#1ab394",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消"
	    }, function () {
	    	$('#thisForm').attr('action','update4SampAll.do');
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
		    	    window.location.reload(true);
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
 		});
	}
	function fnTask(id,no){
		parent.layer.open({
			title:'任务【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/task/show.do?id='+id
		});
	}
	 
	function fnSave(){
		var fzId=$('#fzId').val();
		var cyDate=$('#cyDate').val();
		if(fzId==''){
			layer.msg('请选择采样负责人', {icon: 0,time: 3000});
			return false;
		}
		if(cyDate==''){
			layer.msg('请选择采样日期', {icon: 0,time: 3000});
			return false;
		}
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success('更新成功！', '');
	    	    window.location.reload(true);
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
	function fnSubmitBack(url){
		var apMsg=$('#apMsg').val();
		if(apMsg==''){
			swal("请在备注栏输入退回意见！", "", "warning");
			return ;
		}
		swal({
	        title: "您确定要退回吗",
	        text: "将会退回任务，请谨慎操作！",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "退回",
	        cancelButtonText: "取消"
	    }, function () {
			$('#thisForm').attr('action',url);
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
	function formSubmit4Save(url){
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
			 $('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
		    	    window.location.reload();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	}
	function fnSubmit(url){
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
		    	$('#fzName').val($('#fzId').find('option:selected').text());
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
			
	   		});
		}else{
			parent.toastr.error('请检查必填项！', '');
		}
	}
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
		 
		$(".chosen-container").hover(function(){  
            $(this).addClass('chosen-container-active');  
        },function(){  
        	 $(this).removeClass('chosen-container-active');  
        })
	});
	function fnSelectUsers(){
		var userId=$('#cyId').val();
		layer.open({
			title:'采样人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'user_selects.do?ids='+userId+'&orgId=${vo.orgId}',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#cyId').val(data.id);
			  $('#cyName').val(data.name);
			  setCyFzr(data.id,data.name);
			}
		});
	}
	//设置采样负责人
	function setCyFzr(ids,names){
		var fzId='${vo.fzId}';
		$('#fzId').html('');
		for(var i=0;i<ids.length;i++){
			if(fzId==ids[i]){
				$('#fzId').append('<option value="'+ids[i]+'" selected="selected">'+names[i]+'</option>');
			}else{
				$('#fzId').append('<option value="'+ids[i]+'">'+names[i]+'</option>');
			}
		}
		fnSave();
	}
	//采样设备
	function fnSelectApp(){
		var ids=$('#cyAppIds').val();
		parent.layer.open({
			title:'采样设备',	
			type: 2,
			area: ['1200px', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}bus/taskAp/appara_select2.do?ids='+ids,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin=layero.find('iframe')[0];
				var data=iframeWin.contentWindow.fnSelect();
				$('#cyAppIds').val(data.id);
				$('#cyAppNames').val(data.name);
				parent.layer.close(index);
			}
		});
	}
	function setAppData(id,name){
		var appUL=$('#appUL');
		var index=0;
		var indexStr=appUL.children("li").last().attr('index');
		if(indexStr!=undefined){
			index=parseInt(indexStr)+1;
		}
		for(var i=0;i<id.length;i++){
			var liStr='<li class="search-choice" index="'+index+'"><span>'+name[i]+'</span>'+
	          '<input name="appList['+index+'].appId" value="'+id[i]+'" type="hidden" />'+
	          '<input name="appList['+index+'].appName" value="'+name[i]+'" type="hidden" />'+
	          '<a class="search-choice-close"  onclick="removeLi(this)" title="删除"><i class="fa fa-close text-danger"></i></a></li>';
	          appUL.append(liStr);
	          index++;
		}
	}
	function removeLi(obj){
		var t = $(obj).parent(); 
		t.remove();
		parent.layer.msg('删除成功', {icon: 0,time: 3000});
		//fnSave();
	}
	function chooseCyMethod(){
		var ids=$('#cyStandIds').val();
		parent.layer.open({
			title:'采样方法',	
			type: 2,
			area: ['1200px', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/sampSource/select2.do?ids='+ids,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin=layero.find('iframe')[0];
				var data=iframeWin.contentWindow.fnSelect();
				$('#cyStandIds').val(data.id);
				$('#cyStandNames').val(data.name);
				parent.layer.close(index);
			}
		});
	}
	//车辆信息
	function fnSelectCar(){
		var ids='';
		$('input[name$=".carId"]').each(function(){
			if(ids.indexOf($(this).val())<0){
				ids+=$(this).val()+",";
			}
		});
		layer.open({
			title:'车辆预约',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'car_select.do?ids='+ids+'&id=${vo.id}',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  setCarData(data.id,data.name);
			  fnSave();
			}
		});
	}
	function setCarData(id,name){
		var carUL=$('#carUL');
		var index=0;
		var indexStr=carUL.children("li").last().attr('index');
		if(indexStr!=undefined){
			index=parseInt(indexStr)+1;
		}
		for(var i=0;i<id.length;i++){
			var liStr='<li class="search-choice" index="'+index+'"><span>'+name[i]+'</span>'+
	          '<input name="carList['+index+'].carId" value="'+id[i]+'" type="hidden" />'+
	          '<input name="carList['+index+'].name" value="'+name[i]+'" type="hidden" />'+
	          '<a class="search-choice-close"  onclick="removeLi(this)" title="删除"><i class="fa fa-close text-danger"></i></a></li>';
	          carUL.append(liStr);
	          index++;
		}
	}
	//采样容器
	function fnSelectCt(n){
		var ids='';
		$('#sampTb_ct_'+n).find('input[name$=".containerId"]').each(function(){
			if(ids.indexOf($(this).val())<0){
				ids+=$(this).val()+",";
			}
		});
		layer.open({
			title:'采样容器',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}res/consumable/selects.do?ids='+ids,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  setCtData(data.id,data.name,n);
			}
		});
	}
	function setCtData(ids,names,n){
		var td='';
		for(var i=0;i<ids.length;i++){
			td+='<tr>'+
	                '<td>'+names[i]+
			        	'<input name="sampList['+n+'].containerList['+i+'].containerId"  value="'+ids[i]+'" type="hidden" />'+
						'<input name="sampList['+n+'].containerList['+i+'].container"  value="'+names[i]+'" type="hidden" />'+
			       '</td>'+
			       '<td width="40">'+
			        	 '<input name="sampList['+n+'].containerList['+i+'].num" value="1" type="text" min="1" class="form-control required digits" validate="required"/>'+ 
			        '</td>'+
			        '<td width="34" style="text-align:center;"><a onclick="removeTr(this)"><i class="fa fa-close text-danger"></i></a></td>'+
	   		 	'</tr>';
		}
		$('#sampTb_ct_'+n).html(td);
	}
	//采样容器
	function fnSelectCtZk(n){
		var ids='';
		$('#zkTb_ct_'+n).find('input[name$=".containerId"]').each(function(){
			if(ids.indexOf($(this).val())<0){
				ids+=$(this).val()+",";
			}
		});
		layer.open({
			title:'采样容器',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}res/consumable/selects.do?ids='+ids,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  setCtDataZk(data.id,data.name,n);
			}
		});
	}
	function setCtDataZk(ids,names,n){
		var td='';
		for(var i=0;i<ids.length;i++){
			td+='<tr>'+
	                '<td>'+names[i]+
			        	'<input name="zsampList['+n+'].containerList['+i+'].containerId"  value="'+ids[i]+'" type="hidden" />'+
						'<input name="zsampList['+n+'].containerList['+i+'].container"  value="'+names[i]+'" type="hidden" />'+
			       '</td>'+
			       '<td width="40">'+
			        	 '<input name="zsampList['+n+'].containerList['+i+'].num" value="1" type="text" min="1" class="form-control required digits" validate="required"/>'+ 
			        '</td>'+
			        '<td width="34" style="text-align:center;"><a onclick="removeTr(this)"><i class="fa fa-close text-danger"></i></a></td>'+
	   		 	'</tr>';
		}
		$('#zkTb_ct_'+n).html(td);
	}
	function removeTr(obj){
		$(obj).closest('tr').remove();
	}
	function copyCt(n){
		var ptd=$('#sampTb_ct_'+n).children('tr');
		if(ptd.length>0){
			var i=n;
			$('#sampTb tr:gt('+n+')').each(function(){
				i++;
				var trStr='';
				for(var j=0;j<ptd.length;j++){
					var value1=$(ptd[j]).find('input').eq(0).val();
					var value2=$(ptd[j]).find('input').eq(1).val();
					var value3=$(ptd[j]).find('input').eq(2).val();
					trStr+='<tr><td>'+value2+
		                	'<input name="sampList['+i+'].containerList['+j+'].containerId"  value="'+value1+'" type="hidden" />'+
							'<input name="sampList['+i+'].containerList['+j+'].container"  value="'+value2+'" type="hidden" />'+
		               		'</td><td width="40">'+
		                	'<input name="sampList['+i+'].containerList['+j+'].num" value="'+value3+'" type="text"  class="form-control required digits" validate="required"/>'+ 
		               		'</td><td width="34" style="text-align:center;"><a onclick="removeTr(this)"><i class="fa fa-close text-danger"></i></a></td></tr>';
				}
				$('#sampTb_ct_'+i).html(trStr);
			});
		}
	}
	function copyCtZk(n){
		var ptd=$('#zkTb_ct_'+n).children('tr');
		if(ptd.length>0){
			var i=n;
			$('#zkTb tr:gt('+n+')').each(function(){
				i++;
				var trStr='';
				for(var j=0;j<ptd.length;j++){
					var value1=$(ptd[j]).find('input').eq(0).val();
					var value2=$(ptd[j]).find('input').eq(1).val();
					var value3=$(ptd[j]).find('input').eq(2).val();
					trStr+='<tr><td>'+value2+
		                	'<input name="zsampList['+i+'].containerList['+j+'].containerId"  value="'+value1+'" type="hidden" />'+
							'<input name="zsampList['+i+'].containerList['+j+'].container"  value="'+value2+'" type="hidden" />'+
		               		'</td><td width="40">'+
		                	'<input name="zsampList['+i+'].containerList['+j+'].num" value="'+value3+'" type="text"  class="form-control required digits" validate="required"/>'+ 
		               		'</td><td width="34" style="text-align:center;"><a onclick="removeTr(this)"><i class="fa fa-close text-danger"></i></a></td></tr>';
				}
				$('#zkTb_ct_'+i).html(trStr);
			});
		}
	}
	</script>
	<script type="text/javascript">
	function closeTd(obj){
		$(obj).html('展开');
		$(obj).closest('tr').next().hide();
		$(obj).attr('onclick','openTd(this)');
	}
	function openTd(obj){
		$(obj).html('闭合');
		$(obj).closest('tr').next().show();
		$(obj).attr('onclick','closeTd(this)');
	}
	function chooseItem(tbId,allIds,n,samplingId){
		var id=$('#'+tbId+'_itemIds_'+n).val();
		//获取已选项目
		var	url='item_select.do?ids='+allIds+'&id='+id;
		layer.open({
			title:'项目选择',	
			type: 2,
			 area: ['800px', '450px'],
			fix: false, //不固定
			maxmin: true,
			content: url,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#'+tbId+'_itemIds_'+n).val(data.id.join(','));
				$('#'+tbId+'_itemNames_'+n).val(data.name.join(','));
			}
		});
	}
	function deleteSamp(sampId){
		$.ajax({ 
			url:"${basePath}bus/sampling/deleteSamp.do",
			data: {'id':sampId},
			async:false,
			success: function(data){
				if(data.status=='success'){
					parent.toastr.success(data.message, '');
					location.reload();
				}else{
					parent.toastr.error(data.message, '');
				}
			},
			error:function(ajaxobj){  
				parent.toastr.error(ajaxobj, '');
		    }
		});
	}
	function addSamp(pointId,type){
		$.ajax({ 
			url:"${basePath}bus/sampling/addSamp.do",
			data: {'pointId':pointId,'type':type},
			async:false,
			success: function(data){
				if(data.status=='success'){
					parent.toastr.success(data.message, '');
					location.reload();
				}else{
					parent.toastr.error(data.message, '');
				}
			},
			error:function(ajaxobj){  
				parent.toastr.error(ajaxobj, '');
		    }
		});
	}
	//选中要添加质控样的项目
	function addSampZk(pointId,type,allItemIds){
		//获取已选项目
		var	url='item_select.do?ids='+allItemIds;
		layer.open({
			title:'质控项目选择',	
			type: 2,
			 area: ['800px', '450px'],
			fix: false, //不固定
			maxmin: true,
			content: url,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				fnSaveSampZk(pointId,type,data.id.join(','));
			}
		});
	}
	//后台保存质控项目
	function fnSaveSampZk(pointId,type,itemIds){
		$.ajax({ 
			url:"${basePath}bus/sampling/addSamp.do",
			data: {'pointId':pointId,'type':type,'ids':itemIds},
			async:false,
			success: function(data){
				if(data.status=='success'){
					parent.toastr.success(data.message, '');
					location.reload();
				}else{
					parent.toastr.error(data.message, '');
				}
			},
			error:function(ajaxobj){  
				parent.toastr.error(ajaxobj, '');
		    }
		});
	}
	//删除样品样品
	function delSamp(pointId,type,num){
		if (num == 1)//样品数不能小于1
		{
			parent.toastr.error('频次不能小于1', '');
			return;
		}
		$.ajax({ 
			url:"${basePath}bus/sampling/delSamp.do",
			data: {'pointId':pointId,'type':type},
			async:false,
			success: function(data){
				if(data.status=='success'){
					 parent.toastr.success(data.message, '');
					 window.location.reload(true);
				}else{
					parent.toastr.error(data.message, '');
				}
			},
			error:function(ajaxobj){  
				parent.toastr.error(ajaxobj, '');
		    }
		});
	}
	</script>
</body>
</html>
