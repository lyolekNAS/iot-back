package org.sav.fornas.iotback.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

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
	Place place;

	@OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	@JsonManagedReference
	List<DevicePorts> devicePorts;
}
