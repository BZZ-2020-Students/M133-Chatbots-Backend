document.addEventListener("DOMContentLoaded", () => {
    setUpPage();
});

function setUpPage() {
    const chatbotCollection = document.getElementById("chatbotCollection");
    fetch("".concat(baseUrl, "/chatbot/allFromUser"))
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Failed to get user");
            }
        })
        .then(data => {
            data.forEach(chatbot => {
                const chatbotElement = document.createElement("div");
                chatbotElement.classList.add("chatbotContainer");
                chatbotElement.innerHTML = `
                    <div class="chatbot-header">
                        <h3>${chatbot.chatbotName}</h3>
                    </div>
                    <div class="chatbot-footer">
                        <button onclick="loadChatbot('${chatbot.id}')">Start chatting</button>
                        <button onclick="deleteChatbot('${chatbot.id}')">Delete</button>
                    </div>
                `;
                chatbotCollection.appendChild(chatbotElement);
            });
        })
        .catch(error => {
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