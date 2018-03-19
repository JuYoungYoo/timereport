/**
 * Created by user on 2017-05-26.
 */
$('.sidebar-menu li').eq(1).addClass('active');

$(function(){
    
});
/*      등록      */
function addFn(){
    check.userid($('#userid'));
    check.pwd($('#pwd'));
    if('' != $('#pwd').val()) check.pwd($('#pwdCheck'));
    check.name($('#name'));
    href.post('/member/add', '#mainform');
}