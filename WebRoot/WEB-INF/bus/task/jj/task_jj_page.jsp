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
						<li><a>样品交接</a></li>
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
	$(function() {
		var url = 'gridData.do';
		var colNames = ['任务编号','受检单位','样品名称','检测类型','样品来源','采/送样人员','采/送样日期','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				width : 120,
				fixed:true,
				formatter:formatTask,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custVo.custName',
				index : 'cust.custName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskType',
				index : 'taskType',
				width : 100,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'source',
				index : 'source',
				width : 100,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'cyName',
				index : 'cyName',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'cyDate',
				index : 'cyDate',
				width : 130,
				fixed:true,
				sortable : false,
				search:false
			},{
				name : 'act',
				index : 'act',
				width : 110,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
		gridInitMin(url, colNames, colModel,false);
	});
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
			ce = '<a class="btn btn-outline btn-info btn-xs" title="打印" href="javascript:fnPrint(\''+ids[i]+'\')">标签</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : ce+' '+be
			});
		}
	}
	function formatTask(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnTask(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.jj =='是'){
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
	//打印
	function fnPrint(id){
		parent.layer.open({
			title:'条码打印',	
			type: 2,
			 area: ['900px', '500px'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}bus/taskJj/print.do?id='+id
		});
	}
	</script>
</body>
</html>