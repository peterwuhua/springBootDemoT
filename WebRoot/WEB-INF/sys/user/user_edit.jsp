<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../../include/tld.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="${basePath}h/css/plugins/chosen/chosen.css" rel="stylesheet">
    <%@ include file="../../include/css.jsp" %>
    <%@ include file="../../include/status.jsp" %>
</head>
<body class="gray-bg">
<div class="col-sm-12">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <ol class="breadcrumb">
                <li><a href="javascript:backMainPage();">人员</a></li>
                <li><strong>基本信息编辑</strong></li>
            </ol>
        </div>
        <div class="ibox-content">
            <form method="post" action="${fn:length(vo.id)>0? 'update4Data.do':'add4Data.do'}" class="form-horizontal"
                  enctype="multipart/form-data">
                <c:if test="${fn:length(vo.id)>0}">
                    <input name="id" value="${vo.id}" type="hidden"/>
                </c:if>
                <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
                    <tbody>
                    <tr>
                        <td class="width-15 active"><label class="pull-right">员工编号:</label></td>
                        <td class="width-35"><input id="no" name="no" class="form-control required" validate="required"
                                                    maxlength=64 placeholder="编号" type="text" value="${vo.no }"/></td>
                        <td class="width-15 active"><label class="pull-right">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</label>
                        </td>
                        <td class="width-35"><input id="name" name="name" class="form-control required"
                                                    validate="required" maxlength=64 placeholder="名称" type="text"
                                                    value="${vo.name }"/></td>
                    </tr>
                    <tr>
                        <td class=" active"><label
                                class="pull-right">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label></td>
                        <td>
                            <div class="radio i-checks">
                                <label>
                                    <div class="iradio_square-green">
                                        <input type="radio" value="男" name="sex"
                                               <c:if test="${vo.sex=='男'}">checked</c:if>>
                                    </div>
                                    男
                                </label>
                                <label>
                                    <div class="iradio_square-green">
                                        <input type="radio" value="女" name="sex"
                                               <c:if test="${vo.sex=='女'}">checked</c:if>>
                                    </div>
                                    女
                                </label>
                            </div>
                        </td>
                        <td class=" active"><label class="pull-right">身份证号码:</label></td>
                        <td><input id="credentialsNo" name="credentialsNo" class="form-control " type="text"
                                   value="${vo.credentialsNo }"/></td>
                    </tr>
                    <tr>
                        <td class=" active"><label class="pull-right">公司手机号:</label></td>
                        <td><input id="telephone" name="telephone" class="form-control" placeholder="13800000000"
                                   type="text" value="${vo.telephone }"/></td>
                        <td class="active"><label
                                class="pull-right">短&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label></td>
                        <td><input id="subTel" name="subTel" class="form-control" type="text" value="${vo.subTel}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class=" active"><label class="pull-right">个人手机号:</label></td>
                        <td><input id="mobile" name="mobile" class="form-control  required" validate="required"
                                   placeholder="13800000000" type="text" value="${vo.mobile }"/></td>
                        <td class="active"><label
                                class="pull-right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label></td>
                        <td><input id="email" name="email" class="form-control email" placeholder="xx@xx.com"
                                   type="text" value="${vo.email }"/></td>
                    </tr>
                    <tr>
                        <td class=" active"><label class="pull-right">毕业学校:</label></td>
                        <td><input id="graduationSchool" name="graduationSchool" class="form-control " type="text"
                                   value="${vo.graduationSchool }"/></td>
                        <td class=" active"><label
                                class="pull-right">专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:</label></td>
                        <td><input id="profession" name="profession" class="form-control " type="text"
                                   value="${vo.profession }"/></td>
                    </tr>
                    <tr>
                        <td class=" active"><label class="pull-right">学历/学位:</label></td>
                        <td><input id="education" name="education" class="form-control " type="text"
                                   value="${vo.education }"/></td>
                        <td class="active"><label
                                class="pull-right">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</label></td>
                        <td><input id="techTitle" name="techTitle" class="form-control" type="text"
                                   value="${vo.techTitle }"/></td>
                    </tr>
                    <tr>
                        <td class="active"><label class="pull-right ">入职日期:</label></td>
                        <td><input id="workDate" name="workDate" class="form-control dateISO" type="text"
                                   value="${vo.workDate }"/></td>
                        <td class="active"><label class="pull-right ">从事本技术领域年限:</label></td>
                        <td><input id="workYear" name="workYear" class="form-control" type="text"
                                   value="${vo.workYear }"/></td>
                    </tr>
                    <tr>
                        <th class="width-15 active"><label class="pull-right">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label>
                        </th>
                        <td class="width-35">
                            <div class="input-group">
                                <input type="text" id="orgName" name="orgName" class="form-control  required"
                                       validate="required" placeholder="请选择" autocomplete="off" value="${vo.orgName}"
                                       onclick="fnSelect()">
                                <input type="hidden" id="orgId" name="orgId" value="${vo.orgId}">
                                <div class="input-group-btn">
                                    <button tabindex="-1" class="btn btn-primary" type="button" onclick="fnSelect()">
                                        选择
                                    </button>
                                </div>
                            </div>
                        </td>
                        <td class="active"><label
                                class="pull-right">排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序:</label></td>
                        <td><input id="sort" name="sort" class="form-control " type="text" value="${vo.sort }"/></td>
                    </tr>
                    <%--<td class="active"><label class="pull-right">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务:</label></td>
                    <td><input id="job" name="job" class="form-control " type="text" value="${vo.job }" /></td>--%>
                    </tr>
                    <tr>
                        <td class="active"><label
                                class="pull-right">岗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</label></td>
                        <td>
                            <select name="duty" data-placeholder="请选择" class="chosen-select form-control" multiple>
                                <c:forEach items="${dutyList}" var="e" varStatus="s">
                                    <c:choose>
                                        <c:when test="${fn:contains(vo.duty,e)}">
                                            <option value="${e}" selected="selected" hassubinfo="true">${e}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${e}" hassubinfo="true">${e}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="active"><label
                                class="pull-right">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label></td>
                        <td>
                            <input maxlength="128" id="remark" name="remark" class="form-control " type="text" value="${vo.remark }"/>
                        </td>s
                    <tr>
                        <td class="active"><label class="pull-right">上传文件:</label></td>
                        <td>
                            <input type="file" name="files" multiple="multiple" class="form-control"/>
                        </td>
                        <td colspan="2" id="removeFile">
                            <c:forEach items="${vo.fileList}" var="e" varStatus="v">
                                <div style="float: left;margin-right: 10px;">
                                    <a href="download.do?filePath=${e.filePath}&trueName=${e.fileName}"
                                       class="btn btn-w-m btn-info">${e.fileName}</a>
                                    <a onclick="removeFiles('${e.id}',this)" title="删除"><i
                                            class="fa fa-close text-danger"></i></a>
                                </div>
                            </c:forEach>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="hr-line-dashed"></div>
                <div class="form-group">
                    <div class="col-sm-12 col-sm-offset-1">
                        <button class="btn btn-w-m btn-success" type="button" onclick="formSubmit('save4Data.do');">
                            <i class="fa fa-floppy-o" aria-hidden="true"></i> 保存
                        </button>
                        <button class="btn btn-w-m btn-primary" type="button" onclick="formSubmitAndBack();">
                            <i class="fa fa-check" aria-hidden="true"></i> 保存并返回
                        </button>
                        <a class="btn btn-w-m btn-white" href="javascript:backMainPage();"><i class="fa fa-undo"
                                                                                              aria-hidden="true"></i> 返回</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="../../include/js.jsp" %>
<!-- Chosen -->
<script src="${basePath}h/js/plugins/chosen/chosen.jquery.js"></script>
<script>
    $('input[type="file"]').prettyFile();

    function removeFiles(id, obj) {
        layer.confirm('确认要删除?', {icon: 3, title: '系统提示'}, function (index) {
            $.ajax({
                url: '${basePath}sys/files/deleteOne.do?id=' + id,
                dataType: "json",
                type: "post",
                async: false,
                success: function (data) {
                    if (data.status == 'success') {
                        layer.msg(data.message, {icon: 0, time: 1000});
                        $(obj).parent().remove();
                    }
                },
                error: function (ajaxobj) {
                }
            });
        });
    }

    function fnSelect() {
        var pId = $('#orgId').val();
        var pName = $('#orgName').val();
        layer.open({
            title: '部门选择',
            type: 2,
            area: ['400px', '75%'],
            fix: false, //不固定
            maxmin: true,
            content: '${basePath}sys/org/select2.do?ids=' + pId,
            btn: ['确定', '取消'], //按钮
            btn1: function (index, layero) {
                var iframeWin = window[layero.find('iframe')[0]['name']];
                var data = iframeWin.fnSelect();
                $('#orgId').val(data.id);
                $('#orgName').val(data.name);
            }
        });
    }

    $(document).ready(function () {
        $(".i-checks").iCheck({
            checkboxClass: "icheckbox_square-green",
            radioClass: "iradio_square-green",
        });
        var config = {
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
        }
    });
</script>
</body>
</html>
