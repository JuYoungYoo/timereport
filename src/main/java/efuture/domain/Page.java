package efuture.domain;

/**
 * Created by user on 2017-05-15.
 */
public class Page {

    private int perPageNum;     // 한 페이지에 보여줄 게시글 수
    private int pageBlock;      // 페이징 블록 사이즈

    private int listNum;        // 가상 번호
    private int startNum;      // rownum start
    private int endNum;        // rownum end
    private int totalPage;
    private int startPage;
    private int endPage;
    private int prevPage;
    private int nextPage;
    private int current;        // 현 페이지
    private int total;


    public int getListNum() {
        return this.total - ((this.getCurrent() - 1) * this.getPerPageNum());
    }

    public void setListNum(int listNum) {
        this.listNum = listNum;
    }

    public Page(int current, int total) {
        this(10,10, current, total);
    }

    public Page(int perPageNum, int pageBlock, int current, int total) {
        this.perPageNum = perPageNum;
        this.pageBlock = pageBlock;
        this.total = total;
        this.current = current;

        if(current < 1) this.current = 1;
        totalPage = (int)Math.ceil((double)total/(double)perPageNum);
        startNum = perPageNum * (current -1);
        endNum = this.perPageNum;

        startPage = (int) Math.ceil((double)current/(double)pageBlock) * pageBlock - (pageBlock - 1);
        endPage = (int) Math.ceil((double)current/(double)pageBlock) * pageBlock;
        prevPage = (int) Math.ceil((double)current/(double)pageBlock) * pageBlock - pageBlock;
        nextPage = (int) Math.ceil((double)current/(double)pageBlock) * pageBlock + 1;
    }

    public int getPerPageNum() {
        return perPageNum;
    }

    public void setPerPageNum(int perPageNum) {
        this.perPageNum = perPageNum;
    }

    public int getPageBlock() {
        return pageBlock;
    }

    public void setPageBlock(int pageBlock) {
        this.pageBlock = pageBlock;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        if(endPage > totalPage) endPage = totalPage;
         return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getNextPage() {
        if(totalPage < this.nextPage) nextPage = totalPage;
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Page{" +
                "perPageNum=" + perPageNum +
                ", pageBlock=" + pageBlock +
                ", listNum=" + listNum +
                ", startNum=" + startNum +
                ", endNum=" + endNum +
                ", totalPage=" + totalPage +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                ", prevPage=" + prevPage +
                ", nextPage=" + nextPage +
                ", current=" + current +
                ", total=" + total +
                '}';
    }
}
