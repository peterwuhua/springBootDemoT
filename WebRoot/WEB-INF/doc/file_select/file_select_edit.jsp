<%@ page language="java" import="java.util.*,com.zhuozhengsoft.pageoffice.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="shortcut icon" href="favicon.ico"> 
    <%@ include file="../../include/css.jsp"%>
</head>
<body class="gray-bg">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>
							<ol class="breadcrumb">
								<li><a href="gridPage.do">文件</a></li>
								<li><strong>查看</strong></li>
							</ol>
						</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form method="post" action="" class="form-horizontal">
                            <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
								<tbody>
									<tr>
										<c:if test="${vo.state eq '现行'}">
											<td class="width-15 active"><label class="pull-right">状态:</label></td>
											<td class="width-35"><strong style="color: green;">${vo.state}</strong></td>
										</c:if>
										<c:if test="${vo.state eq '作废'}">
											<td class="width-15 active"><label class="pull-right">状态:</label></td>
											<td class="width-35"><strong style="color: red;">${vo.state}</strong></td>
										</c:if>
										<td class="width-15 active"><label class="pull-right">操作:</label></td>
										<c:if test="${fn:length(vo.relativePath)>0}">
											<td class="width-35">
												<c:if test="${fn:length(vo.id)>0}">
													<%
														cn.demi.doc.vo.DocumentVo t = (cn.demi.doc.vo.DocumentVo)request.getAttribute("vo");
													%>
													<a href="download.do?filePath=${vo.relativePath}&trueName=${vo.name}${vo.type}" class="btn btn-w-m btn-info">下载文件</a>
													<c:if test="${fn:contains(vo.type,'doc')||fn:contains(vo.type,'xls')||fn:contains(vo.type,'ppt')}">
														<a href="<%=PageOfficeLink.openWindow(request,basePath+"/doc/document/open.do?id="+t.getId(),"width=900px;height=600px;")%>" class="btn btn-w-m btn-info">打开文件</a>
													</c:if>
													<c:if test="${vo.type eq '.pdf'}">
														<a href="javascript:openFile('${basePath}sys/open/open.do','${vo.relativePath}','pdf');" class="btn btn-w-m btn-info">打开文件</a> 
													</c:if>
													<c:if test="${fn:contains(vo.type,'jpg')||fn:contains(vo.type,'png')||fn:contains(vo.type,'jpeg')||fn:contains(vo.type,'gif')}">
														<a href="javascript:openImg('${vo.relativePath}','${vo.name}');" class="btn btn-w-m btn-info">打开文件</a>
													</c:if>
												</c:if>
											</td>
										</c:if>
										<c:if test="${fn:length(vo.relativePath)==0}">
											<td class="width-35"><strong style="color: red;">未上传文件</strong></td>
										</c:if>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">所属文件夹:</label></td>
										<td class="width-35">${vo.categoryVo.name}</td>
										<td class="width-15 active"><label class="pull-right">名称:</label></td>
										<td class="width-35">${vo.name}</td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">标题:</label></td>
										<td class="width-35">${vo.title}</td>
										<td class="width-15 active"><label class="pull-right">标记:</label></td>
										<td class="width-35">${vo.sign}</td>
									</tr>
									<tr>
										<td class="width-15 active"><label class="pull-right">大小:</label></td>
										<td class="width-35">${vo.size}</td>
										<td class="width-15 active"><label class="pull-right">说明:</label></td>
										<td class="width-35" colspan="3">${vo.describtion}</td>
									</tr>
								</tbody>
							</table>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
									<a href="gridPage.do?selectName=${vo.selectName}" class="btn btn-white">返回</a>
                                </div>
                           </div>
                        </form>
                    </div>
                </div>
    </div>
     <%@ include file="../../include/js.jsp"%>
     <%@ include file="../../sys/open/open_img.jsp"%>
</body>
</html>
