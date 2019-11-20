<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
#detail_tb span{
	width: 10px;
}

#detail_tb .btn{
	padding: 6px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="javascript:backMainPage();">报价申请</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active" style="width: 60px;text-align: center;" rowspan="5"><label>基<br>本<br>信<br>息</label></td>
							<td class="active" style="width: 10%"><label class="pull-right">客户名称:</label></td>
							<td>
								<div class="input-group">
									<input type="text" id="custName" name="custName" class="form-control required" validate="required" placeholder="请选择" value="${vo.custName}"  onclick="fnSelectCust()"> 
									<input type="hidden" id="custId" name="custId" value="${vo.custId}">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectCust()">选择</button>
									</div>
								</div>
							</td>
							<td class="active" style="width: 10%"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
							<td>
								<input type="text" id="custUser" name="custUser" class="form-control required" validate="required" value="${vo.custUser}"> 
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">单位地址:</label></td>
							<td>
								<input type="text" id="custAddress" name="custAddress" class="form-control" value="${vo.custAddress}"> 
							</td>
							<td class="active"><label class="pull-right">联系电话:</label></td>
							<td>
								<input type="text" id="custMobile" name="custMobile" class="form-control mobile required" validate="required" value="${vo.custMobile}"> 
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
							<td>
								<input type="text" id="custEmail" name="custEmail" class="form-control email" value="${vo.custEmail}"> 
							</td>
							<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
							<td>
								<input type="text" id="custZip" name="custZip" class="form-control" value="${vo.custZip}"> 
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">监测场景:</label></td>
							<td>
								<select name="taskEnv" id="taskEnv" class="form-control required" validate="required" onchange="changeEnv();"></select>
							</td>
							 <td class="active"></td>
							<td>
							</td>
						</tr>
						<tr id="taskDivId">
							 <td class="active"><label class="pull-right">监测类别:</label></td>
							<td>
							<select name="taskType" id="taskType" class="form-control required" validate="required" disable="none"></select>
								<%-- <select name="taskType" id="taskType" class="form-control required" validate="required">
									<option value="">请选择</option>
									<c:forEach items="${taskTypeList}" var="e" varStatus="s">
										<c:choose>
											<c:when test="${e.name==vo.taskType}">
												<option value="${e.name}" selected="selected">${e.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${e.name}">${e.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select> --%>
							</td>
							<td class="active"><label class="pull-right">项目频次:</label></td>
							<td>
								<input style="width: 60%; display: inline-block;" type="number" id="pc" name="pc"  class="form-control required digits" validate="required" value="${vo.pc}">
								<select style="width: 30%; display: inline-block;" id="pcUnit" name="pcUnit"  class="form-control">
									<c:choose>
										<c:when test="${vo.pcUnit=='次/月'}">
											<option value="次">次</option>
											<option value="次/月" selected="selected">次/月</option>
											<option value="次/季">次/季</option>
											<option value="次/年">次/年</option>
										</c:when>
										<c:when test="${vo.pcUnit=='次/季'}">
											<option value="次">次</option>
											<option value="次/月">次/月</option>
											<option value="次/季" selected="selected">次/季</option>
											<option value="次/年">次/年</option>
										</c:when>
										<c:when test="${vo.pcUnit=='次/年'}">
											<option value="次">次</option>
											<option value="次/月">次/月</option>
											<option value="次/季">次/季</option>
											<option value="次/年" selected="selected">次/年</option>
										</c:when>
										<c:otherwise>
											<option value="次" selected="selected">次</option>
											<option value="次/月">次/月</option>
											<option value="次/季">次/季</option>
											<option value="次/年">次/年</option>
										</c:otherwise>
									</c:choose>
								</select>
							</td>
						</tr>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						 <thead>
						 	<tr>
						 		<th style="width: 50px;">序号</th>
						 		<th style="width: 200px;">样品类型</th>
						 		<th style="width: 150px;">样品名称</th>
						 		<th >检测项目</th>
						 		<th style="width: 100px;">分析费</th>
						 		<th style="width: 100px;">采样费</th>
						 		<th style="width: 20px;"><button onclick="addRow()" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>添加</button></th>
						 	</tr>
						 </thead>
						<tbody id="detail_tb">
							<c:forEach items="${vo.detailList}" var="e" varStatus="s">
								<tr index="${s.index}">
							 		<td>
							 			${s.index+1}
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
							 				<input type="hidden" id="sampTypeId${s.index}" name="detailList[${s.index}].sampTypeId" value="${e.sampTypeId}">
											<input type="text" id="sampTypeName${s.index}" name="detailList[${s.index}].sampTypeName" class="form-control required" validate="required" placeholder="请选择" value="${e.sampTypeName}" onclick="fnSelectSampType('${s.index}')"> 
											<div class="input-group-btn"><button tabindex="-1" class="btn btn-primary" type="button"  onclick="fnSelectSampType('${s.index}');">选择</button></div>
							 				<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="sampName${s.index}" name="detailList[${s.index}].sampName" class="form-control "  placeholder="请输入" value="${e.sampName}" >
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVal(this);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
											<input type="text" id="itemNames${s.index}" name="detailList[${s.index}].itemNames" class="form-control required" validate="required" placeholder="请选择" value="${e.itemNames}"  onclick="chooseItem('${s.index}');">
											<input type="hidden" id="itemIds${s.index}" name="detailList[${s.index}].itemIds" value="${e.itemIds}">
											<div class="input-group-btn"><button tabindex="-1" class="btn btn-primary" type="button"  onclick="chooseItem('${s.index}');">选择</button></div>
											<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="fxPrice${s.index}" name="detailList[${s.index}].fxPrice" class="form-control number" value="${e.fxPrice}" onchange="countPrice()">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVal(this);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="cyPrice${s.index}" name="detailList[${s.index}].cyPrice" class="form-control number" value="${e.cyPrice}" onchange="countPrice()">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVal(this);return false;"></span>
							 			</div>
							 		</td>
							 		<td align="center"><a  href="javascript:;" onclick="removeTr(this);"><font color="red">删除</font></a></td>
							 	</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
							<tr>
								<td class="active"><label class="pull-right">检测费用:</label></td>
								<td>
									<input type="text" id="price" name="price" class="form-control number" value="${vo.price}">
								</td>
								<td class="active"><label class="pull-right">折&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扣:</label></td>
								<td>
									<input type="text" id="discount" name="discount" class="form-control number" value="${vo.discount}" onchange="countPrice()">
								</td>
								<td class="active"><label class="pull-right">交通费用:</label></td>
								<td>
									<input type="text" id="jtPrice" name="jtPrice" class="form-control number" value="${vo.jtPrice}" onchange="countPrice();">
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">报告费用:</label></td>
								<td>
									<input type="text" id="bgPrice" name="bgPrice" class="form-control number" value="${vo.bgPrice}" onchange="countPrice()">
								</td>
								<td class="active"><label class="pull-right">税费等其他费用:</label></td>
								<td>
									<input type="text" id="otherPrice" name="otherPrice" class="form-control number" value="${vo.otherPrice}" onchange="countPrice()">
								</td>
								<td class="active"><label class="pull-right">合同费用:</label></td>
								<td>
									<input type="text" id="htPrice" name="htPrice" class="form-control number" value="${vo.htPrice}" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">报价日期:</label></td>
								<td>
									<div class="input-group date">
										<input type="text" id="bdate" name="bdate"  class="form-control required" placeholder="请选择" validate="required" value="${vo.bdate}">
										 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
								<td class="active"><label class="pull-right">有效期至:</label></td>
								<td>
									<div class="input-group date">
										<input type="text" id="yxq" name="yxq"  class="form-control required dateISO" placeholder="请选择" validate="required" value="${vo.yxq}">
										 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
								<td class="active"><label class="pull-right">报&nbsp;&nbsp;价&nbsp;&nbsp;人:</label></td>
								<td>
									<input type="hidden" id="buserId" name="buserId" value="${vo.buserId}">
									<input type="text" id="buserName" name="buserName" class="form-control" value="${vo.buserName}" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="5">
									<textarea rows="2" class="form-control" id="remark" name="remark" maxlength="128">${vo.remark}</textarea>
								</td>
							</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a id="sub_btn" class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('${fn:length(vo.id)>0? 'updateData.do?isCommit=1':'addData.do?isCommit=1'}');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
		   <!-- Sweet alert -->
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>
		function formSubmitSave(url){
			$('#thisForm').attr('action',url);
			$('#thisForm').submit()
		}
		function formSubmit(url){
			if($('#detail_tb').children().length==0){
				parent.toastr.error('请添加详情信息', '');
				return false;
			}
			swal({
		        title: "您确定要提交该任务吗",
		        text: "提交后将无法修改，请谨慎操作！",
		        type: "success",
		        showCancelButton: true,
		        confirmButtonColor: "#1ab394",
		        confirmButtonText: "确定",
		        cancelButtonText: "取消"
		    }, function () {
				$('#thisForm').attr('action',url);
				var b = $("#thisForm").FormValidate();
				if(b){
					 $('#thisForm').ajaxSubmit(function(res) {
				    	if(res.status=='success'){
				    	    parent.toastr.success(res.message, '');
					        backMainPage();
				        }else{
				        	parent.toastr.error(res.message, '');
				        }
					});
				}
		    });
		}
		//选择客户
		function fnSelectCust(){
			var custId=$('#custId').val();
			layer.open({
				title:'客户信息',	
				type: 2,
				 area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '${basePath}cus/customer/select.do?id='+custId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
				  var iframeWin = window[layero.find('iframe')[0]['name']];
				  var selectData = iframeWin.fnSelect();
				  $("#custName").val(selectData.name);
				  $("#custId").val(selectData.id);
				  $("#custUser").val(selectData.person);
				  $("#custAddress").val(selectData.address);
				  $("#custMobile").val(selectData.telephone);
				  $("#custEmail").val(selectData.email);
				  $("#custZip").val(selectData.zip);
				}
			});
		}
		function removeFiles(id,obj){
			layer.confirm('确认要删除?', {icon:3, title:'系统提示'}, function(index){
				$.ajax({
					url:'${basePath}sys/files/deleteOne.do?id='+id,
					dataType:"json",
					type:"post",
					async:false,
					success: function(data){
						if(data.status=='success'){
							layer.msg(data.message, {icon: 0,time: 1000});
							$(obj).parent().remove();
						}
					},
					error:function(ajaxobj){
				    }  
				});
			});
		}
		$(document).ready(function() {
			$(".i-checks").iCheck({
				checkboxClass : "icheckbox_square-green",
				radioClass : "iradio_square-green",
			});
			selectTaskEnv();	
			
		});
		
		
		$("#taskDivId").hide();
		function changeEnv()
		{
			var selectval = $("#taskEnv").val();
		    $("#taskDivId").show();
		    
		    $.ajax({
		    	url:"${basePath}/cus/budget/ajaxGetTaskType.do",
		    	data:{"taskEnv":selectval},
		    	datatype : "json",
		    	success : function(data) {
					var value = data.taskTypeList;
					var optionstring = "<option value=\"\" >-请选择-</option>";
					for (var i = 0; i < value.length; i++) {
						if('${vo.taskType}'== value[i]){
						optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
								+ value[i] + "</option>";
						}else{
							optionstring += "<option value=\"" + value[i] + "\" >"
								+ value[i] + "</option>";
						}
					}
					$("#taskType").html(optionstring);
				}
		    })
		}
		
		
		function selectTaskEnv()
		{
			$.ajax({
				url : '${basePath}/cus/budget/ajaxGetTaskEnv.do',
				datatype : "json",
				success : function(data) {
					var value = data.taskEnvList;
					var optionstring = "<option value=\"\" >-请选择-</option>";
					for (var i = 0; i < value.length; i++) {
						if('${vo.taskEnv}'== value[i]){
						optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
								+ value[i] + "</option>";
						}else{
							optionstring += "<option value=\"" + value[i] + "\" >"
								+ value[i] + "</option>";
						}
					}
					$("#taskEnv").html(optionstring);
				}
			});
			
			
				
		}
	</script>
<script type="text/javascript">


function addRow(){
	var table=$('#detail_tb');
	var num=table.find('tr').length;
	$('#sampNum').val(num+1);
	var index=0;
	if(num>0){
		index=parseInt(table.find('tr').eq(num-1).attr('index'));
		index++;
	}
	var sampTypeId='${vo.sampTypeId}';
	var sampTypeName='${vo.sampTypeName}';
	if(sampTypeId.indexOf(',')>=0){
		sampTypeId='';
		sampTypeName='';
	}
	table.append($('<tr index='+index+'>').append('<td>'+(index+1)+'</td>')
			.append('<td><div class="input-group" style="width: 100%"><input type="hidden" id="sampTypeId'+index+'" value="'+sampTypeId+'" name="detailList['+index+'].sampTypeId" >'+
					'<input type="text" id="sampTypeName'+index+'" name="detailList['+index+'].sampTypeName" value="'+sampTypeName+'" class="form-control required" validate="required" placeholder="请选择" onclick="fnSelectSampType('+index+')">'+
					'<div class="input-group-btn"><button tabindex="-1" class="btn btn-primary" type="button"  onclick="fnSelectSampType('+index+');">选择</button></div><span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span></div></td>')
			.append('<td><div class="input-group" style="width: 100%"><input type="text" id="sampName'+index+'" name="detailList['+index+'].sampName" class="form-control " ><span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVal(this);return false;"></span></div></td>')
			.append('<td><div class="input-group" style="width: 100%"><input type="text" id="itemNames'+index+'" name="detailList['+index+'].itemNames" class="form-control required" validate="required" placeholder="请选择" onclick="chooseItem('+index+');">'+
					'<input type="hidden" id="itemIds'+index+'" name="detailList['+index+'].itemIds" ><div class="input-group-btn"><button tabindex="-1" class="btn btn-primary" type="button"  onclick="chooseItem('+index+');">选择</button></div><span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span></div></td>')
			.append('<td><div class="input-group" style="width: 100%"><input type="text" id="fxPrice'+index+'" name="detailList['+index+'].fxPrice" value="0.0" class="form-control number"  onchange="countPrice()"><span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVal(this);return false;"></span></div></td>')
			.append('<td><div class="input-group" style="width: 100%"><input type="text" id="cyPrice'+index+'" name="detailList['+index+'].cyPrice" value="0.0" class="form-control number"  onchange="countPrice()"><span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVal(this);return false;"></span></div></td>')
			.append('<td align="center"><a  href="javascript:;" onclick="removeTr(this);"><font color="red">删除</font></a></td>'));
	
}
function fnSelectSampType(n){
	var sampTypeId=$('#sampTypeId'+n).val();
	layer.open({
		title:'样品类型',	
		type: 2,
		area: ['300px', '75%'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/sampType/select.do?id='+sampTypeId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
		  var iframeWin = window[layero.find('iframe')[0]['name']];
		  var data=iframeWin.fnSelect();
		  $('#sampTypeId'+n).val(data.id);
		  $('#sampTypeName'+n).val(data.name);
		}
	});
}
function chooseItem(n){
	var sampTypeId=$('#sampTypeId'+n).val();
	if(sampTypeId==''){
		layer.msg("请选择样品类型！", {icon: 0,time: 3000});
		return false;
	}
	//sort=0代表普通项目 1代表现场项目
	var	url='item_select.do?sort=0&ids='+$('#itemIds'+n).val()+'&sampTypeId='+sampTypeId;
	layer.open({
		title:'检测项目',	
		type: 2,
		 area: ['70%', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: url,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#itemIds'+n).val(data.id);
			$('#itemNames'+n).val(data.name);
			$('#fxPrice'+n).val(data.price);
			countPrice();
		}
	});
}
function copyItem(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('input').eq(0).val();
	var nameValue=obj.closest('td').find('input').eq(1).val();
	var priceValue=obj.closest('td').next().find('input').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#detail_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
		$(this).find('td').eq(indexTd).find('input').eq(1).val(nameValue);
		$(this).find('td').eq(indexTd+1).find('input').eq(0).val(priceValue);
	});
	countPrice();
}
function copyVal(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('input').eq(0).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#detail_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
	});
	countPrice();
}

function copyVals(obj){
	obj=$(obj);
	var idValue=obj.closest('td').find('input').eq(0).val();
	var nameValue=obj.closest('td').find('input').eq(1).val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#detail_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
		$(this).find('td').eq(indexTd).find('input').eq(1).val(nameValue);
	});
}
function removeTr(obj){
	$(obj).parent().parent().remove();
	countPrice();
}
function countPrice(){
	var t=0;
	$('input[name$=".fxPrice"]').each(function(){
		t+=parseFloat($(this).val());
	})
	$('input[name$=".cyPrice"]').each(function(){
		t+=parseFloat($(this).val());
	})
	$('#price').val(t);
	var discount=parseFloat($('#discount').val());
	var jtPrice=parseFloat($('#jtPrice').val());
	var bgPrice=parseFloat($('#bgPrice').val());
	var otherPrice=parseFloat($('#otherPrice').val());
	$('#htPrice').val(t*discount+jtPrice+bgPrice+otherPrice);
}
</script>
	
	
	
</body>
</html>
