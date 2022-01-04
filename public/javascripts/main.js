var webSocket;
var messageInput;

$(document).on('mousemove', function(e){
  $("#modal").css({
    //  left:  e.pageX - 7,
    //  top:   e.pageY - 30,
    //  show: false 
     left:  e.pageX - 7,
     top:   e.pageY - 60,
     show: false 
  });
});

$("#rows").on('keyup', function(e){
  var rows = $(this).val().replace(/[^0-9]/g,'');
  if (rows > 500) {
    rows = 500;
  }
  $(this).val(rows);

  // create the message as json
  let jsonMessage = {
    message: rows 
  };

  // send our json message to the server
  sendToServer(jsonMessage);
});

$("#probability-n").on("keyup", function(e) {
  // TODO:
});
$("#probability-k").on("keyup", function(e) {
  // TODO:
});
$("#combination-n").on("keyup", function(e) {
  // TODO:
});
$("#combination-k").on("keyup", function(e) {
  // TODO:
});

$(document).ready(function() {
  $(".triangle-row span").mouseenter(function() {
    var clazz = $(this).attr("class");
    $("#modal-content").html(clazz);
  });
  $(".triangle-row span").mouseleave(function() {
    $("#modal-content").html("");
  });
  $("#div-combination-controls").hide();
  $("#div-probability-controls").hide();
});

$("#triangle-view").on("click", function (e) {
  $("#div-triangle-controls").show();
  $("#div-combination-controls").hide();
  $("#div-probability-controls").hide();
  $("#rows").val("");
  $("#probability-n").val("");
  $("#probability-k").val("");
  $("#compatibility-n").val("");
  $("#compatibility-k").val("");
  $("#triangle").html("");
});
$("#combination-view").on("click", function (e) {
  $("#div-triangle-controls").hide();
  $("#div-combination-controls").show();
  $("#div-probability-controls").hide();
  $("#rows").val("");
  $("#probability-n").val("");
  $("#probability-k").val("");
  $("#compatibility-n").val("");
  $("#compatibility-k").val("");
  $("#triangle").html("");
});
$("#probability-view").on("click", function (e) {
  $("#div-triangle-controls").hide();
  $("#div-combination-controls").hide();
  $("#div-probability-controls").show();
  $("#rows").val("");
  $("#probability-n").val("");
  $("#probability-k").val("");
  $("#compatibility-n").val("");
  $("#compatibility-k").val("");
  $("#triangle").html("");
});

function init() {
  // webSocket = new WebSocket("ws://localhost:9000/ws");
  var host = location.origin.replace(/^https/, 'wss').replace(/^http/, 'ws'); 
  webSocket = new WebSocket(`${host}/ws`);

  webSocket.onopen = onOpen;
  webSocket.onclose = onClose;
  webSocket.onmessage = onMessage;
  webSocket.onerror = onError;
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
  // console.log(event.data);
  let receivedData = JSON.parse(event.data);
  // console.log("New Data: ", receivedData);

  var i;
  var markup = "";
  for (i = 0; i < receivedData.body.rows.length; ++i) {
    var fontSizePixels = 6;
    if (i == 0) {
      fontSizePixels = 26;
    } else if (i == 1) {
      fontSizePixels = 20;
    } else if (i == 2) {
      fontSizePixels = 16;
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
    } else {
      fontSizePixels = 8;
    }
    markup = markup + triangleRowMarkup(receivedData.body.rows[i], fontSizePixels); 
  }

  $("#triangle").html(markup);

  $(".triangle-row span").mouseenter(function() {
    var clazz = $(this).attr("class");
    $("#modal-content").html(clazz);
  });
  $(".triangle-row span").mouseleave(function() {
    $("#modal-content").html("");
  });
}

$("#plus").click(function (e) {
  console.log("increasing font size");
  $('.triangle-row').each(function(i, obj) {
    var previousFontSize = parseInt($(this).attr("style").replace("font-size:", "").replace("px", "").replace(";", ""));
    var newFontSize = previousFontSize + 1;
    $( this ).css({"font-size":newFontSize+"px"});
  });
});

$("#minus").click(function (e) {
  console.log("decreasing font size");
  $('.triangle-row').each(function(i, obj) {
    var previousFontSize = parseInt($(this).attr("style").replace("font-size:", "").replace("px", "").replace(";", ""));
    var newFontSize = previousFontSize - 1;
    $( this ).css({"font-size":newFontSize+"px"});
  });
});

function head(lst) {
  return lst[0];
}

function tail(lst) {
  return lst.slice(1);
}

function triangleRowMarkup(rowObjArray, fontPixelSize=6) {
  var markup = "<div class='triangle-row' style='font-size:" + fontPixelSize + "px'>";
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
