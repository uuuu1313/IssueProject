package com.issuemarket.service.front.member;

import com.issuemarket.dto.BoardSearch;
import com.issuemarket.entities.Post;
import com.issuemarket.entities.QPost;
import com.issuemarket.repositories.MemberRepository;
import com.issuemarket.repositories.PostRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPostListService {

    private final PostRepository postRepository;

    public Page<Post> gets(BoardSearch boardSearch, Long userNo) {
        QPost post = QPost.post;
        BooleanBuilder builder = new BooleanBuilder();

        int page = boardSearch.getPage();
        int pageSize = boardSearch.getPageSize();

        page = page < 1 ? 1 : page;
        pageSize = pageSize < 1 ? 20 : pageSize;
        builder.and(post.member.userNo.eq(userNo));

        String sopt = boardSearch.getSopt();
        String skey = boardSearch.getSkey();

        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            sopt = sopt.trim();
            skey = skey.trim();

            if (sopt.equals("subject_content")) {
                builder.and(post.subject.contains(skey))
                        .or(post.content.contains(skey));
            } else if (sopt.equals("subject")) {
                builder.and(post.subject.contains(skey));
            } else if (sopt.equals("content")) {
                builder.and(post.content.contains(skey));
            }
        }

        Pageable pageable = PageRequest.of(page -1, pageSize, Sort.by(Sort.Order.desc("createdAt")));
        Page<Post> myPostList = postRepository.findAll(builder, pageable);

        return myPostList;
    }
}
