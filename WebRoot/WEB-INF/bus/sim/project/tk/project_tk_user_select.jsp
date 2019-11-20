<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../../include/css.jsp"%>
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
	<%@ include file="../../../../include/js.jsp"%>
	<%@ include file="../../../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var itemtype='${vo.itemType}';
		var url = '${basePath}bus/sim/projectZp/listAu.do?itemType='+encodeURI(itemtype);
		var colNames = [ '编号','名称','手机','当前工作量'];
		var colModel = [ 
			 {
				name : 'no',
				index : 'no',
				width : 90,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'name',
				index : 'name',
				width : 90,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'mobile',
				index : 'mobile',
				width : 90,
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'num',
				index : 'num',
				sortable : false,
				width : 90,
				search : false,
			}];
			gridInitAuto(url, colNames, colModel, '', -1,'',true);
		});

		function gridComplete() {
	    	var userId='${vo.ids}';
	    	if(userId!=''){
	    		var userids=userId.split(',');
	    		for(var i=0;i<userids.length;i++){
	    			$(this).jqGrid('setSelection', userids[i]);
	    		}
	    	}
		}
	    function beforeSelectRow(){
	    	$("#listTable").jqGrid('resetSelection');  
	        return(true); 
	    }
		var index = parent.layer.getFrameIndex(window.name); 
		function fnSelectUser(){
			var rowId = $('#listTable').jqGrid('getGridParam', 'selarrrow');
			var name=[];
			for(var i=0;i<rowId.length;i++){
				var rData = $('#listTable').jqGrid('getRowData',rowId[i]);
				name.push(rData.name);
			}
			var data={};
			data.id=rowId;
			data.name=name;
			return data;
		}
	</script> 
	<script type="text/javascript">
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-90);
	}
	//当窗口大小发生改变时触发
	window.onresize = function doResize() {
		 var win = getWinSize();
		 $("#listTable").jqGrid('setGridWidth', win.WinW-10).jqGrid('setGridHeight', win.WinH-90);
	}
	</script>
</body>
</html>
