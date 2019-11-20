<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
.red{
    color:red;
}
.blue{
    color:blue;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>数据复核</a></li>
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
						<a class="btn btn-primary" href="javascript:;" onclick="fnEdit();"><i class="fa fa-pencil-square-o" aria-hidden="true"> 批量审核</i></a>
						<span>（注：<font color="red">红色</font>：距离完成时间不足2小时或超时，<font color="blue">蓝色</font>：距离要求完成时间不到12小时）</span>
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
	function checkItem() {
		var ids = getSelectIds();
		var id ="";
		var flag=true;
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			var taskId=rData['taskVo.id'];
			if(id==""){
				id=taskId;
			}else if(id!=taskId){
				flag=false;
				break;
			}
		}
		return flag;
	}
	function fnEdit(){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择要录入的记录', {icon: 0,time: 3000});
			return false;
		}else{
			$("#ids").val(selectIds);
		}
		if(!checkItem()){
			layer.msg('不同任务的项目禁止批量操作');
			
			return false;
		}
		openEditPage('edit.do?ids='+selectIds);
	}
	$(function() {
		var url ="";
		url = 'gridData.do';
		var colNames = ['','','检测项目','任务编号','检测性质','样品名称','样品数量','检测人','检测时间','上报日期','操作'];
		var colModel = [ 
			{
				name : 'color',
				index : 'color',
				search : false,
				hidden:true
			},{
				name : 'itemId',
				index : 'itemId',
				hidden:true
			},{
				name : 'itemName',
				index : 'itemName',
				sortable : false,
				width : 90,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'taskVo.no',
				index : 'task.no',
				formatter:formatTask,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.taskType',
				index : 'taskVo.taskType',
				width : 90,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sampName',
				index : 'sampName',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampNum',
				index : 'sampNum',
				
				width : 120,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'testMan',
				index : 'testMan',
				width : 100,
				sortable : false,
				search : false
			},{
				name : 'testTime',
				index : 'testTime',
				width : 130,
				sortable : false,
				search : false
			},{
				name : 'sbDate',
				index : 'sbDate',
				width : 100,
				sortable : false,
				search : false
			},{
				name : 'act',
				index : 'act',
				width : 70,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
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
			jQuery('#'+ids[i]).addClass(rData.color);
		}
	}
	function formatTask(cellValue,options,rowObject){
		var ops='<a href="javascript:void();" onclick="fnTask(\''+rowObject.taskVo.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
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