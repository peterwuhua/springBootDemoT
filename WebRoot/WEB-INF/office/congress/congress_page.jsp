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
					<li><a>会务申请</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div>
						<a class="btn btn-success" href="javascript:void(0);"
							onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary"
							href="javascript:;" onclick="openEditPage('edit.do');">新增</a> 
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
			title:'会务信息',	
			type: 2,
			area: ['800px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/office/congressAudit/show.do?id='+id
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
  		var colNames = ['会议名称','主讲人','会议地点','开始时间','结束时间','申请人','申请日期','状态','操作'];
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
				name : 'zjr',
				index : 'zjr',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'address',
				index : 'address',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'beginTime',
				index : 'beginTime',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'endTime',
				index : 'endTime',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'sqr',
				index : 'sqr',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'supportDate',
				index : 'supportDate',
				width : 100,
				sortable : false,
				search : false
			},{
				name : 'fstatus',
				index : 'fstatus',
				width : 100,
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
		gridInitMin(url, colNames, colModel,true);
		});
		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				var rowData = jQuery("#listTable").jqGrid("getRowData",cl);	
				ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+cl+'\')">查看</a>';
				if (rowData.fstatus == '已保存' || rowData.fstatus =='已拒绝') //会务状态
					{
					ce += ' <a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
					ce += ' <a class="btn btn-outline btn-success btn-xs" title="删除" href="javascript:fnDel(\''+cl+'\')">删除</a>';
					}
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : ce 
				});
			}
		}
		
		
		
		
	</script>

</body>
</html>
