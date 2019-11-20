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
				<h5>
					<ol class="breadcrumb">
						<li><a href="${basePath}cus/customer/gridPage.do">客户</a></li>
						<li><strong>账户信息编辑</strong></li>
					</ol>
				</h5>
				<div class="ibox-tools">
					<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
				</div>
			</div>
			<div class="ibox-content">
			 <div class="panel-heading">
               <div class="panel-options">
					<ul class="nav nav-tabs">
					    <li>
					        <a href="javascript:;" onclick="location.href='${basePath}cus/customer/edit.do?id=${vo.customerVo.id}'" data-toggle="tab">
					           	 客户信息
					        </a>
					    </li>
					    <li><a href="javascript:;" onclick="location.href='${basePath}cus/contacts/gridTab.do?customerVo.id=${vo.customerVo.id}'" data-toggle="tab">联系人</a></li>
					    <li><a href="javascript:;" onclick="location.href='${basePath}cus/cusClient/gridTab.do?customerVo.id=${vo.customerVo.id}'" data-toggle="tab">受检单位</a></li>
					    <li><a href="javascript:;" onclick="location.href='${basePath}cus/cusProduce/gridTab.do?customerVo.id=${vo.customerVo.id}'" data-toggle="tab">生产单位</a></li>
					    <li class="active"><a href="javascript:;" data-toggle="tab">账户信息</a></li>
					    <li><a href="javascript:;" onclick="location.href='${basePath}cus/contract/gridTab.do?customerVo.id=${vo.customerVo.id}'" data-toggle="tab">合同信息</a></li>  
					</ul>
			    </div>
			    </div>
				<%@ include file="../../include/status.jsp"%>
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" value="${vo.customerVo.id}" name="customerVo.id" />
				<div class="col-sm-7">
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a>
					<a class="btn btn-primary" href="javascript:fnEidt();">新增</a>
					<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
					<a class="btn btn-primary" href="import.do?customerVo.id=${vo.customerVo.id}">导入</a>
					<div class="btn-group">
                          <button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">导出 <span class="caret"></span></button>
                          <ul class="dropdown-menu">
                              <li><a href="javascript:;" onclick="jqgridExport('cus-finance-export.xls','账户信息列表.xls',0)" >全部数据</a>
                              </li>
                              <li><a href="javascript:;" onclick="jqgridExport('cus-finance-export.xls','账户信息列表.xls',1)" >选中数据</a>
                              </li>
                          </ul>
                      </div>
					<a href="${basePath}cus/customer/gridPage.do" class="btn btn-white" type="submit">返回</a>
				</div>
				<input type="hidden" name="ids" id="ids">
				<div class="col-sm-5"></div>
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
		var url = '/cus/finance/gridData4Tab.do?customerVo.id=${vo.customerVo.id}';
		var editurl='/cus/finance/gridEdit.do?customerVo.id=${vo.customerVo.id}';
		var colNames = ['操作','单位名称','地址','开户银行','账号','行号','同城结算号','税务登记号'];
		var colModel = [
			 {
   				name : 'act',
   				index : 'act',
   				width : 60,
   				title : false,
   				search : false,
   				frozen : true,
   				resizable : false,
   				sortable : false,
   			}, {
				name : 'name',
				index : 'name',
				width : 200,
				frozen : true,
				resizable : false,
				editable : true,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'address',
				index : 'address',
				editable : true,
				resizable : true,
				width : 200,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'bank',
				index : 'bank',
				editable : true,
				resizable : true,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'accountNum',
				index : 'accountNum',
				editable : true,
				resizable : true,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'bankNum',
				index : 'bankNum',
				editable : true,
				resizable : true,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'settlementNum',
				index : 'settlementNum',
				editable : true,
				resizable : true,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'registerNum',
				index : 'registerNum',
				editable : true,
				resizable : true,
				width : 150,
				searchoptions : {
					sopt : [ 'cn']
				}
			}];

			pageInit(url, colNames, colModel, editurl);
		});

		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:fnEidt(\''+ids[i]+'\');">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
		/**
		 * 设置树组件高度与jqgrid表格的高度随着窗口大小的改变而改变
		 * 
		 * 此事件在窗口大小改变时触发
		 */
		window.onresize = function doResize() {
			 var win = getWinSize();
			 //$(".treeboxheight").height(($(window).height()-128));
			 $("#listTable").jqGrid('setGridWidth', win.WinW-10).jqGrid('setGridHeight', win.WinH-325);
		}
		/**
		 * 设置grid高度
		 */
		function setJgridHeight() {
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-325);
		}
		/**
		 * 删除
		 */
		function jqgridDelete(){
			var selectIds = getSelectIds();
			if(selectIds.length<1){
				layer.msg('请选择要删除的记录', {icon: 0,time: 3000});
				return false;
			}
			layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$("#ids").val(selectIds);
				$("#listForm").attr("action","update2del4Tab.do");
				$("#listForm").submit();
			});
		}
	</script>
	<script type="text/javascript">
		function fnEidt(parm){
			var id = parm;
			if(id == null || id == undefined || id == ''){
				id = "";
			}
			layer.open({
				title:'账户信息',	
				type: 2,
				area: ['830px', '470px'],
				fix: false, //不固定
				maxmin: true,
				content: 'editTab.do?id='+id+'&customerVo.id=${vo.customerVo.id}',
				btn: ['确定','取消'], //按钮
				yes: function(index, layero) {
					 var iframeWin = window[layero.find('iframe')[0]['name']];
					 var frameName = '_blank';
					 if(top!=this){
						 frameName= self.frameElement.name;
					 }
					 iframeWin.saveTable(frameName);
				}
			});
		}
	</script> 
</body>
</html>
