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
					<li><a>汇总报告</a></li>
					<li><strong>编辑</strong>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm" >
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">计划名称:</label></td>
							<td class="width-35">
								${vo.title} 
							</td>
							<td class="width-15 active"><label class="pull-right">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份:</label></td>
							<td class="width-35">
								${vo.year}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
							<td class="width-35">
								${vo.deptName}
							</td>
							<td class="width-15 active"><label class="pull-right">审核目的:</label></td>
							<td class="width-35">
								${vo.purpose}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">审核范围:</label></td>
							<td>
								${vo.ranges}
							</td>
							<td class="active"><label class="pull-right">审核依据:</label></td>
							<td>
								${vo.stand}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">审核方式:</label></td>
							<td>
								${vo.auditType}
							</td>
							<td class="active"><label class="pull-right">内审地点:</label></td>
							<td>
								${vo.address}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">内审组长:</label></td>
							<td>
								${vo.headName}
							</td>
							<td class="active"><label class="pull-right">审核人员:</label></td>
							<td>
								${vo.nsName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">汇&nbsp;总&nbsp;&nbsp; 人:</label></td>
							<td>
								<input type="hidden" id="sumId" name="sumId" value="${vo.sumId}">
								<input type="text" id="sumName" name="sumName" class="form-control" value="${vo.sumName}" readonly="readonly">
							</td>
							<td class="active"><label class="pull-right ">编制日期:</label></td>
							<td>
								<div class="input-group date">
	                              	<input id="sumDate" name="sumDate" class="form-control required dateISO" validate="required" type="text" value="${vo.sumDate}" />
	                              	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                            </div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">内审情况概述:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="content" name="content">${vo.content}</textarea>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">内审结果:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="result" name="result">${vo.result}</textarea>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea maxlength="128" rows="2" class="form-control" id="remark" name="remark">${vo.remark}</textarea>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">内审报告:</label></td>
							<td colspan="3">
								<div class="btn-group" id="repeatReport">
			                       <button class="btn btn-info" type="button" onclick="createReport();">初始化报告 </button>
			                   </div>
		                  	 	<div class="btn-group" id="editReprt">
			                       <button class="btn btn-info" type="button" onclick="fnOpenReport();">修改报告</button>
			                    </div>
							</td>
						</tr>
					</table>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th style="width: 50px;text-align: center;">月份</th>
								<th style="text-align: center;">要素</th>
								<th style="text-align: center;">情况概述</th>
								<th style="text-align: center;">审核结果</th>
								<th style="text-align: center;">进度</th>
								<th style="text-align: center;">报告</th>
							</tr>
						</thead>
						<tbody id="ct_tb">
							<c:forEach items="${vo.recordList}" var="e" varStatus="v">
								<tr>
									<td style="text-align: center;">
										${e.month}
									</td>
									<td>
										${e.item}
									</td>
									<td>
										${e.content}
									</td>
									<td>
										${e.result}
									</td>
									<td>
										${e.status}
										<c:if test="${!fn:contains(e.status,'完成')}">
											<input type="hidden" name="status" value="${e.status}">
										</c:if>
									</td>
									<td>
										<a href="javascript:;" onclick="fnShowReport('${e.id}');">${e.fileName}</a>
									</td>
								</tr>
							</c:forEach>
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
  <!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
<script>
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
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
			var nopass=$('input[name="status"]').length;
			if(nopass>0){
				parent.toastr.error('内审未完成，暂时不能提交！', '');
				return ;
			}
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
	   			    	    POBrowser.openWindow('${basePath}qlt/nsSum/updateRep4Word.do?id=${vo.id}','width=1200px;height=800px;');
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
   			    	    POBrowser.openWindow('${basePath}qlt/nsSum/updateRep4Word.do?id=${vo.id}','width=1200px;height=800px;');
   			        }else{
   			        	parent.toastr.error(res.message, '');
   			        }
   				});
	    	}
		}
		function fnOpenReport(){
			POBrowser.openWindow('${basePath}qlt/nsSum/editReport.do?id=${vo.id}','width=1200px;height=800px;');
		}
		function fnShowReport(id){
			POBrowser.openWindow('${basePath}qlt/nsReport/showReport.do?id='+id,'width=1200px;height=800px;');
		}
	</script>
</body>
</html>
