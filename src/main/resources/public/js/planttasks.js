var tasksTable;
var taskTypeData;

function registerHandlers() {
    setupTasksTable();
    getTaskTypeData();
    $("#openTaskModalBtn").click(function() {
        showAddTaskModal(true);
    });
}

function showAddTaskModal(value) {
    $("#show-task-modal-toggle").prop('checked', value);
}

function showErrorContainer(value) {
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

}

function setupUpTaskTypeSelectOptions() {
    var selectElement = $("#taskTypeSelect");
    for(var i=0; i < taskTypeData.length; i++) {
        var currentArrayElement = taskTypeData[i];
        selectElement.append('<option value="' + currentArrayElement.id + '">' + currentArrayElement.name + '</option>');
    }
}

registerHandlers();