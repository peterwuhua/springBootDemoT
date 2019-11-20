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
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>车辆使用申请</a></li>
					<li><strong>编辑</strong>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					  <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<%-- <td class="width-15 active"><label class="pull-right">车辆编号:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input id="carId" name="carId"  type="hidden" value="${vo.carId}" />
										<input id="code" name="code" class="form-control required" validate="required" type="text" value="${vo.code}"  onclick="fnSelectCar()"/>
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectCar()">选择</button>
										</div>
									</div>
								</td> --%>
								<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input id="busNo" name="busNo" class="form-control" type="text" value="${vo.busNo}" />
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectProject()">选择</button>
										</div>
									</div>
								</td>
							</tr>
														<tr>
								<td class="active"><label class="pull-right">去&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向:</label></td>
								<td>
									<input id="destination" maxlength="64" name="destination" class="form-control required" validate="required" type="text" value="${vo.destination}" />
								</td>
								<td class="active"><label class="pull-right">出差人数:</label></td>
								<td>
		                           		<input id="destRynum" maxlength="10" name="destRynum" class="form-control required digits" min="1" validate="required" type="text" value="${vo.destRynum}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">申请人员:</label></td>
								<td>
									<input id="userName" name="userName" class="form-control" type="text" value="${vo.userName}" readonly="readonly"/>
									<input id="userId" name="userId" type="hidden" value="${vo.userId}" />
								</td>
								<td class="active"><label class="pull-right">申请时间:</label></td>
								<td>
									<div class="input-group date">
		                           		<input id="date" name="date" class="form-control required datetimeISO" validate="required" type="text" value="${vo.date}" />
		                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">使用时间:</label></td>
								<td>
									<div class="input-group date">
		                           		<input id="startTime" name="startTime" class="form-control required" validate="required" type="text" value="${vo.startTime}" />
		                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
								<td class="active"><label class="pull-right">预计归还时间:</label></td>
								<td>
									<div class="input-group date">
		                           		<input id="endTime" name="endTime" class="form-control required" validate="required" type="text" value="${vo.endTime}" />
		                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;途:</label></td>
								<td colspan="3">
									<textarea rows="2" maxlength="128" class="form-control required"  validate="required" id="content" name="content">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
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
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('${fn:length(vo.id)>0? 'updateData.do?isCommit=1':'addData.do?isCommit=1'}');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
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
	<script>
		laydate.render({ 
		  elem: '#startTime',
		  theme: 'molv',
		  calendar: true,
		  trigger: 'click',
		  min:0,
		  max:1
		});
		laydate.render({ 
		  elem: '#endTime',
		  theme: 'molv',
		  calendar: true,
		  trigger: 'click',
		  min:0 
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
			        title: "您确定要提交吗",
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
	<script>
	function fnSelectCar(){
		var carId=$('#carId').val();
		layer.open({
			title:'车辆选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/init/car/select.do?id='+carId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelect();
				$('#carId').val(data.id);
				$('#code').val(data.code);
			}
		});
	}
	function fnSelectProject(){
		layer.open({
			title:'选择任务',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/task/select.do',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#busNo').val(data.no);
			}
		});
	}
	 
	</script>
</body>
</html>
