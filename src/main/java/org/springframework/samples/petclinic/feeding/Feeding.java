package org.springframework.samples.petclinic.feeding;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.pet.Pet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="feeding")
public class Feeding extends BaseEntity{
    
	@Id
	private Integer id;
	
	@NotNull
	@Column(name="start_date")
	@DateTimeFormat(pattern ="yyyy/MM/dd")
    private LocalDate startDate;
    
	@NotNull
	@Positive
	@Column(name="weeks_duration")
	private double weeksDuration;
    
	@NotNull
	@ManyToOne
	@JoinColumn(name="pet")
	Pet pet;   
	
	@ManyToOne
	private FeedingType feedingType;
}
