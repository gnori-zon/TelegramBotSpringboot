package gnorizon.SpringTestReportsBot.repository.Entity.AttributesReport;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class InformationAboutTestCases {
    // map<module name, list<total, closed>> | map<имя модуля, list<всего, закрыто>>
    Map<String, List<Integer>> informationAboutModules;

}
