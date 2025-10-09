package org.sav.fornas.iotback.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
public class Device {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String name;

	LocalDateTime lastUpdated;

	String username;
	String password;

	@ManyToOne
	@JoinColumn(name = "place_id")
	@JsonBackReference
	Place state;
}
