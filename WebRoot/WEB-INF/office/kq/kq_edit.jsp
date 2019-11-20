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
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>请假申请</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content" style="padding: 5px 10px;">
				<form method="post" action="${fn:length(vo.id)>0? 'updateData.do?isCommit=0':'addData.do?isCommit=0'}" class="form-horizontal" id="listForm">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" id="id" value="${vo.id}" type="hidden" />
					</c:if> 
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label></td>
								<td class="width-35">
									<select name="type" id="type" class="form-control required" validate="required">
										<option value="">请选择</option>
										<c:forEach items="${typeList}" var="e" varStatus="s">
											<c:choose>
												<c:when test="${e==vo.type}">
													<option value="${e}" selected="selected">${e}</option>
												</c:when>
												<c:otherwise>
													<option value="${e}">${e}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<td class="width-15 active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></td>
								<td class="width-35">
									<input readonly="readonly" id="deptName" name="deptName" class="form-control " type="text" value="${vo.deptName}" />
									<input  id="deptId" name="deptId" type="hidden" value="${vo.deptId}" />
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">申&nbsp;&nbsp;请&nbsp;人:</label></td>
								<td class="width-35">
									<input readonly="readonly" id="userName" name="userName" class="form-control " type="text" value="${vo.userName}" />
									<input  id="userId" name="userId" type="hidden" value="${vo.userId}" />
								</td>
								<td class="width-15 active"><label class="pull-right">申请时间:</label></td>
								<td class="width-35">
									<input readonly="readonly" id="date" name="date" class="form-control " type="text" value="${vo.date}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">开始时间:</label></td>
								<td>
									<div class="input-group date">
		                           		<input id="startTime" name="startTime"  class="form-control required" validate="required" placeholder="开始时间" type="text" value="${vo.startTime}" />
		                           		<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
								<td class="active"><label class="pull-right">截止时间:</label></td>
								<td>
									<div class="input-group date">
		                           		<input id="endTime" name="endTime"  class="form-control required" validate="required" placeholder="截止时间" type="text" value="${vo.endTime}"/>
		                           		<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">共计(h):</label></td>
								<td class="width-35">
								   	<input id="hours" name="hours"  class="form-control" type="text" value="${vo.hours}" onfocus="countSc();"/>
								</td>
								<td class="active"><label class="pull-right">工作代理人:</label></td>
								<td>
									<div class="input-group">
										<input type="text" id="jober" name="jober" class="form-control"  placeholder="请选择" value="${vo.jober}" onclick="fnSelectJober()"> 
										<input type="hidden" id="joberId" name="joberId" value="${vo.joberId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectJober()">选择</button>
										</div>
									 </div>
								</td>
							</tr>
							 <tr>
								<td class="active"><label class="pull-right">请假事由:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="content" name="content">${vo.content}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<c:if test="${fn:length(logList)>0}">
						<table class="table table-bordered">
							<thead>
								<tr>
									<th width="50">序号</th>
									<th width="150">节点</th>
									<th width="150">审批人</th>
									<th width="150">审批时间</th>
									<th width="150">审批结果</th>
									<th>审批意见</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${logList}" var="e" varStatus="v">
									<tr>
										<td>${v.index+1}</td>
										<td>${e.busType}</td>
										<td>${e.userName}</td>
										<td>${e.logTime}</td>
										<td>
											<c:choose>
												<c:when test="${e.audit=='提交'}">
													通过
												</c:when>
												<c:otherwise>
													${e.audit}
												</c:otherwise>
											</c:choose>
										</td>
										<td>${e.msg}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" type="button" onclick="fnSubmitSave('saveData.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="submitForm('${fn:length(vo.id)>0? 'updateData.do?isCommit=1':'addData.do?isCommit=1'}')"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script>
	laydate.render({
	    elem: '#startTime',
	    type: 'datetime',
	    format: 'yyyy-MM-dd HH点',
	    theme: 'molv',
	    min:0,
	    calendar: true,
	    done: function(value, date, endDate){
	    	$('#startTime').val(value);
	    	countSc();
	    }
	});
	laydate.render({
	    elem: '#endTime',
	    type: 'datetime',
	    format: 'yyyy-MM-dd HH点',
	    theme: 'molv',
	    min:0,
	    calendar: true,
	    done: function(value, date, endDate){
	    	$('#endTime').val(value);
	    	countSc();
	    }
	});
	function countSc(){
		var st=$('#startTime').val();
		var et=$('#endTime').val();
		if(st=='' || et==''){
			$('#hours').val(0);
		}else{
			st=st.replace('-','/').replace('点','');
			et=et.replace('-','/').replace('点','');
			var date1 = new Date(st+':00')
			var date2 = new Date(et+':00')
			console.log(date1);
			console.log(date2);
			$('#hours').val(getInervalHour(date1,date2));
		}
	}
	function getInervalHour(startDate, endDate) {
        var ms = endDate.getTime() - startDate.getTime();
        if (ms < 0){
        	return 0;
        }else{
        	return Math.floor(ms/1000/60/60);
        }
    }
	function fnSelectJober() {
		layer.open({
			title:'用户信息',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/select.do',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var selectData = iframeWin.fnSelect();
			  $("#jober").val(selectData.name);
			  $("#joberId").val(selectData.id);
			}
		})
	}
	var index = parent.layer.getFrameIndex(window.name); 
	function fnSubmitSave(url){
		var t = $("#listForm").FormValidate();
		if(t){
			$('#auditName').val($('#auditId').find('option[selected="selected"]').text());
			$.ajax({ 
				url:url,
				dataType:"json",
				data:$('#listForm').serialize(),
				type:"post",
				success: function(data){
					parent.layer.msg(data.message, {icon: 0,time: 3000})
					if("success"==data.status){
						backMainPage();
						//parent.layer.close(index);
					}
				},
				error:function(ajaxobj){  
					layer.msg(ajaxobj, {icon: 0,time: 3000});
			    }  
			});
		}
	}
	function submitForm(url){
		$('#listForm').attr('action',url);
		var t = $("#listForm").FormValidate();
		if(t){
			layer.confirm('确认要提交吗?', {icon:3, title:'系统提示'}, function(index){
				$('#listForm').ajaxSubmit(function(res) {
			    	if(res.status=='success'){
			    	    parent.toastr.success(res.message, '');
				        backMainPage();
			        }else{
			        	parent.toastr.error(res.message, '');
			        }
				});
			});
		}
	}
	</script>
</body>
</html>