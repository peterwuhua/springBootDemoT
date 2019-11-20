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
					<li><a>计划审核</a></li>
					<li><strong>编辑</strong></li>
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
					</table>
					<table class="table table-bordered">
						 <thead>
							<tr>
								<td colspan="16" style="background-color:#fff;border: 0px;">要素信息</td>
							</tr>
							<tr>
								<th style="width: 80px;text-align: center;">条款</th>
								<th style="text-align: center;">要素</th>
								<th style="width: 30px;">一月</th>
								<th style="width: 30px;">二月</th>
								<th style="width: 30px;">三月</th>
								<th style="width: 30px;">四月</th>
								<th style="width: 30px;">五月</th>
								<th style="width: 30px;">六月</th>
								<th style="width: 30px;">七月</th>
								<th style="width: 30px;">八月</th>
								<th style="width: 30px;">九月</th>
								<th style="width: 30px;">十月</th>
								<th style="width: 30px;">十一月</th>
								<th style="width: 30px;">十二月</th>
								<th style="width: 120px;text-align: center;">负责人</th>
								<th style="width: 150px;text-align: center;">协助人</th>
							</tr>
						</thead>
						<tbody id="ct_tb">
							<c:forEach items="${vo.detailList}" var="e" varStatus="v">
								<tr>
									<td>
										${e.code}
									</td>
									<td>
										${e.name}
									</td>
									 <c:forEach begin="1" end="12" var="i">
										<td class="mtd">
											<c:set var="a"><c:out value="${i}"/></c:set>
											<c:set var="b" value="${i},"/>
											<c:set var="c" value=",${i}"/>
											<c:set var="d" value=",${i},"/>
											<div class="checkbox i-checks">
												<div class="icheckbox_square-green">
													<c:choose>
														<c:when test="${e.months==a || fn:startsWith(e.months,b) || fn:endsWith(e.months,c) || fn:contains(e.months,d)}">
															<input type="checkbox" name="detailList[${v.index}].months" value="${i}" checked="checked" disabled>
														</c:when>
														<c:otherwise>
															<input type="checkbox" name="detailList[${v.index}].months" value="${i}" disabled>
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</td>
									</c:forEach>
									<td>
										${e.headName}
									</td>
									<td>
										${e.xzName}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered">
						<tr>
							<td colspan="4" style="background-color:#fff;">审核信息</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">审&nbsp;&nbsp;核&nbsp;&nbsp;人:</label></td>
							<td>
								<input type="hidden" id="auditId" name="auditId" value="${vo.auditId}">
								<input type="text" id="auditName" name="auditName" class="form-control" value="${vo.auditName}" readonly="readonly">
							</td>
							<td class="active"><label class="pull-right ">审核日期:</label></td>
							<td>
								<div class="input-group date">
	                              	<input id="auditDate" name="auditDate" class="form-control required dateISO" validate="required" type="text" value="${vo.auditDate}" />
	                              	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                            </div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea maxlength="128" rows="2" class="form-control" id="remark" name="remark"></textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a id="sub_btn" class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('updateData.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 通过</a>
							<a id="sub_btn" class="btn btn-w-m btn-danger" href="javascript:;" onclick="formSubmitBack('updateData.do?isCommit=-1');"><i class="fa fa-check" aria-hidden="true"></i> 退回</a>
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
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
	});
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
	function formSubmitBack(url){
		var remark=$('#remark').val();
		if(remark==''){
			parent.toastr.error('请输入退回原因！', '');
			return false;
		}
		$('#thisForm').attr('action',url);
		swal({
	        title: "您确定要提交该任务吗",
	        text: "提交后将无法修改，请谨慎操作！",
	        type: "warning",
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
	</script>
</body>
</html>
