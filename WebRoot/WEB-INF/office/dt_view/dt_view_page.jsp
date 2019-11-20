<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a>文件查看</a></li>
						<li><strong>列表</strong></li>
					</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);"
							onclick="jqgridReload();">刷新</a> 
					</div>
					<div class="jqGrid_wrapper">
						<table id="listTable"></table>
						<div id="pager"></div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
				<!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
	<%@ include file="../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = 'gridData.do';
		var colNames = ['编号','文件名称','文件来源','保密等级','下发日期','附件','操作'];
		var colModel = [ 
			{
				name : 'code',
				index : 'code',
				formatter:showTask,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'title',
				index : 'title',
				width : 100,
				sortable : false,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'source',
				index : 'source',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'dj',
				index : 'dj',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'sendTime',
				index : 'sendTime',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'fileName',
				index : 'fileName',
				sortable : false,
				formatter:showFile,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 110,
				fixed:true,
				title : false,
				search : false,
				sortable : false
			}];
		gridInitMin(url, colNames, colModel,false);
		});
		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
				ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:openEditPage(\'show.do?id='+ids[i]+'\')">查看</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be+' '+ce
				});
			}
		}
		function showTask(cellValue,options,rowObject){
			return '<a href="javascript:;" onclick="fnShow(\''+rowObject.id+'\',\''+cellValue+'\')" >'+cellValue+'</a>';
		}
		function fnShow(id,no){
			var index = layer.open({
				title:'查看【'+no+'】',	
				type: 2,
				area: ['800px','400px'],
				fix: false, //不固定
				maxmin: true,
				content: '/office/dt/show.do?id='+id
			});
		}
		function showFile(cellValue,options,rowObject){
			return '<a href="javascript:;" onclick="fnShowFile(\''+rowObject.id+'\')" >'+cellValue+'</a>';
		}
		function fnShowFile(id){
			POBrowser.openWindow('${basePath}office/dt/open.do?id='+id,'width=1200px;height=800px;');
		}
	</script>
</body>
</html>
