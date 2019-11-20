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
						<li><a>报价查看</a></li>
						<li><strong>列表</strong></li>
					</ol>
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
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script>
	 
	$(function() {
		var url = 'gridData.do';
		var colNames = ['编号单','客户名称','联系人','电话','合同金额','报价日期','有效期至','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				width : 130,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custName',
				index : 'custName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custUser',
				index : 'custUser',
				width : 80,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'custMobile',
				index : 'custMobile',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'htPrice',
				index : 'htPrice',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'bdate',
				index : 'bdate',
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'yxq',
				index : 'yxq',
				width : 100,
				fixed:true,
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
			be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+ids[i]+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	
	function fnShow(id){
		var index = layer.open({
			title:'报价信息',	
			type: 2,
			area: ['800px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/cus/budgetView/show.do?id='+id
		});
	}
	</script>
</body>
</html>