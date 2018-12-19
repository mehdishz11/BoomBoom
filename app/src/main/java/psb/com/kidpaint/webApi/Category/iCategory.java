package psb.com.kidpaint.webApi.Category;


import psb.com.kidpaint.webApi.Category.GetCategory.GetCategory;
import psb.com.kidpaint.webApi.Category.GetCategory.iGetCategory;

public interface iCategory {

   String apiAddress = "Category/";

   GetCategory getCategory();
   GetCategory getCategory(iGetCategory.iResult iResult);

}
