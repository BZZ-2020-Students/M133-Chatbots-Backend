document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("createChatbotForm").addEventListener("submit", createChatbot);

    setUpPage();
});

function createChatbot(event) {
    event.preventDefault();

    const createChatbotForm = document.getElementById("createChatbotForm");
    const formData = new FormData(createChatbotForm);
    const data = new URLSearchParams(formData);

    fetch("".concat(baseUrl, "/chatbot/create"), {
        method: "POST",
        body: data
    }).then(response => {
        if (response.ok) {
            window.location.reload();
        } else {
            throw new Error("Failed to create chatbot");
        }
    }).catch(error => {
        alert(error.message);
    });

}

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
                        <div id="updateChatbot-${chatbot.id}">
                            <label for="chatbotName">Edit Chatbot Name</label>
                            <input type="text" id="chatbotName-${chatbot.id}" name="name" value="${chatbot.chatbotName}" ">
                            <button type="button" onclick="editChatbot('${chatbot.id}')">Update</button>
                        </div>
                    </div>
                `;
                chatbotCollection.appendChild(chatbotElement);
            });
        })
        .catch(error => {
            alert(error.message);
        });

    fetch("".concat(baseUrl, "/rating/user"))
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Failed to get user");
            }
        })
        .then(data => {
            const ratingCollection = document.getElementById("ratingCollection");
            data.forEach(rating => {
                const firstOption = (rating.rating === "UPVOTE") ? {
                    value: "UPVOTE",
                    text: "Upvote"
                } : {
                    value: "DOWNVOTE",
                    text: "Downvote"
                };

                const secondOption = (rating.rating === "UPVOTE") ? {
                    value: "DOWNVOTE",
                    text: "Downvote"
                } : {
                    value: "UPVOTE",
                    text: "Upvote"
                };

                let checkbox;
                if (rating.favourite) {
                    checkbox = `<input type="checkbox" id="chatbotFavourited-${rating.chatbot.id}" checked>`;
                } else {
                    checkbox = `<input type="checkbox" id="chatbotFavourited-${rating.chatbot.id}">`;
                }

                const ratingElement = document.createElement("div");
                ratingElement.classList.add("ratingContainer");
                ratingElement.innerHTML = `
                    <div class="rating-header">
                        <h3>Rated chatbot: ${rating.chatbot.chatbotName}</h3>
                    </div>
                    <div class="rating-footer">
                        <p>Is it a favourite: 
                            ${checkbox}
                        </p>
                        <p>The rating: 
                            <select id="chatbotRating-${rating.chatbot.id}">
                                <option value="${firstOption.value}">${firstOption.text}</option>
                                <option value="${secondOption.value}">${secondOption.text}</option>
                            </select>
                        </p>
                        <button onclick="rateChatbot('${rating.chatbot.id}','${rating.id}')">Update rating</button>
                        <button onclick="deleteRating('${rating.id}')">Delete rating</button>
                    </div>
                `;
                ratingCollection.appendChild(ratingElement);
            });
        })
        .catch(error => {
            alert(error.message);
        });
}

function deleteRating(ratingId) {
    fetch("".concat(baseUrl, "/rating/delete/", ratingId), {
        method: "DELETE"
    }).then(response => {
        if (response.ok) {
            window.location.reload();
        } else {
            throw new Error("Failed to delete rating");
        }
    }).catch(error => {
        alert(error.message);
    });
}

function rateChatbot(chatbotId, rateId) {
    const data = new URLSearchParams();
    const favouriteField = document.getElementById("chatbotFavourited-" + chatbotId);
    const favourited = favouriteField.checked;

    const ratingField = document.getElementById("chatbotRating-" + chatbotId);
    const rating = ratingField.value;

    data.append("chatbotId", chatbotId);
    data.append("favourite", favourited);
    data.append("rating", rating);

    fetch("".concat(baseUrl, "/rating/update/", rateId), {
        method: "PUT",
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