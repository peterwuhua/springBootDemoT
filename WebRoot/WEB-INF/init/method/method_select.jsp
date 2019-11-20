<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-12">
	<div class="ibox float-e-margins">
		<div class="">
				<%@ include file="../../include/status.jsp"%>
				<form method="post" id="listForm">
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
		var editurl='';
		var colNames = ['','方法编号','条款','检测方法'];
		var colModel = [ 
			{
				name : 'id',
				index : 'id',
				title:false,
				hidden:true,
				search:false
			}, {
				name : 'code',
				index : 'code',
				width:150,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'chapter',
				index : 'chapter',
				width : 80,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'name',
				index : 'name',
				searchoptions : {
					sopt : ['cn']
				}
			}];
			gridInitAuto(url, colNames, colModel, '', -1,'',true);
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-90);
		});
		function gridComplete() {
	    	var mids='${vo.ids}';
	    	if(mids!=null&&mids!=''){
	    		var ids=mids.split(',');
	    		for(var i=0;i<ids.length;i++){
	    			if(ids[i]!=''){
	    				$(this).jqGrid('setSelection', ids[i]);
	    			}
	    		}
	    	}
		}
		function fnSelectM(){
			var rowId = $('#listTable').jqGrid('getGridParam', 'selarrrow');
			var names=[];
			for(var i=0;i<rowId.length;i++){
				var rData = $('#listTable').jqGrid('getRowData',rowId[i]);
				names.push(rData.code+' '+rData.chapter+rData.name);
			}
			var data={};
			data.id=rowId;
			data.name=names;
			return data;
		}
	</script> 
    </body>
</html>