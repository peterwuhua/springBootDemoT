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
								<li><a>仪器校准</a></li>
								<li><strong>已办列表</strong></li>
							</ol>
						</div>
						<div class="pull-right">
			                <div class="btn-group">
			                    <a type="button" class="btn btn-xs btn-outline btn-default"  onclick="location.href='/res/appara/recordPage.do?type=calibration'">待校准列表</a>
			                    <a type="button" class="btn btn-xs btn-success active">已校准列表</a>
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
                            <div class="hr-line-dashed"></div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
</body>
<script type="text/javascript">
$(function() {
	var url = 'gridData.do?type=calibration&apparaVo.id='+$("#apparaId").val();
	var editurl='gridEdit.do';
	var colNames = ['设备编号','仪器名称','规格型号','校准人','校准时间','校准单位','结论 ','附件','操作'];
	var colModel = [
		{
			name : 'apparaVo.no',
			index : 'appara.no',
			width : 110,
			frozen : true,
			resizable : false,
			editable : true,
			searchoptions : {
				sopt : [ 'cn']
			}
		},  {
			name : 'apparaVo.name',
			index : 'appara.name',
			frozen : true,
			resizable : false,
			editable : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'apparaVo.spec',
			index : 'appara.spec',
			editable : false,
			resizable : true,
			width : 100,
			searchoptions : {
				sopt : [ 'cn']
			}
		},{
			name : 'person',
			index : 'person',
			width : 70,
			sortable : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'date',
			index : 'date',
			width : 100,
			sortable : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'unit',
			index : 'unit',
			sortable : false,
			width : 70,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'result',
			index : 'result',
			sortable : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'fj',
			index : 'trueName',
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
			if(rowData.trueName){
			fj = '<a class="btn btn-xs btn-info" title="查看" href="download.do?filePath='+rowData.fileUrl+'&trueName='+rowData.trueName+'" >'+rowData.trueName+'</a>';
			}else{
			fj = '';	
			}
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:fnEdit(\'edit.do?id='+rowData.id+'\')">修改</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be,
				fj : fj
			});
		}
	}
	function add(id,type){
		var url='edit.do?apparaVo.id='+id+'&type='+type;
		layer.open({
			title:'校准记录-新增/修改',	
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
	function fnEdit(url){
		layer.open({
			title:'校准记录-新增/修改',	
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
