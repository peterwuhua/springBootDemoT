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
                          <label class="col-md-4 control-label" >客户编号：</label>
                          <div class="col-md-8" >
                              <input id="code" name="code" value="${vo.code}" type="text" class="form-control" >
                          </div>
                    </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >客户名称：</label>
                          <div class="col-md-8" >
                              <input id="name" type="text" name="name" value="${vo.name}" class="form-control" >
                          </div>
                     </div>
                    <div class="form-group col-md-3">
                          <label class="col-md-4 control-label" >客户经理：</label>
                          <div class="col-md-8" >
                              <input id="saler" type="text"  name="saler" value="${vo.saler}" class="form-control" >
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
		var colNames = ['客户编号','客户名称','客户类型','客户行业','电话','邮箱','联系人','客户经理'];
		var colModel = [
			  {
				name : 'code',
				index : 'code',
				width : 100,
				formatter:formatNo,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'name',
				index : 'name',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'cusType',
				index : 'cusType',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'industry',
				index : 'industry',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'phone',
				index : 'phone',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'email',
				index : 'email',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'person',
				index : 'person',
				sortable : false,
				width : 70,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'saler',
				index : 'saler',
				sortable : false,
				width : 70,
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
	function formatNo(cellValue,options,rowObject){
		var ops='<a href="javascript:void(0);" onclick="fnShow(\''+rowObject.id+'\',\''+cellValue+'\');">'+cellValue+'</a>';
		return ops;
	}
	function fnShow(id,no){
		var index = layer.open({
			title:'客户【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bi/customer/show.do?id='+id
		});
	}
	function fnSearch(){
		var postData = $("#listTable").jqGrid("getGridParam", "postData");
		$.extend(postData, {'code':$('#code').val(),'name':$('#name').val()
			,'saler':$('#saler').val()});
		jQuery("#listTable").jqGrid('setGridParam',{url:'gridData.do'}).trigger("reloadGrid")
	}
	</script>
</body>
</html>