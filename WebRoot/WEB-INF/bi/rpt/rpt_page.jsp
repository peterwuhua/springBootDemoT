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
                          <label class="col-md-4 control-label" >报表编号：</label>
                          <div class="col-md-8" >
                              <input id="reportNo" name="reportNo" value="${vo.reportNo}" type="text" class="form-control" placeholder="报表编号">
                          </div>
                    </div>
                   
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >报表名称：</label>
                          <div class="col-md-8" >
                              <input id="title" name="title" value="${vo.title}" type="text" class="form-control" placeholder="报表名称">
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
                          <label class="col-md-4 control-label" >样品类别：</label>
                          <div class="col-md-8" >
                              <input id="sampName" type="text"  name="sampName" value="${vo.sampName}" class="form-control" placeholder="样品类别">
                          </div>
                     </div>
                      <div class="form-group col-md-6" id="data_5">
                          <label class="col-md-2 control-label" style="width: 81px;">上报日期：</label>
                          <div class="input-daterange input-group col-md-10" style="width:300px;">
                              <input id="startDate" type="text" class="form-control dateISO" name="startDate" placeholder="开始日期"/>
                              <span class="input-group-addon">到</span>
                              <input id="endDate" type="text" class="form-control dateISO" name="endDate" placeholder="截止日期"/>
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
		var colNames = ['报告编号','报表名称','样品名称','检测性质','上报日期','当前进度','操作'];
		var colModel = [ 
			{
				name : 'reportNo',
				index : 'reportNo',
				search : false,
			},{
				name : 'title',
				index : 'title',
				sortable : false,
				search : false,
			},{
				name : 'sampName',
				index : 'sampName',
				sortable : false,
				search : false
			},{
				name : 'taskType',
				index : 'taskType',
				sortable : false,
				search : false
			},{
				name : 'reportDate',
				index : 'reportDate',
				sortable : false,
				search : false,
			},{
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
			var be = '<a class="btn btn-outline btn-success btn-xs" title="日志" href="javascript:fnSelectProcess(\''+rData.id+'\',\''+rData.status+'\')">日志</a>';
			var ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+ids[i]+'\',\''+rData.reportNo+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be+' '+ce
			});
		}
	}
	//进度 弹出层
	function fnSelectProcess(id,status){
		var url='${basePath}bus/progress/show.do?type=task&busId='+id+'&busType='+encodeURI(status);
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
	function fnShow(id,no){
		if(no=='null'){
			no='/';
		}
		var url='/bus/rpt/show.do?id='+id;
		var index = layer.open({
			title:'报表【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: url
		});
	}
	function fnSearch(){
		var postData = $("#listTable").jqGrid("getGridParam", "postData");
		$.extend(postData, {'reportNo':$('#reportNo').val(),'title':$('#title').val(),'taskType':$('#taskType').val()
			,'sampName':$('#sampName').val()
			,'startDate':$('#startDate').val(),'endDate':$('#endDate').val()});
		jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
	}
	</script>
</body>
</html>