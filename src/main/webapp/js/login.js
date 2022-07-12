document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("loginForm").addEventListener("submit", login);
});

function login(event) {
    event.preventDefault();

    let valid = false;
    while (!valid) {
        const randomNumber = Math.floor(Math.random() * 100) + 1;
        const input = prompt("Enter the following number: " + randomNumber);
        valid = +input === randomNumber;
    }

    if (valid) {
        const loginForm = document.getElementById("loginForm");
        const formData = new FormData(loginForm);
        const data = new URLSearchParams(formData);
        console.log(baseUrl);
        fetch("".concat(baseUrl, "/auth/login"), {
            method: "POST",
            body: data
        }).then(function (response) {
            if (response.ok) {
                console.log(response)
                return response.json();
            } else {
                throw new Error("Login failed");
            }
        }).then(function (data) {
            user = {
                id: data.id,
                username: data.username,
                role: data.userRole,
            }

            window.location.href = "profile.html";
        }).catch(function (error) {
            alert(error.message);
        });
    }
}