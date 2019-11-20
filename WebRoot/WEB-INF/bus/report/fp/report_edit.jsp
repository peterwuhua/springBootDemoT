<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>

<style type="text/css">
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
.form-control{
	padding: 2px 4px;
}
.input-group-addon{
padding: 2px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">报告分配</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">报告编号:</label></td>
							<td class="width-35">
								<input id="reportNo" name="reportNo" class="form-control required" validate="required" type="text" value="${vo.reportNo}" />
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
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>
								${vo.sampName}
							</td>
							<td class="active"><label class="pull-right ">检测日期:</label></td>
							<td>
								<div class="input-group date">
	                              	<input name="testDate" class="form-control required dateISO" validate="required" type="text" value="${vo.testDate}" />
	                            	<span class="input-group-addon">至</span>
	                            	<input name="testEndDate" class="form-control required dateISO" validate="required" type="text" value="${vo.testEndDate}" />
	                            </div>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">编制人员:</label></td>
							<td>
								<select class="form-control required" validate="required" id="makeUserId" name="makeUserId" onchange="setBZRY(this);">
									<option value="">-请选择-</option>
									<c:forEach items="${userList}" var="e" varStatus="v">
									 	 <c:choose>
									 	 	<c:when test="${vo.makeUserId==e.id}">
									 	 		<option value="${e.id}" selected="selected">${e.userVo.name}</option>
									 	 	</c:when>
									 	 	<c:otherwise>
									 	 		<option value="${e.id}">${e.userVo.name}</option>
									 	 	</c:otherwise>
									 	 </c:choose>
									 </c:forEach>
								</select>
								<input type="hidden" id="makeUser" name="makeUser" value="${vo.makeUser}" >
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">分&nbsp;配&nbsp;&nbsp;人:</label></td>
							<td>
								<input type="text" id="fpUser" name="fpUser" class="form-control" placeholder="分配人员" value="${vo.fpUser}"  readonly="readonly">
								<input type="hidden" name="fpUserId"  value="${vo.fpUserId}">
							</td>
							<td class="width-15 active"><label class="pull-right">分配日期:</label></td>
							<td>
								<div class="input-group date">
	                              	<input id="fpDate" name="fpDate" class="form-control required datetimeISO" validate="required" placeholder="分配日期" type="text" value="${vo.fpDate}" />
	                            	 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                            </div>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" type="button" onclick="fnSubmitSave('update4Data.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('update4Data.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
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
function setBZRY(obj){
	var t = $(obj).find("option:selected").text();
	$(obj).closest("td").find('input').val(t);
}


	function fnSubmitSave(url){
		$('#thisForm').attr('action',url);
		 $('#thisForm').submit();
	}
	function fnSubmit(url){
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
				 $('#thisForm').submit();
			}
	    });
	}

</script>
<script type="text/javascript">
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
</script>
</body>
</html>
