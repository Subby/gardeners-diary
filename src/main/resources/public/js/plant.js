function showModal(value) {
    $("#modal-toggle").prop('checked', value);
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
    replaceImage();
    $("#editPlantBtn").click(function() {
        showErrorContainer(false);
        showModal(true);
    });
    $("#updatePlantBtn").click(function() {
        sendUpdatePlantRequest();
    });
    $("#deletePlantBtn").click(function() {
        showDeleteModal(true);
    });
    $("#deletePlantConfirmBtn").click(function() {
        sendDeletePlantRequest();
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
    $("#loadingSpinnerImage").show();
    $.get("/plantinfo/" + plantType.toLowerCase(),
        function(data){
            if(data.status === "success") {
                $("#loadingSpinnerImage").hide();
                $('#plantImage').attr("src", data.image);
            } else {
                $("#loadingSpinnerImage").hide();
                $('#plantImage').attr("src", "/img/imageNA.png");
            }
        });
}

function sendDeletePlantRequest() {
    $.ajax({
        url: '/plant/delete/' + plantId,
        type: 'DELETE',
        success: function(result) {
            if(result === "success") {
                showToast("Success", "The plant was deleted successfully. Redirecting to manage garden plant, click <a href='/managegarden'>here</a> to return to the page.", "success");
                window.setTimeout(function() {
                    window.location.href = "/managegarden";
                }, 5000);
            }
        },
        error: function(result, statusText, errorText) {
            showToast("Error", "The plant was not deleted.", "error");
        }
    });
}

registerHandlers();