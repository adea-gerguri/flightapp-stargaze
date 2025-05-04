package airline.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AirlineEntity {

  private String id;
  private String name;
  private String country;
  private String city;
  private String code;
  private int planeCount;
}
