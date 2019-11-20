<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12" style="height: 100%">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 0px;">
				<form action="" method="post" id="listForm">
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
		var url = 'gridListData.do';
		var colNames = ['','合同编号','客户名称','销售名称','合同金额','开始日期','截止日期','合同状态'];
		var colModel = [ 
			{
				name : 'id',
				index : 'id',
				hidden:true,
				title:false
			},{
				name : 'code',
				index : 'code',
				width : 90,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'customerVo.name',
				index : 'customerVo.name',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'saler',
				index : 'saler',
				sortable : false,
				width : 90,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'contractSum',
				index : 'contractSum',
				sortable : false,
				width : 90,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'sdate',
				index : 'sdate',
				sortable : false,
				width : 90,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'edime',
				index : 'edime',
				sortable : false,
				width : 90,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'status',
				index : 'status',
				sortable : false,
				search : false,
			}];
			gridInitAuto(url, colNames, colModel, '', 20,'#pager',true);
		});

		function gridComplete() {
	    	var id='${vo.id}';
	    	if(id!=''){
	    		$(this).jqGrid('setSelection', id);
	    	}
		}
	    function beforeSelectRow(){
	    	$("#listTable").jqGrid('resetSelection');  
	        return(true); 
	    }
		function fnSelect(){
			return $("#listTable").jqGrid("getRowData",getSelectId());
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
		$("#listTable").jqGrid('setGridHeight', win.WinH-125);
	}
	</script>
</body>
</html>
