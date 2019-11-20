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
					<li><a href="gridPage.do">用户体验</a></li>
					<li><strong id="mesg">列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-primary" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						<a class="btn btn-primary" href="javascript:void(0);" onclick="openEditPage('edit.do');">新增</a>
						<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
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
	<%@ include file="../../include/grid_page.jsp"%>
	<link href="${basePath}h/js/plugins/jquery-ztree/3.5.24/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css" />
	<script src="${basePath}h/js/plugins/jquery-ztree/3.5.24/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script src="${basePath}ext/js/tree.js" type="text/javascript"></script>

	<script>
	 
	$(function() {
		var url = 'gridData.do';
		var editurl='gridEdit.do';
		var colNames = ['问题描述','模块','提问人','提问时间','排序','操作'];
		var colModel = [ 
			{
				name : 'title',
				index : 'title',
				width : 500,
				frozen : true,
				resizable : false,
				editable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'moduleName',
				index : 'moduleName',
				resizable : false,
				editable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'userName',
				index : 'userName',
				resizable : false,
				editable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'date',
				index : 'date',
				resizable : false,
				editable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'sort',
				index : 'sort',
				width : 80,
				editable : true,
				searchoptions : {
					sopt : ['ne']
				}
			},{
				name : 'act',
				index : 'act',
				width :70,
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
				var cl = ids[i];
				be = '<a class="btn btn-primary btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
	</script>
</body>
</html>