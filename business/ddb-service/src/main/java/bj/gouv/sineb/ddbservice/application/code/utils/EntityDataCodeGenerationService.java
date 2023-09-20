package bj.gouv.sineb.ddbservice.application.code.utils;


import bj.gouv.sineb.ddbservice.application.code.repository.aae.*;
import bj.gouv.sineb.ddbservice.application.code.repository.common.*;
import bj.gouv.sineb.ddbservice.application.code.repository.rc_rbdd.*;
import bj.gouv.sineb.ddbservice.application.code.repository.ppe.BeneficiaireCategoryProjetRepository;
import bj.gouv.sineb.ddbservice.application.code.repository.ppe.SourceFinancementProjetRepository;
import bj.gouv.sineb.ddbservice.application.code.repository.sie.BlocActiviteEconomiqueRepository;
import bj.gouv.sineb.ddbservice.application.code.repository.sie.BrancheActiviteEconomiqueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EntityDataCodeGenerationService {

    private final CiviliteRepository civiliteRepository;
    private final FormeJuridiqueActeurRepository formeJuridiqueActeurRepository;
    private final MotifQualificationRepository motifQualificationRepository;
    private final ProfessionRepository professionRepository;
    private final RoleActeurRepository roleActeurRepository;
    private final TitreActeurRepository titreActeurRepository;


    private final ArrondissementRepository arrondissementRepository;
    private final CategoryEltRepository categoryEltRepository;
    private final ComiteRepository comiteRepository;
    private final CommuneRepository communeRepository;
    private final DepartementRepository departementRepository;
    private final DomaineInterventionPrincipaleRepository domaineInterventionPrincipaleRepository;
    private final DomaineInterventionSecondaireRepository domaineInterventionSecondaireRepository;
    private final EtatRepository etatRepository;
    private final FormeEnergiePrimaireRepository formeEnergiePrimaireRepository;
    private final FormeEnergieSecondaireRepository formeEnergieSecondaireRepository;
    private final ModeReceptionRepository modeReceptionRepository;
    private final PaysRepository paysRepository;
    private final SousCategoryEltRepository sousCategoryEltRepository;
    private final VilleRepository villeRepository;
    private final BeneficiaireCategoryProjetRepository beneficiaireCategoryProjetRepository;
    private final SourceFinancementProjetRepository sourceFinancementProjetRepository;
    private final DroitAccesRessourceDocumentaireRepository droitAccesRessourceDocumentaireRepository;
    private final FormatRessourceDocumentaireRepository formatRessourceDocumentaireRepository;
    private final LangueRessourceDocumentaireRepository langueRessourceDocumentaireRepository;
    private final NatureRessourceDocumentaireRepository natureRessourceDocumentaireRepository;
    private final PorteeRessourceDocumentaireRepository porteeRessourceDocumentaireRepository;
    private final TypeRessourceDocumentaireRepository typeRessourceDocumentaireRepository;
    private final BlocActiviteEconomiqueRepository blocActiviteEconomiqueRepository;
    private final BrancheActiviteEconomiqueRepository brancheActiviteEconomiqueRepository;
    private final TypeCompteRepository typeCompteRepository;
    private final TypeStructureRepository typeStructureRepository;



    public EntityDataCodeGenerationService(CiviliteRepository civiliteRepository, FormeJuridiqueActeurRepository formeJuridiqueActeurRepository, MotifQualificationRepository motifQualificationRepository, ProfessionRepository professionRepository, RoleActeurRepository roleActeurRepository, TitreActeurRepository titreActeurRepository, ArrondissementRepository arrondissementRepository, CategoryEltRepository categoryEltRepository, ComiteRepository comiteRepository, CommuneRepository communeRepository, DepartementRepository departementRepository, DomaineInterventionPrincipaleRepository domaineInterventionPrincipaleRepository, DomaineInterventionSecondaireRepository domaineInterventionSecondaireRepository, EtatRepository etatRepository, FormeEnergiePrimaireRepository formeEnergiePrimaireRepository, FormeEnergieSecondaireRepository formeEnergieSecondaireRepository, ModeReceptionRepository modeReceptionRepository, PaysRepository paysRepository, SousCategoryEltRepository sousCategoryEltRepository, VilleRepository villeRepository, BeneficiaireCategoryProjetRepository beneficiaireCategoryProjetRepository, SourceFinancementProjetRepository sourceFinancementProjetRepository, DroitAccesRessourceDocumentaireRepository droitAccesRessourceDocumentaireRepository, FormatRessourceDocumentaireRepository formatRessourceDocumentaireRepository, LangueRessourceDocumentaireRepository langueRessourceDocumentaireRepository, NatureRessourceDocumentaireRepository natureRessourceDocumentaireRepository, PorteeRessourceDocumentaireRepository porteeRessourceDocumentaireRepository, TypeRessourceDocumentaireRepository typeRessourceDocumentaireRepository, BlocActiviteEconomiqueRepository blocActiviteEconomiqueRepository, BrancheActiviteEconomiqueRepository brancheActiviteEconomiqueRepository, TypeCompteRepository typeCompteRepository, TypeStructureRepository typeStructureRepository) {
        this.civiliteRepository = civiliteRepository;
        this.formeJuridiqueActeurRepository = formeJuridiqueActeurRepository;
        this.motifQualificationRepository = motifQualificationRepository;
        this.professionRepository = professionRepository;
        this.roleActeurRepository = roleActeurRepository;
        this.titreActeurRepository = titreActeurRepository;
        this.arrondissementRepository = arrondissementRepository;
        this.categoryEltRepository = categoryEltRepository;
        this.comiteRepository = comiteRepository;
        this.communeRepository = communeRepository;
        this.departementRepository = departementRepository;
        this.domaineInterventionPrincipaleRepository = domaineInterventionPrincipaleRepository;
        this.domaineInterventionSecondaireRepository = domaineInterventionSecondaireRepository;
        this.etatRepository = etatRepository;
        this.formeEnergiePrimaireRepository = formeEnergiePrimaireRepository;
        this.formeEnergieSecondaireRepository = formeEnergieSecondaireRepository;
        this.modeReceptionRepository = modeReceptionRepository;
        this.paysRepository = paysRepository;
        this.sousCategoryEltRepository = sousCategoryEltRepository;
        this.villeRepository = villeRepository;
        this.beneficiaireCategoryProjetRepository = beneficiaireCategoryProjetRepository;
        this.sourceFinancementProjetRepository = sourceFinancementProjetRepository;
        this.droitAccesRessourceDocumentaireRepository = droitAccesRessourceDocumentaireRepository;
        this.formatRessourceDocumentaireRepository = formatRessourceDocumentaireRepository;
        this.langueRessourceDocumentaireRepository = langueRessourceDocumentaireRepository;
        this.natureRessourceDocumentaireRepository = natureRessourceDocumentaireRepository;
        this.porteeRessourceDocumentaireRepository = porteeRessourceDocumentaireRepository;
        this.typeRessourceDocumentaireRepository = typeRessourceDocumentaireRepository;
        this.blocActiviteEconomiqueRepository = blocActiviteEconomiqueRepository;
        this.brancheActiviteEconomiqueRepository = brancheActiviteEconomiqueRepository;
        this.typeCompteRepository = typeCompteRepository;
        this.typeStructureRepository = typeStructureRepository;
    }


    public String generateEntityDataCode(String tableName){
        return switch (tableName) {
            case "Civilite" -> "CVT-" + civiliteRepository.count();
            case "FormeJuridiqueActeur" -> "FAJ-" + formeJuridiqueActeurRepository.count();
            case "MotifQualification" -> "MQ-" + motifQualificationRepository.count();
            case "Profession" -> "PRF-" + professionRepository.count();
            case "RoleActeur" -> "RA-" + roleActeurRepository.count();
            case "TitreActeur" -> "TA-" + titreActeurRepository.count();
            case "Arrondissement" -> "ARD-" + arrondissementRepository.count();
            case "CategoryElt" -> "CE-" + categoryEltRepository.count();
            case "Comite" -> "CMT-" + comiteRepository.count();
            case "Commune" -> "CMN-" + communeRepository.count();
            case "Departement" -> "DP-" + departementRepository.count();
            case "DomaineInterventionPrincipale" -> "DIP-" + domaineInterventionPrincipaleRepository.count();
            case "DomaineInterventionSecondaire" -> "DIS-" + domaineInterventionSecondaireRepository.count();
            case "Etat" -> "ET-" + etatRepository.count();
            case "FormeEnergiePrimaire" -> "FEP-" + formeEnergiePrimaireRepository.count();
            case "FormeEnergieSecondaire" -> "FES-" + formeEnergieSecondaireRepository.count();
            case "ModeReception" -> "MR-" + modeReceptionRepository.count();
            case "Pays" -> "PYS-" + paysRepository.count();
            case "SousCategoryElt" -> "SCE-" + sousCategoryEltRepository.count();
            case "Ville" -> "VL-" + villeRepository.count();
            case "BeneficiaireCategoryProjet" -> "CBP-" + beneficiaireCategoryProjetRepository.count();
            case "SourceFinancementProjet" -> "SFP-" + sourceFinancementProjetRepository.count();
            case "DroitAccesRessourceDocumentaire" -> "DARD-" + droitAccesRessourceDocumentaireRepository.count();
            case "FormatRessourceDocumentaire" -> "FRD-" + formatRessourceDocumentaireRepository.count();
            case "LangueRessourceDocumentaire" -> "LRD-" + langueRessourceDocumentaireRepository.count();
            case "NatureRessourceDocumentaire" -> "NRD-" + natureRessourceDocumentaireRepository.count();
            case "PorteeRessourceDocumentaire" -> "PRD-" + porteeRessourceDocumentaireRepository.count();
            case "TypeRessourceDocumentaire" -> "TRD-" + typeRessourceDocumentaireRepository.count();
            case "BlocActiviteEconomique" -> "BLAE-" + blocActiviteEconomiqueRepository.count();
            case "BrancheActiviteEconomique" -> "BRAE-" + brancheActiviteEconomiqueRepository.count();
            case "TypeCompte" -> "TC-" + typeCompteRepository.count();
            case "TypeStructure" -> "TS-" + typeStructureRepository.count();
            default -> {
                log.info("EntityDataCodeGenerationRepository default behavior called ...");
                yield "";
            }
        };
    }
}
