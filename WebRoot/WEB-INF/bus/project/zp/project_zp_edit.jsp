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
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><strong>任务指派</strong></li>
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
								<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
								<td class="width-35">
									<a href="javascript:void();" onclick="fnShow('${vo.id}','${vo.no}');">${vo.no}</a>
								</td>
								<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
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
						</tbody>
					</table>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
					  <legend>指派信息</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">踏勘人员:</label></td>
							<td class="width-35">
								<div class="input-group">
									<input type="hidden" id="userId" name="tkUserId" value="${vo.tkUserId}">
									<input type="text" id="userName" name="tkUserName" value="${vo.tkUserName}" class="form-control required" validate="required" placeholder="请选择" onclick="fnSelectUser()">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
									</div>
								</div>
							</td>
							<td class="width-15 active"><label class="pull-right">踏勘日期:</label></td>
							<td class="width-35">
								<div class="input-group date">
									<input type="text" id="tdate" name="tkDate" class="form-control dateISO required" validate="required" > 
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="remark" name="remark" maxlength="128"></textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmit('updateData.do');"><i class="fa fa-check" aria-hidden="true"></i> 提交</a>
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
			var index = layer.open({
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
			var userId=$('#userId').val();
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
				  var data=iframeWin.fnSelect();
				  $('#userId').val(data.id);
				  $('#userName').val(data.name);
				}
			});
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
	</script>
</body>
</html>
