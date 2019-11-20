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
<style type="text/css">
.sub_item_span{
	min-width: 100px;
	margin-right: 10px;
}
.sub_item_span a{
	float: right;
}
.radio label{
	padding-left: 5px;
}

</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><strong>任务登记</strong></li>
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
					<input id="id" name="id" value="${vo.id}" type="hidden" />
					<input name="sampType" value="${vo.sampType}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>受<br>检<br>单<br>位</label></td>
							<td class="width-10 active"><label class="pull-right">单位名称:</label></td>
							<td class="width-30">
								<div class="input-group" style="width: 100%">
									<input type="text" id="custName" name="custVo.custName" class="form-control required" validate="required" placeholder="请选择" value="${vo.custVo.custName}"> 
									<input type="hidden" id="custId" name="custVo.clientVo.id" value="${vo.custVo.clientVo.id}">
									<div class="input-group-btn">
                                         <button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown">
                                             <span class="caret"></span>
                                         </button>
                                         <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                         </ul>
                                     </div>
								</div>
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
										<input type="text" id="industry" name="custVo.industry" class="form-control "  value="${vo.custVo.industry}">
										<div class="input-group-btn">
											<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="border-right-color: #ccc; border-top-color: #ccc; border-bottom-color: #ccc;">
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
								<div class="input-group" style="width: 100%">
									<input type="text" id="wtCustName" name="custVo.wtName" class="form-control" value="${vo.custVo.wtName}" > 
									<input type="hidden" id="wtCustId" name="custVo.customerVo.id" value="${vo.custVo.customerVo.id}">
									 <div class="input-group-btn">
                                         <button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown">
                                             <span class="caret"></span>
                                         </button>
                                         <ul class="dropdown-menu dropdown-menu-right" role="menu">
                                         </ul>
                                     </div>
								</div>
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
							<td id="rowsTd" class="active" rowspan="9" style="text-align: center;"><label>监<br>测<br>要<br>求</label></td>
							<td class="active"><label class="pull-right">样品类别:</label></td>
							<td colspan="3">
								<div class="input-group" style="width: 100%">
									<input type="text" id="sampTypeName" name="sampTypeName" class="form-control required" validate="required" placeholder="请选择" value="${vo.sampTypeName}" onclick="fnSelectSampType()"> 
									<input type="hidden" id="sampTypeId" name="sampTypeId" value="${vo.sampTypeId}">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectSampType()">选择</button>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								<select name="taskType" id="taskType" class="form-control required" validate="required">
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
							<td class="active"><label class="pull-right">样品来源:</label></td>
							<td>
								<div class="radio i-checks">
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="送样" name="source" <c:if test="${vo.source=='送样'}">checked</c:if>>
                                          </div>送样
                                      </label>
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="外采" name="source" <c:if test="${vo.source=='外采'}">checked</c:if>>
                                          </div>外采
                                      </label>
                                 </div>
							</td>
							 
						</tr>
						<tr class="tpTr">
							<td class="active"><label class="pull-right">样品处理要求:</label></td>
							<td>
								<div class="radio i-checks">
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="委托方自取" name="dealRequest" <c:if test="${vo.dealRequest=='委托方自取'}">checked</c:if>>
                                          </div>委托方自取
                                      </label>
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="委托检测机构进行销毁" name="dealRequest" <c:if test="${vo.dealRequest=='委托检测机构进行销毁'}">checked</c:if>>
                                          </div>委托检测机构进行销毁
                                      </label>
                                  </div>
							</td>
							<td class="active"><label class="pull-right">样品保存条件:</label></td>
							<td>
								<select id="saveRequest" name="saveRequest" class="form-control">
									<option value="">-请选择-</option>
									<c:forEach items="${srList}" var="e">
										<c:choose>
											<c:when test="${vo.saveRequest==e}">
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
						<tr class="tpTr">
							<td class="active"><label class="pull-right">送样人员:</label></td>
							<td>
								<input type="text" id="cyName" name="cyName" class="form-control required" validate="required" value="${vo.cyName}">
							</td>
							<td class="active"><label class="pull-right">送样日期:</label></td>
							<td>
								<div class="input-group date">
									<input type="text" id="cyDate" name="cyDate" class="form-control datetimeISO required" validate="required" value="${vo.cyDate}">
									 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">报告份数:</label></td>
							<td>
								<input type="text" id="reportNum" name="reportNum"  class="form-control digits required" validate="required" value="${vo.reportNum}">
							</td>
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
                                          <input type="radio" value="其他" name="reportWay" <c:if test="${vo.reportWay=='其他'}">checked</c:if>>
                                          </div>其他
                                      </label>
                                 </div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">是否加急:</label></td>
							<td>
								<div class="radio i-checks">
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="是" name="jj" <c:if test="${vo.jj=='是'}">checked</c:if>>
                                          </div>是
                                      </label>
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="否" name="jj" <c:if test="${vo.jj=='否'}">checked</c:if>>
                                          </div>否
                                      </label>
                                 </div>
							</td>
							<td class="active"><label class="pull-right">要求完成日期:</label></td>
							<td>
								<div class="input-group date">
									<input type="text" id="finishDate" name="finishDate"  class="form-control dateISO" value="${vo.finishDate}">
									 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">是否评价:</label></td>
							<td>
								<div class="radio i-checks">
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="是" name="pj" <c:if test="${vo.pj=='是'}">checked</c:if>>
                                          </div>是
                                      </label>
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="否" name="pj" <c:if test="${vo.pj=='否'}">checked</c:if>>
                                          </div>否
                                      </label>
                                 </div>
							</td>
							 <td class="active"><label class="pull-right">是否分包:</label></td>
								<td>
									<div class="radio i-checks">
	                                      <label>
	                                          <div class="iradio_square-green">
	                                          <input type="radio" value="是" name="fb" <c:if test="${vo.fb=='是'}">checked</c:if>>
	                                          </div>是
	                                      </label>
	                                      <label>
	                                          <div class="iradio_square-green">
	                                          <input type="radio" value="否" name="fb" <c:if test="${vo.fb=='否'}">checked</c:if>>
	                                          </div>否
	                                      </label>
	                                 </div>
								</td>
						</tr>
						<tr class="pjTr">
							<td class="active"><label class="pull-right">评价标准:</label></td>
							<td colspan="3">
								<div class="input-group" style="width: 100%">
									<input type="text" id="standNames" name="standNames" class="form-control" placeholder="请选择" value="${vo.standNames}"  onclick="fnSelectPstand()"> 
									<input type="hidden" id="standIds" name="standIds" value="${vo.standIds}">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectPstand()">选择</button>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">受理人员:</label></td>
							<td>
								<input type="text" id="userName" name="userName"  class="form-control" value="${vo.userName}" readonly="readonly">
								<input type="hidden" id="userId" name="userId" value="${vo.userId}">
							</td>
							<td class="active"><label class="pull-right">受理日期:</label></td>
							<td>
								<div class="input-group date">
									<input type="text" id="date" name="date"  class="form-control datetimeISO" value="${vo.date}">
									 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<th class="active"><label class="pull-right">上传附件:</label></th>
							<td>
								<input type="file" name="files" multiple="multiple" class="form-control"/>
							</td>
							<td colspan="2" id="removeFile">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-info">${e.fileName}</a>
									 	<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="remark" name="remark" placeholder="备注" maxlength="128">${vo.remark}</textarea>
							</td>
						</tr>
						<tr>
							<td colspan="5">
								检测点位信息
								<div  style="float: right;">
									<button onclick="fnPoint()" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>选择点位</button>
									&nbsp;<button onclick="addPoint('')" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>新增一行</button>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="5">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th width="80">序号</th>
											<th width="150">样品名称</th>
											<th width="200" class="tpTd">检测点位</th>
											<th width="80" class="tpTd">点位编号</th>
											<th>检测项目</th>
											<th width="50">
											</th>
										</tr>
									</thead>
									<tbody id="point_tb">
										<c:forEach items="${vo.tpList}" var="e" varStatus="v">
											<tr key="${v.index}" >
												<td>
													<input name="tpList[${v.index}].sort" value="${e.sort}" type="text"  class="form-control digits required" validate="required"/>
													<input name="tpList[${v.index}].id" value="${e.id}" type="hidden" />
												</td>
												<td>
													<select id="sampTypeId_${v.index}" name="tpList[${v.index}].sampTypeId" class="form-control required" validate="required" onchange="changeSamp(${v.index});">
														<c:forEach items="${sampList}" var="e1">
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
												</td>
												<td class="tpTd">
													<input id="pointName_${v.index}" name="tpList[${v.index}].pointName" value="${e.pointName}" type="text"  class="form-control required" validate="required"  onchange="checkPoint(this)"/>
												</td>
												<td class="tpTd">
													<input id="pointCode_${v.index}" name="tpList[${v.index}].pointCode" value="${e.pointCode}" type="text"  class="form-control" />
												</td>
												<td>
													<div class="input-group" style="width: 100%">
														<textarea id="itemNames_${v.index}" name="tpList[${v.index}].itemNames" class="form-control required" validate="required" onclick="chooseItem('${v.index}')">${e.itemNames}</textarea>
														<input id="itemIds_${v.index}" name="tpList[${v.index}].itemIds" value="${e.itemIds}" type="hidden" />
														<input id="imId_${v.index}" name="tpList[${v.index}].imId" value="${e.imId}" type="hidden" />
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span>
													</div>
												</td>
												<td align="center">
													<a class="btn btn-xs btn-outline btn-danger"  href="javascript:;" onclick="deleteTr('${e.id}',this);"><i class="fa fa-times"></i></a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
									<!-- <tfoot>
										<tr>
											<td colspan="7"  style="color: red;">
												注：声检测，同点位昼间夜间分两行，用样品名称标注昼间、夜间；
											</td>
										</tr>
									</tfoot> -->
								</table>
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
								 		<td align="center">
								 			<a  href="javascript:;" onclick="removeTr(this);"><font color="red">删除</font></a>
								 		</td>
								 	</tr>
								</c:forEach>
							</tbody>
						</table>	 
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:void(0);" onclick="formSubmitSave('save4Data.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" href="javascript:void(0);" onclick="formSubmit('${fn:length(vo.id)>0? 'update4Data.do?isCommit=1':'add4Data.do?isCommit=1'}');"><i class="fa fa-mail-forward" aria-hidden="true"></i> 保存并提交</a>
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
<script src="${basePath}h/js/plugins/suggest/bootstrap-suggest.min.js"></script>
<script type="text/javascript">
$("#custName").bsSuggest({
    url: "${basePath}cus/client/ajax4Json.do",
    effectiveFields: ["name","person"], 
    effectiveFieldsAlias:{name: "名称",person: "联系人"},
    searchFields: ["name","person"], 
    showBtn: false,
    idField: "id",
    keyField: "name"
}).on('onSetSelectValue', function (e, keyword, row) {
	$("#custId").val(row.id);
	$("#custName").val(row.name);
	$("#custUser").val(row.person);
	$("#custAddress").val(row.address);
	$("#custTel").val(row.phone);
	$("#industry").val(row.cusType);
	$("#attribute").val(row.attribute);
	$("#product").val(row.product);
});
$("#wtCustName").bsSuggest({
    url: "${basePath}cus/customer/ajax4FullJson.do",
    effectiveFields: ["name","person","code"], 
    effectiveFieldsAlias:{name: "名称",code: "编号",person: "联系人"},
    searchFields: ["name","person"], 
    showBtn: false,
    idField: "id",
    keyField: "name"
}).on('onSetSelectValue', function (e, keyword, row) {
	$("#wtCustId").val(row.id);
	$("#wtCustName").val(row.name);
	$("#wtUser").val(row.person);
	$("#wtAddress").val(row.address);
	$("#wtTel").val(row.telephone);
	$("#wtEmail").val(row.email);
	$("#wtZip").val(row.zip);
});
 
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
//判定标准 弹出层
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
	$('input[type="file"]').prettyFile();
	checkSampSource();
	checkPj();
	checkFb();
	
	var arrStr=industrySelect();
	$('#industry').bsSuggest({
	    indexId: 0, //data.value 的第几个数据，作为input输入框的内容
	    indexKey: 0, //data.value 的第几个数据，作为input输入框的内容
	    data: getData(arrStr),
	    autoDropup:true,
	    showBtn: true
	});
	
});
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
function industrySelect() {
	var v='';
	$.ajax({
		url : '${basePath}sys/code/ajaxCodeContent.do?code=cus-sshy',
		datatype : "json",
		async:false,
		success : function(data) {
			v=data;
		}
	});
	return v;
}
function formSubmitSave(url){
	$('#thisForm').attr('action',url);
	$('#thisForm').submit()
}
function formSave(){
	$('#thisForm').attr('action','saveData.do');
	$('#thisForm').ajaxSubmit(function(res) {
    	if(res.status=='success'){
    	   $('#id').val(res.object);
	        return true;
        }else{
        	parent.toastr.error(res.message, '');
        	return false;
        }
	});
}

function formSubmit(url){
	/*var l=$('#point_tb').find('tr').length;
	if(l==0){
		parent.toastr.error('请添加点位信息 ！', '');
		return false;
	}*/
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
	    	 $('#thisForm').attr('action',url);
	    	 $('#thisForm').submit()
    	})
	}else{
		parent.toastr.error('请检查必填项！', '');
	}
}
</script>
<script>
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
		  changeSampType();
		}
	});
}
function changeSampType(){
	var opt=getSampNameOpt();
	$('select[id^="sampTypeId_"]').each(function(){
		$(this).html(opt);
	});
	$('#point_tb').find('tr').each(function(n){
		changeSamp(n);
	});
}
function getSampNameOpt(){
	var sampTypeId=$('#sampTypeId').val();
	var optionstring = '';
	$.ajax({
		url : '${basePath}init/sampType/listData.do?ids='+sampTypeId,
		datatype : "json",
		async:false,
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				optionstring += '<option value="'+data[i].id+'" >'+data[i].name+'</option>';
			}
		}
	});
	return optionstring;
}

function fnPoint(){
	var custName=$('#custName').val();
	var sampTypeId=$('#sampTypeId').val();
	if(null==custName||custName==''){
		layer.msg('请输入客户信息！');
		return false;
	}
	if(null==sampTypeId||sampTypeId==''){
		layer.msg('请选择样品类别！');
		return false;
	}
	layer.open({
		title:'点位选择',	
		type: 2,
		area: ['800px', '80%'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}cus/clientPoint/selects.do?clientVo.name='+encodeURI(custName)+'&sampTypeId='+sampTypeId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			if(data.length>0){
				for(var i=0;i<data.length;i++){
					addPoint(data[i]);
				}
			}
		}
	});
}
function addPoint(data){
	var sampTypeId=$('#sampTypeId').val();
	if(null==sampTypeId||sampTypeId==''){
		layer.msg('请选择样品类别！');
		return false;
	}
	var opt=getSampNameOpt();
	var l=$('#point_tb').find('tr').length;
	var index=0;
	if(l>0){
		index=parseInt($('#point_tb').find('tr').eq(l-1).attr('key'));
		index++;
	}
	var sampTypeId='';
	var pointName='';
	var pointCode='';
	var sampName='';
	var itemId='';
	var itemName='';
	var imId='';
	if(data!=''){
		sampTypeId=data.sampTypeId;
		pointName=data.name;
		pointCode=data.code;
		sampName=data.sampName;
		itemId=data.itemId;
		itemName=data.itemName;
		imId=data.imId;
	} 
	$('#point_tb').append($('<tr key="'+index+'">')
			.append('<td><input name="tpList['+index+'].sort" value="'+(index+1)+'" type="text"  class="form-control digits required" validate="required"/></td>')
			.append('<td><select id="sampTypeId_'+index+'" name="tpList['+index+'].sampTypeId" class="form-control required" validate="required" onchange="changeSamp('+index+')">'+opt+'</select></td>')
			//.append('<td><input id="sampName_'+index+'" name="tpList['+index+'].sampName" value="'+sampName+'" type="text"  class="form-control" /></td>')
			.append('<td class="tpTd"><input id="pointName_'+index+'" name="tpList['+index+'].pointName" value="'+pointName+'" type="text"  class="form-control required" validate="required"  onchange="checkPoint(this)"/></td>')
			.append('<td class="tpTd"><input id="pointCode_'+index+'" name="tpList['+index+'].pointCode" value="'+pointCode+'" type="text"  class="form-control"/></td>')
			//.append('<td><input id="sampNum_'+index+'" name="tpList['+index+'].sampNum" value="1" type="text"  class="form-control digits required"  validate="required" min="1"/></td>')
			.append('<td><div class="input-group" style="width: 100%"><textarea id="itemNames_'+index+'" name="tpList['+index+'].itemNames" class="form-control required" validate="required" onclick="chooseItem('+index+')">'+itemName+'</textarea><input id="itemIds_'+index+'" name="tpList['+index+'].itemIds" value="'+itemId+'" type="hidden" /><input id="imId_'+index+'" name="tpList['+index+'].imId" value="'+imId+'" type="hidden" /><span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span></div></td>')
			.append('<td  align="center"><a class="btn btn-xs btn-outline btn-danger"  href="javascript:;"  onclick="deleteTr(\'\',this);"><i class="fa fa-times"></i></a></td>'));
		
	$('#sampTypeId_'+index).find('option[value="'+sampTypeId+'"]').attr('selected','selected');
	
	var source=$('input[name="source"]:checked').val();
	if(source=='送样'){
		$('.tpTd').hide();
		$('.tpTd').find('input').removeAttr('validate');
		$('.tpTd').find('input').removeClass('required');
	}else{
		$('.tpTd').show();
		$('.tpTd').find('input[id^="pointName_"]').attr('validate','required');
		$('.tpTd').find('input[id^="pointName_"]').addClass('required');
	}
}
function copyVal(obj){
	obj=$(obj);
	var textValue=obj.closest('td').find('textarea').val();
	var idValue=obj.closest('td').find('input').val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#point_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
		$(this).find('td').eq(indexTd).find('textarea').eq(0).val(textValue);
	});
}
function copyItem(obj){
	obj=$(obj);
	var textValue=obj.closest('td').find('textarea').val();
	var idValue=obj.closest('td').find('input').eq(0).val();
	var imValue=obj.closest('td').find('input').eq(1).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var sampTypeId=obj.closest('td').closest('tr').find('select[id^="sampTypeId_"]').val();
	var indexTd=obj.closest('td').index();
	obj.closest('tbody').find('tr:gt('+indexTr+')').each(function(){
		var thisId=$(this).find('select[id^="sampTypeId_"]').val();
		if(thisId==sampTypeId){
			$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
			$(this).find('td').eq(indexTd).find('input').eq(1).val(imValue);
			$(this).find('td').eq(indexTd).find('textarea').eq(0).val(textValue);
		}
	});
}
function deleteTr(id,obj){
	$.ajax({
		url:'${basePath}bus/task/deletePoint.do?id='+id,
		dataType:"json",
		type:"post",
		async:false,
		success: function(data){
			if(data.status=='success'){
				parent.toastr.success(data.message, '');
				$(obj).closest('tr').remove();
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
	$(obj).closest('tr').remove();
}
function chooseItem(n){
	var itemIds=$('#imId_'+n).val();
	var sampTypeId=$('#sampTypeId_'+n).val();
	var source=$('input[name="source"]:checked').val();
	var sampType='${vo.sampType}';
	var	url='${basePath}init/item/im_selects.do?ids='+itemIds+'&sampTypeIds='+sampTypeId+'&sampType='+encodeURI(sampType);
	if(source=='送样'){
		url='${basePath}init/item/im_selects.do?isNow=N&ids='+itemIds+'&sampTypeIds='+sampTypeId+'&sampType='+encodeURI(sampType);
	}
	parent.layer.open({
		title:'检测项目',
		type: 2,
		area: ['1000px', '80%'],
		fix: false, //不固定
		maxmin: true,
		content: url,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin=layero.find('iframe')[0];
			var data=iframeWin.contentWindow.fnSelect();
			$('#itemIds_'+n).val(data.id);
			$('#itemNames_'+n).val(data.name);
			$('#imId_'+n).val(data.im);
			parent.layer.close(index);
		}
	});
}
//更新样品类型清空点位等信息
function changeSamp(n){
	$('#itemIds_'+n).val('');
	$('#itemNames_'+n).val('');
	$('#imId_'+n).val('');
	$('#pointName_'+n).val('');
};
</script>
<script type="text/javascript">
<!--评价信息信息-->
$('input[name="pj"]').on('ifChecked', function(event){
	checkPj();
});
function checkPj(){
	var pj=$('input[name="pj"]:checked').val();
	var source=$('input[name="source"]:checked').val();
	if(pj=='是'){
		$('.pjTr').find('input').attr('validate','required');
		$('.pjTr').find('input').addClass('required');
		$('.pjTr').show();
		if(source=='送样'){
			$('#rowsTd').attr('rowspan',11);
		}else{
			$('#rowsTd').attr('rowspan',9);
		}
	}else{
		$('.pjTr').find('input').removeAttr('validate');
		$('.pjTr').find('input').removeClass('required');
		$('.pjTr').hide();
		if(source=='送样'){
			$('#rowsTd').attr('rowspan',10);
		}else{
			$('#rowsTd').attr('rowspan',8);
		}
	}
}
</script>
<script type="text/javascript">
<!--样品来源信息-->
$('input[name="source"]').on('ifChecked', function(event){
	checkSampSource();
});
function checkSampSource(){
	var source=$('input[name="source"]:checked').val();
	var pj=$('input[name="pj"]:checked').val();
	if(source=='送样'){
		$('.tpTd').hide();
		$('.tpTd').find('input').removeAttr('validate');
		$('.tpTd').find('input').removeClass('required');
		$('.tpTr').show();
		if(pj=='是'){
			$('#rowsTd').attr('rowspan',11);
		}else{
			$('#rowsTd').attr('rowspan',10);
		}
		$('#cyName').attr('validate');
		$('#cyName').addClass('required');
		$('#cyDate').attr('validate');
		$('#cyDate').addClass('required');
	}else{
		$('.tpTd').show();
		$('.tpTd').find('input[id^="pointName_"]').attr('validate','required');
		$('.tpTd').find('input[id^="pointName_"]').addClass('required');
		$('.tpTr').hide();
		if(pj=='是'){
			$('#rowsTd').attr('rowspan',9);
		}else{
			$('#rowsTd').attr('rowspan',8);
		}
		$('#cyName').removeAttr('validate');
		$('#cyName').removeClass('required');
		$('#cyDate').removeAttr('validate');
		$('#cyDate').removeClass('required');
	}
}
</script>
<script type="text/javascript">
<!--分包信息-->
$('input[name="fb"]').on('ifChecked', function(event){
	checkFb();
});
function checkFb(){
	var radioVal=$('input[name="fb"]:checked').val();
	if(radioVal=='是'){
		$('.fbTr').show()
	}else{
		$('.fbTr').hide()
		$('#fb_tb').html('');
	}
}
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
			$('#num'+n).val(data.id.length);
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
				.append('<td><a  href="javascript:;" onclick="removeTr(this);"><font color="red">删除</font></a></td>'));
		index++;
	});
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
</script>
</body>
</html>
