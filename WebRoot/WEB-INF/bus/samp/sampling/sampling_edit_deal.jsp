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
					<li><strong>余样处理</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="ids" value="${vo.ids}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<td width="50">
									序号
								</td>
								<td>
									任务编号
								</td>
								<td>
									客户名称
								</td>
								<td>
									采样点位
								</td>
								<td>
									样品类型
								</td>
								<td>
									样品名称
								</td>
								<td>
									样品编号
								</td>
							</tr>
						</thead>
						<c:forEach items="${sampList}" var="e" varStatus="v">
							<tr>
								<td>
									${v.index+1}
								</td>
								<td>
									${e.taskVo.no}
								</td>
								<td>
									${e.custVo.custName} 
								</td>
								<td>
									${e.pointName}
								</td>
								<td>
									${e.sampTypeName}
								</td>
								<td>
									${e.sampName}
								</td>
								<td>
									${e.sampCode}
								</td>
							</tr>
						</c:forEach>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>	
							<td class="width-15 active"><label class="pull-right">处&nbsp;理&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								<input type="text" id="dealUser" name="dealUser" class="form-control required" validate="required" value="${vo.dealUser}" >
							</td>
							<td class="width-15 active"><label class="pull-right">处理日期:</label></td>
							<td class="width-35">
								<div class="input-group date">
	                              	<input id="dealDate" name="dealDate" class="form-control dateISO required" validate="required" placeholder="请选择" type="text" value="${vo.dealDate}" />
	                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                            </div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">样品处理方式:</label></td>
							<td colspan="3">
								<div class="radio i-checks">
                                       <label>
                                           <div class="iradio_square-green">
                                           <input type="radio" value="委托方自取" name="dealRequest" <c:if test="${vo.dealRequest=='委托方自取'}">checked</c:if>>
                                           </div>委托方自取
                                       </label>
                                       <label>
                                           <div class="iradio_square-green">
                                           <input type="radio" value="委托检测机构进行销毁" name="dealRequest" <c:if test="${vo.dealRequest=='委托检测机构进行销毁'}">checked</c:if>>
                                           </div>委托检测机构进行销毁
                                       </label>
                                   </div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea class="form-control" rows="2" id="dealRemark" name="dealRemark" maxlength="128">${vo.dealRemark}</textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a href="#" class="btn btn-w-m btn-primary" onclick="formSubmit('update4Deal.do');return false;"><i class="fa fa-floppy-o" aria-hidden="true"></i> 提交</a>
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
