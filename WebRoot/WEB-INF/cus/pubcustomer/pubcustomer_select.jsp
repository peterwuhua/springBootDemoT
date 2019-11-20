<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
    <%@ include file="../../include/status.jsp"%>
</head>
<body>
	 <div class="col-sm-12">
		<div class="ibox float-e-margins">
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<input type="hidden" id="cusName" value="${vo.name}"/>
				<input type="hidden" id="cusId" value="${vo.id}"/>
				<div class="col-sm-5"></div>
				<div class="jqGrid_wrapper">
                      <table id="listTable"></table>
                      <div id="pager"></div>
               </div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = 'gridData.do';
		var editurl='gridEdit.do';
		var colNames = ['','','','','','客户名称','法人','联系人',"电话","信用代码 / 身份证明"];
		var colModel = [
			{
				name : 'custCard',
				index : 'custCard',
				hidden:true,
				title:false,
				search:false
				
				}, {
				name : 'jgCode',
				index : 'jgCode',
				hidden:true,
				title:false,
				search:false
				
				},{
				name : 'zip',
				index : 'zip',
				hidden:true,
				title:false,
				search:false
				
					},{
				name : 'address',
				index : 'address',
				hidden:true,
				title:false,
				search:false
				
				},{
					name : 'id',
					index : 'id',
					hidden:true,
					title:false,
					search:false
				},{
					name : 'name',
					index : 'name',
					searchoptions : {
						sopt : [ 'cn']
					}
				}, {
					name : 'custFaRen',
					index : 'custFaRen',
					width : 80,
					searchoptions : {
						sopt : [ 'cn']
					}
				},{
					name : 'person',
					index : 'person',
					width : 70,
					searchoptions : {
						sopt : [ 'cn']
					}
				},{
					name : 'telephone',
					index : 'telephone',
					width : 100,
					searchoptions : {
						sopt : [ 'cn']
					}
				},{
					name : 'custCode',
					index : 'custCode',
					width : 150,
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
		
		function fnMultiSelect(){
			return getSelectIds();
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
	</script>
    </body>
</html>