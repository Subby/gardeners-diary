var currentTaskIdToMarkComplete;
var currentTaskNameToMarkComplete;
var currentTaskIdToMarkIncomplete;
var currentTaskNameToMarkIncomplete;

var completedValue;
var taskNameValue;
var dueDateValue;
var taskTypeValue;
var completeButton;
var editButton;
var deleteButton;
var completeButtonIcon;

function showModal(value) {
    $("#show-task-modal-toggle").prop('checked', value);
}

function showDeleteModal(value) {
    $("#delete-modal-toggle").prop('checked', value);
}

function showErrorContainer(value) {
    if(value) {
        $("#addTaskErrorContainer").show();
    } else {
        $("#addTaskErrorContainer").hide();
    }
}

function showCompleteTaskModal(value) {
    $("#complete-task-modal-toggle").prop('checked', value);
}

function showIncompleteTaskModal(value) {
    $("#incomplete-task-modal-toggle").prop('checked', value);
}

function registerHandlers() {
    $("#editTaskBtn").click(function() {
        showErrorContainer(false);
        showModal(true);
    });
    $("#updateTaskBtn").click(function() {
        sendUpdateTaskRequest();
    });
    $("#deleteTaskBtn").click(function() {
        showDeleteModal(true);
    });
    $("#deleteTaskConfirmBtn").click(function() {
        sendDeleteTaskRequest();
    });
    $("#completeTaskConfirmBtn").click(function() {
        sendCompleteTaskRequest();
    });
    $("#incompleteTaskConfirmBtn").click(function() {
        sendUncompleteTaskRequest();
    });
    $("#completeTaskBtn").click(function() {
        var completeTaskBtnText = $("#completeTaskBtn").text();
        //If the button text is complete task then we know the task is to be completed, show relevant modal
        if(completeTaskBtnText.includes("Mark as complete")) {
            completeTask();
        } else if(completeTaskBtnText.includes(" Mark as incomplete")) {
            uncompleteTask();
        }
    });
    setupTextValues();
    showErrorContainer(false);
}

function sendCompleteTaskRequest() {
    $.post("/task/complete", {
        taskId: currentTaskId
    } ,function(data){
        if(data.status === "success") {
            showToast("Success", "The task was marked as completed.", "success");
            showCompleteTaskModal(false);
            changeValuesForCompletedTask();
        } else {
            showToast("Error", "The task was not marked as completed.", "error");
        }
    });
}

function sendUncompleteTaskRequest() {
    $.post("/task/incomplete", {
        taskId: currentTaskId
    } ,function(data){
        if(data.status === "success") {
            showToast("Success", "The task was marked as incomplete.", "success");
            showIncompleteTaskModal(false);
            changeValuesForIncompletedTask();
        } else {
            showToast("Error", "The task was not marked as incomplete.", "error");
        }
    });
}


function sendUpdateTaskRequest() {
    var newTaskName = $("#newTaskName").val();
    var newTaskType = $("#newTaskType").val();
    var newDueDate = $("#newDueDate").val();
    if(!newTaskName || !newTaskType || !newDueDate) {
        showErrorContainer(true);
        return;
    }
    $.post("/task/update", {
        newTaskName: newTaskName,
        newTaskType: newTaskType,
        newDueDate : newDueDate,
        taskId: currentTaskId
    } ,function(data){
        if(data.status === "success") {
            $("#errorContainer").hide();
            showModal(false);
            updateValues(newTaskName, newTaskType, newDueDate);
            showToast("Success", "The task " + newTaskName + " was updated", "success");
        }
    });
}

function sendDeleteTaskRequest() {
    $.ajax({
        url: '/task/delete/' + currentTaskId,
        type: 'DELETE',
        success: function(result) {
            if(result.status === "success") {
                var redirectUrl = "/plant/view/"+ assignedPlantId;
                showToast("Success", "The task was deleted successfully. Redirecting back to plant, click <a href='" + redirectUrl +"' >here</a> to return to the page manually.", "success");
                //Disable the buttons on the page so no errors are encountered
                editButton.prop("disabled", true);
                deleteButton.prop("disabled", true);
                completeButtonIcon.prop("disabled", true);
                window.setTimeout(function() {
                    window.location.href = redirectUrl;
                }, 5000);
            }
        },
        error: function(result, statusText, errorText) {
            showToast("Error", "The task was not deleted.", "error");
        }
    });
}

function setupTextValues() {
    completedValue = $("#completedValue");
    taskNameValue = $(".taskNameValue");
    dueDateValue = $("#dueDateValue");
    taskTypeValue = $("#taskTypeValue");
    completeButton = $("#completeTaskBtn");
    editButton = $("#editTaskBtn");
    deleteButton = $("#deleteTaskBtn");
    completeButtonIcon = $("#completeButtonIcon");
}

function changeValuesForCompletedTask() {
    completedValue.text("Incomplete");
    completeButton.html(" <i class=\"fa fa-minus\" aria-hidden=\"true\"></i> Mark as incomplete");
    completeButton.attr("class", "");
    completeButtonIcon.attr("class", "fa fa-minus");
}

function changeValuesForIncompletedTask() {
    completedValue.text("Completed");
    completeButton.html(" <i class=\"fa fa-check\" aria-hidden=\"true\"></i> Mark as complete");
    completeButton.attr("class", "tertiary");
    completeButtonIcon.attr("class", "fa fa-check");
}

function updateValues(newTaskName, newTaskType, newDueDate) {
    taskNameValue.text(newTaskName);
    taskTypeValue.text(newTaskType);
    dueDateValue.text(newDueDate);
}

function completeTask() {
    showCompleteTaskModal(true);
}

function uncompleteTask() {
    showIncompleteTaskModal(true);
}


registerHandlers();