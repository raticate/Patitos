package bolts;


 import backtype.storm.Config;
 import backtype.storm.LocalCluster;
 import backtype.storm.StormSubmitter;
 import backtype.storm.spout.SpoutOutputCollector;
 import backtype.storm.task.OutputCollector;
 import backtype.storm.task.TopologyContext;
 import backtype.storm.testing.TestWordSpout;
 import backtype.storm.topology.OutputFieldsDeclarer;
 import backtype.storm.topology.TopologyBuilder;
 import backtype.storm.topology.base.BaseRichSpout;
 import backtype.storm.topology.base.BaseRichBolt;
 import backtype.storm.tuple.Fields;
 import backtype.storm.tuple.Tuple;
 import backtype.storm.tuple.Values;
 import backtype.storm.utils.Utils;

 import java.util.Map;


public class PrintAllBolt extends BaseRichBolt
{
    OutputCollector collector;

    @Override
    public void prepare(
            Map                     map,
            TopologyContext         topologyContext,
            OutputCollector         outputCollector)
    {
        collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple)
    {   String tweet=tuple.getString(0);
        System.out.println(tweet);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {

    }
}
