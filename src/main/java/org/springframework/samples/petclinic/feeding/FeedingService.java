package org.springframework.samples.petclinic.feeding;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class FeedingService {
	
	@Autowired
	private FeedingRepository feedingrepo;
	
    public List<Feeding> getAll(){
        return feedingrepo.findAll();
    }

    public List<FeedingType> getAllFeedingTypes(){
        return feedingrepo.findAllFeedingTypes();
    }

    public FeedingType getFeedingType(String typeName) {
        return feedingrepo.findFeedingTypeByName(typeName);
    }

	@Transactional(rollbackFor = UnfeasibleFeedingException.class)
    public Feeding save(Feeding p) throws UnfeasibleFeedingException {
        if (p.getPet().getType()!=p.getFeedingType().getPetType()) {            	
        	throw new UnfeasibleFeedingException();
        }else
            feedingrepo.save(p);
        return p;       
    }

    
}
