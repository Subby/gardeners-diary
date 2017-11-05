var width = window.innerWidth;
var height = window.innerHeight;
var c1Position;
var currentRect;
var drag = false;
var stage = new Konva.Stage({
  container: 'canvas',
  width: width,
  height: height
});
var layer = new Konva.Layer();

var imageObj = new Image();

function showModal(value) {
	$("#modal-toggle").prop('checked', value);
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
	image: imageObj
  });

  layer.on('mousedown', function() {
	c1Position = stage.getPointerPosition();
	currentRect = createNewRectangle();
	layer.add(currentRect);
	layer.draw();
	drag = true;
  });

  layer.on('mouseup', function() {
	drag = false;
	showModal(true);
  });

  layer.on('mousemove', function() {
	if(drag) {
	  c2Position = stage.getPointerPosition();
	  currentRect.height(c2Position.y - c1Position.y);
	  currentRect.width(c2Position.x - c1Position.x);
	  layer.draw();
	}
  });

  // add the shape to the layer
  layer.add(gardenImage);
  
  // add the layer to the stage
  stage.add(layer);
  
  function createNewRectangle() {
	  var rect = new Konva.Rect({
		x: c1Position.x,
		y: c1Position.y,
		width: 1,
		height: 1,
		stroke: 'black',
		strokeWidth: 2,
		id: 'Rect'
	  });
	return rect;
  }
};
imageObj.src = 'http://1.bp.blogspot.com/-UEPCdxt7NpA/VKPQ5nYf5vI/AAAAAAAAMo0/-yf25tzfP_s/s1600/totalbuild_bird_mats.jpg';
$("#canvasOutputBtn").click(function() {
  $('#outputTextArea').val('');
  $('#outputTextArea').val(stage.toJSON());
});

$("#addPlantBtn").click(function() {
	showModal(false);
});

$("#modal-toggle").change(function() {
	//If modal has been closed using the close button, remove the rectangle.
	removeCurrentRect();
});


