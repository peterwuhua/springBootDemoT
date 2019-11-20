<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-12" style="height: 100%">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 0px;">
				<div class="tabs-container">
                    <div class="tab-content">
						<div class="jqGrid_wrapper">
	                      <table id="listTable"></table>
	                      <div id="pager"></div>
		               </div>
                    </div>
                </div>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
	<script>
	$(function() {
		var url = '${basePath}init/car/gridData.do';
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
		var idstr='${vo.ids}';
    	if(idstr!=''){
    		var	ids=idstr.split(',');
    		for(var i=0;i<ids.length;i++){
    			if(ids[i]!=''){
    				$(this).jqGrid('setSelection', ids[i]);
    			}
    		}
    	}
	}
			function fnSelect(){
				
				var rowId = $('#listTable').jqGrid('getGridParam', 'selarrrow');
				var content = "";
				for(var i=0;i<rowId.length;i++){
					if(0!=i) content+="，";
					var rData = $('#listTable').jqGrid('getRowData',rowId[i]);
					content += rData["code"];
				}
				var data={};
				data.id=rowId;
				data.name=content;
				return data;
			}
		</script> 
		 
		<script type="text/javascript">
		//设置弹窗中jqgrid表格高度
		function setJgridHeight() {
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-130);
		}
		//当窗口大小发生改变时触发
		window.onresize = function doResize() {
			 var win = getWinSize();
			 $("#listTable").jqGrid('setGridWidth', win.WinW-10).jqGrid('setGridHeight', win.WinH-130);
		}
		</script>
</body>
</html>
