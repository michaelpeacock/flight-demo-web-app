var stompClient = null;
let dashboardList = new Map();

connect();

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/flight-demo-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/dashboard-data', function (dashboard) {
            showGreeting(JSON.parse(dashboard.body));            
        });
        stompClient.subscribe('/topic/flight-data', function (flight) {
            handleFlightUpdate(JSON.parse(flight.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    console.log("message: " + message);
    dashboardList.set(message.dashboardTitle, message.dashboardValue);

    var content = ' ';
    for (let [key, value] of dashboardList) {
        console.log(key + ' = ' + value);
        content += "<tr><td>" + key + ": " + value;
    }
    $("#greetings").html(content);
}



