package bj.gouv.sineb.authservice.application.code.mapper;


//@Component
public class GroupeRessourceMapper {

    /*private final RessourceMapper ressourceMapper;
    private final GroupeMapper groupeMapper;
    private final GroupeRepository groupeRepository;
    private final RessourceRepository ressourceRepository;


    public GroupeRessourceMapper(RessourceMapper ressourceMapper,
                                 GroupeMapper groupeMapper,
                                 GroupeRepository groupeRepository,
                                 RessourceRepository ressourceRepository){
        this.ressourceMapper = ressourceMapper;
        this.groupeMapper = groupeMapper;
        this.groupeRepository = groupeRepository;
        this.ressourceRepository = ressourceRepository;
    }



    public GroupeRessourceResponse toDto(GroupeRessourceScope entity){
        if (entity!=null){
            return GroupeRessourceResponse.builder()
                    .trackingId(UUID.randomUUID())
                    .groupeResponse(groupeMapper.toDto(entity.getGroupe()))
                    .ressourceResponse(ressourceMapper.toDto(entity.getRessource()))
                    .build();
        }
        return null;
    }

    public GroupeRessourceScope toEntity(GroupeRessourceRequest event){
        Optional<Groupe> groupeOptional = groupeRepository.findByTrackingId(event.getGroupeTrackingId());
        Optional<Ressource> ressourceOptional = ressourceRepository.findByTrackingId(event.getRessourceTrackingId());
        if (groupeOptional.isEmpty())
            throw new CustomNotFoundException("Groupe with trackingId: "+event.getGroupeTrackingId()+" not found !");
        else if (ressourceOptional.isEmpty())
                throw new CustomNotFoundException("Ressource with trackingId: "+event.getRessourceTrackingId()+" not found !");
        else {
            return GroupeRessourceScope.builder()
                    .id(UUID.randomUUID())
                    .trackingId(UUID.randomUUID())
                    .groupe(groupeOptional.get())
                    .ressource(ressourceOptional.get())
                    .deleted(false)
                    .build();
        }
    }*/
}
