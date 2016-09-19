package Incognito_SQLExpress_Census;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Date;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class GetFrequencySet
{
	/**
    * MapReduceBase类:实现了Mapper和Reducer接口的基类（其中的方法只是实现接口，而未作任何事情）
    * Mapper接口：
    * WritableComparable接口：实现WritableComparable的类可以相互比较。所有被用作key的类应该实现此接口。
    * Reporter 则可用于报告整个应用的运行进度，本例中未使用。 
    * 
    */
    public static class Map extends MapReduceBase implements
            Mapper<LongWritable, Text, Text, IntWritable>
    {
        /**
        * LongWritable, IntWritable, Text 均是 Hadoop 中实现的用于封装 Java 数据类型的类，这些类实现了WritableComparable接口，
        * 都能够被串行化从而便于在分布式环境中进行数据交换，你可以将它们分别视为long,int,String 的替代品。
        */
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
        
        /**
        * Mapper接口中的map方法：
        * void map(K1 key, V1 value, OutputCollector<K2,V2> output, Reporter reporter)
        * 映射一个单个的输入k/v对到一个中间的k/v对
        * 输出对不需要和输入对是相同的类型，输入对可以映射到0个或多个输出对。
        * OutputCollector接口：收集Mapper和Reducer输出的<k,v>对。
        * OutputCollector接口的collect(k, v)方法:增加一个(k,v)对到output
        */
        public void map(LongWritable key, Text value,
                OutputCollector<Text, IntWritable> output, Reporter reporter)
                throws IOException
        {
            String line = value.toString();
//            StringTokenizer tokenizer = new StringTokenizer(line);
//            while (tokenizer.hasMoreTokens())
//            {
//                word.set(tokenizer.nextToken());
//                output.collect(word, one);
//            }
            
            word.set(line);
            output.collect(word, one);
        }
    }
    public static class Reduce extends MapReduceBase implements
            Reducer<Text, IntWritable, Text, IntWritable>
    {
        public void reduce(Text key, Iterator<IntWritable> values,
                OutputCollector<Text, IntWritable> output, Reporter reporter)
                throws IOException
        {
            int sum = 0;
            while (values.hasNext())
            {
                sum += values.next().get();
            }
            //if(sum >= 50)
            output.collect(key, new IntWritable(sum));
        }
    }
    
    public static class MapWithID extends MapReduceBase implements
    Mapper<LongWritable, Text, Text, Text>
	{
		/**
		* LongWritable, IntWritable, Text 均是 Hadoop 中实现的用于封装 Java 数据类型的类，这些类实现了WritableComparable接口，
		* 都能够被串行化从而便于在分布式环境中进行数据交换，你可以将它们分别视为long,int,String 的替代品。
		*/
		//private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		private Text IDandValue = new Text();
		
		/**
		* Mapper接口中的map方法：
		* void map(K1 key, V1 value, OutputCollector<K2,V2> output, Reporter reporter)
		* 映射一个单个的输入k/v对到一个中间的k/v对
		* 输出对不需要和输入对是相同的类型，输入对可以映射到0个或多个输出对。
		* OutputCollector接口：收集Mapper和Reducer输出的<k,v>对。
		* OutputCollector接口的collect(k, v)方法:增加一个(k,v)对到output
		*/
		public void map(LongWritable key, Text value,
		        OutputCollector<Text, Text> output, Reporter reporter)
		        throws IOException
		{
		    String line = value.toString();
		    
		    // Input:
		    // ID:age,address,...
		    // Output:
		    // K	age,address,...
		    // V	ID:1
		    
		    //handle with the listID
		    String listID = line.split(":")[0];
		    
		    word.set(line.split(":")[1]);
		    IDandValue.set(listID + ":1");
		    output.collect(word, IDandValue);
		}
	}
	public static class ReduceWithID extends MapReduceBase implements
	    Reducer<Text, Text, Text, Text>
	{
		public void reduce(Text key, Iterator<Text> iDandValues,
		        OutputCollector<Text, Text> output, Reporter reporter)
		        throws IOException
		{
			// Input:
		    // K	age,address,...
		    // V	ID1,ID2,ID3,...:N
			// Output is the same
			
			String listID = "";
		    int sum = 0;
		    while (iDandValues.hasNext())
		    {
		    	String valueStr = iDandValues.next().toString();
	    		listID += valueStr.split(":")[0] + ",";	//get the id of the list
		    	String value = valueStr.split(":")[1];	//get and calculate the sum of the count
		    	int count = Integer.parseInt(value);
		        sum += count;
		    }
		    listID = listID.substring(0, listID.length() - 1);	//remove the last ','
		    //if(sum >= 50)
		    output.collect(key, new Text(listID + ":" + String.valueOf(sum)));
		}
	}

//    public static class Compile extends MapReduceBase implements
//    Reducer<Text, IntWritable, Text, IntWritable>
//	{
//		public void reduce(Text key, Iterator<IntWritable> values,
//		        OutputCollector<Text, IntWritable> output, Reporter reporter)
//		        throws IOException
//		{
//		    int sum = 0;
//		    while (values.hasNext())
//		    {
//		        sum += values.next().get();
//		    }
//		    output.collect(key, new IntWritable(sum));
//		}
//	}
    public JobConf FrequencySetJob(String inputFolder, String outputFolder)
    {
    	/**
        * JobConf：map/reduce的job配置类，向hadoop框架描述map-reduce执行的工作
        * 构造方法：JobConf()、JobConf(Class exampleClass)、JobConf(Configuration conf)等
        */
        JobConf jconf = new JobConf(GetFrequencySet.class);
        jconf.setJobName("GetFrequencySet");          //设置一个用户定义的job名称
        jconf.setOutputKeyClass(Text.class);    //为job的输出数据设置Key类
        jconf.setOutputValueClass(Text.class);  //为job输出设置value类
//        jconf.setMapperClass(Map.class);        //为job设置Mapper类
//        jconf.setCombinerClass(Reduce.class);      //为job设置Combiner类
//        jconf.setReducerClass(Reduce.class);        //为job设置Reduce类
        jconf.setMapperClass(MapWithID.class);        //为job设置Mapper类
        jconf.setCombinerClass(ReduceWithID.class);      //为job设置Combiner类
        jconf.setReducerClass(ReduceWithID.class);        //为job设置Reduce类
        jconf.setInputFormat(TextInputFormat.class);    //为map-reduce任务设置InputFormat实现类
        jconf.setOutputFormat(TextOutputFormat.class);  //为map-reduce任务设置OutputFormat实现类

        /**
        * InputFormat描述map-reduce中对job的输入定义
        * setInputPaths():为map-reduce job设置路径数组作为输入列表
        * setInputPath()：为map-reduce job设置路径数组作为输出列表
        */
        FileInputFormat.setInputPaths(jconf, new Path(inputFolder));
        FileOutputFormat.setOutputPath(jconf, new Path(outputFolder));
    	
        return jconf;
    }
    
    /*
     * args[0]: input file folder
     * args[1]: output file folder
     */
    public HashMap<List<String>, String> GetFrequencySet(DataTable dt, String[] args)
    		throws Exception
    {
    	HashMap<List<String>, String> hm = null;

		HadoopHelper.HadoopConfig();
		
		long startTime = System.currentTimeMillis();
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		String inputFolder = args[0] + "/" + time;			//set input file folder
		String outputFolder = args[1] + "/" + time;	// set output file folder name
		
		String inputUrl = inputFolder + "/" + time + ".txt";	//put datatable data to input hdfs
		String outputUrl = outputFolder + "/part-00000";			//get HashMap data from output hdfs
		
//		HadoopHelper.Datatable2Hdfs(dt, inputUrl, ",");	//create file in hdfs
		HadoopHelper.Datatable2HdfsWithID(dt, inputUrl, ",");	//create file in hdfs
		
		JobConf jconf = FrequencySetJob(inputFolder, outputFolder);	//set the job
		JobClient.runJob(jconf);        		//run the job
    	
		hm = HadoopHelper.ReduceOut2MapWithID(outputUrl);
		
		long endTime = System.currentTimeMillis(); //end time
		long totalTime = 0;
		totalTime += endTime - startTime; //total time
		System.out.println("getting frequency set uses total time:" + totalTime + "ms");
		
    	return hm;
    }
    
//    public void main(String[] args) throws Exception
//    {
//		long startTime = System.currentTimeMillis(); // start time
//		HadoopHelper.HadoopConfig();
//		String[] otherArgs = new String[2];
//		otherArgs[0] = "hdfs://h1m1:9000/input/inputMPP_M/Adult10";
//		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//		otherArgs[1] = "hdfs://h1m1:9000/output/outputMPP_M/Adult10" + time;
//		
//		// set split size
//		
//		JobConf jconf = FrequencySetJob(otherArgs);
//					
//		JobClient.runJob(jconf);        //运行一个job
//		
//		long endTime = System.currentTimeMillis(); //end time
//		long totalTime = 0;
//		totalTime += endTime - startTime; //total time
//		System.out.println("total time:" + totalTime + "ms");
//    }
}