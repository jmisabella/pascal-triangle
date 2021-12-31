var webSocket;
var messageInput;

function init() {
    // webSocket = new WebSocket("ws://localhost:9000/ws");
    var host = location.origin.replace(/^https/, 'wss').replace(/^http/, 'ws'); 
    webSocket = new WebSocket(`${host}/ws`);

    webSocket.onopen = onOpen;
    webSocket.onclose = onClose;
    webSocket.onmessage = onMessage;
    webSocket.onerror = onError;
    $("#message-input").focus();
}

function onOpen(event) {
    consoleLog("CONNECTED");
}

function onClose(event) {
    consoleLog("DISCONNECTED");
    appendClientMessageToView(":", "DISCONNECTED");
}

function onError(event) {
    consoleLog("ERROR: " + event.data);
    consoleLog("ERROR: " + JSON.stringify(event));
}

function onMessage(event) {
    console.log(event.data);
    let receivedData = JSON.parse(event.data);
    console.log("New Data: ", receivedData);
    // get the text from the "body" field of the json we
    // receive from the server.
    appendServerMessageToView("Server", receivedData.body);
}

function appendClientMessageToView(title, message) {
    $("#message-content").append("<span>" + title + ": " + message + "<br /></span>");
}

function appendServerMessageToView(title, message) {
    $("#message-content").append("<span>" + title + ": " + message + "<br /><br /></span>");
}

function consoleLog(message) {
    console.log("New message: ", message);
}

window.addEventListener("load", init, false);

$("#send-button").click(function (e) {
    console.log("Sending ...");
    getMessageAndSendToServer();
    // put focus back in the textarea
    $("#message-input").focus();
});

// send the message when the user presses the <enter> key while in the textarea
$(window).on("keydown", function (e) {
    if (e.which == 13) {
        getMessageAndSendToServer();
        return false;
    }
});

// there’s a lot going on here:
// 1. get our message from the textarea.
// 2. append that message to our view/div.
// 3. create a json version of the message.
// 4. send the message to the server.
function getMessageAndSendToServer() {

    // get the text from the textarea
    messageInput = $("#message-input").val();

    // clear the textarea
    $("#message-input").val("");

    // if the trimmed message was blank, return now
    if ($.trim(messageInput) == "") {
        return false;
    }

    // add the message to the view/div
    appendClientMessageToView("Me", messageInput);

    // create the message as json
    let jsonMessage = {
        message: messageInput
    };

    // send our json message to the server
    sendToServer(jsonMessage);
}

// send the data to the server using the WebSocket
function sendToServer(jsonMessage) {
    if(webSocket.readyState == WebSocket.OPEN) {
        consoleLog("SENT: " + jsonMessage.message);
        webSocket.send(JSON.stringify(jsonMessage));
    } else {
        consoleLog("Could not send data. Websocket is not open.");
    }
}

