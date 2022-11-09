package gnorizon.SpringTestReportsBot.repository.Entity.AttributesReport;

import lombok.Data;

import java.util.Map;
@Data
public class InformationAboutTestObjects {
    // название и количество багов
    private Map<String,Integer> functionalityInformation;
}
