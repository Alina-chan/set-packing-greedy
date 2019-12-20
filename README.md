# Greedy Algorithm for Set Packing problem
This is an implementation of a greedy algorithm for a variation of the Set Packing problem.

## Problem description
A seller has `m` different items for sale, denoted by `j = 0, ..., m - 1`. There are `n` prospective buyers, denoted by `i = 0, ..., n - 1`. Each buyer `i` wants a particular subset of objects `Si ⊆ {0, 1,. . . , m - 1}`. It assigns to the subset of objects `Si` value `vi`, which it intends to pay. It is requested to decide on the subset of buyers to which one the seller will assign exactly the subsets of the items they request, so that: 

(i) each item `j = 0, 1,. . . , m - 1` to be given to a single buyer at most

(ii) the sum of the values of the selected buyers to be maximum.

### Example
Consider `m = 5` items and `n = 5` buyers with:
`S0 = {0,1}, v0 = 10`, `S1 = {2,3}, v1 = 15`, `S2 = {1,4}, v2 = 22`, `S3 = {0,1,2}, v3 = 30`, `S4 = {3,4}, v4 = 35`

One possible solution is to select buyers `0` and `1`. Items `S0 ∪ S1 = {0, 1, 2, 3}` are assigned and no additional buyer can be selected without us assigning one of the already assigned objects a second time. The total value of the solution is 25. The best possible solution is selecting the buyers `3` and `4`, with a total value of 65.

## Project requirements
The project requires us to implement a greedy algorithm and to experiment with it to approximate the problem when each set of `Si, i = 0, 1,. . . , n - 1` is represented by a simple linked list and all buyers are also represented by a simple linked list. The greedy algorithm is as follows:

```
Input:  object indexes M = {0, ..., m - 1}, 
        buyer indexes N = {0, ..., n - 1}, 
        buyer data for i = 0,1, ..., n - 1 : Si ⊆ M, vi
        
Exit:   subset of B ⊆ N buyers indices

1. initialize B: = ∅
2. Repeat:
      1. Find i ∈ N with Si ⊆ M and maximum fraction vi / | Si |
      2. If Si !⊆ M for each i ∈ N, return B
      3. Delete i from N, insert i into solution B, set M := M \ Si
```

In the numerical example, this algorithm will first select buyer `4` and then buyer `3` by fully maximizing the sum of values. But this is not the case for every entry of the problem. Thus, the greedy algorithm does not guarantee an optimal solution (maximum value).


## Data files and experimentation

There are 10 data files describing inputs of different dimensions of the problem. Specifically: 5 files with dimension problems `m = 500` and `n = 1000, 3000, 5000, 7000, 9000` and 5 files with dimension problems `n = 2000` and `m = 200, 400, 600, 800, 1000`. First line of each file gives the amount of `m` objects, `n` buyers and the optimal value.

You will execute the algorithm for each file. You will compare the value of the solution returned, with the value of the optimal solution in the file and listed in the `opt` field of the `BuyersList` list, by the `readFile` method. By running the program, main will print the results on the screen as follows:

```
* m = 500:
− n = 1000 avgTime = ...... greedy value = .... opt value = ....
................................................................
* n = 2000:
− m = 200 avgTime = ...... greedy value = .... opt value = ....
...............................................................
```
