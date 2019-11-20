<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content" style="padding: 0px;">
					<form action="gridPage.do" method="post" id="listForm">
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
</body>
<script type="text/javascript">
	$(function() {
		var sampType='${vo.sampType}';
		var url = 'listItem.do?sampTypeId=${vo.sampTypeId}&standId=${vo.standId}&itemVo.id=${vo.itemVo.id}';
		var colNames=[];
		var colModel=[];
		if(sampType.indexOf('化学')>=0){
			colNames = ['','','标准编号','测试参数','分类','mac','twa','stel','超限倍数'];
			colModel = [ 
				{
					name : 'itemVo.id',
					index : 'item.id',
					hidden : true
				},{
					name : 'id',
					index : 'id',
					hidden : true
				}, {
				name : 'code',
				index : 'code',
				width : 130,
				fixed : true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'itemVo.name',
				index : 'item.name',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'type',
				index : 'type',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'value3',
				index : 'value3',
				sortable : false,
				width : 80,
				fixed : true,
				search : false
			},{
				name : 'value',
				index : 'value',
				sortable : false,
				width : 80,
				fixed : true,
				search : false
			},{
				name : 'value2',
				index : 'value2',
				sortable : false,
				width : 80,
				fixed : true,
				search : false
			},{
				name : 'maxValue',
				index : 'maxValue',
				sortable : false,
				width : 80,
				fixed : true,
				search : false
				
			}];
		}else{
			colNames = ['','','标准编号','测试参数','指标','分类','其他分类','限值'];
			colModel = [ 
				{
					name : 'itemVo.id',
					index : 'item.id',
					hidden : true
				},{
					name : 'id',
					index : 'id',
					hidden : true
				},{
				name : 'code',
				index : 'code',
				width : 120,
				fixed : true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'itemVo.name',
				index : 'item.name',
				sortable : false,
				width : 120,
				fixed : true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'itemType',
				index : 'itemType',
				sortable : false,
				width : 120,
				fixed : true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'type',
				index : 'type',
				width : 120,
				fixed : true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'otherType',
				index : 'otherType',
				sortable : false,
				width : 120,
				fixed : true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'valStr',
				index : 'valStr',
				sortable : false,
				search : false
			}];
		}
		gridInitAuto(url, colNames, colModel, '', -1,'',true);
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-85);
	});
	function gridComplete() {
		var id='${vo.ids}';
    	if(id!=null&&id!=''){
    		var ids=id.split(',');
    		for(var i=0;i<ids.length;i++){
    			if(ids[i]!=''){
    				$(this).jqGrid('setSelection', ids[i]);
    			}
    		}
    	}
	}
	function fnSelect(){
		var rowIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
		var rData = $('#listTable').jqGrid('getRowData',rowIds[0]);
		return rData;
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
 
</html>
