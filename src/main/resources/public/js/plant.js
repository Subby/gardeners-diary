function showModal(value) {
    $("#modal-toggle").prop('checked', value);
}

function showErrorContainer(value) {
    if(value) {
        $("#errorContainer").show();
    } else {
        $("#errorContainer").hide();
    }

}

function registerHandlers() {
    replaceImage();
    $("#editPlantBtn").click(function() {
        showErrorContainer(false);
        showModal(true);
    });
    $("#updatePlantBtn").click(function() {
        sendUpdatePlantRequest();
    });

}

function sendUpdatePlantRequest() {
    var plantIdVal = $("#plantId").val();
    var nameVal = $("#plantName").val();
    var typeVal = $("#plantType").val();
    if(!nameVal || !typeVal) {
        showErrorContainer(true);
        return;
    }
    $.post("/plant/update", {
        name: nameVal,
        type: typeVal,
        id: plantIdVal
    } ,function(data){
        if(data.status === "success") {
            $("#errorContainer").hide();
            showModal(false);
            showToast("Success", "The plant " + nameVal + " was updated to the system. Click <a href='/plant/view/" + plantIdVal +  "'>here</a> view your changes.", "success");
        }
    });
}

function replaceImage() {
    $.get("/plantinfo/" + plantType.toLowerCase(),
        function(data){
            if(data.status === "success") {
                $('#plantImage').attr("src", data.image);
            }
        });
}

registerHandlers();