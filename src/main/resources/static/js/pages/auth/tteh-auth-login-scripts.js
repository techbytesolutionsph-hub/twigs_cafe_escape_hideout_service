$(document).ready(function () {
    const errorDiv = $("#login-error");
    const $passwordInput = $("#staff-password");
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

    $("form").on("submit", function (e) {
        if (!this.checkValidity()) {
            e.preventDefault();
            this.reportValidity();
            return;
        }

        e.preventDefault();

        login();
    });

    function login(){
        const request = buildLoginRequest();
        submitSellerLogin(request, errorDiv);
    }

    function buildLoginRequest() {
        return {
            username: $('#staff-username').val(),
            password: $('#staff-password').val()
        };
    }

    function submitSellerLogin(loginRequest, errorDiv) {
        errorDiv.text("").hide();

        setLoginLoading(true);

        fetch("/api/v1/auth/portal/login", {
            method: "POST",
            headers: {
                "Content-Type":"application/json"
            },
            body: JSON.stringify(loginRequest),
            credentials:"include"
        })
            .then(response => {
                if(!response.ok){
                    return response.json().then(data=>{
                        return Promise.reject(data?.message || "Login failed");
                    });
                }
                return response.json();
            })
            .then(data => {
                window.location.href = "/app/portal/dashboard";
            })
            .catch(error => {
                setLoginLoading(false);
                errorDiv
                    .text(typeof error === "string" ? error : error.message || "Login failed")
                    .show();
            });
    }

    function setLoginLoading(isLoading){

        const btn = $("#login-btn");
        const spinner = $("#login-spinner");
        const text = $("#login-btn-text");

        if(isLoading){
            btn.prop("disabled", true);

            spinner.show();
            text.text("Signing in...");
        }else{
            btn.prop("disabled", false);

            spinner.hide();
            text.text("Login");
        }
    }
});