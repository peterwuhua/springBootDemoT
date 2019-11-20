<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
.red{
    color:#FF9966;
}
.blue{
    color:blue;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>现场踏勘</a></li>
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
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>（<font color="red">红色</font>表示已超期；<font color="blue">蓝色</font>表示3天以内，黑色表示3天以上；）
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
		var colNames = ['','','项目编号','受检单位','检测类型','样品名称','立项日期','拟完成日期','指派日期','操作'];
		var colModel = [ 
			{
				name : 'color',
				index : 'color',
				search : false,
				hidden:true
			},{
				name : 'id',
				index : 'id',
				search : false,
				title:false,
				hidden:true
			},{
				name : 'no',
				index : 'no',
				formatter:formatNo,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custVo.custName',
				index : 'custName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskType',
				index : 'taskType',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'rdate',
				index : 'rdate',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'finishDate',
				index : 'finishDate',
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'tkDate',
				index : 'tkDate',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 100,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
		gridInitMin(url, colNames, colModel,false);
	});
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			be = '<a class="btn btn-outline btn-success btn-xs" title="编辑" href="javascript:openEditPage(\'edit4Survey.do?id='+ids[i]+'\')">编辑</a>';
			ce = '<a class="btn btn-outline btn-info btn-xs" title="任务转移" href="javascript:fnEdit(\'edit4Zy.do?projectVo.id='+ids[i]+'\')">转移</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : ce+' '+be
			});
			jQuery('#'+ids[i]).addClass(rData.color);
		}
	}
	function fnEdit(url){
		layer.open({
			title:'任务转移',	
			type: 2,
			area: ['600px', '400px'],
			fix: false, //不固定
			maxmin: true,
			content:url,
			btn: ['确定','关闭'], //按钮
			yes: function(index, layero){
				var iframeWin = window[layero.find('iframe')[0]['name']];
				  iframeWin.submitSave();
			}
		});
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
	</script>
</body>
</html>