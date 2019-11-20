<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
.rowBG{
   color:#ed5565;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>评审记录</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPage.do">未提交的记录</a>
						<a type="button" class="btn btn-xs btn-success active" href="javascript:;">已提交的记录</a>
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
		var colNames = ['编号','标题','部门','记录时间','记录人','操作'];
		var colModel = [ 
			{
				name : 'gsVo.no',
				index : 'gs.no',
				width : 120,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'gsVo.title',
				index : 'gs.title',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'gsVo.deptName',
				index : 'gs.deptName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'date',
				index : 'date',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'userName',
				index : 'userName',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 70,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
		gridInitAuto(url, colNames, colModel, '',10,'#pager',false)
	});
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+rData.id+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {act : be});
		}
	}
	  
	function fnShow(id){
		var index = layer.open({
			title:'记录单',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/qlt/gsRecord/show.do?id='+id
		});
	}
	</script>
</body>
</html>