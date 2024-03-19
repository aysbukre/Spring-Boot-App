package com.bukre.questapp.repos;


import com.bukre.questapp.entities.Like;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByUserIdAndPostId(Long userId, Long postId);

    List<Like> findByUserId(Long userId);

    List<Like> findByPostId(Long postId);

    @Query(value = 	"select 'liked' as action , l.post_id, u.avatar, u.user_name from "
            + "p_like l left join users u on u.id = l.user_id "
            + "where l.post_id in :postIds limit 5", nativeQuery = true)
    List<Object> findUserLikesByPostId(@Param("postIds") List<Long> postIds);
}
