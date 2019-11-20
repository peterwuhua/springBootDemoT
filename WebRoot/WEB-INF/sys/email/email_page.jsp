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
			<ol class="breadcrumb">
				<li><a href="gridPage.do">邮件</a></li>
				<li><strong>列表</strong></li>
			</ol>
		</div>
		<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div class="pull-left">
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" href="javascript:openEditPage('edit.do')">新增</a> 
					<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
				</div>
				<div class="pull-right">
					<a class="btn btn-warning" href="javascript:void(0);" onclick="fnCfg();"><i class="fa fa-cog" aria-hidden="true"> 邮箱配置</i></a> 
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
		var url = 'gridData.do';
		var editurl='gridEdit.do';
		var colNames = ['收件人','发送时间','主题','操作'];
		var colModel = [ 
			{
				name : 'receiver',
				index : 'receiver',
				editable : true,
				frozen : true,
				resizable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sendTime',
				index : 'sendTime',
				editable : false,
				searchoptions : {
					sopt : [ 'cn','ne','eq','le','lt','gt','ge']
				},
			}, {
				name : 'subject',
				index : 'subject',
				frozen : true,
				resizable : false,
				width : 300,
				editable : true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'act',
				index : 'act',
				width : 120,
				fixed:true,
				title : false,
				search : false,
				sortable : false
			}];
			gridInitMin(url, colNames, colModel,true);
		});

		function loadComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				ae = '<a class="btn btn-outline btn-success btn-xs" title="详情" href="javascript:openEditPage(\'show.do?id='+ids[i]+'\')">详情</a>';
				be = '<a class="btn btn-outline btn-success btn-xs" title="再次发送" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">再次发送</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : ae+' '+be
				});
			}
		}
		function fnCfg(){
			layer.open({
				title:'邮箱配置',	
				type: 2,
				area: ['500px', '400px'],
				fix: false, //不固定
				maxmin: true,
				content: 'editCfg.do',
				btn: ['确定','关闭'], //按钮
				yes: function(index, layero){
					var iframeWin = window[layero.find('iframe')[0]['name']];
					  iframeWin.submitSave();
				}
			});
		}
	</script> 
</body>
</html>