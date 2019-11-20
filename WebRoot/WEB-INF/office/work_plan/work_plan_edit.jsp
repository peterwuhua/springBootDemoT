<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-content" style="padding: 5px 10px;">
				<form method="post" action="${fn:length(vo.id)>0? 'updatePlan.do':'addPlan.do'}" class="form-horizontal" id="listForm">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" id="id" value="${vo.id}" type="hidden" />
					</c:if>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">类型:</label></td>
								<td class="width-35">
									<select  id="type" name="type" class="form-control required" validate="required">
										 
									</select>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">计划人:</label></td>
								<td class="width-35">
									<input readonly="readonly" id="userName" name="userName" class="form-control " type="text" value="${vo.userName}" />
									<input  id="userId" name="userId" type="hidden" value="${vo.userId}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">标题:</label></td>
								<td>
									<input id="title" name="title" class="form-control required" validate="required" placeholder="标题" type="text" value="${vo.title }" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">内容:</label></td>
								<td>
									<textarea rows="2" class="form-control" id="content" placeholder="内容" name="content">${vo.content}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">开始时间:</label></td>
								<td>
									<div class="input-group date">
		                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                           		<input id="startDate" name="startDate" style="width: 100px" class="form-control required dateISO" validate="required" placeholder="开始日期" type="text" value="${vo.startDate}" />
		                            	<span class="input-group-addon">
			                                    <span class="fa fa-clock-o"></span>
			                            </span>
		                            	<input id="startTime" name="startTime" style="width: 100px;" class="form-control required" validate="required"  data-mask="99:99" type="text" value="${vo.startTime}" />
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">结束时间:</label></td>
								<td>
									<div class="input-group date">
		                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
										<input id="endDate" name="endDate" style="width: 100px;" class="form-control required dateISO" validate="required" placeholder="结束日期" type="text" value="${vo.endDate}" />
										<span class="input-group-addon">
			                                    <span class="fa fa-clock-o"></span>
			                            </span>
										<input type="text" id="endTime" style="width: 100px;" name="endTime" class="form-control required" validate="required" data-mask="99:99" placeholder="" value="${vo.endTime}">
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">紧急程度:</label></td>
								<td>
									<div class="radio i-checks">
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="一般" name="urgentLevel"   <c:if test="${vo.urgentLevel=='一般'||vo.urgentLevel==null}">checked</c:if>>
                                            </div>一般
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="重要" name="urgentLevel"  <c:if test="${vo.urgentLevel=='重要'}">checked</c:if>>
                                            </div> 重要
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="非常重要" name="urgentLevel"  <c:if test="${vo.urgentLevel=='非常重要'}">checked</c:if>>
                                            </div> 非常重要
                                        </label>
                                    </div>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
        <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
	<script>
	function typeSelect() {
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=work-plan-type',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					if('${vo.type}'== value[i]){
					optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
							+ value[i] + "</option>";
					}else{
						optionstring += "<option value=\"" + value[i] + "\" >"
							+ value[i] + "</option>";
					}
				}
				$("#type").html(optionstring);
			}
		});
	}
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
		typeSelect();
	});
	var index = parent.layer.getFrameIndex(window.name); 
	function submitSave(){
		var t = $("#listForm").FormValidate();
		var startDate = $("input[name='startDate']").val();
		var startTime = $("input[name='startTime']").val()  
		var endDate = $("input[name='endDate']").val()  
		var endTime = $("input[name='endTime']").val()  
		if(startDate < endDate){
			if(t){
				$.ajax({ 
					url:"addPlan.do",
					dataType:"json",
					data:$('#listForm').serialize(),
					type:"post",
					success: function(data){
						parent.layer.msg(data.message, {icon: 0,time: 3000})
						if("success"==data.status){
							parent.layer.close(index);
						}
					},
					error:function(ajaxobj){  
						layer.msg(ajaxobj, {icon: 0,time: 3000});
				    }  
				});
			}
		}else if(startDate == endDate){
			if(startTime < endTime){
				if(t){
					$.ajax({ 
						url:"addPlan.do",
						dataType:"json",
						data:$('#listForm').serialize(),
						type:"post",
						success: function(data){
							parent.layer.msg(data.message, {icon: 0,time: 3000})
							if("success"==data.status){
								parent.layer.close(index);
							}
						},
						error:function(ajaxobj){  
							layer.msg(ajaxobj, {icon: 0,time: 3000});
					    }  
					});
				}
			}else{
				layer.msg("结束时间不能小于开始时间", {icon: 0,time: 3000});	
			}
			
		}else{
			layer.msg("结束时间不能小于开始时间", {icon: 0,time: 3000});
		}
		
		
	}
	</script>
</body>
</html>