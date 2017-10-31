var width = window.innerWidth;
var height = window.innerHeight;
var c1Position;
var rectBottomRight;
var currentRect;
var drag = false;
var stage = new Konva.Stage({
  container: 'canvas',
  width: width,
  height: height
});
var layer = new Konva.Layer();

var imageObj = new Image();

imageObj.onload = function() {
  var yoda = new Konva.Image({
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
  });

  layer.on('mousemove', function() {
    /*writeMessage('x: ' + x + ', y: ' + y);*/
    if(drag) {
      c2Position = stage.getPointerPosition();
      currentRect.height(c2Position.y - c1Position.y); 
      currentRect.width(c2Position.x - c1Position.x);
      layer.draw();     
    }
  });

  // add the shape to the layer
  layer.add(yoda);
  
  // add the layer to the stage
  stage.add(layer);
  
  function createNewRectangle() {
      var rect = new Konva.Rect({
        x: c1Position.x,
        y: c1Position.y,
        width: 1,
        height: 1,
        stroke: 'black',
        strokeWidth: 2
      });
    return rect;
  }
/*  var json = stage.toJSON();
console.log(json);*/
};
imageObj.src = 'http://1.bp.blogspot.com/-UEPCdxt7NpA/VKPQ5nYf5vI/AAAAAAAAMo0/-yf25tzfP_s/s1600/totalbuild_bird_mats.jpg';
$("#canvasOutputBtn").click(function() {
  $('#outputTextArea').val('');
  $('#outputTextArea').val(stage.toJSON());
});
