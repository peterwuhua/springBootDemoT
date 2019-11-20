<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../../include/css.jsp"%>
    <%@ include file="../../../include/status.jsp"%>
</head>
<body>
	 <div class="col-sm-12">
		<div class="ibox float-e-margins">
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div class="col-sm-5"></div>
				<div class="jqGrid_wrapper">
                      <table id="listTable"></table>
                      <div id="pager"></div>
               </div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = 'gridData.do';
		var editurl='';
		var colNames = ['','样品编号','样品名称','客户名称'];
		var colModel = [
				{
					name : 'id',
					index : 'id',
					hidden:true,
					title:false,
					search:false
				},{
					name : 'sampCode',
					index : 'sampCode',
					searchoptions : {
						sopt : [ 'cn']
					}
				}, {
					name : 'sampName',
					index : 'sampName',
					searchoptions : {
						sopt : [ 'cn']
					}
				},{
					name : 'custVo.custName',
					index : 'cust.custName',
					searchoptions : {
						sopt : [ 'cn']
					}
				}];
			gridInitAuto(url, colNames, colModel, '', 10,'#pager',true);
		});

		function loadComplete() {
			var id='${vo.id}';
	    	if(id!=''){
	    		$(this).jqGrid('setSelection', id);
	    	}
		}
	    function beforeSelectRow(){
	    	$("#listTable").jqGrid('resetSelection');  
	        return(true); 
	    }
		var index = parent.layer.getFrameIndex(window.name); 
		function fnSelect(){
			return $("#listTable").jqGrid("getRowData",getSelectId());
		}
		function setNavGrid(){};	
		/**
		 * 设置grid高度
		 */
		function setJgridHeight() {
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-125);
		}
		
		window.onresize = function doResize() {
			 var win = getWinSize();
			 $("#listTable").jqGrid('setGridWidth', win.WinW-10).jqGrid('setGridHeight', win.WinH-140);
		}
	</script> 
	<script>
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
	</script>
    </body>
</html>