package bj.gouv.sineb.authservice.application.constant;

import java.util.UUID;

public class AppAuthConstant {

    /****** AUTH MODULE *******/
    public static final String AUTH_SERVICE = "authService";
    public static final String PAGE = String.valueOf(0);
    public static final String SIZE = String.valueOf(20);
    public static final UUID APP_EVENT_ACCOUNT_VALIDATION_ID = UUID.fromString("3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f");
    public static final UUID APP_EVENT_SEND_ACCOUNT_CREDENTIALS_ID = UUID.fromString("1b7ef1ae-88cd-4411-8a69-56416f0e8893");
    public static final UUID APP_EVENT_REQUEST_EMAIL_RESET_ID = UUID.fromString("a71c6d2e-8a8e-4ed1-b9a7-6fecc30f7b11");
    public static final UUID APP_EVENT_PASSWORD_REQUEST_NO_AUTH_ID = UUID.fromString("c73d18b3-65ad-47fc-95f1-8189fd2a3b36");
    public static final UUID SYSTEM_USER_ID = UUID.fromString("3b8ab2a9-7b6d-4f1e-bb55-9e3e6e2e9a9f");
    public static final UUID SYSTEM_USER_TRACKING_ID = UUID.fromString("18863ae7-5b1c-4157-8a12-4a287d652e66");
    public static final String SYSTEM_USER_EMAIL = "system@system.com";



    /****** RCE MODULE *******/

    /****** DEMANDE DE CONTRIBUTION ******/
    public static final String RC_DEMANDE_CONTRIBUTEUR_DEPOSEE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_GESTIONNAIRE_RC_ANALYSE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_GESTIONNAIRE_RC_REJETEE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_GESTIONNAIRE_RC_ENREGISTREE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_CHEF_SIDE_ANALYSE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_CHEF_SIDE_REJETEE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_CHEF_SIDE_CONFIRMEE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_COMITE_VALIDATION_REJETEE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_COMITE_VALIDATION_VALIDEE= "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";




    /****** DEMANDE DE CONTRIBUTION ******/
    /*public static final String RC_DEMANDE_ACCES_DOCUMENT_DEPOSEE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_ACCES_DOCUMENT_ENREGISTREE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_GESTIONNAIRE_RC_REJETEE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_GESTIONNAIRE_RC_ENREGISTREE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_CHEF_SIDE_ANALYSE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_CHEF_SIDE_REJETEE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_CHEF_SIDE_CONFIRMEE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_COMITE_VALIDATION_REJETEE = "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";
    public static final String RC_DEMANDE_COMITE_VALIDATION_VALIDEE= "RC_DEMANDE_CONTRIBUTEUR_DEPOSEE";*/




}
