package bj.gouv.sineb.authservice.application.code.dto.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailData {
	private String cusNo;
	private String cusName;
	private String cusTel;
	private String cusAddress;
}