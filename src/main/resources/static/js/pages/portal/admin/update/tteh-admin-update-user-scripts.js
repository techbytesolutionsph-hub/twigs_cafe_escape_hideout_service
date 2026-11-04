$(document).ready(function () {

    /* Initialized Image Upload */
    initializeClientPhotoUpload();

    $("#choosePhotoBtn").on("click", function () {
        $("#clientPhoto").click();
    });

    $("#submit-btn").on("click", function (event) {

        event.preventDefault();

        validateFields("update-client-form")

        const $password = $('#user-password');

        if (!$password.val().trim()) {
            $password.addClass('is-invalid');
            $('#password-error').addClass('show');
            return;
        }

        $password.removeClass('is-invalid');
        $('#password-error').removeClass('show');

        const $updateUser = $('#submit-btn');
        const url = $updateUser.attr('href');
        const method = $updateUser.data('method');
        const callback = "/app/portal/admin/user-management";

        let request = buildRequest();
        console.log(request);

        submitRequest(request, url, method, callback);
    });

    function buildRequest(){
        return {
            id: $("#user-id").val(),
            username: $("#user-username").val(),
            password: $("#user-password").val(),
            email: $("#user-email").val(),
            role: $("#user-role").val(),
            status: $("#user-status").val(),
            profileImageUrl: $("#clientPhotoPreview").attr("src") || "",
        }
    }

    $('#back-btn').on('click', function(e) {
        e.preventDefault();

        const url = $(this).attr('href');
        console.log(url);

        showConfirmPopup(
            'Leave this page?',
            'Your unsaved user data will be lost.',
            function() {
                window.location.href = url;
            }
        );
    });
});