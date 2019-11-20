<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
legend {
	border-bottom: 0px;
	width: 80px;
	margin-bottom: 0px;
	font-size: 14px !important;
}
.input-group-btn .btn{
	padding:6px;
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
				<h5>
					<ol class="breadcrumb">
						<li><a href="gridPage.do">任务分配</a></li>
						<li><strong>编辑</strong></li>
					</ol>
				</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> <a class="close-link"> <i class="fa fa-times"></i></a>
				</div>
			</div>
			<div class="ibox-content">
				<%@ include file="../../../include/status.jsp"%>
				<form method="post" class="form-horizontal" id="thisForm">
					<input name="ids" value="${vo.ids}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<thead>
							<tr>
								<th width="50">序号</th>
								<th>检测项目</th>
								<th width="10%">样品名称</th>
								<th width="10%">样品数量</th>
								<th width="15%">检测人</th>
								<th width="15%">复核人</th>
								<th width="15%">检测截止时间</th>
								<th width="15%">上报截止日期</th>
							</tr>
						</thead>
						<tbody id="fpTb">
							<c:forEach items="${vo.timList}" var="e" varStatus="s">
								<tr>
									<td align="center">${s.index+1}
										<input type="hidden" value="${e.id}" name="timList[${s.index}].id" /> 
									</td>
									<td>${e.itemName}</td>
									<td>${e.sampName}</td>
									<td>${e.sampNum}</td>
									<td>
										<div class="input-group" style="width: 100%">
											<div class="input-group-btn">
												<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectTester('${e.itemId}','${s.index}')">选择</button>
											</div>
											<input type="hidden" id="testManId${s.index}" name="timList[${s.index}].testManId" value="${e.testManId}">
											<input type="text" id="testMan${s.index}" name="timList[${s.index}].testMan" class="form-control required"  validate="required" placeholder="检测人" value="${e.testMan}" onclick="fnSelectTester('${e.itemId}','${s.index}')">
											<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span>
										</div>
									</td>
									<td>
										<div class="input-group" style="width: 100%">
											<div class="input-group-btn">
												<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectChecker('${s.index}')">选择</button>
											</div>
											<input type="hidden" id="checkManId${s.index}" name="timList[${s.index}].checkManId" value="${e.checkManId}">
											<input type="text" id="checkMan${s.index}" name="timList[${s.index}].checkMan" class="form-control required" validate="required" placeholder="复核人" value="${e.checkMan}" onclick="fnSelectChecker('${s.index}')">
											<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span>
										</div>
									</td>
									<td>
										<div class="input-group">
											<input class="form-control  datetimeISO required" validate="required" value="${e.compDate}" name="timList[${s.index}].compDate"> 
											<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVal(this);return false;"></span>
										</div>
									</td>
									<td>
										<div class="input-group">
											<input class="form-control  dateISO required" validate="required" value="${e.sbDate}" name="timList[${s.index}].sbDate"> 
											<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVal(this);return false;"></span>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
						<legend>审核信息</legend>
					</fieldset>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">分&nbsp;&nbsp;配&nbsp;&nbsp;人:</label></td>
								<td class="width-35">
									<input id="assignUserId" name="assignUserId" type="hidden" value="${vo.assignUserId}" /> 
									<input id="assignUser" name="assignUser" class="form-control" maxlength=32 placeholder="分配人" type="text" value="${vo.assignUser}" readonly="readonly" />
								</td>
								<td class="width-15 active"><label class="pull-right">分配时间:</label></td>
								<td class="width-35">
									<div class="input-group date">
		                              	<input id="assignDate" name="assignDate" class="form-control required datetimeISO" validate="required" maxlength=20 placeholder="审核时间" type="text" value="${vo.assignDate}" />
		                            	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea style="width: 600px;" rows="2" cols="60" id="assignMsg" name="assignMsg" class="form-control" maxlength="128">${vo.assignMsg}</textarea>
								</td>
							</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-primary"  href="javascript:;" onclick="fnSubmit('updateData.do?isCommit=1')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 提交</a>
							<a class="btn btn-w-m btn-default" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<!-- Sweet alert -->
	<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
</body>
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
</script>
<script type="text/javascript">
 
function copyVal(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('input').val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#fpTb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
	});
}
function copyVals(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('input').eq(0).val();
	var nameValue=obj.closest('td').find('input').eq(1).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#fpTb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
		$(this).find('td').eq(indexTd).find('input').eq(1).val(nameValue);
	});
}
function fnSelectTester(itemId,n){
	var userId=$('#testManId'+n).val();
	layer.open({
		title:'人员选择',	
		type: 2,
		 area: ['70%', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: 'tester_select.do?testManId='+userId+'&itemId='+itemId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
		  var iframeWin = window[layero.find('iframe')[0]['name']];
		  var data=iframeWin.fnSelectUser();
		  $('#testMan'+n).val(data.name);
			$('#testManId'+n).val(data.id);
		}
	});
}
function fnSelectChecker(n){
	var userId=$('#checkManId'+n).val();
	layer.open({
		title:'人员选择',	
		type: 2,
		 area: ['70%', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: 'checker_select.do?id='+userId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
		  var iframeWin = window[layero.find('iframe')[0]['name']];
		  var data=iframeWin.fnSelectUser();
		   $('#checkMan'+n).val(data['userVo.name']);
			$('#checkManId'+n).val(data.id);
		}
	});
}
</script>
</html>
