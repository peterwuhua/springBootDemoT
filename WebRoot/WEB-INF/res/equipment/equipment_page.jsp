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
						<li><a>计量设备</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div>
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
					<a class="btn btn-primary" href="javascript:void(0);" onclick="openEditPage('edit.do');">新增</a> 
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
		var colNames = ['仪器编号 ','仪器名称','规格型号','出厂编号 ','保管科室','保管人','状态','操作'];
		var colModel = [
			{
				name : 'no',
				index : 'no',
				width : 120,
				formatter:fnShow,
				searchoptions : {
					sopt : [ 'cn']
				}
			},  {
				name : 'name',
				index : 'name',
				width : 120,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'spec',
				index : 'spec',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'code',
				index : 'code',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'deptName',
				index : 'deptName',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'keeper',
				index : 'keeper',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'status',
				index : 'status',
				sortable : false,
				width : 100,
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
	function fnShow(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="show(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		return ops;
	}
	function show(id,no){
		var index = layer.open({
			title:'仪器【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/res/equipment/show.do?id='+id
		});
	}
	</script> 
</body>
</html>
