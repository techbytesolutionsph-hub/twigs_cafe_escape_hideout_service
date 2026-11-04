$(document).ready(function () {
    $('#viewUserModal').on('show.bs.modal', function (event) {
        const button = event.relatedTarget;

        if (!button) {
            console.error('Modal trigger button not found.');
            return;
        }

        const username = button.getAttribute('data-username');
        console.log('Selected Username:', username);
        getUserByUsername(username);
    });

    $(document).on('click', '.delete-user-btn', function () {
        const username = $(this).data('username');

        console.log('Selected Username:', username);

        showConfirmPopup(
            "Disable User Account?",
            "This user will no longer be able to sign in.",
            function () {
                softDeleteUser(username);
            }
        );
    });

    function getUserByUsername(username) {
        $.ajax({
            url: `/api/v1/account/admin/get-user/${encodeURIComponent(username)}`,
            type: 'GET',
            dataType: 'json',
            success: function (response) {

                console.log('User Account:', response);

                // Profile
                $('#viewUserPhoto').attr('src', response.profileImageUrl || '/images/default-profile.png');
                $('#viewUsername').text(response.username || '-');
                $('#viewUserEmail').text(response.email || '-');

                const role = (response.role || 'UNASSIGNED')
                    .replaceAll('_', ' ')
                    .toLowerCase()
                    .replace(/\b\w/g, char => char.toUpperCase());
                $('#viewUserRoleDetails').text(role);

                const statusBadge = $('#viewUserStatus');
                const accountStatus = (response.status || 'UNKNOWN').toUpperCase();
                statusBadge.text(accountStatus);
                statusBadge.removeClass('status-active status-inactive status-pending status-suspended status-disabled');

                switch (accountStatus) {
                    case 'ACTIVE':
                        statusBadge.addClass('status-active');
                        break;

                    case 'INACTIVE':
                        statusBadge.addClass('status-inactive');
                        break;

                    case 'SUSPENDED':
                        statusBadge.addClass('status-pending');
                        break;

                    case 'DISABLED':
                        statusBadge.addClass('status-inactive');
                        break;
                }
            },

            error: function (xhr) {
                let message = xhr.responseJSON.message;
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    message = xhr.responseJSON.message;
                }
                showErrorPopup("Error", message);
            }
        });
    }

    function softDeleteUser(username) {

        $.ajax({
            url: "/api/v1/account/admin/delete/" + encodeURIComponent(username),
            type: "DELETE",

            success: function (response) {
                showSuccessThenRedirectPopup(
                    "Success",
                    response.returnMessage,
                    function () {
                        window.location.href = "/app/portal/admin/user-management";
                    }
                );
            },

            error: function (xhr) {
                let message = "Unable to disable the user account.";
                if (xhr.responseJSON) {
                    message = xhr.responseJSON.returnMessage
                        || xhr.responseJSON.message
                        || message;
                }
                showErrorPopup("Error", message);
            }
        });
    }
});