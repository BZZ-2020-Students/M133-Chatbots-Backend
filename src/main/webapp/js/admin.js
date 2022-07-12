document.addEventListener("DOMContentLoaded", () => {
    setupPage();
});

function setupPage() {
    const chatbotCollection = document.getElementById("chatbotCollection");

    fetch("".concat(baseUrl, "/chatbot/list"))
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Failed to get chatbots");
            }
        }).then(data => {
        data.forEach(chatbot => {
                let chatbotElement = document.createElement("div");
                chatbotElement.className = "chatbotContainer";
                chatbotElement.innerHTML = `
                    <h2>${chatbot.chatbotName}</h2>
                    <p>${chatbot.user.username}</p>
                    <div class="chatbot-footer">
                        <button onclick="loadChatbot('${chatbot.id}')">Start chatting</button>
                        <button onclick="deleteChatbot('${chatbot.id}')">Delete</button>
                        <div id="updateChatbot-${chatbot.id}">
                            <label for="chatbotName">Edit Chatbot Name</label>
                            <input type="text" id="chatbotName-${chatbot.id}" name="name" value="${chatbot.chatbotName}" ">
                            <button type="button" onclick="editChatbot('${chatbot.id}')">Update</button>
                        </div>
                    </div>
                `;
                chatbotCollection.appendChild(chatbotElement);
            }
        );
    }).catch(error => {
        alert(error.message);
    });

    const userCollection = document.getElementById("usersContainer");
    fetch("".concat(baseUrl, "/user/list"))
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Failed to get users");
            }
        })
        .then(data => {
            data.forEach(user => {
                let userElement = document.createElement("div");
                userElement.className = "userContainer";
                userElement.innerHTML = `
                    <h2>${user.username}</h2>
                    <div class="user-footer">
                        <button onclick="deleteUser('${user.id}')">Delete</button>
                    </div>
                `;
                userCollection.appendChild(userElement);
            });
        })
        .catch(error => {
            alert(error.message);
        });
}

function deleteUser(userId) {
    if(confirm("Are you sure you want to delete this user?")) {
        fetch("".concat(baseUrl, "/user/delete/", userId), {
            method: "DELETE",
        }).then(response => {
            if (response.ok) {
                alert("User deleted");
                window.location.reload();
            } else {
                throw new Error("Failed to delete user");
            }
        }).catch(error => {
            alert(error.message);
        });
    }
}

function editChatbot(chatbotId) {
    const inputField = document.getElementById("chatbotName-" + chatbotId);
    const chatbotName = inputField.value;
    const data = new URLSearchParams();
    data.append("name", chatbotName);

    fetch("".concat(baseUrl, "/chatbot/update/", chatbotId), {
        method: "PUT",
        body: data
    }).then(response => {
        if (response.ok) {
            window.location.reload();
        } else {
            throw new Error("Failed to update chatbot");
        }
    }).catch(error => {
        alert(error.message);
    });
}

function loadChatbot(chatbotId) {
    const url = new URL((window.location.href.replace("profile.html", "")).concat("chatbot.html"));
    url.searchParams.set("chatbotId", chatbotId);
    url.searchParams
    window.location.href = url.href;
}

function deleteChatbot(chatbotId) {
    if (confirm("Are you sure you want to delete the Chatbot?")) {
        fetch("".concat(baseUrl, "/chatbot/delete/", chatbotId), {
            method: "DELETE",
        }).then(response => {
            if (response.ok) {
                alert("Chatbot deleted");
                window.location.reload();
            } else {
                throw new Error("Failed to delete chatbot");
            }
        }).catch(error => {
            alert(error.message);
        });
    }
}