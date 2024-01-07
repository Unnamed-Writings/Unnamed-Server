package com.mile.controller.post;

import com.mile.dto.ErrorResponse;
import com.mile.dto.SuccessResponse;
import com.mile.post.service.dto.CommentCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Tag(name = "Post", description = "게시글 관련 API - 댓글 등록/ 조회 포함")
public interface PostControllerSwagger {

    @Operation(summary = "댓글 작성")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "댓글 등록이 완료되었습니다."),
                    @ApiResponse(responseCode = "400",
                            description = "1. 댓글 최대 입력 길이(500자)를 초과하였습니다.\n" +
                                    "2.댓글에 내용이 없습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "해당 사용자는 모임에 접근 권한이 없습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    SuccessResponse postComment(
            @PathVariable final Long postId,
            @Valid @RequestBody final CommentCreateRequest commentCreateRequest,
            final Principal principal
    );
}