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
						<li><a>采样准备</a></li>
						<li><strong>编辑</strong></li>
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
								${vo.cyDate}
							</td>
							<td class="width-15 active"><label class="pull-right">采样结束日期:</label></td>
							<td class="width-35">
								${vo.cyEndDate}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right"> 采样小组人员:</label></td>
							<td class="width-35">
								${vo.cyName}
							</td>
							<td class="active"><label class="pull-right">负&nbsp;&nbsp;责&nbsp;人:</label></td>
							<td>
								${vo.fzName}
							</td>
						</tr>
						<tr>
							<td class="active">
								<label class="pull-right ">采样设备:</label>
							</td>
							<td>
							   ${vo.cyAppNames}
								<%-- <div class="input-group" style="width: 100%">
									<div class="chosen-container chosen-container-multi chosen-with-drop" style="width: 85%">
										<ul class="chosen-choices" id="appUL">
											<c:forEach items="${vo.appList}" var="e" varStatus="v">
												<li class="search-choice" index="${v.index}">
													<span>${e.appName}</span>
			          								<input name="appList[${v.index}].id" value="${e.id}" type="hidden" />
			          								<input name="appList[${v.index}].appId" value="${e.appId}" type="hidden" />
			          								<input name="appList[${v.index}].appName" value="${e.appName}" type="hidden" />
		          								</li>
											</c:forEach>
										</ul>
									</div>
								</div> --%>
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
		          								</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>样品信息</b>
								<div style="float: right;">【<a class="closeTd" onclick="closeTd(this)">闭合</a>】</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 0px;">
								<table class="table table-bordered" style="margin-bottom: 0px;border: 0px;">
									<thead>
										<tr>
											<th width="50px">序号</th>
											<th width="15%">检测点位</th>
											<th width="10%">样品名称</th>
											<th width="100">采样日期</th>
											<th width="50">批次</th>
											<th width="15%">样品编号</th>
											<th>检测项目</th>
											<th width="20%">使用容器及仪器</th>
										</tr>
									</thead>
									<tbody id="sampTb">
										<c:set var="num" value="0"/>
										<c:forEach items="${vo.tpList}" var="m">
											<c:forEach items="${m.sampList}" var="e" varStatus="v">
												<tr>
													<td align="center">
														${e.sort}
														<input name="sampList[${num}].id" value="${e.id}" type="hidden" />
													</td>
													<td>
														${e.pointName}
													</td>
													<td>
														${e.sampName}
													</td>
													<td>
														${e.cyDate}
													</td>
													<td>
														${e.p}
													</td>
													<td>
														<input type="text" id="sampTb_sampCode_${num}" class="form-control"  name="sampList[${num}].sampCode" value="${e.sampCode}" >
													</td>
													<td>
														${e.itemNames}
													</td>
													<td>
														<table class="table" style="margin-bottom: 0px;">
								                            <tbody class="ct_td" id="sampTb_ct_${num}" index="${num}">
								                            	<c:forEach items="${e.containerList}" var="e1" varStatus="v1">
									                                <tr>
									                                    <td>
									                                    	${e1.container}
									                                    	<input name="sampList[${num}].containerList[${v1.index}].containerId"  value="${e1.containerId}" type="hidden" />
																			<input name="sampList[${num}].containerList[${v1.index}].container"  value="${e1.container}" type="hidden" />
									                                    </td>
									                                    <td width="40">
									                                    	${e1.num}
									                                    </td>
									                                </tr>
								                                </c:forEach>
								                            </tbody>
								                        </table>
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
								<b>采样规范</b>
								<div style="float: right;">【<a class="closeTd" onclick="closeTd(this)">闭合</a>】</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 0px;">
								<table class="table table-bordered" style="margin-bottom: 0px;border: 0px;">
									 <thead>
									 	<tr>
									 		<th style="width: 50px;">序号</th>
										 	<th style="min-width: 100px;">项目名称</th>
									 		<th style="min-width: 100px;">采样标准</th>
									 		<th style="min-width: 100px;">采样仪器</th>
									 		<th style="min-width: 100px;">采样容器</th>
									 		<th style="min-width: 70px;">采样流量</th>
									 		<th style="min-width: 70px;">采样时长</th>
									 		<th style="min-width: 70px;">采样体积</th>
									 		<th style="min-width: 70px;">存储时间</th>
									 		<th>运输、存储条件</th>
									 		<th style="min-width: 100px;">备注</th>
									 	</tr>
									 </thead>
									 <tbody>
									 	<c:forEach items="${sopList}" var="e" varStatus="v">
									 		<tr>
									 			<td>${v.index+1}</td>
									 			<td>${e.itemVo.name}</td>
									 			<td>${e.methodVo.code}</td>
									 			<td>${e.cyAppName}</td>
									 			<td>${e.ctName}</td>
									 			<td align="center">${e.cyll}</td>
									 			<td align="center">${e.cysc}</td>
									 			<td align="center">${e.cytj}</td>
									 			<td align="center">${e.bcsj}</td>
									 			<td>${e.cctj}</td>
									 			<td>${e.remark}</td>
									 		</tr>
									 	</c:forEach>
									 </tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>准备人信息</b>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">准&nbsp;备&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								<input type="hidden" id="zbId" name="zbId" value="${vo.zbId}">
								<input type="text" id="zbName" name="zbName" value="${vo.zbName}" class="form-control" readonly="readonly">
							</td>
							<td class="width-15 active"><label class="pull-right"> 准备日期:</label></td>
							<td class="width-35">
								<div class="input-group date">
									<input type="text" id="zbDate" name="zbDate" class="form-control datetimeISO" value="${vo.zbDate}">
									 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="zbMsg" name="zbMsg" maxLength="128">${vo.zbMsg}</textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12">
							<a class="btn btn-w-m btn-info" type="button" onclick="fnPrint4Sop()"><i class="fa fa-print" aria-hidden="true"></i> 打印采样规范</a>
							<a class="btn btn-w-m btn-info" type="button" onclick="fnPrint4Ct()"><i class="fa fa-print" aria-hidden="true"></i> 打印耗材清单</a>
							<a class="btn btn-w-m btn-info" type="button" onclick="fnPrint4Tm()"><i class="fa fa-print" aria-hidden="true"></i> 打印编号条码</a>
							
							<a class="btn btn-w-m btn-success" type="button" onclick="formSubmit4Save('updateData.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('updateData.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
							<a class="btn btn-w-m btn-danger" type="button" onclick="fnSubmitBack('updateBack.do?isCommit=-1')"><i class="fa fa-check" aria-hidden="true"></i> 退回</a>
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
	function fnSubmitBack(url){
		var remark=$('#zbMsg').val();
		if(remark==''){
			parent.toastr.error('请在备注栏输入退回原因！', '');
			return false;
		}
		swal({
	        title: "您确定要退回该任务吗",
	        text: "提交后将无法修改，请谨慎操作！",
	        type: "success",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定",
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
	//打印
	function fnPrint4Tm(){
		parent.layer.open({
			title:'条码打印',	
			type: 2,
			 area: ['900px', '500px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}bus/taskJj/print.do?id=${vo.id}'
		});
	}
	 
	function fnPrint4Sop(){
		parent.layer.open({
			title:'采样规范打印',	
			type: 2,
			 area: ['80%', '90%'],
			fix: false, //不固定
			maxmin: true,
			content:'${basePath}bus/taskZb/printSop.do?id=${vo.id}'
		});
	}
	function fnPrint4Ct(){
		var	url='${basePath}bus/taskZb/printCt.do?id=${vo.id}';
		parent.layer.open({
			title:'耗材清单',	
			type: 2,
			 area: ['800px', '90%'],
			fix: false, //不固定
			maxmin: true,
			content: url
		});
	}
</script>
</body>
</html>
