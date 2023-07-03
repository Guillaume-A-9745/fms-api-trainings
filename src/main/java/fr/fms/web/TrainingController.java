package fr.fms.web;

import fr.fms.entities.Training;
import fr.fms.exception.RecordNotFoundException;
import fr.fms.sevice.ImplTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TrainingController {
    @Autowired
    private ImplTrainingService implTrainingService;

    @GetMapping("/trainings")
    public List<Training> allTrainings(){
        return implTrainingService.getTrainings();
    }

    @PostMapping("/trainings")
    public ResponseEntity<Training> saveTraining(@RequestBody Training t){
        Training training = implTrainingService.saveTraining(t);
        if(Objects.isNull(training)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(training.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/trainings/{id}")
    public void deleteTraining(@PathVariable("id") Long id){
        implTrainingService.deleteTraining(id);
    }

    @GetMapping("/trainings/{id}")
    public Training getTrainingById(@PathVariable("id") Long id){
        return implTrainingService.readTraining(id)
                .orElseThrow( () -> new RecordNotFoundException("L'Id de formation " + id + " n'existe pas"));
    }

    @PutMapping("/trainings/{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable Long id,@RequestBody Training upTraining){
        Training training = (Training) implTrainingService.readTraining(id)
                .orElseThrow( () -> new RecordNotFoundException("L'Id de formation " + id + " n'existe pas"));

        // Mettre à jour les attributs de la formation existante
        training.setName(upTraining.getName());
        training.setDescription(upTraining.getDescription());
        training.setPrice(upTraining.getPrice());
        training.setLogo(upTraining.getLogo());

        // Appeler la méthode de service pour mettre à jour la formation
        Training updatedTraining = implTrainingService.saveTraining(training);

        return ResponseEntity.ok(updatedTraining);
    }
}
