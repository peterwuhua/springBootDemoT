<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico"> 
<%@ include file="../../include/css.jsp"%>
<%@ include file="../../include/status.jsp"%>
   
<!-- Sweet Alert -->
<link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link href="${basePath}h/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                   <div class="ibox-title">
						<ol class="breadcrumb">
							<li><a href="javascript:backMainPage();">质控计划</a></li>
							<li>
								<strong>
								<c:if test="${fn:length(vo.id)>0}">编辑</c:if>
								<c:if test="${fn:length(vo.id)==0}">新增</c:if>
								</strong>
							</li> 
						</ol>
					</div>
                    <div class="ibox-content">
                        <form id="thisForm" method="post" class="form-horizontal" >
		                    <c:if test="${fn:length(vo.id)>0}">
		                    	<input name="id" value="${vo.id}" type="hidden" />
		                    </c:if>
		                    <div align="center" style="font-size: 18px;font-weight: bold;">
		                    	<label id="data_3">
		                    		<div class="input-group date" style="width:70px;float: left;">
		                    			<input style="width:70px;" class="form-control add-on" type="text" id="year" name="year" value="${vo.year}">
		                    		 </div>
		                    		 <div style="float: left;padding-top: 2px;">年</div>
		                    	</label>
		                    	<label id="data_4">
		                    		<div class="input-group date" style="width:50px;float: left;">
		                                <input style="width:50px;" type="text" class="form-control add-on" id="month" name="month" value="${vo.month}">
		                            </div>
		                            <div style="float: left;padding-top: 2px;">月份内部质控计划表</div>
		                    	</label>
		                    </div>
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">制定人:</label></td>
										<td class="width-35">
											<input id="acceptName" class="form-control" name="acceptName" type="text" value="${vo.acceptName}" readonly="readonly"/>
											<input type="hidden" id="acceptId"  name="acceptId" value="${vo.acceptId}">
	                                    </td>
										<td class="width-15 active"><label class="pull-right">制定时间:</label></td>
										<td class="width-35">
											<input id="acceptDate" class="form-control" name="acceptDate" type="text" value="${vo.acceptDate }" readonly="readonly"/>
										</td>
	                                </tr>
								</tbody>
							</table>
							<table class="table table-hover">
								 <thead>
								 	<tr>
								 		<th style="text-align: center;width: 50px;">序号</th>
								 		<th style="text-align: center;width: 100px;">考核日期</th>
								 		<th style="text-align: center;width: 150px;">考核项目</th>
								 		<th style="text-align: center;width: 100px;">考核方式</th>
								 		<th style="text-align: center;width: 150px;">被考核人员</th>
								 		<th style="text-align: center;width: 150px;">检测仪器</th>
								 		<th style="text-align: center;width: 150px;">标准物质</th>
								 		<th>备注</th>
								 		<th style="text-align: center;width: 50px;"><button onclick="addRow()" type="button" class="btn btn-info btn-xs"><i class="fa fa-plus"></i>添加</button></th>
								 	</tr>
								 </thead>
								<tbody id="detail_tb">
									<c:forEach items="${vo.sampList}" var="e" varStatus="s">
										<tr index="${s.index}">
									 		<td style="padding:4px;text-align: center;">
									 			${s.index+1}
									 		</td>
									 		<td style="padding:4px;">
									 			<input type="text" id="assDate${s.index}" name="sampList[${s.index}].assDate" class="form-control dateISO" placeholder="请选择" value="${e.assDate}" >
									 		</td>
									 		<td style="padding:4px;">
									 			<input type="text" id="itemNames${s.index}" name="sampList[${s.index}].itemNames" class="form-control" placeholder="请选择" value="${e.itemNames}"  onclick="chooseItem(${s.index});">
									 			<input type="hidden" id="itemIds${s.index}" name="sampList[${s.index}].itemIds"  value="${e.itemIds}" >
									 		</td>
									 		<td style="padding:4px;">
									 			<input type="text" id="type${s.index}" name="sampList[${s.index}].type" class="form-control" placeholder="请输入" value="${e.type}">
									 		</td>
									 		<td style="padding:4px;">
												<input type="text" id="userNames${s.index}" name="sampList[${s.index}].userNames" class="form-control" placeholder="请选择" value="${e.userNames}"  onclick="fnSelectUsers(${s.index})">
												<input type="hidden" id="userIds${s.index}" name="sampList[${s.index}].userIds" value="${e.userIds}">
									 		</td>
									 		<td style="padding:4px;">
												<input type="text" id="appNames${s.index}" name="sampList[${s.index}].appNames" class="form-control" placeholder="请选择" value="${e.appNames}" onclick="fnSelectApps('${s.index}')">
												<input type="hidden" id="appIds${s.index}" name="sampList[${s.index}].appIds" value="${e.appIds}">
									 		</td>
									 		<td style="padding:4px;">
												<input type="text" id="standNames${s.index}" name="sampList[${s.index}].standNames" class="form-control" placeholder="请选择" value="${e.standNames}" onclick="fnSelectStands('${s.index}')">
												<input type="hidden" id="standIds${s.index}" name="sampList[${s.index}].standIds" value="${e.standIds}">
									 		</td>
									 		<td style="padding:4px;">
									 			<input maxlength="128" type="text" name="sampList[${s.index}].remark" class="form-control" placeholder="请输入" value="${e.remark}">
									 		</td>
									 		<td align="center"><a  href="javascript:;" onclick="deleteOne('${e.id}');"><font color="red">删除</font></a></td>
									 	</tr>
									</c:forEach>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-12 col-sm-offset-1">
                                    <button class="btn btn-w-m btn-success" type="button" onclick="formSubmitSave('save.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</button>
                                    <button class="btn btn-w-m btn-primary" type="button" onclick="formSubmit('${fn:length(vo.id)>0? 'updateData.do?isCommit=1':'addData.do?isCommit=1'}');"><i class="fa fa-check" aria-hidden="true"></i> 保存并提交</button>
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
    <!-- Data picker -->
    <script src="${basePath}h/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    
    <script type="text/javascript">

    $('#data_3 .input-group.date').datepicker({
    	minViewMode: 'years',
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true,
        todayHighlight: true
    }).on('changeDate',function(e){
    	var year=e.date.getFullYear();
    	$('#year').val(year);
    });

    $('#data_4 .input-group.date').datepicker({
        minViewMode: 'months',
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true,
        todayHighlight: true
    }).on('changeDate',function(e){
    	var month=e.date.getMonth()+1;
    	if(month<10){
    		$('#month').val('0'+month);
    	}else{
    		$('#month').val(month);
    	}
    });
    
    function formSubmitSave(url){
		$('#thisForm').attr('action',url);
		$('#thisForm').submit()
	}
	function formSubmit(url){
		var length=$('#detail_tb').find('tr').length;
    	if(length==0){
    		layer.msg('请添加计划详情', {icon: 0,time: 3000});
    		return true;
    	}
		swal({
	        title: "您确定要提交该任务吗",
	        text: "提交后将无法修改，请谨慎操作！",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
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
	function fnSelectUsers(n){
		var userIds=$('#userIds'+n).val();
		var userNames=$('#userNames'+n).val();
		layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'user_select.do?objIds='+userIds+'&objNames='+userNames,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#userIds'+n).val(data.id);
				$('#userNames'+n).val(data.name);
			}
		});
	}
	function fnSelectApps(n){
		var appIds=$('#appIds'+n).val();
		var appNames=$('#appNames'+n).val();
		layer.open({
			title:'仪器选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'app_select.do?objIds='+appIds+'&objNames='+appNames,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#appIds'+n).val(data.id);
				$('#appNames'+n).val(data.name);
			}
		});
	}
	function fnSelectStands(n){
		var standIds=$('#standIds'+n).val();
		var standNames=$('#standNames'+n).val();
		layer.open({
			title:'仪器选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'stand_select.do?objIds='+standIds+'&objNames='+standNames,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#standIds'+n).val(data.id);
				$('#standNames'+n).val(data.name);
			}
		});
	}
	function chooseItem(n){
		var itemIds=$('#itemIds'+n).val();
		var itemNames=$('#itemNames'+n).val();
		layer.open({
			title:'仪器选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: 'item_select.do?objIds='+itemIds+'&objNames='+itemNames,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#itemIds'+n).val(data.id);
				$('#itemNames'+n).val(data.name);
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
		table.append($('<tr index='+index+'>').append('<td align="center">'+(index+1)+'</td>')
				.append('<td style="padding:4px;"><input type="text" id="assDate'+index+'" name="sampList['+index+'].assDate" class="form-control dateISO" placeholder="请选择" ></td>')
				.append('<td style="padding:4px;"><input type="text" id="itemNames'+index+'" name="sampList['+index+'].itemNames" class="form-control" placeholder="请选择" onclick="chooseItem('+index+');"><input type="hidden" id="itemIds'+index+'" name="sampList['+index+'].itemIds" ></td>')
				.append('<td style="padding:4px;"><input type="text" id="type'+index+'" name="sampList['+index+'].type" class="form-control" placeholder="请输入"></td>')
				.append('<td style="padding:4px;">'+
					'<input type="text" id="userNames'+index+'" name="sampList['+index+'].userNames" class="form-control" placeholder="请选择"  onclick="fnSelectUsers('+index+')"><input type="hidden" id="userIds'+index+'" name="sampList['+index+'].userIds" ></td>')
				.append('<td style="padding:4px;">'+
					'<input type="text" id="appNames'+index+'" name="sampList['+index+'].appNames" class="form-control" placeholder="请选择" onclick="fnSelectApps('+index+')"><input type="hidden" id="appIds'+index+'" name="sampList['+index+'].appIds"  ></td>')
				.append('<td style="padding:4px;">'+
					'<input type="text" id="standNames'+index+'" name="sampList['+index+'].standNames" class="form-control" placeholder="请选择"  onclick="fnSelectStands('+index+')"><input type="hidden" id="standIds'+index+'" name="sampList['+index+'].standIds" ></td>')
				.append('<td style="padding:4px;"><input maxlength="128" type="text" name="sampList['+index+'].remark" class="form-control" placeholder="请输入" ></td>')
				.append('<td align="center"><a onclick="removeTr(this)"><i class="fa fa-times fa-2x text-danger"></i></a></td>'));
		$('.dateISO').each(function(){
			  laydate.render({
			    elem: this,
			    theme: 'molv',
			    calendar: true,
			    trigger: 'click'
			  });
			});
	}
	function removeTr(obj){
		$(obj).parent().parent().remove();
	}
    </script>
</body>
</html>
