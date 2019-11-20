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
					<li><a>评审记录</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm" >
					<input name="id" value="${vo.id}" type="hidden" />
					<input name="gsVo.id" value="${vo.gsVo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">计划名称:</label></td>
								<td class="width-35">
									${vo.gsVo.title}
								</td>
								<td class="width-15 active"><label class="pull-right">评审时间:</label></td>
								<td class="width-35">
									${vo.gsVo.psDate}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
								<td class="width-35">
									${vo.gsVo.deptName}
								</td>
								<td class="width-15 active"><label class="pull-right">评审主持人:</label></td>
								<td class="width-35">
									${vo.gsVo.zcName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">评审地点:</label></td>
								<td>
									${vo.gsVo.address}
								</td>
								<td class="active"><label class="pull-right">评审依据:</label></td>
								<td>
									${vo.gsVo.stand}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内审组长:</label></td>
								<td>
									${vo.gsVo.headName}
								</td>
								<td class="active"><label class="pull-right">审核人员:</label></td>
								<td>
									${vo.gsVo.psName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">计划详情:</label></td>
								<td colspan="3">
									${vo.gsVo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.gsVo.remark}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">评审内容:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="content" name="content">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">评审中发现的问题:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="problem" name="problem">${vo.problem}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">评审结论:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="result" name="result">${vo.result}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">纠正/改进的措施:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="measures" name="measures">${vo.measures}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">对纠正/改进措施的检查:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="checks" name="checks">${vo.checks}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea maxlength="128" rows="2" class="form-control" id="remark" name="remark">${vo.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">记&nbsp;&nbsp;录&nbsp;&nbsp;人:</label></td>
								<td>
									<input type="hidden" id="userId" name="userId" value="${vo.userId}">
									<input type="text" id="userName" name="userName" class="form-control" value="${vo.userName}" readonly="readonly">
								</td>
								<td class="active"><label class="pull-right ">记录日期:</label></td>
								<td>
									<div class="input-group date">
		                              	<input id="date" name="date" class="form-control required dateISO" validate="required" type="text" value="${vo.date}" />
		                              	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a id="sub_btn" class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('addData.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 提交</a>
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
