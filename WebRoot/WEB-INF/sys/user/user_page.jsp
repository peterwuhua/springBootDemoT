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
					<li><a>人员档案</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
				<input type="hidden" name="ids" id="ids">
				<div>
					<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
					<a class="btn btn-primary" href="javascript:void(0);" onclick="openEditPage('edit.do');">新增</a> 
					<a class="btn btn-danger" href="javascript:void(0);" onclick="jqgridDelete();">删除</a>
					<div class="btn-group">
                          <button data-toggle="dropdown" class="btn btn-info dropdown-toggle" aria-expanded="false">导出 <span class="caret"></span></button>
                          <ul class="dropdown-menu">
                              <li><a href="javascript:;" onclick="jqgridExport('sys-user-export.xls','人员信息列表.xls',0)" >全部数据</a>
                              </li>
                              <li><a href="javascript:;" onclick="jqgridExport('sys-user-export.xls','人员信息列表.xls',1)" >选中数据</a>
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
		var url = 'gridData.do';
		var colNames = ['员工编号','姓名','部门','岗位','性别','个人手机','邮箱','操作'];
		var colModel = [ 
			 {
				name : 'no',
				index : 'no',
				width : 60,
				searchhidden:false,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'name',
				index : 'name',
				formatter:formatNo,
				sortable : false,
				width : 60,
				searchoptions : {
					sopt : [ 'cn']
				}
			}, {
				name : 'orgName',
				index : 'orgName',
				sortable : false,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'duty',
				index : 'duty',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'sex',
				index : 'sex',
				sortable : false,
				width : 80,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'mobile',
				index : 'mobile',
				sortable : false,
				width : 100,
				searchoptions : {
					sopt : [ 'cn']
				}
			},{
				name : 'email',
				index : 'email',
				sortable : false,
				width : 100,
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

			gridInitAuto(url, colNames, colModel, '','20','#pager',true)
		});

		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];
				be = '<a class="btn btn-outline btn-success btn-xs" title="修改" href="javascript:openEditPage(\'edit.do?id='+ids[i]+'\')">修改</a>';
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
		
		/**
		 * 获取自定义导出列
		 */
		function fnSelectExport(){
			layer.open({
			  title:'自定义导出列',	
			  type: 2,
			  area: ['70%', '80%'],
			  fix: false, //不固定
			  maxmin: true,
			  content:'column.do',
			  btn: ['确定','取消'],
			  btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var tempInfo = iframeWin.selectTemp();
				  jqgridExport(tempInfo.tempCode,tempInfo.tempName,2)
				}
			})
		}
		function formatNo(cellValue,options,rowObject){
			var ops='<a href="javascript:void(0);" onclick="fnShow(\''+rowObject.id+'\');">'+cellValue+'</a>';
			return ops;
		}
		function fnShow(id){
			var index = layer.open({
				title:'查看',	
				type: 2,
				area: ['1000px','85%'],
				fix: false, //不固定
				maxmin: true,
				content: '/sys/user/show.do?id='+id
			});
		}
	</script> 
</body>
</html>
