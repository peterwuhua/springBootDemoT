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
								<td class="active"><label class="pull-right">入库仪器:</label></td>
								<td>
									${vo.equiptVo.name} ${vo.equiptVo.no}
								</td>
								<td class="width-15 active"><label class="pull-right">入&nbsp;&nbsp;库&nbsp;&nbsp;人:</label></td>
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
										<input id="outUseTime" name="outVo.useTime" class="form-control" type="text" value="${vo.outVo.useTime}" readonly="readonly"/>
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
								<td class="active"><label class="pull-right">入库时间:</label></td>
								<td>
									<div class="input-group date">
										<input id="useTime" name="useTime" class="form-control required" validate="required" placeholder="2018-01-01 12:00" type="text" value="${vo.useTime}"/>
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							 </tr>
							 <tr>
								<td class="active"><label class="pull-right">使用时长:</label></td>
								<td>
									<input id="duration" name="duration" class="form-control required" validate="required" type="text" value="${vo.duration}" />
								</td>
							 </tr>
							<tr>
							 	<td class="active"><label class="pull-right">当前状态:</label></td>
								<td>
									 <div class="radio i-checks">
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="待入库" name="status" <c:if test="${vo.status=='待入库'}">checked=\"checked\"</c:if>>
                                            </div>待入库
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="已入库" name="status"  <c:if test="${vo.status=='已入库'}">checked=\"checked\"</c:if>>
                                            </div>已入库
                                        </label>
                                    </div>
								</td>
							 	<td class="active"><label class="pull-right">仪器状态:</label></td>
								<td>
									 <div class="radio i-checks">
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="正常" name="appStatus" checked="checked">
                                            </div>正常
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="异常" name="appStatus" >
                                            </div>异常
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
	 laydate.render({
	  elem: '#useTime',
	  type: 'datetime',
	  done: function(value, date, endDate){
		  $('#useTime').val(value);
		  countTime()
	  }
	});
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
	var index = parent.layer.getFrameIndex(window.name);
	function fnSelect(){
		var t = $("#thisForm").FormValidate();
		if(t){
			$.ajax({
				url:'${basePath}res/equipmentIn/updateData.do',
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
	function countTime(){
		var startTime=$('#outUseTime').val();
		var endTime=$('#useTime').val();
		var st = new Date(startTime.replace(/\-/g, "/")).getTime();
		var et = new Date(endTime.replace(/\-/g, "/")).getTime();
		var mts=parseInt((et-st)/60000);
		var dt="";
		var day=parseInt(mts/(24*60));
		if(day>0){
			dt+=day+"天";
			mts=mts%(24*60);
		}
		var hours=parseInt(mts/60);
		if(hours>0){
			dt+=hours+"小时";
			mts=mts%60;
		}
		if(mts>0){
			dt+=mts+"分钟";
		}
		$('#duration').val(dt);
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
