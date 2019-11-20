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
				<input type="hidden" id="id" value="${vo.id}"/>
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
		var colNames = ['','方法编号','方法名称'];
		var colModel = [
			{
				name : 'id',
				index : 'id',
				hidden:true,
				title:false,
				search:false
			},{
				name : 'code',
				index : 'code',
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'name',
				index : 'name',
				searchoptions : {
					sopt : [ 'cn']
				}
			}];

			gridInitAuto(url, colNames, colModel, '', -1,'#pager',true);
		});

		function loadComplete() {
			var name='${vo.name}';
	    	if(id!=''){
	    		$(this).jqGrid('setSelection', id);
	    	}
		}
		function loadComplete(data) {
			var name='${vo.name}';
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var rData = data.datas[i];
				if(name==(rData.code+rData.name)){
					$(this).jqGrid('setSelection', ids[i]);
				}
			}
		}
		var index = parent.layer.getFrameIndex(window.name); 
		function fnSelect(){
			return $("#listTable").jqGrid("getRowData",getSelectId());
		}
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
    </body>
</html>