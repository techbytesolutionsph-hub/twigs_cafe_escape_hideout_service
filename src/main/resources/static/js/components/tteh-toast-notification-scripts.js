function showToast(type, title, message) {

    const container = document.getElementById("otp-toast-error");
    const toast = document.createElement("div");
    toast.classList.add("custom-toast", type);

    let icon = "";

    switch(type) {

        case "success":
            icon = "✔";
            break;

        case "error":
            icon = "✖";
            break;

        case "info":
            icon = "i";
            break;

        case "warning":
            icon = "!";
            break;
    }

    toast.innerHTML = `
        <div class="toast-icon">${icon}</div>
        <div class="toast-content">
            <div class="toast-title">${title}</div>
            <div class="toast-message">${message}</div>
        </div>
        <div class="toast-close">&times;</div>
    `;

    container.appendChild(toast);

    /* AUTO REMOVE */
    const autoRemove = setTimeout(() => {
        removeToast(toast);
    }, 4000);

    /* CLOSE BUTTON */
    toast.querySelector(".toast-close")
        .addEventListener("click", () => {
            clearTimeout(autoRemove);
            removeToast(toast);
        });
}

function removeToast(toast) {
    toast.classList.add("hide");
    toast.addEventListener("animationend", () => {
        toast.remove();
    });
}