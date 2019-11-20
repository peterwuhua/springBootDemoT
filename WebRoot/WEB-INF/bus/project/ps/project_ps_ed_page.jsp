<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
.rowBG{
   color:#ed5565;
}
</style>
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
         <!--PageOffice.js -->
     <script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
	<script>
	 
	$(function() {
		var url = 'gridDatad.do';
		var colNames = ['项目编号','受检单位','检测类型','样品名称','样品来源','是否评价','评审人','评审日期','操作'];
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
				width :80,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
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
				name : 'psName',
				index : 'psName',
				width : 120,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'psDate',
				index : 'psDate',
				width : 140,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 180,
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
			de = '<a class="btn btn-outline btn-info btn-xs" title="下载" href="javascript:fnFile(\''+rData.id+'\')">评审记录表</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : de+' '+be+' '+ce
			});
		}
	}
	//进度 弹出层
	function fnSelectProcess(id,status){
		parent.layer.open({
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
		var ops='<a href="javascript:void();" onclick="fnShowProject(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnShowProject(id,no){
		parent.layer.open({
			title:'项目详情【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/project/show.do?id='+id
		});
	}
	function fnShow(id){
		parent.layer.open({
			title:'评审信息',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/projectPs/show.do?id='+id
		});
	}
	function fnFile(id){
		POBrowser.openWindow('${basePath}bus/projectPs/showWord.do?id='+id,'width=1200px;height=800px;');
	}
	</script>
</body>
</html>