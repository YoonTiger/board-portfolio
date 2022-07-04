package basic.board.repository;

import basic.board.entity.Board;
import basic.board.paging.PageDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    void save(Board board);

    void update(Board board);

    void delete(Long id);

    Optional<Board> findById(Long id);

    List<Board> findAll(PageDTO pageDTO);

    List<Board> findByMemberId(Long id);

    int findTotalSizeForSearch(PageDTO pageDTO);

    int selectBoardTotalCount();
}
