<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>
<style type="text/css">
.sub_item_span{
	min-width: 100px;
	margin-right: 10px;
}
.sub_item_span a{
	float: right;
}
.radio label{
	padding-left: 5px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>业务受理</a></li>
					<li><strong>已提交</strong></li>
					<li><strong>更新</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<input name="sampType" value="${vo.sampType}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>受<br>检<br>单<br>位</label></td>
							<td class="width-10 active"><label class="pull-right">单位名称:</label></td>
							<td class="width-30">
								<input type="text" id="custName" name="custVo.custName" class="form-control" value="${vo.custVo.custName}"> 
								<input type="hidden" id="custId" name="custVo.clientVo.id" value="${vo.custVo.clientVo.id}">
							</td>
							 <td class="width-10 active"><label class="pull-right">单位地址:</label></td>
							<td class="width-30">
								<input type="text" id="custAddress" name="custVo.custAddress" class="form-control" value="${vo.custVo.custAddress}"> 
							</td>
						</tr>
						<tr>
							<td class=" active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
							<td>
								<input type="text" id="custUser" name="custVo.custUser" class="form-control" value="${vo.custVo.custUser}"> 
							</td>
							<td class="active"><label class="pull-right">联系电话:</label></td>
							<td>
								<input type="text" id="custTel" name="custVo.custTel" class="form-control" value="${vo.custVo.custTel}"> 
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">行业分类和代码:</label></td>
							<td>
								<%-- <input type="text" id="industry" name="custVo.industry" class="form-control" value="${vo.custVo.industry}"> --%>
								<div class="input-group" style="width: 100%;">
										<input type="text" id="industry" name="custVo.industry" class="form-control required" validate="required" value="${vo.custVo.industry}">
										<div class="input-group-btn">
											<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="border-right-color: red; border-top-color: red; border-bottom-color: red;">
												<span class="caret"></span>
											</button>
											<ul class="dropdown-menu dropdown-menu-right" role="menu">
											</ul>
										</div>
								 </div> 
							</td>
							<td class="active"><label class="pull-right">单位性质:</label></td>
							<td>
								<input type="text" id="attribute" name="custVo.attribute" class="form-control" value="${vo.custVo.attribute}"> 
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">主要产品:</label></td>
							<td colspan="3">
								<input type="text" id="product" name="custVo.product" class="form-control" value="${vo.custVo.product}"> 
							</td>
						</tr>
						<tr>
							<td class="active" rowspan="3" style="text-align: center;width: 50px;"><label>委<br>托<br>单<br>位</label></td>
							<td class="width-10 active"><label class="pull-right">单位名称:</label></td>
							<td class="width-30">
								<input type="text" id="wtCustName" name="custVo.wtName" class="form-control" value="${vo.custVo.wtName}" > 
								<input type="hidden" id="wtCustId" name="custVo.customerVo.id" value="${vo.custVo.customerVo.id}">
							</td>
							<td class="width-10 active"><label class="pull-right">单位地址:</label></td>
							<td class="width-30">
								<input type="text" id="wtAddress" name="custVo.wtAddress" class="form-control" value="${vo.custVo.wtAddress}"> 
							</td>
							
						</tr>
						<tr>
							<td class="active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
							<td>
								<input type="text" id="wtUser" name="custVo.wtUser" class="form-control" value="${vo.custVo.wtUser}"> 
							</td>
							<td class="active"><label class="pull-right">联系电话:</label></td>
							<td>
								<input type="text" id="wtTel" name="custVo.wtTel" class="form-control" value="${vo.custVo.wtTel}"> 
							</td>
						</tr>
						<tr>
							 <td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
							<td>
								<input type="text" id="wtEmail" name="custVo.wtEmail" class="form-control email" value="${vo.custVo.wtEmail}"> 
							</td>
							<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
							<td>
								<input type="text" id="wtZip" name="custVo.wtZip" class="form-control" value="${vo.custVo.wtZip}"> 
							</td>
						</tr>
						<tr>
							<td id="rowsTd" class="active" rowspan="9" style="text-align: center;"><label>监<br>测<br>要<br>求</label></td>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
								<input name="taskType" value="${vo.taskType}" type="hidden" />
							</td>
							<td class="active"><label class="pull-right">样品类别:</label></td>
							<td>
								${vo.sampTypeName}
								<input name="sampTypeName" value="${vo.sampTypeName}" type="hidden" />
								<input id="sampTypeId" name="sampTypeId" value="${vo.sampTypeId}" type="hidden" />
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">样品来源:</label></td>
							<td>
								${vo.source}
							</td>
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>
								 <input type="text" id="sampName" name="sampName" class="form-control" value="${vo.sampName}" > 
							</td>
						</tr>
						<tr class="tpTr">
							<td class="active"><label class="pull-right">样品处理要求:</label></td>
							<td>
								<div class="radio i-checks">
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="委托方自取" name="dealRequest" <c:if test="${vo.dealRequest=='委托方自取'}">checked</c:if>>
                                          </div>委托方自取
                                      </label>
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="委托检测机构进行销毁" name="dealRequest" <c:if test="${vo.dealRequest=='委托检测机构进行销毁'}">checked</c:if>>
                                          </div>委托检测机构进行销毁
                                      </label>
                                  </div>
							</td>
							<td class="active"><label class="pull-right">样品保存条件:</label></td>
							<td>
								<select id="saveRequest" name="saveRequest" class="form-control">
									<option value="">-请选择-</option>
									<c:forEach items="${srList}" var="e">
										<c:choose>
											<c:when test="${vo.saveRequest==e}">
												<option value="${e}" selected="selected">${e}</option>
											</c:when>
											<c:otherwise>
												<option value="${e}">${e}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr class="tpTr">
							<td class="active"><label class="pull-right">送样人员:</label></td>
							<td>
								<input type="text" id="cyName" name="cyName" class="form-control required" validate="required" value="${vo.cyName}">
							</td>
							<td class="active"><label class="pull-right">送样日期:</label></td>
							<td>
								<div class="input-group date">
									<input type="text" id="cyDate" name="cyDate" class="form-control datetimeISO required" validate="required" value="${vo.cyDate}">
									 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">报告份数:</label></td>
							<td>
								<input type="text" id="reportNum" name="reportNum"  class="form-control digits" value="${vo.reportNum}">
							</td>
							<td class="active"><label class="pull-right">取报告方式:</label></td>
							<td>
								<div class="radio i-checks">
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="自取" name="reportWay" <c:if test="${vo.reportWay=='自取'}">checked</c:if>>
                                          </div>自取
                                      </label>
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="邮寄" name="reportWay" <c:if test="${vo.reportWay=='邮寄'}">checked</c:if>>
                                          </div>邮寄
                                      </label>
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="其他" name="reportWay" <c:if test="${vo.reportWay=='其他'}">checked</c:if>>
                                          </div>其他
                                      </label>
                                 </div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">是否加急:</label></td>
							<td>
								<div class="radio i-checks">
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="是" name="jj" <c:if test="${vo.jj=='是'}">checked</c:if>>
                                          </div>是
                                      </label>
                                      <label>
                                          <div class="iradio_square-green">
                                          <input type="radio" value="否" name="jj" <c:if test="${vo.jj=='否'}">checked</c:if>>
                                          </div>否
                                      </label>
                                 </div>
							</td>
							<td class="active"><label class="pull-right">要求完成日期:</label></td>
							<td>
								<div class="input-group date">
									<input type="text" id="finishDate" name="finishDate"  class="form-control dateISO" value="${vo.finishDate}">
									 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">是否评价:</label></td>
							<td>
								${vo.pj}
							</td>
							 <td class="active"><label class="pull-right">是否分包:</label></td>
							<td>
								${vo.fb}
							</td>
						</tr>
						<tr class="pjTr">
							<td class="active"><label class="pull-right">评价标准:</label></td>
							<td colspan="3">
								<div class="input-group" style="width: 100%">
									<input type="text" id="standNames" name="standNames" class="form-control" placeholder="请选择" value="${vo.standNames}"  onclick="fnSelectPstand()"> 
									<input type="hidden" id="standIds" name="standIds" value="${vo.standIds}">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectPstand()">选择</button>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">受理人员:</label></td>
							<td>
								${vo.userName}
								<input type="hidden" id="userName" name="userName" value="${vo.userName}" >
								<input type="hidden" id="userId" name="userId" value="${vo.userId}">
							</td>
							<td class="active"><label class="pull-right">受理日期:</label></td>
							<td>
								${vo.date}
								<input type="hidden" id="date" name="date" value="${vo.date}">
							</td>
						</tr>
						<tr>
							<th class="active"><label class="pull-right">上传附件:</label></th>
							<td>
								<input type="file" name="files" multiple="multiple" class="form-control"/>
							</td>
							<td colspan="2" id="removeFile">
								<c:forEach items="${vo.fileList}" var="e" varStatus="v">
								 	<div style="float: left;margin-right: 10px;">
									 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-info">${e.fileName}</a>
									 	<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
								 	</div>
								 </c:forEach>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="remark" name="remark" maxlength="128">${vo.remark}</textarea>
							</td>
						</tr>
						<tr>
							<td colspan="5">
								检测点位信息
							</td>
						</tr>
						<tr>
							<td colspan="5">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th width="80px">序号</th>
											<th width="150">样品类别</th>
											<th width="150">样品名称</th>
											<th width="150" class="tpTd">检测点位</th>
											<th width="80" class="tpTd">点位编号</th>
											<th>检测项目</th>
										</tr>
									</thead>
									<tbody id="point_tb">
										<c:forEach items="${vo.tpList}" var="e" varStatus="v">
											<tr key="${v.index}" >
												<td>
													${e.sort}
													<input name="tpList[${v.index}].sort" value="${e.sort}" type="hidden" />
													<input name="tpList[${v.index}].id" value="${e.id}" type="hidden" />
												</td>
												<td>
													${e.sampTypeName}
													<input name="tpList[${v.index}].sampTypeId" value="${e.sampTypeId}" type="hidden" />
													<input name="tpList[${v.index}].sampTypeName" value="${e.sampTypeName}" type="hidden" />
												</td>
												<td>
													<input id="sampName_${v.index}" name="tpList[${v.index}].sampName" value="${e.sampName}" type="text"  class="form-control required" validate="required"/>
												</td>
												<td class="tpTd">
													<input id="pointName_${v.index}" name="tpList[${v.index}].pointName" value="${e.pointName}" type="text"  class="form-control required" validate="required"/>
												</td>
												<td class="tpTd">
													<input id="pointCode_${v.index}" name="tpList[${v.index}].pointCode" value="${e.pointCode}" type="text"  class="form-control"/>
												</td>
												<td>
													<a href="javascript:void(0);" onclick="showIM('${e.id}');">${e.itemNames}</a>
													<input name="tpList[${v.index}].itemIds" value="${e.itemIds}" type="hidden" />
													<input name="tpList[${v.index}].itemNames" value="${e.itemNames}" type="hidden" />
												</td>
											</tr>
										</c:forEach>
									</tbody>
									<!-- <tfoot>
										<tr>
											<td colspan="7" style="color: red;">
												注：声检测，同点位昼间夜间分两行，用样品名称标注昼间、夜间；
											</td>
										</tr>
									</tfoot> -->
								</table>
							</td>
						</tr>
					</table>
					<div class="fbTr">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
							 <thead>
							 	<tr>
							 		<th style="width: 50px;">序号</th>
							 		<th style="width: 25%;">分包单位</th>
							 		<th style="width: 15%;">联系人</th>
							 		<th style="width: 15%;">联系电话</th>
							 		<th >分包项目</th>
							 		<th style="width: 15%;">分包数量</th>
							 	</tr>
							 </thead>
							<tbody id="fb_tb">
								<c:forEach items="${vo.fbList}" var="e" varStatus="s">
									<tr index="${s.index}">
								 		<td>
								 			${s.index+1}
								 			<input type="hidden" name="fbList[${s.index}].id" value="${e.id}">
								 		</td>
								 		<td>
								 			${e.fbVo.name}
								 			<input type="hidden" name="fbList[${s.index}].fbVo.id" value="${e.fbVo.id}">
								 		</td>
								 		<td>
								 			<input type="text" name="fbList[${s.index}].fbUser" class="form-control required" validate="required" value="${e.fbUser}">
								 		</td>
								 		<td>
								 			<input type="text" name="fbList[${s.index}].fbMobile" class="form-control required" validate="required" value="${e.fbMobile}">
								 		</td>
								 		<td>
								 			${e.itemNames}
											<input type="hidden" name="fbList[${s.index}].itemNames" validate="required" class="form-control required" value="${e.itemNames}">
											<input type="hidden" name="fbList[${s.index}].itemIds" value="${e.itemIds}">
								 		</td>
										<td>
								 			<input type="text" name="fbList[${s.index}].num" class="form-control digits required" validate="required" value="${e.num}">
								 		</td>					 		 
								 	</tr>
								</c:forEach>
							</tbody>
						</table>	 
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:void(0);" onclick="formSubmit('update4Yb.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 更新</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../include/js.jsp"%>
<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="${basePath}h/js/plugins/suggest/bootstrap-suggest.min.js"></script>
<script type="text/javascript">
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
//判定标准 弹出层
function fnSelectPstand(){
	var id=$('#standIds').val();
	var sampTypeId=$('#sampTypeId').val();
	parent.layer.open({
		title:'评价标准',	
		type: 2,
		area: ['1000px', '500px'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}init/pstandard/selects2.do?ids='+id+'&sampTypeId='+sampTypeId,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
		  var iframeWin=layero.find('iframe')[0];
		  var data=iframeWin.contentWindow.fnSelect();
		  $('#standIds').val(data.id);
		  $('#standNames').val(data.name);
			parent.layer.close(index);
		}
	});
}
$(document).ready(function() {
	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green",
	});
	$('input[type="file"]').prettyFile();
	checkSampSource();
	checkPj();
	checkFb();
	var arrStr=industrySelect();
	$('#industry').bsSuggest({
	    indexId: 0, //data.value 的第几个数据，作为input输入框的内容
	    indexKey: 0, //data.value 的第几个数据，作为input输入框的内容
	    data: getData(arrStr),
	    autoDropup:true,
	    showBtn: true
	});
	
});
function getData(arrStr){
	var data = {
	    	value: []
	    };
	var ut=arrStr.split(',');
	for(var i=0;i<ut.length;i++){
		data.value.push({word: ut[i]});
	}
	return data;
}
function industrySelect() {
	var v='';
	$.ajax({
		url : '${basePath}sys/code/ajaxCodeContent.do?code=cus-sshy',
		datatype : "json",
		async:false,
		success : function(data) {
			v=data;
		}
	});
	return v;
} 
 
function formSubmit(url){
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
}
</script>
<script>
function checkPj(){
	var pj='${vo.pj}';
	var source='${vo.source}';
	if(pj=='是'){
		$('.pjTr').find('input').attr('validate','required');
		$('.pjTr').find('input').addClass('required');
		$('.pjTr').show();
		if(source=='送样'){
			$('#rowsTd').attr('rowspan','11');
		}else{
			$('#rowsTd').attr('rowspan','9');
		}
	}else{
		$('.pjTr').find('input').removeAttr('validate');
		$('.pjTr').find('input').removeClass('required');
		$('.pjTr').hide();
		if(source=='送样'){
			$('#rowsTd').attr('rowspan','10');
		}else{
			$('#rowsTd').attr('rowspan','8');
		}
	}
}
function checkSampSource(){
	var source='${vo.source}';
	var pj='${vo.pj}';
	if(source=='送样'){
		$('.tpTd').hide();
		$('.tpTd').find('input').removeAttr('validate');
		$('.tpTd').find('input').removeClass('required');
		$('.tpTr').show();
		$('#cyName').attr('validate');
		$('#cyName').addClass('required');
		$('#cyDate').attr('validate');
		$('#cyDate').addClass('required');
		if(pj=='是'){
			$('#rowsTd').attr('rowspan',11);
		}else{
			$('#rowsTd').attr('rowspan',10);
		}
	}else{
		$('.tpTd').show();
		$('.tpTd').find('input[id^="pointName_"]').attr('validate','required');
		$('.tpTd').find('input[id^="pointName_"]').addClass('required');
		$('.tpTr').hide();
		$('#cyName').removeAttr('validate');
		$('#cyName').removeClass('required');
		$('#cyDate').removeAttr('validate');
		$('#cyDate').removeClass('required');
		if(pj=='是'){
			$('#rowsTd').attr('rowspan',9);
		}else{
			$('#rowsTd').attr('rowspan',8);
		}
	}
}
function checkFb(){
	var radioVal='${vo.fb}';
	if(radioVal=='是'){
		$('.fbTr').show()
	}else{
		$('.fbTr').hide()
	}
}
function copyVal(obj){
	obj=$(obj);
	var textValue=obj.closest('td').find('textarea').val();
	var idValue=obj.closest('td').find('input').val();
	var indexTr=obj.closest('td').closest('tr').index();
	var indexTd=obj.closest('td').index();
	$('#point_tb tr:gt('+indexTr+')').each(function(){
		$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
		$(this).find('td').eq(indexTd).find('textarea').eq(0).val(textValue);
	});
}
function showIM(id){
	parent.layer.open({
		title:'已选项目方法列表',	
		type: 2,
		area: ['700px', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: '${basePath}/bus/im/list4Im.do?busId='+id,
	});
}
</script>
</body>
</html>
