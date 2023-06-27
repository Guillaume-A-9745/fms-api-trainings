package fr.fms;

import fr.fms.dao.CategoryRepository;
import fr.fms.dao.TrainingRepository;
import fr.fms.entities.Category;
import fr.fms.entities.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiTrainingsTestApplication implements CommandLineRunner {

	@Autowired
	TrainingRepository trainingRepository;
	@Autowired
	CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiTrainingsTestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		generatedData();
	}

	private void generatedData() {
//		Category program = categoryRepository.save(new Category(1L,"Langage de programmation",null));

		trainingRepository.save(new Training(null, "Java", "Java SE 8 sur 5 jours", 3500, 1,null));
		trainingRepository.save(new Training(null, "DotNet", "DotNet & entity framework sur 5 jours", 2750, 1,null));
		trainingRepository.save(new Training(null, "PowerBi", "Business Intelligence sur 5 jours", 3000, 1,null));
		trainingRepository.save(new Training(null, "NodeJs", "Prise en main de NodeJs/Express sur 2 jours", 1400, 1,null));
		trainingRepository.save(new Training(null, "Php", "Initiation au Dév/Web avec Php sur 4 jours", 1300, 1,null));
	}
}
