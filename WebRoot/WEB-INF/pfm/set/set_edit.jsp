<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../../include/tld.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="favicon.ico">
    <%@ include file="../../include/css.jsp" %>
    <%@ include file="../../include/status.jsp" %>
</head>
<body>
<div class="col-sm-12">
    <div class="ibox float-e-margins">
        <div class="ibox-content" style="padding: 5px 10px;">
            <form method="post" action="${fn:length(vo.id)>0? 'updatePlan.do':'addPlan.do'}" class="form-horizontal"
                  id="listForm">
                <c:if test="${fn:length(vo.id)>0}">
                    <input name="id" id="id" value="${vo.id}" type="hidden"/>
                </c:if>
                <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
                    <tbody>
                    <tr>
                        <td class="width-15 active"><label class="pull-right">工作名称:</label></td>
                        <td class="width-35">
                            <input id="workName" name="workName" class="form-control required" validate="required"
                                   maxlength=32 placeholder="工作名称" type="text" value="${vo.workName}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="width-15 active"><label class="pull-right">编号:</label></td>
                        <td class="width-35">
                            <input id="code" name="code" class="form-control required" validate="required"
                                   maxlength=32 placeholder="编号" type="text" value="${vo.code}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="width-15 active"><label class="pull-right">工作时长:</label></td>
                        <td class="width-35">
                            <input id="time" name="time" class="form-control required" validate="required"
                                   maxlength=32 placeholder="-1 代表按任务要求计算或不计时" type="text" value="${vo.time}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="width-15 active"><label class="pull-right">分值:</label></td>
                        <td class="width-35">
                            <input id="value" name="value" class="form-control required" validate="required"
                                   maxlength=32 placeholder="分值" type="text" value="${vo.value}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="width-15 active"><label class="pull-right">备注:</label></td>
                        <td class="width-35">
                            <input maxlength="128" id="remarks" name="remarks" class="form-control " type="text" value="${vo.remarks}"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
<%@ include file="../../include/js.jsp" %>
<!-- Input Mask-->
<script src="${basePath}h/js/plugins/jasny/jasny-bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $(".i-checks").iCheck({
            checkboxClass: "icheckbox_square-green",
            radioClass: "iradio_square-green",
        });
    });
    var index = parent.layer.getFrameIndex(window.name);

    function submitSave() {
        var t = $("#listForm").FormValidate();
        if (t) {
            $.ajax({
                url: "${fn:length(vo.id)>0? 'updatePlan.do':'addPlan.do'}",
                dataType: "json",
                data: $('#listForm').serialize(),
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
</script>
</body>
</html>