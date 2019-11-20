<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<link href="${basePath}h/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
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
.control-label{
	width: 70px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding-top: 0px;">
				<form action="page.do" method="post" id="listForm" class="form-horizontal m-t">
					<div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >设备编号：</label>
                          <div class="col-md-8" >
                              <input id="no" name="apparaVo.no" value="${vo.apparaVo.no}" type="text" class="form-control" placeholder="编号">
                          </div>
                    </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >仪器名称：</label>
                          <div class="col-md-8" >
                              <input id="name" type="text" name="apparaVo.name" value="${vo.apparaVo.name}" class="form-control" placeholder="仪器名称">
                          </div>
                     </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >使用部门：</label>
                          <div class="col-md-8" >
                              <input id="orgName" type="text" name="orgName" value="${vo.orgName}" class="form-control" placeholder="使用部门">
                          </div>
                     </div>
                     <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >使用人：</label>
                          <div class="col-md-8" >
                              <input id="userName" type="text" name="userName" value="${vo.userName}" class="form-control" placeholder="使用人">
                          </div>
                     </div>
                      <div class="form-group col-md-6" id="data_5">
                          <label class="col-md-2 control-label">使用日期：</label>
                          <div class="input-daterange input-group col-md-10" id="datepicker"  style="width:300px;">
                              <input id="startDate" type="text" class="laydate-icon input-sm form-control" name="startDate" placeholder="起始日期"/>
                              <span class="input-group-addon">到</span>
                              <input id="endDate" type="text" class="laydate-icon input-sm form-control" name="endDate" placeholder="截至日期"/>
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
	<!-- Data picker -->
    <script src="${basePath}h/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script>
	$(function() {
		$('#data_5 .input-daterange').datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true
        });
		var url = 'gridData4Used.do';
		var colNames = ['','设备编号','仪器名称','规格/型号','开始时间','截至时间','时长','使用人','部门'];
		var colModel = [ 
			{
				name : 'apparaVo.id',
				index : 'apparaVo.Id',
				hidden:true,
				search : false
			},{
				name : 'apparaVo.no',
				index : 'apparaVo.no',
				formatter:formatNo,
				search : false,
			}, {
				name : 'apparaVo.name',
				index : 'apparaVo.name',
				sortable : false,
				search : false
			}, {
				name : 'apparaVo.spec',
				index : 'apparaVo.spec',
				sortable : false,
				search : false,
			},{
				name : 'startTime',
				index : 'startTime',
				sortable : false,
				search : false,
			},{
				name : 'endTime',
				index : 'endTime',
				sortable : false,
				search : false,
			},{
				name : 'duration',
				index : 'duration',
				sortable : false,
				search : false,
			},{
				name : 'userName',
				index : 'userName',
				sortable : false,
				search : false,
			},{
				name : 'orgName',
				index : 'orgName',
				sortable : false,
				search : false,
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
		$("#listTable").jqGrid('setGridHeight', win.WinH-200);
	}
	 
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnShow(\''+rowObject.apparaVo.id+'\');">'+cellValue+'</a>';
		return ops;
	}
	function fnShow(id){
		var index = layer.open({
			title:'仪器信息',	
			type: 2,
			area: ['800px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/res/appara/show.do?id='+id
		});
	}
	function fnSearch(){
		var postData = $("#listTable").jqGrid("getGridParam", "postData");
		$.extend(postData, {'apparaVo.no':$('#no').val(),'apparaVo.name':$('#name').val()
			,'orgName':$('#orgName').val(),'userName':$('#userName').val()
			,'startDate':$('#startDate').val(),'endDate':$('#endDate').val()});
		jQuery("#listTable").jqGrid('setGridParam',{url:'gridData4Used.do'}).trigger("reloadGrid")
	}
	</script>
</body>
</html>