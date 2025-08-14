
const element = document.getElementById("numbers");
for (let index = 0; index <= 100; index++) {
    const button = document.createElement("button");
    button.value = index;
    button.textContent = index;
    button.className = "number";
    element.appendChild(button);
}
let stompClient = null;
const gameId = /*[[${game.id}]]*/ '1'; // Thymeleaf injects the game ID

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
        // Subscribe to game updates
        stompClient.subscribe(`/topic/game/${gameId}`, (message) => {
            const update = JSON.parse(message.body);
            console.log("Game update:", update);
        });
    });
}

function sendChoice(number) {
    stompClient.send(`/app/game/${gameId}/choose`, {}, JSON.stringify({ number }));
}

document.addEventListener("DOMContentLoaded", () => {
    connect();
    document.querySelectorAll(".number").forEach(button => {
        button.addEventListener("click", () => {
            sendChoice(parseInt(button.value));
        });
    });
});