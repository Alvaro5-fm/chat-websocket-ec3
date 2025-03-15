$(document).ready(function(){
    var usernamePage = $("#username-page");
    var chatPage = $("#chat-page");
    var usernameForm = $("#usernameForm");
    var messageForm = $("#messageForm");
    var messageArea = $("#messageArea");
    var connectingElement = $(".connecting");
    var stompClient = null;
    var username = null;

    function conectarUsuario(event){
        username = $("#name").val().trim();
        if(username){
            usernamePage.addClass("d-none");
            chatPage.removeClass("d-none");
            var socket = new SockJS("/notificaciones");
            stompClient = Stomp.over(socket);
            stompClient.connect({}, onConnected, onError)
        }
        event.preventDefault();
    }

    function onConnected(){
        stompClient.subscribe("/topic/public", onMessageReceived);
        stompClient.send("/app/chat.addUser", {}, JSON.stringify({
            envio: username,
            tipo: 'UNIR'
        }));
        connectingElement.addClass("d-none");
    }

    function onError(){
        connectingElement.text("No se pudo establecer conexión con el servidor WebSocket");
        connectingElement.css('color', 'red');
    }

    function onMessageReceived(payload){
        var message = JSON.parse(payload.body);
        var messageElement = $("<li>").addClass("list-group-item mb-2 rounded");

        if(message.tipo === 'UNIR'){
            messageElement.addClass("event-message text-center text-success fw-semibold").text("🔵 " + message.envio + " se ha unido al chat");
        } else if (message.tipo === 'DEJAR'){
            messageElement.addClass("event-message text-center text-danger fw-semibold").text("🔴 " + message.envio + " ha salido del chat");
        } else {
            messageElement.addClass("message d-flex flex-column");

            var messageContent = $("<div>").addClass("message-content");

            var usernameElement = $("<span>").addClass("fw-bold text-primary").text(message.envio);
            var textElement = $("<span>").text(" " + message.contenido);
            var timeElement = $("<div>").addClass("text-muted small mt-1 text-end").text(message.hora);

            messageContent.append(usernameElement).append(textElement);
            messageElement.append(messageContent).append(timeElement);

            // ✅ Estilizar mensaje si lo envió el usuario actual
            if(message.envio === username){
                messageElement.css({
                    "background-color": "#dcf8c6",
                    "align-self": "flex-end",
                    "text-align": "right"
                });
            } else {
                messageElement.css("background-color", "#ffffff");
            }
        }

        messageArea.append(messageElement);
        messageArea.scrollTop(messageArea[0].scrollHeight);
    }

    function enviarMensaje(event){
        var messageContent = $("#message").val().trim();
        if(messageContent && stompClient){
            var chatMessage = {
                envio: username,
                contenido: messageContent,
                tipo: "CHAT"
            };
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            $("#message").val("");
        }
        event.preventDefault();
    }

    usernameForm.on("submit", conectarUsuario);
    messageForm.on("submit", enviarMensaje);
});
