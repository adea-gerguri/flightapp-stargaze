package shared;

import jakarta.validation.constraints.Min;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
public class PaginationQueryParams {
    @DefaultValue("0")
    @QueryParam("skip")
    private int skip;

    @DefaultValue("5")
    @QueryParam("limit")private int limit;

    @DefaultValue("1")
    @QueryParam("sort")
    @Range(min=0, max=1)
    private int sort;

    private String sortField;

    public int setSkip(int skip) {
        return this.skip=skip;
    }
    public int setLimit(int limit) {
        return this.limit=limit;
    }
    public int setSort(String sort){
        if(sort.equalsIgnoreCase("asc")){
            return 1;
        }
        else{
            return -1;
        }
    }
}
