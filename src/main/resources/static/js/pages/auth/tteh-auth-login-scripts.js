$(document).ready(function () {
    const $passwordInput = $("#password");
    const $togglePassword = $("#togglePassword");

    if ($passwordInput.length && $togglePassword.length) {
        $togglePassword.on("click", function () {

            const isPassword = $passwordInput.attr("type") === "password";
            $passwordInput.attr("type", isPassword ? "text" : "password");

            $togglePassword.html(
                isPassword
                    ? '<i class="bi bi-eye-slash"></i>'
                    : '<i class="bi bi-eye"></i>'
            );

            $togglePassword.attr(
                "aria-label",
                isPassword ? "Hide password" : "Show password"
            );
        });
    }
});