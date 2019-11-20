<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>

</head>
<body>
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a>请假申请</a></li>
					<li><strong>编辑</strong></li>
				</ol>
			</div>
			<div class="ibox-content" style="padding: 5px 10px;">
				<form method="post" action="" class="form-horizontal" id="thisForm" enctype="multipart/form-data">
					<c:if test="${fn:length(vo.id)>0}">
						<input name="id" id="id" value="${vo.id}" type="hidden" />
					</c:if>
					<c:if test="${fn:length(vo.no)>0}">
						<input name="no" id="no" value="${vo.no}" type="hidden" />
					</c:if>
					 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="width-15 active"><label class="pull-right">类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:</label></td>
								<td class="width-35">
								   <select name="type" id="type" class="form-control required" validate="required">
										<option value="">请选择</option>
										<c:forEach items="${typeList}" var="e" varStatus="s">
											<c:choose>
												<c:when test="${e==vo.type}">
													<option value="${e}" selected="selected">${e}</option>
												</c:when>
												<c:otherwise>
													<option value="${e}">${e}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<td class="width-15 active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label></td>
								<td class="width-35">
									<input  id="deptName" name="deptName" class="form-control " type="text" value="${vo.deptName}" />
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">开始时间:</label></td>
								<td>
									<div class="input-group date" >
										<input type="text" id="beginTime" name="beginTime" class="form-control" value="${vo.beginTime }"/>
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span> 
									</div> 
								</td>
								<td class="active"><label class="pull-right">结束时间:</label></td>
								<td>
									<div class="input-group date" >
										 <input id="endTime" name="endTime"  class="form-control"  type="text" value="${vo.endTime }" />
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span> 
									</div> 
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">共&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计:</label></td>
								<td class="width-35">
								   <div style="width:90%;height:39px;float:left;">
								   	<input  id="sumDay" name="sumDay"  class="form-control number" type="text" value="${vo.sumDay}" />
								   </div>
								   <div  style="width:10%;height:39px;float:left;"><label style=" position: relative;top:20%;left:40%;text-align: center;">小时</label></div>
								</td>
								<td class="active"></td>
								<td>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">请假事由:</label></td>
								<td colspan="3">
								   <textarea maxlength="128" rows="3" class="form-control" id="remark" placeholder="请假事由" name="remark">${vo.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">职位代理人:</label></td>
								<td>
									<div class="input-group">
										<input type="text" id="jober" name="jober" class="form-control"  placeholder="请选择" value="${vo.jober}" onclick="fnSelectJober()"> 
										<input type="hidden" id="joberId" name="joberId" value="${vo.joberId}">
										<div class="input-group-btn">
											<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectJober()">选择</button>
										</div>
									 </div>
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">申请人员:</label></td>
								<td>
									<input  id="person" name="person" class="form-control " type="text" value="${vo.person}" />
									<input type="hidden" id="personId" name="personId" value="${vo.personId}" >
								</td>
								<td class="active"><label class="pull-right">申请日期:</label></td>
								<td>
									<input  id="supportDate" name="supportDate" class="form-control " type="text" value="${vo.supportDate}" />
								</td>
							</tr>
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
	                <div class="form-group">
	                    <div class="col-sm-12 col-sm-offset-1">
	                        <a class="btn btn-w-m btn-primary" href="javascript:void(0);"
	                           onclick="formSubmitSave('save4Data.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 保存</a>
	                        <a class="btn btn-w-m btn-success" href="javascript:void(0);"
	                           onclick="formSubmit('${fn:length(vo.id)>0? 'update4Data.do?isCommit=1':'add4Data.do?isCommit=1'}');"><i
	                                class="fa fa-mail-forward" aria-hidden="true"></i> 保存并提交</a>
	                        <a class="btn btn-w-m btn-white" href="javascript:backMainPage();">
	                        <i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
	                    </div>
	                </div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../../include/js.jsp"%>
        <!-- Input Mask-->
    <script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
	<script>
	function fnSelectJober() {
		layer.open({
			title:'用户信息',	
			type: 2,
			area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/selects.do',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin = window[layero.find('iframe')[0]['name']];
			  var selectData = iframeWin.fnSelect();
			  $("#jober").val(selectData.name);
			  $("#joberId").val(selectData.id);
			}
		})
	}
	function formSubmitSave(url){
		$('#thisForm').attr('action',url);
		$('#thisForm').submit();
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
	
	function backMainPage(){
		 var basePath=location.href.substr(0,location.href.lastIndexOf("/")+1);
		 var currentIframe=$(window.parent.document).find(".J_iframe").filter( ":visible" );
		 var editPageKey=$(currentIframe).attr("data-id");
		 var mainPageKey=window.parent.pageMap.get(editPageKey);
		 window.parent.pageMap.remove(editPageKey);//移除map缓存
		 var mainIframe=$(window.parent.document).find('iframe[data-id="'+mainPageKey+'"]')[0];
		 $(mainIframe).attr("data-id",editPageKey);
		 $(mainIframe).css("display","inline");
		 var treeUrl=mainIframe.contentWindow.treeUrl;
		 if(!!treeUrl&&treeUrl!='undefined'){
			 mainIframe.contentWindow.reloadTree(basePath+treeUrl);
		 }
		 mainIframe.contentWindow.jqgridReload(); 
		 currentIframe.remove();
	}
	
	//生成yyyy-MM-dd日期格式并返回
	function scDate(year,month,day)
	{
		var nian = String(year); 
		var yue = "";
		if (month < 10)
			{
			yue = "0"+String(month);
			}else{
				yue = String(month);
			}
		
		var ri = "";
		if (day < 10)
			{
			ri = "0"+String(day);
			}else{
				ri = String(day);
			}
		return nian + "-" + yue + "-" +ri;
	}
	
	
	
	var starthour=0.0;
	var endhour=0.0;
	var starttime ;
	var endtime;
	var totalHour=0.0 ;
	laydate.render({
		  elem: '#beginTime'
		  ,type: 'datetime'
		  ,done: function(value, date, endDate){
			var svalue =   scDate( date.year ,date.month,date.date);
			starthour = date.hours;
		   var begintime = new Date(Date.parse(svalue.replace(/-/g, "/")));//将开始时间由字符串格式转换为日期格式   
		    starttime = begintime.getTime();//将开始日期转换成毫秒 
		     if (endtime != null && endtime != "")
		    	 {
		    	  var totalDay = parseInt((endtime-starttime)/1000/3600/24);//结束日期减去开始日期后转换成天数
		    	  if (starthour > endhour)
		    		  {
		    		  totalHour = totalDay*8 + (starthour-endhour);
		    		  }else{
		    			  totalHour = totalDay*8 + (endhour-starthour);
		    		  }
		    	  
		    	 }else{
		    		 totalHour=0.0 ;
		    	 }
		    $("#sumDay").val(totalHour);
		  }
		});
	
	
	
	laydate.render({
		  elem: '#endTime'
			  ,type: 'datetime'
		  ,done: function(value, date, endDate){
				var svalue =   scDate( date.year ,date.month,date.date);
				endhour = date.hours;
			   var endTime = new Date(Date.parse(svalue.replace(/-/g, "/")));//将结束时间由字符串格式转换为日期格式   
			   endtime = endTime.getTime();//将结束日期转换成毫秒 
			     if (starttime != null && starttime != "")
			    	 {
			    	 var totalDay = parseFloat((endtime-starttime)/(1000*3600*24));
			    	  if (starthour > endhour)
		    		  {
		    		  totalHour = totalDay*8 + (starthour-endhour);
		    		  }else{
		    			  totalHour = totalDay*8 + (endhour-starthour);
		    		  }
			    	 }else{
			    		 totalHour=0.0 ;
			    	 }
			   $("#sumDay").val(totalHour);
		  }
		});
	
	</script>
</body>
</html>