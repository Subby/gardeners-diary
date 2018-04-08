var tasksTable;
var taskTypeData;

function registerHandlers() {
    setupTasksTable();
    getTaskTypeData();
}

function setupTasksTable() {
    tasksTable = $('#tasksTable').DataTable({
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
        var currentArrayElement = taskTypeId[i];
        //Find matching id
        if(currentArrayElement.id) {
            return currentArrayElement.name;
        }
    }
    return "Task type name not found";
}

function getTaskTypeData() {
    $.get("/tasktypes/data", function(data) {
        taskTypeData = data;
    });
}

registerHandlers();