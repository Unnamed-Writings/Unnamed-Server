package com.mile.controller.moim;

import com.mile.authentication.PrincipalHandler;
import com.mile.dto.SuccessResponse;
import com.mile.exception.message.SuccessMessage;
import com.mile.moim.service.MoimService;
import com.mile.moim.service.dto.BestMoimListResponse;
import com.mile.moim.service.dto.ContentListResponse;
import com.mile.moim.service.dto.MoimAuthenticateResponse;
import com.mile.moim.service.dto.MoimCuriousPostListResponse;
import com.mile.moim.service.dto.MoimInfoResponse;
import com.mile.moim.service.dto.MoimTopicResponse;
import com.mile.moim.service.dto.TemporaryPostExistResponse;
import com.mile.moim.service.dto.TopicListResponse;
import com.mile.resolver.moim.MoimIdPathVariable;
import com.mile.writerName.service.dto.PopularWriterListResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moim")
public class MoimController implements MoimControllerSwagger {

    private final MoimService moimService;
    private final PrincipalHandler principalHandler;

    @Override
    @GetMapping("/{moimId}")
    public SuccessResponse<ContentListResponse> getTopicsFromMoim(
            @MoimIdPathVariable final Long moimId,
            @PathVariable("moimId") final String moimUrl
    ) {
        return SuccessResponse.of(SuccessMessage.TOPIC_SEARCH_SUCCESS, moimService.getContentsFromMoim(moimId, principalHandler.getUserIdFromPrincipal()));
    }

    @Override
    @GetMapping("/{moimId}/authenticate")
    public SuccessResponse<MoimAuthenticateResponse> getAuthenticationOfMoim(
            @MoimIdPathVariable final Long moimId,
            @PathVariable("moimId") final String moimUrl
    ) {
        return SuccessResponse.of(SuccessMessage.MOIM_AUTHENTICATE_SUCCESS, moimService.getAuthenticateUserOfMoim(moimId, principalHandler.getUserIdFromPrincipal()));
    }

    @Override
    @GetMapping("/{moimId}/mostCuriousWriters")
    public SuccessResponse<PopularWriterListResponse> getMostCuriousWritersOfMoim(
            @MoimIdPathVariable final Long moimId,
            @PathVariable("moimId") final String moimUrl
    ) {
        return SuccessResponse.of(SuccessMessage.MOIM_POPULAR_WRITER_SEARCH_SUCCESS, moimService.getMostCuriousWriters(moimId));
    }

    @Override
    @GetMapping("/{moimId}/topic")
    public SuccessResponse<MoimTopicResponse> getTopicFromMoim(
            @MoimIdPathVariable final Long moimId,
            @PathVariable("moimId") final String moimUrl
    ) {
        return SuccessResponse.of(SuccessMessage.MOIM_TOPIC_GET_SUCCESS, moimService.getTopicFromMoim(moimId));

    }

    @GetMapping("/{moimId}/info")
    @Override
    public SuccessResponse<MoimInfoResponse> getMoimInfo(
            @MoimIdPathVariable final Long moimId,
            @PathVariable("moimId") final String moimUrl
    ) {
        return SuccessResponse.of(SuccessMessage.MOIM_INFO_SUCCESS, moimService.getMoimInfo(moimId));
    }


    @GetMapping("/{moimId}/topicList")
    @Override
    public SuccessResponse<TopicListResponse> getTopicList(
            @MoimIdPathVariable final Long moimId,
            @PathVariable("moimId") final String moimUrl
    ) {
        return SuccessResponse.of(SuccessMessage.TOPIC_LIST_SEARCH_SUCCESS, moimService.getTopicList(moimId));
    }

    @Override
    @GetMapping("/{moimId}/mostCuriousPost")
    public SuccessResponse<MoimCuriousPostListResponse> getMostCuriousPostByMoim(
            @MoimIdPathVariable final Long moimId,
            @PathVariable("moimId") final String moimUrl
    ) {
        return SuccessResponse.of(SuccessMessage.MOIM_TOP_2_POST_GET_SUCCESS, moimService.getMostCuriousPostFromMoim(moimId));
    }

    @GetMapping("/best")
    public SuccessResponse<BestMoimListResponse> getBestMoimAndPostList() {
        return SuccessResponse.of(SuccessMessage.BEST_MOIM_POSTS_GET_SUCCESS, moimService.getBestMoimAndPostList());
    }

    @Override
    @GetMapping("/{moimId}/temporary")
    public SuccessResponse<TemporaryPostExistResponse> getTemporaryPost(
            @MoimIdPathVariable final Long moimId,
            @PathVariable("moimId") final String moimUrl
    ) {
        return SuccessResponse.of(SuccessMessage.IS_TEMPORARY_POST_EXIST_GET_SUCCESS, moimService.getTemporaryPost(moimId, principalHandler.getUserIdFromPrincipal()));
    }
}
