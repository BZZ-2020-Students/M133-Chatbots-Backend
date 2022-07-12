document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("signupForm").addEventListener("submit", login);
});

function login(event) {
    event.preventDefault();
    const signupForm = document.getElementById("signupForm");
    const formData = new FormData(signupForm);
    const data = new URLSearchParams(formData);
    console.log(baseUrl);
    fetch("".concat(baseUrl, "/user/create"), {
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
        window.location.href = "login.html";
    }).catch(function (error) {
        alert(error.message);
    });
}