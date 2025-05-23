package shared.mongoUtils;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InsertResult {
  private List<String> insertedIds = new ArrayList<>();

  public static InsertResult fromId(String insertedId) {
    InsertResult result = new InsertResult();

    result.insertedIds.add(insertedId);
    return result;
  }
}
