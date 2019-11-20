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
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">样品信息</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
							<td class="width-35">
								${vo.taskVo.no}
							</td>
							<td class="width-15 active"><label class="pull-right">样品编号:</label></td>
							<td class="width-35">
								${vo.sampCode} 
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
							<td class="width-35">
								${vo.custVo.custName} 
							</td>
							<td class="width-15 active"><label class="pull-right">采样点位:</label></td>
							<td class="width-35">
								${vo.pointName} 
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">样品类型:</label></td>
							<td class="width-35">
								${vo.sampTypeName} 
							</td>
							<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
							<td class="width-35">
								${vo.sampName} 
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">收样日期:</label></td>
							<td class="width-35">
								${vo.reciveDate}
							</td>
							 <td class="width-15 active"><label class="pull-right">保存地点:</label></td>
							<td class="width-35">
								${vo.saveAddress}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">样品处理要求:</label></td>
							<td class="width-35">
								${vo.dealRequest}
							</td>
							<td class="width-15 active"><label class="pull-right">保存条件:</label></td>
							<td class="width-35">
								${vo.saveRequest}
							</td>
						</tr>
						<c:choose>
							<c:when test="${type=='水'}">
								<tr>
									<td class="width-15 active"><label class="pull-right">样品性状:</label></td>
									<td class="width-35">
										${vo.xz}
									</td>
									<td class="width-15 active"><label class="pull-right">是否添加添加剂:</label></td>
									<td class="width-35">
										${vo.tjj}
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="width-15 active"><label class="pull-right">样品性状:</label></td>
									<td class="width-35" colspan="3">
										${vo.xz}
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
						<tr>
							<td class="width-15 active"><label class="pull-right">采/送样人:</label></td>
							<td class="width-35">
								${vo.taskVo.cyName}
							</td>
							<td class="width-15 active"><label class="pull-right">采/送样日期:</label></td>
							<td class="width-35">
								${vo.taskVo.cyDate}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">保管部门:</label></td>
							<td class="width-35">
								${vo.deptName}
							</td>
							<td class="width-15 active"><label class="pull-right">保&nbsp;管&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								${vo.reciveUser}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">是否留样:</label></td>
							<td class="width-35">
								${vo.ly}
							</td>
							<td class="width-15 active"><label class="pull-right">当前状态:</label></td>
							<td class="width-35">
								<c:choose>
									<c:when test="${vo.status=='SAMP_00'}">
										库存中
									</c:when>
									<c:when test="${vo.status=='SAMP_10'}">
										已留样
									</c:when>
									<c:otherwise>
										已处理
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<c:if test="${vo.ly=='是'}">
							<tr>
								<td class="width-15 active"><label class="pull-right">留样日期:</label></td>
								<td class="width-35">
									<div class="input-group date">
		                              	<input id="lyDate" name="lyDate" class="form-control dateISO required" validate="required" placeholder="请选择" type="text" value="${vo.lyDate}" />
		                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
								<td class="width-15 active"><label class="pull-right">截止日期:</label></td>
								<td class="width-35">
									<div class="input-group date">
		                              	<input id="bcqx" name="bcqx" class="form-control dateISO required" validate="required" placeholder="请选择" type="text" value="${vo.bcqx}" />
		                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="width-15 active"><label class="pull-right">留样原因:</label></td>
							<td colspan="3">
								<textarea class="form-control" rows="2" id="lyReason" name="lyReason" >${vo.lyReason}</textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a href="#" class="btn btn-w-m btn-primary" onclick="formSubmit('updateData.do');return false;"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
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
		function formSubmit(url){
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
		}
		 
		function fnSelectUser(){
			var userId=$('#useId').val();
			layer.open({
				title:'人员选择',	
				type: 2,
				 area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}sys/account/select.do?id='+userId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var data=iframeWin.fnSelect();
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
