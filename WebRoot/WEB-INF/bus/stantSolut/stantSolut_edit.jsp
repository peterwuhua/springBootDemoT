<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../../include/tld.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
    <%@ include file="../../include/css.jsp" %>
    <%@ include file="../../include/status.jsp" %>
    <link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
    <style type="text/css">
        .form-control {
            padding: 2px;
        }

        .table > tbody > tr > td {
            padding: 2px;
        }

        .table > tbody > tr > td > input, .table > tbody > tr > td > select {
            height: 100%;
            padding: 2px;
        }
    </style>
</head>
<body>
<div class="col-sm-12">
    <div class="ibox float-e-margins">
        <div class="ibox-content">
            <form method="post" action="" class="form-horizontal" id="thisForm">
                <input name="id" value="${vo.id}" type="hidden"/>
                <input name="itemId" id="itemId" value="${vo.itemId}" type="hidden" />
                <input name="itemName" id="itemName" value="${vo.itemName}" type="hidden" />
                <input name="taskItemId" id="taskItemId" value="${vo.taskItemId}" type="hidden" />
                <table id="samp_tb" style="margin-bottom: 5px; width: 100%">
                    <tr>
                   	 <td class="active" rowspan="2" style="text-align: center; width: 50px;"></td>
                        <td style="width: 9%; text-align: right;">试剂名称：</td>
                        <td style="width: 16%; padding: 2px;">
                        	<div class="input-group">
								<input name="reagentName" id="reagentName" value="${vo.reagentName}" placeholder="请选择试剂" type="text" class="form-control required" validate="required" onclick="fnSelectReagent()" /> 
								<input name="reagentId" id="reagentId" value="${vo.reagentId}" type="hidden" />
								<div class="input-group-btn">
									<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectReagent()">选择</button>
								</div>
							</div>
                        </td>
                        <td style="width: 9%; text-align: right;">级别：</td>
                        <td style="width: 16%; padding: 2px;">
                            <input id="plevel" name="plevel" value="${vo.plevel}" type="text" class="form-control " placeholder="级别"/>
                        </td>
                        <td style="width: 9%; text-align: right;">批号：</td>
                        <td style="width: 16%; padding: 2px;">
                            <input id="piH" name="piH" value="${vo.piH}" type="text" class="form-control" placeholder="批号"/>
                        </td>
                        <td style="width: 7%; text-align: right;">配置依据：</td>
                        <td style="width: 18%; padding: 2px;">
                        <input id="pzYJ" name="pzYJ" value="${vo.pzYJ}"
                                                                     type="text" class="form-control"
                                                                     placeholder="配置依据"/></td>
                    </tr>
                    <tr>
                        <td style="text-align: right;">天平型号：</td>
                        <td style="padding: 2px;"><input id="tpXh" name="tpXh" value="${vo.tpXh}" type="text"
                                                         class="form-control" placeholder="天平型号"/></td>
                        <td style="text-align: right;">编号：</td>
                        <td style="padding: 2px;">
                            <input id="code" name="code" value="${vo.code}" type="text" class="form-control" placeholder="编号"/>
                        </td>
                        <td style="text-align: right;">精度：</td>
                        <td style="padding: 2px;">
                        	<input id="jd" name="jd" value="${vo.jd}" type="text" class="form-control" placeholder="精度"/>
                        </td>
                        <td style="text-align: right;"></td>
                        <td style="padding: 2px;">
                        </td>
                    </tr>
                    <tr>
                    	<td class="active" rowspan="1" style="text-align: center; width: 50px;"></td>
                        <td style="text-align: right;">温度：</td>
                        <td style="padding: 2px;">
                            <input id="wd" name="wd" value="${vo.wd}" type="text" class="form-control" placeholder="温度℃"/>
                        </td>
                        <td style="text-align: right;">湿度：</td>
                        <td style="padding: 2px;">
                            <input id="sd" name="sd" value="${vo.sd}" type="text" class="form-control" placeholder="湿度%"/>
                        </td>
                        <td style="text-align: right;">保存条件：</td>
                        <td style="padding: 2px;">
                            <input id="bctj" name="bctj" value="${vo.bctj}" type="text" class="form-control" placeholder="保存条件"/>
                        </td>
                        <td style="text-align: right;">有效日期：</td>
                        <td style="padding: 2px;">
                            <input id="date" name="date" value="${vo.date}" type="text" class="form-control" placeholder="有效日期"/>
                        </td>
                    </tr>
               	 	<tr>
                       <td class="active" rowspan="2" style="text-align: center; width: 50px;"></td> 
                       <td style="text-align: right;">配置记录：</td>
                        <td colspan="7" style="padding: 2px;">
                           <textarea rows="3" cols="7" class="form-control" id="pzJl" name="pzJl" maxlength="512">${vo.pzJl}</textarea>
                        </td>
                    </tr>
                   <tr>   
                    	<td style="text-align: right;">配置人员：</td>
                        <td style="padding: 2px;">
                            <input id="pzr" name="pzr" value="${vo.pzr}" type="text" class="form-control" />
                            <input name="pzrId" id="pzrId" value="${vo.pzrId}" type="hidden" />
                        </td>
                        <td style="text-align: right;">配置日期：</td>
                        <td style="padding: 2px;">
                            <input id="pzDate" name="pzDate" value="${vo.pzDate}" type="text" class="form-control " />
                        </td>
                        <td style="text-align: right;"></td>
                        <td style="padding: 2px;">
                        </td>
                        <td style="text-align: right;"></td>
                        <td style="padding: 2px;">
                        </td>
                    </tr>
               	 </table>
                 <div style="overflow-x: auto;">
                    <table  class="table table-bordered" style="margin-bottom: 0px; min-width: 1400px">
                         <tr>   
                       <td class="active" rowspan="15" style="text-align: center; width: 50px;"><label>标<br>定<br>记<br>录</label></td> 
                    	<td colspan="2" style="text-align: right;">基准溶液名称：</td>
                        <td style="padding: 2px;" colspan="2">
                        	<div class="input-group">
								<input name="jzName" id="jzName" value="${vo.jzName}" placeholder="基准溶液名称" type="text" class="form-control required" validate="required" onclick="fnSelectStanard()" /> 
								<input name="jzNameId" id="jzNameId" value="${vo.jzNameId}" type="hidden" />
								<div class="input-group-btn">
									<button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelectStanard()">选择</button>
								</div>
							</div>
                        </td>
                        <td colspan="2" style="text-align: right;">基准溶液浓度：</td>
                        <td style="padding: 2px;" colspan="2">
                            <input id="jzNd" name="jzNd" value="${vo.jzNd}" type="text" class="form-control" placeholder="基准溶液（物质）浓度"/>
                        </td>
                        <td style="text-align: right;">基准溶液配制日期：</td>
                        <td style="padding: 2px;" colspan="2">
                            <input id="jzDate" name="jzDate" value="${vo.jzDate}" type="text" class="form-control" placeholder="基准溶液配制日期"/>
                        </td>
                    </tr>
                        <tr>
                            <th width="60" rowspan="2" style="text-align: center;">名称</th>
                            <th width="60" rowspan="2" style="text-align: center;">序号</th>
                           	<!-- <th width="160" rowspan="2">□基准物质m (g)<br>□溶液取样量V (mL)</th> -->
                           	<th width="160" rowspan="2" style="text-align: center;">
                        		<div class="radio i-checks">
									<label>
										<div class="iradio_square-green">
											<input type="radio" value="是" name="jzWz" <c:if test="${vo.jzWz=='是'}">checked</c:if>>
										</div>基准物质m (g)
									</label> 
									<label>
										<div class="iradio_square-green">
											<input type="radio" value="否" name="jzWz" <c:if test="${vo.jzWz!='是'}">checked</c:if>>
										</div>溶液取样量V (mL)
									</label>
								</div>
							</th>
                            <th colspan="4" style="text-align: center;">标准滴定溶液消耗量V1（mL）</th>
                            <!-- <th width="110"  rowspan="2" style="text-align: center;">标准滴定溶液浓度<br>□C □Ts/x  □ρ（        ）</th> -->
                            <th width="120"  rowspan="2" style="text-align: center;">
                            	标准滴定溶液浓度
                            	<div class="radio i-checks">
									<label>
										<div class="iradio_square-green">
											<input type="radio" value="1" name="bzNd" <c:if test="${vo.bzNd=='1'}">checked</c:if>>
										</div>C
										<div class="iradio_square-green">
											<input type="radio" value="2" name="bzNd" <c:if test="${vo.bzNd=='2'}">checked</c:if>>
										</div>Ts/x
										<div class="iradio_square-green">
											<input type="radio" value="3" name="bzNd" <c:if test="${vo.bzNd=='3'}">checked</c:if>>
										</div>ρ
									</label>
								</div>
                            </th> 
                            <th width="110"  rowspan="2" style="text-align: center;">均值</th>
                            <th width="110" rowspan="2" style="text-align: center;">极差相对值1<br>（％）</th>
                            <th width="110" rowspan="2" style="text-align: center;">极差相对值2<br>（％）</th>
                        </tr>
                        <tr>
                        	<th width="90" style="text-align: center;">终读</th>
                        	<th width="90" style="text-align: center;">始读</th>
                        	<th width="90" style="text-align: center;">消耗量</th>
                        	<th width="90" style="text-align: center;">减空白</th>
                        </tr>
                        
                        <c:forEach items="${vo.stantMxList}" var="e" varStatus="v">
                          <c:if test="${v.index<5}" > 
	                            <tr>
		                             <c:if test="${v.index==0}">
		                            	<td style="text-align: center;" rowspan="5">
		                            		初标
		                                </td>
		                             </c:if> 
	                             	<td style="text-align: center;">
	                             		${e.deme1} 
	                             	</td>
	                                <td >
	                                	<c:choose>
			                             	<c:when test="${v.index==0}">
			                             		/
			                             	</c:when>
			                             	<c:otherwise>
			                            		<input type="text" class="form-control" name="stantMxList[${v.index}].deme2" value="${e.deme2}"> 	
			                             	</c:otherwise>
		                            	 </c:choose>
	                                </td>
	                                <td>
	                                   <input type="hidden" name="stantMxList[${v.index}].id" value="${e.id}"> 
	                               		<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" id="v_1_${v.index}" name="stantMxList[${v.index}].v1" value="${e.v1}" onchange="countXHL(${v.index})">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
	                                </td>
	                                <td>
	                               		<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" id="v_2_${v.index}" name="stantMxList[${v.index}].v2" value="${e.v2}" onchange="countXHL(${v.index})">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
	                                </td>
	                                <td>
	                               		<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" id="v_3_${v.index}" name="stantMxList[${v.index}].v3" value="${e.v3}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
	                                </td>
	                                <td>
	                                	<c:choose>
			                             	<c:when test="${v.index==0}">
			                             		/
			                             	</c:when>
			                             	<c:otherwise>
				                             	<div class="input-group" style="width: 100%">
										 			<input type="text" class="form-control" id="v_4_${v.index}" name="stantMxList[${v.index}].v4" value="${e.v4}">
										 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 				</div>
			                             	</c:otherwise>
		                            	 </c:choose>
	                                </td>
	                                <td>
	                               		<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" id="v_5_${v.index}" name="stantMxList[${v.index}].v5" value="${e.v5}" onchange="countAvg(${v.index})">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
	                                </td>
	                                <c:choose>
	                                	<c:when test="${v.index==0}">
		                                	 <td rowspan="5">
			                               		<div class="input-group" style="width: 100%">
										 			<input type="text" class="form-control" id="avg_1" name="stantMxList[${v.index}].avg1" value="${e.avg1}">
									 			</div>
			                                </td>
			                                <td rowspan="5">
			                               		<div class="input-group" style="width: 100%">
										 			<input type="text" class="form-control" name="stantMxList[${v.index}].jc1" value="${e.jc1}">
									 			</div>
			                                </td>
	                                	</c:when>
	                                </c:choose>
	                                <c:choose>
	                                	<c:when test="${v.index==0}">
	                                		<td rowspan="10">
			                               		<div class="input-group" style="width: 100%">
										 			<input type="text" class="form-control" name="stantMxList[${v.index}].jcall" value="${e.jcall}">
									 			</div>
			                                </td>
	                                	</c:when>
	                                </c:choose>
	                              </tr>
                            </c:if>
                            <c:if test="${v.index>=5}">
	                            <tr>
		                             <c:if test="${v.index==5}">
		                            	<td style="text-align: center;" rowspan="5">
		                            		复标
		                                </td>
		                             </c:if>
                             		<td style="text-align: center;">
	                                	${e.deme1}
	                                </td>
	                                <td>
	                                	 <c:choose>
			                             	<c:when test="${v.index==5}">
			                             		/
			                             	</c:when>
			                             	<c:otherwise>
			                            		<input type="text" class="form-control" name="stantMxList[${v.index}].deme2" value="${e.deme2}"> 	
			                             	</c:otherwise>
		                            	 </c:choose>
	                                </td>
	                                <td>
	                                <input type="hidden" name="stantMxList[${v.index}].id" value="${e.id}"> 
	                               		<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" id="v_1_${v.index}" name="stantMxList[${v.index}].v1" value="${e.v1}" onchange="countXHL(${v.index})">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
	                                </td>
	                                <td>
	                               		<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" id="v_2_${v.index}" name="stantMxList[${v.index}].v2" value="${e.v2}" onchange="countXHL(${v.index})">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
	                                </td>
	                                <td>
	                               		<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" id="v_3_${v.index}" name="stantMxList[${v.index}].v3" value="${e.v3}">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
	                                </td>
	                                <td>
	                                	<c:choose>
			                             	<c:when test="${v.index==5}">
			                             		/
			                             	</c:when>
			                             	<c:otherwise>
				                             	<div class="input-group" style="width: 100%">
										 			<input type="text" class="form-control" id="v_4_${v.index}" name="stantMxList[${v.index}].v4" value="${e.v4}">
										 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
								 				</div>
			                             	</c:otherwise>
		                            	 </c:choose>
	                                </td>
	                                <td>
	                               		<div class="input-group" style="width: 100%">
								 			<input type="text" class="form-control" id="v_5_${v.index}" name="stantMxList[${v.index}].v5" value="${e.v5}" onchange="countAvg(${v.index})">
								 			<span style="display: table-cell;" class=" btn btn-xs glyphicon glyphicon-arrow-down" aria-hidden="true" onclick="copyVals(this,${v.index});return false;"></span>
							 			</div>
	                                </td>
	                                 <c:choose>
	                                	<c:when test="${v.index==5}">
		                                	<td rowspan="5">
			                               		<div class="input-group" style="width: 100%">
										 			<input type="text" class="form-control" id="avg_2" name="stantMxList[${v.index}].avg2" value="${e.avg2}" >
									 			</div>
			                                </td>
			                                <td rowspan="5">
			                               		<div class="input-group" style="width: 100%">
										 			<input type="text" class="form-control" name="stantMxList[${v.index}].jc2" value="${e.jc2}">
									 			</div>
			                                </td>
	                                	</c:when>
	                                </c:choose>	
	                            </tr>
                            </c:if>
                        </c:forEach>
                        <tr>
                    		<td style="text-align: right;">结论：</td>
	                        <td colspan="5" style="padding: 2px;">
	                            <textarea rows="2" cols="3" class="form-control" id="bz" name="bz" maxlength="512">${vo.bz}</textarea>
	                        </td>
	                    	<td style="text-align: right;">计算公式：</td>
	                        <td colspan="2" style="padding: 2px;">
	                            <input id="jsgs" name="jsgs" value="${vo.jsgs}" type="text" class="form-control" />
	                        </td>
	                        <td style="text-align: right;">标准滴定溶液<br>平均浓度：</td>
	                        <td colspan="2" style="padding: 2px;">
	                            <input id="bzAvg" name="bzAvg" value="${vo.bzAvg}" type="text" class="form-control" />
	                        </td>
	                    </tr>
                    </table>
                    <table style="margin-bottom: 5px; width: 100%">
                    	
	                    <tr>
	                   	 <td style="text-align: right;">初标人员：</td>
	                        <td style="padding: 2px;">
	                            <input id="cbr" name="cbr" value="${vo.cbr}" type="text" class="form-control" />
	                            <input id="cbrId" name="cbrId" value="${vo.cbrId}" type="hidden"  />
	                        </td>
	                        <td style="text-align: right;">复标人员：</td>
	                        <td style="padding: 2px;">
	                            <input id="fbr" name="fbr" value="${vo.fbr}" type="text" class="form-control" />
	                            <input id="fbrId" name="fbrId" value="${vo.fbrId}" type="hidden"  />
	                        </td>
	                    	<td style="text-align: right;">标定日期：</td>
	                        <td style="padding: 2px;">
	                            <input id="bdDate" name="bdDate" value="${vo.bdDate}" type="text" class="form-control" />
	                        </td>
	                        <td style="text-align: right;">实施日期：</td>
	                        <td style="padding: 2px;">
	                            <input id="ssDate" name="ssDate" value="${vo.ssDate}" type="text" class="form-control" />
	                        </td>
	                    </tr>       
                    </table>
                </div> 
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <div class="col-sm-12" style="text-align: right;">
                        <!-- <a class="btn btn-w-m btn-success" type="button"
                           onclick="fnSubmit4Save('updateCyd.do?isCommit=0')"><i class="fa fa-floppy-o"
                                                                                 aria-hidden="true"></i> 保存</a> -->
                        <a class="btn btn-w-m btn-primary" type="button"
                           onclick="fnSubmit('updateData.do')"><i class="fa fa-check" aria-hidden="true"></i>完成</a>
                        <a class="btn btn-w-m btn-white" href="javascript:close();"><i class="fa fa-times"
                                                                                       aria-hidden="true"></i> 关闭</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="../../include/js.jsp" %>
<!-- Chosen -->
<script src="${basePath}h/js/plugins/chosen/chosen.jquery.js"></script>
<!-- Input Mask-->
<script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
<script type="text/javascript">


    var index = parent.layer.getFrameIndex(window.name);

    function fnSubmit(url) {
        var t = $("#thisForm").FormValidate();
        if (t) {
            $.ajax({
                url: url,
                dataType: "json",
                data: $('#thisForm').serialize(),
                type: "post",
                success: function (data) {
                    parent.layer.msg(data.message, {icon: 0, time: 3000})
                    if ("success" == data.status) {
                        parent.layer.close(index);
                    }
                },
                error: function (ajaxobj) {
                    layer.msg(ajaxobj, {icon: 0, time: 3000});
                }
            });
        }
    }

    function close() {
        parent.layer.close(index);
    }

    function copyVals(obj) {
    	obj=$(obj);console.log(obj);
    	var value0=obj.closest('td').find('input').eq(0).val();
    	var indexTr=obj.closest('td').closest('tr').index();
    	var indexTd=obj.closest('td').index();
    	$('# tr:gt('+indexTr+')').each(function(){
    		$(this).find('td').eq(indexTd).find('input').eq(0).val(value0);
    	});
    }

    function checkThis(obj) {
        var t = $(obj).find('option:selected').text();
        $(obj).closest('td').find('input').val(t);
    }

    $(document).ready(function () {
    	$(".i-checks").iCheck({
			checkboxClass : "icheckbox_square-green",
			radioClass : "iradio_square-green",
		});
      /**  var config = {
            '.chosen-select': {},
            '.chosen-select-deselect': {
                allow_single_deselect: true
            },
            '.chosen-select-no-single': {
                disable_search_threshold: 10
            },
            '.chosen-select-no-results': {
                no_results_text: 'Oops, nothing found!'
            },
            '.chosen-select-width': {
                width: "95%"
            }
        }
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }**/
    })
function fnSelectReagent(){
    	var id = '${vo.reagentId}';
    	parent.layer.open({
    		title:'试剂清单',	
    		type: 2,
    		area: ['1000px','85%'],
    		fix: false, //不固定
    		maxmin: true,
    		content: '/bus/stantSolut/select1.do?id='+id,
    		btn: ['确定','取消'], //按钮
    		btn1: function(index, layero) {
    			var iframeWin=layero.find('iframe')[0];
				var data=iframeWin.contentWindow.fnSelect();
    			$("#reagentId").val(data.id);
    			$("#reagentName").val(data.name);
    			$("#level").val(data.grade);
    			$("#piH").val(data.bnum);
    			$("#code").val(data.no);
    			$("#date").val(data.exp);
    			$("#bctj").val(data.saveCode);
    			parent.layer.close(index);
    		}	
    	});
}
    
function fnSelectStanard(){
    	var id = '${vo.jzNameId}';
    	parent.layer.open({
    		title:'标准品清单',	
    		type: 2,
    		area: ['1000px','85%'],
    		fix: false, //不固定
    		maxmin: true,
    		content: '/bus/stantSolut/select.do?id='+id,
    		btn: ['确定','取消'], //按钮
    		btn1: function(index, layero) {
    			var iframeWin=layero.find('iframe')[0];
				var data=iframeWin.contentWindow.fnSelect();
    			$("#jzNameId").val(data.id);
    			$("#jzName").val(data.name);
    			$("#jzNd").val(data.content);
    			parent.layer.close(index);
    		}	
    	});
}

function countXHL(n){
	var v1 = 0.0;
	var v2 = 0.0;
	var v4 = 0.0;
	if($('#v_1_'+n).val()!=null && $('#v_1_'+n).val()!=''){
		v1=parseFloat($('#v_1_'+n).val());
	}
	if($('#v_2_'+n).val()!=null && $('#v_2_'+n).val()!=''){
		v2=parseFloat($('#v_2_'+n).val());
	}
	if(v1!=0.0 && v2!=0.0){
		var v3 = (v2-v1).toFixed(4);
		$('#v_3_'+n).val(''+v3);
	}
	//计算减空白
	if (n>0 && n<5){
		if(v1!=0.0 && v2!=0.0){
			if($('#v_3_0').val()!=null && $('#v_3_0').val()!=''){
				var v3_=parseFloat($('#v_3_0').val());
				var v3 = (v2-v1).toFixed(4);
				v4 = (v3-v3_).toFixed(4); 
				$('#v_4_'+n).val(''+v4);
			}
		}
	}
	if (n>5 && n<10){
		if(v1!=0.0 && v2!=0.0){
			if($('#v_3_5').val()!=null && $('#v_3_5').val()!=''){
				var v3_=parseFloat($('#v_3_5').val());
				var v3 = (v2-v1).toFixed(4);
				v4 = (v3-v3_).toFixed(4); 
				$('#v_4_'+n).val(''+v4);
			}
		}
	}
}
function countAvg(n){
	if(n>=0 && n<5){
		var sl = 0;
		var rs = 0.0;
		for(var i = 0;i<5;i++){
			var s = $('#v_5_'+i).val();
			if(s==null || s==''){
				continue;
			}
			sl++;
			var ss = parseFloat($('#v_5_'+i).val());
			rs+=ss;
		}
		if(sl>0){
			var avg = (rs/sl).toFixed(4);
			$('#avg_1').val(''+avg);
		}
	}
	if(n>=5 && n<10){
		var sl = 0;
		var rs = 0.0;
		for(var i = 5;i<10;i++){
			var s = $('#v_5_'+i).val();
			if(s==null || s==''){
				continue;
			}
			sl++;
			var ss = parseFloat($('#v_5_'+i).val());
			rs+=ss;
		}
		if(sl>0){
			var avg = (rs/sl).toFixed(4);
			$('#avg_2').val(''+avg);
		}
	}
}

    
</script>
</body>
</html>
