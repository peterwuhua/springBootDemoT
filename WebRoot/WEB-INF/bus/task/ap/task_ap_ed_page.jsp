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
						<li><a>采样安排</a></li>
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
		var colNames = ['','任务编号','受检单位','样品名称','检测类型','采样小组人员','开始采样日期','采样结束日期','操作'];
		var colModel = [ 
			{
				name : 'status',
				index : 'status',
				hidden:true,
				title:false,
				search:false
			},{
				name : 'no',
				index : 'no',
				formatter:formatTask,
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
				name : 'sampName',
				index : 'sampName',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'taskType',
				index : 'taskType',
				width : 100,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'cyName',
				index : 'cyName',
				width : 80,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'cyDate',
				index : 'cyDate',
				width : 120,
				fixed:true,
				search:false
			},{
				name : 'cyEndDate',
				index : 'cyEndDate',
				width : 120,
				fixed:true,
				search:false
			},{
				name : 'act',
				index : 'act',
				width : 220,
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
			if(rData.status=='现场采样'){
				be += '  <a class="btn btn-outline btn-success btn-xs" title="更新"  href="javascript:openEditPage(\'edited.do?id='+ids[i]+'\')">更新</a>';
			}
			ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:openEditPage(\'show.do?id='+rData.id+'\')">查看</a>';
			de = '<a class="btn btn-outline btn-info btn-xs" title="下载" href="javascript:fnFile(\''+rData.id+'\')">采样计划单</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be+' '+ce+' '+de
			});
		}
	}
	
	function fnFile(id){
		POBrowser.openWindow('${basePath}bus/taskAp/editWord.do?id='+id,'width=1200px;height=800px;');
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
	function fnShow(id){
		var index = layer.open({
			title:'采样交接详情',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/taskJj/show.do?id='+id
		});
	}
	function formatTask(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnTask(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnTask(id,no){
		parent.layer.open({
			title:'任务【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/task/show.do?id='+id
		});
	}
	//打印
	function fnPrint(id){
		var list=[];
		$.ajax({
			url:'${basePath}bus/taskJj/ajaxGetSampList.do?id='+id,
			dataType:"json",
			type:"post",
			async:false,
			success: function(data){
				for(var i=0;i<data.length;i++){
					var obj={};
					obj.sampCode=data[i].sampCode;
					obj.sampName=data[i].sampName;
					obj.itemName=data[i].itemNames;
					obj.saveAddress=data[i].saveAddress;
					list.push(obj);
				}
			},
			error:function(ajaxobj){
		    }  
		});
		var	url='${basePath}bus/taskJj/print.do';
		parent.layer.open({
			title:'条码打印',	
			type: 2,
			 area: ['80%', '90%'],
			fix: false, //不固定
			maxmin: true,
			content: url,
			success: function(layero, index){
				var iframeWin=layero.find('iframe')[0];
				iframeWin.contentWindow.fnSelect(list);
			}
		});
	}
	</script>
</body>
</html>