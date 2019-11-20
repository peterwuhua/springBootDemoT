<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../../include/css.jsp"%>
<%@ include file="../../../../include/status.jsp"%>

</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a>报告审核</a></li>
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
	<%@ include file="../../../../include/js.jsp"%>
	<%@ include file="../../../../include/grid_page.jsp"%>

	<script>
	function fnEdit(){
		location.href='edit.do?id='+$('#id').val();
	}
	$(function() {
		var url ="";
		url = 'gridDatad.do';
		var colNames = ['','项目编号','项目名称','项目类型','委托单位','拟完成日期','项目负责人','当前进度','操作'];
		var colModel = [ 
			{
				name : 'isBack',
				index : 'isBack',
				hidden:true,
				search : false
			},{
				name : 'no',
				index : 'no',
				width : 150,
				fixed:true,
				formatter:formatNo,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				width : 150,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'itemType',
				index : 'itemType',
				width : 150,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'custVo.custName',
				index : 'cust.custName',
				width : 180,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'finishDate',
				index : 'finishDate',
				width : 120,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'userName',
				index : 'userName',
				width : 120,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'status',
				index : 'status',
				width : 80,
				fixed:true,
				formatter:formatStatus,
				sortable : false,
				search : false,
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
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="进度" href="javascript:fnSelectProcess(\''+rData.id+'\',\''+rData.status+'\')">进度</a>';
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
			  content: '${basePath}bus/progress/show4simple.do?busId='+id+'&busType='+encodeURI(status)
		});
	}
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnShowScheme(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnShowScheme(id,no){
		parent.layer.open({
			title:'项目编号【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/sim/project/show.do?id='+id
		});
	}
	function fnShow(id){
		parent.layer.open({
			title:'报告详情',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/sim/project/reptSh/show.do?id='+id
		});
	}
	function formatStatus(cellValue,options,rowObject){
		var ops=cellValue;
		if(ops=="finish"){
			ops = "完成";
		}else if(ops=="stop"){
			ops = "终止";
		}else if(ops=="XM_00"){
			ops = "项目立项";
		}else if(ops=="XM_10"){
			ops = "合同评审";
		}else if(ops=="XM_20"){
			ops = "合同签订";
		}else if(ops=="XM_30"){
			ops = "任务登记";
		}else if(ops=="XM_40"){
			ops = "派发任务";
		}else if(ops=="XM_50"){
			ops = "现场踏勘";
		}else if(ops=="XM_55"){
			ops = "方案编制";
		}else if(ops=="XM_60"){
			ops = "报告编制";
		}else if(ops=="XM_70"){
			ops = "报告评审";
		}else if(ops=="XM_80"){
			ops = "提交备案";
		}else if(ops=="XM_90"){
			ops = "备案归档";
		}
		
		return ops;
	}
	</script>
</body>
</html>