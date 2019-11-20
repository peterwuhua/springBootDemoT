(function ($) {
    var v;
    //Create a new Validator instance for jQuery   
    v = $.FdyValidator = new FdyValidator();
    //extend all jQuery instance and so that all jQuery instance could execute validator
    $.fn.extend({
        FormValidate: function (handler, custom_msg) {
            return v.FormValidate($(this).selector, handler, custom_msg);
        },
        InitValidate: function (handler, custom_msg) {
            return v.InitValidate($(this).selector, handler, custom_msg);
        }
    });
})(jQuery);

//class
function FdyValidator() {
    this.version = 1.0;
};

FdyValidator.Tag = { "TagName": "input", "AttrName": "validate" };

FdyValidator.prototype.InitValidate = function (form, handler, custom_msg) {
    var _form = form;
    var _handle = (handler || "follow").split(" ");  // should convert to arrary
    var _message = custom_msg || FdyValidator.message;
    //默认返回true
    var ret_val = true;
    var fail = 0;
    $(_form + " :" + FdyValidator.Tag["TagName"]).each(function () {
       // var succ = true;

        var attr = $(this).attr(FdyValidator.Tag["AttrName"]);
        if (typeof (attr) != "undefined") {
            //添加绑定
            $(this).blur(function () { FdyValidator.Validator($(this),_handle,_message) });
        }
    });
};

FdyValidator.Validator = function (item, handler, custom_msg) {
    var succ = true;
    var _handle = handler;  // should convert to arrary
    var _message = custom_msg || FdyValidator.message;
    var val = item.val();    //取得元素的值
    var attr = item.attr(FdyValidator.Tag["AttrName"]);
    var func_names = attr.split(" ");   //取得要验证的rules
    var msg;    //save message if error occured

    for (var i in func_names) {
        //遍历rules
        var msg_index = ["program"];    //默认一个数组存放错误索引
        succ = false;
        if (func_names[i] in FdyValidator.rules) {
            msg_index = [func_names[i]];
            //验证表达式是否正确
            if (FdyValidator.rules[func_names[i]].validate(item, val, msg_index))
                succ = true;
        }
        if (!succ) {
            //验证不通过
            if (msg_index[0] in _message)
                msg = _message[msg_index[0]];
            else
                msg = _message["unknown"];

            for (var j in _handle) {
                if (_handle[j] in FdyValidator.ErrorHandle) {
                    FdyValidator.ErrorHandle[_handle[j]](item, succ, msg);
                }
            }
            break;
        }
        else {
            FdyValidator.ErrorHandle["remove"](item);
        }

    }
}

FdyValidator.prototype.FormValidate = function (form, handler, custom_msg) {
    var _form = form;
    var _handle = (handler || "follow").split(" ");  // should convert to arrary
    var _message = custom_msg || FdyValidator.message;
    //默认返回true
    var ret_val = true;
    var fail = 0;
    $(_form + " :" + FdyValidator.Tag["TagName"]).each(function () {
        var succ = true;

        var attr = $(this).attr(FdyValidator.Tag["AttrName"]);
        if (typeof (attr) != "undefined") {
            var val = $(this).val();    //取得元素的值
            var func_names = attr.split(" ");   //取得要验证的rules
            var msg;    //save message if error occured

            for (var i in func_names) {
                //遍历rules
                var msg_index = ["program"];    //默认一个数组存放错误索引
                succ = false;
                if (func_names[i] in FdyValidator.rules) {
                    msg_index = [func_names[i]];
                    //验证表达式是否正确
                    if (FdyValidator.rules[func_names[i]].validate($(this), val, msg_index))
                        succ = true;
                }
                if (!succ) {
                    //验证不通过
                    fail = parseInt(fail) + 1;
                    if (msg_index[0] in _message)
                        msg = _message[msg_index[0]];
                    else
                        msg = _message["unknown"];

                    for (var j in _handle) {
                        if (_handle[j] in FdyValidator.ErrorHandle) {
                            FdyValidator.ErrorHandle[_handle[j]]($(this), succ, msg);
                        }
                    }
                    break;
                }
                else {
                    FdyValidator.ErrorHandle["remove"]($(this));
                }

            }


            ret_val = ret_val && succ;
        }

    });
    return fail == 0;
};

FdyValidator.InnerValidate = function (str, rule) {
    if (str == "")
        return true;
    var re = new RegExp(rule);
    return (re.test(str));
};

FdyValidator.rules = {
    "required": {   //must be filled
        reg: /^[ ]+$/,
        validate: function (element, str) { if (str == "") return false; return (!FdyValidator.InnerValidate(str, this.reg)); }
    },
    "email": {      // email
        reg: /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/,
        validate: function (element, str) { return (FdyValidator.InnerValidate(str, this.reg)); }
        //validate=FdyValidator.InnerValidate
    },
    "cardno": {     // cardno for campus card
        reg: /^\d+$/,
        validate: function (element, str) { return (FdyValidator.InnerValidate(str, this.reg)); }
    },
    "cardpwd": {   // password for campus card
        reg: /^\d{6}$/,
        validate: function (element, str) { return (FdyValidator.InnerValidate(str, this.reg)); }
    },
    "cname": {    // chinese name
        reg: /[\u4e00-\u9fa5]/,
        validate: function (element, str) { return (FdyValidator.InnerValidate(str, this.reg)); }
    },
    "integer": {  // integer, max & min is optional
        reg: /^[0-9]*[1-9][0-9]*$/,
        validate: function (element, str, msg_index) {
            var ret_val;
            var msg = msg_index[0] || "integer";

            ret_val = FdyValidator.InnerValidate(str, this.reg);

            var value = parseInt(str || 0);
            var min_val = 0, max_val = 0;

            if (typeof (element.attr("min")) != "undefined") {
                min_val = parseInt(element.attr("min"));
                msg_index.push(min_val.toString());
                if (value < min_val)
                    ret_val = false;
                msg = msg + "_min";
            }

            if (typeof (element.attr("max")) != "undefined") {
                max_val = parseInt(element.attr("max"));
                msg_index.push(max_val.toString());
                if (value > max_val)
                    ret_val = false;
                msg = msg + "_max";
            }
            msg_index[0] = msg;
            return ret_val;
        }
    },
    "ip": { //ip address
        reg: /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/,
        validate: function (element, str) { return (FdyValidator.InnerValidate(str, this.reg)); }
    },
    "username": { // user name
        reg: /^[a-z]\w{2,18}$/,
        validate: function (element, str) { return (FdyValidator.InnerValidate(str, this.reg)); }
    },
    "mobile": { // user name
        reg: /^(((13)|(15)|(14)|(18))\d{9})$/,
        validate: function (element, str) { return (FdyValidator.InnerValidate(str, this.reg)); }
    }
};

FdyValidator.message = {
    "required": "必填",
    "email": "请输入有效的电子邮件",
    "cardno": "不正确的校园卡号，请重新输入！",
    "cardpwd": "不正确的校园卡密码，请重新输入！",
    "cname": "错误的中文名！",
    "integer": "请输入整数！",
    "integer_min": "请输入大于[ {0} ]的整数！",
    "integer_max": "请输入小于[ {0} ]的整数！",
    "integer_min_max": "请输入[ {0} - {1} ]之间的整数！",
    "ip": "请输入正确的IP地址",
    "username": "请输入以字母开头，后续可以包括下划线、字母、数字的最少三位，最长19用户名，",
    "program": "程序错误(非法的验证属性)，请检查！",
    "mobile": "移动电话输入错误，请检查！",
    "unknown": "未定义错误！",
    "success": "验证通过！"
};

FdyValidator.ErrorHandle = {
    "follow": function (item, succ, msg) {

        if (item.next("label").length > 0) {
            item.next("label").remove();  //remove prompt if existed
        }
        if (!succ)
            $("<label class='error'></label>").attr("for", item.attr("id")).text(msg).insertAfter(item);
        item.attr("class",item.attr("class") + " error");

    },
    "remove": function (item)
    {
        if (item.next("label").length > 0) {
            item.next("label").remove();  //remove prompt if existed
            item.attr("class",FdyValidator.StrHandle["removeCss"](item.context.className));
        }
    }
};

FdyValidator.StrHandle = {
    "removeCss": function (css) {
        var new_css = "";
        var strs = new Array();
        strs = css.split(" ");
        for (i = 0; i < strs.length ; i++)
        {
            if (strs[i] != "error") {
                new_css += strs[i] + " ";
            }
        }
        return new_css;
    }
};