package psb.com.kidpaint.webApi.Category;

import psb.com.kidpaint.webApi.Category.GetCategory.GetCategory;
import psb.com.kidpaint.webApi.Category.GetCategory.iGetCategory;
import psb.com.kidpaint.webApi.paint.getAllPaints.GetAllPaints;
import psb.com.kidpaint.webApi.paint.getAllPaints.iGetAllPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.GetMyPaints;
import psb.com.kidpaint.webApi.paint.getMyPaints.iGetMyPaints;
import psb.com.kidpaint.webApi.paint.postPaint.PostPaint;
import psb.com.kidpaint.webApi.paint.postPaint.iPostPaint;

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
