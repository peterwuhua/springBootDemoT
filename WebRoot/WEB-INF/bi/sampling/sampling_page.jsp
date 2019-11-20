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
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm" class="form-horizontal m-t">
					<div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >样品编号：</label>
                          <div class="col-md-8" >
                              <input id="sampCode" name="sampCode" value="${vo.sampCode}" type="text" class="form-control" >
                          </div>
                    </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >产品名称：</label>
                          <div class="col-md-8" >
                              <input id="sampName" type="text" name="sampName" value="${vo.sampName}" class="form-control" >
                          </div>
                     </div>
                    <div class="form-group col-md-5" id="data_5">
                          <label class="col-md-2 control-label" style="width: 81px;">到样日期：</label>
                          <div class="input-daterange input-group col-md-10" style="width:300px;">
                              <input id="startDate" type="text" class="form-control dateISO" name="startDate" placeholder="开始日期"/>
                              <span class="input-group-addon">到</span>
                              <input id="endDate" type="text" class="form-control dateISO" name="endDate" placeholder="截止日期"/>
                          </div>
					</div>
					<div class="form-group col-md-2">
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
		var colNames = ['样品编号 ','产品名称','规格型号','产品等级','受检单位','到样时间',"抽（送）样单位/抽样人",'检验性质 ','承检部门 ','备注'];
		var colModel = [
			{
				name : 'sampCode',
				index : 'sampCode',
				width : 170,
				frozen : true,
				resizable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'sampName',
				index : 'sampName',
				width : 170,
				sortable : false,
				frozen : true,
				resizable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},  {
				name : 'rule',
				index : 'rule',
				width : 150,
				resizable : false,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'dj',
				index : 'dj',
				sortable : false,
				width : 80,
				resizable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'taskVo.custName',
				index : 'task.custName',
				width : 200,
				sortable : false,
				resizable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'reciveDate',
				index : 'reciveDate',
				sortable : false,
				width : 80,
				resizable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'taskVo.custNameCy',
				index : 'task.custNameCy',
				width : 200,
				sortable : false,
				resizable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'taskVo.taskType',
				index : 'task.taskType',
				sortable : false,
				width : 70,
				resizable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'fxDeptName',
				index : 'fxDeptName',
				width : 150,
				sortable : false,
				resizable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'remark',
				index : 'remark',
				width : 150,
				sortable : false,
				resizable : true,
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
			rowList : [10,20,50,100,1000],
			pager : pager,
			sortname : 'sort',
			sortorder : "asc",
			viewrecords : true,
			height:'auto',
			recordpos : 'right',
			jsonReader : {
				root : 'datas'
			},
			shrinkToFit:false,    
	        autoScroll: true,  
	        search:false,
			gridComplete : gridComplete,
			loadComplete : loadComplete
		});
		setJgridWidth();//宽度
		setJgridHeight();//高度
		setNavGrid();//功能按钮
		jQuery("#listTable").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" });//横向滚动条 
		jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
		$(".ui-search-oper").hide();
		fncleanName();
		if(!!isPull){
			fnUpdate4Pull();
		}
	}
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-180);
	}
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnShow(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		return ops;
	}
	function fnShow(id,no){
		var index = layer.open({
			title:'样品【'+no+'】',	
			type: 2,
			area: ['800px','400px'],
			fix: false, //不固定
			maxmin: true,
			content: '/bi/sampling/show.do?id='+id
		});
	}
	function fnSearch(){
		var postData = $("#listTable").jqGrid("getGridParam", "postData");
		$.extend(postData, {'sampCode':$('#sampCode').val(),'sampName':$('#sampName').val()
			,'startDate':$('#startDate').val(),'endDate':$('#endDate').val()});
		jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
	}
	</script>
</body>
</html>