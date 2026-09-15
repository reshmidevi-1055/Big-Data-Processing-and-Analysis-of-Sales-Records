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
    profit:chararray
);

filtered_data = FILTER sales BY item != 'Item Type' AND item IS NOT NULL;
grouped_data = GROUP filtered_data BY item;
counted_data = FOREACH grouped_data GENERATE group AS item_type, COUNT(filtered_data) AS total_orders;

DUMP counted_data;
