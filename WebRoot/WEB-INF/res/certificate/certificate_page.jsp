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
					<li><a>资质管理</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary" href="javascript:;" onclick="openEditPage('edit.do');">新增</a> 
						<a class="btn btn-danger" href="javascript:;" onclick="jqgridDelete();">删除</a> 
						<div class="btn-group">
							<button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">
								导出 <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a href="javascript:;" onclick="jqgridExport('res-certificate-export.xls','资质管理信息列表.xls',0)">全部数据</a></li>
								<li><a href="javascript:;" onclick="jqgridExport('res-certificate-export.xls','资质管理信息列表.xls',1)">选中数据</a></li>
							</ul>
						</div>
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
		var colNames = ['','姓名 ','证书名称 ','证书编号','发证机关','证件类别','起始日期','结束日期','操作'];
		var colModel = [
			{
				name : 'userId',
				index : 'userId',
				title : false,
				hidden: true,
				search:false
			},{
				name : 'userName',
				index : 'userName',
				formatter:formatNo,
				width : 120,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'name',
				index : 'name',
				width : 150,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'code',
				index : 'code',
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'unit',
				index : 'unit',
				sortable : false,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'type',
				index : 'type',
				sortable : false,
				width : 120,
				searchoptions : {
					sopt : [ 'cn']
				}
			},  {
				name : 'startTime',
				index : 'startTime',
				sortable : false,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'endTime',
				index : 'endTime',
				sortable : false,
				width : 150,
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
				sortable : false
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
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnShow(\''+rowObject.userId+'\');">'+cellValue+'</a>';
		return ops;
	}
	function fnShow(id){
		var index = layer.open({
			title:'查看',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/sys/user/show.do?id='+id
		});
	}
	</script>
</body>
</html>
