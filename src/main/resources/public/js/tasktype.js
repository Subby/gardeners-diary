function registerHandlers() {
    $("#openTaskTypeModalBtn").click(function() {
        showErrorContainer(false);
        showAddTaskTypeModal(true);
    });
    $("#addTaskTypeBtn").click(function() {
        sendAddTaskTypeRequest();
    });
}

function showAddTaskTypeModal(value) {
    $("#addTaskTypeModal-toggle").prop('checked', value);
}

function showErrorContainer(value) {
    if(value) {
        $("#errorContainer").show();
    } else {
        $("#errorContainer").hide();
    }
}

function sendAddTaskTypeRequest() {
    var taskTypeName = $('#taskTypeName').val();
    if(!taskTypeName) {
        showErrorContainer(true);
        return;
    }
    $.post("/tasktype/add", {
        name: taskTypeName
    } ,function(data){
        if(data.status === "success") {
            showErrorContainer(false);
            showModal(false);
            showToast("Success", "The task type " + taskTypeName + " was added to the system." ,"success");
        }
    });
}

function setupTaskTypeTable() {
    plantTable = $('#taskTypesTable').DataTable({
        ajax: {
            url: '/plants/data',
            dataSrc: ''
        },
        columns: [
            {data: 'name'},
            {
                data: 'id',
                render: function(data, type, full, meta) {
                    return '<button id="editTaskType"><i class="fa fa-pencil" aria-hidden="true"></i> Edit</a>';
                }
            },
            {
                data: 'id',
                render: function(data, type, full, meta) {
                    return '<button id="deleteTaskType"><i class="fa fa-trash" aria-hidden="true"></i> Delete</a>';
                }
            }
        ]
    });
}

registerHandlers();