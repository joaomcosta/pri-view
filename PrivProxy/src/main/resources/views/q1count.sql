select l_returnflag, l_linestatus, count(l_orderkey) as count_order from lineitem where l_shipdate <= date '1998-09-01' group by l_returnflag, l_linestatus order by l_returnflag, l_linestatus