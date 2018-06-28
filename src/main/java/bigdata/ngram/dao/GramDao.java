package bigdata.ngram.dao;

public interface GramDao {
    String getLambda();

    String[] getTopByNumber(int topK);

    String[] getTopByProb(double totalProb);
}
