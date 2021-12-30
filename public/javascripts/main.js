var webSocket;
var messageInput;

$(document).on('mousemove', function(e){
  $("#modal").css({
     left:  e.pageX - 7,
     top:   e.pageY - 30,
     show: false 
  });
});

$("#rows").on('keyup', function(e){
  var rows = $(this).val().replace(/[^0-9]/g,'');
  if (rows > 1000) {
    rows = 1000;
  }
  $(this).val(rows);
  // TODO: fire this request to server

  // create the message as json
  let jsonMessage = {
    message: rows 
  };

  // send our json message to the server
  sendToServer(jsonMessage);


});

$(document).ready(function() {
  $(".triangle-row span").mouseenter(function() {
    var clazz = $(this).attr("class");
    $("#modal-content").html(clazz);
    // $("#modal").fadeIn(500);
  });
  $(".triangle-row span").mouseleave(function() {
    $("#modal-content").html("");
    // $("#modal").fadeOut(100);
  });
});

function init() {
  webSocket = new WebSocket("ws://localhost:9000/ws");
  webSocket.onopen = onOpen;
  webSocket.onclose = onClose;
  webSocket.onmessage = onMessage;
  webSocket.onerror = onError;
  $("#message-input").focus();


  $(".triangle-row span").mouseenter(function() {
    var clazz = $(this).attr("class");
    $("#modal-content").html(clazz);
    // $("#modal").fadeIn(500);
  });
  $(".triangle-row span").mouseleave(function() {
    $("#modal-content").html("");
    // $("#modal").fadeOut(100);
  });

}

function onOpen(event) {
  consoleLog("CONNECTED");
}

function onClose(event) {
  consoleLog("Disconnected from server");
  consoleLog("Re-initializing a new fresh connection so server will be available for next action");
  init();
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
  // appendServerMessageToView("Server", receivedData.body);
  
  var firstRow = head(receivedData.body.rows);
  console.log("HERE: " + head(firstRow.row).actual);
  console.log("HERE: " + head(firstRow.row).approximation);

  var i;
  var markup = "";
  for (i = 0; i < receivedData.body.rows.length; ++i) {
    var fontSizePixels = 6;
    if (i == 0) {
      fontSizePixels = 20;
    } else if (i == 1) {
      fontSizePixels = 18;
    } else if (i == 2) {
      fontSizePixels = 15;
    } else if (i == 3) {
      fontSizePixels = 14;
    } else if (i == 4) {
      fontSizePixels = 13;
    } else if (i == 5) {
      fontSizePixels = 12;
    } else if (i == 6) {
      fontSizePixels = 11;
    } else if (i == 7 || i == 8) {
      fontSizePixels = 10;
    } else if (i >= 9 && i <= 19) {
      fontSizePixels = 9;
    } else if (i >= 20 && i <= 35) {
      fontSizePixels = 8;
    } else if (i >= 36 && i <= 46) {
      fontSizePixels = 7;
    } else if (i >= 47 && i <= 52) {
      fontSizePixels = 6;
    } else {
      fontSizePixels = 5;
    }
    markup = markup + triangleRowMarkup(receivedData.body.rows[i], fontSizePixels); 
  }
  // alert(markup);
  // alert(receivedData.body);


  $("#triangle").html(markup);

  $(".triangle-row span").mouseenter(function() {
    var clazz = $(this).attr("class");
    $("#modal-content").html(clazz);
    // $("#modal").fadeIn(500);
  });
  $(".triangle-row span").mouseleave(function() {
    $("#modal-content").html("");
    // $("#modal").fadeOut(100);
  });


  // $("#triangle").html("<span>" + receivedData.body + "<br /><br /></span>");
}

function head(lst) {
  return lst[0];
}

function tail(lst) {
  return lst.slice(1);
}

function triangleRowMarkup(rowObjArray, fontPixelSize=6) {
  var markup = "<div class='triangle-row font-" + fontPixelSize + "'>";
  var remaining = rowObjArray.row; 
  while (remaining.length > 0) {
    var next = head(remaining);
    remaining = tail(remaining);
    markup += "<span class='" + next.actual + "'>" + next.approximation + "</span>"
  }
  return markup + "</div>";
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
