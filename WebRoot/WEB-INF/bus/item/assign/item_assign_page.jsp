<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>

</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>任务分配</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-success active" href="javascript:;">未提交的记录</a>
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPaged.do">已提交的记录</a>
					</div>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						<a class="btn btn-primary dropdown-toggle" href="javascript:;" onclick="fnEdit('edit.do');"><i class="fa fa-pencil-square-o" aria-hidden="true"> 批量分配</i></a>
						<a class="btn btn-danger" href="javascript:;" onclick="updateStop();">终止</a>
					</div>
					<div class="jqGrid_wrapper">
						<table id="listTable"></table>
						<div id="pager"></div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>

	<script>
	function fnEdit(url){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择要分配的记录', {icon: 0,time: 3000});
			return false;
		}else{
			$("#ids").val(selectIds);
		}
		openEditPage('edit.do?ids='+selectIds);
	}
	$(function() {
		var url ="";
		url = 'gridData.do';
		var colNames = ['检测项目','任务编号','检测性质','样品名称','样品数量','下达日期','要求完成日期','操作'];
		var colModel = [ 
			{
				name : 'itemName',
				index : 'itemName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.no',
				index : 'task.no',
				formatter:formatTask,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.taskType',
				index : 'task.taskType',
				width : 90,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				search : false,
				sortable : false
			},{
				name : 'sampNum',
				index : 'sampNum',
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'taskVo.xdDate',
				index : 'task.xdDate',
				search : false,
				sortable : false
			},{
				name : 'compDate',
				index : 'compDate',
				search : false,
				sortable : false
			},{
				name : 'act',
				index : 'act',
				width : 70,
				fixed:true,
				title : false,
				search : false,
				sortable : false
			}];
		gridInitMin(url, colNames, colModel,true);
	});
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?ids='+ids[i]+'\')">编辑</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	function updateStop(){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择终止的记录', {icon: 0,time: 3000});
			return false;
		}
		layer.confirm('确认要终止吗?', {icon:3, title:'系统提示'}, function(index){
			$.ajax({ 
				url:"${basePath}bus/itemAssign/update2Stop.do",
				data: {'ids':selectIds.join(',')},
				async:false,
				success: function(data){
					if(data.status=='success'){
						jqgridReload()
					}
					layer.msg(data.message, {icon: 0,time: 3000});
				},
				error:function(ajaxobj){  
					layer.msg(ajaxobj, {icon: 0,time: 1000});
		    	}  
			});
		});
	}
	function formatTask(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnTask(\''+rowObject.taskVo.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.taskVo.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
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