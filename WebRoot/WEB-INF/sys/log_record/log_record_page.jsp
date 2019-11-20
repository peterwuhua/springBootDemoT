<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
#listTable td{
	padding-left: 2px;
	padding-right: 2px;
}
</style>
</head>
<body class="gray-bg">
<div class="col-sm-12">
	<div class="ibox float-e-margins">
		<div class="ibox-title">
			<ol class="breadcrumb">
				<li><a>日志</a></li>
				<li><strong>列表</strong></li>
			</ol>
		</div>
		<div class="ibox-content">
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
<script>
	$(function() {
		var url = 'gridData.do';
		var editurl='gridEdit.do';
		var colNames = ['时间','IP','操作人','模块','操作','描述','参数'];
		var colModel = [ 
	   			 {
	   				name : 'ctime',
	   				index : 'ctime',
	   				width : 50,
	   				searchoptions : {
	   					sopt : ['cn']
	   				}
	   			},{
					name : 'ip',
					index : 'ip',
					width : 60,
					searchoptions : {
						sopt : ['cn']
					}
				},{
					name : 'user',
					index : 'user',
					width : 50,
					searchoptions : {
						sopt : ['cn']
					}
				},{
					name : 'module',
					index : 'module',
					width : 30,
					searchoptions : {
						sopt : ['cn']
					}
				}, {
	   				name : 'opt',
	   				index : 'opt',
	   				width : 20,
	   				searchoptions : {
	   					sopt : ['cn']
	   				}
	   			}, {
	   				name : 'content',
	   				index : 'content',
	   				width : 50,
	   				searchoptions : {
	   					sopt : ['cn']
	   				}
	   			}, {
	   				name : 'params',
	   				index : 'params',
	   				searchoptions : {
	   					sopt : [ 'cn']
	   				},
	   			} ];
			gridInitAuto(url, colNames, colModel, '', 10,'#pager',false);
		});
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-210);
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
	        loadComplete : loadComplete,
	        ondblClickRow:function(rowid){
	        	fnShow(rowid);
	        }
		});
		setFilterToolbar();//行内查询
		setJgridWidth();//宽度
		setJgridHeight();//高度
		setNavGrid();//功能按钮
		jQuery("#listTable").jqGrid('setFrozenColumns'); //冻结表头
		$(".ui-search-oper").hide();
		fncleanName();
	}
	function fnShow(id){
		var index = layer.open({
			title: "更新内容",
			type: 2,
			area: ['800px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/sys/logRecord/show.do?id='+id
		});
	}
	</script> 
	
</body>
</html>

</html>