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
							<li><a>标准品管理</a></li>
							<li><strong>
							入库
							</strong></li>
						</ol>
					</div>
                    <div class="ibox-content">
                        <form method="post" action="/res/stanoutin/addInDepot.do" class="form-horizontal" enctype="multipart/form-data">
							<c:if test="${fn:length(vo.id)>0}">
								<input name="id" value="${vo.id}" type="hidden" />
							</c:if>
							 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<thead>
									<tr>	
										<td class="active" colspan="4"><label class="pull-left">入库信息</label></td>
									</tr>
								</thead>
								<tbody>
									<tr>	
										<td class="width-15 active"><label class="pull-right">标准品名称:</label></td>
										<td class="width-35">
											<div class="input-group" style="width: 100%">
												<input id="standardName" class="form-control required" validate="required" name="name" type="text" value="${vo.name }" />
												 <input id="standardId" name="standardVo.id" value="${vo.id}" type="hidden" />
												<div class="input-group-btn">
													<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect()">选择</button>
												</div>
											</div>
										</td>
										<td class="width-15 active"><label class="pull-right">标准品编号:</label></td>
										<td class="width-35"><input id="standardNo" class="form-control required" validate="required"  name="no" type="text" value="${vo.no}"  placeholder="根据编号新增标准品或修改已有的数量"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">规格型号:</label></td>
										<td class="width-35"><input id="rule"  class="form-control"  type="text" name="rule" value="${vo.rule} " /></td>
										<td class="width-15 active"><label class="pull-right">供应商:</label></td>
										<td class="width-35">
											<div class="input-group">
												<input name="supplier" id="supplier" value="${vo.supplier}" type="text" class="form-control" /> 
												<div class="input-group-btn">
													<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectSupplier()">选择</button>
												</div>
											</div>
										</td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">当前库存:</label></td>
										<td class="width-35"><input id="amount"  class="form-control required number" validate="required"  type="text" name="amount" value="${vo.amount} " /></td>
										<td class="width-15 active"><label class="pull-right">警戒数量:</label></td>
										<td class="width-35"><input id="safeAmount" placeholder="请输入警戒数量"  class="form-control required number" validate="required" name="safeAmount" type="text" value="${vo.safeAmount }"/></td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">批号:</label></td>
										<td class="width-35"><input id="cerNo" placeholder="请输入批号"  class="form-control" name="cerNo" type="text" value="${vo.cerNo }"/></td>
										<td class="width-15 active"><label class="pull-right">含量/浓度:</label></td>
										<td class="width-35"><input id="content" placeholder="请输入含量/浓度"  class="form-control" name="content" type="text" value="${vo.content }"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">生产厂家:</label></td>
										<td class="width-35"><input id="producer" placeholder="请输入生产厂家"  class="form-control" name="producer" type="text" value="${vo.producer }"/></td>
										<td class="width-15 active"><label class="pull-right">保存条件:</label></td>
										<td class="width-35"><input id="saveCondition" placeholder="请输入保存条件"  class="form-control" name="saveCondition" type="text" value="${vo.saveCondition }"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">生产日期:</label></td>
										<td class="width-35">
											<div class="input-group date">
												<input id="mfg" placeholder="请输入生产日期"  class="form-control required" validate="required" name="mfg" type="text" value="${vo.mfg }"/>
												<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										</td>
										<td class="width-15 active"><label class="pull-right">有效期至:</label></td>
										<td class="width-35">
											<div class="input-group date">
												<input id="exp" placeholder="请输入有效期"  class="form-control required" validate="required" name="exp" type="text" value="${vo.exp }"/>
												<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
											</div>
										</td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">入&nbsp;库&nbsp;&nbsp;人:</label></td>
										<td class="width-35">
											<div class="input-group" style="width: 100%">
												<input id="inPerson" placeholder="请输入操作人" class="form-control required" validate="required" name="inPerson" type="text" value="${vo.inPerson }"/>
												<div class="input-group-btn">
													<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectUser()">选择</button>
												</div>
											</div>
										</td>
										<td class="width-15 active"><label class="pull-right">入库时间:</label></td>
										<td class="width-35"><input id="inDate" placeholder="请输入入库日期" class="form-control datetimeISO required" validate="required" name="inDate" type="text" value="${vo.inDate }"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">入库数量:</label></td>
										<td class="width-35"><input id="inNum" placeholder="请输入入库数量"  class="form-control number required" validate="required" name="inNum" type="text" value="${vo.inNum }"/></td>
										<td class="width-15 active"><label class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
										<td class="width-35"><input maxlength="128" id="remark"  class="form-control" name="remark" type="text" value="${vo.remark }"/></td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-12 col-sm-offset-1">
                                    <button class="btn btn-w-m btn-primary"  type="button"  onclick="formSubmitAndBack();"><i class="fa fa-check" aria-hidden="true"></i> 提交</button>
									<a href="javascript:backMainPage();" class="btn btn-w-m btn-white" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../../include/js.jsp"%>
    <script type="text/javascript">
    laydate.render({ 
    	  elem: '#mfg',
    	  type: 'month',
    	  format: 'yyyy-MM' //可任意组合
    	});
    	laydate.render({ 
    		  elem: '#exp',
    		  type: 'month',
    		  format: 'yyyy-MM' //可任意组合
    		});
    function fnSelect(){
    	var standardId=$('#standardId').val();
    	parent.layer.open({
			title:'标准品选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}res/standard/select.do?id='+standardId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin=layero.find('iframe')[0];
			  var data=iframeWin.contentWindow.fnSelect();
			   $('#standardName').val(data.name);
			   $('#standardNo').val(data.no);
			   $('#rule').val(data.rule);
			   $('#supplier').val(data.supplier);
			   $('#amount').val(data.amount);
			   $('#safeAmount').val(data.safeAmount);
			   $('#cerNo').val(data.cerNo);
			   $('#content').val(data.content);
			   $('#producer').val(data.producer);
			   $('#saveCondition').val(data.saveCondition);
			   $('#mfg').val(data.mfg);
			   $('#exp').val(data.exp);
			   parent.layer.close(index);
			}
		});
    }
    function fnSelectSupplier(){
		var supplierId=$('#supplierId').val();
		layer.open({
			title:'供应商选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}res/supplier/select.do?id='+supplierId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var data=iframeWin.fnSelect();
				$('#supplier').val(data.name);
			}
		});
	}
	function fnSelectUser(){
		parent.layer.open({
			title:'人员选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}sys/account/select.do',
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin=layero.find('iframe')[0];
			  var data=iframeWin.contentWindow.fnSelect();
			   $('#inPerson').val(data.name);
			   parent.layer.close(index);
			}
		});
	}
    </script>
</body>
</html>
