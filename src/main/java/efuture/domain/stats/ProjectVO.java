package efuture.domain.stats;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017-07-07.
 */
@Data
public class ProjectVO {
    private String project_name;
    private int optionSize = 0;
    private ArrayList<HashMap<String,Object>> optionList;
}
