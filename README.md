# ParallelExperiment

A Java-based performance experiment that measures the speedup of parallel computation across different file sizes using multiprocessing and Bubble Sort.

## 🚀 Features

### 📁 File Generation

- Automatically generates **3 datasets** of **120 files each**
- **Small files** — 2000 numbers/file
- **Medium files** — 5000 numbers/file
- **Large files** — 10000 numbers/file
- Uses a **fixed random seed** to ensure reproducible results every run

### ⚙️ Parallel Processing

- Uses **ProcessBuilder** to spawn real **OS-level child processes**
- Dynamically splits **120 files across P workers** (`P = 1, 2, 3...` up to CPU core count)
- Measures **makespan** — total time from first process start to last process finish

### 🔢 Bubble Sort O(n²)

- Each worker reads a file and sorts it using **Bubble Sort**
- **O(n²)** complexity ensures computation is heavy enough to show real parallelism gains
- Results are intentionally discarded — only timing matters

### 📊 Results & Reporting

- Prints a formatted table to console for each dataset
- Exports **results.csv** for manual chart creation in Excel
- Calculates speedup using the formula:

```math
S_p = \frac{T_1}{T_p}

### Where:

T1 — execution time with 1 process
Tp — execution time with P processes
