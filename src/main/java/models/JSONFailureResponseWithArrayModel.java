package models;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JSONFailureResponseWithArrayModel {

    private String timestamp;
    private Integer status;
    private String message;
    private List<String> errors;

}
