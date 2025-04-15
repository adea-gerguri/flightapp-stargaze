package shared.mongoUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeleteResult {
    private int deletedCount;

    public static DeleteResult fromCount(int count) {
        return new DeleteResult(count);
    }
}
