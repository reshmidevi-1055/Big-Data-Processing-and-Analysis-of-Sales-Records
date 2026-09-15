sales = LOAD '/home/resmi/sales.csv' USING PigStorage(',') AS (
    region:chararray,
    country:chararray,
    item:chararray,
    channel:chararray,
    priority:chararray,
    date:chararray,
    id:chararray,
    ship_date:chararray,
    units:chararray,
    price:chararray,
    cost:chararray,
    revenue:chararray,
    total_cost:chararray,
    profit:double
);

filtered_data = FILTER sales BY channel != 'Sales Channel' AND channel IS NOT NULL;
grouped_data = GROUP filtered_data BY channel;
channel_profit = FOREACH grouped_data GENERATE group AS sales_channel, SUM(filtered_data.profit) AS total_profit;

DUMP channel_profit;
