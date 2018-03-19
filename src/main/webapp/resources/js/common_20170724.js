/**
 * 공통 JAVASCRIPT
 * Created by user on 2017-05-23.
 */
$(document).ready(function () {
    /* AJAX 로딩 시 */
    $(document).ajaxStart(function () {
        var width = 50;
        var height = 50;
        var top = ( $(window).height() - height ) / 2 + $(window).scrollTop();
        var left = ( $(window).width() - width ) / 2 + $(window).scrollLeft();

        if ($("#div_ajax_load_image").length != 0) {
            $("#div_ajax_load_image").css({
                "top": top + "px",
                "left": left + "px"
            });
            $("#div_ajax_load_image").show();
        } else {
            $('body').append('' +
                '<div id="div_ajax_load_image" style="position:absolute; top:' + top + 'px; left:' + left + 'px; width:' + width + 'px; height:' + height + 'px; ' +
                'z-index:9999; background:#f0f0f0; filter:alpha(opacity=50); opacity:alpha*0.5; margin:auto; padding:0; ">' +
                '<img src="/resources/image/loading_img.gif" style="width:95px; height:95px;"></div>');
        }
    });
    $(document).ajaxStop(function () {
        $("#div_ajax_load_image").hide();
    });
});

/*  쿠키 생성   */
function setCookie(name, val, path) {
    var d = new Date();
    d.setHours(d.getHours() + 6);
    var cookie = name + '=' + val;
    cookie += ";expires=" + d.toGMTString();
    if (path) cookie += ';path=' + path;
    document.cookie = cookie;
}

/*  유효성 검사 */
var check = {
    init: function () {
        $('.help-block').html('<div class="fail"></div>');
    },
    userid: function (tag) {
        var val = tag.val();
        var regexp = /^[a-zA-Z0-9_-]*$/;
        if ('' == val) {
            this.fail('아이디를 입력해주세요.', tag);
            return;
        } else if (!regexp.test(val)) {
            this.fail('영문,숫자,특수문자(-,_)만 사용 가능합니다.', tag);
            return;
        } else if (val.length >= 30) {
            this.fail('아이디는 30자 미만으로 사용 가능합니다.', tag);
            return;
        } else {
            // 아이디 중복확인 ajax
            this.success(tag);
        }
    },
    pwd: function (tag) {
        var val = tag.val();
        if ('' == val) {
            this.fail('비밀번호를 입력해주세요.', tag);
            return;
        } else if (val.length < 4) {
            this.fail('비밀번호는 4자리이상 입니다.', tag);
            return;
        } else {
            this.success(tag);
            return 'success';
        }
    },
    pwdMatch: function (val, compare_val, tag, compare_tag) {
        if (val != compare_val) {
            this.fail('비밀번호가 일치하지 않습니다.', tag);
            return;
        } else {
            this.success(tag);
            this.success(compare_tag);
        }
    },
    name: function (tag) {
        var val = tag.val();
        if ('' == val) {
            this.fail('이름을 입력해주세요.', tag);
            return;
        } else {
            this.success(tag);
        }
    },
    fail: function (text, tag) {
        var result_tag = $(tag).next('span.help-block');
        $(result_tag).html('<i class="fa fa-times-circle-o fail"> ' + text + '</i>');
    },
    success: function (tag) {
        var result_tag = $(tag).next('span.help-block');
        $(result_tag).html('<i class="fa fa-check success"></i>');
    }
}

/*  url 이동  */
var href = {
    post: function (url, form_tag) {
        $(form_tag).attr('method', 'post');
        $(form_tag).attr('action', url);
        $(form_tag).submit();
    }
}

/*  all member checkbox event */
$(function () {
    /* 체크박스 전체 클릭 */
    $('#allCheck').on('ifToggled', function () {
        if ($(this).is(':checked')) {
            $('input[type="checkbox"]').iCheck('check');
        } else {
            $('input[type="checkbox"]').iCheck('uncheck');
        }
    });
})


Map = function () {
    this.map = new Object();
};
Map.convertObj = function (mapData) {
    var arr = Object.keys(mapData).map(function (k) {
        return mapData[k];
    });
    return arr;
}
Map.prototype = {
    put: function (key, value) {
        this.map[key] = value;
    },
    get: function (key) {
        return this.map[key];
    },
    containsKey: function (key) {
        return key in this.map;
    },
    containsValue: function (value) {
        for (var prop in this.map) {
            if (this.map[prop] == value) return true;
        }
        return false;
    },
    isEmpty: function (key) {
        return (this.size() == 0);
    },
    clear: function () {
        for (var prop in this.map) {
            delete this.map[prop];
        }
    },
    remove: function (key) {
        delete this.map[key];
    },
    keys: function () {
        var keys = new Array();
        for (var prop in this.map) {
            keys.push(prop);
        }
        return keys;
    },
    values: function () {
        var values = new Array();
        for (var prop in this.map) {
            values.push(this.map[prop]);
        }
        return values;
    },
    size: function () {
        var count = 0;
        for (var prop in this.map) {
            count++;
        }
        return count;
    }
};


Date.prototype.addHours = function (h) {
    this.setHours(this.getHours() + h);
    return this;
};
Date.prototype.YYYYMMDDHH = function () {
    return this.format('YYYY-MM-DD HH');
};
