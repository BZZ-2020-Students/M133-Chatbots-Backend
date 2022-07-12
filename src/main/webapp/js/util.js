const baseUrl = 'http://localhost:8080/chatbots-1.4.0/api';

let user = null;

document.addEventListener("DOMContentLoaded", () => {
    fetch("".concat(baseUrl, "/auth/auth-check"))
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Failed to get user");
            }
        }).then(data => {
        user = data;
        console.log(user);

    }).catch(error => {
        alert(error.message);
    });

    let nav = document.getElementById("nav");

    if (nav) {
        // get current page
        let url = new URL(window.location.href);
        let page = url.pathname.substring(url.pathname.lastIndexOf("/") + 1);
        url.pathname = url.pathname.substring(0, url.pathname.lastIndexOf("/"));
        let index = (page === "index.html");
        if (!index) {
            url.pathname = url.pathname.substring(0, url.pathname.lastIndexOf("/"));
        }

        // add navigation links
        let navLinks = document.createElement("div");
        navLinks.className = "nav-links";
        nav.appendChild(navLinks);

        // home
        let navLink = document.createElement("a");
        navLink.href = (index) ? "index.html" : "../index.html";
        navLink.innerHTML = "Home";
        navLinks.appendChild(navLink);

        // login
        if (user === null) {
            navLink = document.createElement("a");
            navLink.href = (index) ? "pages/login.html" : "login.html";
            navLink.innerHTML = "Login";
            navLinks.appendChild(navLink);
        } else {
            navLink = document.createElement("a");
            navLink.onclick = () => {
                user = null;
                console.log("Logged out");
            }
            navLink.innerHTML = "Logout";
            navLinks.appendChild(navLink);
        }
    }
});