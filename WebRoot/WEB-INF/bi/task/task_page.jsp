<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<style type="text/css">
.form-group{
	margin-bottom: 5px;
}
.form-group > label{
	height: 34px;
	padding-top: 10px;
	padding-left: 0px;
	padding-right: 0px;
}
.form-group > div{
	padding-left: 0px;
	padding-right: 0px;
}
.rowBG{
   color:#ed5565;
}
.ui-pg-selbox{
	width: 43px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm" class="form-horizontal m-t">
					<div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >任务编号：</label>
                          <div class="col-md-8" >
                              <input id="no" name="no" value="${vo.no}" type="text" class="form-control" placeholder="任务编号">
                          </div>
                    </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >客户名称：</label>
                          <div class="col-md-8" >
                              <input id="custName" name="custVo.custName" value="${vo.custVo.custName}" type="text" class="form-control" placeholder="客户名称">
                          </div>
                    </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >检测性质：</label>
                          <div class="col-md-8" >
                          	 <select id="taskType" name="taskType" class="form-control">
                          	 	<option value="">全部</option>
                          	 	<c:forEach items="${busList}" var="e" varStatus="v">
                          	 		<c:choose>
	                          	 		<c:when test="${vo.taskType==e.name}">	
	                          	 			<option value="${e.name}" selected="selected">${e.name}</option>
	                          	 		</c:when>
	                          	 		<c:otherwise>
	                          	 			<option value="${e.name}">${e.name}</option>
	                          	 		</c:otherwise>
	                          	 	</c:choose>
                          	 	</c:forEach>
                          	 </select>
                          </div>
                     </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >样品名称：</label>
                          <div class="col-md-8" >
                              <input id="sampName" type="text"  name="sampName" value="${vo.sampName}" class="form-control" placeholder="样品名称">
                          </div>
                     </div>
                      <div class="form-group col-md-4" id="data_5">
                          <label class="col-md-2 control-label" style="width: 81px;">受理日期：</label>
                         <div class="input-daterange input-group col-md-10" style="width:300px;">
                              <input id="startDate" type="text" class="form-control dateISO" name="startDate" placeholder="开始日期"/>
                              <span class="input-group-addon">到</span>
                              <input id="endDate" type="text" class="form-control dateISO" name="endDate" placeholder="截止日期"/>
                          </div>
					</div>
					<div class="form-group col-md-4">
                          <label class="col-md-3 control-label" >受理人员：</label>
                          <div class="col-md-6" >
                              <input id="userName" name="userName" value="${vo.userName}" type="text" class="form-control" placeholder="受理人员" >
                          </div>
                    </div>
					<div class="form-group col-md-3">
						<a class="btn btn-primary" href="javascript:void(0);" onclick="fnSearch();"><i class="fa fa-search" aria-hidden="true"></i> 查询</a>
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
		var colNames = ['','任务编号','客户名称','点位名称','样品名称','样品来源','受理日期','商定完成日期','检测性质','受理人员','当前进度','操作'];
		var colModel = [ 
			{
				name : 'type',
				index : 'type',
				 hidden:true,
				title:false,
				search : false,
			},{
				name : 'no',
				index : 'no',
				width : 120,
				fixed:true,
				search : false,
			},{
				name : 'custVo.custName',
				index : 'cust.custName',
				sortable : false,
				search : false
			},{
				name : 'pointNames',
				index : 'pointNames',
				sortable : false,
				search : false
			},{
				name : 'sampName',
				index : 'sampName',
				width : 150,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'source',
				index : 'source',
				width : 80,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'date',
				index : 'date',
				width : 130,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'finishDate',
				index : 'finishDate',
				width : 100,
				fixed:true,
				sortable : false,
				search : false,
			},  {
				name : 'taskType',
				index : 'taskType',
				width : 80,
				fixed:true,
				sortable : false,
				search : false
			},{
				name : 'userName',
				index : 'userName',
				width : 80,
				fixed:true,
				sortable : false,
				search : false
			},
			{
				name : 'status',
				index : 'status',
				width : 80,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'act',
				index : 'act',
				width : 100,
				fixed:true,
				search : false,
				sortable : false
			}];
		gridInitAuto(url, colNames, colModel, 20,'#pager');
	});
	function gridInitAuto(url, colNames, colModel,rowNum,pager) {
		$.jgrid.defaults.styleUI = "Bootstrap";
		var mygrid = $("#listTable").jqGrid({
			url : url,
			datatype : "json",
			mtype : "POST",
			colNames : colNames,
			colModel : colModel,
			rownumbers:true,
			rowNum : rowNum,
			rowList : [10,20,50,100,1000],
			pager : pager,
			sortname : 'sort',
			sortorder : "asc",
			viewrecords : true,
			height:'auto',
			autowidth:true,
			recordpos : 'right',
			jsonReader : {
				root : 'datas'
			},
			shrinkToFit:true,    
	        autoScroll: true,
	        search:false,
			gridComplete : gridComplete,
			loadComplete : loadComplete
		});
		setJgridWidth();//宽度
		setJgridHeight();//高度
		fncleanName();
	}
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-220);
	}
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			var be = '<a class="btn btn-outline btn-success btn-xs" title="日志" href="javascript:fnSelectProcess(\''+ids[i]+'\',\''+rData.type+'\',\''+rData.status+'\')">日志</a>';
			var ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+ids[i]+'\',\''+rData.type+'\',\''+rData.no+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be+' '+ce
			});
			if(rData.status=='终止'){
				jQuery('#'+ids[i]).addClass("rowBG");
			}
		}
	}
	//进度 弹出层
	function fnSelectProcess(id,type,status){
		var url='${basePath}bus/progress/show.do?type=task&busId='+id+'&busType='+encodeURI(status);
		if(type=='计量'){
			url='${basePath}jl/progress/show.do?type=task&busId='+id+'&busType='+encodeURI(status);
		}
		layer.open({
			  type: 2,
			  title: false,
			  closeBtn: 1, //不显示关闭按钮
			  fix: false, //不固定
			  shadeClose: true,
			  anim:5,
			  area: ['1000px','500px'],
			  content: url
		});
	}
	function fnShow(id,type,no){
		if(no=='null'){
			no='/';
		}
		var url='/bus/task/show.do?id='+id;
		if(type=='计量'){
			url='/jl/task/show.do?id='+id;
		}
		var index = layer.open({
			title:'项目【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: url
		});
	}
	function fnSearch(){
		var postData = $("#listTable").jqGrid("getGridParam", "postData");
		$.extend(postData, {'no':$('#no').val(),'custVo.custName':$('#custName').val(),'taskType':$('#taskType').val()
			,'sampName':$('#sampName').val(),'startDate':$('#startDate').val(),'endDate':$('#endDate').val(),'userName':$('#userName').val()});
		jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
	}
	</script>
</body>
</html>