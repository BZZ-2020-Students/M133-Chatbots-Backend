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
        if (!data.error) {
            user = {
                id: data.id,
                username: data.username,
                role: data.userRole,
            }
        }
    }).then(() => {
        setNav();
    }).catch(error => {
        alert(error.message);
    });
});

function setNav() {
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

        const loggedIn = user !== null;
        if (!loggedIn) {
            navLink = document.createElement("a");
            navLink.href = (index) ? "pages/login.html" : "login.html";
            navLink.innerHTML = "Login";
            navLinks.appendChild(navLink);

            navLink = document.createElement("a");
            navLink.href = (index) ? "pages/register.html" : "register.html";
            navLink.innerHTML = "Register";
            navLinks.appendChild(navLink);
        } else {
            navLink = document.createElement("a");
            navLink.href = (index) ? "pages/profile.html" : "profile.html";
            navLink.innerHTML = "Profile";
            navLinks.appendChild(navLink);

            if (user.role === "ADMIN") {
                navLink = document.createElement("a");
                navLink.href = (index) ? "pages/admin.html" : "admin.html";
                navLink.innerHTML = "Admin";
                navLinks.appendChild(navLink);
            }

            navLink = document.createElement("a");
            navLink.href = (index) ? "pages/login.html" : "login.html";
            navLink.onclick = () => {
                user = null;
                fetch("".concat(baseUrl, "/auth/logout"))
                    .then(response => {
                        if (response.ok) {
                            window.location.href = (index) ? "pages/login.html" : "login.html";
                        } else {
                            throw new Error("Failed to logout");
                        }
                    })
                    .catch(error => {
                        alert(error.message);
                    });
            }
            navLink.innerHTML = "Logout";
            navLinks.appendChild(navLink);
        }
    }
}