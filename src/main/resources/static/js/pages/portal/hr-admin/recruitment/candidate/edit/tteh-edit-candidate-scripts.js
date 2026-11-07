$(document).ready(function () {
    initializeDatePicker("#candidate-applied-date", "mm/dd/yyyy");

    initializeDocumentUpload();

    $("#update-candidate-btn").on("click", function () {

        if (!validateForm()) {
            return;
        }

        const request = buildApplicantRequest();

        console.log(request);
        updateCandidate(request);
    });

    function buildApplicantRequest() {
        return {
            id: $('#applicantId').val(),
            firstName: $("#candidate-firstname").val().trim(),
            middleName: $("#candidate-middlename").val().trim(),
            lastName: $("#candidate-lastname").val().trim(),
            suffix: $("#candidate-suffix").val().trim(),
            email: $("#candidate-email").val().trim(),
            phoneNumber: $("#candidate-phone").val().trim(),
            appliedJob: $("#candidate-applied-job").val(),
            location: $("#candidate-location").val().trim(),
            experience: $("#candidate-experience").val()
                ? Number($("#candidate-experience").val())
                : null,
            appliedDate: $("#candidate-applied-date").val() || null,
            appliedStage: "APPLIED",
            resumeLink: uploadRequest?.secure_url
                ?? ($('#existingResumeLink').val() || null),
            resumeFilename: uploadRequest?.original_filename
                ?? ($('#existingResumeFilename').val() || null),
            notes: $("#candidate-notes").val().trim()
        };
    }

    $("#back-client-btn").on("click", function (e) {
        e.preventDefault();
        showConfirmPopup(
            "Leave this page?",
            "Your unsaved candidate data will be lost.",
            () => window.location.href = $(this).data("url")
        );
    });

    function validateForm() {
        const form = $("#edit-candidate-form")[0];
        let valid = form.checkValidity();

        const dateInput = $("#candidate-applied-date")[0];
        const date = dateInput.value.trim();
        const match = date.match(/^(\d{4})-(\d{2})-(\d{2})$/);

        let validDate = false;

        if (match) {
            const [, yyyy, mm, dd] = match;
            const parsedDate = new Date(+yyyy, +mm - 1, +dd);

            validDate = parsedDate.getFullYear() === +yyyy
                && parsedDate.getMonth() === +mm - 1
                && parsedDate.getDate() === +dd;
        }

        dateInput.setCustomValidity(!date ? "Required" : validDate
            ? "" : "Please select a valid applied date."
        );

        $(dateInput).next(".form-control")
            .toggleClass("is-invalid", !validDate);

        if (!validDate) {
            valid = false;
        }

        form.classList.add("was-validated");

        if (!valid) {
            form.reportValidity();
            return false;
        }

        return true;
    }

    function updateCandidate(request) {

        $.ajax({
            url: "/api/v1/recruitment/update-candidate",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(request),

            success: function (response) {
                console.log("Candidate updated successfully.", response);
                showSuccessThenRedirectPopup(
                    "Success",
                    response.returnMessage,
                    () => {
                        window.location.href = "/app/portal/hr-recruitment/candidates";
                    }
                );
            },

            error: function (xhr) {
                console.error("Failed to update candidate.", xhr.responseJSON);
                let message = "Unable to update candidate.";

                if (xhr.responseJSON && xhr.responseJSON.message) {
                    message = xhr.responseJSON.message;
                }

                showErrorPopup("Error", message);
            }
        });
    }
});