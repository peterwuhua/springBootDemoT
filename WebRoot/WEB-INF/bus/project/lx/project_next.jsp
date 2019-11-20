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
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
.form-control{
	padding: 4px 4px;
}
.table select{
	padding-top: 2px;
}
#detail_tb .btn{
	padding: 6px;
}
table>thead>tr>th{
	text-align: center;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><strong>项目立项</strong></li>
					<li><strong>${vo.sampType}</strong></li>
					<li><strong>送样信息</strong>
						<c:if test="${vo.isBack=='Y'}">
							（<span style="color: red"> 退回原因：${logVo.msg}</span> ）
						</c:if>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						 <thead>
						 	<tr>
						 		<th style="width: 50px;">序号</th>
						 		<th style="width: 120px;">样品类型</th>
						 		<th style="width: 10%;">样品名称</th>
						 		<th style="width: 10%;">样品性状</th>
						 		<th style="width: 10%;">包装情况</th>
						 		<th >检测项目</th>
						 		<th style="width:10%;">备注</th>
						 		<th style="width: 20px;"><button onclick="addRow()" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>添加</button></th>
						 	</tr>
						 </thead>
						<tbody id="detail_tb">
							<c:forEach items="${vo.pointList}" var="e" varStatus="s">
								<tr index="${s.index}">
							 		<td>
							 			${s.index+1}
							 		</td>
							 		<td>
							 			<select id="sampTypeId_${s.index}" name="pointList[${s.index}].sampTypeId" class="form-control required" validate="required">
							 				<c:forEach items="${sampTypeList}" var="e1" varStatus="s1">
							 					<c:choose>
							 						<c:when test="${e1.id==e.sampTypeId}">
							 							<option value="${e1.id}" selected="selected">${e1.name}</option>
							 						</c:when>
							 						<c:otherwise>
							 							<option value="${e1.id}">${e1.name}</option>
							 						</c:otherwise>
							 					</c:choose>
							 				</c:forEach>
							 			</select>
							 			<input type="hidden" id="sampTypeName_${s.index}" name="pointList[${s.index}].sampTypeName" value="${e.sampTypeName}">
							 		</td>
							 		<td>
							 			<input type="text" id="sampName_${s.index}" name="pointList[${s.index}].sampName" class="form-control required" validate="required" placeholder="请输入" value="${e.sampName}" >
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="xz_${s.index}" name="pointList[${s.index}].xz" class="form-control" value="${e.xz}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
								 			<input type="text" id="packing_${s.index}" name="pointList[${s.index}].packing" class="form-control" value="${e.packing}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span>
							 			</div>
							 		</td>
							 		<td>
							 			<div class="input-group" style="width: 100%">
											<input type="text" id="itemName_${s.index}" name="pointList[${s.index}].itemName" class="form-control required" validate="required" placeholder="请选择" value="${e.itemName}"  onclick="chooseItem('${s.index}');">
											<input type="hidden" id="itemId_${s.index}" name="pointList[${s.index}].itemId" value="${e.itemId}">
											<div class="input-group-btn"><button tabindex="-1" class="btn btn-primary" type="button"  onclick="chooseItem('${s.index}');">选择</button></div>
											<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span>
							 			</div>
							 			<input type="hidden" id="imId_${s.index}" name="pointList[${s.index}].imId" value="${e.imId}">
							 			<input type="hidden" id="fxPrice_${s.index}" name="pointList[${s.index}].fxPrice" value="${e.fxPrice}" >
							 		</td>
							 		<td>
							 			<input type="text" name="pointList[${s.index}].remark" class="form-control" value="${e.remark}" maxlength="128">
							 		</td>
							 		<td align="center"><a  href="javascript:;" onclick="removeTr(this);"><font color="red">删除</font></a></td>
							 	</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active"><label class="pull-right">样品处理要求:</label></td>
								<td>
									<div class="radio i-checks">
	                                      <label>
	                                          <div class="iradio_square-green">
	                                          <input type="radio" value="委托方自取" name="schemeVo.dealRequest" <c:if test="${vo.schemeVo.dealRequest=='委托方自取'}">checked</c:if>>
	                                          </div>委托方自取
	                                      </label>
	                                      <label>
	                                          <div class="iradio_square-green">
	                                          <input type="radio" value="委托检测机构进行销毁" name="schemeVo.dealRequest" <c:if test="${vo.schemeVo.dealRequest=='委托检测机构进行销毁'}">checked</c:if>>
	                                          </div>委托检测机构进行销毁
	                                      </label>
	                                  </div>
								</td>
								<td class="active"><label class="pull-right">样品保存条件:</label></td>
								<td>
									<select id="saveRequest" name="schemeVo.saveRequest" class="form-control">
										<option value="">-请选择-</option>
										<c:forEach items="${srList}" var="e">
											<c:choose>
												<c:when test="${vo.schemeVo.saveRequest==e}">
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
							<tr>
								<td class="width-15 active"><label class="pull-right">检测费用:</label></td>
								<td class="width-35">
									<input type="text" id="price" name="invoiceVo.price" class="form-control number" value="${vo.invoiceVo.price}" >
								</td>
								<td class="width-15 active"><label class="pull-right">折&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扣:</label></td>
								<td class="width-35">
									<input type="text" id="discount" name="invoiceVo.discount" class="form-control number" value="${vo.invoiceVo.discount}" onchange="countPrice()">
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">交通费用:</label></td>
								<td class="width-35">
									<input type="text" id="jtPrice" name="invoiceVo.jtPrice" class="form-control number" value="${vo.invoiceVo.jtPrice}" onchange="countHtPrice();">
								</td>
								<td class="width-15 active"><label class="pull-right">报告费用:</label></td>
								<td class="width-35">
									<input type="text" id="bgPrice" name="invoiceVo.bgPrice" class="form-control number" value="${vo.invoiceVo.bgPrice}" onchange="countHtPrice();">
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">税费等其他费用:</label></td>
								<td class="width-35">
									<input type="text" id="otherPrice" name="invoiceVo.otherPrice" class="form-control number" value="${vo.invoiceVo.otherPrice}" onchange="countHtPrice();">
								</td>
								<td class="width-15 active"><label class="pull-right">优惠费用:</label></td>
								<td class="width-35">
									<input type="text" id="yhPrice" name="invoiceVo.yhPrice" class="form-control number" value="${vo.invoiceVo.yhPrice}" onchange="countHtPrice();">
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">合同费用:</label></td>
								<td class="width-35">
									<input type="text" id="htPrice" name="invoiceVo.htPrice" class="form-control" value="${vo.invoiceVo.htPrice}" readonly="readonly">
								</td>
								<td class="active"><label class="pull-right ">是否分包:</label></td>
								<td>
									<div class="radio i-checks">
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="是" name="fb" <c:if test="${vo.fb=='是'}">checked</c:if>>
                                            </div>是
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="否" name="fb" <c:if test="${vo.fb!='是'}">checked</c:if>>
                                            </div>否
                                        </label>
                                    </div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">送样人员:</label></td>
								<td class="width-35"><input type="text" id="cyUserName" name="schemeVo.cyUserName" class="form-control required" validate="required" value="${vo.schemeVo.cyUserName}"></td>
								<td class="width-15 active"><label class="pull-right">送样时间:</label></td>
								<td class="width-35">
									<div class="input-group date">
										<input type="text" id="cyDate" name="schemeVo.cyDate" class="form-control datetimeISO required" validate="required"  placeholder="请选择" value="${vo.schemeVo.cyDate}">
										 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">其&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;他:</label></td>
								<td colspan="3">
									<textarea rows="2" class="form-control" id="others" name="schemeVo.remark" maxlength="128">${vo.schemeVo.remark}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="fbTr">
						<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" style="margin-bottom: 0px;">
							 <thead>
							 	<tr>
							 		<th style="width: 50px;">序号</th>
							 		<th style="width: 250px;">分包单位</th>
							 		<th style="width: 100px;">联系人</th>
							 		<th style="width: 120px;">联系电话</th>
							 		<th >分包项目</th>
							 		<th style="width: 100px;">分包数量</th>
							 		<th style="width: 100px;">分包费用</th>
							 		<th style="width: 50px;">
							 			<button onclick="chooseFbUnit()" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>添加</button>
							 		</th>
							 	</tr>
							 </thead>
							<tbody id="fb_tb">
								<c:forEach items="${vo.fbList}" var="e" varStatus="s">
									<tr index="${s.index}">
								 		<td>
								 			${s.index+1}
								 		</td>
								 		<td>
								 			${e.fbVo.name}
								 			<input type="hidden" id="fbId${s.index}" name="fbList[${s.index}].fbVo.id" value="${e.fbVo.id}">
								 		</td>
								 		<td>
								 			<input type="text" id="fbUser${s.index}" name="fbList[${s.index}].fbUser" class="form-control required" validate="required" value="${e.fbUser}">
								 		</td>
								 		<td>
								 			<input type="text" id="fbMobile${s.index}" name="fbList[${s.index}].fbMobile" class="form-control required" validate="required" value="${e.fbMobile}">
								 		</td>
								 		<td>
								 			<div class="input-group" style="width: 100%">
												<input type="text" id="itemNames${s.index}" name="fbList[${s.index}].itemNames" validate="required" class="form-control required" placeholder="请选择" value="${e.itemNames}"  onclick="chooseItem4Fb(${s.index});">
												<input type="hidden" id="itemIds${s.index}" name="fbList[${s.index}].itemIds" value="${e.itemIds}">
												<div class="input-group-btn">
													<button tabindex="-1" class="btn btn-primary" type="button"  onclick="chooseItem4Fb(${s.index});">选择</button>
												</div>
								 			</div>
								 		</td>
										<td>
								 			<input type="text" id="num${s.index}" name="fbList[${s.index}].num" class="form-control digits required" validate="required" value="${e.num}">
								 		</td>					 		 
								 		<td>
								 			<input type="text" id="price${s.index}" name="fbList[${s.index}].price" class="form-control number required" validate="required" value="${e.price}" onchange="countFbPrice()">
								 		</td>
								 		<td align="center">
								 			<a  href="javascript:;" onclick="removeTr(this);"><font color="red">删除</font></a>
								 		</td>
								 	</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="8" style="text-align: right;"><div class="input-group" style="width: 100%"><label style="padding:6px 8px;">费用合计:</label><input style="width: 100px;float: right;" type="text" id="fbPrice" name="fbPrice" class="form-control" value="${vo.fbPrice}" readonly="readonly"></div></td>
								</tr>
							</tfoot>
						</table>	 
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="edit.do?id=${vo.id}" ><i class="fa fa-level-up" aria-hidden="true"></i> 上一步</a>
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmitSave('saveNext.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('update4NextData.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
		   <!-- Sweet alert -->
    <script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
		changed();
	});
		function formSubmitSave(url){
			var sampNum=$('#detail_tb').find('tr').length;
			if(sampNum<=0){
				layer.msg("请添加样品信息！", {icon: 0,time: 3000});
				return false;
			}
			$('#thisForm').attr('action',url);
			$('#thisForm').submit()
		}
		function formSubmit(url){
			var sampNum=$('#detail_tb').find('tr').length;
			if(sampNum<=0){
				layer.msg("请添加样品信息！", {icon: 0,time: 3000});
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
		
		function addRow(){
			var table=$('#detail_tb');
			var num=table.find('tr').length;
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
			var samName='${vo.sampName}';
			var stOpt=getSampTypeOpt();
			table.append($('<tr index='+index+'>').append('<td>'+(index+1)+'</td>')
					.append('<td><select id="sampTypeId_'+index+'" name="pointList['+index+'].sampTypeId"  class="form-control required" validate="required">'+stOpt+
							'</select><input type="hidden" id="sampTypeName_'+index+'" name="pointList['+index+'].sampTypeName" value="'+sampTypeName+'"/></td>')
					.append('<td><input type="text" id="sampName_'+index+'" name="pointList['+index+'].sampName" value="'+samName+'" class="form-control required" validate="required" ></td>')
					.append('<td><div class="input-group" style="width: 100%"><input type="text" id="xz_'+index+'" name="pointList['+index+'].xz" class="form-control" >'+
							'<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span></div></td>')
					.append('<td><div class="input-group" style="width: 100%"><input type="text" id="packing_'+index+'" name="pointList['+index+'].packing" class="form-control" >'+
							'<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this);return false;"></span></div></td>')
					.append('<td><div class="input-group" style="width: 100%"><input type="text" id="itemName_'+index+'" name="pointList['+index+'].itemName" class="form-control required" validate="required" placeholder="请选择" onclick="chooseItem('+index+');">'+
							'<input type="hidden" id="itemId_'+index+'" name="pointList['+index+'].itemId" ><div class="input-group-btn"><button tabindex="-1" class="btn btn-primary" type="button"  onclick="chooseItem('+index+');">选择</button></div>'+
							'<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyItem(this);return false;"></span></div><input type="hidden" id="imId_'+index+'"  name="pointList['+index+'].imId" ><input type="hidden" id="fxPrice_'+index+'"  name="pointList['+index+'].fxPrice" value="0.0" ></td>')
					.append('<td><input type="text" name="pointList['+index+'].remark" class="form-control" maxlength="128"></td>')
					.append('<td align="center"><a  href="javascript:;" onclick="removeTr(this);" class="btn  btn-xs btn-outline btn-danger">删除</a></td>'));
			
		}
		function getSampTypeOpt(){
			var opt='';
			<c:forEach items="${sampTypeList}" var="e">
				opt+='<option value="${e.id}">${e.name}</option>'
			</c:forEach>
			return opt;
		}
		function removeTr(obj){
			$(obj).parent().parent().remove();
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
		function copyItem(obj){
			obj=$(obj);
			var idValue=obj.closest('td').find('input').eq(0).val();
			var nameValue=obj.closest('td').find('input').eq(1).val();
			var imValue=obj.closest('td').find('input').eq(2).val();
			var priceValue=obj.closest('td').find('input').eq(3).val();
			var indexTr=obj.closest('td').closest('tr').index();
			var indexTd=obj.closest('td').index();
			$('#detail_tb tr:gt('+indexTr+')').each(function(){
				$(this).find('td').eq(indexTd).find('input').eq(0).val(idValue);
				$(this).find('td').eq(indexTd).find('input').eq(1).val(nameValue);
				$(this).find('td').eq(indexTd).find('input').eq(2).val(imValue);
				$(this).find('td').eq(indexTd).find('input').eq(3).val(priceValue);
			});
			countPrice();
		}
		function chooseItem(n){
			var sampTypeId=$('#sampTypeId_'+n).val();
			//sort=0代表普通项目 1代表现场项目
			var	url='${basePath}init/item/im_selects.do?isNow=N&ids='+$('#imId_'+n).val()+'&sampTypeIds='+sampTypeId;
			parent.layer.open({
				title:'检测项目',	
				type: 2,
				area: ['1000px', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: url,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
					var iframeWin=layero.find('iframe')[0];
					var data=iframeWin.contentWindow.fnSelect();
					$('#itemId_'+n).val(data.id);
					$('#itemName_'+n).val(data.name);
					$('#imId_'+n).val(data.im);
					$('#fxPrice_'+n).val(data.price);
					parent.layer.close(index);
					countPrice();
				}
			});
		}
		 
		function countPrice(){
			var t=0;
			$('input[id^="fxPrice_"]').each(function(){
				t+=parseFloat($(this).val());
			})
			$('#price').val(t);
			countHtPrice();
		}
		function countHtPrice(){
			var price=parseFloat($('#price').val());
			var discount=parseFloat($('#discount').val());
			var yhPrice=parseFloat($('#yhPrice').val());
			var jtPrice=parseFloat($('#jtPrice').val());
			var bgPrice=parseFloat($('#bgPrice').val());
			var otherPrice=parseFloat($('#otherPrice').val());
			$('#htPrice').val(price*discount-yhPrice+jtPrice+bgPrice+otherPrice);
		}
	</script>
<script type="text/javascript">
<!--分包相关信息-->
$('input[name="fb"]').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
	changed();
});
function changed(){
	var radioVal=$('input[name="fb"]:checked').val();
	if(radioVal=='是'){
		$('.fbTr').show()
	}else{
		$('.fbTr').hide()
		$('#fb_tb').html('');
	}
};
function chooseFbUnit(){
	var ids=[];
	$('input[id^="fbId"]').each(function(){
		ids.push($(this).val());
	});
	var	url='${basePath}res/supplier/selects.do?ids='+ids.join(',');
	layer.open({
		title:'分包单位',	
		type: 2,
		 area: ['70%', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: url,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			addFbRow(data);
		}
	});
}
function chooseItem4Fb(n){
	//获取本次需要的检测项目集合
	var ids='';
	$('input[id^="itemId"]').each(function(){
		var itemId=$(this).val();
		var itemIds=itemId.split(',');
		for(var i=0;i<itemIds.length;i++){
			if(itemIds[i]!='' && ids.indexOf(itemIds[i])<0){
				ids+=itemIds[i]+",";
			}
		}
	});
	if(ids==''){
		layer.msg("请先选择检测项目", {icon: 0,time: 3000});
		return false;
	}
	//获取已选分包项目
	var	url='fb_item_select.do?ids='+ids+'&id='+$('#itemIds'+n).val();
	layer.open({
		title:'分包项目选择',	
		type: 2,
		 area: ['70%', '85%'],
		fix: false, //不固定
		maxmin: true,
		content: url,
		btn: ['确定','取消'], //按钮
		btn1: function(index, layero) {
			var iframeWin = window[layero.find('iframe')[0]['name']];
			var data=iframeWin.fnSelect();
			$('#itemIds'+n).val(data.id.join(','));
			$('#itemNames'+n).val(data.name.join(','));
			$('#price'+n).val(data.price);
			$('#num'+n).val(data.id.length);
			countFbPrice();
		}
	});
}
function addFbRow(data){
	var table=$('#fb_tb');
	var num=table.find('tr').length;
	var index=0;
	if(num>0){
		index=parseInt(table.find('tr').eq(num-1).attr('index'));
		index++;
	}
	data.forEach(function(val) {
		table.append($('<tr index='+index+'>').append('<td>'+(index+1)+'</td>')
				.append('<td>'+val.name+'<input type="hidden" id="fbId'+index+'" name="fbList['+index+'].fbVo.id" value="'+val.id+'"></td>')
				.append('<td><input type="text" id="fbUser'+index+'" name="fbList['+index+'].fbUser" class="form-control required" validate="required" value="'+val.linkman+'"></td>')
				.append('<td><input type="text" id="fbMobile'+index+'" name="fbList['+index+'].fbMobile" class="form-control required" validate="required" value="'+val.linkmanTel+'"></td>')
				.append('<td><div class="input-group" style="width: 100%">'+
					'<input type="text" id="itemNames'+index+'" name="fbList['+index+'].itemNames" class="form-control required" validate="required" placeholder="请选择" onclick="chooseItem4Fb('+index+');">'+
					'<input type="hidden" id="itemIds'+index+'" name="fbList['+index+'].itemIds" >'+
					'<div class="input-group-btn"><button tabindex="-1" class="btn btn-primary" type="button"  onclick="chooseItem4Fb('+index+');">选择</button></div>'+
		 			'</div></td>')
		 		.append('<td><input type="text" id="num'+index+'" name="fbList['+index+'].num" class="form-control digits required" validate="required" ></td>')
				.append('<td><input type="text" id="price'+index+'" name="fbList['+index+'].price" class="form-control number required" validate="required" onchange="countFbPrice()"></td>')
				.append('<td><a  href="javascript:;" onclick="removeTr(this);"><font color="red">删除</font></a></td>'));
		index++;
	});
	countFbPrice();
}
function countFbPrice(){
	var price=0;
	$('#fb_tb input[name$=".price"]').each(function(){
		if(isNaN(parseFloat($(this).val()))){
			price+=0;
		}else{
			price+=parseFloat($(this).val());
		}
	})
	$('#fbPrice').val(price);
}
</script>
</body>
</html>
