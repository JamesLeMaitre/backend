package bj.gouv.sineb.demandeservice.application.code.utils;

import bj.gouv.sineb.demandeservice.application.code.repository.common.*;
import bj.gouv.sineb.demandeservice.application.code.repository.membre.DemandeCompteRepository;
import bj.gouv.sineb.demandeservice.application.code.repository.membre.TypeCompteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntityDataCodeGenerationService {

    private final TypeCompteRepository typeCompteRepository;

    private final TypeDemandeRepository typeDemandeRepository;

    private final TypeStructureRepository typeStructureRepository;

    private final PaysRepository paysRepository;

    private final CiviliteRepository civiliteRepository;

    private final DemandeCompteRepository demandeCompteRepository;


    public String generateEntityDataCode(String tableName) {
        return switch (tableName) {
            case "Civilite" -> "CVT-" + civiliteRepository.count();
            case "Pays" -> "PYS-" + paysRepository.count();
            case "TypeDemande" -> "TPD-" + typeDemandeRepository.count();
            case "TypeCompte" -> "TPC-" + typeCompteRepository.count();
            case "TypeStructure" -> "TPS-" + typeStructureRepository.count();
            case "DemandeCompte" -> "DDC-" + demandeCompteRepository.count();
            default -> {
                log.info("EntityDataCodeGenerationRepository default behavior called ...");
                yield "";
            }
        };
    }


}
