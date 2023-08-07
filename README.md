# Tomasulo

This repository contains a mini Tomasulo algorithm, used for dynamic scheduling of instructions to optimize the performance of microprocessors developed as part of the Micro-processors course at the German University in Cairo, under the supervision of Dr.Milad Ghantous. 

## About
The mini Tomasulo project aims to demonstrate the performance of the Tomasulo algorithm in optimizing the performance of microprocessors by allowing out-of-order computation of instruction allowing the better utilization of computation cycles while maintaining the order of issuing and executions 

## Repository Contents

- `Instruction.java`: This class represents a single instruction given to the algorithm.
- `Register.java`: This class represents a single element in the instruction register.  
- `Reservation station.java `: This class represents a single reservation station that holds an instruction waiting to start or to commit its execution.
- `Tomasulo.java`: This class holds the main logic of the Tomasulo algorithm.


## Installation

1. Clone the repository:
   ```
   git clone https://github.com/karim-walid-wahdan/Tomasulo.git
   ```
2. Execute the program and follow the on-console instructions
<h1>Authors</h1>
<ul>
  <li><a href="https://github.com/karim-walid-wahdan">Karim Wahdan</a></li>
  <li><a href="https://github.com/MostKhalifa">Mostafa Khalifa</a></li>
</ul>
## Contributing

Contributions to the Tomasulo project are welcome. If you find any issues or have suggestions for improvements, please create an issue or submit a pull request.
