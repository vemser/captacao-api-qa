package models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JSONFailureResponseWithoutArrayModel {

    private String timestamp;
    private Integer status;
    private String message;
    private String errors;
}
