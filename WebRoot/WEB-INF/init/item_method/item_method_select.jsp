<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
</head>
<body>
	<div class="col-sm-12">
	<div class="ibox float-e-margins">
		<div class="">
				<%@ include file="../../include/status.jsp"%>
				<form method="post" id="listForm">
					<input type="hidden" id="methodId" name="methodVo.id" value="${vo.methodVo.id}">
					<input type="hidden" id="itemId" name="itemVo.id" value="${vo.itemVo.id}">
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
		var url = '${basePath}init/method/gridData.do';
		var editurl='';
		var colNames = ['编号','条款','名称'];
		var colModel = [ 
			 {
				name : 'code',
				index : 'code',
				width : 150,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'chapter',
				index : 'chapter',
				width : 100,
				fixed:true,
				searchoptions : {
					sopt : ['cn']
				}
			},{
				name : 'name',
				index : 'name',
				searchoptions : {
					sopt : ['cn']
				}
			}];
			gridInitAuto(url, colNames, colModel, '', 10,'#pager',true);
			var win = getWinSize();
			$("#listTable").jqGrid('setGridHeight', win.WinH-120);
		});
		function gridComplete() {
			 var mids='${vo.methodVo.id}';
			 setHiden(mids);
		}
		function setHiden(ids){
			var ids = ids.replace("，",",");
			var s = ids.split(",");
			for ( var i = 0; i <= s.length; i++){
				if(s[i]!=''){
					 $('#listTable').setRowData(s[i],null,{display: 'none'});
				}
			}
		}
		function fnSelectM(){
			var selectIds = getSelectIds();
			var itemId='${vo.itemVo.id}';
			if(selectIds.length<1){
				layer.msg('请选择要添加的方法', {icon: 0,time: 3000});
				return true;
			}else{
				$.ajax({ 
					url:"addMethod.do",
					data: {'itemVo.id':itemId, 'methodVo.ids':selectIds.join(',')},
					async:false,
					success: function(data){
						if("success"==data.status){
							parent.location.reload();
							parent.layer.msg(data.message, {icon: 0,time: 3000});
							parent.layer.close(index);
						}else{
							layer.msg(data.message, {icon: 0,time: 3000});
						}
					},
					error:function(ajaxobj){  
						layer.msg(ajaxobj, {icon: 0,time: 3000});
				    }  
				});
			}
		}
	</script> 
    </body>
</html>