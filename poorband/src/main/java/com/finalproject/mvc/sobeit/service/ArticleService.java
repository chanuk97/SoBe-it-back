package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.ArticleLike;
import com.finalproject.mvc.sobeit.entity.Vote;
import com.finalproject.mvc.sobeit.repository.ArticleLikeRepo;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.VoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ArticleService {
    @Autowired
    ArticleRepo articleRepo;
    @Autowired
    ArticleLikeRepo articleLikeRepo;
    @Autowired
    VoteRepo voteRepo;

    /**
     * 글 작성
     * @param article
     */
    public void writeArticle(Article article) {
        article.setWrittenDate(LocalDateTime.now());
        articleRepo.save(article);
    }

    /**
     * 글 수정
     */
    public void updateArticle(Article article) {
        article.setEditedDate(LocalDateTime.now());
        article.setWrittenDate(LocalDateTime.now()); // 작성일 null이 안됨.. select해와서 다시 저장하는 방법 말고 유지시키는 방법 없나?
        articleRepo.save(article);
    }

    /**
     * 글 삭제
     * @param id
     */
    public void deleteArticle(Long id) {
        articleRepo.deleteById(id);
    }

    /**
     * 글 상세 조회
     * @param id
     * @return
     */
    public Article selectArticleById(Long id) {
        return articleRepo.findById(id).orElse(null);
    }

    /**
     * 글 전체 조회
     * @return
     */
    public List<Article> selectAllArticle() {
        return articleRepo.findAll();
    }

    /**
     * 글 좋아요
     * @param articleLike
     */
    public boolean likeArticle(ArticleLike articleLike){
        boolean isLiked = false;
        ArticleLike existingLike = articleLikeRepo.findById(articleLike.getLikeSeq()).orElse(null); // 기존 좋아요가 있는 지 확인
        if (existingLike==null){ // 좋아요한 적 없으면 좋아요 생성
            articleLikeRepo.save(articleLike);
            isLiked = true;
        }
        else { // 좋아요한 적 있으면 좋아요 취소(삭제)
            articleLikeRepo.delete(existingLike);
        }
        return isLiked;
    }

    /**
     * 투표하기
     */
    public Vote voteArticle(Vote vote){
        Vote votedVote = voteRepo.save(vote);
        return votedVote;
    }
}
