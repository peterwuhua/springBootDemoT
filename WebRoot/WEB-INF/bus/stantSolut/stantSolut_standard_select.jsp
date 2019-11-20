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
		//var url = 'gridData.do';
		var url = '${basePath}res/standard/gridData.do';
		var editurl='';
  		var colNames = ['','编号','标准品名称','规格型号','当前库存','保管人'];
		var colModel = [ 
			{
				name : 'id',
				index : 'id',
				hidden:true,
				title:false,
				search:false
			},{
				name : 'no',
				index : 'no',
				sortable : false,
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
				name : 'rule',
				index : 'rule',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'amount',
				index : 'amount',
				width : 100,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'keeper',
				index : 'keeper',
				width : 70,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];
			gridInitAuto(url, colNames, colModel,  editurl, 20,'#pager',true);
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-90);
		});
	function gridComplete() {
		var id='${vo.id}';
		$(this).jqGrid('setSelection', id);
	}
	function fnSelect(){
		return $("#listTable").jqGrid("getRowData",getSelectId());
	}
	
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
