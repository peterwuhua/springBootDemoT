<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>

</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<div class="pull-left">
					<ol class="breadcrumb">
						<li><a href="/res/appara/gridPage.do">仪器校准</a></li>
						<li><strong>列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
	                <div class="btn-group">
	                    <a type="button" class="btn btn-xs btn-success active" >待校准列表</a>
	                    <a type="button" class="btn btn-xs btn-outline btn-default" onclick="location.href='/res/apparaRecord/gridPage.do?type=calibration'">已校准列表</a>
	                </div>
	            </div>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div>
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
					<div class="btn-group">
                          <button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">导出 <span class="caret"></span></button>
                          <ul class="dropdown-menu">
                              <li><a href="javascript:;" onclick="jqgridExport('res-appara-export.xls','仪器信息列表.xls',0)" >全部数据</a>
                              </li>
                              <li><a href="javascript:;" onclick="jqgridExport('res-appara-export.xls','仪器信息列表.xls',0)" >选中数据</a>
                              </li>
                          </ul>
                     </div>
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
	<script>
	 
	$(function() {
		var url = 'gridData4type.do?type=calibration';
		var colNames = ['设备编号 ','仪器名称','规格型号','出厂编号 ','仪器分类','保管科室','保管人','状态','操作'];
		var colModel = [
			{
				name : 'no',
				index : 'no',
				width : 120,
				frozen : true,
				resizable : false,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},  {
				name : 'name',
				index : 'name',
				width : 120,
				frozen : true,
				resizable : false,
				editable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'spec',
				index : 'spec',
				editable : false,
				resizable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'code',
				index : 'code',
				width : 100,
				frozen : false,
				resizable : true,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'typeId',
				index : 'typeId',
				width : 100,
				frozen : true,
				resizable : false,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'deptName',
				index : 'deptName',
				editable : true,
				resizable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'keeper',
				index : 'keeper',
				editable : true,
				resizable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'state',
				index : 'state',
				editable : false,
				resizable : true,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'act',
				index : 'act',
				width : 70,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			}];
		gridInitMin(url, colNames, colModel,true);
		});
	
	function gridComplete() {
		var ids = jQuery("#listTable").jqGrid('getDataIDs');
		for (var i = 0; i < ids.length; i++) {
			var cl = ids[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="校准" href="javascript:add(\''+ids[i]+'\',\'calibration\')">校准</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be
			});
		}
	}
	function add(id,type){
		var url='/res/apparaRecord/edit.do?apparaVo.id='+id+'&type='+type;
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
</body>
</html>
