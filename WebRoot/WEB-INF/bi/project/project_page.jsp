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
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm" class="form-horizontal m-t">
					<div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >项目编号：</label>
                          <div class="col-md-8" >
                              <input id="no" name="no" value="${vo.no}" type="text" class="form-control" >
                          </div>
                    </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >受检单位：</label>
                          <div class="col-md-8" >
                              <input id="custName" type="text" name="custVo.custName" value="${vo.custVo.custName}" class="form-control" >
                          </div>
                    </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >分类类别：</label>
                          <div class="col-md-8" >
                          	 <select id="sampType" name="sampType" class="form-control">
                          	 	<option value="">全部</option>
                          	 	<c:forEach items="${busList}" var="e" varStatus="v">
                          	 		<c:choose>
	                          	 		<c:when test="${vo.sampType==e}">	
	                          	 			<option value="${e}" selected="selected">${e}</option>
	                          	 		</c:when>
	                          	 		<c:otherwise>
	                          	 			<option value="${e}">${e}</option>
	                          	 		</c:otherwise>
	                          	 	</c:choose>
                          	 	</c:forEach>
                          	 </select>
                          </div>
                    </div>
                    <div class="form-group col-md-4" id="data_5">
                         <label class="col-md-2 control-label" style="width: 81px;">立项日期：</label>
                         <div class="input-daterange input-group col-md-3" style="width:300px;">
                              <input id="startDate" type="text" class="form-control dateISO" name="startDate" placeholder="开始日期"/>
                              <span class="input-group-addon" style="font-size:bold;">到</span>
                              <input id="endDate" type="text" class="form-control dateISO" name="endDate" placeholder="截止日期"/>
                         </div>
					</div>
					<div class="form-group col-md-4">
                          <label class="col-md-3 control-label" >立项人：</label>
                          <div class="col-md-6" style="width:190px;">
                              <input id="userName" type="text" name="userName" value="${vo.userName}" class="form-control" placeholder="立项人">
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
		var url = 'gridData.do';
		var colNames = ['项目编号','受检单位','委托单位','分类类别','合同金额','签订日期','已收金额','合同状态','立项人','操作'];
		var colModel = [ 
			{
				name : 'no',
				index : 'no',
				formatter:formatNo,
				width : 120,
				fixed:true,
				search : false,
			}, {
				name : 'custVo.custName',
				index : 'cust.custName',
				sortable : false,
				search : false
			},{
				name : 'custVo.wtName',
				index : 'cust.wtName',
				sortable : false,
				search : false
			}, {
				name : 'sampType',
				index : 'sampType',
				sortable : false,
				search : false
			},{
				name : 'htPrice',
				index : 'htPrice',
				width : 100,
				fixed:true,
				sortable : false,
				search : false
			},{
				name : 'qdDate',
				index : 'qdDate',
				width : 120,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'pay',
				index : 'pay',
				width : 100,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'htSt',
				index : 'htSt',
				width : 100,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'userName',
				index : 'userName',
				width : 100,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'act',
				index : 'act',
				width : 80,
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
		$("#listTable").jqGrid('setGridHeight', win.WinH-230);
	}
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var rData = data.datas[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShowSch(\''+rData.id+'\')">方案查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
			if(rData.status=='终止'){
				jQuery('#'+ids[i]).removeClass("jqgrow");
				jQuery('#'+ids[i]).addClass("rowBG");
			}
		}
	}
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void();" onclick="fnShow(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		return ops;
	}
	function fnShow(id,no){
		parent.layer.open({
			title:'项目详情【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bi/project/show.do?id='+id
		});
	}
	function fnShowSch(id){
		parent.layer.open({
			title:'方案查看',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/projectFa/show.do?id='+id
		});
	}
	function fnSearch(){
		var postData = $("#listTable").jqGrid("getGridParam", "postData");
		$.extend(postData, {'no':$('#no').val()
			,'custName':$('#custName').val(),'sampType':$('#sampType').val()
			,'startDate':$('#startDate').val(),'endDate':$('#endDate').val(),'userName':$('#userName').val()});
		jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
	}
	</script>
</body>
</html>