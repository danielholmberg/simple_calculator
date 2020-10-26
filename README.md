# simple_calculator by Daniel Holmberg
SECTRA programming assignment – The Simple Calculator
`
## Assumptions
I'm making the following assumptions: 
- `<value>` only allows *positive* numbers
- `<register>` has a *one-way (directed)* relationship to each adjacent `<register>`

## Run the Calculator
  1. `git clone git@github.com:danielholmberg/simple_calculator.git`
  2. `cd simple_calculator`
  3. Compile the Java Source Code `javac src/*.java -d bin/classes`
  4. Run the Calculator `java -cp bin/classes SimpleCalculator`
  
## Test the Calculator with a file
  1. Select the `2. Use a file` choice from the interactive menu
  2. Type in the file-path (based on your current directory) of the file you want to use, e.g. `test/test.txt` 
  
## Adding additions
  1. Add a new field to `enum OperationType` in `OperationType.java`
  2. Add a switch-case to functions `parseOperationType(String operationParam)` and `quickMath(OperationEdge edge)` in `CalculatorGraph.java`
  3. **Optional** - Make necessary changes for correct input parsing in `SimpleCalculator.java`)