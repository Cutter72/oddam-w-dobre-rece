document.addEventListener("DOMContentLoaded", function() {
    /**
     * HomePage - Help section
     */
    var scrollHere = document.getElementById("form-place");
  var urlParams = new URLSearchParams(window.location.search);
    var step = 4;
    // if (urlParams.has("step")){
    //     step = urlParams.get("step");
    //     scrollHere.scrollIntoView();
    // } else {
    //     window.history.pushState(null, "TitleURL","/user/form/step2?step=4#Form");
    //     step = 4;
    // }

  class Help {
    constructor($el) {
      this.$el = $el;
      this.$buttonsContainer = $el.querySelector(".help--buttons");
      this.$slidesContainers = $el.querySelectorAll(".help--slides");
      this.currentSlide = this.$buttonsContainer.querySelector(".active").parentElement.dataset.id;
      this.init();
    }

    init() {
      this.events();
    }

    events() {
      /**
       * Slide buttons
       */
      this.$buttonsContainer.addEventListener("click", e => {
        if (e.target.classList.contains("btn")) {
          this.changeSlide(e);
        }
      });

      /**
       * Pagination buttons
       */
      this.$el.addEventListener("click", e => {
        if (e.target.classList.contains("btn") && e.target.parentElement.parentElement.classList.contains("help--slides-pagination")) {
          this.changePage(e);
        }
      });
    }

    changeSlide(e) {
      e.preventDefault();
      const $btn = e.target;

      // Buttons Active class change
      [...this.$buttonsContainer.children].forEach(btn => btn.firstElementChild.classList.remove("active"));
      $btn.classList.add("active");

      // Current slide
      this.currentSlide = $btn.parentElement.dataset.id;

      // Slides active class change
      this.$slidesContainers.forEach(el => {
        el.classList.remove("active");

        if (el.dataset.id === this.currentSlide) {
          el.classList.add("active");
        }
      });
    }

    /**
     * TODO: callback to page change event
     */
    changePage(e) {
      e.preventDefault();
      const page = e.target.dataset.page;

      console.log(page);
    }
  }
  const helpSection = document.querySelector(".help");
  if (helpSection !== null) {
    new Help(helpSection);
  }

  /**
   * Form Select
   */
  class FormSelect {
    constructor($el) {
      this.$el = $el;
      this.options = [...$el.children];
      this.init();
    }

    init() {
      this.createElements();
      this.addEvents();
      this.$el.parentElement.removeChild(this.$el);
    }

    createElements() {
      // Input for value
      this.valueInput = document.createElement("input");
      this.valueInput.type = "text";
      this.valueInput.name = this.$el.name;

      // Dropdown container
      this.dropdown = document.createElement("div");
      this.dropdown.classList.add("dropdown");

      // List container
      this.ul = document.createElement("ul");

      // All list options
      this.options.forEach((el, i) => {
        const li = document.createElement("li");
        li.dataset.value = el.value;
        li.innerText = el.innerText;

        if (i === 0) {
          // First clickable option
          this.current = document.createElement("div");
          this.current.innerText = el.innerText;
          this.dropdown.appendChild(this.current);
          this.valueInput.value = el.value;
          li.classList.add("selected");
        }

        this.ul.appendChild(li);
      });

      this.dropdown.appendChild(this.ul);
      this.dropdown.appendChild(this.valueInput);
      this.$el.parentElement.appendChild(this.dropdown);
    }

    addEvents() {
      this.dropdown.addEventListener("click", e => {
        const target = e.target;
        this.dropdown.classList.toggle("selecting");

        // Save new value only when clicked on li
        if (target.tagName === "LI") {
          this.valueInput.value = target.dataset.value;
          this.current.innerText = target.innerText;
        }
      });
    }
  }
  document.querySelectorAll(".form-group--dropdown select").forEach(el => {
    new FormSelect(el);
  });

  /**
   * Hide elements when clicked on document
   */
  document.addEventListener("click", function(e) {
    const target = e.target;
    const tagName = target.tagName;

    if (target.classList.contains("dropdown")) return false;

    if (tagName === "LI" && target.parentElement.parentElement.classList.contains("dropdown")) {
      return false;
    }

    if (tagName === "DIV" && target.parentElement.classList.contains("dropdown")) {
      return false;
    }

    document.querySelectorAll(".form-group--dropdown .dropdown").forEach(el => {
      el.classList.remove("selecting");
    });
  });

  /**
   * Switching between form steps
   */
  class FormSteps {
    constructor(form) {
      this.$form = form;
      this.$next = form.querySelectorAll(".next-step");
      this.$organizations = document.querySelectorAll(".organization");
      this.$requireds = document.querySelectorAll(".required");
      this.$prev = form.querySelectorAll(".prev-step");
      this.$step = form.querySelector(".form--steps-counter span");
      this.currentStep = step;

      //variables for rewrite inputs tu summary page
        //inputs
      this.$organization = document.querySelectorAll(".organization");
      this.$streetInput = document.querySelector(".streetInput");
      this.$cityInput = document.querySelector(".cityInput");
      this.$postCodeInput = document.querySelector(".postCodeInput");
      this.$callNumberInput = document.querySelector(".callNumberInput");
      this.$dateInput = document.querySelector(".dateInput");
      this.$timeInput = document.querySelector(".timeInput");
      this.$courierNoteInput = document.querySelector(".courierNoteInput");
      //outputs
      this.$organizationAndCitySummary = document.querySelector(".organizationAndCitySummary");
      this.$streetSummary = document.querySelector(".streetSummary");
      this.$citySummary = document.querySelector(".citySummary");
      this.$postCodeSummary = document.querySelector(".postCodeSummary");
      this.$callNumberSummary = document.querySelector(".callNumberSummary");
      this.$dateSummary = document.querySelector(".dateSummary");
      this.$timeSummary = document.querySelector(".timeSummary");
      this.$courierNoteSummary = document.querySelector(".courierNoteSummary");

      this.$stepInstructions = form.querySelectorAll(".form--steps-instructions p");
      const $stepForms = form.querySelectorAll("form > div");
      this.slides = [...this.$stepInstructions, ...$stepForms];

      this.init();
    }

    /**
     * Init all methods
     */
    init() {
      this.events();
      this.updateForm();
    }

    /**
     * Rewrite inputs to summary page (Cutter72)
     */

    updateSummary() {
        console.log(this.$organization);
      this.$organization.forEach(org => {
          if(org.checked){
            this.$organizationAndCitySummary.innerText = "Dla: " + org.nextElementSibling.nextElementSibling.firstElementChild.innerText;
        }
      });
      this.$streetSummary.innerText = this.$streetInput.value;
      this.$citySummary.innerText = this.$cityInput.value;
      this.$postCodeSummary.innerText = this.$postCodeInput.value;
      this.$callNumberSummary.innerText = this.$callNumberInput.value;
      this.$dateSummary.innerText = this.$dateInput.value;
      this.$timeSummary.innerText = this.$timeInput.value;
      this.$courierNoteSummary.innerText = this.$courierNoteInput.value;
    }

    /**
     * All events that are happening in form
     */
    events() {
      // Next step
      this.$next.forEach(btn => {
        btn.addEventListener("click", e => {
          e.preventDefault();
        if (this.currentStep == 4) {
            var isChecked = false;
            this.$organizations.forEach(checkbox => {
                if(checkbox.checked){
                  isChecked = true;
                }
            });
            if (isChecked) {
                this.currentStep++;
                window.history.pushState(null, "TitleURL","/user/form/step2?step="+this.currentStep+"#Form");
                this.updateForm();
            } else {
                alert("Zaznacz organizację.");
            }
        }else if(this.currentStep == 5){
            var notEmpty = true;
            this.$requireds.forEach(textarea => {
                if(textarea.checkValidity() && notEmpty){
                } else {
                notEmpty = false;
                }
            });
            if (notEmpty) {
                this.currentStep++;
                window.history.pushState(null, "TitleURL","/user/form/step2?step="+this.currentStep+"#Form");
                this.updateForm();
                this.updateSummary();
            } else {
                alert("Proszę wypełnić wszystkie pola oznaczone gwiazdką.");
            }
        }else {
            this.currentStep++;
            window.history.pushState(null, "TitleURL","/user/form/step2?step="+this.currentStep+"#Form");
            this.updateForm();
        }
        });
      });

      // Previous step
      this.$prev.forEach(btn => {
        btn.addEventListener("click", e => {
          e.preventDefault();
          this.currentStep--;
        window.history.pushState(null, "TitleURL","/user/form/step2?step="+this.currentStep+"#Form");
          this.updateForm();
        });
      });

      // Form submit
      this.$form.querySelector("form").addEventListener("submit", e => this.submit(e));
    }

    /**
     * Update form front-end
     * Show next or previous section etc.
     */
    updateForm() {
      this.$step.innerText = this.currentStep;

      // TODO: Validation

      this.slides.forEach(slide => {
        slide.classList.remove("active");

        if (slide.dataset.step == this.currentStep) {
          slide.classList.add("active");
        }
      });

      this.$stepInstructions[0].parentElement.parentElement.hidden = this.currentStep >= 6;
      this.$step.parentElement.hidden = this.currentStep >= 6;

      // TODO: get data from inputs and show them in summary
    }

    /**
     * Submit form
     *
     * TODO: validation, send data to server
     */
    submit(e) {
      // e.preventDefault();
      // this.currentStep++;
        // window.history.pushState(null, "TitleURL","/user/form/step2?step="+this.currentStep+"#Form");
      // this.updateForm();
    }
  }
  const form = document.querySelector(".form--steps");
  if (form !== null) {
    new FormSteps(form);
  }
});
