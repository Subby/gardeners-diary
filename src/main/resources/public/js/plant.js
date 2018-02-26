function showModal(value) {
    $("#modal-toggle").prop('checked', value);
}

function registerHandlers() {
    $("editPlantBtn").click(function() {
        $("#errorContainer").hide();
        showModal(true);
    });
    $("editPlantBtn").click(function() {
        sendUpdatePlantRequest();
    });
}

function sendUpdatePlantRequest() {
    var plantIdVal = $("#plantId").val();
    var nameVal = $("#plantName").val();
    var typeVal = $("#plantType").val();
    if(!nameVal && !typeVal) {
        $("#errorContainer").show();
        return;
    }
    $.post("/plant/add", {
        name: nameVal,
        type: typeval,
        id: plantIdVal
    } ,function(data){
        if(data.status === "success") {
            $("#errorContainer").hide();
            showModal(false);
            showToast("Success", "The plant " + nameVal + " was updated to the system. Click <a href='/plant/view/" + plantIdVal +  "'>here</a> view your changes.", "success");
        }
    });
}

registerHandlers();