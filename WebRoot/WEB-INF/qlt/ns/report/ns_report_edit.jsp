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
					<li><a>内审报告</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm" >
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<td colspan="4" style="background-color:#fff;border: 0px;">计划信息</td>
							</tr>
						</thead>
						<tr>
							<td class="width-15 active"><label class="pull-right">计划名称:</label></td>
							<td class="width-35">
								${vo.nsVo.title} 
							</td>
							<td class="width-15 active"><label class="pull-right">审核年度:</label></td>
							<td class="width-35">
								${vo.year}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
							<td class="width-35">
								${vo.nsVo.deptName}
							</td>
							<td class="width-15 active"><label class="pull-right">审核目的:</label></td>
							<td class="width-35">
								${vo.nsVo.purpose}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">审核范围:</label></td>
							<td>
								${vo.nsVo.ranges}
							</td>
							<td class="active"><label class="pull-right">审核依据:</label></td>
							<td>
								${vo.nsVo.stand}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">审核方式:</label></td>
							<td>
								${vo.nsVo.auditType}
							</td>
							<td class="active"><label class="pull-right">内审地点:</label></td>
							<td>
								${vo.nsVo.address}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">内审组长:</label></td>
							<td>
								${vo.nsVo.headName}
							</td>
							<td class="active"><label class="pull-right">审核人员:</label></td>
							<td>
								${vo.nsVo.nsName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.nsVo.remark}
							</td>
						</tr>
					</table>
					<table class="table table-bordered">
						<tr>
							<td colspan="4" style="background-color:#fff;">报告信息</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">审核月份:</label></td>
							<td class="width-35">
								${vo.month}月
							</td>
							<td class="width-15 active"><label class="pull-right ">审核要素:</label></td>
							<td>
								${vo.item}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">情况概述:</label></td>
							<td>
								${vo.content}
							</td>
							<td class="active"><label class="pull-right ">内审结论:</label></td>
							<td>
								 ${vo.result}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">报告编制人:</label></td>
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
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea maxlength="128" rows="2" class="form-control" id="remark" name="remark">${vo.remark}</textarea>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">月度报告:</label></td>
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
								<td colspan="16" style="background-color:#fff;border: 0px;">要素信息
								</td>
							</tr>
							<tr>
								<th style="width: 50px;text-align: center;">条款</th>
								<th>检查内容</th>
								<th>检查重点</th>
								<th style="width: 30px;">符合</th>
								<th style="width: 30px;">基本符合</th>
								<th style="width: 30px;">不符合</th>
								<th style="min-width: 100px;text-align: center;">存在问题</th>
								<th style="min-width: 100px;text-align: center;">整改情况</th>
							</tr>
						</thead>
						<tbody id="ct_tb">
							<c:forEach items="${vo.detailList}" var="e" varStatus="v">
								<tr>
									<td colspan="8">
										<c:if test="${e.code!=''}">${e.code}、</c:if>${e.name}
									</td>
								</tr>
								<c:forEach items="${e.subList}" var="e1" varStatus="v1">
									<c:choose>
										<c:when test="${e1.subList!=null && fn:length(e1.subList)>0}">
											<c:forEach items="${e1.subList}" var="e2" varStatus="v2">
												<tr>
													<c:if test="${v2.index==0}">
														<td rowspan="${e1.count}" align="center">
															${e1.code}
														</td>
														<td rowspan="${e1.count}">
															${e1.name}
														</td>
													</c:if>
													<td>
														${e2.name}
													</td>
													<td class="mtd">
														<div class="checkbox i-checks">
															<div class="iradio_square-green">
																<c:choose>
																	<c:when test="${e2.result=='符合'}">
																		<input type="radio" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].result" value="符合" checked="checked" disabled="disabled">
																	</c:when>
																	<c:otherwise>
																		<input type="radio" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].result" value="符合" disabled="disabled">
																	</c:otherwise>
																</c:choose>
															</div>
														</div>
													</td>
													<td class="mtd">
														<div class="checkbox i-checks">
															<div class="iradio_square-green">
																<c:choose>
																	<c:when test="${e2.result=='基本符合'}">
																		<input type="radio" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].result" value="基本符合" checked="checked" disabled="disabled">
																	</c:when>
																	<c:otherwise>
																		<input type="radio" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].result" value="基本符合" disabled="disabled">
																	</c:otherwise>
																</c:choose>
															</div>
														</div>
													</td>
													<td class="mtd">
														<div class="checkbox i-checks">
															<div class="iradio_square-green">
																<c:choose>
																	<c:when test="${e2.result=='不符合'}">
																		<input type="radio" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].result" value="不符合" checked="checked"  disabled="disabled">
																	</c:when>
																	<c:otherwise>
																		<input type="radio" name="detailList[${v.index}].subList[${v1.index}].subList[${v2.index}].result" value="不符合"  disabled="disabled">
																	</c:otherwise>
																</c:choose>
															</div>
														</div>
													</td>
													<td>
														${e2.question}
													</td>
													<td>
														${e2.rectification}
													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td align="center">
													${e1.code}
												</td>
												<td>
													${e1.name}
												</td>
												<td>
													/
												</td>
												<td class="mtd">
													<div class="checkbox i-checks">
														<div class="iradio_square-green">
															<c:choose>
																<c:when test="${e1.result=='符合'}">
																	<input type="radio" name="detailList[${v.index}].subList[${v1.index}].result" value="符合" checked="checked" disabled="disabled">
																</c:when>
																<c:otherwise>
																	<input type="radio" name="detailList[${v.index}].subList[${v1.index}].result" value="符合" disabled="disabled">
																</c:otherwise>
															</c:choose>
														</div>
													</div>
												</td>
												<td class="mtd">
													<div class="checkbox i-checks">
														<div class="iradio_square-green">
															<c:choose>
																<c:when test="${e1.result=='基本符合'}">
																	<input type="radio" name="detailList[${v.index}].subList[${v1.index}].result" value="基本符合" checked="checked" disabled="disabled">
																</c:when>
																<c:otherwise>
																	<input type="radio" name="detailList[${v.index}].subList[${v1.index}].result" value="基本符合" disabled="disabled">
																</c:otherwise>
															</c:choose>
														</div>
													</div>
												</td>
												<td class="mtd">
													<div class="checkbox i-checks">
														<div class="iradio_square-green">
															<c:choose>
																<c:when test="${e1.result=='不符合'}">
																	<input type="radio" name="detailList[${v.index}].subList[${v1.index}].result" value="不符合" checked="checked" disabled="disabled">
																</c:when>
																<c:otherwise>
																	<input type="radio" name="detailList[${v.index}].subList[${v1.index}].result" value="不符合" disabled="disabled">
																</c:otherwise>
															</c:choose>
														</div>
													</div>
												</td>
												<td>
													${e1.question}
												</td>
												<td>
													${e1.rectification}
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a id="sub_btn" class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmit4Save('updateData.do?isCommit=0');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
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
	function formSubmit4Save(url){
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
	function formSubmit(url){
		var msg=checkValue();
    	if(msg!=''){
    		layer.msg(msg, {icon: 0,time: 3000});
    		return true;
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
	function createReport(){
		var msg=checkValue();
    	if(msg==''){
    		if(confirm("报告已经存在，要重新生成吗？")){
    			 $('#thisForm').attr('action','updateData.do?isCommit=0');
   				 $('#thisForm').ajaxSubmit(function(res) {
   			    	if(res.status=='success'){
   			    	    parent.toastr.success(res.message, '');
   			    	    POBrowser.openWindow('${basePath}qlt/nsReport/updateRep4Word.do?id=${vo.id}','width=1200px;height=800px;');
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
		    	    POBrowser.openWindow('${basePath}qlt/nsReport/updateRep4Word.do?id=${vo.id}','width=1200px;height=800px;');
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
    	}
	}
	function fnOpenReport(){
		POBrowser.openWindow('${basePath}qlt/nsReport/editReport.do?id=${vo.id}','width=1200px;height=800px;');
	}
	$(function(){
		var has='${vo.filePath}';
		if(has==''){
			$('#editReprt').hide();
		}
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
	})
</script>
</body>
</html>
