<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
    <%@ include file="../../include/status.jsp"%>
    
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                   <div class="ibox-title">
						<ol class="breadcrumb">
							<li><a href="gridPage.do">试剂管理</a></li>
							<li><strong>
							<c:if test="${fn:length(vo.id)>0}">编辑</c:if>
							<c:if test="${fn:length(vo.id)==0}">新增</c:if>
							</strong></li>
						</ol>
					</div>
                    <div class="ibox-content">
                        <form method="post" action="addInDepot.do" class="form-horizontal" enctype="multipart/form-data">
		                    <input name="reagentVo.id" value="${vo.reagentVo.id}" type="hidden" />
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<td class="width-15 active"><label class="pull-right">编号 :</label></td>
										<td class="width-35">${vo.reagentVo.no }</td>
										<input name="reagentVo.no" value="${vo.reagentVo.no} " type="hidden" />
										<td class="width-15 active"><label class="pull-right">名称:</label></td>
										<td class="width-35">${vo.reagentVo.name }</td>
										<input name="reagentVo.name" value="${vo.reagentVo.name} " type="hidden" />
	                                </tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">俗称 :</label></td>
										<td class="width-35">${vo.reagentVo.sname }</td>
										<input name="reagentVo.sname" value="${vo.reagentVo.sname} " type="hidden" />
										<td class="width-15 active"><label class="pull-right">英文名称:</label></td>
										<td class="width-35">${vo.reagentVo.ename }</td>
										<input name="reagentVo.ename" value="${vo.reagentVo.ename} " type="hidden" />
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">级别:</label></td>
										<td class="width-35">${vo.reagentVo.grade}</td>
										<input name="reagentVo.grade" value="${vo.reagentVo.grade} " type="hidden" />
										<td class="width-15 active"><label class="pull-right">类型:</label></td>
										<td class="width-35">${vo.reagentVo.type}</td>
										<input name="reagentVo.type" value="${vo.reagentVo.type} " type="hidden" />
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">数量:</label></td>
										<td class="width-35">${vo.reagentVo.amount }</td>
										<input name="reagentVo.amount" value="${vo.reagentVo.amount} " type="hidden" />
										<td class="width-15 active"><label class="pull-right">警戒数量:</label></td>
										<td class="width-35">${vo.reagentVo.safeAmount }</td>
										<input name="reagentVo.safeAmount" value="${vo.reagentVo.safeAmount} " type="hidden" />
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">单位:</label></td>
										<td class="width-35">${vo.reagentVo.unit }</td>
										<input name="reagentVo.unit" value="${vo.reagentVo.unit} " type="hidden" />
										<td class="width-15 active"><label class="pull-right">供应商:</label></td>
										<td class="width-35">${vo.reagentVo.supplier }</td>
										<input name="reagentVo.supplier" value="${vo.reagentVo.supplier} " type="hidden" />
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">批号:</label></td>
										<td class="width-35">${vo.reagentVo.bnum }</td>
										<input name="reagentVo.bnum" value="${vo.reagentVo.bnum} " type="hidden" />
										<td class="width-15 active"><label class="pull-right">纯度:</label></td>
										<td class="width-35">${vo.reagentVo.purity}</td>
										<input name="reagentVo.purity" value="${vo.reagentVo.purity} " type="hidden" />
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">保存条件:</label></td>
										<td class="width-35">${vo.reagentVo.saveCode }</td>
										<input name="reagentVo.saveCode" value="${vo.reagentVo.saveCode} " type="hidden" />
										<td class="width-15 active"><label class="pull-right">用途:</label></td>
										<td class="width-35">${vo.reagentVo.purpose }</td>
										<input name="reagentVo.purpose" value="${vo.reagentVo.purpose} " type="hidden" />
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">生产日期:</label></td>
										<td class="width-35">${vo.reagentVo.mfg }</td>
										<input name="reagentVo.mfg" value="${vo.reagentVo.mfg} " type="hidden" />
										<td class="width-15 active"><label class="pull-right">有效期至:</label></td>
										<td class="width-35">${vo.reagentVo.exp }</td>
										<input name="reagentVo.exp" value="${vo.reagentVo.exp} " type="hidden" />
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">保管人:</label></td>
										<td class="width-35">${vo.reagentVo.keeper }</td>
										<input name="reagentVo.keepId" value="${vo.reagentVo.keepId} " type="hidden" />
										<input name="reagentVo.keeper" value="${vo.reagentVo.keeper} " type="hidden" />
										<td class="width-15 active"><label class="pull-right">保管部门:</label></td>
										<td class="width-35">${vo.reagentVo.deptName }</td>
										<input name="reagentVo.deptName" value="${vo.reagentVo.deptName} " type="hidden" />
										<input name="reagentVo.deptId" value="${vo.reagentVo.deptId} " type="hidden" />
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">参考单价:</label></td>
										<td class="width-35">${vo.reagentVo.price }</td>
										<input name="reagentVo.price" value="${vo.reagentVo.price} " type="hidden" />
										<td class="width-15 active"><label class="pull-right">状态:</label></td>
										<td class="width-35">${vo.reagentVo.state }</td>
										<input name="reagentVo.state" value="${vo.reagentVo.state} " type="hidden" />
									</tr>
								</tbody>
							</table>
							 <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>	
										<td class="width-15 active"><label class="pull-right">操作人:</label></td>
										<td class="width-35"><input id="inPerson" placeholder="请输入操作人"  class="form-control" name="inPerson" type="text" value="${vo.inPerson }"/></td>
										<td class="width-15 active"><label class="pull-right">入库时间:</label></td>
										<td class="width-35"><input id="inDate" placeholder="请输入入库日期"  class="form-control dateISO" name="inDate" type="text" value="${vo.inDate }"/></td>
									</tr>
									<tr>	
										<td class="width-15 active"><label class="pull-right">入库数量:</label></td>
										<td class="width-35"><input id="inNum" placeholder="请输入入库数量"  class="form-control number" name="inNum" type="text" value="${vo.inNum }"/></td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-12 col-sm-offset-1">
                                    <button class="btn btn-w-m btn-primary" type="submit" ><i class="fa fa-check" aria-hidden="true"></i> 提交</button>
									<a href="gridPage.do" class="btn btn-w-m btn-white" ><i class="fa fa-undo" aria-hidden="true"></i> 返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
    <script type="text/javascript">
    var gradeflag = true;
    var purityflag = true;
    var typeflag = true;
	function gradeSelect() {
		if (!gradeflag) {
			return flase;
		}
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=res-grade',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					optionstring += "<option value=\"" + value[i] + "\" >"
							+ value[i] + "</option>";
				}
				$("#grade").html(optionstring);
				gradeflag = false;
			}
		});
	}
	function typeSelect() {
		if (!typeflag) {
			return flase;
		}
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=res-type',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					optionstring += "<option value=\"" + value[i] + "\" >"
							+ value[i] + "</option>";
				}
				$("#type").html(optionstring);
				typeflag = false;
			}
		});
	}
	function puritySelect() {
		if (!purityflag) {
			return flase;
		}
		$.ajax({
			url : '${basePath}sys/code/ajaxCodeContent.do?code=res-purity',
			datatype : "json",
			success : function(data) {
				var value = data.split(",");
				var optionstring = "";
				for (var i = 0; i < value.length; i++) {
					optionstring += "<option value=\"" + value[i] + "\" >"
							+ value[i] + "</option>";
				}
				$("#purity").html(optionstring);
				purityflag = false;
			}
		});
	}
    
    </script>
</body>
</html>
