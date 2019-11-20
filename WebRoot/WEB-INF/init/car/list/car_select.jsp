<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12" style="height: 100%">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 0px;">
				<form action="page.do" method="post" id="listForm">
				<div class="col-sm-5"></div>
				<div class="jqGrid_wrapper">
                      <table id="listTable"></table>
                      <div id="pager"></div>
               </div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<%@ include file="../../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = '${basePath}init/car/gridData4Select.do';
		var editurl='';
		var colNames = ['','车牌号码','车辆名称','型号'];
		var colModel = [ 
			 {
				name : 'id',
				index : 'id',
				hidden : true,
				title : false,
				search:false
			}, {
				name : 'code',
				index : 'code',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
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
				search : false,
			}];
			gridInitAuto(url, colNames, colModel, '', -1,'',true);
		});

		function gridComplete() {
	    	var id='${vo.id}';
	    	$(this).jqGrid('setSelection',id);
		}
	    function beforeSelectRow(){
	    	$("#listTable").jqGrid('resetSelection');  
	        return(true); 
	    }
		function fnSelect(){
			var rowId = $('#listTable').jqGrid('getGridParam', 'selarrrow');
			var rData = $('#listTable').jqGrid('getRowData',rowId[0]);
			return rData;
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
	<script type="text/javascript">
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-80);
	}
	//当窗口大小发生改变时触发
	window.onresize = function doResize() {
		 var win = getWinSize();
		 $("#listTable").jqGrid('setGridWidth', win.WinW-10).jqGrid('setGridHeight', win.WinH-215);
	}
	</script>
</body>
</html>
