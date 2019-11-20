<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="favicon.ico">
	<%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
               		 <div class="ibox-title">
						<ol class="breadcrumb">
							<li><a href="gridPage.do">文件</a></li>
							<li><strong>查询</strong></li>
						</ol>
					  </div>
                    <div class="ibox-content">
                        <div class="search-form">
                            <form action="gridPage.do" method="post" id="listForm">
                                <div class="input-group">
                                    <input type="text" placeholder="请输入您要搜索的文件信息" name="selectName" class="form-control input-lg" value="${vo.selectName}">
                                    <input type="hidden" name="ids" id="ids">
                                    <div class="input-group-btn">
                                        <button class="btn btn-lg btn-primary" type="submit">搜索</button>
                                    </div>
                                </div>
                            </form>
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
                               	<tbody>
                                	<c:set var="tempValue"><span id="key" style="color: red;">${vo.selectName}</span></c:set>
                                	<c:forEach items="${fileList}" var="e" varStatus="s">
                                		<c:if test="${e.isPer == 'Y'}">
 											<div class="hr-line-dashed"></div>
                                                 <div class="search-result">
                                                  <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
													<tbody>
														<tr>
															<td width=150 class="active"><label class="pull-right">是否授权:</label></td>
															<td width=350 class=""><a style="color: green;">是</a></td>
															<td width=150 class="active"><label class="pull-right">文件名:</label></td>
															<td width=350 class=""><h3><a href="edit.do?id=${e.id}&selectName=${vo.selectName}"}>${fn:replace(e.name,vo.selectName,tempValue)}</a></h3></td>
														</tr>
														<tr>
															<td width=150 class="active"><label class="pull-right">所属文件夹:</label></td>
															<td width=350 class="">${fn:replace(e.categoryVo.name,vo.selectName,tempValue)}(${fn:replace(e.categoryVo.code,vo.selectName,tempValue)})</td>
															<td width=150 class="active"><label class="pull-right">标题:</label></td>
															<td class="">${fn:replace(e.title,vo.selectName,tempValue)}</td>
														</tr>
														<tr>
															<td width=150 class="active"><label class="pull-right">大小:</label></td>
															<td width=350 class="">${fn:replace(e.size,vo.selectName,tempValue)}</td>
															<td width=150 class="active"><label class="pull-right">标记:</label></td>
															<td class="">${fn:replace(e.sign,vo.selectName,tempValue)}</td>
														</tr>
														<tr><td colspan="4">说明：${fn:replace(e.describtion,vo.selectName,tempValue)}</td></tr>
													</tbody>
												</table>
                                              </div>
                                             </c:if>
                                            <c:if test="${e.isPer == 'N'}">
                                            	<div class="hr-line-dashed"></div>
                                                 <div class="search-result">
                                                  <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
													<tbody>
														<tr>
															<td width=150 class="active"><label class="pull-right">是否授权:</label></td>
															<td width=350 class=""><a style="color: red;">否</a></td>
															<td width=150 class="active"><label class="pull-right">文件名:</label></td>
														    <td width=350 class=""><h3><a>${fn:replace(e.name,vo.selectName,tempValue)}</a></h3></td>
													    </tr>
														<tr>
															<td width=150 class="active"><label class="pull-right">所属文件夹:</label></td>
															<td width=350 class="">${fn:replace(e.categoryVo.name,vo.selectName,tempValue)}(${fn:replace(e.categoryVo.code,vo.selectName,tempValue)})</td>
															<td width=150 class="active"><label class="pull-right">标题:</label></td>
															<td class="">${fn:replace(e.title,vo.selectName,tempValue)}</td>
														</tr>
														<tr>
															<td width=150 class="active"><label class="pull-right">大小:</label></td>
															<td width=350 class="">${fn:replace(e.size,vo.selectName,tempValue)}</td>
															<td width=150 class="active"><label class="pull-right">标记:</label></td>
															<td class="">${fn:replace(e.sign,vo.selectName,tempValue)}</td>
														</tr>
														<tr><td colspan="4">说明：${fn:replace(e.describtion,vo.selectName,tempValue)}</td></tr>
													</tbody>
												</table>
                                              </div>
                                            </c:if>
                                    	</c:forEach>
                               	</tbody>
                           </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="../../include/js.jsp"%>
	<%@ include file="../../include/grid_page.jsp"%>
</body>
</html>
