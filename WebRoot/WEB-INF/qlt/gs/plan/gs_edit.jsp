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
#ct_tb >tr >td{
	padding: 2px;
}
#ct_tb .btn{
	padding-left: 2px;
	padding-right: 2px;
}
.mtd{
	padding-left: 6px !important;
	padding-right: 2px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>评审计划</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm" >
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">计划名称:</label></td>
								<td class="width-35">
									<input type="text" id="title" name="title" class="form-control required" validate="required" value="${vo.title}"> 
								</td>
								<td class="width-15 active"><label class="pull-right">评审时间:</label></td>
								<td class="width-35">
									<div class="input-group date">
		                              	<input id="psDate" name="psDate" class="form-control required dateISO" validate="required" type="text" value="${vo.psDate}" />
		                              	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="deptName" name="deptName" class="form-control required" validate="required" placeholder="请选择" value="${vo.deptName}"  onclick="fnSelectOrg()"> 
										<input type="hidden" id="deptId" name="deptId" value="${vo.deptId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectOrg()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">评审主持人:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="zcName" name="zcName" class="form-control required" validate="required" value="${vo.zcName}"  onclick="fnSelectZc()"> 
										<input type="hidden" id="zcId" name="zcId" value="${vo.zcId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectZc()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">评审地点:</label></td>
								<td>
									<input type="text" id="address" name="address"  class="form-control" value="${vo.address}">
								</td>
								<td class="active"><label class="pull-right">评审依据:</label></td>
								<td>
									<input type="text" id="stand" name="stand"  class="form-control" value="${vo.stand}">
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内审组长:</label></td>
								<td>
									<div class="input-group">
										<input type="text" id="headName" name="headName" class="form-control required" validate="required" placeholder="请选择" value="${vo.headName}"  onclick="fnSelectHead()"> 
										<input type="hidden" id="headId" name="headId" value="${vo.headId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectHead()">选择</button>
										</div>
									</div>
								</td>
								<td class="active"><label class="pull-right">审核人员:</label></td>
								<td>
									<div class="input-group">
										<input type="text" id="psName" name="psName" class="form-control required" validate="required" placeholder="请选择" value="${vo.psName}"  onclick="fnSelectUsers()"> 
										<input type="hidden" id="psId" name="psId" value="${vo.psId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUsers()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">计划详情:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="content" name="content">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">编&nbsp;&nbsp;制&nbsp;&nbsp;人:</label></td>
								<td>
									<input type="hidden" id="userId" name="userId" value="${vo.userId}">
									<input type="text" id="userName" name="userName" class="form-control" value="${vo.userName}" readonly="readonly">
								</td>
								<td class="active"><label class="pull-right ">编制日期:</label></td>
								<td>
									<div class="input-group date">
		                              	<input id="date" name="date" class="form-control required dateISO" validate="required" type="text" value="${vo.date}" />
		                              	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea maxlength="128" rows="2" class="form-control" id="remark" name="remark">${vo.remark}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a id="sub_btn" class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('${fn:length(vo.id)>0? 'updateData.do?isCommit=1':'addData.do?isCommit=1'}');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
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
	function fnSelectOrg(){
		var deptId=$('#deptId').val();
		layer.open({
			title:'部门选择',	
			type: 2,
			area: ['300px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/org/select.do?id='+deptId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#deptId').val(data.id);
			  $('#deptName').val(data.name);
			}
		});
	}
	function fnSelectZc(){
		var zcId=$('#zcId').val();
		layer.open({
			title:'人员选择',	
			type: 2,
			area: ['800px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/select.do?id='+zcId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#zcId').val(data.id);
			  $('#zcName').val(data.name);
			}
		});
	}
	function fnSelectHead(){
		var headId=$('#headId').val();
		layer.open({
			title:'人员选择',	
			type: 2,
			area: ['800px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/select.do?id='+headId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#headId').val(data.id);
			  $('#headName').val(data.name);
			}
		});
	}
	function fnSelectUsers(){
		var psId=$('#psId').val();
		layer.open({
			title:'人员选择',	
			type: 2,
			area: ['800px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/selects.do?ids='+psId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#psId').val(data.id);
			  $('#psName').val(data.name);
			}
		});
	}
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
	});
	function formSubmitSave(url){
		$('#thisForm').attr('action',url);
		$('#thisForm').submit()
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
	    	})
		}
	}
</script>
</body>
</html>
