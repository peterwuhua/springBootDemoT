<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 0px;">
				<%@ include file="../../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
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
		var url = '${basePath}init/car/list4Select.do';
  		var colNames = ['','车牌号码','车辆名称','型号','当前预约'];
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
			}, {
				name : 'num',
				index : 'num',
				width : 70,
				fixed:true,
				align:'center',
				formatter:formatNum,
				sortable : false,
				search : false,
			} ];
			gridInitAuto(url, colNames, colModel, '', -1,'',true);
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-90);
		});
	function gridComplete() {
		var id='${vo.ids}';
    	if(id!=null&&id!=''){
    		var ids=id.split(',');
    		for(var i=0;i<ids.length;i++){
    			if(ids[i]!=''){
    				 $('#listTable').setRowData(ids[i],null,{display: 'none'});
    			}
    		}
    	}
	}
	function formatNum(cellValue,options,rowObject){
		var ops='<a href="javascript:void();" onclick="showYY(\''+rowObject.id+'\',\''+rowObject.code+'\');">'+cellValue+'</a>';
		return ops;
	}
 
	function showYY(id,code){
		parent.layer.open({
			title:'车辆预约详情【<font color="blue">'+code+'</font>】',	
			type: 2,
			area: ['800px','400px'],
			fix: false, //不固定
			maxmin: true,
			content: 'car_show.do?ids='+id
		});
	}
	function fnSelect(){
		var rowIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
		var names=[];
		for(var i=0;i<rowIds.length;i++){
			var rData = $('#listTable').jqGrid('getRowData',rowIds[i]);
			names.push(rData.name+'('+rData.code+')');
		}
		var data={};
		data.id=rowIds;
		data.name=names;
		return data;
	}
	</script>
</body>
</html>
