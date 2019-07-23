document.addEventListener("DOMContentLoaded", function () {
    setTimeout("location.href = location.origin + '/login';", 5200);
    var timer = document.querySelector("#timer");
    setTimeout("timer.innerText = '4';", 1000);
    setTimeout("timer.innerText = '3';", 2000);
    setTimeout("timer.innerText = '2';", 3000);
    setTimeout("timer.innerText = '1';", 4000);
    setTimeout("timer.innerText = '0';", 5000);
});
