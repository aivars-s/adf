package adf.homework.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Long id;
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank private String phone;
    @NotBlank @Email private String email;

    private List<OrderDTO> orders;

}
