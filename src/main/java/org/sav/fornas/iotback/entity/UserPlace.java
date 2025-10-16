package org.sav.fornas.iotback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class UserPlace {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	Integer userId;

	@ManyToOne
	@JoinColumn(name = "place_id")
	private Place place;
}
