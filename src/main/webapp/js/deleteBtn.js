$('document').ready(function () {
    var deleteBtnList = $('.deleteBtn');
    deleteBtnList.on('click', function () {
        preventDefault();
        jConfirm('Are you sure??', '', function (r) {
            if (r == true) {
                //proceed
            }
        })
    });
});