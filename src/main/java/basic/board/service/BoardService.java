package basic.board.service;

import basic.board.entity.Board;
import basic.board.exception.NoSuchBoardException;
import basic.board.paging.PageDTO;
import basic.board.paging.PaginationInfo;
import basic.board.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;


    /**게시물 작성*/
    @Transactional
    public void boardWrite(Board board){
        boardMapper.save(board);
    }

    /**게시물 삭제*/
    @Transactional
    public void boardDelete(Long id){
        boardMapper.delete(id);
    }

    /**게시물 수정*/
    @Transactional
    public void boardUpdate(Board board){
        boardMapper.update(board);
    }

    /**게시물 상세 페이지*/
    public Board boardInfo(long id){
        return boardMapper.findById(id).orElseThrow(() -> new NoSuchBoardException("존재하지 않는 게시물입니다"));
    }




    /**게시물 전체 조회*/
    public List<Board> boardList(PageDTO pageDTO){
        /** 1.빈리스트를 생성한다.*/
        List<Board> boardList = new ArrayList<>();

        /** 2-1.총 데이터수량을 구한다.*/
        int boardTotalCount = boardMapper.selectBoardTotalCount();

        /** 2-2.총 검색한데이터수량을 구한다.*/
        int searchBoardTotalCount = boardMapper.findTotalSizeForSearch(pageDTO);

        /** 3.PaginationInfo에 pageDTO(criteria)를 대입하여 생성하여
         * 현재페이지, 페이지에보여질 게시물갯수, 현재페이지 갯수를 갖게된다.*/
        PaginationInfo paginationInfo = new PaginationInfo(pageDTO);


        /** 4.paginationInfo 에 위에서 구한 게시물총데이터수량을 입력한다.
         * 검색했을 경우에 검색데이터수량이 게시물총데이터 수량보다 작다면
         * 보여질 게시물총수량은 검색데이터수량이되며 검색을 안했을시에는
         * 총데이터수량이 그대로 입력된다.
         * 총데이터량이 0보다 크다면 calculation 내부매서드를 호출하여
         * 페이지에 관련된 모든 정보를 얻게된다.*/
        if(searchBoardTotalCount < boardTotalCount){
            paginationInfo.setTotalRecordCount(searchBoardTotalCount);
        }else{
            paginationInfo.setTotalRecordCount(boardTotalCount);
        }

        /** 5.pageDTO에 위의 모든정보를 가진 paginationInfo를 설정한다.*/
        pageDTO.setPaginationInfo(paginationInfo);

        if(boardTotalCount > 0){
            boardList = boardMapper.findAll(pageDTO);
        }
        return boardList;
    }



}
