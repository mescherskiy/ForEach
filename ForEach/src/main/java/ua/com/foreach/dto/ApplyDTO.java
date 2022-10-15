package ua.com.foreach.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplyDTO {
    private String projectName;
    private String candidateFullname;
    private String candidateUsername;
    private String languages;

    public static ApplyDTO of(String projectName, String candidateFullname, String candidateUsername, String languages) {
        return new ApplyDTO(projectName, candidateFullname, candidateUsername, languages);
    }
}
