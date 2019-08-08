document.addEventListener("DOMContentLoaded", function () {
    var showDetailsBtn = document.querySelectorAll(".showDetailsBtn");

    showDetailsBtn.forEach(btn => {
         btn.addEventListener("click", function () {
         var details = this.parentElement.nextElementSibling;
        console.log(btn);
        if (details.hidden) {
            btn.innerText = "Ukryj";
            details.hidden = false;
        } else {
            btn.innerText = "Pokaż";
            details.hidden = true;
        }
         });
     })
});
