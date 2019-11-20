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
					<li><a>容许浓度</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary" href="javascript:openEditPage('edit.do')">新增</a> 
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
			var colNames = [ '检测项目','中文名', '化学文摘号', 'MAC', 'PC-TWA', 'PC-STEL','最大超限倍数','备注','操作' ];
			var colModel = [
			{
				name : 'itemVo.name',
				index : 'item.name',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'name',
				index : 'name',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'cas',
				index : 'cas',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'mac',
				index : 'mac',
				width : 80,
				fixed : true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'pctwa',
				index : 'pctwa',
				width : 80,
				fixed : true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'pcstel',
				index : 'pcstel',
				width : 80,
				fixed : true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'cxbs',
				index : 'cxbs',
				width : 90,
				fixed : true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'remark',
				index : 'remark',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'act',
				index : 'act',
				width : 70,
				fixed : true,
				title : false,
				search : false,
				sortable : false,
			} ];
			gridInitMin(url, colNames, colModel, true);
		});
		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var rData = $('#listTable').jqGrid('getRowData', ids[i]);
				var tt = rData.taskType;
				if (rData.type != '') {
					tt += "(" + rData.type + ")"
				}
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ ids[i] + '\')">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be,
					taskType : tt
				});
			}
		}
		function formatNo(cellValue, options, rowObject) {
			var ops = '';
			ops += '<a href="javascript:void(0);" onclick="fnShow(\''
					+ rowObject.id + '\',\'' + cellValue + '\');">' + cellValue
					+ '</a>';
			if (rowObject.isBack == 1) {
				ops += '<img alt="退回" src="/static/img/tuihui.png" style="height:22px;width:25px;">';
			}
			return ops;
		}
		function formatOn(cellValue, options, rowObject) {
			if (cellValue == '') {
				return '全部';
			} else {
				return cellValue;
			}
		}
		function formatRn(cellValue, options, rowObject) {
			if (cellValue == 'Y') {
				return '是';
			} else {
				return '否';
			}
		}
	</script>
</body>
</html>
