package bj.gouv.sineb.emailservice.old.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "mail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mailTo;

	private String mailFrom;

	private String name;

	private String subject;

	private String text;
	private Instant dateCreate;
}