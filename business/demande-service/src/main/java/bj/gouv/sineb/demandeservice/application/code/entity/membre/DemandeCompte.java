package bj.gouv.sineb.demandeservice.application.code.entity.membre;

import bj.gouv.sineb.common.domain.CommonEntity;
import bj.gouv.sineb.demandeservice.application.code.entity.common.Civilite;
import bj.gouv.sineb.demandeservice.application.code.entity.common.Pays;
import bj.gouv.sineb.demandeservice.application.code.entity.common.TypeDemande;
import bj.gouv.sineb.demandeservice.application.code.entity.common.TypeStructure;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "membre_demande")
@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemandeCompte extends CommonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID trackingId;
    @Column(name = "nom")
    private String name;

    @Column(name = "prenom")
    private String firstName;

    @Column(name = "numer_ifu")
    private String ifuNumber;

    @Column(name = "email")
    private String mail;

    @Column(name = "adresse")
    private String address;

    @Column(name = "date_naissance")
    private Date dateBirth;

    @Column(name = "lieu_naissance")
    private String birthPlace;

    @Column(name = "profession_en_cours")
    private String professionInProgress;

    @Column(name = "niveau_formation")
    private String trainingLevel;

//    @Column(name = "acc")
//    private String seniorityField;

    @Column(name = "phone_number ")
    private String phoneNumber;

    @Column(name = "photo")
    private String photo;

    @Column(name = "raison_social")
    private String socialReason;

    @Column(name = "doc_personnel_institution")
    private String docPersonnelInstitution;

    @Column(name = "piece_identite")
    private String identityPiece;

    @Column(name = "piece_justificatif")
    private String supportingDocument;

    private String validated;

    @ManyToOne
    @JoinColumn(name = "id_type_demande")
    private TypeDemande typeDemande;

    @ManyToOne
    @JoinColumn(name = "id_type_compte")
    private TypeCompte typeCompte;

    @ManyToOne
    @JoinColumn(name = "id_type_structure")
    private TypeStructure typeStructure;

    @ManyToOne
    @JoinColumn(name = "id_civilite")
    private Civilite civilite;

    @ManyToOne
    @JoinColumn(name = "id_pays")
    private Pays pays;

}
