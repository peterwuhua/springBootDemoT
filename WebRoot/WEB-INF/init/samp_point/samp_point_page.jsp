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
					<li><a href="gridPage.do">环境监测点位</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary" href="javascript:;" onclick="openEditPage('edit.do');">新增</a> 
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
	<script>
	 
	$(function() {
		var url = 'gridData.do';
		var editurl='gridEdit.do';
  		var colNames = ['监测点位','点位代码','样品名称','市县','频次','操作'];
		var colModel = [ 
			 {
				name : 'name',
				index : 'name',
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'code',
				index : 'code',
				width : 80,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				width : 80,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'sx',
				index : 'sx',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'pc',
				index : 'pc',
				width : 100,
				formatter:fmtStr,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
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
				var cl = ids[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
		function fmtStr(cellValue,options,rowObject){
			return cellValue+rowObject.pcUnit;
		}
	</script>

</body>
</html>
