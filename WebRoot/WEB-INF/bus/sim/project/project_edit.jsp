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
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><strong>项目立项</strong></li>
					<li><strong>编辑</strong> <c:if test="${vo.isBack=='Y'}">
							（<span style="color: red"> 退回原因：${logVo.msg}</span> ）
						</c:if></li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" value="${vo.id}" type="hidden" />
					</c:if>
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active" rowspan="4" style="text-align: center; width: 50px;"><label>委<br>托<br>单<br>位
								</label></td>
								<td class="active" style="width: 120px;"><label class="pull-right">单位名称:</label></td>
								<td style="width: 35%;">
									<div class="input-group" style="width: 100%">
										<input type="text" id="custName" name="custVo.custName" class="form-control required" validate="required" placeholder="请选择" value="${vo.custVo.custName}"> <input type="hidden" id="custId" name="custVo.clientVo.id" value="${vo.custVo.clientVo.id}">
										<div class="input-group-btn">
											<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown">
												<span class="caret"></span>
											</button>
											<ul class="dropdown-menu dropdown-menu-right" role="menu">
											</ul>
										</div>
									</div>
								</td>
								<td class="active"  style="width: 120px;"><label class="pull-right">单位地址:</label></td>
								<td ><input type="text" id="custAddress" name="custVo.custAddress" class="form-control required" validate="required"  value="${vo.custVo.custAddress}"></td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td><input type="text" id="custUser" name="custVo.custUser" class="form-control required" validate="required" value="${vo.custVo.custUser}"></td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td><input type="text" id="custTel" name="custVo.custTel" class="form-control required" validate="required"  value="${vo.custVo.custTel}"></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">行业分类和代码:</label></td>
								<td>
									<div class="input-group" style="width: 100%;">
										<input type="text" id="industry" name="custVo.industry" class="form-control "  value="${vo.custVo.industry}">
										<div class="input-group-btn">
											<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown" style="border-right-color: #ccc; border-top-color: #ccc; border-bottom-color: #ccc;">
												<span class="caret"></span>
											</button>
											<ul class="dropdown-menu dropdown-menu-right" role="menu">
											</ul>
										</div>
									 </div>
								</td>
								<td class="active"><label class="pull-right">单位性质:</label></td>
								<td><input type="text" id="attribute" name="custVo.attribute" class="form-control" value="${vo.custVo.attribute}"></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">主要产品:</label></td>
								<td colspan="3"><input type="text" id="product" name="custVo.product" class="form-control" value="${vo.custVo.product}"></td>
							</tr>
							<tr>
							 <td id="rowsTd" class="active" rowspan="7" style="text-align: center;"><label>项<br>目<br>要<br>求
								</label></td>
								<td class="active"><label class="pull-right">项目名称:</label></td>
								<td>
									<input type="text" id="sampName" name="sampName" class="form-control required" validate="required"   value="${vo.sampName}"> 
								</td>
								<td class="active"><label class="pull-right">项目类型:</label></td>
								<td>
									<select id="itemType" name="itemType" class="form-control required" validate="required"></select>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">加&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;急:</label></td>
								<td>
									<div class="radio i-checks">
										<label>
											<div class="iradio_square-green">
												<input type="radio" value="是" name="jj" <c:if test="${vo.jj=='是'}">checked</c:if>>
											</div>是
										</label> <label>
											<div class="iradio_square-green">
												<input type="radio" value="否" name="jj" <c:if test="${vo.jj!='是'}">checked</c:if>>
											</div>否
										</label>
									</div>
								</td>
								<td class="active"><label class="pull-right">拟完成日期:</label></td>
								<td>
									<div class="input-group date">
										<input type="text" id="finishDate" name="finishDate" class="form-control  dateISO" placeholder="请选择"  value="${vo.finishDate}"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">合同签订:</label></td>
								<td>
									<div class="radio i-checks">
										<label>
											<div class="iradio_square-green">
												<input type="radio" value="是" name="htqd" <c:if test="${vo.htqd=='是'}">checked</c:if>>
											</div>是
										</label> <label>
											<div class="iradio_square-green">
												<input type="radio" value="否" name="htqd" <c:if test="${vo.htqd!='是'}">checked</c:if>>
											</div>否
										</label>
									</div>
								</td>
								<td class="active"><label class="pull-right ">现场踏勘:</label></td>
								<td class="zcytd">
									<div class="radio i-checks">
										<label>
											<div class="iradio_square-green">
												<input type="radio" value="是" name="xctk" <c:if test="${vo.xctk=='是'}">checked</c:if>>
											</div>是
										</label> 
										<label>
											<div class="iradio_square-green">
												<input type="radio" value="否" name="xctk" <c:if test="${vo.xctk!='是'}">checked</c:if>>
											</div>否
										</label>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">方案编制:</label></td>
								<td>
									<div class="radio i-checks">
										<label>
											<div class="iradio_square-green">
												<input type="radio" value="是" name="fabz" <c:if test="${vo.fabz=='是'}">checked</c:if>>
											</div>是
										</label> <label>
											<div class="iradio_square-green">
												<input type="radio" value="否" name="fabz" <c:if test="${vo.fabz!='是'}">checked</c:if>>
											</div>否
										</label>
									</div>
								</td>
								<td class="active"></td>
								<td></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">项目负责人:</label></td>
								<td><input type="text" id="userName" name="userName" class="form-control  "  value="${vo.userName}">
									<input type="hidden" id="userId" name="userId" value="${vo.userId}">
								</td>
								<td class="active"><label class="pull-right">立项日期:</label></td>
								<td>
									<div class="input-group date">
										<input type="text" id="rdate" name="rdate" class="form-control " placeholder="请选择"  value="${vo.rdate}"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<th class="active"><label class="pull-right">上传资料:</label></th>
								<td><input type="file" name="file" multiple="multiple" class="form-control" /></td>
								<td colspan="2" id="removeFile">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
										<div style="float: left; margin-right: 10px;">
											<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a> 
											<c:if test="${e.createUser==user}">
												<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
											</c:if>	
										</div>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3"><textarea rows="3" class="form-control" id="remark" name="remark" maxlength="128">${vo.remark}</textarea></td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('save4Data.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
							<a id="sub_btn"  class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('${fn:length(vo.id)>0? 'update4Data.do?isCommit=1':'add4Data.do?isCommit=1'}');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
							<a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
	<script src="${basePath}h/js/plugins/suggest/bootstrap-suggest.min.js"></script>
	<!-- Sweet alert -->
	<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script>
	
	$("#custName").bsSuggest({
	    url: "${basePath}cus/client/ajax4Json.do",
	    effectiveFields: ["name","person"], 
	    effectiveFieldsAlias:{name: "名称",person: "联系人"},
	    searchFields: ["name","person"], 
	    showBtn: false,
	    idField: "id",
	    keyField: "name"
	}).on('onSetSelectValue', function (e, keyword, row) {
		$("#custId").val(row.id);
		$("#custName").val(row.name);
		$("#custUser").val(row.person);
		$("#custAddress").val(row.address);
		$("#custTel").val(row.phone);
		$("#industry").val(row.cusType);//行业
		$("#attribute").val(row.attribute);
		$("#product").val(row.product);
	});
	$("#wtCustName").bsSuggest({
	    url: "${basePath}cus/customer/ajax4FullJson.do",
	    effectiveFields: ["name","person","code"], 
	    effectiveFieldsAlias:{name: "名称",code: "编号",person: "联系人"},
	    searchFields: ["name","person"], 
	    showBtn: false,
	    idField: "id",
	    keyField: "name"
	}).on('onSetSelectValue', function (e, keyword, row) {
		$("#wtCustId").val(row.id);
		$("#wtCustName").val(row.name);
		$("#wtUser").val(row.person);
		$("#wtAddress").val(row.address);
		$("#wtTel").val(row.telephone);
		$("#wtEmail").val(row.email);
		$("#wtZip").val(row.zip);
	});
	laydate.render({
		  elem: '#rdate',
		  theme: 'molv',
		  done: function(value, date, endDate){
			  countNewFinishDay();
		  }
	});
	function countNewFinishDay(){
		var rdate=$('#rdate').val();
		var array = rdate.split("-");
		var dt = new Date(array[0], (parseInt(array[1])-1), parseInt(array[2]));
		
		var longTime=1000*60*60*24*56;//立项日期+40个工作日(56天)
		dt.setTime(dt.getTime()+longTime);
		year=dt.getFullYear();
		month=dt.getMonth()+1;
		day=dt.getDate();
		$('#finishDate').val(year+'-'+(month>9?month:'0'+month)+'-'+(day>9?day:'0'+day));
	}
	function countFinishDay(){
		var pc=parseInt($('#pc').val());
		var rdate=$('#rdate').val();
		var array = rdate.split("-");
		var dt = new Date(array[0], (parseInt(array[1])-1), parseInt(array[2]));
		var cycleUnit= $('#cycleUnit').val();
		var cycle= parseInt($('#cycle').val());
		cycle=cycle*pc;
		if(cycleUnit=='年'){
			var month=dt.getMonth()+1;
			var day=dt.getDate();
			var year=dt.getFullYear();
			year=year+cycle;
			$('#finishDate').val(year+'-'+(month>9?month:'0'+month)+'-'+(day>9?day:'0'+day));
		}else if(cycleUnit=='季'){
			var month=(dt.getMonth()+1)+cycle*3;
			var day=dt.getDate();
			var year=dt.getFullYear();
			year=year+Math.floor(month/12);
			month=month%12;
			$('#finishDate').val(year+'-'+(month>9?month:'0'+month)+'-'+(day>9?day:'0'+day));
		}else if(cycleUnit=='月'){
			var month=(dt.getMonth()+1)+cycle;
			var day=dt.getDate();
			var year=dt.getFullYear();
			year=year+Math.floor(month/12);
			month=month%12;
			$('#finishDate').val(year+'-'+(month>9?month:'0'+month)+'-'+(day>9?day:'0'+day));
		}else{
			var longTime=1000*60*60*24*cycle;
			dt.setTime(dt.getTime()+longTime);
			year=dt.getFullYear();
			month=dt.getMonth()+1;
			day=dt.getDate();
			$('#finishDate').val(year+'-'+(month>9?month:'0'+month)+'-'+(day>9?day:'0'+day));
		}
	}
	$('input[type="file"]').prettyFile();
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
			//多选框内删除
			$(".chosen-container").hover(function(){  
	            $(this).addClass('chosen-container-active');  
	        },function(){  
	        	 $(this).removeClass('chosen-container-active');  
	        })
	        
			$(".i-checks").iCheck({
				checkboxClass : "icheckbox_square-green",
				radioClass : "iradio_square-green",
			});
			itemTypeSelect();
			
			var arrStr=industrySelect();
			$('#industry').bsSuggest({
			    indexId: 0, //data.value 的第几个数据，作为input输入框的内容
			    indexKey: 0, //data.value 的第几个数据，作为input输入框的内容
			    data: getData(arrStr),
			    autoDropup:true,
			    showBtn: true
			});
			
		});
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
		function itemTypeSelect() {
			$.ajax({
				url : '${basePath}sys/code/ajaxCodeContent.do?code=cus-xmlx',
				datatype : "json",
				success : function(data) {
					var value = data.split(",");
					var optionstring = "<option value=\"\" >-请选择-</option>";
					for (var i = 0; i < value.length; i++) {
						if('${vo.itemType}'== value[i]){
						optionstring += "<option value=\"" + value[i] + "\" selected=\"selected\">"
								+ value[i] + "</option>";
						}else{
							optionstring += "<option value=\"" + value[i] + "\" >"
								+ value[i] + "</option>";
						}
					}
					$("#itemType").html(optionstring);
				}
			});
		}
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

		function formSubmitSave(url){
			$('#thisForm').attr('action',url);
			$('#thisForm').submit()
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
		    	})
			}else{
				parent.toastr.error('请检查必填项！', '');
			}
		}
	</script>
</body>
</html>
