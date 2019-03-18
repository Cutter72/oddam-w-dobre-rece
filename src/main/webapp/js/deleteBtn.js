console.log("Usuń buton weszło");
$('document').ready(function () {
    var deleteBtnList = $('.deleteBtn');
    console.log(deleteBtnList);
    deleteBtnList.on('click', function () {
        preventDefault();
        jConfirm('Are you sure??', '', function (r) {
            if (r == true) {
                console.log('OK');
            }
        })
    });
});