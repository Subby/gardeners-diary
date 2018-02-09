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

function showModal(value) {
	$("#modal-toggle").prop('checked', value);
}

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
	x: 50,
	y: 50,
	height: 250,
	width: 500,
	image: imageObj,
    id: 'gardenImage'
  });

  layer.on('mousedown', function() {
  	if(!rectClick) {
        c1Position = stage.getPointerPosition();
        currentRect = createNewRectangle();
        layer.add(currentRect);
        layer.draw();
        drag = true;
    }
  });

  layer.on('mouseup', function() {
  	if(!rectClick) {
        drag = false;
        showModal(true);
	}
  });

  layer.on('mousemove', function() {
	if(drag) {
	  c2Position = stage.getPointerPosition();
	  currentRect.height(c2Position.y - c1Position.y);
	  currentRect.width(c2Position.x - c1Position.x);
	  layer.draw();
	}
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

  stage.get('#region').on("mousemove", function() {
      var mousePos = stage.getPointerPosition();
      tooltip.position({
          x : mousePos.x + 5,
          y : mousePos.y + 5
      });
      tooltip.text(this.attrs.regionName);
      tooltip.show();
      tooltipLayer.batchDraw();
      stage.draw();
  });
  stage.get('#region').on("mousedown", function() {
      drag = false;
      rectClick = true;
      window.location.replace("/plant/");

  });
  stage.get('#region').on("mouseout", function() {
      tooltip.hide();
      tooltipLayer.draw();
      stage.draw();
  });

  function createNewRectangle() {
	  var rect = new Konva.Rect({
		x: c1Position.x,
		y: c1Position.y,
		width: 1,
		height: 1,
		stroke: 'black',
		strokeWidth: 2,
		id: 'region',
        regionName: 'someName'
	  });
	return rect;
  }
};
imageObj.src = imageFile;
$("#canvasOutputBtn").click(function() {
  //$('#outputTextArea').val('');
  $.post("/savegardenjson", { json:layer.toJSON()} ,function(data){
      if(data === "success") {
          $.toast({
              heading: 'Success',
              text: 'The drawn garden regions were stored sucessfully.',
              showHideTransition: 'slide',
              icon: 'success',
              loader: false
          });
      }
  });
});

$("#addPlantBtn").click(function() {
	showModal(false);
});

$("#modal-toggle").change(function() {
	//If modal has been closed using the close button, remove the rectangle.
	removeCurrentRect();
});


