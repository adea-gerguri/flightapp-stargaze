package shared.mongoUtils;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InsertResult {
  private List<String> insertedIds;

  public static InsertResult fromId(String insertedId) {
    InsertResult result = new InsertResult();
    result.insertedIds.add(insertedId);
    return result;
  }
}
