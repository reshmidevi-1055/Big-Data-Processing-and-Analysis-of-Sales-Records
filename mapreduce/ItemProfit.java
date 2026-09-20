import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ItemProfit {
    public static class MapClass extends Mapper<LongWritable, Text, Text, DoubleWritable> {
        private Text itemType = new Text();
        private DoubleWritable profit = new DoubleWritable();

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (line.startsWith("Region")) return;
            String[] tokens = line.split(",");
            if (tokens.length > 13) {
                try {
                    String item = tokens[2].trim();
                    double profitVal = Double.parseDouble(tokens[13].trim());
                    itemType.set(item);
                    profit.set(profitVal);
                    context.write(itemType, profit);
                } catch (Exception e) {}
            }
        }
    }

    public static class ReducerClass extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
        @Override
        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
            double totalProfit = 0;
            for (DoubleWritable val : values) {
                totalProfit += val.get();
            }
            context.write(key, new DoubleWritable(totalProfit));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Item Profit");
        job.setJarByClass(ItemProfit.class);
        job.setMapperClass(MapClass.class);
        job.setReducerClass(ReducerClass.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
