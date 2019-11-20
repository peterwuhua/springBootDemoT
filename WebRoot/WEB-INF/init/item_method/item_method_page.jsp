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
					<li><a href="gridPage.do">采样规范</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="page.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-primary" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
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
			var colNames = ['操作','类型','检测项目', '方法标准', '采样容器', '采样设备','运输、存储条件','存储时间(h)', '采样流量', '采样时长', '采样体积', '备注'];
			var colModel = [{
				name : 'act',
				index : 'act',
				width : 60,
				title : false,
				search : false,
				frozen : true,
				resizable : false,
				sortable : false,
			},{
				name : 'itemVo.sampTypeNames',
				index : 'item.sampTypeNames',
				width : 100,
				frozen : true,
				resizable : false,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			},{
				name : 'itemVo.name',
				index : 'item.name',
				width : 100,
				frozen : true,
				resizable : false,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'methodVo.name',
				index : 'method.name',
				width :300,
				frozen : true,
				resizable : false,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'ctName',
				index : 'ctName',
				width : 200,
				resizable : true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'cyAppName',
				index : 'cyAppName',
				sortable : false,
				width : 200,
				resizable : true,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}, {
				name : 'cctj',
				index : 'cctj',
				width : 200,
				resizable : true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			},
			{
				name : 'bcsj',
				index : 'bcsj',
				width : 100,
				resizable : true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			},{
				name : 'cyll',
				index : 'cyll',
				width : 100,
				sortable : false,
				resizable : true,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			},{
				name : 'cysc',
				index : 'cysc',
				width : 100,
				sortable : false,
				resizable : true,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			},{
				name : 'cytj',
				index : 'cytj',
				width : 100,
				sortable : false,
				resizable : true,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			},{
				name : 'remark',
				index : 'remark',
				width : 100,
				sortable : false,
				resizable : true,
				searchoptions : {
					sopt : [ 'cn' ]
				}
			}];
			gridInit(url, colNames, colModel, '', 20,'#pager',false,true);
		});
		function loadComplete(data) {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var rData = data.datas[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ rData.id + '\')">编辑</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
	</script>
</body>
</html>
