let originalBtnMsg = null;

function validateFields(id) {
    const form = document.getElementById(id);

    if (!form) return false;

    form.classList.add('was-validated');

    return form.checkValidity();
}

function submitRequest(request, url, method, callback){
    $.ajax({
        url: url,
        type: method,
        contentType: "application/json",
        data: JSON.stringify(request),
        beforeSend: function () {
            const btnMsg = method === 'POST' ? 'Creating...' : 'Updating...';
            originalBtnMsg = $('#submit-btn').text().trim();

            $("#submit-btn")
                .prop("disabled", true)
                .html(`<span class="spinner-border spinner-border-sm me-2"></span> ${btnMsg}`);
        },
        success: function (response) {
            showSuccessThenRedirectPopup(
                "Success",
                response.returnMessage,
                () => {
                    window.location.href = callback;
                }
            );
        },
        error: function (xhr) {
            let message = xhr.responseJSON.message;
            if (xhr.responseJSON && xhr.responseJSON.message) {
                message = xhr.responseJSON.message;
            }
            showErrorPopup("Error", message);
        },
        complete: function () {
            setTimeout(function () {
                if(method === "POST"){
                    $("#submit-btn")
                        .prop("disabled", false)
                        .html(`<i class="fa-solid fa-user-plus mr-2"></i>${originalBtnMsg}`);
                }else{
                    $("#submit-btn")
                        .prop("disabled", false)
                        .html(`<i class="bi bi-pencil-square me-2"></i>${originalBtnMsg}`);
                }
            }, 1500);
        }
    });
}