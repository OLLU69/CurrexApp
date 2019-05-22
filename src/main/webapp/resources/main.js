var homePath = null;

function init(rootPath) {
    $(function () {
        homePath = rootPath;
    });
}

function toExchange() {
    sendGetRequest("/exchange");
}

function exchange(data, onSuccess, onError) {
    $.ajax({
        url: homePath + "/exchange",
        type: "POST",
        data: JSON.stringify(data),
        contentType: "application/json;charset=UTF-8",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "text/plain;charset=UTF-8");
        },
        // timeout: 1000,
        success: function (data) {
            if (data.toUpperCase() === "Ok".toUpperCase()) onSuccess();
            else onError(data);
        },
        error: function (jqXHR, textStatus) {
            onError(jqXHR.status + ":" + jqXHR.statusText + ":" + textStatus);
            console.log('ERROR: sendUserFormData textStatus: ' + textStatus + " jqXHR: " + jqXHR.status + ":" + jqXHR.statusText);
        }
    })
}

function toHome() {
    sendGetRequest("/index")
}

function toUserOperations() {
    sendGetRequest("/user_operations");
}

function toAdminOperations() {
    sendGetRequest("/admin_operations");
}

function toAdminReference() {
    sendGetRequest("/admin_reference");
}

function toUserReference() {
    sendGetRequest("/user_reference");
}

function UpdateReference() {
    sendGetRequest("/update_references");
}

function removeOperation(id) {
    sendGetRequest("/remove_operation/" + id);
}

function sendGetRequest(path) {
    path = path || "/index";
    location.href = homePath + path;
}

