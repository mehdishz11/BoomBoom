package psb.com.kidpaint.webApi.Category;

import psb.com.kidpaint.webApi.Category.GetCategory.GetCategory;
import psb.com.kidpaint.webApi.Category.GetCategory.iGetCategory;

public class Category implements iCategory{

    @Override
    public GetCategory getCategory() {
        return new GetCategory();
    }

    @Override
    public GetCategory getCategory(iGetCategory.iResult iResult) {
        return new GetCategory(iResult);
    }
}
