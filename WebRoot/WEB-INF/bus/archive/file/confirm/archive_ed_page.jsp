<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../../include/css.jsp"%>
</head>
<body class="gray-bg">
	  <div class="ibox float-e-margins">
		  <div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a a href="gridPage.do">档案</a></li>
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
				<%@ include file="../../../../include/status.jsp"%>
				<form action="gridPaged.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<input type="hidden" name="dirIds" id="dirIds" value="${vo.dirIds}">
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
	<%@ include file="../../../../include/js.jsp"%>
	<%@ include file="../../../../include/grid_page.jsp"%>
	<link href="${basePath}h/js/plugins/jquery-ztree/3.5.24/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css"/>
	<script src="${basePath}h/js/plugins/jquery-ztree/3.5.24/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script src="${basePath}ext/js/tree.js" type="text/javascript"></script>

	<script>
	function fnEdit(){
		openEditPage('edit.do?dirIds='+$('#dirIds').val());
	}
	$(function() {
		var dirIds = '${vo.dirIds}';
		var url ="";
		if(dirIds.length>0){
		 url = 'gridDatad.do?dirIds='+dirIds;
		}else{
			url = 'gridDatad.do';
		}
		var colNames = ['分类','编号','名称','归档人','归档时间','状态','操作'];
		var colModel = [
			{
				name : 'archiveTypeVo.name',
				index : 'archiveType.name',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'code',
				index : 'code',
				width : 100,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'title',
				index : 'title',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'userName',
				index : 'userName',
				sortable : false,
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'time',
				index : 'time',
				sortable : false,
				width :130,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'status',
				index : 'status',
				search : false,
				sortable : false,
				width :60
			},{
		   		name : 'act',
		   		index : 'act',
		   		width : 110,
		   		title : false,
		   		search : false,
		   		sortable : false,
		   	}];
		gridInitMin(url, colNames, colModel,false);
		});

	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var cl = ids[i];
			var rowData = jQuery("#listTable").jqGrid("getRowData",cl);
			ce = '<a class="btn btn-outline btn-info btn-xs" title="查看" href="javascript:openEditPage(\'show.do?id='+ids[i]+'\')">查看</a>';	
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : ce
			});
		}
	}

	
	</script> 
	
</body>
</html>
