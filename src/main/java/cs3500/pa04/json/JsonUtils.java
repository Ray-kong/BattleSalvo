package cs3500.pa04.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Coord;
import cs3500.pa04.adapter.CoordAdapter;
import cs3500.pa04.json.data.CoordJson;
import cs3500.pa04.json.data.CoordListJson;
import cs3500.pa04.json.response.MessageJson;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that provides functionality to serialize and deserialize JSON data.
 */
public class JsonUtils {

  /**
   * Serializes a list of coordinates into a JsonNode, along with a method name.
   *
   * @param methodName the method name to be included in the JSON data
   * @param list       the list of coordinates to be serialized
   * @return a JsonNode that represents the serialized data
   */
  public static JsonNode serializeCoordList(String methodName, List<Coord> list) {
    ObjectMapper mapper = new ObjectMapper();
    CoordJson[] coordsArrayJson = new CoordJson[list.size()];
    for (int i = 0; i < list.size(); i++) {
      coordsArrayJson[i] = CoordAdapter.coordToJson(list.get(i));
    }
    CoordListJson listJson = new CoordListJson(coordsArrayJson);
    JsonNode listJsonNode = mapper.convertValue(listJson, JsonNode.class);

    MessageJson msgJson = new MessageJson(methodName, listJsonNode);
    return mapper.convertValue(msgJson, JsonNode.class);
  }

  /**
   * Deserializes a JsonNode into a list of coordinates.
   *
   * @param json the JsonNode to be deserialized
   * @return a list of coordinates that represents the deserialized data
   */
  public static List<Coord> deserializeCoordList(JsonNode json) {
    ObjectMapper mapper = new ObjectMapper();
    CoordListJson jsonList = mapper.convertValue(json, CoordListJson.class);
    List<Coord> list = new ArrayList<>();
    for (CoordJson cj : jsonList.coords()) {
      list.add(CoordAdapter.jsonToCoord(cj));
    }
    return list;
  }
}
