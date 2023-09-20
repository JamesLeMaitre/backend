package bj.gouv.sineb.authservice.application.code.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

	private String mailFrom;
	private String mailTo;
	private String mailCc;
	private String mailBcc;
	private String mailSubject;
	private String mailContent;
	private String contentType = "text/plain";
	private List<Object> attachments;
}