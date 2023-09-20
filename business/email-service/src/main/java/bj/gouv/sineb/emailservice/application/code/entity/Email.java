package bj.gouv.sineb.emailservice.application.code.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name = "emails")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Email implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "mail_from")
    private String mailFrom;

    @Column(name = "mail_to")
    private String mailTo;

    @Column(name = "mail_cc")
    private String mailCc;

    @Column(name = "mail_bcc")
    private String mailBcc;

    @Column(name = "mail_subject")
    private String mailSubject;

    @Column(name = "mail_content")
    private String mailContent;

//    private List<String> attachments;

}
