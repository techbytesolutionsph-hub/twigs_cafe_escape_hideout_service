$(document).ready(function () {

    $(document).on("click", "#sign-out-btn", function (e) {
        e.preventDefault();
        staffLogoutAndRedirect();
    });
});

/* Common Sweet Loader */
function showLogoutLoader() {
    Swal.fire({
        title: 'Signing out',
        html: '<span style="font-size:14px; color:#555;">Please wait a moment</span>',
        background: '#ffffff',
        color: '#333',
        allowOutsideClick: false,
        allowEscapeKey: false,
        showConfirmButton: false,
        customClass: { popup: 'custom-logout-popup' },
        didOpen: () => Swal.showLoading()
    });
}

/* Staff Logout */
function staffLogoutAndRedirect() {

    showLogoutLoader();

    setTimeout(function () {
        fetch("/api/v1/auth/portal/logout", {
            method: "POST",
            credentials: "include"
        })
            .then(res => {
                if (!res.ok) console.error("Logout failed", res.statusText);
                else console.log("Logged out successfully");
            })
            .catch(err => console.error("Error during logout:", err))
            .finally(() => {
                window.location.replace("/app/portal/login");
            });
    }, 2000);
}