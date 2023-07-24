package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PointChangeDto {

	@Schema(type = "Long", example = "1")
	@NotNull(message = "유저 ID를 입력해주세요.")
	private Long userId;

	@Schema(type = "Integer", example = "2000")
	@NotNull(message = "포인트를 입력해주세요.")
	@Min(value = 0, message = "포인트의 최솟값은 0입니다.")
	private Integer point;

	private PointChangeDto(Long userId, Integer point) {
		this.userId = userId;
		this.point = point;
	}

	public static PointChangeDto create(Long userId, Integer point) {
		return new PointChangeDto(userId, point);
	}
}