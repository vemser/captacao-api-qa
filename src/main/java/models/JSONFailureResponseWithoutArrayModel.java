package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.junit.Ignore;

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
