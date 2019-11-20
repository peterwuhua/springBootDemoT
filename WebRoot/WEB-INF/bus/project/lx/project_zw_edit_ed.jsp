<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../../include/css.jsp"%>
<%@ include file="../../../include/status.jsp"%>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><strong>项目立项</strong></li>
					<li><strong>${vo.sampType}</strong></li>
					<li><strong>编辑</strong>
						<c:if test="${vo.isBack=='Y'}">
							（<span style="color: red"> 退回原因：${logVo.msg}</span> ）
						</c:if>
					</li>
				</ol>
			</div>
			<div class="ibox-content">
				<form method="post"  class="form-horizontal" id="thisForm"  enctype="multipart/form-data">
					<input name="id" value="${vo.id}" type="hidden" />
					<input name="sampType" value="${vo.sampType}" type="hidden" />
					<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
						<tbody>
							<tr>
								<td class="active" rowspan="4" style="text-align: center;width: 50px;"><label>受<br>检<br>单<br>位</label></td>
								<td class="width-10 active"><label class="pull-right">单位名称:</label></td>
								<td class="width-30">
									<input type="text" id="custName" name="custVo.custName" class="form-control required" validate="required" value="${vo.custVo.custName}"> 
									<input type="hidden" id="custId" name="custVo.clientVo.id" value="${vo.custVo.clientVo.id}">
								</td>
								 <td class="width-10 active"><label class="pull-right">单位地址:</label></td>
								<td class="width-30">
									<input type="text" id="custAddress" name="custVo.custAddress" class="form-control required" validate="required" value="${vo.custVo.custAddress}"> 
								</td>
							</tr>
							<tr>
								<td class=" active"><label class="pull-right">联&nbsp;&nbsp;系&nbsp;人:</label></td>
								<td>
									<input type="text" id="custUser" name="custVo.custUser" class="form-control required" validate="required" value="${vo.custVo.custUser}"> 
								</td>
								<td class="active"><label class="pull-right">联系电话:</label></td>
								<td>
									<input type="text" id="custTel" name="custVo.custTel" class="form-control required" validate="required" value="${vo.custVo.custTel}"> 
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
								<td id="rowsTd" class="active" rowspan="7" style="text-align: center;"><label>检<br>测<br>要<br>求</label></td>
								 <td class="active"><label class="pull-right">检测类型:</label></td>
								<td>
									<select name="taskType" id="taskType" class="form-control required" validate="required">
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
									</select>
								</td>
								<td class="active"><label class="pull-right">样品名称:</label></td>
								<td>
									<input type="text" id="sampName" name="sampName" class="form-control" value="${vo.sampName}" > 
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">立项日期:</label></td>
								<td>
									<div class="input-group date">
										<input type="text" id="rdate" name="rdate"  class="form-control dateISO required" placeholder="请选择" validate="required" value="${vo.rdate}">
										 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
								<td class="active"><label class="pull-right">项目频次:</label></td>
								<td>
									${vo.pc}${vo.pcUnit}
									<input type="hidden" id="pc" name="pc"  value="${vo.pc}" >
									<input type="hidden" id="pcUnit" name="pcUnit" value="${vo.pcUnit}">
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">单次周期:</label></td>
								<td>
									${vo.cycle}${vo.cycleUnit}
									<input type="hidden" id="cycle" name="cycle"  value="${vo.cycle}" >
									<input type="hidden" id="cycleUnit" name="cycleUnit"  value="${vo.cycleUnit}" >
								</td>
								<td class="active"><label class="pull-right">拟完成日期:</label></td>
								<td>
									<div class="input-group date">
										<input type="text" id="finishDate" name="finishDate"  class="form-control required dateISO" placeholder="请选择" validate="required" value="${vo.finishDate}">
										 <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right">报告份数:</label></td>
								<td><input type="number" id="reportNum" name="reportNum"  class="form-control required digits" validate="required" value="${vo.reportNum}"></td>
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
                                            <input type="radio" value="送达" name="reportWay" <c:if test="${vo.reportWay=='送达'}">checked</c:if>>
                                            </div>送达
                                        </label>
                                         <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="其它" name="reportWay" <c:if test="${vo.reportWay=='其它'}">checked</c:if>>
                                            </div>其它
                                        </label>
                                    </div>
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
                                        </label>
                                        <label>
                                            <div class="iradio_square-green">
                                            <input type="radio" value="否" name="jj" <c:if test="${vo.jj!='是'}">checked</c:if>>
                                            </div>否
                                        </label>
                                    </div>
								</td>
								<td class="active"><label class="pull-right ">现场踏勘:</label></td>
								<td>
									${vo.xctk}
									<input type="hidden" id="xctk" name="xctk" value="${vo.xctk}"/>
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
                                        </label>
                                        <label>
                                            <div class="icheckbox_square-green">
                                            <input type="checkbox" value="环保工程资料" name="info" <c:if test="${fn:contains(vo.info,'环保工程资料')}">checked</c:if>>
                                            </div>环保工程资料
                                        </label>
                                        <label>
                                            <div class="icheckbox_square-green">
                                            <input type="checkbox" value="主要污染源情况" name="info" <c:if test="${fn:contains(vo.info,'主要污染源情况')}">checked</c:if>>
                                            </div>主要污染源情况
                                        </label>
                                         <label>
                                            <div class="icheckbox_square-green">
                                            <input type="checkbox" value="项目工程资料" name="info" <c:if test="${fn:contains(vo.info,'项目工程资料')}">checked</c:if>>
                                            </div>项目工程资料
                                        </label>
                                         <label>
                                            <div class="icheckbox_square-green">
                                            <input type="checkbox" value="设备清单" name="info" <c:if test="${fn:contains(vo.info,'设备清单')}">checked</c:if>>
                                            </div>设备清单
                                        </label>
                                         <label>
                                            <div class="icheckbox_square-green">
                                            <input type="checkbox" value="其他" name="info" <c:if test="${fn:contains(vo.info,'其它')}">checked</c:if>>
                                            </div>其他
                                        </label>
                                    </div>
								</td>
							</tr>
							<tr>
								<th class="active"><label class="pull-right">上传文件:</label></th>
								<td>
									<input type="file" name="file" multiple="multiple" class="form-control"/>
								</td>
								<td colspan="2" id="removeFile">
									<c:forEach items="${vo.fileList}" var="e" varStatus="v">
									 	<div style="float: left;margin-right: 10px;">
										 	<a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}" class="btn btn-w-m btn-info">${e.fileName}</a>
										 	<a onclick="removeFiles('${e.id}',this)" title="删除"><i class="fa fa-close text-danger"></i></a>
									 	</div>
									 </c:forEach>
								</td>
							</tr>
							<tr>
								<td class="active" rowspan="2" style="text-align: center;"><label>受<br>理<br>方<br>信<br>息</label></td>
								<td class="active"><label class="pull-right">立&nbsp;&nbsp;项&nbsp;&nbsp;人:</label></td>
								<td>
									<input type="hidden" id="userId" name="userId" value="${vo.userId}">
									<input type="text" id="userName" name="userName" class="form-control" placeholder="受理人" value="${vo.userName}" readonly="readonly">
								</td>
								<td class="active"><label class="pull-right ">受理日期:</label></td>
								<td>
									<div class="input-group date">
		                              	<input id="sdate" name="sdate" class="form-control required dateISO" validate="required" placeholder="受理日期" type="text" value="${vo.sdate}" />
		                              	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                            </div>
								</td>
							</tr>
							<tr>
								<td class="active"><label class="pull-right ">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
								<td colspan="3">
									<textarea rows="3" class="form-control" id="remark" name="remark" maxlength="128">${vo.remark}</textarea>
								</td>
							</tr>
							
						</tbody>
					</table>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-12 col-sm-offset-1">
							<a class="btn btn-w-m btn-success" href="javascript:;" onclick="formSubmitSave('updated.do');"><i class="fa fa-floppy-o" aria-hidden="true"></i> 更新</a>
							 <a class="btn btn-w-m btn-white" href="javascript:backMainPage();" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="../../../include/js.jsp"%>
<script src="${basePath}h/js/plugins/suggest/bootstrap-suggest.min.js"></script>
<script>
	 
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
			$(".i-checks").iCheck({
				checkboxClass : "icheckbox_square-green",
				radioClass : "iradio_square-green",
			});
			
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
			 $('#thisForm').ajaxSubmit(function(res) {
		    	if(res.status=='success'){
		    	    parent.toastr.success(res.message, '');
			        backMainPage();
		        }else{
		        	parent.toastr.error(res.message, '');
		        }
			});
		}
	</script>
</body>
</html>
