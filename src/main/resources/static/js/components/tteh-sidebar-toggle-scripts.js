$(document).ready(function () {
    let toastTimer;

    function notify(message) {
        const $toast = $("#toast");
        $toast.text(message).addClass("show");
        clearTimeout(toastTimer);
        toastTimer = setTimeout(function() {
            $toast.removeClass("show");
        }, 2600);
    }

    window.notify = notify;

    $(document).on("click", ".nav a.nav-toggle", function(event) {
        event.preventDefault();
        const $toggle = $(this);
        const submenuId = $toggle.attr("aria-controls");
        if (!submenuId) return;
        const $submenu = $("#" + submenuId);
        if (!$submenu.length) return;
        const isOpen = $submenu.hasClass("open");
        $submenu.toggleClass("open", !isOpen);
        $toggle.attr("aria-expanded", String(!isOpen)).toggleClass("collapsed", isOpen);

        if (!isOpen) {
            $(".nav .submenu").not($submenu).removeClass("open");
            $(".nav a.nav-toggle").not($toggle).attr("aria-expanded", "false").addClass("collapsed");
        }
    });

    $(document).on("click", ".nav a[data-page]", function(event) {
        const $link = $(this);
        const href = $link.attr("href");

        $(".nav a.nav-link, .nav a.submenu-link").removeClass("active");
        $link.addClass("active");

        if ($(window).width() <= 760) {
            closeSidebar();
        }

        if (href && href !== "#") {
            window.location.href = href;
        }
    });

    $(document).on("click", "[data-action]", function() {
        const action = $(this).data("action");
        notify(action + ": demo action — connect this to your application route/API.");
    });

    const $sidebar = $("#sidebar");
    const $overlay = $("#overlay");

    function closeSidebar() {
        $sidebar.removeClass("open");
        $overlay.removeClass("show");
    }

    window.closeSidebar = closeSidebar;

    $("#menuToggle").on("click", function() {
        $sidebar.toggleClass("open");
        $overlay.toggleClass("show");
    });

    $overlay.on("click", function() {
        closeSidebar();
    });

    const $app = $(".app");
    const $menuToggle = $("#menuToggle");

    if (!$app.length || !$menuToggle.length) return;

    $menuToggle.on("click", function () {

        if ($(window).width() >= 769) {
            $app.toggleClass("sidebar-collapsed");
        }

    });
});