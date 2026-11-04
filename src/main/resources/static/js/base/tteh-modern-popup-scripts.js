function showSuccessPopup(title, message) {

    const icon = {
        src: "https://cdn.lordicon.com/lupuorrc.json",
        colors: "primary:#36765A,secondary:#24543E"
    };

    let popup = $(".modern-popup-overlay");

    if (!popup.length) {
        popup = $("<div class='modern-popup-overlay'></div>");
        $("body").append(popup);
    }

    popup.html(`
        <div class="modern-popup" style="height: auto">
            <div class="modern-popup-icon">
                <lord-icon
                    src="${icon.src}"
                    trigger="loop"
                    colors="${icon.colors}">
                </lord-icon>
            </div>

            <div class="modern-popup-title">${title}</div>
            <div class="modern-popup-message" style="max-height: 300px; overflow-y: auto;">${message}</div>

            <div class="modern-popup-actions">
                <button class="popup-btn popup-btn-ok">
                    OK
                </button>
            </div>
        </div>
    `);

    $(".popup-btn-ok").on("click", function () {
        popup.remove();
    });
}

function showSuccessThenRedirectPopup(title, message, callback) {

    const icon = {
        src: "https://cdn.lordicon.com/lupuorrc.json",
        colors: "primary:#36765A,secondary:#24543E"
    };

    let popup = $(".modern-popup-overlay");

    if (!popup.length) {
        popup = $("<div class='modern-popup-overlay'></div>");
        $("body").append(popup);
    }

    popup.html(`
        <div class="modern-popup" style="height: auto;">
            <div class="modern-popup-icon">
                <lord-icon
                    src="${icon.src}"
                    trigger="loop"
                    colors="${icon.colors}">
                </lord-icon>
            </div>

            <div class="modern-popup-title">${title}</div>
            <div class="modern-popup-message" style="max-height: 300px; overflow-y: auto;">${message}</div>

            <div class="modern-popup-actions">
                <button class="popup-btn popup-btn-ok">
                    OK
                </button>
            </div>
        </div>
    `);

    $(".popup-btn-ok").on("click", function () {
        popup.remove();

        if (typeof callback === "function") {
            callback();
        }
    });
}

function showInfoPopup(title, message) {

    const icon = {
        src: "https://cdn.lordicon.com/hursldrn.json",
        colors: "primary:#24543e,secondary:#8FAF9B"
    };

    let popup = $(".modern-popup-overlay");

    if (!popup.length) {
        popup = $("<div class='modern-popup-overlay'></div>");
        $("body").append(popup);
    }

    popup.html(`
        <div class="modern-popup">
            <div class="modern-popup-icon">
                <lord-icon
                    src="${icon.src}"
                    trigger="loop"
                    colors="${icon.colors}">
                </lord-icon>
            </div>

            <div class="modern-popup-title">${title}</div>
            <div class="modern-popup-message" style="max-height: 300px; overflow-y: hidden;">${message}</div>

            <div class="modern-popup-actions">
                <button class="popup-btn popup-btn-ok">
                    OK
                </button>
            </div>
        </div>
    `);

    $(".popup-btn-ok").on("click", function () {
        popup.remove();
    });
}

function showWarningPopup(title, message) {

    const icon = {
        src: "https://cdn.lordicon.com/tdrtiskw.json",
        colors: "primary:#24543E,secondary:#D6B56D"
    };

    let popup = $(".modern-popup-overlay");

    if (!popup.length) {
        popup = $("<div class='modern-popup-overlay'></div>");
        $("body").append(popup);
    }

    popup.html(`
        <div class="modern-popup" style="height: auto">
            <div class="modern-popup-icon">
                <lord-icon
                    src="${icon.src}"
                    trigger="loop"
                    colors="${icon.colors}">
                </lord-icon>
            </div>

            <div class="modern-popup-title">${title}</div>
            <div class="modern-popup-message" style="max-height: 300px; overflow-y: auto;">${message}</div>

            <div class="modern-popup-actions">
                <button class="popup-btn popup-btn-ok">
                    OK
                </button>
            </div>
        </div>
    `);

    $(".popup-btn-ok").on("click", function () {
        popup.remove();
    });
}

function showErrorPopup(title, message) {

    const icon = {
        src: "https://cdn.lordicon.com/nhfyhmlt.json",
        colors: "primary:#24543E,secondary:#C98282"
    };

    let popup = $(".modern-popup-overlay");

    if (!popup.length) {
        popup = $("<div class='modern-popup-overlay'></div>");
        $("body").append(popup);
    }

    popup.html(`
        <div class="modern-popup" style="height: auto;">
            <div class="modern-popup-icon">
                <lord-icon
                    src="${icon.src}"
                    trigger="loop"
                    colors="${icon.colors}">
                </lord-icon>
            </div>

            <div class="modern-popup-title">${title}</div>
            <div class="modern-popup-message" style="max-height: 300px; overflow-y: auto;">${message}</div>

            <div class="modern-popup-actions">
                <button class="popup-btn popup-btn-ok">
                    OK
                </button>
            </div>
        </div>
    `);

    $(".popup-btn-ok").on("click", function () {
        popup.remove();
    });
}

function showConfirmPopup(title, message, onConfirm = null, ...params) {

    const icon = {
        src: "https://cdn.lordicon.com/kthelypq.json",
        colors: "primary:#24543e,secondary:#8FAF9B"
    };

    let popup = $(".modern-popup-overlay");

    if (!popup.length) {
        popup = $("<div class='modern-popup-overlay'></div>");
        $("body").append(popup);
    }

    popup.html(`
        <div class="modern-popup" style="height: auto;">
            <div class="modern-popup-icon">
                <lord-icon
                    src="${icon.src}"
                    trigger="loop"
                    colors="${icon.colors}">
                </lord-icon>
            </div>

            <div class="modern-popup-title">${title}</div>
            <div class="modern-popup-message" style="max-height: 300px; overflow-y: auto;">${message}</div>

            <div class="modern-popup-actions">
                <button class="popup-btn popup-btn-cancel">Cancel</button>
                <button class="popup-btn popup-btn-confirm">Confirm</button>
            </div>
        </div>
    `);

    $(".popup-btn-cancel").on("click", function () {
        popup.remove();
    });

    $(".popup-btn-confirm").on("click", function () {
        popup.remove();
        popup.remove();

        if (typeof onConfirm === "function") {
            onConfirm(...params);
        }
    });
}


function showPopup(title, message, type, onConfirm = null, ...params) {

    const iconMap = {
        success: {
            src: "https://cdn.lordicon.com/lupuorrc.json",
            colors: "primary:#00979D,secondary:#0F3460"
        },
        info: {
            src: "https://cdn.lordicon.com/hursldrn.json",
            colors: "primary:#00979D,secondary:#B0DCDE"
        },
        confirm: {
            src: "https://cdn.lordicon.com/kthelypq.json",
            colors: "primary:#00979D,secondary:#B0DCDE"
        },
        warning: {
            src: "https://cdn.lordicon.com/tdrtiskw.json",
            colors: "primary:#00979D,secondary:#B0DCDE"
        },
        error: {
            src: "https://cdn.lordicon.com/nhfyhmlt.json",
            colors: "primary:#00979D,secondary:#B0DCDE"
        }
    };

    const icon = iconMap[type] || iconMap.info;

    let popup = document.querySelector(".modern-popup-overlay");

    if (!popup) {
        popup = document.createElement("div");
        popup.className = "modern-popup-overlay";
        document.body.appendChild(popup);
    }

    popup.innerHTML = `
        <div class="modern-popup" style="height: auto;">
            <div class="modern-popup-icon">
                <lord-icon
                    src="${icon.src}"
                    trigger="loop"
                    colors="${icon.colors}">
                </lord-icon>
            </div>

            <div class="modern-popup-title">${title}</div>
            <div class="modern-popup-message" style="max-height: 300px; overflow-y: auto;">${message}</div>

            <div class="modern-popup-actions">
                ${
                type === "confirm"
                    ? `
                        <button class="popup-btn popup-btn-cancel">Cancel</button>
                        <button class="popup-btn popup-btn-confirm">Confirm</button>
                    `
                    : `
                        <button class="popup-btn popup-btn-ok">
                            OK
                        </button>
                    `
    }
            </div>
        </div>
    `;

    const cancelBtn = popup.querySelector(".popup-btn-cancel");
    const confirmBtn = popup.querySelector(".popup-btn-confirm");
    const okBtn = popup.querySelector(".popup-btn-ok");

    cancelBtn?.addEventListener("click", () => popup.remove());

    confirmBtn?.addEventListener("click", () => {
        popup.remove();
        popup.remove();
        if (typeof onConfirm === "function") {
            onConfirm(...params);
        }
    });

    okBtn?.addEventListener("click", () => popup.remove());
}