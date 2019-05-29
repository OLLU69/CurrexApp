var homePath = null;

function init(rootPath) {
    $(function () {
        homePath = rootPath;
    });
}

function toExchange() {
    sendGetRequest("/user/exchange");
}

function exchange(data, onSuccess, onError) {
    $.ajax({
        url: homePath + "/user/exchange",
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
    sendGetRequest("/user/operations");
}

function toAdminOperations() {
    sendGetRequest("/admin/operations");
}

function toLogout() {
    sendGetRequest("/logout");
}

function toAdminReference() {
    sendGetRequest("/admin/reference");
}

function toUserReference() {
    sendGetRequest("/user/reference");
}

function UpdateReference() {
    sendGetRequest("/admin/update_references");
}

function removeOperation(id) {
    sendGetRequest("/user/remove_operation/" + id);
}

function sendGetRequest(path) {
    path = path || "/index";
    location.href = homePath + path;
}

