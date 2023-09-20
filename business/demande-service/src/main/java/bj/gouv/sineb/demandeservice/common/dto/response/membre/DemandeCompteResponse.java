package bj.gouv.sineb.demandeservice.common.dto.response.membre;

import bj.gouv.sineb.common.enums.DataStatus;
import bj.gouv.sineb.demandeservice.application.code.entity.common.Civilite;
import bj.gouv.sineb.demandeservice.application.code.entity.common.Pays;
import bj.gouv.sineb.demandeservice.application.code.entity.membre.TypeCompte;
import bj.gouv.sineb.demandeservice.application.code.entity.common.TypeStructure;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DemandeCompteResponse implements Serializable {

    private UUID trackingId;
    private String nom;
    private String prenom;
    private String numeroIFU;
    private String adresse;
    private Date dateNaissance;
    private String lieuNaissance;
    private String mail;
    private String photo;
    private String raisonSocial;
    private String docPersonnelInstitution;
    private String pieceIdentite;
    private String pieceJustificatif;
    private String validated;
    private TypeCompte typeCompte;
    private TypeStructure typeStructure;
    private Civilite civilite;
    private Pays pays;
    private DataStatus dataStatus;
    private String codeAuto;
    private String createdBy;
    private String updatedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateCreated;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateUpdated;

}
