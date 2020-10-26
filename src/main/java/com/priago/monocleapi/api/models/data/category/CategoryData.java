package com.priago.monocleapi.api.models.data.category;

import com.priago.monocleapi.api.models.data.MonocleData;
import com.priago.monocleapi.core.enums.MonocleCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of {@link MonocleData} for categories
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@NoArgsConstructor
public class CategoryData implements MonocleData {

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String name;

    @Getter
    private boolean empty;


    //  CONSTRUCTORS

    public CategoryData(MonocleCategory category) {
        if (category == null) {
            this.empty = true;
            this.code = StringUtils.EMPTY;
            this.name = StringUtils.EMPTY;
        } else {
            this.empty = false;
            this.code = category.getCode();
            this.name = category.getName();
        }
    }
}
