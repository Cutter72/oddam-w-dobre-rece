document.addEventListener("DOMContentLoaded", function () {
    var submitBtn = document.querySelector('#registrationSubmit');
    var pass1 = document.querySelector('#password1');
    var pass2 = document.querySelector('#password2');

    pass1.addEventListener("keyup", passCheck);
    pass2.addEventListener("keyup", passCheck);

    function passCheck() {
        if (pass1.value === pass2.value && pass1.value !== "") {
            submitBtn.disabled = false;
        } else {
            submitBtn.disabled = true;
        }
    }

});

