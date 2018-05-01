var width = window.innerWidth;
var height = window.innerHeight;
var c1Position;
var currentRect;
var drag = false;
var rectClick = false;
var stage = new Konva.Stage({
    container: 'canvas',
    width: width,
    height: height
});
var layer = generateBaseLayer();
var imageLayer = new Konva.Layer();
var tooltipLayer = new Konva.Layer();
var imageObj = new Image();

function generateBaseLayer() {
    if(gardenJSON) {
        return Konva.Node.create(gardenJSON);
    }
    return new Konva.Layer();
}

function removeCurrentRect() {
    currentRect.destroy();
    layer.draw();
    currentRect = null;
}

imageObj.onload = function() {
    var gardenImage = new Konva.Image({
        x: 0,
        y: 0,
        height: 867,
        width: 1600,
        image: imageObj,
        id: 'gardenImage'
    });

    var tooltip = new Konva.Text({
        text: "",
        fontFamily: "Calibri",
        fontSize: 12,
        padding: 5,
        textFill: "white",
        fill: "black",
        alpha: 0.75,
        visible: false
    });
    imageLayer.add(gardenImage);
    // add the shape to the layer
    if(!gardenJSON) {
        layer.add(gardenImage);
    }

    tooltipLayer.add(tooltip);

    stage.add(imageLayer);
    // add the layer to the stage
    stage.add(layer);
    //add layer for tooltips
    stage.add(tooltipLayer);

    stage.get('.regionRect').on("mousemove", function() {
        var mousePos = stage.getPointerPosition();
        tooltip.position({
            x : mousePos.x + 5,
            y : mousePos.y + 5
        });
        tooltip.text(this.getAttrs().regionName);
        tooltip.show();
        tooltipLayer.batchDraw();
        stage.draw();
    });
    stage.get('.regionRect').on("mousedown", function() {
        drag = false;
        rectClick = true;
        window.location.replace("/plant/view/");

    });
    stage.get('.regionRect').on("mouseout", function() {
        tooltip.hide();
        tooltipLayer.draw();
        stage.draw();
    });

    stage.get('.regionRect').on("mousemove", function() {
        var mousePos = stage.getPointerPosition();
        tooltip.position({
            x : mousePos.x + 5,
            y : mousePos.y + 5
        });
        tooltip.text(this.getAttrs().regionName);
        tooltip.show();
        tooltipLayer.batchDraw();
        stage.draw();
    });
    stage.get('.regionRect').on("mousedown", function() {
        drag = false;
        rectClick = true;
        window.location.replace("/plant/view/" + this.getAttrs().plantId);

    });
    stage.get('.regionRect').on("mouseout", function() {
        tooltip.hide();
        tooltipLayer.draw();
        stage.draw();
    });
};
imageObj.src = imageFile;



