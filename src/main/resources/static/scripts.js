let token = null;

$(document).ready(function () {
    console.log("Index page is ready");

    $('#connect-websocket').click(function () {
        console.log("Try to connect to web-socket");
        token = $('#token').val();
        connect();
    });

    $('#former-message').click(function () {
        getFormerMessage();
    });

    $('#make-seen-btn').click(function () {
        let notificationId = $('#make-seen').val();
        makeSeen(notificationId);
    });
});

function connect() {
    const socket = new SockJS('/school-websocket');
    stompClient = Stomp.over(socket);

    const customHeader = {
        Authorization: token
    };

    stompClient.connect(customHeader, function (frame) {
        console.log('Connected: ', frame);

        stompClient.subscribe('/topic/global-notifications', function (message) {
            showMessage(JSON.parse(message.body).content, JSON.parse(message.body).messageId);
        });

        stompClient.subscribe('/user/topic/private-messages', function (message) {
            showMessage(JSON.parse(message.body).content, JSON.parse(message.body).messageId);
        });

        if (token === 'u1') {
            stompClient.subscribe('/topic/ch1-notification', function (message) {
                showMessage(JSON.parse(message.body).content, JSON.parse(message.body).messageId);
            });
        }
    });
}

function showMessage(message, messageId) {
    $("#messages").append("<tr><td>" + messageId + "</td><td>" + message + "</td></tr>");
}

function getFormerMessage() {
    stompClient.send("/ws/former-notification", {}, JSON.stringify({'user': token}));
}

function makeSeen(notificationId) {
    stompClient.send("/ws/make-seen", {}, JSON.stringify({'notificationId': notificationId}));
}