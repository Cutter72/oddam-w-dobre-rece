document.addEventListener("DOMContentLoaded", function () {
    var deleteBtnList = document.querySelectorAll('.deleteBtn');
    for (var i = 0; i < deleteBtnList.length; i++) {
        deleteBtnList[i].addEventListener("click", function (evt) {
            evt.preventDefault();
            if (confirm('Na pewno chcesz usunąć tego użytkownika?')) {
                window.location.href = this.href;
            }
        });
    }
});

