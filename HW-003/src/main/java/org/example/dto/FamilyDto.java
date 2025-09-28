package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import static org.example.util.constant.ErrorMessageConstant.*;
import static org.example.util.constant.RegexConstant.FAMILY_NAME_REGEX;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDto {

	private UUID familyId;

	@NotBlank(message = WARNING_FAMILY_NAME_NOT_NULL_MESSAGE)
	@Pattern(regexp = FAMILY_NAME_REGEX,
			message = WARNING_ENTER_FAMILY_NAME_MESSAGE
	)
	private String familyName;

	private PersonDto creatorDto;

	private List<PersonDto> membersDto;
}
