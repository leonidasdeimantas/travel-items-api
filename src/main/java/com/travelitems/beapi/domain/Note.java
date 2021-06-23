package com.travelitems.beapi.domain;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String note;

	@Column(nullable = false)
	private String tripUrl;

	@Column(nullable = false)
	private LocalDateTime time;

	private String userName;

	public Note(String note, String tripUrl) {
		this.note = note;
		this.tripUrl = tripUrl;
	}

	protected Note() {};
}
