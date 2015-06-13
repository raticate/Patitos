package bolts;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.google.common.base.CharMatcher;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cate on 6/12/15.
 */
public class FilterWeirdCharsBolt extends BaseRichBolt {
    OutputCollector collector;
    static Pattern nonASCII;
    static CharMatcher matcher;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        //nonASCII = Pattern.compile("[^\\x00-\\x7f]");
        matcher = new CharMatcher() {
            @Override
            public boolean matches(char c) {
                return c <= '\u007f';
            }
        };

    }

    @Override
    public void execute(Tuple input) {
        String tweet = input.getString(0);
        tweet = matcher.retainFrom(tweet);
        collector.emit(new Values(tweet));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("filtered-tweet"));
    }
}
