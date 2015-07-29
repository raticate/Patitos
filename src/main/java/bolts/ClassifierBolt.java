package bolts;

import NaiveBayes.BayesClassifier;
import NaiveBayes.Classifier;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.google.common.base.CharMatcher;
import scala.util.parsing.combinator.testing.Str;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by cate on 29-07-15.
 */
public class ClassifierBolt extends BaseRichBolt{
    OutputCollector collector;
    static Classifier<String, Integer> bayes;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        bayes = new BayesClassifier<String, Integer>();


    }

    @Override
    public void execute(Tuple input) {
        String tweet = input.getString(0);
        collector.emit(new Values(tweet));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("filtered-tweet"));
    }
}