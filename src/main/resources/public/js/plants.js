var plantTable;

function registerHandlers() {
    getPlantData();
}

function getPlantData() {
    plantTable = $('#plantsTable').DataTable({
        ajax: {
            url: '/plants/data',
            dataSrc: ''
        },
        columns: [
            {data: 'name'},
            {data: 'type'},
            {data: 'created_at'},
            {data: 'updated_at'},
            {
                data: 'id',
                render: function(data, type, full, meta) {
                    return '<a href="/plant/view/' + data +'" class="button"><i class="fa fa-eye" aria-hidden="true"> View</a>';
                }
            }
        ]
    });
}

registerHandlers();
