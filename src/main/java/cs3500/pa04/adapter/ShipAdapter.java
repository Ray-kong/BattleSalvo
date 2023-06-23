package cs3500.pa04.adapter;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa04.json.data.CoordJson;
import cs3500.pa04.json.data.ShipJson;
import java.util.List;

/**
 * An adapter class for the Ship Data Definition
 */
public class ShipAdapter {

  /**
   * The reason why this method is necessary for the reason that
   * our data definition did not match the server's. Therefore, we
   * have this method in JsonUtils to allow us to translate a Ship
   * into the server represented ShipJson
   *
   * @param ship Ship object to convert
   * @return ShipJson output that correlates to the given Ship
   */
  public static ShipJson shipToJson(Ship ship) {
    /*  this is assuming that in the Ship construction in PA03, that the Ship Coords
     * are generated from the top left always  */
    List<Coord> shipCoords = ship.getShipCoords();

    Coord starting = shipCoords.get(0);
    for (Coord c : shipCoords) {
      if (c.getX() < starting.getX() || c.getY() < starting.getY()) {
        starting = c;
      }
    }

    CoordJson startingCoord = CoordAdapter.coordToJson(starting);

    int length = ship.getShipType().getSize();

    String orientation;
    if (shipCoords.get(0).getX() == shipCoords.get(1).getX()) {
      orientation = "VERTICAL";
    } else {
      orientation = "HORIZONTAL";
    }

    return new ShipJson(startingCoord, length, orientation);
  }
}
