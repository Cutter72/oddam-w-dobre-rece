package pl.oddam.model.dto;

import lombok.Data;

@Data
public class StepOneToThreeParameters {
    private Long[] needIdTab;
    private Long[] targetIdTab;
    private Long cityId;
    private String organizationName;
    private Integer bags;
}
