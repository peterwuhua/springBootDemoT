<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><a href="gridPage.do">满意度调查</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
				</div>
			</div>
			<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div class="col-sm-7">
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" href="javascript:void(0);" onclick="openEditPage('edit.do');">新增</a> 
					<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
				</div>
				<div class="col-sm-5"></div>
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
		var colNames = ['客户编号 ','客户名称','调查人 ','调查日期','联系电话','评分','意见建议','录入人','操作'];
		var colModel = [
			{
				name : 'customerVo.code',
				index : 'customerVo.code',
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'customerVo.name',
				index : 'customer.name',
				width : 200,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},  {
				name : 'custUser',
				index : 'custUser',
				width : 100,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'cdate',
				index : 'cdate',
				sortable : false,
				width : 90,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'custMobile',
				index : 'custMobile',
				sortable : false,
				width : 90,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'pf',
				index : 'pf',
				sortable : false,
				width : 70,
				fixed:true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'resean',
				index : 'resean',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'userName',
				index : 'userName',
				sortable : false,
				fixed:true,
				width : 70,
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
				sortable : false,
			}];
			gridInitMin(url, colNames, colModel,true);
		});

		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
	</script> 
	
</body>
</html>
