$(document).ready(function () {

    /* User Toggle */
    const $userMenuToggle = $("#userMenuToggle");
    const $userDropdown = $("#userDropdown");
    $userMenuToggle.on("click", function(event) {
        event.stopPropagation();
        const isOpen = $userDropdown.hasClass("show");
        $userDropdown.toggleClass("show", !isOpen);
        $userMenuToggle.attr("aria-expanded", String(!isOpen));
    });
    $(document).on("click", function(event) {
        if (!$(event.target).closest(".user-menu").length) {
            $userDropdown.removeClass("show");
            $userMenuToggle.attr("aria-expanded", "false");
        }
    });
    $(document).on("keydown", function(event) {
        if (event.key === "Escape") {
            $userDropdown.removeClass("show");
            $userMenuToggle.attr("aria-expanded", "false");
        }
    });
    $(document).on("click", "[data-user-page]", function(event) {
        event.preventDefault();
        const page = $(this).data("user-page");
        $userDropdown.removeClass("show");
        $userMenuToggle.attr("aria-expanded", "false");
        if (page === "Logout") {
            notify("Logout selected. Connect this action to your logout endpoint.");
        } else {
            notify(page + ": selected.");
        }
    });

    /* Notification Bell */
    const $notificationBell = $('#notificationBell');
    const $notificationDropdown = $('#notificationDropdown');

    $notificationBell.on('click', function (event) {
        event.stopPropagation();

        const isOpening = $notificationDropdown.prop('hidden');

        $notificationDropdown.prop('hidden', !isOpening);
        $notificationBell.attr('aria-expanded', String(isOpening));
    });

    $(document).on('click', function (event) {
        if (!$(event.target).closest('.notification-wrapper').length) {
            $notificationDropdown.prop('hidden', true);
            $notificationBell.attr('aria-expanded', 'false');
        }
    });

    $(document).on('keydown', function (event) {
        if (event.key === 'Escape') {
            $notificationDropdown.prop('hidden', true);
            $notificationBell.attr('aria-expanded', 'false');
        }
    });
});