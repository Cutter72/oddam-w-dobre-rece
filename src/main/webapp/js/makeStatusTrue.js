document.addEventListener("DOMContentLoaded", function () {
    var showDetailsBtn = document.querySelectorAll(".makeStatusTrue");

    showDetailsBtn.forEach(btn => {
        btn.addEventListener("click", function () {
            var details = this.parentElement.nextElementSibling;
            if (details.hidden) {
                btn.innerText = "Ukryj";
                btn.parentElement.style.backgroundColor = "#f0f0f0";
                details.hidden = false;
            } else {
                btn.innerText = "Pokaż";
                btn.parentElement.style.backgroundColor = "#ffffff";
                details.hidden = true;
            }
         });
     })
});
