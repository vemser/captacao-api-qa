package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONFailureResponseWithoutArrayModel {

    private String timestamp;
    private Integer status;
    private String message;
    private String errors;
}
