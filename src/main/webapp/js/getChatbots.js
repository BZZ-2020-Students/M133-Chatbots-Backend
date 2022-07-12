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
                    <div id="createRating-${chatbot.id}">
                        <h3>Rate this chatbot</h3>
                        <div id="rateChatbot-${chatbot.id}">
                            <label for="chatbotFavourited-${chatbot.id}">Favourite</label>
                            <input type="checkbox" id="chatbotFavourited-${chatbot.id}" name="name">
                            <select id="chatbotRating-${chatbot.id}">
                                <option value="UPVOTE">Upvote</option>
                                <option value="DOWNVOTE">Downvote</option>
                            </select>
                            <button type="button" onclick="rateChatbot('${chatbot.id}')">Rate</button>
                        </div>
                    </div>
                `;
                chatbotCollection.appendChild(chatbotElement);
            }
        );
    }).catch(error => {
        alert(error.message);
    });
});

function rateChatbot(chatbotId) {
    const data = new URLSearchParams();
    const favouriteField = document.getElementById("chatbotFavourited-" + chatbotId);
    const favourited = favouriteField.checked;

    const ratingField = document.getElementById("chatbotRating-" + chatbotId);
    const rating = ratingField.value;

    data.append("chatbotId", chatbotId);
    data.append("favourite", favourited);
    data.append("rating", rating);

    fetch("".concat(baseUrl, "/rating/create"), {
        method: "POST",
        body: data
    }).then(response => {
        if (response.ok) {
            window.location.reload();
        } else {
            throw new Error("Failed to rate chatbot");
        }
    }).catch(error => {
        alert(error.message);
    });
}

function loadChatbot(chatbotId) {
    const url = new URL((window.location.href.replace("index.html", "")).concat("pages/chatbot.html"));
    url.searchParams.set("chatbotId", chatbotId);
    url.searchParams
    window.location.href = url.href;
}