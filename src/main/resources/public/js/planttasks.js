var tasksTable;
var taskTypeData;

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
}

function showAddTaskModal(value) {
    $("#show-task-modal-toggle").prop('checked', value);
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
            {data: 'created_at'},
            {data: 'updated_at'},
            {
                data: 'task_type_id',
                render: function(data, type, full, meta) {
                    return getTaskNameForTaskId(data);
                }
            },
            {
                data: 'id',
                render: function(data, type, full, meta) {
                    return '<a href="/task/view/' + data +'" class="button"><i class="fa fa-eye" aria-hidden="true"> View</a>';
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

registerHandlers();