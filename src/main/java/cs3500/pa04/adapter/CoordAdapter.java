package cs3500.pa04.adapter;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.enumerations.CoordType;
import cs3500.pa04.json.data.CoordJson;

/**
 * Coord Adapter class
 */
public class CoordAdapter {

  /**
   *  Converts a CoordJson record into a default Coord Object
   *
   * @param coordJson   CoordJson to covert
   * @return            Converted Coord Object
   */
  public static Coord jsonToCoord(CoordJson coordJson) {
    //by default a CoordJson will be transformed into an Ocean Type Coord
    return new Coord(coordJson.x(), coordJson.y(), CoordType.EMPTY);
  }

  /**
   * Converts a Coord Object into a CoordJson record
   *
   * @param coord   Coord Object to convert
   * @return        Converted CoordJson record
   */
  public static CoordJson coordToJson(Coord coord) {
    return new CoordJson(coord.getX(), coord.getY());
  }
}
