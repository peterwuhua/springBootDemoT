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
							<li><a>耗材管理</a></li>
							<li><strong>
							入库
							</strong></li>
						</ol>
					</div>
                    <div class="ibox-content">
                        <form method="post" action="/res/consoutin/addInDepot.do" class="form-horizontal" enctype="multipart/form-data">
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
										<td class="width-15 active"><label class="pull-right">耗材名称:</label></td>
										<td class="width-35">
											<div class="input-group" style="width: 100%">
												<input id="consumableName" class="form-control required" validate="required" name="consumableVo.name" type="text" value="${vo.consumableVo.name }"  onclick="fnSelect()"/>
												 <input id="consumableId" name="consumableVo.id" value="${vo.consumableVo.id}" type="hidden" />
												<div class="input-group-btn">
													<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect()">选择</button>
												</div>
											</div>
										</td>
										<td class="width-15 active"><label class="pull-right">耗材编号:</label></td>
										<td class="width-35"><input id="consumableNo" class="form-control" name="consumableVo.no" type="text" value="${vo.consumableVo.no}" readonly="readonly"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">规格型号:</label></td>
										<td class="width-35"><input id="model"  class="form-control"  type="text" name="consumableVo.model" value="${vo.consumableVo.model} " readonly="readonly"/></td>
										<td class="width-15 active"><label class="pull-right">当前库存:</label></td>
										<td class="width-35"><input id="amount"  class="form-control"  type="text" name="consumableVo.amount" value="${vo.consumableVo.amount} " readonly="readonly"/></td>
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
										<td class="width-35"><input id="inDate" placeholder="请输入入库日期"  class="form-control datetimeISO required" validate="required" name="inDate" type="text" value="${vo.inDate }"/></td>
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
    function fnSelect(){
    	var consumableId=$('#consumableId').val();
    	parent.layer.open({
			title:'耗材选择',	
			type: 2,
			 area: ['70%', '85%'],
			fix: false, //不固定
			maxmin: true,
			content: '${basePath}res/consumable/select.do?id='+consumableId,
			btn: ['确定','取消'], //按钮
			btn1: function(index, layero) {
			  var iframeWin=layero.find('iframe')[0];
			  var data=iframeWin.contentWindow.fnSelect();
			   $('#consumableId').val(data.id);
			   $('#consumableName').val(data.name);
			   $('#consumableNo').val(data.no);
			   $('#model').val(data.model);
			   $('#amount').val(data.amount);
			   parent.layer.close(index);
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
