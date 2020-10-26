package com.priago.monocleapi.api.models.data.search;

import com.priago.monocleapi.api.models.data.MonocleData;
import com.priago.monocleapi.core.enums.SortOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * A class representation of a pagination object used by Monocle
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@NoArgsConstructor
public class MonoclePageAndSortData implements MonocleData {

    @Getter
    private int pageNumber;

    @Getter
    private int pageSize;

    @Getter
    private String attribute;

    @Getter
    private SortOrder sortOrder;

    @Getter
    private boolean empty;


    //  CONSTRUCTORS

    public MonoclePageAndSortData(int pageNumber, int pageSize, String attribute, String sortOrder) {
        if (pageNumber < 0 || pageSize <= 0 || StringUtils.isEmpty(attribute) || StringUtils.isEmpty(sortOrder)) {
            this.empty = true;
            this.pageNumber = -1;
            this.pageSize = -1;
            this.attribute = StringUtils.EMPTY;
            this.sortOrder = null;
        } else {
            this.empty = false;
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
            this.attribute = attribute;
            this.sortOrder = EnumUtils.isValidEnumIgnoreCase(SortOrder.class, sortOrder) ? SortOrder.valueOf(sortOrder.toUpperCase()) : SortOrder.DESCENDING;
        }
    }

    public MonoclePageAndSortData(int pageNumber, int pageSize, String attribute, SortOrder sortOrder) {
        if (pageNumber < 0 || pageSize <= 0 || StringUtils.isEmpty(attribute) || sortOrder == null) {
            this.empty = true;
            this.pageNumber = -1;
            this.pageSize = -1;
            this.attribute = StringUtils.EMPTY;
            this.sortOrder = null;
        } else {
            this.empty = false;
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
            this.attribute = attribute;
            this.sortOrder = sortOrder;
        }
    }
}
