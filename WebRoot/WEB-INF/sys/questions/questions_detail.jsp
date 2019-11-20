<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>帮助中心</title>
<meta name="keywords" content="">
<meta name="description" content="">
<head>
<link rel="shortcut icon" href="favicon.ico">
<%@ include file="../../include/css.jsp"%>
<link rel="stylesheet" href="${basePath}ext/layui/css/layui.css">
<style type="text/css">
.qlist>li {
	line-height: 32px;
	white-space: nowrap;
	font-size: 14px;
	list-style: disc;
	padding-left: 20px;
	font-family: 'lucida Grand', Verdana;
}

h2 {
	font-size: 30px;
}

.active {
	background: #C22D2E;
	color: #C22D2E;
	border-bottom: 0;
	margin-bottom: 1px;
}
</style>
</head>
<body class="gray-bg">
	<div class="col-sm-12">
		<div class="ibox float-e-margins">
			<div class="ibox-title" style="border-bottom: 4px solid #18a689;">
				<h5>
					<ol class="breadcrumb">
						<li><a href="questionDetail.do">帮助中心</a></li>
					</ol>
				</h5>
			</div>
			<div class="ibox-content">
				<div class="row m-b-sm m-t-sm">
					<form action="questionDetail.do" method="post" id="listForm">
						<div class="col-lg-10 col-lg-offset-1">
							<div class="row m-b-sm m-t-sm">
								<div class="col-md-1">
									<button type="button" id="loading-example-btn" class="btn btn-white btn-sm">
										<i class="fa fa-refresh"></i> 刷新
									</button>
								</div>
								<div class="col-md-11">
									<div class="input-group">
										<input type="text" placeholder="请输入问题" class="input-sm form-control" name="selectName" value="${vo.selectName }"> <span class="input-group-btn">
											<button type="submit" class="btn btn-sm btn-primary">搜索</button>
										</span>
									</div>
								</div>
							</div>
						</div>
						<input type="hidden" name="pageBean.currentPage" id="pageNumber" value="${pageResult.pageBean.currentPage}" /> 
						<input type="hidden" name="pageBean.pageSize" id="pageSize" value="${pageResult.pageBean.pageSize}" /> 
						<input type="hidden" name="order" id="order" value="${pageResult.order}" /> 
						<input type="hidden" name="orderColumn" id="orderColumn" value="${pageResult.orderColumn}" /> 
						<input type="hidden" name="moduleName" id="moduleName" value="${vo.moduleName }">
					</form>
					<!-- <div class="hr-line-dashed"></div> -->
					<div class="col-lg-10 col-lg-offset-1">
						<div class="col-lg-3">
							<div class="ibox float-e-margins">
								<div class="ibox-title">
									<h5>模块</h5>
									<div class="ibox-tools">
										<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a> <a class="close-link"> <i class="fa fa-times"></i></a>
									</div>
								</div>
								<div class="ibox-content treeboxheight" style="border-left-color: #eae4e4; border-style: solid; border-width: 1px;">
									<div class="zTreeDemoBackground left">
										<ul id="tree" class="ztree"></ul>
									</div>
								</div>
							</div>
							<%-- <jsp:include page="questions_left.jsp" /> --%>
							<%-- <%@include file="questions_left.jsp"%> --%>

						</div>
						<div class="col-lg-9 animated fadeInRight">
							<div class="row">
								<div class="ibox float-e-margins">
									<div class="ibox no-padding panel-heading" style="padding-left: 20px">
										<c:if test="${not empty pageResult.resultList}">
											<h2>
												<c:if test="${fn:length(vo.moduleName)<=0}">全部问题</c:if>
												${vo.moduleName }
											</h2>
											<div class="panel-group" id="accordion">
												<c:forEach items="${pageResult.resultList}" var="e" varStatus="s">
													<div class="panel panel-default">
														<div class="panel-heading" data-toggle="collapse" data-parent="#accordion" href="#collapse_${s.index }">
															<h4 class="panel-title">
																<a data-toggle="collapse" data-parent="#accordion" href="#collapse_${s.index }"> ${e.question} </a>
															</h4>
														</div>
														<div id="collapse_${s.index }" class="panel-collapse collapse">
															<div class="panel-body">${e.answer }</div>
														</div>
													</div>
												</c:forEach>
											</div>
										</c:if>
									</div>
								</div>
								<div class="text-center">
									<div class="btn-group">
										<div id="LAY_page" style="margin: 0 10px;"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../../include/js.jsp"%>
	<%@ include file="../open/open_img.jsp"%>
	<script src="${basePath}ext/layui/layui.js"></script>
	<link href="${basePath}h/js/plugins/jquery-ztree/3.5.24/css/zTreeStyle/metro.css" rel="stylesheet" type="text/css" />
	<script src="${basePath}h/js/plugins/jquery-ztree/3.5.24/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
	<script src="${basePath}ext/js/tree.js" type="text/javascript"></script>
	<script type="text/javascript">
		var setting = {
			data: {simpleData: {enable: true}},
			view: {
				showLine: false
			},
			// 回调函数
			callback : {
				onClick : function(event, treeId, treeNode, clickFlag) {
					$("#pid").val(treeNode.id);
					$("#moduleName").val(treeNode.name);
					$("form").submit();
				}
			}
		};
		$(document).ready(function(){
			$.ajax({
			    url: 'treeData.do',
				success: function(data) { 
					var moduleName = '${vo.moduleName}';
					initTree('${selectedIds}',data);
					var treeObj = $.fn.zTree.getZTreeObj("tree");
					if(moduleName.length>0){
						var node = treeObj.getNodeByParam("name", moduleName);
						if(null != node){
							node.icon="/h/img/select.png";
							treeObj.updateNode(node);
						}
					}
						
					
			    }
			});
		});
	</script>
	<script>
			layui
					.use(
							[ 'layim', 'laypage' ],
							function() {
								var layim = layui.layim, layer = layui.layer, laytpl = layui.laytpl, $ = layui.jquery, laypage = layui.laypage;
								laypage({
									cont : 'LAY_page',
									pages : '${pageResult.pageBean.totalPages}',//分页总数
									groups : 2,//连续分页数
									curr : '${pageResult.pageBean.currentPage}',//当前页
									first : 1,
									last : '${pageResult.pageBean.totalPages}',
									prev : '<i class="fa fa-chevron-left"></i>',
									next : '<i class="fa fa-chevron-right"></i>',
									skip : false,
									jump : function(obj, first) {
										var curr = obj.curr;
										if (!first) {
											page(curr,
													'${pageResult.pageBean.pageSize}');
										}
									}
								});
							});
			$(function() {
				$(".gohome").css("display", "none");
			})
		</script>
	<script>
        $(document).ready(function(){

            $('#loading-example-btn').click(function () {
                btn = $(this);
                simpleLoad(btn, true)

                // Ajax example
//                $.ajax().always(function () {
//                    simpleLoad($(this), false)
//                });
				location.href="questionDetail.do";
                simpleLoad(btn, false)
            });
        });

        function simpleLoad(btn, state) {
            if (state) {
                btn.children().addClass('fa-spin');
                btn.contents().last().replaceWith(" Loading");
            } else {
                setTimeout(function () {
                    btn.children().removeClass('fa-spin');
                    btn.contents().last().replaceWith(" 刷新");
                }, 2000);
            }
        }
        $(function(){
            //改变树组件容器的高度
        	 $(".treeboxheight").height(($(window).height()-350));

        });
        window.onresize = function doResize() {
       	 var win = getWinSize();
       	 $(".treeboxheight").height(($(window).height()-350));
       	 $("#listTable").jqGrid('setGridWidth', win.WinW-10).jqGrid('setGridHeight', win.WinH-260);
       }
    </script>
</body>
</html>
