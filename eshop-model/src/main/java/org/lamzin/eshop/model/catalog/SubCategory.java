package org.lamzin.eshop.model.catalog;


import org.hibernate.validator.constraints.NotEmpty;
import org.lamzin.eshop.validation.IsNotPersist;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Dmitriy on 04/11/2015.
 */
@Entity
public class SubCategory {
    @Id
    @IsNotPersist(typeName = SubCategory.class, fieldName = "subCategoryId")
    @NotEmpty
    private String subCategoryId;

    @NotEmpty
    private String subCategoryName;

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategory) {
        this.subCategoryId = subCategory;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubCategory)) return false;

        SubCategory that = (SubCategory) o;

        if (subCategoryId != null ? !subCategoryId.equals(that.subCategoryId) : that.subCategoryId != null)
            return false;
        return !(subCategoryName != null ? !subCategoryName.equals(that.subCategoryName) : that.subCategoryName != null);

    }

    @Override
    public int hashCode() {
        int result = subCategoryId != null ? subCategoryId.hashCode() : 0;
        result = 31 * result + (subCategoryName != null ? subCategoryName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "subCategoryId='" + subCategoryId + '\'' +
                ", subCategoryName='" + subCategoryName + '\'' +
                '}';
    }
}
