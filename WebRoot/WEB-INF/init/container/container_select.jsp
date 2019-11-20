<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 0px;">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="id" id="id">
					<div class="col-sm-5"></div>
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
		var editurl='gridEdit.do';
  		var colNames = ['编号','名称','计量单位','规格'];
		var colModel = [ 
			 {
				name : 'code',
				index : 'code',
				sortable : false,
				width : 100,
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
				name : 'unit',
				index : 'unit',
				width : 100,
				sortable : false,
				search : false,
			},{
				name : 'maxVal',
				index : 'maxVal',
				width : 100,
				sortable : false,
				search : false,
			}];
			gridInitAuto(url, colNames, colModel, '', -1,'',true);
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-90);
		});
	function gridComplete() {
		var id='${vo.id}';
		if(id!=''){
			$(this).jqGrid('setSelection', id);
		}
	}
	function fnSelect(){
		var rowIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
		var rData = $('#listTable').jqGrid('getRowData',rowIds[0]);
		var names=rData.maxVal+rData.unit+rData.name;
		var data={};
		data.id=rowIds;
		data.name=names;
		return data;
	}
	</script>
<script type="text/javascript">
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
        search:true,
		editurl:editurl,
		multiselect : multiselect,
		gridComplete : gridComplete,
		loadComplete : loadComplete,
		beforeSelectRow: beforeSelectRow
	});
	setFilterToolbar();//行内查询
	setJgridWidth();//宽度
	setJgridHeight();//高度
	setNavGrid();//功能按钮
	jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
	$(".ui-search-oper").hide();
	fncleanName();
}
function beforeSelectRow(){
	$("#listTable").jqGrid('resetSelection');  
    return(true); 
}
</script>
</body>
</html>
