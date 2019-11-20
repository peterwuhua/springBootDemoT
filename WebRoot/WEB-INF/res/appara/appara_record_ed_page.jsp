<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
    <%@ include file="../../include/status.jsp"%>
<style type="text/css">
.form-group{
	margin-bottom: 5px;
	padding: 0px;
}
.form-group > label{
	height: 34px;
	padding-top: 10px;
	padding-left: 0px;
	padding-right: 0px;
	float: left;
}
</style>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                   <div class="ibox-title">
                   		<div class="pull-left">
							<ol class="breadcrumb">
								<li><a>仪器检定/校准</a></li>
								<li><strong>已办列表</strong></li>
							</ol>
						</div>
						<div class="pull-right">
			                <div class="btn-group">
			                    <a type="button" class="btn btn-xs btn-outline btn-default"  onclick="location.href='/res/appara/recordPage.do'">待检定/校准列表</a>
			                    <a type="button" class="btn btn-xs btn-success active">已检定/校准列表</a>
			                </div>
			            </div>
					</div>
                    <div class="ibox-content">
						<form action="gridPaged.do" method="post" id="listForm">
							<input type="hidden" name="ids" id="ids">
							<div class="form-group col-md-3">
		                        <label class="control-label" >年度：</label>
		                        <input style="width: 190px;" id="date" class="form-control" name="date" type="text" value="${vo.date}" />
		                    </div>
							<div class="form-group col-md-3">
		                        <label class="control-label" >编号：</label>
		                        <input style="width: 190px;" id="appNo" name="apparaVo.no" value="${vo.apparaVo.no}" type="text" class="form-control" >
		                    </div>
		                    <div class="form-group col-md-3">
		                    	<label class="control-label" >仪器名称：</label>
		                    	<input style="width: 190px;" id="appName" name="apparaVo.name" value="${vo.apparaVo.name}" type="text" class="form-control" >
		                    </div>
		                    <div class="form-group col-md-1">
								<a class="btn btn-primary" href="javascript:void(0);" onclick="fnSearch();"><i class="fa fa-search" aria-hidden="true"></i> 查询</a>
							</div>
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
</body>
<script type="text/javascript">
$(function() {
	var url = 'gridDatad.do?date='+$('#date').val();
	var colNames = ['设备编号','仪器名称','规格型号','类型','检定/校准人','日期','单位','结论 ','附件','操作'];
	var colModel = [
		{
			name : 'apparaVo.no',
			index : 'appara.no',
			width : 110,
			sortable : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		},  {
			name : 'apparaVo.name',
			index : 'appara.name',
			sortable : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'apparaVo.spec',
			index : 'appara.spec',
			sortable : false,
			width : 100,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'type',
			index : 'type',
			formatter:formatStr,
			sortable : false,
			width : 80,
			stype : 'select',
			searchoptions : {
				sopt : [ 'cn'],
				value:{'':'全部','test':'检定','calibration':'校验'}
			}
		},  {
			name : 'person',
			index : 'person',
			width : 90,
			sortable : false,
			search:false
		}, {
			name : 'date',
			index : 'date',
			width : 100,
			sortable : false,
			search:false
		}, {
			name : 'unit',
			index : 'unit',
			sortable : false,
			search:false
		}, {
			name : 'result',
			index : 'result',
			sortable : false,
			search:false
		}, {
			name : 'fj',
			index : 'trueName',
			sortable : false,
			search:false
		},{
			name : 'act',
			index : 'act',
			width : 70,
			fixed:true,
			title : false,
			search : false,
			sortable : false,
		}];
		gridInitAuto(url, colNames, colModel,20,'#pager');
	});
	function gridInitAuto(url, colNames, colModel,rowNum,pager) {
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
	        search:false,
			gridComplete : gridComplete,
			loadComplete : loadComplete
		});
		setJgridWidth();//宽度
		setJgridHeight();//高度
		fncleanName();
	}
	//设置弹窗中jqgrid表格高度
	function setJgridHeight() {
		var win = getWinSize();
		$("#listTable").jqGrid('setGridHeight', win.WinH-220);
	}
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
	function fnEdit(url){
		layer.open({
			title:'仪器检定-新增/修改',	
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
	function formatStr(cellValue){
		if(cellValue=='test'){
			return '检定';
		}else if(cellValue=='calibration'){
			return '校验';
		}else {
			return '/';
		}
	}
	laydate.render({
		  elem: '#date',
		  theme: 'molv',
		  type: 'year'
	});
	function fnSearch(){
		var postData = $("#listTable").jqGrid("getGridParam", "postData");
		$.extend(postData, {'date':$('#date').val(),'apparaVo.no':$('#appNo').val()
			,'apparaVo.name':$('#appName').val()});
		jQuery("#listTable").jqGrid('setGridParam',{url:'gridDatad.do'}).trigger("reloadGrid")
	}
</script>
</html>
