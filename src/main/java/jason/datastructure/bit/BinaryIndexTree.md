##Overview
BinaryIndexTree is an efficient data structure for **dynamic** range query.

If the input is not dynamic, we could use prefix sum. Prefix sum can give tge range sum in constant time.

lastbit = index & -index;
