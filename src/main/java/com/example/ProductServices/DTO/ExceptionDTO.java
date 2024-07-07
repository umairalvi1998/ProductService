package com.example.ProductServices.DTO;
/* Lets say you want to customize the exception response and want to send some more information.
For example :-  with the exception information you can also tell the resolution.
For this you can use the ExceptionDTO
 */

import lombok.Setter;
import lombok.Getter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ExceptionDTO {
    private String message;
  private String solution;
  private int statusCode;
  private LocalDateTime timestamp;

}
