<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
    <%@ include file="../../include/status.jsp"%>
<style type="text/css">
.panel-heading{
	padding: 0px;
}
.col-sm-10{
	padding: 0px 0px 1px 0px;
}
</style>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                   <div class="ibox-title">
                   		<div class="pull-left">
							<ol class="breadcrumb">
								<li><a>仪器维修</a></li>
								<li><strong>列表</strong></li>
							</ol>
						</div>
						<div class="pull-right">
			                <div class="btn-group">
			                    <a type="button" class="btn btn-xs btn-success active" >待维修列表</a>
			                    <a type="button" class="btn btn-xs btn-outline btn-default" href="gridPaged.do">已维修列表</a>
			                </div>
			            </div>
					</div>
                    <div class="ibox-content">
                        <form method="post" action="" class="form-horizontal" enctype="multipart/form-data">
							<form action="gridPage.do" method="post" id="listForm">
								<input type="hidden" name="ids" id="ids">
								<div class="jqGrid_wrapper">
				                      <table id="listTable"></table>
				                      <div id="pager"></div>
				               </div>
						 </form>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
</body>
<script type="text/javascript">
$(function() {
	var url = '/res/appara/gridData4Reppair.do';
	var colNames = ['设备编号','仪器名称','规格型号','保管科室','保管人','操作'];
	var colModel = [
		{
			name : 'no',
			index : 'no',
			width : 110,
			sortable : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		},  {
			name : 'name',
			index : 'name',
			sortable : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'spec',
			index : 'spec',
			sortable : false,
			width : 100,
			searchoptions : {
				sopt : [ 'cn']
			}
		},  {
			name : 'deptName',
			index : 'deptName',
			sortable : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'keeper',
			index : 'keeper',
			sortable : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		},{
			name : 'act',
			index : 'act',
			width : 70,
			fixed:true,
			title : false,
			search : false,
			sortable : false,
		}];
		gridInitMin(url, colNames, colModel,false);
	});
	function loadComplete(data) {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var cl = ids[i];
			var rowData = data.datas[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="维修记录" href="javascript:fnEdit(\'edit.do?apparaVo.id='+rowData.id+'\')">记录</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	function fnEdit(url){
		layer.open({
			title:'仪器维修-新增',	
			type: 2,
			area: ['1000px', '450px'],
			fix: false, //不固定
			maxmin: true,
			content: url,
			btn: ['确定','取消'], //按钮
			yes: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  iframeWin.fnSelect();
			}
		});
	}
</script>
</html>
