var taskTypeTable;
var taskTypeIdToDelete;
var taskTypeNameToDelete;

function registerHandlers() {
    $("#openTaskTypeModalBtn").click(function() {
        showErrorContainer(false);
        showAddTaskTypeModal(true);
    });
    $("#addTaskTypeBtn").click(function() {
        sendAddTaskTypeRequest();
    });
    $("#deleteTaskTypeConfirmBtn").click(function() {
        sendTaskTypeRequest();
    });
    setupTaskTypeTable();
}

function showAddTaskTypeModal(value) {
    if(value) {
        $("#taskTypeToDeleteName").text(taskTypeNameToDelete);
    }
    $("#addTaskTypeModal-toggle").prop('checked', value);
}

function showErrorContainer(value) {
    if(value) {
        $("#errorContainer").show();
    } else {
        $("#errorContainer").hide();
    }
}

function showDeleteModal(value) {
    $("#delete-modal-toggle").prop('checked', value);
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
            showAddTaskTypeModal(false);
            taskTypeTable.ajax.reload();
            showToast("Success", "The task type " + taskTypeName + " was added to the system." ,"success");
        }
    });
}

function setupTaskTypeTable() {
    taskTypeTable = $('#taskTypesTable').DataTable({
        ajax: {
            url: '/tasktypes/data',
            dataSrc: ''
        },
        columns: [
            {data: 'name'},
            {
                data: 'id',
                render: function(data, type, full, meta) {
                    return '<button onclick="" id="editTaskType"><i class="fa fa-pencil" aria-hidden="true"></i> Edit</a>';
                }
            },
            {
                data: 'id',
                render: function(data, type, full, meta) {
                    return '<button onclick="deleteTaskType(' + full.id + ',\'' + full.name + '\')" id="deleteTaskType"><i class="fa fa-trash" aria-hidden="true"></i> Delete</a>';
                }
            }
        ]
    });
}

function deleteTaskType(taskTypeId, taskTypeName) {
    taskTypeIdToDelete = taskTypeId;
    taskTypeNameToDelete = taskTypeName;
    showDeleteModal(true);
}

function sendTaskTypeRequest() {
    $.ajax({
        url: '/tasktype/delete/' + taskTypeIdToDelete,
        type: 'DELETE',
        success: function(result) {
            if(result.status === "success") {
                taskTypeTable.ajax.reload();
                showDeleteModal(false);
                showToast("Success", "The task type was deleted successfully.", "success");
            } else if(result.status === "failed") {
                showToast("Error", "The task type was not deleted.", "error");
            }
        },
        error: function(result, statusText, errorText) {
            showToast("Error", "The task type was not deleted.", "error");
        }
    });
}



registerHandlers();