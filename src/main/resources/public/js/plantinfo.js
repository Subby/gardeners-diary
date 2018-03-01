var plantInfoDesc = $("#plantInfoDesc");
var plantSunReq = $("#plantSunReq");
var plantInfoSowingMeth = $("#plantInfoSowingMeth");
var plantInfoRowSpacing = $("#plantInfoRowSpacing");
var plantInfoHeight = $("#plantInfoHeight");
var plantInfoErrorContainer = $("#plantInfoErrorContainer ul");

function registerHandlers() {
    showPlantInfoContainer(false);
    showPlantInfoInnerContainer(false);
    showPlantInfoErrorContainer(false);
    $("#searchPlantsBtn").click(function() {
        getPlantInformation();
    });
    $("#hidePlantInfoBtn").click(function() {
        showPlantInfoContainer(false);
        showPlantInfoInnerContainer(false);
    });
}

function showPlantInfoErrorContainer(value) {
    var errorContainer = $("#plantInfoErrorContainer");
    if(value) {
        errorContainer.show();
    } else {
        clearErrorsList();
        errorContainer.hide();
    }
}

function showPlantInfoContainer(value) {
    var plantInfoContainer = $("#plantInfo");
    if(value) {
        plantInfoContainer.show();
    } else {
        clearErrorsList();
        plantInfoContainer.hide();
    }
}

function showPlantInfoInnerContainer(value) {
    var plantInfoInnerContainer = $("#plantInfoInnerContainer");
    if(value) {
        plantInfoInnerContainer.show();
    } else {
        clearErrorsList();
        plantInfoInnerContainer.hide();
    }
}

function showLoadingSpinner(value) {
    var loadingSpinner = $("#loadingSpinner");
    if(value) {
        loadingSpinner.show();
    } else {
        loadingSpinner.hide();
    }
}

function getPlantInformation() {
    showPlantInfoContainer(true);
    showLoadingSpinner(true);
    showPlantInfoInnerContainer(false);
    showPlantInfoErrorContainer(false);
    clearExisitingFields();
    var typeVal = $("#plantType").val();
    if(!typeVal) {
        appendPlantTypeEmptyError();
        showPlantInfoErrorContainer(true);
        return;
    }
    $.get("/plantinfo/" + typeVal.toLowerCase(),
        function(data){
        if(data.status === "success") {
            showLoadingSpinner(false);
            appendPlantInformation(data);
            showPlantInfoInnerContainer(true);
        } else {
            showLoadingSpinner(false);
            appendNotFoundError();
            showPlantInfoErrorContainer(true);
            showPlantInfoContainer(false);
        }
    });
}

function clearExisitingFields() {
    plantInfoDesc.text("");
    plantSunReq.text("");
    plantInfoSowingMeth.text("");
    plantInfoRowSpacing.text("");
    plantInfoHeight.text("");
}

function appendPlantInformation(plantData) {
    plantInfoDesc.text(plantData.description);
    plantSunReq.text(plantData.sun_requirements);
    plantInfoSowingMeth.text(plantData.sowing_method);
    plantInfoRowSpacing.text(plantData.row_spacing);
    plantInfoHeight.text(plantData.height);
}

function appendNotFoundError() {
    plantInfoErrorContainer.append('<li>The plant type you searched was not found in the plant information database. You can still add this plant using the plant type.</li>');
}

function appendPlantTypeEmptyError() {
    plantInfoErrorContainer.append('<li>Please make sure that the plant type field is filled in.</li>');
}

function clearErrorsList() {
    plantInfoErrorContainer.empty();
}
registerHandlers();