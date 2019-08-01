document.addEventListener("DOMContentLoaded", function () {
    var logoutForm = document.querySelector('#logoutForm');
    var timer = document.querySelector("#timer");
    setTimeout("timer.innerText = '4';", 1000);
    setTimeout("timer.innerText = '3';", 2000);
    setTimeout("timer.innerText = '2';", 3000);
    setTimeout("timer.innerText = '1';", 4000);
    setTimeout("timer.innerText = '0';", 5000);
    setTimeout("logoutForm.submit();", 7200);
});
