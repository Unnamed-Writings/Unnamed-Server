package com.mile.post.service;

import com.mile.exception.message.ErrorMessage;
import com.mile.exception.model.ForbiddenException;
import com.mile.exception.model.NotFoundException;
import com.mile.moim.domain.Moim;
import com.mile.moim.domain.popular.MoimCuriousPost;
import com.mile.moim.service.dto.response.MoimCuriousPostListResponse;
import com.mile.moim.service.dto.response.MoimMostCuriousPostResponse;
import com.mile.post.domain.Post;
import com.mile.post.repository.PostRepository;
import com.mile.topic.domain.Topic;
import com.mile.utils.SecureUrlUtil;
import com.mile.writername.domain.WriterName;
import com.mile.writername.service.WriterNameRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostRetriever {

    private final PostRepository postRepository;
    private final SecureUrlUtil secureUrlUtil;
    private static final int POST_BY_TOPIC_PER_PAGE_SIZE = 6;
    private final WriterNameRetriever writerNameRetriever;

    public Post findById(
            final Long postId
    ) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.POST_NOT_FOUND)
        );

    }

    public String getTemporaryPostExist(
            final Moim moim,
            final WriterName writerName
    ) {
        Optional<Post> post = postRepository.findByMoimAndWriterNameWhereIsTemporary(moim, writerName);
        if (post.isEmpty()) {
            return secureUrlUtil.encodeUrl(0L);
        }
        return post.get().getIdUrl();
    }

    public Slice<Post> findByTopicAndLastPostId(
            final Topic topic,
            final Long lastPostId
    ) {
        PageRequest pageRequest = PageRequest.of(0, POST_BY_TOPIC_PER_PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<Post> posts = postRepository.findByTopicAndLastPostId(topic, pageRequest, lastPostId);
        return posts;
    }


    public List<Post> getLatestPostsByMoim(Moim moim) {
        return postRepository.findLatest4NonTemporaryPostsByMoim(moim);
    }

    public List<Post> findAllByTopic(
            final Topic topic
    ) {
        return postRepository.findByTopic(topic);
    }

    private List<Post> getPostHaveCuriousCount(
            final List<Post> postList
    ) {
        postList.removeIf(post -> post.getCuriousCount() <= 0);
        return postList;
    }

    public void authenticateUserWithPost(
            final Post post,
            final Long userId
    ) {
        Long moimId = post.getTopic().getMoim().getId();
        writerNameRetriever.findByMoimAndUserWithNotExceptionCase(moimId, userId);
    }

    public void authenticateUserOfMoim(
            final boolean isUserInMoim
    ) {
        if (!isUserInMoim) {
            throw new ForbiddenException(ErrorMessage.USER_MOIM_AUTHENTICATE_ERROR);
        }
    }


    public boolean existsPostByWriterWithPost(
            final Long postId,
            final Long writerNameId
    ) {
        return postRepository.existsPostByIdAndWriterNameId(postId, writerNameId);
    }

    public void authenticateWriterWithPost(
            final Long postId,
            final Long writerNameId
    ) {
        if (!existsPostByWriterWithPost(postId, writerNameId)) {
            throw new ForbiddenException(ErrorMessage.WRITER_AUTHENTICATE_ERROR);
        }
    }

    public void authenticateWriter(
            final Long postId,
            final WriterName writerName
    ) {
        authenticateWriterWithPost(postId, writerName.getId());
    }

    public boolean isWriterOfPost(final Post post, final WriterName writerName) {
        return post.getWriterName().equals(writerName);
    }

    public MoimCuriousPostListResponse getMostCuriousPostByMoim(final Moim moim) {
        List<Post> postList = getPostHaveCuriousCount(postRepository.findTop2ByMoimOrderByCuriousCountDesc(moim));
        return MoimCuriousPostListResponse.of(postList
                .stream()
                .map(p->MoimMostCuriousPostResponse.of(MoimCuriousPost.of(p))
                ).toList());
    }


    public int findPostCountByWriterNameId(
            final Long writerNameId
    ) {
        return postRepository.countByWriterNameId(writerNameId);
    }

    public List<Post> findAllByTopics(
            final List<Topic> topics
    ) {
        return topics.stream()
                .flatMap(topic -> postRepository.findByTopic(topic).stream())
                .collect(Collectors.toList());
    }
}
