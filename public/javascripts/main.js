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
  var msg = "rows=" + rows;
  let jsonMessage = {
    message: msg 
  };
  sendToServer(jsonMessage);
});

$("#probability-n").on("keyup", function(e) {
  var n = $(this).val().replace(/[^0-9]/g,'');
  var k = $("#probability-k").val().replace(/[^0-9]/g,'');
  if (n > 500) {
    n = 500;
  }
  if (n > '' && k > n) {
    k = n;
  }
  $("#probability-n").val(n);
  $("#probability-k").val(k);
  if (n && k) {
    var msg = "probability-n=" + n + ";probability-k=" + k;
    let jsonMessage = {
      message: msg 
    };
    sendToServer(jsonMessage);
  }
});
$("#probability-k").on("keyup", function(e) {
  var k = $(this).val().replace(/[^0-9]/g,'');
  var n = $("#probability-n").val().replace(/[^0-9]/g,'');
  if (n > 500) {
    n = 500;
  }
  if (n > '' && k > n) {
    k = n;
  }
  $("#probability-n").val(n);
  $("#probability-k").val(k);
  if (n && k) {
    var msg = "probability-n=" + n + ";probability-k=" + k;
    let jsonMessage = {
      message: msg 
    };
    // send our json message to the server
    sendToServer(jsonMessage);
  }
});
$("#combination-n").on("keyup", function(e) {
  var n = $(this).val().replace(/[^0-9]/g,'');
  var k = $("#combination-k").val().replace(/[^0-9]/g,'');
  if (n > 500) {
    n = 500;
  }
  if (n > '' && k > n) {
    k = n;
  }
  $("#combination-n").val(n);
  $("#combination-k").val(k);
  if (n && k) {
    var msg = "combination-n=" + n + ";combination-k=" + k;
    let jsonMessage = {
      message: msg 
    };
    sendToServer(jsonMessage);
  }
});
$("#combination-k").on("keyup", function(e) {
  var k = $(this).val().replace(/[^0-9]/g,'');
  var n = $("#combination-n").val().replace(/[^0-9]/g,'');
  if (n > 500) {
    n = 500;
  }
  if (n > '' && k > n) {
    k = n;
  }
  $("#combination-n").val(n);
  $("#combination-k").val(k);
  if (n && k) {
    var msg = "combination-n=" + n + ";combination-k=" + k;
    let jsonMessage = {
      message: msg 
    };
    sendToServer(jsonMessage);
  }
});

$(document).ready(function() {
  $(".triangle-row span").mouseenter(function() {
    var clazz = head($(this).attr("class").split(" "));
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
  $("#combination-n").val("");
  $("#combination-k").val("");
  $("#triangle").html("");
});
$("#combination-view").on("click", function (e) {
  $("#div-triangle-controls").hide();
  $("#div-combination-controls").show();
  $("#div-probability-controls").hide();
  $("#rows").val("");
  $("#probability-n").val("");
  $("#probability-k").val("");
  $("#combination-n").val("");
  $("#combination-k").val("");
  $("#triangle").html("");
});
$("#probability-view").on("click", function (e) {
  $("#div-triangle-controls").hide();
  $("#div-combination-controls").hide();
  $("#div-probability-controls").show();
  $("#rows").val("");
  $("#probability-n").val("");
  $("#probability-k").val("");
  $("#combination-n").val("");
  $("#combination-k").val("");
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
  // if (receivedData.body.msg) {
  //   markup = markup + "<div class='msg' style='font-size:" + 26 + "px'>" + receivedData.body.msg + "</div>";
  // }
  var optionalBoldColumn = '';
  if (receivedData.body.msg && receivedData.body.msg.includes("combinations is row ")) {
    var remaining = receivedData.body.msg.substring(receivedData.body.msg.indexOf("column ") + "column ".length);
    optionalBoldColumn = remaining.split(" ")[0];
  } else if (receivedData.body.msg && receivedData.body.msg.includes("(column ")) {
    var remaining = receivedData.body.msg.substring(receivedData.body.msg.indexOf("(column ") + "(column ".length);
    optionalBoldColumn = remaining.split(" ")[0].replace(")", "").replace("<br />", "").replace("<br/>", "").replace("<br", "");
  }
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
    if (i == receivedData.body.rows.length - 1) { // last row, apply any bold columns...
      markup = markup + triangleRowMarkup(receivedData.body.rows[i], fontSizePixels, optionalBoldColumn); 
    } else {
      markup = markup + triangleRowMarkup(receivedData.body.rows[i], fontSizePixels); 
    }
  }
  if (receivedData.body.msg) {
    // markup = markup + "<div class='msg' style='font-size:" + fontSizePixels + "px'>" + receivedData.body.msg + "</div>";
    markup = markup + "<div class='msg' >" + receivedData.body.msg + "</div>";
  }

  $("#triangle").html(markup);

  $(".triangle-row span").mouseenter(function() {
    var clazz = head($(this).attr("class").split(" "));
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

function triangleRowMarkup(rowObjArray, fontPixelSize=6, highlightedColumnInFinalRow='') {
  var markup = "<div class='triangle-row' style='font-size:" + fontPixelSize + "px'>";
  var remaining = rowObjArray.row; 
  var column = 1; 
  while (remaining.length > 0) {
    var optionalBoldClass = ''
    if (column.toString() == highlightedColumnInFinalRow || highlightedColumnInFinalRow == '*') {
      optionalBoldClass = " bold";
    }
    var next = head(remaining);
    remaining = tail(remaining);
    markup += "<span class='" + next.actual + optionalBoldClass + "'>" + next.approximation + "</span>";
    column += 1;
  }
  return markup + "</div>";
}

function consoleLog(message) {
  console.log("New message: ", message);
}

window.addEventListener("load", init, false);

// send the data to the server using the WebSocket
function sendToServer(jsonMessage) {
  if(webSocket.readyState == WebSocket.OPEN) {
    consoleLog("SENT: " + jsonMessage.message);

    webSocket.send(JSON.stringify(jsonMessage));
  } else {
    consoleLog("Could not send data. Websocket is not open.");
  }
}
