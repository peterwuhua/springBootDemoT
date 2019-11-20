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
					<div class="form-group col-md-2">
                          <label class="col-md-4 control-label" >编号：</label>
                          <div class="col-md-8" >
                              <input id="userCode" name="userCode" value="${vo.userCode}" type="text" class="form-control" placeholder="">
                          </div>
                    </div>
                   
                    <div class="form-group col-md-2">
                          <label class="col-md-4 control-label" >姓名：</label>
                          <div class="col-md-8" >
                              <input id="userName" name="userName" value="${vo.userName}" type="text" class="form-control" placeholder="">
                          </div>
                    </div>
                      <div class="form-group col-md-5">
                          <label class="col-md-2 control-label" style="width: 81px;">日期：</label>
                          <div class="input-daterange input-group col-md-10" style="width:300px;">
                              <input id="startDate" type="text" class="form-control dateISO" name="startDate" value="${vo.startDate}" placeholder="开始日期"/>
                              <span class="input-group-addon">到</span>
                              <input id="endDate" type="text" class="form-control dateISO" name="endDate" value="${vo.endDate}" placeholder="截止日期"/>
                          </div>
					</div>
					<div class="form-group col-md-1">
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
		var url = 'gridData.do?startDate=${vo.startDate}&endDate=${vo.endDate}';
		var colNames = ['编号','姓名','部门','考勤日期','状态'];
		var colModel = [ 
			{
				name : 'userCode',
				index : 'userCode',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'userName',
				index : 'userName',
				sortable : false,
				search : false,
			}, {
				name : 'deptName',
				index : 'deptName',
				sortable : false,
				search : false,
			},{
				name : 'date',
				index : 'date',
				sortable : false,
				search : false,
			},{
				name : 'status',
				index : 'status',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
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
			rowList : [10,20,50,100],
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
		$("#listTable").jqGrid('setGridHeight', win.WinH-180);
	}
	function fnSearch(){
		var postData = $("#listTable").jqGrid("getGridParam", "postData");
		$.extend(postData, {'userCode':$('#userCode').val(),'userName':$('#userName').val()
			,'startDate':$('#startDate').val(),'endDate':$('#endDate').val()});
		jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
	}
	</script>
</body>
</html>