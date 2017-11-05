function showModal(value) {
    $("#modal-toggle").prop('checked', value);
}

$("#editPlantBtn").click(function() {
    showModal(true);
});

$("#modal-toggle").change(function() {

});
