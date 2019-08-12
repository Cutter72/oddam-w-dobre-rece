document.addEventListener("DOMContentLoaded", function () {
    var makeStatusTrueBtn = document.querySelectorAll(".makeStatusTrue");

    makeStatusTrueBtn.forEach(btn => {
        btn.addEventListener("click", function () {
            this.nextElementSibling.hidden = false;
            this.remove();
         });
     })
});
