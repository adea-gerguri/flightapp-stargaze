package shared.mongoUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateResult {
    private long matchedCount;
    private long modifiedCount;

    public static UpdateResult fromCounts(long matchedCount, long modifiedCount) {
        return new UpdateResult(matchedCount, modifiedCount);
    }
}


