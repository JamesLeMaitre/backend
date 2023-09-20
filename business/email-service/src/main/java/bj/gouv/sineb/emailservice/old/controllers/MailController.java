package bj.gouv.sineb.emailservice.old.controllers;


import bj.gouv.sineb.emailservice.old.formatter.handlers.ExceptionHandlers;
import bj.gouv.sineb.emailservice.old.formatter.responses.HttpResponse;
import bj.gouv.sineb.emailservice.old.dto.MailRequest;
import bj.gouv.sineb.emailservice.old.formatter.utils.ResponseUtils;
import bj.gouv.sineb.emailservice.old.responses.ResponseApi;
import bj.gouv.sineb.emailservice.old.services.MailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Objects;


import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/mail/", produces = APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()")
public class MailController extends ExceptionHandlers {

    private final MailService service;

//    FileUpload fileUpload = new FileUpload();

    @Value("${upload.directory}")
    private String uploadDirectory;

    public MailController(MailService service) {
        this.service = service;
    }


    @PostMapping(value = "send")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> index(@Valid @RequestBody MailRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
        }
        return ResponseEntity.status(OK)
                .contentType(APPLICATION_JSON)
                .body(ResponseUtils.successResponse("Mail sent with success.", OK, service.sendMail(request),true));
    }
//    @PostMapping(value = "sendMail")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public ResponseEntity<HttpResponse> send(@Valid @RequestBody MailDTO request, BindingResult result) {
//        if (result.hasErrors()) {
//            return this.createValidationHttpResponse("Error validation ",result.getFieldErrors());
//        }
//        return ResponseEntity.status(OK)
//                .contentType(APPLICATION_JSON)
//                .body(successResponse("Mail sent with success.", OK, service.sendSimpleMail(request),true));
//    }

    @GetMapping(value = "ip_adress")
    public ResponseApi getHostAddress() throws SocketException {

        InetAddress a = null;

        Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
        while (e.hasMoreElements()){
            Enumeration<InetAddress> i = e.nextElement().getInetAddresses();
            while (i.hasMoreElements()){
                 a = i.nextElement();
                System.out.println(a.getHostName()+" -> "+a.getHostAddress()+
                        "\n\t isloopback? "+a.isLoopbackAddress()+
                        "\n\t isSiteLocalAddress? "+a.isSiteLocalAddress()+
                        "\n\t isIPV6? "+(a instanceof Inet6Address)+
                        "\n\t isPasserel? "+(a.isMulticastAddress())
                );
            }
            System.out.println("---------------");

        }

        return new ResponseApi(OK, "Email envoyer avec succèes", a.getHostAddress(), true);
    }

//    @GetMapping("list")
//    public ResponseApi getAllMAil() {
//        return new ResponseApi(OK, "Email envoyer avec succèes", service.getAllMaill(), true);
//    }


    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseApi uploadFile(@RequestParam("file") MultipartFile file)  {

        try {
            if (file.isEmpty()) {
                return new ResponseApi(OK, "Veuillez selectionner un fichier!", false);
            }

            getString(file);
        } catch (IOException e) {
            return new ResponseApi(OK, "Erreur Serveur!", e.getMessage(), false);
        }
        return new ResponseApi(OK, "Fichier téléversé avec succès!", true);

    }


    @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseApi handleFileUpload(@RequestParam("files") MultipartFile[] files) {

        try {
//            if (files == null || files.length == 0) {
//                return new ResponseApi(OK, "Veuillez selectionner un fichier !", false);
//            }

            for (MultipartFile file : files) {
                getString(file);
            }
        } catch (IOException e) {
            return new ResponseApi(OK, "Veuillez selectionner un fichier !",  false);
        }
     return new ResponseApi(OK, "Fichier téléversé avec succès!", true);
    }

    @NotNull
    private void getString(@RequestParam("file") MultipartFile file) throws IOException {

        File userUploadsDirectory = new File(uploadDirectory);
        if (!userUploadsDirectory.exists()) {
            userUploadsDirectory.mkdirs();
        }

        // Sauvegarder le fichier dans le dossier "uploads"
        File uploadedFile = new File(userUploadsDirectory, Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(uploadedFile);

        System.out.println("*********************************************");
        System.out.println("Done !!!");
        System.out.println("*********************************************");
    }

//    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void uploadFile(@RequestParam("file") MultipartFile file) {
//        fileUpload.uploadFile(file);
//    }
//
//    @PostMapping(value = "uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public void uploadFiles(@RequestParam("files") MultipartFile[] files) {
//        fileUpload.uploadFiles(files);
//    }


}
