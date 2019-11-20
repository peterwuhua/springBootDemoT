<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../../include/css.jsp"%>
<%@ include file="../../../../include/status.jsp"%>
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
select{
padding: 2px;
}
</style>
</head>
<body style="overflow: auto;">
	<div class="col-sm-12" style="min-width: 1600px;">
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<form method="post" action="updateTaskPoint.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table style="margin-bottom: 5px;width: 100%">
						<tr>
							<td style="text-align: right;">温&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度：</td>
							<td style="padding: 2px;">
								 <input id="qw" name="qw" value="${vo.qw}" type="text" class="form-control" placeholder="°C"/>
							</td>
							<td style="text-align: right;">相对湿度：</td>
							<td style="padding: 2px;">
								<input id="sd" name="sd" value="${vo.sd}" type="text" class="form-control" placeholder="%"/>
							</td>
							<td style="text-align: right;">气压：</td>
							<td style="padding: 2px;">
								<input id="qy" name="qy" value="${vo.qy}" type="text" class="form-control" placeholder="KPa"/>
							</td>
							<td style="text-align: right;">风&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;速：</td>
							<td style="padding: 2px;">
								<input id="fs" name="fs" value="${vo.fs}" type="text" class="form-control" placeholder="m/s"/>
							</td>
						</tr>
						<tr>
							<td style="text-align: right;">测&nbsp;量&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
									<select id="fxId" name="fxId"  class="form-control required" validate="required" onchange="checkThis(this);">
										<option value="">-请选择-</option>
										<c:forEach items="${userList}" var="e">
											<c:choose>
												<c:when test="${vo.fxId==e.id}">
													<option value="${e.id}" selected="selected">${e.userName}</option>
												</c:when>
												<c:otherwise>
													<option value="${e.id}">${e.userName}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<input type="hidden" id="fxName" name="fxName" value="${vo.fxName}" >
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
							<td style="text-align: right;">现场描述：</td>
							<td style="padding: 2px;">
								<input id="xcDesc" name="xcDesc" value="${vo.xcDesc}" type="text" class="form-control" placeholder="现场或样品情况" />
							</td>
						</tr>
						<tr>
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
							<td style="text-align: right;">受检单位陪同人：</td>
							<td style="padding: 2px;">
								<input id="ptUser" name="ptUser" value="${vo.ptUser}" type="text" class="form-control" placeholder="" />
							</td>
						</tr>
					</table>
					<table class="table table-bordered" style="margin-bottom: 0px;">
						<thead>
							<tr>
								<th rowspan="3" width="60">序号</th>
								<th rowspan="3" >检测岗位车间/装置</th>
								<th rowspan="3" >检测点</th>
								<th rowspan="3" width="80">工人巡检停留时间/巡检次数</th>
								<th rowspan="3" width="80">接触时间率(%)</th>
								<th rowspan="3" width="80">体力劳动强度</th>
								<th rowspan="3" width="90">类型</th>
								<th colspan="12" >测量结果【℃】</th>
							</tr>
							<tr>
								<th colspan="4">工作开始后0.5h</th>
								<th colspan="4">工作中</th>
								<th colspan="4">工作结束前0.5h</th>
							</tr>
							<tr>
								<th width="80">湿球温度</th>
								<th width="80">黑球温度</th>
								<th width="80">干球温度</th>
								<th width="80">WBGT指数</th>
								<th width="80">湿球温度</th>
								<th width="80">黑球温度</th>
								<th width="80">干球温度</th>
								<th width="80">WBGT指数</th>
								<th width="80">湿球温度</th>
								<th width="80">黑球温度</th>
								<th width="80">干球温度</th>
								<th width="80">WBGT指数</th>
							</tr>
						</thead>
						<tbody id="samp_tb">
							<c:forEach items="${vo.sampList}" var="e" varStatus="v">
								<tr>
							 		<td align="center">
							 			${v.index+1}
								 		<input type="hidden" name="sampList[${v.index}].id" value="${e.id}">
							 			<input type="hidden" name="sampList[${v.index}].recordVo.id" value="${e.recordVo.id}">
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
											<input style="text-align: center;" type="text" class="form-control" name="sampList[${v.index}].fcType" value="${e.fcType}" >
											<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
					 					</div>	
							 		</td>
							 		<td>
							 		 	<div class="input-group" style="width: 100%">
								 		 	<select id="pl_${v.index}"  class="form-control" name="sampList[${v.index}].recordVo.pl"  onchange="changeCtSels(this);return false;">
								 			 	<c:choose>
								 			 		<c:when test="${e.recordVo.pl=='室外'}">
								 			 			<option value="室内">室内</option>
							 			 				<option value="室外" selected="selected">室外</option>
								 			 		</c:when>
								 			 		<c:otherwise>
								 			 			<option value="室内" selected="selected">室内</option>
							 			 				<option value="室外">室外</option>
								 			 		</c:otherwise>
								 			 	</c:choose>
								 			 </select>
											<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyCtSels(this);return false;"></span>
					 					</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="val_${v.index}_1_1" class="form-control "  name="sampList[${v.index}].recordVo.v1" value="${e.recordVo.v1}"  onchange="countVal(1,${v.index});">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyCtVals(this,1);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="val_${v.index}_1_2" class="form-control "  name="sampList[${v.index}].recordVo.v2" value="${e.recordVo.v2}"  onchange="countVal(1,${v.index});">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyCtVals(this,1);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="val_${v.index}_1_3" class="form-control "  name="sampList[${v.index}].recordVo.v3" value="${e.recordVo.v3}"  onchange="countVal(1,${v.index});">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true"  onclick="copyCtVals(this,1);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="value_${v.index}_1" class="form-control "  name="sampList[${v.index}].recordVo.avg1" value="${e.recordVo.avg1}">
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="val_${v.index}_2_1" class="form-control "  name="sampList[${v.index}].recordVo.v4" value="${e.recordVo.v4}"  onchange="countVal(2,${v.index});">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true"  onclick="copyCtVals(this,2);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="val_${v.index}_2_2" class="form-control "  name="sampList[${v.index}].recordVo.v5" value="${e.recordVo.v5}" onchange="countVal(2,${v.index});">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true"  onclick="copyCtVals(this,2);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="val_${v.index}_2_3" class="form-control "  name="sampList[${v.index}].recordVo.v6" value="${e.recordVo.v6}" onchange="countVal(2,${v.index});">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true"  onclick="copyCtVals(this,2);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="value_${v.index}_2" class="form-control "  name="sampList[${v.index}].recordVo.avg2" value="${e.recordVo.avg2}">
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="val_${v.index}_3_1" class="form-control "  name="sampList[${v.index}].recordVo.v7" value="${e.recordVo.v7}"  onchange="countVal(3,${v.index});">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true"  onclick="copyCtVals(this,3);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="val_${v.index}_3_2" class="form-control "  name="sampList[${v.index}].recordVo.v8" value="${e.recordVo.v8}" onchange="countVal(3,${v.index});">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyCtVals(this,3);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="val_${v.index}_3_3" class="form-control "  name="sampList[${v.index}].recordVo.v9" value="${e.recordVo.v9}" onchange="countVal(3,${v.index});">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyCtVals(this,3);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="value_${v.index}_3" class="form-control "  name="sampList[${v.index}].recordVo.avg3" value="${e.recordVo.avg3}">
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

function countVal(n,index){
	var type=$('#pl_'+index).val();
	var v1=parseFloat($('#val_'+index+'_'+n+'_1').val());
	var v2=parseFloat($('#val_'+index+'_'+n+'_2').val());
	var v3=parseFloat($('#val_'+index+'_'+n+'_3').val());
	if(isNaN(v1)){
		v1=0;
	}
	if(isNaN(v2)){
		v2=0;
	}
	if(isNaN(v3)){
		v3=0;
	}
	var v=0;
	if(type=='室外'){
		v=v1*0.7+v2*0.2+v3*0.1;
	}else{
		v=v1*0.7+v2*0.3;
	}
	$('#value_'+index+'_'+n).val(fmtVal(v,2));
}
function copyCtVals(obj,n){
	obj=$(obj);
	var value0=obj.closest('td').find('input').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#samp_tb tr:gt('+indexTr+')').each(function(){
		var tdObj=$(this).find('td').eq(indexTd).find('input').eq(0);
		tdObj.val(value0);
		countVal(n,$(this).index())
	});
}
function changeCtSels(obj){
	var index=$(obj).closest('td').closest('tr').index();
	countVal(1,index);
	countVal(2,index);
	countVal(3,index);
}
function copyCtSels(obj){
	obj=$(obj);
	var value0=obj.closest('td').find('select').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#samp_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('select').eq(0).val(value0);
		countVal(1,$(this).index());
		countVal(2,$(this).index());
		countVal(3,$(this).index());
	});
}
//格式化计算结果保留n个有效数字
function fmtVal(val,n){
	var valStr=val.toString();
	if(valStr.indexOf('.')>0){
		var l=valStr.split(".")[1].length
		if(l>n){
			val=val.toFixed(n);
		}
	}
	return val;
}
</script>
</body>
</html>
