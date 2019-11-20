<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../../../include/tld.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
    <%@ include file="../../../include/css.jsp" %>
    <link href="${basePath}h/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <%@ include file="../../../include/status.jsp" %>

    <style type="text/css">
        legend {
            border-bottom: 0px;
            width: 80px;
            margin-bottom: 0px;
            font-size: 14px !important;
        }
    </style>
</head>
<body class="gray-bg">
<div class="col-sm-12">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <ol class="breadcrumb">
                <li><a>报告返工</a></li>
                <li><strong>编辑</strong></li>
            </ol>
        </div>
        <div class="ibox-content">
            <form method="post" class="form-horizontal" id="thisForm">
                <input name="id" value="${vo.id}" type="hidden"/>
                <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
                    <tr>
                        <td class="width-15 active"><label class="pull-right">报告编号:</label></td>
                        <td class="width-35">
                            <div class="input-group">
                                ${vo.reportNo}
                            </div>
                        </td>
                        <td class="width-15 active"><label class="pull-right">任务编号:</label></td>
                        <td class="width-35">
                            <a href="javascript:void(0);"
                               onclick="fnTask('${vo.taskVo.id}','${vo.taskVo.no}');">${vo.taskVo.no}</a>
                        </td>
                    </tr>
                    <tr>
                        <td class="active"><label class="pull-right">客户名称:</label></td>
                        <td>
                            ${vo.custVo.custName}
                        </td>
                        <td class="active"><label class="pull-right">检测类型:</label></td>
                        <td>
                            ${vo.reportVo.taskType}
                        </td>
                    </tr>
                    <tr>
                        <td class="active"><label class="pull-right">样品类别:</label></td>
                        <td>
                            ${vo.reportVo.sampTypeName}
                        </td>
                        <td class="active"><label class="pull-right">样品名称:</label></td>
                        <td>
                            ${vo.reportVo.sampName}
                        </td>
                    </tr>
                    <tr>
                        <td class="active"><label class="pull-right ">报告日期:</label></td>
                        <td>
                            ${vo.reportVo.reportDate}
                        </td>
                        <td class="active"><label class="pull-right">要求完成日期:</label></td>
                        <td>
                            ${vo.reportVo.finishDate}
                        </td>
                    </tr>
                </table>
                <fieldset class="layui-elem-field layui-field-title" style="margin-bottom: 10px;">
                    <legend>返工信息</legend>
                </fieldset>
                <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
                    <tbody>
                    <tr>
                        <td class="active"><label class="pull-right">更新内容:</label></td>
                        <td colspan="3">
                            <textarea id="content" name="content" class="form-control"
                                      placeholder="此出填写报告返工的内容，报告文件中需独立更新">${vo.content}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="width-15 active"><label class="pull-right">报告信息:</label></td>
                        <td>
                            <button class="btn btn-info" type="button" onclick="fnOpenReport();">在线编辑报告</button>
                        </td>
                        <td class="width-15 active"><label class="pull-right">电子存档:</label></td>
                        <td class="width-35">
                            <div class="input-group">
                                <input type="text" readonly="readonly" name="categoryName" id="categoryName"
                                       class="form-control" placeholder="请选择存放路径" value="${vo.categoryName}">
                                <input type="hidden" id="categoryId" name="categoryId" value="${vo.categoryId}">
                                <div class="input-group-btn">
                                    <button tabindex="-1" class="btn btn-primary" type="button"
                                            onclick="fnSelectcategory()">选择
                                    </button>
                                </div>
                            </div>
                        </td>

                    </tr>
                    <tr>
                        <td class="active"><label class="pull-right">存放位置:</label></td>
                        <td colspan="3">
                            <div class="input-group" style="width: 100%">
                                <textarea style="width: 100%;" id="position" name="position" class="form-control"
                                          placeholder="地点+楼层+柜号+列号">${vo.position}</textarea>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="width-15 active"><label class="pull-right">处&nbsp;理&nbsp;&nbsp;人:</label></td>
                        <td>
                            <input type="text" id="userName" name="userName" class="form-control" placeholder="归档人员"
                                   value="${vo.userName}" readonly="readonly">
                            <input type="hidden" name="userId" value="${vo.userId}">
                        </td>
                        <td class="width-15 active"><label class="pull-right">返工日期:</label></td>
                        <td>
                            <div class="input-group date">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                <input id="date" name="date" class="form-control required datetimeISO"
                                       validate="required" placeholder="归档日期" type="text" value="${vo.date}"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="active"><label
                                class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
                        <td colspan="3">
                            <textarea style="width: 100%;" id="remark" name="remark" class="form-control"
                                      maxlength="128">${vo.remark}</textarea>
                        </td>
                    </tr>
                </table>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <div class="col-sm-12 col-sm-offset-1">
                        <a class="btn btn-w-m btn-primary" type="button"
                           onclick="fnSubmit2Save('updateData.do?isCommit=0')"><i class="fa fa-file-o"
                                                                                  aria-hidden="true"></i> 保存</a>
                        <a class="btn btn-w-m btn-success" type="button" onclick="fnSubmit('updateData.do?isCommit=1')"><i
                                class="fa fa-check" aria-hidden="true"></i> 提交</a>
                        <a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo"
                                                                                              aria-hidden="true"></i> 返回</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="../../../include/js.jsp" %>
<script src="${basePath}h/js/plugins/sweetalert/sweetalert.min.js"></script>
<!--PageOffice.js -->
<script type="text/javascript" src="${basePath}pageoffice.js" id="po_js_main"></script>
<script>
    function fnOpenReport() {
        POBrowser.openWindow('${basePath}bus/reportUpd/editReport.do?id=${vo.id}', 'width=1200px;height=800px;');
    }

    function fnSelectcategory() {
        layer.open({
            title: '路径选择',
            type: 2,
            area: ['300px', '470px'],
            fix: false, //不固定
            maxmin: true,
            content: '${basePath}doc/category/select.do?id=' + $('#categoryId').val(),
            btn: ['确定', '取消'], //按钮
            btn1: function (index, layero) {
                var iframeWin = window[layero.find('iframe')[0]['name']];
                var data = iframeWin.fnSelect();
                $('#categoryId').val(data.id);
                $('#categoryName').val(data.name);
            }
        });
    }

    function fnTask(id, no) {
        parent.layer.open({
            title: '任务【' + no + '】',
            type: 2,
            area: ['1000px', '85%'],
            fix: false, //不固定
            maxmin: true,
            content: '/bus/task/show.do?id=' + id
        });
    }

    function fnSubmit(url) {
        swal({
            title: "您确定要提交该任务吗",
            text: "提交后将无法修改，请谨慎操作！",
            type: "success",
            showCancelButton: true,
            confirmButtonColor: "#1ab394",
            confirmButtonText: "确定",
            cancelButtonText: "取消"
        }, function () {
            $('#thisForm').attr('action', url);
            var b = $("#thisForm").FormValidate();
            if (b) {
                $('#thisForm').ajaxSubmit(function (res) {
                    if (res.status == 'success') {
                        parent.toastr.success(res.message, '');
                        backMainPage();
                    } else {
                        parent.toastr.error(res.message, '');
                    }
                });
            }
        });
    }

    function fnSubmit2Save(url) {
        $('#thisForm').attr('action', url);
        $('#thisForm').ajaxSubmit(function (res) {
            if (res.status == 'success') {
                parent.toastr.success(res.message, '');
            } else {
                parent.toastr.error(res.message, '');
            }
        });
    }
</script>
</body>
</html>
