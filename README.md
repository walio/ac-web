# ac-web
web of auto-complete，实现了一个非常非常非常简易的restful服务器，利用了dao的思想，可能设计的确实太复杂，用mysql也优于hbase，主要为了练习
#### 流程
1. 首先根据input查找habse有无对应的history，找不到则回退，确定最终
backoffhistory
2. 对高阶和低阶概率进行归并排序，其中低阶概率需要乘以对应的lambda