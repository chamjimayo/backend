package com.project.chamjimayo.controller.dto.response;

import com.project.chamjimayo.repository.domain.entity.Restroom;
import com.project.chamjimayo.service.dto.EquipmentNameNId;
import com.project.chamjimayo.service.dto.RestroomManagerNameNId;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class NearByResponse {

  private String restroomName;

  private double longitude;

  private double latitude;

  // 남여 공용 화장실인가?
  private Boolean unisex;

  // 도로명 주소
  private String address;

  // 개방 시간
  private String operatingHour;

  // 화장실 대표 사진 url
  private String restroomPhoto;

  // 비품이 있을 확률 -> 어떤 비품이 있는 확률인지...?
  private double equipmentExistenceProbability;

  // 공용(무료)인가 유료인가?
  private String publicOrPaid;

  // 이용가능한 상태인가?
  private Boolean accessibleToiletExistence;

  // 남자 대변기 수
  private Integer maleToiletCount;

  // 여자 대변기 수
  private Integer femaleToiletCount;

  // 남자 이용 가능 대변기 수
  private Integer availableMaleToiletCount;

  // 여자 이용 가능 대변기 수
  private Integer availableFemaleToiletCount;

  private List<EquipmentNameNId> equipments;

  private float reviewRating;

  private RestroomManagerNameNId restroomManager;

  private Double distance;

  private Long restroomId;

  private Integer price;


  public NearByResponse makeDto(Restroom restroom, double distance) {
    this.restroomName = restroom.getRestroomName();
    this.longitude = restroom.getLocationLongitude();
    this.latitude = restroom.getLocationLatitude();
    this.unisex = restroom.getUnisex();
    this.address = restroom.getAddress();
    this.operatingHour = restroom.getOperatingHour();
    this.restroomPhoto = restroom.getRestroomPhotos().get(0).getPhotoUrl(); // 첫번째에 있는 사진 사용
    this.equipmentExistenceProbability = restroom.getEquipmentExistenceProbability();
    this.publicOrPaid = restroom.getPublicOrPaid();
    this.accessibleToiletExistence = restroom.getAccessibleToiletExistence();
    this.maleToiletCount = restroom.getMaleToiletCount();
    this.femaleToiletCount = restroom.getFemaleToiletCount();
    this.availableMaleToiletCount = restroom.getAvailableMaleToiletCount();
    this.availableFemaleToiletCount = restroom.getAvailableFemaleToiletCount();
    this.equipments = restroom.getEquipments()
        .stream().map(equipment -> new EquipmentNameNId(equipment.getEquipmentName(),
            equipment.getEquipmentId()))
        .collect(Collectors.toList());
    this.reviewRating = (restroom.getAverageRating() == null) ? 0 : restroom.getAverageRating();
    if (restroom.getRestroomManager() == null) {
      this.restroomManager = null;
    } else {
      this.restroomManager = new RestroomManagerNameNId(
          restroom.getRestroomManager().getName(),
          restroom.getRestroomManager().getManagerId());
    }
    this.distance = distance;
    this.restroomId = restroom.getRestroomId();
    this.price = restroom.getPrice();
    return this;
  }
}
