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
						<li><a>报告复核</a></li>
						<li><strong id="mesg">已提交列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
					<div class="btn-group">
						<a type="button" class="btn btn-xs btn-outline btn-default" href="gridPage.do">未提交的记录</a>
						<a type="button" class="btn btn-xs btn-success active" href="javascript:;">已提交的记录</a>
					</div>
				</div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
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
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>

	<script>
	function fnEdit(){
		location.href='edit.do?id='+$('#id').val();
	}
	$(function() {
		var url ="";
		url = 'gridDatad.do';
		var colNames = ['报告编号','任务编号','客户名称','检测性质','样品类别','样品名称','复核时间','要求完成日期','操作'];
		var colModel = [ 
			{
				name : 'reportNo',
				index : 'reportNo',
				width :250,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskVo.no',
				index : 'task.no',
				width :120,
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
			}, {
				name : 'taskType',
				index : 'taskType',
				sortable : false,
				width :80,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sampTypeName',
				index : 'sampTypeName',
				sortable : false,
				width :100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sampName',
				index : 'sampName',
				sortable : false,
				width :150,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'reptDate',
				index : 'reptDate',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'finishDate',
				index : 'finishDate',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 110,
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
			be = '<a class="btn btn-outline btn-success btn-xs" title="进度" href="javascript:fnSelectProcess(\''+rData.taskVo.id+'\',\''+rData.status+'\')">进度</a>';
			ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+rData.id+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be+' '+ce
			});
		}
	}
	//进度 弹出层
	function fnSelectProcess(id,status){
		layer.open({
			  type: 2,
			  title: false,
			  closeBtn: 1, //不显示关闭按钮
			  fix: false, //不固定
			  shadeClose: true,
			  anim:5,
			  area: ['1000px','500px'],
			  content: '${basePath}bus/progress/show.do?busId='+id+'&busType='+encodeURI(status)
		});
	}
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnShowScheme(\''+rowObject.taskVo.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.taskVo.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnShowScheme(id,no){
		parent.layer.open({
			title:'任务单【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/task/show.do?id='+id
		});
	}
	function fnShow(id){
		var index = layer.open({
			title:'报告详情',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/reportAudit/show.do?id='+id
		});
	}
	</script>
</body>
</html>