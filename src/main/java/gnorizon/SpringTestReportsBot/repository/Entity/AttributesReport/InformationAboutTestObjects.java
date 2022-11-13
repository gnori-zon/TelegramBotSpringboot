package gnorizon.SpringTestReportsBot.repository.Entity.AttributesReport;

import lombok.Data;

import java.util.Map;
@Data
public class InformationAboutTestObjects {
    // map<func name, list<total, closed>> | map<имя функции, list<всего, закрыто>>
    private Map<String,Integer> functionalityInformation;
}
