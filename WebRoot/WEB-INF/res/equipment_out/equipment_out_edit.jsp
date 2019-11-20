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
								<td class="width-15 active"><label class="pull-right">仪器名称:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="appName" name="equiptVo.name" class="form-control required" validate="required" value="${vo.equiptVo.name}" autocomplete="off" onclick="fnSelectApp()">
										<input type="hidden" id="appId" name="equiptVo.id"  value="${vo.equiptVo.id}"/> 
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button"  onclick="fnSelectApp()">选择</button>
										</div>
									</div>
								</td>
								<td class="width-15 active"><label class="pull-right">出&nbsp;&nbsp;库&nbsp;&nbsp;人:</label></td>
								<td class="width-35">
									 <div class="input-group" style="width: 100%">
										<input type="hidden" id="useId" name="useId" value="${vo.useId}">
										<input type="text" id="useName" name="useName" class="form-control required" validate="required" value="${vo.useName}" onclick="fnSelectUser()">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">出库时间:</label></td>
								<td>
									<div class="input-group date">
										<input id="useTime" name="useTime" class="form-control datetimeISO required" validate="required" placeholder="2018-01-01 12:00" type="text" value="${vo.useTime}" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
								<td class="active"><label class="pull-right">预计入库时间:</label></td>
								<td>
									<div class="input-group date">
										<input id="backTime" name="backTime" class="form-control datetimeISO required" validate="required" placeholder="2018-01-01 12:00" type="text" value="${vo.backTime}" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							 </tr>
							<tr>
							 	<td class="active"><label class="pull-right">当前状态:</label></td>
								<td colspan="3">
									 <div class="radio i-checks">
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="待出库" name="status" <c:if test="${vo.status=='待出库'}">checked=\"checked\"</c:if>>
                                            </div>待出库
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="已出库" name="status"  <c:if test="${vo.status!='待出库'}">checked=\"checked\"</c:if>>
                                            </div>已出库
                                        </label>
                                    </div>
								</td>
							 </tr>
							 <tr>
								<td class="active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3"><textarea maxlength="128" rows="2" placeholder="请输入备注" name="remark" class="form-control">${vo.remark}</textarea></td>
							</tr>
							  
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script type="text/javascript">
	var index = parent.layer.getFrameIndex(window.name);
	function fnSelectApp(){
		var id=$('#appId').val();
		parent.layer.open({
			title:'使用仪器',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}res/equipment/select.do?id='+id,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin=layero.find('iframe')[0];
			  var data=iframeWin.contentWindow.fnSelect();
			  $('#appName').val(data.name);
			  $('#appId').val(data.id);
			}
		});
	}
	function fnSelectUser(){
		var userId=$('#useId').val();
		parent.layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/select.do?id='+userId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin=layero.find('iframe')[0];
			  var data=iframeWin.contentWindow.fnSelect();
			   $('#useName').val(data.name);
			   $('#useId').val(data.id);
			}
		});
	}
	function submitSave(){
		var t = $("#thisForm").FormValidate();
		var url='${basePath}res/equipmentOut/addData.do';
		var id=$('#id').val();
		if(id!=''){
			url='${basePath}res/equipmentOut/updateData.do'
		}
		if(t){
			$.ajax({
				url:url,
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
