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
                              <input id="no" name="no" value="${vo.no}" type="text" class="form-control" placeholder="">
                          </div>
                    </div>
                   
                    <div class="form-group col-md-2">
                          <label class="col-md-4 control-label" >姓名：</label>
                          <div class="col-md-8" >
                              <input id="userName" name="userName" value="${vo.userName}" type="text" class="form-control" placeholder="">
                          </div>
                    </div>
                    <div class="form-group col-md-2">
                          <label class="col-md-4 control-label" >类型：</label>
                          <div class="col-md-8" >
                          	 <select id="type" name="type" class="form-control">
                          	 	<option value="">全部</option>
                         	 	<c:choose>
                          	 		<c:when test="${vo.type=='请假'}">	
                          	 			<option value="请假" selected="selected">请假</option>
                          	 			<option value="外勤">外勤</option>
                          	 		</c:when>
                          	 		<c:when test="${vo.type=='外勤'}">	
                          	 			<option value="请假">请假</option>
                          	 			<option value="外勤" selected="selected">外勤</option>
                          	 		</c:when>
                          	 		<c:otherwise>
                          	 			<option value="请假">请假</option>
                          	 			<option value="外勤">外勤</option>
                          	 		</c:otherwise>
                          	 	</c:choose>
                          	 </select>
                          </div>
                     </div>
                      <div class="form-group col-md-5">
                          <label class="col-md-2 control-label" style="width: 81px;">日期：</label>
                          <div class="input-daterange input-group col-md-10" style="width:300px;">
                              <input id="startDate" type="text" class="form-control dateISO" name="startDate" placeholder="开始日期"/>
                              <span class="input-group-addon">到</span>
                              <input id="endDate" type="text" class="form-control dateISO" name="endDate" placeholder="截止日期"/>
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
		var url = 'gridData4Query.do';
		var colNames = ['编号','类型','部门','申请人','开始时间','截止时间','描述','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				width : 100,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'type',
				index : 'type',
				sortable : false,
				width : 80,
				fixed:true,
				stype : 'select',
				searchoptions : {
					sopt : ['eq'],
					value:{'':'全部','请假':'请假','外勤':'外勤'}
				}
			}, {
				name : 'deptName',
				index : 'deptName',
				width : 100,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'userName',
				index : 'userName',
				width : 80,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'date',
				index : 'date',
				width : 140,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'endTime',
				index : 'endTime',
				width : 140,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'content',
				index : 'content',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 70,
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
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = $('#listTable').jqGrid('getRowData',ids[i]);
			var ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+ids[i]+'\',\''+rData.no+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act :ce
			});
		}
	}
	function fnShow(id,no){
		if(no=='null'){
			no='/';
		}
		var url='/office/kq/show.do?id='+id;
		var index = layer.open({
			title:'查看【'+no+'】',	
			type: 2,
			area: ['700px','420px'],
			fix: false, //不固定
			maxmin: true,
			content: url
		});
	}
	function fnSearch(){
		var postData = $("#listTable").jqGrid("getGridParam", "postData");
		$.extend(postData, {'no':$('#no').val(),'userName':$('#userName').val(),'type':$('#type').val()
			,'startDate':$('#startDate').val(),'endDate':$('#endDate').val()});
		jQuery("#listTable").jqGrid('setGridParam',{url:'gridData4Query.do'}).trigger("reloadGrid")
	}
	</script>
</body>
</html>