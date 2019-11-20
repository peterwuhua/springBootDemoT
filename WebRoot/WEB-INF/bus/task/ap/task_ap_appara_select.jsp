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
		var url = '${basePath}res/appara/listData4Out.do';
  		var colNames = ['设备编号','设备名称','规格型号','保管科室','保管人','当前预约'];
		var colModel = [ 
			 {
				name : 'no',
				index : 'no',
				width : 100,
				fixed:true,
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
				name : 'spec',
				index : 'spec',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'deptName',
				index : 'deptName',
				width : 100,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'keeper',
				index : 'keeper',
				width : 70,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'ext01',
				index : 'ext01',
				width : 70,
				fixed:true,
				align:'center',
				formatter:formatNum,
				sortable : false,
				search : false,
			}];
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
    				//$(this).jqGrid('setSelection', ids[i]);
    				 $('#listTable').setRowData(ids[i],null,{display: 'none'});
    			}
    		}
    	}
	}
	function fnSelect(){
		var rowIds = $('#listTable').jqGrid('getGridParam', 'selarrrow');
		var names=[];
		for(var i=0;i<rowIds.length;i++){
			var rData = $('#listTable').jqGrid('getRowData',rowIds[i]);
			if(null!=rData.no && rData.no!=''){
				names.push(rData.name+' '+rData.spec+'('+rData.no+')');
			}else if(null!=rData.spec && rData.spec!=''){
				names.push(rData.name+' '+rData.spec);
			}else{
				names.push(rData.name);
			}
		}
		var data={};
		data.id=rowIds;
		data.name=names;
		return data;
	}
	function formatNum(cellValue,options,rowObject){
		var ops='<a href="javascript:void();" onclick="showYY(\''+rowObject.id+'\',\''+rowObject.name+' '+rowObject.spec+'\');">'+cellValue+'</a>';
		return ops;
	}
	function showYY(id,name){
		parent.layer.open({
			title:'仪器预约详情【<font color="blue">'+name+'</font>】',	
			type: 2,
			area: ['800px','400px'],
			fix: false, //不固定
			maxmin: true,
			content: 'appara_show.do?ids='+id
		});
	}
	</script>

</body>
</html>
