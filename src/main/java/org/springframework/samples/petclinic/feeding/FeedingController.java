package org.springframework.samples.petclinic.feeding;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feeding")
public class FeedingController {
 
	
	private static final String VIEWS_FEEDING_CREATE_OR_UPDATE_FORM = "feedings/createOrUpdateFeedingForm";

	private final FeedingService fService;
	private final PetService pService;



	@Autowired
	public FeedingController(FeedingService feedingService, PetService petService) {
		this.fService = feedingService;
		this.pService = petService;
	}
	
	@GetMapping(value = "/create")
	public String initCreationForm(Feeding feeding, ModelMap model) {
		Feeding f = new Feeding();
		Collection<Pet> pets = pService.getAllPets();
		Collection<FeedingType> feedingTypes = fService.getAllFeedingTypes();
		model.put("feeding", f);
		model.put("pets", pets);
		model.put("feedingTypes", feedingTypes);
		return VIEWS_FEEDING_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/create")
	public String processCreationForm(@Valid Feeding feeding, BindingResult result, ModelMap model) throws UnfeasibleFeedingException {		
		if (result.hasErrors()) {
			model.put("feeding", feeding);
			return VIEWS_FEEDING_CREATE_OR_UPDATE_FORM;
		}
		else {
            try {
				this.fService.save(feeding);
			} catch (UnfeasibleFeedingException e) {
				e.printStackTrace();
				return VIEWS_FEEDING_CREATE_OR_UPDATE_FORM;
			}
            return "redirect:/welcome";
		}
	}
	
}
