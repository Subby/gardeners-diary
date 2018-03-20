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
    $("#errorContainer").hide();
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

  function createNewRectangle() {
	  var rect = new Konva.Rect({
		x: c1Position.x,
		y: c1Position.y,
		width: 1,
		height: 1,
		stroke: 'black',
		strokeWidth: 2,
		name: 'regionRect',
        regionName: 'someName',
        plantId: ''
	  });
      rect.on("mousemove", function() {
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
      rect.on("mousedown", function() {
          drag = false;
          rectClick = true;
          window.location.replace("/plant/view/" + this.getAttrs().plantId);

      });
      rect.on("mouseout", function() {
          tooltip.hide();
          tooltipLayer.draw();
          stage.draw();
      });
	return rect;
  }
};
imageObj.src = imageFile;
$("#addPlantBtn").click(function() {
    var nameVal = $("#plantName").val();
    var typeVal = $("#plantType").val();
    if(!nameVal || !typeVal) {
        $("#errorContainer").show();
        return;
    }
    $.post("/plant/add", {
        name: nameVal,
        type: $("#plantType").val(),
        gardenId: $("#gardenId").val()
    } ,function(data){
        if(data.status === "success") {
            $("#errorContainer").hide();
            var apiImage = getPlantTypeImage();
            var rectImage = new Image();
            rectImage.onload = function() {
                currentRect.fillPatternImage(rectImage);
            };
            rectImage.src = apiImage;
            showModal(false);
            showToast("Success", "The plant " + data.plant_name + " was added to the system. Click <a href='/plant/view/" + data.id +  "'>here</a> to view information.", "success");
            currentRect.getAttrs().regionName = data.plant_name;
            currentRect.getAttrs().plantId = data.id;
            saveGardenState();
            plantTable.ajax.reload();
        } else {
            removeCurrentRect();
            showToast("Information", "The plant was not added.", "info");
        }
    });

});

$("#modal-toggle").change(function() {
	//If modal has been closed using the close button, remove the rectangle.
	removeCurrentRect();
    showToast("Information", "The plant was not added.", "info");
});

function getPlantTypeImage() {
    var type = $("#plantType").val().toLowerCase();
    $.get("/plantinfo/" + type,function(data){
        if(data.status === "Ok") {
           return data.image;
        }
    });
    return null;
}

function saveGardenState() {
    $.post("/savegardenjson", { json:layer.toJSON()} ,function(data){
        if(data === "success") {
            showToast("Success", "The drawn garden regions were stored sucessfully.", "success")
        }
    });
}


