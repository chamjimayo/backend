package com.project.chamjimayo.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "search")
@Getter
@ToString(exclude = "searchId")
@NoArgsConstructor
public class Search {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "search_id")
	private Integer searchId;

	// 회원 아이디 (해당 검색 기록은 어떤 회원의 기록인가)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// 검색어
	@Column(name = "search_word")
	private String searchWord;

	// 검색어에 대한 도로명 주소
	@Column(name = "road_address")
	private String roadAddress;

}