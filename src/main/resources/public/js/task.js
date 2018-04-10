function showModal(value) {
    $("#show-task-modal-toggle").prop('checked', value);
}

function showDeleteModal(value) {
    $("#delete-modal-toggle").prop('checked', value);
}

function showErrorContainer(value) {
    if(value) {
        $("#errorContainer").show();
    } else {
        $("#errorContainer").hide();
    }
}

function registerHandlers() {
    $("#editTaskBtn").click(function() {
        showErrorContainer(false);
        showModal(true);
    });
    $("#updatePlantBtn").click(function() {
        sendUpdateTaskRequest();
    });
    $("#deleteTaskBtn").click(function() {
        showDeleteModal(true);
    });
    $("#deleteTaskConfirmBtn").click(function() {
        sendDeleteTaskRequest();
    });
    showErrorContainer(false);
}

function sendUpdateTaskRequest() {

}

function sendDeleteTaskRequest() {

}

registerHandlers();