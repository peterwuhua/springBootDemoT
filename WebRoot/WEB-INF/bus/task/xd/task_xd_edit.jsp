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
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
 
.form-control{
	padding: 4px 4px;
}
.table select{
	padding-top: 2px;
}
#detail_tb .btn{
	padding: 6px;
}
.table th{
text-align: center;
}
a{
	color: blue;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">任务下达</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="ids" value="${vo.ids}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<th width="50">序号</th>
								<th width="120">任务编号</th>
								<th>检测项目</th>
								<th style="width: 30%;">样品数量</th>
								<th style="width: 20%;">检测科室</th>
								<th style="width: 20%;">检测要求完成时间</th>
							</tr>
						</thead>
						<tbody id="xdTb">
							<c:forEach items="${vo.timList}" var="e" varStatus="s">
								<tr>
									<td>
										${s.index+1}
										<input type="hidden" value="${e.id}" name="timList[${s.index}].id" /> 
										<input type="hidden" value="${e.taskVo.id}" name="timList[${s.index}].taskVo.id" /> 
										<input type="hidden" value="${e.itemId}" name="timList[${s.index}].itemId" /> 
										<input type="hidden" value="${e.itemName}" name="timList[${s.index}].itemName" />
									</td>
									<td><a href="javascript:void();" onclick="fnTask('${e.taskVo.id}','${e.taskVo.no}');">${e.taskVo.no}</a></td>
									<td>${e.itemName}</td>
									<td style="word-wrap:break-word;word-break:break-all;">${e.sampNum}</td>
									<td>
										<div class="input-group" style="width: 100%">
											<select class="form-control required" validate="required" name="timList[${s.index}].deptId" id="orgId${s.index}" >
												<option value="">请选择</option>
												<c:forEach items="${orgList}" var="e1" varStatus="s1">
													<c:choose>
														<c:when test="${e.deptId==e1.id}">
															<option value="${e1.id}" selected="selected">${e1.name}</option>
														</c:when>
														<c:otherwise>
															<option value="${e1.id}">${e1.name}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select> 
											<span  style="display: table-cell;"  class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copySelect(this);return false;"></span></td>
										</div>
									<td>
										<div class="input-group">
											<input class="form-control datetimeISO  required" validate="required" value="${e.compDate}" name="timList[${s.index}].compDate"> 
											<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVal(this);return false;"></span>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					 
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">下达人员:</label></td>
							<td class="width-35">
								<input type="hidden" id="xdUserId" name="xdUserId" value="${vo.xdUserId}">
								<input type="text" id="xdUser" name="xdUser" value="${vo.xdUser}" class="form-control" readonly="readonly">
							</td>
							<td class="width-15 active"><label class="pull-right">下达时间:</label></td>
							<td class="width-35">
								<div class="input-group date">
									<input type="text" id="xdDate" name="xdDate" class="form-control datetimeISO required" validate="required" value="${vo.xdDate}">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="xdMsg" name="xdMsg" maxLength="128">${vo.xdMsg}</textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('updateData.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 提交</a>
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
	function copyVal(obj){
		obj=$(obj);
		var idValue=obj.closest('td').find('input').eq(0).val();
		var indexTr=obj.closest('td').closest('tr').index();
		var indexTd=obj.closest('td').index();
		$('#xdTb tr:gt('+indexTr+')').each(function(){
			$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
		});
	}
	function copySelect(obj){
		obj=$(obj);
		var idValue=obj.closest('td').find('select').val();
		var indexTr=obj.closest('td').closest('tr').index();
		var indexTd=obj.closest('td').index();
		$('#xdTb tr:gt('+indexTr+')').each(function(){
			$(this).find('td').eq(indexTd).find('select').val(idValue);
		});
	}
	function formSubmitSave(url){
		$('#thisForm').attr('action',url);
		$('#thisForm').submit()
	}
	function formSubmit(url){
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
				 $('#thisForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
			}
	    });
	}
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
