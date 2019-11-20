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
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>文件登记</a></li>
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
		var url = 'gridDatad.do';
		var editurl='gridEdit.do';
		var colNames = ['编号','文件名称','文件来源','保密等级','上传日期','附件','操作'];
		var colModel = [ 
			{
				name : 'code',
				index : 'code',
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
				name : 'date',
				index : 'date',
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
				width : 70,
				fixed:true,
				title : false,
				search : false,
				sortable : false
			}];
		gridInitMin(url, colNames, colModel,false);
		});
		function loadComplete(data) {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var rData = data.datas[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+rData.id+'\',\''+rData.no+'\')">查看</a>';
				jQuery("#listTable").jqGrid('setRowData', rData.id, {
					act : be
				});
			}
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
