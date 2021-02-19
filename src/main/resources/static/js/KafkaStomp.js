var stompClient = null;
let dashboardList = new Map();

connect();

function setConnected(connected) {
    $("#dashboard-data").html("");
}

function connect() {
    var socket = new SockJS('/flight-demo-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/dashboard-data', function (dashboard) {
            updateDashboard(JSON.parse(dashboard.body));            
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

function updateDashboard(message) {
    console.log("message: " + message);
    dashboardList.set(message.dashboardTitle, message.dashboardValue);

    var content = ' ';
    for (let [key, value] of dashboardList) {
        console.log(key + ' = ' + value);
        content += "<tr><td>" + key + ": " + value;
    }
    $("#dashboard-data").html(content);
}


