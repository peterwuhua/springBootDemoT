<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
.btn-group>button{
	margin-right: 10px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li>报告签发</li>
						<li><strong>编辑</strong></li>
					</ol>
				</h5>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">报告编号:</label></td>
							<td class="width-35">
								${vo.reportNo}
							</td>
							<td class="width-15 active"><label class="pull-right">任务编号:</label></td>
							<td class="width-35">
								<a href="javascript:void(0);" onclick="fnTask('${vo.taskVo.id}','${vo.taskVo.no}');">${vo.taskVo.no}</a>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">客户名称:</label></td>
							<td>
								${vo.custVo.custName}
							</td>
							 <td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">样品类别:</label></td>
							<td>
								${vo.sampTypeName}
							</td>
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>
								${vo.sampName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">报告日期:</label></td>
							<td>
								${vo.reportDate}
							</td>
							<td class="active"><label class="pull-right">要求完成日期:</label></td>
							<td>
								${vo.finishDate}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">报告信息:</label></td>
							<td colspan="3">
			                  <div class="btn-group" id="editReprt">
		                  	 		<button class="btn btn-info" type="button" onclick="fnOpenReport('');">在线查看报告</button>
		                       		<c:if test="${vo.taskType=='定期检测'}">
		                       			<button class="btn btn-info" type="button" onclick="fnOpenReport('dq');">在线查看报告(总)</button>
		                       		</c:if>
			                    </div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:</label></td>
							<td colspan="3">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" >${e.fileName}</a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
					</table>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						<legend>审核信息</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">批&nbsp;准&nbsp;&nbsp;人:</label></td>
								<td>
									<input type="text" id="signUser" name="signUser" class="form-control" placeholder="签发人员" value="${vo.signUser}"  readonly="readonly">
									<input type="hidden" name="signUserId"  value="${vo.signUserId}">
								</td>
								<td class="width-15 active"><label class="pull-right">批准日期:</label></td>
								<td>
									<div class="input-group date">
		                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                              	<input id="signDate" name="signDate" class="form-control required datetimeISO" placeholder="审核日期" type="text" value="${vo.signDate}" />
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">审核意见:</label></td>
								<td colspan="3">
									<textarea rows="2" id="signMsg" name="signMsg" class="form-control" maxlength="128">${vo.signMsg}</textarea>
								</td>
							</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('updateData.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 提交</a>
							<a class="btn btn-w-m btn-danger" type="button" onclick="formSubmit4Back('updateData.do?isCommit=-1')"><i class="fa fa-check" aria-hidden="true"></i> 退回</a>
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
            <!--PageOffice.js -->
     <script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
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
		function formSubmit4Back(url){
			var signMsg=$('#signMsg').val();
			if(signMsg==''){
				parent.toastr.error('请输入退回原因！', '');
				return false;
			}
			swal({
		        title: "您确定要提交该任务吗",
		        text: "提交后将无法修改，请谨慎操作！",
		        type: "warning",
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
		 
		function fnShowReport(type){
			POBrowser.openWindow('${basePath}bus/report/showReport.do?id=${vo.id}&uuid='+type,'width=1200px;height=800px;');
		}
		function fnOpenReport(type){
			POBrowser.openWindow('${basePath}bus/report/auditReport.do?id=${vo.id}&uuid='+type,'width=1200px;height=800px;');
		}
	</script>
</body>
</html>