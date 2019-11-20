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
						<li><a>现场踏勘</a></li>
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
				<form action="gridPaged.do" method="post" id="listForm">
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
		var colNames = ['项目编号','受检单位','检测类型','样品名称','踏勘人','踏勘日期','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				formatter:formatNo,
				width : 110,
				fixed:true,
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
				width : 80,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				width : 110,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'tkUserName',
				index : 'tkUserName',
				width : 110,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'tkDate',
				index : 'tkDate',
				width : 110,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 260,
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
			var ce='',be='';
			be = '<a class="btn btn-outline btn-success btn-xs" title="进度" href="javascript:fnSelectProcess(\''+rData.id+'\',\''+rData.status+'\')">进度</a>';
			if(rData.sampType.indexOf('卫生')>=0){
				ce += '<a class="btn btn-outline btn-info btn-xs" title="查看" href="javascript:fnShowWl(\''+rData.id+'\');">物料单</a>';
				ce += ' <a class="btn btn-outline btn-info btn-xs" title="查看" href="javascript:fnShowDW(\''+rData.id+'\')">点位表</a>';
				ce += ' <a class="btn btn-outline btn-info btn-xs" title="查看" href="javascript:fnShowDC(\''+rData.id+'\')">调查表</a>';
				
			}
			ce += ' <a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+rData.id+'\',\''+rData.no+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be+' '+ce
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
	 
	function fnShow(id,no){
		parent.layer.open({
			title:'项目【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/projectTk/show4Surve.do?id='+id
		});
	}
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void();" onclick="fnShow1(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		if(rowObject.jj =='是'){
			ops+='<img alt="加急" src="/static/img/jiaji.png" style="height:22px;width:25px;">';
		}
		return ops;
	}
	function fnShow1(id,no){
		parent.layer.open({
			title:'项目详情【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/project/show.do?id='+id
		});
	}
	function formatTime(cellValue){
		var dateType = "";  
	    var date = new Date();  
	    date.setTime(cellValue);  
	    dateType += date.getFullYear();   //年  
	    dateType += "-" + getMonth(date); //月   
	    dateType += "-" + getDay(date);   //日  
		return dateType;
	}
	//返回 01-12 的月份值   
	function getMonth(date){  
	    var month = "";  
	    month = date.getMonth() + 1; //getMonth()得到的月份是0-11  
	    if(month<10){  
	        month = "0" + month;  
	    }  
	    return month;  
	}  
	//返回01-30的日期  
	function getDay(date){  
	    var day = "";  
	    day = date.getDate();  
	    if(day<10){  
	        day = "0" + day;  
	    }  
	    return day;  
	}
	function fnShowDW(id){
		POBrowser.openWindow('${basePath}bus/projectTk/showDW.do?id='+id,'width=1200px;height=800px;');
	}
	function fnShowWl(id){
		POBrowser.openWindow('${basePath}bus/projectTk/showWl.do?id='+id,'width=1200px;height=800px;');
	}
	function fnShowDC(id){
		POBrowser.openWindow('${basePath}bus/projectTk/showDC.do?id='+id,'width=1200px;height=800px;');
	}
	</script>
</body>
</html>