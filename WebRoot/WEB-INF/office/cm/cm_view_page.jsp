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
					<li><a>抄送查看</a></li>
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
	 
	$(function() {
		var url = 'gridData.do';
		var colNames = ['编号','类型','成本','工时',"申请人",'申请日期','操作'];
		var colModel = [ 
			{
				name : 'code',
				index : 'code',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'type',
				index : 'type',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'price',
				index : 'price',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'hours',
				index : 'hours',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'userName',
				index : 'userName',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'sdate',
				index : 'sdate',
				width : 100,
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
				be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+rData.id+'\',\''+rData.code+'\')">查看</a>';
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
				content: '/office/cmView/show.do?id='+id
			});
		}
	</script>

</body>
</html>
