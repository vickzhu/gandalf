package com.gandalf.framework.ibatis;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class BaseQuery implements Serializable {

    /**
     * 
     */
    private static final long    serialVersionUID = -6103451409586972564L;
    private static final Integer defaultPageSize  = new Integer(20);
    private static final Integer defaultFristPage = new Integer(1);
    private static final Integer defaultTotleItem = new Integer(0);
    private static final int     defaultStartRow  = 1;
    private static final int     defaultEndRow    = defaultPageSize.intValue();
    private Integer              startIndex       = 0;                                 // 开始下标,从0开始
    public SimpleDateFormat      dateFormate      = new SimpleDateFormat("MM-dd-yyyy");
    private Integer              totalItem;
    private Integer              pageSize;
    private Integer              currentPage;
    private int                  startRow         = defaultStartRow;
    private int                  endRow           = defaultEndRow;

    protected Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public boolean isFirstPage() {
        return this.getCurrentPage().intValue() == 1;
    }

    public int getPreviousPage() {
        int back = this.getCurrentPage().intValue() - 1;

        if (back <= 0) {
            back = 1;
        }

        return back;
    }

    public boolean isLastPage() {
        return this.getTotalPage() == this.getCurrentPage().intValue();
    }

    public int getNextPage() {
        int back = this.getCurrentPage().intValue() + 1;

        if (back > this.getTotalPage()) {
            back = this.getTotalPage();
        }

        return back;
    }

    public Integer getCurrentPage() {
        if (currentPage == null) {
            return defaultFristPage;
        }

        return currentPage;
    }

    public void setCurrentPageString(String pageString) {
        if (isBlankString(pageString)) {
            return;
        }

        try {
            Integer integer = new Integer(pageString);
            this.setCurrentPage(integer);
        } catch (NumberFormatException ignore) {
        }
    }

    public void setCurrentPage(Integer cPage) {
        if ((cPage == null) || (cPage.intValue() <= 0)) {
            this.currentPage = null;
        } else {
            this.currentPage = cPage;
        }

        // 当当前页面发生改变时，重新设定startRow和endRow的值
        this.buildStartEndRow(getPageFristItem(), getPageSize());
    }

    public Integer getPageSize() {
        if (pageSize == null) {
            return getDefaultPageSize();
        }

        return pageSize;
    }

    public boolean hasSetPageSize() {
        return pageSize != null;
    }

    public void setPageSizeString(String pageSizeString) {
        if (isBlankString(pageSizeString)) {
            return;
        }

        try {
            Integer integer = new Integer(pageSizeString);
            this.setPageSize(integer);
        } catch (NumberFormatException ignore) {
        }
    }

    private boolean isBlankString(String pageSizeString) {
        if (pageSizeString == null) {
            return true;
        }

        if (pageSizeString.trim().length() == 0) {
            return true;
        }

        return false;
    }

    public void setPageSize(Integer pSize) {
        if ((pSize == null) || (pSize.intValue() <= 0)) {
            this.pageSize = null;
        } else {
            this.pageSize = pSize;
        }

        // 当页面大小发生改变时，重新设定startRow和endRow的值
        this.buildStartEndRow(getPageFristItem(), getPageSize());
    }

    public Integer getTotalItem() {
        if (totalItem == null) {
            return defaultTotleItem;
        }

        return totalItem;
    }

    public void setTotalItem(Integer tItem) {
        if (tItem == null) {
            throw new IllegalArgumentException("TotalItem can't be null.");
        }

        this.totalItem = tItem;

        int current = this.getCurrentPage().intValue();
        int lastPage = this.getTotalPage();

        if (current > lastPage) {
            this.setCurrentPage(new Integer(lastPage));
        }
    }

    public int getTotalPage() {
        int pgSize = this.getPageSize().intValue();
        int total = this.getTotalItem().intValue();
        int result = total / pgSize;

        if ((total == 0) || ((total % pgSize) != 0)) {
            result++;
        }

        return result;
    }

    public int getPageFristItem() {
        int cPage = this.getCurrentPage().intValue();

        if (cPage == 1) {
            return 1;
        }

        cPage--;

        int pgSize = this.getPageSize().intValue();

        return (pgSize * cPage) + 1;
    }

    public int getPageLastItem() {
        int cPage = this.getCurrentPage().intValue();
        int pgSize = this.getPageSize().intValue();
        int assumeLast = pgSize * cPage;
        int totalItem = getTotalItem().intValue();

        if (assumeLast > totalItem) {
            return totalItem;
        } else {
            return assumeLast;
        }
    }

    /**
     * @return Returns the endRow.
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * @param endRow The endRow to set.
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    /**
     * @return Returns the startRow.
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * @param startRow The startRow to set.
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * 根据开始下标和行数生成startRow和endRow，开始下标建议从1开始
     * 
     * @param startRow 从1开始
     * @param rows 行数
     */
    public void buildStartEndRow(int startRow, int rows) {
        this.startRow = startRow;

        if (this.startRow <= 0) {
            this.startRow = 1;
        }
        this.startIndex = this.startRow - 1;
        this.endRow = this.startRow;

        if (rows > 0) {
            this.endRow = (this.endRow + rows) - 1;
        }
    }

    /**
     * @return the startIndex
     */
    public Integer getStartIndex() {
        return this.startIndex;
    }

}
