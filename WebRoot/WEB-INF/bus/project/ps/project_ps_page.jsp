<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>合同评审</a></li>
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
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
						<a class="btn btn-danger" href="javascript:;" onclick="updateStop();">终止</a>
					</div>
					<div class="jqGrid_wrapper">
						<table id="listTable"></table>
						<div id="pager"></div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>
	<script>
	 
	$(function() {
		var url = 'gridData.do';
		var colNames = ['项目编号','受检单位','检测类型','样品名称','样品来源','是否评价','现场踏勘','立项日期','拟完成日期','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				width : 130,
				fixed:true,
				formatter:formatNo,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custVo.custName',
				index : 'cust.custName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskType',
				index : 'taskType',
				sortable : false,
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				sortable : false,
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'zsy',
				index : 'zsy',
				width : 80,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'pj',
				index : 'pj',
				width : 80,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'xctk',
				index : 'xctk',
				width : 80,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'rdate',
				index : 'rdate',
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'finishDate',
				index : 'finishDate',
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 130,
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
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">评审</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void();" onclick="fnShow(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnShow(id,no){
		parent.layer.open({
			title:'项目详情【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/project/show.do?id='+id
		});
	}
	function updateStop(){
		var selectIds = getSelectIds();
		if(selectIds.length<1){
			layer.msg('请选择终止的记录', {icon: 0,time: 3000});
			return false;
		}
		layer.confirm('确认要终止吗?', {icon:3, title:'系统提示'}, function(index){
			$.ajax({ 
				url:"${basePath}bus/schemePs/update2Stop.do",
				data: {'ids':selectIds.join(',')},
				async:false,
				success: function(data){
					if(data.status=='success'){
						jqgridReload()
					}
					layer.msg(data.message, {icon: 0,time: 3000});
				},
				error:function(ajaxobj){  
					layer.msg(ajaxobj, {icon: 0,time: 1000});
		    	}  
			});
		});
	}
	</script>
</body>
</html>