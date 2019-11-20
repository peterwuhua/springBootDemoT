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
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
a{
	color: blue;
}
td>.table > tbody > tr > td,div>.table > tbody > tr > td{
padding: 2px;
}
.table > thead > tr > th{
text-align: center;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><strong>现场踏勘</strong></li>
					<li><strong>${vo.sampType}</strong></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<input id="id" name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: -1px;">
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
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
							<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
							<td class="width-35">
								${vo.sampName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">立项日期:</label></td>
							<td class="width-35">${vo.rdate}</td>
							<td class="width-15 active"><label class="pull-right">拟完成日期:</label></td>
							<td class="width-35">${vo.finishDate}</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
						<tr>
							<td colspan="4"><label>车间 / 岗位</label></td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 1px;">
								<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  style="margin-bottom: 0px;">
									<thead>
										<tr>
											<th width="50">序号</th>
											<th width="20%">车间名称</th>
											<th width="20%">工作制度</th>
											<th width="20%">工作方式</th>
											<th>工作内容</th>
											<th width="220"><button onclick="addRoom()" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i> 添加车间</button></th>
										</tr>
									</thead>
									<tbody id="room">
										<c:forEach items="${vo.roomList}" var="e" varStatus="v">
											<tr>
												<td>
													<input type="text" name="roomList[${v.index}].sort" class="form-control digits required" validate="required" value="${e.sort}">
													<input type="hidden" name="roomList[${v.index}].id"  value="${e.id}">
												</td>
												<td>
													<input type="text" name="roomList[${v.index}].name" class="form-control required" validate="required" value="${e.name}">
												</td>
												<td>
													<div class="input-group" style="width: 100%">
														<input type="text" name="roomList[${v.index}].workType" class="form-control" value="${e.workType}">
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
													 </div>
												</td>
												<td>
													<div class="input-group" style="width: 100%">
														<input type="text" name="roomList[${v.index}].workWay" class="form-control" value="${e.workWay}">
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
													 </div>
												</td>
												<td>
													<div class="input-group" style="width: 100%">
														<input type="text" name="roomList[${v.index}].works" class="form-control" value="${e.works}">
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
													 </div>
												</td>
												<td align="center">
													<a class="btn btn-outline btn-sm btn-danger" href="javascript:;" onclick="deleteRoom('${e.id}',this);"><i class="fa fa-remove"></i> 删除</font></a>
													<a class="btn btn-outline btn-sm btn-primary" href="javascript:;" onclick="addPoint('${e.id}');"><i class="fa fa-plus"></i> 检测点</font></a>
													<a class="btn btn-outline btn-sm btn-primary" href="javascript:;" onclick="addMaterial('${e.id}');"><i class="fa fa-plus"></i> 物料</font></a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4"><label>检测点</label></td>
						</tr>
					</table>	
					<div style="overflow-x:scroll;">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  style="margin-bottom: 0px;min-width: 1800px">
							<thead>
								<tr>
									<th rowspan="2" width="120">操作</th>
									<th width="50" rowspan="2">序号</th>
									<th width="100" rowspan="2">车间名称</th>
									<th width="100" rowspan="2">检测点名称</th>
									<th colspan="2">作业人数</th>
									<th rowspan="2" width="150">生产设备</th>
									<th colspan="2">设备数量</th>
									<th rowspan="2" >危害因素</th>
									<th rowspan="2" width="80">接触时间</th>
									<th rowspan="2" width="150">防护设备</th>
									<th colspan="2">设备数量</th>
									<th rowspan="2" width="150">防护用品<br>及佩戴情况</th>
									<th rowspan="2" width="120">操作</th>
								</tr>
								<tr>
									<th width="80">总数</th>
									<th width="80">数/班</th>
									<th width="80">总</th>
									<th width="80">开启</th>
									<th width="80">总</th>
									<th width="80">开启</th>
								</tr>
							</thead>
							<tbody id="point">
								<c:forEach items="${vo.potList}" var="e" varStatus="v">
									<tr>
										<td align="center">
											<a class="btn btn-outline btn-sm btn-danger" href="javascript:;" onclick="deletePoint('${e.id}',this);"><i class="fa fa-remove"></i> </font></a>
											<a class="btn btn-outline btn-sm btn-primary" href="javascript:;" onclick="addWork('${e.id}');"><i class="fa fa-plus"></i> 调查</font></a>
										</td>
										<td>
											<input type="text" name="potList[${v.index}].sort" class="form-control digits required" validate="required" value="${e.sort}">
											<input type="hidden" name="potList[${v.index}].id"  value="${e.id}">
										</td>
										<td>
											${e.roomVo.name}
										</td>
										<td>
											<input type="text" name="potList[${v.index}].name" class="form-control required" validate="required" value="${e.name}">
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="potList[${v.index}].workTal" class="form-control number" value="${e.workTal}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
											 </div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="potList[${v.index}].workNum" class="form-control number" value="${e.workNum}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
											 </div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="potList[${v.index}].productName" class="form-control" value="${e.productName}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
											 </div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="potList[${v.index}].productTal" class="form-control number" value="${e.productTal}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
											 </div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="potList[${v.index}].productNum" class="form-control number" value="${e.productNum}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
											 </div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" id="itemName_${v.index}" name="potList[${v.index}].itemNames" class="form-control required" validate="required" placeholder="请选择" value="${e.itemNames}"  onclick="chooseItem(${v.index});">
												<input type="hidden" id="itemId_${v.index}" name="potList[${v.index}].itemIds" value="${e.itemIds}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this,${v.index});return false;"></span>
								 			</div>
								 			<input type="hidden" id="imId_${v.index}" name="potList[${v.index}].imId" value="${e.imId}">
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="potList[${v.index}].workHours" class="form-control" value="${e.workHours}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
											 </div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="potList[${v.index}].fhName" class="form-control" value="${e.fhName}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
											 </div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="potList[${v.index}].fhTal" class="form-control number" value="${e.fhTal}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
											 </div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="potList[${v.index}].fhNum" class="form-control number" value="${e.fhNum}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
											 </div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="potList[${v.index}].others" class="form-control" value="${e.others}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
											 </div>
										</td>
										<td align="center">
											<a class="btn btn-outline btn-sm btn-danger" href="javascript:;" onclick="deletePoint('${e.id}',this);"><i class="fa fa-remove"></i> </font></a>
											<a class="btn btn-outline btn-sm btn-primary" href="javascript:;" onclick="addWork('${e.id}');"><i class="fa fa-plus"></i> 调查</font></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  style="margin-bottom: 0px;">	 
						<tr>
							<td colspan="4"><label>物料信息</label></td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 1px;">
								<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  style="margin-bottom: 0px;">
									<thead>
										<tr>
											<th width="50">序号</th>
											<th width="100">所在车间</th>
											<th width="100">类型</th>
											<th width="15%">物料名称</th>
											<th width="15%">主要成分及含量</th>
											<th width="15%">性状</th>
											<th width="15%">用量及使用<br>时间或产生量</th>
											<th>接触方式</th>
											<th width="50">操作</th>
										</tr>
									</thead>
									<tbody id="material">
										<c:forEach items="${vo.materialList}" var="e" varStatus="v">
											<tr>
												<td>
													<input type="text" name="materialList[${v.index}].sort" class="form-control digits required" validate="required" value="${e.sort}">
													<input type="hidden" name="materialList[${v.index}].id"  value="${e.id}">
												</td>
												<td>
													${e.roomVo.name}
												</td>
												<td>
													<select name="materialList[${v.index}].type" class="form-control">
														<c:forEach items="${wlList}" var="e1">
															<c:choose>
																<c:when test="${e.type==e1}">
																	<option value="${e1}" selected="selected">${e1}</option>
																</c:when>
																<c:otherwise>
																	<option value="${e1}">${e1}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</td>
												<td>
													<input type="text" name="materialList[${v.index}].name" class="form-control required" validate="required" value="${e.name}">
												</td>
												<td>
													<div class="input-group" style="width: 100%">
														<input type="text" name="materialList[${v.index}].cts" class="form-control" value="${e.cts}">
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
													 </div>
												</td>
												<td>
													<div class="input-group" style="width: 100%">
														<input type="text" name="materialList[${v.index}].xz" class="form-control" value="${e.xz}">
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
													 </div>
												</td>
												<td>
													<div class="input-group" style="width: 100%">
														<input type="text" name="materialList[${v.index}].yl" class="form-control" value="${e.yl}">
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
													 </div>
												</td>
												<td>
													<div class="input-group" style="width: 100%">
														<input type="text" name="materialList[${v.index}].useType" class="form-control" value="${e.useType}">
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
													 </div>
												</td>
												<td align="center">
													<a class="btn btn-outline btn-sm btn-danger" href="javascript:;" onclick="deleteMaterial('${e.id}',this);"><i class="fa fa-remove"></i> </font></a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4"><label>写实调查信息</label></td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 1px;">
								<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer"  style="margin-bottom: 0px;">
									<thead>
										<tr>
											<th width="50">序号</th>
											<th width="100">所在车间</th>
											<th width="100">检测点</th>
											<th width="100">姓名</th>
											<th width="50">工龄</th>
											<th width="150">工作开始时间</th>
											<th width="150">工作结束时间</th>
											<th width="15%">工作内容</th>
											<th >备注</th>
											<th width="50">操作</th>
										</tr>
									</thead>
									<tbody id="work">
										<c:forEach items="${vo.workList}" var="e" varStatus="v">
											<tr>
												<td>
													<input type="text" name="workList[${v.index}].sort" class="form-control digits required" validate="required" value="${e.sort}">
													<input type="hidden" name="workList[${v.index}].id"  value="${e.id}">
												</td>
												<td>
													${e.roomVo.name}
												</td>
												<td>
													${e.pointVo.name}
												</td>
												<td>
													<input type="text" name="workList[${v.index}].user" class="form-control" value="${e.user}">
												</td>
												<td>
													<input type="text" name="workList[${v.index}].age" class="form-control digits" value="${e.age}">
												</td>
												<td>
													<div class="input-group" style="width: 100%">
														<input type="text" name="workList[${v.index}].startTime" class="form-control timeISO" value="${e.startTime}" placeholder="开始时间">
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
													 </div>
												</td>
												<td>
													<div class="input-group" style="width: 100%">
														<input type="text" name="workList[${v.index}].endTime" class="form-control timeISO" value="${e.endTime}" placeholder="结束时间">
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
													 </div>
												</td>
												<td>
													<div class="input-group" style="width: 100%">
														<input type="text" name="workList[${v.index}].works" class="form-control" value="${e.works}">
														<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
													 </div>
												</td>
												<td>
													 <input type="text" name="workList[${v.index}].remark" class="form-control" value="${e.remark}" maxlength="128">
												</td>
												<td align="center">
													<a class="btn btn-outline btn-sm btn-danger" href="javascript:;" onclick="deleteWork('${e.id}',this);"><i class="fa fa-remove"></i> </font></a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active" style="width: 10%"><label class="pull-right">调&nbsp;查&nbsp;&nbsp;人:</label></td>
							<td class="" style="width: 23%">
								<div class="input-group">
									<input type="hidden" id="tkUserId" name="tkUserId" value="${vo.tkUserId}">
									<input type="text" id="tkUserName" name="tkUserName" value="${vo.tkUserName}" class="form-control required" validate="required" placeholder="请选择" onclick="fnSelectUser()">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
									</div>
								</div>
							</td>
							<td class="active" style="width: 10%"><label class="pull-right">陪同人:</label></td>
							<td class="" style="width: 23%">
								<input type="text" id="tkJdr" name="custVo.tkJdr" value="${vo.custVo.tkJdr}" class="form-control" > 
							</td>
							<td class="active" style="width: 10%"><label class="pull-right">调查日期:</label></td>
							<td class="" >
								<div class="input-group date">
									<input type="text" id="tkDate" name="tkDate" value="${vo.tkDate}" class="form-control dateISO required" validate="required" > 
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td class="width-35" colspan="5">
								<textarea rows="2" id="tkMsg" name="tkMsg" class="form-control" maxlength="128">${vo.tkMsg}</textarea>
							</td>
						</tr>
						<tr>
							<th class="active"><label class="pull-right">上传附件:</label></th>
							<td>
								<input type="file" name="file" multiple="multiple" class="form-control"/>
							</td>
							<td colspan="4" id="removeFile">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
									 	<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('update4Data.do?isCommit=0');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
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
		//选择人
		function fnSelectUser(){
			var userId=$('#tkUserId').val();
			layer.open({
				title:'人员选择',	
				type: 2,
				 area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: 'user_select.do?id=${vo.id}&ids='+userId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelectUser();
				  $('#tkUserId').val(data.id);
				  $('#tkUserName').val(data.name);
				}
			});
		}
		function formSubmitSave(url){
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
		function form4Save(){
			var b=true;
			$('#thisForm').attr('action','update4Data.do?isCommit=0');
			$('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status!='success'){
		        	parent.toastr.error(res.message, '');
		        	return false
		        }
			});
			return b;
		}
		function formSubmit(url){
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
		$('input[type="file"]').prettyFile();
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
</script>
<script type="text/javascript">
//添加车间
function addRoom(){
	var pjId=$('#id').val();
	if(form4Save()){
		$.ajax({
			url:'${basePath}bus/projectTk/addRoom.do?projectId='+pjId,
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
				parent.toastr.error('添加车间信息异常！', '');
		    }  
		});
	}
}
//删除车间
function deleteRoom(id,obj){
	$.ajax({
		url:'${basePath}bus/projectTk/deleteRoom.do?id='+id,
		dataType:"json",
		type:"post",
		async:false,
		success: function(data){
			if(data.status=='success'){
				parent.toastr.success(data.message, '');
				$(obj).parent().parent().remove();
			}else{
				parent.toastr.error(data.message, '');
			}
		},
		error:function(ajaxobj){
			parent.toastr.error('删除车间信息异常！', '');
	    }  
	});
}
//添加物料
function addMaterial(roomId){
	if(form4Save()){
		$.ajax({
			url:'${basePath}bus/projectTk/addMaterial.do?roomVo.id='+roomId,
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
				parent.toastr.error('添加物料信息异常！', '');
		    }  
		});
	}
}
//删除物料
function deleteMaterial(id,obj){
	$.ajax({
		url:'${basePath}bus/projectTk/deleteMaterial.do?id='+id,
		dataType:"json",
		type:"post",
		async:false,
		success: function(data){
			if(data.status=='success'){
				parent.toastr.success(data.message, '');
				$(obj).parent().parent().remove();
			}else{
				parent.toastr.error(data.message, '');
			}
		},
		error:function(ajaxobj){
			parent.toastr.error('删除物料信息异常！', '');
	    }  
	});
}
//添加检测点
function addPoint(roomId){
	if(form4Save()){
		$.ajax({
			url:'${basePath}bus/projectTk/addPoint.do?roomVo.id='+roomId,
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
				parent.toastr.error('添加检测点信息异常！', '');
		    }  
		});
	}
}
//删除检测点
function deletePoint(id,obj){
	$.ajax({
		url:'${basePath}bus/projectTk/deletePoint.do?id='+id,
		dataType:"json",
		type:"post",
		async:false,
		success: function(data){
			if(data.status=='success'){
				parent.toastr.success(data.message, '');
				$(obj).parent().parent().remove();
			}else{
				parent.toastr.error(data.message, '');
			}
		},
		error:function(ajaxobj){
			parent.toastr.error('删除检测点信息异常！', '');
	    }  
	});
}
//增加写实调查
function addWork(pointId){
	if(form4Save()){
		$.ajax({
			url:'${basePath}bus/projectTk/addWork.do?pointVo.id='+pointId,
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
				parent.toastr.error('添加写实调查信息异常！', '');
		    }  
		});
	}
}
//删除写实调查
function deleteWork(id,obj){
	$.ajax({
		url:'${basePath}bus/projectTk/deleteWork.do?id='+id,
		dataType:"json",
		type:"post",
		async:false,
		success: function(data){
			if(data.status=='success'){
				parent.toastr.success(data.message, '');
				$(obj).parent().parent().remove();
			}else{
				parent.toastr.error(data.message, '');
			}
		},
		error:function(ajaxobj){
			parent.toastr.error('删除写实调查信息异常！', '');
	    }  
	});
}
function copyVals(obj,index){
	obj=$(obj);
	var value=obj.closest('td').find('input').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	obj.closest('tbody').find('tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(value);
	});
}
function chooseItem(n){
	var sampType='${vo.sampType}';
	var imId=$('#imId_'+n).val();
	var	url='${basePath}init/item/im_selects.do?&ids='+imId+'&sampType='+encodeURI(sampType);
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
			$('#itemId_'+n).val(data.id);
			$('#itemName_'+n).val(data.name);
			$('#imId_'+n).val(data.im);
			parent.layer.close(index);
		}
	});
}
function copyItem(obj,index){
	obj=$(obj);
	var idValue=obj.closest('td').find('input').eq(0).val();
	var nameValue=obj.closest('td').find('input').eq(1).val();
	var imValue=obj.closest('td').find('input').eq(2).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#point tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
		$(this).find('td').eq(indexTd).find('input').eq(1).val(nameValue);
		$(this).find('td').eq(indexTd).find('input').eq(2).val(imValue);
	});
}
</script>
</body>
</html>
