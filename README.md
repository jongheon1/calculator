# Simple Command Line Calculator

A command line calculator that supports basic arithmetic operations, decimal numbers, negative numbers, and parentheses.

## Features
- Basic arithmetic operations (+, -, *, /)
- Parentheses support for operation precedence
- Decimal numbers
- Negative numbers

## Example Usage
```
Calculator started. Type 'exit' to quit.

Enter expression: 1 + 2 * 3
Result: 7

Enter expression: (1 + 2) * 3
Result: 9

Enter expression: -1.5 + 2.5
Result: 1.0
```

## Architecture
The calculator consists of three main components:
1. **Parser**: Converts string input into tokens
2. **PostfixConverter**: Converts infix notation to postfix notation
3. **Calculator**: Evaluates postfix expression


## Build and Run
To build and run the calculator:
```bash
# Build
./gradlew build

# Run
./gradlew run
```