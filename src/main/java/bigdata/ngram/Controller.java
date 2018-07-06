package bigdata.ngram;
import bigdata.ngram.service.impl.ImplGramService;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @RequestMapping(value = "/v1/predict/{history}",method = RequestMethod.GET,produces = "application/json")
    public String[] predict(@PathVariable String history){
        ImplGramService gramService = new ImplGramService();
        return gramService.getPredict(history);
    }
}
