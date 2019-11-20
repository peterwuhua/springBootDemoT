<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
<div class="col-sm-12">
	<div class="ibox float-e-margins">
		<div class="ibox-title">
			<ol class="breadcrumb">
				<li><a href="gridPage.do">角色</a></li>
				<li><strong>列表</strong></li>
			</ol>
		</div>
		<div class="ibox-content">
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div>
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" href="javascript:void(0);" onclick="openEditPage('edit.do');">新增</a> 
					<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
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
		var colNames = [ '编码','名称','说明','排序','操作 ',];
		var colModel = [ 
			 {
				name : 'code',
				index : 'code',
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'name',
				index : 'name',
				width : 150,
				fixed:true,
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'describtion',
				index : 'describtion',
				sortable : false,
				searchoptions : {
					sopt : ['cn']
				}
			}, {
				name : 'sort',
				index : 'sort',
				width : 60,
				fixed:true,
				align:"center",
				searchoptions : {
					sopt : [ 'cn']
				},
			},{
				name : 'act',
				index : 'act',
				width :180,
				fixed:true,
				title : false,
				search : false,
				sortable : false,
			},];
			gridInitAuto(url, colNames, colModel, '','20','#pager',true)
		});
		
		function loadComplete(data){
			for (var i = 0; i < data.datas.length; i++) {
				var cl = data.datas[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+cl.id+'\');">修改</a>';
				ce = '<a class="btn btn-outline btn-success btn-xs" href="javascript:fnSelect(\''+cl.id+'\',\''+cl.name+'\');">功能</a>';
				de = '<a class="btn btn-outline btn-success btn-xs" href="javascript:fnSelectFunPerm(\''+cl.id+'\',\''+cl.name+'\');">权限</a>';
				fe = '<a class="btn btn-outline btn-success btn-xs" href="javascript:fnUserShow(\''+cl.id+'\');">账户</a>';
				jQuery("#listTable").jqGrid('setRowData', cl.id, {
					act : be+" "+ce+" "+de+" "+fe
				});
			}
		}
	</script>
	<script>
		function fnSelect(roleId,roleName){
			 layer.open({
			  title:'功能:'+roleName,	
			  type: 2,
			  area: ['350px', '75%'],
			  fix: false, //不固定
			  maxmin: true,
			  content: 'preSavePermission.do?roleId='+roleId,
				  btn: ['确定','取消'], //按钮
				  yes: function(index, layero) {
					  var iframeWin = window[layero.find('iframe')[0]['name']];
					  iframeWin.fnSelect();
					}
			});
		}
		function fnSelectFunPerm(roleId,roleName){
			layer.open({
				  title:'权限：'+roleName,	
				  type: 2,
				  area: ['800px', '85%'],
				  fix: false, //不固定
				  maxmin: true,
				  content: '${basePath}sys/rolePerm/preSaveFunPerm.do?roleId='+roleId
				});
		}
		function fnUserShow(roleId){
			layer.open({
				  title:'账户',	
				  type: 2,
				  area: ['800px', '75%'],
				  fix: false, //不固定
				  maxmin: true,
				  content: 'accountEdit.do?roleId='+roleId
				});
		}
	</script> 
</body>
</html>
