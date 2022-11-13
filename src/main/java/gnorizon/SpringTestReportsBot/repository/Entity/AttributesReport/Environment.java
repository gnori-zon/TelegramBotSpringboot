package gnorizon.SpringTestReportsBot.repository.Entity.AttributesReport;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class Environment{
    private String standName;
    private String [] OSNames;
    // for Intermediate Map<Name,List<Total,Closed>> | для Промежуточного  Map<Название,List<Всего,Закрыто>>
    private Map<String,List<Integer>> OSInformation;
    private Map<String,List<Integer>> BrowsersInformation;

}
