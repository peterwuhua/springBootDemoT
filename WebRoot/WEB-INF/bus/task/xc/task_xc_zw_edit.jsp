<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend {
	border-bottom: 0px;
	width: 80px;
	margin-bottom: 0px;
	font-size: 14px !important;
}

.form-control {
	padding: 2px;
}

.table>tbody>tr>td, .table>thead>tr>th {
	padding: 2px;
}

.table>thead>tr>th {
	text-align: center;
}

#cyd_tb .btn {
	padding: 6px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><strong>现场采样</strong></li>
						<li><strong>${vo.sampType}</strong></li>
						<li><strong>编辑</strong></li>
					</ol>
				</h5>
			</div>
			<div class="ibox-content">
				<form method="post" action="update4Data.do?isCommit=0" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 2px;">
						<tr>
							<td class="active" style="width: 150px;"><label class="pull-right">任务编号:</label></td>
							<td>${vo.no}</td>
							 <td class="active"  style="width: 150px;"><label class="pull-right">受检单位:</label></td>
							<td>
								${vo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
							<td>
								${vo.custVo.custUser}
							</td>
							<td class="active"><label class="pull-right">联系电话:</label></td>
							<td>
								${vo.custVo.custTel}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">单位地址:</label></td>
							<td colspan="3">
								${vo.custVo.custAddress}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>${vo.taskType}</td>
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>${vo.sampName}</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">开始采样时间:</label></td>
							<td class="width-35">${vo.cyDate}</td>
							<td class="width-15 active"><label class="pull-right">采样结束时间:</label></td>
							<td class="width-35">${vo.cyEndDate}</td>
						</tr>
					</table>
					<div style="overflow-x: auto;">
						<table class="table table-bordered table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px; min-width: 1700px;">
							<thead>
								<tr>
									<td colspan="16">现场情况
										<a class="btn btn-primary btn-xs btn-outline" onclick="addPoint()"><i class="fa fa-plus" aria-hidden="true"></i>&nbsp;添加点位</a> 
										<a class="btn btn-danger btn-xs btn-outline" onclick="delPoint()"><i class="fa fa-close" aria-hidden="true"></i>&nbsp;删除点位</a> 
									</td>
								</tr>
								<tr>
									<th style="width: 50px;"></th>
									<th style="width: 50px;">序号</th>
									<th style="width: 100px;">样品操作</th>
									<th style="width: 10%;">车间/岗位</th>
									<th style="width: 10%;">点位名称</th>
									<th style="width: 60px;">采样<br>数量</th>
									<th style="width: 100px;">采样方式</th>
									<th style="width: 65px;">采样时长（min）</th>
									<th style="width: 60px;">采样<br>频次
									</th>
									<th>接触职业病<br>危害因素
									</th>
									<th style="width: 60px;">作业<br>人数
									</th>
									<th style="width: 100px;">接触方式</th>
									<th style="width: 80px;">接触<br>频次
									</th>
									<th style="width: 65px;">接触时间（h/d）</th>
									<th style="width: 10%;">职业病防护设<br>施及运行情况
									</th>
									<th style="width: 10%;">个人防护用品<br>及佩戴情况
									</th>
								</tr>
							</thead>
							<tbody id="point_tb">
								<c:forEach items="${vo.tpList}" var="e" varStatus="v">
									<tr>
										<td align="center">
											<div class="checkbox i-checks">
												<div class="icheckbox_square-green">
													<input type="checkbox" class="form-control" value="${e.id}">
												</div>
											</div>
										</td>
										<td align="center">
											<input id="tpId_${v.index}" name="tpList[${v.index}].id" value="${e.id}" type="hidden" />
											<input id="sort_${v.index}" style="text-align: center;" type="text" name="tpList[${v.index}].sort" class="form-control digits required" validate="required" value="${e.sort}">
										</td>
										<td align="center">
											<a class="btn btn-primary btn-xs btn-outline" onclick="addSamp('${e.id}','')">&nbsp;&nbsp;<i class="fa fa-plus" aria-hidden="true"></i>&nbsp;&nbsp;</a> 
											<a class="btn btn-danger btn-xs btn-outline" onclick="delSamp('${e.id}','','${e.pc}')">&nbsp;&nbsp;<i class="fa fa-close" aria-hidden="true"></i>&nbsp;&nbsp;</a> 
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												 <input id="room_${v.index}" name="tpList[${v.index}].room" value="${e.room}" type="text"  class="form-control required" validate="required" />
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
										</td>
										<td>
											<input id="pointName_${v.index}" name="tpList[${v.index}].pointName" value="${e.pointName}" type="text"  class="form-control required" validate="required" onchange="checkPoint(this)"/>
										</td>
										<td align="center">${e.sampNum}</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input id="cyType_${v.index}" type="text" name="tpList[${v.index}].cyType" class="form-control" value="${e.cyType}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input id="cyHours_${v.index}" type="text" name="tpList[${v.index}].cyHours" class="form-control digits" value="${e.cyHours}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
										</td>
										<td align="center">${e.pc}${e.pcUnit}</td>
										<td>
											<div class="input-group" style="width: 100%">
												<textarea id="itemNames_${v.index}" name="tpList[${v.index}].itemNames" class="form-control required" validate="required" onclick="chooseItem('${v.index}')">${e.itemNames}</textarea>
												<input id="itemIds_${v.index}" name="tpList[${v.index}].itemIds" value="${e.itemIds}" type="hidden" />
												<input id="imId_${v.index}" name="tpList[${v.index}].imId" value="${e.imId}" type="hidden" />
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span>
											</div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="tpList[${v.index}].workNum" class="form-control" value="${e.workNum}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="tpList[${v.index}].workType" class="form-control" value="${e.workType}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="tpList[${v.index}].workPc" class="form-control" value="${e.workPc}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<input type="text" name="tpList[${v.index}].workHours" class="form-control" value="${e.workHours}">
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 			</div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<textarea name="tpList[${v.index}].fh" class="form-control">${e.fh}</textarea>
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copytextArea(this,${v.index});return false;"></span>
								 			</div>
										</td>
										<td>
											<div class="input-group" style="width: 100%">
												<textarea name="tpList[${v.index}].others" class="form-control">${e.others}</textarea>
												<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copytextArea(this,${v.index});return false;"></span>
								 			</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<table class="table table-bordered table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
						<thead>
							<tr>
								<td colspan="10">采样单列表 &nbsp;&nbsp;&nbsp;<a class="btn btn-primary btn-xs" onclick="fnHb();return false;"> 合并采样单</a> &nbsp;&nbsp;
									<a class="btn btn-info btn-xs" onclick="fnCf();return false;"> 拆分采样单</a> &nbsp;&nbsp;
									<a class="btn btn-danger btn-xs" onclick="fnInitAllCyd()">重置采样单</a>
								</td>
							</tr>
							<tr>
								<th style="width: 50px;"></th>
								<th style="width: 50px;">序号</th>
								<th style="width: 10%;">车间/岗位</th>
								<th style="width: 15%;">点位名称</th>
								<th>检测项目</th>
								<th style="width: 90px;">采样日期</th>
								<th style="width: 80px;">采样数量</th>
								<th style="width: 250px;">采样单模板</th>
								<th style="width:150px;">采样单</th>
							</tr>
						</thead>
						<tbody id="cyd_tb">
							<c:forEach items="${vo.cydList}" var="e" varStatus="v">
								<tr>
									<td align="center" onclick="checkThisTr(this)">
										<input type="hidden" id="cyDate_${v.index}" name="cydList[${v.index}].cyDate" value="${e.cyDate}"> 
										<label class="i-checks ">
											<div class="icheckbox_square-green">
												<input type="checkbox" id="id_${v.index}" key="${e.itemNames}" value="${e.id}">
											</div>
										</label>
									</td>
									<td align="center">
										<input style="text-align: center;" type="text" class="form-control required digits" validate="required" name="cydList[${v.index}].sort" value="${e.sort}"> 
										<input type="hidden" name="cydList[${v.index}].id" value="${e.id}">
									</td>
									<td>${e.room}</td>
									<td>${e.pointName}</td>
									<td>${e.itemNames}</td>
									<td align="center">${e.cyDate}</td>
									<td align="center">${e.sampNum}</td>
									<td><select class="form-control" id="type_${v.index}" name="cydList[${v.index}].type">
											<c:forEach items="${cydEnum}" var="e1">
												<c:choose>
													<c:when test="${e1.code==e.type}">
														<option value="${e1.code}" selected="selected">${e1.name}</option>
													</c:when>
													<c:otherwise>
														<option value="${e1.code}">${e1.name}</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
									</select></td>
									<td style="text-align: center;">
										<c:choose>
											<c:when test="${e.cySt=='2'}">
												<a class="btn btn-outline btn-success" type="button" onclick="fnPoint('${e.id}','${e.itemNames}-${e.cyDate}')">已填报</a>
											</c:when>
											<c:when test="${e.cySt=='1'}">
												<a class="btn btn-outline btn-warning" type="button" onclick="fnPoint('${e.id}','${e.itemNames}-${e.cyDate}')">填报中</a>
												<input type="hidden" name="unComp">
											</c:when>
											<c:otherwise>
												<a class="btn btn-outline btn-danger" type="button" onclick="fnPoint('${e.id}','${e.itemNames}-${e.cyDate}')">未填报</a>
												<input type="hidden" name="unComp">
											</c:otherwise>
										</c:choose>
										<a class="btn btn-outline btn-info" type="button" onclick="fnShowFile('${e.id}')">&nbsp;下&nbsp;&nbsp;载 &nbsp;</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
						<tr>
							<td class="width-15 active"><label class="pull-right">现场数据上报:</label></td>
							<td class="width-35"><c:choose>
									<c:when test="${vo.xcSt=='-1'}">
										无现场数据
									</c:when>
									<c:when test="${vo.xcSt=='2'}">
										<a class="btn btn-outline btn-success" type="button" onclick="fnItem()">已填报</a>
										（<span style="color: red">注：先填写采样单，后上报数据</span>）
									</c:when>
									<c:when test="${vo.xcSt=='1'}">
										<a class="btn btn-outline btn-warning" type="button" onclick="fnItem()">填报中</a>
										（<span style="color: red">注：先填写采样单，后上报数据</span>）
										<input type="hidden" name="unComp">
									</c:when>
									<c:otherwise>
										<a class="btn btn-outline btn-danger" type="button" onclick="fnItem()">未填报</a>
										（<span style="color: red">注：先填写采样单，后上报数据</span>）
										<input type="hidden" name="unComp">
									</c:otherwise>
								</c:choose></td>
							<td class="width-15 active"></td>
							<td class="width-35"></td>
						</tr>
						<tr>
							<th class="active"><label class="pull-right">上传附件:</label></th>
							<td><input type="file" name="files" multiple="multiple" class="form-control" /></td>
							<td colspan="2" id="removeFile"><c:forEach items="${vo.fileList}" var="e" varStatus="v">
									<div style="float: left; margin-right: 10px;">
										<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-info">${e.fileName}</a> <a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
									</div>
								</c:forEach></td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">填报人员:</label></td>
							<td class="width-35"><input type="hidden" id="cyUserId" name="cyUserId" value="${vo.cyUserId}"> <input type="text" id="cyUserName" name="cyUserName" value="${vo.cyUserName}" class="form-control" readonly="readonly"></td>
							<td class="width-15 active"><label class="pull-right">填报日期:</label></td>
							<td class="width-35">
								<div class="input-group date">
									<input type="text" id="cyTime" name="cyTime" class="form-control datetimeISO" value="${vo.cyTime}"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3"><textarea rows="2" class="form-control" id="cyMsg" name="cyMsg" maxlength="128">${vo.cyMsg}</textarea></td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" type="button" onclick="fnSubmit4Save('update4Data.do?isCommit=0')"><i class="fa fa-check" aria-hidden="true"></i> 保存</a> 
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('update4Data.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a> 
							<a class="btn btn-w-m btn-danger" type="button" onclick="fnSubmitBack('updateBack.do?isCommit=-1')"><i class="fa fa-check" aria-hidden="true"></i> 退回</a> 
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<!--PageOffice.js -->
	<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
	<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".i-checks").iCheck({
				checkboxClass : "icheckbox_square-green",
				radioClass : "iradio_square-green",
			});
		});
		function checkThisTr(obj) {
			var checked = $(obj).find('input[type="checkbox"]').prop('checked');
			if (checked) {
				$(obj).find('input[type="checkbox"]').iCheck('uncheck');
			} else {
				$(obj).find('input[type="checkbox"]').iCheck('check');
			}
		}
		//合并采样单
		function fnHb() {
			var l = $('#cyd_tb').find('input[id^="id_"]:checked').length;
			if (l <= 1) {
				parent.toastr.error('请选择2行以上的数据进行合并!', '');
				return false;
			}

			var item = '';
			var f = true;
			var ids = [];
			var date = '';
			$('#cyd_tb').find('input[id^="id_"]:checked').each(
					function() {
						ids.push($(this).val());
						if (item == '') {
							item = $(this).attr('key');
						}
						if (item != $(this).attr('key')) {
							f = false;
						}
						var dt = $(this).closest('td').find(
								'input[id^="cyDate_"]').val();
						if (date == '') {
							date = dt;
						} else if (date.indexOf(dt) < 0) {
							date += ',' + dt;
						}
					});
			if (date.indexOf(',') > 0) {
				parent.toastr.error('不同日期采样单不能进行合并!', '');
				return false;
			}
			if (f) {
				ajaxHb(ids.join(','));
			} else {
				layer.confirm('合并采样单项目不同，确定要合并？', {
					icon : 3,
					title : '系统提示'
				}, function(index) {
					ajaxHb(ids.join(','));
				});
			}
		}
		function ajaxHb(ids) {
			$.ajax({
				url : '${basePath}bus/taskXc/update4Hb.do?ids=' + ids,
				dataType : "json",
				type : "post",
				async : false,
				success : function(data) {
					if (data.status == 'success') {
						parent.toastr.success(data.message, '');
						location.reload();
					}
				},
				error : function(ajaxobj) {
				}
			});
		}
		//拆分采样单
		function fnCf() {
			var l = $('#cyd_tb').find('input[id^="id_"]:checked').length;
			if (l <= 0) {
				parent.toastr.error('请选择要拆分的数据行!', '');
				return false;
			} else if (l > 1) {
				parent.toastr.error('一次只能拆分一行数据！', '');
				return false;
			}
			var id = $('#cyd_tb').find('input[id^="id_"]:checked').val();
			$.ajax({
				url : '${basePath}bus/taskXc/update4Cf.do?id=' + id,
				dataType : "json",
				type : "post",
				async : false,
				success : function(data) {
					if (data.status == 'success') {
						parent.toastr.success(data.message, '');
						location.reload();
					}
				},
				error : function(ajaxobj) {
				}
			});
		}
		//采样单
		function fnPoint(pointId, sampName) {
			$('#thisForm').ajaxSubmit(
				function(res) {
					if (res.status != 'success') {
						parent.toastr.error(res.message, '');
						return false;
					} else {
						parent.layer.open({
							title : '【' + sampName + '】采样单',
							type : 2,
							area : [ '100%', '100%' ],
							fix : true,
							maxmin : false,
							content : '${basePath}bus/taskXc/editCyd4Zw.do?id='
									+ pointId,
							end : function() {
								location.reload();
							}
						});
					}
			});
		}
		function fnItem() {
			var l = $('#cyd_tb').find('input[name="unComp"]').length;
			if (l > 0) {
				layer.confirm('请确认已填写采样单！', {
					icon : 3,
					title : '系统提示'
				}, function(index) {
					fnItem4Edit();
				});
			} else {
				fnItem4Edit()
			}
		}
		function fnItem4Edit() {
			parent.layer.open({
				title : '现场分析记录单',
				type : 2,
				area : [ '100%', '100%' ],
				fix : true, //不固定
				maxmin : false,
				content : '${basePath}bus/taskXc/edit4Item.do?id=${vo.id}',
				end : function() {
					location.reload();
				}
			});
		}
		
	</script>
	<script>
		function fnTask(id, no) {
			parent.layer.open({
				title : '任务【' + no + '】',
				type : 2,
				area : [ '1000px', '85%' ],
				fix : false, //不固定
				maxmin : true,
				content : '/bus/task/show.do?id=' + id
			});
		}
		function fnSubmit4Save(url) {
			$('#thisForm').attr('action', url);
			$('#thisForm').ajaxSubmit(function(res) {
				if (res.status == 'success') {
					parent.toastr.success(res.message, '');
					location.reload();
				} else {
					parent.toastr.error(res.message, '');
				}
			});
		}
		function fnSubmit(url) {
			var num=$('#point_tb').find('tr').length;
			if (num < 1)//样品数不能小于1
			{
				parent.toastr.error('请添加检测点位！', '');
				return;
			}
			$('#thisForm').attr('action', url);
			var b = $("#thisForm").FormValidate();
			if (b) {
				var un = $('input[name="unComp"]').length;
				if (un > 0) {
					swal({
						title : "部分采样单未完成，您确定要提交该任务吗",
						text : "提交后请在已办详情内进行数据补录！",
						type : "success",
						showCancelButton : true,
						confirmButtonColor : "#1ab394",
						confirmButtonText : "确定",
						cancelButtonText : "取消"
					}, function() {
						$('#thisForm').ajaxSubmit(function(res) {
							if (res.status == 'success') {
								parent.toastr.success(res.message, '');
								backMainPage();
							} else {
								parent.toastr.error(res.message, '');
							}
						});
					});
				}else{
					swal({
						title : "您确定要提交该任务吗",
						text : "提交后将无法修改，请谨慎操作！",
						type : "success",
						showCancelButton : true,
						confirmButtonColor : "#1ab394",
						confirmButtonText : "确定",
						cancelButtonText : "取消"
					}, function() {
						$('#thisForm').ajaxSubmit(function(res) {
							if (res.status == 'success') {
								parent.toastr.success(res.message, '');
								backMainPage();
							} else {
								parent.toastr.error(res.message, '');
							}
						});

					});
				}
			} else {
				parent.toastr.error('请检查必填项！', '');
			}
		}
		function fnSubmitBack(url) {
			var remark = $('#cyMsg').val();
			if (remark == '') {
				parent.toastr.error('请在备注栏输入退回原因！', '');
				return false;
			}
			swal({
				title : "您确定要退回该任务吗",
				text : "提交后将无法修改，请谨慎操作！",
				type : "success",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			}, function() {
				$('#thisForm').attr('action', url);
				$('#thisForm').ajaxSubmit(function(res) {
					if (res.status == 'success') {
						parent.toastr.success(res.message, '');
						backMainPage();
					} else {
						parent.toastr.error(res.message, '');
					}
				});
			});
		}
		function fnOpenWord() {
			POBrowser.openWindow(
					'${basePath}bus/taskData/editWord.do?id=${vo.id}',
					'width=1200px;height=800px;');
		}
		$(function() {
			$(".i-checks").iCheck({
				checkboxClass : "icheckbox_square-green",
				radioClass : "iradio_square-green",
			});
		});

		$('input[type="file"]').prettyFile();
		function removeFiles(id, obj) {
			layer.confirm('确认要删除?', {
				icon : 3,
				title : '系统提示'
			}, function(index) {
				$.ajax({
					url : '${basePath}sys/files/deleteOne.do?id=' + id,
					dataType : "json",
					type : "post",
					async : false,
					success : function(data) {
						if (data.status == 'success') {
							layer.msg(data.message, {
								icon : 0,
								time : 1000
							});
							$(obj).parent().remove();
						}
					},
					error : function(ajaxobj) {
					}
				});
			});
		}
	</script>
	<script type="text/javascript">
		//添加样品
		function addSamp(pointId, type) {
			$.ajax({
				url : "${basePath}bus/sampling/addSamp.do",
				data : {
					'pointId' : pointId,
					'type' : type
				},
				async : false,
				success : function(data) {
					if (data.status = 'success') {
						parent.toastr.success(data.message, '');
						window.location.reload(true);
					} else {
						parent.toastr.error(data.message, '');
					}
				},
				error : function(ajaxobj) {
					parent.toastr.error(ajaxobj, '');
				}
			});
		}
		//删除样品样品
		function delSamp(pointId, type,num) {
			if (num <= 1)//样品数不能小于1
			{
				parent.toastr.error('频次不能小于1', '');
				return;
			}
			$.ajax({
				url : "${basePath}bus/sampling/delSamp.do",
				data : {
					'pointId' : pointId,
					'type' : type
				},
				async : false,
				success : function(data) {
					if (data.status = 'success') {
						parent.toastr.success(data.message, '');
						window.location.reload(true);
					} else {
						parent.toastr.error(data.message, '');
					}
				},
				error : function(ajaxobj) {
					parent.toastr.error(ajaxobj, '');
				}
			});
		}
		function fnInitAllCyd() {
			swal({
				title : "您确定要重置采样单信息吗",
				text : "该操作无法回退，请谨慎操作！",
				type : "success",
				showCancelButton : true,
				confirmButtonColor : "#1ab394",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			}, function() {
				$('#thisForm').attr('action', 'initAllCyd.do');
				$('#thisForm').ajaxSubmit(function(res) {
					if (res.status == 'success') {
						parent.toastr.success(res.message, '');
						window.location.reload(true);
					} else {
						parent.toastr.error(res.message, '');
					}
				});
			});
		}
		function delPoint(){
			var num=$('#point_tb').find('input[type="checkbox"]:checked').length;
			if (num <1)//样品数不能小于1
			{
				parent.toastr.error('请选择要删除的检测点位', '');
				return;
			}
			var pointId=[];
			$('#point_tb').find('input[type="checkbox"]:checked').each(function(){
				pointId.push($(this).val());
			});
			swal({
		        title: "您确定要删除点位吗",
		        text: "提删除后无法恢复，请谨慎操作！",
		        type: "success",
		        showCancelButton: true,
		        confirmButtonColor: "#DD6B55",
		        confirmButtonText: "确定",
		        cancelButtonText: "取消"
		    }, function () {
				$.ajax({
					url : "${basePath}bus/taskXc/delPoint.do",
					data : {'ids' :  pointId.join(',')},
					async : false,
					success : function(data) {
						if (data.status = 'success') {
							parent.toastr.success(data.message, '');
							window.location.reload(true);
						} else {
							parent.toastr.error(data.message, '');
						}
					},
					error : function(ajaxobj) {
						parent.toastr.error(ajaxobj, '');
					}
				});
		    });
		}
		function addPoint(){
			var taskId='${vo.id}';
			$.ajax({
				url : "${basePath}bus/taskXc/addPoint.do",
				data : {'id' :taskId},
				async : false,
				success : function(data) {
					if (data.status = 'success') {
						parent.toastr.success(data.message, '');
						window.location.reload(true);
					} else {
						parent.toastr.error(data.message, '');
					}
				},
				error : function(ajaxobj) {
					parent.toastr.error(ajaxobj, '');
				}
			});
		}
		function chooseItem(n){
			var itemIds=$('#itemIds_'+n).val();
			var imIds=$('#imId_'+n).val();
			var sampType='${vo.sampType}';
			var	url='${basePath}init/item/im_selects.do?ids='+imIds+'&sampType='+encodeURI(sampType);
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
					if(itemIds!=data.id){
						$('#thisForm').attr('action', 'update4Data.do?isCommit=0');
						$('#thisForm').ajaxSubmit(function(res) {
							if (res.status == 'success') {
								uptPoint(n);
							}
							window.location.reload();
						});
					}
					parent.layer.close(index);
				}
			});
		}
		function copyItem(obj){
			obj=$(obj);
			var textValue=obj.closest('td').find('textarea').val();
			var idValue=obj.closest('td').find('input').eq(0).val();
			var imValue=obj.closest('td').find('input').eq(1).val();
			var indexTr=obj.closest('td').closest('tr').index();
			var indexTd=obj.closest('td').index();
			$('#thisForm').attr('action', 'update4Data.do?isCommit=0');
			$('#thisForm').ajaxSubmit(function(res) {
				if (res.status == 'success') {
					obj.closest('tbody').find('tr:gt('+indexTr+')').each(function(){
						var oldItemId=$(this).find('td').eq(indexTd).find('input').eq(0).val();
						$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
						$(this).find('td').eq(indexTd).find('input').eq(1).val(imValue);
						$(this).find('td').eq(indexTd).find('textarea').eq(0).val(textValue);
						if(idValue!=oldItemId){
							uptPoint($(this).index());
						}
					});
					window.location.reload();
				}
			});
		}
		function uptPoint(n){
			var id=$('#tpId_'+n).val();
			var itemIds=$('#itemIds_'+n).val();
			var itemNames=$('#itemNames_'+n).val();
			var imId=$('#imId_'+n).val();
			$.ajax({
				url : "${basePath}bus/taskXc/uptPoint.do",
				data : {'id' :id,'itemIds':itemIds,'itemNames':itemNames,'imId':imId},
				async : false,
				success : function(data) {
					if (data.status = 'success') {
						parent.toastr.success(data.message, '');
					} else {
						parent.toastr.error(data.message, '');
					}
				},
				error : function(ajaxobj) {
					parent.toastr.error(ajaxobj, '');
				}
			});
		}
	</script>
<script type="text/javascript">
function fnShowFile(cydId){
	parent.layer.open({
		title:'选择模板/打开已有文件',	
		type: 2,
		area: ['400px', '300px'],
		fix: true, //不固定
		maxmin: true,
		content: '${basePath}bus/sampCyd/selectZwCyd.do?id='+cydId
	});
}
function copyVals(obj){
	obj=$(obj);
	var value0=obj.closest('td').find('input').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#point_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(value0);
	});
}
function copytextArea(obj){
	obj=$(obj);
	var value0=obj.closest('td').find('textarea').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#point_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('textarea').eq(0).val(value0);
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
