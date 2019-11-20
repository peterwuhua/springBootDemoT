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
					<li><a>打卡记录</a></li>
					<li><strong>列表</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form action="gridPage.do" method="post" id="listForm">
					<input type="hidden" name="ids" id="ids">
					<div class="pull-left">
						<a class="btn btn-success" href="javascript:void(0);" onclick="jqgridReload();">刷新</a> 
						<a class="btn btn-primary" href="javascript:;" onclick="fnAdd()"><i class="fa fa-plus"> 打卡</i></a> 
						<a class="btn btn-primary" href="javascript:;" onclick="fnEdit('')"><i class="fa fa-plus"> 补卡</i></a>
					</div>
					<div class="pull-right">
						<c:if test="${isEdit}">
							<a class="btn btn-warning" href="javascript:void(0);" onclick="fnCfg();"><i class="fa fa-cog" aria-hidden="true"> 工作时间配置</i></a> 
						</c:if>
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
	function fnAdd(){
		var msg=checkTime();
		if(msg!=null&&msg!=''){
			layer.msg(msg, {icon: 0,time: 3000});
		}else{
			$.ajax({ 
				url:"add2Dk.do",
				dataType:"json",
				type:"post",
				success: function(data){
					if("success"==data.status){
						 location.reload();
					}
					parent.layer.msg(data.message, {icon: 0,time: 3000})
				},
				error:function(ajaxobj){  
					layer.msg(ajaxobj, {icon: 0,time: 3000});
			    }  
			});
		}
	}
	function checkTime(){
		var msg;
		$.ajax({ 
			url:"checkTime.do",
			dataType:"json",
			type:"post",
			async:false,
			success: function(data){
				msg=data.message
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }  
		});
		return msg;
	}
	function fnEdit(id){
		layer.open({
			title:'新增/修改',	
			type: 2,
			area: ['600px', '420px'],
			fix: false, //不固定
			maxmin: true,
			content: 'edit.do?id='+id,
			end: function () {
                location.reload();
            }
		});
	}
	$(function() {
		var url = 'gridData.do';
		var editurl='gridEdit.do';
  		var colNames = ['类型','部门','打卡人','打卡时间','描述','状态','操作'];
		var colModel = [ 
			 {
				name : 'type',
				index : 'type',
				sortable : false,
				width : 100,
				fixed:true,
				stype : 'select',
				searchoptions : {
					sopt : ['eq'],
					value:{'':'全部','打卡':'打卡','补卡':'补卡'}
				}
			}, {
				name : 'deptName',
				index : 'deptName',
				width : 150,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'userName',
				index : 'userName',
				width : 150,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'date',
				index : 'date',
				width : 150,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'content',
				index : 'content',
				sortable : false,
				search : false,
			},{
				name : 'status',
				index : 'status',
				width : 100,
				fixed:true,
				sortable : false,
				search : false,
			},{
				name : 'act',
				index : 'act',
				width : 150,
				fixed:true,
				title : false,
				search : false,
				sortable : false
			}];
		gridInitMin(url, colNames, colModel,false);
		});
		function gridComplete() {
			var ids = jQuery("#listTable").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var rData = $('#listTable').jqGrid('getRowData',ids[i]);
				var be = '<a class="btn btn-outline btn-success btn-xs" title="查看" href="javascript:fnShow(\''+ ids[i]+'\')">查看</a>';
				if(rData.status=='已保存'){
					be += '  <a class="btn btn-outline btn-info btn-xs" title="修改" href="javascript:fnEdit(\''+ ids[i]+'\')">修改</a>';
					be += '  <a class="btn btn-outline btn-danger btn-xs" title="删除" href="javascript:fnDel(\''+ ids[i]+'\')">删除</a>';
				}
				jQuery("#listTable").jqGrid('setRowData', ids[i], {
					act : be
				});
			}
		}
		/**
		 * 删除
		 */
		function fnDel(id){
			layer.confirm('确认要删除吗?', {icon:3, title:'系统提示'}, function(index){
				$.ajax({ 
					url:"deleteOne.do",
					dataType:"json",
					data:{'id':id},
					type:"post",
					success: function(data){
						if("success"==data.status){
							 location.reload();
						}
						parent.layer.msg(data.message, {icon: 0,time: 3000})
					},
					error:function(ajaxobj){  
						layer.msg(ajaxobj, {icon: 0,time: 3000});
				    }  
				});
			});
		}
		function fnShow(id){
			layer.open({
				title:'查看',	
				type: 2,
				area: ['600px', '400px'],
				fix: false, //不固定
				maxmin: true,
				content: 'show.do?id='+id
			});
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
