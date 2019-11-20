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
		var colNames = ['','','编号','单位名称','类型','联系人',"电话","邮箱","邮编"];
		var colModel = [
			{
			name : 'address',
			index : 'address',
			hidden:true,
			title:false,
			search:false
			
			},{
				name : 'id',
				index : 'id',
				hidden:true,
				title:false,
				search:false
			},{
				name : 'code',
				index : 'code',
				width : 80,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'name',
				index : 'name',
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'type',
				index : 'type',
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'person',
				index : 'person',
				width : 70,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'phone',
				index : 'phone',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'email',
				index : 'email',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'zip',
				index : 'zip',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];
			gridInitAuto(url, colNames, colModel, '', 10,'#pager',true);
		});
		function loadComplete() {
			var ids='${vo.ids}';
			setHiden(ids)
		}
		function setHiden(ids){
			if(null!=ids){
				var ids = ids.replace("，",",");
				var s = ids.split(",");
				for ( var i = 0; i <= s.length; i++){
					 $('#listTable').setRowData(s[i],null,{display: 'none'});
				}
			}
		}
		var index = parent.layer.getFrameIndex(window.name); 
		function fnSelect(){
			var data=[];
			var ids=getSelectIds();
			for (var i = 0; i <ids.length; i++){
				data.push($("#listTable").jqGrid("getRowData",ids[i]));
			}
			return data;
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
				loadComplete : loadComplete
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
    </body>
</html>