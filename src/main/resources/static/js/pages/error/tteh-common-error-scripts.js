$(document).ready(function() {
    $("#back-to-home-btn").on("click", function(e) {
        e.preventDefault();

        $.ajax({
            url: "/api/v1/auth/portal/logout",
            type: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            success: function() {
                console.log("Logged out successfully");
            },
            error: function() {
                console.error("Logout failed");
            },
            complete: function() {
                window.location.href = "/app/portal/login";
            }
        });
    });
});