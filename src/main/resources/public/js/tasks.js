function initDatePicker() {
    var options = {
        format: 'dd/mm/yyyy',
        autoHide: true,
        autoPick: true,
        trigger: '#dateSelectBtn'
    };
    $('[data-toggle="datepicker"]').datepicker(options);
}
function showModal(value) {
    $("#modal-toggle").prop('checked', value);
}
$("#openTaskModalBtn").click(function() {
    showModal(true);
});
initDatePicker();


