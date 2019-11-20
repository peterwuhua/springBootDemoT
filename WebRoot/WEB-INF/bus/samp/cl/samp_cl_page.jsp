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
						<li><a href="gridPage.do">余样处理记录</a></li>
						<li><strong id="mesg">列表</strong></li>
					</ol>
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
		var url ="";
		url = 'gridData.do';
		var colNames = ['任务编号','样品编号','客户名称','处理人','处理日期','处理方式','备注','操作'];
		var colModel = [ 
			{
				name : 'taskVo.no',
				index : 'task.no',
				width : 90,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampCode',
				index : 'sampCode',
				width : 90,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custVo.custName',
				index : 'cust.custName',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'dealUser',
				index : 'dealUser',
				width : 70,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'dealDate',
				index : 'dealDate',
				width : 70,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'dealRequest',
				index : 'dealRequest',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'dealRemark',
				index : 'dealRemark',
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
		gridInitMin(url, colNames, colModel,false);
	});
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:openEditPage(\'show.do?id='+ids[i]+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	</script>
</body>
</html>