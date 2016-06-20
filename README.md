# imdg-samples
Some (Hazelcast-based) Sample Code for my talk "In-Memory Data Grids - Supercomputing for the Rest of Us"
   
## Autodiscovery

start Node 1 => console
start Node 1 => console
kill 1 => console

## Producer / Consumer

start producer
start consumer => console (migration)
start consumer 2 => console

## Executor

start Node 1, 2
start producer (up to 4 times)
start executor => console (hello on all nodes)
start executor 2 => capitals on up to four nodes
kill one capital node
start executor 2 => capital(s) of the killed node on other node(s)   

