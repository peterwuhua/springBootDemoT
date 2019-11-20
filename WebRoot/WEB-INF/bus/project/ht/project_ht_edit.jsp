<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<%@ include file="../../../include/status.jsp"%>

<style type="text/css">
legend{
border-bottom:0px;
width: 80px;
margin-bottom:0px;
font-size: 14px !important;
}
.form-control{
	padding: 4px 4px;
}
.table select{
	padding-top: 2px;
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
					<li><strong>合同签订</strong></li>
					<li><strong>${vo.sampType}</strong></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">项目编号:</label></td>
							<td class="width-35">
								<a href="javascript:void();" onclick="fnShow('${vo.id}','${vo.no}');">${vo.no}</a>
							</td>
							<td class="width-15 active"><label class="pull-right">受检单位:</label></td>
							<td class="width-35">
								${vo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
							<td class="width-15 active"><label class="pull-right">样品名称:</label></td>
							<td class="width-35">
								${vo.sampName}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">立项日期:</label></td>
							<td class="width-35">${vo.rdate}</td>
							<td class="width-15 active"><label class="pull-right">拟完成日期:</label></td>
							<td class="width-35">${vo.finishDate}</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								${vo.remark}
							</td>
						</tr>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">合同费用:</label></td>
							<td class="width-35">
								${vo.invoiceVo.htPrice}
							</td>
							 <td class="width-15 active"><label class="pull-right">预付款比例:</label></td>
							<td class="width-35">
								<input id="payRatio" name="invoiceVo.payRatio"  class="form-control number required" range="[0,1]" validate="required"  maxlength="128" type="text" value="${vo.invoiceVo.payRatio }" onchange="countYSPrice();"/>
								<input id="invoiceVo.htPrice" name="invoiceVo.htPrice"  class="form-control"   type="hidden" value="${vo.invoiceVo.htPrice}" />
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">预付金额:</label></td>
							<td class="width-35">
								<input id="yfkMin" name="invoiceVo.yfkMin"  class="form-control" readonly="readonly"  type="text" value="${vo.invoiceVo.yfkMin }" />
							</td>
							<td class="width-15 active"><label class="pull-right">剩余金额:</label></td>
							<td class="width-35">
								<input id="sskMin" name="invoiceVo.sskMin"  class="form-control" readonly="readonly"  type="text" value="${vo.invoiceVo.sskMin }" />
							</td>
						</tr>
					</table>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="width-15 active"><label class="pull-right">合同编号:</label></td>
							<td class="width-35">
								<input type="text" id="htNo" name="htNo" value="${vo.htNo}" class="form-control required" validate="required">
							</td>
							<td class="width-15 active"><label class="pull-right">合同状态:</label></td>
							<td class="width-35">
								${vo.htSt}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">合同模板:</label></td>
							<td>
								<select class="form-control required" validate="required" id="htTemp" name="htTemp" onchange="changeHtTemp();">
									<option value="">-请选择-</option>
									<option value="HT-AQ-SJ.ftl">安全三级标准化评审合同</option>
									<option value="HT-AQ-YJ.ftl">安全应急预案合同</option>
									<option value="HT-HJ-JS.ftl">环境技术服务合同</option>
									<option value="HT-WT-JC.ftl">委托检测协议</option>
									<option value="HT-ZW-XZ.ftl">职卫现状评价合同</option>
									<option value="HT-ZY-WS.ftl">职业卫生检测技术服务合同</option>
									<option value="HT-ZH-JS.ftl">综合技术服务合同</option>
								</select>
							</td>
							<td colspan="2">
								<c:choose>
									<c:when test="${vo.htSt=='未生成'}">
										<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitHt('saveData4Ht.do');"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 生成合同</a>
									</c:when>
									<c:otherwise>
										<a class="btn btn-w-m btn-danger" href="javascript:;" onclick="formSubmitHt('saveData4Ht.do');"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> 重置合同</a>
									</c:otherwise>
								</c:choose>
								<c:if test="${vo.htName!=null && vo.htName!=''}">
								 	<div class="btn-group" id="editReprt">
				                       <a class="btn btn-info" type="button" onclick="fnOpenWord();">在线编辑</a>
				                    </div>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">签订人员:</label></td>
							<td class="width-35">
								<div class="input-group">
									<input type="hidden" id="qdId" name="qdId" value="${vo.qdId}">
									<input type="text" id="qdName" name="qdName" value="${vo.qdName}" class="form-control required" validate="required" placeholder="请选择" onclick="fnSelectUserHt()">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUserHt()">选择</button>
									</div>
								</div>
							</td>
							<td class="width-15 active"><label class="pull-right">签订日期:</label></td>
							<td class="width-35">
								<%-- <div class="input-group date">
									<input type="text" id="qdDate" name="qdDate" class="form-control datetimeISO required" validate="required" value="${vo.qdDate}">
									 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div> --%>
								<div class="input-group date" >
										<input id="qdDate" name="qdDate"  class="form-control dateISO"  type="text" value="${vo.qdDate}" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="qdMsg" name="qdMsg" maxlength="128">${vo.qdMsg}</textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
						      <a class="btn btn-w-m btn-primary" href="javascript:void(0);" onclick="formSubmitSave('updateData.do?isCommit=0');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<c:if test="${vo.htSt!='未生成'}">
								<a class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('updateData.do?isCommit=1');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
							</c:if>
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
         <!--PageOffice.js -->
     <script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
	<script>
	function fnShow(id,no){
		parent.layer.open({
			title:'项目详情【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/project/show.do?id='+id
		});
	}
	function fnSelectUserHt(){
		var userId=$('#qdId').val();
		layer.open({
			title:'签订人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/selects.do?ids='+userId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#qdId').val(data.id);
			  $('#qdName').val(data.name);
			}
		});
	}
	function formSubmitHt(url){
		var l=$('#editReprt').find('a').length;
		if(l>0){
			swal({
		        title: "合同已存在，您确定要重新生成合同文件吗",
		        text: "重新生成后原合同将被删除，请谨慎操作！",
		        type: "warning",
		        showCancelButton: true,
		        confirmButtonColor: "#DD6B55",
		        cancelButtonText: "取消",
		        confirmButtonText: "确定"
		    }, function () {
		    	$('#thisForm').attr('action',url);
				$('#thisForm').submit()
	   		});
		}else{
			$('#thisForm').attr('action',url);
			$('#thisForm').submit()
		}
	}
	function changeHtTemp(){
		$('#thisForm').attr('action','updateData.do?isCommit=0');
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
		        location.reload() 
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
	function formSubmitSave(url){
    	$('#thisForm').attr('action',url);
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
		        location.reload() 
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
	}
	function formSubmit(url){
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
			swal({
		        title: "您确定要提交该任务吗",
		        text: "提交后将无法修改，请谨慎操作！",
		        type: "success",
		        showCancelButton: true,
		        confirmButtonColor: "#1ab394",
		        confirmButtonText: "确定",
		        cancelButtonText: "取消"
		    }, function () {
				 $('#thisForm').ajaxSubmit(function(res) {
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
	function fnOpenWord(){
		POBrowser.openWindow('${basePath}bus/projectHt/editWord.do?id=${vo.id}','width=1200px;height=800px;');
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
	
	 $(document).ready(function(){
		 countYSPrice();
		 var v='${vo.htTemp}';
		 $('#htTemp').find('option[value="'+v+'"]').attr('selected','selected');
	});
	function countYSPrice(){
		var htprice = "${vo.invoiceVo.htPrice}";
		var contractJe=parseFloat(htprice);//合同金额
		var payratio=parseFloat($('#payRatio').val());//预付款比例
		var yfk = contractJe*payratio;
		var ssk = contractJe- yfk;
		$('#yfkMin').val(yfk);
		$('#sskMin').val(ssk);
	}
</script>
</body>
</html>
