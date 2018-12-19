package psb.com.kidpaint.webApi.match;

import psb.com.kidpaint.webApi.match.Get.GetMatch;
import psb.com.kidpaint.webApi.match.Get.iGetMatch;

public interface iMatch {


   GetMatch getMatch();
   GetMatch getMatch(iGetMatch.iResult iResult);

}
