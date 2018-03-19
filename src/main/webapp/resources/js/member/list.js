/**
 * Created by user on 2017-05-16.
 */
// 회원관리 메뉴 CSS 활성화 
$('.sidebar-menu li').eq(1).addClass('active');

$(function () {
    /*  search param click event */
    $('#schBtn').click(function () {
        searchFn();
    });
    /*  search param selectbox event */
    $('#perPageNum').add('#DP_CODE').change(function () {
        searchFn();
    });
    /*  search param selectbox event */
    $('#perPageNum').add('#DP_CODE').change(function () {
        searchFn();
    });
});

/*  검색 */
function searchFn(){
    $('#current').val(1);
    href.post('/member', '#mainform');
}

/*  내용보기 */
function contentFn(seq) {
    $('#seq').val(seq);
    href.post('/member/form/update', '#mainform');
}

/*      삭제      */
function deleteFn() {
    /*삭제 할 회원 SEQ : JSON Array*/
    var seqArray = new Array();
    var selCnt = $('input[name="check"]:checked').size();
    if(0 == selCnt){
        alert('삭제할 회원을 선택해주세요.');
        return;
    }
    if(!confirm('선택하신 회원은 총 ' + selCnt + '입니다.\n삭제하시겠습니까?')) return;
    $('input[name="check"]:checked').each(function () {
        seqArray.push($(this).val());
    });
    $.ajax({
        type : 'post',
        url : '/member/delete/selected',
        data : JSON.stringify(seqArray),
        contentType : 'application/json',
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
/*  페이지 이동  */
function movePageFn(page){
    $('#current').val(page);
    href.post('/member', '#mainform');
}