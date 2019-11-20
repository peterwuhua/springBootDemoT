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
				<form action="gridPage.do" method="post" id="listForm">
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
		var url = 'gridData4Run.do?sampTypeId=${vo.sampTypeId}';
		var colNames = [ '标准编号','标准名称'];
		var colModel = [ 
			 {
				name : 'code',
				index : 'code',
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
			}];
			gridInitAuto(url, colNames, colModel, '', -1,'',true);
		});
		function gridComplete() {
			var id='${vo.id}';
	    	if(id!=null&&id!=''){
	    		$(this).jqGrid('setSelection', id);
	    	}
		}
		var index = parent.layer.getFrameIndex(window.name); 
		function fnSelect(){
			var rowIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
			var names='';
			var codes='';
			var nameCodes='';
			if(rowIds.length>0){
				var rData = $('#listTable').jqGrid('getRowData',rowIds[0]);
				names=rData.name;
				codes=rData.code;
				if(rData.name.indexOf(rData.code)<0){
					nameCodes=rData.name+' '+rData.code;
				}else{
					nameCodes=rData.name;
				}
			}
			var data={'id':rowIds[0],'name':names,'code':codes,'nameCode':nameCodes};
			return data;
		}
	</script> 
	 
	<script type="text/javascript">
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-90);
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
