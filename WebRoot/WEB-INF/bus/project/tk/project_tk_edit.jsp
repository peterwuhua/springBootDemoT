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
					<li><strong>现场踏勘</strong></li>
					<li><strong>${vo.projectVo.sampType}</strong></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<input name="projectVo.id" value="${vo.projectVo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
							<td class="width-35">
								<a href="javascript:void();" onclick="fnShow('${vo.projectVo.id}','${vo.projectVo.no}');">${vo.projectVo.no}</a>
							</td>
							<td class="width-15 active"><label class="pull-right">受检单位:</label></td>
							<td class="width-35">
								${vo.projectVo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.projectVo.taskType}
							</td>
							<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
							<td class="width-35">
								${vo.projectVo.sampName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">立项日期:</label></td>
							<td class="width-35">${vo.projectVo.rdate}</td>
							<td class="width-15 active"><label class="pull-right">拟完成日期:</label></td>
							<td class="width-35">${vo.projectVo.finishDate}</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.projectVo.remark}
							</td>
						</tr>
					</table>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
					  <legend>踏勘信息</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">建设单位名称:</label></td>
							<td class="width-35">
								<input type="text" id="buildUnit" name="buildUnit" value="${vo.buildUnit}" class="form-control" >
							</td>
							<td class="width-15 active"><label class="pull-right">项目名称:</label></td>
							<td class="width-35">
								<input type="text" id="name" name="name" value="${vo.name}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">项目环评时间:</label></td>
							<td class="width-35">
								<div class="input-group date">
									<input type="text" id="itemHpTime" name="itemHpTime" value="${vo.itemHpTime}" class="form-control dateISO" >
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
							<td class="width-15 active"><label class="pull-right">项目环评单位:</label></td>
							<td class="width-35">
								<input type="text" id="itemHpUnit" name="itemHpUnit" value="${vo.itemHpUnit}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">项目开工建设<br>及投产时间:</label></td>
							<td class="width-35">
								<div class="input-group date">
									<input type="text" id="productionDate" name="productionDate" value="${vo.productionDate}" class="form-control dateISO" >
									 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
							<td class="width-15 active"><label class="pull-right">环保设施设计<br>及施工单位:</label></td>
							<td class="width-35">
								<input type="text" id="constructUnit" name="constructUnit" value="${vo.constructUnit}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">项目设计生产能力:</label></td>
							<td class="width-35">
								<input type="text" id="designCapacity" name="designCapacity" value="${vo.designCapacity}" class="form-control" >
							</td>
							<td class="width-15 active"><label class="pull-right">项目目前生产能力:</label></td>
							<td class="width-35">
								<input type="text" id="nowCapacity" name="nowCapacity" value="${vo.nowCapacity}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">主管部门:</label></td>
							<td class="width-35">
								<input type="text" id="mainName" name="mainName" value="${vo.mainName}" class="form-control" >
							</td>
							<td class="width-15 active"><label class="pull-right">整体或分期验监测:</label></td>
							<td class="width-35">
								<input type="text" id="staging" name="staging" value="${vo.staging}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">废水排口共有:</label></td>
							<td class="width-35">
								<input type="text" id="wasteWater" name="wasteWater" value="${vo.wasteWater}" class="form-control" >
							</td>
							<td class="width-15 active"><label class="pull-right">废水治理措施:</label></td>
							<td class="width-35">
								<input type="text" id="wasteMeasures" name="wasteMeasures" value="${vo.wasteMeasures}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">雨水排口共有:</label></td>
							<td class="width-35">
								<input type="text" id="rain" name="rain" value="${vo.rain}" class="form-control" >
							</td>
							<td class="width-15 active"><label class="pull-right">雨水治理措施:</label></td>
							<td class="width-35">
								<input type="text" id="rainMeasures" name="rainMeasures" value="${vo.rainMeasures}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">废气排口共有:</label></td>
							<td class="width-35">
								<input type="text" id="exhaustGas" name="exhaustGas" value="${vo.exhaustGas}" class="form-control" >
							</td>
							<td class="width-15 active"><label class="pull-right">废气治理措施:</label></td>
							<td class="width-35">
								<input type="text" id="gasMeasures" name="gasMeasures" value="${vo.gasMeasures}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">固体废物治理措施:</label></td>
							<td class="width-35">
								<input type="text" id="solidWaste" name="solidWaste" value="${vo.solidWaste}" class="form-control" >
							</td>
							<td class="width-15 active"><label class="pull-right">噪声治理措施:</label></td>
							<td class="width-35">
								<input type="text" id="noise" name="noise" value="${vo.noise}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">废水处理能力:</label></td>
							<td class="width-35">
								<input type="text" id="wasteCapacity" name="wasteCapacity" value="${vo.wasteCapacity}" class="form-control" >
							</td>
							<td class="width-15 active"><label class="pull-right">废气处理能力:</label></td>
							<td class="width-35">
								<input type="text" id="gasCapacity" name="gasCapacity" value="${vo.gasCapacity}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">废水排口在<br>线装置情况:</label></td>
							<td class="width-35">
								<input type="text" id="wasteDevice" name="wasteDevice" value="${vo.wasteDevice}" class="form-control" >
							</td>
							<td class="width-15 active"><label class="pull-right">废气排口在<br>线装置情况:</label></td>
							<td class="width-35">
								<input type="text" id="gasDevice" name="gasDevice" value="${vo.gasDevice}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">应急预案及<br>事故应急池:</label></td>
							<td class="width-35">
								<input type="text" id="emergencyPlan" name="emergencyPlan" value="${vo.emergencyPlan}" class="form-control" >
							</td>
							<td class="width-15 active"><label class="pull-right">排污口设置<br>及规范化情况:</label></td>
							<td class="width-35">
								<input type="text" id="standardiz" name="standardiz" value="${vo.standardiz}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">固体废物处理<br>协议签订情况:</label></td>
							<td class="width-35">
								<input type="text" id="solidAgreement" name="solidAgreement" value="${vo.solidAgreement}" class="form-control" >
							</td>
							<td class="width-15 active"><label class="pull-right">废水处理协<br>议签订情况:</label></td>
							<td class="width-35">
								<input type="text" id="wasteAgreement" name="wasteAgreement" value="${vo.wasteAgreement}" class="form-control" >
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">其他相关情况:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="remark" name="remark" maxlength="128">${vo.remark}</textarea>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">踏勘人员:</label></td>
							<td class="width-35">
								<div class="input-group">
									<input type="hidden" id="userId" name="userId" value="${vo.userId}">
									<input type="text" id="userName" name="userName" value="${vo.userName}" class="form-control required" validate="required" placeholder="请选择" onclick="fnSelectUser()">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
									</div>
								</div>
							</td>
							<td class="width-15 active"><label class="pull-right">踏勘日期:</label></td>
							<td class="width-35">
								<div class="input-group date">
									<input type="text" id="tdate" name="tdate" value="${vo.tdate}" class="form-control dateISO required" validate="required" > 
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('save4Survey.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('update4Survey.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
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
				  var data=iframeWin.fnSelectUser();
				  $('#userId').val(data.id);
				  $('#userName').val(data.name);
				}
			});
		}
		function formSubmitSave(url){
			$('#thisForm').attr('action',url);
			$('#thisForm').submit()
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
