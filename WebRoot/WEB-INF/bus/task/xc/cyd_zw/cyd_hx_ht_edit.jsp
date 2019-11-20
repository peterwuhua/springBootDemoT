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
</style>
</head>
<body style="overflow: auto;">
	<div class="col-sm-12">
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
							<td style="text-align: right;">测&nbsp;试&nbsp;&nbsp;人：</td>
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
							<td style="text-align: right;">校&nbsp;核&nbsp;&nbsp;人：</td>
							<td style="padding: 2px;">
									<select id="fxId" name="jhId"  class="form-control required" validate="required" onchange="checkThis(this);">
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
								<th width="60" rowspan="2">序号</th>
								<th width="10%" rowspan="2">车间/岗位</th>
								<th width="10%" rowspan="2">检测点</th>
								<th width="80" rowspan="2">接触时间</th>
								<th width="80" rowspan="2">测量时间</th>
								<th width="90" colspan="3">仪器读值</th>
								<th width="90" colspan="3">检测结果</th>
								<th width="80" rowspan="2">Cstel</th>
								<th width="80" rowspan="2">Ctwa</th>
								<th width="10%" rowspan="2">备注</th>
								<th rowspan="2">检测项目</th>
							</tr>
							<tr>
								<th width="80">1</th>
								<th width="80">2</th>
								<th width="80">3</th>
								<th width="80">1</th>
								<th width="80">2</th>
								<th width="80">3</th>
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
								 			<input type="text" id="wh_${v.index}" class="form-control" name="sampList[${v.index}].workHours" value="${e.workHours}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input data-mask="99:99" type="text" class="form-control  required" validate="required" name="sampList[${v.index}].cyTime" value="${e.cyTime}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="v_${v.index}_1" class="form-control "  name="sampList[${v.index}].recordVo.v1" value="${e.recordVo.v1}" onchange="countVal(this,1,'${e.itemNames}',${v.index});">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyCtVals(this,1,'${e.itemNames}');return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="v_${v.index}_2" class="form-control "  name="sampList[${v.index}].recordVo.v2" value="${e.recordVo.v2}" onchange="countVal(this,2,'${e.itemNames}',${v.index});">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyCtVals(this,2,'${e.itemNames}');return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="v_${v.index}_3" class="form-control "  name="sampList[${v.index}].recordVo.v3" value="${e.recordVo.v3}" onchange="countVal(this,3,'${e.itemNames}',${v.index});">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyCtVals(this,3,'${e.itemNames}');return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="j_${v.index}_1" class="form-control "  name="sampList[${v.index}].recordVo.v4" value="${e.recordVo.v4}">
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="j_${v.index}_2" class="form-control "  name="sampList[${v.index}].recordVo.v5" value="${e.recordVo.v5}">
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="j_${v.index}_3" class="form-control "  name="sampList[${v.index}].recordVo.v6" value="${e.recordVo.v6}">
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="stel_${v.index}" class="form-control" name="sampList[${v.index}].recordVo.avg1" value="${e.recordVo.avg1}">
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="twa_${v.index}" class="form-control" name="sampList[${v.index}].recordVo.avg2" value="${e.recordVo.avg2}">
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" name="sampList[${v.index}].remark" value="${e.remark}" maxlength="128">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			${e.itemNames}
							 			<input type="hidden"  id="itemName_${v.index}" value="${e.itemNames}">
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
//计算列 的复制
function copyCtVals(obj,n,itemNames){
	obj=$(obj);
	var value0=obj.closest('td').find('input').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#samp_tb tr:gt('+indexTr+')').each(function(){
		var thisIndex=$(this).index();
		var thisItemName=$('#itemName_'+thisIndex).val();
		if(thisItemName==itemNames){
			var tdObj=$(this).find('td').eq(indexTd).find('input').eq(0);
			tdObj.val(value0);
			countVal(tdObj,n,itemNames,thisIndex)
		}
	});
}
function countVal(obj,n,itemNames,index){
	var v=parseFloat($(obj).val());
	if(isNaN(v)){
		v=0;
	}
	if(itemNames=='一氧化碳'){
		var j=v*1.162;
		$('#j_'+index+'_'+n).val(fmtVal(j,3));
		countStel(index);
		countTwa(itemNames,index);
	}else if(itemNames=='二氧化碳'){
		var j=v*10000*1.83;
		$('#j_'+index+'_'+n).val(fmtVal(j,3));
		countStel(index);
		countTwa(itemNames,index);
	}
}
//计算stel
function countStel(index){
	var stel=0;
	$('input[id^="j_'+index+'_"]').each(function(){
		var v=parseFloat($(this).val());
		if(!isNaN(v) && stel<v){
			stel=v;
		}
	});
	$('#stel_'+index).val(fmtVal(stel,3));;
}
//计算 twa
function countTwa(itemNames,index){
	var wh=parseFloat($('#wh_'+index).val());
	if(isNaN(wh)){
		wh=0;
	}
	var twa=0;//平均值
	var n=0;//结果数
	$('input[id^="j_'+index+'_"]').each(function(){
		var v=parseFloat($(this).val());
		if(!isNaN(v)){
			twa+=v;
			n++;
		}
	});
	if(n>0){
		twa=twa/n;
	}
	if(itemNames=='一氧化碳'){
		twa=twa*wh/8;
	}else if(itemNames=='二氧化碳'){
		twa=(twa*wh+500*(8-wh))/8;
	}
	$('#twa_'+index).val(fmtVal(twa,3));
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
</script>
</body>
</html>
