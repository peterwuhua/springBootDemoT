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
						<a type="button" class="btn btn-xs btn-success active" href="javascript:;">未提交的记录</a>
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPaged.do">已提交的记录</a>
					</div>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);"
							onclick="jqgridReload();">刷新</a> <a class="btn btn-primary"
							href="javascript:;" onclick="openEditPage('edit.do')">新增</a> <a
							class="btn btn-danger" href="javascript:void(0);"
							onclick="jqgridDelete();">删除</a>
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
		gridInitMin(url, colNames, colModel,true);
		});
		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
		/**
		 * 删除
		 */
		function jqgridDelete(){
			var selectIds = getSelectIds();
			if(selectIds.length<1){
				layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
				return false;
			}
			layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$("#ids").val(selectIds);
				$("#listForm").attr("action","delete.do");
				$("#listForm").submit();
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
