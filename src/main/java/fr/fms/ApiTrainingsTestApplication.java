package fr.fms;

import fr.fms.dao.CategoryRepository;
import fr.fms.dao.TrainingRepository;
import fr.fms.entities.Category;
import fr.fms.entities.Training;
import fr.fms.security.entity.AppRole;
import fr.fms.security.entity.AppUser;
import fr.fms.security.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class ApiTrainingsTestApplication implements CommandLineRunner {

	@Autowired
	TrainingRepository trainingRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	AccountServiceImpl accountService;

	public static void main(String[] args) {
		SpringApplication.run(ApiTrainingsTestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		generatedData();
	}

	private void generatedData() {
//		Category program = categoryRepository.save(new Category(1L,"Langage de programmation",null));

		trainingRepository.save(new Training(null, "Java", "Java SE 8 sur 5 jours", 3500, "Java.png", 1,null));
		trainingRepository.save(new Training(null, "Java Avancé", "Exceptions, fichiers, Jdc,thread... sur 8 jours", 2000, "Java.png", 1,null));
		trainingRepository.save(new Training(null, "DotNet", "DotNet & entity framework sur 5 jours", 2750, "dotNet.png", 1,null));
		trainingRepository.save(new Training(null, "PowerBi", "Business Intelligence sur 5 jours", 3000, "Power_BI.png", 1,null));
		trainingRepository.save(new Training(null, "NodeJs", "Prise en main de NodeJs/Express sur 2 jours", 1400, "Node.js.png", 1,null));

		trainingRepository.save(new Training(null, "Spring", "Spring Core/Mvc/Security sur 3 jours", 799, "", 1,null));
		trainingRepository.save(new Training(null, "PHP", "Initiation au Dév/Web avec Php sur 4 jours", 1300, "PHP.png", 1,null));
		trainingRepository.save(new Training(null, "Excel débutant", "Apprendre les bases d'éxcel sur 5 jours", 1500, "", 1,null));
		trainingRepository.save(new Training(null, "Excel Avancé", "Apprendre les fonctions avancée sur 5 jours", 2000, "", 1,null));
		trainingRepository.save(new Training(null, "Machine learning", "Découvrer le machine learning sur 4 jours", 1250, "", 1,null));

		accountService.saveUser(new AppUser(null, "papa", "1234", new ArrayList<>()));
		accountService.saveUser(new AppUser(null, "mama", "1234", new ArrayList<>()));
		accountService.saveRole(new AppRole(null,"ADMIN"));
		accountService.saveRole(new AppRole(null,"USER"));
		accountService.addRoleToUser("papa", "ADMIN");
		accountService.addRoleToUser("papa", "USER");
		accountService.addRoleToUser("mama", "USER");


	}
}
