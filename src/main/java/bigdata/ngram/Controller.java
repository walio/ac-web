package bigdata.ngram;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @RequestMapping(value = "/rest/predict/{history}",method = RequestMethod.GET,produces = "application/json")
    public String predict(@PathVariable String history){
        return "predict of "+history;
    }
}
