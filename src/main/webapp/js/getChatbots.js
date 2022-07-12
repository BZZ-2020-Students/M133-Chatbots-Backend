document.addEventListener("DOMContentLoaded", () => {
    let chatbotCollection = document.getElementById("chatbotCollection");

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
                    <button onclick="loadChatbot('${chatbot.id}')">Start chatting</button>
                `;
                chatbotCollection.appendChild(chatbotElement);
            }
        );
    }).catch(error => {
        alert(error.message);
    });
});

function loadChatbot(chatbotId) {
    const url = new URL((window.location.href.replace("index.html", "")).concat("pages/chatbot.html"));
    url.searchParams.set("chatbotId", chatbotId);
    url.searchParams
    window.location.href = url.href;
}