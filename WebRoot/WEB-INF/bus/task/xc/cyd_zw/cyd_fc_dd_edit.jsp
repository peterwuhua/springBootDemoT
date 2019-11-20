<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../../include/css.jsp"%>
<%@ include file="../../../../include/status.jsp"%>
 <link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
.form-control{
	padding: 4px;
}
.table>thead>tr>th{
	text-align: center;
}
.table>tbody>tr>td{
	padding: 2px;
}
</style>
</head>
<body style="overflow: auto;">
	<div class="col-sm-12"  style="min-width: 1800px;">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="updateTaskPoint.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table style="margin-bottom: 5px;width: 100%">
						<tr>
							<td style="width: 8%;text-align: right;">温&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td>
							<td style="width: 17%;padding: 2px;">
								 <input id="qw" name="qw" value="${vo.qw}" type="text" class="form-control" placeholder="°C"/>
							</td>
							<td  style="width: 8%;text-align: right;">相对湿度：</td>
							<td style="width: 17%;padding: 2px;">
								<input id="sd" name="sd" value="${vo.sd}" type="text" class="form-control" placeholder="%"/>
							</td>
							<td style="width: 8%;text-align: right;">气压：</td>
							<td style="width: 17%;padding: 2px;">
								<input id="qy" name="qy" value="${vo.qy}" type="text" class="form-control" placeholder="KPa"/>
							</td>
							<td style="width: 8%;text-align: right;">风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：</td>
							<td style="width: 17%;padding: 2px;">
								<input id="fs" name="fs" value="${vo.fs}" type="text" class="form-control" placeholder="m/s"/>
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">采&nbsp;样&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
									<select id="cyId" name="cyId"  class="form-control required" validate="required" onchange="checkThis(this);">
										<option value="">-请选择-</option>
										<c:forEach items="${userList}" var="e">
											<c:choose>
												<c:when test="${vo.cyId==e.id}">
													<option value="${e.id}" selected="selected">${e.userName}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}">${e.userName}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<input type="hidden" id="cyName" name="cyName" value="${vo.cyName}" >
							</td>
							<td style="text-align: right;">校&nbsp;对&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
									<select id="jhId" name="jhId"  class="form-control required" validate="required" onchange="checkThis(this);">
										<option value="">-请选择-</option>
										<c:forEach items="${userList}" var="e">
											<c:choose>
												<c:when test="${vo.jhId==e.id}">
													<option value="${e.id}" selected="selected">${e.userName}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}">${e.userName}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<input type="hidden" id="jhName" name="jhName" value="${vo.jhName}" >
							</td>
							<td style="text-align: right;">测量仪器：</td>
							<td style="padding: 2px;"><select id="cyAppId" name="cyAppId" class="form-control" onchange="checkThis(this);">
									<option value="">请选择</option>
									<c:forEach items="${appList}" var="e">
										<c:choose>
											<c:when test="${vo.cyAppId==e.id}">
												<option value="${e.id}" selected="selected">${e.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${e.id}">${e.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select> 
								<input id="cyAppName" name="cyAppName" value="${vo.cyAppName}" type="hidden" />
							</td>
							<td style="text-align: right;">采样方法：</td>
							<td style="padding: 2px;">
								<%-- <div class="input-group" style="width: 100%">
									<input id="cyStandId" name="cyStandId" value="${vo.cyStandId}" type="hidden" /> 
									<input id="cyStandName" name="cyStandName" value="${vo.cyStandName}" type="text" class="form-control" placeholder="请选择" onclick="chooseCyMothed()" />
								</div> --%>
								<select name="cyStandId" data-placeholder="请选择" class="chosen-select form-control" multiple>
									<c:forEach items="${cyMethodList}" var="e1" >
										<c:choose>
											<c:when test="${fn:contains(vo.cyStandId,e1.id)}">
												<option value="${e1.id}" selected="selected" hassubinfo="true">${e1.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${e1.id}" hassubinfo="true">${e1.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<input id="cyStandName" name="cyStandName" value="${vo.cyStandName}" type="hidden" />
							</td>
						</tr>
						<tr>
								<td style="text-align: right;">受检单位陪同人：</td>
								<td style="padding: 2px;">
									<input id="ptUser" name="ptUser" value="${vo.ptUser}" type="text" class="form-control" placeholder="" />
								</td>
						</tr>
					</table>
					<table class="table table-bordered" style="margin-bottom: 0px;">
						<thead>
							<tr>
								<th rowspan="2" width="140">样品编号</th>
								<th rowspan="2" width="100">仪器编号</th>
								
								<th rowspan="2" width="150">车间/岗位</th>
								<th rowspan="2" width="150">检测点/检测对象</th>
								
								<th rowspan="2" width="80">工人巡检停留时间/巡检次数</th>
								<th rowspan="2" width="80">接触时间(h/d)</th>
								<th colspan="2" >采样流量(L/min)</th>
								<th rowspan="2"  width="100">开始时间</th>
								<th rowspan="2"  width="100">结束时间</th>
								<th rowspan="2" width="100">采样体积(L)</th>
								<th rowspan="2" width="100">标准体积(L)</th>
								<th rowspan="2">采样前滤膜质量</th>
								<th rowspan="2"  width="100">粉尘种类</th>
								<th rowspan="2"  width="120">备注</th>
							</tr>
							<tr>
								<th width="100">采样前</th>
								<th width="100">采样后</th>
							</tr>
						</thead>
						<tbody id="samp_tb">
							<c:forEach items="${vo.sampList}" var="e" varStatus="v">
								<tr>
							 		<td>
							 			<input type="hidden" name="sampList[${v.index}].id" value="${e.id}">
							 			<input type="hidden" name="sampList[${v.index}].recordVo.id" value="${e.recordVo.id}">
							 			<input type="hidden" id="cyHours_${v.index}" name="sampList[${v.index}].pointVo.cyHours" value="${e.pointVo.cyHours}">
							 			<input type="text" class="form-control" name="sampList[${v.index}].sampCode" value="${e.sampCode}" >
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" name="sampList[${v.index}].recordVo.demo1" value="${e.recordVo.demo1}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			${e.pointVo.room}
							 		</td>
						    		<td>
							 			${e.pointName}
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" name="sampList[${v.index}].workPc" value="${e.workPc}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" name="sampList[${v.index}].workHours" value="${e.workHours}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" name="sampList[${v.index}].recordVo.demo2" value="${e.recordVo.demo2}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="ll_h_${v.index}" class="form-control" name="sampList[${v.index}].recordVo.demo3" value="${e.recordVo.demo3}"  onchange="countTj(${v.index})">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyTjVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td><!-- 开始时间 -->
							 			<div class="input-group" style="width: 100%">
								 			<input data-mask="99:99" type="text" id="cyTime_${v.index}" class="form-control required" validate="required" name="sampList[${v.index}].cyTime" value="${e.cyTime}" >
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyTimeVals(this,${v.index});return false;" ></span>
							 			</div>
							 		</td>
							 		<td><!-- 结束时间 -->
							 			<div class="input-group" style="width: 100%">
								 			<input  data-mask="99:99" type="text" id="cyEndTime_${v.index}" class="form-control required" validate="required" name="sampList[${v.index}].cyEndTime" value="${e.cyEndTime}"  onchange="countTj(${v.index})">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyTimeVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="tj_1_${v.index}" class="form-control" name="sampList[${v.index}].num" value="${e.num}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="tj_2_${v.index}" class="form-control" name="sampList[${v.index}].tj" value="${e.tj}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td><!-- 检测结果 -->
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" name="sampList[${v.index}].recordVo.v1" value="${e.recordVo.v1}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" name="sampList[${v.index}].fcType" value="${e.fcType}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" name="sampList[${v.index}].remark" value="${e.remark}" maxlength="128">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12" style="text-align:center;">
							<a class="btn btn-w-m btn-success" type="button" onclick="fnSubmit4Save('updateCyd4Zw.do?isCommit=0')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('updateCyd4Zw.do?isCommit=1')"><i class="fa fa-check" aria-hidden="true"></i> 完成</a>
							<a class="btn btn-w-m btn-white" href="javascript:close();" ><i class="fa fa-times" aria-hidden="true"></i> 关闭</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../../include/js.jsp"%>
 <!-- Chosen -->
<script src="${basePath}h/js/plugins/chosen/chosen.jquery.js"></script>
      <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
<script type="text/javascript">
var index = parent.layer.getFrameIndex(window.name); 
function fnSubmit4Save(url){
	$.ajax({ 
		url:url,
		dataType:"json",
		data:$('#thisForm').serialize(),
		type:"post",
		success: function(data){
			parent.layer.msg(data.message, {icon: 0,time: 3000})
		},
		error:function(ajaxobj){  
			layer.msg(ajaxobj, {icon: 0,time: 3000});
	    }  
	});
}
function fnSubmit(url){
	var t = $("#thisForm").FormValidate();
	if(t){
		$.ajax({ 
			url:url,
			dataType:"json",
			data:$('#thisForm').serialize(),
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
}
function close(){
	parent.layer.close(index);
}
function copyVals(obj){
	obj=$(obj);
	var value0=obj.closest('td').find('input').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#samp_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(value0);
	});
}
//采样标准 弹出层
function chooseCyMothed(){
	var ids=$('#cyStandId').val();
	parent.layer.open({
		title:'采样标准',	
		type: 2,
		area: ['800px', '500px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/sampSource/selects.do?ids='+ids,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin=layero.find('iframe')[0];
			var data=iframeWin.contentWindow.fnSelect();
		    $('#cyStandId').val(data.id);
			$('#cyStandName').val(data.name);
			parent.layer.close(index);
		}
	});
}
function checkThis(obj){
	var t=$(obj).find('option:selected').text();
	$(obj).closest('td').find('input').val(t);
}

$('input[name$=".cyTime"]').each(function(n){
	$('#cyTime_'+n).change(function(){
		countSc(n,$(this).val());
	})
});

function copyTimeVals(obj){
	obj=$(obj);
	var value0=obj.closest('td').find('input').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#samp_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(value0);
		countSc($(this).index(),value0);
	});
}

function countSc(n,value){
	
	var et='';
	var dd=parseInt($('#cyHours_'+n).val());
	if(isNaN(dd)){
		dd=0;
	}
	if(value!='' && value.indexOf(":")>=0){
		var t=value.split(":");
		var hour=parseInt(t[0]);
		var miniter=parseInt(t[1]);
		miniter=miniter+dd;
		if(miniter>=60){
			hour = hour+1;
			miniter = miniter-60;
		}
		if(hour<=9){
			hour='0'+hour;
		}
		if(miniter<=9){
			miniter='0'+miniter;
		}
		et=hour+':'+miniter;
	}
	$('#cyEndTime_'+n).val(et);
	countTj(n);
}
function countTj(n){
	var v=parseFloat($('#ll_h_'+n).val());
	var st=$('#cyTime_'+n).val();
	var et=$('#cyEndTime_'+n).val();
	var dd=countTime(st,et);
	if(isNaN(dd)){
		dd=0;
	}
	var tj=0;
	if(!isNaN(v)){
		tj= v*dd;
	}
	$('#tj_1_'+n).val(tj);
	$('#tj_2_'+n).val(tj);
}
function countTime(st,et){
	var time=0;
	if(st!='' && et!=''){
		var t=st.split(":");
		var hour=parseInt(t[0]);
		if(isNaN(hour)){
			hour=0;
		}
		var miniter=parseInt(t[1]);
		if(isNaN(miniter)){
			miniter=0;
		}
		var s1=hour*60+miniter;
		
		t=et.split(":");
		hour=parseInt(t[0]);
		if(isNaN(hour)){
			hour=0;
		}
		miniter=parseInt(t[1]);
		if(isNaN(miniter)){
			miniter=0;
		}
		var s2=hour*60+miniter;
		time=s2-s1;
	}else{
		time=0;
	}
	 return time;
}
function copyTjVals(obj){
	obj=$(obj);
	var value0=obj.closest('td').find('input').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#samp_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(value0);
		countTj($(this).index());
	});
}
$(document).ready(function(){
	var config = {
	          '.chosen-select': {},
	          '.chosen-select-deselect': {
	              allow_single_deselect: true
	          },
	          '.chosen-select-no-single': {
	              disable_search_threshold: 10
	          },
	          '.chosen-select-no-results': {
	              no_results_text: 'Oops, nothing found!'
	          },
	          '.chosen-select-width': {
	              width: "95%"
	          }
	      }
	      for (var selector in config) {
	          $(selector).chosen(config[selector]);
	      }
})
</script>
</body>
</html>
