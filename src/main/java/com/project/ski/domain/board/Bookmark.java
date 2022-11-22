package com.project.ski.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(name = "subscribe", columnNames = { "fromUserId", "toResortId" }) })
public class Bookmark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnoreProperties({ "boards", "tayos", "password", "clubUsers", "carpools" })
	@ManyToOne
	@JoinColumn(name = "fromUserId")
	private User fromUser;

	@JsonIgnoreProperties
	@ManyToOne
	@JoinColumn(name = "toResortId")
	private Resort toResort;

	private LocalDateTime createDate;

	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
