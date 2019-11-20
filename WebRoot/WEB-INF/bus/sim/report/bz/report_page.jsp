<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../../include/css.jsp"%>
<%@ include file="../../../../include/status.jsp"%>

</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>报告编制</a></li>
						<li><strong id="mesg">列表</strong></li>
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
	<%@ include file="../../../../include/js.jsp"%>
	<%@ include file="../../../../include/grid_page.jsp"%>

	<script>
	function fnEdit(){
		location.href='edit.do?id='+$('#id').val();
	}
	$(function() {
		var url ="";
		url = 'gridData.do';
		var editurl='gridEdit.do';
		var colNames = ['','项目编号','项目名称','项目类型','委托单位','拟完成日期','项目负责人','操作'];
		var colModel = [ 
			{
				name : 'isBack',
				index : 'isBack',
				hidden:true,
				search : false
			},{
				name : 'no',
				index : 'no',
				width : 150,
				fixed:true,
				formatter:formatTh,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				width : 150,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'itemType',
				index : 'itemType',
				width : 150,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custVo.custName',
				index : 'cust.custName',
				width : 180,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'finishDate',
				index : 'finishDate',
				width : 120,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'userName',
				index : 'userName',
				width : 120,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 100,
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
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnShow(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function formatTh(cellValue,options,rowObject){
		var ops=cellValue;
		if(rowObject.isBack == 'Y'){
			ops+='<img alt="退回" src="/static/img/tuihui.png" style="height:22px;width:25px;">';
		}
		if(rowObject.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnShow(id,no){
		parent.layer.open({
			title:'任务单【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/sim/project/show.do?id='+id
		});
	}
	</script>
</body>
</html>