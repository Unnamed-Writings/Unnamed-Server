package com.mile.controller.topic;

import com.mile.authentication.PrincipalHandler;
import com.mile.dto.SuccessResponse;
import com.mile.exception.message.SuccessMessage;
import com.mile.resolver.topic.TopicIdPathVariable;
import com.mile.topic.service.TopicService;
import com.mile.topic.service.dto.PostListInTopicResponse;
import com.mile.topic.service.dto.TopicDetailResponse;
import com.mile.topic.service.dto.TopicPutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/topic")
public class TopicController implements TopicControllerSwagger {

    private final TopicService topicService;
    private final PrincipalHandler principalHandler;

    @Override
    @GetMapping("/{topicId}")
    public SuccessResponse<PostListInTopicResponse> getPostListByTopic(
            @TopicIdPathVariable Long topicId,
            @PathVariable("topicId") final String topicUrl
    ) {
        return SuccessResponse.of(SuccessMessage.MOIM_POST_GET_SUCCESS, topicService.getPostListByTopic(topicId));
    }

    @Override
    @GetMapping("/{topicId}/details")
    public ResponseEntity<SuccessResponse<TopicDetailResponse>> getTopicDetail(
            @TopicIdPathVariable final Long topicId,
            @PathVariable("topicId") final String topicUrl
    ) {
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.TOPIC_DETAIL_GET_SUCCESS ,topicService.getTopicDetail(principalHandler.getUserIdFromPrincipal(), topicId)));
    }

    @Override
    @DeleteMapping("/{topicId}")
    public ResponseEntity<SuccessResponse> deleteTopic(
            @TopicIdPathVariable final Long topicId,
            @PathVariable("topicId") final String topicUrl
    ) {
        topicService.deleteTopic(principalHandler.getUserIdFromPrincipal(), topicId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.TOPIC_DELETE_SUCCESS));
    }

    @PutMapping("/{topicId}")
    public ResponseEntity<SuccessResponse> putTopic(
            @RequestBody final TopicPutRequest topicPutRequest,
            @TopicIdPathVariable final Long topicId,
            @PathVariable("topicId") final String topicUrl
    ) {
        topicService.putTopic(principalHandler.getUserIdFromPrincipal(), topicId, topicPutRequest);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.TOPIC_PUT_SUCCESS));
    }
}
