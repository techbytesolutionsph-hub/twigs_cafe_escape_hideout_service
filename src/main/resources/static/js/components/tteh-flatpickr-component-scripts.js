function initializeDatePicker(id, placeholder) {
    flatpickr(id, {
        enableTime: false,
        disableMobile: true,
        altInput: true,
        altFormat: "Y-m-d",
        dateFormat: "Y-m-d",
        altInputClass: "form-control text-sm required-field",
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializeTimePicker(id, placeholder) {
    flatpickr(id, {
        enableTime: true,
        disableMobile: true,
        noCalendar: true,
        time_24hr: false,
        dateFormat: "h:i K",
        altInput: true,
        altFormat: "h:i K",
        altInputClass: "form-control text-sm required-field",

        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },

        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                $(instance.input).removeClass("is-invalid");
                $(instance.altInput).removeClass("is-invalid");
            }
        },

        position: "auto"
    });
}

function initializeMaxTodayDatePicker(id, placeholder) {
    flatpickr(id, {
        enableTime: false,
        disableMobile: true,
        altInput: true,
        altFormat: "Y-m-d",
        dateFormat: "Y-m-d",
        maxDate: "today",
        altInputClass: "form-control text-sm required-field",
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializeDatePicker2(id, placeholder) {
    flatpickr(id, {
        enableTime: false,
        disableMobile: true,
        altInput: true,
        altFormat: "Y-m-d",
        dateFormat: "Y-m-d",
        altInputClass: "form-control assessment-form-control",
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializeTimePicker2(id, placeholder) {
    flatpickr(id, {
        enableTime: true,
        disableMobile: true,
        noCalendar: true,
        dateFormat: "H:i",
        altInput: true,
        altFormat: "h:i K",
        altInputClass: "form-control assessment-form-control",
        time_24hr: false,
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializeMaxTodayDatePicker2(id, placeholder) {
    flatpickr(id, {
        enableTime: false,
        disableMobile: true,
        altInput: true,
        altFormat: "Y-m-d",
        dateFormat: "Y-m-d",
        minDate: "today",
        altInputClass: "form-control assessment-form-control",
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                $(instance.input).removeClass("is-invalid");
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializeMinTodayDatePicker(id, placeholder) {
    flatpickr(id, {
        enableTime: false,
        disableMobile: true,
        altInput: true,
        altFormat: "Y-m-d",
        dateFormat: "Y-m-d",
        minDate: "today",
        altInputClass: "form-control text-sm required-field",
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializePastDatePicker(id, placeholder) {
    flatpickr(id, {
        enableTime: false,
        disableMobile: true,
        altInput: true,
        altFormat: "Y-m-d",
        dateFormat: "Y-m-d",
        maxDate: new Date().fp_incr(-1),
        altInputClass: "form-control text-sm required-field",
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializeFutureDatePicker(id, placeholder) {
    flatpickr(id, {
        enableTime: false,
        disableMobile: true,
        altInput: true,
        altFormat: "Y-m-d",
        dateFormat: "Y-m-d",
        minDate: new Date().fp_incr(1),
        altInputClass: "form-control text-sm required-field",
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializeDateRangePicker(id, placeholder, minDate = null, maxDate = null) {
    flatpickr(id, {
        mode: "range",
        enableTime: false,
        disableMobile: true,
        altInput: true,
        altFormat: "Y-m-d",
        dateFormat: "Y-m-d",
        minDate: minDate,
        maxDate: maxDate,
        altInputClass: "form-control text-sm required-field",
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializeMonthYearPicker(id, placeholder) {
    flatpickr(id, {
        plugins: [
            new monthSelectPlugin({
                shorthand: false,
                dateFormat: "Y-m",
                altFormat: "F Y"
            })
        ],
        altInput: true,
        disableMobile: true,
        altInputClass: "form-control text-sm required-field",
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializeYearPicker(id, placeholder) {
    flatpickr(id, {
        plugins: [
            new monthSelectPlugin({
                shorthand: false,
                dateFormat: "Y",
                altFormat: "Y"
            })
        ],
        altInput: true,
        disableMobile: true,
        altInputClass: "form-control text-sm required-field",
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializeDateTimePicker(id, placeholder) {
    flatpickr(id, {
        enableTime: true,
        disableMobile: true,
        dateFormat: "Y-m-d H:i",
        altInput: true,
        altFormat: "Y-m-d h:i K",
        altInputClass: "form-control text-sm required-field",
        time_24hr: false,
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializeTimePicker(id, placeholder) {
    flatpickr(id, {
        enableTime: true,
        disableMobile: true,
        noCalendar: true,
        dateFormat: "H:i",
        altInput: true,
        altFormat: "h:i K",
        altInputClass: "form-control text-sm required-field",
        time_24hr: false,
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}

function initializeMultipleDatePicker(id, placeholder) {
    flatpickr(id, {
        mode: "multiple",
        enableTime: false,
        disableMobile: true,
        altInput: true,
        altFormat: "Y-m-d",
        dateFormat: "Y-m-d",
        altInputClass: "form-control text-sm required-field",
        onReady: function (selectedDates, dateStr, instance) {
            instance.altInput.setAttribute("placeholder", placeholder);
        },
        onChange: function (selectedDates, dateStr, instance) {
            if (dateStr) {
                // Remove validation from original input
                $(instance.input).removeClass("is-invalid");

                // Remove validation from visible Flatpickr input
                $(instance.altInput).removeClass("is-invalid");
            }
        },
        position: "auto"
    });
}