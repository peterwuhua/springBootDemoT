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
					<li><a>评审报告</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm" >
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">计划名称:</label></td>
								<td class="width-35">
									${vo.title}
								</td>
								<td class="width-15 active"><label class="pull-right">评审时间:</label></td>
								<td class="width-35">
									${vo.psDate}
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
								<td class="width-35">
									${vo.deptName}
								</td>
								<td class="width-15 active"><label class="pull-right">评审主持人:</label></td>
								<td class="width-35">
									${vo.zcName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">评审地点:</label></td>
								<td>
									${vo.address}
								</td>
								<td class="active"><label class="pull-right">评审依据:</label></td>
								<td>
									${vo.stand}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内审组长:</label></td>
								<td>
									${vo.headName}
								</td>
								<td class="active"><label class="pull-right">审核人员:</label></td>
								<td>
									${vo.psName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">计划详情:</label></td>
								<td colspan="3">
									${vo.content}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">编&nbsp;&nbsp;制&nbsp;&nbsp;人:</label></td>
								<td>
									${vo.userName}
								</td>
								<td class="active"><label class="pull-right ">编制日期:</label></td>
								<td>
									${vo.date}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									${vo.remark}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">评审报告:</label></td>
								<td colspan="3">
									<div class="btn-group" id="repeatReport">
				                       <button class="btn btn-info" type="button" onclick="createReport();">初始化报告 </button>
				                   </div>
			                  	 	<div class="btn-group" id="editReprt">
				                       <button class="btn btn-info" type="button" onclick="fnOpenReport();">修改报告</button>
				                    </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">编&nbsp;&nbsp;制&nbsp;&nbsp;人:</label></td>
								<td>
									<input type="hidden" id="reportId" name="reportId" value="${vo.reportId}">
									<input type="text" id="reportName" name="reportName" class="form-control" value="${vo.reportName}" readonly="readonly">
								</td>
								<td class="active"><label class="pull-right ">编制日期:</label></td>
								<td>
									<div class="input-group date">
		                              	<input id="reportDate" name="reportDate" class="form-control required dateISO" validate="required" type="text" value="${vo.reportDate}" />
		                              	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
						</tbody>
					</table>
					<table class="table table-bordered">
						<thead>
							<tr>
								<td>记录人</td>
								<td>记录日期</td>
								<td>评审内容</td>
								<td>评审中发现的问题</td>
								<td>评审结论</td>
								<td>实施纠正/改进的措施</td>
								<td>检查</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${vo.recordList}" var="e" varStatus="v">
								<tr>
									<td>${e.userName}</td>
									<td>${e.date}</td>
									<td>${e.content}</td>
									<td>${e.problem}</td>
									<td>${e.result}</td>
									<td>${e.measures}</td>
									<td>${e.checks}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a id="sub_btn" class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('updateData.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
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
	$(document).ready(function() {
		var has='${vo.filePath}';
		if(has==''){
			$('#editReprt').hide();
		}
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
	//检查报告是否存在
	function checkValue(){
		var msg='';
		$.ajax({ 
			url:"checkReportFile.do?id=${vo.id}",
			dataType:"json",
			type:"post",
			async:false,
			success: function(data){
				if("error"==data.type){
					msg=data.message;
				}
			},
			error:function(ajaxobj){  
				msg=ajaxobj;
		    }  
		});
		return msg;
	}
	function createReport(){
		var msg=checkValue();
    	if(msg==''){
    		if(confirm("报告已经存在，要重新生成吗？")){
    			 $('#thisForm').attr('action','updateData.do?isCommit=0');
   				 $('#thisForm').ajaxSubmit(function(res) {
   			    	if(res.status=='success'){
   			    	    parent.toastr.success(res.message, '');
   			    	    POBrowser.openWindow('${basePath}qlt/gsReport/updateRep4Word.do?id=${vo.id}','width=1200px;height=800px;');
   			        }else{
   			        	parent.toastr.error(res.message, '');
   			        }
   				});
    		}
    	}else{
    		$('#editReprt').show();
    		 $('#thisForm').attr('action','updateData.do?isCommit=0');
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
			    	    POBrowser.openWindow('${basePath}qlt/gsReport/updateRep4Word.do?id=${vo.id}','width=1200px;height=800px;');
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
    	}
	}
	function fnOpenReport(){
		POBrowser.openWindow('${basePath}qlt/gsReport/editReport.do?id=${vo.id}','width=1200px;height=800px;');
	}
	function fnShowReport(id){
		POBrowser.openWindow('${basePath}qlt/gsReport/showReport.do?id='+id,'width=1200px;height=800px;');
	}
</script>
</body>
</html>
