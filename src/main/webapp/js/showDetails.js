document.addEventListener("DOMContentLoaded", function () {
    var showDetailsBtn = document.querySelectorAll(".showDetailsBtn");

    showDetailsBtn.forEach(btn => {
         btn.addEventListener("click", function () {
             console.log("weszło w guzik");
             var details = this.parentElement.nextElementSibling;
             console.log(details.hidden);
             if (details.hidden) {
                 details.hidden = false;
             } else {
                 details.hidden = true;
             }
         });
     })
});
