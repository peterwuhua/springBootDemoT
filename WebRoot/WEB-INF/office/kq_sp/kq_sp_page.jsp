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
						<li><a>总经理审核</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-success active" href="javascript:;">未提交的记录</a>
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPaged.do">已提交的记录</a>
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
		var url = 'gridData.do';
		var colNames = ['编号','类型','部门','申请人','开始时间','截止时间','描述','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				width : 100,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'type',
				index : 'type',
				sortable : false,
				width : 80,
				fixed:true,
				stype : 'select',
				searchoptions : {
					sopt : ['eq'],
					value:{'':'全部','事假':'事假','婚假':'婚假','病假':'病假','丧假':'丧假','哺乳假':'哺乳假','陪产假':'陪产假','产假':'产假','外出':'外出'}
				}
			}, {
				name : 'deptName',
				index : 'deptName',
				width : 100,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'userName',
				index : 'userName',
				width : 80,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'startTime',
				index : 'startTime',
				width : 140,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'endTime',
				index : 'endTime',
				width : 140,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'content',
				index : 'content',
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
		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">编辑</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
	</script>
</body>
</html>
