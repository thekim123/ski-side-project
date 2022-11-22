package com.project.ski.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.ski.domain.resort.Resort;
import com.project.ski.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;
	private String content;
	private String postImageUrl;

	@JoinColumn(name = "resortId")
	@ManyToOne
	private Resort resort;

	@JsonIgnoreProperties({ "boards" })
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	@OrderBy("id desc")
	@JsonIgnoreProperties("board")
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	private List<Comment> comment;

	@JsonIgnoreProperties({ "board" })
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	private List<Likes> likes;

	@Transient
	private long likeCount;

	@Transient
	private boolean likeState;
	private LocalDateTime createDate;

	private long pageCount;

	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
