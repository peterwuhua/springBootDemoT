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
						<li><a>入库管理</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPage.do">待出库的记录</a>
						<a type="button" class="btn btn-xs btn-success active" href="javascript:;">已出库的记录</a>
					</div>
				</div>
			</div>
			<div class="ibox-content">
				<form action="page.do" method="post" id="listForm">
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
		var url = 'gridData.do?status='+encodeURI('${vo.status}');
		var colNames = ['','仪器名称 ','仪器编号','入库人 ','入库时间','使用时长','仪器状态','状态','操作'];
		var colModel = [
			{
				name : 'equiptVo.spec',
				index : 'equipt.spec',
				title : false,
				hidden : true,
			},{
				name : 'equiptVo.name',
				index : 'equipt.name',
				formatter:formatName,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'equiptVo.no',
				index : 'equipt.no',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'useName',
				index : 'useName',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'useTime',
				index : 'useTime',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'duration',
				index : 'duration',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'appStatus',
				index : 'appStatus',
				sortable : false,
				searchoptions : {
					sopt : [ 'eq']
				}
			}, {
				name : 'status',
				index : 'status',
				sortable : false,
				searchoptions : {
					sopt : [ 'eq']
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
			gridInitMin(url, colNames, colModel,false);
		});
	
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			 be = '<a class="btn  btn-outline btn-success btn-xs" title="查看" href="javascript:;" onclick="fnShow(\''+rData.id+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', rData.id, {
				act : be
			});
		}
	}
	function formatName(cellValue,options,rowObject){
		return cellValue+' '+rowObject.equiptVo.spec;
	}
	function fnShow(id){
		layer.open({
			  type: 2,
			  title: '入库查看',
			  closeBtn: 1, //不显示关闭按钮
			  fix: false, //不固定
			  shadeClose: true,
			  anim:5,
			  area: ['800px','400px'],
			  content: '/res/equipmentIn/show.do?id='+id
		});
	}
	</script>
</body>
</html>
