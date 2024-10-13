package com.ensah.core.web.controllers;

import com.ensah.core.bo.*;
import com.ensah.core.dao.IEnseignantRepository;
import com.ensah.core.dto.ExamenForm;
import com.ensah.core.services.*;
import com.ensah.core.services.impl.ElementPedagogiqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/examens")
public class ExamenController {

    @Autowired
    private ExamenService examenService;

    @Autowired
    private SemestreService semestreService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SalleService salleService;

    @Autowired
    private TypeExamenService typeExamenService;

    @Autowired
    private ElementPedagogiqueService elementPedagogiqueService;

    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private GroupeService groupeService;

    @Autowired
    private CadreAdministratifService cadreAdministratifService;

    @GetMapping("/nouveau")
    public String showAddForm(Model model) {
        ExamenForm examenForm = new ExamenForm();
        model.addAttribute("examenForm", examenForm);
        model.addAttribute("sessions", sessionService.getAllSessions());
        model.addAttribute("semestres", semestreService.getAllSemestres());
        model.addAttribute("salles", salleService.getAllSalles());
        model.addAttribute("typesExamens", typeExamenService.getAllTypeExamens());
        model.addAttribute("typesElements", elementPedagogiqueService.getTypesElements());
        model.addAttribute("elementsPedagogiques", elementPedagogiqueService.getAllElements());
        model.addAttribute("cadresAdministratifs", cadreAdministratifService.getAllCadresAdministratifs());
        return "examens/add";
    }

    @PostMapping("/nouveau")
    public String addExamen(@ModelAttribute("examenForm") @Valid ExamenForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("sessions", sessionService.getAllSessions());
            model.addAttribute("semestres", semestreService.getAllSemestres());
            model.addAttribute("salles", salleService.getAllSalles());
            model.addAttribute("typesExamens", typeExamenService.getAllTypeExamens());
            model.addAttribute("typesElements", elementPedagogiqueService.getTypesElements());
            model.addAttribute("elementsPedagogiques", elementPedagogiqueService.getAllElements());
            model.addAttribute("enseignants", enseignantService.getAllEnseignants());
            model.addAttribute("cadresAdministratifs", cadreAdministratifService.getAllCadresAdministratifs()); // Ajouter cette ligne
            return "examens/add";
        }

        // Création de l'examen
        Examen examen = new Examen();
        examen.setAnneeUniversitaire(form.getAnneeUniversitaire());
        Session session = sessionService.getSessionById(form.getSessionId());
        Semestre semestre = semestreService.getSemestreById(form.getSemestreId());
        TypeExamen typeExamen = typeExamenService.getTypeExamenById(form.getTypeExamenId());
        ElementPedagogique elementPedagogique = elementPedagogiqueService.getElementById(form.getElementPedagogiqueId());


        if (elementPedagogique != null) {
            examen.setCoordonnateur(elementPedagogique.getCoordonnateur().getNom() + " " + elementPedagogique.getCoordonnateur().getPrenom());
        }

        examen.setDate(form.getDate());
        examen.setHeureDebut(form.getHeureDebut());
        examen.setDureePrevue(form.getDureePrevue());

        // Enregistrement de l'examen dans la base de données
        examen = examenService.saveExamen(examen);

        if (session != null && semestre != null && typeExamen != null && elementPedagogique != null) {
            examen.setSession(session);
            examen.setSemestre(semestre);
            examen.setType(typeExamen);
            examen.setElementPedagogique(elementPedagogique);

            // Sélection aléatoire des enseignants
            List<Enseignant> enseignants = selectRandomEnseignants(form.getNombreSurveillants());
            examen.setEnseignants(enseignants);

            // Enregistrement des enseignants associés à l'examen dans la base de données
            for (Enseignant enseignant : enseignants) {
                enseignant.getExamens().add(examen);
                enseignantService.saveEnseignant(enseignant);
            }

            // Gestion des surveillants et des salles
            Salle salle = salleService.getSalleById(form.getSalleId());
            if (salle != null) {
                SalleSurveillant salleSurveillant = new SalleSurveillant();
                salleSurveillant.setSalle(salle);
                salleSurveillant.setNombreSurveillants(form.getNombreSurveillants());
                salleSurveillant.setExamen(examen);
                salleService.saveSalleSurveillant(salleSurveillant);
            }

            return "redirect:/examens";
        }

        return "examens/add";
    }


    @GetMapping
    public String listElements(Model model) {
        model.addAttribute("examens", examenService.getAllExamens());
        return "examens/planExams";
    }

    @GetMapping("/elementPedagogique/{id}")
    @ResponseBody
    public ResponseEntity<?> getElementPedagogique(@PathVariable("id") Long id) {
        ElementPedagogique element = elementPedagogiqueService.getElementById(id);
        if (element == null) {
            return ResponseEntity.notFound().build();
        }
        String coordonnateur = element.getCoordonnateur().getNom() + " " + element.getCoordonnateur().getPrenom();
        return ResponseEntity.ok(coordonnateur);
    }

    private List<Enseignant> selectEnseignants(String mode, Long id, int nombreSurveillants) {
        List<Enseignant> allEnseignants = new ArrayList<>();
        Set<Enseignant> selectedEnseignants = new HashSet<>();

        if ("departement".equals(mode)) {
            List<Groupe> groupes = groupeService.getGroupsByDepartement(id);
            for (Groupe groupe : groupes) {
                allEnseignants.addAll(groupe.getEnseignants());
            }
        } else if ("filiere".equals(mode)) {
            List<Groupe> groupes = groupeService.getGroupsByFiliere(id);
            for (Groupe groupe : groupes) {
                allEnseignants.addAll(groupe.getEnseignants());
            }
        }

        else {
            // Sinon, sélectionnez aléatoirement nombreSurveillants enseignants
            Random random = new Random();
            while (selectedEnseignants.size() < nombreSurveillants) {
                int randomIndex = random.nextInt(allEnseignants.size());
                selectedEnseignants.add(allEnseignants.get(randomIndex));
            }
        }

        return new ArrayList<>(selectedEnseignants);
    }

    private List<Enseignant> selectRandomEnseignants(int nombreSurveillants) {
        List<Enseignant> allEnseignants = enseignantService.getAllEnseignants();
        Collections.shuffle(allEnseignants);
        return allEnseignants.subList(0, Math.min(nombreSurveillants, allEnseignants.size()));
    }
    @PostMapping("/modifier/{id}")
    public String updateExamen(@PathVariable("id") Long examenId, @ModelAttribute("examenForm") @Valid ExamenForm form, BindingResult result, Model model, @RequestParam("epreuveFile") MultipartFile epreuveFile, @RequestParam("pvFile") MultipartFile pvFile) {

        // Récupérer l'examen existant depuis la base de données
        Examen examen = examenService.getExamenById(examenId);

        if (examen == null) {
            // Gérer le cas où l'examen n'est pas trouvé
            return "redirect:/examens";
        }

        // Votre logique de validation existante...

        // Mettre à jour les autres champs de l'examen avec les nouvelles données
        examen.setAnneeUniversitaire(form.getAnneeUniversitaire());
        examen.setDate(form.getDate());
        examen.setHeureDebut(form.getHeureDebut());
        examen.setDureePrevue(form.getDureePrevue());

        // Mettre à jour les fichiers d'épreuve et de PV s'ils sont fournis
        if (!epreuveFile.isEmpty()) {
            try {
                byte[] epreuveBytes = epreuveFile.getBytes();

                examen.setEpreuveFilePath(saveFileAndGetPath(epreuveBytes, epreuveFile.getOriginalFilename()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!pvFile.isEmpty()) {
            try {
                byte[] pvBytes = pvFile.getBytes();
                String pvFileName = pvFile.getOriginalFilename();
                examen.setPvFilePath(saveFileAndGetPath(pvBytes, pvFileName));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Enregistrer les modifications dans la base de données
        examenService.saveExamen(examen);

        // Rediriger vers la page de liste des examens
        return "redirect:/examens";
    }


    private String saveFileAndGetPath(byte[] fileBytes, String fileName) {
        // Définir le répertoire de stockage des fichiers téléchargés
        String uploadDirectory = "/chemin/vers/le/repertoire/de/stockage/";

        try {
            // Créer le répertoire s'il n'existe pas
            Files.createDirectories(Paths.get(uploadDirectory));

            // Générer un nom de fichier unique (par exemple, en ajoutant un horodatage)
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;

            // Chemin complet du fichier sur le serveur
            String filePath = uploadDirectory + uniqueFileName;

            // Écrire les données du fichier dans le fichier sur le serveur
            Path path = Paths.get(filePath);
            Files.write(path, fileBytes);

            // Retourner le chemin d'accès complet du fichier sur le serveur
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            // En cas d'erreur, renvoyer null ou une chaîne vide pour indiquer que le fichier n'a pas pu être enregistré
            return null;
        }
    }

}
