package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.ReviewDto;
import com.project.chamjimayo.controller.dto.UpdateReviewDto;
import com.project.chamjimayo.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "review", description = "리뷰 API")
@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewController {

	private final ReviewService reviewService;

	@Operation(summary = "리뷰 작성", description = "새로운 리뷰를 작성합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "리뷰가 성공적으로 작성되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
		@ApiResponse(responseCode = "404", description = "1. 화장실을 찾을 수 없습니다. \t\n2. 유저를 찾지 못했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"RESTROOM_NOT_FOUND\", \"msg\": \"화장실을 찾을 수 없습니다.\" }")))
	})
	@PostMapping
	public ResponseEntity<ReviewDto> createReview(
		@RequestBody ReviewDto reviewDto) {
		ReviewDto createdReview = reviewService.createReview(reviewDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
	}

	@Operation(summary = "리뷰 조회", description = "특정 리뷰를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "리뷰 조회 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
		@ApiResponse(responseCode = "400", description = "1. 파라미터가 부족합니다. \t\n2. 올바르지 않은 파라미터 값입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"NEED_MORE_PARAMETER\", \"msg\": \"파라미터가 부족합니다.\" }"))),
		@ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"REVIEW_NOT_FOUND\", \"msg\": \"리뷰를 찾을 수 없습니다.\" }")))
	})
	@GetMapping("/{reviewId}")
	public ResponseEntity<ReviewDto> getReview(
		@Parameter(description = "리뷰 ID", required = true, example = "1 (Long)")
		@PathVariable Long reviewId) {
		ReviewDto reviewDto = reviewService.getReview(reviewId);
		return ResponseEntity.ok(reviewDto);
	}

	@Operation(summary = "리뷰 수정", description = "기존 리뷰를 수정합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "리뷰 수정 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
		@ApiResponse(responseCode = "400", description = "1. 파라미터가 부족합니다. \t\n2. 올바르지 않은 파라미터 값입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"NEED_MORE_PARAMETER\", \"msg\": \"파라미터가 부족합니다.\" }"))),
		@ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"REVIEW_NOT_FOUND\", \"msg\": \"리뷰를 찾을 수 없습니다.\" }")))
	})
	@PatchMapping("/{reviewId}")
	public ResponseEntity<ReviewDto> updateReview(
		@Parameter(description = "리뷰 ID", required = true, example = "1 (Long)")
		@PathVariable Long reviewId,
		@RequestBody UpdateReviewDto updateReviewDto) {
		ReviewDto updatedReview = reviewService.updateReview(reviewId, updateReviewDto);
		return ResponseEntity.ok(updatedReview);
	}

	@Operation(summary = "리뷰 삭제", description = "특정 리뷰를 삭제합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "리뷰 삭제 성공"),
		@ApiResponse(responseCode = "400", description = "1. 파라미터가 부족합니다. \t\n2. 올바르지 않은 파라미터 값입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"NEED_MORE_PARAMETER\", \"msg\": \"파라미터가 부족합니다.\" }"))),
		@ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"REVIEW_NOT_FOUND\", \"msg\": \"리뷰를 찾을 수 없습니다.\" }")))
	})
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> deleteReview(
		@Parameter(description = "리뷰 ID", required = true, example = "1 (Long)")
		@PathVariable Long reviewId) {
		reviewService.deleteReview(reviewId);
		return ResponseEntity.ok("리뷰 삭제 성공");
	}

	@Operation(summary = "해당 화장실의 모든 리뷰 조회 (기본)", description = "특정 화장실에 해당하는 모든 리뷰를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "리뷰 조회 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
		@ApiResponse(responseCode = "400", description = "1. 파라미터가 부족합니다. \t\n2. 올바르지 않은 파라미터 값입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"NEED_MORE_PARAMETER\", \"msg\": \"파라미터가 부족합니다.\" }"))),
		@ApiResponse(responseCode = "404", description = "화장실을 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"RESTROOM_NOT_FOUND\", \"msg\": \"화장실을 찾을 수 없습니다.\" }")))
	})
	@GetMapping("/list/{restroomId}")
	public ResponseEntity<List<ReviewDto>> getReviewsByRestroomId(
		@Parameter(description = "화장실 ID", required = true, example = "1 (Long)")
		@PathVariable Long restroomId) {
		List<ReviewDto> reviewDtoList = reviewService.getReviewsByRestroomId(restroomId);
		return ResponseEntity.ok(reviewDtoList);
	}

	@Operation(summary = "해당 화장실의 모든 리뷰 조회 (별점 높은 순)", description = "특정 화장실에 해당하는 모든 리뷰를 별점이 높은 순으로 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "리뷰 조회 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
		@ApiResponse(responseCode = "400", description = "1. 파라미터가 부족합니다. \t\n2. 올바르지 않은 파라미터 값입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"NEED_MORE_PARAMETER\", \"msg\": \"파라미터가 부족합니다.\" }"))),
		@ApiResponse(responseCode = "404", description = "화장실을 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"RESTROOM_NOT_FOUND\", \"msg\": \"화장실을 찾을 수 없습니다.\" }")))
	})
	@GetMapping("/list/{restroomId}/high-rating")
	public ResponseEntity<List<ReviewDto>> getReviewsByRestroomIdOrderByHighRating(
		@Parameter(description = "화장실 ID", required = true, example = "1 (Long)")
		@PathVariable Long restroomId) {
		List<ReviewDto> reviewDtoList = reviewService.getReviewsByRestroomIdOrderByHighRating(
			restroomId);
		return ResponseEntity.ok(reviewDtoList);
	}

	@Operation(summary = "해당 화장실의 모든 리뷰 조회 (별점 낮은 순)", description = "특정 화장실에 해당하는 모든 리뷰를 별점이 낮은 순으로 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "리뷰 조회 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
		@ApiResponse(responseCode = "400", description = "1. 파라미터가 부족합니다. \t\n2. 올바르지 않은 파라미터 값입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"NEED_MORE_PARAMETER\", \"msg\": \"파라미터가 부족합니다.\" }"))),
		@ApiResponse(responseCode = "404", description = "화장실을 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"RESTROOM_NOT_FOUND\", \"msg\": \"화장실을 찾을 수 없습니다.\" }")))
	})
	@GetMapping("/list/{restroomId}/low-rating")
	public ResponseEntity<List<ReviewDto>> getReviewsByRestroomIdOrderByLowRating(
		@Parameter(description = "화장실 ID", required = true, example = "1 (Long)")
		@PathVariable Long restroomId) {
		List<ReviewDto> reviewDtoList = reviewService.getReviewsByRestroomIdOrderByLowRating(
			restroomId);
		return ResponseEntity.ok(reviewDtoList);
	}

	@Operation(summary = "해당 화장실의 평균 평점", description = "해당 화장실의 평균 평점을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "평균 평점 조회 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
		@ApiResponse(responseCode = "400", description = "1. 파라미터가 부족합니다. \t\n2. 올바르지 않은 파라미터 값입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"NEED_MORE_PARAMETER\", \"msg\": \"파라미터가 부족합니다.\" }"))),
		@ApiResponse(responseCode = "404", description = "화장실을 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"RESTROOM_NOT_FOUND\", \"msg\": \"화장실을 찾을 수 없습니다.\" }")))
	})
	@GetMapping("/rating/{restroomId}")
	public Float averageRating(
		@Parameter(description = "화장실 ID", required = true, example = "1 (Long)")
		@PathVariable Long restroomId) {
		Float averageRating = reviewService.averageRating(restroomId);
		return averageRating;
	}
}
