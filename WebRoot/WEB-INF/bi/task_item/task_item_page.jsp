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
.ui-pg-selbox{
	width: 43px !important;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >任务编号：</label>
                          <div class="col-md-8" >
                              <input id="no" name="taskVo.no" value="${vo.taskVo.no}" type="text" class="form-control" placeholder="任务编号">
                          </div>
                    </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >检测性质：</label>
                         <div class="col-md-8" >
                          	 <select id="taskType" name="taskVo.taskType" class="form-control">
                          	 	<option value="">全部</option>
                          	 	<c:forEach items="${busList}" var="e" varStatus="v">
                          	 		<c:choose>
	                          	 		<c:when test="${vo.taskVo.taskType==e.name}">	
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
                     
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >检测部门：</label>
                          <div class="col-md-8" >
                              <input id="deptName" type="text" name="deptName" value="${vo.deptName}" class="form-control" placeholder="检测部门">
                          </div>
                     </div>
                     <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >检测人员：</label>
                          <div class="col-md-8" >
                              <input id="testMan" type="text" name="testMan" value="${vo.testMan}" class="form-control" placeholder="检测人员">
                          </div>
                     </div>
                     <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >检测项目：</label>
                          <div class="col-md-8" >
                              <input id="itemName" type="text" name="itemName" value="${vo.itemName}" class="form-control" placeholder="检测项目">
                          </div>
                     </div>
                     <div class="form-group col-md-6" id="data_5">
                          <label class="col-md-2 control-label" >检测日期：</label>
                          <div class="input-daterange input-group col-md-10" style="width:300px;">
                              <input id="startDate" type="text" class="form-control dateISO" name="startDate" placeholder="开始日期"/>
                              <span class="input-group-addon">到</span>
                              <input id="endDate" type="text" class="form-control dateISO" name="endDate" placeholder="截止日期"/>
                          </div>
					</div>
					 <div class="form-group col-md-6">
						   <a class="btn btn-primary" href="javascript:void(0);" onclick="fnSearch();"><i class="fa fa-search" aria-hidden="true"></i> 查询</a>
							<!--<a class="btn btn-primary" href="javascript:void(0);" onclick="chartValueFun();"><i class="fa fa-line-chart" aria-hidden="true"></i> 检测结果分析</a>-->
							<!-- <a class="btn btn-primary" href="javascript:void(0);" onclick="chartNumFun();"><i class="fa fa-line-chart" aria-hidden="true"></i> 检测项目分析</a> -->
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
		var colNames = ['','任务编号','受检单位','检测性质','样品名称','检测项目','样品数','检测人员','检测时间','当前进度','操作'];
		var colModel = [ 
			{
				name : 'itemId',
				index : 'itemId',
				hidden:true,
				search : false
			},{
				name : 'taskVo.no',
				index : 'task.no',
				width : 100,
				fixed:true,
				formatter:formatNo,
				sortable : false,
				search : false
			},{
				name : 'taskVo.custVo.custName',
				index : 'task.cust.custName',
				sortable : false,
				search : false
			},{
				name : 'taskVo.taskType',
				index : 'task.taskType',
				width : 80,
				fixed:true,
				sortable : false,
				search : false
			},{
				name : 'sampName',
				index : 'sampName',
				sortable : false,
				search : false
			},{
				name : 'itemName',
				index : 'itemName',
				width : 120,
				fixed:true,
				sortable : false,
				search : false
			},{
				name : 'sampNum',
				index : 'sampNum',
				width : 70,
				fixed:true,
				sortable : false,
				search : false
			},{
				name : 'testMan',
				index : 'testMan',
				width : 100,
				fixed:true,
				sortable : false,
				search : false
			}, {
				name : 'testTime',
				index : 'testTime',
				formatter:formatTime,
				sortable : false,
				width : 100,
				fixed:true,
				search : false
			},{
				name : 'status',
				index : 'status',
				width : 70,
				fixed:true,
				sortable : false,
				search : false
			},{
				name : 'act',
				index : 'act',
				width : 70,
				fixed:true,
				sortable : false,
				search : false
			}];
		gridInitAuto(url, colNames, colModel, '', 10,'#pager',false);
	});
	function gridInitAuto(url, colNames, colModel, editurl,rowNum,pager,multiselect) {
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
			editurl:editurl,
			multiselect : multiselect,
			gridComplete : gridComplete,
			loadComplete : loadComplete
		});
		setJgridWidth();//宽度
		setJgridHeight();//高度
		setNavGrid();//功能按钮
	}
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-240);
	}
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			var be = '<a class="btn btn-outline btn-success btn-xs" title="日志" href="javascript:fnSelectProcess(\''+rData.id+'\',\''+rData.status+'\')">日志</a>';
			ce = rData.status;
			if(rData.status=='终止'){
				ce='<font color="red">已终止<font>';
			}
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be,
				"status":ce
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
			  content: '${basePath}bus/progress/show.do?type=item&busId='+id+'&busType='+encodeURI(status)
		});
	}
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnShow(\''+rowObject.taskVo.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		return ops;
	}
	function formatTime(cellValue){
		var ops=cellValue;
		if(null==cellValue){
			ops='';
		}else if(cellValue.length>10){
			ops=cellValue.substring(0,10);
		}
		return ops;
	}
	
	function fnShow(taskId,no){
		var index = layer.open({
			title:'任务单【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/task/show.do?id='+taskId
		});
	}
	function chartValueFun(){
		var ids = getSelectIds();
		if(ids.length<1){
			layer.msg('请选择要分析的测试参数', {icon: 0,time: 3000});
			return false;
		}
		var itemIds="";
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			if(itemIds.indexOf(rData.itemId)<0){
				itemIds+=rData.itemId+',';
			}
		}
		layer.open({
			title:'测试参数结果分析图表',	
			type: 2,
			area: ['80%','90%'],
			fix: false, //不固定
			maxmin: true,
			content: 'chart_value.do?itemId='+itemIds
		});
	}
	function chartNumFun(){
		layer.open({
			title:'测试参数数量分析图表',	
			type: 2,
			area: ['80%','90%'],
			fix: false, //不固定
			maxmin: true,
			content: 'chart_num.do'
		});
	}
	function fnSearch(){
		var postData = $("#listTable").jqGrid("getGridParam", "postData");
		$.extend(postData, {'taskVo.no':$('#no').val(),'taskVo.taskType':$('#taskType').val()
			,'sampName':$('#sampName').val()
			,'deptName':$('#deptName').val(),'testMan':$('#testMan').val()
			,'startDate':$('#startDate').val(),'endDate':$('#endDate').val(),'itemName':$('#itemName').val()});
		jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
	}
	</script>
</body>
</html>