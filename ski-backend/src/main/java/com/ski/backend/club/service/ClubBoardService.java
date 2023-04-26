package com.ski.backend.club.service;


import com.ski.backend.club.entity.Club;
import com.ski.backend.club.entity.ClubBoard;
import com.ski.backend.club.entity.ClubUser;
import com.ski.backend.club.entity.ClubRole;
import com.ski.backend.config.AuditorProvider;
import com.ski.backend.config.auth.PrincipalDetails;
import com.ski.backend.user.entity.User;
import com.ski.backend.club.repository.ClubBoardRepository;
import com.ski.backend.club.repository.ClubRepository;
import com.ski.backend.club.repository.ClubUserRepository;
import com.ski.backend.club.dto.ClubBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.Objects;

import static com.ski.backend.club.entity.ClubRole.NONMEMBER;


@Service
@RequiredArgsConstructor
public class ClubBoardService {

    private final ClubBoardRepository clubBoardRepository;
    private final ClubRepository clubRepository;
    private final ClubUserRepository clubUserRepository;


    /**
     * 동호회 게시판
     * 상세 조회 -{boardId}
     */
    @Transactional(readOnly = true)
    public Page<ClubBoardDto> getClubBoard(Pageable pageable, long clubBoardId) {
        Page<ClubBoard> byId = clubBoardRepository.findById(pageable, clubBoardId);
        return byId.map(ClubBoardDto::new);
    }

    /**
     * 동호회 게시판 전체 조회
     */
    @Transactional
    public Page<ClubBoardDto> getAllClubBoard(Pageable pageable, long clubId) {
        Page<ClubBoard> clubBoard = clubBoardRepository.findByClubId(pageable, clubId);
        return clubBoard.map(ClubBoardDto::new);
    }


    /**
     * 동호회 게시판 생성
     * 해당 동호회에 가입한 유저만이 게시판에 글을 쓸 수 있음
     */
    @Transactional
    public void createClubBoard(ClubBoardDto dto, Authentication auth) {
        User loginUser = ((PrincipalDetails) auth.getPrincipal()).getUser();
        Club club = clubRepository.findById(dto.getClubId()).orElseThrow(() -> {
            throw new EntityNotFoundException("동호회 찾기 실패");
        });

        ClubUser clubUser = clubUserRepository.findByClubIdAndUserId(club.getId(), loginUser.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException("해당 동호회 회원이 아닙니다.");
        });

        if (isValidWhenNotice(dto, clubUser)) {
            throw new AccessDeniedException("클럽의 관리자나 매니저만이 공지글을 쓸 수 있습니다.");
        }

        if (clubUser.getClubRole().equals(NONMEMBER)) {
            throw new AccessDeniedException("동호회 회원이 아니면 글을 쓸 수 없습니다.");
        }

        ClubBoard cb = dto.toEntity(clubUser);
        clubBoardRepository.save(cb);
    }


    /**
     * @return 클럽 게시글이 공지일 때 관리자이거나 매니저인지 여부
     * @author thekim123
     * @since 2023.04.27
     */
    public boolean isValidWhenNotice(ClubBoardDto dto, ClubUser clubUser) {
        return Objects.equals(dto.getSortScope(), "notice") &&
                (!Objects.equals(clubUser.getClubRole(), ClubRole.ADMIN) ||
                        !Objects.equals(clubUser.getClubRole(), ClubRole.MANAGER));
    }

    /**
     * @param clubBoardId 클럽 게시판 id
     * @param auth        로그인 정보
     * @author jo-kim
     * @author thekim123
     * @apiNote 동호회 게시판 수정 서비스 로직<br/>
     * 작성자이거나 강제 편집할 권한이 있는자이면 삭제한다. <br/>
     * 둘다 아니라면 AccessDeniedException 을 던진다.
     * @since last modified at 2023.04.27
     */
    @Transactional
    public void update(long clubBoardId, ClubBoardDto dto, Authentication auth) {
        ClubBoard boards = clubBoardRepository
                .findById(clubBoardId)
                .orElseThrow(() -> new EntityNotFoundException("동호회 게시글을 찾을 수 없습니다."));

        if (isWriter(boards, auth)) {
            boards.update(dto);
            return;
        }

        if (hasAuthorityToForceEdit(boards)) {
            boards.update(dto);
            boards.forceUpdate(dto);
        } else {
            throw new AccessDeniedException("게시글을 수정할 권한이 없습니다.");
        }
    }

    /**
     * @param clubBoardId 클럽 게시판 id
     * @param auth        로그인 정보
     * @author jo-kim
     * @author thekim123
     * @apiNote 동호회 게시판 삭제 서비스 로직<br/>
     * 작성자이거나 강제 편집할 권한이 있는자이면 삭제한다. <br/>
     * 둘다 아니라면 AccessDeniedException 을 던진다.
     * @since last modified at 2023.04.27
     */
    public void delete(long clubBoardId, Authentication auth) {
        ClubBoard boards = clubBoardRepository
                .findById(clubBoardId)
                .orElseThrow(() -> new EntityNotFoundException("동호회 게시글을 찾을 수 없습니다."));

        // TODO: 클럽 게시판을 바로 삭제하지 말까? 분탕종자들 방지하게?
        if (isWriter(boards, auth)) {
            clubBoardRepository.delete(boards);
            return;
        }

        if (hasAuthorityToForceEdit(boards)) {
            AuditorProvider ad = new AuditorProvider();
            boards.forceDelete(String.valueOf(ad.getCurrentAuditor()));
        } else {
            throw new AccessDeniedException("게시글을 수정할 권한이 없습니다.");
        }
    }

    /**
     * @apiNote 동호회 게시글 작성자인지 여부
     * @author jo-kim
     * @author thekim123
     * @since last modified at 2023.04.27
     */
    public boolean isWriter(ClubBoard cb, Authentication auth) {
        PrincipalDetails pd = (PrincipalDetails) auth.getPrincipal();
        String loginUsername = pd.getUsername();
        User cbWriter = cb.getClubUser().getUser();

        return Objects.equals(cbWriter.getUsername(), loginUsername);
    }

    /**
     * @apiNote 동호회 게시글을 강제로 수정 or 삭제할 권한이 있는지 여부<br/>
     * 관리자 or 매니저이면 삭제할 권한이 있다.
     * @author jo-kim
     * @author thekim123
     * @since last modified at 2023.04.27
     */
    public boolean hasAuthorityToForceEdit(ClubBoard cb) {
        return Objects.equals(cb.getClubUser().getClubRole(), ClubRole.ADMIN) ||
                Objects.equals(cb.getClubUser().getClubRole(), ClubRole.MANAGER);
    }

}
