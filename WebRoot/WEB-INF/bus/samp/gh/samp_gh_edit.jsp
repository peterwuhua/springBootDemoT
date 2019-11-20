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
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<%@ include file="../../../include/status.jsp"%>

<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
</style>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">样品编号:</label></td>
							<td class="width-35">
								${vo.sampCode}
								<input id="sampId" name="samplingVo.id" type="hidden" value="${vo.samplingVo.id}" />
							</td>
							<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
							<td class="width-35">
								${vo.sampName}
								<input id="sampName" name="sampName" type="hidden" value="${vo.sampName}" />
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">归&nbsp;还&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								<div class="input-group">
									<input id="useId" name="useId" type="hidden" value="${vo.useId}" />
	                              	<input id="useName" name="useName" class="form-control required" validate="required" type="text" value="${vo.useName}" onclick="fnSelectUser()"/>
	                            	<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
									</div>
	                            </div>
							</td>
							<td class="width-15 active"><label class="pull-right">归还时间:</label></td>
							<td class="width-35">
								<div class="input-group date">
	                              	<input id="useDate" name="useDate" class="form-control datetimeISO required" validate="required" placeholder="YYYY-MM-DD HH:MM:SS" type="text" value="${vo.useDate}" />
	                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                            </div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</label></td>
							<td>
								<div class="radio i-checks">
                                       <label>
                                           <div class="iradio_square-green">
                                           <input type="radio" value="SAMP_22" name="status" <c:if test="${vo.status=='SAMP_22'}">checked</c:if>>
                                           </div>待归还
                                       </label>
                                       <label>
                                           <div class="iradio_square-green">
                                           <input type="radio" value="SAMP_23" name="status" <c:if test="${vo.status=='SAMP_23'}">checked</c:if>>
                                           </div>已归还
                                       </label>
                                   </div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea class="form-control" rows="2" id="content" name="content" >${vo.content}</textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
		   <!-- Sweet alert -->
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>
		var index = parent.layer.getFrameIndex(window.name); 
		function submitSave(){
			var t = $("#thisForm").FormValidate();
			if(t){
				$.ajax({ 
					url:"updateData.do",
					dataType:"json",
					data:$('#thisForm').serialize(),
					type:"post",
					success: function(data){
						parent.layer.msg(data.message, {icon: 0,time: 3000})
						if("success"==data.status){
							parent.layer.close(index);
						}
					},
					error:function(ajaxobj){  
						layer.msg(ajaxobj, {icon: 0,time: 3000});
				    }  
				});
			}
		}
		function fnSelectUser(){
			var userId=$('#useId').val();
			parent.layer.open({
				title:'人员选择',	
				type: 2,
				 area: ['800px', '400px'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}sys/account/select.do?id='+userId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
					var iframeWin=layero.find('iframe')[0];
					var data=iframeWin.contentWindow.fnSelect();
					$('#useId').val(data.id);
					$('#useName').val(data.name);
				}
			});
		}
		$(function(){
			$(".i-checks").iCheck({
				checkboxClass : "icheckbox_square-green",
				radioClass : "iradio_square-green",
			});
		});
	</script>
</body>
</html>
