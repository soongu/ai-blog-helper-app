package com.example.bloghelper.service;

import com.example.bloghelper.dto.PostCreateRequest;
import com.example.bloghelper.dto.PostResponse;
import com.example.bloghelper.dto.PostUpdateRequest;
import com.example.bloghelper.entity.Post;
import com.example.bloghelper.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * PostService 클래스는 게시글(Post) 관련 비즈니스 로직을 담당합니다.
 * 컨트롤러(Controller)로부터 요청을 받아 저장소(Repository)를 통해 엔티티에 접근하고,
 * DTO를 통해 결과를 반환합니다.
 */
@Service
@Transactional // 클래스 내 메서드들이 기본적으로 트랜잭션 범위 내에서 실행되도록 합니다.
@RequiredArgsConstructor // Lombok 어노테이션으로, final 필드에 대한 생성자를 자동으로 생성합니다.
public class PostService {
    // 게시글에 대한 CRUD 작업을 담당하는 Repository
    private final PostRepository postRepository;

    /**
     * 게시글을 생성하는 메서드입니다.
     *
     * @param request PostCreateRequest DTO로, 클라이언트가 전달한 제목, 내용 등 게시글 정보를 포함하고 있습니다.
     * @return 생성된 게시글을 담은 PostResponse DTO
     */
    public PostResponse createPost(PostCreateRequest request) {
        // 요청 DTO로부터 제목과 내용을 꺼내 Builder 패턴을 통해 Post 엔티티를 생성합니다.
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        // 생성한 Post 엔티티를 데이터베이스에 저장합니다.
        Post savedPost = postRepository.save(post);

        // 저장된 엔티티를 바탕으로 응답 DTO를 생성하여 반환합니다.
        return new PostResponse(savedPost);
    }

    /**
     * 특정 게시글을 조회하는 메서드입니다.
     *
     * @param id 조회할 게시글의 식별자(ID)
     * @return 조회한 게시글을 담은 PostResponse DTO
     * @throws EntityNotFoundException 해당 ID로 게시글을 찾을 수 없을 경우 예외 발생
     */
    @Transactional(readOnly = true) // 조회 전용이므로 readOnly = true를 통해 성능 최적화 및 안전성 향상
    public PostResponse getPost(Long id) {
        // ID를 통해 게시글을 조회하고, 없을 경우 예외를 발생시킵니다.
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
        // 조회한 엔티티를 응답 DTO로 변환하여 반환합니다.
        return new PostResponse(post);
    }

    /**
     * 모든 게시글을 조회하는 메서드입니다. 최신 순으로 정렬된 리스트를 반환합니다.
     *
     * @return 모든 게시글을 PostResponse DTO의 리스트로 반환
     */
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        // 저장소에서 최신순으로 정렬된 모든 게시글을 가져와 PostResponse DTO로 변환합니다.
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(PostResponse::new)
                .toList();
    }

    /**
     * 게시글을 수정하는 메서드입니다.
     *
     * @param id      수정할 게시글의 식별자(ID)
     * @param request 수정할 새로운 제목과 내용을 담은 PostUpdateRequest DTO
     * @return 수정된 게시글 정보를 담은 PostResponse DTO
     * @throws EntityNotFoundException 해당 ID로 게시글을 찾을 수 없을 경우 예외 발생
     */
    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        // ID를 통해 기존 게시글을 조회하고 없으면 예외를 발생시킵니다.
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));

        // Post 엔티티의 update 메서드를 통해 제목과 내용을 변경합니다.
        post.update(request.getTitle(), request.getContent());

        // 변경된 내용을 저장소에 반영하고, 저장된 엔티티를 기반으로 응답 DTO를 만들어 반환합니다.
        Post updatedPost = postRepository.save(post);
        return new PostResponse(updatedPost);
    }

    /**
     * 게시글을 삭제하는 메서드입니다.
     *
     * @param id 삭제할 게시글의 식별자(ID)
     * @throws EntityNotFoundException 해당 ID로 게시글을 찾을 수 없을 경우 예외 발생
     */
    public void deletePost(Long id) {
        // 게시글 존재 여부를 확인한 뒤, 없으면 예외 발생
        if (!postRepository.existsById(id)) {
            throw new EntityNotFoundException("Post not found with id: " + id);
        }
        // 존재한다면 해당 게시글을 삭제합니다.
        postRepository.deleteById(id);
    }


}
