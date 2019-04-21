document.addEventListener("DOMContentLoaded", function() {
    setTimeout("location.href = location.origin + '/user';", 5000);
    console.log(location.origin);
});
