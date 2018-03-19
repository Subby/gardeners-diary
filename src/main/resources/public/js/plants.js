var plantData;

function registerHandlers() {
    getPlantData();
}

function getPlantData() {
    $('#plantsTable').DataTable({
        ajax: {
            url: '/plants/data',
            dataSrc: ''
        },
        columns: [
            { data: 'name' },
            { data: 'type' },
            { data: 'created_at' },
            { data: 'updated_at' },
            {}
        ]
    });
}

registerHandlers();
