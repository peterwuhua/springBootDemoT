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
						<li><a>补卡审核</a></li>
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
	<%@ include file="../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = 'gridDatad.do';
  		var colNames = ['部门','打卡人','打卡时间','描述','审核人','审核日期','审核结果','操作'];
		var colModel = [ 
			{
				name : 'deptName',
				index : 'deptName',
				width : 150,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'userName',
				index : 'userName',
				width : 150,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'date',
				index : 'date',
				width : 150,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'content',
				index : 'content',
				sortable : false,
				search : false,
			},{
				name : 'auditName',
				index : 'auditName',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'auditDate',
				index : 'auditDate',
				width : 120,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'auditResult',
				index : 'auditResult',
				width : 80,
				sortable : false,
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
				be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+rData.id+'\')">查看</a>';
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
				content: '/office/dgAudit/show.do?id='+id
			});
		}
	</script>

</body>
</html>
