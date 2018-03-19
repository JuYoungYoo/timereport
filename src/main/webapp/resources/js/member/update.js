/**
 * Created by user on 2017-05-26.
 */
$('.sidebar-menu li').eq(1).addClass('active');

$(function () {
    /*  유효성 검사  */
    $('#userid').blur(function () {
        check.userid($(this));
    });
    $('#name').blur(function () {
        check.name($(this));
    });
});

/*수정*/
function updateFn() {
    /*유효성 검사*/
    check.userid($('#userid'));
    check.name($('#name'));
    /*수정 Action*/
    $.ajax({
        type : 'post',
        url : '/member/update',
        data : $('#mainform').serialize(),
        dataType : 'text',
        error : function(err){
            alert('[ERROR] 고객센터로 연락바랍니다.');
        },
        success : function(data){
            href.post('/member','#mainform');
        }
    });
}
/*비밀번호 변경*/
function changePwdFn(){
    $.ajax({
        type : 'post',
        data : $('#pwdform').serialize(),
        url : '/member/update/pwd',
        dataType : 'JSON',
        error : function(err){
            console.log(err);
            alert('[ERROR] ' + err.RESULT_MSG);
        },
        success : function(data){
            console.log(data);
            alert(data.RESULT_MSG);
            if("SUCCESS" == data.RESULT_CODE){
                $('#modal_pwd').modal('toggle');
            }
        }
    });
}
/*      삭제      */
function deleteFn() {
    if(!confirm('삭제하시겠습니까?')) return;
    $.ajax({
        type : 'get',
        url : '/member/delete',
        data : { seq : $('#seq').val() },
        dataType : 'json',
        error : function(err){
            console.log(err);
            alert('[ERROR] 고객센터로 연락바랍니다.');
        },
        success : function (data){
            console.log(data);
            alert(data.RESULT_MSG);
            if('SUCCESS' == data.RESULT_CODE) href.post('/member','#mainform');
        }
    })
}