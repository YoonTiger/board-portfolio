package basic.board.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationInfo {

    private Criteria criteria;

    private int totalRecordCount;

    private int firstPage;
    private int lastPage;
    private int totalPageCount;

    private int firstRecordIndex;

    private boolean hasPreviousPage;
    private boolean hasNextPage;

    /** SQL의 조건절에 사용되는 마지막 RNUM
     * 오라클처럼 LIMIT구문이 존재하지않는 DB에서 사용 */
    private int lastRecordIndex;


    public PaginationInfo(Criteria criteria) {

        /** 현재페이지가 1보다 작으면 현재페이지를 1로 설정*/
        if (criteria.getCurrentPageNo() < 1) {
            criteria.setCurrentPageNo(1);
        }
        /** 페이지에 보여질 게시물갯수가 1보다 작거나 100보다 크다면
         * 페이지에 보여질 게시물갯수를 10으로 설정*/
        if (criteria.getRecordsPerPage() < 1 || criteria.getRecordsPerPage() > 100) {
            criteria.setRecordsPerPage(10);
        }

        /**
         * 현재 페이지갯수를 5로 설정*/
        criteria.setPageSize(5);


        /**현재페이지, 페이지에보여질 게시물갯수, 현재페이지 갯수를 크리테리아에 저장한다*/
        this.criteria = criteria;
    }

    public void setTotalRecordCount(int totalRecordCount) {

        this.totalRecordCount = totalRecordCount;

        if (totalRecordCount > 0) {
            calculation();
        }
    }

    private void calculation() {

        /** 전체 페이지 수
         *
         * (현재 페이지 번호가 전체 페이지 수보다 크면 현재 페이지 번호에 전체 페이지 수를 저장)
         * ( (전체 데이터 개수 - 1) / 페이지당 출력할 데이터 개수 ) + 1 */
        totalPageCount = ((totalRecordCount - 1) / criteria.getRecordsPerPage()) + 1;
        if (criteria.getCurrentPageNo() > totalPageCount) {
            criteria.setCurrentPageNo(totalPageCount);
        }


        /** 페이지 리스트의 첫 페이지 번호
         *
         * ( (현재 페이지 번호 - 1) / 화면 하단의 페이지 개수 ) * 화면 하단의 페이지 개수 + 1*/
        firstPage = ((criteria.getCurrentPageNo() - 1) / criteria.getPageSize()) * criteria.getPageSize() + 1;

        /** 페이지 리스트의 마지막 페이지 번호
         *
         * (마지막 페이지가 전체 페이지 수보다 크면 마지막 페이지에 전체 페이지 수를 저장)
         * (첫 페이지 번호 + 화면 하단의 페이지 개수) - 1*/
        lastPage = firstPage + criteria.getPageSize() - 1;
        if (lastPage > totalPageCount) {
            lastPage = totalPageCount;
        }

        /** SQL의 조건절에 사용되는 첫 RNUM
         * LIMIT 구문의 첫 번째 값에 들어갈 데이터*/
        firstRecordIndex = (criteria.getCurrentPageNo() - 1) * criteria.getRecordsPerPage();

        /** SQL의 조건절에 사용되는 마지막 RNUM
         * 오라클 DB에서 사용*/
        lastRecordIndex = criteria.getCurrentPageNo() * criteria.getRecordsPerPage();

        /** 이전 페이지 존재 여부 */
        hasPreviousPage = firstPage != 1;

        /** 다음 페이지 존재 여부 */
        hasNextPage = (lastPage * criteria.getRecordsPerPage()) < totalRecordCount;
    }

}
