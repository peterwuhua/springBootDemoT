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
			<div class="ibox-content">
				<form id="thisForm" method="post" action="updateData.do" class="form-horizontal">
					<input name="id" id="id" value="${vo.id}" type="hidden" />
					<div class="tab-content">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tr>
								<td class="active"><label class="pull-right">仪器名称:</label></td>
								<td>
									${vo.apparaVo.name} ${vo.apparaVo.no}
								</td>
								<td class="width-15 active"><label class="pull-right">出&nbsp;&nbsp;库&nbsp;&nbsp;人:</label></td>
								<td class="width-35">
									${vo.useName}
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">出库时间:</label></td>
								<td>${vo.useTime}</td>
								<td class="active"><label class="pull-right">预计入库时间:</label></td>
								<td>${vo.backTime}</td>
							</tr>
							<tr>
							 	<td class="active"><label class="pull-right">当前状态:</label></td>
								<td>
									${vo.status}
								</td>
							 </tr>
							 <tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">${vo.remark}</td>
							</tr>
							  
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script type="text/javascript">
	function fnSelectUser(){
		var useId=$('#useId').val();
		parent.layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/user_select.do?id='+useId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin=layero.find('iframe')[0];
				var data=iframeWin.contentWindow.fnSelect();
				$('#useId').val(data.id);
				$('#useName').val(data['userVo.name']);
				
			}
		});
	}
	var index = parent.layer.getFrameIndex(window.name);
	function fnSelect(){
		$.ajax({
			url:'${basePath}res/apparaOut/updateData.do',
			type:"POST",
			data:$('#thisForm').serialize(),
			dataType:'json',
			async: false,
			success:function(data){
				if(data.status == "success"){
					parent.jqgridReload();
					parent.layer.msg(data.message, {icon: 0,time: 3000});
					parent.layer.close(index);
				}else{
				   layer.msg(data.message, {icon: 0,time: 3000});
				}
			}
		});
	}
	$(function (){
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
	});
    </script>
</body>
</html>
