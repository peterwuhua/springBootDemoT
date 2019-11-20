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
					<li><a>工作查阅</a></li>
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
	<%@ include file="../../include/grid_page.jsp"%>
	<script>

	function fnShow(id){
		var index = layer.open({
			title:'工作查阅',	
			type: 2,
			area: ['800px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/office/workReportView/show.do?id='+id
		});
	}
	function fnDel(id){
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			$("#ids").val(id);
			$("#listForm").attr("action","delete.do");
			$("#listForm").submit();
		});
	}
	
	$(function() {
		var url = 'gridData.do';
		var editurl='gridEdit.do';
  		var colNames = ['工作名称','汇报部门','汇报人','汇报日期','附件','状态','反馈内容','操作'];
		var colModel = [ 
			 {
				name : 'name',
				index : 'name',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'depart',
				index : 'depart',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'person',
				index : 'person',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'reportDate',
				index : 'reportDate',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'filename',
				index : 'filename',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'wpStatus',
				index : 'wpStatus',
				width : 100,
				sortable : false,
				search : false
			},{
				name : 'fkContent',
				index : 'fkContent',
				width : 150,
				sortable : false,
				search : false
			},{
				name : 'act',
				index : 'act',
				width : 150,
				title : false,
				search : false,
				sortable : false
			}];
		gridInitMin(url, colNames, colModel,false);
		});
		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				var rowData = jQuery("#listTable").jqGrid("getRowData",cl);	
				ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+cl+'\')">查看</a>';
				if (rowData.wpStatus == '未反馈') //工作报告状态
					{
					ce += ' <a class="btn btn-outline btn-success btn-xs" title="反馈" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">反馈</a>';
					}
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : ce 
				});
			}
		}
		
		
		
		
	</script>

</body>
</html>
