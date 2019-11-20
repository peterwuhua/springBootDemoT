<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><strong>项目立项</strong></li>
					<li><strong>${vo.sampType}</strong></li>
					<li><strong>编辑</strong>
						<c:if test="${vo.isBack=='Y'}">
							（<span style="color: red"> 退回原因：${logVo.msg}</span> ）
						</c:if>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<input name="sampType" value="${vo.sampType}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>受<br>检<br>单<br>位</label></td>
								<td class="width-10 active"><label class="pull-right">单位名称:</label></td>
								<td class="width-30">
									<input type="text" id="custName" name="custVo.custName" class="form-control required" validate="required" value="${vo.custVo.custName}"> 
									<input type="hidden" id="custId" name="custVo.clientVo.id" value="${vo.custVo.clientVo.id}">
								</td>
								 <td class="width-10 active"><label class="pull-right">单位地址:</label></td>
								<td class="width-30">
									<input type="text" id="custAddress" name="custVo.custAddress" class="form-control required" validate="required" value="${vo.custVo.custAddress}"> 
								</td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td>
									<input type="text" id="custUser" name="custVo.custUser" class="form-control required" validate="required" value="${vo.custVo.custUser}"> 
								</td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td>
									<input type="text" id="custTel" name="custVo.custTel" class="form-control required" validate="required" value="${vo.custVo.custTel}"> 
								</td>
							</tr>
							<tr>
								 <td class="active"><label class="pull-right">行业分类和代码:</label></td>
								<td>
									<%-- <input type="text" id="industry" name="custVo.industry" class="form-control" value="${vo.custVo.industry}"> --%>
									<div class="input-group" style="width: 100%;">
										<input type="text" id="industry" name="custVo.industry" class="form-control required" validate="required" value="${vo.custVo.industry}">
										<div class="input-group-btn">
											<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="border-right-color: red; border-top-color: red; border-bottom-color: red;">
												<span class="caret"></span>
											</button>
											<ul class="dropdown-menu dropdown-menu-right" role="menu">
											</ul>
										</div>
									 </div> 
								</td>
								<td class="active"><label class="pull-right">单位性质:</label></td>
								<td>
									<input type="text" id="attribute" name="custVo.attribute" class="form-control" value="${vo.custVo.attribute}"> 
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">主要产品:</label></td>
								<td colspan="3">
									<input type="text" id="product" name="custVo.product" class="form-control" value="${vo.custVo.product}"> 
								</td>
							</tr>
							<tr>
								<td class="active" rowspan="3" style="text-align: center;width: 50px;"><label>委<br>托<br>单<br>位</label></td>
								<td class="width-10 active"><label class="pull-right">单位名称:</label></td>
								<td class="width-30">
									<input type="text" id="wtCustName" name="custVo.wtName" class="form-control" value="${vo.custVo.wtName}" > 
									<input type="hidden" id="wtCustId" name="custVo.customerVo.id" value="${vo.custVo.customerVo.id}">
								</td>
								<td class="width-10 active"><label class="pull-right">单位地址:</label></td>
								<td class="width-30">
									<input type="text" id="wtAddress" name="custVo.wtAddress" class="form-control" value="${vo.custVo.wtAddress}"> 
								</td>
								
							</tr>
							<tr>
								<td class="active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td>
									<input type="text" id="wtUser" name="custVo.wtUser" class="form-control" value="${vo.custVo.wtUser}"> 
								</td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td>
									<input type="text" id="wtTel" name="custVo.wtTel" class="form-control" value="${vo.custVo.wtTel}"> 
								</td>
							</tr>
							<tr>
								 <td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
								<td>
									<input type="text" id="wtEmail" name="custVo.wtEmail" class="form-control email" value="${vo.custVo.wtEmail}"> 
								</td>
								<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
								<td>
									<input type="text" id="wtZip" name="custVo.wtZip" class="form-control" value="${vo.custVo.wtZip}"> 
								</td>
							</tr>
							<tr>
								<td id="rowsTd" class="active" rowspan="9" style="text-align: center;"><label>检<br>测<br>要<br>求</label></td>
								<td class="active"><label class="pull-right">样品类别:</label></td>
								<td>
									${vo.sampTypeName}
									<input type="hidden" id="sampTypeName" name="sampTypeName" class="form-control required" validate="required" value="${vo.sampTypeName}" > 
									<input type="hidden" id="sampTypeId" name="sampTypeId" value="${vo.sampTypeId}">
								</td>
								<td class="active"><label class="pull-right">样品名称:</label></td>
								<td>
									<input type="text" id="sampName" name="sampName" class="form-control" value="${vo.sampName}" > 
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">检测类型:</label></td>
								<td>
									<select name="taskType" id="taskType" class="form-control required" validate="required">
										<option value="">请选择</option>
										<c:forEach items="${taskTypeList}" var="e" varStatus="s">
											<c:choose>
												<c:when test="${e.name==vo.taskType}">
													<option value="${e.name}" selected="selected">${e.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.name}">${e.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<td class="active"><label class="pull-right">项目频次:</label></td>
								<td>
									${vo.pc}${vo.pcUnit}
									<input type="hidden" id="pc" name="pc"  value="${vo.pc}" >
									<input type="hidden" id="pcUnit" name="pcUnit" value="${vo.pcUnit}">
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">立项日期:</label></td>
								<td>
									<div class="input-group date">
										<input type="text" id="rdate" name="rdate"  class="form-control dateISO required" placeholder="请选择" validate="required" value="${vo.rdate}">
										 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
								<td class="active"><label class="pull-right">单次周期:</label></td>
								<td>
									${vo.cycle}${vo.cycleUnit}
									<input type="hidden" id="cycle" name="cycle"  value="${vo.cycle}" >
									<input type="hidden" id="cycleUnit" name="cycleUnit"  value="${vo.cycleUnit}" >
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">报告份数:</label></td>
								<td><input type="number" id="reportNum" name="reportNum"  class="form-control required digits" validate="required" value="${vo.reportNum}"></td>
								
								<td class="active"><label class="pull-right">拟完成日期:</label></td>
								<td>
									<div class="input-group date">
										<input type="text" id="finishDate" name="finishDate"  class="form-control required dateISO" placeholder="请选择" validate="required" value="${vo.finishDate}">
										 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">取报告方式:</label></td>
								<td>
									<div class="radio i-checks">
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="自取" name="reportWay" <c:if test="${vo.reportWay=='自取'}">checked</c:if>>
                                            </div>自取
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="邮寄" name="reportWay" <c:if test="${vo.reportWay=='邮寄'}">checked</c:if>>
                                            </div>邮寄
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="送达" name="reportWay" <c:if test="${vo.reportWay=='送达'}">checked</c:if>>
                                            </div>送达
                                        </label>
                                         <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="其它" name="reportWay" <c:if test="${vo.reportWay=='其它'}">checked</c:if>>
                                            </div>其它
                                        </label>
                                    </div>
								</td>
								<td class="active"><label class="pull-right ">加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;急:</label></td>
								<td>
									<div class="radio i-checks">
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="是" name="jj" <c:if test="${vo.jj=='是'}">checked</c:if>>
                                            </div>是
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="否" name="jj" <c:if test="${vo.jj!='是'}">checked</c:if>>
                                            </div>否
                                        </label>
                                    </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">样品来源:</label></td>
								<td>
                                	${vo.zsy}
                                	<input type="hidden" id="zsy" name="zsy" value="${vo.zsy}"/>
								</td>
								<td class="active"><label class="pull-right ">同意使用非标准方法:</label></td>
								<td>
									<div class="radio i-checks">
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="是" name="fbzff" <c:if test="${vo.fbzff=='是'}">checked</c:if>>
                                            </div>是
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="否" name="fbzff" <c:if test="${vo.fbzff!='是'}">checked</c:if>>
                                            </div>否
                                        </label>
                                    </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">现场踏勘:</label></td>
								<td>
									${vo.xctk}
									<input type="hidden" id="xctk" name="xctk" value="${vo.xctk}"/>
								</td>
								<td class="active"><label class="pull-right ">是否评价:</label></td>
								<td>
									<div class="radio i-checks">
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="是" name="pj" <c:if test="${vo.pj=='是'}">checked</c:if>>
                                            </div>是
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="否" name="pj" <c:if test="${vo.pj!='是'}">checked</c:if>>
                                            </div>否
                                        </label>
                                    </div>
								</td>
							</tr>
							<tr id="pjTr">
								<td class="active"><label class="pull-right">评价依据:</label></td>
								<td colspan="3">
									<div class="input-group" style="width: 100%">
										<input type="text" id="standNames" name="standNames" class="form-control required" validate="required"  placeholder="请选择" value="${vo.standNames}" onclick="fnSelectPstand()"> 
										<input type="hidden" id="standIds" name="standIds" value="${vo.standIds}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectPstand()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">委托方提供资料:</label></td>
								<td colspan="3">
									<div class="checkbox i-checks">
                                        <label>
                                            <div class="icheckbox_square-green">
                                            <input type="checkbox" value="厂区总平面图" name="info" <c:if test="${fn:contains(vo.info,'厂区总平面图')}">checked</c:if>>
                                            </div>厂区总平面图
                                        </label>
                                        <label>
                                            <div class="icheckbox_square-green">
                                            <input type="checkbox" value="环保工程资料" name="info" <c:if test="${fn:contains(vo.info,'环保工程资料')}">checked</c:if>>
                                            </div>环保工程资料
                                        </label>
                                        <label>
                                            <div class="icheckbox_square-green">
                                            <input type="checkbox" value="主要污染源情况" name="info" <c:if test="${fn:contains(vo.info,'主要污染源情况')}">checked</c:if>>
                                            </div>主要污染源情况
                                        </label>
                                         <label>
                                            <div class="icheckbox_square-green">
                                            <input type="checkbox" value="项目工程资料" name="info" <c:if test="${fn:contains(vo.info,'项目工程资料')}">checked</c:if>>
                                            </div>项目工程资料
                                        </label>
                                         <label>
                                            <div class="icheckbox_square-green">
                                            <input type="checkbox" value="设备清单" name="info" <c:if test="${fn:contains(vo.info,'设备清单')}">checked</c:if>>
                                            </div>设备清单
                                        </label>
                                         <label>
                                            <div class="icheckbox_square-green">
                                            <input type="checkbox" value="其他" name="info" <c:if test="${fn:contains(vo.info,'其它')}">checked</c:if>>
                                            </div>其他
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
										 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
										 	<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
									 	</div>
									 </c:forEach>
								</td>
							</tr>
							<tr>
								<td class="active" rowspan="2" style="text-align: center;"><label>受<br>理<br>方<br>信<br>息</label></td>
								<td class="active"><label class="pull-right">立&nbsp;&nbsp;项&nbsp;&nbsp;人:</label></td>
								<td>
									<input type="hidden" id="userId" name="userId" value="${vo.userId}">
									<input type="text" id="userName" name="userName" class="form-control" placeholder="受理人" value="${vo.userName}" readonly="readonly">
								</td>
								<td class="active"><label class="pull-right ">受理日期:</label></td>
								<td>
									<div class="input-group date">
		                              	<input id="sdate" name="sdate" class="form-control required dateISO" validate="required" placeholder="受理日期" type="text" value="${vo.sdate}" />
		                              	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea rows="3" class="form-control" id="remark" name="remark" maxlength="128">${vo.remark}</textarea>
								</td>
							</tr>
							
						</tbody>
					</table>
					<c:if test="${vo.zsy=='送样'}">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							 <thead>
							 	<tr>
							 		<th style="width: 50px;">序号</th>
							 		<th style="width: 120px;">样品类型</th>
							 		<th style="width: 10%;">样品名称</th>
							 		<th style="width: 10%;">样品性状</th>
							 		<th style="width: 10%;">包装情况</th>
							 		<th >检测项目</th>
							 		<th style="width:10%;">备注</th>
							 	</tr>
							 </thead>
							<tbody id="detail_tb">
								<c:forEach items="${vo.pointList}" var="e" varStatus="s">
									<tr index="${s.index}">
								 		<td>
								 			${s.index+1}
								 			<input type="hidden" name="pointList[${s.index}].id" value="${e.id}">
								 		</td>
								 		<td>
								 			<select id="sampTypeId_${s.index}" name="pointList[${s.index}].sampTypeId" class="form-control required" validate="required">
								 				<c:forEach items="${sampTypeList}" var="e1" varStatus="s1">
								 					<c:choose>
								 						<c:when test="${e1.id==e.sampTypeId}">
								 							<option value="${e1.id}" selected="selected">${e1.name}</option>
								 						</c:when>
								 						<c:otherwise>
								 							<option value="${e1.id}">${e1.name}</option>
								 						</c:otherwise>
								 					</c:choose>
								 				</c:forEach>
								 			</select>
								 			<input type="hidden" id="sampTypeName_${s.index}" name="pointList[${s.index}].sampTypeName" value="${e.sampTypeName}">
								 		</td>
								 		<td>
								 			<input type="text" id="sampName_${s.index}" name="pointList[${s.index}].sampName" class="form-control required" validate="required" placeholder="请输入" value="${e.sampName}" >
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
									 			<input type="text" id="xz_${s.index}" name="pointList[${s.index}].xz" class="form-control" value="${e.xz}">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
									 			<input type="text" id="packing_${s.index}" name="pointList[${s.index}].packing" class="form-control" value="${e.packing}">
									 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span>
								 			</div>
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
												<input type="text" id="itemName_${s.index}" name="pointList[${s.index}].itemName" class="form-control required" validate="required" placeholder="请选择" value="${e.itemName}"  onclick="chooseItem('${s.index}');">
												<input type="hidden" id="itemId_${s.index}" name="pointList[${s.index}].itemId" value="${e.itemId}">
												<div class="input-group-btn"><button tabindex="-1" class="btn btn-primary" type="button"  onclick="chooseItem('${s.index}');">选择</button></div>
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span>
								 			</div>
								 			<input type="hidden" id="imId_${s.index}" name="pointList[${s.index}].imId" value="${e.imId}">
								 			<input type="hidden" id="fxPrice_${s.index}" name="pointList[${s.index}].fxPrice" value="${e.fxPrice}" >
								 		</td>
								 		<td>
								 			<input type="text" name="pointList[${s.index}].remark" class="form-control" value="${e.remark}" maxlength="128">
								 		</td>
								 	</tr>
								</c:forEach>
							</tbody>
						</table>
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tbody>
								<tr>
									<td class="active"><label class="pull-right">样品处理要求:</label></td>
									<td>
										<div class="radio i-checks">
		                                      <label>
		                                          <div class="iradio_square-green">
		                                          <input type="radio" value="委托方自取" name="schemeVo.dealRequest" <c:if test="${vo.schemeVo.dealRequest=='委托方自取'}">checked</c:if>>
		                                          </div>委托方自取
		                                      </label>
		                                      <label>
		                                          <div class="iradio_square-green">
		                                          <input type="radio" value="委托检测机构进行销毁" name="schemeVo.dealRequest" <c:if test="${vo.schemeVo.dealRequest=='委托检测机构进行销毁'}">checked</c:if>>
		                                          </div>委托检测机构进行销毁
		                                      </label>
		                                  </div>
									</td>
									<td class="active"><label class="pull-right">样品保存条件:</label></td>
									<td>
										<select id="saveRequest" name="schemeVo.saveRequest" class="form-control">
											<option value="">-请选择-</option>
											<c:forEach items="${srList}" var="e">
												<c:choose>
													<c:when test="${vo.schemeVo.saveRequest==e}">
														<option value="${e}" selected="selected">${e}</option>
													</c:when>
													<c:otherwise>
														<option value="${e}">${e}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td class="width-15 active"><label class="pull-right">检测费用:</label></td>
									<td class="width-35">
										<input type="text" id="price" name="invoiceVo.price" class="form-control number" value="${vo.invoiceVo.price}">
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
									<td class="width-15 active"><label class="pull-right">费用总计:</label></td>
									<td class="width-35">
										<input type="text" id="totalPrice" name="invoiceVo.totalPrice" class="form-control" value="${vo.invoiceVo.totalPrice}" readonly="readonly">
									</td>
									<td class="width-15 active"><label class="pull-right">合同费用:</label></td>
									<td class="width-35">
										<input type="text" id="htPrice" name="invoiceVo.htPrice" class="form-control" value="${vo.invoiceVo.htPrice}" readonly="readonly">
									</td>
								</tr>
								<tr>
									<td class="width-15 active"><label class="pull-right">送样人员:</label></td>
									<td class="width-35"><input type="text" id="cyUserName" name="schemeVo.cyUserName" class="form-control required" validate="required" value="${vo.schemeVo.cyUserName}"></td>
									<td class="width-15 active"><label class="pull-right">送样时间:</label></td>
									<td class="width-35">
										<div class="input-group date">
											<input type="text" id="cyDate" name="schemeVo.cyDate" class="form-control datetimeISO required" validate="required"  placeholder="请选择" value="${vo.schemeVo.cyDate}">
											 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
										</div>
									</td>
								</tr>
								<tr>
									<td class="active"><label class="pull-right ">是否分包:</label></td>
									<td>
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
									<td class="active"><label class="pull-right ">其&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;他:</label></td>
									<td colspan="3">
										<textarea rows="2" class="form-control" id="others" name="schemeVo.remark" maxlength="128">${vo.schemeVo.remark}</textarea>
									</td>
								</tr>
							</tbody>
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
									 			<input type="hidden" name="fbList[${s.index}].id" value="${e.id}">
									 		</td>
									 		<td>
									 			${e.fbVo.name}
									 			<input type="hidden" name="fbList[${s.index}].fbVo.id" value="${e.fbVo.id}">
									 		</td>
									 		<td>
									 			<input type="text" name="fbList[${s.index}].fbUser" class="form-control required" validate="required" value="${e.fbUser}">
									 		</td>
									 		<td>
									 			<input type="text" name="fbList[${s.index}].fbMobile" class="form-control required" validate="required" value="${e.fbMobile}">
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
									 			<input type="text" name="fbList[${s.index}].num" class="form-control digits required" validate="required" value="${e.num}">
									 		</td>					 		 
									 		<td>
									 			<input type="text" name="fbList[${s.index}].price" class="form-control number required" validate="required" value="${e.price}" onchange="countFbPrice()">
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
					</c:if>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('updated.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 更新</a>
							 <a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../include/js.jsp"%>
<script src="${basePath}h/js/plugins/suggest/bootstrap-suggest.min.js"></script>
<script>
	 
	$('input[type="file"]').prettyFile();
	function fnSelectSampType(){
		var sampTypeId=$('#sampTypeId').val();
		layer.open({
			title:'样品类型',	
			type: 2,
			area: ['300px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/sampType/selects_last.do?ids='+sampTypeId+'&name='+encodeURI('${vo.sampType}'),
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#sampTypeId').val(data.id);
			  $('#sampTypeName').val(data.name);
			}
		});
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
		$('input[name="pj"]').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
			changedPj();
		});
		function changedPj(){
			var radioVal=$('input[name="pj"]:checked').val();
			if(radioVal=='是'){
				$('#pjTr').show();
				$('#rowsTd').attr('rowspan',10);
				$('#standNames').attr('validate','required');
				$('#standNames').addClass('required');
			}else{
				$('#pjTr').hide();
				$('#rowsTd').attr('rowspan',9);
				$('#standNames').removeAttr('validate');
				$('#standNames').removeClass('required');
			}
		};
		//评价标准 弹出层
		function fnSelectPstand(){
			var id=$('#standIds').val();
			var sampTypeId=$('#sampTypeId').val();
			parent.layer.open({
				title:'评价标准',	
				type: 2,
				area: ['1000px', '500px'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}init/pstandard/selects2.do?ids='+id+'&sampTypeId='+sampTypeId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin=layero.find('iframe')[0];
				  var data=iframeWin.contentWindow.fnSelect();
				  $('#standIds').val(data.id);
				  $('#standNames').val(data.name);
					parent.layer.close(index);
				}
			});
		}
		$(document).ready(function() {
			$(".i-checks").iCheck({
				checkboxClass : "icheckbox_square-green",
				radioClass : "iradio_square-green",
			});
			changedPj();
			changed();
			var arrStr=industrySelect();
			$('#industry').bsSuggest({
			    indexId: 0, //data.value 的第几个数据，作为input输入框的内容
			    indexKey: 0, //data.value 的第几个数据，作为input输入框的内容
			    data: getData(arrStr),
			    autoDropup:true,
			    showBtn: true
			});
		});
		function industrySelect() {
			var v = '';
			$.ajax({
				url : '${basePath}sys/code/ajaxCodeContent.do?code=cus-sshy',
				datatype : "json",
				async:false,
				success : function(data) {
					v = data;
				}
			});
			return v;
		}
		function getData(arrStr){
			var data = {
			    	value: []
			    };
			var ut=arrStr.split(',');
			for(var i=0;i<ut.length;i++){
				data.value.push({word: ut[i]});
			}
			return data;
		}
		
		function formSubmitSave(url){
			$('#thisForm').attr('action',url);
			 $('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
			        backMainPage();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	</script>
<script type="text/javascript">

function copyVals(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('input').eq(0).val();
	var nameValue=obj.closest('td').find('input').eq(1).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#detail_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
		$(this).find('td').eq(indexTd).find('input').eq(1).val(nameValue);
	});
}
function copyItem(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('input').eq(0).val();
	var nameValue=obj.closest('td').find('input').eq(1).val();
	var imValue=obj.closest('td').find('input').eq(2).val();
	var priceValue=obj.closest('td').find('input').eq(3).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#detail_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
		$(this).find('td').eq(indexTd).find('input').eq(1).val(nameValue);
		$(this).find('td').eq(indexTd).find('input').eq(2).val(imValue);
		$(this).find('td').eq(indexTd).find('input').eq(3).val(priceValue);
	});
	countPrice();
}
function chooseItem(n){
	var sampTypeId=$('#sampTypeId_'+n).val();
	//sort=0代表普通项目 1代表现场项目
	var	url='${basePath}init/item/im_selects.do?isNow=N&ids='+$('#imId_'+n).val()+'&sampTypeIds='+sampTypeId;
	parent.layer.open({
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
			$('#itemId_'+n).val(data.id);
			$('#itemName_'+n).val(data.name);
			$('#imId_'+n).val(data.im);
			$('#fxPrice_'+n).val(data.price);
			parent.layer.close(index);
			countPrice();
		}
	});
}
function countPrice(){
	var t=0;
	$('input[id^="fxPrice_"]').each(function(){
		t+=parseFloat($(this).val());
	})
	$('#price').val(t);
	countHtPrice();
}
function countHtPrice(){
	var price=parseFloat($('#price').val());
	var discount=parseFloat($('#discount').val());
	var yhPrice=parseFloat($('#yhPrice').val());
	var jtPrice=parseFloat($('#jtPrice').val());
	var bgPrice=parseFloat($('#bgPrice').val());
	var otherPrice=parseFloat($('#otherPrice').val());
	$('#totalPrice').val(price+jtPrice+bgPrice+otherPrice);
	$('#htPrice').val(price*discount-yhPrice+jtPrice+bgPrice+otherPrice);
}
<!--分包相关信息-->
$('input[name="fb"]').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
	changed();
});
function changed(){
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
	$('input[id^="itemId"]').each(function(){
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
function removeTr(obj){
	$(obj).parent().parent().remove();
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
</script>
</body>
</html>
