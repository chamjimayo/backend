package com.project.chamjimayo.domain.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "restroom")
@Getter
@ToString(exclude = "restroomId")
@NoArgsConstructor
public class Restroom extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restroom_id")
	private Integer restroomId;

	@Column(name = "restroom_name")
	private String restroomName;

	// 위도
	@Column(name = "location_latitude")
	private double locationLatitude;

	// 경도
	@Column(name = "location_longitude")
	private double locationLongitude;

	// 남여 공용 화장실인가?
	@Column(name = "unisex")
	@Pattern(regexp = "[01]")
	private Boolean unisex;

	// 도로명 주소
	@Column(name = "road_address")
	private String roadAddress;

	// 개방 시간
	@Column(name = "opening_hour")
	private String openingHour;

	// 폐쇄 시간
	@Column(name = "closing_hour")
	private String closingHour;

	// 화장실 대표 사진 url
	@Column(name = "restroom_photo")
	private String restroomPhoto;

	// 비품이 있을 확률 -> 어떤 비품이 있는 확률인지...?
	@Column(name = "equipment_existence_probability")
	private double equipmentExistenceProbability;

	// 공용(무료)인가 유료인가?
	@Column(name = "public_or_paid")
	private String publicOrPaid;

	// 이용가능한 상태인가?
	@Column(name = "accessible_toilet_existence")
	@Pattern(regexp = "[01]")
	private Boolean accessibleToiletExistence;

	// 남자 대변기 수
	@Column(name = "male_toilet_count")
	private Integer maleToiletCount;

	// 여자 대변기 수
	@Column(name = "female_toilet_count")
	private Integer femaleToiletCount;

	// 남자 이용 가능 대변기 수
	@Column(name = "available_male_toilet_count")
	private Integer availableMaleToiletCount;

	// 여자 이용 가능 대변기 수
	@Column(name = "available_female_toilet_count")
	private Integer availableFemaleToiletCount;

	@OneToMany(mappedBy = "restroom")
	private List<Equipment> equipments;

	@OneToMany(mappedBy = "restroom")
	private List<Review> reviews;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	private RestroomManager restroomManager;

}
