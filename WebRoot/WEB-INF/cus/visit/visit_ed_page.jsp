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
								<li><a>客户回访</a></li>
								<li><strong>已办列表</strong></li>
							</ol>
						</div>
						<div class="pull-right">
			                <div class="btn-group">
			                    <a type="button" class="btn btn-xs btn-outline btn-default"  href="gridPage.do">待回访列表</a>
			                    <a type="button" class="btn btn-xs btn-success active" href="javascript:;">已回访列表</a>
			                </div>
			            </div>
					</div>
                    <div class="ibox-content">
						<form action="gridPage.do" method="post" id="listForm">
							<input type="hidden" name="ids" id="ids">
							<div>
								<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
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
	var url = 'gridData.do';
	var colNames = ['客户编号 ','客户名称','客户类型','电话','邮箱','联系人','回访日期','回访人','操作'];
	var colModel = [
		 {
			name : 'customerVo.code',
			index : 'customer.code',
			width : 100,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'customerVo.name',
			index : 'customer.name',
			sortable : false,
			searchoptions : {
				sopt : [ 'cn']
			}
		},{
			name : 'customerVo.cusType',
			index : 'customer.cusType',
			sortable : false,
			width : 100,
			searchoptions : {
				sopt : [ 'cn']
			}
		},{
			name : 'customerVo.phone',
			index : 'customer.phone',
			sortable : false,
			width : 100,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'customerVo.email',
			index : 'customer.email',
			sortable : false,
			width : 100,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'customerVo.person',
			index : 'customer.person',
			sortable : false,
			width : 70,
			searchoptions : {
				sopt : [ 'cn']
			}
		},{
			name : 'vdate',
			index : 'vdate',
			resizable : true,
			width : 100,
			searchoptions : {
				sopt : [ 'cn']
			}
		},{
			name : 'userName',
			index : 'userName',
			resizable : true,
			width : 100,
			searchoptions : {
				sopt : [ 'cn']
			}
		}, {
			name : 'act',
			index : 'act',
			width : 100,
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
			be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:fnEdit(\'edit.do?id='+rowData.id+'\')">修改</a>';
			ce = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+rowData.id+'\')">查看</a>';
			jQuery("#listTable").jqGrid('setRowData', ids[i], {
				act : be+' '+ce
			});
		}
	}
	function fnEdit(url){
		layer.open({
			title:'客户回访',	
			type: 2,
			area: ['800px', '450px'],
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
	function fnShow(id){
		layer.open({
			title:'客户回访',	
			type: 2,
			area: ['800px', '450px'],
			fix: false, //不固定
			maxmin: true,
			content: 'show.do?id='+id
		});
	}
</script>
</html>
