# Conway's Game of Life
Developed as my 7.5HD Improved Custom Program for KIT101 Programming Fundamentals.

# Game Rules
1. Any live cell with fewer than two live neighbors dies, as if by under population.
2. Any live cell with two or three live neighbors lives on to the next generation.
3. Any live cell with more than three live neighbors dies, as if by overpopulation.
4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.

# Features
* Grid creation of set size (125x125).
* Ability to set cells to alive or dead states using mouse.
* Saving / Loading of grid states as .grid file format.
* Adding additional grid states to existing grid.
* Generation speed adjustment (generations per second).

**Game of Life Behaviour**
<p align="center">
  <img width="460" height="300" src="https://media.giphy.com/media/dxrMOg2B7hKTxf0fBS/giphy.gif">
</p>

**Showcase of the Application**
<p align="center">
  <img width="460" height="300" src="https://media.giphy.com/media/j4wQrjM7mQxXcP22Xq/giphy.gif">
</p>

**Application Structure Chart**
<p align="center">
  <img width="460" height="300" src="https://i.ibb.co/YkdGYck/7-5-HD-Structure-Chart.png">
</p>

In order for the simulation to run without the GUI freezing up, it required the implementation of multiple threads. A new thread is created once the simulation is run, and then stopped once the simulation has ended.

[Narrated preview of the application!](https://www.youtube.com/watch?v=RbKH78xsf2w)
