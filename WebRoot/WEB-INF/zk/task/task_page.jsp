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
						<li><a>质控计划</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-primary active" href="javascript:;">未提交的记录</a>
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPaged.do">已提交的记录</a>
					</div>
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
		var editurl='gridEdit.do';
		var colNames = ['','编号','标题','数量','考核项目','考核对象','制定人','制定日期','操作'];
		var colModel = [
			{
				name : 'isBack',
				index : 'isBack',
				hidden : true,
				search : false,
			} , {
				name : 'no',
				index : 'no',
				width : 100,
				formatter:formatNo,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'title',
				index : 'title',
				width : 150,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'sampNum',
				index : 'sampNum',
				width : 50,
				search : false,
				sortable : false
			},{
				name : 'itemNames',
				index : 'itemNames',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'objNames',
				index : 'objNames',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'acceptName',
				index : 'acceptName',
				width : 80,
				search : false,
				sortable : false
			},{
				name : 'acceptDate',
				index : 'acceptDate',
				search : false,
				sortable : false
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
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">修改</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	function jqgridDelete(){
		var selectIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
		if(selectIds.length<1){
			layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
			return false;
		}
		layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
			$("#ids").val(selectIds);
			$("#listForm").attr("action","delete.do");
			$("#listForm").submit();
		});
	}
	function formatNo(cellValue,options,rowObject){
		var ops='';
		ops+='<a href="javascript:void();" onclick="fnShow(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.isBack == 1){
			ops+='<img alt="退回" src="/static/img/tuihui.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnShow(taskId,no){
		var index = layer.open({
			title:'质控计划【'+no+'】',	
			type: 2,
			area: ['60%','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/zk/task/show.do?id='+taskId
		});
	}
	</script> 
	
</body>
</html>
