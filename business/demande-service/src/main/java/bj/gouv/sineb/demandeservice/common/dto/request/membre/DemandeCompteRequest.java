package bj.gouv.sineb.demandeservice.common.dto.request.membre;

import bj.gouv.sineb.demandeservice.application.code.entity.common.Civilite;
import bj.gouv.sineb.demandeservice.application.code.entity.common.Pays;
import bj.gouv.sineb.demandeservice.application.code.entity.common.TypeDemande;
import bj.gouv.sineb.demandeservice.application.code.entity.common.TypeStructure;
import bj.gouv.sineb.demandeservice.application.code.entity.membre.TypeCompte;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DemandeCompteRequest implements Serializable {
    private String nom;
    private String prenom;
    private String numeroIFU;
    private String adresse;
//    private Date dateNaissance;
    private String lieuNaissance;
    private String mail;
    private String photo;
    private String raisonSocial;
    private String docPersonnelInstitution;
    private String pieceIdentite;
    private String pieceJustificatif;
    private TypeDemande typeDemande;
    private TypeCompte typeCompte;
    private TypeStructure typeStructure;
    private Civilite civilite;
    private Pays pays;

}
