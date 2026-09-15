import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CountryUnits {
    public static class MapClass extends Mapper<LongWritable, Text, Text, LongWritable> {
        private Text country = new Text();
        private LongWritable units = new LongWritable();

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (line.startsWith("Region")) return;
            String[] tokens = line.split(",");
            if (tokens.length > 8) {
                try {
                    String ctry = tokens[1].trim();
                    long unitsVal = Long.parseLong(tokens[8].trim());
                    country.set(ctry);
                    units.set(unitsVal);
                    context.write(country, units);
                } catch (Exception e) {}
            }
        }
    }

    public static class ReducerClass extends Reducer<Text, LongWritable, Text, LongWritable> {
        @Override
        public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            long totalUnits = 0;
            for (LongWritable val : values) {
                totalUnits += val.get();
            }
            context.write(key, new LongWritable(totalUnits));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Country Units");
        job.setJarByClass(CountryUnits.class);
        job.setMapperClass(MapClass.class);
        job.setReducerClass(ReducerClass.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
