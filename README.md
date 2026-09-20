# Projeto 2 - AED (Erasmus Area Manager)

A Java console application that manages a geographic area of Erasmus students and the services (eating, lodging and leisure) they use. Built as the second project for the **Algoritmos e Estruturas de Dados (AED)** course at NOVA FCT, with a hand-rolled data-structures library (lists, hash tables, BSTs, AVL and Red-Black trees) instead of the Java Collections Framework.

## Authors

- Vicente Santos (71471) — vr.santos@campus.fct.unl.pt
- Ricardo Amorim (71365) — rja.amorim@campus.fct.unl.pt

## Overview

The app models a bounded geographic rectangle ("bounds") that contains:

- **Services** — `eating`, `lodging` and `leisure` locations, each with a price/capacity and reviews.
- **Students** — `bookish`, `outgoing` and `thrifty` types, each with different rules for visiting services and moving between them.

Everything is driven from a command-line prompt: define the area bounds, register services and students, move students around, rate services, and query the system (rankings, tags, nearest service of a type, etc.).

## Project structure

```
src/
├── Main.java              # CLI entry point: reads commands, dispatches to processing methods
├── Commands.java           # Enum of all available commands and their help text
├── App/                    # Top-level application logic (bounds, save/load, orchestration)
├── Services/                # Service model: Eating, Lodging, Leisure, Review, entry-control
├── Students/                # Student model: Bookish, Outgoing, Thrifty and shared behaviours
├── Errors/                  # Checked exceptions used for domain-specific error handling
└── dataStructures/          # Custom data structures (lists, hash tables, BST/AVL/Red-Black trees, iterators)
```

Serialized `.ser` files in the project root (`tokyo.ser`, `madagascar.ser`, `buenos_aires.ser`, `costa_do_sol.ser`, `costa_da_caparica.ser`, `costa_anywhere.ser`) are saved areas that can be restored with the `load` command.

## Building and running

Requires a JDK (17+, since the code uses `switch` expressions).

```bash
# Compile
javac -d out $(find src -name "*.java" -not -path "*/App.zip*")

# Run
java -cp out Main
```

Or open the project in IntelliJ IDEA using the included `Projeto_2_AED.iml` module file and run `Main.java` directly.

## Commands

| Command | Description |
|---|---|
| `bounds` | Defines the new geographic bounding rectangle |
| `save` | Saves the current geographic bounding rectangle to a text file |
| `load` | Loads a geographic bounding rectangle from a text file |
| `service` | Adds a new service (eating, lodging or leisure) to the current area |
| `services` | Lists all services in the current area, in order of registration |
| `student` | Adds a student to the current area |
| `students` | Lists all students, or those from a given country, alphabetically |
| `leave` | Removes a student from the current area |
| `go` | Moves a student to a leisure or eating service |
| `move` | Changes a student's home (lodging) |
| `users` | Lists students currently at a given service (eating or lodging) |
| `star` | Evaluates a service with a rating and description |
| `where` | Locates a student |
| `visited` | Lists locations visited by a student |
| `ranking` | Lists services ordered by star rating |
| `ranked` | Lists the service(s) of a type/score closest to a student |
| `tag` | Lists services with a review containing a given word |
| `find` | Finds the most relevant service of a type for a student |
| `help` | Shows the available commands |
| `exit` | Saves the area and terminates the program |
