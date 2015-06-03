import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import bolts.PrintAllBolt;
import spouts.TweetSpout;
import storm.kafka.*;


    public class Topology {
        public static void main(String[] args) throws Exception
        {
            // create the topology
            TopologyBuilder builder = new TopologyBuilder();

            /*BrokerHosts brokerHosts = new ZkHosts("localhost:2181");
            SpoutConfig kafkaConf= new SpoutConfig(brokerHosts, "patos", "", "patos");
            kafkaConf.scheme = new SchemeAsMultiScheme(new StringScheme());
            KafkaSpout kafkaSpout= new KafkaSpout(kafkaConf);
            builder.setSpout("kafka-spout", kafkaSpout, 1);*/
            String key=Properties.getString("twitter.key");
            String secret=Properties.getString("twitter.secret");
            String token=Properties.getString("twitter.token");
            String tokenSecret="ccfuYmgRJtZcRmZKp0tMClkIr1XLpLkCdRRjuJ83dlkMo";//Properties.getString("twitter.tokenSecret");
            builder.setSpout("twitter-spout", new TweetSpout(key,secret,token,tokenSecret));
            builder.setBolt("printer-bolt", new PrintAllBolt()).shuffleGrouping("twitter-spout");


            // create the default config objectK
            Config conf = new Config();

            // set the config in debugging mode
            //conf.setDebug(true);

        /*if (args != null && args.length > 0) {

            // run it in a live cluster

            // set the number of workers for running all spout and bolt tasks
            conf.setNumWorkers(3);

            // create the topology and submit with config
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());

        } else {*/

            // run it in a simulated local cluster

            // set the number of threads to run - similar to setting number of workers in live cluster
            conf.setMaxTaskParallelism(3);

            // create the local cluster instance
            LocalCluster cluster = new LocalCluster();

            // submit the topology to the local cluster
            cluster.submitTopology("RTA-DNS", conf, builder.createTopology());

            // let the topology run for 300 seconds. note topologies never terminate!
            Utils.sleep(3000000000000000000L);

            // now kill the topology
            cluster.killTopology("tweet-word-count");

            // we are done, so shutdown the local cluster
            cluster.shutdown();
            //}
        }
    }
