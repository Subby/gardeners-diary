var tasksTable;
var taskTypeData;

var currentTaskIdToMarkComplete;
var currentTaskNameToMarkComplete;
var currentTaskIdToMarkIncomplete;
var currentTaskNameToMarkIncomplete;



function registerHandlers() {
    getTaskTypeData();
    setupTasksTable();
    showAddTaskErrorContainer(false);
    $("#openTaskModalBtn").click(function() {
        showAddTaskModal(true);
    });
    $("#addTaskForPlantButton").click(function() {
        sendAddTaskRequest();
    });
    $("#completeTaskConfirmBtn").click(function() {
        sendCompleteTaskRequest();
    });
    $("#incompleteTaskConfirmBtn").click(function() {
        sendUncompleteTaskRequest();
    });
    $('#completedFilterCheckbox').on("click", function(e) {
        tasksTable.draw();
    });
}

function showAddTaskModal(value) {
    $("#show-task-modal-toggle").prop('checked', value);
}

function showCompleteTaskModal(value) {
    $("#complete-task-modal-toggle").prop('checked', value);
}

function showIncompleteTaskModal(value) {
    $("#incomplete-task-modal-toggle").prop('checked', value);
}

function showAddTaskErrorContainer(value) {
    if(value) {
        $("#addTaskErrorContainer").show();
    } else {
        $("#addTaskErrorContainer").hide();
    }
}

function setupTasksTable() {
    tasksTable = $('#tasksTable').DataTable({
        language: {
            "emptyTable": "Currently no tasks associated with this plant."
        },
        ajax: {
            url: '/plant/' + plantId + '/tasks',
            dataSrc: ''
        },
        columns: [
            {data: 'name'},
            {
                data: 'task_type_id',
                render: function(data, type, full, meta) {
                    return getTaskNameForTaskId(data);
                }
            },
            {data: 'created_at'},
            {data: 'updated_at'},
            {data: 'due_date'},
            {
                data: 'completed',
                render: function (data, type, full, meta) {
                    if(data === false) {
                        return 'Incomplete';
                    } else if(data === true) {
                        return 'Complete';
                    }
                }
            },
            {
                data: 'id',
                render: function(data, type, full, meta) {
                    return '<a href="/task/view/' + data +'" class="button"><i class="fa fa-eye" aria-hidden="true"> View</a>';
                }
            },
            {
                data: 'id',
                render: function(data, type, full, meta) {
                    if(full.completed === false) {
                        return '<button onclick="completeTask('+ data +',\'' + full.name + '\')"><i class="fa fa-check" aria-hidden="true"></i> Mark as complete</button>';
                    } else if(full.completed === true) {
                        return '<button onclick="uncompleteTask('+ data +',\'' + full.name + '\')"><i class="fa fa-minus" aria-hidden="true"></i> Mark as incomplete</button>';
                    }

                }
            }
        ]
    });
}

function getTaskNameForTaskId(taskTypeId) {
    //Loop through task type array
    for(var i=0; i < taskTypeData.length; i++) {
        var currentArrayElement = taskTypeData[i];
        //Find matching id
        if(currentArrayElement.id) {
            return currentArrayElement.name;
        }
    }
    return "Task type name not found";
}

function getTaskTypeData() {
    $.ajax({
        url: '/tasktypes/data',
        type: 'GET',
        success: function(data){
            taskTypeData = data;
            setupUpTaskTypeSelectOptions();
        },
        error: function(data) {
            console.log("ERROR");
        }
    });
}

function sendAddTaskRequest() {
    var taskNameVal = $("#taskName").val();
    var taskTypeVal = $("#taskType").val();
    var plantIdValTask = $("#addTaskPlantId").val();
    var emailReminderVal = $("#emailReminder").val();
    var dueDateVal = $("#dueDate").val();

    if(!taskNameVal || !taskTypeVal || !taskTypeVal || !plantIdValTask || !emailReminderVal || !dueDateVal) {
        showAddTaskErrorContainer(true);
        return;
    }

    $.post("/task/add", {
        name: taskNameVal,
        taskTypeId: taskTypeVal,
        plantId: plantIdValTask,
        emailReminder: convertEmailReminderSwitchToBoolean(emailReminderVal),
        dueDate: dueDateVal
    } ,function(data){
        if(data.status === "success") {
            showAddTaskErrorContainer(false);
            showToast("Success", "The task was added to the system.", "success");
            showAddTaskModal(false);
            tasksTable.ajax.reload();
        } else {
            showToast("Error", "The plant was not added.", "error");
        }
    });
}

function setupUpTaskTypeSelectOptions() {
    var selectElement = $("#taskType");
    for(var i=0; i < taskTypeData.length; i++) {
        var currentArrayElement = taskTypeData[i];
        selectElement.append('<option value="' + currentArrayElement.id + '">' + currentArrayElement.name + '</option>');
    }
}

function convertEmailReminderSwitchToBoolean(emailReminderVal) {
    //Convert the email reminder switch to a boolean for easier parsing server side
    if(emailReminderVal === "on") {
        return "true";
    }
    return "false";
}

function completeTask(taskId, taskName) {
    currentTaskIdToMarkComplete = taskId;
    currentTaskNameToMarkComplete = taskName;
    showCompleteTaskModal(true);
}

function uncompleteTask(taskId, taskName) {
    currentTaskIdToMarkIncomplete = taskId;
    currentTaskNameToMarkIncomplete = taskName;
    showIncompleteTaskModal(true);
}

function sendCompleteTaskRequest() {
    $.post("/task/complete", {
        taskId: currentTaskIdToMarkComplete
    } ,function(data){
        if(data.status === "success") {
            showToast("Success", "The task was marked as completed.", "success");
            showCompleteTaskModal(false);
            tasksTable.ajax.reload();
        } else {
            showToast("Error", "The task was not marked as completed.", "error");
        }
    });
}

function sendUncompleteTaskRequest() {
    $.post("/task/incomplete", {
        taskId: currentTaskIdToMarkIncomplete
    } ,function(data){
        if(data.status === "success") {
            showToast("Success", "The task was marked as uncomplete.", "success");
            showIncompleteTaskModal(false);
            tasksTable.ajax.reload();
        } else {
            showToast("Error", "The task was not marked as uncomplete.", "error");
        }
    });
}

//Adapted from: https://stackoverflow.com/questions/11226614/datatables-create-filter-checkbox
$.fn.dataTableExt.afnFiltering.push(function(oSettings, aData, iDataIndex) {
    var checked = $('#completedFilterCheckbox').is(':checked');
    if (checked && (aData[5] === "Complete" || aData[5] === "Incomplete")) {
        return true;
    }
    if (!checked && (aData[5] === "Incomplete")) {
        return true;
    }
    return false;
});

registerHandlers();