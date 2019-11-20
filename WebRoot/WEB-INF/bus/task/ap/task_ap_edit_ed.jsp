<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<style type="text/css">
legend{
	border-bottom:0px;
	width: 80px;
	margin-bottom:0px;
	font-size: 14px !important;
}
.form-control{
	padding: 4px;
}
tbody.ct_td>tr>td{
	padding: 0px;
}
.closeTd{
	color: blue;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>
					<ol class="breadcrumb">
						<li><a>采样安排</a></li>
						<li><strong>已办</strong></li>
						<li><strong>更新</strong></li>
					</ol>
				</h5>
			</div>
			<div class="ibox-content">
				<form method="post" action="updateYb.do" class="form-horizontal" id="thisForm">
					<input name="id" value="${vo.id}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tr>
							<td class="active"  style="width: 150px;"><label class="pull-right">任务编号:</label></td>
							<td>
								<a onclick="fnTask('${vo.id}','${vo.no}');">${vo.no}</a>
							</td>
							<td class="active"  style="width: 150px;"><label class="pull-right">单位名称:</label></td>
							<td>
								${vo.custVo.custName}
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right">检测类型:</label></td>
							<td>
								${vo.taskType}
							</td>
							<td class="active"><label class="pull-right">样品名称:</label></td>
							<td>
								${vo.sampName}
							</td>
						</tr>
						<tr>
							 
							<td class="active"><label class="pull-right">受理日期:</label></td>
							<td>
								${vo.date}
							</td>
							<td class="active"><label class="pull-right">要求完成日期:</label></td>
							<td>
								${vo.finishDate}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">采样开始日期:</label></td>
							<td class="width-35">
								${vo.cyDate}
							</td>
							<td class="width-15 active"><label class="pull-right">采样结束日期:</label></td>
							<td class="width-35">
								${vo.cyEndDate}
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right"> 采样小组人员:</label></td>
							<td class="width-35">
								<div class="input-group" style="width: 100%;">
									<input type="hidden" id="cyId" name="cyId" value="${vo.cyId}">
									<input type="text" id="cyName" name="cyName" value="${vo.cyName}" class="form-control required" validate="required" placeholder="请选择" onclick="fnSelectUsers()">
									<div class="input-group-btn">
										<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUsers()">选择</button>
									</div>
								</div>
							</td>
							<td class="active"><label class="pull-right">负&nbsp;&nbsp;责&nbsp;人:</label></td>
							<td>
								<select id="fzId" name="fzId" class="form-control required" validate="required" onchange="fnSave()">
									<c:forEach items="${fzList}" var="e">
										<c:choose>
											<c:when test="${e.id==vo.fzId}">
												<option value="${e.id}" selected="selected">${e.userName}</option>
											</c:when>
											<c:otherwise>
												<option value="${e.id}">${e.userName}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<input type="hidden" id="fzName" name="fzName"  value="${vo.fzName}">
							</td>
						</tr>
						<tr>
							<td class="active">
								<label class="pull-right ">采样设备:</label>
							</td>
							<td>
								<div class="input-group" style="width: 100%">
									<div class="chosen-container chosen-container-multi chosen-with-drop" style="width: 85%">
										<ul class="chosen-choices" id="appUL">
											<c:forEach items="${vo.appList}" var="e" varStatus="v">
												<li class="search-choice" index="${v.index}">
													<span>${e.appName}</span>
			          								<input name="appList[${v.index}].id" value="${e.id}" type="hidden" />
			          								<input name="appList[${v.index}].appId" value="${e.appId}" type="hidden" />
			          								<input name="appList[${v.index}].appName" value="${e.appName}" type="hidden" />
			          								<a class="search-choice-close" onclick="removeLi(this)" title="删除"><i class="fa fa-close text-danger"></i></a>
		          								</li>
											</c:forEach>
										</ul>
									</div>
									<button class="btn btn-primary" type="button" onclick="fnSelectApp()">选择</button>
								</div>
							</td>
							<td class="active"><label class="pull-right ">车辆预约:</label></td>
							<td>
								<div class="input-group" style="width: 100%">
									<div class="chosen-container chosen-container-multi chosen-with-drop" style="width: 85%">
										<ul class="chosen-choices" id="carUL">
											<c:forEach items="${vo.carList}" var="e" varStatus="v">
												<li class="search-choice" index="${v.index}">
													<span>${e.name}(${e.code})</span>
			          								<input name="carList[${v.index}].id" value="${e.id}" type="hidden" />
			          								<input name="carList[${v.index}].carId" value="${e.carId}" type="hidden" />
			          								<input name="carList[${v.index}].name" value="${e.name}" type="hidden" />
			          								<a class="search-choice-close" onclick="removeLi(this)" title="删除"><i class="fa fa-close text-danger"></i></a>
		          								</li>
											</c:forEach>
										</ul>
									</div>
									<button class="btn btn-primary" type="button" onclick="fnSelectCar()">选择</button>
								</div>
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">采样方法:</label></td>
							<td colspan="3">
								<div class="input-group" style="width: 100%">
									<textarea id="cyStandNames" name="cyStandNames" class="form-control" onclick="chooseCyMethod()">${vo.cyStandNames}</textarea>
									<input id="cyStandIds" name="cyStandIds" value="${vo.cyStandIds}" type="hidden" />
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>检测点位信息配置</b>
								<div style="float: right;">【<a class="closeTd" onclick="closeTd(this)">闭合</a>】</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 0px;">
								<table class="table table-bordered" style="margin-bottom: 0px;border: 0px;">
									<thead>
										<tr>
											<th width="5%">序号</th>
											<th width="10%">检测点位</th>
											<th width="10%">样品名称</th>
											<th width="7%" style="text-align: center;">采样数量</th>
											<th width="7%" style="text-align: center;">空白样</th>
											<th width="7%" style="text-align: center;">平行样</th>
											<th width="12%">采样频次</th>
											<th>检测项目</th>
										</tr>
									</thead>
									<tbody id="point_tb">
										<c:forEach items="${vo.tpList}" var="e" varStatus="v">
											<tr key="${v.index}" >
												<td>
													${e.sort}
													<input name="tpList[${v.index}].id" value="${e.id}" type="hidden" />
													<input id="sampId_${v.index}" name="tpList[${v.index}].sampTypeId" value="${e.sampTypeId}" type="hidden" />
												</td>
												
												<td>
													${e.pointName}
												</td>
												<td>
													${e.sampName}
												</td>
												<td>
													${e.sampNum}
													<input name="tpList[${v.index}].sampNum" value="${e.sampNum}" type="hidden"/>
												</td>
												<td>
													<c:choose>
														<c:when test="${e.type=='声'}">
														/
														</c:when>
														<c:otherwise>
															${e.zkNum}<input name="tpList[${v.index}].zkNum" value="${e.zkNum}" type="hidden" />
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${e.type=='声'}">
														/
														</c:when>
														<c:otherwise>
															${e.pxNum}<input name="tpList[${v.index}].pxNum" value="${e.pxNum}" type="hidden" />
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													${e.pc} ${e.pcUnit}
													<input  name="tpList[${v.index}].pc" value="${e.pc}" type="hidden" />
													<input   name="tpList[${v.index}].pcUnit" value="${e.pcUnit}" type="hidden"  />
												</td>
												<td>
													${e.itemNames}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>样品信息确认</b>
								<div style="float: right;">【<a class="closeTd" onclick="closeTd(this)">闭合</a>】</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" style="padding: 0px;">
								<table class="table table-bordered" style="margin-bottom: 0px;border: 0px;">
									<thead>
										<tr>
											<th width="60px">序号</th>
											<th width="15%">检测点位</th>
											<th width="15%">样品名称</th>
											<th width="100px">采样日期</th>
											<th width="50px">批次</th>
											<th width="20%" style="text-align: center;">样品编号</th>
											<th>检测项目</th>
											<th width="20%">使用容器及仪器</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="num" value="0"/>
										<c:forEach items="${vo.tpList}" var="m">
										<c:forEach items="${m.sampList}" var="e" varStatus="v">
											<tr>
												<td>
													<input type="text" class="form-control required" validate="required" name="sampList[${num}].sort" value="${e.sort}" >
													<input name="sampList[${num}].id" value="${e.id}" type="hidden" />
												</td>
												<td>
													${e.pointName}
												</td>
												<td>
													${e.sampName}
												</td>
												<td>
													${e.cyDate}
												</td>
												<td>
													${e.p}
												</td>
												<td>
													<input type="text" id="sampCode_${num}" class="form-control required" validate="required" name="sampList[${num}].sampCode" value="${e.sampCode}" >
												</td>
												<td>
													${e.itemNames}
												</td>
												<td>
													 <table class="table" style="margin-bottom: 0px;">
							                            <tbody class="ct_td" id="zkTb_ct_${num}" index="${num}">
							                            	<c:forEach items="${e.containerList}" var="e1" varStatus="v1">
								                                <tr>
								                                    <td>
								                                    	${e1.container}
								                                    </td>
								                                    <td width="40">
								                                    	 ${e1.num}
								                                    </td>
								                                </tr>
							                                </c:forEach>
							                            </tbody>
							                        </table>
												</td>
											</tr>
											<c:set var="num" value="${num+1}"/>
										</c:forEach>
										<c:forEach items="${m.zkList}" var="e" varStatus="v">
											<tr>
												<td>
													<input type="text" class="form-control required" validate="required" name="sampList[${num}].sort" value="${e.sort}" >
													<input name="sampList[${num}].id" value="${e.id}" type="hidden" />
												</td>
												<td>
													${e.pointName}
												</td>
												<td>
													${e.sampName}
												</td>
												<td>
													${e.cyDate}
												</td>
												<td>
													${e.p}
												</td>
												<td>
													<input type="text" id="sampCode_${num}" class="form-control required" validate="required" name="sampList[${num}].sampCode" value="${e.sampCode}" >
												</td>
												<td>
													${e.itemNames}
												</td>
												<td>
													 <table class="table" style="margin-bottom: 0px;">
							                            <tbody class="ct_td" id="zkTb_ct_${num}" index="${num}">
							                            	<c:forEach items="${e.containerList}" var="e1" varStatus="v1">
								                                <tr>
								                                    <td>
								                                    	${e1.container}
								                                    </td>
								                                    <td width="40">
								                                    	 ${e1.num}
								                                    </td>
								                                </tr>
							                                </c:forEach>
							                            </tbody>
							                        </table>
												</td>
											</tr>
											<c:set var="num" value="${num+1}"/>
										</c:forEach>
										</c:forEach>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<b>安排人信息</b>
							</td>
						</tr>
						<tr>
							<td class="width-15 active"><label class="pull-right">安&nbsp;排&nbsp;&nbsp;人:</label></td>
							<td class="width-35">
								${vo.apName}
								<input type="hidden" id="apId" name="apId" value="${vo.apId}">
								<input type="hidden" id="apName" name="apName" value="${vo.apName}" >
							</td>
							<td class="width-15 active"><label class="pull-right"> 安排日期:</label></td>
							<td class="width-35">
								${vo.apDate}
								<input type="hidden" id="apDate" name="apDate" value="${vo.apDate}">
							</td>
						</tr>
						<tr>
							<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
							<td colspan="3">
								<textarea rows="2" class="form-control" id="apMsg" name="apMsg">${vo.apMsg}</textarea>
							</td>
						</tr>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-primary" type="button" onclick="fnSubmit('updateYb.do')"><i class="fa fa-floppy-o" aria-hidden="true"></i> 更新</a>
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
	function fnTask(id,no){
		parent.layer.open({
			title:'任务【'+no+'】',	
			type: 2,
			area: ['1000px','85%'],
			fix: false, //不固定
			maxmin: true,
			content: '/bus/task/show.do?id='+id
		});
	}
	function fnSubmit(url){
		$('#thisForm').attr('action',url);
		var b = $("#thisForm").FormValidate();
		if(b){
	    	$('#fzName').val($('#fzId').find('option:selected').text());
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
	$(document).ready(function() {
		$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
		 
		$(".chosen-container").hover(function(){  
            $(this).addClass('chosen-container-active');  
        },function(){  
        	 $(this).removeClass('chosen-container-active');  
        })
	});
	function fnSelectUsers(){
		var userId=$('#cyId').val();
		layer.open({
			title:'采样人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'user_selects.do?ids='+userId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  $('#cyId').val(data.id);
			  $('#cyName').val(data.name);
			  setCyFzr(data.id,data.name);
			}
		});
	}
	//设置采样负责人
	function setCyFzr(ids,names){
		var fzId='${vo.fzId}';
		$('#fzId').html('');
		for(var i=0;i<ids.length;i++){
			if(fzId==ids[i]){
				$('#fzId').append('<option value="'+ids[i]+'" selected="selected">'+names[i]+'</option>');
			}else{
				$('#fzId').append('<option value="'+ids[i]+'">'+names[i]+'</option>');
			}
		}
		fnSave();
	}
	//采样设备
	function fnSelectApp(){
		var ids='';
		$('input[name$=".appId"]').each(function(){
			if(ids.indexOf($(this).val())<0){
				ids+=$(this).val()+",";
			}
		});
		layer.open({
			title:'采样设备',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'appara_select.do?ids='+ids,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  setAppData(data.id,data.name);
			}
		});
	}
	function setAppData(id,name){
		var appUL=$('#appUL');
		var index=0;
		var indexStr=appUL.children("li").last().attr('index');
		if(indexStr!=undefined){
			index=parseInt(indexStr)+1;
		}
		for(var i=0;i<id.length;i++){
			var liStr='<li class="search-choice" index="'+index+'"><span>'+name[i]+'</span>'+
	          '<input name="appList['+index+'].appId" value="'+id[i]+'" type="hidden" />'+
	          '<input name="appList['+index+'].appName" value="'+name[i]+'" type="hidden" />'+
	          '<a class="search-choice-close"  onclick="removeLi(this)" title="删除"><i class="fa fa-close text-danger"></i></a></li>';
	          appUL.append(liStr);
	          index++;
		}
	}
	function removeLi(obj){
		$(obj).parent().remove();
		layer.msg('删除成功', {icon: 0,time: 3000});
	}
	//车辆信息
	function fnSelectCar(){
		var ids='';
		$('input[name$=".carId"]').each(function(){
			if(ids.indexOf($(this).val())<0){
				ids+=$(this).val()+",";
			}
		});
		layer.open({
			title:'车辆预约',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'car_select.do?ids='+ids+'&id=${vo.id}',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  setCarData(data.id,data.name);
			}
		});
	}
	function setCarData(id,name){
		var carUL=$('#carUL');
		var index=0;
		var indexStr=carUL.children("li").last().attr('index');
		if(indexStr!=undefined){
			index=parseInt(indexStr)+1;
		}
		for(var i=0;i<id.length;i++){
			var liStr='<li class="search-choice" index="'+index+'"><span>'+name[i]+'</span>'+
	          '<input name="carList['+index+'].carId" value="'+id[i]+'" type="hidden" />'+
	          '<input name="carList['+index+'].name" value="'+name[i]+'" type="hidden" />'+
	          '<a class="search-choice-close"  onclick="removeLi(this)" title="删除"><i class="fa fa-close text-danger"></i></a></li>';
	          carUL.append(liStr);
	          index++;
		}
	}
	//采样容器
	function fnSelectCt(n){
		var ids='';
		$('#ct_'+n).find('input[name$=".containerId"]').each(function(){
			if(ids.indexOf($(this).val())<0){
				ids+=$(this).val()+",";
			}
		});
		layer.open({
			title:'采样容器',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/container/selects.do?ids='+ids,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var data=iframeWin.fnSelect();
			  setCtData(data.id,data.name,n);
			}
		});
	}
	function setCtData(ids,names,n){
		var td='';
		for(var i=0;i<ids.length;i++){
			td+='<tr>'+
	                '<td>'+names[i]+
			        	'<input name="tpList['+n+'].containerList['+i+'].containerId"  value="'+ids[i]+'" type="hidden" />'+
						'<input name="tpList['+n+'].containerList['+i+'].container"  value="'+names[i]+'" type="hidden" />'+
			       '</td>'+
			       '<td width="40">'+
			        	 '<input name="tpList['+n+'].containerList['+i+'].num" value="1" type="text" min="1" class="form-control required digits" validate="required"/>'+ 
			        '</td>'+
			        '<td width="34" style="text-align:center;"><a onclick="removeTr(this)"><i class="fa fa-close text-danger"></i></a></td>'+
	   		 	'</tr>';
		}
		$('#ct_'+n).html(td);
	}
	function removeTr(obj){
		$(obj).closest('tr').remove();
	}
	function copyCt(n){
		var ptd=$('#ct_'+n).children('tr');
		var sampId=$('#sampId_'+n).val();
		if(ptd.length>0){
			var i=n;
			$('#point_tb tr:gt('+n+')').each(function(){
				i++;
				var newSampId=$('#sampId_'+i).val();
				if(sampId==newSampId){
					var trStr='';
					for(var j=0;j<ptd.length;j++){
						var value1=$(ptd[j]).find('input').eq(0).val();
						var value2=$(ptd[j]).find('input').eq(1).val();
						var value3=$(ptd[j]).find('input').eq(2).val();
						trStr+='<tr><td>'+value2+
			                	'<input name="tpList['+i+'].containerList['+j+'].containerId"  value="'+value1+'" type="hidden" />'+
								'<input name="tpList['+i+'].containerList['+j+'].container"  value="'+value2+'" type="hidden" />'+
			               		'</td><td width="40">'+
			                	'<input name="tpList['+i+'].containerList['+j+'].num" value="'+value3+'" type="text"  class="form-control required digits" validate="required"/>'+ 
			               		'</td><td width="34" style="text-align:center;"><a onclick="removeTr(this)"><i class="fa fa-close text-danger"></i></a></td></tr>';
					}
					$('#ct_'+i).html(trStr);
				}
			});
		}
	}
	</script>
	<script type="text/javascript">
	function closeTd(obj){
		$(obj).html('展开');
		$(obj).closest('tr').next().hide();
		$(obj).attr('onclick','openTd(this)');
	}
	function openTd(obj){
		$(obj).html('闭合');
		$(obj).closest('tr').next().show();
		$(obj).attr('onclick','closeTd(this)');
	}
	function chooseItem(allIds,n,samplingId){
		$('#thisForm').ajaxSubmit(function(res) {
	    	if(res.status=='success'){
	    	    parent.toastr.success(res.message, '');
	        }else{
	        	parent.toastr.error(res.message, '');
	        }
		});
		//获取已选分包项目
		var	url='item_select.do?ids='+allIds+'&id='+$('#itemIds_'+n).val();
		layer.open({
			title:'项目选择',	
			type: 2,
			 area: ['800px', '450px'],
			fix: false, //不固定
			maxmin: true,
			content: url,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#itemIds_'+n).val(data.id.join(','));
				$('#itemNames_'+n).val(data.name.join(','));
				var sampCode=ajaxGetSampCode(samplingId,data.id.join(','));
				$('#sampCode_'+n).val(sampCode);
			}
		});
	}
	function ajaxGetSampCode(samplingId,itemIds){
		var code='';
		$.ajax({ 
			url:"${basePath}bus/taskAp/ajaxGetSampCode.do",
			data: {'id':samplingId,'ids':itemIds},
			async:false,
			success: function(data){
				code=data;
			},
			error:function(ajaxobj){  
				layer.msg(ajaxobj, {icon: 0,time: 3000});
		    }
		});
		return code;
	}
	function chooseCyMethod(){
		var ids=$('#cyStandIds').val();
		parent.layer.open({
			title:'采样方法',	
			type: 2,
			area: ['1200px', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}init/sampSource/select2.do?ids='+ids,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin=layero.find('iframe')[0];
				var data=iframeWin.contentWindow.fnSelect();
				$('#cyStandIds').val(data.id);
				$('#cyStandNames').val(data.name);
				parent.layer.close(index);
			}
		});
	}
	</script>
</body>
</html>
