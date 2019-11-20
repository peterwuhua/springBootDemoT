<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
.rowBG{
   color:#ed5565;
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
						<li><strong>列表</strong></li>
					</ol>
				</div>
				<div class="pull-right">
	                <div class="btn-group">
	                    <a type="button" class="btn btn-xs btn-success active"  href="javascript:;">待回访列表</a>
	                    <a type="button" class="btn btn-xs btn-outline btn-default" href="gridPaged.do" >已回访列表</a>
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
	<script>
	 
	$(function() {
		//var url = '/cus/customer/gridData4Visit.do';
		var url = '/cus/visit/gridData4Visit.do';
		var colNames = ['客户编号 ','客户名称','客户类型','电话','邮箱','联系人','上次回访日期','操作'];
		var colModel = [
			 {
				name : 'customerVo.code',
				index : 'customerVo.code',
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'customerVo.name',
				index : 'customerVo.name',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'customerVo.cusType',
				index : 'customerVo.cusType',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'customerVo.phone',
				index : 'customerVo.phone',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'customerVo.email',
				index : 'customerVo.email',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'customerVo.person',
				index : 'customerVo.person',
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
			}, {
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
			var rData = data.datas[i];
			be = '<a class="btn btn-outline btn-success btn-xs" title="回访" href="javascript:add(\''+ rData.customerVo.id+'\');">回访</a>';
			jQuery("#listTable").jqGrid('setRowData', rData.id, {
				act : be
			});
			if(rData.status=='1'){
				//jQuery('#'+ids[i]).removeClass("jqgrow");
				jQuery('#'+ rData.id).addClass("rowBG");
			}
		}
	}
	function add(id,type){
		var url='/cus/visit/edit.do?customerVo.id='+id;
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
	};
	</script> 
</body>
</html>
