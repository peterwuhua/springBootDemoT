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
					<li><strong>${vo.sampType}</strong></li>
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
					<input name="sampType" value="${vo.sampType}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active" rowspan="4" style="text-align: center; width: 50px;"><label>受<br>检<br>单<br>位
								</label></td>
								<td class="width-10 active"><label class="pull-right">单位名称:</label></td>
								<td class="width-30">
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
								<td class="width-10 active"><label class="pull-right">单位地址:</label></td>
								<td class="width-30"><input type="text" id="custAddress" name="custVo.custAddress" class="form-control required" validate="required" value="${vo.custVo.custAddress}"></td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td><input type="text" id="custUser" name="custVo.custUser" class="form-control required" validate="required" value="${vo.custVo.custUser}"></td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td><input type="text" id="custTel" name="custVo.custTel" class="form-control required" validate="required" value="${vo.custVo.custTel}"></td>
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
								<%-- <select id="industry" name="custVo.industry" class="form-control required" validate="required" value="${vo.custVo.industry}"></select> --%>
								</td>
								<td class="active"><label class="pull-right">单位性质:</label></td>
								<td><input type="text" id="attribute" name="custVo.attribute" class="form-control" value="${vo.custVo.attribute}"></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">主要产品:</label></td>
								<td colspan="3"><input type="text" id="product" name="custVo.product" class="form-control" value="${vo.custVo.product}"></td>
							</tr>
							<tr>
								<td class="active" rowspan="3" style="text-align: center; width: 50px;"><label>委<br>托<br>单<br>位
								</label></td>
								<td class="width-10 active"><label class="pull-right">单位名称:</label></td>
								<td class="width-30">
									<div class="input-group" style="width: 100%">
										<input type="text" id="wtCustName" name="custVo.wtName" class="form-control" value="${vo.custVo.wtName}"> <input type="hidden" id="wtCustId" name="custVo.customerVo.id" value="${vo.custVo.customerVo.id}">
										<div class="input-group-btn">
											<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown">
												<span class="caret"></span>
											</button>
											<ul class="dropdown-menu dropdown-menu-right" role="menu">
											</ul>
										</div>
									</div>
								</td>
								<td class="width-10 active"><label class="pull-right">单位地址:</label></td>
								<td class="width-30"><input type="text" id="wtAddress" name="custVo.wtAddress" class="form-control" value="${vo.custVo.wtAddress}"></td>

							</tr>
							<tr>
								<td class="active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td><input type="text" id="wtUser" name="custVo.wtUser" class="form-control" value="${vo.custVo.wtUser}"></td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td><input type="text" id="wtTel" name="custVo.wtTel" class="form-control" value="${vo.custVo.wtTel}"></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
								<td><input type="text" id="wtEmail" name="custVo.wtEmail" class="form-control email" value="${vo.custVo.wtEmail}"></td>
								<td class="active"><label class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:</label></td>
								<td><input type="text" id="wtZip" name="custVo.wtZip" class="form-control" value="${vo.custVo.wtZip}"></td>
							</tr>
							<tr>
								<td id="rowsTd" class="active" rowspan="9" style="text-align: center;"><label>检<br>测<br>要<br>求
								</label></td>
								<td class="active"><label class="pull-right">样品类别:</label></td>
								<td colspan="3">
									<div class="input-group" style="width: 100%">
										<input type="text" id="sampTypeName" name="sampTypeName" class="form-control required" validate="required" placeholder="请选择" value="${vo.sampTypeName}" onclick="fnSelectSampType()"> <input type="hidden" id="sampTypeId" name="sampTypeId" value="${vo.sampTypeId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectSampType()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">检测类型:</label></td>
								<td><select name="taskType" id="taskType" class="form-control required" validate="required">
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
								</select></td>
								<td class="active"><label class="pull-right">项目频次:</label></td>
								<td><input style="width: 90%; display: inline-block;" type="number" id="pc" name="pc" class="form-control required digits" min="1" validate="required" value="${vo.pc}" onchange="countFinishDay()"> <input type="hidden" id="pcUnit" name="pcUnit" value="${vo.pcUnit}"> <span>${vo.pcUnit}</span></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">立项日期:</label></td>
								<td>
									<div class="input-group date">
										<input type="text" id="rdate" name="rdate" class="form-control required" placeholder="请选择" validate="required" value="${vo.rdate}"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
								<td class="active"><label class="pull-right">单次周期:</label></td>
								<td><input style="width: 80%; display: inline-block;" type="number" id="cycle" name="cycle" min="1" class="form-control required digits" validate="required" value="${vo.cycle}" onchange="countFinishDay()"> <select style="width: 18%; display: inline-block;" id="cycleUnit" name="cycleUnit" class="form-control" onchange="countFinishDay()">
										<c:choose>
											<c:when test="${vo.cycleUnit=='年'}">
												<option selected="selected">年</option>
												<option>月</option>
												<option>日</option>
											</c:when>
											<c:when test="${vo.cycleUnit=='月'}">
												<option>年</option>
												<option selected="selected">月</option>
												<option>日</option>
											</c:when>
											<c:otherwise>
												<option>年</option>
												<option>月</option>
												<option selected="selected">日</option>
											</c:otherwise>
										</c:choose>
								</select></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">报告份数:</label></td>
								<td><input type="number" id="reportNum" name="reportNum" class="form-control required digits" validate="required" value="${vo.reportNum}"></td>

								<td class="active"><label class="pull-right">拟完成日期:</label></td>
								<td>
									<div class="input-group date">
										<input type="text" id="finishDate" name="finishDate" class="form-control required dateISO" placeholder="请选择" validate="required" value="${vo.finishDate}"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">取报告方式:</label></td>
								<td>
									<div class="radio i-checks">
										<label>
											<div class="iradio_square-green">
												<input type="radio" value="自取" name="reportWay" <c:if test="${vo.reportWay=='自取'}">checked</c:if>>
											</div>自取
										</label> <label>
											<div class="iradio_square-green">
												<input type="radio" value="邮寄" name="reportWay" <c:if test="${vo.reportWay=='邮寄'}">checked</c:if>>
											</div>邮寄
										</label> <label>
											<div class="iradio_square-green">
												<input type="radio" value="送达" name="reportWay" <c:if test="${vo.reportWay=='送达'}">checked</c:if>>
											</div>送达
										</label> <label>
											<div class="iradio_square-green">
												<input type="radio" value="其它" name="reportWay" <c:if test="${vo.reportWay=='其它'}">checked</c:if>>
											</div>其它
										</label>
									</div>
								</td>
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
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">样品来源:</label></td>
								<td>
									<div class="radio i-checks">
										<label>
											<div class="iradio_square-green">
												<input type="radio" value="送样" name="zsy" <c:if test="${vo.zsy=='送样'}">checked</c:if>>
											</div>送样
										</label> <label>
											<div class="iradio_square-green">
												<input type="radio" value="外采" name="zsy" <c:if test="${vo.zsy!='送样'}">checked</c:if>>
											</div>外采
										</label>
									</div>
								</td>
								<td class="active"><label class="pull-right ">同意使用非标准方法:</label></td>
								<td>
									<div class="radio i-checks">
										<label>
											<div class="iradio_square-green">
												<input type="radio" value="是" name="fbzff" <c:if test="${vo.fbzff=='是'}">checked</c:if>>
											</div>是
										</label> <label>
											<div class="iradio_square-green">
												<input type="radio" value="否" name="fbzff" <c:if test="${vo.fbzff!='是'}">checked</c:if>>
											</div>否
										</label>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">现场踏勘:</label></td>
								<td class="zcytd">
									<div class="radio i-checks">
										<label>
											<div class="iradio_square-green">
												<input type="radio" value="是" name="xctk" <c:if test="${vo.xctk=='是'}">checked</c:if>>
											</div>是
										</label> <label>
											<div class="iradio_square-green">
												<input type="radio" value="否" name="xctk" <c:if test="${vo.xctk!='是'}">checked</c:if>>
											</div>否
										</label>
									</div>
								</td>
								<td class="active"><label class="pull-right ">是否评价:</label></td>
								<td>
									<div class="radio i-checks">
										<label>
											<div class="iradio_square-green">
												<input type="radio" value="是" name="pj" <c:if test="${vo.pj=='是'}">checked</c:if>>
											</div>是
										</label> <label>
											<div class="iradio_square-green">
												<input type="radio" value="否" name="pj" <c:if test="${vo.pj!='是'}">checked</c:if>>
											</div>否
										</label>
									</div>
								</td>
							</tr>
							<tr id="pjTr">
								<td class="active"><label class="pull-right">评价依据:</label></td>
								<td colspan="3">
									<div class="input-group" style="width: 100%">
										<input type="text" id="standNames" name="standNames" class="form-control required" validate="required" placeholder="请选择" value="${vo.standNames}" onclick="fnSelectPstand()"> <input type="hidden" id="standIds" name="standIds" value="${vo.standIds}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectPstand()">选择</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">委托方提供资料:</label></td>
								<td colspan="3">
									<div class="checkbox i-checks">
										<label>
											<div class="icheckbox_square-green">
												<input type="checkbox" value="厂区总平面图" name="info" <c:if test="${fn:contains(vo.info,'厂区总平面图')}">checked</c:if>>
											</div>厂区总平面图
										</label> <label>
											<div class="icheckbox_square-green">
												<input type="checkbox" value="环保工程资料" name="info" <c:if test="${fn:contains(vo.info,'环保工程资料')}">checked</c:if>>
											</div>环保工程资料
										</label> <label>
											<div class="icheckbox_square-green">
												<input type="checkbox" value="主要污染源情况" name="info" <c:if test="${fn:contains(vo.info,'主要污染源情况')}">checked</c:if>>
											</div>主要污染源情况
										</label> <label>
											<div class="icheckbox_square-green">
												<input type="checkbox" value="项目工程资料" name="info" <c:if test="${fn:contains(vo.info,'项目工程资料')}">checked</c:if>>
											</div>项目工程资料
										</label> <label>
											<div class="icheckbox_square-green">
												<input type="checkbox" value="设备清单" name="info" <c:if test="${fn:contains(vo.info,'设备清单')}">checked</c:if>>
											</div>设备清单
										</label> <label>
											<div class="icheckbox_square-green">
												<input type="checkbox" value="其他" name="info" <c:if test="${fn:contains(vo.info,'其它')}">checked</c:if>>
											</div>其他
										</label>
									</div>
								</td>
							</tr>
							<tr>
								<th class="active"><label class="pull-right">上传文件:</label></th>
								<td><input type="file" name="file" multiple="multiple" class="form-control" /></td>
								<td colspan="2" id="removeFile"><c:forEach items="${vo.fileList}" var="e" varStatus="v">
										<div style="float: left; margin-right: 10px;">
											<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a> <a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
										</div>
									</c:forEach></td>
							</tr>
							<tr>
								<td class="active" rowspan="2" style="text-align: center;"><label>受<br>理<br>方<br>信<br>息
								</label></td>
								<td class="active"><label class="pull-right">立&nbsp;&nbsp;项&nbsp;&nbsp;人:</label></td>
								<td><input type="hidden" id="userId" name="userId" value="${vo.userId}"> <input type="text" id="userName" name="userName" class="form-control" placeholder="受理人" value="${vo.userName}" readonly="readonly"></td>
								<td class="active"><label class="pull-right ">受理日期:</label></td>
								<td>
									<div class="input-group date">
										<input id="sdate" name="sdate" class="form-control required dateISO" validate="required" placeholder="受理日期" type="text" value="${vo.sdate}" /> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
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
							<c:choose>
								<c:when test="${vo.zsy=='送样'}">
									<a id="sub_btn" style="display: none;" class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('${fn:length(vo.id)>0? 'update4Data.do?isCommit=1':'add4Data.do?isCommit=1'}');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
									<a id="next_btn" class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmitSave('save2Next.do');"><i class="fa fa-level-down" aria-hidden="true"></i> 保存并下一步</a>
								</c:when>
								<c:otherwise>
									<a id="sub_btn" class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmit('${fn:length(vo.id)>0? 'update4Data.do?isCommit=1':'add4Data.do?isCommit=1'}');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</a>
									<a id="next_btn" style="display: none;" class="btn btn-w-m btn-primary" href="javascript:;" onclick="formSubmitSave('save2Next.do');"><i class="fa fa-level-down" aria-hidden="true"></i> 保存并下一步</a>
								</c:otherwise>
							</c:choose>
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
			  countFinishDay();
			  //countNewFinishDay();
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
	function fnSelectSampType(){
		var sampTypeId=$('#sampTypeId').val();
		layer.open({
			title:'样品类型',	
			type: 2,
			area: ['300px', '75%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/sampType/selects_last.do?ids='+sampTypeId+'&name='+encodeURI('${vo.sampType}'),
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#sampTypeId').val(data.id);
			  $('#sampTypeName').val(data.name);
			}
		});
	}
	 
		$('input[name="zsy"]').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
			changed2();
		});
		function changed2(){
			var radioVal=$('input[name="zsy"]:checked').val();
			if(radioVal=='送样'){
				$('.zcytd').find("input[type='radio']").each(function(){
					if(this.value == '否') {  
				        $(this).iCheck('check');  
				    } 
					$(this).parent().addClass('disabled')
					$(this).attr('disabled',true)
				})
				$('#pc').val(1);
				$('#pc').attr('readonly','readonly')
				$('#pcUnit').val('次');
				$('#pcUnit').parent().find('span').html('次'); 
				$('#sub_btn').hide();
				$('#next_btn').show();
				countFinishDay();
			}else{
				$('#next_btn').hide();
				$('#sub_btn').show();
				$('.zcytd').find("input[type='radio']").each(function(){
					$(this).parent().removeClass('disabled')
					$(this).removeAttr('disabled')
				})
				$('#pc').removeAttr('readonly')
			}
		};
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
		 
		$('input[name="pj"]').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
			changedPj();
		});
		function changedPj(){
			var radioVal=$('input[name="pj"]:checked').val();
			if(radioVal=='是'){
				$('#pjTr').show();
				$('#rowsTd').attr('rowspan',10);
				$('#standNames').attr('validate','required');
				$('#standNames').addClass('required');
			}else{
				$('#pjTr').hide();
				$('#rowsTd').attr('rowspan',9);
				$('#standNames').removeAttr('validate');
				$('#standNames').removeClass('required');
			}
		};
		//评价标准 弹出层
		function fnSelectPstand(){
			var sampTypeId=$('#sampTypeId').val();
			var id=$('#standIds').val();
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
			changed2();
			changedPj();
			fnPstand();
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
