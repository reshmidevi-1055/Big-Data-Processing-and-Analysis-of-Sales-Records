# 📊 Big Data Processing and Analysis of Sales Records Dataset

### 📝 Project Description
This project focuses on analyzing a large-scale global sales dataset containing **100,000 transaction records**. Using Big Data technologies like **Hadoop MapReduce** and **Apache Pig**, the project processes and extracts meaningful business insights regarding global profits, regional distributions, sales channel performances, and top-selling commodities.

---

### 🎯 Objectives
* Extract total sales profit metrics classified by commodity item types.
* Analyze regional profit contributions globally.
* Monitor transaction volume and units sold per country.
* Identify the highest-demand consumer goods.
* Compare distribution channel efficiencies (Online vs. Offline) across profits and unit quantities.
* Group order priority distributions to understand supply-demand urgency.

---

### 🔗 Dataset Reference
* **Dataset Name:** 100,000 Sales Records
* **Source Link:** [Kaggle Dataset Link](https://kaggle.com)
* **Description:** The source archive consists of standard transactional fields including Regions, Countries, Item Types, Sales Channels, Order Priorities, Units Sold, Unit Price, Total Revenue, Total Cost, and Total Profit.

---

### 🛠️ Technologies & Environment Used
* **Operating System:** Windows Subsystem for Linux (WSL) / Ubuntu 20.04
* **Core Framework:** Apache Hadoop v3.x (HDFS, MapReduce)
* **Dataflow Language:** Apache Pig
* **Programming Language:** Java 8 (JDK 1.8)

---

### ⚡ Big Data Processing Frameworks

#### 1. Java MapReduce Tasks
Processes parallelized large text operations to summarize key financials:
* **Item Type-wise Total Profit (`ItemProfit.java`):** Aggregates overall financial margins per item type.
* **Region-wise Total Profit (`RegionProfit.java`):** Maps profit outputs directly based on target continent/region columns.
* **Country-wise Total Units Sold (`CountryUnits.java`):** Reduces dataset counts to generate chronological sum reports of item units sold per sovereign state.

#### 2. Apache Pig Scripts
Runs descriptive local pipeline tasks:
* `top_items.pig`: Computes global high-demand item sales totals.
* `task2_channel_profit.pig`: Summarizes monetary transactions filtered by channel types.
* `task3_units.pig`: Monitors product delivery density across Online and Offline operations.
* `task4_priority.pig`: Classifies internal log counts according to distinct execution priorities (Critical, High, Medium, Low).
* `task5_country_profit.pig`: Pinpoints the top 10 most profitable global market spaces.
