<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
<style type="text/css">
.panel-heading{
	padding: 0px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<form method="post" action="${fn:length(vo.id)>0? 'updateData.do':'addData.do'}" name="form" class="form-horizontal" id="thisForm">
			<c:if test="${fn:length(vo.id)>0}">
				<input name="id" value="${vo.id}" type="hidden" />
			</c:if>
				<input name="auth" value="${vo.auth}" type="hidden" />
				<input name="fstatus" class="form-control" type="hidden" value="${vo.fstatus}" /><!-- 新增状态为1 -->
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<ol class="breadcrumb">
						<li><a href="javascript:backMainPage();">客户</a></li>
						<li><strong>编辑</strong></li>
					</ol>
				</div>
				<div class="ibox-content">
					<div class="panel-heading">
						<div class="panel-options">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#cus1" data-toggle="tab">客户信息 </a></li>
								<li><a onmousedown="checkSave();" href="#" onclick="location.href='${basePath}cus/contacts/gridTab.do?customerVo.id=${vo.id}'" data-toggle="tab">联系人</a></li>
								<li><a onmousedown="checkSave();" href="#" onclick="location.href='${basePath}cus/saleContact/gridPage4Cus.do?customerId=${vo.id}'" data-toggle="tab">客户跟踪记录</a></li>
							</ul>
						</div>
					</div>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">客户名称:</label></td>
								<td class="width-35"><input id="name" name="name" class="form-control required" validate="required" maxlength="128" placeholder="名称" type="text" value="${vo.name }" onblur="checkCusName(this);" /></td>
								<td class="active"><label class="pull-right">法&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人:</label></td>
								<td><input id="custFaRen" name="custFaRen" class="form-control" type="text" value="${vo.custFaRen }"/></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</label></td>
								<td class="width-35"><input id="address" name="address" class="form-control required" validate="required" maxlength="128" placeholder="地址" type="text" value="${vo.address}" /></td>
								<td class="width-15 active"><label class="pull-right">固定电话:</label></td>
								<td class="width-35"><input id="telephone" name="telephone" class="form-control required" validate="required" placeholder="021-50835869" type="text" value="${vo.telephone}" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">信用代码 /<br> 身份证明:</label></td>
								<td><input id="custCode" name="custCode" class="form-control" maxlength="32" type="text" value="${vo.custCode }" /></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">开户银行:</label></td>
								<td><input id="custBank" name="custBank" class="form-control" maxlength="64" type="text" value="${vo.custBank }" /></td>
								<td class="active"><label class="pull-right">开户账号:</label></td>
								<td><input id="custAccount" name="custAccount" class="form-control" type="text" value="${vo.custAccount }"/></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;&nbsp;人:</label></td>
								<td class="width-35"><input id="person" name="person" class="form-control required" validate="required" maxlength="32" placeholder="联系人" type="text" value="${vo.person}" /></td>
								<td class="width-15 active"><label class="pull-right">联系方式:</label></td>
								<td class="width-35"><input id="phone" name="phone" class="form-control required" validate="required" placeholder="13800000000" type="text" value="${vo.phone}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
								<td class="width-35"><input id="email" name="email" class="form-control email" type="text" value="${vo.email}" /></td>
								<td class="width-15 active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
								<td class="width-35"><input id="zip" name="zip" class="form-control" type="text" value="${vo.zip}" /></td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真:</label></td>
								<td class="width-35">
									<input id="fax" name="fax"  class="form-control" type="text" value="${vo.fax }" />
								</td>
								<td class="width-15 active"><label class="pull-right">成立日期:</label></td>
								<td class="width-35">
									<div class="input-group date">
										<input id="buildDate" name="buildDate"  class="form-control dateISO"  type="text" value="${vo.buildDate }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">所属区域:</label></td>
								<td class="width-35">
									<div class="input-group">
										<input type="text" id="selectPath" name="areaPath" class="form-control" placeholder="请选择上级区域" value="${vo.areaPath}"  onclick="fnSelect()"> 
										<input type="hidden" id="selectId" name="areaId" value="${vo.areaId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect()">选择</button>
										</div>
									</div>
								</td>
								<td class="active"><label class="pull-right">行&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label></td>
								<td>
									<!-- <select id="industry" name="industry" class="form-control"></select> -->
									<div class="input-group" style="width: 100%;">
										<input type="text" id="industry" name="industry" class="form-control required" validate="required" value="${vo.industry}">
										<div class="input-group-btn">
											<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="border-right-color: red; border-top-color: red; border-bottom-color: red;">
												<span class="caret"></span>
											</button>
											<ul class="dropdown-menu dropdown-menu-right" role="menu">
											</ul>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="width-15 active"><label class="pull-right">客户属性:</label></td>
								<td class="width-35"><input id="cusType" name="cusType" class="form-control" type="text" value="${vo.cusType}" /></td>
							<!--<td class="active"><label class="pull-right">客户类型:</label></td>
								<td>
									<select id="cusCates" name="cusCates" class="form-control"></select>
								</td>-->
								<td class="active"><label class="pull-right">跟进状态:</label></td>
								<td>
								<input id="gjStatus" name="gjStatus" class="form-control" readonly="readonly" type="text" value="未签合同" />
								</td>
							</tr>
							<tr>
							    <td><label class="pull-right">销售人员:</label></td>
								<td>
											<input id="saler" name="saler" class="form-control" readonly="readonly" type="text" value="${vo.saler}" />
											<input id="salerId" name="salerId" class="form-control" type="hidden" value="${vo.salerId}" />	
								</td>
								<td class="active"><label class="pull-right">提交日期:</label></td>
								<td>
									<input id="supportDate" name="supportDate" class="form-control" readonly="readonly" type="text" value="${vo.supportDate}" />
								</td>
							</tr>
							
							
							<tr>
								<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3"><textarea maxlength="128" rows="2" class="form-control" id="remark" name="remark" placeholder="备注">${vo.remark}</textarea></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<button class="btn btn-w-m btn-success" type="button" onclick="formSubmit4Save('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
							<button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit();"><i class="fa fa-check" aria-hidden="true"></i> 保存并返回</button>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<script src="${basePath}h/js/plugins/suggest/bootstrap-suggest.min.js"></script>
	<script type="text/javascript">
	 $(document).ready(function(){
		$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green",})
		cusTypeSelect();	
		var arrStr=industrySelect();
		$('#industry').bsSuggest({
		    indexId: 0, //data.value 的第几个数据，作为input输入框的内容
		    indexKey: 0, //data.value 的第几个数据，作为input输入框的内容
		    data: getData(arrStr),
		    autoDropup:true,
		    showBtn: true
		});
		//industrySelect();
		//cusCatesSelect();
		//gjStatusSelect();
		//gradeSelect();
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
	 	function checkSave(){
	    	var id='${vo.id}';
	    	if(id==''){
	    		layer.alert('请先保存客户基本信息', {icon: 6});
	    		return false;
	    	}else{
	    		return true;
	    	}
	    }
	 	function gradeSelect(){
	 		$.ajax({
				url : '${basePath}sys/code/ajaxCodeContent.do?code=cus-grade',
				datatype : "json",
				success : function(data) {
					var value = data.split(",");
					var optionstring = "";
					for (var i = 0; i < value.length; i++) {
						if('${vo.grade}'== value[i]){
						optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
								+ value[i] + "</option>";
						}else{
							optionstring += "<option value=\"" + value[i] + "\" >"
								+ value[i] + "</option>";
						}
					}
					$("#grade").html(optionstring);
				}
			});
	 	}
		function cusTypeSelect() {
			$.ajax({
				url : '${basePath}sys/code/ajaxCodeContent.do?code=cus-khlx',
				datatype : "json",
				success : function(data) {
					var value = data.split(",");
					var optionstring = "<option value=\"\" >-请选择-</option>";
					for (var i = 0; i < value.length; i++) {
						if('${vo.cusType}'== value[i]){
						optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
								+ value[i] + "</option>";
						}else{
							optionstring += "<option value=\"" + value[i] + "\" >"
								+ value[i] + "</option>";
						}
					}
					$("#cusType").html(optionstring);
				}
			});
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
		
		function cusCatesSelect() { //客户类型选择
			$.ajax({
				url : '${basePath}/cus/normalList/fetchCustCates.do',
				datatype : "json",
				success : function(data) {
					var value = data;
					var optionstring = "<option value=\"\" >-请选择-</option>";
					for (var i = 0; i < value.length; i++) {
						if('${vo.cusCates}'== value[i]){
						optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
								+ value[i] + "</option>";
						}else{
							optionstring += "<option value=\"" + value[i] + "\" >"
								+ value[i] + "</option>";
						}
					}
					$("#cusCates").html(optionstring);
				}
			});
		}
		
		
		function gjStatusSelect() { //跟进状态选择
			$.ajax({
				url : '${basePath}/cus/normalList/fetchGjStatus.do',
				datatype : "json",
				success : function(data) {
					var value = data;
					var optionstring = "<option value=\"\" >-请选择-</option>";
					for (var i = 0; i < value.length; i++) {
						if('${vo.gjStatus}'== value[i]){
						optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
								+ value[i] + "</option>";
						}else{
							optionstring += "<option value=\"" + value[i] + "\" >"
								+ value[i] + "</option>";
						}
					}
					$("#gjStatus").html(optionstring);
				}
			});
		}
	</script>
	<script>
		function fnSelect() {
			var pId=$('#selectId').val();
			layer.open({
				title : '区域选择',
				type : 2,
				area : [ '300px', '470px' ],
				fix : false, //不固定
				maxmin : true,
				content : '${basePath}sys/area/select.do?id=' + pId,
				btn : [ '确定', '取消' ], //按钮
				btn1 : function(index, layero) {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var data=iframeWin.fnSelect();
					$('#selectId').val(data.id);
					$('#selectPath').val(data.path);
				}
			});
		}
		function checkCusName(obj){
			var id = '${vo.id}';
			var flag = false;
			$.ajax({
				url:'${basePath}/cus/customer/checkCusName.do',
				dataType:"json",
				data:{"id":id,"name":$(obj).val()},
				type:"post",
				async:false,
				success: function(data){
					if("error" == data.type){
						layer.alert(data.message, {
		   					icon: 2,
		   					shadeClose: true
		   				});
						$(obj).val('');
					}
					if("success" == data.type){
						flag =true;
					}
				},
				error:function(ajaxobj){
			    }  
			});
			return flag;
		}
		function fnSelectUser(){
			var salerId=$('#salerId').val();
			layer.open({
				title:'人员选择',	
				type: 2,
				 area: ['70%', '85%'],
				fix: false, //不固定
				maxmin: true,
				content: '/sys/account/user_select.do?id='+salerId,
				btn: ['确定','取消'], //按钮
				btn1: function(index, layero) {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var data=iframeWin.fnSelect();
					$('#salerId').val(data.id);
					$('#saler').val(data['userVo.name']);
				}
			});
		}
		function formSubmit4Save(url){
			if(!!$("#lastTestDate").val()){
				if(!$("#cycle").val()){
					layer.msg('请填写检测周期', {icon: 0,time: 3000});
					return false;
				}
			}
			$('#thisForm').attr('action',url);
			$('#thisForm').submit()
		}
		function formSubmit(){
			if(!!$("#lastTestDate").val()){
				if(!$("#cycle").val()){
					layer.msg('请填写检测周期', {icon: 0,time: 3000});
					return false;
				}
			}
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
</body>
</html>
