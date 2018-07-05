package bigdata.ngram.service.impl;

import bigdata.ngram.dao.impl.ImplGramDao;
import bigdata.ngram.model.Gram;
import bigdata.ngram.model.Predict;
import bigdata.ngram.service.GramService;

import java.util.ArrayList;

public class ImplGramService implements GramService {
    @Override
    public String[] getPredict(String history) {
        ImplGramDao gramDao = new ImplGramDao();
        Gram gram = gramDao.getGram(history,30);
        ArrayList<String> predicts = new ArrayList<>();
        for (Predict predict:gram.getPredictsByNumber(10)){
            predicts.add(predict.getFollowing());
        }
        return (String[]) predicts.toArray();
    }
}
