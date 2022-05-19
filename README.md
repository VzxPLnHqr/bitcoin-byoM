# bitcoin-byoM (bring your own Monad!)

Let us make writing bitcoin software more complex by adding
some "abstract nonsense" in. Yes, the abstract nonsense we refer
to here is that of the mathematical branch known as category theory.

Joking aside, what this way of thinking allows us to (hopefully) do is abstract 
away the finer details, the parts of a specific algorithm that we do not care
much about, and focus on getting the core correct.

In practice, this means that these algorithms will not be very optimized. However,
they should be presented in a way which aligns more with their mathematical structure.

Optimizations come after understanding. This project is an effort to better understand
bitcoin by explaining it to an (abstract) computer.

# Example Usage
// TBD

## Setup
1. use the [Nix](https://nixos.org) package manager to install dependencies by first installing Nix and then running `nix-shell -p scala` which will ensure that you have a decent version of scala/java installed
2. for the build, we use the `mill` build tool [Mill Website](https://com-lihaoyi.github.io/mill), which also requires java
3. a bootstrap script for mill has been checked into the repository already
4. `./mill -i main.run` runs the `main` module of the `bulid.sc` project (the `-i` allows for `readLine` to work properly)

## License

Public domain. 


