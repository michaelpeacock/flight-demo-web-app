



function handleFlightUpdate(flight) {
    console.log("handle flight update: flight count");
    flight.forEach(flight => {
      var entity = viewer.entities.getOrCreateEntity(flight.id);
      var position = Cesium.Cartesian3.fromDegrees(flight.longitude, flight.latitude, flight.altitude);
      var heading = Cesium.Math.toRadians(0);
      var pitch = Cesium.Math.toRadians(15.0);
      var roll = Cesium.Math.toRadians(0.0);
      var orientation = Cesium.Transforms.headingPitchRollQuaternion(position, new Cesium.HeadingPitchRoll(heading, pitch, roll));

      
      entity.description = buildDescription(flight);
      entity.position = position;
      entity.orientation = orientation; 
      //entity.point = { pixelSize: 10, color: Cesium.Color.RED };
      entity.billboard = {
        color : Cesium.Color.BLACK.withAlpha(0.5),
        rotation : Cesium.Math.toRadians(-flight.heading),
        image : 'images/airplane-4-64.png',
        width : 32,
        height : 32
      };
      /*
        const pointEntity = viewer.entities.add({
        description: `ID: ${flight.id} data point at (${flight.longitude}, ${flight.latitude})`,
        position: Cesium.Cartesian3.fromDegrees(flight.longitude, flight.latitude, flight.altitude),
        point: { pixelSize: 10, color: Cesium.Color.RED }
        });
    */

      });

}

function buildDescription(flight) {
  var description = "\
    <p> \
      ID: " + flight.id + "<br> \
      Country: " + flight.originCountry + "<br><br> \
      Lat/Long: " + flight.latitude + " / " + flight.longitude + " <br> \
      Alitude: " + flight.altitude + " <br> \
      Heading: " + flight.heading + "<br> \
      Speed: " + flight.speed + " <br><br> \
      Last Update: " + flight.updateTime + " <br> \
      </p>";
  return description;
}